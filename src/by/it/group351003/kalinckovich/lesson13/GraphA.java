package by.it.group351003.kalinckovich.lesson13;

import java.util.*;

public class GraphA {
    private static final HashMap<String, PriorityQueue<String>> graph = new HashMap<>();
    private static final HashMap<String, Integer> inDegree = new HashMap<>(); // Степени входа для Kahn's Algorithm


    private static void addComponent(String[] pair) {
        TreeSet<String> set = new TreeSet<>();

        graph.putIfAbsent(pair[0], new PriorityQueue<>());
        graph.putIfAbsent(pair[1], new PriorityQueue<>());
        graph.get(pair[0]).add(pair[1]);

        inDegree.put(pair[1], inDegree.getOrDefault(pair[1], 0) + 1);
        inDegree.putIfAbsent(pair[0], 0);
    }

    private static String topologicalSort() {
        Queue<String> queue = new LinkedList<>();
        List<String> topologyOrder = new ArrayList<>();

        for (String s : graph.keySet()) {
            if(inDegree.get(s) == 0) {
                queue.add(s);
            }
        }

        while (!queue.isEmpty()) {
            String s = queue.poll();
            topologyOrder.add(s);

            for(String item : graph.get(s)) {
                inDegree.put(item, inDegree.get(item) - 1);
                if(inDegree.get(item) == 0) {
                    queue.add(item);
                }
            }
        }
        StringBuilder result = new StringBuilder();
        for(String s : topologyOrder) {
            result.append(s).append(" ");
        }
        return result.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String n = scanner.nextLine();
        String[] parts = n.split(", ");
        for(String s : parts) {
            addComponent(s.split(" -> "));
        }
        scanner.close();
        System.out.println(topologicalSort());
        graph.clear();
        inDegree.clear();
    }
}
