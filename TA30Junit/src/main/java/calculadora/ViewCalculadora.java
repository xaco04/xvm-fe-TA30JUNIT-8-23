package calculadora;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewCalculadora extends JFrame {
    private JTextField textField;
    private double currentInput = 0;
    private String lastOperator = "";
    private boolean newInput = true;

    public ViewCalculadora() {
        setTitle("Calculadora");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 20));
        textField.setHorizontalAlignment(JTextField.RIGHT);
        textField.setEditable(false);
        panel.add(textField, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(5, 4, 5, 5));

        String[] buttonLabels = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.addActionListener(new ButtonClickListener());
            buttonsPanel.add(button);
        }

        panel.add(buttonsPanel, BorderLayout.CENTER);
        add(panel);
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if ("0123456789.".contains(command)) {
                if (newInput) {
                    textField.setText("");
                    newInput = false;
                }
                textField.setText(textField.getText() + command);
            } else if ("+-*/".contains(command)) {
                try {
                    if (!lastOperator.isEmpty()) {
                        performOperation();
                    }
                    currentInput = Double.parseDouble(textField.getText());
                    lastOperator = command;
                    newInput = true;
                } catch (NumberFormatException ex) {
                    textField.setText("Error");
                }
            } else if ("=".equals(command)) {
                performOperation();
                lastOperator = "";
                newInput = true;
            }
        }

        private void performOperation() {
            try {
                double num = Double.parseDouble(textField.getText());
                Calculadora calculator = new Calculadora();

                switch (lastOperator) {
                    case "+":
                        currentInput = calculator.add(currentInput, num);
                        break;
                    case "-":
                        currentInput = calculator.subtract(currentInput, num);
                        break;
                    case "*":
                        currentInput = calculator.multiply(currentInput, num);
                        break;
                    case "/":
                        currentInput = calculator.divide(currentInput, num);
                        break;
                }

                textField.setText(String.valueOf(currentInput));
            } catch (NumberFormatException | ArithmeticException ex) {
                textField.setText("Error");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ViewCalculadora().setVisible(true));
    }
}
