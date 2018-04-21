package JavaResources;

import JavaResources.Service.Service;
import JavaResources.View.Loader;
import JavaResources.View.StageManager;

public interface Controller {

    void initialize(StageManager stageManager, Service service, Loader loader);
}
