package by.it.group351005.brezgunov.lesson11;

import java.util.*;

public class MyLinkedHashSet<E> implements Set<E> {
    private static class Node<E> {
        Object value;
        Node<E> nextCollision, nextAdd, prev;
    }

    private Node<E> head = null, tail = null;

    private int size = 0;
    private Node<E>[] arr = new Node[10];

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
            if (o.equals(temp.value)) {
                return true;
            }
            temp = temp.nextCollision;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> currNode = head;
            @Override
            public boolean hasNext() {
                return currNode != null;
            }

            @Override
            public E next() {
                if (hasNext()) {
                    Node<E> temp = currNode;
                    currNode = currNode.nextAdd;
                    return (E) temp.value;
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    private void insert(Node<E> newNode) {
        int index = findIndex(newNode.value);
        Node<E> temp = arr[index];
        newNode.nextCollision = null;
        if (temp == null) {
            arr[index] = newNode;
        } else {
            while (temp.nextCollision != null) {
                temp = temp.nextCollision;
            }
            temp.nextCollision = newNode;
        }
    }

    private void extendArr() {
        Node<E>[] temp = arr;
        arr = new Node[arr.length * 2];
        for (Node<E> eNode : temp) {
            Node<E> curr = eNode;
            while (curr != null) {
                Node<E> tempNode = curr.nextCollision;
                insert(curr);
                curr = tempNode;
            }
        }
    }

    @Override
    public boolean add(Object o) {
        if (contains(o))
            return false;

        if (size == arr.length * 0.7) {
            extendArr();
        }

        Node<E> newNode = new Node<>();
        newNode.value = (E) o;

        insert(newNode);
        size++;

        if (head == null) {
            head = tail = newNode;
            newNode.nextAdd = newNode.prev = null;
        } else {
            tail.nextAdd = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = findIndex(o);

        Node<E> temp = arr[index];

        if (temp == null) {
            return false;
        }
        if (temp.value.equals(o)) {
            arr[index] = temp.nextCollision;
        } else {
            while (temp.nextCollision != null && !temp.nextCollision.value.equals(o)) {
                temp = temp.nextCollision;
            }
            if (temp.nextCollision == null) {
                return false;
            } else {
                Node<E> deleted = temp.nextCollision;
                temp.nextCollision = temp.nextCollision.nextCollision;
                temp = deleted;
            }
        }

        if (tail == head) {
            tail = head = null;
            return true;
        }

        if (temp.prev == null) {
            head = temp.nextAdd;
                temp.nextAdd.prev = null;
        } else {
            temp.prev.nextAdd = temp.nextAdd;
        }

        if (temp.nextAdd == null) {
            tail = temp.prev;
        } else {
            temp.nextAdd.prev = temp.prev;
        }
        size--;
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o))
                return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean isAdded = false;
        for (Object o : c) {
            if (add(o))
                isAdded = true;
        }
        return isAdded;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean isDeleted = false;
        for (Object o : this){
            if (!c.contains(o) && remove(o))
                isDeleted = true;
        }
        return isDeleted;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean isRemoved = false;
        for (Object o : c) {
            if (remove(o))
                isRemoved = true;
        }
        return isRemoved;
    }

    @Override
    public void clear() {
        size = 0;
        arr = new Node[10];
        tail = head = null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        if (head == null) {
            return "[]";
        }
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
