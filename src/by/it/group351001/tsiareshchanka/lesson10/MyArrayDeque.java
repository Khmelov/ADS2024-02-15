package by.it.group351001.tsiareshchanka.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
public class MyArrayDeque<E> implements Deque<E> {
    public E[] elements;
    private int size;
    private int front;
    private int rear;

    public MyArrayDeque(){
        elements = (E[]) new Object[10];
        front = 0;
        rear = 0;
        size = 0;
    }
    private void resize(){
        E[] newElements = (E[]) new Object[elements.length * 2];
        int index = 0;
        if (rear == front) {
            rear = elements.length;
        }
        else if (front == -1){
            front = 0;
        }
        for (int i = front; i != rear; i = (i + 1) % elements.length){
            newElements[index++] = elements[i];
        }
        newElements[index++] = elements[elements.length - 1];
        elements = newElements;
        front = 0;
        rear = size;
    }
    private int nextIndex(int index){
        index++;
        return index % elements.length;
    }
    private int prevIndex(int index){
        index--;
        if (index < 0){
            index = elements.length + index;
        }
        return index % elements.length;
    }
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("[");
        for (int i = front; i != rear; i = (i+1) % elements.length){
            sb.append(elements[i]);
            if (nextIndex(i) != rear){
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
    @Override
    public int size(){
        return size;
    }
    @Override
    public boolean isEmpty(){
        return size == 0;
    }
    @Override
    public void addFirst(E element){
        if (size == elements.length){
            resize();
        }
            front = prevIndex(front);
        elements[front] = element;
        size++;
    }
    @Override
    public boolean add(E e) {
        addLast(e);
        return true;
    }
    @Override
    public void addLast(E element){
        if (size == elements.length){
            resize();
        }
        elements[rear] = element;
        if (size != elements.length - 1){
            rear = nextIndex(rear);
        }
        size++;
    }
    @Override
    public E getFirst(){
        if (isEmpty()){
             throw new IllegalStateException();
        }
        return elements[front];
    }
    @Override
    public E getLast(){
        if (isEmpty()){
            throw new IllegalStateException();
        }
        return elements[prevIndex(rear)];
    }
    @Override
    public E pollLast(){
        if (isEmpty()){
            return  null;
        }
        rear = prevIndex(rear);
        E element = elements[rear];
        elements[rear] = null;
        size--;
        return element;
    }
    @Override
    public E pollFirst(){
        if (isEmpty()){
            return  null;
        }
        E element = elements[front];
        elements[front] = null;
        front = nextIndex(front);
        size--;
        return element;
    }
    @Override
    public E element(){
        return getFirst();
    }
    @Override
    public E poll(){
        return pollFirst();
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
    public boolean remove(Object o) {
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
