package by.it.group351005.bitno.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

public class MyLinkedList<E> implements Deque<E> {
private class ListElem{
        private E value;
        private ListElem nextElem = null;
    }

    private ListElem listHead = null;
    @Override
    public String toString() {
        StringBuilder answer = new StringBuilder("");
        ListElem curElem = listHead;
        answer.append('[');
        while (curElem != null){
            answer.append(curElem.value.toString());
            curElem = curElem.nextElem;
            if (curElem != null){
                answer.append(", ");
            }
        }
        answer.append(']');
        return answer.toString();
    }

    @Override
    public boolean add(E e) {
        ListElem newElem = new ListElem();
        newElem.value = e;
        ListElem curElem = listHead;
        if (listHead == null){
            listHead = newElem;
        }
        else {
            while (curElem.nextElem != null){
                curElem = curElem.nextElem;
            }
            curElem.nextElem = newElem;
        }
        return true;
    }


    public E remove(int index) {
        E result;
        ListElem curElem = listHead;
        if (index == 0){
            result = listHead.value;
            listHead = listHead.nextElem;
        }
        else{
            for (int i = 0; i < index - 1; i++) {
                curElem = curElem.nextElem;
                if (curElem == null){
                    result = null;
                }
            }
            result = (E) curElem.nextElem.value;
            curElem.nextElem = curElem.nextElem.nextElem;
        }
        return (E) result;
    }

    @Override
    public int size() {
        int counter = 0;
        ListElem curElem = listHead;
        while (curElem != null){
            counter++;
            curElem = curElem.nextElem;
        }
        return counter;
    }

    @Override
    public boolean remove(Object o) {
        ListElem curElem = listHead;
        if (o.equals(curElem.value)){
            listHead = listHead.nextElem;
            return true;
        }
        while (curElem.nextElem != null){
            if (o.equals(curElem.nextElem.value)){
                curElem.nextElem = curElem.nextElem.nextElem;
                return true;
            }
            curElem = curElem.nextElem;
        }
        return false;
    }


    @Override
    public boolean isEmpty() {
        return true;
    }


    @Override
    public void clear() {
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return true;
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
    public void addFirst(E e) {
        ListElem newElem = new ListElem();
        newElem.nextElem = listHead;
        newElem.value = e;
        listHead = newElem;
    }

    @Override
    public void addLast(E e) {
        ListElem newElem = new ListElem();
        newElem.nextElem = null;
        newElem.value = e;
        if (listHead == null)
            listHead = newElem;
        else {
            ListElem curElem = listHead;
            while (curElem.nextElem != null) {
                curElem = curElem.nextElem;
            }
            curElem.nextElem = newElem;
        }
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
        E answer = null; ;
        if (listHead != null) {
            answer = listHead.value;
            listHead = listHead.nextElem;
        }
        return answer;
    }

    @Override
    public E pollLast() {
        E answer;
        if (listHead.nextElem == null){
            answer = listHead.value;
            listHead = null;
        }
        else {
            ListElem curElem = listHead;
            while (curElem.nextElem.nextElem != null) {
                curElem = curElem.nextElem;
            }
            answer = curElem.nextElem.value;
            curElem.nextElem = null;
        }
        return answer;
    }

    @Override
    public E getFirst() {
        return listHead.value;
    }

    @Override
    public E getLast() {
        ListElem curElem = listHead;
        while (curElem.nextElem != null) {
            curElem = curElem.nextElem;
        }
        return curElem.value;
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
    public E poll() {
        E answer = null; ;
        if (listHead != null) {
            answer = listHead.value;
            listHead = listHead.nextElem;
        }
        return answer;
    }

    @Override
    public E element() {
        return listHead.value;
    }

    @Override
    public E peek() {
        return null;
    }

    @Override
    public void push(E e) {

    }

    @Override
    public E pop() {
        return null;
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
