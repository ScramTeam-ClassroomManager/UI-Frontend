package it.unical.classroommanager_ui.model;

public class UserManager {
    private static UserManager instance;
    private User user;

    private UserManager(){}

    public static UserManager getInstance(){
        if (instance == null){
            instance = new UserManager();
        }
        return instance;
    }

    public void setUser(User user){

        this.user = user;

    }

    public void logout(){

        user = null;

    }

    public User getUser(){

        return user;

    }
}
