package OOP.Lab1;

/*
Создать класс AngleRange для хранения промежутка для углов
 - Реализовать механизм создания объекта через задание начальной и конечной точки промежутка в виде углов float, int или Angle      DONE
 - Предусмотреть возможность использования включающих и исключающих промежутков                                                     DONE
 - реализовать возможность сравнения объектов на эквивалентность (eq)                                                               DONE
 - реализовать строковое представление объекта (str, repr)                                                                          DONE
 - реализовать получение длины промежутка (abs или отдельны метод)                                                                  DONE
 - реализовать сравнение промежутков
 - реализовать операцию in для проверки входит один промежуток в другой или угол в промежуток                                       DONE
 - реализовать операции сложения, вычитания (результат в общем виде - список промежутков)
 */

public class AngleRange implements Comparable<AngleRange>
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

    // region Operations

    // region Comparing

    @Override
    public int compareTo(AngleRange o)
    {
        return 0;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null || getClass() != obj.getClass())
        {
            return false;
        }
        AngleRange angleRange = (AngleRange) obj;
        return length() == angleRange.length();
    }

    // endregion

    public boolean in(AngleRange other)
    {
        return this.in(other.start) && this.in(other.end);
    }

    public boolean in(Angle angle)
    {
        float angleRad = angle.getRadians();
        float startRad = start.getRadians();
        float endRad = end.getRadians();
        boolean afterStart = isInclusiveStart ? angleRad >= startRad : angleRad > startRad;
        boolean beforeEnd = isInclusiveEnd ? angleRad <= endRad : angleRad < endRad;

        if (startRad <= endRad)
        {
            return afterStart && beforeEnd;
        } else
        {
            return afterStart || beforeEnd;
        }
    }

    // region Absolute value and length

    public float abs()
    {
        return length();

    }

    public static float abs(AngleRange angleRange)
    {
        return angleRange.length();
    }

    private float length()
    {
        float start = (float) (this.start.getRadians() - (isInclusiveStart ? Math.toRadians(1) : 0f));
        float end = (float) (this.end.getRadians() + (isInclusiveEnd ? Math.toRadians(1) : 0f));

        if (start > end)
        {
            return (float) ((2 * Math.PI - start) + end);
        } else
        {
            return end - start;
        }
    }

    // endregion

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