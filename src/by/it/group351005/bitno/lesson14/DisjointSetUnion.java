package by.it.group351005.bitno.lesson14;
import java.util.ArrayList;

public class DisjointSetUnion {
    int[] parent;
    int[] rank;

    DisjointSetUnion(int size) {
        parent = new int[size];
        rank = new int[size];
        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    void union(int a, int b) {
        a = find(a);
        b = find(b);
        if (a != b) {
            if (rank[a] < rank[b]) {
                int temp = a;
                a = b;
                b = temp;
            }
            parent[b] = a;
            if (rank[a] == rank[b])
                rank[a]++;
        }
    }

    int find(int i) {
        return i == parent[i] ? i : find(parent[i]);
    }

    ArrayList<Integer> getSizes() {
        var componentSizes = new ArrayList<Integer>();
        for (int i = 0; i < parent.length; i++) {
            var root = find(i);
            while (componentSizes.size() <= root)
                componentSizes.add(0);
            componentSizes.set(root, componentSizes.get(root) + 1);
        }
        return componentSizes;
    }
}