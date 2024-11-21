package by.it.group310902.sverchkov.lesson10;

import javax.naming.OperationNotSupportedException;
import java.util.*;

public class MyArrayDeque<E> implements Deque<E> {

    private int firstIndex = 0;
    private int lastIndex = 0;
    private int count = 0;
    private final int DEFAULT_CAPASITY = 10;
    E[] elements;

    MyArrayDeque() {
        elements = (E[]) new Object[DEFAULT_CAPASITY];
    }

    MyArrayDeque(int size) {
        elements = (E[]) new Object[size];
    }

    private void growArray() {
        E[] tempElements = (E[]) new Object[elements.length * 2];
        int i = 0;
        if (firstIndex > lastIndex) {
            while (firstIndex < elements.length) {
                tempElements[i++] = elements[firstIndex++];
            }
            firstIndex = 0;
        }
        while (firstIndex <= lastIndex) {
            tempElements[i++] = elements[firstIndex++];
        }
        elements = tempElements;
        firstIndex = 0;
        lastIndex = count - 1;
    }

    @Override
    public void addFirst(E e) {
        if (isEmpty()) {
            elements[firstIndex] = e;
            lastIndex = firstIndex;
            count++;
            return;
        }
        if (firstIndex == 0) {
            if (lastIndex == elements.length - 1) {
                growArray();
            }
            elements[elements.length - 1] = e;
            firstIndex = elements.length - 1;
        } else {
            elements[--firstIndex] = e;
        }
        count++;
    }

    @Override
    public void addLast(E e) {
        if (isEmpty()) {
            elements[lastIndex] = e;
            count++;
            firstIndex = lastIndex;
            return;
        }
        if (lastIndex == elements.length - 1) {
            if (firstIndex == 0) {
                growArray();
                elements[++lastIndex] = e;
            } else {
                elements[0] = e;
                lastIndex = 0;
            }
        } else {
            elements[++lastIndex] = e;
        }
        count++;
    }

    @Override
    public boolean offerFirst(E e) {
        try{
            addFirst(e);
        } catch (Exception exception){
            return false;
        }
        return true;
    }

    @Override
    public boolean offerLast(E e) {
        try{
            addLast(e);
        } catch (Exception exception){
            return false;
        }
        return true;
    }

    @Override
    public E removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        E removed;
        removed = elements[firstIndex];

        if (firstIndex == elements.length - 1) {
            elements[firstIndex] = null;
            firstIndex = 0;
        } else {
            elements[firstIndex++] = null;
        }

        return removed;
    }

    @Override
    public E removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        E removed;
        removed = elements[lastIndex];

        if (lastIndex == 0) {
            elements[lastIndex] = null;
            lastIndex = elements.length - 1;
        } else {
            elements[lastIndex--] = null;
        }

        return removed;
    }

    @Override
    public E pollFirst() {
        if (isEmpty()) {
            return null;
        }

        E removed;
        removed = elements[firstIndex];

        if (firstIndex == elements.length - 1) {
            elements[firstIndex] = null;
            firstIndex = 0;
        } else {
            elements[firstIndex++] = null;
        }
        count--;
        return removed;
    }

    @Override
    public E pollLast() {
        if (isEmpty()) {
            return null;
        }

        E removed;
        removed = elements[lastIndex];

        if (lastIndex == 0) {
            elements[lastIndex] = null;
            lastIndex = elements.length - 1;
        } else {
            elements[lastIndex--] = null;
        }
        count--;
        return removed;
    }

    @Override
    public E getFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        return elements[firstIndex];
    }

    @Override
    public E getLast() {
        if (isEmpty()) throw new NoSuchElementException();
        return elements[lastIndex];
    }

    @Override
    public E peekFirst() {
        if (isEmpty()) return null;
        return elements[firstIndex];
    }

    @Override
    public E peekLast() {
        if (isEmpty()) return null;
        return elements[lastIndex];
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
    public boolean add(E e) {
        try{
            addLast(e);
        } catch (Exception exception){
            return false;
        }
        return true;
    }

    @Override
    public boolean offer(E e) {
        try{
            offerLast(e);
        } catch (Exception exception){
            return false;
        }
        return true;
    }

    @Override
    public E remove() {
        return removeFirst();
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E element() {
        return getFirst();
    }

    @Override
    public E peek() {
        return peekFirst();
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
        addFirst(e);
    }

    @Override
    public E pop() {
        return removeFirst();
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
    public int size() {
        return count;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("[");
        int i = firstIndex;
        if (lastIndex < firstIndex){
            for (i = firstIndex; i < elements.length; i++){
                string.append(elements[i].toString());
                if (i != lastIndex){
                    string.append(", ");
                }
            }
            i = 0;
        }
        while (i <= lastIndex){
            string.append(elements[i].toString());
            if (i != lastIndex){
                string.append(", ");
            }
            i++;
        }
        string.append("]");
        return string.toString();
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
