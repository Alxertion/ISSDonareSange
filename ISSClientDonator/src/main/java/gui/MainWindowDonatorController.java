package gui;

import JavaResources.Controller;
import JavaResources.Service.Service;
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
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import model.Analiza;
import model.Boala;
import model.Cont;
import model.Donator;
import services.IObserver;
import services.IServices;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Base64;
import java.util.List;

public class MainWindowDonatorController extends UnicastRemoteObject implements Controller, IObserver, Serializable {
    private Cont user;
    private StageManager stageManager;
    private IServices service;
    private Loader loader;
    private Donator donator;

    @FXML
    private Text textFaraAnalize;

    @FXML
    private ProgressIndicator indicatorDonare;

    @FXML
    private Accordion analizeAccordion;

    @FXML
    private Button vreaSaDonezButton;

    @FXML
    private Button backButton;

    public MainWindowDonatorController() throws RemoteException {
    }


    @Override
    public void initialize(StageManager stageManager, IServices service, Loader loader) {
        this.stageManager = stageManager;
        this.service = service;
        this.loader = loader;
    }

    @Override
    public void prepareWindow() {

        textFaraAnalize.setVisible(false);
        setImagesForButtons();
        setDonator();
        setAccordionAnalize();
        setProgressIndicator();


    }

    private void setProgressIndicator() {

        int days = service.daysBeforeAnotherDonation(donator.getIdDonator());

        if(days >= 180){
            indicatorDonare.setVisible(false);
            vreaSaDonezButton.setVisible(true);
        }
        else{
            double procent = 5*days/9;
            indicatorDonare.setProgress(procent/100);
            vreaSaDonezButton.setVisible(false);
            indicatorDonare.setVisible(true);
        }

    }

    private void setAccordionAnalize() {

        List<Analiza> listOfAllAnalizeOfDonator = service.cautaAnalizeleUnuiDonator(donator.getIdDonator());

        if(listOfAllAnalizeOfDonator.size()==0){
            textFaraAnalize.setVisible(true);
        }

        if(listOfAllAnalizeOfDonator.size() < 3){
            analizeAccordion.getPanes().remove(listOfAllAnalizeOfDonator.size(),3);
        }
        setTitluriSiContinutPanes(listOfAllAnalizeOfDonator);

    }

    private void setTitluriSiContinutPanes(List<Analiza> listOfAllAnalizeOfDonator) {

        List<TitledPane> panes = analizeAccordion.getPanes();

        for(int i=0; i<listOfAllAnalizeOfDonator.size(); i++){
            Analiza analiza = listOfAllAnalizeOfDonator.get(i);
            TitledPane actualPane =  panes.get(i);
            actualPane.setText(analiza.toString());
            actualPane.setContent(new Text(formAnalizaContent(analiza.getBoli())));

        }
    }

    private String formAnalizaContent(List<Boala> boli) {

        String contentAnaliza = "";

        if(boli.size()==0){
            contentAnaliza = contentAnaliza + "Sunteti apt pentru donare.";
        }
        else{
            contentAnaliza = contentAnaliza + "Ne pare rau dar nu sunteti apt pentru donare. Analizele au iesti positiv pentru: \n ";

            for (Boala b: boli){
                contentAnaliza = contentAnaliza + " - " + b.getNume() + "\n";
            }
        }

        return contentAnaliza;

    }

    private void setDonator() {
        donator = service.findDonatorByUsername(user.getUsername());
    }

    @Override
    public void setUser(Cont user) {
        this.user=user;
    }

    private void setImagesForButtons() {
        setImageToBack();
        setImageToVreauSaDonez();
    }

    private void setImageToVreauSaDonez() {
        vreaSaDonezButton.graphicProperty().bind(Bindings.when(vreaSaDonezButton.hoverProperty())
                .then(new ImageView(loader.vreau_sa_donez_touched()))
                .otherwise(new ImageView(loader.vreau_sa_donez_untouched())));

        vreaSaDonezButton.setShape(new Circle());
    }

    private void setImageToBack() {

//        backButton.graphicProperty().bind(Bindings.when(backButton.hoverProperty())
//                .then(new ImageView(loader.back_button_touched()))
//                .otherwise(new ImageView(loader.back_button_untouched())));
//
//        backButton.setShape(new Circle());
    }

    @FXML
    private void vreauSaDonezButtonPressed(ActionEvent event) {

        try {
            FXMLLoader loaderFXML = new FXMLLoader();
            loaderFXML.setLocation(getClass().getResource(FXMLEnum.ConditiiDonare.getFxmlFile()));
            Parent rootNode = loaderFXML.load();
            Controller controller = loaderFXML.getController();
            controller.setUser(user);;
            stageManager.switchScene(FXMLEnum.ConditiiDonare, rootNode, loaderFXML.getController(), loader);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void notifyClient() throws RemoteException {
        System.out.println("Am fost notificat -> Donator");
    }

    @FXML
    public void logOut(ActionEvent actionEvent) {
        try {
            FXMLLoader loaderFXML = new FXMLLoader();
            loaderFXML.setLocation(getClass().getResource(FXMLEnum.LoginWindowDonator.getFxmlFile()));
            Parent rootNode = loaderFXML.load();
            service.logout(this.user);
            stageManager.switchScene(FXMLEnum.LoginWindowDonator, rootNode, loaderFXML.getController(), loader);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
