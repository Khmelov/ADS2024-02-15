package by.it.group351002.alexeichik.lesson10;
import java.util.Collection;
import java.util.Iterator;
import java.util.Deque;

public class MyArrayDeque<E> implements Deque<E> {
    private E[]Deq;
    static final int initSize = 3;
    int size = 0;

    public MyArrayDeque(){this(initSize);}
    public MyArrayDeque(int size){Deq = (E[]) new Object[size];
    };

    @Override
    public void addFirst(E e) {
        if (size==Deq.length) {
            E[] newDeq;
            newDeq = (E[]) new Object[size * 2];
            for (int i = 0; i < size; i++)
                newDeq[i] = Deq[i];
            Deq = newDeq;
        }
        for (int i = size; i > 0; i--)
            Deq[i] = Deq[i-1];

        Deq[0] = e;
        size++;
    }

    @Override
    public void addLast(E e) {
        if (size==Deq.length) {
            E[] newDeq;
            newDeq = (E[]) new Object[size * 2];
            for (int i = 0; i < size; i++)
                newDeq[i] = Deq[i];
            Deq = newDeq;
        }

        Deq[size] = e;
        size++;

    }

    @Override
    public boolean offerFirst(E e) {
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append("[");
        for (int i =0;i<size-1;i++){
            sb.append(Deq[i]);
            sb.append(", ");
        }
        sb.append(Deq[size-1]);
        sb.append("]");
        return sb.toString();
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
        if (size==0)
            return null;
        else
        {
            E first=Deq[0];
            for (int i =0; i<size-1; i++)
                Deq[i] = Deq[i+1];
            size--;
            return first;
        }
    }

    @Override
    public E pollLast() {
        if (size == 0)
            return null;
        else {
            size--;
            return Deq[size];
        }
    }

    @Override
    public E getFirst() {
        return Deq[0];
    }

    @Override
    public E getLast() {
        return Deq[size-1];
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
        if (size==Deq.length) {
            E[] newDeq;
            newDeq = (E[]) new Object[size * 2];
            for (int i = 0; i < size; i++)
                newDeq[i] = Deq[i];
            Deq = newDeq;
        }

        Deq[size] = e;
        size++;
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
        if (size==0)
            return null;
        else
        {
           E first=Deq[0];
           for (int i =0; i<size-1; i++)
               Deq[i] = Deq[i+1];
           size--;
           return first;
        }

    }

    @Override
    public E element() {
        if (size == 0)
            return null;
        else
            return Deq[0];
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
