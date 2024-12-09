// В данном задании необходимо реализовать аналог списка (List) без использования
// других классов из стандартной библиотеки. Требуется реализовать основные методы интерфейса List.
// Основное внимание будет уделено методам добавления элементов, удаления по индексу и
// вычисления размера списка. Код должен работать с произвольными объектами.

package by.it.group351001.sosnovski.lesson09;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

// Класс ListA реализует интерфейс List с параметром типа E (обобщенный тип).
public class ListA<E> implements List<E> {

    // Элементы будут храниться в массиве. В начале список инициализируется с фиксированной длиной.
    E[] elements;

    // curInd - текущий индекс, показывающий, сколько элементов добавлено в список.
    int curInd = 0;

    // size - начальный размер массива.
    static int size = 8;

    // Конструктор по умолчанию. Инициализирует массив с начальным размером.
    public ListA() {
        this(size); // Вызывает другой конструктор с параметром размера.
    }

    // Конструктор с указанием размера. Создает массив для хранения элементов.
    public ListA(int size) {
        elements = (E[]) new Object[size]; // Создание массива с типом Object и приведение его к типу E.
    }

    // Метод toString() возвращает строковое представление списка в формате [elem1, elem2, ...].
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('['); // Начинаем строку с открывающей скобки.
        for (int i = 0; i < curInd; i++) {
            sb.append(elements[i]); // Добавляем каждый элемент в строку.

            if (i < curInd - 1) {
                sb.append(", "); // Добавляем запятую и пробел после каждого элемента, кроме последнего.
            }
        }
        sb.append(']'); // Заканчиваем строку закрывающей скобкой.
        return sb.toString(); // Возвращаем строковое представление списка.
    }

    // Метод add(E e) добавляет элемент в список. Возвращает true при успешном добавлении.
    @Override
    public boolean add(E e) {
        // Проверяем, не переполнен ли массив. Если да, то увеличиваем его размер
        if (curInd == elements.length) {
            E[] tempElements = (E[]) new Object[elements.length * 2]; // Создаем новый массив большего размера.

            // Копируем элементы из старого массива в новый.
            for (int i = 0; i < elements.length; i++) {
                tempElements[i] = elements[i];
            }

            elements = tempElements; // Присваиваем новый массив.
        }

        elements[curInd] = e; // Добавляем элемент в массив.
        curInd++; // Увеличиваем текущий индекс.
        return true; // Возвращаем true.
    }

    // Метод remove(int index) удаляет элемент по индексу и возвращает его.
    @Override
    public E remove(int index) {
        // Проверяем, что индекс находится в допустимом диапазоне.
        if (index < 0 || index >= curInd) {
            return null; // Если индекс неверный, возвращаем null.
        }

        E deletedElem = elements[index]; // Сохраняем элемент, который нужно удалить.
        // Сдвигаем все элементы после удаленного влево.
        for (int i = index; i < curInd - 1; i++) {
            elements[i] = elements[i + 1];
        }

        curInd--; // Уменьшаем текущий индекс.
        return deletedElem; // Возвращаем удаленный элемент.
    }

    // Метод size() возвращает количество элементов в списке.
    @Override
    public int size() {
        return curInd; // Текущий индекс равен количеству элементов.
    }

    // Необязательные к реализации методы. Они могут быть реализованы позже или оставлены пустыми.
    @Override
    public void add(int index, E element) {
        // Пустая реализация. Метод добавления элемента по индексу.
    }

    @Override
    public boolean remove(Object o) {
        return false; // Пустая реализация. Метод удаления элемента по значению.
    }

    @Override
    public E set(int index, E element) {
        return null; // Пустая реализация. Метод замены элемента по индексу.
    }

    @Override
    public boolean isEmpty() {
        return false; // Пустая реализация. Метод проверки, пуст ли список.
    }

    @Override
    public void clear() {
        // Пустая реализация. Метод очистки списка.
    }

    @Override
    public int indexOf(Object o) {
        return 0; // Пустая реализация. Метод поиска индекса элемента.
    }

    @Override
    public E get(int index) {
        return null; // Пустая реализация. Метод получения элемента по индексу.
    }

    @Override
    public boolean contains(Object o) {
        return false; // Пустая реализация. Метод проверки, содержит ли список элемент.
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0; // Пустая реализация. Метод поиска последнего индекса элемента.
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false; // Пустая реализация. Метод проверки, содержит ли список все элементы коллекции.
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false; // Пустая реализация. Метод добавления всех элементов коллекции.
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false; // Пустая реализация. Метод добавления всех элементов коллекции с индекса.
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false; // Пустая реализация. Метод удаления всех элементов коллекции.
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false; // Пустая реализация. Метод сохранения только элементов из коллекции.
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null; // Пустая реализация. Метод получения подсписка.
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null; // Пустая реализация. Метод получения итератора списка с индекса.
    }

    @Override
    public ListIterator<E> listIterator() {
        return null; // Пустая реализация. Метод получения итератора списка.
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null; // Пустая реализация. Метод преобразования списка в массив.
    }

    @Override
    public Object[] toArray() {
        return new Object[0]; // Пустая реализация. Метод преобразования списка в массив объектов.
    }

    // Метод iterator() также не реализован, так как в рамках задания это необязательно.
    @Override
    public Iterator<E> iterator() {
        return null; // Пустая реализация. Метод для итерации по элементам списка.
    }

}
