package by.it.group351001.ushakou.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * MyArrayDeque - это реализация двусторонней очереди (Deque) на основе массива.
 * Двусторонняя очередь позволяет добавлять и удалять элементы как с начала, так и с конца.
 * В этом классе реализованы основные методы, необходимые для работы с двусторонней очередью.
 * Реализация включает динамическое изменение размера массива при добавлении элементов.
 *
 * Основные поля класса:
 * - defaultSize: начальный размер массива, который будет использоваться при создании.
 * - _front: индекс первого элемента очереди.
 * - _rear: индекс последнего элемента очереди.
 * - _items: массив, хранящий элементы очереди.
 * - Count: текущее количество элементов в очереди.
 */

public class MyArrayDeque<E> implements Deque<E> {
    final int defaultSize = 8; // Стандартный размер массива для хранения элементов
    int _front; // Индекс первого элемента в очереди
    int _rear; // Индекс последнего элемента в очереди
    E[] _items; // Массив для хранения элементов
    int Count; // Текущее количество элементов в очереди

    // Конструктор, который инициализирует массив заданного размера и устанавливает _rear на -1
    @SuppressWarnings("unchecked")
    MyArrayDeque() {
        _items = (E[]) new Object[defaultSize]; // Создание массива заданного размера
        _rear = -1; // Инициализация _rear
        _front = 0; // Инициализация _front
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(); // Строка для вывода содержимого очереди
        sb.append('['); // Начало вывода
        for (int i = _front, count = 0; count < Count; count++) { // Итерация по элементам
            sb.append(_items[i % _items.length]); // Добавление элемента в строку
            if (count < Count - 1) { // Если это не последний элемент
                sb.append(", "); // Добавление запятой между элементами
            }
            i++; // Переход к следующему индексу
        }
        sb.append("]"); // Завершение строки
        return sb.toString(); // Возврат результата
    }

    @Override
    public boolean add(E e) {
        addLast(e); // Добавление элемента в конец очереди
        return true; // Возврат успеха операции
    }

    @Override
    public void addFirst(E e) {
        if (Count == _items.length) // Проверка на переполнение
            Resize(_items.length * 2); // Увеличение размера массива в два раза
        _front = (_front - 1 + _items.length) % _items.length; // Переход к предыдущему индексу
        _items[_front] = e; // Добавление элемента
        Count++; // Увеличение количества элементов
    }

    @Override
    public void addLast(E e) {
        if (Count == _items.length) // Проверка на переполнение
            Resize(_items.length * 2); // Увеличение размера массива в два раза
        _rear = (_rear + 1) % _items.length; // Переход к следующему индексу
        _items[_rear] = e; // Добавление элемента
        Count++; // Увеличение количества элементов
    }

    void Resize(int max) {
        @SuppressWarnings("unchecked")
        E[] tempItems = (E[]) new Object[max]; // Создание нового массива увеличенного размера
        for (int i = 0; i < Count; i++) // Перенос элементов из старого массива
            tempItems[i] = _items[(_front + i) % _items.length]; // Копирование элемента с учетом кольцевого массива
        _items = tempItems; // Замена старого массива на новый
        _front = 0; // Сброс индекса _front
        _rear = Count - 1; // Установка индекса _rear
    }

    @Override
    public int size() {
        return Count; // Возврат количества элементов в очереди
    }

    @Override
    public E pollFirst() {
        if (Count == 0) // Проверка на пустую очередь
            return null; // Возврат null, если очередь пуста
        E item = _items[_front]; // Сохранение первого элемента
        _front = (_front + 1) % _items.length; // Переход к следующему элементу
        Count--; // Уменьшение количества элементов
        return item; // Возврат удаленного элемента
    }

    @Override
    public E pollLast() {
        if (Count == 0) // Проверка на пустую очередь
            return null; // Возврат null, если очередь пуста
        E item = _items[_rear]; // Сохранение последнего элемента
        _rear = (_rear - 1 + _items.length) % _items.length; // Переход к предыдущему элементу
        Count--; // Уменьшение количества элементов
        return item; // Возврат удаленного элемента
    }

