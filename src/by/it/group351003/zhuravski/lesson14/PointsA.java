package by.it.group351003.zhuravski.lesson14;

import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Scanner;

class Point {
    int x;
    int y;
    int z;
};

public class PointsA {
    public static double findDistance(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2) + Math.pow(p1.z - p2.z, 2));
    }

    public static void main(String[] args) {
        DomDSU dsu = new DomDSU();

        Scanner scan = new Scanner(System.in);
        int maxDist = scan.nextInt();
        int pointCount = scan.nextInt();
        for (int i = 0; i < pointCount; i++) {
            Point p = new Point();
            p.x = scan.nextInt();
            p.y = scan.nextInt();
            p.z = scan.nextInt();
            dsu.add(p);

            int j = 0;
            Iterator<DSU> iter = dsu.elems.iterator();
            while (j < dsu.elems.size() - 1) {
                DSU cur = iter.next();
                if (findDistance((Point)cur.val, p) < maxDist) {
                    dsu.union(j, dsu.elems.size() - 1);
                }
                j++;
            }
        }
        scan.close();

        Integer[] sizes = dsu.getSizes();
        Arrays.sort(sizes);
        for (int i = sizes.length - 1; i >= 0; i--) {
            System.out.printf("%d ", sizes[i]);
        }
        System.out.println();
    }
}
