package by.it.group310902.yoshchyk.lesson13;

import java.util.*;

public class GraphB {
    // Граф, представленный в виде списка смежности с использованием HashMap
    private static final HashMap<String, PriorityQueue<String>> graph = new HashMap<>();
    // Счетчики входящих ребер для каждой вершины
    private static final HashMap<String, Integer> inDegree = new HashMap<>();

    // Метод для добавления направленного ребра в граф
    private static void addComponent(String[] pair) {
        // Инициализируем структуру графа для обоих узлов, если они еще не присутствуют
        graph.putIfAbsent(pair[0], new PriorityQueue<>());
        graph.putIfAbsent(pair[1], new PriorityQueue<>());
        // Добавляем ребро от первого узла ко второму
        graph.get(pair[0]).add(pair[1]);

        // Обеспечиваем, чтобы у исходного узла было количество входящих ребер 0, если он начальная точка
        inDegree.putIfAbsent(pair[0], 0);
        // Увеличиваем счетчик входящих ребер для целевого узла
        inDegree.put(pair[1], inDegree.getOrDefault(pair[1], 0) + 1);
    }

    // Метод для проверки наличия циклов в графе
    private static boolean isHaveCycles() {
        Queue<String> queue = new LinkedList<>(); // Очередь для обработки узлов
        List<String> topologicalOrder = new ArrayList<>(); // Список для хранения отсортированного порядка

        // Добавляем все узлы с количеством входящих ребер 0 в очередь
        for (String s : graph.keySet()) {
            if (inDegree.get(s) == 0) {
                queue.add(s);
            }
        }

        // Обрабатываем узлы, пока очередь не опустеет
        while (!queue.isEmpty()) {
            String node = queue.poll(); // Получаем следующий узел
            topologicalOrder.add(node); // Добавляем его в отсортированный порядок

            // Уменьшаем количество входящих ребер у его соседей
            for (String s : graph.get(node)) {
                inDegree.put(s, inDegree.get(s) - 1); // Уменьшаем счетчик входящих ребер
                // Если количество входящих ребер становится 0, добавляем его в очередь для обработки
                if (inDegree.get(s) == 0) {
                    queue.add(s);
                }
            }
        }

        // Если количество обработанных узлов меньше общего количества узлов, это означает наличие цикла
        return topologicalOrder.size() < graph.size();
    }

    // Основной метод для выполнения программы
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Сканер для ввода данных
        String n = scanner.nextLine(); // Читаем строку ввода
        String[] parts = n.split(", "); // Разделяем ввод на компоненты
        // Добавляем каждую компоненту как ребро в граф
        for (String s : parts) {
            addComponent(s.split(" -> "));
        }
        scanner.close(); // Закрываем сканер

        // Проверяем наличие циклов и выводим результат
        if (isHaveCycles()) {
            System.out.println("yes"); // Если циклы есть, выводим "yes"
        } else {
            System.out.println("no"); // Если циклов нет, выводим "no"
        }

        // Очищаем граф и карту входящих ребер для потенциального повторного использования
        graph.clear();
        inDegree.clear();
    }
}