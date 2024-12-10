package by.it.group351001.shimanchuk.lesson13;

/**
 * Класс GraphC выполняет поиск сильно связных компонент в ориентированном графе с использованием алгоритма Косарайю.
 * - Граф вводится через консоль в формате: "начальная_вершина конечная_вершина,...".
 * - Граф представлен в виде списка смежности.
 * - Выполняется сортировка соседей в лексикографическом порядке.
 * - Результат выводится в виде отсортированных компонент.
 */

import java.util.*;

public class GraphC {

    // Класс для сортировки строк в обратном лексикографическом порядке
    static class LexicalComparator implements Comparator<String> {
        public int compare(String s1, String s2) {
            // Сравнение строк в обратном порядке
            return s2.compareTo(s1);
        }
    }

    public static void main(String[] args) {
        // Создаем структуру для хранения списка смежности
        Map<String, ArrayList<String>> neighbours = new HashMap<>();
        // Стек для хранения порядка завершения обхода вершин
        Stack<String> st = new Stack<>();
        // Множество для отслеживания посещенных вершин
        Set<String> vis = new HashSet<>();

        // Чтение ввода графа из консоли
        Scanner scanner = new Scanner(System.in);
        String[] vals = scanner.nextLine().split(","); // Разделяем строки по запятой
        scanner.close();

        String start; // Начальная вершина
        String end;   // Конечная вершина

        // Заполняем список смежности из входных данных
        for (String s : vals) {
            String[] current = s.trim().split(""); // Разделяем символы строки
            start = current[0]; // Первая вершина
            end = (current[current.length - 1]); // Последняя вершина
            // Если у вершины ещё нет списка соседей, создаём его
            if (neighbours.get(start) == null)
                neighbours.put(start, new ArrayList<>());

            // Добавляем связь от начальной к конечной вершине
            neighbours.get(start).add(end);
        }

        // Сортируем соседей в обратном лексикографическом порядке
        for (ArrayList<String> list : neighbours.values()) {
            list.sort(new LexicalComparator()); // Используем пользовательский компаратор
        }

        // Выполняем первый проход алгоритма DFS для нахождения порядка завершения вершин
        for (String w : neighbours.keySet()) {
            if (!vis.contains(w)) { // Если вершина ещё не посещена
                dfs(neighbours, w, vis, st); // Запускаем обход в глубину
            }
        }

        // Создаем транспонированный граф (меняем направления рёбер)
        Map<String, ArrayList<String>> transpNeighbours = new HashMap<>();
        for (String v : neighbours.keySet()) {
            ArrayList<String> list = neighbours.get(v); // Список соседей текущей вершины
            for (String child : list) {
                // Если у соседа ещё нет списка исходящих связей, создаём его
                if (transpNeighbours.get(child) == null)
                    transpNeighbours.put(child, new ArrayList<>());
                // Добавляем обратное ребро
                transpNeighbours.get(child).add(v);
            }
        }

        // Очищаем множество посещенных вершин для второго прохода
        vis.clear();

        // Выполняем второй проход алгоритма DFS для транспонированного графа
        while (!st.empty()) {
            String v = st.pop(); // Извлекаем вершину из стека
            if (!vis.contains(v)) { // Если вершина ещё не посещена
                StringBuilder sb = new StringBuilder(); // Строка для сбора компонент
                dfs(transpNeighbours, v, vis, sb); // Запускаем обход в глубину на транспонированном графе
                char[] charArr = sb.toString().toCharArray(); // Преобразуем компоненту в массив символов
                Arrays.sort(charArr); // Сортируем символы компоненты
                System.out.println(charArr); // Выводим компоненту
            }
        }

    }

    // Рекурсивный метод DFS с использованием стека для первого прохода
    private static void dfs(Map<String, ArrayList<String>> neighbours,
                            String v, Set<String> vis, Stack<String> st) {
        vis.add(v); // Отмечаем текущую вершину как посещённую

        if (neighbours.get(v) != null) { // Если у вершины есть соседи
            for (String child : neighbours.get(v)) { // Обходим всех соседей
                if (!vis.contains(child)) { // Если сосед не посещён
                    dfs(neighbours, child, vis, st); // Рекурсивный вызов для соседа
                }
            }
        }

        st.push(v); // Добавляем вершину в стек после обработки
    }

    // Рекурсивный метод DFS с использованием StringBuilder для второго прохода
    private static void dfs(Map<String, ArrayList<String>> neighbours,
                            String v, Set<String> vis, StringBuilder sb) {
        vis.add(v); // Отмечаем текущую вершину как посещённую
        sb.append(v); // Добавляем вершину в текущую компоненту
        if (neighbours.get(v) != null) { // Если у вершины есть соседи
            for (String child : neighbours.get(v)) { // Обходим всех соседей
                if (!vis.contains(child)) { // Если сосед не посещён
                    dfs(neighbours, child, vis, sb); // Рекурсивный вызов для соседа
                }
            }
        }
    }
}