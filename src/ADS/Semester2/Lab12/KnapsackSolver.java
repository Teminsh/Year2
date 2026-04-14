package ADS.Semester2.Lab12;

import java.util.ArrayList;
import java.util.List;

public class KnapsackSolver {

    public static KnapsackResult solve(int maxCapacity, List<Item> items) {
        int n = items.size();
        int[][] dp = new int[n + 1][maxCapacity + 1];

        for (int i = 1; i <= n; i++) {
            Item currentItem = items.get(i - 1);
            for (int w = 0; w <= maxCapacity; w++) {
                if (currentItem.getWeight() <= w) {
                    dp[i][w] = Math.max(
                            dp[i - 1][w],
                            dp[i - 1][w - currentItem.getWeight()] + currentItem.getValue()
                    );
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        int res = dp[n][maxCapacity];
        int w = maxCapacity;
        List<Item> selectedItems = new ArrayList<>();

        for (int i = n; i > 0 && res > 0; i--) {
            if (res != dp[i - 1][w]) {
                Item item = items.get(i - 1);
                selectedItems.add(item);
                res -= item.getValue();
                w -= item.getWeight();
            }
        }

        return new KnapsackResult(dp[n][maxCapacity], selectedItems);
    }
}