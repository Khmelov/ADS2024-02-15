package by.it.group351001.radzetskii.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

public class MyArrayDeque<E> implements Deque<E> {
    private final int cap=4;
    private  E[] MyDeque;
    private int head,tail;

    private int currSize=0;

    private void resize(){
        E[] newDeque=(E[]) new Object[currSize*2];
        System.arraycopy(MyDeque,tail,newDeque,0,currSize-tail);

        if (tail>0){
            System.arraycopy(MyDeque,0,newDeque,currSize-tail,tail);
        }

        head=0;
        tail=currSize;
        MyDeque=newDeque;
    }
    private boolean isFull(){
        if (currSize==MyDeque.length){
            return true;
        }
        return false;
    }

    public MyArrayDeque(){
        MyDeque = (E[]) new Object[cap];
        head=0;
        tail=0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        for (int i = 0; i < currSize; i++) {
            int index = (head + i) % MyDeque.length;
            sb.append(MyDeque[index]);
            if (i < currSize - 1) {
                sb.append(", ");
            }
        }

        sb.append("]");
        return sb.toString();
    }


    @Override
    public boolean add(E e) {
        if (e==null){
            return false;
        }
        addLast(e);
        return true;
    }

    @Override
    public void addFirst(E e) {
        if (isFull()) {
            resize();
        }
        head = (head - 1 + MyDeque.length) % MyDeque.length;
        MyDeque[head] = e;
        currSize++;
    }

    @Override
    public void addLast(E e) {
        if (isFull()) {
            resize();
        }
        MyDeque[tail] = e;
        tail = (tail + 1) % MyDeque.length;
        currSize++;
    }

    @Override
    public E element() {
        return getFirst();
    }

    @Override
    public E getFirst() {
        if (currSize == 0) {
            return null;
        }
        return MyDeque[head];
    }

    @Override
    public E getLast() {
        if (currSize == 0) {
            return null;
        }
        if (tail==0) {
            return MyDeque[MyDeque.length - 1];
        }
        return MyDeque[tail-1];
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E pollLast() {
        if (currSize==0) {
            return null;
        }
        tail = (tail - 1 + MyDeque.length) % MyDeque.length;
        E value = (E) MyDeque[tail];
        MyDeque[tail] = null;
        currSize--;
        return value;
    }

    @Override
    public E pollFirst() {
        if (currSize==0) {
            return null;
        }
        E value = (E) MyDeque[head];
        MyDeque[head] = null;
        head = (head + 1) % MyDeque.length;
        currSize--;
        return value;
    }

    //interface methods

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
    public boolean removeLastOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
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
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public int size() {
        return currSize;
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
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
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
}
