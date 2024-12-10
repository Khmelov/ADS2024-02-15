package by.it.group351003.kalinckovich.lesson13;

import java.util.*;

public class GraphC {
    private static final HashMap<String, PriorityQueue<String>> graph = new HashMap<>();
    private static final HashMap<String,PriorityQueue<String>> reverseGraph = new HashMap<>();
    private static final HashSet<String> visited = new HashSet<>();
    private static final Stack<String> stack = new Stack<>();
    private static final PriorityQueue<String> component = new PriorityQueue<>();
    private static void addComponent(String[] pair){
        graph.putIfAbsent(pair[0], new PriorityQueue<>());
        graph.putIfAbsent(pair[1], new PriorityQueue<>());

        reverseGraph.putIfAbsent(pair[0], new PriorityQueue<>());
        reverseGraph.putIfAbsent(pair[1], new PriorityQueue<>());

        graph.get(pair[0]).add(pair[1]);
        reverseGraph.get(pair[1]).add(pair[0]);
    }

    private static void dfsFirst(String node){
        visited.add(node);

        for (String item : graph.get(node)) {
            if (!visited.contains(item)) {
                dfsFirst(item);
            }
        }

        stack.push(node);
    }

    private static void dfsSecond(String node,PriorityQueue<String> component){
        visited.add(node);
        component.add(node);
        for (String item : reverseGraph.get(node)) {
            if (!visited.contains(item)) {
                dfsSecond(item,component);
            }
        }
    }

    private static void findSccs(){
        for(String node : graph.keySet()){
            if(!visited.contains(node)){
                dfsFirst(node);
            }
        }

        visited.clear();
        StringBuilder stringBuilder = new StringBuilder();

        while(!stack.isEmpty()){
            String node = stack.pop();
            if(!visited.contains(node)){
                dfsSecond(node,component);
                while (!component.isEmpty()) {
                    stringBuilder.append(component.poll());
                }
                stringBuilder.append("\n");
            }
        }
        System.out.println(stringBuilder);
    }



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String n = scanner.nextLine();
        String[] parts = n.split(", ");
        for(String s : parts) {
            addComponent(s.split("->"));
        }
        scanner.close();
        findSccs();
        graph.clear();
        reverseGraph.clear();
        visited.clear();
        component.clear();
        stack.clear();
    }
}
