package by.it.group310901.baradzin.lesson09;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

// Аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ
public class ListC<E> implements List<E> {
    static final int defaultSize = 8;
    static final int extendStep = 4;
    static final int shrinkStep = 4;

    E[] elements;
    int length = 0;

    public ListC() {
        this(defaultSize);
    }

    @SuppressWarnings("unchecked")
    public ListC(int size) {
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
        try {
            if (length == elements.length)
                resize(extendStep, length);
            elements[length++] = elem;
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
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
     * @param step New lenght is old plus step
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

    @Override
    public boolean containsAll(Collection<?> collection) {
        for (Object elem : collection)
            if (!contains(elem))
                return false;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        var isModified = false;
        for (var elem : collection)
            isModified = add(elem) || isModified;
        return isModified;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> collection) {
        var initIndex = index;
        for (E elem : collection)
            add(index++, elem);
        return index != initIndex;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        var deleted = false;
        for (int i = 0; i < length; i++)
            if (collection.contains(elements[i])) {
                remove(i--);
                deleted = true;
            }
        return deleted;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        boolean deleted = false;
        for (int i = 0; i < length; i++) {
            if (!collection.contains(elements[i])) {
                remove(i--);
                deleted = true;
            }
        }
        return deleted;
    }

    // ----- Опциональные к реализации методы --------------------------------------

    @Override
    @SuppressWarnings("unchecked")
    public List<E> subList(int from, int to) {
        var subLenght = to - from + 1;
        if (from < 0 || to >= length || subLenght < 1)
            throw new IndexOutOfBoundsException("Invalid subList borders: " + from + ".." + to);

        var subElements = (E[]) new Object[subLenght];
        System.arraycopy(elements, from, subElements, 0, subLenght);
        return new ListC<>(subElements);
    }

    /**
     * Init ListC with array
     *
     * @param elements Internal array without nullish tail
     */
    public ListC(E[] elements) {
        this.elements = elements;
        length = elements.length;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        if (index >= length)
            throw new IndexOutOfBoundsException(
                    "Index '" + index + "' is equal or greater than lenght '" + length + "'.");
        var iter = iterator();
        for (int i = 0; i < index; i++)
            iter.next();
        return null;
    }

    @Override
    public ListIterator<E> listIterator() {
        return listIterator(0);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] arr) {
        if (arr.length < length)
            return (T[]) java.util.Arrays.copyOf(elements, length, arr.getClass());
        System.arraycopy(elements, 0, arr, 0, length);
        for (int i = length; i < arr.length; i++)
            arr[i] = null;
        return arr;
    }

    @Override
    public Object[] toArray() {
        return elements.clone();
    }

    // ----- Опциональные к реализации методы (необходимо для отладки) -------------

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            protected int current = 0;

            @Override
            public boolean hasNext() {
                return current < length;
            }

            @Override
            public E next() {
                return elements[current++];
            }

            @Override
            public void remove() {
                ListC.this.remove(--current);
            }
        };
    }
}
