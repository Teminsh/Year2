package OOP.Lab1;

public class Main
{
    static void main(String[] args)
    {
        Angle angle1 = Angle.fromDegrees(150f);
        Angle angle2 = Angle.fromDegrees(150);
        AngleRange range1 = new AngleRange(6f, 60f, false, false);
        AngleRange range2 = new AngleRange(5f, 6f, true, false);

        System.out.println();
    }
}