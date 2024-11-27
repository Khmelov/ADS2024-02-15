package by.it.group351005.gaiduk.lesson10;

/*
 * MyPriorityQueue - это класс, реализующий приоритетную очередь на основе структуры данных "куча".
 * Очередь организована в виде массива, где элементы упорядочены таким образом,
 * что родитель всегда меньше своих детей (для мин-кучи).
 * Класс поддерживает основные операции для очереди, такие как добавление, удаление и проверка наличия элементов.
 * Также реализованы методы для работы с коллекциями, например, добавление и удаление всех элементов из другой коллекции.
 *
 * Основные методы:
 * - add(E e): добавляет элемент в очередь.
 * - remove(): удаляет и возвращает минимальный элемент из очереди.
 * - peek(): возвращает минимальный элемент без удаления.
 * - size(): возвращает текущее количество элементов в очереди.
 * - clear(): очищает очередь.
 */
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Queue;

public class MyPriorityQueue<E extends Comparable<E>> implements Queue<E> {

    final int defaultSize = 8; // Начальный размер массива
    E[] _elements; // Массив для хранения элементов очереди
    int size; // Текущий размер очереди

    // Конструктор по умолчанию
    public MyPriorityQueue() {
        _elements = (E[]) new Comparable[defaultSize]; // Инициализируем массив с заданным размером
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(); // Строитель строк для представления очереди
        sb.append("[");
        for (int i = 0; i < size; i++) {
            sb.append(_elements[i]); // Добавляем элемент в строку
            if (i < size - 1) {
                sb.append(", "); // Добавляем запятую между элементами
            }
        }
        sb.append("]"); // Закрываем квадратные скобки
        return sb.toString(); // Возвращаем строковое представление очереди
    }

    // Вспомогательный метод для восстановления свойства кучи (вверх)
    void heapifyUp(int index) {
        int parent = (index - 1) / 2; // Находим индекс родителя

        // Если текущий элемент меньше родителя, меняем их местами
        if (parent >= 0 && _elements[index].compareTo(_elements[parent]) < 0) {
            E temp = _elements[index];
            _elements[index] = _elements[parent];
            _elements[parent] = temp;
            heapifyUp(parent); // Рекурсивно восстанавливаем кучу вверх
        }
    }

    // Вспомогательный метод для восстановления свойства кучи (вниз)
    void heapifyDown(int index) {
        int left = 2 * index + 1; // Индекс левого ребенка
        int right = 2 * index + 2; // Индекс правого ребенка
        int largest = left; // Предполагаем, что левый ребенок - наибольший

        // Проверяем, является ли правый ребенок меньше наибольшего элемента
        if (right < size && _elements[right].compareTo(_elements[largest]) < 0)
            largest = right; // Если да, обновляем наибольший

        // Если наибольший элемент меньше текущего, меняем их местами
        if (largest < size && _elements[largest].compareTo(_elements[index]) < 0) {
            E temp = _elements[index];
            _elements[index] = _elements[largest];
            _elements[largest] = temp;
            heapifyDown(largest); // Рекурсивно восстанавливаем кучу вниз
        }
    }

    @Override
    public int size() {
        return size; // Возвращаем текущее количество элементов
    }

    @Override
    public boolean isEmpty() {
        return size == 0; // Проверяем, пуста ли очередь
    }

    @Override
    public boolean contains(Object o) {
        // Проверяем наличие элемента в очереди
        for (int i = 0; i < size; i++) {
            if (_elements[i].equals(o)) {
                return true; // Если элемент найден, возвращаем true
            }
        }
        return false; // Если элемент не найден, возвращаем false
    }

    @Override
    public boolean add(E e) {
        // Проверяем, достаточно ли места в массиве
        if (size == _elements.length) {
            resize(); // Если нет, увеличиваем размер массива
        }
        _elements[size++] = e; // Добавляем элемент в конец массива
        heapifyUp(size - 1); // Восстанавливаем свойство кучи
        return true; // Успешное добавление
    }

    // Метод для изменения размера массива
    void resize() {
        int newCapacity = _elements.length * 2; // Увеличиваем размер массива в два раза
        E[] newItems = (E[]) new Comparable[newCapacity]; // Создаем новый массив
        System.arraycopy(_elements, 0, newItems, 0, size); // Копируем элементы из старого массива
        _elements = newItems; // Устанавливаем новый массив
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        // Проверяем наличие всех элементов из коллекции
        for (Object item : c) {
            if (!contains(item)) {
                return false; // Если хотя бы один элемент не найден, возвращаем false
            }
        }
        return true; // Если все элементы найдены, возвращаем true
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false; // Флаг для отслеживания изменений
        for (E item : c) {
            if (add(item)) {
                modified = true; // Если элемент добавлен, устанавливаем флаг
            }
        }
        return modified; // Возвращаем true, если хотя бы один элемент добавлен
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false; // Флаг для отслеживания изменений
        for (Object item : c) {
            if (remove(item)) {
                modified = true; // Если элемент удален, устанавливаем флаг
            }
        }
        return modified; // Возвращаем true, если хотя бы один элемент удален
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false; // Флаг для отслеживания изменений
        for (int i = size - 1; i >= 0; i--) {
            // Если элемент не содержится в коллекции, удаляем его
            if (!c.contains(_elements[i])) {
                remove(_elements[i]); // Удаляем элемент
                modified = true; // Устанавливаем флаг
            }
        }
        return modified; // Возвращаем true, если хотя бы один элемент удален
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            // Ищем элемент для удаления
            if (Objects.equals(o, _elements[i])) {
                // Меняем текущий элемент с последним в массиве
                _elements[i] = _elements[--size];
                heapifyDown(i); // Восстанавливаем свойство кучи
                return true; // Успешное удаление
            }
        }
        return false; // Если элемент не найден, возвращаем false
    }

    @Override
    public boolean offer(E e) {
        return add(e); // Используем метод add для добавления элемента
    }

    @Override
    public E remove() {
        if (isEmpty()) {
            throw new IllegalArgumentException("PriorityQueue is empty"); // Проверяем, пуста ли очередь
        }
        E root = _elements[0]; // Сохраняем корневой элемент (минимальный)
        _elements[0] = _elements[--size]; // Перемещаем последний элемент на место корня
        heapifyDown(0); // Восстанавливаем свойство кучи
        return root; // Возвращаем минимальный элемент
    }

    @Override
    public E poll() {
        if (isEmpty()) {
            return null; // Если очередь пуста, возвращаем null
        }
        return remove(); // Используем метод remove для удаления и получения элемента
    }

    @Override
    public E element() {
        if (size == 0) {
            return null; // Если очередь пуста, возвращаем null
        }
        return _elements[0]; // Возвращаем корневой элемент (минимальный) без удаления
    }

    @Override
    public E peek() {
        return element(); // Используем метод element для получения минимального элемента
    }

    @Override
    public void clear() {
        size = 0; // Очищаем очередь, устанавливая размер в 0
    }

    @Override
    public Iterator<E> iterator() {
        return null; // Метод не реализован, возвращаем null
    }

    @Override
    public Object[] toArray() {
        return new Object[0]; // Метод не реализован, возвращаем пустой массив
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null; // Метод не реализован, возвращаем null
    }
}
