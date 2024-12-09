package by.it.group351001.sosnovski.lesson13;

import java.util.*;

public class GraphB {

    /**
     проверяем, содержит ли заданный ориентированный граф цикл,
     используя алгоритм обхода в глубину (DFS). Если вершина уже находится
     в текущем стеке рекурсии во время обхода, это указывает на цикл.
     */

    // Массив для отслеживания посещенных вершин
    static boolean[] visited;
    // Массив для отслеживания вершин в текущем стеке рекурсии
    static boolean[] stack;

    // Метод для проверки наличия цикла в графе
    public static boolean isCyclic(GraphUtil graph) {
        visited = new boolean[graph.vertexCount]; // Инициализация массива посещений
        stack = new boolean[graph.vertexCount];   // Инициализация массива рекурсивного стека

        // Проверяем каждую вершину графа
        for (int i = 0; i < graph.vertexCount; i++) {
            // Если вершина не посещена и вызывает цикл, возвращаем true
            if (!visited[i] && isCyclicUtil(graph, i))
                return true;
        }

        // Если ни одна вершина не вызвала цикл, возвращаем false
        return false;
    }

    // Рекурсивный метод для поиска цикла
    static boolean isCyclicUtil(GraphUtil graph, int vertex) {
        if (!visited[vertex]) { // Если вершина ещё не посещена
            visited[vertex] = true; // Отмечаем вершину как посещенную
            stack[vertex] = true;   // Добавляем вершину в текущий стек рекурсии

            // Проверяем всех соседей текущей вершины
            for (int neighbor : graph.getNeighbors(vertex)) {
                // Если сосед не посещен и вызывает цикл, возвращаем true
                if (!visited[neighbor] && isCyclicUtil(graph, neighbor))
                    return true;

                // Если сосед уже в текущем стеке рекурсии, возвращаем true (цикл найден)
                if (stack[neighbor])
                    return true;
            }
        }

        // Удаляем вершину из текущего стека рекурсии
        stack[vertex] = false;
        return false; // Цикл не найден
    }

    // Метод для нахождения максимального номера вершины
    static int getMax(int[] startVertex, int[] endVertex) {
        int size = Integer.MIN_VALUE; // Инициализируем минимальное значение
        for (int i = 0; i < startVertex.length; i++) {
            // Обновляем максимальное значение для стартовых и конечных вершин
            if (startVertex[i] > size)
                size = startVertex[i];
            if (endVertex[i] > size)
                size = endVertex[i];
        }
        return size; // Возвращаем максимальный номер вершины
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in); // Считываем входные данные
        String[] arr = scan.nextLine().split(","); // Разделяем рёбра по запятой

        int[] startVertex = new int[arr.length]; // Массив начальных вершин
        int[] endVertex = new int[arr.length];   // Массив конечных вершин

        // Обрабатываем входные данные
        for (int i = 0; i < arr.length; i++) {
            String[] current = arr[i].trim().split(" "); // Разделяем начальную и конечную вершины
            startVertex[i] = Integer.parseInt(current[0]); // Преобразуем в целое число
            endVertex[i] = Integer.parseInt(current[current.length - 1]); // Последнее значение - конечная вершина
        }

        // Создаем граф с количеством вершин на единицу больше максимальной
        GraphUtil graph = new GraphUtil(getMax(startVertex, endVertex) + 1);

        // Добавляем рёбра в граф
        for (int i = 0; i < arr.length; i++)
            graph.addOrientedEdge(startVertex[i], endVertex[i]);

        // Проверяем граф на наличие цикла и выводим результат
        System.out.println(isCyclic(graph) ? "yes" : "no");
        scan.close(); // Закрываем сканер
    }
}
