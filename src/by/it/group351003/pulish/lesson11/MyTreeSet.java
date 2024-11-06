package by.it.group351003.pulish.lesson11;

import java.util.*;

public class MyTreeSet<E> implements Set {
    private final int maxDepth = 5;
    private int currentSize = 10;
    private MyLinkedList<E> list = new MyLinkedList<>();
    private MyLinkedList<E>[] values = new MyLinkedList[currentSize];

    private void resize(){
        int newSize = currentSize + (currentSize >> 1);
        MyLinkedList<E>[] newValues = new MyLinkedList[newSize];
        for (MyLinkedList<E> value : values) {
            if (value != null) {
                Node<E> temp = value.getHead();
                while (temp != null) {
                    int newIndex = Math.abs(temp.value.hashCode() % newSize);
                    if (newValues[newIndex] == null) {
                        newValues[newIndex] = new MyLinkedList<>();
                    }
                    newValues[newIndex].add(temp.value);
                    temp = temp.next;
                }
            }
        }
        values = newValues;
        currentSize = newSize;
    }

    @Override
    public int size() {
        int size = 0;
        for (MyLinkedList<E> value : values) {
            if (value != null) {
                size += value.getSize();
            }
        }
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        int hashIndex = o.hashCode() % currentSize;
        boolean result = false;
        if(values[hashIndex] != null){
            result = values[hashIndex].takeValue((E) o) != null;
        }
        return result;
    }

    @Override
    public Iterator iterator() {
        return new Iterator() {
            private Node<E> current = list.getHead();

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {
                E value = null;
                if (current != null) {
                    value = current.value;
                    current = current.next;
                }
                return value;
            }
        };
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public boolean add(Object o) {
        int hashIndex = Math.abs(o.hashCode() % currentSize);
        boolean isUnique = false;
        if(values[hashIndex] == null){
            values[hashIndex] = new MyLinkedList<>();
        }
        if(values[hashIndex].takeValue((E)o) == null){
            isUnique = true;
            values[hashIndex].add((E) o);
            list.add((E) o);
            if(values[hashIndex].getSize() == maxDepth){
                resize();
            }
        }
        return isUnique;
    }

    @Override
    public boolean remove(Object o) {
        int hashIndex = Math.abs(o.hashCode() % currentSize);
        boolean result = false;
        if(values[hashIndex] != null){
            result = values[hashIndex].remove((E)o);
            if(result) {
                list.remove((E) o);
            }
        }
        return result;
    }

    @Override
    public boolean containsAll(Collection c) {
        boolean result = true;
        for (Object o : c) {
            result = result & contains(o);
            if(!result) break;
        }
        return result;
    }

    @Override
    public boolean addAll(Collection c) {
        boolean result = false;
        for (Object o : c) {
            result = result | add(o);
        }
        return result;
    }

    @Override
    public void clear() {
        Arrays.fill(values, null);
        list = new MyLinkedList<>();
    }

    @Override
    public boolean removeAll(Collection c) {
        boolean result = false;
        for (Object o : c) {
            result = result | remove(o);
        }
        return result;
    }

    @Override
    public boolean retainAll(Collection c) {
        boolean result = false;
        Node<E> temp = list.getHead();
        while (temp != null) {
            E val = temp.value;
            if (!c.contains(val)) {
                remove(val);
                result = true;
            }
            temp = temp.next;
        }
        return result;
    }

    @Override
    public Object[] toArray(Object[] a) {
        return new Object[0];
    }

    @Override
    public String toString() {
        Comparator<Integer> comparator = Integer::compare;
        list.sort((Comparator<E>) comparator);
        String result;
        if(list.getSize() > 0){
            result = "[" + list + "]";
        }else{
            result = "[]";
        }
        return result;
    }
}
