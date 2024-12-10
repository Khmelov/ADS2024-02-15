package by.it.group351001.sosnovski.lesson09;

// Задание:
// Реализовать класс ListB, который будет аналогом стандартного списка без использования других классов стандартной библиотеки.
// Класс должен реализовывать интерфейс List<E> и включать в себя основные методы для работы со списком, такие как:
// - add(E e)
// - add(int index, E element)
// - remove(int index)
// - remove(Object o)
// - size()
// - get(int index)
// - set(int index, E element)
// - isEmpty()
// - clear()
// - indexOf(Object o)
// - lastIndexOf(Object o)
// - contains(Object o)
// Опционально реализовать методы для работы с коллекциями и другие служебные методы.

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListB<E> implements List<E> {

    // Массив для хранения элементов списка
    E[] elements;
    // Текущий индекс, указывающий на количество добавленных элементов
    int curInd = 0;
    // Исходный размер массива
    static int size = 8;

    // Конструктор по умолчанию, инициализирует массив с размером 8
    public ListB() {
        this(size);
    }

    // Конструктор, который принимает размер массива
    public ListB(int size) {
        elements = (E[]) new Object[size]; // Создаем массив элементов
    }

    // Переопределение метода toString для удобного отображения списка
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < curInd; i++) {
            sb.append(elements[i]);
            if (i < curInd - 1) {
                sb.append(", "); // Добавляем запятую между элементами
            }
        }
        sb.append(']');
        return sb.toString();
    }

    // Добавление элемента в конец списка
    @Override
    public boolean add(E e) {
        // Проверка, достигнут ли предел массива. Если да, то увеличиваем его вдвое.
        if (curInd == elements.length) {
            E[] tempElements = (E[]) new Object[elements.length * 2];
            System.arraycopy(elements, 0, tempElements, 0, elements.length);
            elements = tempElements;
        }

        elements[curInd] = e; // Добавляем новый элемент
        curInd++; // Увеличиваем текущий индекс
        return true;
    }

    // Удаление элемента по индексу
    @Override
    public E remove(int index) {
        // Проверяем корректность индекса
        if (index < 0 || index >= curInd) {
            return null;
        }

        E deletedElem = elements[index]; // Сохраняем удаляемый элемент
        // Сдвигаем все элементы после удаленного влево
        System.arraycopy(elements, index + 1, elements, index, curInd - index - 1);
        curInd--; // Уменьшаем размер списка
        return deletedElem; // Возвращаем удаленный элемент
    }

    // Метод возвращает текущее количество элементов в списке
    @Override
    public int size() {
        return curInd; // Возвращаем количество элементов в списке
    }

    // Вставка элемента на определенный индекс
    @Override
    public void add(int index, E element) {
        if (index < 0 || index > curInd) {
            return; // Проверка на допустимый индекс
        }

        // Если массив заполнен, увеличиваем его размер
        if (curInd == elements.length) {
            E[] tempElements = (E[]) new Object[elements.length * 2];
            System.arraycopy(elements, 0, tempElements, 0, elements.length);
            elements = tempElements;
        }

        // Сдвигаем элементы вправо от заданного индекса
        System.arraycopy(elements, index, elements, index + 1, curInd - index);
        elements[index] = element; // Вставляем элемент
        curInd++; // Увеличиваем количество элементов
    }

    // Удаление указанного элемента
    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < curInd; i++) {
            if (o.equals(elements[i])) { // Находим элемент
                // Сдвигаем элементы влево, начиная с индекса удаляемого элемента
                System.arraycopy(elements, i + 1, elements, i, curInd - i - 1);
                curInd--; // Уменьшаем размер списка
                return true; // Элемент удален
            }
        }
        return false; // Элемент не найден
    }

    // Замена элемента на определенном индексе
    @Override
    public E set(int index, E element) {
        // Проверяем корректность индекса
        if (index < 0 || index >= curInd) {
            return null;
        }

        E oldElem = elements[index]; // Сохраняем старый элемент
        elements[index] = element; // Заменяем его новым
        return oldElem; // Возвращаем старый элемент
    }

    // Проверка, пустой ли список
    @Override
    public boolean isEmpty() {
        return curInd == 0; // Возвращаем true, если в списке нет элементов
    }

    // Очистка списка
    @Override
    public void clear() {
        elements = (E[]) new Object[size]; // Инициализируем новый массив
        curInd = 0; // Сбрасываем счетчик элементов
    }

    // Поиск индекса первого вхождения элемента
    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < curInd; i++) {
            if (o.equals(elements[i])) {
                return i; // Возвращаем индекс найденного элемента
            }
        }
        return -1; // Элемент не найден
    }

    // Получение элемента по индексу
    @Override
    public E get(int index) {
        // Проверка на допустимость индекса
        if (index < 0 || index >= curInd) {
            return null;
        }
        return elements[index]; // Возвращаем элемент
    }

    // Проверка, содержится ли указанный элемент в списке
    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0; // Возвращаем true, если элемент найден
    }

    // Поиск индекса последнего вхождения элемента
    @Override
    public int lastIndexOf(Object o) {
        for (int i = curInd - 1; i >= 0; i--) {
            if (o.equals(elements[i])) {
                return i; // Возвращаем индекс последнего найденного элемента
            }
        }
        return -1; // Элемент не найден
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Опциональные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    @Override
    public boolean containsAll(Collection<?> c) {
        return false; // Метод-заглушка
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false; // Метод-заглушка
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false; // Метод-заглушка
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false; // Метод-заглушка
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false; // Метод-заглушка
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null; // Метод-заглушка
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null; // Метод-заглушка
    }

    @Override
    public ListIterator<E> listIterator() {
        return null; // Метод-заглушка
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null; // Метод-заглушка
    }

    @Override
    public Object[] toArray() {
        return new Object[0]; // Метод-заглушка
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    ////////        Эти методы имплементировать необязательно    ////////////
    ////////        но они будут нужны для корректной отладки    ////////////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    @Override
    public Iterator<E> iterator() {
        return null; // Метод-заглушка
    }
}
