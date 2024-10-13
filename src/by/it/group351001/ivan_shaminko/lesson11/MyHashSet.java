package by.it.group351001.ivan_shaminko.lesson11;

import java.util.*;
import static java.util.Objects.hash;
//size() +
//clear() +
//isEmpty() +
//add(Object) +
//remove(Object) +
//contains(Object) +

public class MyHashSet<E> implements Set<E> {
    private int size = 1;
    private MyNode<E>[] table = (MyNode<E>[]) new MyNode[8];
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
    private void resize(){
        int oldsize = size;
        MyNode<E>[] newTable = (MyNode<E>[]) new MyNode[size + (size >> 1) + 1];
        for (int i = 0; i < oldsize; i++) {
            MyNode<E> current = table[i];
            while (current != null) {
                int newIndex = gethash(current.data) % size;
                MyNode<E> next = current.next;
                current.next = newTable[newIndex];
                newTable[newIndex] = current;
                current = next;
            }
        }
        table = newTable;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        boolean first = true; // Флаг для первого элемента

        for (MyNode<E> node : table) {
            MyNode<E> current = node;
            while (current != null) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append(current.data);
                first = false;
                current = current.next;
            }
        }

        sb.append("]");
        return sb.toString();
    }

    @Override
    public int size() {
        return takenplaces;
    }

    @Override
    public void clear() {
        table = (MyNode<E>[]) new MyNode[size];
        takenplaces = 0;
    }
    @Override
    public boolean isEmpty() {
        return takenplaces == 0;
    }

    @Override
    public boolean add(E e) {
        if (takenplaces >= size * 0.75) {
            resize();
        }

        int index = gethash(e) % size;
        MyNode<E> current = table[index];

        while (current != null) {
            if (current.data.equals(e)) {
                return false;
            }
            current = current.next;
        }

        MyNode<E> newNode = new MyNode<>(e, table[index]);
        table[index] = newNode;
        takenplaces++;
        return true;
    }

    @Override
    public boolean contains(Object o) {
        int index = gethash((E) o) % size;
        MyNode<E> current = table[index];

        while (current != null) {
            if (current.data.equals(o)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        int index = gethash((E) o) % size;
        MyNode<E> current = table[index];
        MyNode<E> prev = null;

        while (current != null) {
            if (current.data.equals(o)) {
                if (prev == null) {
                    table[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                takenplaces--;
                return true;
            }
            prev = current;
            current = current.next;
        }
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