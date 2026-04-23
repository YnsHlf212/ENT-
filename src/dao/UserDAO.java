package dao;

import model.Student;
import model.Teacher;
import model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for User entities.
 * SECURITY FLAWS:
 * 1. SQL injection vulnerabilities (no prepared statements)
 * 2. No input validation
 * 3. Passwords stored and retrieved in plain text
 * 4. No proper connection handling
 * 5. Sensitive data exposure in logs
 */
public class UserDAO {
    
    // SECURITY FLAW: SQL injection vulnerability (using string concatenation)
    public User getUserByUsername(String username) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        User user = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.createStatement();
            
            // SECURITY FLAW: SQL injection vulnerability (no prepared statement)
            String query = "SELECT * FROM users WHERE username = '" + username + "'";
            System.out.println("Executing query: " + query); // SECURITY FLAW: Logging query with potential sensitive data
            
            resultSet = statement.executeQuery(query);
            
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String password = resultSet.getString("password"); // SECURITY FLAW: Retrieving password in plain text
                String role = resultSet.getString("role");
                String fullName = resultSet.getString("full_name");
                String email = resultSet.getString("email");
                
                System.out.println("Found user: " + username + " with password: " + password); // SECURITY FLAW: Logging sensitive data
                
                if ("student".equals(role)) {
                    String classGroup = resultSet.getString("class_group");
                    user = new Student(id, username, password, fullName, email, classGroup);
                } else if ("teacher".equals(role)) {
                    user = new Teacher(id, username, password, fullName, email);
                } else {
                    user = new User(id, username, password, role, fullName, email);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting user by username: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // SECURITY FLAW: Not properly closing resources
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        return user;
    }
    
    // SECURITY FLAW: SQL injection vulnerability (using string concatenation)
    public User authenticateUser(String username, String password) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        User user = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.createStatement();
            
            // SECURITY FLAW: SQL injection vulnerability (no prepared statement)
            // SECURITY FLAW: Plain text password comparison in SQL
            String query = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'";
            System.out.println("Executing authentication query: " + query); // SECURITY FLAW: Logging query with credentials
            
            resultSet = statement.executeQuery(query);
            
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String role = resultSet.getString("role");
                String fullName = resultSet.getString("full_name");
                String email = resultSet.getString("email");
                
                System.out.println("User authenticated: " + username); // SECURITY FLAW: Logging authentication success
                
                if ("student".equals(role)) {
                    String classGroup = resultSet.getString("class_group");
                    user = new Student(id, username, password, fullName, email, classGroup);
                } else if ("teacher".equals(role)) {
                    user = new Teacher(id, username, password, fullName, email);
                } else {
                    user = new User(id, username, password, role, fullName, email);
                }
            } else {
                System.out.println("Authentication failed for user: " + username); // SECURITY FLAW: Logging authentication failure
            }
        } catch (SQLException e) {
            System.out.println("Error authenticating user: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // SECURITY FLAW: Not using try-with-resources
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        return user;
    }
    
    // Get all students
    public List<Student> getAllStudents() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Student> students = new ArrayList<>();
        
        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.createStatement();
            
            String query = "SELECT * FROM users WHERE role = 'student'";
            resultSet = statement.executeQuery(query);
            
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password"); // SECURITY FLAW: Retrieving password in plain text
                String fullName = resultSet.getString("full_name");
                String email = resultSet.getString("email");
                String classGroup = resultSet.getString("class_group");
                
                Student student = new Student(id, username, password, fullName, email, classGroup);
                students.add(student);
            }
        } catch (SQLException e) {
            System.out.println("Error getting all students: " + e.getMessage());
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
        
        return students;
    }
    
    // Get all teachers
    public List<Teacher> getAllTeachers() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Teacher> teachers = new ArrayList<>();
        
        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.createStatement();
            
            String query = "SELECT * FROM users WHERE role = 'teacher'";
            resultSet = statement.executeQuery(query);
            
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password"); // SECURITY FLAW: Retrieving password in plain text
                String fullName = resultSet.getString("full_name");
                String email = resultSet.getString("email");
                
                Teacher teacher = new Teacher(id, username, password, fullName, email);
                teachers.add(teacher);
            }
        } catch (SQLException e) {
            System.out.println("Error getting all teachers: " + e.getMessage());
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
        
        return teachers;
    }
    
    // Add a new user
    public boolean addUser(User user) {
        Connection connection = null;
        Statement statement = null;
        boolean success = false;
        
        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.createStatement();
            
            String classGroup = "";
            if (user instanceof Student) {
                classGroup = ((Student) user).getClassGroup();
            }
            
            // SECURITY FLAW: SQL injection vulnerability (no prepared statement)
            String query = "INSERT INTO users (username, password, role, full_name, email, class_group) VALUES ('" +
                    user.getUsername() + "', '" +
                    user.getPassword() + "', '" + // SECURITY FLAW: Storing password in plain text
                    user.getRole() + "', '" +
                    user.getFullName() + "', '" +
                    user.getEmail() + "', '" +
                    classGroup + "')";
            
            System.out.println("Executing query: " + query); // SECURITY FLAW: Logging query with sensitive data
            
            int rowsAffected = statement.executeUpdate(query);
            success = rowsAffected > 0;
            
            if (success) {
                System.out.println("User added successfully: " + user.getUsername());
            }
        } catch (SQLException e) {
            System.out.println("Error adding user: " + e.getMessage());
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
    
    // Update an existing user
    public boolean updateUser(User user) {
        Connection connection = null;
        Statement statement = null;
        boolean success = false;
        
        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.createStatement();
            
            String classGroup = "";
            if (user instanceof Student) {
                classGroup = ((Student) user).getClassGroup();
            }
            
            // SECURITY FLAW: SQL injection vulnerability (no prepared statement)
            String query = "UPDATE users SET " +
                    "username = '" + user.getUsername() + "', " +
                    "password = '" + user.getPassword() + "', " + // SECURITY FLAW: Updating password in plain text
                    "role = '" + user.getRole() + "', " +
                    "full_name = '" + user.getFullName() + "', " +
                    "email = '" + user.getEmail() + "', " +
                    "class_group = '" + classGroup + "' " +
                    "WHERE id = " + user.getId();
            
            System.out.println("Executing query: " + query); // SECURITY FLAW: Logging query with sensitive data
            
            int rowsAffected = statement.executeUpdate(query);
            success = rowsAffected > 0;
            
            if (success) {
                System.out.println("User updated successfully: " + user.getUsername());
            }
        } catch (SQLException e) {
            System.out.println("Error updating user: " + e.getMessage());
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
    
    // Delete a user
    public boolean deleteUser(int userId) {
        Connection connection = null;
        Statement statement = null;
        boolean success = false;
        
        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.createStatement();
            
            // SECURITY FLAW: SQL injection vulnerability (no prepared statement)
            String query = "DELETE FROM users WHERE id = " + userId;
            
            System.out.println("Executing query: " + query);
            
            int rowsAffected = statement.executeUpdate(query);
            success = rowsAffected > 0;
            
            if (success) {
                System.out.println("User deleted successfully, ID: " + userId);
            }
        } catch (SQLException e) {
            System.out.println("Error deleting user: " + e.getMessage());
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