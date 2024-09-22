package by.it.group351005.vaveyko.lesson10;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

public class MyPriorityQueue<E> implements Queue<E> {

    final int defaultSize = 10;
    E[] _items;
    int _count;

    MyPriorityQueue () {
        _items = (E[]) new Object[defaultSize];
        _count = 0;
    }

    int getL(int i) {
        return i*2+1;
    }
    int getR(int i) {
        return i*2+2;
    }

    public String toString() {
        StringBuilder line = new StringBuilder();
        line.append('[');
        for (int i = 0; i < _count; i++) {
            line.append(_items[i]);
            if (i < _count - 1)
                line.append(", ");
        }
        line.append(']');
        return line.toString();
    }

    @Override
    public int size() {
        return _count;
    }

    @Override
    public boolean isEmpty() {
        return _count == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < _count; i++)
            if (o.equals(_items[i]))
                return true;
        return false;
    }

    @Override
    public boolean add(E e) {
        return false;
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
    public boolean offer(E e) {
        return false;
    }

    @Override
    public E remove() {
        return null;
    }

    @Override
    public E poll() {
        return null;
    }

    @Override
    public E element() {
        return null;
    }

    @Override
    public E peek() {
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
    public Iterator<E> iterator() {
        return null;
    }
}
