package by.it.group351001.voronov.lesson13;

import java.util.*;

public class GraphC {

    // Компаратор для сортировки строк в обратном лексикографическом порядке
    static class ReverseLexicalComparator implements Comparator<String> {
        public int compare(String s1, String s2) {
            return s2.compareTo(s1);
        }
    }

    public static void main(String[] args) {
        Map<String, ArrayList<String>> adjacencyList = new HashMap<>();
        Stack<String> finishOrder = new Stack<>();
        Set<String> visitedNodes = new HashSet<>();

        // Чтение входных данных
        Scanner scanner = new Scanner(System.in);
        String[] edges = scanner.nextLine().split(",");
        scanner.close();

        String sourceVertex;
        String destinationVertex;

        // Построение списка смежности
        for (String edge : edges) {
            String[] edgeData = edge.trim().split("");
            sourceVertex = edgeData[0];
            destinationVertex = edgeData[edgeData.length - 1];

            adjacencyList.putIfAbsent(sourceVertex, new ArrayList<>());
            adjacencyList.get(sourceVertex).add(destinationVertex);
        }

        // Сортировка списков смежности в обратном лексикографическом порядке
        for (ArrayList<String> adjacentVertices : adjacencyList.values()) {
            adjacentVertices.sort(new ReverseLexicalComparator());
        }

        // Выполнение первого обхода в глубину для получения порядка завершения
        for (String vertex : adjacencyList.keySet()) {
            if (!visitedNodes.contains(vertex)) {
                depthFirstSearch(adjacencyList, vertex, visitedNodes, finishOrder);
            }
        }

        // Построение транспонированного графа
        Map<String, ArrayList<String>> transposedAdjacencyList = new HashMap<>();
        for (String vertex : adjacencyList.keySet()) {
            ArrayList<String> neighbors = adjacencyList.get(vertex);
            for (String neighbor : neighbors) {
                transposedAdjacencyList.putIfAbsent(neighbor, new ArrayList<>());
                transposedAdjacencyList.get(neighbor).add(vertex);
            }
        }

        // Очистка множества посещённых вершин
        visitedNodes.clear();

        // Выполнение второго обхода в глубину для поиска компонент связности
        while (!finishOrder.isEmpty()) {
            String vertex = finishOrder.pop();
            if (!visitedNodes.contains(vertex)) {
                StringBuilder component = new StringBuilder();
                depthFirstSearch(transposedAdjacencyList, vertex, visitedNodes, component);

                char[] sortedComponent = component.toString().toCharArray();
                Arrays.sort(sortedComponent);
                System.out.println(sortedComponent);
            }
        }
    }

    // Обход в глубину с использованием стека для сохранения порядка завершения
    private static void depthFirstSearch(Map<String, ArrayList<String>> adjacencyList,
                                         String currentVertex, Set<String> visitedNodes, Stack<String> finishOrder) {
        visitedNodes.add(currentVertex);

        if (adjacencyList.get(currentVertex) != null) {
            for (String neighbor : adjacencyList.get(currentVertex)) {
                if (!visitedNodes.contains(neighbor)) {
                    depthFirstSearch(adjacencyList, neighbor, visitedNodes, finishOrder);
                }
            }
        }

        finishOrder.push(currentVertex);
    }

    // Обход в глубину с использованием StringBuilder для записи компоненты связности
    private static void depthFirstSearch(Map<String, ArrayList<String>> adjacencyList,
                                         String currentVertex, Set<String> visitedNodes, StringBuilder component) {
        visitedNodes.add(currentVertex);
        component.append(currentVertex);

        if (adjacencyList.get(currentVertex) != null) {
            for (String neighbor : adjacencyList.get(currentVertex)) {
                if (!visitedNodes.contains(neighbor)) {
                    depthFirstSearch(adjacencyList, neighbor, visitedNodes, component);
                }
            }
        }
    }
}
