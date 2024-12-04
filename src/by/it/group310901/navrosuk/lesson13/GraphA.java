package by.it.group310901.navrosuk.lesson13;

import java.util.*;

//топологическая  сортировка

public class GraphA {

    private final SortedMap<String, SortedSet<String>> adjacencyList =
            new TreeMap<>(Collections.reverseOrder());

    private List<String> topologicalOrder = null;

    private boolean hasCycle = false;

    // Геттеры для топологического порядка и проверки наличия цикла
    public List<String> getTopologicalOrder() { return topologicalOrder; }
    public boolean hasCycle() { return hasCycle; }

    // Конструктор графа, принимающий строковое представление графа
    public GraphA(String input) {
        // Разделяем строки на ребра
        var edges = input.split(", ");
        for (var edge : edges) {
            // Разделяем каждое ребро на вершины
            var vertices = edge.split(" -> ");
            var from = vertices[0];
            var to = vertices[1];
            // Строим список смежности
            adjacencyList.computeIfAbsent(from, _1_ -> new TreeSet<>(Collections.reverseOrder())).add(to);
        }
        // Проверяем наличие цикла в графе
        checkCycle();
        // Если цикл не найден, считаем топологический порядок
        if (!hasCycle) {
            topologicalOrder = new ArrayList<>();
            calcTopologicalSort();
        }
    }

    // Метод для проверки цикла в графе
    private void checkCycle() {
        var marked = new HashSet<String>(); // Множество для помеченных вершин
        var onStack = new HashSet<String>(); // Множество для вершин на текущем пути
        // Для каждой вершины графа проверяем наличие цикла
        for (var vertex : adjacencyList.keySet())
            if (!marked.contains(vertex))
                checkCycle(vertex, marked, onStack);
    }

    // Рекурсивная функция для проверки цикла из каждой вершины
    private void checkCycle(String vertex, HashSet<String> marked, HashSet<String> onStack) {
        marked.add(vertex);  // Помечаем вершину как посещенную
        onStack.add(vertex);  // Добавляем вершину в текущий путь
        var neighbors = adjacencyList.get(vertex); // Получаем соседей вершины
        if (neighbors != null)
            for (var neighbor : neighbors) {
                // Если цикл уже найден, выходим
                if (hasCycle) return;

                // Если сосед еще не посещен, продолжаем поиск
                if (!marked.contains(neighbor))
                    checkCycle(neighbor, marked, onStack);
                    // Если сосед в текущем пути, это означает цикл
                else if (onStack.contains(neighbor))
                    hasCycle = true;  // Обнаружен цикл
            }
        onStack.remove(vertex); // Убираем вершину из текущего пути
    }

    // Метод для вычисления топологического порядка
    private void calcTopologicalSort() {
        var marked = new HashSet<String>();  // Множество для помеченных вершин
        // Для каждой вершины, если она еще не помечена, вызываем рекурсивный метод
        for (var vertex : adjacencyList.keySet())
            if (!marked.contains(vertex))
                calcTopologicalSort(vertex, marked);
        // После того как все вершины добавлены в топологический порядок, переворачиваем список
        Collections.reverse(topologicalOrder);
    }

    // Рекурсивная функция для обхода графа и формирования топологического порядка
    private void calcTopologicalSort(String vertex, HashSet<String> marked) {
        marked.add(vertex); // Помечаем вершину как посещенную
        var neighbors = adjacencyList.get(vertex); // Получаем соседей вершины
        if (neighbors != null)
            for (var neighbor : neighbors)
                if (!marked.contains(neighbor))  // Если сосед еще не помечен
                    calcTopologicalSort(neighbor, marked);
        // Добавляем вершину в список топологического порядка после обхода всех соседей
        topologicalOrder.add(vertex);
    }

    // Главный метод программы, запускающий работу с графом
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        // Чтение строки ввода и создание графа
        var graph = new GraphA(scanner.nextLine());

        // Получаем топологический порядок и выводим его
        var topologicalOrder = graph.getTopologicalOrder();
        for (var vertex : topologicalOrder)
            System.out.print(vertex + " ");
    }
}
