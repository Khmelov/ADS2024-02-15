package by.it.group351005.gorodko.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyTreeSet<E> implements Set<E> {
    private int size = 0;

    private E[] set = (E[])new Object[size];

    public String toString() {
        String res = "[";
        for (int i = 0; i < size - 1; i++) {
            res += set[i].toString() + ", ";
        }
        if (size != 0) {
            res += set[size - 1].toString();
        }
        return res + "]";
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        set = null;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean add(Object e) {
        if (contains(e)) return false;
        E[] newset = (E[]) new Object[size + 1];
        int i = 0;
        for (; i < size && ((Comparable<? super E>)set[i]).compareTo((E)e) < 0; i++) {
            newset[i] = set[i];
        }
        newset[i++] = (E)e;
        for (; i <= size; i++) {
            newset[i] = set[i - 1];
        }
        set = newset;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (!contains(o)) return false;
        E[] newset = (E[]) new Object[size - 1];
        int i = 0;
        while (!set[i].equals(o)) {
            newset[i] = set[i];
            i++;
        }
        for (; i < size - 1; i++) {
            newset[i] = set[i + 1];
        }
        set = newset;
        size--;
        return true;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            if (o.equals(set[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean addAll(Collection c) {
        boolean res = false;
        for (Object o: c) {
            res = res | add(o);
        }
        return res;
    }

    @Override
    public boolean removeAll(Collection c) {
        boolean res = false;
        for (Object o: c) {
            res = res | remove(o);
        }
        return res;
    }

    @Override
    public boolean retainAll(Collection c) {
        boolean res = false;
        for (int i = 0; i < size; i++) {
            if (!c.contains(set[i])) {
                remove(set[i]);
                res = true;
                i--;
            }
        }
        return res;
    }

    @Override
    public boolean containsAll(Collection c) {
        for (Object o: c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }




    //////////////////////////////////////////////////
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
}
