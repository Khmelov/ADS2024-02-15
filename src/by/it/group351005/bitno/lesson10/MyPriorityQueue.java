package by.it.group351005.bitno.lesson10;

import java.util.*;

public class MyPriorityQueue<E> implements Queue<E> {
    private int capacity = 1;
    private int size = 0;
    @SuppressWarnings("unchecked")
    private E[] queueElems = (E[]) new Object[capacity];
    private int[] queueHashes = new int[capacity];

    private void increaseCapacity()
    {
        capacity *= 2;
        @SuppressWarnings("unchecked")
        E[] newArray = (E[]) new Object[capacity];
        int[] newHashes = new int[capacity];
        System.arraycopy(queueElems, 0, newArray, 0, size);
        System.arraycopy(queueHashes, 0, newHashes, 0, size);
        queueElems = newArray;
        queueHashes = newHashes;
    }

    private void swap(int index1, int index2){
        int hashTemp = queueHashes[index1];
        E objTemp = queueElems[index1];
        queueHashes[index1] = queueHashes[index2];
        queueElems[index1] = queueElems[index2];
        queueHashes[index2] = hashTemp;
        queueElems[index2] = objTemp;
    }
    private void siftUp(int k, E x){
        int nextPos;
        int key = queueHashes[size];
        while (k > 0){
            nextPos = (k - 1) >>> 1;
            if (key >= queueHashes[nextPos])
                break;
            swap(k, nextPos);
            k = nextPos;
        }
        queueElems[k] = x;
        queueHashes[k] = key;
    }

    private void siftDown(int k, E x) {
        int nextPos, right;
        int key = queueHashes[size];
        int half = size >>> 1;
        while (k < half) {
            nextPos = (k << 1) + 1;
            right = nextPos + 1;
            if (right < size && queueHashes[right] < queueHashes[nextPos]) {
                nextPos = right;
            }
            if (key <= queueHashes[nextPos])
                break;
            swap(k, nextPos);
            k = nextPos;
        }
        queueElems[k] = x;
        queueHashes[k] = key;
    }
    @Override
    public String toString(){
        StringBuilder str = new StringBuilder("[");
        for (int i = 0; i < size - 1; i++) {
            if (queueElems[i] != null)
                str.append(queueElems[i].toString()).append(", ");
        }
        if (size > 0)
            str.append(queueElems[size - 1].toString());
        str.append("]");
        return str.toString();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        capacity = 1;
        size = 0;
        queueElems = (E[]) new Object[capacity];
        queueHashes = new int[capacity];
    }

    @Override
    public boolean add(E e) {
        if (size >= capacity)
            increaseCapacity();
        queueHashes[size] = e.hashCode();
        queueElems[size] = e;
        siftUp(size, e);
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int oIndex = -1;
        for (int i = 0; i < size; i++) {
            if (queueElems[i].equals(o)){
               oIndex = i;
               break;
            }
        }
        if (oIndex != -1) {
            size--;
            if (size == oIndex)
                queueElems[oIndex] = null;
            else {
                E moved = queueElems[size];
                queueElems[size] = null;
                siftDown(oIndex, moved);
                if (queueElems[oIndex] == moved)
                    siftUp(oIndex, moved);
            }
            return true;
        }
        else
            return false;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            if (queueElems[i].equals(o)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean offer(E e) {
        add(e);
        return true;
    }

    @Override
    public E poll() {
        E answer = null;
        if (size > 0)
            answer = queueElems[0];
        remove(answer);
        return answer;
    }

    @Override
    public E peek() {
        if (size == 0)
            return null;
        return queueElems[0];
    }

    @Override
    public E element() {
        if (size == 0)
            throw new NoSuchElementException("The collection is empty.");
        return queueElems[0];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object element : c){
            if (!this.contains(element))
                return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c.isEmpty())
            return false;
        for (E element : c){
            add(element);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean isChanged = false;
        MyPriorityQueue<E> newQueue = new MyPriorityQueue<>();
        for (int i = 0; i < size; i++) {
            if (c.contains(queueElems[i])){
                isChanged = true;
            } else {
                newQueue.add(queueElems[i]);
            }
        }
        queueElems = newQueue.queueElems;
        queueHashes = newQueue.queueHashes;
        size = newQueue.size();
        capacity = newQueue.capacity;
        return isChanged;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean isChanged = false;
        MyPriorityQueue<E> newQueue = new MyPriorityQueue<>();
        for (int i = 0; i < size; i++) {
            if (c.contains(queueElems[i])){
                newQueue.add(queueElems[i]);
            } else {
                isChanged = true;
            }
        }
        queueElems = newQueue.queueElems;
        queueHashes = newQueue.queueHashes;
        size = newQueue.size();
        capacity = newQueue.capacity;
        return isChanged;
    }

///////////////////////////////////////////////////////////////////////////
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
    public E remove() {
        E answer = queueElems[0];
        remove(queueElems[0]);
        return answer;
    }
}
