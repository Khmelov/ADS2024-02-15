package by.it.group351001.gavrilovich.lesson03;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Math.pow;

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
        private List<List<Long>> layers = new ArrayList<>();
        private List<Long> heap = new ArrayList<>();

        MaxHeap() {
            layers.add(new ArrayList<>());
        }

        int siftDown(int i) { //просеивание вверх

            return i;
        }

        void siftUp() { //просеивание вниз
            int curLayer = layers.size() - 1;
            int curLayerPos = layers.getLast().size() - 1;
            int topLayerPos = curLayerPos / 2;
            int topLayer = curLayer - 1;
            long temp;
            while (topLayer >= 0) {
                if (curLayerPos % 2 == 0) {
                    if (layers.get(topLayer).get(topLayerPos) > layers.get(curLayer).get(curLayerPos)) {
                        break;
                    }
                    else {
                        temp = layers.get(topLayer).get(topLayerPos);
                        layers.get(topLayer).set(topLayerPos, layers.get(curLayer).get(curLayerPos));
                        layers.get(curLayer).set(curLayerPos, temp);
                        curLayer--;
                        topLayer--;
                        curLayerPos = topLayerPos;
                        topLayerPos = curLayerPos / 2;
                    }
                }
                else {
                    if (layers.get(topLayer).get(topLayerPos) < layers.get(curLayer).get(curLayerPos)) {
                        break;
                    }
                    else {
                        temp = layers.get(topLayer).get(topLayerPos);
                        layers.get(topLayer).set(topLayerPos, layers.get(curLayer).get(curLayerPos));
                        layers.get(curLayer).set(curLayerPos, temp);
                        curLayer--;
                        topLayer--;
                        curLayerPos = topLayerPos;
                        topLayerPos = curLayerPos / 2;
                    }
                }
            }
        }

        void insert(Long value) { //вставка
            int curMax = (int) pow(2,(layers.size() - 1));
            if (layers.getLast().size() == curMax) {
                layers.add(new ArrayList<>());
            }
            layers.getLast().add(value);
            siftUp();;
        }

        Long extractMax() { //извлечение и удаление максимума
            Long result = layers.getLast().get(0);
            int pos = 0;//Не помешшала бы проверка на пустое дерево
            for (int i = 1; i < layers.getLast().size(); i++) {//
                if (layers.getLast().get(i) > result) {//Но было принято решение забить
                    result = layers.getLast().get(i);//Всё равно это быдлокод
                    pos = i;
                }
            }
            layers.getLast().remove(pos);
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
