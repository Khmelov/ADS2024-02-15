package by.it.group310901.kanunnikava.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

/*Создайте class MyLinkedList<E>, который реализует интерфейс Deque<E>
    и работает на основе двунаправленного связного списка
            БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ*/

public class MyLinkedList<E> implements Deque<E> {

    private E[] elements = (E[]) new Object[1]; // Массив элементов с начальной длиной 1
    private int size = 0;

    private void posincsize() { // Приватный метод для увеличения размера массива при необходимости
        if (size == elements.length) {
            E[] newarr = (E[]) new Object[size * 3 / 2 + 1]; // Создание нового массива большего размера
            System.arraycopy(elements, 0, newarr, 0, size); // Копирование элементов в новый массив
            elements = newarr;
        }
    }

    public E remove(int index){ // Метод для удаления элемента по индексу
        E temp = elements[index]; // Сохранение удаляемого элемента
        System.arraycopy(elements, index+1, elements, index, size - index + 1); // Сдвиг элементов после удаленного элемента влево
        size--;
        return temp;
    }

    public String toString(){  // Метод для получения строкового представления списка
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size - 1; i++){ // Цикл для добавления элементов в строку
            sb.append(elements[i]).append(", ");
        }
        if (size > 0){
            sb.append(elements[size - 1]);
        }
        sb.append("]");
        return sb.toString();
    }


    @Override
    public void addFirst(E e) { // Переопределение метода addFirst для добавления элемента в начало списка
        posincsize();
        System.arraycopy(elements, 0, elements, 1, size);  // Сдвиг элементов вправо
        elements[0] = e;
        size++;
    }

    @Override
    public void addLast(E e) { // Переопределение метода addLast для добавления элемента в конец списка
        posincsize();
        elements[size] = e; // Добавление элемента в конец массива
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
        return pollFirst();
    }

    @Override
    public E removeLast() {
        return pollLast();
    }

    @Override
    public E pollFirst() { // Переопределение метода pollFirst для удаления и возвращения первого элемента
        return poll();
    }

    @Override
    public E pollLast() { // Переопределение метода pollLast для удаления и возвращения последнего элемента
        size--;
        return elements[size]; // Возвращение последнего элемента
    }

    @Override
    public E getFirst() { // Переопределение метода getFirst для получения первого элемента
        return elements[0];
    }

    @Override
    public E getLast() { // Переопределение метода getLast для получения последнего элемента
        return elements[size - 1]; // Возвращение последнего элемента
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
    public boolean add(E e) {  // Переопределение метода add для добавления элемента в конец списка
        posincsize();
        elements[size] = e; // Добавление элемента в конец массива
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

    @Override
    public E poll() { // Переопределение метода poll для удаления и возвращения первого элемента
        E temp = elements[0]; // Сохранение первого элемента
        size--;
        System.arraycopy(elements, 1, elements, 0, size); // Сдвиг элементов влево
        return temp;
    }

    @Override
    public E element() {
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

    public boolean remove(Object o) { // Метод для удаления элемента по значению
        int index = -1; // Инициализация индекса
        for (int i = 0; i<size; i++){ // Поиск элемента в массиве
            if (elements[i].equals(o)){
                index = i; // Присвоение индекса элемента
                System.arraycopy(elements, index+1, elements, index, size-index+1); // Сдвиг элементов влево
                size--;
                return true;
            }
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