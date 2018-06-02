package JavaResources;

import JavaResources.View.FXMLEnum;
import JavaResources.View.Loader;
import JavaResources.View.StageManager;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import model.CentruTransfuzii;
import model.Cont;
import model.Donator;
import model.Pacient;
import services.FrontException;
import services.IServices;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


public class FormularDonator extends UnicastRemoteObject implements Controller,Serializable {

    @FXML
    private TextField numeTextField;

    @FXML
    private TextField prenumeTextField;

    @FXML
    private DatePicker dataNasteriiDatePicker;

    @FXML
    private TextField cnpTextField;

    @FXML
    private TextField domiciliuCITextField;

    @FXML
    private ComboBox localitateCIComboBox;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField telefonTextField;

    @FXML
    private TextField cnpPacientTextField;

    @FXML
    private Button backButton;

    @FXML
    private Button forwardButton;

    @FXML
    private GridPane panou;

    private Donator donator;

    private Cont user;

    private StageManager stageManager;
    private IServices service;
    private Loader loader;

    public FormularDonator() throws RemoteException {
    }


    @Override
    public void initialize(StageManager stageManager, IServices service, Loader loader) {
        this.stageManager = stageManager;
        this.service = service;
        this.loader = loader;

        setImagesForButtons();
        setDonator();
        setContentLocalitateComboBox();
    }

    private void setContentLocalitateComboBox() {

        localitateCIComboBox.getItems().addAll(
                "Jucu",
                "Cluj-Napoca",
                "Feleac"
        );

        localitateCIComboBox.setValue("Cluj-Napoca");
    }

    private void completeazaCuDateleAnterioare() {

        LocalDate localDate = LocalDate.now();

        if(donator.getDataNasterii()!=null){
            java.sql.Date dateConvert =  (java.sql.Date)donator.getDataNasterii();
            localDate = dateConvert.toLocalDate();
        }

        numeTextField.setText(donator.getNume());
        prenumeTextField.setText(donator.getPrenume());
        dataNasteriiDatePicker.setValue(localDate);
        cnpTextField.setText(donator.getCNP());
        emailTextField.setText(donator.getEmail());
        telefonTextField.setText(donator.getTelefon());

    }

    private void setDonator() {
        donator = service.findDonatorByUsername(user.getUsername());
    }

    @Override
    public void prepareWindow() {

        dataNasteriiDatePicker.setFocusTraversable(false);
        completeazaCuDateleAnterioare();
    }

    @Override
    public void setUser(Cont user) {
        this.user = user;
    }

    private void setImagesForButtons() {
        setImageToBack();
        setImageToForward();
    }

    private void setImageToForward() {
        forwardButton.graphicProperty().bind(Bindings.when(forwardButton.hoverProperty())
                .then(new ImageView(loader.forward_button_touched()))
                .otherwise(new ImageView(loader.forward_button_untouched())));

        forwardButton.setShape(new Circle());
    }

    private void setImageToBack() {

        backButton.graphicProperty().bind(Bindings.when(backButton.hoverProperty())
                .then(new ImageView(loader.back_button_touched()))
                .otherwise(new ImageView(loader.back_button_untouched())));

        backButton.setShape(new Circle());
    }

    private void validareCampuriInainteDeDonare(String CNP, String CNPIntrodus, String email, String emailIntrodus) throws FrontException{

        if(!CNPIntrodus.equals(CNP) || CNPIntrodus.length()==0){
            throw new FrontException("CNP-ul este diferit fata de cel de la logare!");
        }

        if(!email.equals(emailIntrodus)){
            System.out.println("Email diferit fata de cel de la logare");
        }

    }

    @FXML
    private void forwardButtonPressed(ActionEvent event){

        try{
            Pacient pacient = null;

            String numeDonator = numeTextField.getText();
            String prenumeDonator = prenumeTextField.getText();
            String CNP = cnpTextField.getText();
            String email = emailTextField.getText();
            String telefon = telefonTextField.getText();
            String cnpPacient = cnpPacientTextField.getText();
            LocalDate dataNasterii = dataNasteriiDatePicker.getValue();
            Date dateDataNasterii = Date.from(dataNasterii.atStartOfDay(ZoneId.systemDefault()).toInstant());

            validareCampuriInainteDeDonare(donator.getCNP(), CNP, donator.getEmail(), email);

            if(cnpPacient.length() >0){
                pacient = service.cautaPacientDupaCNP(cnpPacient);
            }

            updateDonator(donator, numeDonator, prenumeDonator, telefon, dateDataNasterii);
            //va trebui sa ne gandim cum cautam cel mai apropiat centru de transfuzii
            CentruTransfuzii centruTransfuzii =  service.cautaCelMaiApropiatCentruDeTransfuzii();

            service.inregistreazaDonator(centruTransfuzii,donator, pacient);
            successfulMessage();

        }catch (FrontException e){
            Alert message = new Alert(Alert.AlertType.ERROR);
            message.setTitle("Mesaj eroare");
            message.setContentText(e.getMessage());
            message.showAndWait();
        }


    }

    private void successfulMessage() {

        Alert message = new Alert(Alert.AlertType.CONFIRMATION);
        message.setTitle("Inregistrare Donare");
        message.setContentText(
                "Inregistrare donare efectuata cu succes"
        );
        message.showAndWait();
        changeToMainWindow();

    }

    private void changeToMainWindow() {

        try {
            FXMLLoader loaderFXML = new FXMLLoader();
            loaderFXML.setLocation(getClass().getResource(FXMLEnum.MainWindowDonator.getFxmlFile()));
            Parent rootNode = loaderFXML.load();
            Controller controller = loaderFXML.getController();
            controller.setUser(user);
            stageManager.switchScene(FXMLEnum.MainWindowDonator, rootNode, loaderFXML.getController(), loader);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void updateDonator(Donator donator, String numeDonator, String prenumeDonator, String telefon, java.util.Date dataNasterii) {

        donator.setNume(numeDonator);
        donator.setPrenume(prenumeDonator);
        donator.setTelefon(telefon);
        donator.setDataNasterii(dataNasterii);
    }

    @FXML
    private void backButtonPressed(ActionEvent event) {
        try {
            FXMLLoader loaderFXML = new FXMLLoader();
            loaderFXML.setLocation(getClass().getResource(FXMLEnum.ConditiiDonare.getFxmlFile()));
            Parent rootNode = loaderFXML.load();
            Controller controller = loaderFXML.getController();
            controller.setUser(user);
            stageManager.switchScene(FXMLEnum.ConditiiDonare, rootNode, loaderFXML.getController(), loader);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
