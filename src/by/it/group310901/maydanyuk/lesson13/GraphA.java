package by.it.group310901.maydanyuk.lesson13;

import java.util.*;
//реализуют топологичесскую сортировку направленного ациклического графа
//с использованием алгоритма обхода в глубину

// Класс для представления ориентированного графа
class GraphA {
    // Поле для хранения графа: карта, где ключи - это вершины, а значения - списки смежных вершин
    private Map<Character, List<Character>> gr;

    // Конструктор графа, инициализирующий поле gr как TreeMap с обратным порядком сортировки
    public GraphA() {
        gr = new TreeMap<>(Collections.reverseOrder());
    }

    // Метод для добавления ребра в граф
    public void addEdge(char first, char second) {
        // Если вершина first не существует в графе, добавляем её со списком смежных вершин
        if (!gr.containsKey(first)) {
            gr.put(first, new ArrayList<>());
        }
        // Добавляем вершину second в список смежных вершин для first
        gr.get(first).add(second);
    }

    // Метод для топологической сортировки графа
    public void topologicalSort() {
        // Сортируем списки смежных вершин для каждой вершины в обратном порядке
        for (List<Character> temp : gr.values()) {
            Collections.sort(temp, Collections.reverseOrder());
        }

        // Стек для хранения результирующей топологической сортировки
        Stack<Character> stack = new Stack<>();
        // Множество для отслеживания посещенных вершин
        Set<Character> visited = new HashSet<>();

        // Обходим все вершины графа
        for (char tmpNode : gr.keySet()) {
            // Если вершина еще не посещена, выполняем DFS для неё
            if (!visited.contains(tmpNode)) {
                DFS(tmpNode, visited, stack);
            }
        }

        // Выводим вершины из стека, что даёт топологический порядок
        while (!stack.isEmpty()) {
            System.out.print(stack.pop() + " ");
        }
    }

    // Вспомогательный метод для выполнения глубинного поиска (DFS)
    private void DFS(char node, Set<Character> visited, Stack<Character> stack) {
        // Отмечаем текущую вершину как посещенную
        visited.add(node);

        // Рекурсивно посещаем все смежные вершины, которые еще не были посещены
        for (char tempnode : gr.getOrDefault(node, new ArrayList<>())) {
            if (!visited.contains(tempnode)) {
                DFS(tempnode, visited, stack);
            }
        }

        // Добавляем текущую вершину в стек после всех её смежных вершин
        stack.push(node);
    }

    // Метод main для запуска программы
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Считываем входные данные, представляющие рёбра графа
        String input = scanner.nextLine();

        // Создаём новый граф
        GraphA graph = new GraphA();

        // Разбиваем входные данные на отдельные рёбра
        String[] edges = input.split(", ");
        for (String edge : edges) {
            // Разделяем каждое ребро на две вершины
            String[] vert = edge.split(" -> ");
            char A = vert[0].charAt(0);
            char B = vert[1].charAt(0);
            // Добавляем ребро в граф
            graph.addEdge(A, B);
        }

        // Выполняем топологическую сортировку графа и выводим результат
        graph.topologicalSort();
    }
}
