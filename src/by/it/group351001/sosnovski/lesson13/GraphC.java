package by.it.group351001.sosnovski.lesson13;

import java.util.*;

public class GraphC {

    /**
     Этот код выполняет нахождение компонент сильной связности (КСС) в ориентированном графе.
     Используется алгоритм Косарайю:

     Первый проход DFS: Для нахождения порядка обхода вершин.
     Транспонирование графа: Перестановка направлений всех рёбер.
     Второй проход DFS: Для определения компонент сильной связности.
     */

    // Компаратор для обратного лексикографического порядка
    static class LexicalComparator implements Comparator<String> {
        public int compare(String s1, String s2) {
            return s2.compareTo(s1); // Сравнение строк в обратном порядке
        }
    }

    public static void main(String[] args) {
        // Хранилище графа как списков смежности
        Map<String, ArrayList<String>> neighbours = new HashMap<>();
        Stack<String> st = new Stack<>(); // Стек для хранения порядка завершения DFS
        Set<String> vis = new HashSet<>(); // Множество для отслеживания посещённых вершин

        // Считываем граф из ввода
        Scanner scanner = new Scanner(System.in);
        String[] vals = scanner.nextLine().split(","); // Разделяем рёбра по запятой
        scanner.close();

        // Заполняем граф
        for (String s : vals) {
            String[] current = s.trim().split(""); // Разделяем строку на отдельные символы
            String start = current[0]; // Начальная вершина
            String end = current[current.length - 1]; // Конечная вершина
            // Если вершина ещё не добавлена в граф, добавляем её
            if (!neighbours.containsKey(start))
                neighbours.put(start, new ArrayList<>());

            // Добавляем ребро
            neighbours.get(start).add(end);
        }

        // Сортируем списки смежности в обратном лексикографическом порядке
        for (ArrayList<String> list : neighbours.values()) {
            list.sort(new LexicalComparator());
        }

        // Первый проход DFS: формируем стек вершин по завершению их обработки
        for (String w : neighbours.keySet()) {
            if (!vis.contains(w)) { // Если вершина не посещена, запускаем DFS
                dfs(neighbours, w, vis, st);
            }
        }

        // Транспонируем граф (меняем направление всех рёбер)
        Map<String, ArrayList<String>> transpNeighbours = new HashMap<>();
        for (String v : neighbours.keySet()) {
            ArrayList<String> list = neighbours.get(v);
            for (String child : list) {
                // Если транспонированная вершина ещё не добавлена, добавляем её
                if (!transpNeighbours.containsKey(child))
                    transpNeighbours.put(child, new ArrayList<>());
                // Добавляем обратное ребро
                transpNeighbours.get(child).add(v);
            }
        }

        // Очищаем множество посещённых вершин для повторного использования
        vis.clear();

        // Второй проход DFS: находим компоненты сильной связности
        while (!st.empty()) {
            String v = st.pop(); // Берём вершину из стека
            if (!vis.contains(v)) { // Если вершина не посещена, это новая КСС
                StringBuilder sb = new StringBuilder(); // Строим КСС
                dfs(transpNeighbours, v, vis, sb); // DFS по транспонированному графу
                char[] charArr = sb.toString().toCharArray(); // Преобразуем КСС в массив символов
                Arrays.sort(charArr); // Сортируем КСС в лексикографическом порядке
                System.out.println(charArr); // Выводим КСС
            }
        }

    }

    // DFS с использованием стека для фиксации порядка завершения вершин
    private static void dfs(Map<String, ArrayList<String>> neighbours,
                            String v, Set<String> vis, Stack<String> st) {
        vis.add(v); // Помечаем вершину как посещённую

        // Рекурсивно обходим всех соседей
        if (neighbours.get(v) != null) {
            for (String child : neighbours.get(v)) {
                if (!vis.contains(child)) {
                    dfs(neighbours, child, vis, st);
                }
            }
        }

        // Добавляем вершину в стек после обработки всех её соседей
        st.push(v);
    }

    // DFS для нахождения компонент сильной связности
    private static void dfs(Map<String, ArrayList<String>> neighbours,
                            String v, Set<String> vis, StringBuilder sb) {
        vis.add(v); // Помечаем вершину как посещённую
        sb.append(v); // Добавляем вершину в текущую компоненту

        // Рекурсивно обходим всех соседей
        if (neighbours.get(v) != null) {
            for (String child : neighbours.get(v)) {
                if (!vis.contains(child)) {
                    dfs(neighbours, child, vis, sb);
                }
            }
        }
    }
}
