package by.it.group351001.orlovich.lesson14;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class PointsA {

    private static class Point {
        final int x, y, z;

        Point(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        double distanceTo(Point other) {
            return Math.hypot(Math.hypot(this.x - other.x, this.y - other.y), this.z - other.z);
        }
    }

    private static ArrayList<Integer> calculateClusterSizes(int distance, int size, ArrayList<Point> points) {
        var dsu = new DisjointSetUnion(size);

        for (int i = 0; i < size; i++)
            for (int j = i + 1; j < size; j++)
                if (points.get(i).distanceTo(points.get(j)) < distance)
                    dsu.union(i, j);

        var dsuSizes = dsu.getSizes();

        dsuSizes.removeIf(s -> s == 0);
        Collections.sort(dsuSizes, Collections.reverseOrder());

        return dsuSizes;
    }

    public static void main(String[] args) {
        int distance, length;
        var points = new ArrayList<Point>();

        try (var scanner = new Scanner(System.in)) {
            distance = scanner.nextInt();
            length = scanner.nextInt();

            for (int i = 0; i < length; i++)
                points.add(new Point(scanner.nextInt(), scanner.nextInt(), scanner.nextInt()));
        }

        var dsuSizes = calculateClusterSizes(distance, length, points);

        var result = String.join(" ", dsuSizes.stream().map(String::valueOf).toArray(String[]::new));
        System.out.println(result);
    }
}
