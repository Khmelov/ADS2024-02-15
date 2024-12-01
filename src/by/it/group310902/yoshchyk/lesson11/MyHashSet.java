package by.it.group310902.yoshchyk.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
//класс обеспечивает базовую реализацию хэш-набора.
// использует массив связанных списков для обработки коллизий, возникают, когда два разных
// элемента хэшируются в один и тот же индекс в массиве.

//работает на основе массива с адресацией по хеш-коду и односвязным списком для элементов с коллизиями
public class MyHashSet<E> implements Set<E> {

    // Узел в связанном списке, используемом для хранения элементов в каждом блоке хэш-таблицы
    class Node<E> {
        E data; // Данные, хранящиеся в узле
        Node<E> next; // Ссылка на следующий узел

        Node(E data) {
            this.data = data;
        } // Инициализация данных узла
    }
    static final int defaultSize = 32;// Размер по умолчанию для хэш-таблицы
    Node<E>[] _items; // Массив для хранения узлов
    int _count; // Текущее количество элементов в наборе
    //в случае коллизии новые элементы добавляются в начало связанного списка в этом контейнере

    // Конструктор, который создает хэш-набор с заданным размером
    public MyHashSet() {
        this(defaultSize);
    }

    // Конструктор, который принимает начальную емкость
    public MyHashSet(int capacity) {
        _items = new Node[capacity];
    }

    // для определения индекса контейнера.
    int GetHash(Object value) {
        return (value.hashCode()) % _items.length;
    }// Вычисление индекса с использованием хеш-кода.

    //Метод для возврата хеш сета в строку
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        boolean first = true;
        for (int i = 0; i < _items.length; i++) {
            Node<E> current = _items[i];
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

    //Метод для возврата размера
    @Override
    public int size() {
        return _count;
    }

    //Метод для определения пустоты
    @Override
    public boolean isEmpty() {
        return _count == 0;
    }

    //Метод для определения содержания объекта
    @Override
    public boolean contains(Object o) {
        // Поиск элемента по хеш-коду и проверка на его наличие в списке
        Node<E> current = _items[GetHash(o)];
        while (current != null) {
            if (current.data.equals(o)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    //Метод для добавления элемента
    @Override
    public boolean add(E e) {
        int index = GetHash(e);// Вычисление индекса для нового элемента
        Node<E> current = _items[index];

        // Проверка на дублирование элемента.
        while (current != null) {
            if (current.data.equals(e)) {
                return false;// Элемент уже существует, добавление не требуется
            }
            current = current.next;
        }

        // Создание нового узла и добавление его в начало списка
        Node<E> newNode = new Node<>(e);
        newNode.next = _items[index]; // Присоединение нового узла к началу списка
        _items[index] = newNode; // Обновление ссылки в массиве
        _count++; // Увеличение счетчика элементов

        // Проверка необходимости увеличения размера хэш-таблицы
        if (_count >= _items.length ) {
            resize();// Увеличение размера хэш-таблицы
        }
        return true; // Элемент успешно добавлен
    }

    // Метод, вызываемый для удвоения емкости хэш-таблицы
    void resize() {
        Node<E>[] newItems = new Node[_items.length * 2]; // Новый массив в два раза больше
        // Перемещение элементов в новый массив
        for (int i = 0; i < _items.length; i++) {
            Node<E> current = _items[i]; // Начало списка на данной позиции
            while (current != null) {
                Node<E> next = current.next; // Сохранение ссылки на следующий узел
                int newIndex = current.data.hashCode() & 0x7FFFFFFF % newItems.length; // Новый индекс для хеширования

                // Добавление текущего узла в новый массив
                current.next = newItems[newIndex];
                newItems[newIndex] = current; // Обновление ссылки в новом массиве
                current = next; // Переход к следующему узлу
            }
        }
        _items = newItems;
    }


    //Метод для удаления элемента
    @Override
    public boolean remove(Object o) {
        int index = GetHash(o); // Вычисление индекса для удаления
        Node<E> current = _items[index];
        Node<E> previous = null;

        // Поиск узла для удалени
        while (current != null) {
            if (current.data.equals(o)) {
                if (previous == null) {
                    _items[index] = current.next; // Удаление первого узла
                } else {
                    previous.next = current.next;; // Удаление узла из списка
                }
                _count--;
                return true;
            }
            previous = current; // Обновление ссылки на предыдущий узел
            current = current.next; // Переход к следующему узлу
        }
        return false;
    }


    //Метод для очистки сета
    @Override
    public void clear() {
        for (int i = 0; i < _items.length; i++)
            _items[i] = null;
        _count = 0;
    }


    ////////////////////////////////////////////////////////////////////////

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