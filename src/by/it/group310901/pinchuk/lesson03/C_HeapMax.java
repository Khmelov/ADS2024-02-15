package by.it.group310901.pinchuk.lesson03;

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
    /*Данный код представляет собой реализацию
    класса MaxHeap, который представляет структуру данных "Пирамида максимума" (Max Heap).*/

    private class MaxHeap {
        /*Класс MaxHeap реализует структуру данных
        "Пирамида максимума", где каждый элемент в вершине пирамиды является наибольшим.*/
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! НАЧАЛО ЗАДАЧИ !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
        //тут запишите ваше решение.
        //Будет мало? Ну тогда можете его собрать как Generic и/или использовать в варианте B
        private List<Long> heap = new ArrayList<>();
/*  - siftDown(int i): Процедура просеивания вниз для восстановления порядка пирамиды,
 проверяя и корректируя позицию элементов.
   - siftUp(int i): Процедура просеивания вверх для восстановления порядка пирамиды,
    проверяя и корректируя позицию элементов.
   - insert(Long value): Вставка элемента в пирамиду и последующее просеивание вверх
   для поддержания свойств пирамиды.
   - extractMax(): Извлечение и удаление максимального элемента из пирамиды,
   последующее восстановление порядка с помощью просеивания вниз.
   - colswap(int i, int j): Обмен значений между двумя элементами в пирамиде.*/
        int siftDown(int i) { //просеивание вверх
            int leftChild = 2 * i + 1;
            int rightChild = 2 * i + 2;
            int largest = i;

            if (leftChild < heap.size() && heap.get(leftChild) > heap.get(largest)) {
                largest = leftChild;
            }

            if (rightChild < heap.size() && heap.get(rightChild) > heap.get(largest)) {
                largest = rightChild;
            }

            if (largest != i) {
                colswap(i, largest);
                siftDown(largest);
            }
            return i;
        }
/* Пирамида максимума обеспечивает возможность эффективного
 извлечения максимального элемента из коллекции.
   - При вставке нового элемента он позиционируется так,
   чтобы при последующей извлечении максимум был на вершине.
   - Процедуры просеивания вниз и вверх обеспечивают поддержание
    свойств пирамиды после добавления или удаления элементов.*/
        int siftUp(int i) { //просеивание вниз
            int parent = (i - 1) / 2;

            while (i > 0 && heap.get(i) > heap.get(parent)) {
                colswap(i, parent);
                i = parent;
                parent = (i - 1) / 2;
            }
            return i;
        }

        void insert(Long value) {
            heap.add(value);
            siftUp(heap.size() - 1);//вставка
        }

        Long extractMax() { //извлечение и удаление максимума
            if (heap.isEmpty()) {
                return null;
        }
            Long max = heap.get(0);
            heap.set(0, heap.get(heap.size() - 1));
            heap.remove(heap.size() - 1);
            siftDown(0);

            return max;
        }

        private void colswap(int i, int j) {
            Long temp = heap.get(i);
            heap.set(i, heap.get(j));
            heap.set(j, temp);
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
            //System.out.println(heap); //debug
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
