package model;

import java.io.Serializable;

/**
 * 
 */
public class Cont implements Serializable {

    /**
     * Default constructor
     */
    public Cont() {
    }

    private String username;

    /**
     * 
     */
    private String password;

    /**
     * @param username
     */
    public Cont(String username,String password) {
        this.username=username;
        this.password=password;
    }

    /**
     * @return
     */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAllFields(Cont cont){
        this.username = cont.getUsername();
        this.password = cont.getPassword();
    }

}