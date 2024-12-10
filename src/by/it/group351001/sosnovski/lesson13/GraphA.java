package by.it.group351001.sosnovski.lesson13;

import java.util.*;

public class GraphA {

    /**
     Программа реализует топологическую сортировку ориентированного графа.
     Топологическая сортировка используется для упорядочивания вершин в графе,
     где для каждого ребра из вершины 𝑢 в вершину 𝑣 вершина 𝑢 предшествует 𝑣.

     Краткое описание работы программы:

     Построение графа: Считываются вершины и связи между ними из пользовательского ввода.
     Топологическая сортировка: Алгоритм использует обход в глубину (DFS) для сортировки графа.
     Узлы сортируются так, чтобы для каждого ребра начальная вершина появлялась раньше конечной.
     Вывод результата: Напечатан порядок вершин после сортировки.
     */



    // Рекурсивный метод для выполнения DFS и добавления узлов в стек
    static void topologicalSortUnit(String node, Map<String, ArrayList<String>> graph, Set<String> visited, Stack<String> stack) {
        // Добавляем текущую вершину в множество посещенных
        visited.add(node);

        // Проверяем наличие соседей у текущей вершины
        if (graph.get(node) != null) {
            // Для каждого соседа вызываем рекурсивно topologicalSortUnit, если он не посещен
            for (String nextNode : graph.get(node)) {
                if (!visited.contains(nextNode)) {
                    topologicalSortUnit(nextNode, graph, visited, stack);
                }
            }
        }
        // После обработки всех соседей добавляем текущую вершину в стек
        stack.push(node);
    }

    // Метод для выполнения топологической сортировки графа
    static void topologicalSort(Map<String, ArrayList<String>> graph) {
        // Стек для хранения результата сортировки
        Stack<String> stack = new Stack<>();
        // Множество посещенных вершин
        Set<String> visited = new HashSet<>();

        // Сортируем соседей каждой вершины в обратном лексикографическом порядке
        for (ArrayList<String> array : graph.values()) {
            array.sort(Comparator.reverseOrder());
        }

        // Запускаем DFS для каждой вершины графа
        for (String node : graph.keySet()) {
            if (!visited.contains(node)) {
                topologicalSortUnit(node, graph, visited, stack);
            }
        }

        // Выводим вершины из стека (в порядке топологической сортировки)
        while (!stack.isEmpty()) {
            System.out.print(stack.pop() + " ");
        }
    }

    // Метод для ввода графа из консоли
    private static void getGraph(Map<String, ArrayList<String>> graph) {
        Scanner in = new Scanner(System.in);

        boolean isEnd = false; // Флаг окончания ввода
        while (!isEnd) {
            // Считываем исходящую вершину
            String vertexOut = in.next();
            if (!graph.containsKey(vertexOut)) {
                graph.put(vertexOut, new ArrayList<>()); // Создаем запись в графе
            }
            // Считываем название ребра (не используется в алгоритме)
            String edge = in.next();
            // Считываем входящую вершину
            String vertexIn = in.next();
            // Удаляем запятую, если она есть, и проверяем окончание ввода
            if (vertexIn.charAt(vertexIn.length() - 1) == ',') {
                vertexIn = vertexIn.substring(0, vertexIn.length() - 1);
            } else {
                isEnd = true; // Если нет запятой, значит ввод завершен
            }
            // Добавляем входящую вершину в список смежности исходящей вершины
            graph.get(vertexOut).add(vertexIn);
        }
    }

    public static void main(String[] args) {
        // Инициализируем граф как список смежности
        Map<String, ArrayList<String>> graph = new HashMap<>();
        // Считываем граф из консоли
        getGraph(graph);
        // Выполняем топологическую сортировку и выводим результат
        topologicalSort(graph);
    }
}
