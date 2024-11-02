package by.it.group310902.karpechenko.lesson10;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

public class MyPriorityQueue<E extends Comparable<E>> implements Queue<E> {

    private int size;
    private final static int minSize = 8;
    private E[] elem;

    public MyPriorityQueue(){
        size = 0;
        elem = (E[]) new Comparable[minSize];
    }
    private void swap(int x, int y){
        E t = elem[x];
        elem[x] = elem[y];
        elem[y] = t;
    }
    private void SiftUp(int i){
        while (elem[i].compareTo(elem[(i-1)/2]) < 0){
            swap(i, (i-1)/2);
            i = (i - 1) / 2;
        }
    }
    private void SiftDown(int i){
        while(2 * i + 1 < size){
            int l = 2 * i + 1;
            int r = 2 * i + 2;
            int j = l;
            if ((r < size) && (elem[r].compareTo(elem[j]) < 0)){
                j = r;
            }
            if (elem[i].compareTo(elem[j]) < 0){
                break;
            }
            swap(i, j);
            i = j;
        }
    }
    void heapify() {
        for (int i = (size / 2); i >= 0; i--) {
            SiftDown(i);
        }
    }
    @Override
    public String toString() {
        StringBuilder st = new StringBuilder();
        st.append('[');
        for (int i = 0; i < size - 1; i++){
            st.append(elem[i] + ", ");
        }
        if (size > 0){
            st.append(elem[size - 1]);
        }
        st.append(']');
        return st.toString();
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
        for (int i = 0; i < size; i++){
            if (elem[i].equals(o)){
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
    private void resize(){
        E[] t = (E[]) new Comparable[size * 2];
        System.arraycopy(elem, 0, t, 0, size);
        elem = t;
    }
    @Override
    public boolean add(E e) {
        if (size == elem.length){
            resize();
        }
        elem[size] = e;
        SiftUp(size);
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++){
            if(o.equals(elem[i])){
                size--;
                elem[i] = elem[size];
                SiftDown(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (c == null) throw new NullPointerException();
        if (size == 0) return false;
        for (var item : c) {
            if (!contains(item))
                return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c == null) return false;
        for (var item : c){
            add(item);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        int j = 0, k = 0;
        for (int i = 0; i < size ; i++){
            if (!c.contains(elem[i])){
                elem[j++] = elem[i];
            }
            else{
                k++;
            }
        }
        if (k != 0){
            size-=k;
            heapify();
            return true;
        }
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        int j = 0, k = 0;
        for (int i = 0; i < size ; i++){
            if (c.contains(elem[i])){
                elem[j++] = elem[i];
            }
            else{
                k++;
            }
        }
        if (k != 0){
            size-=k;
            heapify();
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        size = 0;
        elem = (E[]) new Comparable[minSize];
    }

    @Override
    public boolean offer(E e) {
        return add(e);
    }

    @Override
    public E remove() {
        if (size == 0) {
            return null;
        }
        E temp = elem[0];
        size--;
        elem[0] = elem[size];
        SiftDown(0);
        return temp;
    }

    @Override
    public E poll() {
        return remove();
    }

    @Override
    public E element() {
        if(!isEmpty()){
            return elem[0];
        }
        return null;
    }

    @Override
    public E peek() {
        return element();
    }
}
