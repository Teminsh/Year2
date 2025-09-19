package OOP.Lab1;

/*
Создать класс Angle для хранения углов
 - хранить внутреннее состоние угла в радианах                                              DONE
 - возможность создания угла в радианах и градусах                                          DONE
 - реализовать присваивание и получение угла в раддианах и градусах                         DONE
 - реализовать сранение углов с учелом, что 2 Pi*N + x = x, где Pi=3.14.1529..., N-целое
 - релизовать перобразование угла в строку, float, int, str
 - реализовать сравнение углов                                                              DONE
 - реализовать сложение (в том числе с float и int, считая, что они заданы в радианах),
   вычитание (считая, что они заданы в радианах), умножение на и деление на число углов
 - реализовать строкове представление объекта (str, repr)                                   DONE
 */


public class Angle implements Comparable<Angle>
{
    float radians;
    float degrees;

    // region Constructors
    public Angle()
    {
        this.radians = 0;
        this.degrees = 0;
    }

    private Angle(float radians, float degrees)
    {
        this.radians = radians;
        this.degrees = degrees;
    }

    public static Angle fromRadians(float radians)
    {
        radians = normalizeRadians(radians);
        float degrees = (float) Math.toDegrees(radians);
        return new Angle(radians, degrees);
    }

    public static Angle fromDegrees(float degrees)
    {
        degrees = normalizeDegrees(degrees);
        float radians = (float) Math.toRadians(degrees);
        return new Angle(radians, degrees);
    }

    public Angle(Angle original)
    {
        this.radians = original.radians;
        this.degrees = original.degrees;
    }
    // endregion

    // region Operations
    @Override
    public int compareTo(Angle other)
    {
        return Float.compare(this.degrees, other.degrees);
    }

    //region Adding
    public Angle add(Angle other)
    {
        float newDegrees = this.degrees + other.degrees;
        return Angle.fromDegrees(newDegrees);
    }

    public Angle addDegrees(float degrees)
    {
       return this.add(Angle.fromDegrees(degrees));
    }

    public Angle addRadians(float radians)
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
        float newDegrees = this.degrees - other.degrees;
        return Angle.fromDegrees(newDegrees);
    }

    public Angle subtractDegrees(float degrees)
    {
        return this.subtract(Angle.fromDegrees(degrees));
    }

    public Angle subtractRadians(float radians)
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
        float newDegrees = this.degrees * number;
        return Angle.fromDegrees(newDegrees);
    }

    public Angle multiply(int number)
    {
        float newDegrees = this.degrees * number;
        return Angle.fromDegrees(newDegrees);
    }
    // endregion

    // region Division
    public Angle divide(float number)
    {
        float newDegrees = this.degrees / number;
        return Angle.fromDegrees(newDegrees);
    }

    public Angle divide(int number)
    {
        float newDegrees = this.degrees / number;
        return Angle.fromDegrees(newDegrees);
    }
    // endregion



    // endregion

    @Override
    public String toString()
    {
        return "Angle {radians ='" + radians + "', degrees = '" + degrees + "'}";
    }

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

    // region Getters and Setters
    public float getRadians()
    {
        return radians;
    }

    public void setRadians(float radians)
    {
        this.radians = normalizeRadians(radians);
        degrees = (float) Math.toDegrees(radians);
    }

    public float getDegrees()
    {
        return degrees;
    }

    public void setDegrees(float degrees)
    {
        this.degrees = normalizeDegrees(degrees);
        radians = (float) Math.toRadians(degrees);
    }
    // endregion

}