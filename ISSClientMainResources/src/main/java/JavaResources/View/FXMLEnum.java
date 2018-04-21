package JavaResources.View;

public enum FXMLEnum {
    LoginWindow{
      @Override
      public String getFxmlFile(){
          return "/FXMLCont/Login.fxml";
      }

      @Override
        public String getTitle(){
          return "Login";
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
    FormularDonator2{
        @Override
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
