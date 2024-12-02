package by.it.group310902.strizhevskiy.lesson09;

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
    private int capacity = 10;
    private int size = 0;
    private Object[] elements = new Object[capacity];


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(elements[i]);
            sb.append(", ");
        }
        int len = sb.length();
        if (len > 1) { sb.setLength(len-2); }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean add(E e) {
        if (size == capacity) {
            Object[] temp = new Object[capacity = 1+size*3/2];
            System.arraycopy(elements, 0, temp, 0, size);
            elements = temp;
        }
        elements[size++] = e;
        return true;
    }

    @Override
    public E remove(int index) {
        E oldE = (E) elements[index];
        System.arraycopy(elements, index+1, elements, index, size-index-1);
        elements[--size] = null;
        return oldE;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void add(int index, E element) {
        if (index > size) { return; }

        Object[] temp = elements;
        if (size == capacity) {
            temp = new Object[capacity = 1+size*3/2];
            System.arraycopy(elements, 0, temp, 0, index);
        }
        if (index < size) {
            System.arraycopy(elements, index, temp, index+1, size-index);
        }
        temp[index] = element;
        elements = temp;
        size++;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index == -1) { return false; }
        remove(index);
        return true;
    }

    @Override
    public E set(int index, E element) {
        E oldE = (E) elements[index];
        elements[index] = element;
        return oldE;
    }


    @Override
    public boolean isEmpty() {
        return size == 0;
    }


    @Override
    public void clear() {
        for (int i = 0; i < size; i++) { elements[i] = null; }
        size = 0;
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        if (o == null) {
            while (index < size && elements[index] != null) { index++; }
            return index == size ? -1 : index;
        }

        while (index < size && !o.equals(elements[index])) { index++; }
        return index == size ? -1 : index;
    }

    @Override
    public E get(int index) {
        return (E) elements[index];
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int index = size-1;
        if (o == null) {
            while (0 <= index && elements[index] != null) { index--; }
            return index;
        }

        while (0 <= index && !o.equals(elements[index])) { index--; }
        return index;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        boolean res = true;
        for (Object e : c) { res &= contains(e); }
        return res;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E e : c) { add(e); }
        return !c.isEmpty();
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        Object[] temp = elements;
        if (size+c.size() > capacity) {
            temp = new Object[capacity = c.size()+size*3/2];
            System.arraycopy(elements, 0, temp, 0, index);
        }
        if (index < size) {
            System.arraycopy(elements, index, temp, index+c.size(), size-index);
        }
        for (E e : c) { temp[index++] = e; }
        elements = temp;
        size += c.size();
        return !c.isEmpty();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        int l = 0, r = 0;
        while (r < size) {
            if (c.contains(elements[r])) { r++; continue; }
            if (l != r) { elements[l] = elements[r]; }
            l++;
            r++;
        }
        size = l;
        while (l < r) { elements[l++] = null; }
        return size != r;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        int l = 0, r = 0;
        while (r < size) {
            if (!c.contains(elements[r])) { r++; continue; }
            if (l != r) { elements[l] = elements[r]; }
            l++;
            r++;
        }
        size = l;
        while (l < r) { elements[l++] = null; }
        return size != r;
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
