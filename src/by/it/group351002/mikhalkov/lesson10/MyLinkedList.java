package by.it.group351002.mikhalkov.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

public class MyLinkedList<E> implements Deque<E> {
    class LinkedNode<E> {
        E element;
        LinkedNode<E> prev;
        LinkedNode<E> next;

        public LinkedNode(E element) {
            this.element = element;
        }
    }

    LinkedNode<E> head;
    LinkedNode<E> tail;
    int size;

    MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        LinkedNode<E> temp = head;
        for (int i = 0; i < size; i++) {
            sb.append(temp.element);
            if (i < size - 1) {
                sb.append(", ");
            }
            temp = temp.next;
        }
        sb.append(']');
        return sb.toString();
    }

    @Override
    public boolean add(E e) {
        addLast(e);
        return true;
    }

    public E remove(int index) {
        if (index < 0 || index >= size) {
            return null;
        }

        LinkedNode<E> temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        E e = temp.element;

        if (temp.prev != null) {
            temp.prev.next = temp.next;
        } else {
            head = temp.next;
        }

        if (temp.next != null) {
            temp.next.prev = temp.prev;
        } else {
            tail = temp.prev;
        }

        size--;

        return e;
    }

    @Override
    public boolean remove(Object o) {
        LinkedNode<E> temp = head;
        int index = 0;
        while (temp != null) {
            if (temp.element.equals(o)) {
                remove(index);
                return true;
            }
            index++;
            temp = temp.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void addFirst(E e) {
        LinkedNode<E> node = new LinkedNode<>(e);
        if (head != null) {
            node.next = head;
            head.prev = node;
        }
        head = node;

        if (tail == null) {
            tail = node;
        }

        size++;
    }

    @Override
    public void addLast(E e) {
        LinkedNode<E> node = new LinkedNode<>(e);
        if (tail != null) {
            tail.next = node;
            node.prev = tail;
        }
        tail = node;

        if (head == null) {
            head = node;
        }

        size++;
    }

    @Override
    public E element() {
        return getFirst();
    }

    @Override
    public E getFirst() {
        if (size == 0) {
            return null;
        }
        return head.element;
    }

    @Override
    public E getLast() {
        if (size == 0) {
            return null;
        }
        return tail.element;
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E pollFirst() {
        if (size == 0) {
            return null;
        }
        E e = head.element;
        head = head.next;

        if (head != null) {
            head.prev = null;
        }
        else {
            tail = null;
        }

        size--;
        return e;
    }

    @Override
    public E pollLast() {
        if (size == 0) {
            return null;
        }
        E e = tail.element;
        tail = tail.prev;

        if (tail != null) {
            tail.next = null;
        }
        else {
            head = null;
        }

        size--;
        return e;
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
}