package ui;

import model.Student;
import model.Teacher;
import model.User;
import security.AuthenticationService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Login screen for the application.
 * SECURITY FLAWS:
 * 1. Password shown in plain text
 * 2. No input validation
 * 3. No HTTPS/SSL
 * 4. No protection against brute force attacks
 */
public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JTextField passwordField; // SECURITY FLAW: Using JTextField instead of JPasswordField
    private JButton loginButton;
    private JLabel statusLabel;

    private AuthenticationService authService;

    public LoginFrame() {
        authService = new AuthenticationService();

        setTitle("Gestionnaire de Notes - Connexion");
        setSize(460, 440);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();

        // SECURITY FLAW: Hardcoded credentials in comments
        // Default users:
        // admin/admin123
        // teacher1/teacher123
        // student1/student123
    }

    private void initComponents() {
        // --- Palette & polices ---
        final Color fondHaut   = new Color(18, 55, 110);
        final Color fondBas    = new Color(35, 95, 170);
        final Color bleu       = new Color(30, 90, 160);
        final Color bleuHover  = new Color(22, 70, 135);
        final Color blanc      = Color.WHITE;
        final Color labelColor = new Color(70, 80, 95);
        final Color borderColor = new Color(195, 210, 230);

        // --- Fond dégradé ---
        JPanel background = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, fondHaut, getWidth(), getHeight(), fondBas);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        background.setOpaque(true);

        // --- Carte blanche ---
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(blanc);
        card.setPreferredSize(new Dimension(360, 390));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(185, 210, 235), 1),
            BorderFactory.createEmptyBorder(0, 0, 24, 0)));

        // --- En-tête de la carte ---
        JPanel cardHeader = new JPanel();
        cardHeader.setLayout(new BoxLayout(cardHeader, BoxLayout.Y_AXIS));
        cardHeader.setBackground(bleu);
        cardHeader.setBorder(BorderFactory.createEmptyBorder(22, 20, 18, 20));

        // Avatar "GN"
        JPanel avatarPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Cercle blanc semi-transparent
                g2.setColor(new Color(255, 255, 255, 55));
                g2.fillOval(0, 0, getWidth(), getHeight());
                // Bordure blanche légère
                g2.setColor(new Color(255, 255, 255, 130));
                g2.drawOval(1, 1, getWidth() - 3, getHeight() - 3);
                // Texte "GN"
                g2.setFont(new Font("Segoe UI", Font.BOLD, 19));
                g2.setColor(blanc);
                FontMetrics fm = g2.getFontMetrics();
                String txt = "GN";
                int tx = (getWidth() - fm.stringWidth(txt)) / 2;
                int ty = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                g2.drawString(txt, tx, ty);
            }
        };
        avatarPanel.setOpaque(false);
        avatarPanel.setPreferredSize(new Dimension(54, 54));
        avatarPanel.setMaximumSize(new Dimension(54, 54));
        avatarPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("Gestionnaire de Notes", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 17));
        titleLabel.setForeground(blanc);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Connexion à votre espace", JLabel.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(175, 210, 245));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        cardHeader.add(avatarPanel);
        cardHeader.add(Box.createVerticalStrut(10));
        cardHeader.add(titleLabel);
        cardHeader.add(Box.createVerticalStrut(3));
        cardHeader.add(subtitleLabel);

        // --- Corps formulaire ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(blanc);
        formPanel.setBorder(BorderFactory.createEmptyBorder(22, 30, 0, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0;

        // Label identifiant
        JLabel usernameLabel = new JLabel("Identifiant");
        usernameLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        usernameLabel.setForeground(labelColor);
        gbc.gridy = 0; gbc.insets = new Insets(0, 0, 4, 0);
        formPanel.add(usernameLabel, gbc);

        // Champ identifiant
        usernameField = new JTextField();
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        usernameField.setPreferredSize(new Dimension(0, 36));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(borderColor),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        gbc.gridy = 1; gbc.insets = new Insets(0, 0, 14, 0);
        formPanel.add(usernameField, gbc);

        // Label mot de passe
        JLabel passwordLabel = new JLabel("Mot de passe");
        passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        passwordLabel.setForeground(labelColor);
        gbc.gridy = 2; gbc.insets = new Insets(0, 0, 4, 0);
        formPanel.add(passwordLabel, gbc);

        // Champ mot de passe
        passwordField = new JTextField(); // SECURITY FLAW: Using JTextField instead of JPasswordField
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        passwordField.setPreferredSize(new Dimension(0, 36));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(borderColor),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        gbc.gridy = 3; gbc.insets = new Insets(0, 0, 18, 0);
        formPanel.add(passwordField, gbc);

        // Bouton connexion
        loginButton = new JButton("Se connecter");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        loginButton.setBackground(bleu);
        loginButton.setForeground(blanc);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setOpaque(true);
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        loginButton.setPreferredSize(new Dimension(0, 38));
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent e) {
                loginButton.setBackground(bleuHover);
            }
            @Override public void mouseExited(java.awt.event.MouseEvent e) {
                loginButton.setBackground(bleu);
            }
        });
        gbc.gridy = 4; gbc.insets = new Insets(0, 0, 8, 0);
        formPanel.add(loginButton, gbc);

        // Label statut
        statusLabel = new JLabel("", JLabel.CENTER);
        statusLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        statusLabel.setForeground(new Color(190, 40, 40));
        gbc.gridy = 5; gbc.insets = new Insets(0, 0, 0, 0);
        formPanel.add(statusLabel, gbc);

        // Assemblage carte
        card.add(cardHeader, BorderLayout.NORTH);
        card.add(formPanel, BorderLayout.CENTER);

        background.add(card);

        // Listeners
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        passwordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        add(background);
    }

    private void login() {
        String username = usernameField.getText();
        String password = passwordField.getText(); // SECURITY FLAW: Getting password as plain text

        // SECURITY FLAW: No input validation

        System.out.println("Login attempt: " + username + " / " + password); // SECURITY FLAW: Logging credentials

        if (authService.authenticate(username, password)) {
            User currentUser = AuthenticationService.getCurrentUser();

            // Open appropriate dashboard based on user role
            if (currentUser instanceof Student) {
                openStudentDashboard((Student) currentUser);
            } else if (currentUser instanceof Teacher) {
                openTeacherDashboard((Teacher) currentUser);
            } else if ("admin".equals(currentUser.getRole())) {
                openAdminDashboard(currentUser);
            } else {
                statusLabel.setText("Rôle inconnu : " + currentUser.getRole());
            }

            // Close login window
            dispose();
        } else {
            statusLabel.setText("Identifiant ou mot de passe incorrect");
        }
    }

    private void openStudentDashboard(Student student) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                StudentDashboard dashboard = new StudentDashboard(student);
                dashboard.setVisible(true);
            }
        });
    }

    private void openTeacherDashboard(Teacher teacher) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                TeacherDashboard dashboard = new TeacherDashboard(teacher);
                dashboard.setVisible(true);
            }
        });
    }

    private void openAdminDashboard(User admin) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                AdminDashboard dashboard = new AdminDashboard(admin);
                dashboard.setVisible(true);
            }
        });
    }

    // Main method for testing
    public static void main(String[] args) {
        // Initialize database
        dao.DatabaseConnection.initializeDatabase();

        // Create and show login frame
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
            }
        });
    }
}

// Dashboards will be implemented in separate files
