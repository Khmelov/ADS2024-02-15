package by.it.group351001.voronov.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

public class MyArrayDeque<E> implements Deque<E> {
    final int INITIAL_CAPACITY = 8;
    int headIndex;
    int tailIndex;
    E[] elements;
    int elementCount;

    MyArrayDeque() {
        elements = (E[]) new Object[INITIAL_CAPACITY];
        tailIndex = -1;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append('[');
        for (int i = headIndex, counter = 0; counter < elementCount; i++, counter++) {
            result.append(elements[i % elements.length]);
            if (counter < elementCount - 1) {
                result.append(", ");
            }
        }
        result.append("]");
        return result.toString();
    }

    @Override
    public boolean add(E e) {
        addLast(e);
        return true;
    }

    @Override
    public void addFirst(E e) {
        if (elementCount == elements.length)
            expandCapacity(elements.length * 2);
        headIndex = (headIndex - 1 + elements.length) % elements.length;
        elements[headIndex] = e;
        elementCount++;
    }

    @Override
    public void addLast(E e) {
        if (elementCount == elements.length)
            expandCapacity(elements.length * 2);
        tailIndex = (tailIndex + 1) % elements.length;
        elements[tailIndex] = e;
        elementCount++;
    }

    void expandCapacity(int newCapacity) {
        E[] newElements = (E[]) new Object[newCapacity];
        int counter = 0;
        for (int i = headIndex; counter != elementCount; i++) {
            newElements[counter++] = elements[i % elements.length];
        }
        elements = newElements;
        headIndex = 0;
        tailIndex = elementCount - 1;
    }

    @Override
    public int size() {
        return elementCount;
    }

    @Override
    public E pollFirst() {
        if (elementCount == 0)
            return null;
        E removedItem = elements[headIndex];
        headIndex = (headIndex + 1) % elements.length;
        elementCount--;
        return removedItem;
    }

    @Override
    public E pollLast() {
        if (elementCount == 0)
            return null;
        E removedItem = elements[tailIndex];
        tailIndex = (tailIndex - 1 + elements.length) % elements.length;
        elementCount--;
        return removedItem;
    }

    @Override
    public E getFirst() {
        if (elementCount == 0)
            return null;
        return elements[headIndex];
    }

    @Override
    public E getLast() {
        if (tailIndex == -1 && elementCount > 0)
            return elements[headIndex];
        if (elementCount == 0)
            return null;
        return elements[tailIndex];
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E element() {
        return getFirst();
    }

    //////////////////////////

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
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
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
        return elementCount == 0;
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