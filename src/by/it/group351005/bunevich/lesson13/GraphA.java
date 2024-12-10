package by.it.group351005.bunevich.lesson13;

import java.util.*;

public class GraphA {

    static void topologicalSortUnit(String node, Map<String, ArrayList<String>> graph, Set<String> visited, Stack<String> stack) {
        visited.add(node);

        if (graph.get(node)!=null){
            for (String nextNode: graph.get(node)
            ) {
                if (!visited.contains(nextNode)){
                    topologicalSortUnit(nextNode, graph, visited, stack);
                }
            }
        }
        stack.push(node);
    }
    /*Параметры: Принимает вершину, граф, множество посещенных вершин и стек.
    Функциональность: Отмечает вершину как посещенную. Если у вершины есть исходящие
    рёбра, рекурсивно посещает каждую связанную вершину, которая ещё не посещена.
    После обработки всех связанных вершин помещает текущую вершину в стек.*/

    static void topologicalSort(Map<String, ArrayList<String>> graph) {
        Stack<String> stack = new Stack<>();
        Set<String> visited = new HashSet<>();

        for (ArrayList<String> array : graph.values()) {
            array.sort(Comparator.reverseOrder());
        }

        for (String node : graph.keySet()
        ) {
            if (!visited.contains(node)){
                topologicalSortUnit(node, graph, visited, stack);
            }
        }

        while (!stack.isEmpty()) {
            System.out.print(stack.pop() + " ");
        }
    }
    /*Инициализирует стек и множество для отслеживания посещенных вершин.
    Сортирует списки смежности в обратном порядке для правильной обработки.
    Проходит по каждой вершине в графе, вызывая topologicalSortUnit для непосещенных вершин.
    После обработки всех вершин извлекает из стека и печатает топологический порядок.*/

    private static void getGraph(Map<String, ArrayList<String>> graph) {
        Scanner in = new Scanner(System.in);

        boolean isEnd = false;
        while (!isEnd) {
            String vertexOut = in.next();
            if (!graph.containsKey(vertexOut)) {
                graph.put(vertexOut, new ArrayList<>());
            }
            //0 -> 2, 1 -> 3, 2 -> 3, 0 -> 1
            String edge = in.next();
            String vertexIn = in.next();
            if (vertexIn.charAt(vertexIn.length() - 1) == ',') {
                vertexIn = vertexIn.substring(0, vertexIn.length() - 1);
            } else {
                isEnd = true;
            }
            graph.get(vertexOut).add(vertexIn);
        }
    }
    /*Использует Scanner для считывания ввода.
     Каждая строка содержит ребро от vertexOut к vertexIn.
     Добавляет каждое ребро в граф, удаляя запятые и определяя конец ввода.*/

    public static void main(String[] args) {
        Map<String, ArrayList<String>> graph = new HashMap<>();
        getGraph(graph);
        topologicalSort(graph);
    }
}
