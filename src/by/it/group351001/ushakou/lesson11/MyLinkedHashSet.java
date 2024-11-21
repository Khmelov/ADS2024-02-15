package by.it.group351001.ushakou.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/* *
 * Реализация собственного LinkedHashSet.
 * Этот класс представляет собой структуру данных, которая использует хеширование для быстрого поиска,
 * вставки и удаления элементов, а также поддерживает порядок вставки элементов.
 * Каждому элементу присваивается уникальный хеш-индекс, и элементы сохраняются в связанном списке.
 * Все элементы хранятся в хеш-таблице, а порядок их появления сохраняется с помощью двух ссылок (after и previous).
 * Программа реализует методы для добавления, удаления, поиска элементов, а также методы для работы с коллекциями.
 */
public class MyLinkedHashSet<E> implements Set<E> {

    // Класс для представления узла, хранящего данные и ссылки на следующий и предыдущий узлы
    class Node<E> {
        E data; // Данные элемента
        Node<E> next; // Ссылка на следующий элемент в хеш-таблице
        Node<E> after, previous; // Ссылки на следующий и предыдущий узлы в связанном списке

        // Конструктор узла, инициализирует данные элемента
        Node(E data) {
            this.data = data;
        }
    }

    static final int defaultSize = 32; // Статическая переменная для дефолтного размера хеш-таблицы
    Node<E>[] _items; // Массив для хранения элементов хеш-таблицы

    Node<E> _head, _tail; // Указатели на начало и конец связанного списка
    int _count; // Счётчик количества элементов в хеш-таблице