    @Override
    public E getFirst() {
        if (Count == 0) // Проверка на пустую очередь
            throw new NoSuchElementException(); // Генерация исключения, если очередь пуста
        return _items[_front]; // Возврат первого элемента
    }

    @Override
    public E getLast() {
        if (Count == 0) // Проверка на пустую очередь
            throw new NoSuchElementException(); // Генерация исключения, если очередь пуста
        return _items[_rear]; // Возврат последнего элемента
    }

    @Override
    public E poll() {
        return pollFirst(); // Удаление первого элемента
    }

    @Override
    public E element() {
        return getFirst(); // Получение первого элемента
    }

    @Override
    public boolean offerFirst(E e) {
        addFirst(e); // Добавление элемента в начало очереди
        return true; // Возврат успеха операции
    }

    @Override
    public boolean offerLast(E e) {
        addLast(e); // Добавление элемента в конец очереди
        return true; // Возврат успеха операции
    }

    @Override
    public E removeFirst() {
        E item = pollFirst(); // Удаление первого элемента
        if (item == null) // Проверка на случай, если очередь пуста
            throw new NoSuchElementException(); // Генерация исключения
        return item; // Возврат удаленного элемента
    }

    @Override
    public E removeLast() {
        E item = pollLast(); // Удаление последнего элемента
        if (item == null) // Проверка на случай, если очередь пуста
            throw new NoSuchElementException(); // Генерация исключения
        return item; // Возврат удаленного элемента
    }

    @Override
    public E peekFirst() {
        return isEmpty() ? null : _items[_front]; // Возврат первого элемента или null, если очередь пуста
    }

    @Override
    public E peekLast() {
        return isEmpty() ? null : _items[_rear]; // Возврат последнего элемента или null, если очередь пуста
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        for (int i = 0; i < Count; i++) {
            int index = (_front + i) % _items.length;
            if (o.equals(_items[index])) { // Проверка на равенство
                removeAt(index); // Удаление элемента
                return true; // Возврат успеха операции
            }
        }
        return false; // Элемент не найден
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        for (int i = Count - 1; i >= 0; i--) {
            int index = (_front + i) % _items.length;
            if (o.equals(_items[index])) { // Проверка на равенство
                removeAt(index); // Удаление элемента
                return true; // Возврат успеха операции
            }
        }
        return false; // Элемент не найден
    }

    private void removeAt(int index) {
        // Сдвиг элементов влево от указанного индекса
        for (int i = index; i != _rear; i = (i + 1) % _items.length) {
            _items[i] = _items[(i + 1) % _items.length];
        }
        _rear = (_rear - 1 + _items.length) % _items.length; // Обновление _rear
        Count--; // Уменьшение количества элементов
    }

    @Override
    public boolean offer(E e) {
        return add(e); // Используем метод add для добавления элемента
    }

    @Override
    public E remove() {
        return removeFirst(); // Удаление первого элемента
    }

