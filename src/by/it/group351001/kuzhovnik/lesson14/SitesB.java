package by.it.group351001.kuzhovnik.lesson14;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class SitesB {
    public static void main(String[] args) {
        var points = new ArrayList<String>();
        var dsu = new DisjointSetUnion(1000);

        try (var scanner = new Scanner(System.in)) {
            points = new ArrayList<>();
            String line;
            while (!(line = scanner.next()).equals("end")) {
                var pair = line.split("\\+");
                var x1 = points.indexOf(pair[0]);
                if (x1 == -1)
                    points.add(pair[0]);
                x1 = points.indexOf(pair[0]);

                var x2 = points.indexOf(pair[1]);
                if (x2 == -1)
                    points.add(pair[1]);
                x2 = points.indexOf(pair[1]);

                dsu.union(x1, x2);
            }
        }

        var dsuSizes = dsu.getSizes();
        dsuSizes.sort(Collections.reverseOrder());

        System.out.println(String.join(" ", dsuSizes.stream().map(String::valueOf).toArray(String[]::new)));
    }
}