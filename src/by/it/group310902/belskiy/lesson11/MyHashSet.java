package by.it.group310902.belskiy.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyHashSet<E> implements Set<E> {
    class Node<E> {
        E data;
        Node<E> next;

        Node(E data) {
            this.data = data;
        }
    }
    static final int defaultSize = 32;
    private Node<E>[] elements;
    private int count;

    public MyHashSet() {
        this(defaultSize);
    }

    public MyHashSet(int capacity) {
        elements = new Node[capacity];
    }

    int GetHash(Object value) {
        return (value.hashCode() & 0x7FFFFFFF) % elements.length;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        boolean first = true;
        for (int i = 0; i < elements.length; i++) {
            Node<E> current = elements[i];
            while (current != null) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append(current.data);
                first = false;
                current = current.next;
            }
        }

        sb.append("]");
        return sb.toString();
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public boolean contains(Object o) {
        Node<E> current = elements[GetHash(o)];
        while (current != null) {
            if (current.data.equals(o)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public boolean add(E e) {
        int index = GetHash(e);
        Node<E> current = elements[index];
        while (current != null) {
            if (current.data.equals(e)) {
                return false;
            }
            current = current.next;
        }
        Node<E> newNode = new Node<>(e);
        newNode.next = elements[index];
        elements[index] = newNode;
        count++;
        if (count > elements.length * 0.75) {
            resize();
        }
        return true;
    }

    void resize() {
        Node<E>[] newElements = new Node[elements.length * 2];
        for (int i = 0; i < elements.length; i++) {
            Node<E> current = elements[i];
            while (current != null) {
                Node<E> next = current.next;
                int newIndex = current.data.hashCode() & 0x7FFFFFFF % newElements.length;
                current.next = newElements[newIndex];
                newElements[newIndex] = current;
                current = next;
            }
        }
        elements = newElements;
    }

    @Override
    public boolean remove(Object o) {
        int index = GetHash(o);
        Node<E> current = elements[index];
        Node<E> previous = null;
        while (current != null) {
            if (current.data.equals(o)) {
                if (previous == null) {
                    elements[index] = current.next;
                } else {
                    previous.next = current.next;
                }
                count--;
                return true;
            }
            previous = current;
            current = current.next;
        }
        return false;
    }

    @Override
    public void clear() {
        for (int i = 0; i < elements.length; i++)
            elements[i] = null;
        count = 0;
    }


    ////////////////////////////////////////////////////////////////////////

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }
}