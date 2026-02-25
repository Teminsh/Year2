package OOP.Lab6;

import OOP.Lab6.commands.CommandSpec;
import java.util.Map;

public record KeyboardMemento(Map<String, CommandSpec> bindings) { }
