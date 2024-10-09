package by.it.group351005.bitno.lesson10;

//import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

public class MyArrayDeque<E> implements Deque<E>
{
    @SuppressWarnings("unchecked")
    private E[] queueElems = (E[]) new Object[3];
    private int size = 0;
    private int capacity = 3;
    private int firstElemPos = 2;
    @Override
    public String toString(){
        StringBuilder str = new StringBuilder("[");
        final int LAST_ELEM_INDEX = firstElemPos + size - 1;
        for (int i = firstElemPos; i < LAST_ELEM_INDEX; i++) {
            str.append(queueElems[i].toString()).append(", ");
        }
        str.append(queueElems[LAST_ELEM_INDEX].toString()).append("]");
        return str.toString();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void addFirst(E e) {
        if (firstElemPos - 1 < 0)
            resizeQueue();
        firstElemPos -= 1;
        queueElems[firstElemPos] = e;
        size++;
    }

    @Override
    public void addLast(E e) {
        if (firstElemPos + size >= queueElems.length)
            resizeQueue();
        queueElems[firstElemPos + size] = e;
        size++;
    }

    @Override
    public boolean add(E e) {
        if (firstElemPos + size >= queueElems.length)
            resizeQueue();
        queueElems[firstElemPos + size] = e;
        size++;
        return true;
    }

    @Override
    public E element() {
        return queueElems[firstElemPos];
    }

    @Override
    public E getFirst() {
        return queueElems[firstElemPos];
    }

    @Override
    public E getLast() {
        return queueElems[firstElemPos + size - 1];
    }

    @Override
    public E poll() {
        E answer = null;
        if (size > 0) {
            answer = queueElems[firstElemPos];
            firstElemPos++;
            size--;
        }
        return answer;
    }

    @Override
    public E pollFirst() {
        E answer = null;
        if (size > 0) {
            answer = queueElems[firstElemPos];
            firstElemPos++;
            size--;
        }
        return answer;
    }

    @Override
    public E pollLast() {
        E answer = null;
        if (size > 0) {
            answer = queueElems[firstElemPos + size - 1];
            size--;
        }
        return answer;
    }

    private void resizeQueue()
    {
        capacity *= 2;
        @SuppressWarnings("unchecked")
        E[] newArray = (E[]) new Object[capacity];
        int destPos = capacity / 2  - size / 2;
        System.arraycopy(queueElems, firstElemPos, newArray, destPos, size);
        queueElems = newArray;
        firstElemPos = destPos;
    }
///////////////////////////////////////////////////
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
