package by.it.group351004.student.lesson13;

import java.util.*;

public class GraphC {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String[] arr = scan.nextLine().split(",");

        boolean isNumeric = arr[0].trim().matches(".*\\d.*");

        int[] startVertex = new int[arr.length];
        int[] endVertex = new int[arr.length];

        for (int i = 0; i < arr.length; i++) {
            String[] current = arr[i].trim().split("->");
            startVertex[i] = isNumeric ? Integer.parseInt(current[0].trim()) : current[0].trim().charAt(0) - 'A';
            endVertex[i] = isNumeric ? Integer.parseInt(current[1].trim()) : current[1].trim().charAt(0) - 'A';
        }

        GraphUtil graph = new GraphUtil(getMax(startVertex, endVertex) + (isNumeric ? 0 : 1));

        for (int i = 0; i < arr.length; i++) {
            int indexStart = isNumeric ? startVertex[i] - 1 : startVertex[i];
            int indexEnd = isNumeric ? endVertex[i] - 1 : endVertex[i];
            graph.addOrientedEdge(indexStart, indexEnd);
            String temp = isNumeric ? String.valueOf(startVertex[i]) : String.valueOf((char) (startVertex[i] + 'A'));
            graph.setVertexNames(indexStart, temp);
            temp = isNumeric ? String.valueOf(endVertex[i]) : String.valueOf((char) (endVertex[i] + 'A'));
            graph.setVertexNames(indexEnd, temp);
        }

        List<List<Integer>> stronglyConnectedComponents = getStronglyConnectedComponents(graph);

        StringBuilder sb = new StringBuilder();
        for (List<Integer> component : stronglyConnectedComponents) {
            List<String> names = new ArrayList<>();
            for (int vertex : component) {
                names.add(graph.getVertexName(vertex));
            }
            Collections.sort(names);
            for (String name : names) {
                sb.append(name);
            }
            sb.append("\n");
        }
        System.out.print(sb.toString().trim());
    }

    static int getMax(int[] startVertex, int[] endVertex) {
        int size = Integer.MIN_VALUE;
        for (int i = 0; i < startVertex.length; i++) {
            if (startVertex[i] > size)
                size = startVertex[i];
            if (endVertex[i] > size)
                size = endVertex[i];
        }
        return size;
    }

    public static List<List<Integer>> getStronglyConnectedComponents(GraphUtil graph) {
        List<List<Integer>> result = new ArrayList<>();

        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[graph.vertexCount];
        for (int i = 0; i < graph.vertexCount; i++) {
            if (!visited[i]) {
                dfs(i, visited, stack, graph);
            }
        }

        List<List<Integer>> transposedGraph = transposeGraph(graph);

        Arrays.fill(visited, false);
        while (!stack.isEmpty()) {
            int vertex = stack.pop();
            if (!visited[vertex]) {
                List<Integer> component = new ArrayList<>();
                dfsTransposed(vertex, visited, transposedGraph, component);
                result.add(component);
            }
        }

        return result;
    }

    static void dfs(int vertex, boolean[] visited, Stack<Integer> stack, GraphUtil graph) {
        visited[vertex] = true;
        for (int neighbor : graph.graph.get(vertex)) {
            if (!visited[neighbor]) {
                dfs(neighbor, visited, stack, graph);
            }
        }
        stack.push(vertex);
    }

    static List<List<Integer>> transposeGraph(GraphUtil graph) {
        List<List<Integer>> transposedGraph = new ArrayList<>();
        for (int i = 0; i < graph.vertexCount; i++) {
            transposedGraph.add(new ArrayList<>());
        }
        for (int i = 0; i < graph.vertexCount; i++) {
            for (int neighbor : graph.graph.get(i)) {
                transposedGraph.get(neighbor).add(i);
            }
        }
        return transposedGraph;
    }

    static void dfsTransposed(int vertex, boolean[] visited, List<List<Integer>> transposedGraph, List<Integer> component) {
        visited[vertex] = true;
        component.add(vertex);
        for (int neighbor : transposedGraph.get(vertex)) {
            if (!visited[neighbor]) {
                dfsTransposed(neighbor, visited, transposedGraph, component);
            }
        }
    }
}
