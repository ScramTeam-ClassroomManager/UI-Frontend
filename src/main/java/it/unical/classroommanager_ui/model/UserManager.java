package it.unical.classroommanager_ui.model;

public class UserManager {
    private static UserManager instance;

    private String token;
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

    public void setToken(String token){

        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void logout(){

        user = null;

    }

    public User getUser(){

        return user;

    }

    @Override
    public String toString() {
        return "UserManager{" +
                "token='" + token + '\'' +
                ", user=" + user +
                '}';
    }
}
