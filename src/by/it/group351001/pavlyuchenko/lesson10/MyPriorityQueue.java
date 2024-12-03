package by.it.group351001.pavlyuchenko.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.*;
public class MyPriorityQueue<E extends Comparable<E>> implements Queue<E> {
    private int length = 0, capacity = 10;
    private E[] heap;

    public MyPriorityQueue() {
        heap = (E[]) new Comparable[capacity];
    }

    private void swap(int x, int y) {
        E temp = heap[x];
        heap[x] = heap[y];
        heap[y] = temp;
    }

    private void SiftDown(int i) {
        int left, right, min;
        while ((i << 1) + 1 < length) {
            left = (i << 1) + 1;
            right = (i + 1) << 1;
            if ((right < length) && (heap[right].compareTo(heap[left])) < 0) {
                min = right;
            } else {
                min = left;
            }
            if (heap[i].compareTo(heap[min]) < 0) {
                break;
            }
            swap(i, min);
            i = min;
        }
    }

    private void SiftUp(int i) {
        int buff = (i - 1) >> 1;
        while (i > 0 && heap[i].compareTo(heap[buff]) < 0) {
        swap(i, buff);
        i = buff;
        buff = (i - 1) >> 1;;
        }
    }

    private void Realloc() {
        capacity = length << 1;
            E[] newArr = (E[]) new Comparable[capacity];
            System.arraycopy(heap,0,newArr,0, length);
            heap = newArr;
    }

    private void addHeap(E e) {
        if (length == capacity)
            Realloc();
        heap[length] = e;
        SiftUp(length );
        length++;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < length; i++) {
            sb.append(heap[i]);
            if (i < length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public int size() {
        return length;
    }

    public void clear() {
        heap = (E[]) new Comparable[capacity];
        length = 0;
    }

    public boolean add(E e) {
        addHeap(e);
        return true;
    }

    public E remove() {
        if (length == 0) {
            return null;
        }

        E temp = heap[0];
        length--;
        heap[0] = heap[length];
        SiftDown(0);
        return temp;
    }

    public boolean contains(Object o) {
        for (int i = 0; i < length; i++) {
            if (heap[i].equals(o)) {
                return true;
            }
        }
        return false;
    }

    public boolean offer(E e) {
        return add(e);
    }

    public E poll() {
        return remove();
    }

    public E element() {
        if (capacity == 0) {
            return null;
        }
        return heap[0];
    }

    public E peek() {
        return element();
    }

    public boolean containsAll(Collection<?> c) {
        for (Object item : c) {
            if (!contains(item)) {
                return false;
            }
        }

        return true;
    }

    public boolean addAll(Collection<? extends E> c) {
        boolean flag = false;
        for (E item : c) {
            if (add(item)) {
                flag = true;
            }
        }
        return flag;
    }

    private void heapify() {
        for (int i = (length >> 1); i >= 0; i--) {
            SiftDown(i);
        }
    }

    public boolean removeAll(Collection<?> c) {
        int cursor1 = 0, cursor2 = 0, counter = 0;

        for (cursor2 = 0; cursor2 < length; cursor2++) {
            if (!c.contains(heap[cursor2])) {
                heap[cursor1++] = heap[cursor2];
            } else {
                counter++;
            }
        }

        if (counter != 0) {
            length -= counter;
            heapify();
            return true;
        }

        return false;
    }



    public boolean retainAll(Collection<?> c) {
        int cursor1 = 0, cursor2 = 0, counter = 0;

        for (cursor2 = 0; cursor2 < length; cursor2++) {
            if (c.contains(heap[cursor2])) {
                heap[cursor1++] = heap[cursor2];
            } else {
                counter++;
            }
        }

        if (counter != 0) {
            length -= counter;
            heapify();
            return true;
        }

        return false;
    }

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



