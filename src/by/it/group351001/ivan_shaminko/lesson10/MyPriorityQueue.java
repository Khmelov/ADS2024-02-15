package by.it.group351001.ivan_shaminko.lesson10;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

public class MyPriorityQueue< E extends Comparable<E>> implements Queue<E> {
    private E[] heap;
    private int size;

    public MyPriorityQueue() {
        heap = (E[]) new Comparable[10]; // Начальный размер кучи
        size = 0;
    }

    private void resizeHeap(){
        E[] newHeap = (E[]) new Comparable[heap.length * 2];
        System.arraycopy(heap, 0, newHeap, 0, heap.length);
        heap = newHeap;
    }

    private void siftUp(int index) {
        while (index > 0 && heap[parent(index)].compareTo(heap[index]) > 0) {
            swap(index, parent(index));
            index = parent(index);
        }
    }

    private void swap(int i, int j) {
        E temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    private int parent(int index) {
        return (index - 1) / 2;
    }

    private int leftChild(int index) {
        return 2 * index + 1;
    }

    private int rightChild(int index) {
        return 2 * index + 2;
    }
    private void siftDown(int index) {
        while (leftChild(index) < size) {
            int minChild = leftChild(index);
            if (rightChild(index) < size && heap[rightChild(index)].compareTo(heap[minChild]) < 0) {
                minChild = rightChild(index);
            }
            if (heap[index].compareTo(heap[minChild]) <= 0) {
                break;
            }
            swap(index, minChild);
            index = minChild;
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
    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            if (heap[i].equals(o)) {
                return true;
            }
        }
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

    @Override
    public boolean add(E e) {
        offer(e);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object element : c) {
            if (!contains(element)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for (E element : c) {
            if (offer(element)) {
                modified = true;
            }
        }
        return modified;
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
            for (int i = (size / 2); i >= 0; i--) {
                siftDown(i);
            }
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
            for (int i = (size / 2); i >= 0; i--) {
                siftDown(i);
            }
            return true;
        }

        return false;
    }

    @Override
    public void clear() {
        heap = (E[]) new Comparable[10];
        size = 0;
    }

    @Override
    public boolean offer(E e) {
        if (size == heap.length) {
            resizeHeap();
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
    public E poll() {
        if (isEmpty()) {
            return null;
        }
        E min = heap[0];
        heap[0] = heap[size - 1];
        size--;
        siftDown(0);
        return min;
    }

    @Override
    public E element() {
        if (isEmpty()) {
            throw new IllegalStateException("PriorityQueue is empty");
        }
        return heap[0];
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            return null;
        }
        return heap[0];
    }
}
