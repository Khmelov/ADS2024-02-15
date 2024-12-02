package by.it.group310901.silchenkoarina.lesson13;
import java.util.*;

//Создайте класс GraphB в методе main которого считывается строка структуры орграфа вида:
//1 -> 2, 1 -> 3, 2 -> 3
//
//Затем в консоль выводится фраза о наличии циклов.
//Возможные варианты yes и no.
//Для указанного примера будет такой вывод:
//no

public class GraphB {
    // Рекурсивный метод для поиска цикла в графе
    private static boolean DFS(String node, Map<String, ArrayList<String>> graph, Set<String> visited, Set<String> recStack) {
        visited.add(node);
        recStack.add(node);   // Добавляем вершину в текущий стек рекурсии

        if (graph.get(node) != null) {
            for (String next_node : graph.get(node)) {
                if (!visited.contains(next_node)) {
                    if (DFS(next_node, graph, visited, recStack)) {
                        return true;    // Цикл найден
                    }
                } else if (recStack.contains(next_node)) {
                    return true;        // Цикл найден
                }
            }
        }

        recStack.remove(node);  // Убираем вершину из текущего стека рекурсии перед возвратом
        return false;
    }

    public static void main(String[] args) {
        Map<String, ArrayList<String>> graph = new HashMap<>();
        Scanner in = new Scanner(System.in);

        System.out.println("Введите структуру графа (например: 1 -> 2, 1 -> 3, 2 -> 3):");
        String input = in.nextLine();
        String[] edges = input.split(", "); // Разделение строки на отдельные ребра

        // Заполнение графа
        for (String edge : edges) {
            String[] nodes = edge.split(" -> ");
            String start = nodes[0];
            String end = nodes[1];

            graph.putIfAbsent(start, new ArrayList<>());
            graph.get(start).add(end);

            graph.putIfAbsent(end, new ArrayList<>()); // Для вершин без исходящих рёбер
        }

        // Проверка на наличие циклов
        boolean hasCycle = false;
        Set<String> visited = new HashSet<>();
        Set<String> recStack = new HashSet<>();

        for (String node : graph.keySet()) {
            if (!visited.contains(node)) {
                if (DFS(node, graph, visited, recStack)) {
                    hasCycle = true;
                    break;
                }
            }
        }

        // Выводим результат
        System.out.println(hasCycle ? "yes" : "no");
        in.close();
    }
}