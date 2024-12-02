package by.it.group351001.voronov.lesson11;


import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyHashSet<E> implements Set<E> {

    class Element<E> {
        E value;
        Element<E> nextElement;

        Element(E value) {
            this.value = value;
        }
    }

    static final int DEFAULT_CAPACITY = 32;
    Element<E>[] buckets;
    int size;

    public MyHashSet() {
        this(DEFAULT_CAPACITY);
    }

    public MyHashSet(int capacity) {
        buckets = new Element[capacity];
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[");
        boolean isFirst = true;
        for (int i = 0; i < buckets.length; i++) {
            Element<E> currentElement = buckets[i];
            while (currentElement != null) {
                if (!isFirst) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append(currentElement.value);
                isFirst = false;
                currentElement = currentElement.nextElement;
            }
        }

        stringBuilder.append("]");
        return stringBuilder.toString();
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
    public boolean contains(Object obj) {
        Element<E> currentElement = buckets[computeIndex(obj)];
        while (currentElement != null) {
            if (currentElement.value.equals(obj)) {
                return true;
            }
            currentElement = currentElement.nextElement;
        }
        return false;
    }

    @Override
    public boolean add(E element) {
        int index = computeIndex(element);
        Element<E> currentElement = buckets[index];
        while (currentElement != null) {
            if (currentElement.value.equals(element)) {
                return false;
            }
            currentElement = currentElement.nextElement;
        }
        Element<E> newElement = new Element<>(element);
        newElement.nextElement = buckets[index];
        buckets[index] = newElement;
        size++;
        if (size > buckets.length * 0.75) {
            resizeBuckets();
        }
        return true;
    }

    void resizeBuckets() {
        Element<E>[] newBuckets = new Element[buckets.length * 2];
        for (int i = 0; i < buckets.length; i++) {
            Element<E> currentElement = buckets[i];
            while (currentElement != null) {
                Element<E> nextElement = currentElement.nextElement;
                int newIndex = currentElement.value.hashCode() & 0x7FFFFFFF % newBuckets.length;
                currentElement.nextElement = newBuckets[newIndex];
                newBuckets[newIndex] = currentElement;
                currentElement = nextElement;
            }
        }
        buckets = newBuckets;
    }

    @Override
    public boolean remove(Object obj) {
        int index = computeIndex(obj);
        Element<E> currentElement = buckets[index];
        Element<E> previousElement = null;
        while (currentElement != null) {
            if (currentElement.value.equals(obj)) {
                if (previousElement == null) {
                    buckets[index] = currentElement.nextElement;
                } else {
                    previousElement.nextElement = currentElement.nextElement;
                }
                size--;
                return true;
            }
            previousElement = currentElement;
            currentElement = currentElement.nextElement;
        }
        return false;
    }

    @Override
    public void clear() {
        for (int i = 0; i < buckets.length; i++)
            buckets[i] = null;
        size = 0;
    }

    int computeIndex(Object value) {
        return (value.hashCode() & 0x7FFFFFFF) % buckets.length;
    }

    ////////////////////////////////////////////////////////////////////////
    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] array) {
        return null;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return false;
    }
}