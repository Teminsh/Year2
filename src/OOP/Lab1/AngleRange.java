package OOP.Lab1;

/*
Создать класс AngleRange для хранения промежутка для углов
 - Реализовать механизм создания объекта через задание начальной и конечной точки промежутка в виде углов float, int или Angle      DONE
 - Предусмотреть возможность использования включающих и исключающих промежутков                                                     DONE
 - конструктор (start: Point2d, end: Point2d)
 - реализовать возможность сравнения объектов на эквивалентность (eq)
 - реализовать строковое представление объекта (str, repr)                                                                          DONE
 - реализовать получение длины промежутка (abs или отдельны метод)
 - реализовать сравнение промежутков
 - реализовать операцию in для проверки входит один промежуток в другой или угол в промежуток
 - реализовать операции сложения, вычитания (результат в общем виде - список промежутков)
 */

public class AngleRange
{
    private final Angle start;
    private final Angle end;
    private final boolean isInclusiveStart;
    private final boolean isInclusiveEnd;

    // region Constructors
    public AngleRange(Angle start, Angle end)
    {
        this(start, end, true, true);
    }

    public AngleRange(Angle start, Angle end, boolean isInclusiveStart, boolean isInclusiveEnd)
    {
        this.start = start;
        this.end = end;
        this.isInclusiveStart = isInclusiveStart;
        this.isInclusiveEnd = isInclusiveEnd;
    }

    public AngleRange(float startRadians, float endRadians)
    {
        this(Angle.fromRadians(startRadians), Angle.fromRadians(endRadians));
    }

    public AngleRange(float startRadians, float endRadians, boolean isInclusiveStart, boolean isInclusiveEnd)
    {
        this(Angle.fromRadians(startRadians), Angle.fromRadians(endRadians), isInclusiveStart, isInclusiveEnd);
    }

    public AngleRange(int startDegrees, int endDegrees)
    {
        this(Angle.fromDegrees(startDegrees), Angle.fromDegrees(endDegrees));
    }

    public AngleRange(int startDegrees, int endDegrees, boolean isInclusiveStart, boolean isInclusiveEnd)
    {
        this(Angle.fromDegrees(startDegrees), Angle.fromDegrees(endDegrees), isInclusiveStart, isInclusiveEnd);
    }
    // endregion

    // region Representation
    @Override
    public String toString()
    {
        String openBracket = isInclusiveStart ? "[" : "(";
        String closeBracket = isInclusiveEnd ? "]" : ")";
        return openBracket + start.toFloat() + ";" + end.toFloat() + closeBracket;
    }

    public String toReprString()
    {
        return String.format("AngleRange(start=%f,end=%f,isInclusiveStart=%b,isInclusiveEnd=%b)",
                start.toFloat(), end.toFloat(), isInclusiveStart, isInclusiveEnd);
    }
    // endregion

}