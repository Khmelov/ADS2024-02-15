package by.it.group351002.bob.lesson03;

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


        /*Описание max-кучи: Класс MaxHeap содержит методы для работы с max-кучей:

siftDown(int i): Процедура просеивания элемента вниз по дереву.
Если значение узла больше значения его родителя, то узлы меняются местами.

siftUp(int i): Процедура просеивания элемента вверх по дереву.
Если значение узла больше значения его родителя,
то узлы меняются местами и процедура применяется рекурсивно.
insert(Long value): Вставка нового элемента в max-кучу.
Элемент добавляется в конец массива и затем происходит просеивание вверх.
extractMax(): Извлечение и удаление максимального элемента из max-кучи.
Максимальный элемент находится в корне дерева.
Затем последний элемент массива заменяет корневой элемент, и происходит просеивание вниз.
Чтение входных данных: Процедура findMaxValue(InputStream stream) читает данные из файла,
содержащего операции добавления и извлечения элементов из max-кучи.

Выполнение операций: Программа читает строки из файла. Если строка содержит слово "Insert",
то извлекается значение, которое добавляется в max-кучу с помощью метода insert().
Если строка содержит слово "ExtractMax",
то из максимального элемента извлекается и выводится на экран.

Вывод результата: Максимальное значение, извлеченное из max-кучи,
возвращается как результат работы программы.*/

        private List<Long> heap = new ArrayList<>();
        void siftDown(int i) { //просеивание вверх
            while (heap.get(i) > heap.get((i - 1) / 2)) {
                swap(i, (i - 1) / 2);
            }
        }

        void swap(int index1, int index2) {
            Long buf = heap.get(index1);
            heap.set(index1, heap.get(index2));
            heap.set(index2, buf);
        }
        void siftUp(int i) { //просеивание вниз
            while(heap.get(i) > heap.get((i - 1) / 2)) {
                swap(i, (i - 1) / 2);
                siftDown((i - 1) / 2);
            }
        }
        void insert(Long value) { //вставка
            heap.add(value);
            siftUp(heap.size() - 1);
        }

        Long extractMax() { //извлечение и удаление максимума
            Long result = heap.get(0);
            heap.remove(0);
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
