package by.it.group351005.vaveyko.lesson09;

import java.util.*;

public class ListB<E> implements List<E> {


    //Создайте аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ

    E[] arr;

    static int defaultSize = 10;
    int currLen = 0;
    public ListB(){
        this(defaultSize);
    }
    public ListB(int size){
        arr = (E[]) new Object[size];
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    @Override
    public String toString() {
        String line = "[";

        for (int i = 0; i < currLen; i++) {
            line += arr[i].toString();
            if (i < currLen-1) {
                line += ", ";
            }
        }

        line += "]";
        return line;
    }

    @Override
    public boolean add(E e) {
        if (currLen == arr.length) {
            E[] buff = (E[]) new Object[currLen * 2];
            for (int i = 0; i < currLen; i++) {
                buff[i] = arr[i];
            }
            arr = buff;
        }
        arr[currLen++] = e;
        return true;
    }

    @Override
    public E remove(int index) {
        if (index >= 0 && index < currLen) {
            E buff = arr[index];
            for (int i = index; i < currLen-1; i++) {
                arr[i] = arr[i+1];
            }
            arr[--currLen] = null;
            return buff;
        }

        return null;
    }

    @Override
    public int size() {
        return currLen;
    }

    @Override
    public void add(int index, E element) {
        if (index >= 0 && index < currLen) {

            if (currLen == arr.length) {
                E[] buff = (E[]) new Object[currLen * 2];
                for (int i = 0; i < currLen; i++) {
                    buff[i] = arr[i];
                }
                arr = buff;
            }

            E buffElem = arr[index];
            for (int i = currLen; i > index; i--) {
                arr[i] = arr[i-1];
            }
            arr[index] = element;
            currLen++;
        }
    }

    @Override
    public boolean remove(Object o) {
        int i = 0;
        while (i < currLen && !Objects.equals(arr[i], o)) {
            i++;
        }
        if (i < currLen) {
            this.remove(i);
        }
        return i != currLen;
    }

    @Override
    public E set(int index, E element) {
        if (index >= 0 && index < currLen) {
            E buff = arr[index];
            arr[index] = element;
            return buff;
        }
        return null;
    }


    @Override
    public boolean isEmpty() {
        return currLen == 0;
    }


    @Override
    public void clear() {
        for (int i = 0; i < currLen; i++) {
            arr[i] = null;
        }
        arr = (E[]) new Object[defaultSize];
        currLen = 0;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < currLen; i++) {
            if (Objects.equals(arr[i], o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public E get(int index) {
        if (index >= 0 && index < currLen) {
            return arr[index];
        }
        return null;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < currLen; i++) {
            if (Objects.equals(arr[i], o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = currLen-1; i >= 0; i--) {
            if (Objects.equals(arr[i], o)) {
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
