package by.it.group310901.kanunnikava.lesson10;

import java.util.*;

/*Создайте class MyArrayDeque<E>, который реализует интерфейс Deque<E>
    и работает на основе приватного массива типа E[]
    БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ*/

public class MyArrayDeque<E> implements Deque<E> {
    private E[] elements = (E[]) new Object[1]; // Массив элементов, начальная длина - 1

    private int size = 0; // Переменная для отслеживания текущего размера дека

    @Override
    public String toString(){ // Переопределение метода toString для печати содержимого дека
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i<size-1; i++){
            sb.append(elements[i]).append(", ");
        }
        if (size > 0){
            sb.append(elements[size-1]);
        }
        sb.append("]");
        return sb.toString();
    }

    public int size(){ // Метод для получения текущего размера дека
        return size;
    }

    @Override
    public boolean isEmpty() { // Переопределение метода для проверки пустоты дека
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

    private void posincsize(){  // Метод для увеличения размера массива при необходимости
        if (size == elements.length) {
            E[] newarr = (E[]) new Object[size*3/2+1]; // Создание нового массива большего размера
            System.arraycopy(elements, 0, newarr, 0, size); // Копирование элементов в новый массив
            elements = newarr;
        }
    }

    @Override
    public boolean add(E element){ // Переопределение метода для добавления элемента в конец дека
        posincsize();
        elements[size] = element;
        size++;
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

    public void addFirst(E element){ // Метод для добавления элемента в начало дека
        posincsize();
        System.arraycopy(elements, 0, elements, 1, size); // Сдвиг элементов вправо
        elements[0] = element; // Добавление нового элемента в начало
        size++;
    }
    public void addLast(E element){ // Метод для добавления элемента в конец дека
        posincsize();
        elements[size] = element; // Добавление элемента в массив
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

    public E element(){ // Метод для получения первого элемента дека
        return elements[0];
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

    public E getFirst(){ // Метод для получения первого элемента дека
        return elements[0];
    }
    public E getLast(){ // Метод для получения последнего элемента дека
        return elements[size-1];
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

    public E poll(){ // Метод для удаления и возвращения первого элемента дека
        E temp = elements[0]; // Сохраняет первый элемент
        size--;
        System.arraycopy(elements, 1, elements, 0, size); // Сдвигает элементы влево
        return temp;
    }
    public E pollFirst(){ // Метод для удаления и возвращения первого элемента дека (аналог метода poll)
        return poll();
    }

    public E pollLast(){ // Метод для удаления и возвращения последнего элемента дека
        size--;
        E temp = elements[size];
        return temp;
    }

}