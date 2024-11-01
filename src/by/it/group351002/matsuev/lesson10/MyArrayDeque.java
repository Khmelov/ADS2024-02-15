package by.it.group351002.matsuev.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

public class MyArrayDeque<E> implements Deque<E> {
    E[] elements;
    int curInd = 0;
    static int size = 8;

    public MyArrayDeque() {

        this(size);
    }

    public MyArrayDeque(int size) {

        elements = (E[]) new Object[size];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < curInd; i++) {
            sb.append(elements[i]);

            if (i < curInd - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
        return sb.toString();
    }

    @Override
    public int size() {

        return curInd;
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
    public boolean add(E element){
        if (curInd == elements.length) {
            E[] tempElements = (E[]) new Object[elements.length * 2];

            for (int i = 0; i < elements.length; i++) {
                tempElements[i] = elements[i];
            }

            elements = tempElements;
        }

        elements[curInd] = element;
        curInd++;
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
    public void addFirst(E element){
        if (curInd == elements.length) {
            E[] tempElements = (E[]) new Object[elements.length * 2];

            for (int i = 0; i < elements.length; i++) {
                tempElements[i] = elements[i];
            }

            elements = tempElements;
        }
        for (int i = curInd; i >= 1; i--) {
            elements[i] = elements[i - 1];
        }
        elements[0] = element;
        curInd++;
    }

    @Override
    public void addLast(E element){
        if (curInd == elements.length) {
            E[] tempElements = (E[]) new Object[elements.length * 2];

            for (int i = 0; i < elements.length; i++) {
                tempElements[i] = elements[i];
            }

            elements = tempElements;
        }

        elements[curInd] = element;
        curInd++;
    }

    @Override
    public E element(){
        if (curInd != 0)
          return elements[0];
        else
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
    public E getFirst(){
        if (curInd != 0)
            return elements[0];
        else
            return null;
    }

    @Override
    public E getLast(){
        if (curInd != 0)
            return elements[curInd-1];
        else
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
    public E poll(){
        if (curInd != 0) {
            E deleted = elements[0];
            for (int i = 0; i < curInd - 1; i++) {
                elements[i] = elements[i + 1];
            }
            curInd--;
            return deleted;
        }
        else
            return null;
    }

    @Override
    public E pollFirst(){
        if (curInd != 0) {
            E deleted = elements[0];
            for (int i = 0; i < curInd - 1; i++) {
                elements[i] = elements[i + 1];
            }
            curInd--;
            return deleted;
        }
        else
            return null;
    }

    @Override
    public E pollLast(){
        if (curInd != 0) {
            E deleted = elements[curInd-1];
            curInd--;
            return deleted;
        }
        else
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
}
