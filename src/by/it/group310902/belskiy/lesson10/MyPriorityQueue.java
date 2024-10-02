package by.it.group310902.belskiy.lesson10;

import java.util.*;

public class MyPriorityQueue<E extends Comparable<E>> implements Queue<E> {
    private static final int DEFAULT_INITIAL_CAPACITY = 10;
    private int size;
    private E[] heap;

    public MyPriorityQueue() {
        heap = (E[]) new Comparable[DEFAULT_INITIAL_CAPACITY];
        size = 0;
    }

    void swap(int x, int y) {
        E temp = heap[x];
        heap[x] = heap[y];
        heap[y] = temp;
    }

    void siftDown(int i) {
        int child1, child2, min;

        while (2 * i + 1 < size) {
            child1 = 2 * i + 1;
            child2 = 2 * i + 2;
            min = child1;

            if ((child2 < size) && (heap[child2].compareTo(heap[min])) < 0) {
                min = child2;
            }

            if (heap[i].compareTo(heap[min]) < 0) {
                break;
            }

            swap(i, min);
            i = min;
        }
    }

    void siftUp(int i) {
        while (heap[i].compareTo(heap[(i - 1) / 2]) < 0) {
            swap(i, (i - 1) / 2);
            i = (i - 1) / 2;
        }
    }

    void heapify() {
        for (int i = (size / 2); i >= 0; i--) {
            siftDown(i);
        }
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
            sb.append(heap[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        heap = (E[]) new Comparable[DEFAULT_INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public boolean add(E e) {
        if (size == heap.length) {
            E[] newHeap = (E[]) new Comparable[size * 2];

            System.arraycopy(heap, 0, newHeap, 0, size);
            heap = newHeap;
        }

        heap[size] = e;
        siftUp(size);
        size++;
        return true;
    }

    @Override
    public E remove() {
        if (size == 0) {
            return null;
        }

        E temp = heap[0];
        size--;
        heap[0] = heap[size];
        siftDown(0);

        return temp;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
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
        if (size == 0) {
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

        for (cursor2 = 0; cursor2 < size; cursor2++) {
            if (!c.contains(heap[cursor2])) {
                heap[cursor1++] = heap[cursor2];
            } else {
                counter++;
            }

        }

        if (counter != 0) {
            size -= counter;
            heapify();
            return true;
        }

        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        int cursor1 = 0, cursor2 = 0, counter = 0;

        for (cursor2 = 0; cursor2 < size; cursor2++) {
            if (c.contains(heap[cursor2])) {
                heap[cursor1++] = heap[cursor2];
            } else {
                counter++;
            }
        }

        if (counter != 0) {
            size -= counter;
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

