package by.it.group310901.kanunnikava.lesson13;

import java.util.*;

 /*Создайте класс GraphB в методе main которого считывается строка структуры орграфа вида:
         1 -> 2, 1 -> 3, 2 -> 3

         Затем в консоль выводится фраза о наличии циклов.
         Возможные варианты yes и no.*/

public class GraphB {
    private static boolean dfs(String node, Map<String, ArrayList<String>> graph, Set<String> visited) { // Метод для выполнения обхода в глубину (DFS)
        visited.add(node); // Добавление узла в множество посещенных
        boolean answ = false; // Переменная для отслеживания наличия цикла
        if (graph.get(node) != null) // Если у узла есть смежные узлы
            for (String next_node : graph.get(node)) { // Цикл по смежным узлам
                if (!visited.contains(next_node)) { // Смежный не посещён
                    answ = answ || dfs(next_node, graph, new HashSet<>(visited)); // Рекурсивный вызов DFS для смежного узла и объединение результатов
                } else
                    answ = true; // Обнаружение цикла
            }
        return answ;
    }

    public static void main(String[] args) {
        Map<String, ArrayList<String>> graph = new HashMap<>(); // Создание графа в виде карты (Map)

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
        boolean answ = false; // Переменная для отслеживания наличия цикла

        for (String node : graph.keySet()) { // Цикл по всем узлам графа
            answ = answ || dfs(node, graph, new HashSet<>()); // Вызов DFS для узла и объединение результатов

        }

        if (!answ) // Если цикл не обнаружен
            System.out.println("no");
        else // Если цикл обнаружен
            System.out.println("yes");

        in.close();
    }
}