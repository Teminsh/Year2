package ADS.Lab1;

import java.util.Scanner;
import java.util.Stack;

/*
Лаба №1 "Задача о скобках"
(Задача состоит из двух пунктов, но вы можете не париться и делать сразу второй)
На вход подаётся строка, состоящая из скобок. Программа должна определить правильность введённого скобочного выражения.
Савкин сказал, что программа должна работать на русском языке: "Введите строку", "Строка не существует",
"Строка существует" и т.п.
Пункт 1: В строке будут скобки только одного типа: или "()", или "{}", или "[]"
Пункт 2: В строке будут все три вида скобок
Для успешной сдачи лабы оба пункта программа должна выполнять корректно
(можно сделать отдельные программы на каждый пункт)

Пример входа:
()[({}())]

*/

public class Main
{
    static void main()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите строку - ");
        String input = scanner.nextLine();

        if (input.isEmpty())
        {
            System.out.println("Строка не существует!");
            return;
        }

        if (checkAllBrackets(input))
        {
            System.out.println("Строка существует!");
        } else
        {
            System.out.println("Строка не существует!");
        }
        scanner.close();
    }

    static boolean checkAllBrackets(String input)
    {
        Stack<Character> stack = new Stack<>();

        for (char c : input.toCharArray())
        {
            if (isOpeningBracket(c))
            {
                stack.push(c);
            } else if (isClosingBracket(c))
            {
                if (stack.isEmpty())
                {
                    return false;
                }

                char openBracket = stack.pop();

                if (!isMatchingPair(openBracket, c))
                {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    private static boolean isMatchingPair(char openBracket, char closeBracket)
    {
        return (openBracket == '(' && closeBracket == ')') ||
                (openBracket == '[' && closeBracket == ']') ||
                (openBracket == '{' && closeBracket == '}');
    }

    private static boolean isClosingBracket(char c)
    {
        return c == ')' || c == ']' || c == '}';
    }

    private static boolean isOpeningBracket(char c)
    {
        return c == '(' || c == '[' || c == '{';
    }

    public static boolean checkBrackets(String input, char openBracket, char closeBracket)
    {
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < input.length(); i++)
        {
            char c = input.charAt(i);

            if (c == openBracket)
            {
                if (!Character.isDigit(input.charAt(i + 1)))
                {
                    return false;
                }
                stack.push(c);
            } else if (c == closeBracket)
            {
                if (stack.isEmpty())
                {
                    return false;
                }

                char currentOpenBracket = stack.pop();

                if (!isMatchingPair(openBracket, c))
                {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }
}