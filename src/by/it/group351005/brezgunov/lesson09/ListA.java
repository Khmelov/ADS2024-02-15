package by.it.group351005.brezgunov.lesson09;

import java.util.*;

public class ListA<E> implements List<E> {

    //Создайте аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    final int DEFAULT_CAPACITY = 10;
    private int size;
    private Object[] arr;

    ListA() {
        this.arr = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        StringBuilder stringBuilder = new StringBuilder("[");
        for (int i = 0; i < size - 1; i++) {
            stringBuilder.append(arr[i].toString()).append(", ");
        }
        stringBuilder.append(arr[size - 1].toString()).append("]");
        return stringBuilder.toString();
    }

    @Override
    public boolean add(E e) {
        if (size == arr.length) {
            arr = Arrays.copyOf(arr, arr.length + (arr.length >> 1));
        }
        arr[size++] = e;
        return true;
    }

    @Override
    public E remove(int index) {
        E temp = (E) arr[index];
        for (int i = index + 1; i < size; i++) {
            arr[i - 1] = arr[i];
        }
        arr[--size] = null;
        return temp;
    }

    @Override
    public int size() {
        return size;
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
