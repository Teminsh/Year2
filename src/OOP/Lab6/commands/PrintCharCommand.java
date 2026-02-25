package OOP.Lab6.commands;

import OOP.Lab6.receivers.TextBuffer;

public class PrintCharCommand implements Command {
    private final TextBuffer buffer;
    private final char ch;

    public PrintCharCommand(TextBuffer buffer, char ch) {
        this.buffer = buffer;
        this.ch = ch;
    }

    @Override
    public CommandResult execute() {
        buffer.append(ch);
        return new CommandResult(buffer.snapshot());
    }

    @Override
    public CommandResult undo() {
        buffer.backspace();
        return new CommandResult(buffer.snapshot());
    }
}
