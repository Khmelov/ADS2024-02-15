package by.it.group310901.kanunnikava.lesson14;
/*Создайте класс StatesHanoiTowerC с методом main.

Самостоятельно или с помощью интернета, или с помощью ботов типа ChatGPT
найдите и реализуйте решение задачи "Ханойские башни":

Даны три стержня A B C, на один из которых нанизаны N колец,
причём кольца отличаются размером и лежат меньшее на большем.
Задача состоит в том, чтобы перенести пирамиду из N колец за
наименьшее число ходов на другой стержень.
За один раз разрешается переносить только одно кольцо,
причём нельзя класть большее кольцо на меньшее.

В методе main вводится высота N стартовой пирамиды А.
Переместить кольца нужно на пустую пирамиду B.
В задаче есть еще одна пустая пирамида С.

Сгруппируйте с помощью DSU в поддеревья все те шаги решения задачи
у которых одинаковая наибольшая высота пирамид A B C.
Стартовое состояние учитывать не нужно.

Выведите на консоль размеры получившихся поддеревьев в порядке возрастания.
Коллекциями пользоваться нельзя!
Эвристики DSU:
1. по размеру поддерева
2. по сокращению пути поддерева*/
import java.util.Scanner;

public class StatesHanoiTowerC {

    private static class DSU { // Внутренний статический класс для реализации системы непересекающихся множеств
        int[] parent; // Массив для хранения родительских элементов
        int[] size; // Массив для хранения размеров множеств

        DSU(int size){ // Конструктор класса, принимающий размер множества
            parent = new int[size];
            this.size = new int[size];
        }
        void make_set(int v) { // Метод для создания множества, содержащего один элемент
            parent[v] = v; // Устанавливает элемент как родителя самого себя
            size[v] = 1; // Устанавливает размер множества равным 1
        }
        int size(int v){ // Метод для получения размера множества, содержащего элемент.
            return size[find_set(v)];
        }

        int find_set(int v) { // Метод для поиска корня множества, содержащего элемент
            if (v == parent[v]) // Если элемент является своим собственным родителем
                return v;
            return parent[v] = find_set(parent[v]); // Выполняет сжатие пути и возвращает корень множества
        }

        void union_sets(int a, int b) { // Метод для объединения двух множеств
            a = find_set(a); // Находит корень множества для первого элемента
            b = find_set(b); // Находит корень множества для второго элемента
            if (a != b) {
                if (size[a] < size[b]) {
                    int temp = a;
                    a = b;
                    b = temp;
                }
                parent[b] = a; // Устанавливает корень второго множества родителем корня первого множества
                size[a] += size[b]; // Увеличивает размер первого множества на размер второго множества
            }
        }
    }

    static int max(int[] heights){ // Метод для нахождения максимального значения в массиве
        int result = (heights[0] > heights[1]) ? heights[0] : heights[1];
        return (result > heights[2]) ? result : heights[2];
    }

    static int[] carryingOver(int N, int step, int k){ // Метод для вычисления изменений высот дисков на основе текущего шага и диска
        int t, axisX, axisY, axisZ;
        if (N % 2 == 0){ // Определяет порядок осей для четного числа дисков
            axisX = 0;
            axisY = 1;
            axisZ = 2;
        } else { // Определяет порядок осей для нечетного числа дисков
            axisX = 0;
            axisY = 2;
            axisZ = 1;
        }
        int [] result = new int[3];
        t = (step / (1 << (k-1)) - 1) / 2;
        int from = 0, to = 0;
        if (k % 2 != 0) // Определяет направление переноса для нечетного номера диска
            switch (t % 3) {
                case 0:
                    from = axisX;
                    to = axisY;
                    break;
                case 1:
                    from = axisY;
                    to = axisZ;
                    break;
                case 2:
                    from = axisZ;
                    to = axisX;
                    break;
            }
        else // Определяет направление переноса для четного номера диска
            switch (t % 3) {
                case 0:
                    from = axisX;
                    to = axisZ;
                    break;
                case 1:
                    from = axisZ;
                    to = axisY;
                    break;
                case 2:
                    from = axisY;
                    to = axisX;
                    break;
            }
        result[from] = -1;
        result[to] = 1;
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt(); // Считывает количество дисков
        int max_size = (1 << N) - 1; // Вычисляет максимальный размер как 2^N - 1
        int[] steps_heights = new int[N]; // Создает массив для хранения высот дисков на каждом шаге
        for(int i = 0; i < N; i++)
            steps_heights[i] = -1;
        DSU dsu = new DSU(max_size); // Создает объект DSU с максимальным размером
        int[] heights = new int[3]; // Создает массив для хранения текущих высот на трех осях
        heights[0] = N; // Инициализирует высоту первой оси количеством дисков
        for(int i = 0; i < max_size; i++){
            int step = i + 1;
            int[] delta;
            if (step % 2 != 0) { // Если шаг нечетный.
                delta = carryingOver(N, step, 1);
            } else { // Если шаг четный.
                int count = step;
                int countDisk = 0;
                while (count % 2 == 0){
                    countDisk++;
                    count /= 2;
                }
                delta = carryingOver(N, step, countDisk + 1);
            }
            for(int j = 0; j < 3; j++)
                heights[j] += delta[j]; // Обновляет высоты на осях
            int max = max(heights); // Находит максимальную высоту
            dsu.make_set(i); // Создает множество для текущего шага
            if (steps_heights[max - 1] == -1)
                steps_heights[max - 1] = i; // Обновляет шаг для максимальной высоты
            else
                dsu.union_sets(steps_heights[max - 1], i); // Объединяет множества для текущего шага и предыдущего с той же высотой
        }

        int[] sizes = new int[N]; // Создает массив для хранения размеров множеств
        for(int i = 0; i < N; i++)
            if (steps_heights[i] != -1)
                sizes[i] = dsu.size(steps_heights[i]); // Определяет размер множества для каждого шага

        StringBuilder sb = new StringBuilder(); // Создает объект для формирования строки результата
        for(int i = 0; i < N; i++){
            int max = i;
            for(int j = i + 1; j < N; j++)
                if(sizes[max] < sizes[j])
                    max = j;
            if (sizes[max] == 0)
                break;
            int temp = sizes[max];
            sizes[max] = sizes[i];
            sizes[i] = temp;
            sb.insert(0, sizes[i] + " ");
        }
        sb.deleteCharAt(sb.length() - 1); // Удаляет последний пробел
        System.out.println(sb);
    }
}
