/*
 * Программа предназначена для кластеризации точек в трехмерном пространстве
 * на основе заданного максимального расстояния между точками.
 * Две точки принадлежат одному кластеру, если расстояние между ними меньше или равно
 * указанному расстоянию. Алгоритм использует структуру данных Disjoint Set Union (Union-Find),
 * чтобы управлять объединением кластеров и проверкой принадлежности точек к ним.
 *
 * Алгоритм:
 * 1. Считываем входные данные: максимальное расстояние (distanceRequired) и количество точек (count).
 * 2. Создаём структуру данных DisJointSet для управления объединением кластеров.
 * 3. Считываем координаты каждой точки и добавляем их в структуру данных DisJointSet.
 * 4. Проверяем каждую новую точку на расстояние до всех ранее считанных точек:
 *    - Если расстояние между новой точкой и существующей точкой меньше или равно максимальному расстоянию,
 *      объединяем их в один кластер с помощью операции `union`.
 * 5. Определяем размеры всех уникальных кластеров:
 *    - Используем метод `findSet` для поиска корня каждого кластера.
 *    - Проверяем, были ли уже учтены найденные корни, чтобы избежать повторений.
 * 6. Сортируем размеры кластеров по убыванию.
 * 7. Выводим размеры кластеров в порядке убывания.
 *
 * Пример работы:
 * Входные данные:
 * 10 (максимальное расстояние), затем координаты точек.
 * Программа находит все кластеры точек, которые находятся на расстоянии не более 10 друг от друга,
 * группирует их и выводит размеры групп по убыванию.
 */
package by.it.group351001.shimanchuk.lesson14;

import java.util.*;

public class PointsA {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int distanceRequired = scanner.nextInt();
        int count = scanner.nextInt();

        DisJointSet<Point> dsu = new DisJointSet<Point>();

        for (int i = 0; i < count; i++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            int z = scanner.nextInt();
            Point point = new Point(x, y, z);
            dsu.makeSet(point);

            for (Point existingPoint : dsu) {
                if (point.distanceTo(existingPoint) <= distanceRequired) {
                    dsu.union(point, existingPoint);
                }
            }
        }

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

        Collections.sort(clusterSizes);
        Collections.reverse(clusterSizes);

        for (int size : clusterSizes) {
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

        double distanceTo(Point other) {
            return Math.hypot(Math.hypot(x - other.x, y - other.y), z - other.z);
        }

    }
}