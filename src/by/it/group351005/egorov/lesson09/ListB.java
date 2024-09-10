package by.it.group351005.egorov.lesson09;

import java.util.*;

public class ListB<E> implements List<E> {


    private final int _INIT_SIZE = 4;
    private E[] _list = (E[])new Object[_INIT_SIZE];
    private int _len = 0;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (int i = 0; i < _len - 1; i++) {
            stringBuilder.append(_list[i]).append(", ");
        }
        if (_len != 0) {
            stringBuilder.append(_list[_len - 1]);
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public boolean add(E e) {
        if (_len + 1 > _list.length) {
            E[] newList = (E[])new Object[_list.length * _list.length];
            for (int i = 0; i < _len; i++) {
                newList[i] = _list[i];
            }
            _list = newList;
        }
        _list[_len] = e;
        _len++;
        return true;
    }

    @Override
    public E remove(int index) {
        if (index >= _len) {
            return null;
        }
        E removeElem = _list[index];
        for (int i = index; i < _len - 1; i++) {
            _list[i] = _list[i+1];
        }
        _len--;
        return removeElem;
    }

    @Override
    public int size() {
        return _len;
    }

    @Override
    public void add(int index, E element) {
        if (index >= _len) {
            throw new RuntimeException("Invalid Index");
        }
        if (_len + 1 > _list.length) {
            E[] newList = (E[])new Object[_list.length * _list.length];
            for (int i = 0; i < index; i++) {
                newList[i] = _list[i];
            }
            newList[index] = element;
            for (int i = index; i < _len; i++) {
                newList[i + 1] = _list[i];
            }
            _list = newList;
        } else {
            for (int i = _len; i > index; i--) {
                _list[i] = _list[i-1];
            }
            _list[index] = element;
        }
        _len++;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index != -1) {
            remove(index);
            return true;
        }
        return false;
    }

    @Override
    public E set(int index, E element) {
        if (index >= _len) {
            throw new RuntimeException("Invalid index");
        }
        E oldElem = _list[index];
        _list[index] = element;
        return oldElem;
    }


    @Override
    public boolean isEmpty() {
        return _len <= 0;
    }


    @Override
    public void clear() {
        for (int to = _len, i = _len = 0; i < to; i++) {
            _list[i] = null;
        }
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < _len; i++) {
            if (Objects.equals(_list[i],o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public E get(int index) {
        if (index >= _len) {
            throw new RuntimeException("Invalid index");
        }
        return _list[index];
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = _len - 1; i >= 0; i--) {
            if (Objects.equals(o,_list[i])){
                return i;
            }
        }
        return -1;
    }


    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Опциональные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////


    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
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
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    ////////        Эти методы имплементировать необязательно    ////////////
    ////////        но они будут нужны для корректной отладки    ////////////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    @Override
    public Iterator<E> iterator() {
        return null;
    }

}
