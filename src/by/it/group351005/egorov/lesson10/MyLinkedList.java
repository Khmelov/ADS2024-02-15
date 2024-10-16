package by.it.group351005.egorov.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.Objects;

public class MyLinkedList<E> implements Deque<E> {

    private class Node{
        E _value;
        Node _next;
        Node _prev;

        Node(E value, Node next,Node prev) {
            this._value = value;
            this._next = next;
            this._prev = prev;
        }
    }

    private Node _head;
    private Node _tail;

    private int _size;

    MyLinkedList() {
        _head = null;
        _tail = null;
        _size = 0;
    }
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        Node dummy = _head;
        while (dummy != null) {
            stringBuilder.append(dummy._value).append(", ");
            dummy = dummy._next;
        }
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append(']');
        return stringBuilder.toString();

    }

    @Override
    public int size() {
        return _size;
    }

    @Override
    public boolean add(E e) {
        addLast(e);
        return true;
    }

    @Override
    public void addFirst(E e) {
        if (_head == null) {
            _tail = _head = new Node(e,null,null);
        }
        else {
            _head._prev = new Node(e, _head, null);
            _head = _head._prev;
        }
        _size++;
    }

    @Override
    public void addLast(E e) {
        if (_tail == null) {
            _head = _tail = new Node(e,null, null);
        }
        else {
            _tail._next = new Node(e, null, _tail);
            _tail = _tail._next;
        }
        _size++;
    }

    @Override
    public E removeFirst() {
        if (_head == null) {
            throw new IllegalStateException("Deque is empty");
        }
        E value = _head._value;
        _head = _head._next;
        _head._prev = null;
        _size--;
        return value;
    }

    @Override
    public E removeLast() {
        if (_tail == null) {
            throw new IllegalStateException("Deque is empty");
        }
        E value = _tail._value;
        _tail = _tail._prev;
        _tail._next = null;
        _size--;
        return value;
    }

    @Override
    public boolean remove(Object o) {
        Node dummy = _head;
        while (dummy != null && !Objects.equals(o,dummy._value)) {
            dummy = dummy._next;
        }
        if (dummy != null) {
            dummy._prev._next = dummy._next;
            dummy._next._prev = dummy._prev;
            _size--;
            return true;
        }
        return false;
    }

    public E remove(int index) {
        Node dummy = _head;
        for (int i = 0; i < index; i++) {
            dummy = dummy._next;
        }
        if (dummy != null) {
            dummy._prev._next = dummy._next;
            dummy._next._prev = dummy._prev;
            _size--;
            return dummy._value;
        }
        return null;
    }

    @Override
    public E remove() {
        return removeFirst();
    }

    @Override
    public E getFirst() {
        if (_head == null) {
            return null;
        }
        return _head._value;
    }

    @Override
    public E getLast() {
        if (_tail == null) {
            return null;
        }
        return _tail._value;
    }

    @Override
    public E element() {
        return getFirst();
    }

    @Override
    public E pollFirst() {
        if (_head == null) {
            return null;
        }
        E value = _head._value;
        _head = _head._next;
        _head._prev = null;
        _size--;
        return value;
    }

    @Override
    public E pollLast() {
        if (_tail == null) {
            return null;
        }
        E value = _tail._value;
        _tail = _tail._prev;
        _tail._next = null;
        _size--;
        return value;
    }

    @Override
    public E poll() {
        return pollFirst();
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
    public E peek() {
        return null;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
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
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        return null;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return false;
    }

    @Override
    public void clear() {

    }
}
