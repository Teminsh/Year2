package OOP.Lab6.commands;

import java.util.Map;

@FunctionalInterface
public interface CommandFactory {
    Command create(Map<String, Object> params);
}
