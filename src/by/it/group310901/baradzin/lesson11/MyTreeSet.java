package by.it.group310901.baradzin.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyTreeSet<E extends Comparable<E>> implements Set<E> {
    static int defaultSize = 8;
    static int extendStep = defaultSize / 2;

    E[] elements;
    int length;

    MyTreeSet() {
        this(defaultSize);
    }

    @SuppressWarnings("unchecked")
    MyTreeSet(int size) {
        elements = (E[]) new Comparable[size];
    }

    @SuppressWarnings("unchecked")
    private void extendElements() {
        var extended = (E[]) new Comparable[elements.length + extendStep];
        System.arraycopy(elements, 0, extended, 0, length);
        elements = extended;
    }

    @SuppressWarnings("unchecked")
    int find(Object e) {
        return find((E) e, 0, length - 1);
    }

    int find(E e, int left, int right) {
        return left > right
                ? -(left + 1)
                : find(e, left, (left + right) >>> 1, right);
    }

    int find(E e, int left, int mid, int right) {
        return elements[mid].compareTo(e) < 0
                ? find(e, mid + 1, right)
                : elements[mid].compareTo(e) > 0
                        ? find(e, left, mid - 1)
                        : mid;
    }

    void remove(int index) {
        System.arraycopy(elements, index + 1, elements, index, --length - index);
        elements[length] = null;
    }

    // ----- required ----------------------------------------------------------

    @Override
    public String toString() {
        var sb = new StringBuilder("[");
        for (int i = 0; i < length - 1; i++)
            sb.append(elements[i]).append(", ");
        if (length != 0)
            sb.append(elements[length - 1]);
        return sb.append("]").toString();
    }

    @Override
    public int size() {
        return length;
    }

    @Override
    public boolean isEmpty() {
        return length == 0;
    }

    @Override
    public boolean contains(Object o) {
        return find(o) >= 0;
    }

    @Override
    public boolean add(E e) {
        int index = find(e);
        if (index >= 0)
            return false;
        if (length == elements.length)
            extendElements();

        index = -(index + 1);
        System.arraycopy(elements, index, elements, index + 1, length++ - index);
        elements[index] = e;

        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = find(o);
        if (index < 0)
            return false;
        remove(index);
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (var i : c)
            if (!contains(i))
                return false;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        var result = false;
        for (var i : c)
            result = add(i) || result;
        return result;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        int old = length;
        for (int i = 0; i < length; i++)
            if (!c.contains(elements[i]))
                remove(i--);
        return old != length;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        var result = false;
        for (var i : c)
            result = remove(i) || result;
        return result;
    }

    @Override
    public void clear() {
        while (length > 0)
            elements[--length] = null;
    }

    // ===== optional ==========================================================

    @Override
    public Iterator<E> iterator() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'iterator'");
    }

    @Override
    public Object[] toArray() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toArray'");
    }

    @Override
    public <T> T[] toArray(T[] a) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toArray'");
    }

}
