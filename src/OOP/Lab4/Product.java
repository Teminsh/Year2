package OOP.Lab4;

public class Product {
    private String title;
    private double price;
    private int stock;

    public final Event<PropertyChangingEventArgs> propertyChanging = new Event<>();
    public final Event<PropertyChangedEventArgs> propertyChanged = new Event<>();

    public Product(String title, double price, int stock) {
        this.title = title;
        this.price = price;
        this.stock = stock;
    }

    //region Setters
    public void setTitle(String newTitle) {
        if (!canChange("title", this.title, newTitle)) return;
        this.title = newTitle;
        propertyChanged.invoke(this, new PropertyChangedEventArgs("title"));
    }

    public void setPrice(double newPrice) {
        if (!canChange("price", this.price, newPrice)) return;
        this.price = newPrice;
        propertyChanged.invoke(this, new PropertyChangedEventArgs("price"));
    }

    public void setStock(int newStock) {
        if (!canChange("stock", this.stock, newStock)) return;
        this.stock = newStock;
        propertyChanged.invoke(this, new PropertyChangedEventArgs("stock"));
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
        return "Product{title='%s', price=%.2f, stock=%d}".formatted(title, price, stock);
    }
    //endregion
}
