package by.it.group351003.egorov_egor.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

public class MyLinkedList<E> implements Deque<E> {

    private class ListNode<E> {
        E data;
        ListNode<E> next;
        ListNode<E> prev;
        ListNode(E data) {
            this.data = data;
            next = null;
            prev = null;
        }
    }

    ListNode<E> head;
    ListNode<E> tail;
    int _count;

    MyLinkedList(){
        _count = 0;
    }

    public String toString() {
        StringBuilder line = new StringBuilder();
        line.append('[');
        ListNode<E> buff = head;
        while(buff != null) {
            line.append(buff.data);
            if (buff.next != null)
                line.append(", ");
            buff = buff.next;
        }
        line.append(']');
        return line.toString();
    }

    @Override
    public boolean add(E e) {
        addLast(e);
        return true;
    }

    public E remove(int index) {
        if (index > -1 && index < _count) {
            ListNode<E> buff = head;
            while (index-- != 0)
                buff = buff.next;

            if (buff.prev != null)
                buff.prev.next = buff.next;
            else
                head = buff.next;

            if (buff.next != null)
                buff.next.prev = buff.prev;
            else
                tail = buff.prev;
            _count--;
            return buff.data;
        }
        return null;
    }

    @Override
    public boolean remove(Object o) {
        ListNode<E> buff = head;
        for (int i = 1; i <= _count; i++) {
            if (o.equals(buff.data)){
                remove(i-1);
                return true;
            }
            buff = buff.next;
        }
        return false;
    }

    @Override
    public int size() {
        return _count;
    }

    @Override
    public void addFirst(E e) {
        ListNode<E> newNode = new ListNode<>(e);
        if (_count++ == 0) {
            head = newNode;
            tail = head;
        } else {
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
        }
    }

    @Override
    public void addLast(E e) {
        ListNode<E> newNode = new ListNode<>(e);
        if (_count++ == 0) {
            head = newNode;
            tail = head;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    @Override
    public E getFirst() {
        return _count == 0 ? null : head.data;
    }

    @Override
    public E getLast() {
        return _count == 0 ? null : tail.data;
    }

    @Override
    public E element() {
        return getFirst();
    }

    @Override
    public E pollFirst() {
        if (_count == 0)
            return null;
        E buff = head.data;
        remove(0);
        return buff;
    }

    @Override
    public E pollLast() {
        if (_count == 0)
            return null;
        E buff = tail.data;
        remove(_count-1);
        return buff;
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
    public E remove() {
        return null;
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