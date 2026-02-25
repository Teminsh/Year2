package OOP.Lab6.receivers;

public class MediaPlayer {
    private boolean running;

    public boolean isRunning() {
        return running;
    }

    public void launch() {
        running = true;
    }

    public void close() {
        running = false;
    }
}
