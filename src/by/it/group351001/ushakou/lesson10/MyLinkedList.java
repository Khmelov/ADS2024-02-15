package by.it.group351001.ushakou.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.Objects;

// Класс MyLinkedList представляет собой реализацию двусвязного списка,
// который поддерживает интерфейс Deque. Это позволяет использовать его как
// двустороннюю очередь, предоставляя операции добавления и удаления элементов
// как с начала, так и с конца списка.

// Класс реализует основные методы для работы со списком, такие как добавление,
// удаление и получение элементов. Список состоит из узлов, каждый из которых
// содержит ссылку на следующий и предыдущий узел. Это обеспечивает быструю
// вставку и удаление элементов по сравнению с массивами.

// Класс MyLinkedList реализует интерфейс Deque и представляет двусвязный список.
public class MyLinkedList<E> implements Deque<E> {

    // Вложенный статический класс для представления узла списка.
    static class DoubleNode<E> {
        public E data; // Данные, хранящиеся в узле
        public DoubleNode<E> next; // Ссылка на следующий узел
        public DoubleNode<E> prev; // Ссылка на предыдущий узел

        // Конструктор узла, инициализирующий данные
        public DoubleNode(E data) {
            this.data = data;
        }
    }

    DoubleNode<E> _head; // Голова списка
    DoubleNode<E> _tail; // Хвост списка
    int Count; // Количество элементов в списке

