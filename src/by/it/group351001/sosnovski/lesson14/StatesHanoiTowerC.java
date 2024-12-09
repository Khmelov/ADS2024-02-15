package by.it.group351001.sosnovski.lesson14;

import java.util.Scanner;

public class StatesHanoiTowerC {

    /**
     решаем задачу анализа движения дисков в задаче Ханойской башни.
     моделируем процесс и определяем размеры групп состояний, которые
     соответствуют одной высоте башни. Основные этапы:

     Используется структура непересекающихся множеств (DSU) для группировки состояний.
     Состояния рассчитываются на основе битового представления шага.
     Результат — размеры групп состояний для каждой высоты башни, отсортированные по убыванию.
     */

    // Реализация структуры непересекающихся множеств (DSU)
    private static class DSU {
        int[] parent; // Родительский элемент множества
        int[] size;   // Размер множества

        // Конструктор: инициализация массивов
        DSU(int size) {
            parent = new int[size];
            this.size = new int[size];
        }

        // Создать множество для элемента v
        void makeSet(int v) {
            parent[v] = v; // Сам себе родитель
            size[v] = 1;   // Начальный размер множества равен 1
        }

        // Получить размер множества, к которому принадлежит элемент v
        int size(int v) {
            return size[findSet(v)];
        }

        // Найти представителя множества для элемента v с путевой компрессией
        int findSet(int v) {
            if (v == parent[v])
                return v;
            return parent[v] = findSet(parent[v]); // Компрессия пути
        }

        // Объединить множества двух элементов
        void unionSets(int a, int b) {
            a = findSet(a);
            b = findSet(b);
            if (a != b) {
                // Всегда объединяем меньший по размеру в больший
                if (size[a] < size[b]) {
                    int temp = a;
                    a = b;
                    b = temp;
                }
                parent[b] = a; // Обновляем родителя
                size[a] += size[b]; // Обновляем размер
            }
        }
    }

    // Рассчитывает перенос диска между башнями на k-м уровне
    static int[] carryingOver(int N, int step, int k) {
        // Определение осей перемещения
        int axisX = 0, axisY, axisZ;
        axisY = (N % 2 == 0) ? 1 : 2; // Чётное N: Y = 1, Z = 2
        axisZ = (N % 2 == 0) ? 2 : 1; // Нечётное N: Y = 2, Z = 1

        int[] result = new int[3]; // Результат перемещения
        int t = (step / (1 << (k - 1)) - 1) / 2; // Определяем текущую фазу движения

        // Карта направлений в зависимости от уровня k
        int[] mapping = (k % 2 != 0) ? new int[]{axisX, axisY, axisZ} : new int[]{axisX, axisZ, axisY};
        int from = mapping[t % 3]; // Откуда переместить
        int to = mapping[(t + 1) % 3]; // Куда переместить

        result[from] = -1; // Уменьшаем высоту на "from"
        result[to] = 1;   // Увеличиваем высоту на "to"
        return result;
    }

    // Подсчёт количества бит в числе
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
        int max_size = (1 << N) - 1; // Общее количество состояний
        int[] steps_heights = new int[N]; // Индексы состояний по высотам
        for (int i = 0; i < N; i++) steps_heights[i] = -1;

        DSU dsu = new DSU(max_size); // Инициализация DSU для состояний
        int[] heights = new int[3]; // Высоты башен
        heights[0] = N; // Начальная высота первой башни

        // Основной цикл по состояниям
        for (int i = 0; i < max_size; i++) {
            int step = i + 1;

            // Вычисляем изменения высот для текущего шага
            int[] delta = (step % 2 != 0)
                    ? carryingOver(N, step, 1)
                    : carryingOver(N, step, countBits(step) + 1);

            // Обновляем высоты башен
            for (int j = 0; j < 3; j++) heights[j] += delta[j];

            // Определяем максимальную высоту среди башен
            int max = Math.max(heights[0], Math.max(heights[1], heights[2]));
            dsu.makeSet(i); // Создаём множество для текущего шага

            // Запоминаем индекс состояния для текущей высоты
            int maxHeightIndex = max - 1;
            if (steps_heights[maxHeightIndex] == -1)
                steps_heights[maxHeightIndex] = i;
            else
                dsu.unionSets(steps_heights[maxHeightIndex], i); // Объединяем состояния
        }

        // Определяем размеры групп состояний
        int[] sizes = new int[N];
        for (int i = 0; i < N; i++)
            if (steps_heights[i] != -1)
                sizes[i] = dsu.size(steps_heights[i]);

        // Сортируем размеры групп состояний по убыванию
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++) {
            int max = i;
            for (int j = i + 1; j < N; j++)
                if (sizes[max] < sizes[j]) max = j;

            if (sizes[max] == 0) break;

            int temp = sizes[max];
            sizes[max] = sizes[i];
            sizes[i] = temp;
            sb.insert(0, sizes[i] + " ");
        }

        sb.deleteCharAt(sb.length() - 1); // Убираем лишний пробел
        System.out.println(sb); // Выводим результат
    }
}

