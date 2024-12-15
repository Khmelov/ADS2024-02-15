package by.it.group351001.orsik.lesson13;

import java.util.*;

public class GraphA {

    public static void main(String[] args) {

        Map<String, List<String>> graph = new HashMap<>();

        try (Scanner scanner = new Scanner(System.in)) {

            String input = scanner.nextLine();
            String[] nodes = input.split("\\s*,\\s*");

            for (String node : nodes) {
                String[] vertexes = node.split("\\s*->\\s*");
                if (graph.containsKey(vertexes[0])) {
                    graph.get(vertexes[0]).add(vertexes[1]);
                    continue;
                }

                List<String> list = new ArrayList<>();
                list.add(vertexes[1]);
                graph.put(vertexes[0], list);
            }
        }

        for (List<String> list : graph.values()) {
            list.sort(Comparator.reverseOrder());
        }

        Stack<String> stack = topologicalSort(graph);

        while (!stack.empty()) {
            System.out.printf("%s ", stack.pop());
        }

    }

    // Метод для выполнения топологической сортировки
    public static Stack<String> topologicalSort(Map<String, List<String>> graph) {
        Stack<String> result = new Stack<>(); // Стек для хранения отсортированных узлов
        Set<String> visited = new HashSet<>(); // Множество для отслеживания посещенных узлов
        for (String vertex : graph.keySet()) {
            // Если узел еще не посещен, вызываем вспомогательный метод
            if (!visited.contains(vertex)) {
                topologicalSort(graph, visited, vertex, result);
            }
        }
        return result; // Возвращаем отсортированные узлы
    }

    // Вспомогательный метод для рекурсивного обхода графа
    private static void topologicalSort(Map<String, List<String>> graph, Set<String> visited, String curr, Stack<String> result) {
        visited.add(curr); // Помечаем текущий узел как посещенный

        // Если у текущего узла есть соседи, рекурсивно обходим их
        if (graph.get(curr) != null) {
            for (String next : graph.get(curr)) {
                // Если соседний узел еще не посещен, продолжаем обход
                if (!visited.contains(next)) {
                    topologicalSort(graph, visited, next, result);
                }
            }
        }

        // Добавляем текущий узел в стек после посещения всех его соседей
        result.push(curr);
    }
}