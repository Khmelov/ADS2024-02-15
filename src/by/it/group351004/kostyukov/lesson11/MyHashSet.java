package by.it.group351004.kostyukov.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
public class MyHashSet<E> implements Set<E> {

    class Node<E>{
        E data;
        Node<E> next;
        Node(E e) {
            data = e;
        }
    }
    static final int START_SIZE = 20;
    int size = 0;
    Node<E>[] items;
    MyHashSet() {this(START_SIZE);}

    MyHashSet(int size) {items = new Node[size];}
    @Override
    public String toString(){
        StringBuilder line = new StringBuilder("[");
        int counter = size;
        for (Node<E> curr : items) {
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

    int getHash(Object o) {
        return (o.hashCode() & 0x7FFFFFFF) % items.length;
    }

    void resize(){
        Node<E>[] newItems = new Node[items.length*2];
        for(Node<E> curr : items) {
            while(curr != null) {
                Node<E> next = curr.next;
                int newIndex = (curr.data.hashCode() & 0x7FFFFFFF) % newItems.length;
                curr.next = newItems[newIndex];
                newItems[newIndex] = curr;
                curr = next;
            }
        }
        items = newItems;
    }

    @Override
    public boolean add(E e) {
        Node<E> newNode = new Node<E>(e);
        int ind = getHash(e);
        Node<E> curr = items[ind];
        while (curr != null) {
            if (curr.data.equals(e)) {
                return false;
            }
            curr = curr.next;
        }
        newNode.next = items[ind];
        items[ind] = newNode;
        if (++size > items.length * 0.7)
            resize();
        return true;
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
