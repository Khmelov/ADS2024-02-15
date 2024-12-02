package by.it.group351002.khmylko.lesson14;

import java.util.*;

public class PointsA {

    static class DSU {
        private final int[] parent;
        private final int[] size;

        // Конструктор DSU
        public DSU(int n) {
            parent = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }


        public int find(int x) {
            if (x != parent[x]) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }


        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if (rootX != rootY) {

                if (size[rootX] < size[rootY]) {
                    parent[rootX] = rootY;
                    size[rootY] += size[rootX];
                } else {
                    parent[rootY] = rootX;
                    size[rootX] += size[rootY];
                }
            }
        }

        public int getSize(int x) {
            return size[find(x)];
        }
    }

    private static double distance(int[] point1, int[] point2) {
        int dx = point1[0] - point2[0];
        int dy = point1[1] - point2[1];
        int dz = point1[2] - point2[2];
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        double D = scanner.nextDouble();
        int N = scanner.nextInt();

        // Чтение точек
        int[][] points = new int[N][3];
        for (int i = 0; i < N; i++) {
            points[i][0] = scanner.nextInt();  // X координата
            points[i][1] = scanner.nextInt();  // Y координата
            points[i][2] = scanner.nextInt();  // Z координата
        }


        DSU dsu = new DSU(N);

        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (distance(points[i], points[j]) < D) {
                    dsu.union(i, j);
                }
            }
        }


        Map<Integer, Integer> clusterSizes = new HashMap<>();
        for (int i = 0; i < N; i++) {
            int root = dsu.find(i);
            clusterSizes.put(root, clusterSizes.getOrDefault(root, 0) + 1);
        }

        List<Integer> sortedSizes = new ArrayList<>(clusterSizes.values());
        sortedSizes.sort(Collections.reverseOrder());

        // Вывод размеров кластеров
        for (int size : sortedSizes) {
            System.out.print(size + " ");
        }
    }
}
