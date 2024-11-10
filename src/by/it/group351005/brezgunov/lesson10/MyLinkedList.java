package by.it.group351005.brezgunov.lesson10;

import java.util.*;

public class MyLinkedList<E> implements List<E> {
    class Node {
        E val;
        Node next;
        Node prev;
        Node(E val, Node next, Node prev) {
            this.val = val;
            this.next = next;
            this.prev = prev;
        }
    }
    private Node head;
    private Node tail;
    @Override
    public String toString() {
        Node curr = head;
        StringBuilder sb = new StringBuilder("[");
        while (curr.next != null) {
            sb.append(curr.val.toString()).append(", ");
            curr = curr.next;
        }
        sb.append(curr.val.toString()).append("]");
        return sb.toString();
    }
    @Override
    public int size() {
        int counter = 0;
        Node curr = head;
        while (curr != null) {
            curr = curr.next;
            counter++;
        }
        return counter;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
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
        if (tail == null) {
            head = new Node(e, null, null);
            tail = head;
        } else {
            tail.next = new Node(e, null, tail);
            tail = tail.next;
        }
        return true;
    }

    public E element() {
        return head.val;
    }

    @Override
    public boolean remove(Object o) {
        Node curr = head;
        while (!curr.val.equals(o) && curr.next != null) {
            curr = curr.next;
        }
        if (curr.next == null)
            return false;
        curr.next.prev = curr.prev;
        curr.prev.next = curr.next;
        return true;
    }

    @Override
    public void addLast(E e) {
        add(e);
    }

    public E poll() {
        return pollFirst();
    }

    public E pollLast() {
        Node temp = tail;
        tail = tail.prev;
        tail.next = null;
        return temp.val;
    }

    public E pollFirst() {
        Node temp = head;
        head = head.next;
        head.prev = null;
        return temp.val;
    }

    @Override
    public void addFirst(E e) {
        head.prev = new Node(e, head, null);
        head = head.prev;
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
    public boolean addAll(int index, Collection<? extends E> c) {
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
    public void clear() {

    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public E set(int index, E element) {
        return null;
    }

    @Override
    public E getFirst() {
        return element();
    }

    public E getLast() {
        return tail.val;
    }

    @Override
    public void add(int index, E element) {

    }

    @Override
    public E remove(int index) {
        Node curr = head;
        for (int i = 0; i < index && curr.next != null; i++) {
            curr = curr.next;
        }
        if (curr.next == null) {
            throw new IndexOutOfBoundsException();
        }
        curr.prev.next = curr.next;
        curr.next.prev = curr.prev;
        return curr.val;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }
}
