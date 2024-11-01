package by.it.group351003.zhuravski.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
/*
toString()
                add(E element)
                remove(int)
                remove(E element)//Скам
                size()

                addFirst(E element)
                addLast(E element)

                element()
                getFirst()
                getLast()

                poll()
                pollFirst()
                pollLast()
 */
//Ультрабюджетный список
public class MyLinkedList<E> implements Deque<E> {
    int length = 0;
    class Elem {
        Elem(E val, Elem prev, Elem next) {
            this.val = val;
            this.prev = prev;
            this.next = next;
        }
        E val;
        Elem next = null;
        Elem prev = null;
    };
    Elem first = null;
    Elem last = null;
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append('[');
        if (length > 0) {
            str.append(first.val.toString());
            Elem cur = first.next;
            while (cur != null) {
                str.append(", ");
                str.append(cur.val.toString());
                cur = cur.next;
            }
        }
        str.append(']');
        return str.toString();
    }
    void remove_elem(Elem cur) {
        if (cur.prev != null) {
            cur.prev.next = cur.next;
        }
        else {
            first = cur.next;
        }
        if (cur.next != null) {
            cur.next.prev = cur.prev;
        }
        else {
            last = cur.prev;
        }
        length--;
    }
    public E remove(int index) {
        if ((index >= 0) && (index < length)) {
            Elem cur = first;
            for (int i = 1; i <= index; i++) {
                cur = cur.next;
            }
            remove_elem(cur);
            return cur.val;
        }
        return null;
    }
    public boolean remove(Object element) {
        if (length > 0) {
            Elem cur = first;
            while ((cur != null) && (cur.val != element)) {
                cur = cur.next;
            }
            if (cur != null) {
                remove_elem(cur);
                return true;
            }
        }
        return false;
    }

    @Override
    public void addFirst(E e) {
        Elem elem = new Elem(e, null, first);
        if (length > 0) {
            first.prev = elem;
        }
        else {
            last = elem;
        }
        first = elem;
        length++;
    }

    @Override
    public void addLast(E e) {
        Elem elem = new Elem(e, last, null);
        if (length > 0) {
            last.next = elem;
        }
        else {
            first = elem;
        }
        last = elem;
        length++;
    }

    @Override
    public boolean offerFirst(E e) {
        return false;
    }

    @Override
    public boolean offerLast(E e) {
        return false;
    }

    @Override
    public E removeFirst() {
        return null;
    }

    @Override
    public E removeLast() {
        return null;
    }

    @Override
    public E pollFirst() {
        if (length > 0) {
            E res = first.val;
            remove_elem(first);
            return res;
        }
        return null;
    }

    @Override
    public E pollLast() {
        if (length > 0) {
            E res = last.val;
            remove_elem(last);
            return res;
        }
        return null;
    }

    @Override
    public E getFirst() {
        if (length > 0) {
            return first.val;
        }
        return null;
    }

    @Override
    public E getLast() {
        if (length > 0) {
            return last.val;
        }
        return null;
    }

    @Override
    public E peekFirst() {
        return null;
    }

    @Override
    public E peekLast() {
        return null;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean add(E e) {
        addLast(e);
        return true;
    }

    @Override
    public boolean offer(E e) {
        return false;
    }

    @Override
    public E remove() {
        return null;
    }

    @Override
    public E poll() {
        if (length > 0) {
            E res = first.val;
            remove_elem(first);
            return res;
        }
        return null;
    }

    @Override
    public E element() {
        if (length > 0) {
            return first.val;
        }
        return null;
    }

    @Override
    public E peek() {
        return null;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public void push(E e) {

    }

    @Override
    public E pop() {
        return null;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public int size() {
        return length;
    }

    @Override
    public boolean isEmpty() {
        return false;
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
    public Iterator<E> descendingIterator() {
        return null;
    }
}
