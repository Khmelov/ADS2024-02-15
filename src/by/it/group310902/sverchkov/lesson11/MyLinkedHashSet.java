package by.it.group310902.sverchkov.lesson11;

import java.util.*;

public class MyLinkedHashSet<E> implements Set<E> {

    private class Node<E> {
        private E element;
        Node<E> next;
        Node<E> orderNext;
        Node<E> orderPrev;

        Node(E element) {
            this.element = element;
        }
    }

    static final int DEFAULT_CAPACITY = 10;

    Node<E> head;
    Node<E> tail;
    Node<E>[] arr;
    int size = 0;

    MyLinkedHashSet() {
        this(DEFAULT_CAPACITY);
    }

    MyLinkedHashSet(int size) {
        arr = new Node[size];
    }

    int getHashCode(Object o) {
        return o.hashCode() % arr.length;
    }

    void addToOrder(Node<E> node){
        if (node == null) throw new NullPointerException();
        if (head == null){
            head= node;
        }
        else {
            tail.orderNext = node;
            node.orderPrev = tail;
        }
        tail = node;
    }

    void removeFromOrder(Node<E> node) {
        if (node == null) throw new NullPointerException();
        if (node == head && node == tail) {
            head = tail = null;
        } else if (node == head) {
            head.orderNext.orderPrev = null;
            head = head.orderNext;
        } else if (node == tail) {
            tail.orderPrev.orderNext = null;
            tail = tail.orderPrev;
        } else {
            node.orderNext.orderPrev = node.orderPrev;
            node.orderPrev.orderNext = node.orderNext;
        }
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
    public boolean contains(Object o) {
        int index = getHashCode(o);

        if (arr[index] != null) {
            for (Node node = arr[index]; node != null; node = node.next) {
                if (node.element.equals(o)) {
                    return true;
                }
            }
        }
        return false;
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
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(E e) {
        if (e == null) throw new NullPointerException();

        int index = getHashCode(e);
        if (arr[index] == null) {
            arr[index] = new Node<E>(e);
            addToOrder(arr[index]);
        } else {
            Node<E> node = arr[index];
            while (node.next != null) {
                if (node.element.equals(e)) {
                    return false;
                }
                node = node.next;
            }
            if (node.element.equals(e)) {
                return false;
            }
            node.next = new Node<E>(e);
            addToOrder(node.next);
        }

        size++;

        if (size >= arr.length * 0.75)
            resize();

        return true;
    }

    void resize() {
        Node<E>[] newArr = new Node[arr.length*2];

        Node<E> node = head;
        while(node != null){
            int index = node.element.hashCode() % (arr.length*2);
            if (newArr[index] == null){
                newArr[index] = node;
                node.next = null;
            }
            else {
                Node<E> temp = newArr[index];
                while(temp.next != null){
                    temp = temp.next;
                }
                temp.next = node;
                node.next = null;
            }
            node = node.orderNext;
        }

        arr = newArr;

    }

    @Override
    public boolean remove(Object o) {
        int index = getHashCode(o);

        if (arr[index] != null) {
            if (arr[index].element.equals(o)) {
                removeFromOrder(arr[index]);
                if (arr[index].next == null) {
                    arr[index] = null;
                } else
                    arr[index] = arr[index].next;
                size--;
                return true;
            }
            for (Node node = arr[index]; node.next != null; node = node.next) {
                if (node.next.element.equals(o)) {
                    removeFromOrder(node.next);
                    node.next = node.next.next;
                    size--;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean changed = false;
        for (E e : c) {
            if (!add(e) && !changed)
                changed = true;
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (c.isEmpty()) {
            clear();
            return true;
        }

        boolean modified = false;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null) {
                for (Node node = arr[i]; node != null; node = node.next) {
                    if (!c.contains(node.element)) {
                        remove(node.element);
                        modified = true;
                    }
                }
            }
        }

        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object o : c) {
            if (contains(o)) {
                remove(o);
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public void clear() {
        arr = new Node[DEFAULT_CAPACITY];
        head = tail = null;
        size = 0;
    }

    @Override
    public String toString() {
        if (size == 0)
            return "[]";

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node<E> node;
        for (node = head; node.orderNext != null; node = node.orderNext) {
            sb.append(node.element);
            sb.append(", ");
        }
        sb.append(node.element).append("]");
        return sb.toString();
    }
}
