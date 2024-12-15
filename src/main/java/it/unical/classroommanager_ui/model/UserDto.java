package it.unical.classroommanager_ui.model;

public class UserDto {
    private String name;
    private String surename;
    private int serialNumber;
    private String email;
    private String role;

    public UserDto(){}

    public String getName(){
        return name;
    }

    public String getSurename(){
        return surename;
    }

    public String getEmail(){
        return email;
    }

    public int getSerialNumber(){
        return serialNumber;
    }

    public String getRole() {
        return role;
    }
}
