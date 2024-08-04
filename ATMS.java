import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ATMS extends JFrame implements ActionListener {
    private double balance = 1000.00;
    private String pin = "1234";
    private ArrayList<String> transactionHistory = new ArrayList<>();

    private JTextField amountField;
    private JTextField pinField;
    private JTextArea transactionArea;
    private JLabel balanceLabel;

    public ATMS() {
        setTitle("ATM Machine");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main Panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1));

        // Balance Enquiry
        balanceLabel = new JLabel("Balance: $" + balance);
        panel.add(balanceLabel);

        // Cash Withdraw
        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(this);
        panel.add(withdrawButton);

        // Cash Deposit
        JButton depositButton = new JButton("Deposit");
        depositButton.addActionListener(this);
        panel.add(depositButton);

        // PIN Change
        JButton pinChangeButton = new JButton("Change PIN");
        pinChangeButton.addActionListener(this);
        panel.add(pinChangeButton);

        // Transaction History
        JButton historyButton = new JButton("Transaction History");
        historyButton.addActionListener(this);
        panel.add(historyButton);

        // Amount Field
        amountField = new JTextField();
        amountField.setBorder(BorderFactory.createTitledBorder("Amount"));
        panel.add(amountField);

        // PIN Field
        pinField = new JTextField();
        pinField.setBorder(BorderFactory.createTitledBorder("PIN"));
        panel.add(pinField);

        add(panel, BorderLayout.CENTER);

        // Transaction History Area
        transactionArea = new JTextArea();
        transactionArea.setEditable(false);
        add(new JScrollPane(transactionArea), BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action) {
            case "Withdraw":
                handleWithdraw();
                break;
            case "Deposit":
                handleDeposit();
                break;
            case "Change PIN":
                handleChangePIN();
                break;
            case "Transaction History":
                handleTransactionHistory();
                break;
        }
    }

    private void handleWithdraw() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (amount <= 0 || amount > balance) {
                JOptionPane.showMessageDialog(this, "Invalid amount or insufficient balance.");
            } else {
                balance -= amount;
                transactionHistory.add("Withdrew: $" + amount);
                updateBalance();
                amountField.setText("");
                JOptionPane.showMessageDialog(this, "Withdrawal successful.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount.");
        }
    }

    private void handleDeposit() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Invalid amount.");
            } else {
                balance += amount;
                transactionHistory.add("Deposited: $" + amount);
                updateBalance();
                amountField.setText("");
                JOptionPane.showMessageDialog(this, "Deposit successful.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount.");
        }
    }

    private void handleChangePIN() {
        String newPIN = pinField.getText();
        if (newPIN.length() != 4 || !newPIN.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "PIN must be 4 digits.");
        } else {
            pin = newPIN;
            transactionHistory.add("PIN changed.");
            pinField.setText("");
            JOptionPane.showMessageDialog(this, "PIN change successful.");
        }
    }

    private void handleTransactionHistory() {
        StringBuilder history = new StringBuilder();
        for (String transaction : transactionHistory) {
            history.append(transaction).append("\n");
        }
        transactionArea.setText(history.toString());
    }

    private void updateBalance() {
        balanceLabel.setText("Balance: $" + balance);
    }

    public static void main(String[] args) {
        new ATMS();
    }
}
