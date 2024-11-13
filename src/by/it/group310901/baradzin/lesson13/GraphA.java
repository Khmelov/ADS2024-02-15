package by.it.group310901.baradzin.lesson13;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class GraphA {

    protected HashMap<String, ArrayList<String>> elements = new HashMap<>();

    public GraphA(Scanner scanner) {
        this(scanner, " -> ");
    }

    public GraphA(Scanner scanner, String separator) {
        for (var connections : scanner.nextLine().split(", ")) {
            var nodes = connections.split(separator);
            if (!elements.containsKey(nodes[0]))
                elements.put(nodes[0], new ArrayList<>());
            elements.get(nodes[0]).add(nodes[1]);
        }
        scanner.close();
    }

    public GraphA sort() {
        for (var i : elements.values())
            i.sort((a, b) -> b.compareTo(a));
        return this;
    }

    public String toString() {
        var sb = new StringBuilder();
        var stack = new Stack<String>();
        var visited = new Stack<String>();

        for (var node : elements.keySet())
            if (!visited.contains(node))
                traverse(node, visited, stack);

        sb.append(stack.pop());
        while (!stack.empty())
            sb.append(' ').append(stack.pop());

        return sb.toString();
    }

    protected Stack<String> traverse(String node, Stack<String> visited, Stack<String> stack) {
        visited.add(node);
        if (elements.get(node) != null)
            for (var next : elements.get(node))
                if (!visited.contains(next))
                    traverse(next, visited, stack);
        stack.push(node);
        return stack;
    }

    public static void main(String[] args) {
        System.out.print(new GraphA(new Scanner(System.in)).sort());
    }
}
