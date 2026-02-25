package OOP.Lab7;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Injector {

    //region Fields
    private final Map<Class<?>, Binding<?>> bindings = new HashMap<>();
    private final ThreadLocal<Map<Class<?>, Object>> scopedInstances = new ThreadLocal<>();
    //endregion

    //region Registration
    public <T> void register(Class<T> interfaceType, Class<? extends T> implType, LifeStyle lifeStyle) {
        bindings.put(interfaceType, new Binding<>(interfaceType, implType, lifeStyle, null));
    }

    public <T> void register(Class<T> interfaceType, Class<? extends T> implType,
                             LifeStyle lifeStyle, Object... params) {
        bindings.put(interfaceType, new Binding<>(interfaceType, implType, lifeStyle, params));
    }

    public <T> void register(Class<T> interfaceType, Supplier<? extends T> factory) {
        bindings.put(interfaceType, new Binding<>(interfaceType, factory));
    }
    //endregion

    //region Resolution
    @SuppressWarnings("unchecked")
    public <T> T getInstance(Class<T> interfaceType) {
        Binding<T> binding = (Binding<T>) bindings.get(interfaceType);
        if (binding == null) {
            throw new IllegalArgumentException("No binding registered for: " + interfaceType.getName());
        }
        if (binding.hasFactory()) {
            return binding.getFactory().get();
        }
        return switch (binding.getLifeStyle()) {
            case PER_REQUEST -> createInstance(binding);
            case SINGLETON   -> resolveSingleton(binding);
            case SCOPED      -> resolveScoped(binding);
        };
    }

    private <T> T resolveSingleton(Binding<T> binding) {
        if (binding.getSingletonInstance() == null) {
            synchronized (binding) {
                if (binding.getSingletonInstance() == null) {
                    binding.setSingletonInstance(createInstance(binding));
                }
            }
        }
        return binding.getSingletonInstance();
    }

    @SuppressWarnings("unchecked")
    private <T> T resolveScoped(Binding<T> binding) {
        Map<Class<?>, Object> scope = scopedInstances.get();
        if (scope == null) {
            throw new IllegalStateException(
                    "No active scope. Use beginScope() before requesting scoped instances.");
        }
        return (T) scope.computeIfAbsent(binding.getInterfaceType(), k -> createInstance(binding));
    }

    @SuppressWarnings("unchecked")
    private <T> T createInstance(Binding<T> binding) {
        Class<? extends T> clazz = binding.getImplementationType();
        Object[] extraParams = binding.getExtraParams();
        for (Constructor<?> ctor : clazz.getConstructors()) {
            Object[] args = resolveArgs(ctor.getParameterTypes(), extraParams);
            if (args != null) {
                try {
                    return (T) ctor.newInstance(args);
                } catch (Exception e) {
                    throw new RuntimeException("Failed to instantiate: " + clazz.getName(), e);
                }
            }
        }
        throw new RuntimeException("No satisfiable constructor found for: " + clazz.getName());
    }

    private Object[] resolveArgs(Class<?>[] paramTypes, Object[] extraParams) {
        Object[] args = new Object[paramTypes.length];
        int extraIndex = 0;
        for (int i = 0; i < paramTypes.length; i++) {
            if (bindings.containsKey(paramTypes[i])) {
                args[i] = getInstance(paramTypes[i]);
            } else if (extraIndex < extraParams.length) {
                args[i] = extraParams[extraIndex++];
            } else {
                return null;
            }
        }
        return args;
    }
    //endregion

    //region Scope Management
    public Scope beginScope() {
        return new Scope(this);
    }

    void enterScope() {
        scopedInstances.set(new HashMap<>());
    }

    void exitScope() {
        scopedInstances.remove();
    }
    //endregion
}