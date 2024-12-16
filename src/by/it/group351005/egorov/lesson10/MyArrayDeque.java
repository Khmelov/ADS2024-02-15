package by.it.group351005.egorov.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

public class MyArrayDeque<E> implements Deque<E> {

    private final int INIT_SIZE = 4;
    private E[] _arr;
    private int _size;
    private int _head;
    private int _tail;

    private void resize(){
        E[] newArr = (E[]) new Object[_arr.length * _arr.length];
        for (int i = 0, j = _head; i < _size; j++, i++) {
            newArr[i] = _arr[j % _arr.length];
        }
        _arr = newArr;
        _head = 0;
        _tail = _size - 1;
    }

    public MyArrayDeque() {
        _arr = (E[])new Object[INIT_SIZE];
        _head = 0;
        _tail = -1;
        _size = 0;
    }

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        for (int i = 0, j = _head; i < _size; i++, j++) {
            stringBuilder.append(_arr[j % _arr.length]).append(", ");
        }
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    @Override
    public int size() {
        return _size;
    }

    @Override
    public void addFirst(E e) {
        if (_arr.length < _size + 1) {
            resize();
        }
        _head = (_head - 1 + _arr.length) % _arr.length;
        _arr[_head] = e;
        _size++;
    }

    @Override
    public void addLast(E e) {
        if (_arr.length < _size + 1) {
            resize();
        }
        _tail = (_tail + 1) % _arr.length;
        _arr[_tail] = e;
        _size++;
    }

    @Override
    public boolean add(E e) {
        addLast(e);
        return true;
    }

    @Override
    public E getFirst() {
        if (_size == 0)
            return null;
        return _arr[_head];
    }

    @Override
    public E getLast() {
        if (_size == 0)
            return null;
        return _tail != -1 ? _arr[_tail] : _arr[_head];
    }

    @Override
    public E element() {
        return getFirst();
    }

    @Override
    public E pollFirst() {
        if (_size == 0)
            return null;
        E elem = _arr[_head];
        _head = (_head + 1) % _arr.length;
        _size--;
        return elem;
    }

    @Override
    public E pollLast() {
        if (_size == 0)
            return null;
        E elem = _arr[_tail];
        _tail = (_tail - 1 + _arr.length) % _arr.length;
        _size--;
        return elem;
    }

    @Override
    public E poll() {
        return pollFirst();
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
