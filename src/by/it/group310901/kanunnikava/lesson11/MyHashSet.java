package by.it.group310901.kanunnikava.lesson11;

import java.util.*;

/*Создайте class MyHashSet<E>, который реализует интерфейс Set<E>
    и работает на основе массива с адресацией по хеш-коду
            и односвязным списком для элементов с коллизиями
            БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ*/

public class MyHashSet<T> implements Set<T> {

    private int capacity = 16; // Начальная емкость хэш-таблицы

    private Node<T>[] arr; // Массив узлов (Node), представляющий хэш-таблицу
    private int size; // Переменная для отслеживания текущего размера множества
    private class Node<T> {

        T elem; // Элемент, хранимый в узле
        Node<T> next; // Ссылка на следующий узел в цепочке
        public Node(T elem, Node<T> next) { // Конструктор класса Node
            this.elem = elem; // Инициализация элемента
            this.next = next; // Инициализация ссылки на следующий узел
        }

    }

    public MyHashSet() {
        arr = (Node<T>[]) new Node[capacity];
    } // Инициализация массива узлов начальной емкости

    @Override
    public String toString() { // Переопределение метода для получения строкового представления множества
        if (isEmpty()) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder().append("["); // Создание нового объекта StringBuilder и добавление открывающей скобки
        for (Node<T> element : arr) {  // Цикл по всем элементам массива узлов
            for (Node<T> curr = element; curr != null; curr = curr.next) { // Цикл по цепочке узлов
                sb.append(curr.elem).append(", "); // Добавление элемента и запятой с пробелом
            }
        }
        return sb.delete(sb.length() - 2, sb.length()).append("]").toString();
    }

    @Override
    public int size() {
        return size;
    } // Переопределение метода для получения текущего размера множества

    @Override
    public void clear() { // Переопределение метода для очистки множества
        size = 0;
        for (int i = 0; i < capacity; i++) { // Цикл по всем индексам массива узлов
            arr[i] = null;
        }
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    } // Переопределение метода для проверки пустоты множества

    @Override
    public boolean add(T t) { // Переопределение метода для добавления элемента в множество

        if (contains(t)) { // Если множество уже содержит элемент
            return false;
        }

        size++;
        int index = indexByHash(t); // Вычисление индекса на основе хэш-кода элемента
        Node<T> elem = arr[index]; // Получение узла по индексу
        if (elem == null) {
            arr[index] = new Node<>(t, null); // Создание нового узла и добавление его в массив
            return true;
        }
        while(elem.next != null){ // Поиск последнего узла в цепочке
            elem = elem.next; // Переход к следующему узлу
        }
        elem.next = new Node<>(t, null); // Добавление нового узла в конец цепочки
        return true;
    }

    @Override
    public boolean remove(Object o) { // Переопределение метода для удаления элемента из множества

        int index = indexByHash(o); // Вычисление индекса на основе хэш-кода элемента
        Node<T> elem = arr[index]; // Получение узла по индексу

        if (elem == null) { // Если узел пустой
            return false;
        }

        if (o.equals(elem.elem)) { // Если элемент совпадает с элементом узла
            size--;
            arr[index] = elem.next; // Удаление узла из массива
            return true;
        }

        for (Node<T> curr = elem; curr.next != null; curr = curr.next) { // Поиск узла в цепочке
            if (curr.next.elem.equals(o)) { // Если элемент найден
                Node<T> temp = curr.next; // Сохранение ссылки на следующий узел
                curr.next = temp.next; // Удаление узла из цепочки
                size--;
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean contains(Object o) { // Переопределение метода для проверки наличия элемента в множестве
        Node<T> elem = arr[indexByHash(o)]; // Получение узла по индексу
        if (elem == null) {
            return false;
        }

        for (Node<T> curr = elem; curr != null; curr = curr.next) { // Поиск узла в цепочке
            if (curr.elem.equals(o)) { // Если элемент найден
                return true;
            }
        }

        return false;
    }

    private int indexByHash(Object o) {
        return (o == null) ? 0 : o.hashCode() % (arr.length - 1);
    } // Приватный метод для вычисления индекса на основе хэш-кода элемента

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
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