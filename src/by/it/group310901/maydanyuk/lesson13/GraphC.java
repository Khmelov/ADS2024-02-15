package by.it.group310901.maydanyuk.lesson13;

import java.util.*;
import java.util.Map.Entry;
//нахоит сильные компоненты связности в направленном графе
//используя алгоритм косарайю
public class GraphC {
    // Статическое поле для отслеживания текущего времени
    static Integer currTime = 0;

    // Метод для выполнения DFS и назначения времени вершинам
    private static void dfsTime(String node, Map<String, List<String>> graph, Set<String> visited, Map<String, Integer> time) {
        visited.add(node); // Отмечаем вершину как посещённую
        if (graph.get(node) != null) {
            for (String next_node : graph.get(node)) {
                if (!visited.contains(next_node)) {
                    currTime++; // Увеличиваем текущее время
                    dfsTime(next_node, graph, visited, time); // Рекурсивно вызываем dfsTime для смежной вершины
                }
            }
        }
        time.put(node, currTime++); // Назначаем время текущей вершине
    }

    // Метод для выполнения стандартного DFS и построения пути
    private static void dfs(String node, Map<String, List<String>> graph, Set<String> visited, List<String> path) {
        visited.add(node); // Отмечаем вершину как посещённую
        path.add(node); // Добавляем вершину в путь
        if (graph.get(node) != null) {
            for (String next_node : graph.get(node)) {
                if (!visited.contains(next_node)) {
                    dfs(next_node, graph, visited, path); // Рекурсивно вызываем dfs для смежной вершины
                }
            }
        }
    }

    // Внутренний класс для сравнения пар (вершина, время) по значениям и ключам
    static class MapEntryComparator implements Comparator<Entry<String, Integer>> {
        @Override
        public int compare(Entry<String, Integer> entry1, Entry<String, Integer> entry2) {
            int valueComparison = entry1.getValue().compareTo(entry2.getValue()); // Сравнение по значениям
            if (valueComparison == 0) {
                return entry2.getKey().compareTo(entry1.getKey()); // Сравнение по ключам в обратном порядке
            }
            return valueComparison;
        }
    }

    // Метод main для запуска программы
    public static void main(String[] args) {
        // Создаем граф, реверсивный граф, множество посещенных вершин и карту времени
        Map<String, List<String>> graph = new HashMap<>();
        Map<String, List<String>> graphReversed = new HashMap<>();
        Set<String> visited = new HashSet<>();
        Map<String, Integer> time = new HashMap<>();

        try (Scanner scanner = new Scanner(System.in)) {
            // Считываем входные данные, представляющие рёбра графа
            String input = scanner.nextLine();
            String[] nodes = input.split("\\s*,\\s*");

            // Обрабатываем каждое ребро
            for (String node : nodes) {
                String[] vertexes = node.split("\\s*->\\s*");
                if (graph.containsKey(vertexes[0])) {
                    graph.get(vertexes[0]).add(vertexes[1]); // Добавляем смежную вершину в граф
                } else {
                    List<String> list = new ArrayList<>();
                    list.add(vertexes[1]);
                    graph.put(vertexes[0], list); // Создаем новую запись в графе
                }

                if (graphReversed.containsKey(vertexes[1])) {
                    graphReversed.get(vertexes[1]).add(vertexes[0]); // Добавляем смежную вершину в реверсивный граф
                    continue;
                }

                List<String> list = new ArrayList<>();
                list.add(vertexes[0]);
                graphReversed.put(vertexes[1], list); // Создаем новую запись в реверсивном графе
            }
        }

        // Выполняем DFS для назначения времени вершинам
        for (String node : graph.keySet()) {
            if (!visited.contains(node)) {
                dfsTime(node, graph, visited, time);
            }
        }

        // Сортируем вершины по времени в обратном порядке
        List<Entry<String, Integer>> entryList = new ArrayList<>(time.entrySet());
        entryList.sort(new MapEntryComparator());
        String[] vertices = new String[entryList.size()];
        for (int i = entryList.size() - 1; i > -1; i--) {
            vertices[entryList.size() - 1 - i] = entryList.get(i).getKey();
        }

        visited = new HashSet<>(); // Пересоздаем множество посещённых вершин для поиска компонент сильной связности
        for (String node : vertices) {
            if (!visited.contains(node)) {
                List<String> path = new ArrayList<>();
                dfs(node, graphReversed, visited, path); // Выполняем DFS для реверсивного графа

                path.sort(CharSequence::compare); // Сортируем путь для правильного вывода

                for (String n : path) {
                    System.out.print(n); // Выводим каждую вершину в компоненте
                }
                System.out.println(); // Переход на новую строку после компоненты
            }
        }
    }
}
