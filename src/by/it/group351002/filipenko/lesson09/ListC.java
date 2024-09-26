package by.it.group351002.filipenko.lesson09;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListC<E> implements List<E> {

    //Создайте аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    private static int capacity = 16;
    private int length = 0;
    E[] array;

    public ListC(int capacity) {
        array = (E[]) new Object[capacity];
        this.capacity = capacity;
    }

    public ListC() {
        this(capacity);
    }

    @Override
    public String toString() {
        StringBuilder strbldr = new StringBuilder();
        strbldr.append("[");

        int i = 0;
        while (i < length) {
            strbldr.append(array[i] + ", ");
            i++;
        }

        int sbLength = strbldr.length();
        if (sbLength > 1)
            strbldr.delete(sbLength - 2, sbLength);
        strbldr.append("]");

        return strbldr.toString();
    }

    @Override
    public boolean add(E e) {
        length++;

        if (length >= capacity) {
            E[] tempElem = (E[]) new Object[capacity += 16];

            for (int i = 0; i < length - 1; i++)
                tempElem[i] = array[i];

            array = tempElem;
        }
        array[length - 1] = e;

        return true;
    }

    @Override
    public E remove(int index) {
        if (index > length || index < 0)
            return null;

        else {
            E element = array[index];

            length--;

            for (int i = index; i < length; i++)
                array[i] = array[i + 1];

            return element;
        }
    }

    @Override
    public int size() {
        return length;
    }


    @Override
    public void add(int index, E element) {
        length++;

        if (length >= capacity) {
            E[] tempElem = (E[]) new Object[capacity += 16];

            for (int i = 0; i < length; i++)
                tempElem[i] = array[i];

            array = tempElem;
        }
        for (int i = length - 1; i > index; i--)
            array[i] = array[i - 1];

        array[index] = element;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < length; i++)
            if (array[i].equals(o)) {
                length--;

                for (int j = i; j < length; j++)
                    array[j] = array[j + 1];

                return true;
            }
        return false;
    }

    @Override
    public E set(int index, E element) {
        if (index > length || index < 0)
            return null;

        else {
            E newElement = array[index];
            array[index] = element;

            return newElement;
        }
    }


    @Override
    public boolean isEmpty() {
        if (length == 0)
            return true;
        return false;
    }

    @Override
    public void clear() {
        capacity =  16;
        array = (E[]) new Object[capacity];
        length = 0;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < length; i++)
            if (array[i].equals(o))
                return i;

        return -1;
    }

    @Override
    public E get(int index) {
        if (index > length || index < 0)
            return null;
        else
            return array[index];
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < length; i++)
            if (array[i].equals(o))
                return true;
        return false;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = length - 1; i >= 0; i--)
            if (array[i].equals(o))
                return i;
        return -1;
    }

    @Override
    public boolean containsAll(Collection<?> c)
    {
        for (Object element : c)
            if (contains(element) == false)
                return false;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E element : c)
            add(element);
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        for (E element : c) {
            add(index, element);
            index++;
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        for (Object element : c)
            while (remove(element));

        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        int i = 0;
        while (i < length) {
            if (c.contains(array[i]) == false) {
                remove(array[i]);
                continue;
            }
            i++;
        }
        return true;
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Опциональные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

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
