package by.it.group351004.shkredova.lesson09;

import java.util.*;

public class ListA<E> implements List<E> {

    private E[]array  = (E[]) new Object[]{};

    private int size = 0;

    /*Вывести список*/
    @Override
    public String toString() {

        String result = "[";

        for (int i = 0; i < size ; i++) {
            result += array[i];

            if (size -1 != i)
                result += ", ";
        }

        result += "]";

        return result;
    }

    /*Добавить элемент и изменить размерность списка по необходимости*/
    @Override
    public boolean add(E e) {

        if(size == array.length)
            array = Arrays.copyOf(array, size * 3 + 1);

        array[size++] = e;

        return true;
    }

    /*Удалить элемент по индексу*/
    @Override
    public E remove(int index) {

        E del = array[index];

        System.arraycopy(array, index+1,array,index,size-1-index);

        size--;

        return del;
    }

    @Override
    public int size() {
        return size;
    }

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

    @Override
    public Iterator<E> iterator() {
        return null;
    }

}