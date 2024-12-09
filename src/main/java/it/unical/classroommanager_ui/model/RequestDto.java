package it.unical.classroommanager_ui.model;


import javafx.beans.property.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class RequestDto {

    private LongProperty id;
    private StringProperty reason;
    private LongProperty classroomId;
    private IntegerProperty userSerialNumber;
    private ObjectProperty<LocalDate> creationDate;
    private StringProperty adminResponse;
    private StringProperty status;
    private ObjectProperty<LocalDate> requestDate;
    private ObjectProperty<LocalTime> startHour;
    private ObjectProperty<LocalTime> endHour;

    public RequestDto() {
        this.id = new SimpleLongProperty();
        this.reason = new SimpleStringProperty();
        this.classroomId = new SimpleLongProperty();
        this.userSerialNumber = new SimpleIntegerProperty();
        this.creationDate = new SimpleObjectProperty<>();
        this.adminResponse = new SimpleStringProperty();
        this.status = new SimpleStringProperty();
        this.requestDate = new SimpleObjectProperty<>();
        this.startHour = new SimpleObjectProperty<>();
        this.endHour = new SimpleObjectProperty<>();
    }

    public long getId() {
        return id.get();
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public LongProperty idProperty() {
        return id;
    }

    public String getReason() {
        return reason.get();
    }

    public void setReason(String reason) {
        this.reason.set(reason);
    }

    public StringProperty reasonProperty() {
        return reason;
    }

    public long getClassroomId() {
        return classroomId.get();
    }

    public void setClassroomId(long classroomId) {
        this.classroomId.set(classroomId);
    }

    public LongProperty classroomIdProperty() {
        return classroomId;
    }

    public String getUserSerialNumber() {
        return String.valueOf(userSerialNumber.get());
    }

    public void setUserSerialNumber(int userSerialNumber) {
        this.userSerialNumber.set(userSerialNumber);
    }

    public IntegerProperty userSerialNumberProperty() {
        return userSerialNumber;
    }

    public LocalDate getCreationDate() {
        return creationDate.get();
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate.set(creationDate);
    }

    public ObjectProperty<LocalDate> creationDateProperty() {
        return creationDate;
    }

    public String getAdminResponse() {
        return adminResponse.get();
    }

    public void setAdminResponse(String adminResponse) {
        this.adminResponse.set(adminResponse);
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public StringProperty statusProperty() {
        return status;
    }

    public LocalDate getRequestDate() {
        return requestDate.get();
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate.set(requestDate);
    }

    public ObjectProperty<LocalDate> requestDateProperty() {
        return requestDate;
    }

    public LocalTime getStartHour() {
        return startHour.get();
    }

    public void setStartHour(LocalTime startHour) {
        this.startHour.set(startHour);
    }

    public ObjectProperty<LocalTime> startHourProperty() {
        return startHour;
    }

    public LocalTime getEndHour() {
        return endHour.get();
    }

    public void setEndHour(LocalTime endHour) {
        this.endHour.set(endHour);
    }

    public ObjectProperty<LocalTime> endHourProperty() {
        return endHour;
    }
}

