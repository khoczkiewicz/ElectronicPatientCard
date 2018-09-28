/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electronicpatientcard.Utils;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.api.ServerValidationModeEnum;
import java.util.ArrayList;
import java.util.List;
import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.dstu3.model.Patient;
import org.hl7.fhir.dstu3.model.Resource;

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
        if (patient.getName().size() > 1) {
            if (patient.getName().get(1).getUse().toString().equals("MAIDEN")) {
                maidenName = patient.getName().get(1).getFamily();
            }

        }

        return new TableRow(name, surName, maidenName, birthDate);
    }
}
