package by.it.group351001.shimanchuk.lesson13;

/*
 * Класс GraphA реализует топологическую сортировку ориентированного графа.
 * - Ввод графа осуществляется через консоль в формате: "вершина-источник ребро вершина-назначения".
 * - Граф представлен в виде списка смежности.
 * - Выполняется сортировка смежных вершин в обратном порядке для корректной обработки.
 * - Результат сортировки выводится в консоль.
 */

import java.util.*;

public class GraphA {

    // Вспомогательный метод для выполнения топологической сортировки одного узла
    static void topologicalSortUnit(String node, Map<String, ArrayList<String>> graph, Set<String> visited, Stack<String> stack) {
        visited.add(node); // Добавляем текущий узел в посещенные

        // Если у узла есть смежные вершины
        if (graph.get(node) != null) {
            // Проходимся по всем смежным узлам
            for (String nextNode : graph.get(node)) {
                if (!visited.contains(nextNode)) { // Если узел ещё не посещён
                    topologicalSortUnit(nextNode, graph, visited, stack); // Рекурсивный вызов для смежного узла
                }
            }
        }
        // После обработки всех соседей добавляем узел в стек
        stack.push(node);
    }

    // Основной метод для выполнения топологической сортировки графа
    static void topologicalSort(Map<String, ArrayList<String>> graph) {
        Stack<String> stack = new Stack<>(); // Стек для хранения результата сортировки
        Set<String> visited = new HashSet<>(); // Множество для отслеживания посещенных узлов

        // Сортируем списки смежных вершин в обратном порядке (для удобства обработки)
        for (ArrayList<String> array : graph.values()) {
            array.sort(Comparator.reverseOrder());
        }

        // Запускаем топологическую сортировку для всех узлов графа
        for (String node : graph.keySet()) {
            if (!visited.contains(node)) { // Если узел ещё не был посещён
                topologicalSortUnit(node, graph, visited, stack); // Выполняем сортировку для узла
            }
        }

        // Выводим содержимое стека (результат топологической сортировки)
        while (!stack.isEmpty()) {
            System.out.print(stack.pop() + " ");
        }
    }

    // Метод для ввода графа с консоли
    private static void getGraph(Map<String, ArrayList<String>> graph) {
        Scanner in = new Scanner(System.in); // Сканер для чтения ввода

        boolean isEnd = false; // Флаг завершения ввода
        while (!isEnd) {
            String vertexOut = in.next(); // Чтение вершины-источника
            if (!graph.containsKey(vertexOut)) {
                graph.put(vertexOut, new ArrayList<>()); // Если вершина ещё не добавлена, добавляем её в граф
            }
            String edge = in.next(); // Чтение ребра (не используется, но требуется для формата ввода)
            String vertexIn = in.next(); // Чтение вершины-назначения
            if (vertexIn.charAt(vertexIn.length() - 1) == ',') { // Проверяем наличие запятой
                vertexIn = vertexIn.substring(0, vertexIn.length() - 1); // Убираем запятую
            } else {
                isEnd = true; // Если запятой нет, значит, это конец ввода
            }
            graph.get(vertexOut).add(vertexIn); // Добавляем ребро в граф
        }
    }

    public static void main(String[] args) {
        Map<String, ArrayList<String>> graph = new HashMap<>(); // Создаем граф
        getGraph(graph); // Заполняем граф с консоли
        topologicalSort(graph); // Выполняем топологическую сортировку
    }
}