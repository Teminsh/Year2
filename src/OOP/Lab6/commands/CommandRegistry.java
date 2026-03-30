package OOP.Lab6.commands;

import java.util.HashMap;
import java.util.Map;

public class CommandRegistry {
    private final Map<String, CommandFactory> factories = new HashMap<>();

    public void register(String type, CommandFactory factory) {
        factories.put(type, factory);
    }

    public boolean hasCommand(String type) {
        return factories.containsKey(type);
    }

    public Command create(CommandSpec spec) {
        CommandFactory factory = factories.get(spec.type());
        if (factory == null) throw new IllegalArgumentException("Unknown command type: " + spec.type());
        return factory.create(spec.params());
    }
}
