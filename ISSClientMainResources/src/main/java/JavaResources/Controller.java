package JavaResources;

import JavaResources.Service.Service;
import JavaResources.View.Loader;
import JavaResources.View.StageManager;
import model.Cont;
import services.IObserver;
import services.IServices;
import services.ServiceException;

public interface Controller{
    void initialize(StageManager stageManager,IServices services, Loader loader);
    void prepareWindow();
    void setUser(Cont user);
}
