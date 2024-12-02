package by.it.group351001.voronov.lesson11;


import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyLinkedHashSet<E> implements Set<E> {
    class Element<E> {
        E value;
        Element<E> nextElement;
        Element<E> prevElement, linkedElement;
        Element(E value) {
            this.value = value;
        }
    }

    static final int DEFAULT_CAPACITY = 20;
    int elementCount = 0;
    Element<E>[] buckets;

    Element<E> firstElement, lastElement;

    MyLinkedHashSet() {
        this(DEFAULT_CAPACITY);
    }

    MyLinkedHashSet(int initialCapacity) {
        buckets = new Element[initialCapacity];
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[");
        Element<E> current = firstElement;
        while (current != null) {
            stringBuilder.append(current.value);
            if (current.linkedElement != null)
                stringBuilder.append(", ");
            current = current.linkedElement;
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public int size() {
        return elementCount;
    }

    @Override
    public boolean isEmpty() {
        return elementCount == 0;
    }

    @Override
    public boolean contains(Object obj) {
        for (Element<E> bucket : buckets) {
            Element<E> current = bucket;
            while (current != null) {
                if (obj.equals(current.value)) {
                    return true;
                }
                current = current.nextElement;
            }
        }
        return false;
    }

    void linkElement(Element<E> newElement) {
        if (firstElement == null) {
            firstElement = newElement;
        } else {
            lastElement.linkedElement = newElement;
            newElement.prevElement = lastElement;
        }
        lastElement = newElement;
    }

    void unlinkElement(Element<E> element) {
        if (element.linkedElement != null) {
            element.linkedElement.prevElement = element.prevElement;
        } else {
            lastElement = element.prevElement;
        }
        if (element.prevElement != null) {
            element.prevElement.linkedElement = element.linkedElement;
        } else {
            firstElement = element.linkedElement;
        }
    }

    void expandBuckets() {
        Element<E>[] newBuckets = new Element[buckets.length * 2];
        for (Element<E> current : buckets) {
            while (current != null) {
                Element<E> next = current.nextElement;
                int newIndex = (current.value.hashCode() & 0x7FFFFFFF) % newBuckets.length;
                current.nextElement = newBuckets[newIndex];
                newBuckets[newIndex] = current;
                current = next;
            }
        }
        buckets = newBuckets;
    }

    @Override
    public boolean remove(Object obj) {
        int index = computeHash(obj);
        Element<E> current = buckets[index];
        Element<E> previous = null;
        while (current != null) {
            if (obj.equals(current.value)) {
                if (previous == null) {
                    buckets[index] = current.nextElement;
                } else {
                    previous.nextElement = current.nextElement;
                }
                elementCount--;
                unlinkElement(current);
                return true;
            }
            previous = current;
            current = current.nextElement;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    int computeHash(Object obj) {
        return (obj.hashCode() & 0x7FFFFFFF) % buckets.length;
    }

    @Override
    public void clear() {
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = null;
        }
        elementCount = 0;
        firstElement = null;
        lastElement = null;
    }

    @Override
    public boolean add(Object obj) {
        Element<E> newElement = new Element<E>((E) obj);
        int index = computeHash(obj);
        Element<E> current = buckets[index];
        while (current != null) {
            if (current.value.equals(obj)) {
                return false;
            }
            current = current.nextElement;
        }
        newElement.nextElement = buckets[index];
        buckets[index] = newElement;
        linkElement(newElement);
        if (++elementCount > buckets.length * 0.7)
            expandBuckets();
        return true;
    }

    @Override
    public boolean addAll(Collection c) {
        boolean modified = false;
        for (Object item : c) {
            if (add(item)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection c) {
        boolean modified = false;
        for (Object item : c) {
            if (remove(item)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection c) {
        if (c.isEmpty()) {
            clear();
            return true;
        }
        boolean modified = false;
        Element<E> current = firstElement;
        while (current != null) {
            Element<E> next = current.linkedElement;
            if (!c.contains(current.value)) {
                remove(current.value);
                modified = true;
            }
            current = next;
        }
        return modified;
    }

    @Override
    public boolean containsAll(Collection c) {
        for (Object item : c) {
            if (!contains(item))
                return false;
        }
        return true;
    }

    //------------------------------

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public Object[] toArray(Object[] a) {
        return new Object[0];
    }
}