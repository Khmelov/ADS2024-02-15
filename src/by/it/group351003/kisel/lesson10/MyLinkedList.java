package by.it.group351003.kisel.lesson10;

import java.util.*;

public class MyLinkedList<E> implements Deque<E> {

    static class DoubleNode<E> {
        public E data;
        public DoubleNode<E> next;
        public DoubleNode<E> prev;

        public DoubleNode(E data) {
            this.data = data;
        }
    }

    DoubleNode<E> _head;
    DoubleNode<E> _tail;
    int Count;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        DoubleNode<E> tempHead = _head;
        while (tempHead.next != null) {
            sb.append(tempHead.data);
            sb.append(", ");
            tempHead = tempHead.next;
        }
        sb.append(tempHead.data);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean add(E e) {
        DoubleNode<E> node = new DoubleNode<>(e);

        if (_head == null) _head = node;
        else {
            _tail.next = node;
            node.prev = _tail;
        }
        _tail = node;
        Count++;
        return true;
    }

    @Override
    public void addFirst(E e) {
        DoubleNode<E> node = new DoubleNode<>(e);
        node.next = _head;
        if (Count == 0) {
            _head = _tail = node;
        } else {
            _head.prev = node;
            _head = node;
        }
        Count++;
    }

    @Override
    public void addLast(E e) {
        add(e);
    }

    @Override
    public E pollFirst() {
        if (Count == 0) return null;
        E output = _head.data;
        if (Count == 1) _head = _tail = null;
        else {
            _head = _head.next;
            _head.prev = null;
        }
        Count--;
        return output;
    }

    @Override
    public E pollLast() {
        if (Count == 0) return null;
        E output = _tail.data;
        if (Count == 1) _head = _tail = null;
        else {
            _tail = _tail.prev;
            _tail.next = null;
        }
        Count--;
        return output;
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E getFirst() {
        if (Count == 0) return null;
        return _head.data;
    }

    @Override
    public E getLast() {
        if (Count == 0) return null;
        return _tail.data;
    }

    @Override
    public E element() {
        return getFirst();
    }

    @Override
    public int size() {
        return Count;
    }

    @Override
    public boolean remove(Object o) {
        DoubleNode<E> tempHead = _head;
        while (tempHead != null) {
            if (Objects.equals(o, tempHead.data)) {
                if (tempHead.next != null) tempHead.next.prev = tempHead.prev;
                else _tail = tempHead.prev;

                if (tempHead.prev != null) tempHead.prev.next = tempHead.next;
                else _head = tempHead.next;
                Count--;
                return true;
            }
            tempHead = tempHead.next;
        }
        return false;
    }

    public E remove(int index) {
        DoubleNode<E> tempHead = _head;
        if (index > -1 && index < Count) {
            int count = 0;
            while (count++ < index) {
                tempHead = tempHead.next;
            }
            if (tempHead.next != null) tempHead.next.prev = tempHead.prev;
            else _tail = tempHead.prev;

            if (tempHead.prev != null) tempHead.prev.next = tempHead.next;
            else _head = tempHead.next;
            Count--;
            return tempHead.data;
        }
        return null;
    }

    //////////////////////////////////////////

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
    public Iterator<E> descendingIterator() {
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
    public void push(E e) {

    }

    @Override
    public E pop() {
        return null;
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
}
