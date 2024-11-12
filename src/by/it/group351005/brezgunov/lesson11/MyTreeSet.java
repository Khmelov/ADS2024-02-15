package by.it.group351005.brezgunov.lesson11;

import java.util.*;

public class MyTreeSet<E> implements Set<E> {
    private final int START_LEN = 10;
    private Object[] arr = new Object[START_LEN];
    private int size = 0;

    private int binarySearch(Object target, int start, int finish) {
        if (start >= finish) {
            return start;
        }
        int mid = (start + finish) / 2;
        if (((Comparable<E>) target).compareTo((E) arr[mid]) > 0) {
            return binarySearch(target, mid + 1, finish);
        } else if (((Comparable<E>) target).compareTo((E) arr[mid]) < 0) {
            return binarySearch(target, start, mid);
        }
        return mid;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        int index = binarySearch(o, 0, size);
        return index < size && arr[index].equals(o);
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int curr = 0;

            @Override
            public boolean hasNext() {
                return curr < size;
            }

            @Override
            public E next() {
                if (hasNext())
                    return (E) arr[curr++];
                else
                    throw new NoSuchElementException();
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
    public boolean add(E e) {
        int newIndex = binarySearch(e, 0, size);
        if (arr[newIndex] != null && arr[newIndex].equals(e))
            return false;
        if (++size >= arr.length) {
            Object[] temp = new Object[arr.length * 2];
            for (int i = 0; i < newIndex; i++) {
                temp[i] = arr[i];
            }
            for (int i = size; i > newIndex; i--) {
                temp[i] = arr[i - 1];
            }
            arr = temp;
        } else {
            for (int i = size; i > newIndex; i--) {
                arr[i] = arr[i - 1];
            }
        }
        arr[newIndex] = e;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = binarySearch(o, 0, size);
        if (index >= size || !arr[index].equals(o))
            return false;
        for (int i = index + 1; i < size; i++) {
            arr[i - 1] = arr[i];
        }
        arr[--size] = null;
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o))
                return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean isAdded = false;
        for (E o : c) {
            if (add(o)) {
                isAdded = true;
            }
        }
        return isAdded;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean isRemoved = false;
        for (Object o : this) {
            if (!c.contains(o)) {
                remove(o);
                isRemoved = true;
            }
        }
        return isRemoved;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean isRemoved = false;
        for (Object o : c) {
            if (remove(o)) {
                isRemoved = true;
            }
        }
        return isRemoved;
    }

    @Override
    public void clear() {
        arr = new Object[START_LEN];
        size = 0;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Object o : this) {
            sb.append(o.toString()).append(", ");
        }
        if (size > 0)
            sb.delete(sb.length() - 2, sb.length()).append("]");
        return sb.toString();
    }
}
