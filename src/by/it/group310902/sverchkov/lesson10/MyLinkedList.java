package by.it.group310902.sverchkov.lesson10;

import java.util.*;



public class MyLinkedList<E> implements Deque<E> {

    private class Node {
        Node next;
        Node prev;
        E data;

        Node(E data) {
            this.data = data;
        }
    }

    int Length;
    Node tail;
    Node head;

    MyLinkedList() {
        tail = head = null;
    }

    @Override
    public void addFirst(E e) {
        if (e == null) throw new NullPointerException();
        if (head == null) {
            head = new Node(e);
            tail = head;
            Length = 1;
            return;
        }
        Node temp = head;
        head = new Node(e);
        head.next = temp;
        temp.prev = head;
        Length++;
    }

    @Override
    public void addLast(E e) {
        if (e == null) throw new NullPointerException();
        if (tail == null) {
            tail = new Node(e);
            head = tail;
            Length = 1;
            return;
        }
        Node temp = tail;
        tail = new Node(e);
        tail.prev = temp;
        temp.next = tail;
        Length++;
    }

    @Override
    public boolean offerFirst(E e) {
        addFirst(e);
        return true;
    }

    @Override
    public boolean offerLast(E e) {
        addLast(e);
        return true;
    }

    @Override
    public E removeFirst() {
        if (Length == 0)
            throw new NoSuchElementException();

        E removed = head.data;

        if (Length == 1) {
            head = tail = null;
            Length--;
            return removed;
        }
        head = head.next;
        head.prev = null;
        Length--;
        return removed;
    }

    @Override
    public E removeLast() {
        if (Length == 0)
            throw new NoSuchElementException();

        E removed = tail.data;

        if (Length == 1) {
            head = tail = null;
            Length--;
            return removed;
        }

        tail = tail.prev;
        tail.next = null;
        Length--;
        return removed;
    }

    @Override
    public E pollFirst() {
        E removed = head.data;

        if (Length == 1) {
            head = tail = null;
            Length--;
            return removed;
        }

        head.next.prev = null;
        head = head.next;
        Length--;
        return removed;
    }

    @Override
    public E pollLast() {
        E removed = tail.data;

        if (Length == 1) {
            head = tail = null;
            Length--;
            return removed;
        }

        tail.prev.next = null;
        tail = tail.prev;
        Length--;
        return removed;
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
    public E peekFirst() {
        return head == null ? null : head.data;
    }

    @Override
    public E peekLast() {
        return tail == null ? null : tail.data;
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
    public boolean add(E e) {
        try {
            addLast(e);
        } catch (NullPointerException ex) {
            return false;
        }
        return true;
    }

    @Override
    public boolean offer(E e) {
        return add(e);
    }

    @Override
    public E remove() {
        return removeFirst();
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E element() {
        return getFirst();
    }

    @Override
    public E peek() {
        return peekFirst();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c == null) throw new NullPointerException();
        for (Object o: c){
            addLast((E) o);
        }
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c == null) throw new NullPointerException();
        for (Object o : c) {
            for (Node node = head; node != null; node = node.next) {
                if (node.data.equals(o)) {
                    if (Length == 1){
                        head = tail = null;
                        Length--;
                        break;
                    }
                    if (node.next != null) {
                        node.next.prev = node.prev;
                    }
                    if (node.prev != null) {
                        node.prev.next = node.next;
                    }
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (c == null) throw new NullPointerException();
        for (Object o : c) {
            for (Node node = head; node != null; node = node.next) {
                if (!node.data.equals(o)) {
                    if (Length == 1){
                        head = tail = null;
                        Length--;
                        break;
                    }
                    if (node.next != null) {
                        node.next.prev = node.prev;
                    }
                    if (node.prev != null) {
                        node.prev.next = node.next;
                    }
                    Length--;
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public void clear() {
        head = tail = null;
        Length = 0;
    }

    @Override
    public void push(E e) {

    }

    @Override
    public E pop() {
        return null;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) throw new NullPointerException();
        for (Node node = head; node != null; node = node.next) {
            if (node.data.equals(o)) {
                if (Length == 1){
                    head = tail = null;
                    Length--;
                    return true;
                }
                if (node.next != null) {
                    node.next.prev = node.prev;
                }
                if (node.prev != null) {
                    node.prev.next = node.next;
                }
                Length--;
                return true;
            }
        }
        return false;
    }

    public E remove(int index) {
        if (index < 0 || index > Length - 1) {
            throw new NoSuchElementException();
        }

        if (Length == 1){
            E data = head.data;
            head = tail = null;
            Length--;
            return data;
        }

        int i;
        Node node;

        int mid = Length / 2;
        if (index < mid) {
            node = head;
            i = 0;
            while (i != index) {
                node = node.next;
                i++;
            }

            if (node.prev != null) {
                node.prev.next = node.next;
            }
            if (node.next != null) {
                node.next.prev = node.prev;
            }

        } else {
            node = tail;
            i = Length - 1;
            while (i != index) {
                node = node.prev;
                i--;
            }
            if (node.next != null) {
                node.next.prev = node.prev;
            }
            if (node.prev != null) {
                node.prev.next = node.next;
            }
        }
        Length--;
        return node.data;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o: c){
            if (!contains(o))
                return false;
        }
        return true;
    }

    @Override
    public boolean contains(Object o) {
        for (Node node = head; node != null; node = node.next){
            if (node.data.equals(o)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return Length;
    }

    @Override
    public boolean isEmpty() {
        return Length == 0;
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

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("[");
        for (Node node = head; node != null; node = node.next) {
            string.append(node.data);
            if (node.next != null) {
                string.append(", ");
            }
        }
        string.append("]");
        return string.toString();
    }
}

