package OOP.Lab1;

public class Main
{
    public static void main(String[] args)
    {
        Angle angle1 = Angle.fromDegrees(4f);
        Angle angle2 = Angle.fromDegrees(120f);


        System.out.println(angle1.divide(90));
    }
}