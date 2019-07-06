package calculatorsmirnovilya;

import java.util.*;

public class ExpressionParser {

    private static String operators = String.format("%s%s%s%s",Panel.SUM_TEXT, Panel.SUB_TEXT, Panel.MULTI_TEXT, Panel.DIV_TEXT);
    private static String delimiters = String.format("%s%s %s", operators, Panel.BRACKET_LEFT_TEXT, Panel.BRACKET_RIGHT_TEXT);

    public static boolean flag = true;

    private static boolean isDelimiter(String token) {
        if (token.length() != 1) {
            return false;
        }
        for (int i = 0; i < delimiters.length(); i++) {
            if (token.charAt(0) == delimiters.charAt(i)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isOperator(String token) {
        if (token.equals("u-")) {
            return true;
        }
        for (int i = 0; i < operators.length(); i++) {
            if (token.charAt(0) == operators.charAt(i)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isFunction(String token) {
        if (token.equals("sqrt")
                || token.equals("cube")
                || token.equals("pow10")) {
            return true;
        }
        return false;
    }

    private static int priority(String token) {
        if (token.equals(Panel.BRACKET_LEFT_TEXT)) {
            return 1;
        }
        if (token.equals(Panel.SUM_TEXT) || token.equals(Panel.SUB_TEXT)) {
            return 2;
        }
        if (token.equals(Panel.MULTI_TEXT) || token.equals(Panel.DIV_TEXT)) {
            return 3;
        }
        return 4;
    }

    public static List<String> parse(String infix) {
        List<String> postfix = new ArrayList<>();
        Deque<String> stack = new ArrayDeque<>();
        StringTokenizer tokenizer = new StringTokenizer(infix, delimiters, true);
        String prev = "";
        String curr = "";
        while (tokenizer.hasMoreTokens()) {
            curr = tokenizer.nextToken();
            if (!tokenizer.hasMoreTokens() && isOperator(curr)) {
                // System.out.println(Panel.ERROR_MESS);
                flag = false;
                return postfix;
            }
            if (curr.equals(" ")) {
                continue;
            }
            if (isFunction(curr)) {
                stack.push(curr);
            } else if (isDelimiter(curr)) {
                if (curr.equals(Panel.BRACKET_LEFT_TEXT)) {
                    stack.push(curr);
                } else if (curr.equals(Panel.BRACKET_RIGHT_TEXT)) {
                    while (!stack.peek().equals(Panel.BRACKET_LEFT_TEXT)) {
                        postfix.add(stack.pop());
                        if (stack.isEmpty()) {
                            // System.out.println(Panel.ERROR_BRACKET);
                            flag = false;
                            return postfix;
                        }
                    }
                    stack.pop();
                    if (!stack.isEmpty() && isFunction(stack.peek())) {
                        postfix.add(stack.pop());
                    }
                } else {
                    if (curr.equals(Panel.SUB_TEXT) &&
                            (prev.equals("") || (isDelimiter(prev) && !prev.equals(Panel.BRACKET_RIGHT_TEXT)))) {
                        // унарный минус
                        curr = "u-";
                    } else {
                        while (!stack.isEmpty() && (priority(curr) <= priority(stack.peek()))) {
                            postfix.add(stack.pop());
                        }
                    }
                    stack.push(curr);
                }
            } else {
                postfix.add(curr);
            }
            prev = curr;
        }
        while (!stack.isEmpty()) {
            if (isOperator(stack.peek())) {
                postfix.add(stack.pop());
            } else {
                // System.out.println(Panel.ERROR_BRACKET);
                flag = false;
                return postfix;
            }
        }
        return postfix;
    }
}
