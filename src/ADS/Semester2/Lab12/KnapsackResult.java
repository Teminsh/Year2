package ADS.Semester2.Lab12;

import java.util.List;

public class KnapsackResult {
    private final int totalValue;
    private final List<Item> selectedItems;

    public KnapsackResult(int totalValue, List<Item> selectedItems) {
        this.totalValue = totalValue;
        this.selectedItems = selectedItems;
    }

    public int getTotalValue() {
        return totalValue;
    }

    public List<Item> getSelectedItems() {
        return selectedItems;
    }
}