package by.it.group351003.kisel.lesson10;

import java.util.*;

public class MyLinkedList<E> implements Deque<E> {

    // Вложенный класс для узла двусвязного списка
    static class DoubleNode<E> {
        public E data; // Данные, хранящиеся в узле
        public DoubleNode<E> next; // Ссылка на следующий узел
        public DoubleNode<E> prev; // Ссылка на предыдущий узел

        // Конструктор для создания узла с данными
        public DoubleNode(E data) {
            this.data = data; // Инициализируем данные узла
        }
    }

    DoubleNode<E> _head; // Указатель на голову списка
    DoubleNode<E> _tail; // Указатель на хвост списка
    int Count; // Количество элементов в списке

    // Метод для представления списка в виде строки
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(); // Создаем StringBuilder для формирования строки
        sb.append('['); // Добавляем открывающую скобку
        DoubleNode<E> tempHead = _head; // Начинаем с головы списка
        // Проходим по всем узлам и добавляем данные в строку
        while (tempHead != null) {
            sb.append(tempHead.data); // Добавляем данные узла
            tempHead = tempHead.next; // Переходим к следующему узлу
            if (tempHead != null) {
                sb.append(", "); // Добавляем запятую, если это не последний элемент
            }
        }
        sb.append("]"); // Добавляем закрывающую скобку
        return sb.toString(); // Возвращаем строку
    }

    // Метод для добавления элемента в конец списка
    @Override
    public boolean add(E e) {
        DoubleNode<E> node = new DoubleNode<>(e); // Создаем новый узел с данными

        if (_head == null) // Если список пуст, устанавливаем голову
            _head = node;
        else {
            _tail.next = node; // Присоединяем новый узел к концу списка
            node.prev = _tail; // Устанавливаем ссылку на предыдущий узел
        }
        _tail = node; // Обновляем указатель на хвост
        Count++; // Увеличиваем счетчик элементов
        return true; // Возвращаем true для обозначения успешного добавления
    }

    // Метод для добавления элемента в начало списка
    @Override
    public void addFirst(E e) {
        DoubleNode<E> node = new DoubleNode<>(e); // Создаем новый узел с данными
        node.next = _head; // Устанавливаем ссылку на текущую голову
        if (Count == 0) { // Если список пуст, устанавливаем голову и хвост
            _head = _tail = node;
        } else {
            _head.prev = node; // Устанавливаем ссылку на новый узел для текущей головы
            _head = node; // Обновляем указатель на голову
        }
        Count++; // Увеличиваем счетчик элементов
    }

    // Метод для добавления элемента в конец списка (переиспользуем метод add)
    @Override
    public void addLast(E e) {
        add(e); // Используем метод add для добавления элемента
    }

    // Метод для извлечения и удаления первого элемента
    @Override
    public E pollFirst() {
        if (Count == 0) return null; // Если список пуст, возвращаем null
        E output = _head.data; // Сохраняем данные первой головы
        if (Count == 1) // Если в списке один элемент
            _head = _tail = null; // Очищаем список
        else {
            _head = _head.next; // Обновляем указатель на голову
            _head.prev = null; // Удаляем ссылку на предыдущий узел
        }
        Count--; // Уменьшаем счетчик элементов
        return output; // Возвращаем извлеченные данные
    }

    // Метод для извлечения и удаления последнего элемента
    @Override
    public E pollLast() {
        if (Count == 0) return null; // Если список пуст, возвращаем null
        E output = _tail.data; // Сохраняем данные хвоста
        if (Count == 1) // Если в списке один элемент
            _head = _tail = null; // Очищаем список
        else {
            _tail = _tail.prev; // Обновляем указатель на хвост
            _tail.next = null; // Удаляем ссылку на следующий узел
        }
        Count--; // Уменьшаем счетчик элементов
        return output; // Возвращаем извлеченные данные
    }

    // Метод для извлечения первого элемента (переиспользуем pollFirst)
    @Override
    public E poll() {
        return pollFirst(); // Используем метод pollFirst для извлечения
    }

    // Метод для получения первого элемента без удаления
    @Override
    public E getFirst() {
        if (Count == 0) return null; // Если список пуст, возвращаем null
        return _head.data; // Возвращаем данные первой головы
    }

    // Метод для получения последнего элемента без удаления
    @Override
    public E getLast() {
        if (Count == 0) return null; // Если список пуст, возвращаем null
        return _tail.data; // Возвращаем данные хвоста
    }

    // Метод для получения первого элемента (переиспользуем getFirst)
    @Override
    public E element() {
        return getFirst(); // Используем метод getFirst
    }

    // Метод для получения текущего размера списка
    @Override
    public int size() {
        return Count; // Возвращаем текущее количество элементов
    }

    // Метод для удаления указанного элемента
    @Override
    public boolean remove(Object o) {
        DoubleNode<E> tempHead = _head; // Начинаем с головы списка
        // Проходим по всем узлам, ищем элемент для удаления
        while (tempHead != null) {
            if (Objects.equals(o, tempHead.data)) { // Если данные узла равны искомому элементу
                // Обновляем ссылки для соединения соседних узлов
                if (tempHead.next != null)
                    tempHead.next.prev = tempHead.prev; // Устанавливаем ссылку на предыдущий узел
                else
                    _tail = tempHead.prev; // Если это хвост, обновляем указатель на хвост

                if (tempHead.prev != null)
                    tempHead.prev.next = tempHead.next; // Устанавливаем ссылку на следующий узел
                else
                    _head = tempHead.next; // Если это голова, обновляем указатель на голову

                Count--; // Уменьшаем счетчик элементов
                return true; // Возвращаем true для обозначения успешного удаления
            }
            tempHead = tempHead.next; // Переходим к следующему узлу
        }
        return false; // Возвращаем false, если элемент не найден
    }

    // Метод для удаления элемента по индексу
    public E remove(int index) {
        DoubleNode<E> tempHead = _head; // Начинаем с головы списка
        if (index > -1 && index < Count) { // Проверяем корректность индекса
            int count = 0; // Счетчик для прохода по списку
            // Проходим до указанного индекса
            while (count++ < index) {
                tempHead = tempHead.next; // Переходим к следующему узлу
            }
            // Обновляем ссылки для соединения соседних узлов
            if (tempHead.next != null)
                tempHead.next.prev = tempHead.prev; // Устанавливаем ссылку на предыдущий узел
            else
                _tail = tempHead.prev; // Если это хвост, обновляем указатель на хвост

            if (tempHead.prev != null)
                tempHead.prev.next = tempHead.next; // Устанавливаем ссылку на следующий узел
            else
                _head = tempHead.next; // Если это голова, обновляем указатель на голову

            Count--; // Уменьшаем счетчик элементов
            return tempHead.data; // Возвращаем данные удаленного узла
        }
        return null; // Возвращаем null, если индекс некорректен
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
