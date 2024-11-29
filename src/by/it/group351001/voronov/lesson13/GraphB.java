package by.it.group351001.voronov.lesson13;

import java.util.*;

public class GraphB {
    static boolean[] visitedNodes;
    static boolean[] recursionStack;

    public static boolean hasCycle(GraphUtil graph) {
        visitedNodes = new boolean[graph.totalVertices];
        recursionStack = new boolean[graph.totalVertices];

        for (int vertex = 0; vertex < graph.totalVertices; vertex++) {
            if (!visitedNodes[vertex] && hasCycleUtil(graph, vertex))
                return true;
        }

        return false;
    }

    static boolean hasCycleUtil(GraphUtil graph, int currentVertex) {
        if (!visitedNodes[currentVertex]) {
            visitedNodes[currentVertex] = true;
            recursionStack[currentVertex] = true;

            for (int neighborVertex : graph.getNeighbors(currentVertex)) {
                if (!visitedNodes[neighborVertex] && hasCycleUtil(graph, neighborVertex)) return true;
                if (recursionStack[neighborVertex]) return true;
            }
        }

        recursionStack[currentVertex] = false;
        return false;
    }

    static int getMaxVertexValue(int[] startVertices, int[] endVertices) {
        int maxVertex = Integer.MIN_VALUE;
        for (int i = 0; i < startVertices.length; i++) {
            if (startVertices[i] > maxVertex)
                maxVertex = startVertices[i];
            if (endVertices[i] > maxVertex)
                maxVertex = endVertices[i];
        }
        return maxVertex;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] edges = scanner.nextLine().split(",");

        int[] startVertices = new int[edges.length];
        int[] endVertices = new int[edges.length];
        for (int i = 0; i < edges.length; i++) {
            String[] edgeData = edges[i].trim().split(" ");
            startVertices[i] = Integer.parseInt(edgeData[0]);
            endVertices[i] = Integer.parseInt(edgeData[edgeData.length - 1]);
        }

        GraphUtil graph = new GraphUtil(getMaxVertexValue(startVertices, endVertices) + 1);
        for (int i = 0; i < edges.length; i++)
            graph.addDirectedEdge(startVertices[i], endVertices[i]);

        System.out.println(hasCycle(graph) ? "yes" : "no");
        scanner.close();
    }
}
