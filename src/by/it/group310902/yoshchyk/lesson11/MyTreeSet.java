package by.it.group310902.yoshchyk.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

// Обеспечивает базовый способ хранения элементов и бинарного поиска (аналогично TreeSet в стандартной библиотеке Java).
// Работает на основе отсортированного массива.
public class MyTreeSet<E> implements Set<E> {

    private E[] elements; // Массив для хранения элементов.
    private int size; // Текущее количество элементов в наборе.

    // Конструктор, инициализирующий набор с начальным размером.
    MyTreeSet() {
        size = 0; // Инициализация размера.
        elements = (E[]) new Comparable[5]; // Создание массива с начальным размером.
    }

    // Двоичный поиск для нахождения индекса элемента.
    // Если элемент найден - возвращает индекс, если нет - возвращает отрицательное значение,
    // указывающее, куда следует вставить элемент для поддержания порядка.
    private int BinSearch(int Left, int Right, Object Key) {
        while (Left <= Right) {
            int middle = (Left + Right) / 2; // Нахождение среднего индекса.
            Comparable midVal = (Comparable) elements[middle]; // Получение значения среднего элемента.
            int result = midVal.compareTo(Key); // Сравнение среднего элемента с искомым.

            if (result < 0) // Если искомый элемент больше среднего,
                Left = middle + 1; // Перемещаем левую границу вправо.
            else if (result > 0) // Если искомый элемент меньше среднего,
                Right = middle - 1; // Перемещаем правую границу влево.
            else
                return middle; // Элемент найден, возвращаем его индекс.
        }
        return -1 * Left - 1; // Элемент не найден, возвращаем место для вставки.
    }

    @Override
    // Возвращает строковое представление набора.
    public String toString() {
        StringBuilder sb = new StringBuilder("["); // Строковый строитель для представления набора.
        String delimiter = ""; // Разделитель для элементов.
        for (int i = 0; i < size; i++) { // Проход по элементам.
            sb.append(delimiter).append(elements[i]); // Добавление элемента.
            delimiter = ", "; // Установка разделителя на запятую.
        }
        sb.append("]");
        return sb.toString(); // Возвращает строковое представление набора.
    }

    @Override
    public int size() {
        return size; // Возвращает текущее количество элементов в наборе.
    }

    // Проверяет, пуст ли набор.
    @Override
    public boolean isEmpty() {
        return (size == 0); // Возвращает true, если набор пуст.
    }

    @Override
    public boolean contains(Object o) {
        return (BinSearch(0, size - 1, o) >= 0); // Проверяет наличие элемента в наборе с помощью бинарного поиска.
    }

    @Override
    public Iterator<E> iterator() {
        return null; // Не реализовано.
    }

    @Override
    public Object[] toArray() {
        return new Object[0]; // Не реализовано.
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null; // Не реализовано.
    }

    // Добавляет новый элемент в набор, сохраняя при этом порядок сортировки.
    // Выполняет бинарный поиск для проверки. Не допускает дублирования элементов.
    @Override
    public boolean add(E e) {
        if (size == elements.length) { // Если массив заполнен,
            // Увеличиваем размер массива.
            E[] newArr = (E[]) new Object[(size * 3) / 2 + 1]; // Новый массив с увеличенным размером.
            System.arraycopy(elements, 0, newArr, 0, size); // Копируем старые элементы в новый массив.
            elements = newArr; // Обновляем ссылку на массив.
        }
        int index; // Индекс для хранения позиции элемента.
        if (size == 0) { // Если массив пуст,
            index = -1; // устанавливаем индекс как -1.
        } else {
            index = BinSearch(0, size - 1, e); // Выполняем бинарный поиск элемента.
        }
        if (index >= 0) { // Если элемент уже существует,
            return false; // возвращаем false (дубликат не добавляется).
        } else {
            index *= -1; // Преобразуем индекс для вставки.
            index--; // Уменьшаем индекс на 1 для правильной вставки.
        }

        // Сдвигаем элементы, чтобы освободить место для нового элемента.
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = e; // Вставляем новый элемент.
        size++; // Увеличиваем счетчик элементов.
        return true; // Элемент успешно добавлен.
    }

    @Override
    public boolean remove(Object o) {
        int index = BinSearch(0, size - 1, o); // Поиск элемента для удаления.
        if (index < 0) { // Если элемент не найден,
            return false; // возвращаем false.
        } else {
            // Сдвигаем элементы, чтобы удалить указанный элемент.
            System.arraycopy(elements, index + 1, elements, index, size - index - 1);
            size--; // Уменьшаем счетчик элементов.
            elements[size] = null; // Освобождаем ссылку на удалённый элемент.
        }
        return true; // Элемент успешно удалён.
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        // Проверяет, содержатся ли все элементы коллекции в наборе.
        for (Object it : c) {
            if (!contains(it)) { // Если хотя бы один элемент не найден,
                return false; // возвращаем false.
            }
        }
        return true; // Все элементы найдены.
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        int prevsize = size; // Сохраняем предыдущий размер.
        for (Object it : c) { // Проходим по элементам коллекции.
            add((E) it); // Добавляем каждый элемент в набор.
        }
        return (prevsize != size); // Возвращает true, если размер изменился.
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        int prevsize = size; // Сохраняем предыдущий размер.
        for (int i = size - 1; i > -1; i--) { // Проходим по элементам в обратном порядке.
            if (!c.contains(elements[i])) { // Если элемент не содержится в коллекции,
                remove(elements[i]); // удаляем его.
            }
        }
        return (prevsize != size); // Возвращает true, если размер изменился.
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        int prevsize = size; // Сохраняем предыдущий размер.
        for (Object it : c) { // Проходим по элементам коллекции.
            if (contains(it)) { // Если элемент найден,
                remove(it); // удаляем его.
            }
        }
        return (prevsize != size); // Возвращает true, если размер изменился.
    }

    @Override
    public void clear() {
        // Очистка набора.
        for (int i = 0; i < size; i++) {
            elements[i] = null; // Освобождаем ссылки на элементы.
        }
        size = 0; // Сбрасываем размер.
    }
}