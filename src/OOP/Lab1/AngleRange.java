package OOP.Lab1;

/*
Создать класс AngleRange для хранения промежутка для углов
 - Реализовать механизм создания объекта через задание начальной и конечной точки промежутка в виде углов float, int или Angle      DONE
 - Предусмотреть возможность использования включающих и исключающих промежутков                                                     DONE
 - реализовать возможность сравнения объектов на эквивалентность (eq)                                                               DONE
 - реализовать строковое представление объекта (str, repr)                                                                          DONE
 - реализовать получение длины промежутка (abs или отдельны метод)                                                                  DONE
- реализовать сравнение промежутков                                                                                                 DONE
 - реализовать операцию in для проверки входит один промежуток в другой или угол в промежуток                                       DONE
 - реализовать операции сложения, вычитания (результат в общем виде - список промежутков)
 */

import java.util.ArrayList;
import java.util.List;

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
    public int compareTo(AngleRange other)
    {
        return Float.compare(this.abs(), other.abs());
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
        return this.abs() == angleRange.abs();
    }

    // endregion

    // region In

    public boolean in(AngleRange other)
    {
        return other.in(this.start) && other.in(this.end);
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
        }
        else
        {
            return afterStart || beforeEnd;
        }
    }

    public boolean in(float radians)
    {
        return this.in(Angle.fromRadians(radians));
    }

    public boolean in(int degrees)
    {
        return this.in(Angle.fromDegrees(degrees));
    }

    // endregion

    // region Addition

    public List<AngleRange> add(AngleRange other)
    {
        List<AngleRange> result = new ArrayList<>();
        float startRad = this.start.getRadians();
        float endRad = this.end.getRadians();
        float otherStartRad = other.start.getRadians();
        float otherEndRad = other.end.getRadians();

        if (endRad < otherStartRad || (endRad == otherStartRad && !this.isInclusiveEnd && !other.isInclusiveStart))
        {
            result.add(this);
            result.add(other);
            return result;
        }
        if (otherEndRad < startRad || (otherEndRad == startRad && !other.isInclusiveEnd && !this.isInclusiveStart))
        {
            result.add(other);
            result.add(this);
            return result;
        }

        boolean newIsInclusiveStart;
        boolean newIsInclusiveEnd;

        if (startRad < otherStartRad)
        {
            newIsInclusiveStart = this.isInclusiveStart;
        }
        else if (otherStartRad < startRad)
        {
            newIsInclusiveStart = other.isInclusiveStart;
        }
        else
        {
            newIsInclusiveStart = this.isInclusiveStart || other.isInclusiveStart;
        }

        if (endRad < otherEndRad)
        {
            newIsInclusiveEnd = this.isInclusiveEnd;
        }
        else if (otherEndRad < endRad)
        {
            newIsInclusiveEnd = other.isInclusiveEnd;
        }
        else
        {
            newIsInclusiveEnd = this.isInclusiveEnd || other.isInclusiveEnd;
        }

        result.add(new AngleRange(Angle.fromRadians(Math.min(startRad, otherStartRad)),
                Angle.fromRadians(Math.max(endRad, otherEndRad)), newIsInclusiveStart, newIsInclusiveEnd));

        return result;
    }

    public static List<AngleRange> add(AngleRange firstRange, AngleRange secondRange)
    {
        return firstRange.add(secondRange);
    }

    // endregion

    // region Subtraction

    public List<AngleRange> subtract(AngleRange other)
    {
        List<AngleRange> result = new ArrayList<>();
        float startRad = this.start.getRadians();
        float endRad = this.end.getRadians();
        float otherStartRad = other.start.getRadians();
        float otherEndRad = other.end.getRadians();



        return result;
    }

    // endregion

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
        float start = this.start.getRadians();
        float end = this.end.getRadians();
        if (start >= end)
        {
            return start - end;
        }
        else
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