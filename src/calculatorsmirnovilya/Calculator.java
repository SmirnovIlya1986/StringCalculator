package calculatorsmirnovilya;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class Calculator {

    public static String calculate(String s) {
        List<String> expression = ExpressionParser.parse(s);
        boolean flag = ExpressionParser.flag;
            if (flag) {
                ExpressionParser.flag = true;
                return calc(expression).toString();
            }
            ExpressionParser.flag = true;
            return Panel.ERROR_MESS;
    }

    public static Double calc(List<String> postfix) {
        Deque<Double> stack = new ArrayDeque<>();
        for (String x : postfix) {
            if (x.equals("sqrt")) {
                stack.push(Math.sqrt(stack.pop()));
            } else if (x.equals("cube")) {
                Double tmp = stack.pop();
                stack.push(tmp * tmp * tmp);
            } else if (x.equals("pow10")) {
                stack.push(Math.pow(10, stack.pop()));
            } else if (x.equals(Panel.SUM_TEXT)) {
                stack.push(stack.pop() + stack.pop());
            } else if (x.equals(Panel.SUB_TEXT)) {
                Double b = stack.pop();
                Double a = stack.pop();
                stack.push(a - b);
            } else if (x.equals(Panel.MULTI_TEXT)) {
                stack.push(stack.pop() * stack.pop());
            } else if (x.equals(Panel.DIV_TEXT)) {
                Double b = stack.pop();
                Double a = stack.pop();
                stack.push(a / b);
            } else if (x.equals("u-")) {
                stack.push(-stack.pop());
            } else {
                    stack.push(Double.valueOf(x));
            }
        }
        return stack.pop();
    }
}
