package by.it.group351004.narivonchik.lesson13;

import java.util.*;

public class GraphB {
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

    public static void main(String[] args) {
        Map<String, ArrayList<String>> graph = new HashMap<>();
        getGraph(graph);
        boolean hasCycle = false;
        for (String node : graph.keySet()) {                                //перебираем все вершины и смотрим куда можем по ним добраться
            hasCycle = depthFirstSearch(node, graph, new HashSet<>());
            if (hasCycle) break;
        }
        System.out.println(hasCycle ? "yes" : "no");
    }

    private static boolean depthFirstSearch(String node, Map<String, ArrayList<String>> graph, Set<String> visited) {
        visited.add(node);
        boolean hasCycle = false;
        if (graph.get(node) != null)
            for (String nextNode : graph.get(node)) {       //перебираем все вершины и смотрим куда можем добраться
                if (visited.contains(nextNode))             //если тут уже были - цикл
                    hasCycle = true;
                else                                        //иначе - посмотрим.
                    hasCycle = depthFirstSearch(nextNode, graph, new HashSet<>(visited));
            }
        return hasCycle;
    }
}

