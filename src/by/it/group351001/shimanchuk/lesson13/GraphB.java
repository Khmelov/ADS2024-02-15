package by.it.group351001.shimanchuk.lesson13;

/*
 * Класс GraphB проверяет наличие циклов в ориентированном графе.
 * - Ввод графа осуществляется через консоль в формате: "начальная_вершина конечная_вершина,...".
 * - Граф представлен в виде списка смежности с использованием вспомогательного класса GraphUtil.
 * - Метод isCyclic определяет наличие цикла в графе.
 * - Результат проверки выводится в консоль: "yes" если цикл есть, "no" если цикла нет.
 */

import java.util.*;

public class GraphB {

    static boolean[] visited; // Массив для отслеживания посещённых вершин
    static boolean[] stack;   // Массив для отслеживания текущего стека рекурсии

    // Метод для проверки наличия циклов в графе
    public static boolean isCyclic(GraphUtil graph) {
        visited = new boolean[graph.vertexCount];
        stack = new boolean[graph.vertexCount];

        // Проходим по всем вершинам графа
        for (int i = 0; i < graph.vertexCount; i++) {
            if (!visited[i] && isCyclicUtil(graph, i)) // Если вершина ещё не посещена и обнаружен цикл
                return true;
        }

        return false; // Если циклов не обнаружено
    }

    // Вспомогательный метод для рекурсивной проверки наличия цикла
    static boolean isCyclicUtil(GraphUtil graph, int vertex) {
        if (!visited[vertex]) { // Если вершина ещё не посещена
            visited[vertex] = true; // Отмечаем её как посещённую
            stack[vertex] = true;  // Добавляем вершину в стек рекурсии

            // Проверяем всех соседей текущей вершины
            for (int neighbor : graph.getNeighbors(vertex)) {
                if (!visited[neighbor] && isCyclicUtil(graph, neighbor)) return true; // Рекурсивный вызов для соседа
                if (stack[neighbor]) return true; // Если сосед уже в стеке, найден цикл
            }
        }

        stack[vertex] = false; // Убираем вершину из стека рекурсии
        return false;
    }

    // Метод для нахождения максимального номера вершины в графе
    static int getMax(int[] startVertex, int[] endVertex) {
        int size = Integer.MIN_VALUE;
        for (int i = 0; i < startVertex.length; i++) {
            if (startVertex[i] > size)
                size = startVertex[i];
            if (endVertex[i] > size)
                size = endVertex[i];
        }
        return size;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String[] arr = scan.nextLine().split(","); // Читаем граф из консоли

        int[] startVertex = new int[arr.length];
        int[] endVertex = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            String[] current = arr[i].trim().split(" ");
            startVertex[i] = Integer.parseInt(current[0]); // Начальная вершина
            endVertex[i] = Integer.parseInt(current[current.length - 1]); // Конечная вершина
        }

        // Создаём граф с количеством вершин, равным максимальной вершине + 1
        GraphUtil graph = new GraphUtil(getMax(startVertex, endVertex) + 1);
        for (int i = 0; i < arr.length; i++)
            graph.addOrientedEdge(startVertex[i], endVertex[i]); // Добавляем ориентированное ребро

        // Выводим результат проверки наличия цикла
        System.out.println(isCyclic(graph) ? "yes" : "no");
        scan.close();
    }
}