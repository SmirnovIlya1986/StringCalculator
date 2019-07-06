package calculatorsmirnovilya;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Start {

    private JFrame window;

    public Start() {
        window = new JFrame("Калькулятор");
        Panel panel = new Panel();
        
        // Параметры кнопок
        int buttonWidth = panel.getButtonWidth(); // Ширина
        int buttonHeight = panel.getButtonHeight(); // Высота
        int buttonPadding = panel.getButtonPadding(); // Отступ
        int buttonRowCount = panel.getButtonRowCount(); // Количество рядов
        int buttonColumnCount = panel.getButtonColumnCount(); // Количество столбцов

        int windowWidth = buttonPadding + (buttonWidth + buttonPadding) * buttonColumnCount + 6; // Высота окна
        int windowHeight = buttonPadding + (buttonHeight + buttonPadding) * buttonRowCount + 27; // Ширина окна

        window.setSize(windowWidth, windowHeight);
        window.add(panel);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Start();
            }
        });
    }
}
