package by.it.group310902.chyliuk.lesson14;

import java.util.Scanner;

public class StatesHanoiTowerC {
    public static void main(String[] args) {

        int countRings; // количество колец

        try (Scanner scanner = new Scanner(System.in)) {
            countRings = scanner.nextInt();
        }

        int max_moving = (1 << countRings) - 1; // мах количество перемещений колец
        int[] countStepsInHeights = new int[countRings]; // количество шагов по высоте

        for (int i = 0; i < countRings; i++) {
            countStepsInHeights[i] = -1;
        }

        DSU dsu = new DSU(max_moving);
        int[] heights = new int[3]; // высоты на трёх стержнях

        heights[0] = countRings;
        for (int i = 0; i < max_moving; i++) {
            int step = i + 1; // берём шаг
            int[] delta; // изменения высоты в результате шага
            if (step % 2 != 0) { // если нечётный шаг
                delta = carryingOver(countRings, step, 1); // обновляем высоту стержня
            }
            else {

                int count = step;
                int countDisk = 0; // количество перемещённых колец

                while (count % 2 == 0) {
                    countDisk++; // считаем кольца
                    count /= 2;
                }

                delta = carryingOver(countRings, step, countDisk + 1); // обновляем высоту стержня
            }

            for (int j = 0; j < 3; j++) {
                heights[j] += delta[j]; // заменяем высоты
            }

            int max = max(heights); // мах высоту стержня
            dsu.make_set(i); // создаём новое множество для текущего шага
            if (countStepsInHeights[max - 1] == -1) { // Проверяем, существует ли уже шаг, который привел к максимальной высоте
                countStepsInHeights[max - 1] = i;
            }
            else {
                dsu.union_sets(countStepsInHeights[max - 1], i); // объединяем текущий шаг с шагом, который привел к максимальной высоте
            }
        }

        int[] sizes = new int[countRings]; // массив для хранения размеров поддеревьев для каждой высоты
        for (int i = 0; i < countRings; i++) {
            if (countStepsInHeights[i] != -1) {
                sizes[i] = dsu.size(countStepsInHeights[i]);
            }
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < countRings; i++) {

            int max = i;
            for (int j = i + 1; j < countRings; j++) {
                if (sizes[max] < sizes[j]) {
                    max = j;
                }
            }

            if (sizes[max] == 0) {
                break;
            }
            // помещаем максимальное значение на позицию текущего индекса i
            int temp = sizes[max];
            sizes[max] = sizes[i];
            sizes[i] = temp;
            sb.insert(0, sizes[i] + " ");
        }

        sb.deleteCharAt(sb.length() - 1);
        System.out.println(sb);
    }

    private static int max(int[] heights) {
        return Math.max(Math.max(heights[0], heights[1]), heights[2]);
    }

    static int[] carryingOver(int N, int step, int k) { // функция для управления движением колец
        // N - количество колец
        // step * текущиё шаг
        // k - количество колец перемещённых на текущем шаге
        int t, axisY, axisZ;

        if (N % 2 == 0) {
            axisY = 1; // первый стержень
            axisZ = 2; // второй стержень
        } else {
            axisY = 2; // второй стержень
            axisZ = 1; // первый стержень
        }

        int[] result = new int[3]; // массив для информации о перемещениях для трех стержней
        t = (step / (1 << (k - 1)) - 1) / 2; // Вычисление текущего шага
        int from = 0, to = 0; // Определение направления перемещения колец
        // Определение, какое кольцо куда двигается
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

        result[from] = -1;
        result[to] = 1;

        return result;
    }

    private static class DSU { // структуру данных "Объединять и находить"

        private final int[] parent; //Массив для хранения родительского элемент для каждого узла.
        private final int[] size; // будет хранить размер каждого поддерева

        public DSU(int size) {
            parent = new int[size];
            this.size = new int[size];
        }

        public void make_set(int v) { // Метод, который создает новое множество, состоящее из одного элемента v
            parent[v] = v; // Устанавливаем элемент v как своего собственного родителя
            size[v] = 1;
        }

        public int size(int v) {
            return size[find_set(v)];
        } //Метод для получения размера множества, к которому принадлежит элемент v

        public int find_set(int v) { // Метод для нахождения корня множества, которому принадлежит элемент v
            if (v == parent[v]) // если он корень то возвращаем
                return v;
            return parent[v] = find_set(parent[v]); // рекурсивно находим родителя
        }

        public void union_sets(int a, int b) { // Метод, который объединяет два множества, содержащие элементы a и b
            a = find_set(a);
            b = find_set(b);
            if (a != b) {
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
}
