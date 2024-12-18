package by.it.group310901.maydanyuk.lesson14;

import java.util.Scanner;
/*В методе main вводится высота N стартовой пирамиды А.
Переместить кольца нужно на пустую пирамиду B.
В задаче есть еще одна пустая пирамида С.

Сгруппируйте с помощью DSU в поддеревья все те шаги решения задачи
у которых одинаковая наибольшая высота пирамид A B C.
Стартовое состояние учитывать не нужно.

Выведите на консоль размеры получившихся поддеревьев в порядке возрастания.*/
public class StatesHanoiTowerC {
    private static class DSU {
        int[] parent;
        int[] size;

        // Конструктор для инициализации DSU
        DSU(int size) {
            parent = new int[size];
            this.size = new int[size];
        }

        // Создание множества для элемента
        void makeSet(int v) {
            parent[v] = v;
            size[v] = 1;
        }

        // Получение размера множества, к которому принадлежит элемент
        int size(int v) {
            return size[findSet(v)];
        }

        // Поиск корневого элемента множества
        int findSet(int v) {
            if (v == parent[v])
                return v;
            return parent[v] = findSet(parent[v]); // Оптимизация пути
        }

        // Объединение двух множеств
        void unionSets(int a, int b) {
            a = findSet(a);
            b = findSet(b);
            if (a != b) {
                // Объединение меньшего множества с большим
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

    // Функция для вычисления перемещения диска
    static int[] carryingOver(int N, int step, int k) {
        int axisX = 0, axisY, axisZ;
        axisY = (N % 2 == 0) ? 1 : 2;
        axisZ = (N % 2 == 0) ? 2 : 1;

        int[] result = new int[3];
        int t = (step / (1 << (k - 1)) - 1) / 2;

        int[] mapping = (k % 2 != 0) ? new int[]{axisX, axisY, axisZ} : new int[]{axisX, axisZ, axisY};
        int from = mapping[t % 3];
        int to = mapping[(t + 1) % 3];

        result[from] = -1;
        result[to] = 1;
        return result;
    }

    // Функция для подсчета количества битов, равных 0, в числе
    static int countBits(int num) {
        int count = 0;
        while (num % 2 == 0) {
            count++;
            num /= 2;
        }
        return count;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt(); // Читаем количество дисков
        int max_size = (1 << N) - 1; // Максимальное количество шагов
        int[] steps_heights = new int[N];
        for (int i = 0; i < N; i++)
            steps_heights[i] = -1;
        DSU dsu = new DSU(max_size);
        int[] heights = new int[3]; // Инициализация высот башен
        heights[0] = N;
        for (int i = 0; i < max_size; i++) {

            int step = i + 1;
            int[] delta = (step % 2 != 0) ? carryingOver(N, step, 1) : carryingOver(N, step, countBits(step) + 1);

            for (int j = 0; j < 3; j++)
                heights[j] += delta[j];

            int max = Math.max(heights[0], Math.max(heights[1], heights[2]));
            dsu.makeSet(i);

            int maxHeightIndex = max - 1;
            if (steps_heights[maxHeightIndex] == -1)
                steps_heights[maxHeightIndex] = i;
            else
                dsu.unionSets(steps_heights[maxHeightIndex], i);
        }

        int[] sizes = new int[N];
        for (int i = 0; i < N; i++)
            if (steps_heights[i] != -1)
                sizes[i] = dsu.size(steps_heights[i]);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++) {
            int max = i;
            for (int j = i + 1; j < N; j++)
                if (sizes[max] < sizes[j])
                    max = j;
            if (sizes[max] == 0)
                break;
            int temp = sizes[max];
            sizes[max] = sizes[i];
            sizes[i] = temp;
            sb.insert(0, sizes[i] + " ");
        }
        sb.deleteCharAt(sb.length() - 1);
        System.out.println(sb); // Вывод размеров множеств
    }
}
