package by.it.group351001.yakovenko.lesson13; // Указываем пакет, в котором находится класс

import java.util.*;

// Класс GraphC реализует алгоритм поиска сильных компонент связности
public class GraphC {
    // Список смежности графа
    private final Map<String, Set<String>> adjacencyList = new HashMap<>();
    // Список сильных компонент связности
    private final List<List<String>> strongComponents = new ArrayList<>();

    // Геттер для получения сильных компонент связности
    public List<List<String>> getStrongComponents() {
        return strongComponents;
    }

    // Конструктор класса GraphC, принимает строку с описанием графа
    public GraphC(String input) {
        var edges = input.split(", "); // Разделяем входную строку на рёбра
        for (var edge : edges) { // Обрабатываем каждое ребро
            var vertices = edge.split("->"); // Разделяем вершины
            var from = vertices[0]; // Начальная вершина
            var to = vertices[1]; // Конечная вершина
            // Добавляем вершины в список смежности
            adjacencyList.computeIfAbsent(from, _1_ -> new HashSet<>()).add(to);
        }
        calcStrongComponents(); // Вычисляем сильные компоненты связности
    }

    // Вложенный класс для построения транспонированного графа
    private static class ReverseGraph {
        private final Map<String, Set<String>> adjacencyList = new HashMap<>(); // Список смежности транспонированного графа
        private final List<String> topologicalOrder = new ArrayList<>(); // Топологический порядок вершин

        // Геттер для получения топологического порядка
        public List<String> getTopologicalOrder() {
            return topologicalOrder;
        }

        // Конструктор класса ReverseGraph, создаёт транспонированный граф
        public ReverseGraph(Map<String, Set<String>> adjacencyList) {
            for (var entry : adjacencyList.entrySet()) { // Перебираем рёбра оригинального графа
                var vertex = entry.getKey(); // Вершина
                for (var neighbor : entry.getValue()) // Соседи вершины
                    this.adjacencyList.computeIfAbsent(neighbor, _1_ -> new HashSet<>()).add(vertex);
            }
            calcTopologicalSort(); // Вычисляем топологический порядок
        }

        // Вычисление топологического порядка методом поиска в глубину (DFS)
        private void calcTopologicalSort() {
            var marked = new HashSet<String>(); // Множество посещённых вершин
            for (var vertex : adjacencyList.keySet()) // Перебираем все вершины
                if (!marked.contains(vertex)) // Если вершина не посещена
                    calcTopologicalSort(vertex, marked); // Рекурсивно обрабатываем вершину
            Collections.reverse(topologicalOrder); // Переворачиваем порядок для использования в дальнейшем
        }

        // Рекурсивный метод для топологической сортировки
        private void calcTopologicalSort(String vertex, HashSet<String> marked) {
            marked.add(vertex); // Помечаем вершину как посещённую
            var neighbors = adjacencyList.get(vertex); // Получаем список соседей вершины
            if (neighbors != null) // Если есть соседи
                for (var neighbor : neighbors) // Перебираем каждого соседа
                    if (!marked.contains(neighbor)) // Если сосед не посещён
                        calcTopologicalSort(neighbor, marked); // Рекурсивно обрабатываем его
            topologicalOrder.add(vertex); // Добавляем вершину в список топологического порядка
        }
    }

    // Вычисление сильных компонент связности графа
    private void calcStrongComponents() {
        var reverse = new ReverseGraph(adjacencyList); // Создаём транспонированный граф
        var topologicalOrder = reverse.getTopologicalOrder(); // Получаем топологический порядок вершин

        var marked = new HashSet<String>(); // Множество посещённых вершин
        for (var vertex : topologicalOrder) { // Перебираем вершины в порядке топологического сортировки
            if (!marked.contains(vertex)) { // Если вершина не посещена
                List<String> component = new ArrayList<>(); // Создаём новую компоненту
                dfs(vertex, marked, component); // Выполняем поиск в глубину
                strongComponents.add(component); // Добавляем компоненту в список
            }
        }
        Collections.reverse(strongComponents); // Переворачиваем список компонент для правильного порядка
        for (var component : strongComponents) // Сортируем вершины внутри каждой компоненты
            Collections.sort(component);
    }

    // Поиск в глубину для нахождения компонент связности
    private void dfs(String vertex, Set<String> marked, List<String> component) {
        marked.add(vertex); // Помечаем вершину как посещённую
        component.add(vertex); // Добавляем вершину в текущую компоненту
        var neighbors = adjacencyList.get(vertex); // Получаем список соседей
        if (neighbors != null) // Если есть соседи
            for (var neighbor : neighbors) // Перебираем каждого соседа
                if (!marked.contains(neighbor)) // Если сосед не посещён
                    dfs(neighbor, marked, component); // Рекурсивно обрабатываем соседа
    }

    // Главный метод программы
    public static void main(String[] args) {
        var scanner = new Scanner(System.in); // Создаём объект Scanner для чтения ввода
        var graph = new GraphC(scanner.nextLine()); // Создаём граф из входной строки

        var strongComponents = graph.getStrongComponents(); // Получаем сильные компоненты связности
        for (var strongComponent : strongComponents) { // Перебираем каждую компоненту
            for (var vertex : strongComponent) // Перебираем вершины в компоненте
                System.out.print(vertex); // Выводим вершину
            System.out.print('\n'); // Переход на новую строку после каждой компоненты
        }
    }
}

/*
*Эта задача направлена на нахождение сильных компонент связности
*в ориентированном графе. На вход подаётся строка, описывающая
* рёбра графа в формате "A->B, B->C".
* Программа строит граф, транспонирует его и выполняет поиск сильных
* компонент связности, используя алгоритм Косарайю. В результате
* выводится список компонент, где каждая компонента представляет собой
* группу связанных вершин.
* */