package by.it.group351001.pavlyuchenko.lesson09;

import java.util.*;

public class ListB<E> implements List<E> {


    //Создайте аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    private int currSize = 0;
    private E[] arr = (E[]) new Object[currSize];
    @Override
    public String toString() {
        StringBuilder newString = new StringBuilder();

        newString.append('[');
        for (int i = 0; i < currSize; i++) {
            newString.append(arr[i]);
            if (i != currSize - 1) {
                newString.append(", ");
            }
        }

        newString.append(']');

        return newString.toString();
    }

    @Override
    public boolean add(E e) {
        if (arr.length == currSize) {
            E[] newArr = (E[]) new Object[(currSize << 1) + 1];
            System.arraycopy(arr,0,newArr,0, currSize);
            arr = newArr;
        }
        arr[currSize++] = e;
        return true;
    }

    @Override
    public E remove(int index) {
        if ((index < 0) || (index >= currSize)) {
            return null;
        }
        E removed = arr[index];
        for (int i = index; i < currSize - 1;i++) {
            arr[i] = arr[i + 1];
        }
        currSize--;
        return removed;

    }

    @Override
    public int size() {
        return currSize;
    }

    @Override
    public void add(int index, E element) {
        int i;
        if ((index < 0) || (index >= currSize)) {
            return;
        }
        if (arr.length == currSize) {
            E[] newArr = (E[]) new Object[(currSize << 1) + 1];
            System.arraycopy(arr,0,newArr,0, currSize);
            arr = newArr;
        }
        currSize++;
        for (i = currSize - 1; i > index; i--) {
            arr[i] = arr[i - 1];
        }
        arr[i] = element;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < currSize; i++) {
            if (o.equals(arr[i])) {
                this.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public E set(int index, E element) {
        if ((index < 0) || (index >= currSize)) {
            return null;
        }
        E prevElement = arr[index];
        arr[index] = element;
        return prevElement;
    }


    @Override
    public boolean isEmpty() {
        return (currSize == 0);
    }


    @Override
    public void clear() {
        arr = (E[]) new Object[0];
        currSize = 0;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < currSize; i++) {
            if (o.equals(arr[i])) {
                return i;
            }
        }
        return -1;

    }

    @Override
    public E get(int index) {
        return ((index >= 0) && (index < currSize)) ? arr[index]: null;

    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < currSize; i++) {
            if (o.equals(arr[i])) {
                return true;
            }
        }
        return false;

    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = currSize - 1; i >= 0; i--) {
            if (o.equals(arr[i])) {
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
