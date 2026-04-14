package ADS.Semester2.Lab11;

public class GraphColoring {

    private final Graph graph;
    private int[] colors;
    private int chromaticNumber;

    public GraphColoring(Graph graph) {
        this.graph = graph;
        this.colors = new int[graph.getVertices()];
        this.chromaticNumber = 0;
    }

    private boolean isSafe(int vertex, int color) {
        for (int i = 0; i < graph.getVertices(); i++) {
            if (graph.isAdjacent(vertex, i) && colors[i] == color)
                return false;
        }
        return true;
    }

    private boolean solve(int vertex, int numColors) {
        if (vertex == graph.getVertices()) return true;

        for (int color = 1; color <= numColors; color++) {
            if (isSafe(vertex, color)) {
                colors[vertex] = color;
                if (solve(vertex + 1, numColors)) return true;
                colors[vertex] = 0;
            }
        }
        return false;
    }

    public int[] findColoring() {
        int n = graph.getVertices();
        for (int k = 1; k <= n; k++) {
            colors = new int[n];
            if (solve(0, k)) {
                chromaticNumber = k;
                return colors;
            }
        }
        return colors;
    }

    public int getChromaticNumber() {
        return chromaticNumber;
    }

    public void printResult() {
        int[] result = findColoring();
        String[] names = {"Красный", "Синий", "Зелёный", "Жёлтый",
                "Оранжевый", "Фиолетовый", "Белый", "Чёрный"};
        System.out.println("Хроматическое число χ(G) = " + chromaticNumber);
        System.out.println("Раскраска вершин:");
        for (int i = 0; i < result.length; i++) {
            String colorName = (result[i] - 1 < names.length)
                    ? names[result[i] - 1] : "Цвет-" + result[i];
            System.out.printf("  Вершина %-3d → Цвет %-2d (%s)%n",
                    i, result[i], colorName);
        }
        boolean valid = validate(result);
        System.out.println("Проверка корректности: " + (valid ? "✓ ОК" : "✗ ОШИБКА"));
    }

    public boolean validate(int[] coloring) {
        int n = graph.getVertices();
        for (int u = 0; u < n; u++)
            for (int v = u + 1; v < n; v++)
                if (graph.isAdjacent(u, v) && coloring[u] == coloring[v])
                    return false;
        return true;
    }
}
