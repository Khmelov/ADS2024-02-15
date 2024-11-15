package by.it.group351005.bitno.lesson11;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyTreeSet<E> implements Set<E> {
    private final int DEFAULT_INITIAL_CAPACITY = 0;
    private int size = 0;
    @SuppressWarnings("unchecked")
    private E[] setElems = (E[]) new Object[size];
    private void swap(int index1, int index2) {
        E temp = setElems[index1];
        setElems[index1] = setElems[index2];
        setElems[index2] = temp;
    }
    private void sort() {
        for (int i = 0; i < size; i++) {
            for (int j = i; j < size; j++) {
                if (setElems[i].hashCode() > setElems[j].hashCode())
                    swap(i, j);
            }
        }
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
        if (size == 0)
            return false;
        int left = 0;
        int right = size - 1;
        while (left != right){
            int midElem = (right + left) / 2;
            if (setElems[midElem].hashCode() > o.hashCode()) {
                right = midElem;
            }
            else if (setElems[midElem].hashCode() < o.hashCode()){
                if (left == midElem)
                    left = right;
                else
                    left = midElem;
            }
            else {
                left = midElem;
                right = midElem;
            }
        }
        return setElems[left].equals(o);
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
        if (contains(e))
            return false;
        setElems = Arrays.copyOf(setElems, size + 1);
        setElems[size] = e;
        size++;
        sort();
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (!contains(o))
            return false;
        E[] newSetElems = (E[])new Object[size - 1];
        int j = 0;
        for (int i = 0; i < size; i++) {
            if (!setElems[i].equals(o)) {
                newSetElems[j] = setElems[i];
                j++;
            }
        }
        setElems = newSetElems;
        size--;
        return true;
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            if (setElems[i] != null) {
                str.append(setElems[i].toString());
                if (size - i > 1)
                    str.append(", ");
            }
        }
        str.append("]");
        return str.toString();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object elem : c)
            if (!contains(elem))
                return false;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean isChanged = false;
        for (E elem : c)
            if (add(elem))
                isChanged = true;
        return isChanged;
    }
//
    @Override
    public boolean retainAll(Collection<?> c) {
        boolean isChanged = (size != 0 && c.isEmpty());
        MyTreeSet<E> newSet = new MyTreeSet<E>();
        for (Object elem : c) {
            if (contains(elem))
                newSet.add((E) elem);
            else
                isChanged = true;
        }
        setElems = newSet.setElems;
        size = newSet.size;
        return isChanged;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean isChanged = false;
        for (Object elem : c)
            if (remove(elem))
                isChanged = true;
        return isChanged;
    }

    @Override
    public void clear() {
        size = 0;
        setElems = (E[]) new Object[size];
    }
}
