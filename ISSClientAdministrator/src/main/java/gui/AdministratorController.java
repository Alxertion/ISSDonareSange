package gui;

import JavaResources.Controller;
import JavaResources.Service.Service;
import JavaResources.View.FXMLEnum;
import JavaResources.View.Loader;
import JavaResources.View.StageManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;
import services.IObserver;
import services.IServices;

import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Objects;

public class AdministratorController extends UnicastRemoteObject implements Controller, IObserver,Serializable {
    private StageManager stageManager;
    private IServices service;
    private Loader loader;
    private ObservableList<Medic> modelMedici;
    private ObservableList<PersonalTransfuzii> modelPersonal;
    private ObservableList<Spital> modelSpitale;
    private ObservableList<CentruTransfuzii> modelCentreTransfuzii;
    private Cont user;

    @FXML
    private TableView<Medic> medicTableView;
    @FXML
    private TableView<PersonalTransfuzii> personalTransfuziiTableView;
    @FXML
    private TableView<Spital> spitalTableView;
    @FXML
    private TableView<CentruTransfuzii> centreTransfuziiTableView;
    @FXML
    private TextField longitudineSpitalTextField;
    @FXML
    private TextField latitudineSpitalTextField;
    @FXML
    private TextField numeSpitalTextField;
    @FXML
    private TextField idSpitalTextField;
    @FXML
    private TextField longitudineCentruTransfuziiTextField;
    @FXML
    private TextField latitudineCentruTransfuziiTextField;
    @FXML
    private TextField numeCentruTransfuziiTextField;
    @FXML
    private TextField idCentruTransfuziiTextField;
    @FXML
    private ComboBox<String> centruPersonalCombobox;
    @FXML
    private ComboBox<String> spitalMedicComboBox;
    @FXML
    private TextField numeMedicTextField;
    @FXML
    private TextField prenumeMedicTextField;
    @FXML
    private TextField utilizatorMedicTextField;
    @FXML
    private PasswordField parolaMedicTextField;
    @FXML
    private TextField numePersonalTextField;
    @FXML
    private TextField prenumePersonalTextField;
    @FXML
    private TextField utilizatorPersonalTextField;
    @FXML
    private PasswordField parolaPersonalTextField;
    @FXML
    private TextField cnpPersonalTextField;
    @FXML
    private TextField cnpMedicTextField;
    @FXML
    private TextField emailMedicTextField;
    @FXML
    private TextField emailPersonalTextField;


    public AdministratorController() throws RemoteException {
    }

