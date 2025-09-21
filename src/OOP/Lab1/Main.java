package OOP.Lab1;

public class Main
{
    public static void main(String[] args)
    {
        Angle angle1 = Angle.fromDegrees(-90f);
        Angle angle2 = Angle.fromDegrees(90);
        Angle angle3 = angle1;

        System.out.println(angle2);
    }
}