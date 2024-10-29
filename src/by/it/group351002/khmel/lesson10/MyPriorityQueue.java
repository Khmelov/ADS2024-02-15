package by.it.group351002.khmel.lesson10;


import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

@SuppressWarnings("all")
//класс реализовывает приоритетной очереди с использованием   кучу min-heap
//Min-Heap- родительский элемент всегда меньше или равен своим дочерним элементам.
//когда куча заполнена, создается новый массив с удвоенной емкостью.

public class MyPriorityQueue<E extends Comparable<E>> implements Queue<E> {

    private static final int defaultSize = 10;
    private int currentSize;
    private E[] heap;

    public MyPriorityQueue() {
        heap = (E[]) new Comparable[defaultSize];
        currentSize = 0;

    }

    void swap(int x, int y) {
        E temp = heap[x];
        heap[x] = heap[y];
        heap[y] = temp;
    }
    //Перемещает элемент вниз по куче, сохраняя свойство кучи.
    void siftDown(int i) {
        int child1, child2, min;

        while (2 * i + 1 < currentSize) {
            child1 = 2 * i + 1;
            child2 = 2 * i + 2;
            min = child1;

            if ((child2 < currentSize) && (heap[child2].compareTo(heap[min])) < 0) {
                min = child2;
            }

            if (heap[i].compareTo(heap[min]) < 0) {
                break;
            }

            swap(i, min);
            i = min;
        }
    }
//Перемещает элемент вверх по куче, сохраняя свойство кучи.
//вызываются после вставок и удалений соответственно. гарантирует, что наименьший элемент всегда будет в корне кучи
    void siftUp(int i) {
        while (heap[i].compareTo(heap[(i - 1) / 2]) < 0) {
            swap(i, (i - 1) / 2);
            i = (i - 1) / 2;
        }
    }
//Перестраивает структуру кучи снизу вверх.
    //для восстановления структуры кучи после массовых операций, таких как removeAll() и remainAll().
    void heapify() {
        for (int i = (currentSize / 2); i >= 0; i--) {
            siftDown(i);
        }
    }

    @Override
    public String toString() {
        String tempStr = "[";

        for (int i = 0; i < currentSize; i++) {
            tempStr += heap[i];
            if (i < currentSize - 1) {
                tempStr += ", ";
            }
        }
        tempStr += "]";
        return tempStr.toString();
    }

    @Override
    public int size() {
        return currentSize;
    }

    @Override
    public void clear() {
        heap = (E[]) new Comparable[defaultSize];
        currentSize = 0;
    }
//Добавляет элемент в кучу и сохраняет свойство кучи.
    @Override
    public boolean add(E e) {
        if (currentSize == heap.length) {
            E[] newHeap = (E[]) new Comparable[currentSize * 2];

            System.arraycopy(heap, 0, newHeap, 0, currentSize);
            heap = newHeap;
        }

        heap[currentSize] = e;
        siftUp(currentSize);
        currentSize++;
        return true;
    }
//Удаляет и возвращает верхний элемент кучи (наименьший элемент).
    @Override
    public E remove() {
        if (currentSize == 0) {
            return null;
        }

        E temp = heap[0];
        currentSize--;
        heap[0] = heap[currentSize];
        siftDown(0);

        return temp;
    }
//Проверяет, содержит ли куча определенный элемент.
    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < currentSize; i++) {
            if (heap[i].equals(o)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean offer(E e) {
        return add(e);
    }


    @Override
    public E poll() {
        return remove();
    }

    @Override
    public E element() {
        if (currentSize == 0) {
            return null;
        }

        return heap[0];
    }

    @Override
    public E peek() {
        return element();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object item : c) {
            if (!contains(item)) {
                return false;
            }
        }

        return true;
    }

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

    @Override
    public boolean removeAll(Collection<?> c) {
        int cursor1 = 0, cursor2 = 0, counter = 0;

        for (cursor2 = 0; cursor2 < currentSize; cursor2++) {
            if (!c.contains(heap[cursor2])) {
                heap[cursor1++] = heap[cursor2];
            } else {
                counter++;
            }

        }

        if (counter != 0) {
            currentSize -= counter;
            heapify();
            return true;
        }

        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        int cursor1 = 0, cursor2 = 0, counter = 0;

        for (cursor2 = 0; cursor2 < currentSize; cursor2++) {
            if (c.contains(heap[cursor2])) {
                heap[cursor1++] = heap[cursor2];
            } else {
                counter++;
            }
        }

        if (counter != 0) {
            currentSize -= counter;
            heapify();
            return true;
        }

        return false;
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Опциональные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

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
