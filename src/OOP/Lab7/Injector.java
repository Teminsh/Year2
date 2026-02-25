package OOP.Lab7;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class Injector {

    //region Fields
    private final Map<Class<?>, Binding<?>> bindings = new HashMap<>();
    private final ThreadLocal<Map<Class<?>, Object>> scopedInstances = new ThreadLocal<>();
    private final ThreadLocal<Set<Class<?>>> resolutionStack = ThreadLocal.withInitial(HashSet::new);
    //endregion

    //region Registration
    public <T> void register(Class<T> interfaceType, Class<? extends T> implType, LifeStyle lifeStyle) {
        register(interfaceType, implType, lifeStyle, new Object[0]);
    }

    public <T> void register(Class<T> interfaceType, Class<? extends T> implType, LifeStyle lifeStyle, Object... params) {
        validateRegistration(interfaceType, implType, lifeStyle);
        bindings.put(interfaceType, new Binding<>(interfaceType, implType, lifeStyle, 0, params));
    }

    public <T> void registerPool(Class<T> interfaceType, Class<? extends T> implType, int limit, Object... params) {
        validateRegistration(interfaceType, implType, LifeStyle.POOL);
        if (limit <= 0) {
            throw new IllegalArgumentException("Limit must be greater than 0 for POOL LifeStyle");
        }
        bindings.put(interfaceType, new Binding<>(interfaceType, implType, LifeStyle.POOL, limit, params));
    }

    public <T> void register(Class<T> interfaceType, Supplier<? extends T> factory) {
        if (interfaceType == null || factory == null) {
            throw new IllegalArgumentException("Interface and factory cannot be null");
        }
        if (bindings.containsKey(interfaceType)) {
            throw new IllegalStateException("Interface is already registered: " + interfaceType.getName());
        }
        bindings.put(interfaceType, new Binding<>(interfaceType, factory));
    }

    private void validateRegistration(Class<?> interfaceType, Class<?> implType, LifeStyle lifeStyle) {
        if (interfaceType == null || implType == null || lifeStyle == null) {
            throw new IllegalArgumentException("Registration arguments cannot be null");
        }
        if (!interfaceType.isAssignableFrom(implType)) {
            throw new IllegalArgumentException("Implementation type must implement the interface type");
        }
        if (implType.isInterface() || Modifier.isAbstract(implType.getModifiers())) {
            throw new IllegalArgumentException("Implementation type must be a concrete, instantiable class");
        }
        if (bindings.containsKey(interfaceType)) {
            throw new IllegalStateException("Interface is already registered: " + interfaceType.getName());
        }
    }
    //endregion

    //region Resolution
    @SuppressWarnings("unchecked")
    public <T> T getInstance(Class<T> interfaceType) {
        if (interfaceType == null) {
            throw new IllegalArgumentException("Requested interface type cannot be null");
        }
        Binding<T> binding = (Binding<T>) bindings.get(interfaceType);
        if (binding == null) {
            throw new IllegalStateException("No dependency registered for: " + interfaceType.getName());
        }
        if (!resolutionStack.get().add(interfaceType)) {
            throw new IllegalStateException("Circular dependency detected for: " + interfaceType.getName());
        }
        try {
            if (binding.hasFactory()) {
                return binding.getFactory().get();
            }
            return switch (binding.getLifeStyle()) {
                case PER_REQUEST -> createInstance(binding);
                case SINGLETON -> resolveSingleton(binding);
                case SCOPED -> resolveScoped(binding);
                case POOL -> resolvePool(binding);
            };
        } finally {
            resolutionStack.get().remove(interfaceType);
        }
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
            throw new IllegalStateException("Scoped instance requested outside of an active Scope");
        }
        return (T) scope.computeIfAbsent(binding.getInterfaceType(), k -> createInstance(binding));
    }

    private <T> T resolvePool(Binding<T> binding) {
        synchronized (binding) {
            int count = binding.getRequestCount();
            binding.setRequestCount(count + 1);

            if (binding.getPoolInstances().size() < binding.getLimit()) {
                T instance = createInstance(binding);
                binding.getPoolInstances().add(instance);
                return instance;
            } else {
                return binding.getPoolInstances().get(count % binding.getLimit());
            }
        }
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
        throw new RuntimeException("No matching constructor found for: " + clazz.getName() + " with provided dependencies");
    }

    private Object[] resolveArgs(Class<?>[] paramTypes, Object[] extraParams) {
        Object[] args = new Object[paramTypes.length];
        int extraIndex = 0;
        for (int i = 0; i < paramTypes.length; i++) {
            if (bindings.containsKey(paramTypes[i])) {
                args[i] = getInstance(paramTypes[i]);
            } else if (extraParams != null && extraIndex < extraParams.length) {
                Object param = extraParams[extraIndex++];
                if (param != null && !paramTypes[i].isAssignableFrom(param.getClass())) {
                    return null;
                }
                args[i] = param;
            } else {
                return null;
            }
        }
        return args;
    }
    //endregion

    //region Scope Management
    public Scope beginScope() {
        if (scopedInstances.get() != null) {
            throw new IllegalStateException("A scope is already active on the current thread");
        }
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
