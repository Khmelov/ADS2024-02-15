package by.it.group351001.ushakou.lesson14;

import java.util.Scanner;

public class StatesHanoiTowerC {

    // Вложенный класс для реализации системы непересекающихся множеств (DSU).
    private static class DSU {
        int[] parent; // Массив для хранения родительского элемента для каждой вершины.
        int[] size;   // Массив для хранения размера множества, к которому принадлежит вершина.

        // Конструктор для создания DSU с заданным количеством элементов.
        DSU(int size) {
            parent = new int[size]; // Инициализация массива родительских элементов.
            this.size = new int[size]; // Инициализация массива размеров множеств.
        }

        // Создание множества с единственным элементом.
        void makeSet(int v) {
            parent[v] = v; // Устанавливаем родителем вершину саму себя.
            size[v] = 1;   // Размер множества равен 1.
        }

        // Возвращает размер множества, к которому принадлежит элемент `v`.
        int size(int v) {
            return size[findSet(v)]; // Используем путь к корню для нахождения размера.
        }

        // Находит корень множества, к которому принадлежит элемент `v` (с путевой компрессией).
        int findSet(int v) {
            if (v == parent[v]) // Если вершина является своим родителем, возвращаем ее.
                return v;
            return parent[v] = findSet(parent[v]); // Рекурсивно находим корень и оптимизируем путь.
        }

        // Объединяет два множества, к которым принадлежат элементы `a` и `b`.
        void unionSets(int a, int b) {
            a = findSet(a); // Находим корень множества для `a`.
            b = findSet(b); // Находим корень множества для `b`.
            if (a != b) { // Если элементы находятся в разных множествах, объединяем их.
                if (size[a] < size[b]) { // Меняем местами, чтобы объединять меньшее множество в большее.
                    int temp = a;
                    a = b;
                    b = temp;
                }
                parent[b] = a; // Устанавливаем родителя для `b`.
                size[a] += size[b]; // Увеличиваем размер множества `a`.
            }
        }
    }

    // Метод для вычисления изменений в высотах столбцов на определенном шаге.
    static int[] carryingOver(int N, int step, int k) {
        int axisX = 0, axisY, axisZ; // Оси для перемещения.
        axisY = (N % 2 == 0) ? 1 : 2; // Определяем направление перемещения для четного и нечетного числа дисков.
        axisZ = (N % 2 == 0) ? 2 : 1;

        int[] result = new int[3]; // Массив для хранения изменений высот.
        int t = (step / (1 << (k - 1)) - 1) / 2; // Вычисляем шаг перемещения.

        // Определяем оси для перемещения в зависимости от номера диска.
        int[] mapping = (k % 2 != 0) ? new int[]{axisX, axisY, axisZ} : new int[]{axisX, axisZ, axisY};
        int from = mapping[t % 3]; // Определяем начальную ось.
        int to = mapping[(t + 1) % 3]; // Определяем конечную ось.

        result[from] = -1; // Уменьшаем высоту начального столбца.
        result[to] = 1;   // Увеличиваем высоту конечного столбца.
        return result;
    }

    // Метод для подсчета количества делений числа на 2.
    static int countBits(int num) {
        int count = 0; // Счетчик делений.
        while (num % 2 == 0) { // Пока число делится на 2, увеличиваем счетчик.
            count++;
            num /= 2; // Делим число на 2.
        }
        return count;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Инициализация Scanner для чтения ввода.
        int N = scanner.nextInt(); // Читаем количество дисков.
        int max_size = (1 << N) - 1; // Вычисляем общее количество шагов.
        int[] steps_heights = new int[N]; // Массив для хранения индексов шагов максимальной высоты для каждого диска.
        for (int i = 0; i < N; i++) // Инициализируем массив значениями -1.
            steps_heights[i] = -1;

        DSU dsu = new DSU(max_size); // Создаем DSU для управления шагами.
        int[] heights = new int[3]; // Массив высот столбцов.
        heights[0] = N; // Устанавливаем начальную высоту первого столбца.

        // Проходим по каждому шагу.
        for (int i = 0; i < max_size; i++) {
            int step = i + 1; // Номер шага.
            // Вычисляем изменения высот в зависимости от номера шага.
            int[] delta = (step % 2 != 0) ? carryingOver(N, step, 1) : carryingOver(N, step, countBits(step) + 1);

            for (int j = 0; j < 3; j++) // Обновляем высоты столбцов.
                heights[j] += delta[j];

            int max = Math.max(heights[0], Math.max(heights[1], heights[2])); // Находим максимальную высоту.
            dsu.makeSet(i); // Создаем множество для текущего шага.

            int maxHeightIndex = max - 1; // Определяем индекс для текущей максимальной высоты.
            if (steps_heights[maxHeightIndex] == -1) // Если индекс не инициализирован, сохраняем текущий шаг.
                steps_heights[maxHeightIndex] = i;
            else // Иначе объединяем текущий шаг с предыдущим, имеющим ту же высоту.
                dsu.unionSets(steps_heights[maxHeightIndex], i);
        }

        int[] sizes = new int[N]; // Массив для хранения размеров кластеров шагов.
        for (int i = 0; i < N; i++) // Заполняем массив размерами кластеров.
            if (steps_heights[i] != -1)
                sizes[i] = dsu.size(steps_heights[i]);

        StringBuilder sb = new StringBuilder(); // Строковый буфер для вывода.
        for (int i = 0; i < N; i++) {
            int max = i;
            // Находим индекс максимального размера кластера.
            for (int j = i + 1; j < N; j++)
                if (sizes[max] < sizes[j])
                    max = j;
            if (sizes[max] == 0) // Если больше кластеров нет, выходим из цикла.
                break;
            int temp = sizes[max]; // Меняем местами текущий и максимальный размер.
            sizes[max] = sizes[i];
            sizes[i] = temp;
            sb.insert(0, sizes[i] + " "); // Добавляем размер кластера в строку.
        }
        sb.deleteCharAt(sb.length() - 1); // Удаляем последний пробел.
        System.out.println(sb); // Выводим результат.
    }
}
