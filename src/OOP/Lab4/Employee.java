package OOP.Lab4;

public class Employee {
    //region Fields
    private String name;
    private int salary;
    private String position;
    //endregion

    //region Events
    public final Event<PropertyChangingEventArgs> propertyChanging = new Event<>();
    public final Event<PropertyChangedEventArgs> propertyChanged = new Event<>();
    //endregion

    //region Constructor
    public Employee(String name, int salary, String position) {
        this.name = name;
        this.salary = salary;
        this.position = position;
    }
    //endregion

    //region Setters
    public void setName(String newName) {
        if (!canChange("name", this.name, newName)) return;
        this.name = newName;
        propertyChanged.invoke(this, new PropertyChangedEventArgs("name"));
    }

    public void setSalary(int newSalary) {
        if (!canChange("salary", this.salary, newSalary)) return;
        this.salary = newSalary;
        propertyChanged.invoke(this, new PropertyChangedEventArgs("salary"));
    }

    public void setPosition(String newPosition) {
        if (!canChange("position", this.position, newPosition)) return;
        this.position = newPosition;
        propertyChanged.invoke(this, new PropertyChangedEventArgs("position"));
    }
    //endregion

    //region Helpers
    private boolean canChange(String propertyName, Object oldValue, Object newValue) {
        PropertyChangingEventArgs args = new PropertyChangingEventArgs(propertyName, oldValue, newValue);
        propertyChanging.invoke(this, args);
        return args.isCanChange();
    }

    @Override
    public String toString() {
        return "Employee{name='%s', salary=%d, position='%s'}".formatted(name, salary, position);
    }
    //endregion
}
