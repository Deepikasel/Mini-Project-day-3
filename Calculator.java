import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener {
    private JTextField display;
    private StringBuilder currentInput;
    private double result;
    private String lastOperator;
    private boolean startNewNumber;

    public Calculator() {
        // Frame setup
        setTitle("Calculator");
        setSize(350, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Display field
        display = new JTextField("0");
        display.setFont(new Font("Arial", Font.BOLD, 28));
        display.setEditable(false);
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        add(display, BorderLayout.NORTH);

        // Initialize logic
        currentInput = new StringBuilder();
        result = 0;
        lastOperator = "=";
        startNewNumber = true;

        // Buttons layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4, 5, 5));

        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+",
            "C", "⌫"
        };

        for (String text : buttons) {
            JButton btn = new JButton(text);
            btn.setFont(new Font("Arial", Font.BOLD, 20));
            btn.addActionListener(this);
            panel.add(btn);
        }

        add(panel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if ("0123456789".contains(cmd)) { // Number
            if (startNewNumber) {
                currentInput.setLength(0); 
                startNewNumber = false;
            }
            currentInput.append(cmd);
            display.setText(currentInput.toString());

        } else if (cmd.equals(".")) { // Decimal
            if (startNewNumber) {
                currentInput.setLength(0);
                currentInput.append("0");
                startNewNumber = false;
            }
            if (!currentInput.toString().contains(".")) {
                currentInput.append(".");
                display.setText(currentInput.toString());
            }

        } else if ("+-*/".contains(cmd)) { // Operators
            calculate(getInputNumber());
            lastOperator = cmd;
            startNewNumber = true;

        } else if (cmd.equals("=")) { // Equal
            calculate(getInputNumber());
            lastOperator = "=";
            startNewNumber = true;

        } else if (cmd.equals("C")) { // Clear
            result = 0;
            currentInput.setLength(0);
            display.setText("0");
            lastOperator = "=";
            startNewNumber = true;

        } else if (cmd.equals("⌫")) { // Backspace
            if (!startNewNumber && currentInput.length() > 0) {
                currentInput.deleteCharAt(currentInput.length() - 1);
                if (currentInput.length() == 0) {
                    display.setText("0");
                    startNewNumber = true;
                } else {
                    display.setText(currentInput.toString());
                }
            }
        }
    }

    private double getInputNumber() {
        if (currentInput.length() == 0) return result;
        return Double.parseDouble(currentInput.toString());
    }

    private void calculate(double num) {
        switch (lastOperator) {
            case "=": result = num; break;
            case "+": result += num; break;
            case "-": result -= num; break;
            case "*": result *= num; break;
            case "/": 
                if (num != 0) result /= num;
                else {
                    display.setText("Error");
                    result = 0;
                    currentInput.setLength(0);
                    startNewNumber = true;
                    return;
                }
                break;
        }
        display.setText(String.valueOf(result));
        currentInput.setLength(0);
        currentInput.append(result);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Calculator().setVisible(true));
    }
}
