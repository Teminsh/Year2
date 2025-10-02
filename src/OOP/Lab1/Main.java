package OOP.Lab1;

public class Main
{
    public static void main(String[] args)
    {
        Angle angle1 = Angle.fromDegrees(150f);
        Angle angle2 = Angle.fromDegrees(360);
        Angle angle3 = Angle.fromDegrees(1);
        AngleRange range = new AngleRange(angle1, angle2, true, true);

        System.out.println(range);
    }
}