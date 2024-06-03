package by.it.group351001.pavlyuchenko.lesson03;

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

        void swap(int a, int b) {
          long buff;
          buff = heap.get(a);
            heap.set(a,heap.get(b));
            heap.set(b, buff);
        }

        int siftUp(int i) { //просеивание вверх
            int spinogryz = ((i-1) >> 1);
            while ((i > 0) && (heap.get(i) > heap.get(spinogryz))) {
                swap(i,spinogryz);
                i = spinogryz;
                spinogryz = ((i-1) >> 1);
              }
            return i;
        }

        int siftDown(int i) { //просеивание вниз
            boolean stop;
            stop = false;
            int spinogryz1, spinogryz2;
            int buff;
            spinogryz1 = (i << 1) + 1;
            spinogryz2 = spinogryz1 + 1;
            while (spinogryz1 < heap.size()) {
              if (heap.get(i) < heap.get(spinogryz1)) {
                 buff = spinogryz1;
              } else {
                  if ((spinogryz2 < heap.size()) && (heap.get(i) < heap.get(spinogryz2))) {
                    buff = spinogryz2;
                  } else{
                      break;
                  }
              }
              swap(i, buff);
              i = buff;
              spinogryz1 = (i << 1) + 1;
              spinogryz2 = spinogryz1 + 1;
            }

            return i;
        }

        void insert(Long value) { //вставка
          heap.addLast(value);
          siftUp(heap.size()-1);
        }

        Long extractMax() { //извлечение и удаление максимума
            Long result = null;
            result = heap.getFirst();
            swap(0, heap.size()-1);
            heap.removeLast();
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
