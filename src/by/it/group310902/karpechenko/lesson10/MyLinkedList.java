package by.it.group310902.karpechenko.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.StringJoiner;

public class MyLinkedList<E> implements Deque<E> {

    public class Node{
        private E value;
        private Node next = null;
        private Node prev = null;
        private Node(){}
        private Node(E e){
            value = e;
        }
        public String toString(){
            return value.toString();
        }
    }

    public E remove(int idx){
        if (idx < 0 || idx >= size){
            return null;
        }
        E e = null;
        size--;
        Node i = head;
        while(idx > 0){
            i = i.next;
            idx--;
        }
        if(size != 0){
            if(i == head){
                e = head.value;
                head = i.next;
                i.next.prev = null;
                i = null;
            }
            else if (i.next == null) {
                e = tail.value;
                tail = i.prev;
                i.prev.next = null;
                i = null;
            }
            else {
                e = i.value;
                i.prev.next = i.next;
                i.next.prev = i.prev;
                i = null;
            }
        }
        else{
            e = head.value;
            head = null;
            tail = null;
        }
        return e;
    }

    private int size = 0;
    private Node head = null;
    private Node tail = null;
    public MyLinkedList(){
        size = 0;
        head = null;
        tail = null;
    }
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("[");
        Node i = head;
        System.out.println(i.toString());
        while (i != null){
            s.append(i.value.toString());
            if (i.next == null) break;
            if (i.next != null){
                s.append(", ");
            }
            i = i.next;
        }
        s.append("]");
        return s.toString();
    }

    @Override
    public void addFirst(E e) {
        size ++;
        if(head == null){
            tail.value = head.value = e;
            return;
        }
        Node newie = new Node(e);
        newie.next = head;
        head.prev = newie;
        head = newie;
    }

    @Override
    public void addLast(E e) {
        size++;
        if(head == null){
            head = new Node(e);
            tail = head;
            return;
        }
        Node newie = new Node(e);
        tail.next = newie;
        newie.prev = tail;
        tail = newie;
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
        return poll();
    }

    @Override
    public E pollLast() {
        if (tail != null){
            size--;
            E e = tail.value;
            if(size != 0){
                Node i = tail;
                tail = i.prev;
                i.prev.next = null;
                i = null;
            }
            else{
                head = null;
                tail = null;
            }
        return e;
        }
        else {
            return null;
        }
    }

    @Override
    public E getFirst() {
        return head != null? head.value : null;
    }

    @Override
    public E getLast() {
        return tail != null? tail.value : null;
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
        if(head != null){
        E e = head.value;
        size--;
        if(size != 0){
            Node i = head;
            head = i.next;
            i.next.prev = null;
            i = null;
        }
        else{
            head = null;
            tail = null;
        }
        return e;
    }
        else {
            return null;
        }
    }

    @Override
    public E element() {
        return head != null? head.value : null;
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
    public void push(E e) {

    }

    @Override
    public E pop() {
        return null;
    }
    private boolean removeE(E e){
        return false;

    }
    @Override
    public boolean remove(Object o) {
        Node i = head;
        boolean b = false;
        while(i != null){
            if(i.value.equals(o)){
                b = true;
                size--;
                if(size != 0){
                    if(i == head){
                        head = i.next;
                        i.next.prev = null;
                        i = null;
                    }
                    else if (i.next == null) {
                       tail = i.prev;
                       i.prev.next = null;
                       i = null;
                    }
                    else {
                        i.prev.next = i.next;
                        i.next.prev = i.prev;
                        i = null;
                    }
                }
                else{
                    head = null;
                    tail = null;
                }
                break;
            }
            else
                i = i.next;
        }
        return b;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public int size() {
        return size;
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
    public boolean isEmpty() {
        return head == null;
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
    public boolean containsAll(Collection<?> c) {
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
        Node i = tail;
        while(i.prev != null){
            i = i.prev;
            i.next = null;
        }
        i = null;
    }
}
