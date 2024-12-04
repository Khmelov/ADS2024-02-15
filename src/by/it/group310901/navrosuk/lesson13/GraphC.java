package by.it.group310901.navrosuk.lesson13;

import java.util.*;
//сильные компоненты
public class GraphC {


    private final Map<String, Set<String>> adjacencyList = new HashMap<>();

    private final List<List<String>> strongComponents = new ArrayList<>();


    public List<List<String>> getStrongComponents() { return strongComponents; }

    // Граф в строки
    public GraphC(String input) {
        // Разделяем ввод на рёбра
        var edges = input.split(", ");
        for (var edge : edges) {
            var vertices = edge.split("->");
            var from = vertices[0];  // Исходная вершина
            var to = vertices[1];    // Целевая вершина
            // Строим список смежности для каждой вершины
            adjacencyList.computeIfAbsent(from, _1_ -> new HashSet<>()).add(to);
        }
        calcStrongComponents();  // После построения графа вычисляем сильные компоненты
    }

    // Вложенный класс для представления обратного графа
    private static class ReverseGraph {
        private final Map<String, Set<String>> adjacencyList = new HashMap<>();
        private final List<String> topologicalOrder = new ArrayList<>();

        // Геттер для получения топологического порядка
        public List<String> getTopologicalOrder() { return topologicalOrder; }

        // Конструктор обратного графа, переворачивает рёбра в обратном направлении
        public ReverseGraph(Map<String, Set<String>> adjacencyList) {
            // Переворачиваем все рёбра
            for (var entry : adjacencyList.entrySet()) {
                var vertex = entry.getKey();
                for (var neighbor : entry.getValue())
                    this.adjacencyList.computeIfAbsent(neighbor, _1_ -> new HashSet<>()).add(vertex);
            }
            calcTopologicalSort();  // Выполняем топологическую сортировку
        }

        // Метод для вычисления топологического порядка вершин
        private void calcTopologicalSort() {
            var marked = new HashSet<String>();
            for (var vertex : adjacencyList.keySet())
                if (!marked.contains(vertex))
                    calcTopologicalSort(vertex, marked);
            Collections.reverse(topologicalOrder);  // Переворачиваем порядок для правильного обхода
        }

        // Рекурсивный метод для вычисления топологического порядка
        private void calcTopologicalSort(String vertex, HashSet<String> marked) {
            marked.add(vertex);  // Помечаем вершину как посещённую
            var neighbors = adjacencyList.get(vertex);  // Получаем соседей вершины
            if (neighbors != null)
                for (var neighbor : neighbors)
                    if (!marked.contains(neighbor))
                        calcTopologicalSort(neighbor, marked);  // Рекурсивно обходим соседей
            topologicalOrder.add(vertex);  // Добавляем вершину в топологический порядок
        }
    }

    // Метод для вычисления сильных компонент связности
    private void calcStrongComponents() {
        var reverse = new ReverseGraph(adjacencyList);  // Создаем обратный граф
        var topologicalOrder = reverse.getTopologicalOrder();  // Получаем топологический порядок

        var marked = new HashSet<String>();  // Множество для помеченных вершин
        // Проходим по топологическому порядку
        for (var vertex : topologicalOrder) {
            if (!marked.contains(vertex)) {  // Если вершина ещё не посещена
                List<String> component = new ArrayList<>();  // Список для текущей компоненты
                dfs(vertex, marked, component);  // Запускаем DFS для компоненты
                strongComponents.add(component);  // Добавляем компоненту в список сильных компонент
            }
        }
        Collections.reverse(strongComponents);  // Переворачиваем список компонент
        // Сортируем вершины в каждой компоненте
        for (var component : strongComponents)
            Collections.sort(component);
    }

    // Рекурсивный метод для выполнения поиска в глубину (DFS) и сбора компоненты связности
    private void dfs(String vertex, Set<String> marked, List<String> component) {
        marked.add(vertex);  // Помечаем вершину как посещённую
        component.add(vertex);  // Добавляем вершину в компоненту
        var neighbors = adjacencyList.get(vertex);  // Получаем соседей вершины
        if (neighbors != null)
            for (var neighbor : neighbors)
                if (!marked.contains(neighbor))  // Если сосед не посещен
                    dfs(neighbor, marked, component);  // Рекурсивно продолжаем DFS для соседа
    }

    // Главный метод программы
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        var graph = new GraphC(scanner.nextLine());  // Чтение графа с консоли

        var strongComponents = graph.getStrongComponents();  // Получение сильных компонент
        // Выводим все сильные компоненты
        for (var strongComponent : strongComponents) {
            for (var vertex : strongComponent)
                System.out.print(vertex);  // Печать каждой вершины компоненты
            System.out.print('\n');  // Печать новой строки после каждой компоненты
        }
    }
}
