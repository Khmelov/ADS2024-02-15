package by.it.group351001.budnikov.lesson11;

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
    static final int CAPACITY = 50;
    Node<E>[] elements;
    Node<E> head, tail;
    int count;

    @Override
    public String toString() {
        String tempStr = "[";

        Node<E> current = head;

        if (current != null) {
            tempStr += current.data;
            current = current.after;
        }
        while (current != null) {
            tempStr = tempStr + ", " + current.data;
            current = current.after;
        }
        return tempStr + "]";
    }

    public MyLinkedHashSet() {
        this(CAPACITY);
    }

    public MyLinkedHashSet(int capacity) {
        elements = new Node[capacity];
    }

    int GetHash(Object value) {
        return (value.hashCode()) % elements.length;
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

    void RemoveNode(Node<E> noteToRemove) {
        if (noteToRemove.after != null)
            noteToRemove.after.previous = noteToRemove.previous;
        else
            tail = noteToRemove.previous;

        if (noteToRemove.previous != null)
            noteToRemove.previous.after = noteToRemove.after;
        else
            head = noteToRemove.after;
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
        Node<E>[] newelements = new Node[elements.length * 2];
        for (int i = 0; i < elements.length; i++) {
            Node<E> current = elements[i];
            while (current != null) {
                Node<E> next = current.next;
                int newIndex = current.data.hashCode() % newelements.length;
                current.next = newelements[newIndex];
                newelements[newIndex] = current;
                current = next;
            }
        }
        elements = newelements;
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