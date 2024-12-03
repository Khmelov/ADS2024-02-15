package by.it.group310901.kanunnikava.lesson13;

import java.util.*;

/*Создайте класс GraphA в методе main которого считывается строка структуры орграфа вида:
        0 -> 2, 1 -> 3, 2 -> 3, 0 -> 1

        Затем в консоль выводится его топологическая сортировка вида:
        0 1 2 3*/
public class GraphA {

    private static void dfs(String node, Map<String, ArrayList<String>> graph, Set<String> visited, Stack<String> stack) { // Метод для выполнения обхода в глубину (DFS)
        visited.add(node); // Добавление узла в множество посещенных
        if (graph.get(node) != null) // Если у узла есть смежные узлы
            for (String next_node : graph.get(node)) { // Цикл по смежным узлам
                if (!visited.contains(next_node)) { // Если смежный узел не посещен
                    dfs(next_node, graph, visited, stack); // Рекурсивный вызов dfs
                }
            }
        stack.push(node); // Добавление узла в стек
    }

    static class StringComparator implements Comparator<String> { // Вложенный класс для сравнения строк в обратном порядке
        @Override
        public int compare(String s1, String s2) { // Переопределение метода compare
            return s2.compareTo(s1); // Сравнение строк в обратном порядке
        }
    }

    public static void main(String[] args) {
        Map<String, ArrayList<String>> graph = new HashMap<>(); // Создание графа в виде карты (Map)
        Stack<String> stack = new Stack<>(); // Создание стека для хранения узлов
        Set<String> visited = new HashSet<>(); // Сравнение строк в обратном порядке

        Scanner in = new Scanner(System.in); // Создание сканера для чтения ввода

        boolean isEnd = false; // Флаг для завершения ввода

        while (!isEnd) {
            String a = in.next(); // Чтение первого узла
            String s = in.next(); // Чтение стрелки
            String b = in.next(); // Чтение второго узла
            if (b.charAt(b.length() - 1) == ',') {
                b = b.substring(0, s.length() - 1); //Удаление запятой
            } else {
                isEnd = true; // Завершение ввода
            }
            if (!graph.containsKey(a)) // Если первый узел не содержится в графе
                graph.put(a, new ArrayList<>());  // Добавление первого узла в граф
            graph.get(a).add(b); // Добавление второго узла в список смежных узлов первого узла
        }

        for (ArrayList<String> l : graph.values()) { // Цикл по всем спискам смежных узлов
            l.sort(new StringComparator()); // Сортировка списков смежных узлов в обратном порядке }
        }

        for (String node : graph.keySet()) {  // Цикл по всем узлам графа
            if (!visited.contains(node)) { // Узел не посещён
                dfs(node, graph, visited, stack);  // Вызов DFS для узла
            }
        }

        while (!stack.empty()) {
            System.out.print(stack.pop()); // Извлечение узла из стека и вывод его
            System.out.print(' ');
        }

        in.close();
    }
}