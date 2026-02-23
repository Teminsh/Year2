package OOP.Lab4;

public class PropertyChangedEventArgs extends EventArgs
{
    private final String propertyName;

    public PropertyChangedEventArgs(String propertyName)
    {
        this.propertyName = propertyName;
    }

    public String getPropertyName()
    {
        return propertyName;
    }
}