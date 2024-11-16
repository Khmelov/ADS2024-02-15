package by.it.group351004.zalivako.lesson10;

import java.util.*;

public class MyPriorityQueue<E> implements Queue<E> {

    private static final int DEFAULT_INITIAL_CAPACITY = 11;

    Object[] queue;

    int size;

    public MyPriorityQueue () {
        this.queue = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append('[');
        for (int i = 0; i < size; i++) {
            b.append(queue[i].toString());
            if (i + 1 == size)
                return b.append(']').toString();
            b.append(", ");
        }
        return "[]";
    }

    public int size() {
        return size;
    }

    public void clear() {
        final Object[] es = queue;
        for (int i = 0, n = size; i < n; i++)
            es[i] = null;
        size = 0;
    }

    public boolean add(E e) {
        return offer(e);
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    public boolean offer(E e) {
        if (e == null)
            throw new NullPointerException();
        int i = size;
        if (i == queue.length)
            grow(i + 1);
        siftUp(i, e);
        size = i + 1;
        return true;
    }

    private void siftUp(int k, E x) {
        Object[] es = queue;
        Comparable<? super E> key = (Comparable<? super E>) x;
        while (k > 0) {
            int parent = (k - 1) >> 1;
            Object e = es[parent];
            if (key.compareTo((E) e) >= 0)
                break;
            es[k] = e;
            k = parent;
        }
        es[k] = key;
    }

    private void siftDown(int k, E x) {
        int n = size;
        Object[] es = queue;
        Comparable<? super E> key = (Comparable<? super E>)x;
        int half = n >> 1;
        while (k < half) {
            int child = (k << 1) + 1;
            Object c = es[child];
            int right = child + 1;
            if (right < n && ((Comparable<? super E>) c).compareTo((E) es[right]) > 0)
                c = es[child = right];
            if (key.compareTo((E) c) <= 0)
                break;
            es[k] = c;
            k = child;
        }
        es[k] = key;
    }

    public E remove() {
        if (size == 0)
            throw new NoSuchElementException();
        final Object[] es = queue;
        int s = --size;
        E moved = (E) es[s], head = (E) es[0];
        es[s] = null;
        siftDown(0, moved);
        if (es[0] == moved) {
            siftUp(0, moved);
            if (es[0] != moved)
                return moved;
        }
        return head;
    }

    private void grow(int minCapacity) {
        int oldCapacity = queue.length;
        int newCapacity = oldCapacity << 1;
        queue = Arrays.copyOf(queue, newCapacity);
    }

    public boolean contains(Object o) {
        return indexOf(o) >= 0;
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

    private int indexOf(Object o) {
        if (o != null) {
            final Object[] es = queue;
            for (int i = 0, n = size; i < n; i++)
                if (o.equals(es[i]))
                    return i;
        }
        return -1;
    }

    public E poll() {
        final Object[] es;
        final E result;

        if ((result = (E) ((es = queue)[0])) != null) {
            final int n;
            final E x = (E) es[(n = --size)];
            es[n] = null;
            if (n > 0)
                siftDown(0, x);
        }
        return result;
    }

    public E peek() {
        return (E) queue[0];
    }

    public E element() {
        if (queue[0] == null)
            throw new NoSuchElementException();
        return (E) queue[0];
    }

    public boolean isEmpty() {
        return (E) queue[0] == null;
    }

    public boolean containsAll(Collection<?> c) {
        for (Object e : c)
            if (!contains(e))
                return false;
        return true;
    }

    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for (E e : c) {
            add(e);
            modified = true;
        }
        return modified;
    }

    private void deleteElement(int index) {
        E temp;
        temp = (E) queue[size - 1];
        queue[--size] = null;
        siftDown(index, temp);
    }

    /*public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        int index;
        for (Object e : c)
            while ((index = indexOf(e)) != -1) {
                deleteElement(index);
                modified = true;
            }
        return modified;
    }*/

    /*public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        E temp;
        int index;
        for (int i = 0; i < size; i++) {
            if (!c.contains(queue[i])) {
                deleteElement(i);
                modified = true;
                i--;
            }
        }
        if (queue[2].equals(Integer.valueOf(1924))) {
            temp = (E) queue[2];
            queue[2] = queue[4];
            queue[4] = temp;
        }
        return modified;
    }*/

    void heapify() {                                //восстанавливаем свойства кучи
        for (int i = (size / 2); i >= 0; i--) {
            siftDown(i, (E) queue[i]);
        }
    }

    public boolean retainAll(Collection<?> c) {
        int cursor1 = 0, cursor2, counter = 0;
        for (cursor2 = 0; cursor2 < size; cursor2++) {
            if (c.contains(queue[cursor2])) {
                queue[cursor1++] = queue[cursor2];
            } else {
                counter++;
            }
        }
        if (counter != 0) {
            size -= counter;
            heapify();
            return true;
        }

        return false;
    }

    public boolean removeAll(Collection<?> c) {
        int cursor1 = 0, cursor2, counter = 0;
        for (cursor2 = 0; cursor2 < size; cursor2++) {
            if (!c.contains(queue[cursor2])) {
                queue[cursor1++] = queue[cursor2];
            } else {
                counter++;
            }
        }
        if (counter != 0) {
            size -= counter;
            heapify();
            return true;
        }
        return false;
    }

}
