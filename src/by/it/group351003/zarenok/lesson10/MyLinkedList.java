package by.it.group351003.zarenok.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantLock;


class Node<E> {
    Node prev;
    Node next;
    E val;
    Node(E val){
        this.val = val;
    }
}

public class MyLinkedList<E> implements Deque<E> {
    private final ReentrantLock lock = new ReentrantLock();
    private Node<E> head;
    private Node<E> tail;
    private int itemCount = 0;


    @Override
    public void addFirst(E e) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            Node temp = new Node(e);
            if(itemCount == 0){
                head = temp;
                tail = temp;
            }else {
                temp.prev = null;
                temp.next = head;
                head.prev = temp;
                head = temp;
            }
            itemCount++;
        }finally {
            lock.unlock();
        }
    }

    @Override
    public void addLast(E e) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            Node temp = new Node(e);
            if(itemCount == 0){
                head = temp;
                tail = temp;
            }else {
                temp.prev = tail;
                tail.next = temp;
                temp.next = null;
                tail = temp;
            }
            itemCount++;
        }finally {
            lock.unlock();
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
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            E value = head.val;
            head = head.next;
            head.prev = null;
            itemCount--;
            return value;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        try{
            Node temp = head;
            while(temp.next != null){
                sb.append(temp.val).append(", ");
                temp = temp.next;
            }
            sb.append(temp.val).append("]");
        }finally {
            lock.unlock();
        }
        return sb.toString();
    }

    @Override
    public E pollLast() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        E value = null;
        try {
            value = tail.val;
            tail = tail.prev;
            tail.next = null;
            itemCount--;
        } finally {
            lock.unlock();
        }
        return value;
    }

    @Override
    public E getFirst() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        E item = null;
        try{
            if(itemCount > 0){
                item = head.val;
            }
        }finally {
            lock.unlock();
        }
        return item;
    }

    @Override
    public E getLast() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        E item = null;
        try{
            if(itemCount > 0){
                item = tail.val;
            }
        }finally {
            lock.unlock();
        }
        return item;
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
    public boolean add(E e) {
        final int temp = itemCount;
        addLast(e);
        return itemCount > temp;
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
        return pollFirst();
    }

    @Override
    public E element() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        E item = null;
        try {
            if(itemCount > 0){
                item = head.val;
            }
        }finally {
            lock.unlock();
        }
        return item;
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
    public E remove(int index){
        final ReentrantLock lock = this.lock;
        lock.lock();
        E item = null;
        try {
            Node<E> temp;
            if (index == 0) {
                pollFirst();
            } else if (index == itemCount - 1) {
                pollLast();
            } else {
                temp = head;
                for (int i = 0; i < index; i++) {
                    temp = temp.next;
                }
                item = temp.val;
                temp.prev.next = temp.next;
                temp.next.prev = temp.prev;
                temp = head;
            }
            itemCount--;
        } finally {
            lock.unlock();
        }
        return item;
    }

    @Override
    public boolean remove(Object o) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        int prevCount = itemCount;
        try {
            Node<E> temp = head;
            while (temp != null) {
                if (temp.val.equals(o)) {
                    if(temp == head){
                        pollFirst();
                    }else if(temp == tail){
                        pollLast();
                    }else{
                        temp.prev.next = temp.next;
                        temp.next.prev = temp.prev;
                        itemCount--;
                    }
                    return true;
                }
                temp = temp.next;
            }
        } finally {
            lock.unlock();
        }
        return false;
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
        return itemCount;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> current = head;
            @Override
            public boolean hasNext() {
                return current.next != null;
            }

            @Override
            public E next() {
                E item = current.val;
                current = current.next;
                return item;
            }
        };
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