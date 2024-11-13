package by.it.group310901.dritz.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyLinkedHashSet<E> implements Set<E> {
    static int defaultSize = 500;

    Node<E>[] elements;
    Node<E> head, tail;
    int length;

    MyLinkedHashSet() {
        this(defaultSize);
    }

    @SuppressWarnings("unchecked")
    MyLinkedHashSet(int size) {
        elements = new Node[size];
    }

    static class Node<T> {
        T value;
        Node<T> next, before, after;

        Node(T element) {
            value = element;
        }

        public boolean addTo(MyLinkedHashSet<T> list, T element) {
            if (value.equals(element))
                return false;
            if (next != null)
                return next.addTo(list, element);
            next = new Node<T>(element);
            list.tail.after = next;
            next.before = list.tail;
            list.tail = next;
            list.length++;
            return true;
        }

        public boolean removeFrom(MyLinkedHashSet<T> list, Object element) {
            if (next == null)
                return false;
            if (!next.value.equals(element))
                return next.removeFrom(list, element);

            if (next.after == null)
                list.tail = next.before;
            else
                next.after.before = next.before;

            next.before.after = next.after;
            next = next.next;
            list.length--;

            return true;
        }

        public boolean contains(Object o) {
            return o.equals(value) || next != null && next.contains(o);
        }
    }

    // ----- required ----------------------------------------------------------

    @Override
    public String toString() {
        if (head == null)
            return "[]";
        var sb = new StringBuilder("[");
        for (var node = head; node.after != null; node = node.after)
            sb.append(node.value).append(", ");
        return sb.append(tail.value).append("]").toString();
    }

    @Override
    public int size() {
        return length;
    }

    @Override
    public void clear() {
        for (int i = length = 0; i < elements.length; i++)
            elements[i] = null;
        head = tail = null;
    }

    @Override
    public boolean isEmpty() {
        return length == 0;
    }

    @Override
    public boolean add(E e) {
        var index = e.hashCode() % elements.length;

        if (elements[index] != null)
            return elements[index].addTo(this, e);

        elements[index] = new Node<E>(e);
        if (length == 0) {
            head = elements[index];
        } else {
            tail.after = elements[index];
            elements[index].before = tail;
        }
        tail = elements[index];
        length++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = o.hashCode() % elements.length;

        if (elements[index] == null)
            return false;
        if (elements[index].removeFrom(this, o))
            return true;
        if (!elements[index].value.equals(o))
            return false;

        if (elements[index].after == null)
            tail = elements[index].before;
        else
            elements[index].after.before = elements[index].before;

        if (elements[index].before == null)
            head = elements[index].after;
        else
            elements[index].before.after = elements[index].after;

        elements[index] = elements[index].next;
        length--;

        return true;
    }

    @Override
    public boolean contains(Object o) {
        int index = o.hashCode() % elements.length;
        return elements[index] != null && elements[index].contains(o);
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
        var result = false;
        for (var i : this)
            if (!c.contains(i))
                result = remove(i) || result;
        return result;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        var result = false;
        for (var i : c)
            result = remove(i) || result;
        return result;
    }

    // ===== optional ==========================================================

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> current = head;
            private Node<E> previous = null;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {
                previous = current;
                current = current.after;
                return previous.value;
            }

            @Override
            public void remove() {
                MyLinkedHashSet.this.remove(previous.value);
                previous = null;
            }
        };
    }

    @Override
    public Object[] toArray() {
        var iter = iterator();
        var arr = new Object[size()];
        for (int i = 0; iter.hasNext();)
            arr[i++] = iter.next();
        return arr;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (size() >= a.length)
            return (T[]) toArray();

        var iter = iterator();
        for (int i = 0; iter.hasNext(); i++)
            a[i] = (T) iter.next();
        for (int i = size(); i < a.length; i++)
            a[i] = null;
        return a;
    }
}