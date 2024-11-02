package by.it.group310902.belskiy.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyLinkedHashSet<E> implements Set<E> {
    class Node<E> {
        E data;
        Node<E> next;
        Node<E> after, previous;

        Node(E data) {
            this.data = data;
        }
    }
    static final int defaultSize = 32;
    private Node<E>[] elements;

    private Node<E> head, tail;
    private int count;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node<E> current = head;
        if (current != null) {
            sb.append(current.data);
            current = current.after;
        }
        while (current != null) {
            sb.append(", ").append(current.data);
            current = current.after;
        }
        return sb.append("]").toString();
    }

    public MyLinkedHashSet() {
        this(defaultSize);
    }

    public MyLinkedHashSet(int capacity) {
        elements = new Node[capacity];
    }

    int GetHash(Object value) {
        return (value.hashCode() & 0x7FFFFFFF) % elements.length;
    }

    void AddNode(Node<E> newNode) {
        if (head == null) {
            head = newNode;
        } else {
            tail.after = newNode;
            newNode.previous = tail;
        }
        tail = newNode;
    }

    void RemoveNode(Node<E> newNode) {
        if (newNode.after != null)
            newNode.after.previous = newNode.previous;
        else
            tail = newNode.previous;

        if (newNode.previous != null)
            newNode.previous.after = newNode.after;
        else
            head = newNode.after;
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
    public void clear() {
        for (int i = 0; i < elements.length; i++)
            elements[i] = null;
        head = null;
        tail = null;
        count = 0;
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
        AddNode(newNode);
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
                RemoveNode(current);
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
    public boolean containsAll(Collection<?> c) {
        for (Object obj: c) {
            if (!contains(obj))
                return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for (E item : c) {
            if (add(item)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (c.isEmpty()) {
            this.clear();
            return true;
        }
        boolean isModified = false;
        MyLinkedHashSet<E> tempSet = new MyLinkedHashSet<>(elements.length);
        Node<E> current = head;
        while (current != null) {
            if (c.contains(current.data)) {
                tempSet.add(current.data);
                isModified = true;
            }
            current = current.after;
        }
        elements = tempSet.elements;
        head = tempSet.head;
        tail = tempSet.tail;
        count = tempSet.count;

        return isModified;
    }


    @Override
    public boolean removeAll(Collection<?> c) {
        boolean isModified = false;
        for (Object obj: c) {
            if (remove(obj))
                isModified = true;
        }
        return isModified;
    }


    ///////////////////////////////////////////////////////////////////

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
}
