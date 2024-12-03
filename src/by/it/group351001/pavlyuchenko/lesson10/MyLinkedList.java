package by.it.group351001.pavlyuchenko.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

public class MyLinkedList<E> implements Deque<E> {

    public class Node<E> {
        public E data;
        public Node<E> next, prev;

        public Node(E e, Node previ, Node nexti) {
            data = e;
            next = nexti;
            prev = previ;
        }
    }

    private Node<E> head, tail;

    private int length;

    public MyLinkedList() {
        tail = head = new Node<>(null, null, null);
        length = 0;
    }

    public String toString() {
        StringBuilder newString = new StringBuilder();

        newString.append('[');

        Node<E> temp = head.next;

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

    public void addLast(E e) {
        Node<E> temp = new Node<>(e, tail, null);
         tail.next = temp;
         tail = temp;
         length++;
    }

    public void addFirst(E e) {
        Node<E> temp = new Node<>(e, head,head.next);
        if (length != 0) {
           head.next.prev = temp;
        }
        head.next = temp;
        length++;
    }

    public boolean add(E e) {
        addLast(e);
        return true;
    }

    public int size() {
        return length;
    }

    private void delete(Node<E> temp) {
        if (temp != tail) {
            temp.next.prev = temp.prev;
        }
        temp.prev.next = temp.next;
    }

    public E remove(int index) {
        if (index >= 0 && index < length) {
            Node<E> temp = head.next;
            for (int i= 0; i < index; i++) {
                temp = temp.next;
            }
            delete(temp);
            length--;
            return temp.data;
        }
        return null;
    }

    public boolean remove(Object o) {
        Node<E> temp = head.next;
        while (temp != null) {
            if (temp.data.equals(o)) {
               delete(temp);
               length--;
               return true;
            }
            temp = temp.next;
        }
        return false;
    }

    public E getFirst() {
        if (length == 0) {
            return null;
        }

        return head.next.data;
    }

    public E getLast() {
        if (length == 0) {
            return null;
        }

        return tail.data;
    }

    public E element() {
        return getFirst();
    }

    public E pollFirst() {
        if (length == 0) {
            return null;
        }

        length--;

        Node<E> temp = head.next;

        head.next = head.next.next;
        head.next.prev = head;

        return temp.data;
    }

    public E pollLast() {
        if (length == 0) {
            return null;
        }

        length--;

        Node<E> temp = tail;

        tail = tail.prev;
        tail.next = null;

        return temp.data;
    }

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
