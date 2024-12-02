package by.it.group351004.narivonchik.lesson14;

import java.util.*;

public class PointsA {
    // Вложенный класс для представления точки в 3D пространстве
    static class Point {
        int x, y, z; // Координаты точки

        // Конструктор для создания точки
        Point(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        // Метод для вычисления расстояния до другой точки
        double distanceTo(Point other) {
            // Вычисление евклидова расстояния
            return Math.hypot(Math.hypot(x - other.x, y - other.y), z - other.z);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Чтение необходимого расстояния и количества точек
        int distanceRequired = scanner.nextInt();
        int count = scanner.nextInt();

        // Создание структуры непересекающихся множеств для точек
        DisJointSet<Point> dsu = new DisJointSet<Point>();

        // Цикл по количеству точек
        for (int i = 0; i < count; i++) {
            // Чтение координат точки
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            int z = scanner.nextInt();
            Point point = new Point(x, y, z); // Создание новой точки
            dsu.makeSet(point); // Создание множества для этой точки

            // Проверка расстояния до уже существующих точек
            for (Point existingPoint : dsu) {
                // Если расстояние между точками меньше или равно заданному, объединяем их
                if (point.distanceTo(existingPoint) <= distanceRequired) {
                    dsu.union(point, existingPoint);
                }
            }
        }

        // Список для хранения размеров кластеров
        List<Integer> clusterSizes = new ArrayList<>();
        HashSet<Point> set = new HashSet<>(); // Множество для отслеживания корней кластеров

        // Цикл по всем точкам в множестве
        for (Point existingPoint : dsu) {
            Point root = dsu.findSet(existingPoint); // Нахождение представителя кластера
            // Если этот корень уже добавлен, пропускаем
            if (set.contains(root))
                continue;
            set.add(root); // Добавляем корень в множество
            int size = dsu.getClusterSize(root); // Получаем размер кластера
            clusterSizes.add(size); // Добавляем размер в список
        }

        // Сортировка размеров кластеров по убыванию
        Collections.sort(clusterSizes);
        Collections.reverse(clusterSizes);

        // Вывод размеров кластеров
        for (int size : clusterSizes) {
            System.out.print(size + " ");
        }
    }


}