/**
 * Задание:
 * 1. Реализовать класс `ListC<E>`, который является аналогом списка без использования стандартных классов библиотеки Java (таких как `ArrayList` или `LinkedList`).
 * 2. Реализовать методы интерфейса `List`:
 *    - Обязательные: `add(E e)`, `remove(int index)`, `size()`, `get(int index)`, `set(int index, E element)`, `isEmpty()`, `clear()`, `indexOf(Object o)`, `contains(Object o)`, `lastIndexOf(Object o)`
 *    - Дополнительные: `containsAll(Collection<?> c)`, `addAll(Collection<? extends E> c)`, `addAll(int index, Collection<? extends E> c)`, `removeAll(Collection<?> c)`, `retainAll(Collection<?> c)`
 * 3. Использовать массив для хранения элементов списка. При переполнении массива необходимо его расширять.
 * 4. Осуществить ручное управление элементами массива, аналогично тому, как это делается в стандартных коллекциях Java.
 */

package by.it.group351001.sosnovski.lesson09;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListC<E> implements List<E> {

    // Массив для хранения элементов списка
    E[] elements;
    // Текущий индекс, показывающий количество элементов в списке
    int curInd = 0;
    // Начальный размер массива (по умолчанию 8)
    static int size = 8;

    // Конструктор без параметров, создает массив с начальным размером по умолчанию
    public ListC() {
        this(size);  // Вызывает другой конструктор с параметром
    }

    // Конструктор с возможностью задать начальный размер массива
    public ListC(int size) {
        elements = (E[]) new Object[size];  // Инициализация массива
    }

    // Метод для представления списка в строковом формате
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');  // Начало строки
        // Перебираем все элементы списка
        for (int i = 0; i < curInd; i++) {
            sb.append(elements[i]);  // Добавляем каждый элемент в строку
            if (i < curInd - 1) {  // Если это не последний элемент, добавляем запятую
                sb.append(", ");
            }
        }
        sb.append(']');  // Закрываем строку
        return sb.toString();
    }

    // Метод добавления элемента в конец списка
    @Override
    public boolean add(E e) {
        // Если текущий массив заполнен, расширяем его в 2 раза
        if (curInd == elements.length) {
            E[] tempElements = (E[]) new Object[elements.length * 2];
            // Копируем все элементы в новый массив
            for (int i = 0; i < elements.length; i++) {
                tempElements[i] = elements[i];
            }
            elements = tempElements;  // Обновляем ссылку на массив
        }
        elements[curInd] = e;  // Добавляем новый элемент
        curInd++;  // Увеличиваем индекс
        return true;
    }

    // Удаление элемента по индексу
    @Override
    public E remove(int index) {
        // Если индекс некорректен, возвращаем null
        if (index < 0 || index >= curInd) {
            return null;
        }

        E deletedElem = elements[index];  // Запоминаем удаляемый элемент
        // Сдвигаем элементы массива влево, начиная с индекса
        for (int i = index; i < curInd - 1; i++) {
            elements[i] = elements[i + 1];
        }
        curInd--;  // Уменьшаем текущий индекс
        return deletedElem;  // Возвращаем удаленный элемент
    }

    // Метод возвращает количество элементов в списке
    @Override
    public int size() {
        return curInd;
    }

    // Добавление элемента в заданную позицию списка
    @Override
    public void add(int index, E element) {
        if (index < 0 || index > curInd) {
            return;  // Если индекс некорректен, выходим из метода
        }

        // Расширяем массив, если в нем недостаточно места
        if (curInd == elements.length) {
            E[] tempElements = (E[]) new Object[elements.length * 2];
            // Копируем элементы в новый массив
            for (int i = 0; i < elements.length; i++) {
                tempElements[i] = elements[i];
            }
            elements = tempElements;  // Обновляем ссылку на массив
        }

        // Сдвигаем элементы вправо, чтобы освободить место для нового элемента
        for (int i = curInd; i > index; i--) {
            elements[i] = elements[i - 1];
        }

        elements[index] = element;  // Вставляем новый элемент на заданную позицию
        curInd++;  // Увеличиваем индекс
    }

    // Удаление элемента по объекту
    @Override
    public boolean remove(Object o) {
        // Перебираем массив, чтобы найти элемент
        for (int i = 0; i < curInd; i++) {
            if (o.equals(elements[i])) {  // Если элемент найден
                for (int j = i; j < curInd; j++) {
                    elements[j] = elements[j + 1];  // Сдвигаем элементы влево
                }
                curInd--;  // Уменьшаем текущий индекс
                return true;  // Элемент удален
            }
        }
        return false;  // Элемент не найден
    }

    // Замена элемента на заданной позиции
    @Override
    public E set(int index, E element) {
        if (index < 0 || index >= curInd) {
            return null;  // Если индекс некорректен, возвращаем null
        }

        E oldElem = elements[index];  // Запоминаем старый элемент
        elements[index] = element;  // Заменяем его новым
        return oldElem;  // Возвращаем старый элемент
    }

    // Метод проверки, пуст ли список
    @Override
    public boolean isEmpty() {
        return curInd == 0;  // Возвращает true, если список пуст
    }

    // Очистка списка
    @Override
    public void clear() {
        elements = (E[]) new Object[size];  // Создаем новый массив
        curInd = 0;  // Сбрасываем текущий индекс
    }

    // Поиск индекса элемента
    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < curInd; i++) {
            if (o.equals(elements[i])) {  // Если элемент найден, возвращаем его индекс
                return i;
            }
        }
        return -1;  // Элемент не найден
    }

    // Получение элемента по индексу
    @Override
    public E get(int index) {
        if (index < 0 || index >= curInd) {
            return null;  // Если индекс некорректен, возвращаем null
        }
        return elements[index];  // Возвращаем элемент по индексу
    }

    // Проверка, содержится ли элемент в списке
    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;  // Если индекс элемента >= 0, значит элемент есть в списке
    }

    // Поиск последнего вхождения элемента
    @Override
    public int lastIndexOf(Object o) {
        for (int i = curInd - 1; i >= 0; i--) {
            if (o.equals(elements[i])) {  // Если элемент найден, возвращаем его индекс
                return i;
            }
        }
        return -1;  // Элемент не найден
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Опциональные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    // Метод для проверки, содержит ли список все элементы другой коллекции
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object elem : c) {
            if (!contains(elem)) {  // Если хотя бы один элемент не найден, возвращаем false
                return false;
            }
        }
        return true;  // Все элементы найдены
    }

    // Метод для добавления всех элементов из другой коллекции
    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E elem : c) {
            add(elem);  // Добавляем каждый элемент
        }
        return true;
    }

    // Добавление всех элементов коллекции на определенную позицию
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        for (E elem : c) {
            add(index, elem);  // Вставляем каждый элемент на нужную позицию
            index++;  // Увеличиваем индекс для следующего элемента
        }
        return true;
    }

    // Метод для удаления всех элементов, которые содержатся в переданной коллекции
    @Override
    public boolean removeAll(Collection<?> c) {
        boolean deletedElem = false;

        for (int i = 0; i < curInd; i++) {
            if (c.contains(elements[i])) {  // Если элемент найден в коллекции
                remove(i);  // Удаляем элемент
                i--;  // Сдвигаем индекс назад
                deletedElem = true;
            }
        }
        return deletedElem;  // Возвращаем true, если что-то было удалено
    }

    // Метод для сохранения только тех элементов, которые есть в переданной коллекции
    @Override
    public boolean retainAll(Collection<?> c) {
        boolean deletedElem = false;

        for (int i = 0; i < curInd; i++) {
            if (!c.contains(elements[i])) {  // Если элемент не содержится в коллекции
                remove(i);  // Удаляем его
                i--;  // Сдвигаем индекс назад
                deletedElem = true;
            }
        }
        return deletedElem;  // Возвращаем true, если что-то было удалено
    }

    // Методы, не обязательные к реализации (опциональные)
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;  // Не реализовано
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;  // Не реализовано
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;  // Не реализовано
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;  // Не реализовано
    }

    @Override
    public Object[] toArray() {
        return new Object[0];  // Не реализовано
    }

    // Итератор не реализован
    @Override
    public Iterator<E> iterator() {
        return null;
    }
}
