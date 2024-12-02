package by.it.group351001.ushakou.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/*
 * Реализация собственного HashSet.
 * Программа реализует структуру данных HashSet, используя хеширование для быстрого поиска, вставки и удаления элементов.
 * Каждый элемент представляет собой цепочку связанных узлов (Node), чтобы разрешать коллизии.
 * Это решение включает методы добавления, удаления, поиска и изменения размера хеш-таблицы.
 * Также реализован метод toString, чтобы вывести содержимое множества в виде строки.
 */
public class MyHashSet<E> implements Set<E> {

    // Класс для представления узла, хранящего данные и ссылку на следующий узел
    class Node<E> {
        E data; // Данные узла
        Node<E> next; // Ссылка на следующий узел

        // Конструктор узла
        Node(E data) {
            this.data = data;
        }
    }

    // Статическая переменная для хранения дефолтного размера хеш-таблицы
    static final int defaultSize = 32;

    // Массив для хранения элементов хеш-таблицы
    Node<E>[] _items;

    // Счётчик текущего количества элементов в хеш-таблице
    int _count;

    // Конструктор по умолчанию, который инициализирует хеш-таблицу с дефолтным размером
    public MyHashSet() {
        this(defaultSize);
    }

    // Конструктор, позволяющий указать размер хеш-таблицы
    public MyHashSet(int capacity) {
        _items = new Node[capacity];
    }

    /**
     * Метод для вычисления хеш-индекса элемента.
     * @param value - элемент, для которого вычисляется хеш
     * @return хеш-индекс элемента
     */
    int GetHash(Object value) {
        // Возвращаем хеш-индекс, используя хеш-код элемента и операцию остатка от деления
        return (value.hashCode() & 0x7FFFFFFF) % _items.length;
    }

    /**
     * Переопределение метода toString для представления содержимого множества в виде строки.
     * @return строковое представление множества
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("["); // Строка для формирования вывода
        boolean first = true; // Флаг для того, чтобы разделять элементы запятой
        // Проходим по всем индексам хеш-таблицы
        for (int i = 0; i < _items.length; i++) {
            Node<E> current = _items[i]; // Получаем первый узел в цепочке для текущего индекса
            // Проходим по цепочке узлов
            while (current != null) {
                if (!first) { // Если это не первый элемент, добавляем запятую
                    sb.append(", ");
                }
                sb.append(current.data); // Добавляем данные текущего узла
                first = false; // Сбрасываем флаг, чтобы далее добавлялись запятые
                current = current.next; // Переходим к следующему узлу
            }
        }

        sb.append("]"); // Закрываем строку
        return sb.toString(); // Возвращаем строковое представление множества
    }

    /**
     * Метод для получения текущего размера множества.
     * @return количество элементов в множестве
     */
    @Override
    public int size() {
        return _count; // Возвращаем количество элементов
    }

    /**
     * Проверка, пусто ли множество.
     * @return true, если множество пустое, иначе false
     */
    @Override
    public boolean isEmpty() {
        return _count == 0; // Если количество элементов равно 0, множество пустое
    }

    /**
     * Проверка, содержится ли элемент в множестве.
     * @param o - объект, который проверяется
     * @return true, если элемент найден, иначе false
     */
    @Override
    public boolean contains(Object o) {
        // Получаем индекс хеш-таблицы для данного элемента
        Node<E> current = _items[GetHash(o)];
        // Ищем элемент в цепочке
        while (current != null) {
            if (current.data.equals(o)) { // Если нашли совпадение
                return true; // Возвращаем true
            }
            current = current.next; // Переходим к следующему узлу
        }
        return false; // Если не нашли элемент
    }

    /**
     * Метод для добавления элемента в множество.
     * @param e - элемент, который нужно добавить
     * @return true, если элемент был добавлен, иначе false
     */
    @Override
    public boolean add(E e) {
        int index = GetHash(e); // Получаем индекс для нового элемента
        Node<E> current = _items[index];
        // Проверяем, не существует ли уже такой элемент
        while (current != null) {
            if (current.data.equals(e)) {
                return false; // Если элемент уже существует, не добавляем
            }
            current = current.next; // Переходим к следующему узлу
        }
        // Создаём новый узел для элемента
        Node<E> newNode = new Node<>(e);
        newNode.next = _items[index]; // Устанавливаем новый узел как первый в цепочке
        _items[index] = newNode; // Устанавливаем новый узел в хеш-таблицу
        _count++; // Увеличиваем счётчик элементов
        // Если количество элементов превышает 75% от ёмкости, увеличиваем размер хеш-таблицы
        if (_count > _items.length * 0.75) {
            resize();
        }
        return true; // Возвращаем true, так как элемент был добавлен
    }

    /**
     * Метод для увеличения размера хеш-таблицы и перераспределения элементов.
     */
    void resize() {
        // Создаём новый массив для элементов хеш-таблицы с удвоенным размером
        Node<E>[] newItems = new Node[_items.length * 2];
        // Перебираем все элементы старой хеш-таблицы
        for (int i = 0; i < _items.length; i++) {
            Node<E> current = _items[i];
            // Перемещаем элементы в новую хеш-таблицу
            while (current != null) {
                Node<E> next = current.next; // Сохраняем следующий узел
                // Пересчитываем хеш-индекс для нового массива
                int newIndex = current.data.hashCode() & 0x7FFFFFFF % newItems.length;
                current.next = newItems[newIndex]; // Добавляем узел в новый массив
                newItems[newIndex] = current; // Обновляем новый массив
                current = next; // Переходим к следующему узлу
            }
        }
        _items = newItems; // Переназначаем массив хеш-таблицы на новый
    }

    /**
     * Метод для удаления элемента из множества.
     * @param o - элемент, который нужно удалить
     * @return true, если элемент был удалён, иначе false
     */
    @Override
    public boolean remove(Object o) {
        int index = GetHash(o); // Получаем индекс хеш-таблицы для элемента
        Node<E> current = _items[index];
        Node<E> previous = null;
        // Ищем элемент в цепочке
        while (current != null) {
            if (current.data.equals(o)) { // Если нашли элемент
                if (previous == null) { // Если это первый элемент в цепочке
                    _items[index] = current.next; // Сдвигаем указатель на следующий узел
                } else {
                    previous.next = current.next; // Удаляем текущий узел из цепочки
                }
                _count--; // Уменьшаем счётчик элементов
                return true; // Возвращаем true, так как элемент был удалён
            }
            previous = current; // Переходим к следующему узлу
            current = current.next;
        }
        return false; // Если элемент не найден
    }

    /**
     * Метод для очистки множества (удаление всех элементов).
     */
    @Override
    public void clear() {
        // Обнуляем все индексы хеш-таблицы
        for (int i = 0; i < _items.length; i++)
            _items[i] = null;
        _count = 0; // Устанавливаем количество элементов в 0
    }

    // Методы Set, которые не реализованы в данном классе

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
