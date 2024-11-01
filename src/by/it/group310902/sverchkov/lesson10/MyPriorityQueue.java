package by.it.group310902.sverchkov.lesson10;

import java.util.*;
import java.util.function.Predicate;

public class MyPriorityQueue<E> implements Queue<E> {

    E[] heap;
    final int DEFALT_CAPACITY = 10;
    final Comparator<? super E> comparator;
    int size = 0;

    private int parent(int i) {
        return (i - 1) / 2;
    }

    private int leftChild(int i) {
        return (2 * i + 1);
    }

    private int rightChild(int i) {
        return (2 * i + 2);
    }

    private void swap(int i, int j) {
        E temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    MyPriorityQueue() {
        this.comparator = null;
        heap = (E[]) new Object[DEFALT_CAPACITY];
    }

    MyPriorityQueue(Comparator<? super E> comparator) {
        this.comparator = comparator;
        heap = (E[]) new Object[DEFALT_CAPACITY];
    }

    MyPriorityQueue(int size, Comparator<? super E> comparator) {
        this.comparator = comparator;
        heap = (E[]) new Object[size];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) throw new NullPointerException();
        if (size == 0) return false;
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
        return heap;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(E e) {

        if (e == null)
            throw new NullPointerException();

        if (size >= heap.length) {
            growArray();
        }
        int i = size++;
        heap[i] = e;
        siftUp(i);

        return true;
    }

    private void growArray() {
        heap = Arrays.copyOf(heap, heap.length * 2);
    }

    private void siftUp(int index){
        if (comparator == null)
            siftUpComparable(index);
        else
            siftUpUsingComparator(index);
    }

    private void siftUpComparable(int index) {
        Comparable<? super E> key = (Comparable<? super E>) heap[index];
        while (index > 0) {
            int parent = parent(index);
            E e = heap[parent];
            if (key.compareTo(e) >= 0)
                break;
            heap[index] = e;
            index = parent;
        }
        heap[index] = (E) key;
    }

    private void siftUpUsingComparator(int index) {
        E temp = heap[index];
        while (index > 0) {
            int parent = parent(index);
            E e = heap[parent];
            if (comparator.compare(temp, e) >= 0)
                break;
            heap[index] = e;
            index = parent;
        }
        heap[index] = temp;
    }

    private void siftDown(int index){
        if (comparator == null)
            siftDownComparable(index);
        else
            siftDownUsingComparator(index);
    }

    private void siftDownUsingComparator(int index) {
        int half = parent(size+1);
        E temp = heap[index];
        while (index < half) {
            int left = leftChild(index);
            Object c = heap[left];
            int right = rightChild(index);
            if (right < size && comparator.compare((E) c, (E) heap[right]) > 0)
                c = heap[left = right];
            if (comparator.compare(temp, (E) c) <= 0)
                break;
            heap[index] =(E) c;
            index = left;
        }
        heap[index] = temp;
    }

    private void siftDownComparable(int index) {
        Comparable<? super E> key = (Comparable<? super E>) heap[index];
        int half = parent(size+1);
        while (index < half) {
            int left = leftChild(index);
            Object c = heap[left];
            int right = rightChild(index);
            if (right < size &&
                    ((Comparable<? super E>) c).compareTo(heap[right]) > 0)
                c = heap[left = right];
            if (key.compareTo((E) c) <= 0)
                break;
            heap[index] = (E) c;
            index = left;
        }
        heap[index] = (E) key;
    }

    private void heapify() {

        int i = size;
        if (comparator == null)
            for (; i >= 0; i--)
                siftDownComparable(i);
        else
            for (; i >= 0; i--)
                siftDownUsingComparator(i);
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) throw new NullPointerException();
        if (size == 0) throw new NoSuchElementException();

        for (int i = 0; i < size; i++) {
            if (heap[i].equals(o)) {
                heap[i] = heap[size - 1];
                heap[--size] = null;
                siftDown(i);
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (c == null) throw new NullPointerException();
        if (size == 0) return false;
        for (Object o : c) {
            if (!contains(o))
                return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c == null)
            throw new NullPointerException();
        if (c == this)
            throw new IllegalArgumentException();
        boolean modified = false;
        for (E e : c)
            if (add(e))
                modified = true;
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c == null) throw new NullPointerException();
        if (size == 0) return false;

        Predicate<? super E> filter = e-> c.contains(e);
        int j;
        for (j = 0; j < size && !filter.test((E) heap[j]); j++)
            ;
        if (j >= size) {
            return false;
        }

        int k = 0;
        E[] newHeap = (E[]) new Object[heap.length];
        for (int i = 0; i < size(); i++) {
            if (!c.contains(heap[i])) {
                newHeap[k++] = heap[i];
            }
        }
        heap = newHeap;
        size = k;
        heapify();
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (c == null) throw new NullPointerException();
        if (isEmpty()) return false;

        Predicate<? super E> filter = e-> !c.contains(e);
        int j;
        for (j = 0; j < size && !filter.test((E) heap[j]); j++)
            ;
        if (j >= size) {
            return false;
        }

        int k = 0;
        E[] newHeap = (E[]) new Object[heap.length];
        for (int i = 0; i < size(); i++) {
            if (c.contains(heap[i])) {
                newHeap[k++] = heap[i];
            }
        }

        heap = newHeap;
        size = k;
        heapify();
        return true;
    }



    @Override
    public void clear() {
        heap = (E[]) new Object[DEFALT_CAPACITY];
        size = 0;
    }

    @Override
    public boolean offer(E e) {
        return add(e);
    }

    @Override
    public E remove() {
        E x = poll();
        if (x != null)
            return x;
        else
            throw new NoSuchElementException();
    }

    @Override
    public E poll() {
        final E result;

        if ((result = heap[0]) != null) {
            heap[0] = heap[--size];
            heap[size] = null;
            if (size > 0) {
                if (comparator == null)
                    siftDownComparable(0);
                else
                    siftDownUsingComparator(0);
            }
        }
        return result;
    }

    @Override
    public E element() {
        return heap[0];
    }

    @Override
    public E peek() {
        return heap[0];
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("[");
        for (int i = 0; i < size(); i++) {
            string.append(heap[i]);
            if (i < size - 1) {
                string.append(", ");
            }
        }
        string.append("]");
        return string.toString();
    }
}
