package by.it.group351001.yakovenko.lesson14;

import java.util.Scanner;

public class StatesHanoiTowerC {
    // Класс для работы с системой непересекающихся множеств (DSU)
    private static class DSU {
        int[] parent;
        int[] size;

        // Конструктор для инициализации DSU с заданным размером
        DSU(int size) {
            parent = new int[size];
            this.size = new int[size];
        }

        // Метод для создания нового множества с одним элементом
        void makeSet(int v) {
            parent[v] = v;
            size[v] = 1;
        }

        // Метод для получения размера множества, содержащего элемент v
        int size(int v) {
            return size[findSet(v)];
        }

        // Метод для нахождения представителя множества, содержащего элемент v
        int findSet(int v) {
            if (v == parent[v])
                return v;
            return parent[v] = findSet(parent[v]); // Сжатие пути
        }

        // Метод для объединения двух множеств, содержащих элементы a и b
        void unionSets(int a, int b) {
            a = findSet(a);
            b = findSet(b);
            if (a != b) {
                // Объединение по размеру
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

    // Метод для определения перемещения дисков в задаче Ханойской башни
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

    // Метод для подсчета количества завершающих нулевых битов в числе
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
        int N = scanner.nextInt(); // Количество дисков
        int max_size = (1 << N) - 1; // Максимальное количество шагов (2^N - 1)
        int[] steps_heights = new int[N]; // Массив для хранения высот на каждом шаге
        for (int i = 0; i < N; i++)
            steps_heights[i] = -1; // Инициализация массива значением -1
        DSU dsu = new DSU(max_size); // Создание объекта DSU для управления множествами
        int[] heights = new int[3]; // Массив для хранения высот трех стержней
        heights[0] = N; // Начальная высота первого стержня (все диски на первом стержне)

        for (int i = 0; i < max_size; i++) {
            int step = i + 1; // Текущий шаг
            // Определение перемещения дисков в зависимости от текущего шага
            int[] delta = (step % 2 != 0) ? carryingOver(N, step, 1) : carryingOver(N, step, countBits(step) + 1);

            // Обновление высот стержней в соответствии с перемещением дисков
            for (int j = 0; j < 3; j++)
                heights[j] += delta[j];

            int max = Math.max(heights[0], Math.max(heights[1], heights[2])); // Определение максимальной высоты среди стержней
            dsu.makeSet(i); // Создание нового множества для текущего шага

            int maxHeightIndex = max - 1; // Индекс максимальной высоты
            if (steps_heights[maxHeightIndex] == -1)
                steps_heights[maxHeightIndex] = i; // Если высота еще не была установлена, установить текущий шаг
            else
                dsu.unionSets(steps_heights[maxHeightIndex], i); // Иначе объединить множества текущего шага и шага с такой же высотой
        }

        int[] sizes = new int[N]; // Массив для хранения размеров множеств
        for (int i = 0; i < N; i++)
            if (steps_heights[i] != -1)
                sizes[i] = dsu.size(steps_heights[i]); // Заполнение массива размеров множеств

        StringBuilder sb = new StringBuilder(); // Строка для вывода результатов
        for (int i = 0; i < N; i++) {
            int max = i;
            for (int j = i + 1; j < N; j++)
                if (sizes[max] < sizes[j])
                    max = j; // Поиск максимального размера множества
            if (sizes[max] == 0)
                break; // Если размер равен 0, завершить цикл
            int temp = sizes[max];
            sizes[max] = sizes[i];
            sizes[i] = temp; // Обмен значениями
            sb.insert(0, sizes[i] + " "); // Добавление размера в строку
        }
        sb.deleteCharAt(sb.length() - 1); // Удаление последнего пробела
        System.out.println(sb); // Вывод результатов
    }
}
