package by.it.group310901.tit.lesson14;

import java.util.*;

public class PointsA {

    // Вложенный класс для структуры объединения-поиск (DSU)
    static class DSU {
        private final int[] parent; // Массив для хранения родителей каждого элемента
        private final int[] size;   // Массив для хранения размеров множеств

        // Конструктор DSU
        public DSU(int n) {
            parent = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i; // Каждый элемент является своим родителем
                size[i] = 1;   // Изначально размер множества равен 1
            }
        }

        // Метод для нахождения корня элемента с сжатием пути
        public int find(int x) {
            if (x != parent[x]) {
                parent[x] = find(parent[x]); // Сжимаем путь
            }
            return parent[x]; // Возвращаем корень
        }

        // Метод для объединения двух множеств
        public void union(int x, int y) {
            int rootX = find(x); // Находим корень для x
            int rootY = find(y); // Находим корень для y

            // Если корни разные, объединяем множества
            if (rootX != rootY) {
                // Объединяем по размеру
                if (size[rootX] < size[rootY]) {
                    parent[rootX] = rootY; // rootY становится родителем для rootX
                    size[rootY] += size[rootX]; // Обновляем размер множества
                } else {
                    parent[rootY] = rootX; // rootX становится родителем для rootY
                    size[rootX] += size[rootY]; // Обновляем размер множества
                }
            }
        }

        // Метод для получения размера множества, к которому принадлежит элемент x
        public int getSize(int x) {
            return size[find(x)]; // Возвращаем размер множества
        }
    }

    // Метод для вычисления расстояния между двумя точками в 3D пространстве
    private static double distance(int[] point1, int[] point2) {
        int dx = point1[0] - point2[0]; // Разность координат X
        int dy = point1[1] - point2[1]; // Разность координат Y
        int dz = point1[2] - point2[2]; // Разность координат Z
        return Math.sqrt(dx * dx + dy * dy + dz * dz); // Возвращаем евклидово расстояние
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Создаем сканер для ввода

        double D = scanner.nextDouble(); // Читаем максимальное расстояние для соединения точек
        int N = scanner.nextInt(); // Читаем количество точек

        // Чтение точек
        int[][] points = new int[N][3]; // Массив для хранения координат точек
        for (int i = 0; i < N; i++) {
            points[i][0] = scanner.nextInt();  // X координата
            points[i][1] = scanner.nextInt();  // Y координата
            points[i][2] = scanner.nextInt();  // Z координата
        }

        DSU dsu = new DSU(N); // Создаем экземпляр DSU для N точек

        // Объединяем точки, которые находятся на расстоянии меньше D
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (distance(points[i], points[j]) < D) {
                    dsu.union(i, j); // Объединяем точки i и j
                }
            }
        }

        // Словарь для хранения размеров кластеров
        Map<Integer, Integer> clusterSizes = new HashMap<>();
        for (int i = 0; i < N; i++) {
            int root = dsu.find(i); // Находим корень для точки i
            clusterSizes.put(root, clusterSizes.getOrDefault(root, 0) + 1); // Увеличиваем размер кластера
        }

        // Список для хранения размеров кластеров
        List<Integer> sortedSizes = new ArrayList<>(clusterSizes.values());
        sortedSizes.sort(Collections.reverseOrder()); // Сортируем размеры кластеров по убыванию

        // Вывод размеров кластеров
        for (int size : sortedSizes) {
            System.out.print(size + " "); // Печатаем размеры кластеров
        }
    }
}
