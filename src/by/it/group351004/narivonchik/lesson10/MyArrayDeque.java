package by.it.group351004.narivonchik.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

public class MyArrayDeque<E> implements Deque<E> {
    final int START_SIZE = 10;
    int size;
    int head;
    int tail;
    E[] items;

    public MyArrayDeque(){
        size = 0;
        items = (E[]) new Object[START_SIZE];
        head = 1;
        tail = 0;
    }

    void resize(int newLen) {
        E[] tempArr = (E[]) new Object[newLen];
        int index = 0;
        for (int i = head; index < size; i++) {
            tempArr[index++] = items[i % items.length];
        }
        head = 0;
        tail = index-1;
        items = tempArr;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        int index = 0;
        for (int i = head; index < size; i++) {
            sb.append(items[i % items.length]);
            sb.append(", ");
            index++;
        }
        sb.delete(sb.length()-2, sb.length()).append(']');

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
        if (size == items.length) {
            resize(items.length * 2);
        }
        head = (head - 1 + items.length) % items.length;
        items[head] = e;
        size++;
    }

    @Override
    public void addLast(E e) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        tail = (tail + 1) % items.length;
        items[tail] = e;
        size++;
    }

    @Override
    public E element() {
        return getFirst();
    }

    @Override
    public E getFirst() {
        if (size == 0)
            return null;
        return items[head];
    }

    @Override
    public E getLast() {
        if (size == 0)
            return null;
        return items[tail];
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E pollFirst() {
        if (size == 0)
            return null;
        E buff = items[head];
        head = (head + 1) % items.length;
        size--;
        return buff;
    }

    @Override
    public E pollLast() {
        if (size == 0)
            return null;
        E buff = items[tail];
        tail = (tail - 1 + items.length) % items.length;
        size--;
        return buff;
    }



//////////////////////////////////////////////////////////////////////////////////////////////////////
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
    public boolean offer(E e) {
        return false;
    }

    @Override
    public E remove() {
        return null;
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
