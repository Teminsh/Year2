package OOP.Lab7;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Binding<T> {

    //region Fields
    private final Class<T> interfaceType;
    private final Class<? extends T> implementationType;
    private final Supplier<? extends T> factory;
    private final LifeStyle lifeStyle;
    private final Object[] extraParams;
    private volatile T singletonInstance;
    private final int limit;
    private final List<T> poolInstances = new ArrayList<>();
    private int requestCount = 0;
    //endregion

    //region Constructors
    public Binding(Class<T> interfaceType, Class<? extends T> implementationType, LifeStyle lifeStyle, int limit, Object[] extraParams) {
        this.interfaceType = interfaceType;
        this.implementationType = implementationType;
        this.factory = null;
        this.lifeStyle = lifeStyle;
        this.limit = limit;
        this.extraParams = extraParams != null ? extraParams : new Object[0];
    }

    public Binding(Class<T> interfaceType, Supplier<? extends T> factory) {
        this.interfaceType = interfaceType;
        this.implementationType = null;
        this.factory = factory;
        this.lifeStyle = LifeStyle.PER_REQUEST;
        this.limit = 0;
        this.extraParams = new Object[0];
    }
    //endregion

    //region Accessors
    public Class<T> getInterfaceType() { return interfaceType; }
    public Class<? extends T> getImplementationType() { return implementationType; }
    public Supplier<? extends T> getFactory() { return factory; }
    public LifeStyle getLifeStyle() { return lifeStyle; }
    public Object[] getExtraParams() { return extraParams; }
    public int getLimit() { return limit; }
    public T getSingletonInstance() { return singletonInstance; }
    public void setSingletonInstance(T instance) { this.singletonInstance = instance; }
    public List<T> getPoolInstances() { return poolInstances; }
    public int getRequestCount() { return requestCount; }
    public void setRequestCount(int requestCount) { this.requestCount = requestCount; }
    public boolean hasFactory() { return factory != null; }
    //endregion
}
