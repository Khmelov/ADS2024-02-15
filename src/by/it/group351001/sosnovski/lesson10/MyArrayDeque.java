package by.it.group351001.sosnovski.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

/**
 * Данный класс реализует Deque с использованием динамического массива в качестве основной структуры данных.
 * Он поддерживает операции добавления, удаления и доступа к элементам с обоих концов дека.
 * Массив автоматически увеличивается по мере необходимости для размещения большего количества элементов.
 *
 * @param <E> тип элементов, хранимых в этом деке
 */
public class MyArrayDeque<E> implements Deque<E> {
    // Начальный размер массива
    final int INITIAL_SIZE = 8;
    // Индексы для указания начала и конца дека
    int head;
    int tail;
    // Массив для хранения элементов дека
    E[] array;
    // Текущее количество элементов в деке
    int size;

    // Конструктор по умолчанию, инициализирует массив и устанавливает начальные индексы
    public MyArrayDeque() {
        array = (E[]) new Object[INITIAL_SIZE];
        head = INITIAL_SIZE / 2; // Начало массива
        tail = head - 1;         // Конец массива
        size = 0;
    }

    // Метод для увеличения размера массива при нехватке места
    private void Expand(int tmpSize) {
        E[] tempArray = (E[]) new Object[tmpSize]; // Новый массив с большим размером
        int k = (tmpSize - size) / 2; // Расчет нового положения head

        // Копируем элементы в новый массив, начиная с индекса k
        for (int i = 0; i < size; i++) {
            tempArray[i + k] = array[head + i];
        }

        head = k; // Обновляем head
        tail = k + size - 1; // Обновляем tail
        array = tempArray; // Устанавливаем новый массив
    }

    // Переопределение метода toString для удобного отображения содержимого дека
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');

        // Добавляем элементы дека в строку
        for (int i = 0; i < size; i++) {
            sb.append(array[head + i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }

        sb.append(']');
        return sb.toString();
    }

    // Метод возвращает текущий размер дека
    @Override
    public int size() {
        return size;
    }

    // Метод добавляет элемент в конец дека
    @Override
    public boolean add(E e) {
        addLast(e); // Используем метод addLast для добавления в конец
        return true;
    }

    // Метод добавляет элемент в начало дека
    @Override
    public void addFirst(E e) {
        // Если начало дека достигло границы массива, увеличиваем размер массива
        if (head == 0) {
            Expand(array.length * 2);
        }

        head--; // Сдвигаем head влево
        size++; // Увеличиваем размер дека
        array[head] = e; // Добавляем элемент в начало
    }

    // Метод добавляет элемент в конец дека
    @Override
    public void addLast(E e) {
        // Если конец дека достиг границы массива, увеличиваем размер массива
        if (tail == array.length - 1) {
            Expand(array.length * 2);
        }

        tail++; // Сдвигаем tail вправо
        size++; // Увеличиваем размер дека
        array[tail] = e; // Добавляем элемент в конец
    }

    // Метод возвращает первый элемент дека (без удаления)
    @Override
    public E element() {
        return getFirst(); // Возвращаем первый элемент
    }

    // Метод возвращает первый элемент дека (без удаления)
    @Override
    public E getFirst() {
        if (size == 0) {
            return null; // Если дек пуст, возвращаем null
        }

        return array[head]; // Возвращаем элемент по индексу head
    }

    // Метод возвращает последний элемент дека (без удаления)
    @Override
    public E getLast() {
        if (size == 0) {
            return null; // Если дек пуст, возвращаем null
        }

        return array[tail]; // Возвращаем элемент по индексу tail
    }

    // Метод удаляет и возвращает первый элемент дека
    @Override
    public E poll() {
        return pollFirst(); // Используем метод pollFirst для удаления первого элемента
    }

    // Метод удаляет и возвращает первый элемент дека
    @Override
    public E pollFirst() {
        if (size == 0) {
            return null; // Если дек пуст, возвращаем null
        }

        head++; // Сдвигаем head вправо
        size--; // Уменьшаем размер дека
        return array[head - 1]; // Возвращаем удаленный элемент
    }

    // Метод удаляет и возвращает последний элемент дека
    @Override
    public E pollLast() {
        if (size == 0) {
            return null; // Если дек пуст, возвращаем null
        }

        tail--; // Сдвигаем tail влево
        size--; // Уменьшаем размер дека
        return array[tail + 1]; // Возвращаем удаленный элемент
    }

    // Далее идут методы-заглушки, которые можно дополнительно реализовать, но они не обязательны для базовой реализации

    @Override
    public boolean offerFirst(E e) {
        return false;
    }

    @Override
    public boolean offerLast(E e) {
        return false;
    }

    @Override
    public E removeFirst() {
        return null;
    }

    @Override
    public E removeLast() {
        return null;
    }

    @Override
    public E peekFirst() {
        return null;
    }

    @Override
    public E peekLast() {
        return null;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean offer(E e) {
        return false;
    }

    @Override
    public E remove() {
        return null;
    }

    @Override
    public E peek() {
        return null;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public void push(E e) {

    }

    @Override
    public E pop() {
        return null;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

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
    public Iterator<E> descendingIterator() {
        return null;
    }
}
