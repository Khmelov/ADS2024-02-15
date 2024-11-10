package by.it.group310901.dritz.lesson10;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.NoSuchElementException;

public class MyPriorityQueue<E extends Comparable<E>> implements Queue<E> {
    
    private final int SIZE = 8;
    private final int STEP = 4;
    private E[] array = (E[]) new Comparable[SIZE];
    private int size;

    private int getParent(int index) {
        return index < 1 ? -1 : --index / 2; 
    }

    private int getLeft(int index) {
        return index * 2 + 1;
    }

    private int getRight(int index) {
        return index * 2 + 2;
    }

    private void resize(int step) {
        E[] newArray = (E[]) new Comparable[array.length + step];
        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
    }

    private void siftUp(int index) {
        siftUp(index, getParent(index));
    }

    private void siftUp(int child, int parent) {
        if (parent >= 0 && array[child].compareTo(array[parent]) < 0) {
            siftUp(swap(child, parent));
        }
    }

    private void siftDown(int index) {
        siftDown(index, getLeft(index), getRight(index));
    }

    private void siftDown(int parent, int left, int right) {
        int tmp;
        if (right < size && array[right].compareTo(array[left]) < 0) {
            tmp = right;
        } else {
            tmp = left;
        }
        if (!(tmp < size && array[tmp].compareTo(array[parent]) < 0)) {
            tmp = parent;
        }
        if (tmp != parent) {
            siftDown(swap(parent, tmp));
        }
    }

    private void siftRestore() {
        for(int i = size / 2; i >= 0; i--) {
            siftDown(i);
        }
    }

    private int swap(int i, int j) {
        var tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
        return j;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < size - 1; i++) {
            result.append(array[i]).append(", ");
        }

        if (!isEmpty())
            result.append(array[size - 1]);

        return result.append("]").toString();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        while (size > 0) {
            array[--size] = null;
        }
    }

    @Override
    public boolean add(E e) {
        if (!offer(e)) {
            throw new IllegalStateException("Queue full");
        }
        return true;
    }

    @Override
    public E remove() {
        var e = poll();
        if (e == null) {
            throw new NoSuchElementException();
        }
        return e;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            if (array[i].equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean offer(E e) {
        if (size == array.length) {
            resize(STEP);
        }
        array[size++] = e;
        siftUp(size - 1);
        return true;
    }

    @Override
    public E poll() {
        var first = array[0];
        array[0] = array[--size];
        siftDown(0);
        return first;
    }

    @Override
    public E peek() {
        return array[0];
    }

    @Override
    public E element() {
        var e = peek();
        if (e == null) {
            throw new  NoSuchElementException();
        }
        return e;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (var e : c) {
            if (!contains(e))
                return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c == null) {
            throw new NullPointerException();
        }
        if (c == this) {
            throw new IllegalArgumentException();
        }
        int oldSize = size;
        for (var e : c) {
            add(e);
        }
        return oldSize != size;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        int removed = 0;
        for (int i = 0, j = 0; i < size; i++) {
            if (c.contains(array[i]))
                removed++;
            else
                array[j++] = array[i];
        }
        if (removed == 0) {
            return false;
        }
        size -= removed;
        siftRestore();
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        int removed = 0;
        for (int i = 0, j = 0; i < size; i++) {
            if (c.contains(array[i]))
                array[j++] = array[i];
            else
                removed++;
        }
        if (removed == 0) {
            return false;
        }
        size -= removed;
        siftRestore();
        return true;
    }

    //========= not required ==============

    @Override
    public Iterator<E> iterator() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'iterator'");
    }

    @Override
    public Object[] toArray() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toArray'");
    }

    @Override
    public <T> T[] toArray(T[] a) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toArray'");
    }

    @Override
    public boolean remove(Object o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }
}
