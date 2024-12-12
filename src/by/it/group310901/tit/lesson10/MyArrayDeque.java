package by.it.group310901.tit.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;


@SuppressWarnings("all")


public class MyArrayDeque<E> implements Deque<E> {


    // Константа для начального размера массива
    private static final int defaultSize = 10;


    // Текущий размер очереди
    private int currentSize;


    // Массив для хранения элементов очереди
    private E[] elements;


    // Конструктор, инициализирующий массив и текущий размер
    public MyArrayDeque() {
        elements = (E[]) new Object[defaultSize];
        currentSize = 0;
    }


    // Метод для получения строкового представления очеред
    @Override
    public String toString() {
        String tempStr = "[";


        for (int i = 0; i < currentSize; i++) {
            tempStr += elements[i];
            if (i != currentSize - 1) {
                tempStr += ", ";
            }
        }


        tempStr += ']';


        return tempStr.toString();
    }


    // Метод для получения текущего размера очереди
    @Override
    public int size() {
        return currentSize;
    }


    // Метод для добавления элемента в очередь
    @Override
    public boolean add(E e) {
        addLast(e);
        return true;
    }


    // Метод для добавления элемента в начало очереди
    @Override
    public void addFirst(E e) {
        if (currentSize == elements.length) {
            E[] newElements = (E[]) new Object[elements.length * 2];


            System.arraycopy(elements, 0, newElements, 0, currentSize);
            elements = newElements;
        }


        currentSize++;
        for (int i = currentSize - 1; i > 0; i--) {
            elements[i] = elements[i - 1];
        }


        elements[0] = e;
    }


    // Метод для добавления элемента в конец очереди
    @Override
    public void addLast(E e) {
        if (currentSize == elements.length) {
            E[] newElements = (E[]) new Object[elements.length * 2];


            System.arraycopy(elements, 0, newElements, 0, currentSize);
            elements = newElements;
        }


        elements[currentSize++] = e;
    }


    // Метод для получения первого элемента
    @Override
    public E element() {
        return getFirst();
    }


    // Метод для получения первого элемента
    @Override
    public E getFirst() {
        if (currentSize > 0)
            return elements[0];
        return null;
    }


    // Метод для получения последнего элемента
    @Override
    public E getLast() {
        if (currentSize > 0)
            return elements[currentSize - 1];
        return null;
    }


    // Метод для удаления элемента
    @Override
    public E poll() {
        return pollFirst();
    }


    // Метод для удаления первого элемента
    @Override
    public E pollFirst() {
        if (currentSize == 0) {
            return null;
        }


        E polledElement = elements[0];


        currentSize--;


        for (int i = 0; i < currentSize; i++) {
            elements[i] = elements[i + 1];
        }


        return polledElement;
    }


    // Метод для удаления последнего элемента
    @Override
    public E pollLast() {
        if (currentSize == 0) {
            return null;
        }


        return elements[--currentSize];
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

