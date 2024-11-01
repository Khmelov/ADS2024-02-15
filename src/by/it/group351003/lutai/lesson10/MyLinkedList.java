package by.it.group351003.lutai.lesson10;


import java.util.*;

class Node<E> {
    Node prev;
    Node next;
    E val;
    Node(E val){
        this.val = val;
    }
}


public class MyLinkedList<E> implements Deque<E> {
    private Node<E> head;
    private Node<E> tail;
    private int itemCount = 0;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node temp = head;
        while(temp.next != null){
            sb.append(temp.val).append(", ");
            temp = temp.next;
        }
        sb.append(temp.val).append("]");
        return sb.toString();
    }

    @Override
    public boolean add(E e) {
        final int temp = itemCount;
        addLast(e);
        return itemCount > temp;
    }

    public E remove(int index){
        E item = null;
        Node<E> temp;
        if (index == 0) {
            pollFirst();
        } else if (index == itemCount - 1) {
            pollLast();
        } else {
            temp = head;
            for (int i = 0; i < index; i++) {
                temp = temp.next;
            }
            item = temp.val;
            temp.prev.next = temp.next;
            temp.next.prev = temp.prev;
            temp = head;
        }
        itemCount--;
        return item;
    }

    @Override
    public boolean remove(Object o) {
        int prevCount = itemCount;
        Node<E> temp = head;
        while (temp != null) {
            if (temp.val.equals(o)) {
                if(temp == head){
                    pollFirst();
                }else if(temp == tail){
                    pollLast();
                }else{
                    temp.prev.next = temp.next;
                    temp.next.prev = temp.prev;
                    itemCount--;
                }
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    @Override
    public int size() {
        return itemCount;
    }






    @Override
    public void addFirst(E e) {
        Node temp = new Node(e);
        if(itemCount == 0){
            head = temp;
            tail = temp;
        }else {
            temp.prev = null;
            temp.next = head;
            head.prev = temp;
            head = temp;
        }
        itemCount++;
    }

    @Override
    public void addLast(E e) {
        Node temp = new Node(e);
        if(itemCount == 0){
            head = temp;
            tail = temp;
        }else {
            temp.prev = tail;
            tail.next = temp;
            temp.next = null;
            tail = temp;
        }
        itemCount++;
    }




    @Override
    public E element() {
        E item = null;
        if(itemCount > 0){
            item = head.val;
        }
        return item;
    }

    @Override
    public E getFirst() {
        E item = null;
        if(itemCount > 0){
            item = head.val;
        }
        return item;
    }

    @Override
    public E getLast() {
        E item = null;
        if(itemCount > 0){
            item = tail.val;
        }
        return item;
    }






    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E pollFirst() {
        E value = head.val;
        head = head.next;
        head.prev = null;
        itemCount--;
        return value;
    }

    @Override
    public E pollLast() {
        E value = null;
        value = tail.val;
        tail = tail.prev;
        tail.next = null;
        itemCount--;
        return value;
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
    public boolean offer(E e) {
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
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> current = head;
            @Override
            public boolean hasNext() {
                return current.next != null;
            }

            @Override
            public E next() {
                E item = current.val;
                current = current.next;
                return item;
            }
        };
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
