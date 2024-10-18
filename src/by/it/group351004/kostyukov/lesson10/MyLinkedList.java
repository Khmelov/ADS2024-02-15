package by.it.group351004.kostyukov.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
public class MyLinkedList<E> implements Deque<E> {

    private class LNode<E> {
        E data;
        LNode<E> prev;
        LNode<E> next;
        LNode(E data) {
            this.data = data;
            prev = null;
            next = null;
        }
    }

    LNode<E> head;
    LNode<E> tail;
    int size;

    MyLinkedList(){
        size = 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        LNode<E> curr = head;
        while(curr != null) {
            sb.append(curr.data);
            sb.append(", ");
            curr = curr.next;
        }
        sb.delete(sb.length()-2, sb.length()).append(']');
        return sb.toString();
    }

    @Override
    public boolean add(E e) {
        addLast(e);
        return true;
    }

    public E remove(int index) {
        if (index > -1 && index < size) {
            LNode<E> curr = head;
            while (index-- != 0)
                curr = curr.next;

            if (curr.prev != null)
                curr.prev.next = curr.next;
            else
                head = curr.next;

            if (curr.next != null)
                curr.next.prev = curr.prev;
            else
                tail = curr.prev;

            size--;
            return curr.data;
        }
        return null;
    }

    @Override
    public boolean remove(Object o) {
        LNode<E> curr = head;
        for (int i = 0; i < size; i++) {
            if (o.equals(curr.data)){
                remove(i);
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

    @Override
    public void addFirst(E e) {
        LNode<E> newNode = new LNode<>(e);
        if (size++ == 0) {
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
        LNode<E> newNode = new LNode<>(e);
        if (size++ == 0) {
            head = newNode;
            tail = head;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    @Override
    public E element() {
        return getFirst();
    }

    @Override
    public E getFirst() {
        return size == 0 ? null : head.data;
    }

    @Override
    public E getLast() {
        return size == 0 ? null : tail.data;
    }

    @Override
    public E poll() {
        return pollFirst();
    }
    @Override
    public E pollFirst() {
        if (size == 0)
            return null;
        E buff = head.data;
        remove(0);
        return buff;
    }

    @Override
    public E pollLast() {
        if (size == 0)
            return null;
        E buff = tail.data;
        remove(size-1);
        return buff;
    }
    ////////////////////////////////////////////////////////

    @Override
    public int size() {
        return size;
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
