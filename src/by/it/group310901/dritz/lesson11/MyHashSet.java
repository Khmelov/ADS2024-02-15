package by.it.group310901.dritz.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyHashSet<E> implements Set<E> {
    static int defaultSize = 500;

    Node<E>[] elements;
    int length = 0;

    MyHashSet() {
        this(defaultSize);
    }

    @SuppressWarnings("unchecked")
    MyHashSet(int size) {
        elements = new Node[size];
    }

    class Node<T> {
        T value;
        Node<T> next;

        Node(T element) {
            value = element;
        }

        boolean add(T element) {
            if (value.equals(element))
                return false;
            if (next == null) {
                next = new Node<T>(element);
                length++;
                return true;
            }
            return next.add(element);
        }

        public String toString() {
            return next == null ? value.toString() : next.toString(value.toString());
        }

        public String toString(String previous) {
            return previous + ", " + toString();
        }

        public boolean remove(Object o) {
            if (next == null)
                return false;
            if (o.equals(next.value)) {
                next = next.next;
                return true;
            }
            return next.remove(o);
        }

        public boolean contains(Object o) {
            return o.equals(value) || next != null && next.contains(o);
        }
    }

    // ----- required ----------------------------------------------------------

    @Override
    public String toString() {
        var sb = new StringBuilder("[");
        for (int i = 0; i < elements.length; i++)
            if (elements[i] != null)
                sb.append(elements[i].toString()).append(", ");
        if (length != 0)
            sb.delete(sb.length() - 2, sb.length());
        return sb.append("]").toString();
    }

    @Override
    public int size() {
        return length;
    }

    @Override
    public void clear() {
        for (int i = 0; i < elements.length; i++)
            elements[i] = null;
        length = 0;
    }

    @Override
    public boolean isEmpty() {
        return length == 0;
    }

    @Override
    public boolean add(E e) {
        var index = e.hashCode() % elements.length;
        if (elements[index] != null)
            return elements[index].add(e);
        elements[index] = new Node<E>(e);
        length++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = o.hashCode() % elements.length;
        if (elements[index] == null)
            return false;
        if (elements[index].value.equals(o)) {
            elements[index] = elements[index].next;
            length--;
            return true;
        }
        if (elements[index].remove(o)) {
            length--;
            return true;
        }
        return false;
    }

    @Override
    public boolean contains(Object o) {
        int index = o.hashCode() % elements.length;
        return elements[index] != null && elements[index].contains(o);
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

    @Override
    public boolean containsAll(Collection<?> c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'containsAll'");
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addAll'");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'retainAll'");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeAll'");
    }
}