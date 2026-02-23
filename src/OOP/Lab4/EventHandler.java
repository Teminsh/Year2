package OOP.Lab4;

public interface EventHandler<TEventArgs extends EventArgs>
{
    void handle(Object sender, TEventArgs args);
}