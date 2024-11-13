package lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyArrayDeque<E>  implements Deque<E> {
    private static final int INITIAL_CAPACITY = 10;
    private E[] elements;
    private int size;
    private int front;
    private int rear;

    @SuppressWarnings("unchecked")
    public MyArrayDeque() {
        this.elements = (E[]) new Object[INITIAL_CAPACITY];
        this.size = 0;
        this.front = 0;
        this.rear = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(elements[(front + i) % elements.length]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        return sb.append("]").toString();
    }

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

    public void addFirst(E element) {
        ensureCapacity();
        front = (front - 1 + elements.length) % elements.length;
        elements[front] = element;
        size++;
    }

    public void addLast(E element) {
        ensureCapacity(); // Проверяем, достаточно ли места
        elements[rear] = element;
        rear = (rear + 1) % elements.length; // Увеличиваем rear
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

    public E getFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return elements[front];
    }

    public E getLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return elements[(rear - 1 + elements.length) % elements.length];
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

    public E poll() {
        return pollFirst();
    }

    public E pollFirst() {
        if (size == 0) {
            return null;
        }
        E result = elements[front];
        elements[front] = null; // Очистка ссылки
        front = (front + 1) % elements.length;
        size--;
        return result;
    }

    public E pollLast() {
        if (size == 0) {
            return null;
        }
        rear = (rear - 1 + elements.length) % elements.length;
        E result = elements[rear];
        elements[rear] = null; // Очистка ссылки
        size--;
        return result;
    }

    private void ensureCapacity() {
        if (size == elements.length) {
            resize(2 * elements.length);
        }
    }


    private void resize(int newCapacity) {
        E[] newElements = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[(front + i) % elements.length];
        }
        elements = newElements;
        front = 0;
        rear = size;
    }
}
