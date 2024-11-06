package by.it.group351003.kolbeko.lesson09;

import java.util.*;

public class ListB<E> implements List<E> {


    //Создайте аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    static final int INIT_SIZE = 10;
    E[] list;
    int curItem = 0;
    public ListB() {
        this(INIT_SIZE);
    }
    public ListB(int size)
    {
        list = (E[]) new Object[size];
    }

    @Override
    public String toString() {
        StringBuilder sb= new StringBuilder();
        sb.append("[");
        for (int i = 0; i < curItem; ++i)
        {
            E curSym = list[i];
            sb.append(curSym);
            if (i < curItem - 1)
            {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
    @Override
    public boolean add(E e) {
        if (curItem >= list.length)
        {
            E[] _cList = (E[]) new Object[list.length * 2];
            for (int i = 0; i < list.length; ++i)
            {
                _cList[i] = list[i];
            }
            list = _cList;
        }
        list[curItem] = e;
        curItem++;
        return true;
    }
    @Override
    public E remove(int index) {
        if (index < 0 || index >= curItem)
        {
            return null;
        }

        E _rItem = list[index];

        for (int i = index; i < curItem - 1; ++i)
        {
            list[i] = list[i + 1];
        }

        curItem--;

        return _rItem;
    }
    @Override
    public int size() {
        return curItem;
    }
    @Override
    public void add(int index, E element) {
        if (index < 0 || index > curItem)
        {
            return;
        }

        if (curItem >= list.length)
        {
            E[] _cList = (E[]) new Object[list.length * 2];
            for (int i = 0; i < list.length; ++i)
            {
                _cList[i] = list[i];
            }
            list = _cList;
        }

        for (int i = curItem; i > index; i--)
        {
            list[i] = list[i - 1];
        }

        list[index] = element;
        curItem++;
    }
    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < curItem; ++i)
        {
            if (o.equals(list[i])) {
                E rItem = list[i];

                for (int j = i; j < curItem; ++j) {
                    list[j] = list[j + 1];
                }
                curItem--;

                return true;
            }
        }

        return false;
    }
    @Override
    public E set(int index, E element) {
        if (index < 0 || index > curItem)
        {
            return null;
        }

        E _setItem = list[index];
        list[index] = element;
        return _setItem;
    }
    @Override
    public boolean isEmpty() {
        return curItem == 0;
    }
    @Override
    public void clear() {
        list = (E[]) new Object[INIT_SIZE];
        curItem = 0;
    }
    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < curItem; ++i)
        {
            if (o.equals(list[i]))
            {
                return i;
            }
        }

        return -1;
    }
    @Override
    public E get(int index) {
        if (index < 0 || index >= curItem)
        {
            return null;
        }

        return list[index];
    }
    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < curItem; ++i) {
            if (o.equals(list[i]))
            {
                return true;
            }
        }

        return false;
    }
    @Override
    public int lastIndexOf(Object o) {
        for (int i = curItem - 1; i >= 0; --i)
        {
            if (o.equals(list[i]))
            {
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
