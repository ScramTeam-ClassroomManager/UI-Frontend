package it.unical.classroommanager_ui.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private int serialNumber;
    private String role;

    public UserDto(){}

    public Long getId(){return id;}

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstNameName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastNamename(String lastName) {
        this.lastName = lastName;
    }


    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
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
