package by.it.group351005.egorov.lesson10;

import java.util.*;

public class MyPriorityQueue<E extends Comparable<E>> implements Queue<E> {


    private final int INIT_STAT = 4;
    private E[] _arr;
    private int _size;

    private int parent(int i) {return (i-1)/2;}
    private int leftChild(int i) {return (2*i+1);}
    private int rightChild(int i) {return (2*i+2);}

    private void swap(int index, int index2){
        E temp = _arr[index];
        _arr[index] = _arr[index2];
        _arr[index2] = temp;
    }

    private void heapifyUp(int index) {
        while (index > 0 && _arr[index].compareTo(_arr[parent(index)]) < 0) {
            int parent = parent(index);
            swap(index, parent);
            index = parent;
        }
    }

    private void heapifyDown(int index) {
        int smallest = index;
        int left = leftChild(index);
        int right = rightChild(index);
        if (left < _size && _arr[left].compareTo(_arr[smallest]) < 0) {
            smallest = left;
        }
        if (right < _size && _arr[right].compareTo(_arr[smallest]) < 0) {
            smallest = right;
        }
        if (smallest != index) {
            swap(index, smallest);
            heapifyDown(smallest);
        }
    }

    private void resize() {
        E[] newArr = (E[]) new Comparable[_arr.length * _arr.length];
        for (int i = 0; i < _size; i++) {
            newArr[i] = _arr[i];
        }
        _arr = newArr;
    }

    MyPriorityQueue() {
        _arr = (E[]) new Comparable[INIT_STAT];
        _size = 0;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        for (int i = 0; i < _size; i++) {
            stringBuilder.append(_arr[i]).append(", ");
        }
        if (stringBuilder.length() > 1) {
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }
    @Override
    public int size() {
        return _size;
    }

    @Override
    public void clear() {
        for (int i = 0; i < _size; i++) {
            _arr[i] = null;
        }
        _size = 0;
    }

    @Override
    public boolean add(E e) {
        return offer(e);
    }

    @Override
    public E remove() {
        return poll();
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < _size; i++) {
            if (Objects.equals(o,_arr[i])) {
                _arr[i] = _arr[--_size];
                heapifyDown(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < _size; i++) {
            if (Objects.equals(o,_arr[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean offer(E e) {
        if (_size == _arr.length) {
            resize();
        }
        _arr[_size] = e;
        _size++;
        heapifyUp(_size - 1);
        return true;
    }

    @Override
    public E poll() {
        if (isEmpty()){
            return null;
        }
        E elem = _arr[0];
        _arr[0] = _arr[--_size];
        heapifyDown(0);
        return elem;
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            return null;
        }
        return _arr[0];
    }

    @Override
    public E element() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return peek();
    }

    @Override
    public boolean isEmpty() {
        return _size == 0;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        for (var elem : collection) {
            if (!contains(elem)){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        if (collection.isEmpty()) {
            return false;
        }
        for (var elem : collection) {
            add(elem);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        for (var elem : collection) {
            remove(elem);
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        boolean result = false;
        for (int i = _size - 1; i >= 0; i--) {
            if (!collection.contains(_arr[i])) {
                remove(_arr[i]);
                result = true;
            }
        }
        return result;
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
    public <T> T[] toArray(T[] ts) {
        return null;
    }
}
