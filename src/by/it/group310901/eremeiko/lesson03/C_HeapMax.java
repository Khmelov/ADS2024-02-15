package by.it.group310901.eremeiko.lesson03;

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

        // Создаем список для хранения элементов кучи
        private List<Long> heap = new ArrayList<>();

        // Метод для "просеивания" элемента вверх по дереву
        int siftUp(int i) { //просеивание вверх
            while (heap.get(i) > heap.get((i - 1) / 2)){   // Пока элемент больше его родителя...
                swap(i, (i - 1) / 2);  // ...меняем их местами...
                i = (i - 1) / 2;   // ...и переходим к родителю.
            }
            return i;
        }

        // Метод для "просеивания" элемента вниз по дереву
        int siftDown(int i) { //просеивание вниз
            while (2*i + 1 < heap.size()) {  // Пока у элемента есть дети...
                // ...находим большего из детей...
                int left = 2 * i + 1;
                int right = 2 * i + 2;
                int max = left;
                if((right < heap.size()) && (heap.get(right) > heap.get(left)))
                    max = right;
                // ...если элемент уже больше своих детей, то останавливаемся...
                if(i == max)
                    break;
                // ...иначе меняем элемент местами с большим из детей и переходим к нему.
                i = max;
            }
            return i;
        }

        // Метод для вставки элемента в кучу
        void insert(Long value) { //вставка
            heap.add(value);  // Добавляем элемент в конец списка...
            siftUp(heap.size()-1);  // ...и "просеиваем" его вверх по дереву
        }

        private void swap(int i, int j){
            Long temp = heap.get(j);
            heap.set(j, heap.get(i));
            heap.set(i, temp);
        }


        // Метод для извлечения максимального элемента из кучи
        Long extractMax() { //извлечение и удаление максимума
            // Запоминаем максимальный элемент (корень дерева)...
            Long result = null;
            result = heap.get(0);
            heap.remove(0);  // ...заменяем его последним элементом в списке...
            siftDown(0);  // ...и "просеиваем" новый корень вниз по дереву
            return result;  // Возвращаем максимальный элемент
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

