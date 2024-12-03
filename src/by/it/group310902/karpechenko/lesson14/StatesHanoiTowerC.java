package by.it.group310902.karpechenko.lesson14;

import java.util.Arrays;
import java.util.Scanner;

public class StatesHanoiTowerC {

    public static void main(String[] args) {
        int ringCount; // количество колец

        try (Scanner scanner = new Scanner(System.in)) {
            ringCount = scanner.nextInt();
        }

        int maxMoves = (1 << ringCount) - 1; // максимальное количество перемещений
        int[] heightSteps = new int[ringCount]; // массив для отслеживания шагов на каждой высоте

        for (int i = 0; i < ringCount; i++) {
            heightSteps[i] = -1; // инициализируем шаги как -1 (не обработано)
        }

        DSU disjointSetUnion = new DSU(maxMoves);
        int[] pegHeights = new int[3]; // массив высот для трех стержней

        pegHeights[0] = ringCount; // начальная высота на первом стержне
        for (int moveIndex = 0; moveIndex < maxMoves; moveIndex++) {
            int currentMove = moveIndex + 1; // текущий шаг
            int[] heightChange; // массив изменений высоты

            if (currentMove % 2 != 0) { // для нечетного шага
                heightChange = calculateHeightChange(ringCount, currentMove, 1); // вычисляем изменение высоты
            } else {
                int stepCopy = currentMove;
                int movedRings = 0; // количество перемещенных колец

                while (stepCopy % 2 == 0) { // подсчитываем количество делений на 2
                    movedRings++;
                    stepCopy /= 2;
                }

                heightChange = calculateHeightChange(ringCount, currentMove, movedRings + 1); // изменение высоты
            }

            for (int i = 0; i < 3; i++) {
                pegHeights[i] += heightChange[i]; // обновляем высоты стержней
            }

            int maxHeight = getMaxHeight(pegHeights); // находим максимальную высоту
            disjointSetUnion.createSet(moveIndex); // создаем множество для текущего шага

            if (heightSteps[maxHeight - 1] == -1) { // если шаг для этой высоты не обработан
                heightSteps[maxHeight - 1] = moveIndex; // сохраняем шаг
            } else {
                disjointSetUnion.unionSets(heightSteps[maxHeight - 1], moveIndex); // объединяем шаги
            }
        }

        int[] groupSizes = new int[ringCount]; // массив для хранения размеров групп
        for (int i = 0; i < ringCount; i++) {
            if (heightSteps[i] != -1) {
                groupSizes[i] = disjointSetUnion.getSetSize(heightSteps[i]);
            }
        }

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < ringCount; i++) {
            int maxIndex = i;
            for (int j = i + 1; j < ringCount; j++) {
                if (groupSizes[maxIndex] < groupSizes[j]) {
                    maxIndex = j;
                }
            }

            if (groupSizes[maxIndex] == 0) {
                break;
            }

            // Перемещаем максимальное значение в начало
            int temp = groupSizes[maxIndex];
            groupSizes[maxIndex] = groupSizes[i];
            groupSizes[i] = temp;
            result.insert(0, groupSizes[i] + " ");
        }

        result.deleteCharAt(result.length() - 1); // удаляем последний пробел
        System.out.println(result);
    }

    private static int getMaxHeight(int[] pegHeights) {
        return Math.max(Math.max(pegHeights[0], pegHeights[1]), pegHeights[2]);
    }

    static int[] calculateHeightChange(int totalRings, int currentMove, int diskLevel) {
        int temp, pegFrom = 0, pegTo = 0;

        int pegY, pegZ;
        if (totalRings % 2 == 0) { // если количество колец четное
            pegY = 1; // первый дополнительный стержень
            pegZ = 2; // второй дополнительный стержень
        } else { // если количество колец нечетное
            pegY = 2; // второй дополнительный стержень
            pegZ = 1; // первый дополнительный стержень
        }

        int[] result = new int[3]; // массив для изменений высоты стержней
        temp = (currentMove / (1 << (diskLevel - 1)) - 1) / 2;

        // определяем направление движения
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

        result[pegFrom] = -1; // уменьшение высоты на исходном стержне
        result[pegTo] = 1; // увеличение высоты на конечном стержне

        return result;
    }

    static class DSU {
        private final int[] parent;
        private final int[] size;

        public DSU(int n) {
            parent = new int[n];
            size = new int[n];
        }

        public void createSet(int v) {
            parent[v] = v;
            size[v] = 1;
        }

        public int findSet(int v) {
            if (v == parent[v]) return v;
            return parent[v] = findSet(parent[v]); // Сжатие пути
        }

        public int getSetSize(int v) {
            return size[findSet(v)];
        }

        public void unionSets(int a, int b) {
            a = findSet(a);
            b = findSet(b);
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
