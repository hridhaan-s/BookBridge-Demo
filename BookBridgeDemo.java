import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/** Self-contained BookBridge presentation demo. No MySQL or external libraries required. */
public class BookBridgeDemo extends JFrame {
    private final DefaultTableModel model;
    private final List<Book> books = new ArrayList<>();

    public BookBridgeDemo() {
        setTitle("BookBridge - Demo");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        books.add(new Book("The Alchemist", "Paulo Coelho", "Available"));
        books.add(new Book("Atomic Habits", "James Clear", "Available"));
        books.add(new Book("Harry Potter", "J. K. Rowling", "Reserved"));
        books.add(new Book("Wings of Fire", "A. P. J. Abdul Kalam", "Available"));
        books.add(new Book("1984", "George Orwell", "Available"));

        JPanel header = new JPanel(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(18, 22, 18, 22));
        JLabel title = new JLabel("BookBridge");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(new Color(30, 120, 220));
        header.add(title, BorderLayout.WEST);
        JLabel subtitle = new JLabel("Book Donation & Library Management");
        subtitle.setForeground(Color.GRAY);
        header.add(subtitle, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Dashboard", dashboard());
        tabs.addTab("Browse Books", browse());
        tabs.addTab("Donate Book", donate());
        tabs.addTab("Reserve Book", reserve());
        add(tabs, BorderLayout.CENTER);

        JLabel footer = new JLabel("  Demo build • BookBridge • CBSE Class XII IT Project");
        footer.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        footer.setForeground(Color.GRAY);
        add(footer, BorderLayout.SOUTH);
    }

    private JPanel dashboard() {
        JPanel p = new JPanel(new GridLayout(2, 2, 20, 20));
        p.setBorder(BorderFactory.createEmptyBorder(35, 35, 35, 35));
        p.add(card("Total Books", String.valueOf(books.size())));
        p.add(card("Available", String.valueOf(count("Available"))));
        p.add(card("Reserved", String.valueOf(count("Reserved"))));
        p.add(card("Donations", "12"));
        return p;
    }

    private JPanel card(String label, String value) {
        JPanel p = new JPanel(new GridLayout(2, 1));
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)));
        JLabel v = new JLabel(value, SwingConstants.CENTER);
        v.setFont(new Font("Segoe UI", Font.BOLD, 30));
        v.setForeground(new Color(30, 120, 220));
        p.add(v);
        JLabel l = new JLabel(label, SwingConstants.CENTER);
        l.setForeground(Color.DARK_GRAY);
        p.add(l);
        return p;
    }

    private JPanel browse() {
        JPanel p = new JPanel(new BorderLayout(10, 10));
        p.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        model = new DefaultTableModel(new Object[]{"Title", "Author", "Status"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        refresh();
        JTable table = new JTable(model);
        table.setRowHeight(28);
        p.add(new JScrollPane(table), BorderLayout.CENTER);
        JButton refresh = new JButton("Refresh");
        refresh.addActionListener(e -> refresh());
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(refresh);
        p.add(bottom, BorderLayout.SOUTH);
        return p;
    }

    private JPanel donate() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0; c.gridy = 0;
        p.add(new JLabel("Book Title:"), c);
        JTextField title = new JTextField(22);
        c.gridx = 1; p.add(title, c);
        c.gridx = 0; c.gridy = 1;
        p.add(new JLabel("Author:"), c);
        JTextField author = new JTextField(22);
        c.gridx = 1; p.add(author, c);
        JButton donate = new JButton("Donate Book");
        c.gridx = 1; c.gridy = 2; p.add(donate, c);
        donate.addActionListener(e -> {
            String t = title.getText().trim(), a = author.getText().trim();
            if (t.isEmpty() || a.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both title and author.");
                return;
            }
            books.add(new Book(t, a, "Available"));
            title.setText(""); author.setText(""); refresh();
            JOptionPane.showMessageDialog(this, "Book donated successfully!");
        });
        return p;
    }

    private JPanel reserve() {
        JPanel p = new JPanel(new BorderLayout(10, 10));
        p.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JComboBox<String> combo = new JComboBox<>();
        fillCombo(combo);
        JButton reserve = new JButton("Reserve Selected Book");
        JPanel top = new JPanel(new BorderLayout(10, 10));
        top.add(new JLabel("Choose a book:"), BorderLayout.WEST);
        top.add(combo, BorderLayout.CENTER);
        p.add(top, BorderLayout.NORTH);
        p.add(reserve, BorderLayout.SOUTH);
        reserve.addActionListener(e -> {
            int i = combo.getSelectedIndex();
            if (i < 0) return;
            Book b = books.get(i);
            if (b.status.equals("Reserved")) {
                JOptionPane.showMessageDialog(this, "This book is already reserved.");
                return;
            }
            b.status = "Reserved"; refresh(); fillCombo(combo);
            JOptionPane.showMessageDialog(this, "Book reserved successfully!");
        });
        return p;
    }

    private void fillCombo(JComboBox<String> combo) {
        combo.removeAllItems();
        for (Book b : books) combo.addItem(b.title + " — " + b.author + " (" + b.status + ")");
    }

    private void refresh() {
        if (model == null) return;
        model.setRowCount(0);
        for (Book b : books) model.addRow(new Object[]{b.title, b.author, b.status});
    }

    private long count(String status) {
        return books.stream().filter(b -> b.status.equals(status)).count();
    }

    private static class Book {
        String title, author, status;
        Book(String title, String author, String status) {
            this.title = title; this.author = author; this.status = status;
        }
    }

    private static void login() {
        JFrame frame = new JFrame("BookBridge - Login");
        frame.setSize(420, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8); c.fill = GridBagConstraints.HORIZONTAL;
        JLabel title = new JLabel("BookBridge Login", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(30, 120, 220));
        c.gridx = 0; c.gridy = 0; c.gridwidth = 2; p.add(title, c);
        c.gridwidth = 1; c.gridy = 1; c.gridx = 0; p.add(new JLabel("Username:"), c);
        JTextField user = new JTextField("admin", 18); c.gridx = 1; p.add(user, c);
        c.gridy = 2; c.gridx = 0; p.add(new JLabel("Password:"), c);
        JPasswordField pass = new JPasswordField("admin123", 18); c.gridx = 1; p.add(pass, c);
        JButton button = new JButton("Login"); c.gridx = 0; c.gridy = 3; c.gridwidth = 2; p.add(button, c);
        frame.add(p, BorderLayout.CENTER);
        JLabel hint = new JLabel("Demo: admin / admin123", SwingConstants.CENTER);
        hint.setForeground(Color.GRAY); frame.add(hint, BorderLayout.SOUTH);
        button.addActionListener(e -> {
            if (user.getText().trim().equals("admin") && new String(pass.getPassword()).equals("admin123")) {
                frame.dispose(); new BookBridgeDemo().setVisible(true);
            } else JOptionPane.showMessageDialog(frame, "Invalid demo credentials.");
        });
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BookBridgeDemo::login);
    }
}
