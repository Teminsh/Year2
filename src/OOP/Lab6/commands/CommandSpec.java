package OOP.Lab6.commands;

import java.util.Map;

public record CommandSpec(String type, Map<String, Object> params) { }
