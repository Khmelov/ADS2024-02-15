package by.it.group351004.kuchko.lesson09;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListA<E> implements List<E> {

    //Создайте аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    static final int defaultSize = 8;
    E[] my_list;
    int curr_size;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < curr_size; i++) {
            sb.append(my_list[i]);
            if (i < curr_size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
    public ListA () {
        this(defaultSize);
    }

    public ListA(int size) {
        my_list = (E[]) new Object[size];
    }
    @Override
    public boolean add(E e) {
        if (curr_size == my_list.length) {
            E[] newList = (E[]) new Object[my_list.length * 2];
            for (int i = 0; i < my_list.length; i++) {
                newList[i] = my_list[i];
            }
            my_list = newList;
        }
        my_list[curr_size++] = e;
        return true;
    }

    @Override
    public E remove(int index) {
        if (index > -1 && index < curr_size) {
            E elem = my_list[index];
            for (int i = index; i < curr_size - 1; i++)
                my_list[i] = my_list[i + 1];
            curr_size--;
            return elem;
        }
        return null;
    }

    @Override
    public int size() {
        return curr_size;
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Опциональные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    @Override
    public void add(int index, E element) {

    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public E set(int index, E element) {
        return null;
    }


    @Override
    public boolean isEmpty() {
        return false;
    }


    @Override
    public void clear() {

    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
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
