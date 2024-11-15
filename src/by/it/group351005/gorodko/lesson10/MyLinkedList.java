package by.it.group351005.gorodko.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

public class MyLinkedList<E> implements Deque<E> {

    private class Node {
        E data;
        Node prev;
        Node next;
    }

    private Node head = null;
    private Node tail = null;
    private int size = 0;

    public String toString() {
        String res = "[";
        Node temp = head;
        if (temp != null) {
            while (temp.next != null) {
                res += temp.data.toString() + ", ";
                temp = temp.next;
            }
            res += temp.data.toString();
        }
        res += "]";
        return res;
    }

    @Override
    public boolean add(E e) {
        Node node = new Node();
        node.data = e;
        node.prev = tail;
        tail = node;
        node.next = null;
        if (size == 0) {
            head = node;
        }
        else {
            node.prev.next = node;
        }
        size++;
        return true;
    }

    public E remove(int index) {
        if (index >= size) return null;
        int i = 0;
        Node temp = head;
        while (i != index) {
            temp = temp.next;
            i++;
        }
        if (temp == head) {
            head = temp.next;
            head.prev = null;
        }
        else if (temp == tail) {
            tail = temp.prev;
            tail.next = null;
        }
        else {
            temp.prev.next = temp.next;
            temp.next.prev = temp.prev;
        }
        size--;
        return temp.data;
    }

    @Override
    public boolean remove(Object o) {
        Node temp = head;
        while (temp != null && temp.data != o) {
            temp = temp.next;
        }
        if (temp == null) return false;
        if (temp == head) {
            head = temp.next;
            head.prev = null;
        }
        else if (temp == tail) {
            tail = temp.prev;
            tail.next = null;
        }
        else {
            temp.prev.next = temp.next;
            temp.next.prev = temp.prev;
        }
        size--;
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void addFirst(E e) {
        Node node = new Node();
        node.data = e;
        node.next = head;
        head = node;
        node.prev = null;
        if (size == 0) {
            tail = node;
        }
        else {
            node.next.prev = node;
        }
        size++;
    }

    @Override
    public void addLast(E e) {
        add(e);
    }

    @Override
    public E element() {
        return head.data;
    }

    @Override
    public E getFirst() {
        return head.data;
    }

    @Override
    public E getLast() {
        return tail.data;
    }

    @Override
    public E pollFirst() {
        return remove(0);
    }

    @Override
    public E pollLast() {
        return remove(size - 1);
    }

    @Override
    public E poll() {
        return remove(0);
    }




    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public E remove() {
        return null;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public void push(E e) {

    }

    @Override
    public E pop() {
        return null;
    }

    @Override
    public E peek() {
        return null;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean offer(E e) {
        return false;
    }

    @Override
    public boolean offerFirst(E e) {
        return false;
    }

    @Override
    public boolean offerLast(E e) {
        return false;
    }

    @Override
    public E removeFirst() {
        return null;
    }

    @Override
    public E removeLast() {
        return null;
    }

    @Override
    public E peekFirst() {
        return null;
    }

    @Override
    public E peekLast() {
        return null;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
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
    public Iterator<E> descendingIterator() {
        return null;
    }
}
