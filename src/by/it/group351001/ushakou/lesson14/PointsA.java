package by.it.group351001.ushakou.lesson14;

// Импорт необходимых пакетов
import java.util.*;

public class PointsA {
    public static void main(String[] args) {
        // Создаем сканер для чтения ввода
        Scanner scanner = new Scanner(System.in);

        // Читаем требуемое расстояние и количество точек
        int distanceRequired = scanner.nextInt();
        int count = scanner.nextInt();

        // Создаем объединяющее множество (дискретный набор) для управления кластерами точек
        DisJointSet<Point> dsu = new DisJointSet<Point>();

        // Проходим по количеству точек для чтения их координат
        for (int i = 0; i < count; i++) {
            // Читаем координаты точки
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            int z = scanner.nextInt();
            // Создаем новую точку с заданными координатами
            Point point = new Point(x, y, z);
            // Добавляем точку в объединяющее множество
            dsu.makeSet(point);

            // Проверяем существующие точки в объединяющем множестве
            for (Point existingPoint : dsu) {
                // Если расстояние до существующей точки меньше или равно требуемому, объединяем их
                if (point.distanceTo(existingPoint) <= distanceRequired) {
                    dsu.union(point, existingPoint);
                }
            }
        }

        // Список для хранения размеров каждого кластера
        List<Integer> clusterSizes = new ArrayList<>();
        // Множество для отслеживания уже учтенных корней
        HashSet<Point> set = new HashSet<>();

        // Итерация по всем точкам для нахождения размера каждого уникального кластера
        for (Point existingPoint : dsu) {
            // Находим корень текущей точки
            Point root = dsu.findSet(existingPoint);
            // Если мы уже учли этот корень, пропускаем его
            if (set.contains(root))
                continue;
            // Отмечаем этот корень как учтенный
            set.add(root);
            // Получаем размер кластера для этого корня и добавляем в список
            int size = dsu.getClusterSize(root);
            clusterSizes.add(size);
        }

        // Сортируем размеры кластеров в порядке убывания
        Collections.sort(clusterSizes);
        Collections.reverse(clusterSizes);

        // Вывод размеров кластеров
        for (int size : clusterSizes) {
            System.out.print(size + " ");
        }
    }

    // Вложенный класс для представления точки в 3D пространстве
    static class Point {
        int x, y, z;

        // Конструктор для класса Point
        Point(int x, int y, int z) {
            this.x = x; // Присваиваем координату x
            this.y = y; // Присваиваем координату y
            this.z = z; // Присваиваем координату z
        }

        // Метод для расчета расстояния до другой точки
        double distanceTo(Point other) {
            // Вычисляем и возвращаем евклидово расстояние в 3D
            return Math.hypot(Math.hypot(x - other.x, y - other.y), z - other.z);
        }
    }
}