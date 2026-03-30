package OOP.Lab6.commands;

public interface Command {
    CommandResult execute();
    CommandResult undo();
}
