package by.it.group310901.stashuk.lesson10;

import java.util.*;

public class MyPriorityQueue<E> implements Queue<E> {


    private Object[] arr = new Object[1];
    private int size = 0;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
            sb.append(arr[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void swap(int i, int j) {
        Object t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }
    private void heapify(int i) {
        int leftChild;
        int rightChild;
        int largestChild;
        while (true) {
            leftChild = 2 * i + 1;
            rightChild = leftChild + 1;
            largestChild = i;
            if (leftChild < size && ((Comparable<? super E>) arr[leftChild]).compareTo((E) arr[largestChild]) < 0)
                largestChild = leftChild;
            if (rightChild < size && ((Comparable<? super E>) arr[rightChild]).compareTo((E) arr[largestChild]) < 0)
                largestChild = rightChild;
            if (largestChild == i)
                break;
            swap(i,largestChild);
            i = largestChild;
        }
    }

    public void rebuildHeap(Object[] newArr)
    {
        if(newArr.length == 0)
            arr = new Object[1];
        else
            arr = newArr;
        size = newArr.length;
        for (int i = size / 2 - 1; i >= 0; i--)
            heapify(i);
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++)
            if (arr[i].equals(o))
                return true;
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
        if (arr.length == size)
            arr = Arrays.copyOf(arr, size * 2);
        arr[size++] = e;
        int i = size - 1, par = (i-1)/2;
        while(i > 0 && ((Comparable<? super E>) arr[i]).compareTo((E) arr[par]) < 0){
            swap(i,par);
            i = par;
            par = (i-1)/2;
        }
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c)
            if (!contains(o))
                return false;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E o : c)
            add(o);
        return !c.isEmpty();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        int kol = 0;
        for (int i = 0; i < size; i++)
            if (!c.contains(arr[i]))
                kol++;
        if(kol == size)
            return false;
        Object[] newArr = new Object[kol];
        int ci = 0;
        for (int i = 0; i < size(); i++)
            if (!c.contains(arr[i]))
                newArr[ci++] = arr[i];
        rebuildHeap(newArr);
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        int kol = 0;
        for (int i = 0; i < size; i++)
            if (c.contains(arr[i]))
                kol++;
        if(kol == size)
            return false;
        Object[] newArr = new Object[kol];
        int ci = 0;
        for (int i = 0; i < size(); i++)
            if (c.contains(arr[i]))
                newArr[ci++] = arr[i];
        rebuildHeap(newArr);
        return true;
    }

    @Override
    public void clear() {
        arr = new Object[1];
        size = 0;
    }

    @Override
    public boolean offer(E e) {
        return add(e);
    }

    @Override
    public E remove() {
        if (isEmpty())
            throw new NoSuchElementException();
        return poll();
    }

    @Override
    public E poll() {
        if(isEmpty())
            return null;
        E toRet = (E) arr[0];
        swap(0,--size);
        heapify(0);
        return toRet;
    }

    @Override
    public E element() {
        return (E) arr[0];
    }

    @Override
    public E peek() {
        if(isEmpty())
            return null;
        return (E) arr[0];
    }
}
