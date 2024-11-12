package by.it.group310901.mikhaylove.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

public class MyArrayDeque<E> implements Deque<E> {

    private final int SIZE = 8;
    private final int STEP = 4;
    private final int CUT = 4;
    private E[] array = (E[]) new Object[SIZE];
    private int first = 0;
    private int last = 0;

    private void resize(boolean extend) {
        E[] newArray;
        if (extend) {
            newArray = (E[]) new Object[array.length + STEP];
        } else {
            newArray = (E[]) new Object[array.length - STEP];
        }
        System.arraycopy(array, first, newArray, STEP / 2, size());
        last = size() + STEP / 2;
        first = STEP / 2;
        array = newArray;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("[");
        for (int i = first; i < last; i++) {
            result.append(array[i].toString());
            if (i < last - 1) {
                result.append(", ");
            }
        }
        result.append("]");
        return result.toString();
    }

    @Override
    public int size() {
        return last - first;
    }

    @Override
    public boolean add(E e) {
        try {
            addLast(e);
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    @Override
    public void addFirst(E e) {
        if (first == 0) {
            resize(true);
        }
        array[--first] = e;
    }

    @Override
    public void addLast(E e) {
        if (last == array.length) {
            resize(true);
        }
        array[last++] = e;
    }

    @Override
    public E element() {
        return getFirst();
    }

    @Override
    public E getFirst() {
        return array[first];
    }

    @Override
    public E getLast() {
        return array[last - 1];
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E pollFirst() {
        E tmp = array[first];
        if (tmp != null) {
            array[first++] = null;
            if (array.length > SIZE && size() < array.length / CUT) {
                resize(false);
            }
        }
        return tmp;
    }

    @Override
    public E pollLast() {
        E tmp = array[last - 1];
        if (tmp != null) {
            array[--last] = null;
            if (array.length > SIZE && size() < array.length / CUT) {
                resize(false);
            }
        }
        return tmp;
    }

    //========== not required ================

    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isEmpty'");
    }

    @Override
    public Object[] toArray() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toArray'");
    }

    @Override
    public <T> T[] toArray(T[] a) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toArray'");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'containsAll'");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeAll'");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'retainAll'");
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clear'");
    }

    @Override
    public boolean offerFirst(E e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'offerFirst'");
    }

    @Override
    public boolean offerLast(E e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'offerLast'");
    }

    @Override
    public E removeFirst() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeFirst'");
    }

    @Override
    public E removeLast() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeLast'");
    }

    @Override
    public E peekFirst() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'peekFirst'");
    }

    @Override
    public E peekLast() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'peekLast'");
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeFirstOccurrence'");
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeLastOccurrence'");
    }

    @Override
    public boolean offer(E e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'offer'");
    }

    @Override
    public E remove() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public E peek() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'peek'");
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addAll'");
    }

    @Override
    public void push(E e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'push'");
    }

    @Override
    public E pop() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'pop'");
    }

    @Override
    public boolean remove(Object o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public boolean contains(Object o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'contains'");
    }

    @Override
    public Iterator<E> iterator() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'iterator'");
    }

    @Override
    public Iterator<E> descendingIterator() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'descendingIterator'");
    }
}
