package ADS.Semester2.Lab9;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TSPSolver {

    private static final int INF = Integer.MAX_VALUE / 2;

    private final int[][] matrix;
    private final int n;
    private int minCost;
    private List<Integer> bestPath;

    public TSPSolver(int[][] matrix) {
        this.matrix = matrix;
        this.n = matrix.length;
        this.minCost = INF;
        this.bestPath = new ArrayList<>();
        solve();
    }

    private void solve() {
        if (n == 1) {
            minCost = 0;
            bestPath = new ArrayList<>(List.of(0, 0));
            return;
        }

        int size = 1 << n;
        int[][] dp     = new int[size][n];
        int[][] parent = new int[size][n];

        for (int[] row : dp)     Arrays.fill(row, INF);
        for (int[] row : parent) Arrays.fill(row, -1);

        dp[1][0] = 0;

        for (int mask = 1; mask < size; mask++) {
            if ((mask & 1) == 0) continue;

            for (int u = 0; u < n; u++) {
                if ((mask & (1 << u)) == 0) continue;
                if (dp[mask][u] == INF) continue;

                for (int v = 0; v < n; v++) {
                    if ((mask & (1 << v)) != 0) continue;
                    if (matrix[u][v] == 0) continue;

                    int newMask = mask | (1 << v);
                    int cost    = dp[mask][u] + matrix[u][v];
                    if (cost < dp[newMask][v]) {
                        dp[newMask][v]     = cost;
                        parent[newMask][v] = u;
                    }
                }
            }
        }

        int fullMask = size - 1;
        int lastCity = -1;

        for (int u = 1; u < n; u++) {
            if (matrix[u][0] == 0) continue;
            int cost = dp[fullMask][u] + matrix[u][0];
            if (cost < minCost) {
                minCost  = cost;
                lastCity = u;
            }
        }

        if (lastCity != -1) {
            bestPath = new ArrayList<>();
            reconstruct(parent, fullMask, lastCity);
            Collections.reverse(bestPath);
            bestPath.add(0);
        }
    }

    private void reconstruct(int[][] parent, int mask, int city) {
        bestPath.add(city);
        if (mask == 1) return;
        int prev = parent[mask][city];
        reconstruct(parent, mask ^ (1 << city), prev);
    }

    public boolean hasSolution() {
        return minCost < INF;
    }

    public int getMinCost() {
        return hasSolution() ? minCost : -1;
    }

    public List<Integer> getBestPath() {
        return Collections.unmodifiableList(bestPath);
    }
}
