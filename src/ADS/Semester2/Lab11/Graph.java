package ADS.Semester2.Lab11;

public class Graph {

    private final int vertices;
    private final boolean[][] adjacency;

    public Graph(int vertices) {
        this.vertices = vertices;
        this.adjacency = new boolean[vertices][vertices];
    }

    public void addEdge(int u, int v) {
        if (u < 0 || v < 0 || u >= vertices || v >= vertices)
            throw new IllegalArgumentException("Вершина вне диапазона: " + u + ", " + v);
        adjacency[u][v] = true;
        adjacency[v][u] = true;
    }

    public boolean isAdjacent(int u, int v) {
        return adjacency[u][v];
    }

    public int getVertices() {
        return vertices;
    }

    public void printAdjacencyMatrix() {
        System.out.print("   ");
        for (int i = 0; i < vertices; i++) System.out.printf("%3d", i);
        System.out.println();
        for (int i = 0; i < vertices; i++) {
            System.out.printf("%3d", i);
            for (int j = 0; j < vertices; j++)
                System.out.printf("%3s", adjacency[i][j] ? "1" : ".");
            System.out.println();
        }
    }
}