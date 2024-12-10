// Класс MyHashSet<E> представляет собой реализацию множества (Set) на основе хеш-таблицы.
// Он хранит элементы в массиве связных списков (цепочек), используя хеширование для распределения элементов по массиву.
// Реализованы основные методы множества: добавление, удаление, проверка наличия элемента и другие вспомогательные методы.
package by.it.group351001.shimanchuk.lesson11;
// Класс MyHashSet<E> реализует множество на основе хеш-таблицы.
// Внутренний класс Node<E> представляет элемент связного списка, который хранит данные и ссылку на следующий элемент.
// Массив _items хранит начало цепочек для разрешения коллизий.
// Основные методы включают:
// - Добавление элемента (add): проверяет наличие и добавляет, увеличивая размер при необходимости.
// - Удаление элемента (remove): удаляет элемент из множества.
// - Проверка наличия элемента (contains): проверяет, существует ли элемент в множестве.
// - Метод toString() для строкового представления множества.
// - Метод clear() для очистки множества.
// - Реализован метод resize() для перераспределения элементов при превышении загрузки.
// Не реализованы некоторые методы интерфейса Set, такие как итератор, toArray и работа с коллекциями.
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyHashSet <E> implements Set<E> {

    // Внутренний класс Node<E> для хранения элементов в связном списке.
    class Node<E> {
        E data;          // значение элемента
        Node<E> next;    // ссылка на следующий элемент в цепочке

        // Конструктор, инициализирующий значение элемента
        Node(E data) {
            this.data = data;
        }
    }

    // Константа для начального размера массива (по умолчанию)
    static final int defaultSize = 32;

    // Массив цепочек, где каждый индекс хранит начало связного списка для элементов, имеющих одинаковый хеш
    Node<E>[] _items;

    // Количество элементов в множестве
    int _count;

    // Конструктор без параметров, инициализирующий множество с начальным размером по умолчанию
    public MyHashSet() {
        this(defaultSize);
    }

    // Конструктор с параметром для указания начальной емкости
    public MyHashSet(int capacity) {
        _items = new Node[capacity];
    }

    // Метод для вычисления хеша элемента, ограниченного размером массива
    int GetHash(Object value) {
        return (value.hashCode() & 0x7FFFFFFF) % _items.length;
    }

    // Метод для представления множества в виде строки (например, "[a, b, c]")
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        boolean first = true;
        for (int i = 0; i < _items.length; i++) {
            Node<E> current = _items[i];
            // Проход по каждому элементу цепочки в текущем индексе
            while (current != null) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append(current.data);
                first = false;
                current = current.next;
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

    // Проверяет, является ли множество пустым
    @Override
    public boolean isEmpty() {
        return _count == 0;
    }

    // Проверяет, содержит ли множество указанный элемент
    @Override
    public boolean contains(Object o) {
        Node<E> current = _items[GetHash(o)];
        while (current != null) {
            if (current.data.equals(o)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    // Добавляет элемент в множество, возвращает false, если элемент уже существует
    @Override
    public boolean add(E e) {
        int index = GetHash(e);
        Node<E> current = _items[index];
        // Проверка на наличие элемента
        while (current != null) {
            if (current.data.equals(e)) {
                return false; // Элемент уже в множестве
            }
            current = current.next;
        }
        // Добавление элемента в начало цепочки
        Node<E> newNode = new Node<>(e);
        newNode.next = _items[index];
        _items[index] = newNode;
        _count++;

        // Если размер превышает 75% от длины массива, выполняем расширение
        if (_count > _items.length * 0.75) {
            resize();
        }
        return true;
    }

    // Увеличивает размер массива и перераспределяет элементы для уменьшения коллизий
    void resize() {
        Node<E>[] newItems = new Node[_items.length * 2];
        for (int i = 0; i < _items.length; i++) {
            Node<E> current = _items[i];
            while (current != null) {
                Node<E> next = current.next; // Сохраняем ссылку на следующий элемент
                int newIndex = (current.data.hashCode() & 0x7FFFFFFF) % newItems.length;
                // Перемещаем элемент в новый массив
                current.next = newItems[newIndex];
                newItems[newIndex] = current;
                current = next;
            }
        }
        _items = newItems; // Обновляем ссылку на новый массив
    }

    // Удаляет элемент из множества, возвращает true, если элемент был удален
    @Override
    public boolean remove(Object o) {
        int index = GetHash(o);
        Node<E> current = _items[index];
        Node<E> previous = null;

        // Проход по цепочке для поиска элемента
        while (current != null) {
            if (current.data.equals(o)) {
                if (previous == null) {
                    _items[index] = current.next;
                } else {
                    previous.next = current.next;
                }
                _count--;
                return true;
            }
            previous = current;
            current = current.next;
        }
        return false;
    }

    // Очищает множество, удаляя все элементы
    @Override
    public void clear() {
        for (int i = 0; i < _items.length; i++)
            _items[i] = null;
        _count = 0;
    }

    //////////////////////////////////////////////////////////////////////////
    // Заглушки для методов интерфейса Set, которые не были реализованы:

    @Override
    public Iterator<E> iterator() {
        return null; // Не реализован
    }

    @Override
    public Object[] toArray() {
        return new Object[0]; // Не реализован
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null; // Не реализован
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false; // Не реализован
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false; // Не реализован
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false; // Не реализован
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false; // Не реализован
    }
}
