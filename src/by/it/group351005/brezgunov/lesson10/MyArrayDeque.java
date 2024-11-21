package by.it.group351005.brezgunov.lesson10;

import java.util.*;

public class MyArrayDeque<E> implements Deque<E> {
    private Object[] arr;
    private int head = 0;
    private int tail = 0;

    public MyArrayDeque() {
        arr = new Object[10];
    }
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        if (tail - head > 0) stringBuilder.append(arr[head]);
        for (int i = head + 1; i < tail; i++) {
            stringBuilder.append(", ").append(arr[i]);
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
    @Override
    public void addLast(Object o) {
        if (tail == arr.length){
            arr = Arrays.copyOf(arr, tail + (tail >> 1));
        }
        arr[tail++]=o;
    }

    @Override
    public int size() {
        return tail - head;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Iterator<E> descendingIterator() {
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
        addLast(e);
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
    public E poll() {
        E temp = (E) arr[head];
        arr[head++] = null;
        return temp;
    }

    @Override
    public E element() {
        return (E) arr[head];
    }

    @Override
    public E peek() {
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
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public void push(E e) {

    }

    @Override
    public E pop() {
        return null;
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
    public void addFirst(E e) {
        if (head == 0 && tail > 0) {
            System.arraycopy(arr, 0, arr, 1, tail++);
            arr[head] = e;
        } else {
            arr[--head] = e;
        }
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
    public E pollFirst() {
        return poll();
    }

    @Override
    public E pollLast() {
        E temp = (E) arr[tail - 1];
        arr[tail-- - 1] = null;
        return temp;
    }

    @Override
    public E getFirst() {
        return (E) arr[head];
    }

    @Override
    public E getLast() {
        return (E) arr[tail - 1];
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

}
