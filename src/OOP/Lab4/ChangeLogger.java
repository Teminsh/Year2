package OOP.Lab4;

public class ChangeLogger implements EventHandler<PropertyChangedEventArgs>
{
    @Override
    public void handle(Object sender, PropertyChangedEventArgs args)
    {
        System.out.println("[CHANGED] " + sender.getClass().getSimpleName() + "." + args.getPropertyName());
    }
}
