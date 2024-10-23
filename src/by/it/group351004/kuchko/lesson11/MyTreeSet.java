package by.it.group351004.kuchko.lesson11;

import java.util.Set;
import java.util.Collection;
import java.util.Iterator;
@SuppressWarnings("all")
public class MyTreeSet<E extends Comparable<E>> implements Set<E> {
    private final int DEFAULT_SIZE = 16;
    private int size;
    private E[] items;

    MyTreeSet() {
        size = 0;
        items = (E[])new Comparable[DEFAULT_SIZE];
    }

    private void resize() {
        E[] newArr = (E[]) new Comparable[items.length + DEFAULT_SIZE];
        for (int i = 0; i < items.length; i++) newArr[i] = items[i];
        items = newArr;
    }
    private int binarySearch(E e) {
        int left = 0;
        int right = size - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (items[mid].compareTo(e) > 0) right = mid - 1;
            else if (items[mid].compareTo(e) < 0) left = mid + 1;
            else return mid;
        }
        return -1;
    }
    //--------------------------------------------
    @Override
    public int size() { return size; }
    @Override
    public boolean isEmpty() { return size == 0; }
    @Override
    public String toString() {
        StringBuilder line = new StringBuilder();
        line.append('[');
        for (int i = 0; i < size - 1; i++)
            line.append(items[i]).append(", ");
        if (size > 0) line.append(items[size - 1]);
        line.append(']');
        return line.toString();
    }
    //--------------------------------------------
    @Override
    public boolean add(E e) {
        if (contains(e)) return false;
        if (size == items.length) resize();
        int i;
        for (i = size - 1; i >= 0 && items[i].compareTo(e) > 0; i--)
            items[i + 1] = items[i];
        items[i + 1] = e;
        size++;
        return true;
    }
    @Override
    public boolean remove(Object o) {
        int i = binarySearch((E)o);
        if (i < 0) return false;
        for (size--; i < size; i++)
            items[i] = items[i + 1];
        return true;
    }
    @Override
    public boolean contains(Object o) { return binarySearch((E)o) > -1; }
    //--------------------------------------------
    @Override
    public boolean addAll(Collection<? extends E> collection) {
        boolean modified = false;
        for (var c: collection)
            modified = add(c) || modified;
        return modified;
    }
    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean modified = false;
        for (var c: collection)
            modified = remove(c) || modified;
        return modified;
    }
    @Override
    public boolean containsAll(Collection<?> collection) {
        for (var c: collection)
            if (!contains(c)) return false;
        return true;
    }
    @Override
    public boolean retainAll(Collection<?> collection) {
        boolean modified = false;
        int i = 0;
        while (i < size) {
            if (!collection.contains(items[i]))
                modified = remove(items[i]);
            else i++;
        }
        return modified;
    }
    @Override
    public void clear() {
        items = (E[])new Comparable[DEFAULT_SIZE];
        size = 0;
    }
    //--------------------------------------------
    @Override
    public Iterator<E> iterator() { return null; }
    @Override
    public Object[] toArray() { return new Object[0]; }
    @Override
    public <T> T[] toArray(T[] a) { return null; }
}