    // Переопределение метода toString для представления списка в виде строки
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(); // Создание объекта StringBuilder для конкатенации строк
        sb.append('['); // Начало строки с квадратной скобки
        DoubleNode<E> tempHead = _head; // Временная переменная для итерации по списку
        // Проход по всем узлам до предпоследнего
        while (tempHead.next != null) {
            sb.append(tempHead.data); // Добавление данных текущего узла в строку
            sb.append(", "); // Добавление запятой и пробела
            tempHead = tempHead.next; // Переход к следующему узлу
        }
        sb.append(tempHead.data); // Добавление данных последнего узла
        sb.append("]"); // Закрывающая квадратная скобка
        return sb.toString(); // Возврат итоговой строки
    }

    // Добавление элемента в конец списка
    @Override
    public boolean add(E e) {
        DoubleNode<E> node = new DoubleNode<>(e); // Создание нового узла

        // Если список пуст, устанавливаем голову и хвост на новый узел
        if (_head == null)
            _head = node;
        else
        {
            _tail.next = node; // Связываем текущий хвост с новым узлом
            node.prev = _tail; // Устанавливаем ссылку на предыдущий узел
        }
        _tail = node; // Обновляем хвост на новый узел
        Count++; // Увеличиваем количество элементов
        return true; // Успешно добавлено
    }

    // Добавление элемента в начало списка
    @Override
    public void addFirst(E e) {
        DoubleNode<E> node = new DoubleNode<>(e); // Создание нового узла
        node.next = _head; // Установка ссылки на текущую голову
        // Если список пуст, устанавливаем голову и хвост на новый узел
        if (Count == 0) {
            _head = _tail = node;
        }
        else {
            _head.prev = node; // Устанавливаем ссылку на предыдущий узел
            _head = node; // Обновляем голову на новый узел
        }
        Count++; // Увеличиваем количество элементов
    }

    // Добавление элемента в конец списка (вызывает метод add)
    @Override
    public void addLast(E e) {
        add(e);
    }

    // Извлечение и удаление первого элемента списка
    @Override
    public E pollFirst() {
        // Если список пуст, возвращаем null
        if (Count == 0)
            return null;
        E output = _head.data; // Сохраняем данные головы
        // Если в списке только один элемент, обнуляем голову и хвост
        if (Count == 1)
            _head = _tail = null;
        else
        {
            _head = _head.next; // Перемещаем голову на следующий узел
            _head.prev = null; // Обнуляем ссылку на предыдущий узел
        }
        Count--; // Уменьшаем количество элементов
        return output; // Возвращаем данные первого элемента
    }

    // Извлечение и удаление последнего элемента списка
    @Override
    public E pollLast() {
        // Если список пуст, возвращаем null
        if (Count == 0)
            return null;
        E output = _tail.data; // Сохраняем данные хвоста
        // Если в списке только один элемент, обнуляем голову и хвост
        if (Count == 1)
            _head = _tail = null;
        else
        {
            _tail = _tail.prev; // Перемещаем хвост на предыдущий узел
            _tail.next = null; // Обнуляем ссылку на следующий узел
        }
        Count--; // Уменьшаем количество элементов
        return output; // Возвращаем данные последнего элемента
    }

    // Переопределение метода poll для извлечения первого элемента
    @Override
    public E poll() {
        return pollFirst();
    }

    // Получение первого элемента без его удаления
    @Override
    public E getFirst() {
        // Если список пуст, возвращаем null
        if (Count == 0)
            return null;
        return _head.data; // Возвращаем данные головы
    }

    // Получение последнего элемента без его удаления
    @Override
    public E getLast() {
        // Если список пуст, возвращаем null
        if (Count == 0)
            return null;
        return _tail.data; // Возвращаем данные хвоста
    }

    // Получение первого элемента без его удаления (эквивалентно getFirst)
    @Override
    public E element() {
        return getFirst();
    }

    // Возвращение текущего размера списка
    @Override
    public int size() {
        return Count; // Возвращаем количество элементов
    }

    // Удаление указанного объекта из списка
    @Override
    public boolean remove(Object o) {
        DoubleNode<E> tempHead = _head; // Временная переменная для итерации по списку
        while (tempHead != null) {
            // Если данные узла совпадают с удаляемым объектом
            if (Objects.equals(o, tempHead.data)) {
                // Связываем следующий узел с предыдущим, если он существует
                if (tempHead.next != null)
                    tempHead.next.prev = tempHead.prev;
                else
                    _tail = tempHead.prev; // Если это последний элемент, обновляем хвост

                // Связываем предыдущий узел с следующим, если он существует
                if (tempHead.prev != null)
                    tempHead.prev.next = tempHead.next;
                else
                    _head = tempHead.next; // Если это первый элемент, обновляем голову
                Count--; // Уменьшаем количество элементов
                return true; // Успешно удалено
            }
            tempHead = tempHead.next; // Переход к следующему узлу
        }
        return false; // Если элемент не найден
    }

    // Удаление элемента по индексу
    public E remove(int index) {
        DoubleNode<E> tempHead = _head; // Временная переменная для итерации по списку
        // Проверка, что индекс находится в допустимом диапазоне
        if (index > -1 && index < Count) {
            int count = 0; // Счетчик для отслеживания текущего индекса
            // Итерация по списку до указанного индекса
            while (count++ < index) {
                tempHead = tempHead.next; // Переход к следующему узлу
            }
            // Связывание соседних узлов, чтобы удалить текущий узел
            if (tempHead.next != null)
                tempHead.next.prev = tempHead.prev;
            else
                _tail = tempHead.prev; // Если это последний элемент, обновляем хвост

            if (tempHead.prev != null)
                tempHead.prev.next = tempHead.next;
            else
                _head = tempHead.next; // Если это первый элемент, обновляем голову
            Count--; // Уменьшаем количество элементов
            return tempHead.data; // Возвращаем данные удаленного узла
        }
        return null; // Если индекс вне диапазона
    }

    //////////////////////////////////////////

    // Пустые реализации интерфейса Deque
    @Override
    public boolean offer(E e) {
        return false; // Не реализовано
    }

    @Override
    public E remove() {
        return null; // Не реализовано
    }

    @Override
    public E peek() {
        return null; // Не реализовано
    }

    @Override
    public boolean offerFirst(E e) {
        return false; // Не реализовано
    }

    @Override
    public boolean offerLast(E e) {
        return false; // Не реализовано
    }

    @Override
    public E removeFirst() {
        return null; // Не реализовано
    }

    @Override
    public E removeLast() {
        return null; // Не реализовано
    }

    @Override
    public E peekFirst() {
        return null; // Не реализовано
    }

    @Override
    public E peekLast() {
        return null; // Не реализовано
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return false; // Не реализовано
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        return false; // Не реализовано
    }

    @Override
    public boolean isEmpty() {
        return false; // Не реализовано
    }

    @Override
    public boolean contains(Object o) {
        return false; // Не реализовано
    }

    @Override
    public Iterator<E> iterator() {
        return null; // Не реализовано
    }

    @Override
    public Iterator<E> descendingIterator() {
        return null; // Не реализовано
    }

    @Override
    public Object[] toArray() {
        return new Object[0]; // Не реализовано
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null; // Не реализовано
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false; // Не реализовано
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false; // Не реализовано
    }

    @Override
    public void push(E e) {
        // Не реализовано
    }

    @Override
    public E pop() {
        return null; // Не реализовано
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false; // Не реализовано
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false; // Не реализовано
    }

    @Override
    public void clear() {
        // Не реализовано
    }
}
