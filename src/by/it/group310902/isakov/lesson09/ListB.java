package by.it.group310902.isakov.lesson09;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

// Класс ListB реализует интерфейс List<E>, предоставляя основные методы работы с коллекцией
public class ListB<E> implements List<E> {

    // Массив для хранения элементов списка
    E[] elements;
    // Текущий индекс, указывающий на первый свободный слот
    int curInd = 0;
    // Начальный размер массива
    static int size = 8;

    // Конструктор без параметров, использует размер по умолчанию
    public ListB() {
        this(size);
    }


    // Конструктор, принимающий начальный размер массива
    public ListB(int size) {
        elements = (E[]) new Object[size];
    }

    // Переопределение метода toString() для вывода списка в виде строки
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < curInd; i++) {
            sb.append(elements[i]);
            if (i < curInd - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
        return sb.toString();
    }

    // Добавление элемента в список
    @Override
    public boolean add(E e) {
        // Увеличиваем размер массива, если он заполнен
        if (curInd == elements.length) {
            E[] tempElements = (E[]) new Object[elements.length * 2];
            for (int i = 0; i < elements.length; i++) {
                tempElements[i] = elements[i];
            }
            elements = tempElements;
        }
        // Добавляем элемент в массив
        elements[curInd++] = e;
        return true;
    }

    // Удаление элемента по индексу
    @Override
    public E remove(int index) {
        if (index < 0 || index >= curInd) {
            return null; // Если индекс некорректен
        }
        E deletedElem = elements[index];
        // Сдвигаем элементы влево
        for (int i = index; i < curInd - 1; i++) {
            elements[i] = elements[i + 1];
        }
        elements[--curInd] = null; // Очищаем последний элемент
        return deletedElem;
    }

    // Возвращает текущий размер списка
    @Override
    public int size() {
        return curInd;
    }

    // Добавление элемента по индексу
    @Override
    public void add(int index, E element) {
        if (index < 0 || index > curInd) {
            throw new IndexOutOfBoundsException();
        }
        // Увеличиваем размер массива, если нужно
        if (curInd == elements.length) {
            E[] tempElements = (E[]) new Object[elements.length * 2];
            for (int i = 0; i < elements.length; i++) {
                tempElements[i] = elements[i];
            }
            elements = tempElements;
        }
        // Сдвигаем элементы вправо
        for (int i = curInd; i > index; i--) {
            elements[i] = elements[i - 1];
        }
        elements[index] = element;
        curInd++;
    }

    // Удаление элемента по объекту
    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index == -1) return false; // Если элемент не найден
        remove(index);
        return true;
    }

    // Замена элемента по индексу
    @Override
    public E set(int index, E element) {
        if (index < 0 || index >= curInd) {
            throw new IndexOutOfBoundsException();
        }
        E oldElem = elements[index];
        elements[index] = element;
        return oldElem;
    }

    // Проверка на пустоту
    @Override
    public boolean isEmpty() {
        return curInd == 0;
    }

    // Очистка списка
    @Override
    public void clear() {
        elements = (E[]) new Object[size]; // Создаем новый массив
        curInd = 0; // Сбрасываем индекс
    }

    // Поиск индекса первого вхождения элемента
    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < curInd; i++) {
            if (o.equals(elements[i])) {
                return i;
            }
        }
        return -1;
    }

    // Получение элемента по индексу
    @Override
    public E get(int index) {
        if (index < 0 || index >= curInd) {
            throw new IndexOutOfBoundsException();
        }
        return elements[index];
    }

    // Проверка на наличие элемента
    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    // Поиск индекса последнего вхождения элемента
    @Override
    public int lastIndexOf(Object o) {
        for (int i = curInd - 1; i >= 0; i--) {
            if (o.equals(elements[i])) {
                return i;
            }
        }
        return -1;
    }

    // Реализация дополнительных методов интерфейса Collection
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object elem : c) {
            if (!contains(elem)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E e : c) {
            add(e);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (index < 0 || index > curInd) {
            throw new IndexOutOfBoundsException();
        }
        for (E e : c) {
            add(index++, e);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object elem : c) {
            while (contains(elem)) {
                remove(elem);
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        for (int i = 0; i < curInd; i++) {
            if (!c.contains(elements[i])) {
                remove(elements[i]);
                i--; // Компенсируем смещение индекса
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > curInd || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException();
        }
        ListB<E> sublist = new ListB<>(toIndex - fromIndex);
        for (int i = fromIndex; i < toIndex; i++) {
            sublist.add(elements[i]);
        }
        return sublist;
    }

    // Необязательные для реализации методы (выбрасывают исключение)
    @Override
    public ListIterator<E> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException();
    }

    // Преобразование в массив
    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < curInd) {
            return (T[]) java.util.Arrays.copyOf(elements, curInd, a.getClass());
        }
        System.arraycopy(elements, 0, a, 0, curInd);
        if (a.length > curInd) {
            a[curInd] = null;
        }
        return a;
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[curInd];
        System.arraycopy(elements, 0, result, 0, curInd);
        return result;
    }

    // Реализация итератора
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int cursor = 0;

            @Override
            public boolean hasNext() {
                return cursor < curInd;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new java.util.NoSuchElementException();
                }
                return elements[cursor++];
            }
        };
    }
}
