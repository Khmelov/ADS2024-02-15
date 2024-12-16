package by.it.group351001.rudenya_d.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyLinkedHashSet<E> implements Set<E> {

    class Node<E> {
        E data;
        Node<E> next;
        Node<E> prev, follow;
        Node(E data) {
            this.data = data;
        }
    }
    Node<E> _head, _tail;
    static final int defaultSize = 32;
    Node<E>[] _items;
    int _count;

    public MyLinkedHashSet() {
        this(defaultSize);
    }

    public MyLinkedHashSet(int capacity) {
        _items = new Node[capacity];
    }

    int GetHash(Object value) {
        return (value.hashCode()) % _items.length;
    }

    @Override
    public String toString() {
        StringBuilder line = new StringBuilder("[");
        Node<E> curr = _head;
        while (curr != null) {
            line.append(curr.data);
            if (curr.follow != null)
                line.append(", ");
            curr = curr.follow;
        }
        line.append("]");
        return line.toString();
    }

    @Override
    public int size() {
        return _count;
    }

    @Override
    public boolean isEmpty() {
        return _count == 0;
    }

    @Override
    public boolean contains(Object o) {
        Node<E> current = _items[GetHash(o)];
        while (current != null) {
            if (current.data.equals(o)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public boolean add(Object o) {
        int index = GetHash(o);
        Node<E> current = _items[index];
        while (current != null) {
            if (o.equals(current.data)) {
                return false;
            }
            current = current.next;
        }
        Node<E> newNode = new Node<>((E)o);
        newNode.next = _items[index];
        _items[index] = newNode;
        if(_head == null)
            _head = newNode;
        else {
            _tail.follow = newNode;
            newNode.prev = _tail;
        }
        _tail = newNode;
        _count++;
        if (_count >= _items.length ) {
            resize();
        }
        return true;
    }

    void resize() {
        Node<E>[] newItems = new Node[_items.length * 2];
        for (int i = 0; i < _items.length; i++) {
            Node<E> current = _items[i];
            while (current != null) {
                Node<E> next = current.next;
                int newIndex = current.data.hashCode() & 0x7FFFFFFF % newItems.length;
                current.next = newItems[newIndex];
                newItems[newIndex] = current;
                current = next;
            }
        }
        _items = newItems;
    }

    @Override
    public boolean remove(Object o) {
        int index = GetHash(o);
        Node<E> current = _items[index];
        Node<E> previous = null;
        while (current != null) {
            if (current.data.equals(o)) {
                if (previous == null) {
                    _items[index] = current.next;
                } else {
                    previous.next = current.next;
                }
                _count--;
                if (current.follow != null) {
                    current.follow.prev = current.prev;
                } else {
                    _tail = current.prev;
                }
                if (current.prev != null) {
                    current.prev.follow = current.follow;
                } else {
                    _head = current.follow;
                }
                return true;
            }
            previous = current;
            current = current.next;
        }
        return false;
    }

    @Override
    public void clear() {
        for (int i = 0; i < _items.length; i++) {
            _items[i] = null;
        }
        _count = 0;
        _head = null;
        _tail = null;
    }


    ////////////////////////////////////////////////////////////////////////

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
    public boolean addAll(Collection c) {
        boolean isModified = false;
        for(Object item : c) {
            if(add(item)){
                isModified = true;
            }
        }
        return isModified;
    }

    @Override
    public boolean removeAll(Collection c) {
        boolean isModified = false;
        for(Object item : c) {
            if(remove(item)){
                isModified = true;
            }
        }
        return isModified;
    }

    @Override
    public boolean retainAll(Collection c) {
        if (c.isEmpty()){
            clear();
            return true;
        }
        boolean isModified = false;
        Node<E> curr = _head;
        while (curr != null) {
            Node<E> next = curr.follow;
            if (!c.contains(curr.data)) {
                remove(curr.data);
                isModified = true;
            }
            curr = next;
        }
        return isModified;
    }

    @Override
    public boolean containsAll(Collection c) {
        for (Object item : c){
            if(!contains(item))
                return false;
        }
        return true;
    }
}