package by.it.group351003.zhuravski.lesson11;

import java.util.Collection;
import java.util.Iterator;
class Elem<E> {
    Elem(E val, Elem prev, Elem next) {
        this.val = val;
        this.prev = prev;
        this.next = next;
    }
    E val;
    Elem<E> next = null;
    Elem<E> prev = null;
};
public class MyPseudoList<E> {
    int length = 0;
    Elem<E> first = null;
    Elem<E> last = null;
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
            Elem<E> cur = first;
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

    public boolean offerFirst(E e) {
        return false;
    }

    public boolean offerLast(E e) {
        return false;
    }

    public E removeFirst() {
        return null;
    }

    public E removeLast() {
        return null;
    }

    public E pollFirst() {
        if (length > 0) {
            E res = first.val;
            remove_elem(first);
            return res;
        }
        return null;
    }

    public E pollLast() {
        if (length > 0) {
            E res = last.val;
            remove_elem(last);
            return res;
        }
        return null;
    }

    public E getFirst() {
        if (length > 0) {
            return first.val;
        }
        return null;
    }

    public E getLast() {
        if (length > 0) {
            return last.val;
        }
        return null;
    }

    public E peekFirst() {
        return null;
    }

    public E peekLast() {
        return null;
    }

    public boolean removeFirstOccurrence(Object o) {
        return false;
    }

    public boolean removeLastOccurrence(Object o) {
        return false;
    }

    public boolean add(E e) {
        addLast(e);
        return true;
    }

    public boolean offer(E e) {
        return false;
    }

    public E remove() {
        return null;
    }

    public E poll() {
        if (length > 0) {
            E res = first.val;
            remove_elem(first);
            return res;
        }
        return null;
    }

    public E element() {
        if (length > 0) {
            return first.val;
        }
        return null;
    }

    public E peek() {
        return null;
    }

    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    public boolean removeAll(Collection<?> c) {
        return false;
    }

    public boolean retainAll(Collection<?> c) {
        return false;
    }

    public void clear() {
        while (length > 0) {
            remove_elem(first);
        }
    }

    public void push(E e) {

    }

    public E pop() {
        return null;
    }

    public boolean containsAll(Collection<?> c) {
        return false;
    }

    public boolean contains(Object o) {
        return false;
    }

    public int size() {
        return length;
    }

    public boolean isEmpty() {
        return false;
    }

    public Iterator<E> iterator() {
        return null;
    }

    public Object[] toArray() {
        return new Object[0];
    }

    public <T> T[] toArray(T[] a) {
        return null;
    }

    public Iterator<E> descendingIterator() {
        return null;
    }
}
