package OOP.Lab1;

/*
Создать класс Angle для хранения углов
 - хранить внутреннее состояние угла в радианах                                             DONE
 - возможность создания угла в радианах и градусах                                          DONE
 - реализовать присваивание и получение угла в радианах и градусах                          DONE
 - реализовать сравнение углов с учетом, что 2 Pi*N + x = x, где Pi=3.14.1529..., N-целое   DONE
 - реализовать преобразование угла в строку, float, int, str                                DONE
 - реализовать сравнение углов                                                              DONE
 - реализовать сложение (в том числе с float и int, считая, что они заданы в радианах),
   вычитание (считая, что они заданы в радианах), умножение на и деление на число углов     DONE
 - реализовать строковое представление объекта (str, repr)                                  DONE
 */


public class Angle implements Comparable<Angle>
{
    private static final float EPSILON = 1e-6f;
    private float radians;

    // region Constructors

    public Angle()
    {
        this.radians = 0;
    }

    private Angle(float radians)
    {
        this.radians = radians;
    }

    public static Angle fromRadians(float radians)
    {
        return new Angle(radians);
    }

    public static Angle fromDegrees(float degrees)
    {
        float radians = (float) Math.toRadians(degrees);
        return new Angle(radians);
    }

    public Angle(Angle original)
    {
        this.radians = original.radians;
    }

    // endregion

    // region Operations

    // region Comparing

    @Override
    public int compareTo(Angle other)
    {
        return Float.compare(this.radians, other.radians);
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
        Angle other = (Angle) obj;
        return Math.abs(this.radians - other.radians) < EPSILON;
    }

    // endregion

    //region Addition

    public Angle add(Angle other)
    {
        float newRadians = this.radians + other.radians;
        return Angle.fromRadians(newRadians);
    }

    public Angle add(float radians)
    {
        return this.add(Angle.fromRadians(radians));
    }

    public Angle add(int radians)
    {
        return this.add(Angle.fromRadians(radians));
    }

    public static Angle add(Angle angle1, Angle angle2)
    {
        return angle1.add(angle2);
    }

    //endregion

    // region Substraction

    public Angle subtract(Angle other)
    {
        float newRadians = this.radians - other.radians;
        return Angle.fromRadians(newRadians);
    }

    public Angle subtract(float radians)
    {
        return this.subtract(Angle.fromRadians(radians));
    }

    public Angle subtract(int radians)
    {
        return this.subtract(Angle.fromRadians(radians));
    }

    public static Angle subtract(Angle angle1, Angle angle2)
    {
        return angle1.subtract(angle2);
    }

    // endregion

    // region Multiplication

    public Angle multiply(float multiplier)
    {
        float newRadians = this.radians * multiplier;
        return Angle.fromRadians(newRadians);
    }

    public Angle multiply(int multiplier)
    {
        float newRadians = this.radians * multiplier;
        return Angle.fromRadians(newRadians);
    }

    // endregion

    // region Division

    public Angle divide(float divisor)
    {
        if (Math.abs(divisor) < EPSILON)
        {
            throw new ArithmeticException("Division by zero");
        }
        float newRadians = this.radians / divisor;
        return Angle.fromRadians(newRadians);
    }

    public Angle divide(int divisor)
    {
        if (divisor == 0)
        {
            throw new ArithmeticException("Division by zero");
        }
        float newRadians = this.radians / divisor;
        return Angle.fromRadians(newRadians);
    }

    // endregion

    // endregion

    // region Representation

    @Override
    public String toString()
    {
        return radians + " radians";
    }

    public String toReprString()
    {
        return String.format("Angle(radians=%f, degrees=%f)", radians, getDegrees());
    }

    public float toFloat()
    {
        return radians;
    }

    public int toInt()
    {
        return (int) radians;
    }

    // endregion

    // region Normalization

    private static float normalizeRadians(float radians)
    {
        float twoPI = (float) (Math.PI * 2);
        radians = radians % twoPI;
        if (radians < 0)
        {
            radians += twoPI;
        }
        return radians;
    }

    // endregion

    // region Getters and Setters

    public float getRadians()
    {
        return radians;
    }

    public float getNormalizedRadians() { return normalizeRadians(radians); }

    public void setRadians(float radians)
    {
        this.radians = radians;
    }

    public float getDegrees()
    {
        return (float) Math.toDegrees(radians);
    }

    public float getNormalizedDegrees() { return (float) Math.toDegrees(normalizeRadians(radians)); }

    public void setDegrees(float degrees)
    {
        radians = (float) Math.toRadians(degrees);
    }

    // endregion
}