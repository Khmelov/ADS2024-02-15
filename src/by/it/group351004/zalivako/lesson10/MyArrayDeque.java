package by.it.group351004.zalivako.lesson10;

import java.util.*;

public class MyArrayDeque<E> implements Deque<E> {

    Object[] elements;

    int head; //индекс следующего извлекаемого элемента
    int tail; //индекс, по которому будет записан следующий добавленный в конец элемент

    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    final static int DEFAULT_SIZE = 10;

    public MyArrayDeque() {
        elements = new Object[16 + 1];
    }

    public String toString() {
        Object[] a = elements;
        if (a == null)
            return "null";

        StringBuilder b = new StringBuilder();
        b.append('[');
        int first;
        for (first = head; first != tail; first = inc(first, elements.length)) {
            b.append(a[first]);
            if (inc(first, elements.length) == tail)
                return b.append(']').toString();
            b.append(", ");
        }
        return b.toString();
    }

    public int size() {
        return sub(tail, head, elements.length);
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

    static final int sub(int i, int j, int modulus) {
        if ((i -= j) < 0) i += modulus;
        return i;
    }

    public boolean add(E e) {
        addLast(e);
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

    public E getFirst() {
        E e = elementAt(elements, head);
        if (e == null)
            throw new NoSuchElementException();
        return e;
    }

    public E getLast() {
        final Object[] es = elements;
        E e = elementAt(es, dec(tail, es.length));
        if (e == null)
            throw new NoSuchElementException();
        return e;
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

    public E poll() {
        return pollFirst();
    }

    public E pollFirst() {
        final Object[] es;
        final int h;
        E e = elementAt(es = elements, h = head);
        if (e != null) {
            es[h] = null;
            head = inc(h, es.length);
        }
        return e;
    }

    public E pollLast() {
        final Object[] es;
        final int t;
        E e = elementAt(es = elements, t = dec(tail, es.length));
        if (e != null)
            es[tail = t] = null;
        return e;
    }

    static final <E> E elementAt(Object[] es, int i) {
        return (E) es[i];
    }

    static final int dec(int i, int modulus) {   //циклически отнимает единицу, чтобы в случае, если массив закончится слева, элементы стали добавляться с конца массива
        if (--i < 0) i = modulus - 1;
        return i;
    }

    public void addFirst(E e) {
        if (e == null)
            throw new NullPointerException();
        final Object[] es = elements;
        es[head = dec(head, es.length)] = e;
        if (head == tail)
            grow(1);
    }

    private void grow(int needed) {
        final int oldCapacity = elements.length;
        int newCapacity;
        int jump = (oldCapacity < 64) ? (oldCapacity >> 1) : (oldCapacity + 2);
        if (jump < needed || (newCapacity = (oldCapacity + jump)) - MAX_ARRAY_SIZE > 0)
            newCapacity = newCapacity(needed, jump);
        final Object[] es = elements = Arrays.copyOf(elements, newCapacity);
        if ((tail < head) || (tail == head && es[head] != null)){
            int newSpace = newCapacity - oldCapacity;
            System.arraycopy(es, head, es, head + newSpace, oldCapacity - head);
            for (int i = head, to = (head += newSpace); i < to; i++)
                es[i] = null;
        }
    }
    /*private void grow(int needed) {
        // overflow-conscious code
        final int oldCapacity = elements.length;
        int newCapacity;
        // Double capacity if small; else grow by 50%
        int jump = (oldCapacity < 64) ? (oldCapacity + 2) : (oldCapacity >> 1);
        if (jump < needed
                || (newCapacity = (oldCapacity + jump)) - MAX_ARRAY_SIZE > 0)
            newCapacity = newCapacity(needed, jump);
        final Object[] es = elements = Arrays.copyOf(elements, newCapacity);
        // Exceptionally, here tail == head needs to be disambiguated
        if (tail < head || (tail == head && es[head] != null)) {
            // wrap around; slide first leg forward to end of array
            int newSpace = newCapacity - oldCapacity;
            System.arraycopy(es, head,
                    es, head + newSpace,
                    oldCapacity - head);
            for (int i = head, to = (head += newSpace); i < to; i++)
                es[i] = null;
        }
    }*/

    private int newCapacity(int needed, int jump) {
        final int oldCapacity = elements.length, minCapacity;
        if ((minCapacity = oldCapacity + needed) - MAX_ARRAY_SIZE > 0) {
            throw new IllegalStateException("Sorry, deque too big");
        }
        if (needed > jump)
            return minCapacity;
        return (oldCapacity + jump - MAX_ARRAY_SIZE < 0) ? oldCapacity + jump : MAX_ARRAY_SIZE;
    }

    public void addLast(E e) {
        if (e == null)
            throw new NullPointerException();
        final Object[] es = elements;
        es[tail] = e;
        if (head == (tail = inc(tail, es.length)))
            grow(1);
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

    static final int inc(int i, int modulus) {
        if (++i >= modulus) i = 0;
        return i;
    }
}
