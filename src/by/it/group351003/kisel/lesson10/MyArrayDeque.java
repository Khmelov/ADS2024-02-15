package by.it.group351003.kisel.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

public class MyArrayDeque<E> implements Deque<E> {
    final int defaultSize = 8; // Начальный размер массива
    int _front; // Индекс передней части дека
    int _rear; // Индекс задней
    E[] _items; // Массив для хранения
    int Count; // Текущее количество элементов в деке

    // Конструктор, инициализирующий массив и индексы
    MyArrayDeque() {
        _items = (E[]) new Object[defaultSize]; // Создаем массив с начальным размером
        _rear = -1; // Инициализируем заднюю часть дека
    }

    // Метод для представления дека в виде строки
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(); // Создаем StringBuilder для формирования строки
        sb.append('['); // Добавляем открывающую скобку
        // Проходим по элементам дека и добавляем их в строку
        for (int i = _front, count = 0; count < Count; i++, count++) {
            sb.append(_items[i % _items.length]); // Используем модуль для циклического доступа к элементам
            if (count < Count - 1) {
                sb.append(", "); // Добавляем запятую, если это не последний элемент
            }
        }
        sb.append("]"); // Добавляем закрывающую скобку
        return sb.toString(); // Возвращаем строку
    }

    // Метод для добавления элемента в конец дека
    @Override
    public boolean add(E e) {
        addLast(e); // Используем метод addLast для добавления элемента
        return true; // Возвращаем true для обозначения успешного добавления
    }

    // Метод для добавления элемента в начало дека
    @Override
    public void addFirst(E e) {
        // Увеличиваем размер массива, если он заполнен
        if (Count == _items.length)
            Resize(_items.length * 2); // Увеличиваем размер массива вдвое
        _front = (_front - 1 + _items.length) % _items.length; // Обновляем индекс передней части
        _items[_front] = e; // Добавляем элемент в начало
        Count++; // Увеличиваем счетчик элементов
    }

    // Метод для добавления элемента в конец дека
    @Override
    public void addLast(E e) {
        // Увеличиваем размер массива, если он заполнен
        if (Count == _items.length)
            Resize(_items.length * 2); // Увеличиваем размер массива вдвое
        _rear = (_rear + 1) % _items.length; // Обновляем индекс задней части
        _items[_rear] = e; // Добавляем элемент в конец
        Count++; // Увеличиваем счетчик элементов
    }

    // Метод для изменения размера массива
    void Resize(int max) {
        E[] tempItems = (E[]) new Object[max]; // Создаем новый массив с новым размером
        int counter = 0; // Счетчик для копирования элементов
        // Копируем элементы в новый массив
        for (int i = _front; counter != Count; i++)
            tempItems[counter++] = _items[i % _items.length]; // Используем модуль для циклического доступа
        _items = tempItems; // Перенаправляем ссылку на новый массив
        _front = 0; // Обновляем индекс передней части
        _rear = Count - 1; // Обновляем индекс задней части
    }

    // Метод для получения текущего размера дека
    @Override
    public int size() {
        return Count; // Возвращаем текущее количество элементов
    }

    // Метод для извлечения и удаления первого элемента
    @Override
    public E pollFirst() {
        if (Count == 0) // Проверяем, пуст ли дек
            return null; // Возвращаем null, если дек пуст
        E item = _items[_front]; // Сохраняем первый элемент
        _front = (_front + 1) % _items.length; // Обновляем индекс передней части
        Count--; // Уменьшаем счетчик элементов
        return item; // Возвращаем извлеченный элемент
    }

    // Метод для извлечения и удаления последнего элемента
    @Override
    public E pollLast() {
        if (Count == 0) // Проверяем, пуст ли дек
            return null; // Возвращаем null, если дек пуст
        E item = _items[_rear]; // Сохраняем последний элемент
        _rear = (_rear - 1 + _items.length) % _items.length; // Обновляем индекс задней части
        Count--; // Уменьшаем счетчик элементов
        return item; // Возвращаем извлеченный элемент
    }

    // Метод для получения первого элемента без его удаления
    @Override
    public E getFirst() {
        if (Count == 0) // Проверяем, пуст ли дек
            return null; // Возвращаем null, если дек пуст
        return _items[_front]; // Возвращаем первый элемент
    }

    // Метод для получения последнего элемента без его удаления
    @Override
    public E getLast() {
        if (_rear == -1 && Count > 0) // Если дек не пуст, но задняя часть не инициализирована
            return _items[_front]; // Возвращаем первый элемент
        if (Count == 0) // Проверяем, пуст ли дек
            return null; // Возвращаем null, если дек пуст
        return _items[_rear]; // Возвращаем последний элемент
    }


    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E element() {
        return getFirst();
    }

    //////////////////////////

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
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
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
    public boolean isEmpty() {
        return Count == 0;
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