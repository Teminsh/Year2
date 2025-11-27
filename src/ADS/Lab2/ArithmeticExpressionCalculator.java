package ADS.Lab2;

import java.util.Scanner;
import java.util.Stack;

/*
Лаба №2 "Задача об арифметическом выражении"
На вход подаётся математическое выражение. Элементы - числа. Операции - "+ - * /".
Также есть скобочки. Окончанием выражения служит "=".
Программа должна вывести результат выражения

Пример ввода:
2+7*(3/9)-5=

Замечание:
Программа также должна делать "проверку на дурака": нет деления на 0,
все скобки стоят верно (см лабу №1) и т.п.
*/

public class ArithmeticExpressionCalculator
{
    private static int getPrecedence(char operator)
    {
        return switch (operator)
        {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            default -> -1;
        };
    }

    private static boolean isOperator(char ch)
    {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }

    private static String preprocessExpression(String expression)
    {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < expression.length(); i++)
        {
            char ch = expression.charAt(i);

            if (ch == '-')
            {
                if (i == 0 || expression.charAt(i - 1) == '(' || isOperator(expression.charAt(i - 1)))
                {
                    result.append("(0-");
                    i++;
                    while (i < expression.length() &&
                            (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.'))
                    {
                        result.append(expression.charAt(i));
                        i++;
                    }
                    result.append(')');
                    i--;
                }
                else
                {
                    result.append(ch);
                }
            }
            else
            {
                result.append(ch);
            }
        }

        return result.toString();
    }

    private static String infixToPostfix(String expression) throws Exception
    {
        StringBuilder result = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        int bracketBalance = 0;

        for (int i = 0; i < expression.length(); i++)
        {
            char ch = expression.charAt(i);

            if (ch == ' ')
            {
                continue;
            }

            if (Character.isDigit(ch) || ch == '.')
            {
                while (i < expression.length() &&
                        (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.'))
                {
                    result.append(expression.charAt(i));
                    i++;
                }
                result.append(' ');
                i--;
            }
            else if (ch == '(')
            {
                stack.push(ch);
                bracketBalance++;
            }
            else if (ch == ')')
            {
                bracketBalance--;
                if (bracketBalance < 0)
                {
                    throw new Exception("Ошибка: лишняя закрывающая скобка");
                }
                while (!stack.isEmpty() && stack.peek() != '(')
                {
                    result.append(stack.pop()).append(' ');
                }
                if (stack.isEmpty())
                {
                    throw new Exception("Ошибка: несбалансированные скобки");
                }
                stack.pop();
            }
            else if (isOperator(ch))
            {
                while (!stack.isEmpty() && getPrecedence(ch) <= getPrecedence(stack.peek()))
                {
                    result.append(stack.pop()).append(' ');
                }
                stack.push(ch);
            }
            else if (ch == '=')
            {
                break;
            } else
            {
                throw new Exception("Ошибка: неизвестный символ '" + ch + "'");
            }
        }

        if (bracketBalance != 0)
        {
            throw new Exception("Ошибка: несбалансированные скобки");
        }

        while (!stack.isEmpty())
        {
            char op = stack.pop();
            if (op == '(' || op == ')')
            {
                throw new Exception("Ошибка: несбалансированные скобки");
            }
            result.append(op).append(' ');
        }

        System.out.println(result.toString().trim());

        return result.toString().trim();
    }

    private static double evaluatePostfix(String postfix) throws Exception
    {
        Stack<Double> stack = new Stack<>();
        String[] tokens = postfix.split("\\s+");

        for (String token : tokens)
        {
            if (!isOperator(token.charAt(0)) || token.length() > 1)
            {
                try
                {
                    stack.push(Double.parseDouble(token));
                } catch (NumberFormatException e)
                {
                    throw new Exception("Ошибка: некорректное число '" + token + "'");
                }
            } else if (isOperator(token.charAt(0)))
            {
                if (stack.size() < 2)
                {
                    throw new Exception("Ошибка: некорректное выражение");
                }

                double operand2 = stack.pop();
                double operand1 = stack.pop();
                double result = switch (token.charAt(0))
                {
                    case '+' -> operand1 + operand2;
                    case '-' -> operand1 - operand2;
                    case '*' -> operand1 * operand2;
                    case '/' ->
                    {
                        if (operand2 == 0)
                        {
                            throw new Exception("Ошибка: деление на ноль");
                        }
                        yield operand1 / operand2;
                    }
                    default -> 0;
                };

                stack.push(result);
            }
        }

        if (stack.size() != 1)
        {
            throw new Exception("Ошибка: некорректное выражение");
        }

        return stack.pop();
    }

    public static double calculate(String expression) throws Exception
    {
        if (expression == null || expression.isEmpty())
        {
            throw new Exception("Ошибка: пустое выражение");
        }

        if (!expression.trim().endsWith("="))
        {
            throw new Exception("Ошибка: выражение должно заканчиваться знаком '='");
        }

        expression = preprocessExpression(expression);

        String postfix = infixToPostfix(expression);

        return evaluatePostfix(postfix);
    }

    void main()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите математическое выражение (окончание '='):");
        String expression = scanner.nextLine();

        try
        {
            double result = calculate(expression);
            System.out.println("Результат: " + result);
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        scanner.close();
    }
}