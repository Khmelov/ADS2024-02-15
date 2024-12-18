package by.it.group310902.pashkovich.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

// Реализация пользовательского HashSet
public class MyHashSet<E> implements Set<E> {

    // Внутренний класс представляет узел связного списка
    class Node<E> {
        E data; // Данные узла
        Node<E> next; // Ссылка на следующий узел

        Node(E data) { // Конструктор узла
            this.data = data;
        }
    }

    static final int defaultSize = 32; // Стандартный размер хэш-таблицы
    Node<E>[] _items; // Массив узлов, представляющий хэш-таблицу
    int _count; // Количество элементов в множестве

    // Конструктор по умолчанию
    public MyHashSet() {
        this(defaultSize);
    }

    // Конструктор с указанием начальной емкости
    public MyHashSet(int capacity) {
        _items = new Node[capacity];
    }

    // Метод для вычисления хэша элемента
    int GetHash(Object value) {
        return (value.hashCode() & 0x7FFFFFFF) % _items.length;
    }

    // Метод для представления множества в виде строки
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        boolean first = true;
        for (int i = 0; i < _items.length; i++) {
            Node<E> current = _items[i];
            while (current != null) {
                if (!first) {
                    sb.append(", "); // Добавляем запятую между элементами
                }
                sb.append(current.data); // Добавляем текущий элемент
                first = false;
                current = current.next; // Переход к следующему узлу
            }
        }
        sb.append("]");
        return sb.toString();
    }

    // Возвращает количество элементов в множестве
    @Override
    public int size() {
        return _count;
    }

    // Проверяет, пусто ли множество
    @Override
    public boolean isEmpty() {
        return _count == 0;
    }

    // Проверяет, содержится ли заданный объект в множестве
    @Override
    public boolean contains(Object o) {

        Node<E> current = _items[GetHash(o)];
        while (current != null) {
            if (current.data.equals(o)) {
                return true; // Элемент найден
            }
            current = current.next;
        }
        return false; // Элемент не найден
    }

    // Добавляет элемент в множество
    @Override
    public boolean add(E e) {
        int index = GetHash(e); // Вычисляем индекс для элемента
        Node<E> current = _items[index];
        while (current != null) {
            if (current.data.equals(e)) {
                return false; // Элемент уже существует
            }
            current = current.next;
        }
        Node<E> newNode = new Node<>(e); // Создаем новый узел
        newNode.next = _items[index]; // Добавляем его в начало списка
        _items[index] = newNode; // Обновляем ссылку на начало списка
        _count++; // Увеличиваем счетчик элементов
        if (_count > _items.length * 0.75) { // Проверяем, нужно ли увеличивать размер
            resize();
        }
        return true;
    }

    // Увеличивает размер хэш-таблицы и перераспределяет элементы
    void resize() {
        Node<E>[] newItems = new Node[_items.length * 2]; // Увеличиваем массив вдвое
        for (int i = 0; i < _items.length; i++) {
            Node<E> current = _items[i];
            while (current != null) {
                Node<E> next = current.next; // Сохраняем ссылку на следующий узел
                int newIndex = current.data.hashCode() & 0x7FFFFFFF % newItems.length;
                current.next = newItems[newIndex]; // Перемещаем узел в новый массив
                newItems[newIndex] = current;
                current = next; // Переход к следующему узлу
            }
        }
        _items = newItems; // Обновляем ссылку на новый массив
    }

    // Удаляет элемент из множества
    @Override
    public boolean remove(Object o) {
        int index = GetHash(o); // Вычисляем индекс для элемента
        Node<E> current = _items[index];
        Node<E> previous = null;
        while (current != null) {
            if (current.data.equals(o)) {
                if (previous == null) {
                    _items[index] = current.next; // Удаляем первый узел
                } else {
                    previous.next = current.next; // Удаляем текущий узел
                }
                _count--; // Уменьшаем счетчик элементов
                return true;
            }
            previous = current; // Сохраняем текущий узел как предыдущий
            current = current.next;
        }
        return false; // Элемент не найден
    }

    // Очищает множество
    @Override
    public void clear() {
        for (int i = 0; i < _items.length; i++)
            _items[i] = null; // Удаляем все узлы
        _count = 0; // Сбрасываем счетчик элементов
    }

    ////////////////////////////////////////////////////////////////////////

    // Возвращает итератор для множества
    @Override
    public Iterator<E> iterator() {
        return null; // Итератор не реализован
    }

    // Преобразует множество в массив
    @Override
    public Object[] toArray() {
        return new Object[0]; // Метод не реализован
    }

    // Преобразует множество в массив указанного типа
    @Override
    public <T> T[] toArray(T[] a) {
        return null; // Метод не реализован
    }

    // Проверяет, содержатся ли все элементы коллекции в множестве
    @Override
    public boolean containsAll(Collection<?> c) {
        return false; // Метод не реализован
    }

    // Добавляет все элементы из коллекции в множество
    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false; // Метод не реализован
    }

    // Удаляет элементы, которые не содержатся в указанной коллекции
    @Override
    public boolean retainAll(Collection<?> c) {
        return false; // Метод не реализован
    }

    // Удаляет из множества все элементы, содержащиеся в указанной коллекции
    @Override
    public boolean removeAll(Collection<?> c) {
        return false; // Метод не реализован
    }
}