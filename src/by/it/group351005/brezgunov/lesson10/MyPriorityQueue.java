package by.it.group351005.brezgunov.lesson10;

import java.util.*;

public class MyPriorityQueue<E> implements Queue<E> {
    int size = 0;

    private Object[] arr = new Object[10];

    private void swap(int i, int j) {
        Object temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
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
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        if (size > 0) {
            stringBuilder.append(arr[0]);
            for (int i = 1; i < size; i++) {
                stringBuilder.append(", ").append(arr[i]);
            }
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            if (arr[i].equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public Object[] toArray(Object[] a) {
        return new Object[0];
    }

    public void siftUp(int curr) {
        int root = (curr - 1) / 2;
        if (curr >= 0 && ((Comparable<E>) arr[curr]).compareTo((E) arr[root]) < 0) {
            swap(curr, root);
            siftUp(root);
        }
    }

    @Override
    public boolean add(Object o) {
        if (size == arr.length) {
            arr = Arrays.copyOf(arr, arr.length + (arr.length >> 1));
        }
        arr[size++] = o;
        siftUp(size - 1);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean addAll(Collection c) {
        for (Object o : c) {
            add(o);
        }
        return !c.isEmpty();
    }

    @Override
    public void clear() {
        size = 0;
        arr = new Object[10];
    }

    @Override
    public boolean retainAll(Collection c) {
        boolean isChanged = false;
        for (int i = 0; i < size; i++) {
            for (Object o : c) {
                if (!arr[i].equals(o)) {
                    arr[i] = arr[--size];
                    arr[size] = null;
                    siftDown(i);
                    isChanged = true;
                    break;
                }
            }
        }
        return isChanged;
    }

    @Override
    public boolean removeAll(Collection c) {
        boolean isChanged = false;
        for (Object o : c) {
            for (int i = 0; i < size; i++) {
                if (arr[i].equals(o)) {
                    arr[i] = arr[--size];
                    arr[size] = null;
                    siftDown(i);
                    isChanged = true;
                    break;
                }
            }
        }
        return isChanged;
    }

    @Override
    public boolean containsAll(Collection c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean offer(Object o) {
        return add(o);
    }

    public void siftDown(int i) {
        int left = 2 * i + 1, right = 2 * i + 2;
        if (right < size) {
            int smallest = ((Comparable<E>) arr[left]).compareTo((E) arr[right]) < 0 ? left : right;
            if (((Comparable<E>) arr[i]).compareTo((E) arr[smallest]) > 0) {
                swap(i, smallest);
                siftDown(smallest);
            }
        } else if (left < size && ((Comparable<E>) arr[i]).compareTo((E) arr[left]) > 0) {
            swap(i, left);
            siftDown(left);
        }
    }

    @Override
    public E remove() {
        Object temp = arr[0];
        arr[0] = arr[--size];
        arr[size] = null;
        siftDown(0);
        return (E) temp;
    }

    @Override
    public E poll() {
        return remove();
    }

    @Override
    public E element() {
        return peek();
    }

    @Override
    public E peek() {
        return (E) arr[0];
    }
}
