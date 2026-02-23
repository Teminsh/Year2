package OOP.Lab4;

public class PropertyChangingEventArgs extends EventArgs
{
    private final String propertyName;
    private final Object oldValue;
    private final Object newValue;
    private boolean canChange;

    public PropertyChangingEventArgs(String propertyName, Object oldValue, Object newValue)
    {
        this.propertyName = propertyName;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.canChange = true;
    }

    public String getPropertyName()
    {
        return propertyName;
    }

    public Object getOldValue()
    {
        return oldValue;
    }

    public Object getNewValue()
    {
        return newValue;
    }

    public boolean isCanChange()
    {
        return canChange;
    }

    public void setCanChange(boolean canChange)
    {
        this.canChange = canChange;
    }
}