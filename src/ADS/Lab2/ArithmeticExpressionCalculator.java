package ADS.Lab2;

public class ArithmeticExpressionCalculator
{
    private String expression;
    private int position;

    public ArithmeticExpressionCalculator(String expression)
    {
        // Удаляем пробелы и знак равенства в конце
        this.expression = expression.replaceAll("\\s", "").replaceAll("=$", "");
        this.position = 0;
    }

    // Основной метод для вычисления выражения
    public double calculate() throws Exception
    {
        position = 0;
        double result = parseExpression();

        // Проверяем, что обработали всё выражение
        if (position < expression.length())
        {
            throw new Exception("Неожиданный символ на позиции " + position + ": " + expression.charAt(position));
        }

        return result;
    }

    // Парсинг выражений с приоритетом + и -
    private double parseExpression() throws Exception
    {
        double result = parseTerm();

        while (position < expression.length())
        {
            char operator = expression.charAt(position);
            if (operator == '+' || operator == '-')
            {
                position++; // Пропускаем оператор
                double nextTerm = parseTerm();
                if (operator == '+')
                {
                    result += nextTerm;
                } else
                {
                    result -= nextTerm;
                }
            } else
            {
                break;
            }
        }

        return result;
    }

    // Парсинг термов с приоритетом * и /
    private double parseTerm() throws Exception
    {
        double result = parseFactor();

        while (position < expression.length())
        {
            char operator = expression.charAt(position);
            if (operator == '*' || operator == '/')
            {
                position++; // Пропускаем оператор
                double nextFactor = parseFactor();
                if (operator == '*')
                {
                    result *= nextFactor;
                } else
                {
                    // Проверка деления на ноль
                    if (Math.abs(nextFactor) < 1e-10)
                    {
                        throw new Exception("Ошибка: деление на ноль!");
                    }
                    result /= nextFactor;
                }
            } else
            {
                break;
            }
        }

        return result;
    }

    // Парсинг факторов (числа, выражения в скобках, унарный минус)
    private double parseFactor() throws Exception
    {
        if (position >= expression.length())
        {
            throw new Exception("Неожиданный конец выражения");
        }

        char currentChar = expression.charAt(position);

        // Обработка унарного минуса
        if (currentChar == '-')
        {
            position++;
            return -parseFactor();
        }

        // Обработка унарного плюса
        if (currentChar == '+')
        {
            position++;
            return parseFactor();
        }

        // Обработка скобок
        if (currentChar == '(')
        {
            position++; // Пропускаем открывающую скобку
            double result = parseExpression();

            // Проверяем закрывающую скобку
            if (position >= expression.length() || expression.charAt(position) != ')')
            {
                throw new Exception("Ошибка: отсутствует закрывающая скобка ')'");
            }
            position++; // Пропускаем закрывающую скобку
            return result;
        }

        // Парсинг числа
        return parseNumber();
    }

    // Парсинг чисел (целых и дробных)
    private double parseNumber() throws Exception
    {
        if (position >= expression.length())
        {
            throw new Exception("Ожидалось число");
        }

        int start = position;

        // Читаем цифры до точки
        while (position < expression.length() && Character.isDigit(expression.charAt(position)))
        {
            position++;
        }

        // Обрабатываем дробную часть
        if (position < expression.length() && expression.charAt(position) == '.')
        {
            position++; // Пропускаем точку

            // Читаем цифры после точки
            while (position < expression.length() && Character.isDigit(expression.charAt(position)))
            {
                position++;
            }
        }

        // Проверяем, что мы считали хотя бы одну цифру
        if (start == position)
        {
            throw new Exception("Ожидалось число на позиции " + position);
        }

        // Преобразуем строку в число
        try
        {
            return Double.parseDouble(expression.substring(start, position));
        } catch (NumberFormatException e)
        {
            throw new Exception("Некорректное число: " + expression.substring(start, position));
        }
    }

    // Дополнительная проверка корректности скобок
    public static boolean checkBrackets(String expression)
    {
        int count = 0;
        for (char c : expression.toCharArray())
        {
            if (c == '(')
            {
                count++;
            } else if (c == ')')
            {
                count--;
                if (count < 0)
                {
                    return false; // Больше закрывающих скобок
                }
            }
        }
        return count == 0; // Количество открывающих должно равняться закрывающим
    }
}