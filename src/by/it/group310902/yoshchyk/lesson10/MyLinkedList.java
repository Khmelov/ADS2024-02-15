package by.it.group310902.yoshchyk.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.Objects;

public class MyLinkedList<E> implements Deque<E> {

    // Внутренний класс для узлов двусвязного списка
    static class DoubleNode<E> {
        public E data; // Данные узла
        public DoubleNode<E> next; // Ссылка на следующий узел
        public DoubleNode<E> prev; // Ссылка на предыдущий узел

        // Инициализация данных узла
        public DoubleNode(E data) {
            this.data = data;
        }
    }
    DoubleNode<E> head;// Указатель на первый узел
    DoubleNode<E> tail;// Указатель на последний узел
    int Count;// Текущий размер списка

    // Метод для получения строкового представления списка
    @Override
    public String toString() {
        String tempStr = "[";
        DoubleNode<E> tempHead = head;
        while (tempHead.next != null) {
            tempStr += tempHead.data;
            tempStr += ", ";
            tempHead = tempHead.next;
        }
        tempStr += tempHead.data;
        tempStr += "]";
        return tempStr;
    }

    // Метод для добавления элемента в конец списка
    @Override
    public boolean add(E e) {
        DoubleNode<E> node = new DoubleNode<>(e);

        if (head == null)
            head = node;
        else
        {
            tail.next = node;
            node.prev = tail;
        }
        tail = node;
        Count++;
        return true;
    }

    // Метод для добавления элемента в начало списка
    @Override
    public void addFirst(E e) {
        DoubleNode<E> node = new DoubleNode<>(e);
        node.next = head;
        if (Count == 0) {
            head = tail = node;
        }
        else {
            head.prev = node;
            head = node;
        }
        Count++;
    }

    // Метод для добавления элемента в конец списка
    @Override
    public void addLast(E e) {
        add(e);
    }

    // Метод для удаления и возвращения первого элемента
    @Override
    public E pollFirst() {
        if (Count == 0)
            return null;
        E output = head.data;
        if (Count == 1)
            head = tail = null;
        else
        {
            head = head.next;
            head.prev = null;
        }
        Count--;
        return output;
    }

    // Метод для удаления и возвращения последнего элемента
    @Override
    public E pollLast() {
        if (Count == 0)
            return null;
        E output = tail.data;
        if (Count == 1)
            head = tail = null;
        else
        {
            tail = tail.prev;
            tail.next = null;
        }
        Count--;
        return output;
    }

    // Метод для удаления и возвращения первого элемента
    @Override
    public E poll() {
        return pollFirst();
    }

    // Метод для получения первого элемента без удаления
    @Override
    public E getFirst() {
        if (Count == 0)
            return null;
        return head.data;
    }

    // Метод для получения последнего элемента без удаления
    @Override
    public E getLast() {
        if (Count == 0)
            return null;
        return tail.data;
    }

    // Метод для получения первого элемент
    @Override
    public E element() {
        return getFirst();
    }

    // Метод для получения текущего размера списка
    @Override
    public int size() {
        return Count;
    }

    // Метод для удаления первого вхождения элемента
    @Override
    public boolean remove(Object o) {
        DoubleNode<E> tempHead = head;
        while (tempHead != null) {
            if (Objects.equals(o, tempHead.data)) {
                if (tempHead.next != null)
                    tempHead.next.prev = tempHead.prev;
                else
                    tail = tempHead.prev;

                if (tempHead.prev != null)
                    tempHead.prev.next = tempHead.next;
                else
                    head = tempHead.next;
                Count--;
                return true;
            }
            tempHead = tempHead.next;
        }
        return false;
    }

    // Метод для удаления элемента по индексу
    public E remove(int index) {
        DoubleNode<E> tempHead = head;
        if (index > -1 && index < Count) {
            int count = 0;
            while (count++ < index) {// Идем к нужному индексу
                tempHead = tempHead.next;
            }
            // Обновляем ссылки на следующий и предыдущий узлы
            if (tempHead.next != null)
                tempHead.next.prev = tempHead.prev;
            else
                tail = tempHead.prev; // Обновляем хвост, если удаляем последний узел

            if (tempHead.prev != null)
                tempHead.prev.next = tempHead.next;
            else
                head = tempHead.next;// Обновляем голову, если удаляем первый узел
            Count--;
            return tempHead.data; // Возвращаем данные удаленного узла
        }
        return null;
    }

    //////////////////////////////////////////

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
    public boolean isEmpty() {
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
    public Iterator<E> descendingIterator() {
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
    public boolean containsAll(Collection<?> c) {
        return false;
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
}
