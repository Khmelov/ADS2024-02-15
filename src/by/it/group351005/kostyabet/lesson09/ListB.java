package by.it.group351005.kostyabet.lesson09;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListB<E> implements List<E> {

    //Создайте аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    static final int defSize = 8;
    E[] _list;
    int _curItem = 0;
    public ListB() {
        this(defSize);
    }
    public ListB(int size)
    {
        _list = (E[]) new Object[size];
    }
    @Override
    public String toString() {
        StringBuilder sb= new StringBuilder();
        sb.append("[");
        for (int i = 0; i < _curItem; ++i)
        {
            E curSym = _list[i];
            sb.append(curSym);
            if (i < _curItem - 1)
            {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
    @Override
    public boolean add(E e) {
        if (_curItem >= _list.length)
        {
            E[] _cList = (E[]) new Object[_list.length * 2];
            for (int i = 0; i < _list.length; ++i)
            {
                _cList[i] = _list[i];
            }
            _list = _cList;
        }
        _list[_curItem] = e;
        _curItem++;
        return true;
    }
    @Override
    public E remove(int index) {
        if (index < 0 || index >= _curItem)
        {
            return null;
        }

        E _rItem = _list[index];

        for (int i = index; i < _curItem - 1; ++i)
        {
            _list[i] = _list[i + 1];
        }

        _curItem--;

        return _rItem;
    }
    @Override
    public int size() {
        return _curItem;
    }
    @Override
    public void add(int index, E element) {
        if (index < 0 || index > _curItem)
        {
            return;
        }

        if (_curItem >= _list.length)
        {
            E[] _cList = (E[]) new Object[_list.length * 2];
            for (int i = 0; i < _list.length; ++i)
            {
                _cList[i] = _list[i];
            }
            _list = _cList;
        }

        for (int i = _curItem; i > index; i--)
        {
            _list[i] = _list[i - 1];
        }

        _list[index] = element;
        _curItem++;
    }
    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < _curItem; ++i)
        {
            if (o.equals(_list[i])) {
                E rItem = _list[i];

                for (int j = i; j < _curItem; ++j) {
                    _list[j] = _list[j + 1];
                }
                _curItem--;

                return true;
            }
        }

        return false;
    }
    @Override
    public E set(int index, E element) {
        if (index < 0 || index > _curItem)
        {
            return null;
        }

        E _setItem = _list[index];
        _list[index] = element;
        return _setItem;
    }
    @Override
    public boolean isEmpty() {
        return _curItem == 0;
    }
    @Override
    public void clear() {
        _list = (E[]) new Object[defSize];
        _curItem = 0;
    }
    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < _curItem; ++i)
        {
            if (o.equals(_list[i]))
            {
                return i;
            }
        }

        return -1;
    }
    @Override
    public E get(int index) {
        if (index < 0 || index >= _curItem)
        {
            return null;
        }

        return _list[index];
    }
    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < _curItem; ++i) {
            if (o.equals(_list[i]))
            {
                return true;
            }
        }

        return false;
    }
    @Override
    public int lastIndexOf(Object o) {
        for (int i = _curItem - 1; i >= 0; --i)
        {
            if (o.equals(_list[i]))
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
