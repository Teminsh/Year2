package OOP.Lab6.commands;

import OOP.Lab6.receivers.VolumeSystem;

public class VolumeDownCommand implements Command {
    private final VolumeSystem volume;
    private Integer prev;

    public VolumeDownCommand(VolumeSystem volume) {
        this.volume = volume;
    }

    @Override
    public CommandResult execute() {
        prev = volume.level();
        volume.decrease();
        return new CommandResult("volume decreased -" + volume.step() + "%");
    }

    @Override
    public CommandResult undo() {
        if (prev == null) return new CommandResult("nothing to undo");
        volume.setLevel(prev);
        return new CommandResult("volume restored");
    }
}
