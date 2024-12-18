package by.it.group310902.pashkovich.lesson14;

import java.util.Arrays;
import java.util.Scanner;

public class StatesHanoiTowerC {



    public static void main(String[] args) {
        int ringCount; // количество колец

        // Чтение количества колец с консоли
        try (Scanner scanner = new Scanner(System.in)) {
            ringCount = scanner.nextInt(); // ввод количества колец
        }

        // Вычисление максимального числа ходов по формуле для задачи Ханойской башни
        int maxMoves = (1 << ringCount) - 1; // максимальное количество перемещений
        int[] heightSteps = new int[ringCount]; // массив для отслеживания шагов на каждой высоте

        // Инициализация массива высот, указываем, что шаги ещё не обработаны
        for (int i = 0; i < ringCount; i++) {
            heightSteps[i] = -1; // инициализируем шаги как -1 (не обработано)
        }

        // Создаем структуру данных для объединений (DSU)
        DSU disjointSetUnion = new DSU(maxMoves);
        int[] pegHeights = new int[3]; // массив высот для трех стержней

        // Изначальная высота на первом стержне равна количеству колец
        pegHeights[0] = ringCount;
        // Процесс выполнения всех ходов
        for (int moveIndex = 0; moveIndex < maxMoves; moveIndex++) {
            int currentMove = moveIndex + 1; // текущий шаг
            int[] heightChange; // массив изменений высоты

            // Для нечетных шагов вычисляем изменение высоты по простому алгоритму
            if (currentMove % 2 != 0) {
                heightChange = calculateHeightChange(ringCount, currentMove, 1); // вычисляем изменение высоты
            } else {
                int stepCopy = currentMove;
                int movedRings = 0; // количество перемещенных колец

                // Подсчитываем, сколько раз текущий шаг делится на 2
                while (stepCopy % 2 == 0) {
                    movedRings++;
                    stepCopy /= 2;
                }

                // Если шаг четный, определяем сколько колец двигалось в этом шаге
                heightChange = calculateHeightChange(ringCount, currentMove, movedRings + 1);
            }

            // Обновляем высоты стержней после выполнения хода
            for (int i = 0; i < 3; i++) {
                pegHeights[i] += heightChange[i]; // обновляем высоты стержней
            }

            int maxHeight = getMaxHeight(pegHeights); // находим максимальную высоту среди стержней
            disjointSetUnion.createSet(moveIndex); // создаем множество для текущего шага

            // Если шаг для этой высоты еще не был обработан, сохраняем его
            if (heightSteps[maxHeight - 1] == -1) {
                heightSteps[maxHeight - 1] = moveIndex; // сохраняем шаг
            } else {
                // Если шаг для этой высоты уже обработан, объединяем множества
                disjointSetUnion.unionSets(heightSteps[maxHeight - 1], moveIndex);
            }
        }

        // Массив для хранения размеров групп
        int[] groupSizes = new int[ringCount];
        // Вычисление размеров групп для каждого шага
        for (int i = 0; i < ringCount; i++) {
            if (heightSteps[i] != -1) {
                groupSizes[i] = disjointSetUnion.getSetSize(heightSteps[i]);
            }
        }

        // Строим строку результата, сортируя по размеру групп
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < ringCount; i++) {
            int maxIndex = i;
            // Ищем индекс максимального значения в массиве groupSizes
            for (int j = i + 1; j < ringCount; j++) {
                if (groupSizes[maxIndex] < groupSizes[j]) {
                    maxIndex = j;
                }
            }

            // Если группа пустая, выходим из цикла
            if (groupSizes[maxIndex] == 0) {
                break;
            }

            // Перемещаем максимальное значение в начало
            int temp = groupSizes[maxIndex];
            groupSizes[maxIndex] = groupSizes[i];
            groupSizes[i] = temp;
            // Добавляем размер группы в результат
            result.insert(0, groupSizes[i] + " ");
        }

        // Удаляем последний пробел
        result.deleteCharAt(result.length() - 1);
        // Выводим результат
        System.out.println(result);
    }

    // Функция для нахождения максимальной высоты на стержнях
    private static int getMaxHeight(int[] pegHeights) {
        return Math.max(Math.max(pegHeights[0], pegHeights[1]), pegHeights[2]);
    }

    // Функция для вычисления изменения высоты на каждом шаге
    static int[] calculateHeightChange(int totalRings, int currentMove, int diskLevel) {
        int temp, pegFrom = 0, pegTo = 0;

        // Определение, какие стержни будут использоваться в зависимости от четности колец
        int pegY, pegZ;
        if (totalRings % 2 == 0) {
            pegY = 1; // первый дополнительный стержень
            pegZ = 2; // второй дополнительный стержень
        } else {
            pegY = 2; // второй дополнительный стержень
            pegZ = 1; // первый дополнительный стержень
        }

        int[] result = new int[3]; // массив для изменений высоты стержней
        temp = (currentMove / (1 << (diskLevel - 1)) - 1) / 2;

        // Определяем направление движения для каждого кольца в зависимости от четности шага
        if (diskLevel % 2 != 0) {
            switch (temp % 3) {
                case 0 -> pegTo = pegY;
                case 1 -> {
                    pegFrom = pegY;
                    pegTo = pegZ;
                }
                case 2 -> pegFrom = pegZ;
            }
        } else {
            switch (temp % 3) {
                case 0 -> pegTo = pegZ;
                case 1 -> {
                    pegFrom = pegZ;
                    pegTo = pegY;
                }
                case 2 -> pegFrom = pegY;
            }
        }

        // Обновляем изменения высот для стержней
        result[pegFrom] = -1; // уменьшение высоты на исходном стержне
        result[pegTo] = 1; // увеличение высоты на конечном стержне

        return result;
    }

    // Класс для реализации структуры объединений с помощью "сжатия пути"
    static class DSU {
        private final int[] parent; // родительские элементы для каждой вершины
        private final int[] size;   // размер каждой группы

        // Конструктор для инициализации структуры данных
        public DSU(int n) {
            parent = new int[n];
            size = new int[n];
        }

        // Создание нового множества для элемента
        public void createSet(int v) {
            parent[v] = v;
            size[v] = 1;
        }

        // Поиск представителя множества для элемента с применением сжатия пути
        public int findSet(int v) {
            if (v == parent[v]) return v;
            return parent[v] = findSet(parent[v]); // Сжатие пути
        }

        // Получение размера множества для элемента
        public int getSetSize(int v) {
            return size[findSet(v)];
        }

        // Объединение двух множеств
        public void unionSets(int a, int b) {
            a = findSet(a);
            b = findSet(b);
            if (a != b) {
                // Объединяем более мелкое множество с более крупным
                if (size[a] < size[b]) {
                    int temp = a;
                    a = b;
                    b = temp;
                }
                parent[b] = a;
                size[a] += size[b]; // увеличиваем размер объединенного множества
            }
        }
    }
}
