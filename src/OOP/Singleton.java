package OOP;

public class Singleton
{
    private static final Singleton INSTANCE = new Singleton();

    private int x;


    private Singleton()
    {

    }

    public static Singleton GetInstance()
    {
        return INSTANCE;
    }

    public int GetX()
    {
        return x;
    }

    public void SetX(int x)
    {
        this.x = x;
    }
}
