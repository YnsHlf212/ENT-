package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class for database connection.
 * SECURITY FLAWS:
 * 1. Hardcoded database path
 * 2. No connection pooling
 * 3. No proper exception handling
 * 4. No connection closing mechanism
 */
public class DatabaseConnection {
    // SECURITY FLAW: Hardcoded database path
    private static final String DB_URL = "jdbc:sqlite:notes.db";
    
    // SECURITY FLAW: No connection pooling, creating a new connection each time
    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");
            
            // Create a connection to the database
            connection = DriverManager.getConnection(DB_URL);
            
            System.out.println("Connected to the database"); // SECURITY FLAW: Logging sensitive information
            
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Database connection error: " + e.getMessage()); // SECURITY FLAW: Exposing error details
            e.printStackTrace(); // SECURITY FLAW: Printing stack trace to console
            return null; // SECURITY FLAW: Returning null instead of proper error handling
        }
    }
    
    // SECURITY FLAW: No method to close connections properly
    
    // Method to initialize the database schema
    public static void initializeDatabase() {
        Connection connection = null;
        try {
            connection = getConnection();
            
            if (connection != null) {
                // Create tables if they don't exist
                String createUserTable = "CREATE TABLE IF NOT EXISTS users (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "username TEXT NOT NULL UNIQUE," +
                        "password TEXT NOT NULL," + // SECURITY FLAW: Storing passwords as plain text
                        "role TEXT NOT NULL," +
                        "full_name TEXT NOT NULL," +
                        "email TEXT," +
                        "class_group TEXT" +
                        ")";
                
                String createSubjectTable = "CREATE TABLE IF NOT EXISTS subjects (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "code TEXT NOT NULL UNIQUE," +
                        "name TEXT NOT NULL," +
                        "description TEXT," +
                        "default_coefficient REAL DEFAULT 1.0" +
                        ")";
                
                String createGradeTable = "CREATE TABLE IF NOT EXISTS grades (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "value REAL NOT NULL," +
                        "coefficient REAL DEFAULT 1.0," +
                        "subject_id INTEGER NOT NULL," +
                        "student_id INTEGER NOT NULL," +
                        "title TEXT," +
                        "comment TEXT," +
                        "date TEXT," +
                        "FOREIGN KEY (subject_id) REFERENCES subjects(id)," +
                        "FOREIGN KEY (student_id) REFERENCES users(id)" +
                        ")";
                
                String createTeacherSubjectTable = "CREATE TABLE IF NOT EXISTS teacher_subjects (" +
                        "teacher_id INTEGER NOT NULL," +
                        "subject_id INTEGER NOT NULL," +
                        "PRIMARY KEY (teacher_id, subject_id)," +
                        "FOREIGN KEY (teacher_id) REFERENCES users(id)," +
                        "FOREIGN KEY (subject_id) REFERENCES subjects(id)" +
                        ")";
                
                connection.createStatement().execute(createUserTable);
                connection.createStatement().execute(createSubjectTable);
                connection.createStatement().execute(createGradeTable);
                connection.createStatement().execute(createTeacherSubjectTable);
                
                System.out.println("Database schema initialized successfully");
            }
        } catch (SQLException e) {
            System.out.println("Error initializing database schema: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // SECURITY FLAW: Not using try-with-resources for proper connection closing
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Error closing connection: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }
}