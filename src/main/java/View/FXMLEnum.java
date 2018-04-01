package View;

public enum FXMLEnum {

    MainWindowDonator {
        @Override
        public String getFxmlFile() {
            return "/FXMLDonator/MainWindowDonator.fxml";
        }

        @Override
        public String getTitle() {
            return "MainWindowDonator";
        }

    },
    ConditiiDonare{
        @Override
        public String getFxmlFile() {
            return "/FXMLDonator/ConditiiDonare.fxml";
        }

        @Override
        public String getTitle() {
            return "ConditiiDonare";
        }
    },
    FormularDonator{
        @Override
<<<<<<< Updated upstream
        public String getFxmlFile(){return "/FXMLPersonalTransfuzii/FormularDonator.fxml";}

        @Override
        public String getTitle(){return "FormularDonator";}
    },
    MainViewPersonalTransfuzii{
        @Override
        public String getFxmlFile(){return "/FXMLPersonalTransfuzii/PersonalTransfuzii.fxml";}

        @Override
        public String getTitle(){return "PersonalTransfuzii";}
    },
    TrimiteMesaj{
        @Override
        public String getFxmlFile(){return "/FXMLPersonalTransfuzii/Mesaj.fxml";}

        @Override
        public String getTitle(){return "TrimitereMesaj";}
=======
        public String getFxmlFile() {
            return "/FXMLDonator/FormularDonator.fxml";
        }

        @Override
        public String getTitle() {
            return "FormularDonator";
        }
>>>>>>> Stashed changes
    };



    public abstract String getFxmlFile();

    public abstract String getTitle();

}
