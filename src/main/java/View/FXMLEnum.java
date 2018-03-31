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
    };

    public abstract String getFxmlFile();

    public abstract String getTitle();

}
