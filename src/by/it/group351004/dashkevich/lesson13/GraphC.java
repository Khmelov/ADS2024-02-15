package by.it.group351004.dashkevich.lesson13;

import java.util.*;

public class GraphC {

    static class LexicalComparator implements Comparator<String> {
        public int compare(String s1, String s2) {
            return s2.compareTo(s1);
        }
    }

    public static void main(String[] args) {
        Map<String, ArrayList<String>> neighbours = new HashMap<>();
        Stack<String> stack = new Stack<>();
        Set<String> visited = new HashSet<>();

        Scanner scanner = new Scanner(System.in);
        String[] vertexes = scanner.nextLine().split(",");
        scanner.close();

        String startVertex;
        String endVertex;

        for (String str : vertexes) {
            String[] current = str.trim().split("");
            startVertex = current[0];
            endVertex = (current[current.length - 1]);
            if (neighbours.get(startVertex) == null)
                neighbours.put(startVertex, new ArrayList<>());

            neighbours.get(startVertex).add(endVertex);
        }

        for (ArrayList<String> list: neighbours.values()) {
            list.sort(new LexicalComparator());
        }

        for (String vertex : neighbours.keySet()) {
            if (!visited.contains(vertex)) {
                dfs(neighbours, vertex, visited, stack);
            }
        }

        Map<String, ArrayList<String>> transpanentNeighbours = new HashMap<>();
        for (String value :neighbours.keySet()) {
            ArrayList<String> list = neighbours.get(value);
            for (String child : list) {
                if (transpanentNeighbours.get(child) == null)
                    transpanentNeighbours.put(child, new ArrayList<>());
                transpanentNeighbours.get(child).add(value);
            }
        }

        visited.clear();
        while (!stack.empty()) {
            String vertex = stack.pop();
            if (!visited.contains(vertex)) {
                StringBuilder result = new StringBuilder();
                dfs(transpanentNeighbours, vertex, visited, result);
                char[] charArr = result.toString().toCharArray();
                Arrays.sort(charArr);
                System.out.println(charArr);
            }
        }

    }

    private static void dfs(Map<String, ArrayList<String>> neighbours,
                            String vertex, Set<String> visited, Stack<String> stack) {
        visited.add(vertex);

        if (neighbours.get(vertex) != null) {
            for (String child : neighbours.get(vertex)) {
                if (!visited.contains(child)) {
                    dfs(neighbours, child, visited, stack);
                }
            }
        }

        stack.push(vertex);
    }

    private static void dfs(Map<String, ArrayList<String>> neighbours,
                            String vertex, Set<String> visited, StringBuilder result) {
        visited.add(vertex);
        result.append(vertex);
        if (neighbours.get(vertex) != null) {
            for (String child : neighbours.get(vertex)) {
                if (!visited.contains(child)) {
                    dfs(neighbours, child, visited, result);
                }
            }
        }
    }
}