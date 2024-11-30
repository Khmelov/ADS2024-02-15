package by.it.group351003.lutai.lesson14;

import java.util.Arrays;

class TowerState {
    int[] A, B, C;

    TowerState(int[] A, int[] B, int[] C) {
        this.A = Arrays.copyOf(A, A.length);
        this.B = Arrays.copyOf(B, B.length);
        this.C = Arrays.copyOf(C, C.length);
    }

    // Определение максимальной высоты пирамиды
    int getMaxHeight() {
        return Math.max(countDisks(A), Math.max(countDisks(B), countDisks(C)));
    }

    // Считает количество дисков на стержне
    private int countDisks(int[] tower) {
        int count = 0;
        for (int disk : tower) {
            if (disk != 0) count++;
        }
        return count;
    }
}

