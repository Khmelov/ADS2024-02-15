package by.it.group310901.baradzin.lesson13;

import java.util.Scanner;
import java.util.Stack;

public class GraphB extends GraphA {

    public GraphB(Scanner input) {
        super(input);
    }

    public boolean hasCycle() {
        for (var i : elements.keySet())
            if (dfs(i, new Stack<String>()))
                return true;
        return false;
    }

    protected boolean dfs(String node, Stack<String> visited) {
        visited.add(node);
        if (elements.get(node) != null)
            for (var next : elements.get(node))
                if (visited.contains(next) || dfs(next, visited))
                    return true;
        visited.remove(node);
        return false;
    }

    public static void main(String[] args) {
        System.out.print(new GraphB(new Scanner(System.in)).hasCycle() ? "yes" : "no");
    }
}
