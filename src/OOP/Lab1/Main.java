package OOP.Lab1;

public class Main
{
    static void main(String[] args)
    {
        Angle angle1 = Angle.fromRadians(10f);
        Angle angle2 = Angle.fromDegrees(150);
        AngleRange range1 = new AngleRange(0, 10f, false, false);
        AngleRange range2 = new AngleRange(0f, 10f, false, false);



        System.out.println(range1.in(range2));
    }
}