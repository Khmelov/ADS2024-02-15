package by.it.group351001.pavlov.lesson09;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListC<E> implements List<E> {
    private static int incrementSize = 10;
    private int currSize = 0;
    private E[] mas = (E[])new Object[0];

    //Создайте аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < currSize; i++) {
            str.append(mas[i] + ", ");
        }

        String ans = str.toString();

        if (ans.length() > 0)
        {
            ans = ans.substring(0,ans.length() - 2);
        }

        return "[" + ans + "]";
    }

    @Override
    public boolean add(E e) {
        int index = currSize;
        if (currSize == mas.length)
        {
            var tempMas = mas;
            mas = (E[]) new  Object[currSize + incrementSize];

            for (int i = 0; i < currSize; i++) {
                mas[i] = tempMas[i];
            }

        }

        mas[index] = e;
        currSize++;

        return 1 == 1;
    }

    @Override
    public E remove(int index) {
        if (index >= currSize || index < 0) return null;

        var tempMas = mas;

        E result = mas[index];

        for (int i = index + 1; i < currSize; i++) {
            mas[i - 1] = tempMas[i];
        }

        currSize--;
        return  result;
    }

    @Override
    public int size() {
        return currSize;
    }

    @Override
    public void add(int index, E element) {
        if (index < 0 || index > currSize) {
            return;
        }

        if (currSize == mas.length) {
            E[] tempElements = (E[]) new Object[mas.length * 2];

            for (int i = 0; i < mas.length; i++) {
                tempElements[i] = mas[i];
            }

            mas = tempElements;
        }

        for (int i = currSize; i > index; i--) {
            mas[i] = mas[i - 1];
        }

        mas[index] = element;
        currSize++;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < currSize; i++) {
            if (o.equals(mas[i])) {
                E deletedItem = mas[i];

                for (int j = i; j < currSize; j++) {
                    mas[j] = mas[j + 1];
                }

                currSize--;
                return true;
            }
        }
        return false;
    }

    @Override
    public E set(int index, E element) {
        if (index < 0 || index >= currSize) {
            return null;
        }

        E oldElem = mas[index];
        mas[index] = element;
        return oldElem;
    }


    @Override
    public boolean isEmpty() {
        return currSize == 0;
    }


    @Override
    public void clear() {

        currSize = 0;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < currSize; i++) {
            if (o.equals(mas[i])) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= currSize) {
            return null;
        }

        return mas[index];
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < currSize; i++) {
            if (o.equals(mas[i])) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i =currSize - 1; i >= 0; i--) {
            if (o.equals(mas[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object elem: c) {
            if (!contains(elem)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (Object elem: c) {
            add((E) elem);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        for (Object elem: c) {
            add(index, (E) elem);
            index++;
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {

        boolean deletedElem = false;

        for (int i = 0; i < currSize; i++) {
            if (c.contains(mas[i])) {
                remove(i);
                i--;
                deletedElem = true;
            }
        }
        return deletedElem;
    }

    @Override
    public boolean retainAll(Collection<?> c) {

        boolean deletedElem = false;

        for (int i = 0; i < currSize; i++) {
            if (!c.contains(mas[i])) {
                remove(i);
                i--;
                deletedElem = true;
            }
        }
        return deletedElem;
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
