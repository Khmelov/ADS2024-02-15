package by.it.group351003.zhuravski.lesson13;

import java.util.Scanner;

public class GraphA {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        DomGraph graph = new DomGraph(input);
        Character[] arr = graph.topologicalSort();
        for (Character c : arr) {
            System.out.print(c + " ");
        }
        scan.close();
    }
}
