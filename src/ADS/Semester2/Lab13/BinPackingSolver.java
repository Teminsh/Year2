package ADS.Semester2.Lab13;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BinPackingSolver {

    public static List<Bin> solveFirstFitDecreasing(List<Integer> items, int binCapacity) {
        List<Integer> sortedItems = new ArrayList<>(items);
        sortedItems.sort(Collections.reverseOrder());

        List<Bin> bins = new ArrayList<>();

        for (int itemWeight : sortedItems) {
            if (itemWeight > binCapacity) {
                throw new IllegalArgumentException("Ошибка: предмет весом " + itemWeight +
                        " превышает максимальную вместимость ящика (" + binCapacity + ")");
            }

            boolean isPlaced = false;

            for (Bin bin : bins) {
                if (bin.canFit(itemWeight)) {
                    bin.addItem(itemWeight);
                    isPlaced = true;
                    break;
                }
            }

            if (!isPlaced) {
                Bin newBin = new Bin(binCapacity);
                newBin.addItem(itemWeight);
                bins.add(newBin);
            }
        }

        return bins;
    }
}