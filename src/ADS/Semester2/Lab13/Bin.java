package ADS.Semester2.Lab13;

import java.util.ArrayList;
import java.util.List;

public class Bin {
    private final int capacity;
    private int currentLoad;
    private final List<Integer> items;

    public Bin(int capacity) {
        this.capacity = capacity;
        this.currentLoad = 0;
        this.items = new ArrayList<>();
    }

    public boolean canFit(int weight) {
        return (currentLoad + weight) <= capacity;
    }

    public void addItem(int weight) {
        if (canFit(weight)) {
            items.add(weight);
            currentLoad += weight;
        } else {
            throw new IllegalArgumentException("Предмет слишком велик для этого ящика!");
        }
    }

    @Override
    public String toString() {
        return "Ящик [занято: " + currentLoad + "/" + capacity + ", предметы: " + items + "]";
    }
}