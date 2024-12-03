package by.it.group351001.pavlyuchenko.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

public class MyArrayDeque<E> implements Deque<E> {

    private int start,end, capacity, length;
    private E[] deque;

    public MyArrayDeque() {
        deque = (E[]) new Object[11];
        start = 5;
        capacity = 11;
        length = 0;
    }

    private E[] Realloc() {
        capacity = (length << 1) + 1;
        E[] newElements = (E[]) new Object[capacity];
        start = (capacity - length) >> 1;
        end = start + length - 1;
        System.arraycopy(deque, 0, newElements, start , length);
        return newElements;
    }

    public String toString() {
        StringBuilder newString = new StringBuilder();

        newString.append('[');
        for (int i = start; i <= end; i++) {
            newString.append(deque[i]);
            if (i != end) {
                newString.append(", ");
            }
        }

        newString.append(']');

        return newString.toString();
    }

    public int size() {
        return length;
    }

    public void addLast(E e) {
        if (length == capacity)
            deque = Realloc();
            if (end == capacity - 1) {
                for (int i = start; i <= end; i++)
                    deque[i - 1] = deque[i];
                start--;
            } else {
                end = start + length;
            }
        deque[end] = e;
        length++;
    }

    public void addFirst(E e) {
        if (length == capacity)
            deque = Realloc();
        if (start == 0) {
            for (int i = end - 1; i >= start; i--)
                deque[i + 1] = deque[i];
            end++;
        } else
            start = end - length;
        deque[start] = e;
        length++;
    }

    public boolean add(E e) {
        addLast(e);
        /*if (end < capacity - 1 || start == 0) {
            addLast(e);
        } else
            addFirst(e); */
        return true;
    }

    public E element() {
        if (length != 0)
            return deque[start];
        return null;
    }

    public E getFirst() {
        if (length != 0)
            return deque[start];
        return null;
    }

    public E getLast() {
        if (length != 0)
            return deque[end];
        return null;
    }

    public E poll() {
        if (length != 0) {
            length--;
            return deque[start++];
        }

        return null;
    }


    public E pollFirst() {
        return poll();
    }

    public E pollLast() {
        if (length != 0) {
            length--;
            return deque[end--];
        }
        return null;
    }

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
