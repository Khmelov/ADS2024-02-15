package by.it.group310901.baradzin.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementation of Deque interface using doubly linked list
 *
 * @param <E> the type of elements held in this deque
 */
public class MyLinkedList<E> implements Deque<E> {

    private Node<E> head;

    public MyLinkedList() {
        head = new Node<E>(null);
        head.next = head.previous = head;
    }

    private static class Node<E> {
        Node<E> next;
        Node<E> previous;
        E value;

        public Node(E value) {
            this.value = value;
        }

        public Node(E value, Node<E> next, Node<E> previous) {
            this.value = value;
            this.next = next;
            this.previous = previous;
        }

        public boolean hasNext() {
            return next.value != null;
        }

        public boolean hasPrevious() {
            return previous.value != null;
        }

        public void addNext(E value) {
            var node = new Node<E>(value, next, this);
            next.previous = node;
            next = node;
        }

        public void addPrevious(E value) {
            var node = new Node<E>(value, this, previous);
            previous.next = node;
            previous = node;
        }

        public E pollNext() {
            E value = next.value;
            next = next.next;
            next.previous = this;
            return value;
        }

        public E pollPrevious() {
            var value = previous.value;
            previous = previous.previous;
            previous.next = this;
            return value;
        }
    }

    // ----- required ----------------------------------------------------------

    @Override
    public String toString() {
        var sb = new StringBuilder("[");
        for (var node = head; node.hasNext();)
            sb.append((node = node.next).value).append(node.hasNext() ? ", " : "");
        return sb.append("]").toString();
    }

    @Override
    public boolean add(E e) {
        addLast(e);
        return true;
    }

    public E remove(int index) {
        if (index < 0)
            throw new IndexOutOfBoundsException();

        var length = -1;
        for (var node = head; node.hasNext(); node = node.next)
            if (++length == index)
                return node.pollNext();

        throw new IndexOutOfBoundsException();
    }

    @Override
    public boolean remove(Object o) {
        for (var node = head; node.hasNext(); node = node.next)
            if (node.next.value.equals(o)) {
                node.pollNext();
                return true;
            }
        return false;
    }

    @Override
    public int size() {
        var node = head;
        var length = 0;

        while (node.hasNext()) {
            node = node.next;
            length++;
        }

        return length;
    }

    @Override
    public void addFirst(E e) {
        head.addNext(e);
    }

    @Override
    public void addLast(E e) {
        head.addPrevious(e);
    }

    @Override
    public E element() {
        return getFirst();
    }

    @Override
    public E getFirst() {
        var e = peekFirst();
        if (e == null)
            throw new NoSuchElementException();
        return e;
    }

    @Override
    public E getLast() {
        var e = peekLast();
        if (e == null)
            throw new NoSuchElementException();
        return e;
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E pollFirst() {
        return head.pollNext();
    }

    @Override
    public E pollLast() {
        return head.pollPrevious();
    }

    // ===== optional ==========================================================
    // ! NOT TESTED. But, maybe, will work. Or not. Thats normal

    @Override
    public boolean isEmpty() {
        return !head.hasNext() && !head.hasPrevious();
    }

    @Override
    public Object[] toArray() {
        return toArray(new Object[size()]);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < size())
            return (T[]) toArray();
        var i = 0;
        for (var node = head; node.hasNext();)
            a[i++] = (T) (node = node.next).value;
        return a;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (var i : c)
            if (!contains(i))
                return false;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        var oldSize = size();
        for (var i : c)
            remove(i);
        return size() != oldSize;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        var oldSize = size();
        for (var node = head; node.hasNext(); node = node.next)
            if (!c.contains(node.next.value))
                node.pollNext();
        return size() != oldSize;
    }

    @Override
    public void clear() {
        head = new Node<E>(null);
    }

    @Override
    public boolean offerFirst(E e) {
        addFirst(e);
        return true;
    }

    @Override
    public boolean offerLast(E e) {
        addLast(e);
        return true;
    }

    @Override
    public E removeFirst() {
        var e = pollFirst();
        if (e == null)
            throw new NoSuchElementException();
        return e;
    }

    @Override
    public E removeLast() {
        var e = pollLast();
        if (e == null)
            throw new NoSuchElementException();
        return e;
    }

    @Override
    public E peekFirst() {
        return head.hasNext() ? head.next.value : null;
    }

    @Override
    public E peekLast() {
        return head.hasPrevious() ? head.previous.value : null;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        for (var node = head; node.hasNext(); node = node.next)
            if (node.next.value.equals(o)) {
                node.pollNext();
                return true;
            }
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        for (var node = head; node.hasPrevious(); node = node.previous)
            if (node.previous.value.equals(o)) {
                node.pollPrevious();
                return true;
            }
        return false;
    }

    @Override
    public boolean offer(E e) {
        return offerLast(e);
    }

    @Override
    public E remove() {
        return removeFirst();
    }

    @Override
    public E peek() {
        return peekFirst();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        var oldSize = size();
        for (var i : c)
            addLast(i);
        return size() != oldSize;
    }

    @Override
    public void push(E e) {
        addFirst(e);
    }

    @Override
    public E pop() {
        return removeFirst();
    }

    @Override
    public boolean contains(Object o) {
        for (var node = head; node.hasNext();) {
            if ((node = node.next).value == o)
                return true;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            Node<E> node = head;

            @Override
            public boolean hasNext() {
                return node.hasNext();
            }

            @Override
            public E next() {
                return (node = node.next).value;
            }
        };
    }

    @Override
    public Iterator<E> descendingIterator() {
        return new Iterator<E>() {
            Node<E> node = head;

            @Override
            public boolean hasNext() {
                return node.hasPrevious();
            }

            @Override
            public E next() {
                return (node = node.previous).value;
            }
        };
    }
}
