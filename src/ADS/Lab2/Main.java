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

public class Main
{
    static void main()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Калькулятор арифметических выражений");
        System.out.println("Поддерживаемые операции: +, -, *, /, ()");
        System.out.println("Введите выражение (завершите знаком =):");

        try
        {
            String input = scanner.nextLine();

            // Предварительная проверка скобок
            if (!ArithmeticExpressionCalculator.checkBrackets(input))
            {
                System.out.println("Ошибка: неверно расставлены скобки!");
                return;
            }

            ArithmeticExpressionCalculator calculator = new ArithmeticExpressionCalculator(input);
            double result = calculator.calculate();

            System.out.println("Результат: " + result);

        } catch (Exception e)
        {
            System.out.println("Ошибка вычисления: " + e.getMessage());
        } finally
        {
            scanner.close();
        }
    }
}