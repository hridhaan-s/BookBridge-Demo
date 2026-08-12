import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BookBridgeDemo {
    private static final List<Book> BOOKS = new ArrayList<>();

    static {
        BOOKS.add(new Book(1, "The Alchemist", "Paulo Coelho", "Literature", "Class 12", "Good", "Aarav", "Available"));
        BOOKS.add(new Book(2, "Wings of Fire", "A. P. J. Abdul Kalam", "Biography", "Class 11", "Like New", "Riya", "Available"));
        BOOKS.add(new Book(3, "Atomic Habits", "James Clear", "Self Help", "Class 12", "New", "Kabir", "Reserved"));
        BOOKS.add(new Book(4, "1984", "George Orwell", "Literature", "Class 10", "Good", "Ananya", "Available"));
    }

    static class Book {
        int id;
        String title, author, subject, className, condition, donor, status;
        Book(int id, String title, String author, String subject, String className, String condition, String donor, String status) {
            this.id = id; this.title = title; this.author = author; this.subject = subject;
            this.className = className; this.condition = condition; this.donor = donor; this.status = status;
        }
    }

    static int nextId() {
        int max = 0;
        for (Book b : BOOKS) max = Math.max(max, b.id);
        return max + 1;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }

    static class LoginFrame extends JFrame {
        private JTextField txtUsername;
        private JPasswordField txtPassword;
        private JButton btnLogin;

        LoginFrame() {
            setTitle("BookBridge - Login");
            setSize(400, 300);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setResizable(false);

            JPanel mainPanel = new JPanel();
            mainPanel.setBackground(Color.WHITE);
            mainPanel.setLayout(null);
            add(mainPanel);

            JLabel lblHeader = new JLabel("BookBridge Login", SwingConstants.CENTER);
            lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 22));
            lblHeader.setForeground(new Color(30, 144, 255));
            lblHeader.setBounds(20, 20, 360, 40);
            mainPanel.add(lblHeader);

            JLabel lblUsername = new JLabel("Username:");
            lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            lblUsername.setBounds(50, 90, 80, 25);
            mainPanel.add(lblUsername);

            txtUsername = new JTextField();
            txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            txtUsername.setBounds(140, 90, 200, 25);
            mainPanel.add(txtUsername);

            JLabel lblPassword = new JLabel("Password:");
            lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            lblPassword.setBounds(50, 130, 80, 25);
            mainPanel.add(lblPassword);

            txtPassword = new JPasswordField();
            txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            txtPassword.setBounds(140, 130, 200, 25);
            mainPanel.add(txtPassword);

            btnLogin = new JButton("Login");
            btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btnLogin.setBackground(new Color(30, 144, 255));
            btnLogin.setForeground(Color.WHITE);
            btnLogin.setFocusPainted(false);
            btnLogin.setBounds(50, 190, 290, 35);
            mainPanel.add(btnLogin);

            btnLogin.addActionListener(e -> handleLogin());
        }

        private void handleLogin() {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword()).trim();
            if (username.equals("admin") && password.equals("admin123")) {
                new DashboardFrame().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials! Try admin / admin123", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    static class DashboardFrame extends JFrame {
        private JLabel lblTotalBooks;

        DashboardFrame() {
            setTitle("BookBridge - Dashboard");
            setSize(450, 350);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setResizable(false);

            JPanel mainPanel = new JPanel();
            mainPanel.setBackground(Color.WHITE);
            mainPanel.setLayout(null);
            add(mainPanel);

            JLabel lblWelcome = new JLabel("Welcome back, Admin!", SwingConstants.CENTER);
            lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 20));
            lblWelcome.setForeground(new Color(30, 144, 255));
            lblWelcome.setBounds(20, 20, 410, 30);
            mainPanel.add(lblWelcome);

            lblTotalBooks = new JLabel("Total Books Available: Loading...");
            lblTotalBooks.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            lblTotalBooks.setHorizontalAlignment(SwingConstants.CENTER);
            lblTotalBooks.setBounds(20, 70, 410, 30);
            mainPanel.add(lblTotalBooks);

            JButton btnDonate = createBlueButton("Donate Book", 120);
            JButton btnBrowse = createBlueButton("Browse Books", 170);
            JButton btnExit = createBlueButton("Exit Application", 220);
            mainPanel.add(btnDonate); mainPanel.add(btnBrowse); mainPanel.add(btnExit);

            btnDonate.addActionListener(e -> openBookManagement());
            btnBrowse.addActionListener(e -> openBookManagement());
            btnExit.addActionListener(e -> System.exit(0));
            updateBookCount();
        }

        private JButton createBlueButton(String text, int yPos) {
            JButton btn = new JButton(text);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btn.setBackground(new Color(30, 144, 255));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setBounds(100, yPos, 250, 35);
            return btn;
        }

        private void openBookManagement() {
            new BookManagementFrame().setVisible(true);
            dispose();
        }

        private void updateBookCount() {
            lblTotalBooks.setText("Total Books Registered: " + BOOKS.size());
        }
    }

    static class BookManagementFrame extends JFrame {
        private JTextField txtTitle, txtAuthor, txtSubject, txtDonor;
        private JComboBox<String> cbClass, cbCondition;
        private JTable bookTable;
        private DefaultTableModel tableModel;

        BookManagementFrame() {
            setTitle("BookBridge - Book Management");
            setSize(850, 600);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
            mainPanel.setBackground(Color.WHITE);
            mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            add(mainPanel);

            JPanel formPanel = new JPanel(null);
            formPanel.setBackground(Color.WHITE);
            formPanel.setPreferredSize(new Dimension(300, 500));
            formPanel.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(new Color(30, 144, 255)),
                    "Donate a Book", 0, 0, new Font("Segoe UI", Font.BOLD, 14), new Color(30, 144, 255)));

            String[] labels = {"Book Title:", "Author:", "Subject:", "Class:", "Condition:", "Donor Name:"};
            int yOffset = 35;
            for (int i = 0; i < labels.length; i++) {
                JLabel lbl = new JLabel(labels[i]);
                lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                lbl.setBounds(15, yOffset + (i * 50), 90, 25);
                formPanel.add(lbl);
            }

            txtTitle = new JTextField(); txtTitle.setBounds(110, 35, 170, 25); formPanel.add(txtTitle);
            txtAuthor = new JTextField(); txtAuthor.setBounds(110, 85, 170, 25); formPanel.add(txtAuthor);
            txtSubject = new JTextField(); txtSubject.setBounds(110, 135, 170, 25); formPanel.add(txtSubject);
            cbClass = new JComboBox<>(new String[]{"Class 9", "Class 10", "Class 11", "Class 12"});
            cbClass.setBounds(110, 185, 170, 25); cbClass.setBackground(Color.WHITE); formPanel.add(cbClass);
            cbCondition = new JComboBox<>(new String[]{"New", "Like New", "Good", "Fair"});
            cbCondition.setBounds(110, 235, 170, 25); cbCondition.setBackground(Color.WHITE); formPanel.add(cbCondition);
            txtDonor = new JTextField(); txtDonor.setBounds(110, 285, 170, 25); formPanel.add(txtDonor);

            JButton btnDonate = new JButton("Donate");
            styleButton(btnDonate); btnDonate.setBounds(15, 345, 265, 35); formPanel.add(btnDonate);
            JButton btnBack = new JButton("Back to Dashboard");
            styleButton(btnBack); btnBack.setBackground(Color.GRAY); btnBack.setBounds(15, 395, 265, 35); formPanel.add(btnBack);
            mainPanel.add(formPanel, BorderLayout.WEST);

            JPanel rightPanel = new JPanel(new BorderLayout(10, 10));
            rightPanel.setBackground(Color.WHITE);
            String[] columns = {"ID", "Title", "Author", "Subject", "Class", "Condition", "Donor", "Status"};
            tableModel = new DefaultTableModel(columns, 0) {
                @Override public boolean isCellEditable(int row, int column) { return false; }
            };
            bookTable = new JTable(tableModel);
            bookTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            bookTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
            bookTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            rightPanel.add(new JScrollPane(bookTable), BorderLayout.CENTER);

            JPanel actionButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
            actionButtonPanel.setBackground(Color.WHITE);
            JButton btnRefresh = new JButton("Refresh Table"); styleButton(btnRefresh);
            JButton btnRequest = new JButton("Request Selected Book"); styleButton(btnRequest);
            actionButtonPanel.add(btnRefresh); actionButtonPanel.add(btnRequest);
            rightPanel.add(actionButtonPanel, BorderLayout.SOUTH);
            mainPanel.add(rightPanel, BorderLayout.CENTER);

            btnDonate.addActionListener(e -> insertBook());
            btnRefresh.addActionListener(e -> loadBookData());
            btnRequest.addActionListener(e -> requestBook());
            btnBack.addActionListener(e -> { new DashboardFrame().setVisible(true); dispose(); });
            loadBookData();
        }

        private void styleButton(JButton btn) {
            btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
            btn.setBackground(new Color(30, 144, 255));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
        }

        private void loadBookData() {
            tableModel.setRowCount(0);
            for (Book b : BOOKS) {
                tableModel.addRow(new Object[]{b.id, b.title, b.author, b.subject, b.className, b.condition, b.donor, b.status});
            }
        }

        private void insertBook() {
            if (txtTitle.getText().isBlank() || txtAuthor.getText().isBlank() || txtDonor.getText().isBlank()) {
                JOptionPane.showMessageDialog(this, "Please completely fill Title, Author, and Donor Name fields.");
                return;
            }
            BOOKS.add(new Book(nextId(), txtTitle.getText().trim(), txtAuthor.getText().trim(), txtSubject.getText().trim(),
                    cbClass.getSelectedItem().toString(), cbCondition.getSelectedItem().toString(), txtDonor.getText().trim(), "Available"));
            JOptionPane.showMessageDialog(this, "Thank you for donating!");
            txtTitle.setText(""); txtAuthor.setText(""); txtSubject.setText(""); txtDonor.setText("");
            loadBookData();
        }

        private void requestBook() {
            int selectedRow = bookTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a book from the table list first.");
                return;
            }
            int bookId = (int) tableModel.getValueAt(selectedRow, 0);
            String status = (String) tableModel.getValueAt(selectedRow, 7);
            if ("Reserved".equalsIgnoreCase(status)) {
                JOptionPane.showMessageDialog(this, "This book is already reserved by another student.");
                return;
            }
            for (Book b : BOOKS) if (b.id == bookId) { b.status = "Reserved"; break; }
            JOptionPane.showMessageDialog(this, "Book successfully reserved!");
            loadBookData();
        }
    }
}