    @Override
    public E peek() {
        return peekFirst(); // Получение первого элемента
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E e : c) {
            add(e); // Добавление каждого элемента коллекции
        }
        return !c.isEmpty(); // Возврат true, если коллекция не пуста
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object o : c) {
            modified |= remove(o); // Удаление каждого элемента коллекции
        }
        return modified; // Возврат true, если были изменения
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        for (int i = 0; i < Count; i++) {
            if (!c.contains(_items[(_front + i) % _items.length])) {
                removeAt((_front + i) % _items.length); // Удаление, если элемента нет в коллекции
                modified = true; // Обновление флага изменения
                i--; // Уменьшение индекса, так как элемент удален
            }
        }
        return modified; // Возврат true, если были изменения
    }

    @Override
    public void clear() {
        _front = 0; // Сброс индекса _front
        _rear = -1; // Сброс индекса _rear
        Count = 0; // Обнуление количества элементов
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Проверка на равенство ссылок
        if (!(o instanceof Deque)) return false; // Проверка на соответствие интерфейсу
        Deque<?> other = (Deque<?>) o; // Приведение к интерфейсу
        if (Count != other.size()) return false; // Сравнение количества элементов
        for (int i = 0; i < Count; i++) {
            if (!other.contains(_items[(_front + i) % _items.length])) // Сравнение элементов
                return false; // Если элементы не равны, вернуть false
        }
        return true; // Все элементы равны
    }

    @Override
    public int hashCode() {
        int hash = 1; // Начальное значение хеша
        for (int i = 0; i < Count; i++) {
            hash = 31 * hash + (_items[(_front + i) % _items.length] == null ? 0 : _items[(_front + i) % _items.length].hashCode()); // Обновление хеша
        }
        return hash; // Возврат вычисленного хеша
    }

    @Override
    public void push(E e) {
        addFirst(e); // Добавление элемента в начало очереди
    }

    @Override
    public E pop() {
        return removeFirst(); // Удаление и возврат первого элемента
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < Count; i++) {
            int index = (_front + i) % _items.length;
            if (o.equals(_items[index])) { // Проверка на равенство
                removeAt(index); // Удаление элемента
                return true; // Возврат успеха операции
            }
        }
        return false; // Элемент не найден
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) return false; // Проверка наличия каждого элемента
        }
        return true; // Все элементы найдены
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < Count; i++) {
            if (o.equals(_items[(_front + i) % _items.length])) // Проверка на равенство
                return true; // Элемент найден
        }
        return false; // Элемент не найден
    }

    @Override
    public boolean isEmpty() {
        return Count == 0; // Проверка, пуст ли deque
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int currentIndex = _front; // Индекс текущего элемента
            private int elementsReturned = 0; // Количество возвращенных элементов

            @Override
            public boolean hasNext() {
                return elementsReturned < Count; // Проверка наличия следующего элемента
            }

            @Override
            public E next() {
                if (!hasNext()) throw new NoSuchElementException(); // Генерация исключения, если нет следующего элемента
                E item = _items[currentIndex]; // Сохранение текущего элемента
                currentIndex = (currentIndex + 1) % _items.length; // Переход к следующему индексу
                elementsReturned++; // Увеличение счетчика возвращенных элементов
                return item; // Возврат текущего элемента
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[Count]; // Создание массива нужного размера
        for (int i = 0; i < Count; i++) {
            result[i] = _items[(_front + i) % _items.length]; // Копирование элементов в новый массив
        }
        return result; // Возврат нового массива
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < Count) {
            // Если массив недостаточно велик, создаем новый
            @SuppressWarnings("unchecked")
            T[] newArray = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), Count);
            a = newArray; // Обновление массива
        }
        for (int i = 0; i < Count; i++) {
            a[i] = (T) _items[(_front + i) % _items.length]; // Копирование элементов
        }
        if (a.length > Count) {
            a[Count] = null; // Установка следующего элемента в null
        }
        return a; // Возврат обновленного массива
    }

    @Override
    public Iterator<E> descendingIterator() {
        return new Iterator<E>() {
            private int currentIndex = _rear; // Индекс текущего элемента
            private int elementsReturned = 0; // Количество возвращенных элементов

            @Override
            public boolean hasNext() {
                return elementsReturned < Count; // Проверка наличия следующего элемента
            }

            @Override
            public E next() {
                if (!hasNext()) throw new NoSuchElementException(); // Генерация исключения, если нет следующего элемента
                E item = _items[currentIndex]; // Сохранение текущего элемента
                currentIndex = (currentIndex - 1 + _items.length) % _items.length; // Переход к предыдущему индексу
                elementsReturned++; // Увеличение счетчика возвращенных элементов
                return item; // Возврат текущего элемента
            }
        };
    }
}
