package by.it.group351004.barsukov.lesson13;

import java.util.*;

public class GraphC {

    private static HashMap<String, ArrayList<String>> graph, rgraph;
    private static HashMap<Integer, String> sccMap;
    private static HashSet<String> visited;
    private static ArrayList<String> order;

    private static void dfsForRGraph(String toVertex) {
        visited.add(toVertex);
        for (var fromVertex: rgraph.getOrDefault(toVertex, new ArrayList<>())) {
            if (!visited.contains(fromVertex)) {
                dfsForRGraph(fromVertex);
            }
        }
        order.add(toVertex);
    }

    private static void dfsForGraph(String toVertex, Integer curKey) {
        visited.add(toVertex);
        if (!sccMap.containsKey(curKey)) {
            sccMap.put(curKey, "");
        }
        sccMap.put(curKey, sccMap.get(curKey) + toVertex);
        for (var fromVertex: graph.getOrDefault(toVertex,new ArrayList<>())) {
            if (!visited.contains(fromVertex)) {
                dfsForGraph(fromVertex, curKey);
            }
        }
    }

    public static void addToGraph(String fromNode, String toNode) {
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

    public static void addToRGraph (String fromNode, String toNode) {
        if (!rgraph.containsKey(fromNode)) {
            rgraph.put(fromNode, new ArrayList<>());
        }
        if (!rgraph.get(fromNode).contains(toNode)) {
            rgraph.get(fromNode).add(toNode);
        }

        if (!rgraph.containsKey(toNode)) {
            rgraph.put(toNode, new ArrayList<>());
        }
    }

    public static void findScc() {
        visited = new HashSet<>();
        order = new ArrayList<>();
        for (var vertex: rgraph.keySet()) {
            if (!visited.contains(vertex)) {
                dfsForRGraph(vertex);
            }
        }

        visited.clear();
        sccMap = new HashMap<>();
        int count = 0;
        for (var vertex: order.reversed()) {
            if (!visited.contains(vertex)) {
                dfsForGraph(vertex, count++);
            }
        }

    }

    public static void readGraph() {
        Scanner scanner = new Scanner(System.in);

        String[] nodes = scanner.nextLine().split(",");
        for (var node : nodes) {
            String[] vertexes = node.trim().split("->");
            String fromNode = vertexes[0].trim();
            String toNode = vertexes[1].trim();
            addToGraph(fromNode, toNode);
            addToRGraph(toNode,fromNode);
        }

        scanner.close();
    }

    public static void main(String[] args) {
        graph = new HashMap<>();
        rgraph = new HashMap<>();
        readGraph();
        findScc();
        for (var scc: sccMap.values()) {
            char[] charArray = scc.toCharArray();
            Arrays.sort(charArray);
            System.out.println(new String(charArray));
        }
    }
}
