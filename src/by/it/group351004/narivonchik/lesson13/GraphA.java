package by.it.group351004.narivonchik.lesson13;

import java.util.*;
public class GraphA {

    public static void main(String[] args) {
    Map<String, ArrayList<String>> graph = new HashMap<>();
    getGraph(graph);
    topologicalSort(graph);
}
    private static void getGraph(Map<String, ArrayList<String>> graph) {
        Scanner scan = new Scanner(System.in);      //формат строки на входе: 1 -> 3, 2 -> 4, ... 4 -> 5
        boolean isEnd = false;                      //могут быть буквы: A -> B ...
        while (!isEnd) {
            String vertexOut = scan.next();                   //считали стартовую вершину
            if (!graph.containsKey(vertexOut)) {              //если в графе ее еще нет - добавляем и инициализируем массив входных вершин
                graph.put(vertexOut, new ArrayList<>());
            }
            String edge = scan.next();                      //пропускаем ->
            String vertexIn = scan.next();                  //считываем вершину куда входим "3," и обрезаем запятую если есть
            if (vertexIn.charAt(vertexIn.length() - 1) == ',') {
                vertexIn = vertexIn.substring(0, vertexIn.length() - 1);
            } else {
                isEnd = true;
            }
            graph.get(vertexOut).add(vertexIn);         //добавляем в соответствующий массив вершину куда входим
        }
        scan.close();
    }

    static void topologicalSort(Map<String, ArrayList<String>> graph) {
        Stack<String> stack = new Stack<>();
        Set<String> visited = new HashSet<>();

        for (ArrayList<String> array : graph.values()) {    //сортируем массивы входных вершин по убыванию
            array.sort(Comparator.reverseOrder());
        }

        for (String node : graph.keySet()) {
            if (!visited.contains(node)){
                depthFirstSearch(node, graph, visited, stack); //выполняем поиск в глубину для каждой ноды
            }
        }

        while (!stack.isEmpty()) {
            System.out.print(stack.pop() + " ");
        }
    }
    static void depthFirstSearch(String node, Map<String, ArrayList<String>> graph, Set<String> visited, Stack<String> stack) {
        visited.add(node);  //помечаем посещенный узел
        if (graph.get(node)!=null){
            for (String nextNode: graph.get(node)) {
                if (!visited.contains(nextNode)){
                    depthFirstSearch(nextNode, graph, visited, stack); //рекурсивно обходим граф в глубину
                }
            }
        }
        stack.push(node);   //при выходе из обхода от данной ноды пушим его
    }






}
