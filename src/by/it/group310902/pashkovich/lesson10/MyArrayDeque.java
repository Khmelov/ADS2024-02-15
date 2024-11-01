package by.it.group310902.pashkovich.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

public class MyArrayDeque<E> implements Deque<E> {
    private E[] array;
    private int head;
    private int tail;
    private int size;

    public MyArrayDeque() {
        array = (E[]) new Object[10];
        head = 0;
        tail = 0;
        size = 0;
    }

    private void resize() {
        E[] newArray = (E[]) new Object[array.length * 2];
        for (int i = 0; i < size; i++) {
            newArray[i] = array[(head + i) % array.length];
        }
        array = newArray;
        head = 0;
        tail = size;
    }

    @Override
    public void addFirst(E element) {
        if (size == array.length) {
            resize();
        }
        head = (head - 1 + array.length) % array.length;
        array[head] = element;
        size++;
    }

    @Override
    public void addLast(E element) {
        if (size == array.length) {
            resize();
        }
        array[tail] = element;
        tail = (tail + 1) % array.length;
        size++;
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
    public boolean add(E element) {
        addLast(element);
        return true;
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
    public E getFirst() {
        if (size == 0);
        return array[head];
    }

    @Override
    public E getLast() {
        if (size == 0);
        return array[(tail - 1 + array.length) % array.length];
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
    public E element() {
        return getFirst();
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
    public E pollFirst() {
        if (size == 0) return null;
        E result = array[head];
        array[head] = null;
        head = (head + 1) % array.length;
        size--;
        return result;
    }

    @Override
    public E pollLast() {
        if (size == 0) return null;
        tail = (tail - 1 + array.length) % array.length;
        E result = array[tail];
        array[tail] = null;
        size--;
        return result;
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public int size() {
        return size;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(array[(head + i) % array.length]);
            if (i < size - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
