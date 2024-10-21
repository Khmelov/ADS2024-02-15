package by.it.group310901.baradzin.lesson09;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

// Аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ
public class ListB<E> implements List<E> {
    static final int defaultSize = 8;
    static final int extendStep = 4;
    static final int shrinkStep = 4;

    E[] elements;
    int length = 0;

    public ListB() {
        this(defaultSize);
    }

    @SuppressWarnings("unchecked")
    public ListB(int size) {
        elements = (E[]) new Object[size];
    }

    @Override
    public String toString() {
        var sb = new StringBuilder("[");

        for (int i = 0; i < length - 1; i++)
            sb.append(elements[i]).append(", ");
        if (length != 0)
            sb.append(elements[length - 1]);

        sb.append(']');
        return sb.toString();
    }

    @Override
    public boolean add(E elem) {
        if (length == elements.length)
            resize(extendStep, length);
        elements[length++] = elem;
        return true;
    }

    @Override
    public E remove(int index) {
        if (index < 0 || length <= index)
            return null;
        var previous = elements;

        resize(elements.length - length >= shrinkStep ? -shrinkStep : 0, index);
        System.arraycopy(previous, index + 1, elements, index, --length - index);

        return previous[index];
    }

    /**
     * Resize internal array
     *
     * @param step New length is old plus step
     * @param last Copy elements from first to last one
     */
    @SuppressWarnings("unchecked")
    protected void resize(int step, int last) {
        var resized = (E[]) new Object[elements.length + step];
        System.arraycopy(elements, 0, resized, 0, last);
        elements = resized;
    }

    @Override
    public int size() {
        return length;
    }

    @Override
    public void add(int index, E elem) {
        if (index < 0 || index >= length) {
            if (index == length)
                add(elem);
            return;
        }

        if (length == elements.length)
            resize(extendStep, length);

        System.arraycopy(elements, index, elements, index + 1, length++ - index);
        elements[index] = elem;
    }

    @Override
    public boolean remove(Object obj) {
        for (int i = 0; i < elements.length; i++)
            if (elements[i] != null && elements[i].equals(obj)) {
                remove(i);
                return true;
            }
        return false;
    }

    @Override
    public E set(int index, E elem) {
        if (index < 0 || index >= length)
            return null;
        var prev = elements[index];
        elements[index] = elem;
        return prev;
    }

    @Override
    public boolean isEmpty() {
        return length == 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < length; i++)
            elements[i] = null;
        length = 0;
    }

    @Override
    public int indexOf(Object obj) {
        for (int i = 0; i < length; i++)
            if (elements[i] != null && elements[i].equals(obj))
                return i;
        return -1;
    }

    @Override
    public E get(int index) {
        return index < 0 || index >= length ? null : elements[index];
    }

    @Override
    public boolean contains(Object obj) {
        for (int i = 0; i < length; i++)
            if (elements[i] != null && elements[i].equals(obj))
                return true;
        return false;
    }

    @Override
    public int lastIndexOf(Object obj) {
        for (int i = length - 1; i >= 0; i--)
            if (elements[i] != null && elements[i].equals(obj))
                return i;
        return -1;
    }

    // ----- Опциональные к реализации методы -------------------------------------

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

    // ----- Опциональные к реализации методы (необходимо для отладки) -------------

    @Override
    public Iterator<E> iterator() {
        return null;
    }
}
