package calculatorsmirnovilya;

import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Panel extends JPanel {

    // Параметры кнопок
    private int buttonWidth = 100; // Ширина
    private int buttonHeight = 50; // Высота
    private int buttonPadding = 10; // Отступ
    private int buttonRowCount = 8; // Количество рядов (в т. ч. и output)
    private int buttonColumnCount = 4;  // Количество столбцов

    private Font font = new Font("SanSerif", Font.BOLD, 25);

    private JTextField output = new JTextField(); // Окно вывода
    private JTextField message = new JTextField(); // Окно вывода сообщений об ошибках

    public static final String CANCEL_TEXT = "c";
    public static final String BACKSPACE_TEXT = "<-";
    public static final String DIV_TEXT = "/";
    public static final String MULTI_TEXT = "*";
    public static final String SUB_TEXT = "-";
    public static final String SUM_TEXT = "+";
    public static final String MINUS_TEXT = "+-";
    public static final String POINT_TEXT = ".";
    public static final String BRACKET_LEFT_TEXT = "(";
    public static final String BRACKET_RIGHT_TEXT = ")";
    public static final String EQU_TEXT = "=";

    public static final String ERROR_MESS = "Некорректное выражение";
    // public static final String ERROR_BRACKET = "Скобки не согласованы";

    private JButton numbers[] = new JButton[10];
    private JButton cancel = new JButton(CANCEL_TEXT);
    private JButton backspace = new JButton(BACKSPACE_TEXT);
    private JButton div = new JButton(DIV_TEXT);
    private JButton multi = new JButton(MULTI_TEXT);
    private JButton sub = new JButton(SUB_TEXT);
    private JButton sum = new JButton(SUM_TEXT);
    private JButton minus = new JButton(MINUS_TEXT);
    private JButton point = new JButton(POINT_TEXT);
    private JButton bracketLeft = new JButton(BRACKET_LEFT_TEXT);
    private JButton bracketRight = new JButton(BRACKET_RIGHT_TEXT);
    private JButton equ = new JButton(EQU_TEXT);

    int bracketLeftCount = 0; // Количество открытых скобок

    // Паттерны для проверки ввода
    private String pattern1 = "(\\S+)?\\d+";
    private String pattern2 = "(\\S+)?[(][-]([0-9]+[.])?[0-9]+[)]";
    private String pattern3 = "[(][-]([0-9]+[.])?[0-9]+[)]";
    private String pattern4 = "([0-9]+[.])?[0-9]+";
    private String pattern5 = "(\\S+)?([0-9]+[.])?[0-9]+";
    private String pattern6 = "([0-9]+[.])?[0-9]+";
    private String pattern8 = "(\\S+)?[A-Za-zА-Яа-я]+(\\S+)?";
    private String pattern7 = "([(][0-9]+)|([-][0-9]+)" +
            "|((\\S+[/])?[0-9]+)|((\\S+[*])?[0-9]+)" +
            "|((\\S+[-])?[0-9]+)|((\\S+[+])?[0-9]+)" +
            "|((\\S+[(])?[0-9]+)";

    public Panel() {
        setLayout(null);
        ActionListener l = (ActionEvent e) -> {
            JButton button = (JButton) e.getSource();
            String buttonText = button.getText();
            String outputText = output.getText();
            message.setText("");
            if (buttonText.equals(CANCEL_TEXT)) {
                output.setText("");
            } else if (buttonText.equals(BACKSPACE_TEXT)) {
                if (outputText.length() > 0) {
                    if (outputText.endsWith(BRACKET_LEFT_TEXT)) {
                        bracketLeftCount--;
                    }
                    if (outputText.endsWith(BRACKET_RIGHT_TEXT)) {
                        bracketLeftCount++;
                    }
                    output.setText(outputText.substring(0, outputText.length() - 1));
                }
            }
            else if (!outputText.matches(pattern8)) {
                if (buttonText.equals(DIV_TEXT)) {
                    if (outputText.endsWith(BRACKET_RIGHT_TEXT)
                            || outputText.matches(pattern1)) {
                        output.setText(outputText + DIV_TEXT);
                    }
                } else if (buttonText.equals(MULTI_TEXT)) {
                    if (outputText.endsWith(BRACKET_RIGHT_TEXT)
                            || outputText.matches(pattern1)) {
                        output.setText(outputText + MULTI_TEXT);
                    }
                } else if (buttonText.equals(SUB_TEXT)) {
                    if (outputText.endsWith(BRACKET_RIGHT_TEXT)
                            || outputText.matches(pattern1)) {
                        output.setText(outputText + SUB_TEXT);
                    }
                }  else if (buttonText.equals(SUM_TEXT)) {
                    if (outputText.endsWith(BRACKET_RIGHT_TEXT)
                            || outputText.matches(pattern1)) {
                        output.setText(outputText + SUM_TEXT);
                    }
                } else if (buttonText.equals(MINUS_TEXT)) {
                    if (outputText.matches(pattern5)) {
                        String line = outputText;
                        Pattern p1 = Pattern.compile(pattern6);
                        Matcher m1 = p1.matcher(line);
                        String replace1 = "";
                        int replace1Start = 0;
                        while (m1.find()) {
                            replace1 = line.substring(m1.start(), m1.end());
                            replace1Start = m1.start();
                        }
                        line = line.substring(0, replace1Start) + "(-" + replace1 + ")";
                        output.setText(line);
                    } else if (outputText.matches(pattern2)) {
                        String line = outputText;
                        Pattern p1 = Pattern.compile(pattern3);
                        Pattern p2 = Pattern.compile(pattern4);
                        Matcher m1 = p1.matcher(line);
                        String replace1 = "";
                        String replace2 = "";
                        int replace1Start = 0;
                        while (m1.find()) {
                            replace1 = line.substring(m1.start(), m1.end());
                            replace1Start = m1.start();
                        }
                        Matcher m2 = p2.matcher(replace1);
                        while (m2.find()) {
                            replace2 = replace1.substring(m2.start(), m2.end());
                        }
                        line = line.substring(0, replace1Start) + replace2;
                        output.setText(line);
                    }
                } else if (buttonText.equals(POINT_TEXT)) {
                    if (outputText.matches(pattern7)){
                        output.setText(outputText + POINT_TEXT);
                    }
                } else if (buttonText.equals(BRACKET_LEFT_TEXT)) {
                    if (outputText.length() == 0
                            || outputText.endsWith(DIV_TEXT)
                            || outputText.endsWith(MULTI_TEXT)
                            || outputText.endsWith(SUB_TEXT)
                            || outputText.endsWith(SUM_TEXT)
                            || outputText.endsWith(BRACKET_LEFT_TEXT)) {
                        output.setText(output.getText() + BRACKET_LEFT_TEXT);
                        bracketLeftCount++;
                    }
                } else if (buttonText.equals(BRACKET_RIGHT_TEXT)) {
                    if (bracketLeftCount > 0 && outputText.matches(pattern1)
                            || bracketLeftCount > 0 && outputText.endsWith(BRACKET_RIGHT_TEXT)) {
                        output.setText(outputText + BRACKET_RIGHT_TEXT);
                        bracketLeftCount--;
                    }
                } else if (buttonText.equals(EQU_TEXT)) {
                    if (!outputText.isEmpty()) {
                        String result = Calculator.calculate(outputText);
                        if (result.equals(Panel.ERROR_MESS)) {
                            message.setText(Panel.ERROR_MESS);
                        } else {
                            output.setText(result);
                        }
                    }
                } else  {
                    if (outputText.length() == 0
                            || !outputText.endsWith(BRACKET_RIGHT_TEXT)) {
                        output.setText(output.getText() + buttonText);
                    }
                }
            }
        };

        // Размещение кнопок с цифрами
        numbers[0] = new JButton("0");
        numbers[0].setBounds(
                buttonPadding + (buttonWidth + buttonPadding),
                buttonPadding + 6 * (buttonHeight + buttonPadding),
                buttonWidth,
                buttonHeight);
        numbers[0].setFont(font);
        add(numbers[0]);

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                int buttonNumber = y * 3 + x + 1;
                String buttonNumberText = String.valueOf(buttonNumber);
                numbers[buttonNumber] = new JButton(buttonNumberText);
                numbers[buttonNumber].setBounds(buttonPadding + x * (buttonWidth + buttonPadding),
                        buttonPadding + (5 - y) * (buttonHeight + buttonPadding), buttonWidth, buttonHeight);
                numbers[buttonNumber].setFont(font);
                add(numbers[buttonNumber]);
            }
        }

        for (JButton b : numbers) {
            b.addActionListener(l);
        }

        backspace.setBounds(
                buttonPadding + 2 * (buttonWidth + buttonPadding),
                buttonPadding + 2 * (buttonHeight + buttonPadding),
                buttonWidth * 2 + buttonPadding,
                buttonHeight);
        backspace.setFont(font);
        add(backspace);
        backspace.addActionListener(l);

        cancel.setBounds(
                buttonPadding,
                buttonPadding + 2 * (buttonHeight + buttonPadding),
                buttonWidth * 2 + buttonPadding,
                buttonHeight);
        cancel.setFont(font);
        add(cancel);
        cancel.addActionListener(l);

        div.setBounds(
                buttonPadding + 3 * (buttonWidth + buttonPadding),
                buttonPadding + 3 * (buttonHeight + buttonPadding),
                buttonWidth,
                buttonHeight);
        div.setFont(font);
        add(div);
        div.addActionListener(l);

        multi.setBounds(
                buttonPadding + 3 * (buttonWidth + buttonPadding),
                buttonPadding + 4 * (buttonHeight + buttonPadding),
                buttonWidth,
                buttonHeight);
        multi.setFont(font);
        add(multi);
        multi.addActionListener(l);

        sub.setBounds(
                buttonPadding + 3 * (buttonWidth + buttonPadding),
                buttonPadding + 5 * (buttonHeight + buttonPadding),
                buttonWidth,
                buttonHeight);
        sub.setFont(font);
        add(sub);
        sub.addActionListener(l);

        sum.setBounds(
                buttonPadding + 3 * (buttonWidth + buttonPadding),
                buttonPadding + 6 * (buttonHeight + buttonPadding),
                buttonWidth,
                buttonHeight);
        sum.setFont(font);
        add(sum);
        sum.addActionListener(l);

        minus.setBounds(
                buttonPadding,
                buttonPadding + 6 * (buttonHeight + buttonPadding),
                buttonWidth,
                buttonHeight);
        minus.setFont(font);
        add(minus);
        minus.addActionListener(l);

        point.setBounds(
                buttonPadding + 2 * (buttonWidth + buttonPadding),
                buttonPadding + 6 * (buttonHeight + buttonPadding),
                buttonWidth,
                buttonHeight);
        point.setFont(font);
        add(point);
        point.addActionListener(l);

        bracketLeft.setBounds(
                buttonPadding,
                buttonPadding + 7 * (buttonHeight + buttonPadding),
                buttonWidth,
                buttonHeight);
        bracketLeft.setFont(font);
        add(bracketLeft);
        bracketLeft.addActionListener(l);

        bracketRight.setBounds(
                buttonPadding + (buttonWidth + buttonPadding),
                buttonPadding + 7 * (buttonHeight + buttonPadding),
                buttonWidth,
                buttonHeight);
        bracketRight.setFont(font);
        add(bracketRight);
        bracketRight.addActionListener(l);

        equ.setBounds(
                buttonPadding + 2 * (buttonWidth + buttonPadding),
                buttonPadding + 7 * (buttonHeight + buttonPadding),
                buttonWidth * 2 + buttonPadding,
                buttonHeight);
        equ.setFont(font);
        add(equ);
        equ.addActionListener(l);

        // Окно вывода
        int outputWidth = buttonWidth * buttonColumnCount + buttonPadding * (buttonColumnCount - 1);
        output.setBounds(buttonPadding, buttonPadding, outputWidth, buttonHeight);
        output.setFont(font);
        output.setEditable(false);
        add(output);

        // Окно вывода сообщений об ошибках
        int messageWidth = buttonWidth * buttonColumnCount + buttonPadding * (buttonColumnCount - 1);
        message.setBounds(buttonPadding,buttonPadding + (buttonHeight + buttonPadding), messageWidth, buttonHeight);
        message.setFont(font);
        message.setEditable(false);
        add(message);
    }

    public int getButtonWidth() {
        return buttonWidth;
    }

    public int getButtonHeight() {
        return buttonHeight;
    }

    public int getButtonPadding() {
        return buttonPadding;
    }

    public int getButtonRowCount() {
        return buttonRowCount;
    }

    public int getButtonColumnCount() {
        return buttonColumnCount;
    }
}
