package by.it.group351002.matsuev.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

public class MyLinkedList<E> implements Deque<E> {

    class Node{
        E data;
        Node prev;
        Node next;
        public Node(E e)
        {
            this.data = e;
            this.prev = null;
            this.next = null;
        }
    }

    Node head;
    Node tail;

    public MyLinkedList(){
        this.head = null;
        this.tail = null;
    }

    @Override
    public void addFirst(E element) {
        Node temp = new Node(element);
        if (head == null) {
            head = temp;
            tail = temp;
        }
        else {
            temp.next = head;
            head.prev = temp;
            head = temp;
        }
    }

    @Override
    public void addLast(E element){
        Node temp = new Node(element);
        if (tail == null) {
            head = temp;
            tail = temp;
        }
        else {
            tail.next = temp;
            temp.prev = tail;
            tail = temp;
        }
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
    public E pollFirst() {
        E deleted;
        if (head != null)
            deleted = head.data;
        else
            deleted = null;
        remove(0);
        return deleted;
    }

    @Override
    public E pollLast() {
        E deleted;
        int count = 0;
        Node current = head;
        if (tail != null) {
            deleted = tail.data;
            while (current != null) {
                current = current.next;
                count++;
            }
            count--;
            remove(count);
        }
        else
            deleted = null;
        return deleted;
    }

    @Override
    public E getFirst() {
        if (head != null)
            return head.data;
        return null;
    }

    @Override
    public E getLast() {
        if (tail != null)
            return tail.data;
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
    public boolean add(E element){
        Node temp = new Node(element);
        if (tail == null) {
            head = temp;
            tail = temp;
        }
        else {
            tail.next = temp;
            temp.prev = tail;
            tail = temp;
        }
        return true;
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
    public E poll() {
        E deleted;
        if (head != null)
            deleted = head.data;
        else
            deleted = null;
        remove(0);
        return deleted;
    }

    @Override
    public E element() {
        if (head != null)
            return head.data;
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
    public boolean remove(Object o){
        if (head == null || o == null) {
            return false;
        }

        if (o.equals(head.data)) {
            if (head == tail) {
                head = null;
                tail = null;
                return true;
            }

            Node temp = head;
            head = head.next;
            head.prev = null;
            temp.next = null;
            return true;
        }

        Node current = head;

        while (current != null) {
            if(o.equals(current.data))
                break;
            current = current.next;
        }

        if (current == null) {
            return false;
        }

        if (current == tail) {
            if (head == tail) {
                head = null;
                tail = null;
                return true;
            }

            Node temp = tail;
            tail = tail.prev;
            tail.next = null;
            temp.prev = null;
            return true;
        }

        current.prev.next = current.next;
        current.next.prev = current.prev;
        current.prev = null;
        current.next = null;
        return true;
    }

    public E remove(int pos) {
        if (head == null) {
            return null;
        }
        E tempdata;
        if (pos == 0) {
            tempdata = head.data;
            if (head == tail) {
                head = null;
                tail = null;
                return tempdata;
            }

            Node temp = head;
            head = head.next;
            head.prev = null;
            temp.next = null;
            return tempdata;
        }

        Node current = head;
        int count = 0;

        while (current != null && count != pos) {
            current = current.next;
            count++;
        }

        if (current == null) {
            return null;
        }

        if (current == tail) {
            tempdata = tail.data;
            if (head == tail) {
                head = null;
                tail = null;
                return tempdata;
            }
            Node temp = tail;
            tail = tail.prev;
            tail.next = null;
            temp.prev = null;
            return tempdata ;
        }
        tempdata = current.data;
        current.prev.next = current.next;
        current.next.prev = current.prev;
        current.prev = null;
        current.next = null;
        return tempdata;
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
    public int size() {
        if (head == null)
            return 0;
        Node current = head;
        int count = 0;
        while (current != null) {
            current = current.next;
            count++;
        }
        return count;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        Node current = head;
        while (current != null) {
            sb.append(current.data);
            current = current.next;
            if (current != null)
                sb.append(", ");
        }
        sb.append(']');
        return sb.toString();
    }
}
