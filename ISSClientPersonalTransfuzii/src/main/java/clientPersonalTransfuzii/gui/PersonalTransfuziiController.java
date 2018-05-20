package clientPersonalTransfuzii.gui;

import JavaResources.Controller;
import JavaResources.Service.Service;
import JavaResources.View.FXMLEnum;
import JavaResources.View.Loader;
import JavaResources.View.StageManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import services.FrontException;
import services.IObserver;
import services.IServices;
import services.ServiceException;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import java.io.IOException;
import java.io.Serializable;
import java.net.PasswordAuthentication;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PersonalTransfuziiController extends UnicastRemoteObject implements  Controller, IObserver,Serializable {
    private StageManager stageManager;
    private IServices service;
    private Loader loader;
    private Cont user;
    private ObservableList<Donator> observableListDonatori;
    private String[] rh={"+","-"};
    private String[] tipStrings={"TROMBOCITE","GLOBULE_ROSII","PLASMA","SANGE_NEFILTRAT"};
    private String[] stagiuStrings={"PRELEVARE","FILTRARE","ANALIZARE","ELIMINARE","DISTRIBUIRE","SOSIRE","ANULARE"};
    private String[] grupe={"01","A2","B3","AB4"};
    private String[] toatebolile={"HEPATITA","DIABET_ZAHARAT","TBC","SIFILIS","MALARIE","EPILEPSIE","BOLI_PSIHICE","VITILIGO","BRUCELOZA","BOLI_DE_INIMA","ULCER","MIOPIE_FORTE","PSORIAZIS","CANCER"};
    private List<Boala> boli;
    List<CheckMenuItem> allCheckMenuItems;
    @FXML
    TabPane tabPanePersonal;
    @FXML
    ListView<Donator> listaDonatori;
    @FXML
    TextArea analiza;
    @FXML
    ComboBox comboGrupa,comboRh;
    @FXML
    MenuButton boliMenu;

    //for Management pungi sange
    @FXML TableView<PreparatSanguinDTO> ManagementPungiTableView;
    @FXML TableColumn<PreparatSanguinDTO, String> IDPungaColumn, TipPungaColumn, DataPrePungaColumn, DataExpPungaColumn, IDDonatorPungaColumn, IDPacientPungaColumn, IDAnalizaPungaColumn, CantitatePungaColumn, StagiuPungaColumn ;
    @FXML ComboBox<String> TipPungaComboBox, StagiuPungaComboBox;
    @FXML TextField IDPungaField, DataExpPungaField, DataPrePungaField, IDPacientPungaField, IDAnalizaPungaField, IDDonatorPungaField, CantitatePungaField;
    private ObservableList<PreparatSanguinDTO> modelPreparatSanguinDTO;


    public PersonalTransfuziiController() throws RemoteException {
    }

    @Override
    public void initialize(StageManager stageManager,IServices service, Loader loader) {
        this.stageManager=stageManager;
        this.loader=loader;
        this.service=service;
        IDPungaColumn.setCellValueFactory(new PropertyValueFactory<PreparatSanguinDTO,String>("iD"));
        TipPungaColumn.setCellValueFactory(new PropertyValueFactory<PreparatSanguinDTO,String>("tip"));
        DataExpPungaColumn.setCellValueFactory(new PropertyValueFactory<PreparatSanguinDTO,String>("dataExpirare"));
        DataPrePungaColumn.setCellValueFactory(new PropertyValueFactory<PreparatSanguinDTO,String>("dataPrelevare"));
        IDDonatorPungaColumn.setCellValueFactory(new PropertyValueFactory<PreparatSanguinDTO,String>("iDDonator"));
        IDPacientPungaColumn.setCellValueFactory(new PropertyValueFactory<PreparatSanguinDTO,String>("iDPacient"));
        IDAnalizaPungaColumn.setCellValueFactory(new PropertyValueFactory<PreparatSanguinDTO,String>("iDAnaliza"));
        CantitatePungaColumn.setCellValueFactory(new PropertyValueFactory<PreparatSanguinDTO,String>("cantitate"));
        StagiuPungaColumn.setCellValueFactory(new PropertyValueFactory<PreparatSanguinDTO,String>("stagiu"));
        ManagementPungiTableView.getSelectionModel().selectedItemProperty().
                addListener(new ChangeListener<PreparatSanguinDTO>() {
                    @Override
                    public void changed(ObservableValue<? extends PreparatSanguinDTO> observable,
                                        PreparatSanguinDTO oldValue, PreparatSanguinDTO newValue) {
                        showPreparatSanguinDTODetails(newValue);
                    }
                });
        modelPreparatSanguinDTO= FXCollections.observableArrayList(this.service.getAllPreparatSanguinDTO());
        ManagementPungiTableView.setItems(modelPreparatSanguinDTO);

        EnumSet<TipPreparatSanguin> allTip= EnumSet.allOf( TipPreparatSanguin.class );
        List<TipPreparatSanguin> list = new ArrayList<>( allTip );
        TipPungaComboBox.setItems(FXCollections.observableArrayList( tipStrings ));
        StagiuPungaComboBox.setItems(FXCollections.observableArrayList(  stagiuStrings ));
    }

    private void showPreparatSanguinDTODetails(PreparatSanguinDTO newValue) {
        if(newValue!=null){
            IDPungaField.setText(""+newValue.getID());
            IDAnalizaPungaField.setText(""+newValue.getIDAnaliza());
            IDDonatorPungaField.setText(""+newValue.getIDDonator());
            IDPacientPungaField.setText(""+newValue.getIDPacient());
            DataExpPungaField.setText(""+newValue.getDataExpirare());
            DataPrePungaField.setText(""+newValue.getDataPrelevare());
            CantitatePungaField.setText(""+newValue.getCantitate());
            StagiuPungaComboBox.getSelectionModel().select(""+newValue.getStagiu());
            TipPungaComboBox.getSelectionModel().select(""+newValue.getTip());
        }
    }


    @FXML
    public void adaugaPungaAction(ActionEvent actionEvent){
        Date dataPrelev, dataExp;
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        df.setLenient(false);

        if(!isDateValid(DataExpPungaField.getText()) || !isDateValid(DataPrePungaField.getText())) {
            showError("Eroare", "Data introdusa incorect. (format yyyy-MM-dd)");
        }else{
            try {
                dataPrelev=df.parse(DataPrePungaField.getText());
                dataExp=df.parse(DataExpPungaField.getText());
                PreparatSanguinDTO newPrepDTO=new PreparatSanguinDTO(
                        Integer.parseInt(IDPungaField.getText()),
                        TipPungaComboBox.getValue().toString(),
                        dataPrelev,
                        dataExp,
                        Integer.parseInt(IDDonatorPungaField.getText()),
                        Integer.parseInt(IDAnalizaPungaField.getText()),
                        Double.parseDouble(CantitatePungaField.getText()),
                        Integer.parseInt(IDPacientPungaField.getText()),
                        StagiuPungaComboBox.getValue()
                );
                service.adaugarePreparatSanguinDTO(newPrepDTO);
            } catch (ParseException e) {
                showError("Eroare","Data introdusa incorect. (format yyyy-MM-dd)");
            }catch (NumberFormatException err){
                showError("Eroare", "ID-urile trebuie sa fie numere intregi. Cantitatea trebuie sa fie un numar real.");
            }
        }
    }

    @FXML
    public void stergePungaAction(ActionEvent actionEvent){
        if(IDPungaField.getText()!="")
            try {
                int id=Integer.parseInt(IDPungaField.getText());
                service.stergePreparatSanguinDTO(id);
            }catch (NumberFormatException err){
                Alert message = new Alert(Alert.AlertType.ERROR);
                message.setTitle("Mesaj eroare");
                message.setContentText("ID-ul trebuie sa fie numar intreg");
                message.showAndWait();
            }
    }

    @FXML
    public void modificaPungaAction(ActionEvent actionEvent){
        Date dataPrelev, dataExp;
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        df.setLenient(false);

        if(!isDateValid(DataExpPungaField.getText()) || !isDateValid(DataPrePungaField.getText())) {
            showError("Eroare", "Data introdusa incorect. (format yyyy-MM-dd)");
        }else{
            try {
                dataPrelev=df.parse(DataPrePungaField.getText());
                dataExp=df.parse(DataExpPungaField.getText());
                PreparatSanguinDTO newPrepDTO=new PreparatSanguinDTO(
                        Integer.parseInt(IDPungaField.getText()),
                        TipPungaComboBox.getValue().toString(),
                        dataPrelev,
                        dataExp,
                        Integer.parseInt(IDDonatorPungaField.getText()),
                        Integer.parseInt(IDAnalizaPungaField.getText()),
                        Double.parseDouble(CantitatePungaField.getText()),
                        Integer.parseInt(IDPacientPungaField.getText()),
                        StagiuPungaComboBox.getValue()
                );
                service.modificaPreparatSanguinDTO(newPrepDTO);
            } catch (ParseException e) {
                showError("Eroare","Data introdusa incorect. (format yyyy-MM-dd)");
            }catch (NumberFormatException err){
                showError("Eroare", "ID-urile trebuie sa fie numere intregi. Cantitatea trebuie sa fie un numar real.");
            }
        }
    }

    final static String DATE_FORMAT = "yyyy-MM-dd";

    public boolean isDateValid(String date)
    {
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }


    private void showError(String title, String context) {
        Alert message = new Alert(Alert.AlertType.ERROR);
        message.setTitle(title);
        message.setContentText(context);
        message.showAndWait();
    }

    @FXML
    public void notifyButtonPressed(ActionEvent actionEvent){
        try {
            FXMLLoader loaderFXML = new FXMLLoader();
            loaderFXML.setLocation(getClass().getResource(FXMLEnum.TrimiteMesaj.getFxmlFile()));
            AnchorPane mailView = loaderFXML.load();
            Scene scene=new Scene(mailView);
            Stage stage=new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    @FXML
    public void checkConditions(ActionEvent actionEvent){
        try {
            FXMLLoader loaderFXML = new FXMLLoader();
            loaderFXML.setLocation(getClass().getResource(FXMLEnum.FormularDonator2.getFxmlFile()));
            Parent rootNode = loaderFXML.load();
            tabPanePersonal.getSelectionModel().getSelectedItem().setContent(rootNode);

        }catch (IOException e){
            e.printStackTrace();
        }


    }



    @Override
    public void setUser(Cont user) {
        this.user=user;
    }

    @Override
    public void notifyClient() throws RemoteException {
        //System.out.println("Am fost notificat -> Personal");
        modelPreparatSanguinDTO= FXCollections.observableArrayList(this.service.getAllPreparatSanguinDTO());
        ManagementPungiTableView.setItems(modelPreparatSanguinDTO);
    }

    @FXML
    public void logOut(ActionEvent actionEvent) throws RemoteException {
        try {
            FXMLLoader loaderFXML = new FXMLLoader();
            loaderFXML.setLocation(getClass().getResource(FXMLEnum.LoginWindowPersonalTransfuzii.getFxmlFile()));
            Parent rootNode = loaderFXML.load();
            service.logout(this.user);
            stageManager.switchScene(FXMLEnum.LoginWindowPersonalTransfuzii, rootNode, loaderFXML.getController(), loader);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    private void listOnClick(){
        listaDonatori.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    Analiza analizaDonator = service.cautaUltimaAnalizaDupaDonator(listaDonatori.getSelectionModel().getSelectedItem().getIdDonator());
                    if (analizaDonator == null)
                        throw new FrontException("Analizele pentru acest donator nu sunt finalizate.");
                    String verdictFinal=analizaDonator.toString();
                    List<Boala> boli=analizaDonator.getBoli();
                    if(boli.size()==0){
                        verdictFinal=verdictFinal+"Rezultatul analizelor: POZITIV -> APT PENTRU DONARE";
                    }else{
                        verdictFinal=verdictFinal+"Rezultatul analizelor: NEGATIV. Motive:";
                        for(Boala boala:boli){
                            verdictFinal=verdictFinal + boala.getNume()+" ";
                        }
                    }
                    analiza.setPromptText(verdictFinal);
                }catch (FrontException fe){
                    analiza.setPromptText(fe.getMessage());
                }
            }
        });
    }

    @FXML
    public void sendAnaliza(ActionEvent actionEvent){
        try{
            if(listaDonatori.getSelectionModel().getSelectedItem()==null)
                throw new FrontException("Nu ati selectat un donator caruia sa-i transmiteti rezultatele analizelor.");
            String email=listaDonatori.getSelectionModel().getSelectedItem().getEmail();
            if(email==null)
                throw new FrontException("Acest donator nu a comunicat nicio adresa de email");
            service.sendEmail(email,analiza.getPromptText());
            analiza.setPromptText("Analize...");
            Alert message = new Alert(Alert.AlertType.INFORMATION);
            message.setTitle("Mesaj de informare");
            message.setContentText("Emailul a fost transmis.");
            message.showAndWait();
        }catch (FrontException fr){
            Alert message = new Alert(Alert.AlertType.ERROR);
            message.setTitle("Mesaj eroare");
            message.setContentText(fr.getMessage());
            message.showAndWait();
        }

    }

    @FXML
    public void handleAdaugaAnaliza(ActionEvent event){
        try {
            String errors = "";
            if (comboGrupa.getSelectionModel().getSelectedItem()==null)
                errors += "Nu ati selectat grupa.";
            if (comboRh.getSelectionModel().getSelectedItem()==null)
                errors += "Nu ati selectat RH-ul";
            if(listaDonatori.getSelectionModel().getSelectedItem()==null)
                errors+="Nu ati selectat carui donator sa i se atribuie analiza.";
            if (!errors.equals(""))
                throw new FrontException(errors);
            boolean booleanRh=comboRh.getSelectionModel().getSelectedItem().toString().equals("-") ? false:true;
            Analiza analiza=new Analiza(comboGrupa.getSelectionModel().getSelectedItem().toString(),booleanRh);
            analiza.setBoli(boli);
            service.adaugaAnalizaLaDonator(listaDonatori.getSelectionModel().getSelectedItem().getIdDonator(),analiza);
            boli=new ArrayList<>();
            comboGrupa.getSelectionModel().clearSelection();
            comboRh.getSelectionModel().clearSelection();
            allCheckMenuItems.stream().forEach(x->{x.setSelected(false);});

            Alert message = new Alert(Alert.AlertType.INFORMATION);
            message.setTitle("Mesaj informare");
            message.setContentText("Analiza a fost adaugata cu succes.");
            message.showAndWait();
        }catch (FrontException fe){
            Alert message = new Alert(Alert.AlertType.ERROR);
            message.setTitle("Mesaj eroare");
            message.setContentText(fe.getMessage());
            message.showAndWait();
        }catch (ServiceException se){
            Alert message = new Alert(Alert.AlertType.ERROR);
            message.setTitle("Mesaj eroare");
            message.setContentText(se.getMessage());
            message.showAndWait();
        }
    }

    public void prepareWindow(){
        allCheckMenuItems=new ArrayList<>();
        loadListDonatori();
        listOnClick();
        comboRh.setItems(FXCollections.observableArrayList(rh));
        comboGrupa.setItems(FXCollections.observableArrayList(grupe));
        for(int i=0;i<toatebolile.length;i++){

            allCheckMenuItems.add(new CheckMenuItem(toatebolile[i]));
            boliMenu.getItems().add(allCheckMenuItems.get(i));
        }
        boliMenu.getItems().stream().forEach(x->{
            x.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    boli.add(new Boala(x.getText()));
                    boliMenu.arm();
                }
            });
        });
    }

    public void loadListDonatori(){
        List<Donator> donatori=service.getDonatori();
        observableListDonatori= FXCollections.observableArrayList(donatori);
        listaDonatori.setItems(observableListDonatori);
        boli=new ArrayList<>();
    }
}
