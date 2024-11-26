package by.it.group351003.zhuravski.lesson13;

import java.util.Arrays;
import java.util.Scanner;

public class GraphC {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        DomGraph graph = new DomGraph("");
        graph.pushC(input);
        Character[][] arr = graph.findComponents();
        for (Character[] subarr : arr) {
            Arrays.sort(subarr);
            for (Character c : subarr) {
                System.out.print(c);
            }
            System.out.print('\n');
        }
        scan.close();
    }
}
