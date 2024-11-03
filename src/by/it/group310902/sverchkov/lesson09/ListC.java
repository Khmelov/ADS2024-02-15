package by.it.group310902.sverchkov.lesson09;

import java.util.*;

public class ListC<E> implements List<E> {

    //Создайте аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    E[] elements;
    int curInd = 0;
    static int size = 8;

    public ListC() {
        this(size);
    }

    public ListC(int size) {
        elements = (E[]) new Object[size];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < curInd; i++) {
            sb.append(elements[i]);

            if (i < curInd - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
        return sb.toString();
    }

    @Override
    public boolean add(E e) {
        if (curInd == elements.length) {
            growArray();
        }

        elements[curInd] = e;
        curInd++;
        return true;
    }
    private void growArray() {
        E[] tempElements = (E[]) new Object[elements.length * 2];
        for (int i = 0; i < elements.length; i++) {
            tempElements[i] = elements[i];
        }
        elements = tempElements;
    }
    @Override
    public E remove(int index) {
        if (index < 0 || index >= curInd) {
            return null;
        }

        E deletedElem = elements[index];
        for (int i = index; i < curInd - 1; i++) {
            elements[i] = elements[i + 1];
        }

        curInd--;
        return deletedElem;
    }

    @Override
    public int size() {
        return curInd;
    }

    @Override
    public void add(int index, E element) {
        if (index < 0 || index > curInd) {
            return;
        }

        if (curInd == elements.length) {
            E[] tempElements = (E[]) new Object[elements.length * 2];

            for (int i = 0; i < elements.length; i++) {
                tempElements[i] = elements[i];
            }

            elements = tempElements;
        }

        for (int i = curInd; i > index; i--) {
            elements[i] = elements[i - 1];
        }

        elements[index] = element;
        curInd++;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < curInd; i++) {
            if (o.equals(elements[i])) {
                E deletedItem = elements[i];

                for (int j = i; j < curInd; j++) {
                    elements[j] = elements[j + 1];
                }

                curInd--;
                return true;
            }
        }
        return false;
    }

    @Override
    public E set(int index, E element) {
        if (index < 0 || index >= curInd) {
            return null;
        }

        E oldElem = elements[index];
        elements[index] = element;
        return oldElem;
    }


    @Override
    public boolean isEmpty() {
        return curInd == 0;
    }


    @Override
    public void clear() {
        elements = (E[]) new Object[size];
        curInd = 0;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < curInd; i++) {
            if (o.equals(elements[i])) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= curInd) {
            return null;
        }

        return elements[index];
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < curInd; i++) {
            if (o.equals(elements[i])) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = curInd - 1; i >= 0; i--) {
            if (o.equals(elements[i])) {
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
        while (c.size() >= elements.length - curInd) {
            growArray();
        }
        boolean modifed = false;
        for (E element : c) {
            add(element);
            modifed = true;
        }
        return modifed;
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

        for (int i = 0; i < curInd; i++) {
            if (c.contains(elements[i])) {
                remove(i);
                i--;
                deletedElem = true;
            }
        }
        return deletedElem;
    }

    @Override
    public boolean retainAll(Collection<?> c) {

        boolean b = false;
        for (int i = 0; i < curInd; i++) {
            if (!c.contains(elements[i])) {
                remove(i);
                i--;
                b = true;
            }
        }
        return b;
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Опциональные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > curInd) {
            throw new IndexOutOfBoundsException();
        }
        if (fromIndex > toIndex) throw new IllegalArgumentException();
        ListA<E> subList = new ListA<>();
        for (int i = fromIndex; i < toIndex; i++) {
            subList.add(elements[i]);
        }
        return subList;
    }

    private class ListItr implements ListIterator<E> {
        int cursor;
        int lastRet;
        ListItr() {
        }

        ListItr(int i) {
            cursor = i;
        }

        @Override
        public boolean hasNext() {
            return cursor != size();
        }

        @Override
        public E next() {
            if (cursor >= elements.length)
                throw new NoSuchElementException();
            cursor++;
            return elements[lastRet = cursor - 1];
        }

        @Override
        public boolean hasPrevious() {
            return cursor != 0;
        }

        @Override
        public E previous() {
            if (cursor <= 0 || cursor >= elements.length)
                throw new NoSuchElementException();
            return elements[--cursor];
        }

        @Override
        public int nextIndex() {
            return cursor;
        }

        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        @Override
        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            ListC.this.remove(lastRet);
            cursor = lastRet;
            lastRet = -1;
        }

        @Override
        public void set(E e) {
            ListC.this.set(cursor, e);
        }

        @Override
        public void add(E e) {
            ListC.this.add(cursor++, e);
        }
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new ListItr(index);
    }

    @Override
    public ListIterator<E> listIterator() {
        return new ListItr();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return (T[]) Arrays.copyOfRange(elements, 0, curInd, a.getClass());
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOfRange(elements, 0, curInd);
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    ////////        Эти методы имплементировать необязательно    ////////////
    ////////        но они будут нужны для корректной отладки    ////////////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    @Override
    public Iterator<E> iterator() {
        return new ListItr();
    }

}
