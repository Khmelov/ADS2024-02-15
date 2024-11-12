package by.it.group310901.mikhaylove.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinkedList<E> implements Deque<E> {

    private static class Node<E> {
        Node<E> next;
        Node<E> previous;
        E value;

        Node(E value) {
            this.value = value;
            this.next = this;
            this.previous = this;
        }

        Node(E value, Node<E> next, Node<E> previous) {
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
            Node<E> node = new Node<E>(value, next, this);
            next.previous = node;
            next = node;
        }

        public void addPrevious(E value) {
            Node<E> node = new Node<E>(value, this, previous);
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
            E value = previous.value;
            previous = previous.previous;
            previous.next = this;
            return value;
        }
    }

    private Node<E> head = new Node<E>(null);
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("[");
        var node = head;
        while (node.hasNext()) {
            node = node.next;
            result.append(node.value.toString());
            if (node.hasNext()) {
                result.append(", ");
            }
        }
        result.append("]");
        return result.toString();
    }

    @Override
    public boolean add(E e) {
        try {
            addLast(e);
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public E remove(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        int i = -1;
        for (var node = head; node.hasNext(); node = node.next) {
            if (++i == index) {
                return node.pollNext();
            }
        }

        return null;
    }

    @Override
    public boolean remove(Object o) {
        for (var node = head; node.hasNext(); node = node.next) {
            if (node.next.value.equals(o)) {
                node.pollNext();
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        var node = head;
        int length = 0;
        while (node.hasNext()) {
            length++;
            node = node.next;
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
        if (head.hasNext()) {
            return head.next.value;
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public E getLast() {
        if (head.hasPrevious()) {
            return head.previous.value;
        } else {
            throw new NoSuchElementException();
        }
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

    //=============== not required ========================

    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isEmpty'");
    }

    @Override
    public Object[] toArray() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toArray'");
    }

    @Override
    public <T> T[] toArray(T[] a) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toArray'");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'containsAll'");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeAll'");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'retainAll'");
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clear'");
    }

    @Override
    public boolean offerFirst(E e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'offerFirst'");
    }

    @Override
    public boolean offerLast(E e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'offerLast'");
    }

    @Override
    public E removeFirst() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeFirst'");
    }

    @Override
    public E removeLast() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeLast'");
    }

    @Override
    public E peekFirst() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'peekFirst'");
    }

    @Override
    public E peekLast() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'peekLast'");
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeFirstOccurrence'");
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeLastOccurrence'");
    }

    @Override
    public boolean offer(E e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'offer'");
    }

    @Override
    public E remove() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public E peek() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'peek'");
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addAll'");
    }

    @Override
    public void push(E e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'push'");
    }

    @Override
    public E pop() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'pop'");
    }

    @Override
    public boolean contains(Object o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'contains'");
    }

    @Override
    public Iterator<E> iterator() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'iterator'");
    }

    @Override
    public Iterator<E> descendingIterator() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'descendingIterator'");
    }

}
