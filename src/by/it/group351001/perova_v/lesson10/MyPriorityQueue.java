package by.it.group351001.perova_v.lesson10;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

public class MyPriorityQueue<E extends Comparable<E>> implements Queue<E> {

    private static int defaultSize = 8;
    private static int defaultStep = defaultSize / 2;

    private E[] elements;
    private int size;

    public MyPriorityQueue() {
        this(defaultSize);
    }

    @SuppressWarnings("unchecked")
    public MyPriorityQueue(int size) {
        elements = (E[]) new Comparable[size];
    }

    private int getParent(int index) {
        return index < 1 ? -1 : --index / 2;
    }

    private int getLeft(int index) {
        return index * 2 + 1;
    }

    private int getRight(int index) {
        return index * 2 + 2;
    }

    @SuppressWarnings("unchecked")
    private void extend(int step) {
        var extended = (E[]) new Comparable[elements.length + step];
        System.arraycopy(elements, 0, extended, 0, size);
        elements = extended;
    }

    private void siftUp(int index) {
        siftUp(index, getParent(index));
    }

    private void siftUp(int child, int parent) {
        if (parent >= 0 && elements[child].compareTo(elements[parent]) < 0)
            siftUp(swap(child, parent));
    }

    private void siftDown(int index) {
        siftDown(index, getLeft(index), getRight(index));
    }

    private void siftDown(int parent, int left, int right) {
        var target = right < size && elements[right].compareTo(elements[left]) < 0
                ? right
                : left;
        target = target < size && elements[target].compareTo(elements[parent]) < 0
                ? target
                : parent;
        if (target != parent)
            siftDown(swap(parent, target));
    }
    private void siftRestore() {
        for (int i = size / 2; i >= 0; i--)
            siftDown(i);
    }
    private int swap(int i, int j) {
        var tmp = elements[i];
        elements[i] = elements[j];
        elements[j] = tmp;
        return j;
    }
    @Override
    public String toString() {
        var sb = new StringBuilder("[");

        for (int i = 0; i < size - 1; i++)
            sb.append(elements[i]).append(", ");

        if (!isEmpty())
            sb.append(elements[size - 1]);

        return sb.append("]").toString();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        while (size > 0)
            elements[--size] = null;
    }

    @Override
    public boolean add(E e) {
        if (!offer(e))
            throw new IllegalStateException("Queue full");
        return true;
    }

    @Override
    public E remove() {
        var e = poll();
        if (e == null)
            throw new NoSuchElementException();
        return e;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++)
            if (elements[i].equals(o))
                return true;
        return false;
    }

    @Override
    public boolean offer(E e) {
        if (size == elements.length)
            extend(defaultStep);
        elements[size++] = e;
        siftUp(size - 1);
        return true;
    }

    @Override
    public E poll() {
        var first = elements[0];
        elements[0] = elements[--size];
        siftDown(0);
        return first;
    }

    @Override
    public E peek() {
        return elements[0];
    }

    @Override
    public E element() {
        var e = peek();
        if (e == null)
            throw new NoSuchElementException();
        return e;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (var i : c)
            if (!contains(i))
                return false;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c == null)
            throw new NullPointerException();
        if (c == this)
            throw new IllegalArgumentException();
        var oldSize = size;
        for (var e : c)
            add(e);
        return oldSize != size;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        var removed = 0;
        for (int i = 0, j = 0; i < size; i++)
            if (c.contains(elements[i]))
                removed++;
            else
                elements[j++] = elements[i];

        if (removed == 0)
            return false;
        size -= removed;
        siftRestore();
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        var removed = 0;
        for (int i = 0, j = 0; i < size; i++)
            if (c.contains(elements[i]))
                elements[j++] = elements[i];
            else
                removed++;

        if (removed == 0)
            return false;
        size -= removed;
        siftRestore();
        return true;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int i;

            @Override
            public boolean hasNext() {
                return i < size;
            }

            @Override
            public E next() {
                return elements[i++];
            }
        };
    }

    @Override
    public Object[] toArray() {
        var arr = new Comparable[size];
        System.arraycopy(elements, 0, arr, 0, size);
        return arr;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < size)
            return (T[]) toArray();
        System.arraycopy(elements, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }

    @Override
    public boolean remove(Object o) {
        var i = 0;
        var has = false;
        for (; i < size; i++)
            if (has = elements[i].equals(o))
                break;
        if (!has)
            return false;
        System.arraycopy(elements, i + 1, elements, i, --size);
        siftRestore();
        return true;
    }
}
