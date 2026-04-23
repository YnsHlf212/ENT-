package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a teacher in the system.
 * SECURITY FLAW: No proper access control, any teacher can modify any grade
 */
public class Teacher extends User {
    private List<Subject> taughtSubjects;

    // Default constructor
    public Teacher() {
        super();
        this.taughtSubjects = new ArrayList<>();
    }

    // Parameterized constructor
    public Teacher(int id, String username, String password, String fullName, String email) {
        super(id, username, password, "teacher", fullName, email);
        this.taughtSubjects = new ArrayList<>();
    }

    // Getters and setters
    public List<Subject> getTaughtSubjects() {
        return taughtSubjects;
    }

    public void setTaughtSubjects(List<Subject> taughtSubjects) {
        this.taughtSubjects = taughtSubjects;
    }

    // Add a subject to the teacher's taught subjects
    public void addSubject(Subject subject) {
        this.taughtSubjects.add(subject);
    }

    // Check if the teacher teaches a specific subject
    public boolean teachesSubject(Subject subject) {
        for (Subject s : taughtSubjects) {
            if (s.getId() == subject.getId()) {
                return true;
            }
        }
        return false;
    }

    // SECURITY FLAW: No verification if the teacher teaches the subject or if the student is in their class
    public void addGradeToStudent(Student student, Grade grade) {
        // No verification if the teacher teaches this subject
        // No verification if the student is in the teacher's class
        student.addGrade(grade);
        System.out.println("Grade added to student " + student.getFullName() + " by teacher " + this.getFullName());
    }

    // SECURITY FLAW: No verification if the teacher teaches the subject or if the student is in their class
    public void modifyGrade(Grade grade, double newValue) {
        // No verification if the teacher teaches this subject
        // No verification if the grade belongs to a student in the teacher's class
        grade.setValue(newValue);
        System.out.println("Grade modified by teacher " + this.getFullName());
    }

    // Calculate class average for a specific subject
    public double calculateClassAverageForSubject(List<Student> students, String subjectName) {
        double totalAverage = 0.0;
        int count = 0;

        for (Student student : students) {
            double studentAverage = student.calculateAverageForSubject(subjectName);
            if (studentAverage > 0) {
                totalAverage += studentAverage;
                count++;
            }
        }

        return count > 0 ? totalAverage / count : 0.0;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + getId() +
                ", username='" + getUsername() + '\'' +
                ", fullName='" + getFullName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", taughtSubjects=" + taughtSubjects +
                '}';
    }
}