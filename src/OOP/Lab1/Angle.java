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
    float radians;

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
        radians = normalizeRadians(radians);
        return new Angle(radians);
    }

    public static Angle fromDegrees(float degrees)
    {
        degrees = normalizeDegrees(degrees);
        float radians = (float) Math.toRadians(degrees);
        return new Angle(radians);
    }

    public Angle(Angle original)
    {
        this.radians = original.radians;
    }
    // endregion

    // region Operations
    @Override
    public int compareTo(Angle other)
    {
        return Float.compare(this.radians, other.radians);
    }

    //region Adding
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
    public Angle multiply(float number)
    {
        float newRadians = this.radians * number;
        return Angle.fromRadians(newRadians);
    }

    public Angle multiply(int number)
    {
        float newRadians = this.radians * number;
        return Angle.fromRadians(newRadians);
    }
    // endregion

    // region Division
    public Angle divide(float number)
    {
        float newRadians = this.radians / number;
        return Angle.fromRadians(newRadians);
    }

    public Angle divide(int number)
    {
        float newRadians = this.radians / number;
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
    private static float normalizeDegrees(float degrees)
    {
        if (degrees >= 0 && degrees < 360) {
            return degrees;
        }

        float normalized = degrees % 360;

        return normalized < 0 ? normalized + 360 : normalized;
    }

    private static float normalizeRadians(float radians)
    {
        if (radians < Math.PI)
        {
            return radians;
        }

        float twoPI = (float) (Math.PI * 2);

        float normalized = radians % twoPI;

        return normalized < 0 ? normalized + twoPI : normalized;
    }
    // endregion

    // region Getters and Setters
    public float getRadians()
    {
        return radians;
    }

    public void setRadians(float radians)
    {
        this.radians = normalizeRadians(radians);
    }

    public float getDegrees()
    {
        return (float) Math.toDegrees(radians);
    }

    public void setDegrees(float degrees)
    {
        radians = normalizeRadians((float) Math.toRadians(degrees));
    }
    // endregion
}