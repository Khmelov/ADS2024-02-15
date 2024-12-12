package by.it.group310901.tit.lesson10;



import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;


@SuppressWarnings("all")


public class MyPriorityQueue<E extends Comparable<E>> implements Queue<E> {


    private static final int defaultSize = 10;// Начальный размер очереди
    private int currentSize;// Текущий размер очереди
    private E[] heap;// Массив для хранения элементов очереди


    // Конструктор, инициализирующий массив с заданным размером
    public MyPriorityQueue() {
        heap = (E[]) new Comparable[defaultSize];
        currentSize = 0;
    }


    // Метод для обмена местами двух элементов в куче
    void swap(int x, int y) {
        E temp = heap[x];
        heap[x] = heap[y];
        heap[y] = temp;
    }


    // Метод для перемещения узла вниз по дереву, чтобы поддерживать структуру кучи
    void siftDown(int i) {
        int child1, child2, min;


        // Пока есть хотя бы один дочерний элемент
        while (2 * i + 1 < currentSize) {
            child1 = 2 * i + 1; // Левый дочерний узел
            child2 = 2 * i + 2; // Правый дочерний узел
            min = child1; // Предполагаем, что левый дочерний узел минимальный


            // Если правый дочерний узел существует и меньше левого
            if ((child2 < currentSize) && (heap[child2].compareTo(heap[min])) < 0) {
                min = child2; // Обновляем мин для правого дочернего узла
            }
// Если текущий узел меньше минимального дочернего узла, выходим
            if (heap[i].compareTo(heap[min]) < 0) {
                break;
            }


            swap(i, min);// Меняем местами текущий узел и минимальный дочерний
            i = min;// Переходим к минимальному дочернему узлу
        }
    }


    // Метод для перемещения узла вверх по дереву, чтобы поддерживать структуру кучи
    void siftUp(int i) {
        // Пока текущий узел меньше родителя
        while (heap[i].compareTo(heap[(i - 1) / 2]) < 0) {
            swap(i, (i - 1) / 2);// Меняем местами с родителем
            i = (i - 1) / 2;
        }
    }


    // Метод для перестройки кучи
    void heapify() {
        // Начинаем с последнего родительского узла и перемещаем их вниз
        for (int i = (currentSize / 2); i >= 0; i--) {
            siftDown(i);
        }
    }


    // Метод для получения строкового представления очереди
    @Override
    public String toString() {
        String tempStr = "[";


        // Перебор всех элементов в куче
        for (int i = 0; i < currentSize; i++) {
            tempStr += heap[i];
            if (i < currentSize - 1) {
                tempStr += ", ";
            }
        }
        tempStr += "]";
        return tempStr.toString();
    }


    // Метод для получения текущего размера очереди
    @Override
    public int size() {
        return currentSize;
    }


    // Метод для очистки очереди
    @Override
    public void clear() {
        heap = (E[]) new Comparable[defaultSize];
        currentSize = 0;
    }


    // Метод для добавления элемента в очередь
    @Override
    public boolean add(E e) {
        if (currentSize == heap.length) {
            E[] newHeap = (E[]) new Comparable[currentSize * 2];


            System.arraycopy(heap, 0, newHeap, 0, currentSize);
            heap = newHeap;
        }


        heap[currentSize] = e;
        siftUp(currentSize); // Упорядочиваем кучу
        currentSize++;
        return true;
    }


    // Метод для удаления и возврата элемента с высоким приоритето
    @Override
    public E remove() {
        if (currentSize == 0) {
            return null;
        }


        E temp = heap[0];
        currentSize--;
        heap[0] = heap[currentSize]; // Перемещаем последний элемент в корень
        siftDown(0);


        return temp;
    }


    // Метод для проверки наличия элемента в очереди
    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < currentSize; i++) {
            if (heap[i].equals(o)) {
                return true;
            }
        }
        return false;
    }


    // Метод для добавления элемента
    @Override
    public boolean offer(E e) {
        return add(e);
    }


    // Метод для удаления и возврата элемента с высоким приоритетом
    @Override
    public E poll() {
        return remove();
    }


    // Метод для получения элемента с высоким приоритетом без удаления
    @Override
    public E element() {
        if (currentSize == 0) {
            return null;
        }


        return heap[0];
    }


    // Метод для получения элемента с высоким приоритетом без удаления
    @Override
    public E peek() {
        return element();
    }


    // Метод для проверки наличия всех элементов из коллекции
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object item : c) {
            if (!contains(item)) {
                return false;
            }
        }


        return true;
    }


    // Метод для добавления всех элементов из коллекции
    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean flag = false;
        for (E item : c) {
            if (add(item)) {
                flag = true;
            }
        }
        return flag;
    }


    // Метод для удаления всех элементов из коллекции
    @Override
    public boolean removeAll(Collection<?> c) {
        int cursor1 = 0, cursor2 = 0, counter = 0;


        // Перебираем все элементы в куче
        for (cursor2 = 0; cursor2 < currentSize; cursor2++) {
            if (!c.contains(heap[cursor2])) {// Если элемент не в коллекции
                heap[cursor1++] = heap[cursor2];// Сохраняем его в новом массиве
            } else {
                counter++;
            }


        }


        // Если были удалены элементы, обновляем размер и восстанавливаем кучу
        if (counter != 0) {
            currentSize -= counter; // Уменьшаем текущий размер
            heapify(); // Восстанавливаем кучу
            return true;
        }


        return false;
    }




    // Метод для сохранения только тех элементов, которые содержатся в коллекции
    @Override
    public boolean retainAll(Collection<?> c) {
        int cursor1 = 0, cursor2 = 0, counter = 0;


        // Перебираем все элементы в куче
        for (cursor2 = 0; cursor2 < currentSize; cursor2++) {
            if (c.contains(heap[cursor2])) {// Если элемент в коллекции
                heap[cursor1++] = heap[cursor2]; // Сохраняем его
            } else {
                counter++;
            }
        }


        // Если были удалены элементы, обновляем размер и восстанавливаем кучу
        if (counter != 0) {
            currentSize -= counter;
            heapify();
            return true;
        }


        return false;
    }


    @Override
    public boolean remove(Object o) {
        return false;
    }


    @Override
    public boolean isEmpty() {
        return false;
    }




    @Override
    public Iterator<E> iterator() {
        return null;
    }


    @Override
    public Object[] toArray() {
        return new Object[0];
    }


    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }


}
