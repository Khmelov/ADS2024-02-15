package by.it.group310901.tit.lesson13;

import java.util.*;


public class GraphC {
    // Граф, представленный в виде списка смежности с использованием HashMap
    private static final HashMap<String, PriorityQueue<String>> graph = new HashMap<>();
    // Обратный граф для поиска сильных компонент связности
    private static final HashMap<String, PriorityQueue<String>> reverseGraph = new HashMap<>();
    // Набор посещенных узлов
    private static final HashSet<String> visited = new HashSet<>();
    // Стек для хранения порядка обхода узлов
    private static final Stack<String> stack = new Stack<>();
    // Очередь для хранения компонент связности
    private static final PriorityQueue<String> component = new PriorityQueue<>();


    // Метод для добавления направленного ребра в граф и его обратный граф
    private static void addComponent(String[] pair) {
        // Инициализируем граф и обратный граф для обоих узлов, если они еще не присутствуют
        graph.putIfAbsent(pair[0], new PriorityQueue<>());
        graph.putIfAbsent(pair[1], new PriorityQueue<>());


        reverseGraph.putIfAbsent(pair[0], new PriorityQueue<>());
        reverseGraph.putIfAbsent(pair[1], new PriorityQueue<>());


        // Добавляем ребро от первого узла ко второму в граф
        graph.get(pair[0]).add(pair[1]);
        // Добавляем ребро от второго узла к первому в обратный граф
        reverseGraph.get(pair[1]).add(pair[0]);
    }


    // Первый проход DFS для получения порядка завершения узлов
    private static void dfsFirst(String node) {
        visited.add(node); // Помечаем узел как посещенный


        // Рекурсивный обход всех соседей узла
        for (String item : graph.get(node)) {
            if (!visited.contains(item)) {
                dfsFirst(item);
            }
        }


        // После завершения всех соседей добавляем узел в стек
        stack.push(node);
    }


    // Второй проход DFS для нахождения компонент связности
    private static void dfsSecond(String node, PriorityQueue<String> component) {
        visited.add(node); // Помечаем узел как посещенный
        component.add(node); // Добавляем узел в текущую компоненту связности


        // Рекурсивный обход всех соседей в обратном графе
        for (String item : reverseGraph.get(node)) {
            if (!visited.contains(item)) {
                dfsSecond(item, component);
            }
        }
    }


    // Метод для нахождения всех сильных компонент связности
    private static void findSccs() {
        // Первый проход DFS для заполнения стека узлами
        for (String node : graph.keySet()) {
            if (!visited.contains(node)) {
                dfsFirst(node);
            }
        }


        // Очищаем набор посещенных узлов для второго прохода
        visited.clear();
        StringBuilder stringBuilder = new StringBuilder(); // Строка для вывода компонент связности


        // Второй проход DFS по узлам в порядке, определяемом стеком
        while (!stack.isEmpty()) {
            String node = stack.pop(); // Извлекаем узел из стека
            if (!visited.contains(node)) {
                // Создаем новую компоненту связности
                dfsSecond(node, component);
                // Добавляем все узлы из компоненты в строку
                while (!component.isEmpty()) {
                    stringBuilder.append(component.poll());
                }
                stringBuilder.append("\n"); // Добавляем новую строку для разделения компонент
            }
        }
        // Печатаем все найденные компоненты связности
        System.out.println(stringBuilder);
    }


    // Основной метод для выполнения программы
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Сканер для ввода данных
        String n = scanner.nextLine(); // Читаем строку ввода
        String[] parts = n.split(", "); // Разделяем ввод на компоненты
        // Добавляем каждую компоненту как ребро в граф
        for (String s : parts) {
            addComponent(s.split("->"));
        }
        scanner.close(); // Закрываем сканер
        findSccs(); // Находим и выводим сильные компоненты связности
        // Очищаем структуры данных для потенциального повторного использования
        graph.clear();
        reverseGraph.clear();
        visited.clear();
        component.clear();
        stack.clear();
    }
}
