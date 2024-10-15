package by.it.group351003.zhuravski.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

/*
toString()
                size()

                add(E element)
                addFirst(E element)
                addLast(E element)

                element()
                getFirst()
                getLast()

                poll()
                pollFirst()
                pollLast()
 */
public class MyArrayDeque<E> implements Deque<E> {
    int capacity = 40;
    Object[] array;
    int size = 0;
    int zero_point;
    public MyArrayDeque() {
        array = new Object[capacity];
        set_zero_point();
    }
    void set_zero_point() {
        zero_point = capacity / 2;
    }
    void shrink_capacity() {
        if ((size + zero_point == capacity) || (zero_point == 0)) {
            Object[] old_arr = array;
            capacity += 40;
            int old_zero = zero_point;
            set_zero_point();
            array = new Object[capacity];
            System.arraycopy(old_arr, old_zero, array, zero_point, size);
        }
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append('[');
        if (size > 0) {
            str.append(array[zero_point].toString());
            for (int i = zero_point + 1; i < zero_point + size; i++) {
                str.append(", ");
                str.append(array[i].toString());
            }
        }
        str.append(']');
        return str.toString();
    }
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

    public boolean add(E element) {
        addLast(element);
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
        if (size == 0) {
            return null;
        }
        E res = (E)array[zero_point];
        size--;
        zero_point++;
        return res;
    }

    public void addFirst(E element) {
        shrink_capacity();
        zero_point--;
        array[zero_point] = element;
        size++;
    }
    public void addLast(E element) {
        shrink_capacity();
        array[zero_point + size] = element;
        size++;
    }
    public E element() {
        if (size == 0) {
            return null;
        }
        return (E)array[zero_point];
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

    public boolean offerFirst(E element) {
        return false;
    }
    public boolean offerLast(E element) {
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
        if (size == 0) {
            return null;
        }
        E res = (E)array[zero_point];
        size--;
        zero_point++;
        return res;
    }

    @Override
    public E pollLast() {
        if (size == 0) {
            return null;
        }
        E res = (E)array[zero_point + size - 1];
        size--;
        return res;
    }

    @Override
    public E getFirst() {
        if (size == 0) {
            return null;
        }
        return (E)array[zero_point];
    }

    @Override
    public E getLast() {
        if (size == 0) {
            return null;
        }
        return (E)array[zero_point + size - 1];
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
}
