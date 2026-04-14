package ADS.Semester2.Lab10;

public class SearchResult {
    private final int criticalFloor;
    private final int throwsCount;
    private final String log;

    public SearchResult(int criticalFloor, int throwsCount, String log) {
        this.criticalFloor = criticalFloor;
        this.throwsCount = throwsCount;
        this.log = log;
    }

    public int getCriticalFloor() {
        return criticalFloor;
    }

    public int getThrowsCount() {
        return throwsCount;
    }

    public String getLog() {
        return log;
    }
}
