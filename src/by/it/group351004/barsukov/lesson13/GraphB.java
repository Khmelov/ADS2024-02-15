package by.it.group351004.barsukov.lesson13;

import java.util.*;

public class GraphB {

    private static boolean isCycleHelper(
            Integer curVertex,
            HashMap<Integer, ArrayList<Integer>> graph,
            Integer[] info,
            Integer offset
    ) {
        info[curVertex - offset] = 0;

        for (var vertex: graph.getOrDefault(curVertex, new ArrayList<>())) {
            if (info[vertex - offset] == 0 ||
                info[vertex - offset] == -1 && isCycleHelper(vertex,graph,info,offset)) {
                return true;
            }
        }
        info[curVertex - offset] = 1;
        return false;
    }

    public static boolean isCycle(HashMap<Integer, ArrayList<Integer>> graph) {
        // -1 - didn't visit vertex
        // 0 - current dfs visiting
        // 1 - visited in previous dfs
        Integer[] info = new Integer[
                Collections.max(graph.keySet()) - Collections.min(graph.keySet()) + 1
                ];
        int offset = Collections.min(graph.keySet());


        Arrays.fill(info, -1);
        for (var vertex: graph.keySet()) {
            if (info[vertex - offset] == -1 && isCycleHelper(vertex, graph, info, offset)) {
                return true;
            }
        }
        return false;
    }

    public static HashMap<Integer, ArrayList<Integer>> getGraph() {
        Scanner scanner = new Scanner(System.in);
        HashMap<Integer, ArrayList<Integer>> graph = new HashMap<>();
        String[] nodes = scanner.nextLine().split(",");
        for (var node : nodes) {
            String[] vertexes = node.trim().split("->");
            int fromNode = Integer.parseInt(vertexes[0].trim());
            int toNode = Integer.parseInt(vertexes[1].trim());
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
        scanner.close();
        return graph;
    }
    public static void main(String[] args) {
        var graph = getGraph();
        System.out.println(isCycle(graph) ? "yes" : "no");
    }
}
