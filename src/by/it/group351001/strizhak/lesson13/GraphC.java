package by.it.group351001.strizhak.lesson13;

import java.util.*;

public class GraphC {

    // Класс для сравнения строк в лексикографическом порядке (обратный порядок)
    static class LexicalComparator implements Comparator<String> {
        public int compare(String s1, String s2) {
            return s2.compareTo(s1);
        }
    }

    public static void main(String[] args) {
        Map<String, ArrayList<String>> neighbours = new HashMap<>(); // Соседние вершины графа
        Stack<String> st = new Stack<>(); // Стек для хранения порядка обхода
        Set<String> vis = new HashSet<>(); // Множество для отслеживания посещённых вершин

        Scanner scanner = new Scanner(System.in);
        String[] vals = scanner.nextLine().split(","); // Чтение входных данных
        scanner.close();

        String start;
        String end;

        // Заполнение графа на основе входных данных
        for (String s : vals) {
            String[] current = s.trim().split("");
            start = current[0];
            end = (current[current.length - 1]);
            if (neighbours.get(start) == null)
                neighbours.put(start, new ArrayList<>());

            neighbours.get(start).add(end);
        }

        // Сортировка списков смежности в обратном лексикографическом порядке
        for (ArrayList<String> list: neighbours.values()) {
            list.sort(new LexicalComparator());
        }

        // Обход всех вершин графа
        for (String w : neighbours.keySet()) {
            if (!vis.contains(w)) {
                dfs(neighbours, w, vis, st); // Выполнение DFS для непосещённых вершин
            }
        }

        // Создание транспонированного графа
        Map<String, ArrayList<String>> transpNeighbours = new HashMap<>();
        for (String v : neighbours.keySet()) {
            ArrayList<String> list = neighbours.get(v);
            for (String child : list) {
                if (transpNeighbours.get(child) == null)
                    transpNeighbours.put(child, new ArrayList<>());
                transpNeighbours.get(child).add(v);
            }
        }

        vis.clear(); // Очистка множества посещённых вершин
        while (!st.empty()) {
            String v = st.pop();
            if (!vis.contains(v)) {
                StringBuilder sb = new StringBuilder();
                dfs(transpNeighbours, v, vis, sb); // Выполнение DFS для транспонированного графа
                char[] charArr = sb.toString().toCharArray();
                Arrays.sort(charArr); // Сортировка символов в компоненте сильной связности
                System.out.println(charArr); // Вывод компоненты сильной связности
            }
        }
    }

    // Рекурсивный метод DFS для топологической сортировки
    private static void dfs(Map<String, ArrayList<String>> neighbours,
                            String v, Set<String> vis, Stack<String> st) {
        vis.add(v); // Помечаем вершину как посещённую

        if (neighbours.get(v) != null) {
            for (String child : neighbours.get(v)) {
                if (!vis.contains(child)) {
                    dfs(neighbours, child, vis, st); // Рекурсивный вызов для непосещённых соседей
                }
            }
        }

        st.push(v); // Добавляем вершину в стек после обработки всех её соседей
    }

    // Рекурсивный метод DFS для поиска компонент сильной связности
    private static void dfs(Map<String, ArrayList<String>> neighbours,
                            String v, Set<String> vis, StringBuilder sb) {
        vis.add(v); // Помечаем вершину как посещённую
        sb.append(v); // Добавляем вершину в строку компоненты сильной связности

        if (neighbours.get(v) != null) {
            for (String child : neighbours.get(v)) {
                if (!vis.contains(child)) {
                    dfs(neighbours, child, vis, sb); // Рекурсивный вызов для непосещённых соседей
                }
            }
        }
    }
}
