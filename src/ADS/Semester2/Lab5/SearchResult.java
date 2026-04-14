package ADS.Semester2.Lab5;

import java.util.List;

public class SearchResult {
    private final List<Integer> positions;
    private final int comparisons;

    public SearchResult(List<Integer> positions, int comparisons) {
        this.positions = positions;
        this.comparisons = comparisons;
    }

    public List<Integer> getPositions() {
        return positions;
    }

    public int getComparisons() {
        return comparisons;
    }

    public boolean isFound() {
        return !positions.isEmpty();
    }
}