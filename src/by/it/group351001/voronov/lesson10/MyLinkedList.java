package by.it.group351001.voronov.lesson10;

import java.util.*;

public class MyLinkedList<E> implements Deque<E> {

    static class Node<E> {
        public E value;
        public Node<E> nextNode;
        public Node<E> previousNode;

        public Node(E value) {
            this.value = value;
        }
    }

    Node<E> headNode;
    Node<E> tailNode;
    int size;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        Node<E> currentNode = headNode;
        while (currentNode.nextNode != null) {
            sb.append(currentNode.value);
            sb.append(", ");
            currentNode = currentNode.nextNode;
        }
        sb.append(currentNode.value);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean add(E element) {
        Node<E> newNode = new Node<>(element);

        if (headNode == null)
            headNode = newNode;
        else {
            tailNode.nextNode = newNode;
            newNode.previousNode = tailNode;
        }
        tailNode = newNode;
        size++;
        return true;
    }

    @Override
    public void addFirst(E element) {
        Node<E> newNode = new Node<>(element);
        newNode.nextNode = headNode;
        if (size == 0) {
            headNode = tailNode = newNode;
        } else {
            headNode.previousNode = newNode;
            headNode = newNode;
        }
        size++;
    }

    @Override
    public void addLast(E element) {
        add(element);
    }

    @Override
    public E pollFirst() {
        if (size == 0)
            return null;
        E value = headNode.value;
        if (size == 1)
            headNode = tailNode = null;
        else {
            headNode = headNode.nextNode;
            headNode.previousNode = null;
        }
        size--;
        return value;
    }

    @Override
    public E pollLast() {
        if (size == 0)
            return null;
        E value = tailNode.value;
        if (size == 1)
            headNode = tailNode = null;
        else {
            tailNode = tailNode.previousNode;
            tailNode.nextNode = null;
        }
        size--;
        return value;
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E getFirst() {
        if (size == 0)
            return null;
        return headNode.value;
    }

    @Override
    public E getLast() {
        if (size == 0)
            return null;
        return tailNode.value;
    }

    @Override
    public E element() {
        return getFirst();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean remove(Object element) {
        Node<E> currentNode = headNode;
        while (currentNode != null) {
            if (Objects.equals(element, currentNode.value)) {
                if (currentNode.nextNode != null)
                    currentNode.nextNode.previousNode = currentNode.previousNode;
                else
                    tailNode = currentNode.previousNode;

                if (currentNode.previousNode != null)
                    currentNode.previousNode.nextNode = currentNode.nextNode;
                else
                    headNode = currentNode.nextNode;
                size--;
                return true;
            }
            currentNode = currentNode.nextNode;
        }
        return false;
    }

    public E remove(int index) {
        Node<E> currentNode = headNode;
        if (index >= 0 && index < size) {
            int currentIndex = 0;
            while (currentIndex++ < index) {
                currentNode = currentNode.nextNode;
            }
            if (currentNode.nextNode != null)
                currentNode.nextNode.previousNode = currentNode.previousNode;
            else
                tailNode = currentNode.previousNode;

            if (currentNode.previousNode != null)
                currentNode.previousNode.nextNode = currentNode.nextNode;
            else
                headNode = currentNode.nextNode;
            size--;
            return currentNode.value;
        }
        return null;
    }

    //////////////////////////////////////////

    @Override
    public boolean offer(E element) {
        return false;
    }

    @Override
    public E remove() {
        return null;
    }

    @Override
    public E peek() {
        return null;
    }

    @Override
    public boolean offerFirst(E element) {
        return false;
    }

    @Override
    public boolean offerLast(E element) {
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
    public E peekFirst() {
        return null;
    }

    @Override
    public E peekLast() {
        return null;
    }

    @Override
    public boolean removeFirstOccurrence(Object element) {
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object element) {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object element) {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Iterator<E> descendingIterator() {
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
    public void push(E element) {

    }

    @Override
    public E pop() {
        return null;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return false;
    }

    @Override
    public void clear() {

    }
}