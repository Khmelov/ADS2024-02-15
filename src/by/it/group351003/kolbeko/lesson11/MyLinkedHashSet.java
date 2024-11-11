package by.it.group351003.kolbeko.lesson11;

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
    int size = 0;
    Node<E>[] items;

    Node<E> head, tail;

    MyLinkedHashSet() {
        this(START_SIZE);
    }

    MyLinkedHashSet(int size) {
        items = new Node[size];
    }

    @Override
    public String toString() {
        StringBuilder line = new StringBuilder("[");
        Node<E> curr = head;
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
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (Node<E> item : items) {
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
        return (o.hashCode() & 0x7FFFFFFF) % items.length;
    }

    void addNode(Node<E> newNode) {
        if(head == null)
            head = newNode;
        else {
            tail.follow = newNode;
            newNode.prev = tail;
        }
        tail = newNode;
    }

    void removeNode(Node<E> newNode) {
        if (newNode.follow != null) {
            newNode.follow.prev = newNode.prev;
        } else {
            tail = newNode.prev;
        }
        if (newNode.prev != null) {
            newNode.prev.follow = newNode.follow;
        } else {
            head = newNode.follow;
        }
    }

    void resize(){
        Node<E>[] newIems = new Node[items.length*2];
        for(Node<E> curr : items) {
            while(curr != null) {
                Node<E> next = curr.next;
                int newInd = (curr.data.hashCode() & 0x7FFFFFFF) % newIems.length;
                curr.next = newIems[newInd];
                newIems[newInd] = curr;
                curr = next;
            }
        }
        items = newIems;
    }

    @Override
    public boolean remove(Object o) {
        int ind = getHash(o);
        Node<E> curr = items[ind];
        Node<E> prev = null;
        while(curr != null) {
            if (o.equals(curr.data)){
                if(prev == null) {
                    items[ind] = curr.next;
                } else {
                    prev.next = curr.next;
                }
                size--;
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
        for (int i = 0; i < items.length; i++) {
            items[i] = null;
        }
        size = 0;
        head = null;
        tail = null;
    }

    @Override
    public boolean add(Object o) {
        Node<E> newNode = new Node<E>((E)o);
        int ind = getHash(o);
        Node<E> curr = items[ind];
        while (curr != null) {
            if (curr.data.equals(o)) {
                return false;
            }
            curr = curr.next;
        }
        newNode.next = items[ind];
        items[ind] = newNode;
        addNode(newNode);
        if (++size > items.length * 0.7)
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
        Node<E> curr = head;
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
