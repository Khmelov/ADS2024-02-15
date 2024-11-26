package by.it.group351001.anton_matsiushenka.lesson13; // Указываем пакет, в котором находится данный класс

import java.util.*;

// Объявляем публичный класс GraphB
public class GraphB {
    // Храним список смежности графа
    private final Map<String, List<String>> adjacencyList = new HashMap<>();
    private boolean hasCycle = false; // Флаг для проверки наличия цикла в графе

    // Метод возвращает, содержит ли граф цикл
    public boolean hasCycle() { return hasCycle; }

    // Конструктор класса GraphB, принимает строку с описанием графа
    public GraphB(String input) {
        var edges = input.split(", "); // Разделяем входную строку на рёбра
        for (var edge : edges) { // Обрабатываем каждое ребро
            var vertices = edge.split(" -> "); // Разделяем вершины
            var from = vertices[0]; // Начальная вершина
            var to = vertices[1]; // Конечная вершина
            // Добавляем вершины в список смежности
            adjacencyList.computeIfAbsent(from, _1_ -> new ArrayList<>()).add(to);
        }
        checkCycle(); // Проверяем граф на наличие циклов
    }

    // Проверка графа на наличие циклов
    private void checkCycle() {
        var marked = new HashSet<String>(); // Множество посещённых вершин
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

    // Главный метод программы
    public static void main(String[] args) {
        var scanner = new Scanner(System.in); // Создаём объект Scanner для чтения ввода
        var graph = new GraphB(scanner.nextLine()); // Создаём граф из входной строки
        // Выводим "yes", если граф содержит цикл, иначе "no"
        System.out.print(graph.hasCycle() ? "yes" : "no");
    }
}

/*
* Эта задача направлена на проверку наличия цикла в ориентированном графе.
* На вход программа получает строку, описывающую рёбра графа
* в формате "A -> B, B -> C".
* Программа строит список смежности графа, а затем проверяет его на наличие
* циклов с использованием алгоритма поиска в глубину (DFS).
* Если цикл обнаружен, выводится yes, иначе — no.
* */

