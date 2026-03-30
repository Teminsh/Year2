package OOP.Lab6.receivers;

public class VolumeSystem {
    private final int step;
    private int level;

    public VolumeSystem(int step) {
        this.step = step;
        this.level = 0;
    }

    public int step() {
        return step;
    }

    public int level() {
        return level;
    }

    public void setLevel(int level) {
        this.level = clamp(level);
    }

    public void increase() {
        level = clamp(level + step);
    }

    public void decrease() {
        level = clamp(level - step);
    }

    private int clamp(int v) {
        if (v < 0) return 0;
        if (v > 100) return 100;
        return v;
    }
}
