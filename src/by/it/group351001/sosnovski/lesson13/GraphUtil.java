package by.it.group351001.sosnovski.lesson13;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraphUtil {

    /**
     Класс GraphUtil предоставляет набор методов для работы с графами.
     Основная цель — реализация структур данных для представления графа и
     предоставление утилит для манипуляции вершинами и рёбрами.
     Граф представлен в виде списка смежности, что позволяет эффективно
     хранить и обрабатывать данные.
     */

    // Список смежности для представления графа
    final List<List<Integer>> graph;
    // Количество вершин в графе
    public final int vertexCount;
    // Имена вершин для отображения (опционально)
    private String[] vertexNames;

    // Конструктор: инициализация графа с указанным количеством вершин
    public GraphUtil(int count) {
        vertexCount = count; // Сохраняем количество вершин
        graph = new ArrayList<>(); // Создаём список смежности
        vertexNames = new String[vertexCount]; // Создаём массив имён вершин

        // Инициализируем пустые списки смежности для каждой вершины
        for (int i = 0; i < vertexCount; i++) {
            graph.add(new ArrayList<>());
        }
    }

    // Добавление неориентированного ребра между вершинами a и b
    public void addEdge(int a, int b) {
        graph.get(a).add(b); // Добавляем b в список смежности вершины a
        graph.get(b).add(a); // Добавляем a в список смежности вершины b
    }

    // Добавление ориентированного ребра из вершины a в вершину b
    public void addOrientedEdge(int a, int b) {
        graph.get(a).add(b); // Добавляем b в список смежности вершины a
    }

    // Получение списка соседей для заданной вершины
    public int[] getNeighbors(int vertex) {
        // Создаём массив для соседей
        int[] neighbors = new int[graph.get(vertex).size()];
        int i = 0;

        // Копируем индексы соседей из списка смежности
        for (int index : graph.get(vertex)) {
            neighbors[i++] = index;
        }

        // Сортируем массив соседей для упрощения обработки
        Arrays.sort(neighbors);
        return neighbors; // Возвращаем отсортированный массив соседей
    }

    // Получение имени вершины по её индексу
    public String getVertexName(int vertex) {
        return vertexNames[vertex]; // Возвращаем имя вершины
    }

    // Установка имени для вершины
    public void setVertexNames(int vertex, String vertexName) {
        this.vertexNames[vertex] = vertexName; // Присваиваем имя вершине
    }
}
