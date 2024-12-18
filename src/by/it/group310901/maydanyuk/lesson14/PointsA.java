package by.it.group310901.maydanyuk.lesson14;

import java.util.*;
/*Пусть у нас есть набор точек в трехмерном пространстве,
и мы хотим разбить их на кластеры на основе расстояний между ними.

Используем структуру данных DSU
с эвристикой по рангу или размеру поддерева
для объединения близких точек в один кластер.*/
public class PointsA {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Читаем входные данные: допустимое расстояние и количество точек
        int distanceRequired = scanner.nextInt();
        int count = scanner.nextInt();

        DisJointSet<Point> dsu = new DisJointSet<Point>();

        // Считываем точки и обрабатываем их
        for (int i = 0; i < count; i++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            int z = scanner.nextInt();
            Point point = new Point(x, y, z);
            dsu.makeSet(point); // Создаем отдельное множество для новой точки

            // Объединяем множества точек, если они находятся в пределах допустимого расстояния
            for (Point existingPoint : dsu) {
                if (point.distanceTo(existingPoint) <= distanceRequired) {
                    dsu.union(point, existingPoint);
                }
            }
        }

        List<Integer> clusterSizes = new ArrayList<>();
        HashSet<Point> set = new HashSet<>();
        // Определяем размеры всех кластеров (множества точек, которые можно объединить)
        for (Point existingPoint : dsu) {
            Point root = dsu.findSet(existingPoint);
            if (set.contains(root))
                continue;
            set.add(root);
            int size = dsu.getClusterSize(root);
            clusterSizes.add(size);
        }

        // Сортируем размеры кластеров по убыванию
        Collections.sort(clusterSizes);
        Collections.reverse(clusterSizes);

        // Выводим размеры кластеров
        for (int size : clusterSizes) {
            System.out.print(size + " ");
        }
    }

    // Класс, представляющий точку в 3D пространстве
    static class Point {
        int x, y, z;

        Point(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        // Метод для вычисления расстояния до другой точки
        double distanceTo(Point other) {
            return Math.hypot(Math.hypot(x - other.x, y - other.y), z - other.z);
        }
    }
}
