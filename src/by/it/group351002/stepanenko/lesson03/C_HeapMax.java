package by.it.group351002.stepanenko.lesson03;

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
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int largest = i;
            int size = heap.size();

            if (left < size && heap.get(left) > heap.get(largest)) {
                largest = left;
            }

            if (right < size && heap.get(right) > heap.get(largest)) {
                largest = right;
            }

            if (largest != i) {
                swap(i, largest);
                siftDown(largest);
            }

            return i;
        }

        int siftUp(int i) {
            int parent = (i - 1) / 2;

            while (i > 0 && heap.get(i) > heap.get(parent)) {
                swap(i, parent);
                i = parent;
                parent = (i - 1) / 2;
            }

            return i;
        }

        void insert(Long value) {
            heap.add(value);
            int index = heap.size() - 1;
            siftUp(index);
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

        for (int i = 0; i < count; ) {
            String s = scanner.nextLine();

            if (s.equalsIgnoreCase("extractMax")) {
                Long res = heap.extractMax();
                if (res != null && res > maxValue) maxValue = res;
                i++;
            }

            if (s.contains(" ")) {
                String[] p = s.split(" ");
                if (p[0].equalsIgnoreCase("insert"))
                    heap.insert(Long.parseLong(p[1]));
                i++;
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