    // Метод для вывода содержимого множества в строковом формате
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(); // Строка для формирования вывода
        sb.append("["); // Добавляем открывающую скобку
        Node<E> current = _head; // Начинаем с первого узла в связанном списке
        if (current != null) { // Если первый элемент существует
            sb.append(current.data); // Добавляем его данные в строку
            current = current.after; // Переходим к следующему элементу
        }
        // Проходим по остальным элементам в связанном списке
        while (current != null) {
            sb.append(", ").append(current.data); // Добавляем данные каждого элемента
            current = current.after; // Переходим к следующему элементу
        }
        return sb.append("]").toString(); // Закрываем строку и возвращаем результат
    }

    // Конструктор по умолчанию, инициализирует хеш-таблицу с дефолтным размером
    public MyLinkedHashSet() {
        this(defaultSize);
    }

    // Конструктор, принимающий размер хеш-таблицы
    public MyLinkedHashSet(int capacity) {
        _items = new Node[capacity]; // Инициализация хеш-таблицы заданным размером
    }

    // Метод для вычисления хеш-индекса для элемента
    int GetHash(Object value) {
        return (value.hashCode() & 0x7FFFFFFF) % _items.length; // Вычисляем хеш-индекс с помощью хеш-кода элемента
    }

    // Метод для добавления узла в связанный список
    void AddNode(Node<E> newNode) {
        if (_head == null) { // Если список пуст
            _head = newNode; // Новый элемент становится первым
        } else { // Если список не пуст
            _tail.after = newNode; // Новый элемент добавляется в конец списка
            newNode.previous = _tail; // Устанавливаем ссылку на предыдущий элемент
        }
        _tail = newNode; // Новый элемент теперь последний в списке
    }

    // Метод для удаления узла из связанного списка
    void RemoveNode(Node<E> newNode) {
        if (newNode.after != null) // Если после узла есть следующий элемент
            newNode.after.previous = newNode.previous; // Ссылаемся на предыдущий элемент
        else
            _tail = newNode.previous; // Если это последний элемент, обновляем указатель на конец списка

        if (newNode.previous != null) // Если перед узлом есть предыдущий элемент
            newNode.previous.after = newNode.after; // Ссылаемся на следующий элемент
        else
            _head = newNode.after; // Если это первый элемент, обновляем указатель на начало списка
    }

    // Метод для получения текущего размера множества
    @Override
    public int size() {
        return _count; // Возвращаем количество элементов
    }

    // Метод для проверки, пусто ли множество
    @Override
    public boolean isEmpty() {
        return _count == 0; // Если количество элементов равно 0, возвращаем true
    }

    // Метод для очистки множества (удаление всех элементов)
    @Override
    public void clear() {
        for (int i = 0; i < _items.length; i++) // Проходим по всем индексам хеш-таблицы
            _items[i] = null; // Очищаем все элементы
        _head = null; // Обнуляем указатели на начало и конец списка
        _tail = null;
        _count = 0; // Обнуляем счётчик
    }

    // Метод для проверки, содержится ли элемент в множестве
    @Override
    public boolean contains(Object o) {
        Node<E> current = _items[GetHash(o)]; // Получаем первый элемент из хеш-таблицы по хеш-индексу
        while (current != null) { // Ищем элемент в цепочке
            if (current.data.equals(o)) { // Если нашли элемент
                return true; // Возвращаем true
            }
            current = current.next; // Переходим к следующему элементу в цепочке
        }
        return false; // Если элемент не найден
    }

    // Метод для добавления элемента в множество
    @Override
    public boolean add(E e) {
        int index = GetHash(e); // Получаем хеш-индекс для нового элемента
        Node<E> current = _items[index]; // Получаем первый элемент в цепочке по этому индексу
        while (current != null) { // Ищем элемент в цепочке
            if (current.data.equals(e)) { // Если элемент уже существует
                return false; // Возвращаем false, так как добавление не требуется
            }
            current = current.next; // Переходим к следующему элементу
        }
        // Если элемент не найден, создаём новый узел для элемента
        Node<E> newNode = new Node<>(e);
        newNode.next = _items[index]; // Новый узел ссылается на текущий первый элемент в цепочке
        _items[index] = newNode; // Новый узел становится первым в цепочке
        AddNode(newNode); // Добавляем новый узел в связанный список
        _count++; // Увеличиваем счётчик элементов
        if (_count > _items.length * 0.75) { // Если количество элементов превышает 75% от ёмкости хеш-таблицы
            resize(); // Увеличиваем размер хеш-таблицы
        }
        return true; // Возвращаем true, так как элемент был успешно добавлен
    }

    // Метод для увеличения размера хеш-таблицы и перераспределения элементов
    void resize() {
        Node<E>[] newItems = new Node[_items.length * 2]; // Создаём новый массив с удвоенным размером
        for (int i = 0; i < _items.length; i++) { // Перебираем все элементы старой хеш-таблицы
            Node<E> current = _items[i]; // Получаем первый элемент для текущего индекса
            while (current != null) { // Перемещаем элементы в новую хеш-таблицу
                Node<E> next = current.next; // Сохраняем ссылку на следующий элемент
                int newIndex = current.data.hashCode() & 0x7FFFFFFF % newItems.length; // Перераспределяем хеш-индекс для новой хеш-таблицы
                current.next = newItems[newIndex]; // Добавляем узел в новый массив
                newItems[newIndex] = current; // Обновляем новый массив
                current = next; // Переходим к следующему элементу
            }
        }
        _items = newItems; // Переназначаем массив хеш-таблицы на новый
    }

    // Метод для удаления элемента из множества
    @Override
    public boolean remove(Object o) {
        int index = GetHash(o); // Получаем хеш-индекс для элемента
        Node<E> current = _items[index]; // Получаем первый элемент для данного индекса
        Node<E> previous = null; // Переменная для хранения предыдущего элемента
        while (current != null) { // Ищем элемент в цепочке
            if (current.data.equals(o)) { // Если нашли элемент
                RemoveNode(current); // Удаляем его из связанного списка
                if (previous == null) { // Если элемент первый в цепочке
                    _items[index] = current.next; // Сдвигаем цепочку
                } else {
                    previous.next = current.next; // Связываем предыдущий элемент с следующим
                }
                _count--; // Уменьшаем счётчик элементов
                return true; // Возвращаем true, так как элемент был удалён
            }
            previous = current; // Сохраняем текущий элемент как предыдущий
            current = current.next; // Переходим к следующему элементу
        }
        return false; // Если элемент не найден
    }

    // Метод для проверки, содержатся ли все элементы из коллекции в множестве
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object obj: c) { // Проходим по всем элементам коллекции
            if (!contains(obj)) // Если хотя бы один элемент не найден в множестве
                return false; // Возвращаем false
        }
        return true; // Если все элементы найдены, возвращаем true
    }

    // Метод для добавления всех элементов из коллекции в множество
    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false; // Флаг для отслеживания изменений
        for (E item : c) { // Проходим по всем элементам коллекции
            if (add(item)) { // Если элемент был добавлен
                modified = true; // Отмечаем, что изменения произошли
            }
        }
        return modified; // Возвращаем true, если хотя бы один элемент был добавлен
    }

    // Метод для сохранения в множестве только тех элементов, которые присутствуют в коллекции
    @Override
    public boolean retainAll(Collection<?> c) {
        if (c.isEmpty()) { // Если коллекция пустая
            this.clear(); // Очищаем множество
            return true; // Возвращаем true
        }
        boolean isModified = false; // Флаг для отслеживания изменений
        MyLinkedHashSet<E> tempSet = new MyLinkedHashSet<>(_items.length); // Временный набор для хранения оставшихся элементов
        Node<E> current = _head; // Начинаем с первого элемента в связанном списке
        while (current != null) { // Проходим по всем элементам в связанном списке
            if (c.contains(current.data)) { // Если элемент содержится в коллекции
                tempSet.add(current.data); // Добавляем его во временный набор
                isModified = true; // Отмечаем, что изменения произошли
            }
            current = current.after; // Переходим к следующему элементу
        }
        _items = tempSet._items; // Переносим элементы из временного набора в текущий
        _head = tempSet._head;
        _tail = tempSet._tail;
        _count = tempSet._count; // Обновляем счётчик элементов

        return isModified; // Возвращаем true, если были изменения
    }

    // Метод для удаления всех элементов из коллекции
    @Override
    public boolean removeAll(Collection<?> c) {
        boolean isModified = false; // Флаг для отслеживания изменений
        for (Object obj: c) { // Проходим по всем элементам коллекции
            if (remove(obj)) // Если элемент был удалён
                isModified = true; // Отмечаем изменения
        }
        return isModified; // Возвращаем true, если хотя бы один элемент был удалён
    }

    // Методы для работы с итераторами
    @Override
    public Iterator<E> iterator() {
        return null; // Не реализовано
    }

    @Override
    public Object[] toArray() {
        return new Object[0]; // Возвращаем пустой массив
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null; // Не реализовано
    }
}
