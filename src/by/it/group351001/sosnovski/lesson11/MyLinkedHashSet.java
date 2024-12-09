// Класс MyLinkedHashSet<E> представляет собой реализацию упорядоченного множества.
// Использует хеш-таблицу для быстрого поиска и удаления элементов.
// Также поддерживает порядок вставки элементов, используя двойную связность.
// Реализованы основные методы множества, а также дополнительные методы для работы с коллекциями.
package by.it.group351001.sosnovski.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyLinkedHashSet <E> implements Set<E> {

    // Внутренний класс Node<E> для хранения элемента и связей для хеширования и порядка вставки
    class Node<E> {
        E data;          // значение элемента
        Node<E> next;    // ссылка на следующий элемент в цепочке для хеширования
        Node<E> prev, follow; // ссылки на предыдущий и следующий элементы для порядка вставки

        // Конструктор, инициализирующий значение элемента
        Node(E e) {
            data = e;
        }
    }

    // Константа начального размера массива
    static final int START_SIZE = 20;

    // Количество элементов в множестве
    int _size = 0;

    // Массив цепочек (для разрешения коллизий)
    Node<E>[] _items;

    // Указатели на начало и конец упорядоченного списка
    Node<E> _head, _tail;

    // Конструктор по умолчанию с начальным размером массива
    MyLinkedHashSet() {
        this(START_SIZE);
    }

    // Конструктор с возможностью указания размера массива
    MyLinkedHashSet(int size) {
        _items = new Node[size];
    }

    // Метод для представления множества в виде строки, где элементы следуют в порядке вставки
    @Override
    public String toString() {
        StringBuilder line = new StringBuilder("[");
        Node<E> curr = _head;
        while (curr != null) {
            line.append(curr.data);
            if (curr.follow != null)
                line.append(", ");
            curr = curr.follow;
        }
        line.append("]");
        return line.toString();
    }

    // Возвращает количество элементов в множестве
    @Override
    public int size() {
        return _size;
    }

    // Проверяет, является ли множество пустым
    @Override
    public boolean isEmpty() {
        return _size == 0;
    }

    // Проверяет, содержит ли множество указанный элемент
    @Override
    public boolean contains(Object o) {
        for (Node<E> item : _items) {
            Node<E> curr = item;
            while (curr != null) {
                if (o.equals(curr.data)) {
                    return true;
                }
                curr = curr.next;
            }
        }
        return false;
    }

    // Заглушка для метода iterator()
    @Override
    public Iterator<E> iterator() {
        return null;
    }

    // Возвращает хеш для объекта, который соответствует размеру массива
    int getHash(Object o) {
        return (o.hashCode() & 0x7FFFFFFF) % _items.length;
    }

    // Добавляет новый узел в конец упорядоченного списка
    void addNode(Node<E> newNode) {
        if (_head == null) {
            _head = newNode;
        } else {
            _tail.follow = newNode;
            newNode.prev = _tail;
        }
        _tail = newNode;
    }

    // Удаляет узел из упорядоченного списка, обновляя связи
    void removeNode(Node<E> newNode) {
        if (newNode.follow != null) {
            newNode.follow.prev = newNode.prev;
        } else {
            _tail = newNode.prev;
        }
        if (newNode.prev != null) {
            newNode.prev.follow = newNode.follow;
        } else {
            _head = newNode.follow;
        }
    }

    // Увеличивает размер массива и перераспределяет элементы для уменьшения коллизий
    void resize() {
        Node<E>[] newItems = new Node[_items.length * 2];
        for (Node<E> curr : _items) {
            while (curr != null) {
                Node<E> next = curr.next;
                int newInd = (curr.data.hashCode() & 0x7FFFFFFF) % newItems.length;
                curr.next = newItems[newInd];
                newItems[newInd] = curr;
                curr = next;
            }
        }
        _items = newItems;
    }

    // Удаляет элемент из множества, возвращает true, если элемент был удален
    @Override
    public boolean remove(Object o) {
        int ind = getHash(o);
        Node<E> curr = _items[ind];
        Node<E> prev = null;

        while (curr != null) {
            if (o.equals(curr.data)) {
                if (prev == null) {
                    _items[ind] = curr.next;
                } else {
                    prev.next = curr.next;
                }
                _size--;
                removeNode(curr);
                return true;
            }
            prev = curr;
            curr = curr.next;
        }
        return false;
    }

    // Очищает множество, удаляя все элементы
    @Override
    public void clear() {
        for (int i = 0; i < _items.length; i++) {
            _items[i] = null;
        }
        _size = 0;
        _head = null;
        _tail = null;
    }

    // Добавляет элемент в множество, возвращает false, если элемент уже существует
    @Override
    public boolean add(Object o) {
        Node<E> newNode = new Node<E>((E)o);
        int ind = getHash(o);
        Node<E> curr = _items[ind];

        while (curr != null) {
            if (curr.data.equals(o)) {
                return false;
            }
            curr = curr.next;
        }
        newNode.next = _items[ind];
        _items[ind] = newNode;
        addNode(newNode);

        if (++_size > _items.length * 0.7) {
            resize();
        }
        return true;
    }

    // Добавляет все элементы из указанной коллекции
    @Override
    public boolean addAll(Collection c) {
        boolean isModified = false;
        for (Object item : c) {
            if (add(item)) {
                isModified = true;
            }
        }
        return isModified;
    }

    // Удаляет все элементы из множества, которые также содержатся в указанной коллекции
    @Override
    public boolean removeAll(Collection c) {
        boolean isModified = false;
        for (Object item : c) {
            if (remove(item)) {
                isModified = true;
            }
        }
        return isModified;
    }

    // Удаляет все элементы из множества, которые не содержатся в указанной коллекции
    @Override
    public boolean retainAll(Collection c) {
        if (c.isEmpty()) {
            clear();
            return true;
        }
        boolean isModified = false;
        Node<E> curr = _head;

        while (curr != null) {
            Node<E> next = curr.follow;
            if (!c.contains(curr.data)) {
                remove(curr.data);
                isModified = true;
            }
            curr = next;
        }
        return isModified;
    }

    // Проверяет, содержит ли множество все элементы указанной коллекции
    @Override
    public boolean containsAll(Collection c) {
        for (Object item : c) {
            if (!contains(item)) {
                return false;
            }
        }
        return true;
    }

    //------------------------------

    // Заглушки для необязательных методов интерфейса Set
    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public Object[] toArray(Object[] a) {
        return new Object[0];
    }
}
