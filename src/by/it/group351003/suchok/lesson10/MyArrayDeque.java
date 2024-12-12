package by.it.group351003.suchok.lesson10;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class MyArrayDeque<E> implements Deque<E> {
    final ReentrantLock lock = new ReentrantLock();
    private int capacity = 16;
    private E[] elements = (E[]) new Object[capacity];
    private int itemCount = 0;
    private int headIndex = 0;
    private int tailIndex = 0;

    private void resize(){
        int updatedCapacity = capacity + (capacity >> 1);
        capacity = updatedCapacity;
        E[] updatedElements = (E[]) new Object[capacity];
        System.arraycopy(elements, 0, updatedElements, 0, itemCount);
        elements = updatedElements;
    }

    @Override
    public void addFirst(E e) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            if (itemCount + 1 == capacity) {
                resize();
            }
            System.arraycopy(elements, headIndex, elements, headIndex + 1, itemCount);
            elements[headIndex] = e;
            itemCount++;
            tailIndex++;
        }finally {
            lock.unlock();
        }
    }

    @Override
    public void addLast(E e) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            if (itemCount + 1 == capacity) {
                resize();
            }
            elements[tailIndex] = e;
            itemCount++;
            tailIndex = (tailIndex + 1) % capacity;
        }finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        final ReentrantLock lock = this.lock;
        final StringBuilder sb = new StringBuilder();
        lock.lock();
        try {
            sb.append("[");
            for (int i = headIndex; i < tailIndex - 1; i++) {
                sb.append(elements[i]).append(", ");
            }
            sb.append(elements[tailIndex - 1]).append("]");
        }finally {
            lock.unlock();
        }
        return sb.toString();
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
        E item = null;
        try{
            if(itemCount > 0){
                item = elements[headIndex];
                headIndex = (headIndex + 1) % capacity;
                itemCount--;
            }
        }finally {
            lock.unlock();
        }
        return item;
    }

    @Override
    public E pollLast() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        E item = null;
        try{
            if(itemCount > 0){
                tailIndex = (tailIndex - 1 + capacity) % capacity;
                item = elements[tailIndex];
                itemCount--;
            }
        }finally {
            lock.unlock();
        }
        return item;
    }

    @Override
    public E getFirst() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        E item = null;
        try{
            if(itemCount > 0){
                item = elements[headIndex];
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
                item = elements[tailIndex - 1];
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
        final ReentrantLock lock = this.lock;
        lock.lock();
        boolean result;
        try {
            int temp = itemCount;
            addLast(e);
            result = itemCount > temp;
        }finally {
            lock.unlock();
        }
        return result;
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
        return getFirst();
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
    public boolean remove(Object o) {
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
            private int currentIndex = 0;
            @Override
            public boolean hasNext() {
                return currentIndex < itemCount;
            }

            @Override
            public E next() {
                return elements[currentIndex++];
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