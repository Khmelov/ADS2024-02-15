package by.it.group351002.alexeichik.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

public class MyLinkedList<E> implements Deque<E> {
    public MyLinkedList(){
        head = null;
        tail = null;
        size = 0;
    }
    private Node<E> head;
    private Node<E> tail;
    private int size;
    private class Node<E>{
        E elem;
        Node<E> next;
        Node<E> prev;

        public Node(E el,Node<E> next,Node<E> prev){
            this.elem=el;
            this.next=next;
            this.prev=prev;
        }

    }

    @Override
    public E remove() {
        return null;
    }



    @Override
    public void addFirst(E e) {
        Node<E> first = new Node<>(e,head,null);
        if (head==null)
            tail=first;
        if (head!=null)
            head.prev=first;
        head = first;

        size++;
    }

    @Override
    public void addLast(E e) {
        Node<E> last = new Node<>(e,null,tail);
        if (tail!=null)
            tail.next = last;
        else
            head = last;
        tail = last;
        size++;
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

    public E remove(int index) {
        if (index < 0 || index>size-1)
            return null;
        Node<E> first = head;
        Node<E> prev = null;
        for (int i = 0;i<index;i++) {
            prev = first;
            first = first.next;
        }
        E el = first.elem;
        if (prev!=null)
            prev.next = first.next;
        if (index!=size-1)
            first.next.prev = prev;
        size--;
        return el;
    }

    @Override
    public E pollFirst() {
        if (size==0)
            return null;
        Node<E> first = head;
        head = head.next;
        if (head!=null)
            head.prev=null;
        size--;
        return first.elem;
    }

    @Override
    public E pollLast() {
        if (size==0)
            return null;
        Node<E> last = tail;
        tail = tail.prev;
        if (tail!=null)
            tail.next=null;
        size--;
        return last.elem;
    }

    @Override
    public E getFirst() {
        if (size==0)
            return null;
        return head.elem;
    }

    @Override
    public E getLast() {
        if (size==0)
            return null;
        return tail.elem;
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
        Node<E> last = new Node<>(e,null,tail);
        if (tail!=null)
            tail.next = last;
        else
            head = last;
        tail = last;
        size++;
        return true;
    }

    @Override
    public boolean offer(E e) {
        return false;
    }



    @Override
    public E poll() {
        if (size==0)
            return null;
        Node<E> first = head;
        head = head.next;
        head.prev=null;
        size--;
        return first.elem;
    }

    @Override
    public E element() {
        return head.elem;
    }

    @Override
    public String toString() {
        Node<E> first = head;
        StringBuilder sb=new StringBuilder();
        sb.append("[");
        for (int i =0;i<size-1;i++){
            sb.append(first.elem);
            sb.append(", ");
            first=first.next;
        }
        if (size!=0)
            sb.append(tail.elem);
        sb.append("]");
        return sb.toString();
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
    public boolean remove(Object o) {
        Node<E> prev = null;
        Node<E> f = head;
        int i = 1;
        boolean flag = true;
        while (flag && i<=size) {
            if (o.equals(f.elem)){
                flag = false;
                if (prev!=null) {
                    prev.next = f.next;

                }

                if (i!=size)
                    f.next.prev=prev;
            }
            else {
                prev = f;
                f = f.next;
            }
            i++;

        }
        if (!flag) {
            size--;
            return true;
        }
        return false;
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
        return size;
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
