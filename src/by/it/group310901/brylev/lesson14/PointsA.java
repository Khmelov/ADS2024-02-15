package by.it.group310901.brylev.lesson14;

import java.util.*;

public class PointsA {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Вводим необходимые данные
        int distanceRequired = scanner.nextInt();  // Максимальное расстояние для объединения точек
        int count = scanner.nextInt();  // Количество точек

        // Создаем экземпляр DisjointSet для хранения множества точек
        DisJointSet<Point> dsu = new DisJointSet<Point>();

        // Процесс ввода точек и их добавления в множества
        for (int i = 0; i < count; i++) {
            // Чтение координат точки
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            int z = scanner.nextInt();
            Point point = new Point(x, y, z);  // Создаем точку
            dsu.makeSet(point);  // Создаем множество для точки

            // Проходим по всем уже добавленным точкам и объединяем те, что находятся на расстоянии меньше или равно distanceRequired
            for (Point existingPoint : dsu) {
                if (point.distanceTo(existingPoint) <= distanceRequired) {
                    dsu.union(point, existingPoint);  // Объединяем множества
                }
            }
        }

        // Сбор всех размеров кластеров
        List<Integer> clusterSizes = new ArrayList<>();
        HashSet<Point> set = new HashSet<>();
        // Для каждой точки находим ее корень и вычисляем размер соответствующего множества (кластера)
        for (Point existingPoint : dsu) {
            Point root = dsu.findSet(existingPoint);  // Находим корень (представителя множества)
            if (set.contains(root))  // Если уже встречали это множество, пропускаем
                continue;
            set.add(root);  // Добавляем корень в набор
            int size = dsu.getClusterSize(root);  // Получаем размер множества
            clusterSizes.add(size);  // Добавляем размер в список
        }

        // Сортировка размеров кластеров в порядке убывания
        Collections.sort(clusterSizes);
        Collections.reverse(clusterSizes);

        // Выводим размеры кластеров
        for (int size : clusterSizes) {
            System.out.print(size + " ");  // Печать размера каждого кластера через пробел
        }
    }

    // Вложенный класс Point, представляющий точку в 3D пространстве
    static class Point {
        int x, y, z;  // Координаты точки

        // Конструктор для создания точки с заданными координатами
        Point(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        // Метод для вычисления расстояния между двумя точками в 3D пространстве
        double distanceTo(Point other) {
            // Используем теорему Пифагора для вычисления расстояния
            return Math.hypot(Math.hypot(x - other.x, y - other.y), z - other.z);
        }
    }
}