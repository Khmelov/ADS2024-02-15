package by.it.group351005.gorodko.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyLinkedHashSet<E> implements Set {

    private final int size = 12;

    private class Node<E> {
        E data;
        Node<E> next;
        Node<E> addedNext;
    }

    private Node<E>[] set = new Node[size];

    private Node<E> head = null;

    private int getHash(Object o) {
        return o.hashCode() % size;
    }

    public String toString() {
        if (head == null) return "[]";
        String res = "[";
        Node<E> temp = head;
        while (temp != null) {
            res += temp.data.toString() + ", ";
            temp = temp.addedNext;
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
        head = null;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public boolean add(Object e) {
        if (contains(e)) return false;
        int hash = getHash(e);
        Node<E> node = new Node<E>();
        node.data = (E)e;
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
        if (head == null) {
            head = node;
            head.addedNext = null;
        }
        else {
            Node<E> temp = head;
            while (temp.addedNext != null) {
                temp = temp.addedNext;
            }
            temp.addedNext = node;
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
        if (head.data.equals(o)) {
            head = head.addedNext;
        }
        else {
            Node<E> temp = head;
            while (!temp.addedNext.data.equals(o)) {
                temp = temp.addedNext;
            }
            temp.addedNext = temp.addedNext.addedNext;
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

    @Override
    public boolean addAll(Collection c) {
        boolean res = false;
        for (Object o: c) {
            res = res | add(o);
        }
        return res;
    }

    @Override
    public boolean removeAll(Collection c) {
        boolean res = false;
        for (Object o: c) {
            res = res | remove(o);
        }
        return res;
    }

    @Override
    public boolean retainAll(Collection c) {
        boolean res = false;
        Node<E> temp = head;
        while (temp != null) {
            if (!c.contains(temp.data)) {
                remove(temp.data);
                res = true;
            }
            temp = temp.addedNext;
        }
        return res;
    }

    @Override
    public boolean containsAll(Collection c) {
        for (Object o: c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }




    //////////////////////////////////////////////////
    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public Object[] toArray(Object[] a) {
        return new Object[0];
    }
}
