package by.it.group351005.egorov.lesson11;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyHashSet<E> implements Set<E> {


    private class Node<E> {
        E value;
        Node<E> next;

        public Node(E value, Node<E> next) {
            this.next = next;
            this.value = value;
        }

        public Node(Node<E> next) {
            this.next = next;
        }
    }

    private int _size;
    private Node<E>[] _arr;
    private final int DEFAULT_CAPACITY = 32;


    private int getHashCode(Object o) {
        return o.hashCode() % _arr.length;
    }

    public MyHashSet() {
        _arr = new Node[DEFAULT_CAPACITY];
        _size = 0;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        for (var current : _arr) {
            Node<E> temp = current;
            while (temp != null) {
                stringBuilder.append(temp.value).append(", ");
                temp = temp.next;
            }
        }
        if (stringBuilder.length() > 1) {
            stringBuilder.delete(stringBuilder.length() - 2,stringBuilder.length());
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    @Override
    public boolean add(E e) {
        int hash = getHashCode(e);
        Node<E> current = _arr[hash];
        while (current != null) {
            if (current.value.equals(e))
                return false;
            current = current.next;
        }
        _arr[hash] = new Node<E>(e, _arr[hash]);
        _size++;
        return true;
    }

    @Override
    public int size() {
        return _size;
    }

    @Override
    public boolean remove(Object o) {
        int hash = getHashCode(o);
        Node<E> current = _arr[hash];
        Node<E> prev = null;
        while (current != null) {
            if (current.value.equals(o)) {
                if (prev == null) {
                    _arr[hash] = current.next;
                }
                else {
                    prev.next = current.next;
                }
                _size--;
                return true;
            }
            prev = current;
            current = current.next;
        }
        return false;
    }


    @Override
    public boolean isEmpty() {
        return _size == 0;
    }

    @Override
    public boolean contains(Object o) {
        int hash = getHashCode(o);
        Node<E> current = _arr[hash];
        while (current != null) {
            if (current.value.equals(o))
                return true;
            current = current.next;
        }
        return false;
    }

    @Override
    public void clear() {
        _arr = new Node[DEFAULT_CAPACITY];
        _size = 0;
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

    @Override
    public boolean containsAll(Collection<?> collection) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return false;
    }
}
