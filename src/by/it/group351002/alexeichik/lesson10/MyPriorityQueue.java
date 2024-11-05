package by.it.group351002.alexeichik.lesson10;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

public class MyPriorityQueue<E extends Comparable<E>> implements Queue<E> {
    private E[]heap;
    int size;
    int reserve = 5;
    MyPriorityQueue()
    {
        heap = (E[]) new Comparable[reserve];
        size = 0;
    }
    void shiftUp(int ind){
       while (ind>0 && heap[parentInd(ind)].compareTo(heap[ind])>0){
           swap(parentInd(ind),ind);
           ind = parentInd(ind);
       }
    }

    void shiftDown(int i){
        int maxindex = i;
        int indL = leftChild(i);
        if (indL<size && heap[indL].compareTo(heap[maxindex])<0)
            maxindex =indL;

        int indR = rightChild(i);
        if (indR<size && heap[indR].compareTo(heap[maxindex])<0)
            maxindex =indR;

        if (i!=maxindex){
            swap(i,maxindex);
            shiftDown(maxindex);
        }

    }



    void swap(int indP,int indCh){
        E temp = heap[indCh];
        heap[indCh] = heap[indP];
        heap[indP] = temp;

    }

    void resize(){
        if (heap.length==size) {
            E[]newheap = (E[])new  Comparable[size*2];
            for (int i = 0;i<size;i++){
                newheap[i]=heap[i];
            }
            heap=newheap;
        }
    }
    void insert(E el){
        size++;
        heap[size-1]=el;
        shiftUp(size-1);
    }

    E extractMax(){
        E res=heap[0];
        heap[0]=heap[size-1];
        size--;
        shiftDown(0);
        return res;
    }

    E getMax(){
        return heap[0];
    }

    void changePrior(int i,E p){
        E oldP = heap[i];
        heap[i]=p;
        if (heap[i].compareTo(p)>0)
            shiftUp(i);
        else {
            if (heap[i].compareTo(p) < 0)
                shiftDown(i);
        }

    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append("[");
        for (int i =0;i<size-1;i++){
            sb.append(heap[i]);
            sb.append(", ");
        }
        if (size!=0)
            sb.append(heap[size-1]);
        sb.append("]");
        return sb.toString();
    }




    int parentInd(int ind){
        return (ind-1)/2;
    }

    int leftChild(int ind){
       return 2*ind+1;
    }

    int rightChild(int ind){
        return 2*ind+2;
    }
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (size==0);
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0;i<size;i++)
            if(o.equals(heap[i]))
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
        resize();
        heap[size]=e;
        shiftUp(size);
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for(Object item:c)
            if (!contains((E)item))
                return false;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean flag = false;
        for(E item:c)
            if (add(item))
                flag = true;
        return flag;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean flag = false;
        int i=0,j=0;
        while (i<size){
            if (!c.contains(heap[i])) {
                heap[j]=heap[i];
                j++;
            }
            i++;
        }

        if (j != i){
            flag=true;
            size = j;
            for (int k =size/2-1;k>=0;k--){
                shiftDown(k);
            }
        }

        return flag;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean flag = false;
        /*
        for (int i =0;i<size;i++)
            if (!c.contains(heap[i])){
                if (!flag)
                    flag=true;
                heap[i]=heap[size-1];
                shiftDown(i);
                size--;
                if (size<0)
                    size = 0;
                i--;

            }
        */

        int i=0,j=0;
        while (i<size){
            if (c.contains(heap[i])) {
                heap[j]=heap[i];
                j++;
            }
            i++;
        }

        if (j != i){
            flag=true;
            size = j;
            for (int k =size/2-1;k>=0;k--){
                shiftDown(k);
            }
        }

        return flag;
    }

    @Override
    public void clear() {
        heap = (E[]) new Comparable[reserve];
        size=0;
    }

    @Override
    public boolean offer(E e) {
        if (!add(e))
            return false;
        return true;
    }

    @Override
    public E remove() {
        E root=getMax();
        extractMax();
        return root;
    }

    @Override
    public E poll() {
        if (size==0)
            return null;
        E root=getMax();
        extractMax();
        return root;
    }

    @Override
    public E element() {
        return getMax();
    }

    @Override
    public E peek() {
        if (size==0)
            return null;
        return getMax();
    }


}
