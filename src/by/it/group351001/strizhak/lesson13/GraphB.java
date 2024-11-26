package by.it.group351001.strizhak.lesson13;

import java.util.*;

public class GraphB {

    static boolean[] visited; // Массив для отслеживания посещённых узлов
    static boolean[] stack; // Массив для отслеживания узлов в текущем стеке рекурсии

    // Метод для проверки наличия циклов в графе
    public static boolean isCyclic(GraphUtil graph) {
        visited = new boolean[graph.vertexCount]; // Инициализация массива посещённых узлов
        stack = new boolean[graph.vertexCount]; // Инициализация массива стека рекурсии

        // Проверка каждого узла графа
        for (int i = 0; i < graph.vertexCount; i++) {
            if (!visited[i] && isCyclicUtil(graph, i)) // Если узел не посещён и обнаружен цикл
                return true;
        }

        return false;
    }

    // Вспомогательный рекурсивный метод для проверки циклов
    static boolean isCyclicUtil(GraphUtil graph, int vertex) {
        if (!visited[vertex]) { // Если узел не посещён
            visited[vertex] = true; // Помечаем узел как посещённый
            stack[vertex] = true; // Добавляем узел в стек рекурсии

            // Проверяем всех соседей текущего узла
            for (int neighbor : graph.getNeighbors(vertex)) {
                if (!visited[neighbor] && isCyclicUtil(graph, neighbor)) // Если сосед не посещён и обнаружен цикл
                    return true;
                if (stack[neighbor]) // Если сосед уже в стеке рекурсии
                    return true;
            }
        }

        stack[vertex] = false; // Удаляем узел из стека рекурсии
        return false; // Возвращаем false, если циклов не найдено
    }

    // Метод для нахождения максимального значения среди вершин
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

    // Главный метод программы
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String[] arr = scan.nextLine().split(",");
        int[] startVertex = new int[arr.length];
        int[] endVertex = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            String[] current = arr[i].trim().split(" ");
            startVertex[i] = Integer.parseInt(current[0]);
            endVertex[i] = Integer.parseInt(current[current.length - 1]);
        }

        GraphUtil graph = new GraphUtil(getMax(startVertex, endVertex) + 1);
        for (int i = 0; i < arr.length; i++)
            graph.addOrientedEdge(startVertex[i], endVertex[i]);
        System.out.println(isCyclic(graph) ? "yes" : "no"); // Проверка наличия циклов и вывод результата
        scan.close(); // Закрытие сканера
    }
}
