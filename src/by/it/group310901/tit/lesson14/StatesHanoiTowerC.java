package by.it.group310901.tit.lesson14;

import java.util.Scanner;

public class StatesHanoiTowerC {
    public static void main(String[] args) {
        int N;

        // Считываем количество дисков из ввода
        try (Scanner scanner = new Scanner(System.in)) {
            N = scanner.nextInt();
        }

        // Максимальное количество шагов для N дисков (2^N - 1)
        int max_size = (1 << N) - 1;
        int[] steps_heights = new int[N];

        // Инициализируем массив высот шагов
        for (int i = 0; i < N; i++) {
            steps_heights[i] = -1; // -1 означает, что шаг ещё не был зарегистрирован
        }

        // Создаем структуру данных для объединения множеств (DSU)
        DSU dsu = new DSU(max_size);
        int[] heights = new int[3]; // Высоты столбцов

        heights[0] = N; // Изначально все диски находятся на первом столбце
        for (int i = 0; i < max_size; i++) {
            int step = i + 1; // Текущий шаг
            int[] delta;

            // Определяем, как изменяются высоты в зависимости от шага
            if (step % 2 != 0) {
                // Если шаг нечетный, используем один метод
                delta = carryingOver(N, step, 1);
            } else {
                // Если шаг четный, определяем количество дисков, которые перемещаются
                int count = step;
                int countDisk = 0;

                // Подсчитываем количество дисков, перемещаемых в текущем шаге
                while (count % 2 == 0) {
                    countDisk++;
                    count /= 2;
                }

                delta = carryingOver(N, step, countDisk + 1);
            }

            // Обновляем высоты столбцов
            for (int j = 0; j < 3; j++) {
                heights[j] += delta[j];
            }

            // Находим максимальную высоту среди столбцов
            int max = max(heights);
            dsu.make_set(i); // Создаем множество для текущего шага
            if (steps_heights[max - 1] == -1) {
                // Если шаг ещё не зарегистрирован, сохраняем его
                steps_heights[max - 1] = i;
            } else {
                // Иначе объединяем множества
                dsu.union_sets(steps_heights[max - 1], i);
            }
        }

        int[] sizes = new int[N];
        // Подсчитываем размеры множеств для каждого шага
        for (int i = 0; i < N; i++) {
            if (steps_heights[i] != -1) {
                sizes[i] = dsu.size(steps_heights[i]);
            }
        }

        StringBuilder sb = new StringBuilder();

        // Сортируем массив размеров множеств
        for (int i = 0; i < N; i++) {
            int max = i;
            for (int j = i + 1; j < N; j++) {
                if (sizes[max] < sizes[j]) {
                    max = j;
                }
            }

            // Если размер равен 0, выходим из цикла
            if (sizes[max] == 0) {
                break;
            }

            // Меняем местами размеры
            int temp = sizes[max];
            sizes[max] = sizes[i];
            sizes[i] = temp;
            sb.insert(0, sizes[i] + " "); // Добавляем размер в строку
        }

        // Удаляем последний пробел и выводим результат
        sb.deleteCharAt(sb.length() - 1);
        System.out.println(sb);
    }

    // Метод для нахождения максимального значения в массиве высот
    private static int max(int[] heights) {
        return Math.max(Math.max(heights[0], heights[1]), heights[2]);
    }

    // Метод для вычисления изменений высот (перемещения дисков)
    static int[] carryingOver(int N, int step, int k) {
        int t, axisY, axisZ;

        // Определяем оси для перемещения в зависимости от четности N
        if (N % 2 == 0) {
            axisY = 1;
            axisZ = 2;
        } else {
            axisY = 2;
            axisZ = 1;
        }

        int[] result = new int[3];
        // Вычисляем перемещение дисков
        t = (step / (1 << (k - 1)) - 1) / 2;
        int from = 0, to = 0;

        // Определяем, откуда и куда перемещается диск
        if (k % 2 != 0) {
            switch (t % 3) {
                case 0:
                    to = axisY;
                    break;
                case 1:
                    from = axisY;
                    to = axisZ;
                    break;
                case 2:
                    from = axisZ;
                    break;
            }
        } else {
            switch (t % 3) {
                case 0:
                    to = axisZ;
                    break;
                case 1:
                    from = axisZ;
                    to = axisY;
                    break;
                case 2:
                    from = axisY;
                    break;
            }
        }

        result[from] = -1; // Диск убирается с оси
        result[to] = 1; // Диск добавляется на ось

        return result;
    }

    // Вложенный класс для реализации структуры объединения множеств (DSU)
    private static class DSU {
        private final int[] parent; // Массив для хранения родителей
        private final int[] size; // Массив для хранения размеров множеств

        // Конструктор, инициализирующий массивы
        public DSU(int size) {
            parent = new int[size];
            this.size = new int[size];
        }

        // Создание множества
        public void make_set(int v) {
            parent[v] = v;
            size[v] = 1;
        }

        // Получение размера множества
        public int size(int v) {
            return size[find_set(v)];
        }

        // Нахождение представителя множества
        public int find_set(int v) {
            if (v == parent[v])
                return v;
            return parent[v] = find_set(parent[v]); // Путь сжатия
        }

        // Объединение двух множеств
        public void union_sets(int a, int b) {
            a = find_set(a);
            b = find_set(b);
            if (a != b) {
                // Объединяем меньшее множество с большим
                if (size[a] < size[b]) {
                    int temp = a;
                    a = b;
                    b = temp;
                }
                parent[b] = a; // Устанавливаем родителя
                size[a] += size[b]; // Обновляем размер
            }
        }
    }
}
