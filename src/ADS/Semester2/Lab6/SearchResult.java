package ADS.Semester2.Lab6;

import java.util.List;

public class SearchResult {
    private final boolean found;
    private final List<Integer> positions;
    private final int comparisons;

    public SearchResult(boolean found, List<Integer> positions, int comparisons) {
        this.found = found;
        this.positions = positions;
        this.comparisons = comparisons;
    }

    public boolean isFound() {
        return found;
    }

    public List<Integer> getPositions() {
        return positions;
    }

    public int getComparisons() {
        return comparisons;
    }

    @Override
    public String toString() {
        if (!found) {
            return "Совпадений не найдено. Количество посимвольных сравнений: " + comparisons;
        }
        return "Совпадения найдены на позициях: " + positions +
                ". Количество посимвольных сравнений: " + comparisons;
    }
}
