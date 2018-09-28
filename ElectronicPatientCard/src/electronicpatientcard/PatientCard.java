/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electronicpatientcard;

import electronicpatientcard.Classes.VirtualPatient;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 *
 * @author root
 */
public class PatientCard extends Application {

    public void start(Stage window) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("PatientCard.fxml"));
        Scene scene = new Scene(root, 800, 600);
        window.setScene(scene);
        window.show();
    }

    @FXML
    Label firstName;
    @FXML
    Label surName;
    @FXML
    Label maidenName;
    @FXML
    Label birthDate;
    @FXML
    Label gender;
    @FXML
    Label phone;
    @FXML
    Label city;
    @FXML
    Label country;
    @FXML
    Label state;
    @FXML
    Label postalCode;

    public void getPatient(VirtualPatient virtualPatient) {
        this.firstName.setText(virtualPatient.getFirstName());
        this.surName.setText(virtualPatient.getSurName());
        this.maidenName.setText(virtualPatient.getMaidenName());
        this.birthDate.setText(virtualPatient.getBirthDate());
        this.gender.setText(virtualPatient.getGender());
        this.phone.setText(virtualPatient.getPhone());
        this.city.setText(virtualPatient.getCity());
        this.country.setText(virtualPatient.getCountry());
        this.state.setText(virtualPatient.getState());
        this.postalCode.setText(virtualPatient.getPostalCode());
    }
}
