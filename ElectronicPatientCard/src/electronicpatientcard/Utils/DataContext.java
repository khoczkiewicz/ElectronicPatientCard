/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electronicpatientcard.Utils;

import electronicpatientcard.Classes.VirtualPatient;
import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.api.ServerValidationModeEnum;
import ca.uhn.fhir.rest.client.interceptor.LoggingInterceptor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.dstu3.model.IdType;
import org.hl7.fhir.dstu3.model.Parameters;
import org.hl7.fhir.dstu3.model.Patient;
import org.hl7.fhir.dstu3.model.Resource;
import org.hl7.fhir.r4.model.api.IBaseBundle;

/**
 *
 * @author root
 */
public class DataContext {

    private final FhirContext fhirContext;
    private final IGenericClient iGenericClient;
    private List<Patient> patients = new ArrayList<>();

    public DataContext() {
        fhirContext = FhirContext.forDstu3();
        fhirContext.getRestfulClientFactory().setServerValidationMode(ServerValidationModeEnum.NEVER);
        String serverBase = "http://localhost:8080/baseDstu3/";
        iGenericClient = fhirContext.newRestfulGenericClient(serverBase);
    }

    public List<Patient> GetPatients() {
        Bundle bundle = iGenericClient.search().forResource(Patient.class).returnBundle(Bundle.class).execute();

        patients = getPatientsFromBundle(bundle);
        return patients;
    }

    private ArrayList<Patient> getPatientsFromBundle(Bundle bundle) {

        int total = bundle.getTotal();
        ArrayList<Patient> allPatients = new ArrayList<>();

        do {

            List<Bundle.BundleEntryComponent> entries = bundle.getEntry();

            entries.stream().map((e) -> e.getResource()).map((y) -> (Patient) y).forEachOrdered((patient) -> {
                allPatients.add(patient);
            });

            if (allPatients.size() < total) {
                bundle = iGenericClient.loadPage().next(bundle).execute();
            }

        } while (allPatients.size() < total);

        return allPatients;
    }

    public TableRow getBasicData(Patient patient) {

        String id = "";
        String name = "";
        String surName = "";
        String maidenName = "";
        String birthDate = "";

        if (patient.getName().get(0).getGiven() == null) {
            return new TableRow(name, maidenName, surName, birthDate);
        }
        name = patient.getName().get(0).getGiven().get(0) == null ? "" : patient.getName().get(0).getGiven().get(0).toString();
        surName = patient.getName().get(0).getFamily();

        birthDate = patient.getBirthDate() == null ? "" : patient.getBirthDate().toString();
        id = patient.getId().substring(40, 76);
        if (patient.getName().size() > 1) {
            if (patient.getName().get(1).getUse().toString().equals("MAIDEN")) {
                maidenName = patient.getName().get(1).getFamily();
            }
        }

        return new TableRow(id, name, surName, maidenName, birthDate);
    }

    VirtualPatient virtualPatient;

    public VirtualPatient GetDetailedInfo(String patientId) {
        iGenericClient.registerInterceptor(new LoggingInterceptor(true));

        Parameters parameters = iGenericClient
                .operation()
                .onInstance(new IdType("Patient", patientId))
                .named("$everything")
                .withNoParameters(Parameters.class).useHttpGet()
                .execute();

        Bundle bundle = (Bundle) parameters.getParameterFirstRep().getResource();

        do {
            bundle.getEntry().forEach((entry) -> {
                Resource resource = entry.getResource();
                if (resource instanceof Patient) {
                    Patient patient = (Patient) resource;
                    String id = patient.getId().substring(40, 76);
                    String firstName = patient.getName().get(0).getGiven().get(0).toString();
                    String surName = patient.getName().get(0).getFamily();
                    String city = patient.getAddress().get(0).getCity();
                    String state = patient.getAddress().get(0).getState();
                    String postalCode = patient.getAddress().get(0).getPostalCode();
                    String country = patient.getAddress().get(0).getCountry();
                    Date birthDate = patient.getBirthDate();
                    String gender = patient.getGender().toString();
                    String maidenName = "";
                    String phone = patient.getTelecom().get(0).getValue().split(" ")[0];

                    if (patient.getName().size() > 1) {
                        if (patient.getName().get(1).getUse().toString().equals("MAIDEN")) {
                            maidenName += patient.getName().get(1).getFamily();
                        }
                    }
                    virtualPatient = new VirtualPatient(id, firstName, surName, maidenName, state, gender, phone, city, country, state, postalCode);
                }
            });
            if (bundle.getLink(IBaseBundle.LINK_NEXT) != null) {
                Bundle next = iGenericClient.loadPage().next(bundle).execute();
                bundle = next;
            }
        } while (bundle.getLink(IBaseBundle.LINK_NEXT) != null);

        return virtualPatient;
    }
}
