package OOP.Lab1;

/*
Создать класс AngleRange для хранения промежутка для углов
 - Реализовать механизм создания объекта через задание начальной и конечной точки промежутка в виде углов float, int или Angle      DONE
 - Предусмотреть возможность использования включающих и исключающих промежутков                                                     DONE
 - реализовать возможность сравнения объектов на эквивалентность (eq)                                                               DONE
 - реализовать строковое представление объекта (str, repr)                                                                          DONE
 - реализовать получение длины промежутка (abs или отдельны метод)                                                                  DONE
 - реализовать сравнение промежутков                                                                                                DONE
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
        if (start == null || end == null)
        {
            throw new IllegalArgumentException("Start and end cannot be null");
        }
        if (start.getRadians() > end.getRadians())
        {
            throw new IllegalArgumentException("Start of the interval can't be grater that its end");
        }
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
        int startCompare = this.start.compareTo(other.start);
        if (startCompare != 0)
        {
            return startCompare;
        }
        return this.end.compareTo(other.end);
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
        return this.start.equals(angleRange.start) &&
                this.end.equals(angleRange.end) &&
                this.isInclusiveStart == angleRange.isInclusiveStart &&
                this.isInclusiveEnd == angleRange.isInclusiveEnd;
    }

    // endregion

    // region In and Contains

    private boolean isLessOrEqual(float numA, float numB, boolean isInclusiveA, boolean isInclusiveB)
    {
        if (numA < numB) return true;
        if (numA > numB) return false;
        return !isInclusiveA || isInclusiveB;
    }

    public boolean in(AngleRange other)
    {
        float thisStartRad = this.start.getRadians();
        float thisEndRad = this.end.getRadians();
        float otherStartRad = other.start.getRadians();
        float otherEndRad = other.end.getRadians();

        boolean startsInsideOther = isLessOrEqual(otherStartRad, thisStartRad, other.isInclusiveStart, this.isInclusiveStart);
        boolean endsInsideOther = isLessOrEqual(thisEndRad, otherEndRad, this.isInclusiveEnd, other.isInclusiveEnd);

        return startsInsideOther && endsInsideOther;
    }

    public boolean contains(Angle angle)
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

    public boolean contains(float radians)
    {
        return this.contains(Angle.fromRadians(radians));
    }

    public boolean contains(int degrees)
    {
        return this.contains(Angle.fromDegrees(degrees));
    }

    // endregion

    // region Additional Functions

    private boolean isBefore(float numA, float numB, boolean isInclusiveA, boolean isInclusiveB)
    {
        if (numA < numB) { return true; }
        if (numA > numB) { return false; }
        return !isInclusiveA || !isInclusiveB;
    }

    // endregion

    // region Addition

    public List<AngleRange> add(AngleRange other)
    {
        List<AngleRange> result = new ArrayList<>();
        float thisStartRad = this.start.getRadians();
        float thisEndRad = this.end.getRadians();
        float otherStartRad = other.start.getRadians();
        float otherEndRad = other.end.getRadians();

        if (isBefore(thisEndRad, otherStartRad, this.isInclusiveEnd, other.isInclusiveStart))
        {
            result.add(this);
            result.add(other);
            return result;
        }
        else if (isBefore(otherEndRad, thisStartRad, other.isInclusiveEnd, this.isInclusiveStart))
        {
            result.add(other);
            result.add(this);
            return result;
        }

        boolean newIsInclusiveStart;
        boolean newIsInclusiveEnd;

        if (thisStartRad < otherStartRad)
        {
            newIsInclusiveStart = this.isInclusiveStart;
        }
        else if (otherStartRad < thisStartRad)
        {
            newIsInclusiveStart = other.isInclusiveStart;
        }
        else
        {
            newIsInclusiveStart = this.isInclusiveStart || other.isInclusiveStart;
        }

        if (thisEndRad < otherEndRad)
        {
            newIsInclusiveEnd = other.isInclusiveEnd;
        }
        else if (otherEndRad < thisEndRad)
        {
            newIsInclusiveEnd = this.isInclusiveEnd;
        }
        else
        {
            newIsInclusiveEnd = this.isInclusiveEnd || other.isInclusiveEnd;
        }

        result.add(new AngleRange(Angle.fromRadians(Math.min(thisStartRad, otherStartRad)),
                Angle.fromRadians(Math.max(thisEndRad, otherEndRad)), newIsInclusiveStart, newIsInclusiveEnd));

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
        float thisStartRad = this.start.getRadians();
        float thisEndRad = this.end.getRadians();
        float otherStartRad = other.start.getRadians();
        float otherEndRad = other.end.getRadians();

        if (isBefore(thisEndRad, otherStartRad, this.isInclusiveEnd, other.isInclusiveStart) ||
                isBefore(otherEndRad, thisStartRad, other.isInclusiveEnd, this.isInclusiveStart))
        {
            result.add(this);
            return result;
        }

        if ((otherStartRad < thisStartRad || (otherStartRad == thisStartRad && (other.isInclusiveStart || !this.isInclusiveStart))) &&
                (otherEndRad > thisEndRad || (otherEndRad == thisEndRad && (other.isInclusiveEnd || !this.isInclusiveEnd))))
        {
            return result;
        }

        if (thisStartRad < otherStartRad || (thisStartRad == otherStartRad && this.isInclusiveStart && !other.isInclusiveStart))
        {
            result.add(new AngleRange(
                    this.start,
                    other.start,
                    this.isInclusiveStart,
                    !other.isInclusiveStart
            ));
        }

        if (otherEndRad < thisEndRad || (otherEndRad == thisEndRad && !other.isInclusiveEnd && this.isInclusiveEnd))
        {
            result.add(new AngleRange(
                    other.end,
                    this.end,
                    !other.isInclusiveEnd,
                    this.isInclusiveEnd
            ));
        }

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
        return Math.abs(end - start);
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