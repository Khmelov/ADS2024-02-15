package by.it.group351003.kalinckovich.lesson14;

import java.util.Arrays;

class MyDSU {
    private final int[] rootElements;
    private final int[] parent;
    private final int[] size;

    MyDSU(final int n) {

        final int parentCount = (int) (Math.pow(2,n) - 1);

        parent = new int[parentCount];
        size = new int[parentCount];
        rootElements = new int[n + 1];
        Arrays.fill(size, 1);
        Arrays.fill(rootElements, -1);
        for (int i = 0; i < parentCount; i++) {
            parent[i] = i;
        }
    }

    // Поиск сжатия пути
    int find(final int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    // Объединение по размеру поддерева
    void union(int x, final int y) {
        if(rootElements[x] == -1){
            rootElements[x] = y;
        }
        x = rootElements[x];
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

    // Получение размера множества
    int getSize(int x) {
        return size[find(x)];
    }
}
