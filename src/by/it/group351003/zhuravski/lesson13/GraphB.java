package by.it.group351003.zhuravski.lesson13;

import java.util.Scanner;

public class GraphB {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        DomGraph graph = new DomGraph(input);
        boolean hasCycles = false;
        try {
            graph.topologicalSort();
        }
        catch (RuntimeException e) {
            hasCycles = true;
        }
        System.out.println(hasCycles ? "yes" : "no");
        scan.close();
    }
}
