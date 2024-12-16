package by.it.group351004.narivonchik.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyLinkedHashSet<E> implements Set<E> {
    private class Node<E> {
        E data;
        Node<E> next;
        Node<E> after;
        Node<E> before;

        public Node(E value, Node<E> next, Node<E> before, Node<E> after) {
            this.data = value;
            this.next = next;
            this.before = before;
            this.after = after;
        }
    }

    private final int START_SIZE = 16;
    private int size;
    private Node<E>[] items;
    private Node<E> head;
    private Node<E> tail;

    private int getHashCode(Object o) {
        return o.hashCode() % items.length;
    }

    private void addNode(Node<E> node){
        if (head == null) {
            head = tail = node;
        }
        else {
            node.before = tail;
            tail.after = node;
            tail = tail.after;
        }
    }

    private void removeNode(Node<E> node) {
        if (node.before != null) {
            node.before.after = node.after;
        }
        else {
            head = node.after;
        }
        if (node.after != null) {
            node.after.before = node.before;
        }
        else {
            tail = node.before;
        }
    }

    public MyLinkedHashSet() {
        size = 0;
        items = new Node[START_SIZE];
        head = tail = null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        Node<E> curr = head;
        while (curr != null) {
            sb.append(curr.data).append(", ");
            curr = curr.after;
        }
        if (sb.length() > 1) {
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append(']');
        return sb.toString();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean add(E e) {
        int hash = getHashCode(e);
        Node<E> curr = items[hash];
        while (curr != null) {
            if (curr.data.equals(e))
                return false;
            curr = curr.next;
        }
        // add to _items
        items[hash] = new Node<>(e,items[hash],tail,null);
        // add to tail
        addNode(items[hash]);
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int hash = getHashCode(o);
        Node<E> current = items[hash];
        Node<E> prev = null;
        while (current != null) {
            if (current.data.equals(o)) {
                // remove from items
                if (prev == null) {
                    items[hash] = current.next;
                }
                else {
                    prev.next = current.next;
                }
                // remove from head/tail
                removeNode(current);
                size--;
                return true;
            }
            prev = current;
            current = current.next;
        }
        return false;
    }

    @Override
    public boolean contains(Object o) {
        Node<E> current = items[getHashCode(o)];
        while (current != null) {
            if (current.data.equals(o))
                return true;
            current = current.next;
        }
        return false;
    }

    @Override
    public void clear() {
        items = new Node[START_SIZE];
        size = 0;
        head = tail = null;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        for (var obj: collection) {
            if (!contains(obj)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        boolean isModified = false;
        for (var obj: collection) {
            if (add(obj) && !isModified)
                isModified = true;
        }
        return isModified;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        boolean isModified = false;
        Node<E> curr = head;
        while (curr != null) {
            Node<E> tempNext = curr.after;
            if (!collection.contains(curr.data)) {
                remove(curr.data);
                if (!isModified)
                    isModified = true;
            }
            curr = tempNext;
        }
        return isModified;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean isModified = false;
        for (var obj: collection) {
            if (remove(obj) && !isModified)
                isModified = true;
        }
        return isModified;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        return null;
    }
}