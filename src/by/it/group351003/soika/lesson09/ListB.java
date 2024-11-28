package by.it.group351003.soika.lesson09;

import java.util.*;

public class ListB<E> implements List<E> {

    //Создайте аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    final int DEFAULT_CAPACITY = 10;
    private int size;
    private Object[] arr;

    ListB() {
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
    } // возвращает строковое представление списка,
    // перебирает элементы, добавляя в список разделяя запятыми

    @Override
    public boolean add(E e) {
        if (size == arr.length) {
            arr = Arrays.copyOf(arr, arr.length + (arr.length >> 1));
        }
        arr[size++] = e; //сразу эл-т будет доаблен, а потом увеличиться size
        return true;
    }
    // добавляет эл-т в конец списка. если массив заполнен,
    // size увеличивается в 1,5 раза

    @Override
    public E remove(int index) {
        E temp = (E) arr[index];
        for (int i = index + 1; i < size; i++) {
            arr[i - 1] = arr[i];
        }
        arr[--size] = null;
        return temp;
    }//удаляет элемент по индексу. сразу сохраняется удаляемы эл-т,
    // а все остальные сдвигаются влево. последний = null, размер умееньшается

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
        if (size == arr.length) {
            arr = Arrays.copyOf(arr, arr.length + (arr.length >> 1));
        }
        System.arraycopy(arr, index, arr, index + 1, size++ - index);
        arr[index] = element;
    }
    // добавляет эл-т по указаному индексу. массив заполнен => увеличивается.
    // элемеенты сдвигаются вправо, читоб осовбодить место для нового элемента

    @Override
    public boolean remove(Object o) {
        boolean hasObject = false;
        for (int i = 0; i < size && !hasObject; i++) {
            if (arr[i].equals(o)) {
                System.arraycopy(arr, i + 1, arr, i, size-- - i);
                hasObject = true;
            }
        }
        return hasObject;
    } //удаляет первое вхождение элемента о
    // перебирает массив, сдвигает все последуюзие элементы влево, уменьшая размер списка

    @Override
    public E set(int index, E element) {
        E temp = (E) arr[index];
        arr[index] = element;
        return temp;
    } //заменяет элемент по индексу index на element и возвращает старый элемент.


    @Override
    public boolean isEmpty() {
        return size == 0;
    }


    @Override
    public void clear() {
        size = 0;
        arr = new Object[DEFAULT_CAPACITY];
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (arr[i].equals(o)) {
                return i;
            }
        }
        return - 1;
    }

    @Override
    public E get(int index) {
        return (E) arr[index];
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            if (arr[i].equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) {
            if (arr[i].equals(o)) {
                return i;
            }
        }
        return - 1;
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