package OOP.Lab2;

//region Task
/*
Лабораторная работа 2 (красивая консоль)

Создать класс для вывода текста в консоль в произвольном месте, произвольным цветом, большим псевдошрифтом.
Вывод цветом осуществляется при помощи управляющих ANSI команд, использовать внешние библиотеки запрещено.
Управляющие команды не расставлять в виде текста по всему коду, а сохранить в виде констант или перечислителей.
Вывод псевдошрифтом осуществляется путем задания в текстовом файле (формат на ваше усмотрение txt, json,, xml, ...) шаблонов символов
(достаточно задать шаблоны только букв одного алфавита).
Например,
   *    *****
 *  *     *
 ****     *
*    *    *

Класс должен уметь:
  - выводить статически (python: classmethod) текст в произвольном месте, произвольным цветом (цвет задавать при помощи типа данных перечислитель Enum) произвольным символом
          Например:
Printer.print(text: str, color: Color, position : Tuple[int, int], symbol: str)
 - создавать экземпляр с фиксированным цветом и позицией для дальнейшего вывода текста в едином стиле с поддержкой возвращения состояния косноли в исходное состояние
    (поддержка в Python: with, в C#: using, в С++: используйте деструктор)
          Например:
with Printer(color: Color, position : Tuple[int, int], symbol: str) as printer:
       printer.print('text1')
       printer.print('text2')
- использовать произвольный символ для вывода псевдотекста (в примере шаблонов используется символ *)

Продемонстрировать
1. работу класса как статическим образом, так и с использованием создания экземпляра класса, используя with (using и.т.п.).
2. независимость работы класса от поданного файла с шаблонами букв, реализовав шрифты высотой 5 и 7 символов
 */
//endregion

public class Main
{
    public static void main(String[] args)
    {
        try
        {
            System.out.print(AnsiCodes.CLEAR_SCREEN);
            System.out.print(AnsiCodes.CURSOR_HOME);

            System.out.println("=== Demo ===\n");

            System.out.println("1. Static simple text:");
            Printer.print("Hello\n", Color.BRIGHT_GREEN, new int[]{5, 10});

            System.out.print(AnsiCodes.moveCursor(9, 1));

            System.out.print(AnsiCodes.CLEAR_SCREEN);
            System.out.print(AnsiCodes.CURSOR_HOME);
            System.out.println("2. Font 5:");

            Printer.loadFont("font5.txt");
            Printer.print("HELLO", Color.BRIGHT_RED, new int[]{3, 5}, "~");
            Printer.print("WORLD", Color.BRIGHT_CYAN, new int[]{9, 5});

            System.out.print(AnsiCodes.moveCursor(16, 1));

            System.out.print(AnsiCodes.CLEAR_SCREEN);
            System.out.print(AnsiCodes.CURSOR_HOME);
            System.out.println("3. Font 7:");

            Printer.loadFont("font7.txt");
            Printer.print("HELLO", Color.BRIGHT_YELLOW, new int[]{3, 5}, "@");

            System.out.print(AnsiCodes.moveCursor(12, 1));

            System.out.print(AnsiCodes.CLEAR_SCREEN);
            System.out.print(AnsiCodes.CURSOR_HOME);
            System.out.println("4. Try-with-resources:");

            Printer.loadFont("font5.txt");
            try (Printer printer = new Printer(Color.BRIGHT_GREEN, new int[]{3, 10}, "*"))
            {
                printer.print("JAVA IS THE BEST");
                printer.print("CODE");
            }

            System.out.print(AnsiCodes.moveCursor(20, 1));

            System.out.print(AnsiCodes.CLEAR_SCREEN);
            System.out.print(AnsiCodes.CURSOR_HOME);
            System.out.println("5. Positioning Demo:");

            Printer.loadFont("font5.txt");

            Printer.print("A", Color.RED, new int[]{3, 5}, "*");
            Printer.print("B", Color.GREEN, new int[]{9, 10}, "*");
            Printer.print("C", Color.BLUE, new int[]{15, 15}, "*");

            System.out.print(AnsiCodes.moveCursor(22, 1));

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}