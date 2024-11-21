package by.it.group351003.kolbeko.lesson13;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraphUtil {
    final List<List<Integer>> graph;
    public final int vertexCount;

    public GraphUtil(int count) {
        vertexCount = count;
        graph = new ArrayList<>();
        for (int i = 0; i < vertexCount; i++) {
            graph.add(new ArrayList<>());
        }
    }

    public void addOrientedEdge(int a, int b) {
        graph.get(a).add(b);
    }

    public int[] getNeighbors(int vertex) {
        int[] neighbors = new int[graph.get(vertex).size()];
        int i = 0;
        for (int index : graph.get(vertex)) {
            neighbors[i++] = index;
        }
        Arrays.sort(neighbors);
        return neighbors;
    }
}
