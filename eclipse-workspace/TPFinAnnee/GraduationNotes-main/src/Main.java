import ui.LoginFrame;
import utils.DatabaseInitializer;

import javax.swing.*;

/**
 * Main class for the Student Grade Manager application.
 * This class serves as the entry point for the application.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Student Grade Manager application...");

        // Initialize database with sample data
        initializeDatabase();

        // Launch the login screen
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
            }
        });
    }

    /**
     * Initialize the database with sample data if needed.
     */
    private static void initializeDatabase() {
        try {
            DatabaseInitializer initializer = new DatabaseInitializer();
            initializer.initializeDatabase();
        } catch (Exception e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();

            // Show error dialog
            JOptionPane.showMessageDialog(null,
                    "Erreur lors de l'initialisation de la base de données : " + e.getMessage(),
                    "Erreur de base de données",
                    JOptionPane.ERROR_MESSAGE);

            // Exit application
            System.exit(1);
        }
    }
}
