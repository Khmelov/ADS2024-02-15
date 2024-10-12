package by.it.group310902.pashkovich.lesson09;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListB<E> implements List<E> {

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
        elements[curInd++] = e;
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
        elements[--curInd] = null; // Освобождаем последнюю ячейку
        return deletedElem;
    }

    @Override
    public int size() {
        return curInd;
    }

    @Override
    public void add(int index, E element) {
        if (index < 0 || index > curInd) {
            throw new IndexOutOfBoundsException();
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
        int index = indexOf(o);
        if (index == -1) return false;
        remove(index);
        return true;
    }

    @Override
    public E set(int index, E element) {
        if (index < 0 || index >= curInd) {
            throw new IndexOutOfBoundsException();
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
            throw new IndexOutOfBoundsException();
        }
        return elements[index];
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
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
        for (Object elem : c) {
            if (!contains(elem)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E e : c) {
            add(e);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (index < 0 || index > curInd) {
            throw new IndexOutOfBoundsException();
        }
        for (E e : c) {
            add(index++, e);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object elem : c) {
            while (contains(elem)) {
                remove(elem);
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
                i--; // Компенсируем удаление
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
        ListB<E> sublist = new ListB<>(toIndex - fromIndex);
        for (int i = fromIndex; i < toIndex; i++) {
            sublist.add(elements[i]);
        }
        return sublist;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < curInd) {
            return (T[]) java.util.Arrays.copyOf(elements, curInd, a.getClass());
        }
        System.arraycopy(elements, 0, a, 0, curInd);
        if (a.length > curInd) {
            a[curInd] = null;
        }
        return a;
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[curInd];
        System.arraycopy(elements, 0, result, 0, curInd);
        return result;
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
            private int cursor = 0;

            @Override
            public boolean hasNext() {
                return cursor < curInd;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new java.util.NoSuchElementException();
                }
                return elements[cursor++];
            }
        };
    }
}
