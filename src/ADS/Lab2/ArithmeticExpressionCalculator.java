package ADS.Lab2;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;

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
    static void main()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите математическое выражение (завершите знаком =): ");
        String input = scanner.nextLine();

        if (input.isEmpty())
        {
            System.out.println("Строка не существует!");
            scanner.close();
            return;
        }

        if (!input.trim().endsWith("="))
        {
            System.out.println("Ошибка: выражение должно заканчиваться знаком '='!");
            scanner.close();
            return;
        }

        try
        {
            String expression = input.replaceAll("\\s", "").replaceAll("=$", "");

            String postfix = infixToPostfix(expression);

            double result = evaluatePostfix(postfix);

            System.out.println("Результат: " + result);
        }
        catch (Exception e)
        {
            System.out.println("Ошибка вычисления: " + e.getMessage());
        }

        scanner.close();
    }

    private static int getPrecedence(char operator)
    {
        return switch (operator)
        {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            default -> 0;
        };
    }

    private static boolean isOperator(char c)
    {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }


    private static String infixToPostfix(String infix) throws Exception
    {
        StringBuilder output = new StringBuilder();
        Deque<Character> operatorStack = new LinkedList<>();

        int i = 0;
        while (i < infix.length())
        {
            char c = infix.charAt(i);

            if (Character.isDigit(c))
            {
                StringBuilder number = new StringBuilder();
                while (i < infix.length() &&
                        (Character.isDigit(infix.charAt(i)) || infix.charAt(i) == '.'))
                {
                    number.append(infix.charAt(i));
                    i++;
                }
                output.append(number).append(' ');
                continue;
            }

            if (c == '(')
            {
                operatorStack.push(c);
                i++;
                continue;
            }

            if (c == ')')
            {
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(')
                {
                    output.append(operatorStack.pop()).append(' ');
                }

                if (operatorStack.isEmpty())
                {
                    throw new Exception("Неверно расставлены скобки!");
                }

                operatorStack.pop();
                i++;
                continue;
            }

            if (isOperator(c))
            {
                if ((c == '-' || c == '+') &&
                        (i == 0 || infix.charAt(i - 1) == '(' || isOperator(infix.charAt(i - 1))))
                {
                    if (c == '-')
                    {
                        output.append("0 ");
                    }
                    i++;
                    continue;
                }

                while (!operatorStack.isEmpty() &&
                        operatorStack.peek() != '(' &&
                        getPrecedence(operatorStack.peek()) >= getPrecedence(c))
                {
                    output.append(operatorStack.pop()).append(' ');
                }

                operatorStack.push(c);
                i++;
                continue;
            }

            throw new Exception("Неожиданный символ: " + c);
        }

        while (!operatorStack.isEmpty())
        {
            char op = operatorStack.pop();
            if (op == '(' || op == ')')
            {
                throw new Exception("Неверно расставлены скобки!");
            }
            output.append(op).append(' ');
        }

        return output.toString().trim();
    }

    private static double evaluatePostfix(String postfix) throws Exception
    {
        Deque<Double> valueStack = new LinkedList<>();
        String[] tokens = postfix.split(" ");

        for (String token : tokens)
        {
            if (token.isEmpty())
            {
                continue;
            }

            if (Character.isDigit(token.charAt(0)) || (token.length() > 1 && token.charAt(0) == '-'))
            {
                try
                {
                    valueStack.push(Double.parseDouble(token));
                } catch (NumberFormatException e)
                {
                    throw new Exception("Некорректное число: " + token);
                }
                continue;
            }

            if (isOperator(token.charAt(0)))
            {
                if (valueStack.size() < 2)
                {
                    throw new Exception("Некорректное выражение!");
                }

                double second = valueStack.pop();
                double first = valueStack.pop();

                double result = switch (token.charAt(0))
                {
                    case '+' -> first + second;
                    case '-' -> first - second;
                    case '*' -> first * second;
                    case '/' ->
                    {
                        if (Math.abs(second) < 1e-10)
                        {
                            throw new Exception("Деление на ноль!");
                        }
                        yield first / second;
                    }
                    default -> throw new Exception("Неизвестный оператор: " + token);
                };

                valueStack.push(result);
            }
        }

        if (valueStack.size() != 1)
        {
            throw new Exception("Некорректное выражение!");
        }

        return valueStack.pop();
    }
}