package ADS.Lab2;

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

import java.util.Scanner;

import static ADS.Lab1.Main.checkBrackets;

public class Main
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter arithmetical expression - ");
        String expression = scanner.nextLine();

        if (expression.isEmpty())
        {
            System.out.println("There is nothing to calculate!");
            return;
        }
        if (!checkBrackets(expression, '(' , ')'))
        {
            System.out.println("There is nothing to calculate!");
            return;
        }

    }
}