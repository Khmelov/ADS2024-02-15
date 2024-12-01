package by.it.group310902.yoshchyk.lesson14;

import java.util.Arrays;

class MyDSU {
    private final int[] rootElements; // Массив для хранения корней элементов
    private final int[] parent;         // Массив для хранения родителей каждого элемента
    private final int[] size;           // Массив для хранения размеров множеств

    // Конструктор DSU
    MyDSU(final int n) {
        final int parentCount = (int) (Math.pow(2, n) - 1); // Число узлов в дереве

        parent = new int[parentCount]; // Инициализация массива родителей
        size = new int[parentCount];   // Инициализация массива размеров
        rootElements = new int[n + 1]; // Инициализация массива корней

        Arrays.fill(size, 1); // Каждый элемент изначально имеет размер 1
        Arrays.fill(rootElements, -1); // Устанавливаем корни в -1 (нет корня)

        // Инициализируем каждого узла как своего родителя
        for (int i = 0; i < parentCount; i++) {
            parent[i] = i;
        }
    }

    // Метод поиска сжатия пути
    int find(final int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]); // Рекурсивно находим корень и сжимаем путь
        }
        return parent[x]; // Возвращаем корень
    }

    // Метод объединения двух множеств
    void union(int x, final int y) {
        // Если x не имеет корня, устанавливаем y как его корень
        if (rootElements[x] == -1) {
            rootElements[x] = y;
        }
        // Обновляем x до его текущего корня
        x = rootElements[x];
        // Находим корни обоих множеств
        int rootX = find(x);
        int rootY = find(y);
        // Объединяем только если корни разные
        if (rootX != rootY) {
            // Объединяем по размеру
            if (size[rootX] < size[rootY]) {
                parent[rootX] = rootY; // Делаем rootY родителем для rootX
                size[rootY] += size[rootX]; // Обновляем размер нового корня
            } else {
                parent[rootY] = rootX; // Делаем rootX родителем для rootY
                size[rootX] += size[rootY]; // Обновляем размер нового корня
            }
        }
    }

    // Метод для получения размера множества, содержащего элемент x
    int getSize(int x) {
        return size[find(x)]; // Возвращаем размер множества, найденного по корню
    }
}