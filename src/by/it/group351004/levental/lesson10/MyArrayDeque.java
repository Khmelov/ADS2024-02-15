package lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

public class MyArrayDeque<E> implements Deque<E> {
    final int defaultSize = 10;
    int _count;
    int _front;
    int _back;
    E[] _items;

    MyArrayDeque(){
        _count = 0;
        _items = (E[]) new Object[defaultSize];
        _front = 1;
        _back = 0;
    }

    void resize(int newLen) {
        E[] tempArr = (E[]) new Object[newLen];
        int index = 0;
        for (int i = _front; index < _count; i++) {
            tempArr[index++] = _items[i % _items.length];
        }
        _front = 0;
        _back = index-1;
        _items = tempArr;
    }

    @Override
    public int size() {
        return _count;
    }

    public String toString(){
        StringBuilder line = new StringBuilder();
        line.append('[');
        int index = 0;
        for (int i = _front; index < _count; i++) {
            line.append(_items[i % _items.length]);
            if (index < _count-1) {
                line.append(", ");
            }
            index++;
        }
        line.append(']');

        return line.toString();
    }

    @Override
    public boolean add(E e) {
        addLast(e);
        return true;
    }

    @Override
    public void addFirst(E e) {
        if (_count == _items.length) {
            resize(_items.length * 2);
        }
        _front = (_front - 1 + _items.length) % _items.length;
        _items[_front] = e;
        _count++;
    }

    @Override
    public void addLast(E e) {
        if (_count == _items.length) {
            resize(_items.length * 2);
        }
        _back = (_back + 1) % _items.length;
        _items[_back] = e;
        _count++;
    }

    @Override
    public E element() {
        return getFirst();
    }

    @Override
    public E getFirst() {
        if (_count == 0)
            return null;
        return _items[_front];
    }

    @Override
    public E getLast() {
        if (_count == 0)
            return null;
        return _items[_back];
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E pollFirst() {
        if (_count == 0)
            return null;
        E buff = _items[_front];
        _front = (_front + 1) % _items.length;
        _count--;
        return buff;
    }

    @Override
    public E pollLast() {
        if (_count == 0)
            return null;
        E buff = _items[_back];
        _back = (_back - 1 + _items.length) % _items.length;
        _count--;
        return buff;
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
