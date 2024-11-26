package by.it.group351001.anton_matsiushenka.lesson13; // Указываем пакет, в котором находится данный класс

import java.util.*;

public class GraphA { // Объявляем публичный класс GraphA
    // Храним список смежности, отсортированный в обратном порядке
    private final SortedMap<String, SortedSet<String>> adjacencyList = new TreeMap<>(Collections.reverseOrder());
    private List<String> topologicalOrder = null; // Список для хранения топологического порядка
    private boolean hasCycle = false; // Флаг для проверки наличия цикла в графе

    // Метод возвращает топологический порядок
    public List<String> getTopologicalOrder() { return topologicalOrder; }

    // Метод проверяет, содержит ли граф цикл
    public boolean hasCycle() { return hasCycle; }

    // Конструктор класса GraphA, принимает строку с описанием графа
    public GraphA(String input) {
        // Разделяем входную строку на рёбра
        var edges = input.split(", ");
        for (var edge : edges) { // Обрабатываем каждое ребро
            var vertices = edge.split(" -> "); // Разделяем вершины
            var from = vertices[0]; // Начальная вершина
            var to = vertices[1]; // Конечная вершина
            // Добавляем вершины в список смежности
            adjacencyList.computeIfAbsent(from, _1_ -> new TreeSet<>(Collections.reverseOrder())).add(to);
        }
        checkCycle(); // Проверяем граф на наличие циклов
        if (!hasCycle) { // Если цикл не обнаружен
            topologicalOrder = new ArrayList<>(); // Инициализируем список для топологической сортировки
            calcTopologicalSort(); // Выполняем топологическую сортировку
        }
    }

    // Проверка графа на наличие циклов
    private void checkCycle() {
        var marked  = new HashSet<String>(); // Множество посещённых вершин
        var onStack = new HashSet<String>(); // Множество вершин, находящихся в стеке
        for (var vertex : adjacencyList.keySet()) // Проходим по всем вершинам графа
            if (!marked.contains(vertex)) // Если вершина не посещена
                checkCycle(vertex, marked, onStack); // Рекурсивно проверяем её на цикл
    }

    // Рекурсивный метод для проверки на наличие цикла
    private void checkCycle(String vertex, HashSet<String> marked, HashSet<String> onStack) {
        marked.add(vertex); // Помечаем вершину как посещённую
        onStack.add(vertex); // Добавляем вершину в стек
        var neighbors = adjacencyList.get(vertex); // Получаем список соседей вершины
        if (neighbors != null) // Если есть соседи
            for (var neighbor : neighbors) { // Обрабатываем каждого соседа
                if (hasCycle) // Если цикл уже найден, выходим из метода
                    return;
                if (!marked.contains(neighbor)) // Если сосед ещё не посещён
                    checkCycle(neighbor, marked, onStack); // Рекурсивно проверяем его
                else if (onStack.contains(neighbor)) // Если сосед уже в стеке
                    hasCycle = true; // Устанавливаем флаг цикла
            }
        onStack.remove(vertex); // Убираем вершину из стека
    }

    // Выполнение топологической сортировки
    private void calcTopologicalSort() {
        var marked = new HashSet<String>(); // Множество посещённых вершин
        for (var vertex : adjacencyList.keySet()) // Проходим по всем вершинам графа
            if (!marked.contains(vertex)) // Если вершина не посещена
                calcTopologicalSort(vertex, marked); // Рекурсивно выполняем сортировку
        Collections.reverse(topologicalOrder); // Переворачиваем порядок для получения правильного результата
    }

    // Рекурсивный метод для топологической сортировки
    private void calcTopologicalSort(String vertex, HashSet<String> marked) {
        marked.add(vertex); // Помечаем вершину как посещённую
        var neighbors = adjacencyList.get(vertex); // Получаем список соседей вершины
        if (neighbors != null) // Если есть соседи
            for (var neighbor : neighbors) // Обрабатываем каждого соседа
                if (!marked.contains(neighbor)) // Если сосед ещё не посещён
                    calcTopologicalSort(neighbor, marked); // Рекурсивно выполняем сортировку для него
        topologicalOrder.add(vertex); // Добавляем вершину в результат
    }

    // Главный метод программы
    public static void main(String[] args) {
        var scanner = new Scanner(System.in); // Создаём объект Scanner для чтения ввода
        var graph = new GraphA(scanner.nextLine()); // Создаём граф из входной строки

        var topologicalOrder = graph.getTopologicalOrder(); // Получаем топологический порядок
        for (var vertex : topologicalOrder) // Выводим вершины в порядке сортировки
            System.out.print(vertex + " ");
    }
}

/*
* Эта задача решает проблему построения топологической
* сортировки ориентированного графа.
* Программа также проверяет наличие циклов в графе, так как
* топологическая сортировка невозможна для
* графов с циклами. На вход подаётся строка с описанием
* рёбер графа, а на выходе — список вершин в
* порядке топологической сортировки, либо информация об отсутствии
* возможности сортировки (если есть цикл).
* */