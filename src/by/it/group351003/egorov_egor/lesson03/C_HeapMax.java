package by.it.group351003.egorov_egor.lesson03;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Lesson 3. C_Heap.
// Задача: построить max-кучу = пирамиду = бинарное сбалансированное дерево на массиве.
// ВАЖНО! НЕЛЬЗЯ ИСПОЛЬЗОВАТЬ НИКАКИЕ КОЛЛЕКЦИИ, КРОМЕ ARRAYLIST (его можно, но только для массива)

//      Проверка проводится по данным файла
//      Первая строка входа содержит число операций 1 ≤ n ≤ 100000.
//      Каждая из последующих nn строк задают операцию одного из следующих двух типов:

//      Insert x, где 0 ≤ x ≤ 1000000000 — целое число;
//      ExtractMax.

//      Первая операция добавляет число x в очередь с приоритетами,
//      вторая — извлекает максимальное число и выводит его.

//      Sample Input:
//      6
//      Insert 200
//      Insert 10
//      ExtractMax
//      Insert 5
//      Insert 500
//      ExtractMax
//
//      Sample Output:
//      200
//      500


public class C_HeapMax {

    private static class MaxHeap {
        private final List<Long> heap = new ArrayList<>();

        void siftDown(int i) {
            while (2 * i + 1 < heap.size()) {
                int left = 2 * i + 1;
                int right = 2 * i + 2;
                int j = left;
                if (right < heap.size() && heap.get(right) > heap.get(left)) {
                    j = right;
                }
                if (heap.get(i) >= heap.get(j)) {
                    break;
                }
                swap(i, j);
                i = j;
            }
        }

        void siftUp(int i) {
            while (heap.get(i) > heap.get((i - 1) / 2)) {
                swap(i, (i - 1) / 2);
                i = (i - 1) / 2;
            }
        }

        void insert(Long value) {
            heap.add(value);
            siftUp(heap.size() - 1);
        }

        Long extractMax() {
            Long result = heap.get(0);
            heap.set(0, heap.get(heap.size() - 1));
            heap.remove(heap.size() - 1);
            siftDown(0);
            return result;
        }

        void swap(int i, int j) {
            Long tmp = heap.get(i);
            heap.set(i, heap.get(j));
            heap.set(j, tmp);
        }
    }

    Long findMaxValue(InputStream stream) {
        long maxValue = 0L;
        MaxHeap heap = new MaxHeap();
        Scanner scanner = new Scanner(stream);
        int count = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < count; i++) {
            String s = scanner.nextLine();
            if (s.startsWith("Insert")) {
                Long value = Long.parseLong(s.split(" ")[1]);
                heap.insert(value);
            } else if (s.equalsIgnoreCase("ExtractMax")) {
                Long res = heap.extractMax();
                if (res != null && res > maxValue) maxValue = res;
            }
        }
        return maxValue;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        File f = new File(root + "by/it/group351003/egorov_egor/lesson03/heapData.txt");
        C_HeapMax instance = new C_HeapMax();
        Long result = instance.findMaxValue(new FileInputStream(f));
        System.out.println(result);
    }
}
