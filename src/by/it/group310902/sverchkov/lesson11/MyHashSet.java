package by.it.group310902.sverchkov.lesson11;

import java.util.*;

public class MyHashSet<E> implements Set<E> {

    private class Node<E>{
        private E element;
        Node<E> next;
        Node(E element){
            this.element = element;
        }
    }

    static final int DEFAULT_CAPACITY = 10;

    Node<E>[] arr;
    int size = 0;

    MyHashSet(){
        this(DEFAULT_CAPACITY);
    }

    MyHashSet(int size){
        arr = new Node[size];
    }

    int getHashCode(Object o){
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
        }
        else {
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
        }

        size++;

        if (size >= arr.length * 0.75)
            resize();
        return true;
    }

    void resize(){
        Node<E>[] temp = arr;
        arr = new Node[arr.length*2];
        size = 0;

        for (int i = 0; i < temp.length; i++) {
            if (temp[i] != null) {
                for (Node node = temp[i]; node != null; node = node.next) {
                    add((E) node.element);
                }
            }
        }
    }

    @Override
    public boolean remove(Object o) {
        int index = getHashCode(o);

        if (arr[index] != null) {
            if (arr[index].element.equals(o)) {
                if (arr[index].next == null)
                    arr[index] = null;
                else
                    arr[index] = arr[index].next;
                size--;
                return true;
            }
            for (Node node = arr[index]; node.next != null; node = node.next) {
                if (node.next.element.equals(o)) {
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

    @Override
    public void clear() {
        arr = new Node[DEFAULT_CAPACITY];
        size = 0;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null) {
                for (Node node = arr[i]; node != null; node = node.next) {
                    sb.append(node.element).append(", ");
                }
            }
        }
        return sb.length() > 1 ? sb.substring(0,sb.length()-2) + "]": sb.toString() + "]";
    }
}
