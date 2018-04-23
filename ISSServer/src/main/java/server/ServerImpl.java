package server;

import model.Cont;
import persistence.repository.*;
import services.IObserver;
import services.IServices;
import services.ServiceException;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerImpl implements IServices {
    IRepositoryAnalize repositoryAnalize;
    IRepositoryCereri repositoryCereri;
    IRepositoryDonatori repositoryDonatori;
    IRepositoryGlobuleRosii repositoryGlobuleRosii;
    IRepositoryMedici repositoryMedici;
    IRepositoryPersonalTransfuzii repositoryPersonalTransfuzii;
    IRepositoryPlasma repositoryPlasma;
    IRepositorySangeNefiltrat repositorySangeNefiltrat;
    IRepositoryTrombocite repositoryTrombocite;

    //For remoting
    private Map<String, IObserver> loggedClients;

    public ServerImpl(IRepositoryAnalize repositoryAnalize, IRepositoryCereri repositoryCereri, IRepositoryDonatori repositoryDonatori, IRepositoryGlobuleRosii repositoryGlobuleRosii, IRepositoryMedici repositoryMedici,IRepositoryPersonalTransfuzii repositoryPersonalTransfuzii,IRepositoryPlasma repositoryPlasma,IRepositorySangeNefiltrat repositorySangeNefiltrat,IRepositoryTrombocite repositoryTrombocite){
        this.repositoryAnalize=repositoryAnalize;
        this.repositoryCereri=repositoryCereri;
        this.repositoryDonatori=repositoryDonatori;
        this.repositoryGlobuleRosii=repositoryGlobuleRosii;
        this.repositoryMedici=repositoryMedici;
        this.repositoryPersonalTransfuzii=repositoryPersonalTransfuzii;
        this.repositoryPlasma=repositoryPlasma;
        this.repositorySangeNefiltrat=repositorySangeNefiltrat;
        this.repositoryTrombocite=repositoryTrombocite;
        loggedClients=new ConcurrentHashMap<>();
    }

    @Override
    public synchronized void login(Cont user,IObserver client) throws ServiceException {
        boolean loginOk=true;//userRepository.verifyUser(user);
        if (loginOk){
            if(loggedClients.get(user.getUsername())!=null)
                throw new ServiceException("Acest user este deja logat.");
            loggedClients.put(user.getUsername(), client);
        }else
            throw new ServiceException("Autentificare esuata.");
    }

}
