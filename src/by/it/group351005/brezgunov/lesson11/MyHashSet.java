package by.it.group351005.brezgunov.lesson11;

import java.util.*;

public class MyHashSet<E> implements Set<E> {
    private static class Node<E> {
        E value;
        Node<E> next;
    }

    private int size = 0;
    private Node<E>[] arr = new Node[20];

    private int findIndex(Object o) {
        return o.hashCode() % arr.length;
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
        int index = findIndex(o);
        Node<E> temp = arr[index];
        if (temp == null)
            return false;
        while (temp != null) {
            if (temp.value.equals(o)) {
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    @Override
    public Iterator iterator() {
        return new Iterator() {
            private int currIndex = 0, used = 0;
            private Node<E> currNode = arr[0];

            @Override
            public boolean hasNext() {
                return used < size;
            }

            @Override
            public Object next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                if (currNode == null) {
                    do {
                        currNode = arr[++currIndex];
                    } while (currNode == null);
                }
                used++;
                Node<E> temp = currNode;
                currNode = currNode.next;
                return temp.value;
            }
        };
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    private void extendArr() {
        Node<E>[] temp = arr;
        arr = new Node[arr.length * 2];
        int tempSize = size;
        for (Node<E> eNode : temp) {
            Node<E> curr = eNode;
            while (curr != null) {
                add(curr.value);
                curr = curr.next;
            }
        }
        size = tempSize;
    }

    @Override
    public boolean add(Object o) {
        if (contains(o))
            return false;

        if (size == arr.length) {
            extendArr();
        }

        Node<E> newNode = new Node<>();
        newNode.value = (E) o;

        int index = findIndex(o);
        Node<E> temp = arr[index];
        newNode.next = null;

        if (temp == null) {
            arr[index] = newNode;
        } else {
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
        }
        size++;

        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (!contains(o)) {
            return false;
        }

        int index = findIndex(o);

        Node<E> temp = arr[index];

        if (temp.value.equals(o)) {
            arr[index] = temp.next;
        } else {
            while (!temp.next.value.equals(o)) {
                temp = temp.next;
            }
            temp.next = temp.next.next;
        }
        size--;
        return true;
    }

    @Override
    public boolean addAll(Collection c) {
        return false;
    }

    @Override
    public void clear() {
        size = 0;
        arr = new Node[20];
    }

    @Override
    public boolean removeAll(Collection c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection c) {
        return false;
    }

    @Override
    public boolean containsAll(Collection c) {
        return false;
    }

    @Override
    public Object[] toArray(Object[] a) {
        return new Object[0];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Object o : this) {
            if (sb.length() > 1) {
                sb.append(", ").append(o.toString());
            } else {
                sb.append(o.toString());
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
