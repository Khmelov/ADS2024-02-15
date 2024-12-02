package by.it.group351001.voronov.lesson13;

import java.util.*;

public class GraphA {
    static void performTopologicalSort(String currentNode, Map<String, ArrayList<String>> adjacencyList, Set<String> visitedNodes, Stack<String> sortedNodes) {
        visitedNodes.add(currentNode);

        if (adjacencyList.get(currentNode) != null) {
            for (String adjacentNode : adjacencyList.get(currentNode)) {
                if (!visitedNodes.contains(adjacentNode)) {
                    performTopologicalSort(adjacentNode, adjacencyList, visitedNodes, sortedNodes);
                }
            }
        }
        sortedNodes.push(currentNode);
    }

    static void topologicalSort(Map<String, ArrayList<String>> adjacencyList) {
        Stack<String> sortedNodes = new Stack<>();
        Set<String> visitedNodes = new HashSet<>();

        for (ArrayList<String> edges : adjacencyList.values()) {
            edges.sort(Comparator.reverseOrder());
        }

        for (String node : adjacencyList.keySet()) {
            if (!visitedNodes.contains(node)) {
                performTopologicalSort(node, adjacencyList, visitedNodes, sortedNodes);
            }
        }

        while (!sortedNodes.isEmpty()) {
            System.out.print(sortedNodes.pop() + " ");
        }
    }

    private static void readGraph(Map<String, ArrayList<String>> adjacencyList) {
        Scanner input = new Scanner(System.in);

        boolean inputEnded = false;
        while (!inputEnded) {
            String fromVertex = input.next();
            if (!adjacencyList.containsKey(fromVertex)) {
                adjacencyList.put(fromVertex, new ArrayList<>());
            }
            String arrow = input.next(); // "->" или другой разделитель, если есть
            String toVertex = input.next();
            if (toVertex.charAt(toVertex.length() - 1) == ',') {
                toVertex = toVertex.substring(0, toVertex.length() - 1);
            } else {
                inputEnded = true;
            }
            adjacencyList.get(fromVertex).add(toVertex);
        }
    }

    public static void main(String[] args) {
        Map<String, ArrayList<String>> adjacencyList = new HashMap<>();
        readGraph(adjacencyList);
        topologicalSort(adjacencyList);
    }
}
