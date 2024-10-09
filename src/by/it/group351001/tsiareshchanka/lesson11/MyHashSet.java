package by.it.group351001.tsiareshchanka.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
public class MyHashSet<E> implements Set<E> {
    private Node<E>[] table;
    private int size;

    private static class Node<E> {
        E data;
        Node<E> next;

        public Node(E data) {
            this.data = data;
        }
    }

    public MyHashSet() {
        table = new Node[16]; // начальный размер массива
        size = 0;
    }

    public int size() {
        return size;
    }

    public void clear() {
        table = new Node[table.length];
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean add(E element) {
        int index = getIndex(element);
        Node<E> node = table[index];

        while (node != null) {
            if (node.data.equals(element)) {
                return false; // элемент уже есть
            }
            node = node.next;
        }

        Node<E> newNode = new Node<>(element);
        newNode.next = table[index];
        table[index] = newNode;
        size++;
        return true;
    }

    public boolean remove(Object o) {
        int index = getIndex(o);
        Node<E> node = table[index];
        Node<E> prev = null;

        while (node != null) {
            if (node.data.equals(o)) {
                if (prev == null) {
                    table[index] = node.next;
                } else {
                    prev.next = node.next;
                }
                size--;
                return true;
            }
            prev = node;
            node = node.next;
        }
        return false;
    }

    public boolean contains(Object o) {
        int index = getIndex(o);
        Node<E> node = table[index];

        while (node != null) {
            if (node.data.equals(o)) {
                return true;
            }
            node = node.next;
        }
        return false;
    }

    private int getIndex(Object o) {
        int hashCode = o.hashCode();
        return Math.abs(hashCode) % table.length;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        boolean first = true;
        for (Node<E> node : table) {
            while (node != null) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append(node.data);
                first = false;
                node = node.next;
            }
        }
        sb.append("]");
        return sb.toString();
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
