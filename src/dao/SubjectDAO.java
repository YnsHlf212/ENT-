package dao;

import model.Subject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Subject entities.
 * SECURITY FLAWS:
 * 1. SQL injection vulnerabilities (no prepared statements)
 * 2. No input validation
 * 3. No proper connection handling
 */
public class SubjectDAO {
    
    // Get subject by ID
    public Subject getSubjectById(int subjectId) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Subject subject = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.createStatement();
            
            // SECURITY FLAW: SQL injection vulnerability (no prepared statement)
            String query = "SELECT * FROM subjects WHERE id = " + subjectId;
            System.out.println("Executing query: " + query);
            
            resultSet = statement.executeQuery(query);
            
            if (resultSet.next()) {
                String code = resultSet.getString("code");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double defaultCoefficient = resultSet.getDouble("default_coefficient");
                
                subject = new Subject(subjectId, code, name, description, defaultCoefficient);
            }
        } catch (SQLException e) {
            System.out.println("Error getting subject by ID: " + e.getMessage());
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
        
        return subject;
    }
    
    // Get subject by code
    public Subject getSubjectByCode(String code) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Subject subject = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.createStatement();
            
            // SECURITY FLAW: SQL injection vulnerability (no prepared statement)
            String query = "SELECT * FROM subjects WHERE code = '" + code + "'";
            System.out.println("Executing query: " + query);
            
            resultSet = statement.executeQuery(query);
            
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double defaultCoefficient = resultSet.getDouble("default_coefficient");
                
                subject = new Subject(id, code, name, description, defaultCoefficient);
            }
        } catch (SQLException e) {
            System.out.println("Error getting subject by code: " + e.getMessage());
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
        
        return subject;
    }
    
    // Get all subjects
    public List<Subject> getAllSubjects() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Subject> subjects = new ArrayList<>();
        
        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.createStatement();
            
            String query = "SELECT * FROM subjects";
            resultSet = statement.executeQuery(query);
            
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String code = resultSet.getString("code");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double defaultCoefficient = resultSet.getDouble("default_coefficient");
                
                Subject subject = new Subject(id, code, name, description, defaultCoefficient);
                subjects.add(subject);
            }
        } catch (SQLException e) {
            System.out.println("Error getting all subjects: " + e.getMessage());
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
        
        return subjects;
    }
    
    // Add a new subject
    public boolean addSubject(Subject subject) {
        Connection connection = null;
        Statement statement = null;
        boolean success = false;
        
        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.createStatement();
            
            // SECURITY FLAW: SQL injection vulnerability (no prepared statement)
            String query = "INSERT INTO subjects (code, name, description, default_coefficient) VALUES ('" +
                    subject.getCode() + "', '" +
                    subject.getName() + "', '" +
                    subject.getDescription() + "', " +
                    subject.getDefaultCoefficient() + ")";
            
            System.out.println("Executing query: " + query);
            
            int rowsAffected = statement.executeUpdate(query);
            success = rowsAffected > 0;
            
            if (success) {
                System.out.println("Subject added successfully: " + subject.getName());
            }
        } catch (SQLException e) {
            System.out.println("Error adding subject: " + e.getMessage());
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
    
    // Update an existing subject
    public boolean updateSubject(Subject subject) {
        Connection connection = null;
        Statement statement = null;
        boolean success = false;
        
        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.createStatement();
            
            // SECURITY FLAW: SQL injection vulnerability (no prepared statement)
            String query = "UPDATE subjects SET " +
                    "code = '" + subject.getCode() + "', " +
                    "name = '" + subject.getName() + "', " +
                    "description = '" + subject.getDescription() + "', " +
                    "default_coefficient = " + subject.getDefaultCoefficient() + " " +
                    "WHERE id = " + subject.getId();
            
            System.out.println("Executing query: " + query);
            
            int rowsAffected = statement.executeUpdate(query);
            success = rowsAffected > 0;
            
            if (success) {
                System.out.println("Subject updated successfully: " + subject.getName());
            }
        } catch (SQLException e) {
            System.out.println("Error updating subject: " + e.getMessage());
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
    
    // Delete a subject
    public boolean deleteSubject(int subjectId) {
        Connection connection = null;
        Statement statement = null;
        boolean success = false;
        
        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.createStatement();
            
            // SECURITY FLAW: SQL injection vulnerability (no prepared statement)
            String query = "DELETE FROM subjects WHERE id = " + subjectId;
            
            System.out.println("Executing query: " + query);
            
            int rowsAffected = statement.executeUpdate(query);
            success = rowsAffected > 0;
            
            if (success) {
                System.out.println("Subject deleted successfully, ID: " + subjectId);
            }
        } catch (SQLException e) {
            System.out.println("Error deleting subject: " + e.getMessage());
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
    
    // Get subjects taught by a teacher
    public List<Subject> getSubjectsByTeacherId(int teacherId) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Subject> subjects = new ArrayList<>();
        
        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.createStatement();
            
            // SECURITY FLAW: SQL injection vulnerability (no prepared statement)
            String query = "SELECT s.* FROM subjects s " +
                    "JOIN teacher_subjects ts ON s.id = ts.subject_id " +
                    "WHERE ts.teacher_id = " + teacherId;
            
            System.out.println("Executing query: " + query);
            
            resultSet = statement.executeQuery(query);
            
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String code = resultSet.getString("code");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double defaultCoefficient = resultSet.getDouble("default_coefficient");
                
                Subject subject = new Subject(id, code, name, description, defaultCoefficient);
                subjects.add(subject);
            }
        } catch (SQLException e) {
            System.out.println("Error getting subjects by teacher ID: " + e.getMessage());
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
        
        return subjects;
    }
    
    // Assign a subject to a teacher
    public boolean assignSubjectToTeacher(int subjectId, int teacherId) {
        Connection connection = null;
        Statement statement = null;
        boolean success = false;
        
        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.createStatement();
            
            // SECURITY FLAW: SQL injection vulnerability (no prepared statement)
            String query = "INSERT INTO teacher_subjects (teacher_id, subject_id) VALUES (" +
                    teacherId + ", " + subjectId + ")";
            
            System.out.println("Executing query: " + query);
            
            int rowsAffected = statement.executeUpdate(query);
            success = rowsAffected > 0;
            
            if (success) {
                System.out.println("Subject assigned to teacher successfully");
            }
        } catch (SQLException e) {
            System.out.println("Error assigning subject to teacher: " + e.getMessage());
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
}