package by.it.group351001.ushakou.lesson14;

import java.util.*;

public class PointsA {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Создаем объект Scanner для чтения ввода.

        int distanceRequired = scanner.nextInt(); // Читаем требуемое расстояние для объединения точек в один кластер.
        int count = scanner.nextInt(); // Читаем количество точек.

        DSU<Point> dsu = new DSU<>(); // Создаем экземпляр DSU для управления множествами точек.

        // Читаем координаты точек и объединяем их в кластеры.
        for (int i = 0; i < count; i++) {
            int x = scanner.nextInt(); // Координата x текущей точки.
            int y = scanner.nextInt(); // Координата y текущей точки.
            int z = scanner.nextInt(); // Координата z текущей точки.
            Point point = new Point(x, y, z); // Создаем объект Point для текущей точки.
            dsu.makeSet(point); // Добавляем точку в DSU как новое множество.

            // Проверяем расстояние до уже существующих точек.
            for (Point existingPoint : dsu) {
                if (point.distanceTo(existingPoint) <= distanceRequired) { // Если расстояние меньше или равно допустимому.
                    dsu.union(point, existingPoint); // Объединяем точки в один кластер.
                }
            }
        }

        List<Integer> clusterSizes = new ArrayList<>(); // Список для хранения размеров кластеров.
        HashSet<Point> set = new HashSet<>(); // Хранение уже обработанных корневых точек.

        // Вычисляем размеры всех кластеров.
        for (Point existingPoint : dsu) {
            Point root = dsu.findSet(existingPoint); // Находим корень множества для текущей точки.
            if (set.contains(root)) // Если корень уже обработан, пропускаем.
                continue;
            set.add(root); // Добавляем корень в множество обработанных.
            int size = dsu.getClusterSize(root); // Получаем размер кластера.
            clusterSizes.add(size); // Добавляем размер в список.
        }

        // Сортируем размеры кластеров в порядке убывания.
        Collections.sort(clusterSizes);
        Collections.reverse(clusterSizes);

        // Выводим размеры кластеров.
        for (int size : clusterSizes) {
            System.out.print(size + " ");
        }
    }

    // Класс для представления точки в трехмерном пространстве.
    static class Point {
        int x, y, z; // Координаты точки.

        // Конструктор для создания точки с заданными координатами.
        Point(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        // Метод для вычисления расстояния между текущей точкой и другой.
        double distanceTo(Point other) {
            return Math.hypot(Math.hypot(x - other.x, y - other.y), z - other.z); // Вычисляем 3D-расстояние между точками.
        }
    }
}
