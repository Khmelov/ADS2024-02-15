package by.it.group310901.tit.lesson09;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.*;


public class ListC<E> implements List<E> {


    //Создайте аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ


    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////


    static final int defaultSize = 8;// Стандартный размер массива
    E[] _list;// Массив для хранения элементов списка
    int _current;// Количество текущих элементов в списке


    // Конструктор по умолчанию, инициализирует массив с стандартным размером
    public ListC() {
        this(defaultSize);
    }


    // Конструктор, позволяющий указать пользовательский размер массива
    public ListC(int size) {
        _list = (E[]) new Object[size];
    }




    // Метод для строкового представления списка
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < _current; i++) {
            sb.append(_list[i]);
            if (i < _current - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }


    // Метод для добавления элемента в конец списка
    @Override
    public boolean add(E e) {
        if (_current == _list.length) {
            E[] newList = (E[]) new Object[_list.length * 2];
            for (int i = 0; i < _list.length; i++) {
                newList[i] = _list[i];
            }
            _list = newList;
        }
        _list[_current++] = e;
        return true;
    }


    // Метод для удаления элемента по индексу
    @Override
    public E remove(int index) {
        if (index > -1 && index < _current) {
            E elem = _list[index];
            for (int i = index; i < _current - 1; i++)
                _list[i] = _list[i + 1];
            _current--;
            return elem;
        }
        return null;
    }


    // Метод для получения текущего количества элементов в списке
    @Override
    public int size() {
        return _current;
    }


    // Метод для добавления элемента по заданному индексу
    @Override
    public void add(int index, E element) {
        if (_current == _list.length) {
            E[] newList = (E[]) new Object[_list.length * 2];
            for (int i = 0; i < _list.length; i++) {
                newList[i] = _list[i];
            }
            _list = newList;
        }
        if (index > -1 && index <= _current) {
            for (int i = _current; i > index; i--) {
                _list[i] = _list[i - 1];
            }
            _list[index] = element;
            _current++;
        }
    }


    // Метод для удаления первого вхождения элемента
    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index != -1) {
            remove(index);
            return true;
        }
        return false;
    }


    // Метод для замены элемента по индексу
    @Override
    public E set(int index, E element) {
        E item = null;
        if (index > -1 && index < _current) {
            item = _list[index];
            _list[index] = element;
        }
        return item;
    }


    // Метод для проверки, пуст ли список
    @Override
    public boolean isEmpty() {
        return _current == 0;
    }


    // Метод для очистки списка
    @Override
    public void clear() {
        for (int i = 0; i < _current; i++) {
            _list[i] = null;
        }
        _current = 0;
    }


    // Метод для поиска индекса первого вхождения элемента
    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < _current; i++) {
            if (Objects.equals(_list[i], o)) {
                return i;
            }
        }
        return -1;
    }


    // Метод для получения элемента по индексу
    @Override
    public E get(int index) {
        if (index > -1 && index < _current)
            return _list[index];
        return null;
    }


    // Метод для проверки, содержится ли элемент в списке
    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }


    // Метод для поиска индекса последнего вхождения элемента
    @Override
    public int lastIndexOf(Object o) {
        for (int i = _current - 1; i > -1; i--) {
            if (Objects.equals(o, _list[i]))
                return i;
        }
        return -1;
    }


    // Метод для проверки, содержатся ли все элементы из коллекции в списке
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object item : c)
            if (!contains(item))
                return false;
        return true;
    }


    // Метод для добавления всех элементов из коллекции в список
    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c.isEmpty())
            return false;
        for (E item : c)
            add(item);
        return true;
    }


    // Метод для добавления всех элементов из коллекции по заданному индексу
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        boolean modified = false;
        for (E item : c) {
            if (index > -1 && index <= _current) {
                add(index, item);
                index++;
                modified = true;
            }
        }


        return modified;
    }


    // Метод для удаления всех элементов из коллекции из списка
    @Override
    public boolean removeAll(Collection<?> c) {
        boolean deleted = false;


        for (int i = 0; i < _current; i++) {
            if (c.contains(_list[i])) {
                remove(i);
                i--;
                deleted = true;
            }
        }
        return deleted;
    }


    // Метод для удаления всех элементов, которые не содержатся в коллекции
    public boolean retainAll(Collection<?> c) {
        boolean retained = false;


        for (int i = 0; i < _current; i++) {
            if (!c.contains(_list[i])) {
                remove(i);
                i--;
                retained = true;
            }
        }
        return retained;
    }


    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Опциональные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////


    // Метод для получения подсписка
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > _current || fromIndex > toIndex) {
            ListC<E> subList = new ListC<>(toIndex - fromIndex);
            for (int i = fromIndex; i < toIndex; i++)
                subList.add(_list[i]);
            return subList;
        }
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
        Object[] result = new Object[_current];
        for (int i = 0; i < _current; i++) {
            result[i] = _list[i];
        }
        return result;
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
