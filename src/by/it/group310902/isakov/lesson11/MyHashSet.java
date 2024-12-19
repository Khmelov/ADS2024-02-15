package by.it.group310902.isakov.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

// Реализация собственного множества (Set) с использованием хэш-таблицы.
public class MyHashSet<E> implements Set<E> {

    // Внутренний класс для представления узла в связном списке.
    class Node<E> {
        E data; // Данные, хранящиеся в узле.
        Node<E> next; // Ссылка на следующий узел в цепочке.

        Node(E data) {
            this.data = data; // Инициализация данных.
        }
    }

    // Размер хэш-таблицы по умолчанию.
    static final int defaultSize = 32;

    // Массив цепочек (связных списков) для хранения элементов.
    Node<E>[] _items;

    // Количество элементов в множестве.
    int _count;

    // Конструктор без параметров - создает множество с размером по умолчанию.
    public MyHashSet() {
        this(defaultSize);
    }

    // Конструктор с заданной начальной емкостью.
    public MyHashSet(int capacity) {
        _items = new Node[capacity]; // Инициализация массива.
    }

    // Вычисляет хэш-код элемента и приводит его к индексу массива.
    int GetHash(Object value) {
        return (value.hashCode() & 0x7FFFFFFF) % _items.length; // Маскирует знак и вычисляет индекс.
    }

    // Переопределение метода toString() для представления множества в виде строки.
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("["); // Старт строки.
        boolean first = true; // Флаг для добавления запятых между элементами.
        for (int i = 0; i < _items.length; i++) { // Проход по массиву цепочек.
            Node<E> current = _items[i];
            while (current != null) { // Проход по каждому узлу в цепочке.
                if (!first) {
                    sb.append(", "); // Добавление запятой между элементами.
                }
                sb.append(current.data); // Добавление элемента в строку.
                first = false;
                current = current.next; // Переход к следующему узлу.
            }
        }
        sb.append("]");
        return sb.toString();
    }

    // Возвращает количество элементов в множестве.
    @Override
    public int size() {
        return _count;
    }

    // Проверяет, пусто ли множество.
    @Override
    public boolean isEmpty() {
        return _count == 0;
    }

    // Проверяет, содержит ли множество указанный элемент.
    @Override
    public boolean contains(Object o) {
        Node<E> current = _items[GetHash(o)]; // Определяем цепочку по хэшу элемента.
        while (current != null) { // Проходим по цепочке.
            if (current.data.equals(o)) { // Если элемент найден - возвращаем true.
                return true;
            }
            current = current.next; // Переход к следующему узлу.
        }
        return false; // Если элемент не найден - возвращаем false.
    }

    // Добавляет элемент в множество.
    @Override
    public boolean add(E e) {
        int index = GetHash(e); // Вычисляем индекс массива.
        Node<E> current = _items[index]; // Получаем первую цепочку по индексу.
        while (current != null) { // Проверяем, есть ли элемент в цепочке.
            if (current.data.equals(e)) {
                return false; // Если элемент уже есть, ничего не делаем.
            }
            current = current.next;
        }
        Node<E> newNode = new Node<>(e); // Создаем новый узел.
        newNode.next = _items[index]; // Вставляем новый узел в начало цепочки.
        _items[index] = newNode;
        _count++; // Увеличиваем счетчик элементов.

        // Если количество элементов превышает 75% от емкости, увеличиваем таблицу.
        if (_count > _items.length * 0.75) {
            resize();
        }
        return true;
    }

    // Увеличивает размер хэш-таблицы при превышении порога заполненности.
    void resize() {
        Node<E>[] newItems = new Node[_items.length * 2]; // Новый массив вдвое большего размера.
        for (int i = 0; i < _items.length; i++) {
            Node<E> current = _items[i]; // Получаем цепочку.
            while (current != null) {
                Node<E> next = current.next; // Сохраняем ссылку на следующий узел.
                int newIndex = current.data.hashCode() & 0x7FFFFFFF % newItems.length; // Новый индекс.
                current.next = newItems[newIndex]; // Перемещаем узел в новую таблицу.
                newItems[newIndex] = current;
                current = next; // Переход к следующему узлу.
            }
        }
        _items = newItems; // Заменяем старый массив на новый.
    }

    // Удаляет элемент из множества.
    @Override
    public boolean remove(Object o) {
        int index = GetHash(o); // Определяем индекс массива.
        Node<E> current = _items[index];
        Node<E> previous = null; // Для отслеживания предыдущего узла.
        while (current != null) {
            if (current.data.equals(o)) { // Если элемент найден.
                if (previous == null) { // Если удаляем первый узел цепочки.
                    _items[index] = current.next;
                } else {
                    previous.next = current.next; // Удаление узла из цепочки.
                }
                _count--; // Уменьшаем счетчик.
                return true;
            }
            previous = current;
            current = current.next; // Переход к следующему узлу.
        }
        return false; // Если элемент не найден.
    }

    // Очищает множество.
    @Override
    public void clear() {
        for (int i = 0; i < _items.length; i++)
            _items[i] = null; // Удаляем все цепочки.
        _count = 0; // Сбрасываем счетчик.
    }

    // Заглушки для остальных методов интерфейса Set, которые пока не реализованы.
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
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }
}
