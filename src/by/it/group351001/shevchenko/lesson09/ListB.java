package by.it.group351001.shevchenko.lesson09;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListB<E> implements List<E> {

    //Создайте аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ

    static final int defaultSize = 10;
    private int currentSize = 0;
    private E[] elements;

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    public ListB() {
        this(defaultSize);
    }

    public ListB(int size) {
        elements = (E[]) new Object[size];
    }

    @Override
    public String toString() {
        StringBuilder newString = new StringBuilder();

        newString.append('[');
        for (int i = 0; i < currentSize; i++) {
            newString.append(elements[i]);
            if (i != currentSize - 1) {
                newString.append(", ");
            }
        }

        newString.append(']');

        return newString.toString();
    }

    @Override
    public boolean add(E e) {
        if (currentSize == elements.length) {
            E[] newElements = (E[]) new Object[elements.length * 2];
            System.arraycopy(elements, 0, newElements, 0, currentSize);
            elements = newElements;
        }

        elements[currentSize++] = e;

        return true;
    }

    @Override
    public E remove(int index) {
        if ((index < 0) || (index >= currentSize)) {
            return null;
        }

        E removedElement = elements[index];

        for (int i = index; i < currentSize; i++) {
            elements[i] = elements[i + 1];
        }

        currentSize--;

        return removedElement;
    }

    @Override
    public int size() {
        return currentSize;
    }

    @Override
    public void add(int index, E element) {
        if ((index < 0) || (index >= currentSize)) {
            return;
        }

        if (currentSize == elements.length) {
            E[] newElements = (E[]) new Object[elements.length * 2];

            System.arraycopy(elements, 0, newElements, 0, currentSize);
            elements = newElements;
        }

        currentSize++;
        for (int i = currentSize - 1; i > index; i--) {
            elements[i] = elements[i - 1];
        }

        elements[index] = element;
    }

    @Override
    public boolean remove(Object o) {

        for (int i = 0; i < currentSize; i++) {
            if (o.equals(elements[i])) {
                this.remove(i);
                return true;
            }
        }

        return false;
    }

    @Override
    public E set(int index, E element) {
        if ((index < 0) || (index >= currentSize)) {
            return null;
        }

        E setElement = elements[index];
        elements[index] = element;

        return setElement;

    }


    @Override
    public boolean isEmpty() {
        return (currentSize == 0);
    }


    @Override
    public void clear() {
        elements = (E[]) new Object[defaultSize];

        currentSize = 0;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < currentSize; i++) {
            if (o.equals(elements[i])) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public E get(int index) {
        if ((index < 0) || (index >= currentSize)) {
            return null;
        }

        return elements[index];
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < currentSize; i++) {
            if (o.equals(elements[i])) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = currentSize - 1; i >=0; i--) {
            if (o.equals(elements[i])) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object element: c) {
            if (!contains(element)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean flag = false;

        for (Object element: c) {
            add((E) element);
            flag = true;
        }

        return flag;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        boolean flag = false;

        for (Object element: c) {
            add(index, (E) element);
            index++;
            flag = true;
        }

        return flag;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean flag = false;

        for (int i = 0; i < currentSize; i++) {
            if (c.contains(elements[i])) {
                remove(i);
                i--;
                flag = true;
            }
        }

        return flag;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean flag = false;

        for (int i = 0; i < currentSize; i++) {
            if (!c.contains(elements[i])) {
                remove(i);
                i--;
                flag = true;
            }
        }

        return flag;
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
