package dao;

import model.Grade;
import model.Subject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Data Access Object for Grade entities.
 * SECURITY FLAWS:
 * 1. SQL injection vulnerabilities (no prepared statements)
 * 2. No input validation
 * 3. No proper connection handling
 * 4. No access control (any user can access any grade)
 */
public class GradeDAO {
    
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    
    // Get grade by ID
    public Grade getGradeById(int gradeId) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Grade grade = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.createStatement();
            
            // SECURITY FLAW: SQL injection vulnerability (no prepared statement)
            String query = "SELECT g.*, s.* FROM grades g " +
                    "JOIN subjects s ON g.subject_id = s.id " +
                    "WHERE g.id = " + gradeId;
            
            System.out.println("Executing query: " + query);
            
            resultSet = statement.executeQuery(query);
            
            if (resultSet.next()) {
                double value = resultSet.getDouble("value");
                double coefficient = resultSet.getDouble("coefficient");
                int studentId = resultSet.getInt("student_id");
                String title = resultSet.getString("title");
                String comment = resultSet.getString("comment");
                String dateStr = resultSet.getString("date");
                Date date = null;
                try {
                    if (dateStr != null) {
                        date = DATE_FORMAT.parse(dateStr);
                    }
                } catch (ParseException e) {
                    System.out.println("Error parsing date: " + e.getMessage());
                    e.printStackTrace();
                }
                
                // Get subject data
                int subjectId = resultSet.getInt("subject_id");
                String code = resultSet.getString("code");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double defaultCoefficient = resultSet.getDouble("default_coefficient");
                
                Subject subject = new Subject(subjectId, code, name, description, defaultCoefficient);
                
                grade = new Grade(gradeId, value, coefficient, subject, studentId, title, comment, date);
            }
        } catch (SQLException e) {
            System.out.println("Error getting grade by ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        return grade;
    }
    
    // Get all grades for a student
    // SECURITY FLAW: No access control, any user can access any student's grades
    public List<Grade> getGradesByStudentId(int studentId) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Grade> grades = new ArrayList<>();
        
        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.createStatement();
            
            // SECURITY FLAW: SQL injection vulnerability (no prepared statement)
            String query = "SELECT g.*, s.* FROM grades g " +
                    "JOIN subjects s ON g.subject_id = s.id " +
                    "WHERE g.student_id = " + studentId;
            
            System.out.println("Executing query: " + query);
            
            resultSet = statement.executeQuery(query);
            
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                double value = resultSet.getDouble("value");
                double coefficient = resultSet.getDouble("coefficient");
                String title = resultSet.getString("title");
                String comment = resultSet.getString("comment");
                String dateStr = resultSet.getString("date");
                Date date = null;
                try {
                    if (dateStr != null) {
                        date = DATE_FORMAT.parse(dateStr);
                    }
                } catch (ParseException e) {
                    System.out.println("Error parsing date: " + e.getMessage());
                    e.printStackTrace();
                }
                
                // Get subject data
                int subjectId = resultSet.getInt("subject_id");
                String code = resultSet.getString("code");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double defaultCoefficient = resultSet.getDouble("default_coefficient");
                
                Subject subject = new Subject(subjectId, code, name, description, defaultCoefficient);
                
                Grade grade = new Grade(id, value, coefficient, subject, studentId, title, comment, date);
                grades.add(grade);
            }
        } catch (SQLException e) {
            System.out.println("Error getting grades by student ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        return grades;
    }
    
    // Get all grades for a subject
    public List<Grade> getGradesBySubjectId(int subjectId) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Grade> grades = new ArrayList<>();
        
        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.createStatement();
            
            // SECURITY FLAW: SQL injection vulnerability (no prepared statement)
            String query = "SELECT g.*, s.* FROM grades g " +
                    "JOIN subjects s ON g.subject_id = s.id " +
                    "WHERE g.subject_id = " + subjectId;
            
            System.out.println("Executing query: " + query);
            
            resultSet = statement.executeQuery(query);
            
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                double value = resultSet.getDouble("value");
                double coefficient = resultSet.getDouble("coefficient");
                int studentId = resultSet.getInt("student_id");
                String title = resultSet.getString("title");
                String comment = resultSet.getString("comment");
                String dateStr = resultSet.getString("date");
                Date date = null;
                try {
                    if (dateStr != null) {
                        date = DATE_FORMAT.parse(dateStr);
                    }
                } catch (ParseException e) {
                    System.out.println("Error parsing date: " + e.getMessage());
                    e.printStackTrace();
                }
                
                // Get subject data
                String code = resultSet.getString("code");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double defaultCoefficient = resultSet.getDouble("default_coefficient");
                
                Subject subject = new Subject(subjectId, code, name, description, defaultCoefficient);
                
                Grade grade = new Grade(id, value, coefficient, subject, studentId, title, comment, date);
                grades.add(grade);
            }
        } catch (SQLException e) {
            System.out.println("Error getting grades by subject ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        return grades;
    }
    
    // Add a new grade
    // SECURITY FLAW: No access control, any user can add a grade for any student
    public boolean addGrade(Grade grade) {
        Connection connection = null;
        Statement statement = null;
        boolean success = false;
        
        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.createStatement();
            
            String dateStr = grade.getDate() != null ? "'" + DATE_FORMAT.format(grade.getDate()) + "'" : "NULL";
            
            // SECURITY FLAW: SQL injection vulnerability (no prepared statement)
            String query = "INSERT INTO grades (value, coefficient, subject_id, student_id, title, comment, date) VALUES (" +
                    grade.getValue() + ", " +
                    grade.getCoefficient() + ", " +
                    grade.getSubject().getId() + ", " +
                    grade.getStudentId() + ", " +
                    "'" + grade.getTitle() + "', " +
                    "'" + grade.getComment() + "', " +
                    dateStr + ")";
            
            System.out.println("Executing query: " + query);
            
            int rowsAffected = statement.executeUpdate(query);
            success = rowsAffected > 0;
            
            if (success) {
                System.out.println("Grade added successfully for student ID: " + grade.getStudentId());
            }
        } catch (SQLException e) {
            System.out.println("Error adding grade: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        return success;
    }
    
    // Update an existing grade
    // SECURITY FLAW: No access control, any user can update any grade
    public boolean updateGrade(Grade grade) {
        Connection connection = null;
        Statement statement = null;
        boolean success = false;
        
        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.createStatement();
            
            String dateStr = grade.getDate() != null ? "'" + DATE_FORMAT.format(grade.getDate()) + "'" : "NULL";
            
            // SECURITY FLAW: SQL injection vulnerability (no prepared statement)
            String query = "UPDATE grades SET " +
                    "value = " + grade.getValue() + ", " +
                    "coefficient = " + grade.getCoefficient() + ", " +
                    "subject_id = " + grade.getSubject().getId() + ", " +
                    "student_id = " + grade.getStudentId() + ", " +
                    "title = '" + grade.getTitle() + "', " +
                    "comment = '" + grade.getComment() + "', " +
                    "date = " + dateStr + " " +
                    "WHERE id = " + grade.getId();
            
            System.out.println("Executing query: " + query);
            
            int rowsAffected = statement.executeUpdate(query);
            success = rowsAffected > 0;
            
            if (success) {
                System.out.println("Grade updated successfully, ID: " + grade.getId());
            }
        } catch (SQLException e) {
            System.out.println("Error updating grade: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        return success;
    }
    
    // Delete a grade
    // SECURITY FLAW: No access control, any user can delete any grade
    public boolean deleteGrade(int gradeId) {
        Connection connection = null;
        Statement statement = null;
        boolean success = false;
        
        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.createStatement();
            
            // SECURITY FLAW: SQL injection vulnerability (no prepared statement)
            String query = "DELETE FROM grades WHERE id = " + gradeId;
            
            System.out.println("Executing query: " + query);
            
            int rowsAffected = statement.executeUpdate(query);
            success = rowsAffected > 0;
            
            if (success) {
                System.out.println("Grade deleted successfully, ID: " + gradeId);
            }
        } catch (SQLException e) {
            System.out.println("Error deleting grade: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        return success;
    }
    
    // Calculate average grade for a student in a subject
    public double calculateAverageForStudentInSubject(int studentId, int subjectId) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        double average = 0.0;
        
        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.createStatement();
            
            // SECURITY FLAW: SQL injection vulnerability (no prepared statement)
            String query = "SELECT SUM(value * coefficient) / SUM(coefficient) as average " +
                    "FROM grades WHERE student_id = " + studentId + " AND subject_id = " + subjectId;
            
            System.out.println("Executing query: " + query);
            
            resultSet = statement.executeQuery(query);
            
            if (resultSet.next()) {
                average = resultSet.getDouble("average");
            }
        } catch (SQLException e) {
            System.out.println("Error calculating average: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        return average;
    }
    
    // Calculate overall average grade for a student
    public double calculateOverallAverageForStudent(int studentId) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        double average = 0.0;
        
        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.createStatement();
            
            // SECURITY FLAW: SQL injection vulnerability (no prepared statement)
            String query = "SELECT SUM(value * coefficient) / SUM(coefficient) as average " +
                    "FROM grades WHERE student_id = " + studentId;
            
            System.out.println("Executing query: " + query);
            
            resultSet = statement.executeQuery(query);
            
            if (resultSet.next()) {
                average = resultSet.getDouble("average");
            }
        } catch (SQLException e) {
            System.out.println("Error calculating overall average: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        return average;
    }
}