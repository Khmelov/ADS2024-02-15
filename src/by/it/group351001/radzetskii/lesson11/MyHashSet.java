package by.it.group351001.radzetskii.lesson11;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyHashSet<E> implements Set<E> {

    class Node<E> {
        E data;
        Node<E> next;
        Node(E e) {
            data = e;
        }
    }

    static final int START_SIZE = 20;
    int _size = 0;
    Node<E>[] _items;

    MyHashSet() {
        this(START_SIZE);
    }

    MyHashSet(int size) {
        _items = new Node[size];
    }

    @Override
    public String toString() {
        StringBuilder line = new StringBuilder("[");
        int counter = _size;
        for (Node<E> curr : _items) {
            while (curr != null) {
                counter--;
                line.append(curr.data);
                if (counter > 0)
                    line.append(", ");

                curr = curr.next;
            }
        }
        line.append("]");
        return line.toString();
    }

    @Override
    public int size() {
        return _size;
    }

    @Override
    public boolean isEmpty() {
        return _size == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (Node<E> item : _items) {
            Node<E> curr = item;
            while (curr != null) {
                if (o.equals(curr.data)) {
                    return true;
                }
                curr = curr.next;
            }
        }
        return false;
    }

    int getHash(Object o) {
        return (o.hashCode() & 0x7FFFFFFF) % _items.length;
    }

    void resize(){
        Node<E>[] newIems = new Node[_items.length*2];
        for(Node<E> curr : _items) {
            while(curr != null) {
                Node<E> next = curr.next;
                int newInd = (curr.data.hashCode() & 0x7FFFFFFF) % newIems.length;
                curr.next = newIems[newInd];
                newIems[newInd] = curr;
                curr = next;
            }
        }
        _items = newIems;
    }

    @Override
    public boolean add(E e) {
        Node<E> newNode = new Node<E>(e);
        int ind = getHash(e);
        Node<E> curr = _items[ind];
        while (curr != null) {
            if (curr.data.equals(e)) {
                return false;
            }
            curr = curr.next;
        }
        newNode.next = _items[ind];
        _items[ind] = newNode;
        if (++_size > _items.length * 0.7)
            resize();
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int ind = getHash(o);
        Node<E> curr = _items[ind];
        Node<E> prev = null;
        while(curr != null) {
            if (o.equals(curr.data)){
                if(prev == null) {
                    _items[ind] = curr.next;
                } else {
                    prev.next = curr.next;
                }
                _size--;
                return true;
            }
            prev = curr;
            curr = curr.next;
        }
        return false;
    }

    @Override
    public void clear() {
        for (int i = 0; i < _items.length; i++) {
            _items[i] = null;
        }
        _size = 0;
    }
    //---------------------------------

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