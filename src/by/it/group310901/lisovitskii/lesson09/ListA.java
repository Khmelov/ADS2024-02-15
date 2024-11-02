package by.it.group310901.lisovitskii.lesson09;

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
    private final int SIZE = 8;
    private final int CUT = 4;
    private E[] arr = (E[]) new Object[SIZE];//добавляет элемент
    // в массив, увеличивая размер массива, если он заполнен.
    private int k = 0;

    private void resize(int newSize) {
        E[] newArr = (E[]) new Object[newSize];
        System.arraycopy(arr, 0, newArr, 0, k);
        arr = newArr;
    }

    @Override
    public String toString() {// предстоваляет ст строкове п
        // показывает все элементы в виде строки
        String s = "[";
        for (int i = 0; i < k; i++) {
            if (i < k - 1)
                s += arr[i].toString() + ", ";
            else
                s += arr[i].toString();
        }
        s += "]";
        return s;
    }

    @Override
    public boolean add(E e) {
        try {
            if (k == arr.length - 1) {
                resize(arr.length*2);
            }
            arr[k++] = e;
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    @Override
    public E remove(int index) {//удаляет элемент по индексу, смещая оставшиеся элементы влево
        // и при необходимости уменьшает размер массива, если он становится слишком пустым.
        E tmp = arr[index];
        for (int i = index; i < k; i++) {
            arr[i] = arr[i + 1];
        }
        arr[k] = null;
        k--;
        if (arr.length > SIZE && k < arr.length / CUT) {
            resize(arr.length / 2);
        }
        return tmp;
    }

    @Override
    public int size() {
        return k;
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
