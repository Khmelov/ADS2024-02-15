package by.it.group351001.orlovich.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

public class MyLinkedList<E> implements Deque<E> {

    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////

    /*toString()
    add(E element)
    remove(int)
    remove(E element)
    size()
    addFirst(E element)
    addLast(E element)
    element()
    getFirst()
    getLast()
    poll()
    pollFirst()
    pollLast()*/

    private Node<E> head;
    private Node<E> tail;
    private int currentSize;
    public MyLinkedList() {
        head = null;
        tail = null;
        currentSize = 0;
    }

    @Override
    public String toString() {
        StringBuilder newString = new StringBuilder();

        newString.append('[');

        Node<E> temp = head;

        while (temp != null) {
            newString.append(temp.data.toString());
            if (temp.next != null) {
                newString.append(", ");
            }
            temp = temp.next;
        }

        newString.append(']');

        return newString.toString();
    }

    @Override
    public boolean add(E e) {
        addLast(e);
        return true;
    }

    @Override
    public void addFirst(E e) {
        Node<E> temp = new Node<>(e);

        if (currentSize == 0) {
            head = temp;
            tail = temp;
        } else {
            temp.next = head;
            head.prev = temp;
            head = temp;
        }

        currentSize++;
    }

    @Override
    public void addLast(E e) {
        Node<E> temp = new Node<>(e);

        if (currentSize == 0) {
            head = temp;
            tail = temp;
        } else {
            tail.next = temp;
            temp.prev = tail;
            tail = temp;
        }

        currentSize++;
    }

    public E remove(int index) {
        if ((index < 0) || (index >= currentSize)) {
            return null;
        }

        Node<E> temp = head;

        for (int i = 0; i != index; i++) {
            temp = temp.next;
        }

        if (temp != head) {
            temp.prev.next = temp.next;
        } else {
            head = head.next;
        }

        if (temp != tail) {
            temp.next.prev = temp.prev;
        } else {
            tail = tail.prev;
        }

        currentSize--;
        return temp.data;
    }

    @Override
    public boolean remove(Object o) {
        Node<E> temp = head;

        while (temp != null) {
            if (temp.data.equals(o)) {

                if (temp != head) {
                    temp.prev.next = temp.next;
                } else {
                    head = head.next;
                }

                if (temp != tail) {
                    temp.next.prev = temp.prev;
                } else {
                    tail = tail.prev;
                }

                currentSize--;
                return  true;
            }
            temp = temp.next;
        }

        return false;
    }

    @Override
    public int size() {
        return currentSize;
    }

    @Override
    public E element() {
        return getFirst();
    }

    @Override
    public E getFirst() {
        if (currentSize == 0) {
            return null;
        }

        return head.data;
    }

    @Override
    public E getLast() {
        if (currentSize == 0) {
            return null;
        }

        return tail.data;
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E pollFirst() {
        if (currentSize == 0) {
            return null;
        }

        currentSize--;

        Node<E> temp = head;

        head = head.next;
        head.prev = null;

        return temp.data;
    }

    @Override
    public E pollLast() {
        if (currentSize == 0) {
            return null;
        }

        currentSize--;

        Node<E> temp = tail;

        tail = tail.prev;
        tail.next = null;

        return temp.data;
    }


    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Опциональные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////


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
    public boolean offer(E e) {
        return false;
    }

    @Override
    public E remove() {
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
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public boolean isEmpty() {
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

    private class Node<E> {
        public E data;
        public Node<E> next;
        public Node<E> prev;

        public Node(E e) {
            data = e;
            next = null;
            prev = null;
        }
    }
}