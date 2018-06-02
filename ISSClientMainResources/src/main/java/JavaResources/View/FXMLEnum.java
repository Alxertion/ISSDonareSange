package JavaResources.View;

public enum FXMLEnum {
    LoginWindowAdministrator{
      @Override
      public String getFxmlFile(){
          return "/FXMLCont/Login.fxml";
      }

      @Override
        public String getTitle(){
          return "LoginAdministrator";
      }
    },
    LoginWindowDonator{
        @Override
        public String getFxmlFile(){
            return "/FXMLCont/Login.fxml";
        }

        @Override
        public String getTitle(){
            return "LoginDonator";
        }
    },
    LoginWindowMedic{
        @Override
        public String getFxmlFile(){
            return "/FXMLCont/Login.fxml";
        }

        @Override
        public String getTitle(){
            return "LoginMedic";
        }
    },
    LoginWindowPersonalTransfuzii{
        @Override
        public String getFxmlFile(){
            return "/FXMLCont/Login.fxml";
        }

        @Override
        public String getTitle(){
            return "LoginTransfuzii";
        }
    },
    MainWindowAdministrator{
      @Override
      public String getFxmlFile(){
          return "/FXMLAdministrator/administratorView.fxml";
      }
      @Override
        public String getTitle(){
          return "Administrator";
      }
    },
    MainWindowMedic{
      @Override
      public String getFxmlFile(){
          return "/FXMLMedic/medicView.fxml";
      }

      @Override
        public String getTitle(){
          return "Medic";
      }
    },
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
    MainViewPersonalTransfuzii{
        @Override
        public String getFxmlFile(){return "/FXMLPersonalTransfuzii/PersonalTransfuzii.fxml";}

        @Override
        public String getTitle(){return "PersonalTransfuzii";}
    },
    TrimiteMesaj {
        @Override
        public String getFxmlFile() {
            return "/FXMLPersonalTransfuzii/Mesaj.fxml";
        }

        @Override
        public String getTitle() {
            return "TrimitereMesaj";
        }
    },
    FormularDonatorFromPersonalTransfuzii{
        @Override
        public String getFxmlFile() {
            return "/FXMLPersonalTransfuzii/FormularDonator.fxml";
        }

        @Override
        public String getTitle() {
            return "FormularDonator";
        }
    },
    FormularDonator{
        @Override
        public String getFxmlFile() {
            return "/FXMLDonator/FormularDonator.fxml";
        }

        @Override
        public String getTitle() {
            return "FormularDonator";
        }
    };



    public abstract String getFxmlFile();

    public abstract String getTitle();

}
