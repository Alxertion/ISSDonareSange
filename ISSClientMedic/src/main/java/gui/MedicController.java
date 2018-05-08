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
import javafx.scene.control.*;
import model.Cerere;
import model.Cont;
import model.Medic;
import model.Pacient;
import services.IObserver;
import services.IServices;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class MedicController extends UnicastRemoteObject implements Controller, IObserver,Serializable {
    private StageManager stageManager;
    private IServices service;
    private Loader loader;
    private Cont user;
    private ObservableList<Cerere> modelCereri;
    private ObservableList<Pacient> modelPacienti;
    @FXML
    private TextField medicTextField;
    @FXML
    private ComboBox<String> prioritateComboBox;
    @FXML
    private ComboBox<String> grupaComboBox;
    @FXML
    private TextField cantitateTextField;
    @FXML
    private ComboBox<String> rhComboBox;
    @FXML
    private DatePicker dataTextField;
    @FXML
    private TextField cnpPacientTextField;
    @FXML
    private TextField numePacientTextField;
    @FXML
    private PasswordField parolaCurentaTextField;
    @FXML
    private PasswordField parolaCurenta2TextField;
    @FXML
    private PasswordField parolaNouaTextField;
    @FXML
    private PasswordField parolaNoua2TextField;
    @FXML
    private TableView<Cerere> cereriTableView;
    @FXML
    private TableView<Pacient> pacientiTableView;

    public MedicController() throws RemoteException {
    }

    @Override
    public void initialize(StageManager stageManager, IServices service, Loader loader) {
        this.stageManager=stageManager;
        this.loader=loader;
        this.service=service;

        modelCereri = FXCollections.observableArrayList(service.getCereri());
        cereriTableView.setItems(modelCereri);

        modelPacienti = FXCollections.observableArrayList(service.getPacienti());
        pacientiTableView.setItems(modelPacienti);

        rhComboBox.getItems().clear();
        rhComboBox.getItems().add("Pozitiv");
        rhComboBox.getItems().add("Negativ");
        rhComboBox.getSelectionModel().selectFirst();

        grupaComboBox.getItems().clear();
        grupaComboBox.getItems().add("O (I)");
        grupaComboBox.getItems().add("A (II)");
        grupaComboBox.getItems().add("B (III)");
        grupaComboBox.getItems().add("AB (IV)");
        grupaComboBox.getSelectionModel().selectFirst();

        prioritateComboBox.getItems().clear();
        prioritateComboBox.getItems().add("Mica");
        prioritateComboBox.getItems().add("Medie");
        prioritateComboBox.getItems().add("Mare");
        prioritateComboBox.getSelectionModel().selectFirst();

        LocalDate localDate = LocalDate.now();
        dataTextField.setValue(localDate);

        pacientiTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Platform.runLater(() -> {
                    Pacient pacient = pacientiTableView.getSelectionModel().getSelectedItem();
                    cnpPacientTextField.setText(pacient.getCnp());
                    numePacientTextField.setText(pacient.getNume());
                });
            }
        });
    }

    @Override
    public void prepareWindow() {

    }

    @Override
    public void setUser(Cont user) {
        this.user=user;

        String numeMedic = "";
        numeMedic = service.getNumeMedic(user);
        medicTextField.setText(numeMedic);
    }

    @Override
    public void notifyClient() throws RemoteException {
        System.out.println("Am fost notificat -> Medic");
    }

    @FXML
    public void logOut(ActionEvent actionEvent) {
        try {
            FXMLLoader loaderFXML = new FXMLLoader();
            loaderFXML.setLocation(getClass().getResource(FXMLEnum.LoginWindowMedic.getFxmlFile()));
            Parent rootNode = loaderFXML.load();
            service.logout(this.user);
            stageManager.switchScene(FXMLEnum.LoginWindowMedic, rootNode, loaderFXML.getController(), loader);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
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
    public void anulareCerere(ActionEvent actionEvent) {
        try {
            service.stergeCerere(cereriTableView.getSelectionModel().getSelectedItem());
        }
        catch (Exception e) {
            showErrorMessage("Operatia nu a putut fi finalizata!");
        }
    }

    public void golireCampuri(ActionEvent actionEvent) {
        cantitateTextField.setText("");
        cnpPacientTextField.setText("");
        numePacientTextField.setText("");
        prioritateComboBox.getSelectionModel().selectFirst();
        grupaComboBox.getSelectionModel().selectFirst();
        rhComboBox.getSelectionModel().selectFirst();
        dataTextField.getEditor().clear();
        dataTextField.setValue(null);
    }

    public void efectuareCerere(ActionEvent actionEvent) {

    }

    public void schimbaParola(ActionEvent actionEvent) {
        try {
            if (!Objects.equals(parolaCurentaTextField.getText(), parolaCurenta2TextField.getText()) ||
                !Objects.equals(parolaNouaTextField.getText(), parolaNoua2TextField.getText()))
                throw new Exception("Parolele nu se potrivesc!");
            service.schimbaParolaMedic(user.getUsername(),parolaCurentaTextField.getText(),parolaNouaTextField.getText());
            showMessage(Alert.AlertType.CONFIRMATION, "Schimbare parola", "Parola schimbata cu succes!");
        }
        catch (Exception e) {
            showErrorMessage(e.getMessage());
        }
    }
}
