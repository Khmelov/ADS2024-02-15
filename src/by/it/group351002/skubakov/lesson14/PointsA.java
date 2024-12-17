package by.it.group351002.skubakov.lesson14;

import java.util.*;
//
public class PointsA {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //Считывание входных данных
        int distanceRequired = scanner.nextInt();
        int count = scanner.nextInt();

        //Создание множества из входных данных
        DisJointSet<Point> dsu = new DisJointSet<Point>();

        for (int i = 0; i < count; i++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            int z = scanner.nextInt();
            Point point = new Point(x, y, z);
            dsu.makeSet(point);

            /*Для каждой новой точки проверяется расстояние до всех существующих точек в множестве.
            Если расстояние меньше заданного, точки объединяются в один кластер.*/
            for (Point existingPoint : dsu) {
                if (point.distanceTo(existingPoint) < distanceRequired) {
                    dsu.union(point, existingPoint);
                }
            }
        }

        /*Создается список для хранения размеров кластеров и множество для отслеживания уникальных корневых точек.
        Для каждой точки определяется корневая точка кластера, и если она еще не была добавлена в множество,
        добавляется размер кластера в список*/
        List<Integer> clusterSizes = new ArrayList<>();
        HashSet<Point> set = new HashSet<>();
        for (Point existingPoint : dsu) {
            Point root = dsu.findSet(existingPoint);
            if (set.contains(root))
                continue;
            set.add(root);
            int size = dsu.getClusterSize(root);
            clusterSizes.add(size);
        }

        //Список размеров кластеров сортируется в порядке убывания и выводится на экран.
        Collections.sort(clusterSizes);
        Collections.reverse(clusterSizes);

        for (int size : clusterSizes) {
            System.out.print(size + " ");
        }

    }

    /*Вспомогательный класс, представляющий точку в трехмерных координатах и имеющий
    метод по нахождению расстояния между двумя точками*/
    static class Point {
        int x, y, z;

        Point(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        double distanceTo(Point other) {

            return Math.hypot(Math.hypot(x - other.x, y - other.y), z - other.z);
        }

    }
}