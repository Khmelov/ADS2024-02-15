package by.it.group310901.navrosuk.lesson13;

import java.util.*;
//наличие циклов


public class GraphB {

    private final Map<String, List<String>> adjacencyList = new HashMap<>();

    private boolean hasCycle = false;


    public boolean hasCycle() { return hasCycle; }

    // конструктор из строкового представления графа
    public GraphB(String input) {
        // Разделяем строку на отдельные рёбра графа
        var edges = input.split(", ");
        for (var edge : edges) {
            // Разделяем ребро на две вершины
            var vertices = edge.split(" -> ");
            var from = vertices[0];
            var to = vertices[1];
            // Строим список смежности для каждой вершины
            adjacencyList.computeIfAbsent(from, _1_ -> new ArrayList<>()).add(to);
        }

        checkCycle();
    }


    private void checkCycle() {

        var marked = new HashSet<String>();
        var onStack = new HashSet<String>();
        // Для каждой вершины графа проверяем наличие цикла
        for (var vertex : adjacencyList.keySet())
            if (!marked.contains(vertex))
                checkCycle(vertex, marked, onStack);
    }

    // Рекурсивная функция для проверки цикла из конкретной вершины
    private void checkCycle(String vertex, HashSet<String> marked, HashSet<String> onStack) {
        marked.add(vertex);  // Помечаем вершину как посещенную
        onStack.add(vertex);  // Добавляем вершину в текущий путь
        var neighbors = adjacencyList.get(vertex); // Получаем соседей вершины
        if (neighbors != null)
            for (var neighbor : neighbors) {
                // Если цикл уже найден, выходим
                if (hasCycle)
                    return;

                // Если сосед еще не посещен, продолжаем поиск
                if (!marked.contains(neighbor))
                    checkCycle(neighbor, marked, onStack);
                    // Если сосед уже в текущем пути, это означает, что найден цикл
                else if (onStack.contains(neighbor))
                    hasCycle = true;
            }
        onStack.remove(vertex); // Убираем вершину из текущего пути
    }

    // Главный метод программы, запускающий работу с графом
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        // Чтение строки с рёбрами графа и создание графа
        var graph = new GraphB(scanner.nextLine());
        // Вывод результата: если цикл обнаружен, выводим "yes", иначе "no"
        System.out.print(graph.hasCycle() ? "yes" : "no");
    }
}
