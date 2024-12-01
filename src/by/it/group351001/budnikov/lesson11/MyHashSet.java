package by.it.group351001.budnikov.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MyHashSet<E> implements Set<E> {

    private static final int CAPACITY = 50;
    private ListC<E>[] elements;

    private int elementsCnt;
    private int getIndex(E element) {
        return Math.abs(element.hashCode()) % CAPACITY;
    }
    public MyHashSet() {
        elements = new ListC[CAPACITY];
        for (int i = 0; i < CAPACITY; i++) {
            elements[i] = new ListC();
        }
        elementsCnt = 0;
    }
    @Override
    public boolean add(E e) {
        if (contains(e)) {
            return false;
        }

        elements[getIndex(e)].add(e);
        elementsCnt++;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }
    @Override
    public boolean contains(Object o) {
        int index = getIndex((E)o);
        for (int i = 0; i < elements[index].size(); i++) {
            if (o.equals(elements[index].get(i)))
                return true;
        }
        return false;
    }
    @Override
    public boolean remove(Object o) {
        if (!contains(o))
            return false;

        int index = getIndex((E)o);

        for (int i = 0; i < elements[index].size(); i++)
        {
            if (o.equals(elements[index].get(i))) {
                elements[index].remove(i);
                elementsCnt--;
                return true;
            }
        }
        return false;
    }
    @Override
    public int size() {
        return elementsCnt;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }
    @Override
    public void clear() {
        elements = new ListC[CAPACITY];
        elementsCnt = 0;
        for (int i = 0; i < CAPACITY; i++) {
            elements[i] = new ListC();
        }
    }
    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }
    @Override
    public boolean isEmpty() {
        return elementsCnt == 0;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }
    @Override
    public String toString() {
        String tempStr = "[";

        if (elementsCnt > 0) {
            for (int i = 0; i < CAPACITY-1; i++) {
                for (int j = 0; j < elements[i].size(); j++) {
                    tempStr += elements[i].get(j) + ", ";
                }
            }
            for (int j = 0; j < elements[CAPACITY-1].size()-1; j++) {
                tempStr += elements[CAPACITY-1].get(j) + ", ";
            }

            if (elements[CAPACITY-1].size() > 0)
                tempStr += elements[CAPACITY - 1].get(elements[CAPACITY-1].size()-1) + "]";
            else {
                tempStr = tempStr.substring(0, tempStr.length() - 2);
                tempStr += "]";
            }

        }
        else { tempStr += "]"; }

        return tempStr;
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
    public Iterator<E> iterator() {
        return null;
    }
}
