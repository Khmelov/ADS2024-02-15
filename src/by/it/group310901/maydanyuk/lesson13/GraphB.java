package by.it.group310901.maydanyuk.lesson13;

import java.util.*;
//проверяет наличие циклов в направленом графе

// Класс для представления ориентированного графа
class GraphB {
    // Поле для хранения графа: карта, где ключи - это вершины, а значения - списки смежных вершин
    private Map<Character, List<Character>> gr;

    // Конструктор графа, инициализирующий поле gr как TreeMap с естественным порядком сортировки
    public GraphB() {
        gr = new TreeMap<>();
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

    // Метод для глубокого поиска (DFS)
    private boolean DFS(char node, Set<Character> glVisited, Set<Character> local) {
        // Если вершина уже посещена в текущем пути, найден цикл
        if (local.contains(node))
            return true;
        // Если вершина уже глобально посещена, пропускаем её
        if (glVisited.contains(node))
            return false;

        // Добавляем вершину в глобальный и локальный множества посещённых вершин
        glVisited.add(node);
        local.add(node);

        // Получаем список смежных вершин для текущей вершины
        List<Character> NearNodesList = gr.getOrDefault(node, new ArrayList<>());
        // Рекурсивно выполняем DFS для каждой смежной вершины
        for (char tempNode : NearNodesList) {
            if (DFS(tempNode, glVisited, local)) {
                return true; // Если найден цикл, возвращаем true
            }
        }
        // Удаляем вершину из локального множества после обработки
        local.remove(node);
        return false; // Цикл не найден
    }

    // Метод для проверки наличия цикла в графе
    public boolean CycleCheck() {
        // Множество для отслеживания глобально посещённых вершин
        Set<Character> glVisited = new HashSet<>();
        // Перебираем все вершины графа
        for (char node : gr.keySet()) {
            // Если вершина еще не посещена, выполняем DFS для неё
            if (!glVisited.contains(node)) {
                Set<Character> local = new HashSet<>(); // Локальное множество для отслеживания текущего пути
                if (DFS(node, glVisited, local)) return true; // Если найден цикл, возвращаем true
            }
        }
        return false; // Цикл не найден
    }

    // Метод main для запуска программы
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Считываем входные данные, представляющие рёбра графа
        String input = scanner.nextLine();

        // Создаём новый граф
        GraphB graph = new GraphB();

        // Разбиваем входные данные на отдельные рёбра
        String[] edges = input.split(", ");
        for (String edge : edges) {
            // Разделяем каждое ребро на две вершины
            String[] vertices = edge.split(" -> ");
            char A = vertices[0].charAt(0);
            char B = vertices[1].charAt(0);
            // Добавляем ребро в граф
            graph.addEdge(A, B);
        }

        // Проверяем наличие цикла в графе и выводим результат
        if (graph.CycleCheck()) {
            System.out.print("yes");
        } else {
            System.out.print("no");
        }
    }
}
