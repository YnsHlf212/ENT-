package utils;

import dao.GradeDAO;
import dao.SubjectDAO;
import dao.UserDAO;
import model.Grade;
import model.Student;
import model.Subject;
import model.Teacher;
import model.User;

import java.util.Date;

/**
 * Utility class to initialize the database with sample data.
 */
public class DatabaseInitializer {
    
    private UserDAO userDAO;
    private SubjectDAO subjectDAO;
    private GradeDAO gradeDAO;
    
    public DatabaseInitializer() {
        this.userDAO = new UserDAO();
        this.subjectDAO = new SubjectDAO();
        this.gradeDAO = new GradeDAO();
    }
    
    /**
     * Initialize the database with sample data.
     */
    public void initializeDatabase() {
        // Initialize database schema
        dao.DatabaseConnection.initializeDatabase();
        
        // Create users
        createUsers();
        
        // Create subjects
        createSubjects();
        
        // Create grades
        createGrades();
        
        System.out.println("Database initialized with sample data");
    }
    
    private void createUsers() {
        // Create admin
        User admin = new User(1, "admin", "admin123", "admin", "Administrator", "admin@school.com");
        userDAO.addUser(admin);
        
        // Create teachers
        Teacher teacher1 = new Teacher(2, "teacher1", "teacher123", "John Smith", "john.smith@school.com");
        Teacher teacher2 = new Teacher(3, "teacher2", "teacher123", "Jane Doe", "jane.doe@school.com");
        userDAO.addUser(teacher1);
        userDAO.addUser(teacher2);
        
        // Create students
        Student student1 = new Student(4, "student1", "student123", "Alice Johnson", "alice.johnson@student.com", "Class A");
        Student student2 = new Student(5, "student2", "student123", "Bob Williams", "bob.williams@student.com", "Class A");
        Student student3 = new Student(6, "student3", "student123", "Charlie Brown", "charlie.brown@student.com", "Class B");
        Student student4 = new Student(7, "student4", "student123", "Diana Miller", "diana.miller@student.com", "Class B");
        userDAO.addUser(student1);
        userDAO.addUser(student2);
        userDAO.addUser(student3);
        userDAO.addUser(student4);
    }
    
    private void createSubjects() {
        // Create subjects
        Subject math = new Subject(1, "MATH101", "Mathematics", "Basic mathematics course", 2.0);
        Subject physics = new Subject(2, "PHYS101", "Physics", "Introduction to physics", 1.5);
        Subject cs = new Subject(3, "CS101", "Computer Science", "Introduction to programming", 2.0);
        Subject english = new Subject(4, "ENG101", "English", "English language course", 1.0);
        Subject history = new Subject(5, "HIST101", "History", "World history course", 1.0);
        
        subjectDAO.addSubject(math);
        subjectDAO.addSubject(physics);
        subjectDAO.addSubject(cs);
        subjectDAO.addSubject(english);
        subjectDAO.addSubject(history);
        
        // Assign subjects to teachers
        subjectDAO.assignSubjectToTeacher(1, 2); // Math to teacher1
        subjectDAO.assignSubjectToTeacher(2, 2); // Physics to teacher1
        subjectDAO.assignSubjectToTeacher(3, 3); // CS to teacher2
        subjectDAO.assignSubjectToTeacher(4, 3); // English to teacher2
        subjectDAO.assignSubjectToTeacher(5, 3); // History to teacher2
    }
    
    private void createGrades() {
        // Get subjects
        Subject math = subjectDAO.getSubjectById(1);
        Subject physics = subjectDAO.getSubjectById(2);
        Subject cs = subjectDAO.getSubjectById(3);
        Subject english = subjectDAO.getSubjectById(4);
        Subject history = subjectDAO.getSubjectById(5);
        
        // Create grades for student1
        Grade grade1 = new Grade(1, 15.5, 1.0, math, 4, "Quiz 1", "Good work", new Date());
        Grade grade2 = new Grade(2, 17.0, 2.0, math, 4, "Midterm", "Excellent", new Date());
        Grade grade3 = new Grade(3, 14.0, 1.0, physics, 4, "Lab 1", "Satisfactory", new Date());
        Grade grade4 = new Grade(4, 16.0, 1.5, cs, 4, "Project 1", "Very good project", new Date());
        
        gradeDAO.addGrade(grade1);
        gradeDAO.addGrade(grade2);
        gradeDAO.addGrade(grade3);
        gradeDAO.addGrade(grade4);
        
        // Create grades for student2
        Grade grade5 = new Grade(5, 14.0, 1.0, math, 5, "Quiz 1", "Good", new Date());
        Grade grade6 = new Grade(6, 13.5, 2.0, math, 5, "Midterm", "Average", new Date());
        Grade grade7 = new Grade(7, 16.0, 1.0, physics, 5, "Lab 1", "Very good", new Date());
        Grade grade8 = new Grade(8, 18.0, 1.5, cs, 5, "Project 1", "Excellent work", new Date());
        
        gradeDAO.addGrade(grade5);
        gradeDAO.addGrade(grade6);
        gradeDAO.addGrade(grade7);
        gradeDAO.addGrade(grade8);
        
        // Create grades for student3
        Grade grade9 = new Grade(9, 12.0, 1.0, math, 6, "Quiz 1", "Needs improvement", new Date());
        Grade grade10 = new Grade(10, 11.5, 2.0, math, 6, "Midterm", "Below average", new Date());
        Grade grade11 = new Grade(11, 13.0, 1.0, physics, 6, "Lab 1", "Average", new Date());
        Grade grade12 = new Grade(12, 15.0, 1.5, english, 6, "Essay 1", "Good writing", new Date());
        
        gradeDAO.addGrade(grade9);
        gradeDAO.addGrade(grade10);
        gradeDAO.addGrade(grade11);
        gradeDAO.addGrade(grade12);
        
        // Create grades for student4
        Grade grade13 = new Grade(13, 18.0, 1.0, math, 7, "Quiz 1", "Excellent", new Date());
        Grade grade14 = new Grade(14, 19.0, 2.0, math, 7, "Midterm", "Outstanding", new Date());
        Grade grade15 = new Grade(15, 17.5, 1.0, physics, 7, "Lab 1", "Very good", new Date());
        Grade grade16 = new Grade(16, 16.0, 1.5, history, 7, "Essay 1", "Good historical analysis", new Date());
        
        gradeDAO.addGrade(grade13);
        gradeDAO.addGrade(grade14);
        gradeDAO.addGrade(grade15);
        gradeDAO.addGrade(grade16);
    }
    
    public static void main(String[] args) {
        DatabaseInitializer initializer = new DatabaseInitializer();
        initializer.initializeDatabase();
    }
}