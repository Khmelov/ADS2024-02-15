package by.it.group351001.pavlyuchenko.lesson11;
import by.it.group351001.pavlyuchenko.lesson10.MyLinkedList;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
public class MyHashSet<E> implements Set<E> {
    public class Node<E> {
        public E data;
        public Node<E> next;

        public Node(E e, Node<E> nexti) {
            data = e;
            next = nexti;
        }

    }

    public class LinkedList<E> {
        public Node<E> head, tail;
      LinkedList() {
            tail = head = new Node<>(null, null);
      }

        public void AddCall(E e) {
            tail.next = new Node<>(e,null);
            tail = tail.next;
        }
    }


    int size = 0;

    static final int startSize = 20;

    static int capacity;

    LinkedList<E>[] arr;

    MyHashSet(int size) {
        arr = new LinkedList[size];
        capacity = size;
        for (int i = 0; i < capacity;i++)
            arr[i] = new LinkedList<>();
    }

    MyHashSet() {
        arr = new LinkedList[startSize];
        capacity = startSize;
        for (int i = 0; i < capacity;i++)
            arr[i] = new LinkedList<>();
    }

    public String toString() {
        StringBuilder line = new StringBuilder("[");
        int counter = size;
        Node<E> curr;
        for (int i = 0; i < capacity; i++) {
            curr = arr[i].head;
            while (curr.next != null) {
                curr = curr.next;
                counter--;
                line.append(curr.data);
                if (counter > 0)
                    line.append(", ");
            }
        }
        line.append("]");
        return line.toString();
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private int getHash(Object o) {
        return (o.hashCode() % capacity);
    }

    private void realloc() {
        int oldCapacity = capacity;
        capacity <<= 1;
        LinkedList<E>[] newArr = new LinkedList[capacity];
        for (int i = 0; i < capacity;i++)
            newArr[i] = new LinkedList<>();
        Node<E> curr;
        for (int i = 0; i < oldCapacity; i++) {
            curr = arr[i].head;
            while (curr.next != null) {
                curr = curr.next;
                newArr[getHash(curr.data)].AddCall(curr.data);
            }
        }
        arr = newArr;
    }

    public boolean contains(Object o) {
           Node<E> node = arr[getHash(o)].head;
           while (node.next != null) {
               node = node.next;
             if(o.equals(node.data))
                return true;
           }
        return false;
    }

    public boolean remove(Object o) {
        int j = getHash(o);
        Node<E> node = arr[j].head, prev;
            while (node.next != null) {
                prev = node;
                node = node.next;
                if(o.equals(node.data)) {
                    prev.next = node.next;
                    size--;
                    return true;
                }
            }
        return false;
    }

    public void clear() {
        for (int i = 0; i < capacity; i++) {
            arr[i].head.next = null;
            arr[i].tail = arr[i].head;
        }
        size = 0;
    }

    public boolean add(E e) {
        int j = getHash(e);
        Node<E> node = arr[j].head;
        while (node.next != null) {
            node = node.next;
            if(e.equals(node.data))
                return false;
        }
        if (size == capacity) {
            realloc();
            j = getHash(e);
        }
        arr[j].AddCall(e);
        size++;
        return true;
    }

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
