package it.unical.classroommanager_ui.model;

public record User(String serialNumber, String firstName, String lastName, String email, String password, String role) {

    @Override
    public String serialNumber() {
        return serialNumber;
    }

    @Override
    public String firstName() {
        return firstName;
    }

    @Override
    public String lastName() {
        return lastName;
    }

    @Override
    public String email() {
        return email;
    }

    @Override
    public String password() {
        return password;
    }

    @Override
    public String role() { return role;}

    @Override
    public String toString() {
        return "User{" +
                "serialNumber='" + serialNumber + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
