package it.unical.classroommanager_ui.model;
public class ClassroomDto {
    private long id;
    private String name;
    private int cubeNumber;
    private int floor;
    private int capability;
    private int numSocket;
    private boolean projector;
    private boolean available;
    private String type;

    public ClassroomDto() {}
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCubeNumber() {
        return cubeNumber;
    }

    public void setCubeNumber(int cubeNumber) {
        this.cubeNumber = cubeNumber;
    }

    public int getFloor() {
        return floor;
    }

    public int getCapability() {
        return capability;
    }

    public int getNumSocket() {
        return numSocket;
    }

    public boolean isProjector() {
        return projector;
    }
    public boolean getAvailable() {
        return available;
    }
    public String getType() {
        return type;
    }
}
