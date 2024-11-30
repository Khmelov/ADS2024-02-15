package by.it.group351003.lutai.lesson14;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class StatesHanoiTowerC {

    private static final List<TowerState> states = new ArrayList<>();
    private static MyDSU dsu;
    private static int currentIndex = 0;

    private static void solveHanoi(final int n, final int[] A, final int[] B, final int[] C) {
        if (n == 0) return;
        solveHanoi(n - 1, A, C, B);
        moveDisk(A, B);
        saveState(A, B, C);
        TowerState temp = states.getLast();
        final int height = temp.getMaxHeight();
        dsu.union(height,currentIndex);
        currentIndex++;
        solveHanoi(n - 1, C, B, A);
    }

    private static void moveDisk(final int[] src, final int[] dst) {
        final int srcTop = getTopDiskIndex(src);
        final int dstTop = getTopDiskIndex(dst) + 1;
        dst[dstTop] = src[srcTop];
        src[srcTop] = 0;
    }

    private static int getTopDiskIndex(final int[] tower) {
        for (int i = 0; i < tower.length; i++) {
            if (tower[i] == 0) {
                return i - 1;
            }
        }
        return tower.length - 1;
    }

    private static void saveState(final int[] A, final int[] B, final int[] C) {
        states.add(new TowerState(A, B, C));
    }

    private static void fillResultSizes(List<Integer> resultSizes) {
        for (int i = 0; i < states.size(); i++) {
            if (dsu.find(i) == i) {
                resultSizes.add(dsu.getSize(i));
            }
        }
    }

    private static void showAnswer(final List<Integer> resultSizes){
        StringBuilder sb = new StringBuilder();
        for (int size : resultSizes) {
            sb.append(size).append(" ");
        }
        sb.trimToSize();
        System.out.println(sb);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        dsu = new MyDSU(n);
        int[] A = new int[n];
        int[] B = new int[n];
        int[] C = new int[n];

        for (int i = 0; i < n; i++) {
            A[i] = n - i;
        }
        solveHanoi(n, A, B, C);

        List<Integer> resultSizes = new ArrayList<>();
        fillResultSizes(resultSizes);
        Collections.sort(resultSizes);
        showAnswer(resultSizes);
        states.clear();
        currentIndex = 0;
    }
}
