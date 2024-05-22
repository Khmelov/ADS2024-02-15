package by.it.group351003.pulish.lesson03;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class C_HeapMax {

    private class MaxHeap {
        private List<Long> heap = new ArrayList<>();

        int siftDown(int i) {
            int maxIndex = i;
            int leftChild = 2 * i + 1;
            int rightChild = 2 * i + 2;

            // Проверяем, есть ли левый дочерний элемент и больше ли он текущего элемента
            if (leftChild < heap.size() && heap.get(leftChild) > heap.get(maxIndex)) {
                maxIndex = leftChild;
            }

            // Проверяем, есть ли правый дочерний элемент и больше ли он текущего элемента или левого дочернего элемента
            if (rightChild < heap.size() && heap.get(rightChild) > heap.get(maxIndex)) {
                maxIndex = rightChild;
            }

            // Если максимальный элемент не является текущим, то производим обмен
            if (maxIndex != i) {
                swap(i, maxIndex);
                siftDown(maxIndex);
            }

            return i;
        }

        int siftUp(int i) {
            while (i > 0 && heap.get(parent(i)) < heap.get(i)) {
                swap(i, parent(i));
                i = parent(i);
            }
            return i;
        }

        void insert(Long value) {
            heap.add(value);
            siftUp(heap.size() - 1);
        }

        Long extractMax() {
            if (heap.isEmpty()) {
                return null;
            }

            Long max = heap.get(0);
            int lastIndex = heap.size() - 1;
            heap.set(0, heap.get(lastIndex));
            heap.remove(lastIndex);
            siftDown(0);

            return max;
        }

        private int parent(int i) {
            return (i - 1) / 2;
        }

        private void swap(int i, int j) {
            Long temp = heap.get(i);
            heap.set(i, heap.get(j));
            heap.set(j, temp);
        }
    }

    Long findMaxValue(InputStream stream) {
        Long maxValue = 0L;
        MaxHeap heap = new MaxHeap();
        Scanner scanner = new Scanner(stream);
        Integer count = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < count; i++) {
            String s = scanner.nextLine();
            if (s.equalsIgnoreCase("extractMax")) {
                Long res = heap.extractMax();
                if (res != null && res > maxValue) {
                    maxValue = res;
                }
            } else if (s.contains(" ")) {
                String[] p = s.split(" ");
                if (p[0].equalsIgnoreCase("insert")) {
                    heap.insert(Long.parseLong(p[1]));
                }
            }
        }
        return maxValue;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson03/heapData.txt");
        C_HeapMax instance = new C_HeapMax();
        System.out.println("MAX=" + instance.findMaxValue(stream));
    }
}