    @Override
    public void initialize(StageManager stageManager, IServices service, Loader loader) {
        this.stageManager=stageManager;
        this.loader=loader;
        this.service=service;

        modelMedici = FXCollections.observableArrayList(service.getMedici());
        medicTableView.setItems(modelMedici);

        modelPersonal = FXCollections.observableArrayList(service.getPersonalTransfuzii());
        personalTransfuziiTableView.setItems(modelPersonal);

        modelCentreTransfuzii = FXCollections.observableArrayList(service.getCentreTransfuzii());
        centreTransfuziiTableView.setItems(modelCentreTransfuzii);

        modelSpitale = FXCollections.observableArrayList(service.getSpitale());
        spitalTableView.setItems(modelSpitale);

        medicTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Platform.runLater(() -> {
                    Medic medic = medicTableView.getSelectionModel().getSelectedItem();
                    numeMedicTextField.setText(medic.getNume());
                    prenumeMedicTextField.setText(medic.getPrenume());
                    utilizatorMedicTextField.setText(medic.getUsername());
                    cnpMedicTextField.setText(medic.getCNP());
                    emailMedicTextField.setText(medic.getEmail());
                    for (String valoare : spitalMedicComboBox.getItems()) {
                        if (Integer.parseInt(valoare.split(",")[0]) == medic.getIdSpital()) {
                            spitalMedicComboBox.setValue(valoare);
                            break;
                        }
                    }
                });
            }
        });

        personalTransfuziiTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Platform.runLater(() -> {
                    PersonalTransfuzii personalTransfuzii = personalTransfuziiTableView.getSelectionModel().getSelectedItem();
                    numePersonalTextField.setText(personalTransfuzii.getNume());
                    prenumePersonalTextField.setText(personalTransfuzii.getPrenume());
                    utilizatorPersonalTextField.setText(personalTransfuzii.getUsername());
                    cnpPersonalTextField.setText(personalTransfuzii.getCNP());
                    emailPersonalTextField.setText(personalTransfuzii.getEmail());
                    for (String valoare : centruPersonalCombobox.getItems()) {
                        if (Integer.parseInt(valoare.split(",")[0]) == personalTransfuzii.getIdCentruTransfuzii()) {
                            centruPersonalCombobox.setValue(valoare);
                            break;
                        }
                    }
                });
            }
        });

        spitalTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Platform.runLater(() -> {
                    Spital spital = spitalTableView.getSelectionModel().getSelectedItem();
                    idSpitalTextField.setText(spital.getIdSpital().toString());
                    numeSpitalTextField.setText(spital.getNume());
                    longitudineSpitalTextField.setText(((Double)spital.getLongitudine()).toString());
                    latitudineSpitalTextField.setText(((Double)spital.getLatitudine()).toString());
                });
            }
        });

        centreTransfuziiTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Platform.runLater(() -> {
                    CentruTransfuzii centruTransfuzii = centreTransfuziiTableView.getSelectionModel().getSelectedItem();
                    idCentruTransfuziiTextField.setText(centruTransfuzii.getIdCentruTransfuzii().toString());
                    numeCentruTransfuziiTextField.setText(centruTransfuzii.getNume());
                    longitudineCentruTransfuziiTextField.setText(((Double)centruTransfuzii.getLongitudine()).toString());
                    latitudineCentruTransfuziiTextField.setText(((Double)centruTransfuzii.getLatitudine()).toString());
                });
            }
        });

        populareCentruPersonalCombobox();
        populareSpitalMedicCombobox();
    }

    @Override
    public void prepareWindow() {

    }

    @Override
    public void setUser(Cont user) {
        this.user=user;
    }

    @Override
    public void notifyClient() throws RemoteException {
        System.out.println("Am fost notificat -> Administrator");
    }

    private void showMessage(Alert.AlertType type, String header, String text) {
        Alert message = new Alert(type);
        message.setHeaderText(header);
        message.setContentText(text);
        message.showAndWait();
    }

    private void showErrorMessage(String text) {
        showMessage(Alert.AlertType.WARNING, "Warning!", text);
    }

    @FXML
    public void logOut(ActionEvent actionEvent) {
        try {
            FXMLLoader loaderFXML = new FXMLLoader();
            loaderFXML.setLocation(getClass().getResource(FXMLEnum.LoginWindowAdministrator.getFxmlFile()));
            Parent rootNode = loaderFXML.load();
            service.logout(this.user);
            stageManager.switchScene(FXMLEnum.LoginWindowAdministrator, rootNode, loaderFXML.getController(), loader);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    private void populareSpitalMedicCombobox() {
        spitalMedicComboBox.getItems().clear();
        for (Spital s : modelSpitale)
            spitalMedicComboBox.getItems().add("" + s.getIdSpital() + "," + s.getNume());
    }

    private void populareCentruPersonalCombobox() {
        centruPersonalCombobox.getItems().clear();
        for (CentruTransfuzii c : modelCentreTransfuzii)
            centruPersonalCombobox.getItems().add("" + c.getIdCentruTransfuzii() + "," + c.getNume());
    }

    public void adaugareSpital(ActionEvent actionEvent) {
        try {
            Spital spital = new Spital(Integer.parseInt(idSpitalTextField.getText()),
                    numeSpitalTextField.getText(),
                    Double.parseDouble(longitudineSpitalTextField.getText()),
                    Double.parseDouble(latitudineSpitalTextField.getText()));
            service.adaugaSpital(spital);
            modelSpitale.setAll(FXCollections.observableArrayList(service.getSpitale()));
            populareSpitalMedicCombobox();
        }
        catch (Exception ignored) {
            showErrorMessage("Operatiunea nu a putut fi finalizata!");
        }
    }

    public void modificareSpital(ActionEvent actionEvent) {
        try {
            Spital spital = new Spital(Integer.parseInt(idSpitalTextField.getText()),
                    numeSpitalTextField.getText(),
                    Double.parseDouble(longitudineSpitalTextField.getText()),
                    Double.parseDouble(latitudineSpitalTextField.getText()));
            service.modificaSpital(spital);
            modelSpitale.setAll(FXCollections.observableArrayList(service.getSpitale()));
            populareSpitalMedicCombobox();
        }
        catch (Exception ignored) {
            showErrorMessage("Operatiunea nu a putut fi finalizata!");
        }
    }

    public void stergereSpital(ActionEvent actionEvent) {
        try {
            service.stergeSpital(Integer.parseInt(idSpitalTextField.getText()));
            modelSpitale.setAll(FXCollections.observableArrayList(service.getSpitale()));
            populareSpitalMedicCombobox();
        }
        catch (Exception ignored) {
            showErrorMessage("Operatiunea nu a putut fi finalizata!");
        }
    }

    public void adaugareCentru(ActionEvent actionEvent) {
        try {
            CentruTransfuzii centruTransfuzii = new CentruTransfuzii(Integer.parseInt(idCentruTransfuziiTextField.getText()),
                    numeCentruTransfuziiTextField.getText(),
                    Double.parseDouble(longitudineCentruTransfuziiTextField.getText()),
                    Double.parseDouble(latitudineCentruTransfuziiTextField.getText()));
            service.adaugaCentruTransfuzii(centruTransfuzii);
            modelCentreTransfuzii.setAll(FXCollections.observableArrayList(service.getCentreTransfuzii()));
            populareCentruPersonalCombobox();
        }
        catch (Exception ignored) {
            showErrorMessage("Operatiunea nu a putut fi finalizata!");
        }
    }

    public void modificareCentru(ActionEvent actionEvent) {
        try {
            CentruTransfuzii centruTransfuzii = new CentruTransfuzii(Integer.parseInt(idCentruTransfuziiTextField.getText()),
                    numeCentruTransfuziiTextField.getText(),
                    Double.parseDouble(longitudineCentruTransfuziiTextField.getText()),
                    Double.parseDouble(latitudineCentruTransfuziiTextField.getText()));
            service.modificaCentruTransfuzii(centruTransfuzii);
            modelCentreTransfuzii.setAll(FXCollections.observableArrayList(service.getCentreTransfuzii()));
            populareCentruPersonalCombobox();
        }
        catch (Exception ignored) {
            showErrorMessage("Operatiunea nu a putut fi finalizata!");
        }
    }

    public void stergereCentru(ActionEvent actionEvent) {
        try {
            service.stergeCentruTransfuzii(Integer.parseInt(idCentruTransfuziiTextField.getText()));
            modelCentreTransfuzii.setAll(FXCollections.observableArrayList(service.getCentreTransfuzii()));
            populareCentruPersonalCombobox();
        }
        catch (Exception ignored) {
            showErrorMessage("Operatiunea nu a putut fi finalizata!");
        }
    }

    public void adaugareMedic(ActionEvent actionEvent) {
        try {
            int max = 0;
            for (Medic medic : modelMedici) {
                if (medic.getIdMedic() > max)
                    max = medic.getIdMedic();
            }
            max++;
            Medic medic = new Medic(numeMedicTextField.getText(),
                    prenumeMedicTextField.getText(),
                    new Cont(utilizatorMedicTextField.getText(), parolaMedicTextField.getText()),
                    cnpMedicTextField.getText(),
                    emailMedicTextField.getText(),
                    Integer.parseInt(spitalMedicComboBox.getValue().split(",")[0]));
            service.adaugaMedic(medic);
            modelMedici.setAll(FXCollections.observableArrayList(service.getMedici()));
        }
        catch (Exception ignored) {
            showErrorMessage("Operatiunea nu a putut fi finalizata!");
        }
    }

    public void stergereMedic(ActionEvent actionEvent) {
        try {
            int id = -1;
            for (Medic m : modelMedici) {
                if (m.getUsername().equals(utilizatorMedicTextField.getText())) {
                    id = m.getIdMedic();
                    break;
                }
            }
            if (id == -1)
                throw new Exception();
            service.stergeMedic(id);
            modelMedici.setAll(FXCollections.observableArrayList(service.getMedici()));
        }
        catch (Exception ignored) {
            showErrorMessage("Operatiunea nu a putut fi finalizata!");
        }
    }

    public void adaugarePersonal(ActionEvent actionEvent) {
        try {
            int max = 0;
            for (PersonalTransfuzii personalTransfuzii : modelPersonal) {
                if (personalTransfuzii.getIdPersonalTransfuzii() > max)
                    max = personalTransfuzii.getIdPersonalTransfuzii();
            }
            max++;
            PersonalTransfuzii personalTransfuzii = new PersonalTransfuzii(max, numePersonalTextField.getText(),
                    prenumePersonalTextField.getText(),
                    new Cont(utilizatorPersonalTextField.getText(), parolaPersonalTextField.getText()),
                    cnpPersonalTextField.getText(),
                    emailPersonalTextField.getText(),
                    Integer.parseInt(centruPersonalCombobox.getValue().split(",")[0]));
            service.adaugaPersonalTransfuzii(personalTransfuzii);
            modelPersonal.setAll(FXCollections.observableArrayList(service.getPersonalTransfuzii()));
        }
        catch (Exception ignored) {
            showErrorMessage("Operatiunea nu a putut fi finalizata!");
        }
    }

    public void stergerePersonal(ActionEvent actionEvent) {
        try {
            int id = -1;
            for (PersonalTransfuzii p : modelPersonal) {
                if (p.getUsername().equals(utilizatorPersonalTextField.getText())) {
                    id = p.getIdPersonalTransfuzii();
                    break;
                }
            }
            if (id == -1)
                throw new Exception();
            service.stergePersonalTransfuzii(id);
            modelPersonal.setAll(FXCollections.observableArrayList(service.getPersonalTransfuzii()));
        }
        catch (Exception ignored) {
            showErrorMessage("Operatiunea nu a putut fi finalizata!");
        }
    }
}
