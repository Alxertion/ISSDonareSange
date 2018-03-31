package Controllers;

import Service.Service;
import View.Loader;
import View.StageManager;

public interface Controller {

    void initialize(StageManager stageManager, Service service, Loader loader);
}
