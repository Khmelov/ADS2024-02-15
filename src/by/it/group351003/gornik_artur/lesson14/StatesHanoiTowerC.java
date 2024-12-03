package by.it.group351003.gornik_artur.lesson14;

import java.util.Scanner;

public class StatesHanoiTowerC {

    // Класс DSU (Disjoint Set Union) для реализации множества непересекающихся с помощью
    // эвристики по размеру поддерева и сжа тия пути
    //Сгруппируйте с помощью DSU в поддеревья все те шаги решения задачи
    //у которых одинаковая наибольшая высота пирамид A B C.
    //Стартовое состояние учитывать не нужно.
    private static class DSU {
        int[] parent; // массив для хранения родительских узлов
        int[] size;   // массив для хранения размеров поддеревьев

        // Конструктор для инициализации DSU
        DSU(int size) {
            parent = new int[size]; // массив родителей
            this.size = new int[size]; // массив размеров
        }

        // Метод для создания множества для элемента v
        void makeSet(int v) {
            parent[v] = v; // начальный родитель - сам элемент
            size[v] = 1;   // размер множества равен 1
        }

        // Метод для получения размера множества элемента v
        int size(int v) {
            return size[findSet(v)]; // возвращает размер множества, в котором находится v
        }

        // Метод для нахождения корня множества с сжатием пути
        int findSet(int v) {
            if (v == parent[v]) // если элемент сам себе родитель, это корень
                return v;
            return parent[v] = findSet(parent[v]); // сжатие пути
        }

        // Метод для объединения двух множеств
        void unionSets(int a, int b) {
            a = findSet(a); // находим корень множества a
            b = findSet(b); // находим корень множества b
            if (a != b) { // если корни разные, объединяем множества
                // Меняем местами корни, чтобы объединять меньшее дерево в большее
                if (size[a] < size[b]) {
                    int temp = a;
                    a = b;
                    b = temp;
                }
                parent[b] = a; // делаем a родителем b
                size[a] += size[b]; // увеличиваем размер множества
            }
        }
    }

    // Метод для вычисления перемещения дисков между стержнями
    static int[] carryingOver(int N, int step, int k) {
        int axisX = 0, axisY, axisZ;
        // Определяем оси в зависимости от четности количества дисков
        axisY = (N % 2 == 0) ? 1 : 2;
        axisZ = (N % 2 == 0) ? 2 : 1;

        int[] result = new int[3]; // результат перемещений
        int t = (step / (1 << (k - 1)) - 1) / 2;

        // Определяем порядок перемещений в зависимости от номера шага
        int[] mapping = (k % 2 != 0) ? new int[]{axisX, axisY, axisZ} : new int[]{axisX, axisZ, axisY};
        int from = mapping[t % 3];
        int to = mapping[(t + 1) % 3];

        result[from] = -1; // уменьшаем высоту на исходной оси
        result[to] = 1;    // увеличиваем высоту на целевой оси
        return result;
    }

    // Метод для подсчета количества младших битов числа (определяет степень двойки)
    static int countBits(int num) {
        int count = 0;
        while (num % 2 == 0) { // пока число делится на 2
            count++;
            num /= 2;
        }
        return count; // возвращаем количество делений
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt(); // количество дисков
        int max_size = (1 << N) - 1; // общее количество шагов: 2^N - 1
        int[] steps_heights = new int[N]; // массив для хранения высот на каждом шаге
        for (int i = 0; i < N; i++)
            steps_heights[i] = -1; // инициализация значениями -1
        DSU dsu = new DSU(max_size); // создаем DSU для управления состояниями
        int[] heights = new int[3]; // массив высот для трех стержней
        heights[0] = N; // начальная высота первого стержня равна количеству дисков

        // Проходим по всем шагам
        for (int i = 0; i < max_size; i++) {
            int step = i + 1; // текущий шаг
            // Определяем перемещения дисков на текущем шаге
            int[] delta = (step % 2 != 0) ? carryingOver(N, step, 1) : carryingOver(N, step, countBits(step) + 1);

            // Обновляем высоты стержней
            for (int j = 0; j < 3; j++)
                heights[j] += delta[j];

            // Находим максимальную высоту
            int max = Math.max(heights[0], Math.max(heights[1], heights[2]));
            dsu.makeSet(i); // создаем множество для текущего шага

            int maxHeightIndex = max - 1; // индекс максимальной высоты
            if (steps_heights[maxHeightIndex] == -1) // если высота еще не обработана
                steps_heights[maxHeightIndex] = i; // запоминаем индекс
            else
                dsu.unionSets(steps_heights[maxHeightIndex], i); // объединяем множества
        }

        // Массив для хранения размеров кластеров
        int[] sizes = new int[N];
        for (int i = 0; i < N; i++)
            if (steps_heights[i] != -1) // если высота обработана
                sizes[i] = dsu.size(steps_heights[i]); // получаем размер множества

        // Сортируем результаты и выводим их
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++) {
            int max = i;
            for (int j = i + 1; j < N; j++)
                if (sizes[max] < sizes[j]) // находим максимальный размер
                    max = j;
            if (sizes[max] == 0) // если размер равен нулю, прекращаем
                break;
            int temp = sizes[max];
            sizes[max] = sizes[i];
            sizes[i] = temp;
            sb.insert(0, sizes[i] + " "); // добавляем в строку
        }
        sb.deleteCharAt(sb.length() - 1); // удаляем последний пробел
        System.out.println(sb); // выводим результат
    }
}
