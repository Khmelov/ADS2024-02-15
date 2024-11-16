package by.it.group310901.baradzin.lesson13;

import java.util.Scanner;
import java.util.Stack;

public class GraphB extends GraphA {

    public GraphB(Scanner scanner) {
        super(scanner);
    }

    public GraphB(Scanner scanner, String separator) {
        super(scanner, separator);
    }

    public boolean hasCycle() {
        for (var i : elements.keySet())
            if (cycleTraverse(i, new Stack<String>()))
                return true;
        return false;
    }

    protected boolean cycleTraverse(String node, Stack<String> visited) {
        visited.add(node);
        if (elements.get(node) != null)
            for (var next : elements.get(node))
                if (visited.contains(next) || cycleTraverse(next, visited))
                    return true;
        visited.remove(node);
        return false;
    }

    public static void main(String[] args) {
        System.out.print(new GraphB(new Scanner(System.in)).hasCycle() ? "yes" : "no");
    }
}
