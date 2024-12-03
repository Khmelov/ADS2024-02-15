package by.it.group351001.orlovich.lesson14;


import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class StatesHanoiTowerC {
    private static class DisjointSetUnion {
        int[] parent;
        int[] size;

        DisjointSetUnion(int size) {
            parent = new int[size];
            this.size = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;
                this.size[i] = 1;
            }
        }

        int size(int i) {
            return size[find(i)];
        }

        int find(int i) {
            return i == parent[i] ? i : (parent[i] = find(parent[i]));
        }

        void union(int a, int b) {
            a = find(a);
            b = find(b);
            if (a != b) {
                if (size[a] < size[b]) {
                    int temp = a;
                    a = b;
                    b = temp;
                }
                parent[b] = a;
                size[a] += size[b];
            }
        }
    }

    static int[] carryingOver(int N, int step, int k) {
        var x = (N % 2 == 0) ? 0 : 0;
        var y = (N % 2 == 0) ? 1 : 2;
        var z = (N % 2 == 0) ? 2 : 1;

        var result = new int[3];

        var t = (step / (1 << (k - 1)) - 1) / 2;

        var axes = (k % 2 != 0)
                ? new int[] { x, y, z }
                : new int[] { x, z, y };

        result[axes[t % 3]] = -1;
        result[axes[(t + 1) % 3]] = 1;

        return result;
    }

    public static void main(String[] args) {
        int N;
        try (var scanner = new Scanner(System.in)) {
            N = scanner.nextInt();
        }

        var size = (1 << N) - 1;
        var stepsHeights = new int[N];
        Arrays.fill(stepsHeights, -1);

        var dsu = new DisjointSetUnion(size);
        int[] heights = { N, 0, 0 };

        for (int i = 0; i < size; i++) {
            var delta = carryingOver(N, i + 1, Integer.numberOfTrailingZeros(i + 1) + 1);
            for (int j = 0; j < 3; j++)
                heights[j] += delta[j];
            var max = Arrays.stream(heights).max().getAsInt();
            if (stepsHeights[max - 1] == -1)
                stepsHeights[max - 1] = i;
            else
                dsu.union(stepsHeights[max - 1], i);
        }

        var sizes = new int[N];
        for (int i = 0; i < N; i++)
            if (stepsHeights[i] != -1)
                sizes[i] = dsu.size(stepsHeights[i]);

        System.out.println(Arrays.stream(sizes)
                .filter(current -> current > 0)
                .sorted()
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(" ")));
    }
}