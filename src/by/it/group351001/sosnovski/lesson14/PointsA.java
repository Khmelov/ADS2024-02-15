package by.it.group351001.sosnovski.lesson14;

import java.util.*;

public class PointsA {

    /**
     решаем задачу кластеризации точек в трёхмерном пространстве. Основные действия:

     Пользователь задаёт максимальное расстояние (distanceRequired),
     при котором две точки считаются соседями, и количество точек (count).
     Каждая точка добавляется в систему непересекающихся множеств (Union-Find).
     Если расстояние между текущей точкой и уже добавленными точками меньше или
     равно заданному, их множества объединяются.
     В конце программа выводит размеры всех кластеров, отсортированных по убыванию.
     */

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Читаем максимальное расстояние и количество точек
        int distanceRequired = scanner.nextInt(); // Максимальное расстояние для объединения в кластер
        int count = scanner.nextInt(); // Количество точек

        // Создаём систему непересекающихся множеств
        DisJointSet<Point> dsu = new DisJointSet<>();

        // Обрабатываем каждую точку
        for (int i = 0; i < count; i++) {
            // Читаем координаты точки
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            int z = scanner.nextInt();

            // Создаём новую точку
            Point point = new Point(x, y, z);

            // Добавляем точку как отдельное множество
            dsu.makeSet(point);

            // Сравниваем текущую точку со всеми уже существующими
            for (Point existingPoint : dsu) {
                // Если расстояние между точками не превышает заданное, объединяем их множества
                if (point.distanceTo(existingPoint) <= distanceRequired) {
                    dsu.union(point, existingPoint);
                }
            }
        }

        // Собираем размеры всех кластеров
        List<Integer> clusterSizes = new ArrayList<>();
        HashSet<Point> set = new HashSet<>(); // Для предотвращения дублирования кластеров

        // Перебираем все точки в системе множеств
        for (Point existingPoint : dsu) {
            // Находим представителя (корень) множества для текущей точки
            Point root = dsu.findSet(existingPoint);

            // Если этот кластер уже учтён, пропускаем его
            if (set.contains(root)) {
                continue;
            }

            // Добавляем корень в множество учтённых кластеров
            set.add(root);

            // Получаем размер кластера и добавляем его в список
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

    // Класс для представления трёхмерной точки
    static class Point {
        int x, y, z; // Координаты точки

        // Конструктор
        Point(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        // Метод для вычисления расстояния между текущей точкой и другой точкой
        double distanceTo(Point other) {
            // Расстояние рассчитывается по формуле Евклидова расстояния
            return Math.hypot(Math.hypot(x - other.x, y - other.y), z - other.z);
        }
    }
}

