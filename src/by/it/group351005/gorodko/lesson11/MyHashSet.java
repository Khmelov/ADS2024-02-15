package by.it.group351005.gorodko.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyHashSet<E> implements Set<E> {
    private final int size = 12;

    private class Node<E> {
        E data;
        Node<E> next;
    }

    private Node<E>[] set = new Node[size];

    private int getHash(Object o) {
        return o.hashCode() % size;
    }

    public String toString() {
        if (isEmpty()) return "[]";
        String res = "[";
        for (int i = 0; i < size; i++) {
            Node<E> temp = set[i];
            while (temp != null) {
                res += temp.data.toString() + ", ";
                temp = temp.next;
            }
        }
        return res.substring(0, res.length() - 2) + "]";
    }

    @Override
    public int size() {
        int res = 0;
        for (int i = 0; i < size; i++) {
            Node<E> temp = set[i];
            while (temp != null) {
                res++;
                temp = temp.next;
            }
        }
        return res;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            set[i] = null;
        }
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < size; i++) {
            if (set[i] != null) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean add(E e) {
        if (contains(e)) return false;
        int hash = getHash(e);
        Node<E> node = new Node<E>();
        node.data = e;
        node.next = null;
        if (set[hash] == null) {
            set[hash] = node;
        }
        else {
            Node<E> temp = set[hash];
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = node;
        }
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (!contains(o)) return false;
        int hash = getHash(o);
        if (set[hash].data.equals(o)) {
            set[hash] = set[hash].next;
        }
        else {
            Node<E> temp = set[hash];
            while (!temp.next.data.equals(o)) {
                temp = temp.next;
            }
            temp.next = temp.next.next;
        }
        return true;
    }

    @Override
    public boolean contains(Object o) {
        Node<E> temp = set[getHash(o)];
        while (temp != null) {
            if (temp.data.equals(o)) {
                return true;
            }
            temp = temp.next;
        }
        return false;
    }






    //////////////////////////////////////////////////////////////////////
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