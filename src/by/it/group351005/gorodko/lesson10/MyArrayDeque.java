package by.it.group351005.gorodko.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

public class MyArrayDeque<E> implements Deque<E> {

    private int size;
    private E[] adeque;

    public MyArrayDeque() {
        size = 0;
        adeque = (E[]) new Object[size];
    }

    public MyArrayDeque(int s) {
        size = s;
        adeque = (E[]) new Object[size];
    }

    @Override
    public String toString() {
        String res = "[";
        for (int i = 0; i < size - 1; i++) {
            res += adeque[i].toString() + ", ";
        }
        if (size != 0) {
            res += adeque[size - 1].toString();
        }
        res += "]";
        return res;
    }

    public int size() {
        return size;
    }

    public boolean add(E element) {
        size++;
        E[] newadeque = (E[]) new Object[size];
        System.arraycopy(adeque, 0, newadeque, 0, size - 1);
        newadeque[size - 1] = element;
        adeque = newadeque;
        return true;
    }

    public void addFirst(E element) {
        size++;
        E[] newadeque = (E[]) new Object[size];
        System.arraycopy(adeque, 0, newadeque, 1, size - 1);
        newadeque[0] = element;
        adeque = newadeque;
    }

    public void addLast(E element) {
        add(element);
    }

    public E element() {
        if (size == 0) {
            return null;
        }
        return adeque[0];
    }

    public E getFirst() {
        return element();
    }

    public E getLast() {
        if (size == 0) {
            return null;
        }
        return adeque[size - 1];
    }

    public E poll() {
        if (size == 0) return null;
        E res = adeque[0];
        size--;
        E[] newadeque = (E[]) new Object[size];
        System.arraycopy(adeque, 1, newadeque, 0, size);
        adeque = newadeque;
        return res;
    }

    public E pollFirst() {
        return poll();
    }

    public E pollLast() {
        if (size == 0) return null;
        E res = adeque[size - 1];
        size--;
        E[] newadeque = (E[]) new Object[size];
        System.arraycopy(adeque, 0, newadeque, 0, size);
        adeque = newadeque;
        return res;
    }

    //////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////

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
    public boolean offer(E e) {
        return false;
    }

    @Override
    public E remove() {
        return null;
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
