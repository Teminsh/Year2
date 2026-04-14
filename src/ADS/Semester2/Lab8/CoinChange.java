package ADS.Semester2.Lab8;

import java.util.Arrays;

public class CoinChange {
    public static long countWays(int amount, int[] coins) {
        if (amount < 0)
            throw new IllegalArgumentException("Сумма должна быть неотрицательной");
        if (coins == null || coins.length == 0)
            throw new IllegalArgumentException("Массив монет не должен быть пустым");

        long[] dp = new long[amount + 1];
        dp[0] = 1;

        for (int coin : coins) {
            for (int j = coin; j <= amount; j++) {
                dp[j] += dp[j - coin];
            }
        }

        return dp[amount];
    }

    public static void printAllCombinations(int amount, int[] coins) {
        System.out.println("Все комбинации для суммы " + amount
                + " из монет " + Arrays.toString(coins) + ":");
        int[] chosen = new int[amount + 1];
        printHelper(amount, coins, 0, chosen, 0);
    }

    private static void printHelper(int remaining, int[] coins,
                                    int startIdx, int[] chosen, int count) {
        if (remaining == 0) {
            StringBuilder sb = new StringBuilder("  [");
            for (int i = 0; i < count; i++) {
                if (i > 0) sb.append(", ");
                sb.append(chosen[i]);
            }
            sb.append("]");
            System.out.println(sb);
            return;
        }
        for (int i = startIdx; i < coins.length; i++) {
            if (coins[i] <= remaining) {
                chosen[count] = coins[i];
                printHelper(remaining - coins[i], coins, i, chosen, count + 1);
            }
        }
    }
}