package by.it.group310901.sudakov.lesson09;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListB<E> implements List<E> {


    //Создайте аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    E[] elements;
    int curInd = 0;
    static int size = 8;

    public ListB() {
        this(size);
    }

    public ListB(int size) {
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
            E[] tempElements = (E[]) new Object[elements.length * 2];

            for (int i = 0; i < elements.length; i++) {
                tempElements[i] = elements[i];
            }

            elements = tempElements;
        }

        elements[curInd] = e;
        curInd++;
        return true;
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


    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Опциональные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////


    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object item : c) {
            if (!contains(item)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E element : c) {
            add(element);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (index < 0 || index > curInd) {
            throw new IndexOutOfBoundsException();
        }

        for (E element : c) {
            add(index++, element);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object element : c) {
            while (contains(element)) {
                remove(element);
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        for (int i = 0; i < curInd; i++) {
            if (!c.contains(elements[i])) {
                remove(elements[i]);
                i--;  // Уменьшаем индекс, чтобы не пропустить элемент из-за сдвига
                modified = true;
            }
        }
        return modified;
    }


    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > curInd || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException();
        }

        ListB<E> subList = new ListB<>(toIndex - fromIndex);
        for (int i = fromIndex; i < toIndex; i++) {
            subList.add(elements[i]);
        }
        return subList;
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
        return new Iterator<E>() {
            int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < curInd;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new IndexOutOfBoundsException();
                }
                return elements[currentIndex++];
            }
        };
    }

}
