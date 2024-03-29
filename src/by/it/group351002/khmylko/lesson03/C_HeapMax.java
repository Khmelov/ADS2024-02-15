package by.it.group351002.khmylko.lesson03;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

    private class MaxHeap {
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! НАЧАЛО ЗАДАЧИ !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
        //тут запишите ваше решение.
        //Будет мало? Ну тогда можете его собрать как Generic и/или использовать в варианте B
        private List<Long> heap = new ArrayList<>();

        int siftDown(int i) { //просеивание вверх
            long left = heap.get(2*i + 1);
            long right = left;
            if (heap.size() > 2) {
                right = heap.get(2 * i + 2);
            }
            while (i < heap.size() && (heap.get(i) < left || heap.get(i) < right)) {
                int indexMax = left >= right ? 2*i + 1 : 2*i + 2;
                long buff = heap.get(indexMax);
                heap.add(indexMax, heap.get(i));
                heap.remove(indexMax+1);
                heap.add(i, buff);
                heap.remove(i+1);
                i = indexMax;
                if (heap.size() >= 2*i+1) {
                    left = heap.get(2*i + 1);
                    right = left;
                    if (heap.size() >= 2*i+2) {
                        right = heap.get(2 * i + 2);
                    }
                } else {
                    left = -1;
                    right = -1;
                }
            }

            return i;
        }

        int siftUp(int i) { //просеивание вниз
            while (i > 0 && heap.get((i)/2) < heap.get(i)) {
                long buff = heap.remove(i);
                heap.add(i, heap.get((i)/2));
                heap.add((i)/2, buff);
                heap.remove((i)/2+1);
                i = (i)/2;
            }

            return i;
        }

        void insert(Long value) {
            //вставка
            heap.addLast(value);
            if (heap.size() > 1)
                siftUp(heap.size()-1);
        }

        Long extractMax() { //извлечение и удаление максимума
            Long result = heap.remove(0);
            heap.addFirst(heap.removeLast());
            if (heap.size() > 1)
                siftDown(0);

            return result;
        }
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! КОНЕЦ ЗАДАЧИ !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
    }

    //эта процедура читает данные из файла, ее можно не менять.
    Long findMaxValue(InputStream stream) {
        Long maxValue=0L;
        MaxHeap heap = new MaxHeap();
        //прочитаем строку для кодирования из тестового файла
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
                System.out.println(heap); //debug
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

    // РЕМАРКА. Это задание исключительно учебное.
    // Свои собственные кучи нужны довольно редко.
    // "В реальном бою" все существенно иначе. Изучите и используйте коллекции
    // TreeSet, TreeMap, PriorityQueue и т.д. с нужным CompareTo() для объекта внутри.
}
