package by.it.group351005.bitno.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class MyLinkedHashSet<E> implements Set<E> {
    private int hash(E e) {return (e.hashCode() >>> 16) ^ e.hashCode();}
    private static class Node<E>
    {
        int hash;
        E value;//
        Node<E> next;
        Node<E> nextInList = null;
        private Node(int newHash, E newValue, Node<E> newNext){
            hash = newHash;
            value = newValue;
            next = newNext;
        }
    }
    Node<E> listHead = null;
    private final int DEFAULT_INITIAL_CAPACITY = 1 << 2;
    @SuppressWarnings("unchecked")
    private Node<E>[] setElems = (Node<E>[]) new Node[DEFAULT_INITIAL_CAPACITY];
    private int size = 0;
    private int capacity = DEFAULT_INITIAL_CAPACITY;

    private void grow(){
        int newCapacity = capacity * 2;
        Node<E>[] newSetElems = (Node<E>[]) new Node[newCapacity];

        for (int i = 0; i < newCapacity; i++) {
            newSetElems[i] = null;
        }
        for (int i = 0; i < capacity; i++) {
            Node<E> newElem = setElems[i];
            while (newElem != null) {
                Node<E> insertedElem = new Node<E>(newElem.hash, newElem.value, null);
                int newElemPos = insertedElem.hash & (newCapacity - 1);
                Node<E> curElem = newSetElems[newElemPos];
                if (curElem != null) {
                    while (curElem.next != null) {
                        curElem = curElem.next;
                    }
                    curElem.next = insertedElem;
                } else
                    newSetElems[newElemPos] = insertedElem;
                newElem = newElem.next;
            }
        }
        capacity = newCapacity;
        setElems = newSetElems;
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
        E e = (E) o;
        int curPos = hash(e) & (capacity - 1);
        Node curNode = setElems[curPos];
        while (curNode != null) {
            if (curNode.value.equals(e))
                return true;
            curNode = curNode.next;
        }
        return false;
    }
    public final String toString() {
        StringBuilder answer = new StringBuilder("");
        Node<E> curElem = listHead;
        answer.append('[');
        while (curElem != null){
            answer.append(curElem.value.toString());
            curElem = curElem.nextInList;
            if (curElem != null){
                answer.append(", ");
            }
        }
        answer.append(']');
        return answer.toString();
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
        if (e == null || contains(e))
            return false;
        Node<E> newElem = new Node<E>(hash(e), e, null);
        if (listHead == null)
            listHead = newElem;
        else {
            Node<E> curNode = listHead;
            while (curNode.nextInList != null)
                curNode = curNode.nextInList;
            curNode.nextInList = newElem;
        }

        if (capacity == size)
            grow();
        int newElemPos = newElem.hash & (capacity - 1);
        Node<E> curElem = setElems[newElemPos];
        if (curElem != null) {
            while (curElem.next != null) {
                curElem = curElem.next;
            }
            curElem.next = newElem;
        }
        else
            setElems[newElemPos] = newElem;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        E removedValue = (E) o;
        if (listHead != null)
            if (listHead.value.equals(removedValue))
                listHead = listHead.nextInList;
            else {
                Node<E> curNode = listHead;
                while (curNode.nextInList != null) {
                    if (curNode.nextInList.value.equals(removedValue))
                        curNode.nextInList = curNode.nextInList.nextInList;
                    else
                        curNode = curNode.nextInList;
                }
            }
        int newPos = hash(removedValue) & (capacity - 1);
        Node<E> curNode = setElems[newPos];
        if (curNode != null) {
            if (curNode.value.equals(removedValue)) {
                setElems[newPos] = setElems[newPos].next;
                size--;
                return true;
            }
            while (curNode.next != null) {
                if (curNode.next.value.equals(removedValue)) {
                    curNode.next = curNode.next.next;
                    size--;
                    return true;
                }
                curNode = curNode.next;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object element: c)
            if (!contains(element))
                return false;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean isChanged = false;
        for (Object element : c)
            if (add((E)element))
                isChanged = true;
        return isChanged;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean isChanged = false;
        Node<E> curElem = listHead;
        while (curElem != null) {
            if (!c.contains(curElem.value)) {
                remove(curElem.value);
                isChanged = true;
                curElem = listHead;
                continue;
            }
            curElem = curElem.nextInList;
        }
        return isChanged;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean isChanged = false;
        String str;
        if (size == 57)
            size = 57;
        str = toString();
        for (Object element : c) {
            if (element.equals(513))
                element = element;
            if (remove(element))
                isChanged = true;
        }
        return isChanged;
    }

    @Override
    public void clear() {
        size = 0;
        capacity = DEFAULT_INITIAL_CAPACITY;
        setElems = (Node<E>[]) new Node[capacity];
        listHead = null;
    }
}
