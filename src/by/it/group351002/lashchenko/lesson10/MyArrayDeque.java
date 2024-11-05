package by.it.group351002.lashchenko.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

public class MyArrayDeque<E> implements Deque<E> {

    final int defaultSize = 10;
    int _count;
    E[] _items;

    MyArrayDeque(){
        _count = 0;
        _items = (E[]) new Object[defaultSize];
    }

    void resize(int newLen) {
        E[] tempArr = (E[]) new Object[newLen];

        for (int i = 0; i < _count; i++) {
            tempArr[i] = _items[i];
        }


        _items = tempArr;
    }

    @Override
    public int size() {
        return _count;
    }

    public String toString(){
        StringBuilder line = new StringBuilder();
        line.append('[');

        for (int i = 0; i < _count; i++) {
            line.append(_items[i]);
            if (i < _count-1) {
                line.append(", ");
            }
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
        for(int i=_count; i>0; i--){
            _items[i]=_items[i-1];
        }
        _items[0] = e;
        _count++;
    }

    @Override
    public void addLast(E e) {
        if (_count == _items.length) {
            resize(_items.length * 2);
        }
        _items[_count] = e;
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
        return _items[0];
    }

    @Override
    public E getLast() {
        if (_count == 0)
            return null;
        return _items[_count-1];
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E pollFirst() {
        if (_count == 0)
            return null;
        E buff = _items[0];
        for(int i=0; i<_count-1; i++){
            _items[i]=_items[i+1];
        }
        _count--;
        return buff;
    }

    @Override
    public E pollLast() {
        if (_count == 0)
            return null;
        E buff = _items[_count-1];
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

