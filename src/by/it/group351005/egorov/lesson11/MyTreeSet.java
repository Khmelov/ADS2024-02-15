package by.it.group351005.egorov.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyTreeSet<E extends Comparable<E>>  implements Set<E> {

    private final int DEFAULT_SIZE = 16;
    private int _size;
    private E[] _arr;

    private void resize() {
        E[] newArr = (E[]) new Comparable[_arr.length * 2];
        for (int i = 0; i < _size; i++) {
            newArr[i] = _arr[i];
        }
        _arr = newArr;
    }

    private int binarySearch(E e) {
        int left = 0;
        int right = _size - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (_arr[mid].compareTo(e) > 0) {
                right = mid - 1;
            }
            else if (_arr[mid].compareTo(e) < 0) {
                left = mid + 1;
            }
            else {
                return mid;
            }
        }
        return -1;
    }

    public MyTreeSet() {
        _size = 0;
        _arr = (E[])new Comparable[DEFAULT_SIZE];
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        for (int i = 0; i < _size; i++) {
            stringBuilder.append(_arr[i]).append(", ");
        }
        if (stringBuilder.length() > 1) {
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    @Override
    public int size() {
        return _size;
    }

    @Override
    public boolean isEmpty() {
        return _size == 0;
    }

    @Override
    public boolean add(E e) {
        if (contains(e))
            return false;
        if (_size == _arr.length) {
            resize();
        }
        int i = _size - 1;
        while (i >= 0 && _arr[i].compareTo(e) > 0) {
            _arr[i + 1] = _arr[i];
            i--;
        }
        _arr[i + 1] = e;
        _size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int i = binarySearch((E)o);
        if (i == -1) {
            return false;
        }
        _size--;
        for (; i < _size; i++) {
            _arr[i] = _arr[i+1];
        }
        return true;
    }

    @Override
    public boolean contains(Object o) {
        return binarySearch((E)o) != -1;
    }

    @Override
    public void clear() {
        _arr = (E[]) new Comparable[_arr.length];
        _size = 0;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        for (var c: collection) {
            if (!contains(c)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        boolean isModified = false;
        for (var c : collection) {
            if (add(c) && !isModified) {
                isModified = true;
            }
        }
        return isModified;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        boolean isModified = false;
        int i = 0;
        while (i < _size) {
            if (!collection.contains(_arr[i])) {
                remove(_arr[i]);
                if (!isModified) {
                    isModified = true;
                }
            }
            else {
                i++;
            }
        }
        return isModified;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean isModified = false;
        for (var c : collection) {
            if (remove(c) && !isModified) {
                isModified = true;
            }
        }
        return isModified;
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
    public <T> T[] toArray(T[] ts) {
        return null;
    }
}
