import java.util.Stack;

class Solution {
    public int evaluate(String expr) {
        Stack<Integer> values = new Stack<>();
        Stack<Character> ops = new Stack<>();
        
        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);
            
            if (c == ' ') {
                continue;
            }
            
            if (Character.isDigit(c)) {
                int val = 0;
                while (i < expr.length() && Character.isDigit(expr.charAt(i))) {
                    val = (val * 10) + (expr.charAt(i) - '0');
                    i++;
                }
                values.push(val);
                i--; 
            } else if (c == '(') {
                ops.push(c);
            } else if (c == ')') {
                while (ops.peek() != '(') {
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                }
                ops.pop();
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                while (!ops.isEmpty() && hasPrecedence(c, ops.peek())) {
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                }
                ops.push(c);
            }
        }
        
        while (!ops.isEmpty()) {
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));
        }
        
        return values.pop();
    }
    
    private boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') {
            return false;
        }
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) {
            return false;
        }
        return true;
    }
    
    private int applyOp(char op, int b, int a) {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/':
                if (b == 0) throw new UnsupportedOperationException("Cannot divide by zero");
                return a / b;
        }
        return 0;
    }
}
