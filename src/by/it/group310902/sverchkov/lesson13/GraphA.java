package by.it.group310902.sverchkov.lesson13;

import java.util.*;

public class GraphA {
    private Map<String, ArrayList<String>> elements = new HashMap<>();

    public GraphA(Scanner input) {
        for (var connections : input.nextLine().split(", ")) {
            var nodes = connections.split(" -> ");
            if (!elements.containsKey(nodes[0]))
                elements.put(nodes[0], new ArrayList<>());
            elements.get(nodes[0]).add(nodes[1]);
        }
        input.close();
    }

    public GraphA sort() {
        for (var i : elements.values())
            i.sort((a, b) -> b.compareTo(a));
        return this;
    }

    public String toString() {
        var sb = new StringBuilder();
        var stack = new Stack<String>();
        var visited = new HashSet<String>();

        for (var node : elements.keySet())
            if (!visited.contains(node))
                dfs(node, visited, stack);

        sb.append(stack.pop());
        while (!stack.empty())
            sb.append(' ').append(stack.pop());

        return sb.toString();
    }

    private void dfs(String node, Set<String> visited, Stack<String> stack) {
        visited.add(node);
        if (elements.get(node) != null)
            for (var next : elements.get(node))
                if (!visited.contains(next))
                    dfs(next, visited, stack);
        stack.push(node);
    }

    public static void main(String[] args) {
        System.out.print(new GraphA(new Scanner(System.in)).sort());
    }
}
