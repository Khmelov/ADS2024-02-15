package by.it.group351004.brazhalovich.lesson11;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyLinkedHashSet<E> implements Set<E> {
    class Node<E> {
        E data;
        Node<E> next;
        Node<E> prev, follow;
        Node(E e) {
            data = e;
        }
    }

    static final int START_SIZE = 20;
    int _size = 0;
    Node<E>[] _items;

    Node<E> _head, _tail;

    MyLinkedHashSet() {
        this(START_SIZE);
    }

    MyLinkedHashSet(int size) {
        _items = new Node[size];
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

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    int getHash(Object o) {
        return (o.hashCode() & 0x7FFFFFFF) % _items.length;
    }

    void addNode(Node<E> newNode) {
        if(_head == null)
            _head = newNode;
        else {
            _tail.follow = newNode;
            newNode.prev = _tail;
        }
        _tail = newNode;
    }

    void removeNode(Node<E> newNode) {
        if (newNode.follow != null) {
            newNode.follow.prev = newNode.prev;
        } else {
            _tail = newNode.prev;
        }
        if (newNode.prev != null) {
            newNode.prev.follow = newNode.follow;
        } else {
            _head = newNode.follow;
        }
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
                removeNode(curr);
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
        _head = null;
        _tail = null;
    }

    @Override
    public boolean add(Object o) {
        Node<E> newNode = new Node<E>((E)o);
        int ind = getHash(o);
        Node<E> curr = _items[ind];
        while (curr != null) {
            if (curr.data.equals(o)) {
                return false;
            }
            curr = curr.next;
        }
        newNode.next = _items[ind];
        _items[ind] = newNode;
        addNode(newNode);
        if (++_size > _items.length * 0.7)
            resize();
        return true;
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
    //------------------------------



    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public Object[] toArray(Object[] a) {
        return new Object[0];
    }
}