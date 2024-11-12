package by.it.group310902.sverchkov.lesson11;

import java.util.*;

public class MyTreeSet<E> implements Set<E> {

    static final int DEFAULT_CAPACITY = 10;

    E[] arr;
    int size = 0;

    MyTreeSet() {
        this(DEFAULT_CAPACITY);
    }

    MyTreeSet(int size) {
        arr = (E[]) new Object[size];
    }

    int binarySearch(E e) {
        Comparable<? super E> comp = (Comparable<? super E>) e;
        if (size == 0) return -1;

        int left = 0;
        int right = size - 1;

        while (left <= right) {
            int mid = (left + right) / 2;
            if (arr[mid].equals(e)) return mid;
            if (comp.compareTo(arr[mid]) < 0)
                right = mid - 1;
            else if (comp.compareTo(arr[mid]) > 0)
                left = mid + 1;
        }
        return -1;
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
        for (int i = 0; i < size; i++) {
            if (binarySearch((E) o) >= 0) {
                return true;
            }
        }
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
    public boolean add(E e) {
        if (size == 0) {
            arr[size++] = e;
        }
        if (binarySearch(e) >= 0) return false;

        arr[size] = e;
        int i = size - 1;

        Comparable<? super E> comp = (Comparable<? super E>) e;
        while(i >= 0){
            if (comp.compareTo(arr[i]) < 0) {
                E temp = arr[i];
                arr[i] = arr[i + 1];
                arr[i + 1] = temp;
                i--;
            }
            else
                break;
        }

        size++;
        if (size >= arr.length * 0.75) {
            arr = Arrays.copyOf(arr, arr.length * 2);
        }
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (size == 0) return false;
        int index = binarySearch((E) o);
        if (index < 0) return false;

        while(index < size-1){
            E temp = arr[index];
            arr[index] = arr[++index];
            arr[index] = temp;
        }
        arr[index] = null;
        size--;
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o: c){
            if (!contains(o)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean changed = false;
        for (E e: c){
            if (add(e) && !changed) changed = true;
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (c.isEmpty()) {
            clear();
            return true;
        }
        boolean changed = false;
        for (int i = 0; i < size; i++){
            if (!c.contains(arr[i])){
                remove(arr[i--]);
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c.isEmpty()) return false;
        boolean changed = false;
        for (Object o: c){
            if (remove(o) && !changed) changed = true;
        }
        return changed;
    }

    @Override
    public void clear() {
        arr = (E[]) new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    @Override
    public String toString() {
        if (size == 0) return "[]";
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size-1; i++){
            sb.append(arr[i]);
            sb.append(", ");
        }
        sb.append(arr[size-1]);
        sb.append("]");
        return sb.toString();
    }
}
