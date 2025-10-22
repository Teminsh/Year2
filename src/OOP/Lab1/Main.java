package OOP.Lab1;

public class Main
{
    static void main(String[] args)
    {
        Angle angle1 = Angle.fromDegrees(150f);
        Angle angle2 = Angle.fromDegrees(150);
        AngleRange range1 = new AngleRange(0f, 10f, false, false);
        AngleRange range2 = new AngleRange(0f, 5f, false, false);

        System.out.println(range1.subtract(range2));
    }
}