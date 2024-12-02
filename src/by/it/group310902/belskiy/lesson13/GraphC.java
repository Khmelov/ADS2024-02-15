package by.it.group310902.belskiy.lesson13;

import java.util.*;

public class GraphC {

    private Map<String, ArrayList<String>> elements = new HashMap<>();
    public GraphC(Scanner scanner) {
        for (var connections : scanner.nextLine().split(", ")) {
            var nodes = connections.split("->");
            if (!elements.containsKey(nodes[0]))
                elements.put(nodes[0], new ArrayList<>());
            elements.get(nodes[0]).add(nodes[1]);
        }
        //input.close();
        scanner.close();
    }

    public static void main(String[] args) {
        System.out.print(String.join("\n",
                new GraphC(new Scanner(System.in)).findStronglyConnectedComponents()));
    }

    public ArrayList<String> findStronglyConnectedComponents() {
        var visited = new Stack<String>();
        var time = new HashMap<String, Integer>();
        var times = 0;
        for (var node : elements.keySet())
            if (!visited.contains(node))
                times = dfsVisit(node, visited, time, times);
        return getPaths(sortedVertices(time).toArray(new String[0]));
    }

    private int dfsVisit(String node, Stack<String> visited, HashMap<String, Integer> time, int times) {
        visited.add(node);
        if (elements.get(node) != null)
            for (var next_node : elements.get(node))
                if (!visited.contains(next_node))
                    times = dfsVisit(next_node, visited, time, ++times);
        time.put(node, times++);
        return times;
    }

    private ArrayList<String> sortedVertices(HashMap<String, Integer> time) {
        var entryList = new ArrayList<>(time.entrySet());
        entryList.sort((a, b) -> {
            int valueComparison = a.getValue().compareTo(b.getValue());
            return valueComparison == 0 ? a.getKey().compareTo(b.getKey()) : valueComparison;
        });
        var vertices = new ArrayList<String>();
        for (int i = entryList.size() - 1; i > -1; i--)
            vertices.add(entryList.get(i).getKey());
        return vertices;
    }

    private ArrayList<String> getPaths(String[] vertices) {
        var visited = new Stack<String>();
        var reversed = getReversedGraph();
        var result = new ArrayList<String>();
        for (var node : vertices)
            if (!visited.contains(node)) {
                var path = new ArrayList<String>();
                dfs(node, reversed, visited, path);
                path.sort(String::compareTo);
                result.add(String.join("", path));
            }
        return result;
    }

    public HashMap<String, ArrayList<String>> getReversedGraph() {
        var reversed = new HashMap<String, ArrayList<String>>();
        elements.forEach((node, neighbours) -> {
            neighbours.forEach(next -> {
                reversed.computeIfAbsent(next, k -> new ArrayList<>()).add(node);
            });
        });
        return reversed;
    }

    private void dfs(String node, HashMap<String, ArrayList<String>> graph, Stack<String> visited,
                     ArrayList<String> path) {
        visited.add(node);
        path.add(node);
        if (graph.get(node) != null)
            for (String next_node : graph.get(node))
                if (!visited.contains(next_node))
                    dfs(next_node, graph, visited, path);
    }
}
