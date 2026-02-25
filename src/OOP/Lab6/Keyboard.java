package OOP.Lab6;

import OOP.Lab6.commands.Command;
import OOP.Lab6.commands.CommandRegistry;
import OOP.Lab6.commands.CommandResult;
import OOP.Lab6.commands.CommandSpec;
import OOP.Lab6.io.DualOutput;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class Keyboard {
    //region fields
    private final Map<String, CommandSpec> bindings = new HashMap<>();
    private final Deque<Command> undoStack = new ArrayDeque<>();
    private final Deque<Command> redoStack = new ArrayDeque<>();

    private final CommandRegistry registry;
    private final DualOutput output;
    //endregion

    public Keyboard(CommandRegistry registry, DualOutput output) {
        this.registry = registry;
        this.output = output;
    }

    //region bindings
    public void bind(String keyCombo, String commandType, Map<String, Object> params) {
        bindings.put(keyCombo, new CommandSpec(commandType, params));
    }

    public Map<String, CommandSpec> getBindingsSnapshot() {
        return Map.copyOf(bindings);
    }
    //endregion

    //region execution
    public void press(String keyCombo) {
        CommandSpec spec = bindings.get(keyCombo);
        if (spec == null) {
            output.log(keyCombo, "unbound key");
            return;
        }

        try {
            Command command = registry.create(spec);
            CommandResult res = command.execute();

            undoStack.push(command);
            redoStack.clear();

            output.log(keyCombo, res.outputLine());
        } catch (Exception e) {
            output.log(keyCombo, "execution error: " + e.getMessage());
        }
    }

    public void undo() {
        if (undoStack.isEmpty()) {
            output.log("undo", "nothing to undo");
            return;
        }

        try {
            Command command = undoStack.pop();
            CommandResult res = command.undo();
            redoStack.push(command);
            output.log("undo", res.outputLine());
        } catch (Exception e) {
            output.log("undo", "error during undo: " + e.getMessage());
        }
    }

    public void redo() {
        if (redoStack.isEmpty()) {
            output.log("redo", "nothing to redo");
            return;
        }

        try {
            Command command = redoStack.pop();
            CommandResult res = command.execute();
            undoStack.push(command);
            output.log("redo", res.outputLine());
        } catch (Exception e) {
            output.log("redo", "error during redo: " + e.getMessage());
        }
    }
    //endregion

    public KeyboardMemento createMemento() {
        return new KeyboardMemento(getBindingsSnapshot());
    }

    public void restoreFrom(KeyboardMemento memento) {
        bindings.clear();
        bindings.putAll(memento.bindings());
        undoStack.clear();
        redoStack.clear();
    }
}