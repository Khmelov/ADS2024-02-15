package by.it.group310901.baradzin.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementation of Deque interface using array
 *
 * @param <E> the type of elements held in this deque
 */
public class MyArrayDeque<E> implements Deque<E> {
    static int defaultSize = 8;
    static int extendStep = defaultSize / 2;

    private E[] elements;
    private int first = 0;
    private int last = 0;

    public MyArrayDeque() {
        this(defaultSize);
    }

    @SuppressWarnings("unchecked")
    public MyArrayDeque(int size) {
        if (defaultSize < 2)
            throw new IllegalArgumentException("defaultSize should be greater than 1");
        elements = (E[]) new Object[size < 2 ? defaultSize : size];
    }

    // ----- required ----------------------------------------------------------

    @Override
    public String toString() {
        var sb = new StringBuilder();
        sb.append('[');

        var index = first;
        var length = size();
        for (; length > 1; length--, index = incIndex(index))
            sb.append(elements[index]).append(", ");

        if (length == 1)
            sb.append(elements[index]);
        sb.append(']');

        return sb.toString();
    }

    @Override
    public int size() {
        return size(last - first);
    }

    @Override
    public boolean add(E e) {
        addLast(e);
        return true;
    }

    @Override
    public void addFirst(E e) {
        elements[first = decIndex(first)] = e;
        if (first == last)
            extendElements();
    }

    @Override
    public void addLast(E e) {
        elements[last] = e;
        if (first == (last = incIndex(last)))
            extendElements();
    }

    @Override
    public E element() {
        return getFirst();
    }

    @Override
    public E getFirst() {
        var e = peekFirst();
        if (e == null)
            throw new NoSuchElementException();
        return e;
    }

    @Override
    public E getLast() {
        var e = peekLast();
        if (e == null)
            throw new NoSuchElementException();
        return e;
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E pollFirst() {
        var e = elements[first];
        if (e != null) {
            elements[first] = null;
            first = incIndex(first);
        }
        return e;

    }

    @Override
    public E pollLast() {
        var e = elements[last = decIndex(last)];
        if (e != null)
            elements[last] = null;
        return e;
    }

    // ----- helpers -----------------------------------------------------------

    private int size(int preLength) {
        return preLength < 0 ? preLength + elements.length : preLength;
    }

    private int incIndex(int i) {
        return ++i >= elements.length ? 0 : i;
    }

    private int decIndex(int i) {
        return --i < 0 ? elements.length - 1 : i;
    }

    @SuppressWarnings("unchecked")
    private void extendElements() {
        var extended = (E[]) new Object[elements.length + extendStep];

        System.arraycopy(elements, 0, extended, 0, last);
        System.arraycopy(elements, first, extended, first + extendStep, extended.length - (first += extendStep));

        elements = extended;
    }

    // ===== optional ==========================================================
    // ! NOT TESTED. But, maybe, will work. Or not. Thats normal

    @Override
    public boolean isEmpty() {
        return first == last;
    }

    @Override
    public Object[] toArray() {
        var iter = iterator();
        var arr = new Object[size()];
        for (int i = 0; iter.hasNext();)
            arr[i++] = iter.next();
        return arr;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (size() >= a.length)
            return (T[]) toArray();

        var iter = iterator();
        for (int i = 0; iter.hasNext(); i++)
            a[i] = (T) iter.next();
        for (int i = size(); i < a.length; i++)
            a[i] = null;
        return a;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (var i : c) {
            var notFound = true;
            for (var j : this)
                if (i == j) {
                    notFound = false;
                    break;
                }
            if (notFound)
                return notFound;
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        var oldLength = size();
        for (var i : c) {
            var iter = iterator();
            while (iter.hasNext())
                if (iter.next().equals(i))
                    iter.remove();
        }
        return oldLength != size();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        var oldLength = size();
        for (var i : c) {
            var iter = iterator();
            while (iter.hasNext())
                if (!iter.next().equals(i))
                    iter.remove();
        }
        return oldLength != size();
    }

    @Override
    public void clear() {
        for (int i = 0; i < elements.length; i++)
            elements[i] = null;
        first = last = 0;
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
        var e = pollFirst();
        if (e == null)
            throw new NoSuchElementException();
        return e;
    }

    @Override
    public E removeLast() {
        var e = pollLast();
        if (e == null)
            throw new NoSuchElementException();
        return e;
    }

    @Override
    public E peekFirst() {
        return elements[first];
    }

    @Override
    public E peekLast() {
        return elements[decIndex(last)];
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        var iter = iterator();
        while (iter.hasNext())
            if (iter.next() == o) {
                iter.remove();
                return true;
            }
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        var iter = descendingIterator();
        while (iter.hasNext())
            if (iter.next() == o) {
                iter.remove();
                return true;
            }
        return false;
    }

    @Override
    public boolean offer(E e) {
        return offerLast(e);
    }

    @Override
    public E remove() {
        return removeFirst();
    }

    @Override
    public E peek() {
        return peekFirst();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        var oldLength = size();
        for (var i : c)
            addLast(i);
        return oldLength != size();
    }

    @Override
    public void push(E e) {
        addFirst(e);
    }

    @Override
    public E pop() {
        return removeFirst();
    }

    @Override
    public boolean remove(Object o) {
        return removeFirstOccurrence(o);
    }

    @Override
    public boolean contains(Object o) {
        for (var i : this)
            if (o.equals(i))
                return true;
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new ForwardIterator();
    }

    private class ForwardIterator implements Iterator<E> {
        int current = first;
        int length = size();

        @Override
        public boolean hasNext() {
            return length > 0;
        }

        @Override
        public E next() {
            if (length < 1)
                throw new NoSuchElementException();
            var e = elements[current];
            current = incIndex(current);
            length--;
            return e;
        }

        public void remove() {
            // TODO method stub
            throw new UnsupportedOperationException("Unimplemented method 'remove'");
        }
    }

    @Override
    public Iterator<E> descendingIterator() {
        return new DescendingIterator();
    }

    private class DescendingIterator extends ForwardIterator {
        int current = decIndex(last);

        @Override
        public E next() {
            if (length < 1)
                throw new NoSuchElementException();
            var e = elements[current];
            current = decIndex(current);
            length--;
            return e;
        }

        public void remove() {
            // TODO method stub
            throw new UnsupportedOperationException("Unimplemented method 'remove'");
        }
    }
}
