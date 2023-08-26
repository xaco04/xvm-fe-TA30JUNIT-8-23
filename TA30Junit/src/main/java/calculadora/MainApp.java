package calculadora;

import javax.swing.SwingUtilities;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ViewCalculadora calculatorView = new ViewCalculadora();
            calculatorView.setVisible(true);
        });
    }
}
