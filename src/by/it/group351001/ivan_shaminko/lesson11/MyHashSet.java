package by.it.group351001.ivan_shaminko.lesson11;

import java.util.*;
import static java.util.Objects.hash;
//size()
//clear()
//isEmpty()
//add(Object)
//remove(Object)
//contains(Object)

public class MyHashSet<E> implements Set<E> {
    private int size = 0;
    private MyNode<E>[] table = (MyNode<E>[])new MyNode[8];
    private int takenplaces = 0;

    static private class MyNode<E>{
        E data;
        MyNode<E> next;
        MyNode(E e, MyNode next){
            this.data = e;
            this.next = next;
        }

    }
    private int gethash(E e){
        return e.hashCode();
    }
    private int resize(){
        MyNode<E>[] newTable=
    }
    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {

    }
    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean add(E e) {
        if (takenplaces == table.length){
            resize();
        }
        int index = gethash(e);
        MyNode currentNode = table[index];
        if (!contains(e)){
            if (table[index].data == null) {
                table[index].data = e;
            }
            else {
                while (table[index].data != null) {
                    index++;
                }

            }
        }
        return false;
    }
    @Override
    public boolean contains(Object o) {
        for (int i = 0; i != table.length; i++){
            if (o.equals(table[i].data)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
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
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }
    @Override
    public Iterator<E> iterator() {
        return null;
    }

}
