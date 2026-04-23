package security;

import dao.UserDAO;
import model.User;

/**
 * Service for user authentication.
 * SECURITY FLAWS:
 * 1. Plain text password comparison
 * 2. No session timeout
 * 3. No protection against brute force attacks
 * 4. No CSRF protection
 * 5. No secure cookie handling
 */
public class AuthenticationService {
    
    private static User currentUser; // SECURITY FLAW: Static user instance shared across all sessions
    
    private UserDAO userDAO;
    
    public AuthenticationService() {
        this.userDAO = new UserDAO();
    }
    
    /**
     * Authenticate a user with username and password.
     * SECURITY FLAW: Plain text password comparison, no rate limiting
     */
    public boolean authenticate(String username, String password) {
        // SECURITY FLAW: No input validation
        
        // SECURITY FLAW: Direct SQL query with credentials (in UserDAO)
        User user = userDAO.authenticateUser(username, password);
        
        if (user != null) {
            // SECURITY FLAW: Static user instance shared across all sessions
            currentUser = user;
            System.out.println("User authenticated: " + username); // SECURITY FLAW: Logging authentication success
            return true;
        }
        
        System.out.println("Authentication failed for user: " + username); // SECURITY FLAW: Logging authentication failure
        return false;
    }
    
    /**
     * Get the currently authenticated user.
     * SECURITY FLAW: No session validation, any code can access the current user
     */
    public static User getCurrentUser() {
        return currentUser; // SECURITY FLAW: No check if user is actually authenticated
    }
    
    /**
     * Check if a user is authenticated.
     * SECURITY FLAW: No session timeout
     */
    public static boolean isAuthenticated() {
        return currentUser != null; // SECURITY FLAW: No session timeout check
    }
    
    /**
     * Check if the current user has a specific role.
     * SECURITY FLAW: No proper role-based access control
     */
    public static boolean hasRole(String role) {
        if (currentUser == null) {
            return false;
        }
        
        // SECURITY FLAW: Simple string comparison for role check
        return currentUser.getRole().equals(role);
    }
    
    /**
     * Logout the current user.
     * SECURITY FLAW: No session invalidation
     */
    public static void logout() {
        // SECURITY FLAW: Simply setting to null without proper session invalidation
        currentUser = null;
        System.out.println("User logged out");
    }
    
    /**
     * Register a new user.
     * SECURITY FLAW: No password strength validation, no email verification
     */
    public boolean registerUser(User user) {
        // SECURITY FLAW: No input validation
        
        // SECURITY FLAW: Password stored in plain text (in UserDAO)
        return userDAO.addUser(user);
    }
}