package by.it.group351003.lutai.lesson03;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class C_HeapMax {

    private class MaxHeap {
        private List<Long> heap = new ArrayList<>();

        private void swap(int i, int j) {
            Long temp = heap.get(j);
            heap.set(j, heap.get(i));
            heap.set(i, temp);
        }

        int siftDown(int i) {
            while (2 * i + 1 < heap.size()) {
                int left = 2 * i + 1;
                int right = 2 * i + 2;
                int max = left;
                if ((right < heap.size()) && (heap.get(right) > heap.get(left)))
                    max = right;
                if (i == max)
                    break;
                swap(i, max);
                i = max;
            }
            return i;
        }

        int siftUp(int i) {
            while (heap.get(i) > heap.get((i - 1) / 2)) {
                swap(i, (i - 1) / 2);
                i = (i - 1) / 2;
            }
            return i;
        }

        void insert(Long value) {
            heap.add(value);
            siftUp(heap.size() - 1);
        }

        Long extractMax() {
            Long result = null;
            result = heap.get(0);
            heap.remove(0);
            siftDown(0);
            return result;
        }
    }

    Long findMaxValue(InputStream stream) {
        Long maxValue=0L;
        MaxHeap heap = new MaxHeap();
        Scanner scanner = new Scanner(stream);
        Integer count = scanner.nextInt();
        for (int i = 0; i < count; ) {
            String s = scanner.nextLine();
            if (s.equalsIgnoreCase("extractMax")) {
                Long res=heap.extractMax();
                if (res!=null && res>maxValue) maxValue=res;
                System.out.println();
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
        System.out.println("MAX="+instance.findMaxValue(stream));
    }
}