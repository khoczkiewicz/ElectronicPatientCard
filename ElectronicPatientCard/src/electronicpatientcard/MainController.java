/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electronicpatientcard;

import electronicpatientcard.Utils.DataContext;
import electronicpatientcard.Utils.TableRow;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author root
 */
public class MainController implements Initializable, Runnable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TableView<TableRow> tableView;
    private DataContext dataContext;
    private final ObservableList<TableRow> observableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dataContext = new DataContext();
        List<org.hl7.fhir.dstu3.model.Patient> patientList = dataContext.GetPatients();

        TableColumn<TableRow, String> id = new TableColumn<>("Identyfikator");
        id.setCellValueFactory(new PropertyValueFactory("id"));

        TableColumn<TableRow, String> name = new TableColumn<>("Imię");
        name.setCellValueFactory(new PropertyValueFactory("firstName"));

        TableColumn<TableRow, String> surName = new TableColumn<>("Nazwisko");
        surName.setCellValueFactory(new PropertyValueFactory("familyName"));

        TableColumn<TableRow, String> maidenName = new TableColumn<>("Nazwisko panieńskie");
        maidenName.setCellValueFactory(new PropertyValueFactory("maidenName"));

        TableColumn<TableRow, String> birthDate = new TableColumn<>("Data urodzenia");
        birthDate.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));

        tableView.setEditable(false);
        tableView.setSortPolicy(new CallbackImpl());

        patientList.forEach((p) -> {
            observableList.add(dataContext.getBasicData(p));
        });

        tableView.setItems(observableList);

        tableView.getColumns().addAll(name, surName, maidenName, birthDate);
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static class CallbackImpl implements Callback<TableView<TableRow>, Boolean> {

        public CallbackImpl() {
        }

        @Override
        public Boolean call(TableView<TableRow> param) {
            return false;
        }
    }

}
