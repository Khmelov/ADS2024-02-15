package by.it.group351002.matsuev.lesson13;

import java.util.*;

public class GraphB {
    static void dfs(String node, Map<String, ArrayList<String>> graph, Map<String, Integer> visited, boolean[] hasCycle) {
        visited.put(node, 1);

        if (graph.get(node) != null) {
            for (String nextNode : graph.get(node)) {
                if (visited.get(nextNode) == null) {
                    dfs(nextNode, graph, visited, hasCycle);
                } else if (visited.get(nextNode) == 1) {
                    hasCycle[0] = true;
                }
            }
        }

        visited.replace(node, 2);
    }

    private static void getGraph(Map<String, ArrayList<String>> graph) {
        Scanner in = new Scanner(System.in);

        boolean isEnd = false;
        while (!isEnd) {
            String vertexOut = in.next();
            if (!graph.containsKey(vertexOut)) {
                graph.put(vertexOut, new ArrayList<>());
            }
            String edge = in.next();
            String vertexIn = in.next();
            if (vertexIn.charAt(vertexIn.length() - 1) == ',') {
                vertexIn = vertexIn.substring(0, vertexIn.length() - 1);
            } else {
                isEnd = true;
            }
            graph.get(vertexOut).add(vertexIn);
        }
    }

    public static void main(String[] args) {
        Map<String, ArrayList<String>> graph = new HashMap<>();
        Map<String, Integer> visited = new HashMap<>();
        boolean[] hasCycle = new boolean[]{false};
        getGraph(graph);
        for (String node : graph.keySet()) {
            if (visited.get(node) == null) {
                dfs(node, graph, visited, hasCycle);
            }
        }
        System.out.print(hasCycle[0] ? "yes" : "no");
    }
}
