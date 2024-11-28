package by.it.group310902.shirukovaa.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

// Класс MyTreeSet реализует интерфейс Set, используя структуру дерева
public class MyTreeSet<E extends Comparable<E>> implements Set<E> {

    private E[] heap; // Массив для хранения элементов
    private int size = 0; // Текущий размер
    private static int capacity = 16; // Начальная емкость

    // Метод для восстановления структуры кучи
    void heapify(int size, int i) {
        int max = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < size && heap[left].compareTo(heap[max]) > 0)
            max = left;

        if (right < size && heap[right].compareTo(heap[max]) > 0)
            max = right;

        if (max != i) {
            E element = heap[i];
            heap[i] = heap[max];
            heap[max] = element;

            heapify(size, max); // Рекурсивный вызов для восстановления кучи
        }
    }

    // Метод сортировки элементов кучи
    void heapsort() {
        for (int i = (size / 2) - 1; i >= 0; i--)
            heapify(size, i);

        for (int i = size - 1; i >= 0; i--) {
            E element = heap[0];
            heap[0] = heap[i];
            heap[i] = element;

            heapify(i, 0); // Восстановление кучи
        }
    }

    // Конструктор для установки емкости
    MyTreeSet(int capacity) {
        heap = (E[]) new Comparable[capacity];
        this.capacity = capacity;
    }

    // Конструктор по умолчанию
    MyTreeSet() {
        this(capacity);
    }

    // Переопределение toString для отображения элементов
    public String toString() {
        StringBuilder strbldr = new StringBuilder();
        strbldr.append("[");
        for (int i = 0; i < size; i++)
            strbldr.append(heap[i] + ", ");

        int sbLength = strbldr.length();
        if (sbLength > 1)
            strbldr.delete(sbLength - 2, sbLength); // Удаление запятой

        strbldr.append("]");
        return strbldr.toString(); // Возвращение строки
    }

    @Override
    public int size() {
        return size; // Возвращение размера
    }

    @Override
    public void clear() {
        capacity = 16; // Сброс емкости
        size = 0; // Обнуление размера
        heap = (E[]) new Comparable[capacity]; // Новый массив
    }

    @Override
    public boolean isEmpty() {
        return size() == 0; // Проверка на пустоту
    }

    @Override
    public boolean add(E e) {
        if (e == null || contains(e))
            return false; // Проверка на null и дубликаты

        size++;
        if (size >= capacity) { // Увеличение емкости при необходимости
            E[] tempHeap = heap;
            heap = (E[]) new Comparable[capacity *= 2];
            for (int i = 0; i < size - 1; i++)
                heap[i] = tempHeap[i];
        }
        heap[size - 1] = e; // Добавление элемента
        if (size != 1)
            heapsort(); // Сортировка кучи

        return true; // Успешное добавление
    }

    @Override
    public boolean remove(Object o) {
        if (o == null)
            return false; // Проверка на null

        for (int i = 0; i < size; i++)
            if (heap[i].equals(o)) {
                for (int j = i; j < size - 1; j++) // Удаление элемента
                    heap[j] = heap[j + 1];
                size--;
                return true; // Успешное удаление
            }

        return false; // Элемент не найден
    }

    @Override
    public boolean contains(Object o) {
        if (o == null)
            return false; // Проверка на null

        for (int i = 0; i < size; i++)
            if (heap[i].equals(o))
                return true; // Элемент найден

        return false; // Элемент не найден
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c)
            if (!contains(o))
                return false; // Не все элементы найдены
        return true; // Все элементы найдены
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c.size() == 0)
            return false; // Пустая коллекция

        for (Object o : c) {
            if (o == null)
                return false; // Null не допускается
            add((E) o); // Добавление каждого элемента
        }
        return true; // Все элементы добавлены
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Boolean changed = false; // Флаг изменения
        int i = 0;
        while (i < size) {
            if (!c.contains(heap[i])) { // Если элемент не в коллекции
                remove(heap[i]); // Удаление элемента
                changed = true; // Установка флага
                continue;
            }
            i++;
        }
        return changed; // Возвращение изменения
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c.size() == 0)
            return false; // Пустая коллекция
        for (Object o : c) {
            if (o == null)
                return false; // Null не допускается
            remove(o); // Удаление элемента
        }
        return true; // Все элементы удалены
    }

    ///////////////////
    //// необязательные
    ///////////////////

    @Override
    public Iterator<E> iterator() {
        return null; // Метод не реализован
    }

    @Override
    public Object[] toArray() {
        return new Object[0]; // Метод не реализован
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null; // Метод не реализован
    }
}