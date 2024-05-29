package by.it.group310901.baradzin.lesson03;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * <p>Построить max-кучу = пирамиду = бинарное сбалансированное дерево на массиве</p>
 * <p>ВАЖНО! НЕЛЬЗЯ ИСПОЛЬЗОВАТЬ НИКАКИЕ КОЛЛЕКЦИИ, КРОМЕ ARRAYLIST (его можно, но только для массива)</p>
 * <p>Проверка проводится по данным файла. Первая строка входа содержит число операций 1 ≤ n ≤ 100000. Каждая из
 * последующих nn строк задают операцию одного из следующих двух типов:</p>
 * <ul>
 * <li>Insert x, где 0 ≤ x ≤ 1000000000 — целое число. Добавляет число x в очередь с приоритетами</li>
 * <li>ExtractMax. Извлекает максимальное число и выводит его</li>
 * </ul>
 * <p>
 * Sample Input:<br/>
 * 6<br/>
 * Insert 200<br/>
 * Insert 10<br/>
 * ExtractMax<br/>
 * Insert 5<br/>
 * Insert 500<br/>
 * ExtractMax<br/>
 * Sample Output:<br/>
 * 200<br/>
 * 500<br/>
 * </p>
 * <p>РЕМАРКА</p>
 * <p>Это задание исключительно учебное. Свои собственные кучи нужны довольно редко. "В реальном бою" все существенно
 * иначе. Изучите и используйте коллекции TreeSet, TreeMap, PriorityQueue и т.д. с нужным CompareTo() для объекта
 * внутри.</p>
 */

public class C_HeapMax {

    public static void main(String[] args) throws FileNotFoundException {
        var root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group310901/baradzin/lesson03/heapData.txt");
        var instance = new C_HeapMax();
        System.out.println("MAX=" + instance.findMaxValue(stream));
    }

    /**
     * Читает данные из файла, можно не менять
     */
    Long findMaxValue(InputStream stream) {
        var maxValue = 0L;
        var heap = new MaxHeap();
        var scanner = new Scanner(stream);
        var count = scanner.nextInt();
        for (var i = 0; i < count; ) {
            var s = scanner.nextLine();
            if (s.equalsIgnoreCase("extractMax")) {
                var res = heap.extractMax();
                if (res != null && res > maxValue) maxValue = res;
                System.out.println();
                i++;
            }
            if (s.contains(" ")) {
                var p = s.split(" ");
                if (p[0].equalsIgnoreCase("insert")) heap.insert(Long.parseLong(p[1]));
                i++;
            }
        }
        return maxValue;
    }

    /**
     * Решение
     */
    private static class MaxHeap {
        private final List<Long> heap = new ArrayList<>();

        /**
         * Просеивание вверх (если элемент ниже нужного)
         */
        void siftUp(int child) {
            var parent = (child - 1) / 2;
            if (child > 0 && heap.get(child) > heap.get(parent)) {
                swap(child, parent);
                siftDown(parent);
            }
        }

        /**
         * Просеивание вниз (если элемент выше нужного)
         */
        void siftDown(int parent) {
            var right = 2 * parent + 1;
            var left = 2 * parent + 2;
            var child = (heap.size() > right && heap.get(right) > heap.get(parent))
                    ? right
                    : (heap.size() > left && heap.get(left) > heap.get(parent))
                    ? left
                    : parent;

            if (parent != child) {
                swap(parent, child);
                siftDown(child);
            }
        }

        /**
         * Вставка
         */
        void insert(Long value) {
            heap.add(value);
            siftUp(heap.size() - 1);
        }

        /**
         * Извлечение и удаление максимума
         */
        Long extractMax() {
            return (heap.isEmpty()) ? null : pop(0);
        }

        /**
         * Обмен элементов местами
         */
        void swap(int i, int j) {
            var iValue = heap.get(i);
            heap.set(i, heap.get(j));
            heap.set(j, iValue);
        }

        /**
         * Получение элемента, его удаление и нормализация кучи
         */
        Long pop(int i) {
            var iValue = heap.get(i);
            heap.remove(i);
            siftDown(i);
            return iValue;
        }
    }
}
