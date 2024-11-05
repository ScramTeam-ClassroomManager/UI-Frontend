package it.unical.classroommanager_ui.model;

public record User(String serialNumber, String firstName, String lastName, String email, String password) {

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
}
