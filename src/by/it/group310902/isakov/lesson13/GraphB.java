package by.it.group310902.isakov.lesson13;

import java.util.*;

public class GraphB {

    public static void main(String[] args) {

        // Создаем структуру данных для представления графа:
        // ключи - вершины (строки), значения - список смежных вершин (списки строк)
        Map<String, List<String>> graph = new HashMap<>();

        // Открываем сканер для считывания данных с консоли
        try (Scanner scanner = new Scanner(System.in)) {

            // Считываем строку, содержащую список рёбер графа
            String input = scanner.nextLine();
            // Разбиваем строку на отдельные вершины и рёбра по разделителю ","
            String[] nodes = input.split("\\s*,\\s*");

            // Проходим по каждому элементу (рёбру) в списке
            for (String node : nodes) {
                // Разбиваем каждое ребро на пару вершин (например, "A->B")
                String[] vertexes = node.split("\\s*->\\s*");

                // Если вершина уже существует в графе, добавляем в список её смежных вершин
                if (graph.containsKey(vertexes[0])) {
                    graph.get(vertexes[0]).add(vertexes[1]);
                    continue;
                }

                // Если вершины еще нет, создаем новый список смежных вершин
                List<String> list = new ArrayList<>();
                list.add(vertexes[1]);
                graph.put(vertexes[0], list);
            }
        }

        // Проверяем наличие цикла в графе
        if (checkLoop(graph)) {
            System.out.println("yes"); // Если цикл найден, выводим "yes"
        }
        else {
            System.out.println("no"); // Если цикла нет, выводим "no"
        }

    }

    // Метод для проверки наличия цикла в графе
    public static boolean checkLoop(Map<String, List<String>> graph) {

        // Переменная для хранения состояния: найден ли цикл
        boolean loop = false;

        // Проходим по всем вершинам графа
        for (String vertex : graph.keySet()) {
            // Если в процессе топологической сортировки обнаружен цикл, устанавливаем loop в true
            loop = loop || topologicalSort(graph, new HashSet<>(), vertex);
        }

        return loop; // Возвращаем результат: true - цикл найден, false - нет
    }

    // Метод для выполнения топологической сортировки с проверкой на цикл
    private static boolean topologicalSort(Map<String, List<String>> graph, Set<String> visited, String curr) {

        // Добавляем текущую вершину в список посещенных
        visited.add(curr);
        boolean loop = false;

        // Проверяем смежные вершины для текущей вершины
        if (graph.get(curr) != null) {
            for (String next : graph.get(curr)) {
                // Если соседняя вершина не была посещена, продолжаем сортировку рекурсивно
                if (!visited.contains(next)) {
                    loop = loop || topologicalSort(graph, new HashSet<>(visited), next);
                }
                // Если соседняя вершина уже была посещена, значит цикл найден
                else {
                    loop = true;
                }
            }
        }

        return loop; // Возвращаем результат: true - цикл найден, false - нет
    }
}
