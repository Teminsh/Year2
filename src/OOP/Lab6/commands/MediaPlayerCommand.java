package OOP.Lab6.commands;

import OOP.Lab6.receivers.MediaPlayer;

public class MediaPlayerCommand implements Command {
    private final MediaPlayer player;
    private Boolean prev;

    public MediaPlayerCommand(MediaPlayer player) {
        this.player = player;
    }

    @Override
    public CommandResult execute() {
        prev = player.isRunning();
        player.launch();
        return new CommandResult("media player launched");
    }

    @Override
    public CommandResult undo() {
        if (prev == null) return new CommandResult("nothing to undo");
        if (!prev) {
            player.close();
            return new CommandResult("media player closed");
        }
        return new CommandResult("media player kept running");
    }
}
