package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a student in the system.
 * SECURITY FLAW: No proper access control, any student can access any grade
 */
public class Student extends User {
    private String classGroup;
    private List<Grade> grades;

    // Default constructor
    public Student() {
        super();
        this.grades = new ArrayList<>();
    }

    // Parameterized constructor
    public Student(int id, String username, String password, String fullName, String email, String classGroup) {
        super(id, username, password, "student", fullName, email);
        this.classGroup = classGroup;
        this.grades = new ArrayList<>();
    }

    // Getters and setters
    public String getClassGroup() {
        return classGroup;
    }

    public void setClassGroup(String classGroup) {
        this.classGroup = classGroup;
    }

    public List<Grade> getGrades() {
        return grades; // SECURITY FLAW: Direct access to all grades without filtering
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    // Add a grade to the student
    public void addGrade(Grade grade) {
        this.grades.add(grade);
    }

    // Calculate average grade for a specific subject
    public double calculateAverageForSubject(String subjectName) {
        List<Grade> subjectGrades = new ArrayList<>();
        for (Grade grade : grades) {
            if (grade.getSubject().getName().equals(subjectName)) {
                subjectGrades.add(grade);
            }
        }

        if (subjectGrades.isEmpty()) {
            return 0.0;
        }

        double totalWeightedGrade = 0.0;
        double totalCoefficient = 0.0;

        for (Grade grade : subjectGrades) {
            totalWeightedGrade += grade.getValue() * grade.getCoefficient();
            totalCoefficient += grade.getCoefficient();
        }

        return totalCoefficient > 0 ? totalWeightedGrade / totalCoefficient : 0.0;
    }

    // Calculate overall average grade
    public double calculateOverallAverage() {
        if (grades.isEmpty()) {
            return 0.0;
        }

        double totalWeightedGrade = 0.0;
        double totalCoefficient = 0.0;

        for (Grade grade : grades) {
            totalWeightedGrade += grade.getValue() * grade.getCoefficient();
            totalCoefficient += grade.getCoefficient();
        }

        return totalCoefficient > 0 ? totalWeightedGrade / totalCoefficient : 0.0;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + getId() +
                ", username='" + getUsername() + '\'' +
                ", fullName='" + getFullName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", classGroup='" + classGroup + '\'' +
                '}';
    }
}
