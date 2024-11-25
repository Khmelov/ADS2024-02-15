package by.it.group351003.pisarchik.lesson14;

import java.util.*;

public class PointsA {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int distanceRequired = scanner.nextInt();
        int count = scanner.nextInt();

        DSU<Point> dsu = new DSU<>();
        List<Point> points = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            int z = scanner.nextInt();
            Point point = new Point(x, y, z);

            // Проверка на дубликаты
            if (!dsu.contains(point)) {
                dsu.makeSet(point);
                points.add(point);
            } else {
                System.out.println("Duplicate point detected: " + point);
            }
        }

        // Объединяем точки
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                if (points.get(i).distanceTo(points.get(j)) <= distanceRequired) {
                    dsu.union(points.get(i), points.get(j));
                }
            }
        }

        // Подсчет размеров кластеров
        Map<Point, Integer> clusterSizes = new HashMap<>();
        for (Point point : points) {
            Point root = new Point(dsu.findSet(point));
            clusterSizes.put(root, dsu.getClusterSize(root));
        }

        // Сортировка и вывод размеров кластеров
        List<Integer> sizes = new ArrayList<>(clusterSizes.values());
        Collections.sort(sizes, Collections.reverseOrder());

        for (int size : sizes) {
            System.out.print(size + " ");
        }
    }

    static class Point {
        int x, y, z;

        Point(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        Point(Point other) {
            this.x = other.x;
            this.y = other.y;
            this.z = other.z;
        }

        double distanceTo(Point other) {
            return Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2) + Math.pow(z - other.z, 2));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y && z == point.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }

        @Override
        public String toString() {
            return "Point{" + "x=" + x + ", y=" + y + ", z=" + z + '}';
        }
    }
}
