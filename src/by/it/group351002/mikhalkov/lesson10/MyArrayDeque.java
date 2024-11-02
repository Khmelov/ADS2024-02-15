package by.it.group351002.mikhalkov.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

public class MyArrayDeque<E> implements Deque<E> {
    final int INITIAL_SIZE = 8;
    int head;
    int tail;
    E[] array;
    int size;
    public MyArrayDeque() {
        array = (E[]) new Object[INITIAL_SIZE];
        head = INITIAL_SIZE / 2;
        tail = head - 1;
        size = 0;
    }

    private void Expand(int tmpSize) {
        E[] tempArray = (E[]) new Object[tmpSize];
        int k = (tmpSize - size) / 2;

        for (int i = 0; i < size; i++) {
            tempArray[i + k] = array[head + i];
        }

        head = k;
        tail = k + size - 1;
        array = tempArray;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append('[');
        for (int i = 0; i < size; i++) {
            sb.append(array[head + i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');

        return sb.toString();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean add(E e) {
        addLast(e);
        return true;
    }

    @Override
    public void addFirst(E e) {
        if (head == 0) {
            Expand(array.length * 2);
        }

        head--;
        size++;
        array[head] = e;
    }

    @Override
    public void addLast(E e) {
        if (tail == array.length - 1) {
            Expand(array.length * 2);
        }

        tail++;
        size++;
        array[tail] = e;
    }

    @Override
    public E element() {
        return getFirst();
    }

    @Override
    public E getFirst() {
        if (size == 0) {
            return null;
        }

        return array[head];
    }

    @Override
    public E getLast() {
        if (size == 0) {
            return null;
        }

        return array[tail];
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E pollFirst() {
        if (size == 0) {
            return null;
        }

        head++;
        size--;
        return array[head - 1];
    }

    @Override
    public E pollLast() {
        if (size == 0) {
            return null;
        }

        tail--;
        size--;
        return array[tail + 1];
    }



    @Override
    public boolean offerFirst(E e) {
        return false;
    }

    @Override
    public boolean offerLast(E e) {
        return false;
    }

    @Override
    public E removeFirst() {
        return null;
    }

    @Override
    public E removeLast() {
        return null;
    }

    @Override
    public E peekFirst() {
        return null;
    }

    @Override
    public E peekLast() {
        return null;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean offer(E e) {
        return false;
    }

    @Override
    public E remove() {
        return null;
    }

    @Override
    public E peek() {
        return null;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public void push(E e) {

    }

    @Override
    public E pop() {
        return null;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean contains(Object o) {
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

    @Override
    public Iterator<E> descendingIterator() {
        return null;
    }
}