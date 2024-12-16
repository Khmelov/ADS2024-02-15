package by.it.group351004.barsukov.lesson13;

import java.util.*;

public class GraphA {

    private static void topologySortHelper(
        HashSet<String> visited,
        String fromVertex,
        HashMap<String, ArrayList<String>> graph,
        Stack<String> order
    ) {
        for (var toVertex : graph.getOrDefault(fromVertex, new ArrayList<>())) {
            if (!visited.contains(toVertex)) {
                visited.add(toVertex);
                topologySortHelper(visited,toVertex,graph,order);
            }
        }
        order.push(fromVertex);
    }

    public static String topologySort(HashMap<String, ArrayList<String>> graph) {
        HashSet<String> visited = new HashSet<>();
        Stack<String> order = new Stack<>();

        for (var array : graph.values()) {
            array.sort(Comparator.reverseOrder());
        }

        for (var toVertex: graph.keySet()) {
            if (!visited.contains(toVertex)) {
                visited.add(toVertex);
                topologySortHelper(visited, toVertex, graph, order);
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        while (!order.empty()) {
            stringBuilder.append(order.pop()).append(" ");
        }
        return stringBuilder.toString();
    }

    public static HashMap<String, ArrayList<String>> getGraph() {
        Scanner scanner = new Scanner(System.in);
        HashMap<String, ArrayList<String>> graph = new HashMap<>();
        String[] nodes = scanner.nextLine().split(",");
        for (var node : nodes) {
            String[] vertexes = node.trim().split("->");
            String fromNode = vertexes[0].trim();
            String toNode = vertexes[1].trim();
            if (!graph.containsKey(fromNode)) {
                graph.put(fromNode, new ArrayList<>());
            }
            if (!graph.get(fromNode).contains(toNode)) {
                graph.get(fromNode).add(toNode);
            }

            if (!graph.containsKey(toNode)) {
                graph.put(toNode, new ArrayList<>());
            }

        }
        return graph;
    }


    public static void main(String[] args) {
        HashMap<String, ArrayList<String>> graph = getGraph();
        System.out.print(topologySort(graph));
    }

}
