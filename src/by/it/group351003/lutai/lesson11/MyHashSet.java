package by.it.group351003.lutai.lesson11;

import by.it.group351003.lutai.lesson11.MyLinkedList;

import java.util.*;



public class MyHashSet<E> implements Set {

    private final int maxDepth = 4;
    private int currentSize = 6;
    private MyLinkedList<E>[] values = new MyLinkedList[currentSize];

    private void resize(){
        int newSize = currentSize * 2;
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
            private int currentIndex = 0;
            @Override
            public boolean hasNext() {
                return currentIndex < currentSize;
            }

            @Override
            public Object next() {
                while(values[currentIndex] == null){
                    currentIndex++;
                }
                return values[currentIndex];
            }
        };
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public boolean add(Object o) {
        int hashIndex = Math.abs(o.hashCode() % currentSize);;
        boolean isUnique = false;
        if(values[hashIndex] == null){
            values[hashIndex] = new MyLinkedList<>();
        }
        if(values[hashIndex].takeValue((E)o) == null){
            isUnique = true;
            values[hashIndex].add((E) o);
            if(values[hashIndex].getSize() == maxDepth){
                resize();
            }
        }
        return isUnique;
    }

    @Override
    public boolean remove(Object o) {
        int hashIndex = Math.abs(o.hashCode() % currentSize);;
        boolean result = false;
        if(values[hashIndex] != null){
            result = values[hashIndex].remove((E)o);
        }
        return result;
    }

    @Override
    public boolean addAll(Collection c) {
        return false;
    }

    @Override
    public void clear() {
        Arrays.fill(values, null);
    }

    @Override
    public boolean removeAll(Collection c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection c) {
        return false;
    }

    @Override
    public boolean containsAll(Collection c) {
        return false;
    }

    @Override
    public Object[] toArray(Object[] a) {
        return new Object[0];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        boolean first = true;
        for (MyLinkedList<E> value : values) {
            if (value != null && value.getSize() > 0) {
                if (!first) sb.append(", ");
                sb.append(value.toString());
                first = false;
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
