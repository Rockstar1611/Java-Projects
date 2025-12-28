/* username=shiva,password=1234 use this.! */
package pog;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class OnlineBankingSystem extends JFrame {

    private final CardLayout cardLayout;
    private final JPanel mainPanel;

    private JTextField usernameField;
    private JPasswordField passwordField;

    private JLabel welcomeLabel;
    private JLabel balanceLabel;

    private double balance = 5000.0;
    private final ArrayList<String> transactions = new ArrayList<>();

    public OnlineBankingSystem() {
        setTitle("Online Banking System");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createLoginPanel(), "Login");
        mainPanel.add(createDashboardPanel(), "Dashboard");

        add(mainPanel);
        cardLayout.show(mainPanel, "Login");

        setVisible(true);
    }

    // 🔐 Login Panel with background and centered box
    private JPanel createLoginPanel() {
        JPanel panel = new BackgroundPanel("background.jpg");
        panel.setLayout(null);

        JPanel loginBox = new JPanel();
        loginBox.setLayout(null);
        loginBox.setBounds(150, 100, 300, 220);
        loginBox.setBackground(new Color(255, 255, 255, 200)); // semi-transparent white
        loginBox.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        panel.add(loginBox);

        JLabel title = new JLabel("Bank Login", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setBounds(80, 10, 140, 30);
        loginBox.add(title);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(30, 60, 80, 25);
        loginBox.add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(120, 60, 140, 25);
        loginBox.add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(30, 100, 80, 25);
        loginBox.add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(120, 100, 140, 25);
        loginBox.add(passwordField);

        JButton loginBtn = new JButton("BANKING");
        loginBtn.setBounds(90, 150, 120, 30);
        loginBtn.setBackground(new Color(30, 136, 229));
        loginBtn.setForeground(Color.WHITE);

        loginBtn.addActionListener(e -> {
            String user = usernameField.getText().trim();
            String pass = new String(passwordField.getPassword());
            if (user.equals("shiva") && pass.equals("1234")) {
                welcomeLabel.setText("Welcome, " + user + "!");
                updateBalanceLabel();
                cardLayout.show(mainPanel, "Dashboard");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password");
            }
        });

        loginBox.add(loginBtn);

        return panel;
    }

    // 🏦 Dashboard Panel
    private JPanel createDashboardPanel() {
        JPanel panel = new BackgroundPanel("background.jpg");
        panel.setLayout(null);

        welcomeLabel = new JLabel("Welcome!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setBounds(150, 20, 250, 30);
        panel.add(welcomeLabel);

        balanceLabel = new JLabel();
        balanceLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        balanceLabel.setBounds(180, 60, 200, 25);
        updateBalanceLabel();
        panel.add(balanceLabel);

        JButton depositBtn = new JButton("Deposit");
        depositBtn.setBounds(100, 110, 120, 30);
        depositBtn.addActionListener(e -> depositMoney());
        panel.add(depositBtn);

        JButton withdrawBtn = new JButton("Withdraw");
        withdrawBtn.setBounds(280, 110, 120, 30);
        withdrawBtn.addActionListener(e -> withdrawMoney());
        panel.add(withdrawBtn);

        JButton historyBtn = new JButton("Transaction History");
        historyBtn.setBounds(160, 160, 200, 30);
        historyBtn.addActionListener(e -> showTransactionHistory());
        panel.add(historyBtn);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(200, 220, 120, 30);
        logoutBtn.setBackground(Color.LIGHT_GRAY);
        logoutBtn.addActionListener(e -> {
            usernameField.setText("");
            passwordField.setText("");
            cardLayout.show(mainPanel, "Login");
        });
        panel.add(logoutBtn);

        return panel;
    }

    private void updateBalanceLabel() {
        balanceLabel.setText("Balance: ₹" + String.format("%.2f", balance));
    }

    private void depositMoney() {
        String input = JOptionPane.showInputDialog(this, "Enter deposit amount:");
        if (input == null) return;

        try {
            double amount = Double.parseDouble(input.trim());
            if (amount > 0) {
                balance += amount;
                transactions.add("Deposited ₹" + String.format("%.2f", amount));
                updateBalanceLabel();
                JOptionPane.showMessageDialog(this, "₹" + String.format("%.2f", amount) + " deposited successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Enter a valid positive amount.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input.");
        }
    }

    private void withdrawMoney() {
        String input = JOptionPane.showInputDialog(this, "Enter withdrawal amount:");
        if (input == null) return;

        try {
            double amount = Double.parseDouble(input.trim());
            if (amount > 0 && amount <= balance) {
                balance -= amount;
                transactions.add("Withdrew ₹" + String.format("%.2f", amount));
                updateBalanceLabel();
                JOptionPane.showMessageDialog(this, "₹" + String.format("%.2f", amount) + " withdrawn successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid amount or insufficient balance.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input.");
        }
    }

    private void showTransactionHistory() {
        if (transactions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No transactions yet.");
            return;
        }

        JTextArea area = new JTextArea();
        for (String t : transactions) {
            area.append(t + "\n");
        }
        area.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setPreferredSize(new Dimension(300, 200));

        JOptionPane.showMessageDialog(this, scrollPane, "Transaction History", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(OnlineBankingSystem::new);
    }
}

// 🌄 Background Panel Class
class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        try {
            backgroundImage = new ImageIcon(imagePath).getImage();
        } catch (Exception e) {
            System.out.println("Image not found: " + e.getMessage());
        }
        setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}