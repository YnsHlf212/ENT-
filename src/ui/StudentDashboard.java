package ui;

import dao.GradeDAO;
import dao.SubjectDAO;
import model.Grade;
import model.Student;
import model.Subject;
import security.AuthenticationService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Dashboard for student users.
 * SECURITY FLAWS:
 * 1. No access control (can view/modify other students' grades)
 * 2. No input validation
 * 3. No CSRF protection
 * 4. XSS vulnerability in displaying comments
 */
public class StudentDashboard extends JFrame {
    
    private Student student;
    private GradeDAO gradeDAO;
    private SubjectDAO subjectDAO;
    
    private JTable gradesTable;
    private DefaultTableModel tableModel;
    private JTextField studentIdField; // SECURITY FLAW: Allowing to enter any student ID
    private JButton viewGradesButton;
    private JButton logoutButton;
    private JLabel averageLabel;
    
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    
    public StudentDashboard(Student student) {
        this.student = student;
        this.gradeDAO = new GradeDAO();
        this.subjectDAO = new SubjectDAO();
        
        setTitle("Tableau de bord Élève - " + student.getFullName());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initComponents();
        loadGrades(student.getId()); // Initially load the current student's grades
    }
    
    private void initComponents() {
        final Color bleu = new Color(30, 90, 160);
        final Color blanc = Color.WHITE;

        // Header bleu
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(bleu);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));

        JLabel welcomeLabel = new JLabel("Bienvenue, " + student.getFullName() + " !");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        welcomeLabel.setForeground(blanc);

        logoutButton = new JButton("Déconnexion");
        logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        logoutButton.setBackground(new Color(22, 70, 135));
        logoutButton.setForeground(blanc);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorderPainted(false);
        logoutButton.setOpaque(true);
        logoutButton.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
        logoutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });

        headerPanel.add(welcomeLabel, BorderLayout.WEST);
        headerPanel.add(logoutButton, BorderLayout.EAST);

        // Barre de recherche / sélection (SECURITY FLAW: Allowing to view any student's grades)
        JPanel studentIdPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        studentIdPanel.setBackground(new Color(240, 245, 252));
        studentIdPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(195, 210, 230)));

        JLabel idLabel = new JLabel("ID Élève :");
        idLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        idLabel.setForeground(new Color(70, 80, 95));
        studentIdPanel.add(idLabel);

        studentIdField = new JTextField(8);
        studentIdField.setText(String.valueOf(student.getId())); // Default to current student
        studentIdField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        studentIdField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(195, 210, 230)),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        studentIdPanel.add(studentIdField);

        viewGradesButton = new JButton("Voir les notes");
        viewGradesButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        viewGradesButton.setBackground(bleu);
        viewGradesButton.setForeground(blanc);
        viewGradesButton.setFocusPainted(false);
        viewGradesButton.setBorderPainted(false);
        viewGradesButton.setOpaque(true);
        viewGradesButton.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
        viewGradesButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        viewGradesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int studentId = Integer.parseInt(studentIdField.getText());
                    loadGrades(studentId);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(StudentDashboard.this,
                            "ID élève invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        studentIdPanel.add(viewGradesButton);

        // Average label
        averageLabel = new JLabel("Moyenne générale : N/A");
        averageLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        averageLabel.setForeground(bleu);
        studentIdPanel.add(Box.createHorizontalStrut(30));
        studentIdPanel.add(averageLabel);

        // Table des notes
        String[] columns = {"ID", "Matière", "Titre", "Note", "Coefficient", "Date", "Commentaire"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // SECURITY FLAW: Allow editing the grade value
                return column == 3; // Only the value column is editable
            }
        };

        gradesTable = new JTable(tableModel);
        gradesTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        gradesTable.setRowHeight(26);
        gradesTable.setGridColor(new Color(220, 228, 240));
        gradesTable.setSelectionBackground(new Color(210, 225, 245));
        gradesTable.setSelectionForeground(Color.BLACK);
        gradesTable.getTableHeader().setBackground(new Color(30, 90, 160));
        gradesTable.getTableHeader().setForeground(Color.WHITE);
        gradesTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        gradesTable.getColumnModel().getColumn(0).setPreferredWidth(30);
        gradesTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        gradesTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        gradesTable.getColumnModel().getColumn(3).setPreferredWidth(50);
        gradesTable.getColumnModel().getColumn(4).setPreferredWidth(80);
        gradesTable.getColumnModel().getColumn(5).setPreferredWidth(80);
        gradesTable.getColumnModel().getColumn(6).setPreferredWidth(200);

        // SECURITY FLAW: Allow editing grades directly in the table
        gradesTable.getModel().addTableModelListener(e -> {
            if (e.getType() == javax.swing.event.TableModelEvent.UPDATE) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                if (column == 3) {
                    updateGrade(row);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(gradesTable);

        // Barre du haut = header + studentIdPanel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(studentIdPanel, BorderLayout.SOUTH);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(blanc);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }
    
    // SECURITY FLAW: No access control, any student can view any other student's grades
    private void loadGrades(int studentId) {
        // Clear existing data
        tableModel.setRowCount(0);
        
        // Load grades for the specified student
        List<Grade> grades = gradeDAO.getGradesByStudentId(studentId);
        
        for (Grade grade : grades) {
            Object[] row = {
                grade.getId(),
                grade.getSubject().getName(),
                grade.getTitle(),
                grade.getValue(),
                grade.getCoefficient(),
                grade.getDate() != null ? DATE_FORMAT.format(grade.getDate()) : "",
                grade.getComment() // SECURITY FLAW: XSS vulnerability (no escaping of HTML)
            };
            tableModel.addRow(row);
        }
        
        // Update average
        double average = gradeDAO.calculateOverallAverageForStudent(studentId);
        averageLabel.setText("Overall Average: " + String.format("%.2f", average));
    }
    
    // SECURITY FLAW: No access control, any student can modify any grade
    private void updateGrade(int row) {
        try {
            int gradeId = (int) tableModel.getValueAt(row, 0);
            double newValue = Double.parseDouble(tableModel.getValueAt(row, 3).toString());
            
            // Get the grade from database
            Grade grade = gradeDAO.getGradeById(gradeId);
            
            if (grade != null) {
                // Update the grade value
                grade.setValue(newValue);
                
                // Save to database
                boolean success = gradeDAO.updateGrade(grade);
                
                if (success) {
                    System.out.println("Grade updated successfully: " + gradeId);
                    
                    // Refresh the average
                    int studentId = Integer.parseInt(studentIdField.getText());
                    double average = gradeDAO.calculateOverallAverageForStudent(studentId);
                    averageLabel.setText("Moyenne générale : " + String.format("%.2f", average));
                } else {
                    JOptionPane.showMessageDialog(this, 
                            "Échec de la mise à jour de la note", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                    "Note invalide. Veuillez saisir un nombre.", "Erreur", JOptionPane.ERROR_MESSAGE);
            
            // Reload grades to reset the invalid value
            int studentId = Integer.parseInt(studentIdField.getText());
            loadGrades(studentId);
        }
    }
    
    private void logout() {
        AuthenticationService.logout();
        
        // Open login screen
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
                dispose(); // Close current window
            }
        });
    }
}