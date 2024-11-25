package by.it.group310902.pashkovich.lesson13;

import java.util.*;
public class GraphA {
    java

package by.it.group310902.pashkovich.lesson13;

import java.util.*;

public class GraphA {

    /**
     * Рекурсивная функция для выполнения топологической сортировки для одного узла.
     *
     * @param node    Текущий узел, который обрабатывается.
     * @param graph   Представление графа в виде списка смежности.
     * @param visited Множество узлов, которые уже посещены.
     * @param stack   Стек, в котором сохраняется результат топологической сортировки.
     */
    static void topologicalSortUnit(String node, Map<String, ArrayList<String>> graph, Set<String> visited, Stack<String> stack) {
        // Помечаем текущий узел как посещённый
        visited.add(node);

        // Если у текущего узла есть исходящие рёбра
        if (graph.get(node) != null) {
            // Рекурсивно посещаем всех непосещённых соседей
            for (String nextNode : graph.get(node)) {
                if (!visited.contains(nextNode)) {
                    topologicalSortUnit(nextNode, graph, visited, stack);
                }
            }
        }
        // Добавляем текущий узел в стек после обработки всех его соседей
        stack.push(node);
    }

    /**
     * Функция для выполнения топологической сортировки всего графа.
     *
     * @param graph Представление графа в виде списка смежности.
     */
    static void topologicalSort(Map<String, ArrayList<String>> graph) {
        Stack<String> stack = new Stack<>(); // Стек для хранения отсортированных узлов
        Set<String> visited = new HashSet<>(); // Множество для отслеживания посещённых узлов

        // Сортируем список смежности для каждого узла в порядке убывания (реверс)
        for (ArrayList<String> array : graph.values()) {
            array.sort(Comparator.reverseOrder());
        }

        // Выполняем топологическую сортировку для каждого непосещённого узла в графе
        for (String node : graph.keySet()) {
            if (!visited.contains(node)) {
                topologicalSortUnit(node, graph, visited, stack);
            }
        }

        // Выводим узлы в топологическом порядке (стек разворачивается для получения правильного порядка)
        while (!stack.isEmpty()) {
            System.out.print(stack.pop() + " ");
        }
    }

    /**
     * Функция для построения графа на основе пользовательского ввода.
     *
     * @param graph Представление графа в виде списка смежности.
     */
    private static void getGraph(Map<String, ArrayList<String>> graph) {
        Scanner in = new Scanner(System.in);

        boolean isEnd = false; // Флаг для определения завершения ввода
        while (!isEnd) {
            // Читаем начальную вершину ребра
            String vertexOut = in.next();
            // Если вершина ещё не добавлена в граф, добавляем её
            if (!graph.containsKey(vertexOut)) {
                graph.put(vertexOut, new ArrayList<>());
            }

            // Читаем символ ребра (например, "->" или "-")
            String edge = in.next();
            // Читаем конечную вершину ребра
            String vertexIn = in.next();

            // Проверяем, заканчивается ли входная строка запятой
            if (vertexIn.charAt(vertexIn.length() - 1) == ',') {
                vertexIn = vertexIn.substring(0, vertexIn.length() - 1);
            } else {
                isEnd = true; // Если запятой нет, считаем ввод завершённым
            }
            // Добавляем конечную вершину в список смежности начальной вершины
            graph.get(vertexOut).add(vertexIn);
        }
    }

    /**
     * Главный метод программы.
     */
    public static void main(String[] args) {
        Map<String, ArrayList<String>> graph = new HashMap<>(); // Граф в виде списка смежности
        getGraph(graph); // Построение графа на основе ввода
        topologicalSort(graph); // Выполнение топологической сортировки
    }
}

