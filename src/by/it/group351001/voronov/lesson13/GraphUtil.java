package by.it.group351001.voronov.lesson13;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraphUtil {
    final List<List<Integer>> adjacencyList; // Список смежности графа
    public final int totalVertices; // Общее количество вершин
    private String[] vertexLabels; // Массив меток вершин

    public GraphUtil(int vertexCount) {
        totalVertices = vertexCount;
        adjacencyList = new ArrayList<>();
        vertexLabels = new String[totalVertices];
        for (int i = 0; i < totalVertices; i++) {
            adjacencyList.add(new ArrayList<>());
        }
    }

    // Метод для добавления неориентированного ребра
    public void addUndirectedEdge(int vertexA, int vertexB) {
        adjacencyList.get(vertexA).add(vertexB);
        adjacencyList.get(vertexB).add(vertexA);
    }

    // Метод для добавления ориентированного ребра
    public void addDirectedEdge(int vertexA, int vertexB) {
        adjacencyList.get(vertexA).add(vertexB);
    }

    // Получить соседние вершины для заданной вершины
    public int[] getNeighbors(int vertex) {
        int[] neighbors = new int[adjacencyList.get(vertex).size()];
        int i = 0;
        for (int adjacentVertex : adjacencyList.get(vertex)) {
            neighbors[i++] = adjacentVertex;
        }
        Arrays.sort(neighbors);
        return neighbors;
    }

    // Получить метку вершины
    public String getVertexLabel(int vertex) {
        return vertexLabels[vertex];
    }

    // Установить метку для заданной вершины
    public void setVertexLabel(int vertex, String label) {
        this.vertexLabels[vertex] = label;
    }
}
