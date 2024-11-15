package by.it.group351003.kalinckovich.lesson13;

import java.util.*;

public class GraphB {
    private static final HashMap<String, PriorityQueue<String>> graph = new HashMap<>();
    private static final HashMap<String,Integer> inDegree = new HashMap<>();

    private static void addComponent(String[] pair){
        graph.putIfAbsent(pair[0], new PriorityQueue<>());
        graph.putIfAbsent(pair[1], new PriorityQueue<>());
        graph.get(pair[0]).add(pair[1]);

        inDegree.putIfAbsent(pair[0], 0);
        inDegree.put(pair[1], inDegree.getOrDefault(pair[1], 0) + 1);
    }

    private static boolean isHaveCycles(){
        Queue<String> queue = new LinkedList<>();
        List<String> topologicalOrder = new ArrayList<>();

        for(String s : graph.keySet()){
            if(inDegree.get(s) == 0){
                queue.add(s);
            }
        }

        while(!queue.isEmpty()){
            String node = queue.poll();
            topologicalOrder.add(node);
            for(String s : graph.get(node)){
                inDegree.put(s, inDegree.get(s) - 1);
                if(inDegree.get(s) == 0){
                    queue.add(s);
                }
            }
        }
        return topologicalOrder.size() < graph.size();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String n = scanner.nextLine();
        String[] parts = n.split(", ");
        for(String s : parts) {
            addComponent(s.split(" -> "));
        }
        scanner.close();
        if(isHaveCycles()){
            System.out.println("yes");
        }else{
            System.out.println("no");
        }
        graph.clear();
        inDegree.clear();
    }
}
