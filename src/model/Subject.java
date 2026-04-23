package model;

/**
 * Represents a subject or course in the system.
 */
public class Subject {
    private int id;
    private String code;
    private String name;
    private String description;
    private double defaultCoefficient;

    // Default constructor
    public Subject() {
    }

    // Parameterized constructor
    public Subject(int id, String code, String name, String description, double defaultCoefficient) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.defaultCoefficient = defaultCoefficient;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDefaultCoefficient() {
        return defaultCoefficient;
    }

    public void setDefaultCoefficient(double defaultCoefficient) {
        this.defaultCoefficient = defaultCoefficient;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", defaultCoefficient=" + defaultCoefficient +
                '}';
    }
}