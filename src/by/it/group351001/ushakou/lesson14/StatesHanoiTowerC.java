package by.it.group351001.ushakou.lesson14; // Указываем пакет, в котором находится класс

import java.util.Scanner; // Импортируем класс Scanner для чтения ввода

// Основной класс для решения задачи Ханойской башни с учётом её состояний
public class StatesHanoiTowerC {
    public static void main(String[] args) { // Главный метод программы

        int N; // Количество дисков в Ханойской башне

        // Используем Scanner для чтения количества дисков
        try (Scanner scanner = new Scanner(System.in)) {
            N = scanner.nextInt(); // Читаем количество дисков
        }

        int max_size = (1 << N) - 1; // Общее количество шагов (2^N - 1)
        int[] steps_heights = new int[N]; // Массив для отслеживания шагов высот

        // Инициализируем массив шагов высот значением -1
        for (int i = 0; i < N; i++) {
            steps_heights[i] = -1;
        }

        DSU dsu = new DSU(max_size); // Создаём структуру данных "Система непересекающихся множеств" (DSU)
        int[] heights = new int[3]; // Высоты трёх стержней

        heights[0] = N; // Изначально все диски находятся на первом стержне
        for (int i = 0; i < max_size; i++) { // Перебираем все шаги
            int step = i + 1; // Текущий шаг
            int[] delta; // Изменения высот стержней

            if (step % 2 != 0) { // Если шаг нечётный
                delta = carryingOver(N, step, 1); // Перемещаем диск между стержнями
            } else { // Если шаг чётный
                int count = step; // Количество оставшихся шагов
                int countDisk = 0; // Счётчик дисков

                // Определяем, какой диск перемещать
                while (count % 2 == 0) {
                    countDisk++;
                    count /= 2;
                }

                delta = carryingOver(N, step, countDisk + 1); // Перемещаем диск
            }

            // Обновляем высоты стержней
            for (int j = 0; j < 3; j++) {
                heights[j] += delta[j];
            }

            int max = max(heights); // Определяем максимальную высоту
            dsu.make_set(i); // Создаём множество для текущего шага

            // Проверяем, был ли этот шаг уже обработан
            if (steps_heights[max - 1] == -1) {
                steps_heights[max - 1] = i; // Запоминаем шаг
            } else {
                dsu.union_sets(steps_heights[max - 1], i); // Объединяем множества
            }
        }

        int[] sizes = new int[N]; // Массив для хранения размеров множеств
        for (int i = 0; i < N; i++) { // Перебираем все высоты
            if (steps_heights[i] != -1) { // Если шаг был обработан
                sizes[i] = dsu.size(steps_heights[i]); // Определяем размер множества
            }
        }

        StringBuilder sb = new StringBuilder(); // Создаём объект для построения строки результата

        for (int i = 0; i < N; i++) { // Сортируем результаты по убыванию
            int max = i;
            for (int j = i + 1; j < N; j++) {
                if (sizes[max] < sizes[j]) { // Если найден больший размер множества
                    max = j;
                }
            }

            if (sizes[max] == 0) { // Если размер равен нулю, прекращаем обработку
                break;
            }

            // Меняем местами размеры множеств
            int temp = sizes[max];
            sizes[max] = sizes[i];
            sizes[i] = temp;
            sb.insert(0, sizes[i] + " "); // Добавляем размер множества в строку
        }

        sb.deleteCharAt(sb.length() - 1); // Удаляем последний пробел
        System.out.println(sb); // Выводим результат
    }

    // Функция для нахождения максимальной высоты
    private static int max(int[] heights) {
        return Math.max(Math.max(heights[0], heights[1]), heights[2]);
    }

    // Функция для расчёта изменений высот при переносе дисков
    static int[] carryingOver(int N, int step, int k) {
        int t, axisY, axisZ;

        // Определяем порядок осей в зависимости от чётности N
        if (N % 2 == 0) {
            axisY = 1;
            axisZ = 2;
        } else {
            axisY = 2;
            axisZ = 1;
        }

        int[] result = new int[3]; // Массив для хранения изменений высот
        t = (step / (1 << (k - 1)) - 1) / 2; // Рассчитываем текущий шаг
        int from = 0, to = 0; // Инициализируем начальный и конечный стержни

        // Определяем начальный и конечный стержни в зависимости от текущего шага
        if (k % 2 != 0)
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
        else {
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

        result[from] = -1; // Уменьшаем высоту на начальном стержне
        result[to] = 1; // Увеличиваем высоту на конечном стержне

        return result; // Возвращаем изменения высот
    }

    // Класс для реализации системы непересекающихся множеств (DSU)
    private static class DSU {

        private final int[] parent; // Массив для хранения родителей
        private final int[] size; // Массив для хранения размеров множеств

        public DSU(int size) {
            parent = new int[size]; // Инициализируем массив родителей
            this.size = new int[size]; // Инициализируем массив размеров множеств
        }

        // Создаём множество для вершины
        public void make_set(int v) {
            parent[v] = v;
            size[v] = 1;
        }

        // Получаем размер множества
        public int size(int v) {
            return size[find_set(v)];
        }

        // Находим представителя множества (сжатие путей)
        public int find_set(int v) {
            if (v == parent[v])
                return v;
            return parent[v] = find_set(parent[v]);
        }

        // Объединяем два множества
        public void union_sets(int a, int b) {
            a = find_set(a);
            b = find_set(b);
            if (a != b) {
                if (size[a] < size[b]) { // Объединяем меньший в больший
                    int temp = a;
                    a = b;
                    b = temp;
                }
                parent[b] = a;
                size[a] += size[b];
            }
        }
    }
}

/*
 * Задача состоит в том, чтобы вычислить размеры компонент, соответствующих состояниям Ханойской башни
 * на каждом шаге перемещения дисков. Используется алгоритм, включающий подсчёт высот и системы DSU
 * для объединения шагов с одинаковыми высотами. Результатом является список размеров групп шагов,
 * отсортированных по убыванию.
 */
