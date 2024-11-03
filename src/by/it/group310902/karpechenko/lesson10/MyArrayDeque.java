package by.it.group310902.karpechenko.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

public class MyArrayDeque<E> implements Deque<E> {
    private E[] elem;
    private final int minSize = 8;
    private int size = 0;
    private int firstInd = 0;
    public MyArrayDeque(){
       elem = (E[]) new Object[minSize];
    }
    public MyArrayDeque(int x){
        elem = (E[]) new Object[x];
    }
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for(int i = firstInd; i < firstInd + size; i++) {
            sb.append(elem[i].toString());
            if (i < firstInd + size - 1){
                sb.append(", ");
            }
        }
        sb.append(']');
        return sb.toString();
    }

    @Override
    public void addFirst(E e) {
        if(firstInd == 0){
            resizeMyDeque();
        }
        size++;
        firstInd--;
        elem[firstInd] = e;
    }

    @Override
    public void addLast(E e) {
        add(e);
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
        E out = null;
        if (size > 0){
            out = elem[firstInd];
            firstInd++;
            size--;
        }
        return out;
    }

    @Override
    public E pollLast() {
        E out = null;
        if (size > 0) {
            out = elem[firstInd + size - 1];
            size--;
        }
        return out;
    }

    @Override
    public E getFirst() {
        return element();
    }

    @Override
    public E getLast() {
        return elem[firstInd + size - 1];
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
        if (firstInd + size >= elem.length){
            resizeMyDeque();
        }
        size++;
        elem[firstInd + size - 1] = e;
        return true;
    }
    private void resizeMyDeque(){
        E[] tempElements = (E[]) new Object[elem.length * 2];
        System.arraycopy(elem, firstInd, tempElements,elem.length - size/2, size);
        firstInd = elem.length - size/2;
        elem = tempElements;
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
        return elem[firstInd];
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

    public int size(){
        return size;
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
