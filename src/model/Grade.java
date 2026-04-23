package model;

import java.util.Date;

/**
 * Represents a grade for a student in a specific subject.
 */
public class Grade {
    private int id;
    private double value;
    private double coefficient;
    private Subject subject;
    private int studentId; // SECURITY FLAW: No proper access control, any student can access any grade
    private String title;
    private String comment;
    private Date date;

    // Default constructor
    public Grade() {
    }

    // Parameterized constructor
    public Grade(int id, double value, double coefficient, Subject subject, int studentId, String title, String comment, Date date) {
        this.id = id;
        this.value = value;
        this.coefficient = coefficient;
        this.subject = subject;
        this.studentId = studentId;
        this.title = title;
        this.comment = comment;
        this.date = date;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", value=" + value +
                ", coefficient=" + coefficient +
                ", subject=" + subject +
                ", studentId=" + studentId +
                ", title='" + title + '\'' +
                ", comment='" + comment + '\'' +
                ", date=" + date +
                '}';
    }
}