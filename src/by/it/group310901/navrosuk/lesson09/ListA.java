package by.it.group310901.navrosuk.lesson09;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListA<E> implements List<E> {

    // Массив для хранения элементов списка. Тип данных — обобщённый (generic).
    private E[] mas = (E[]) new Object[0]; // Изначально пустой массив.

    // Переменная для хранения текущего количества элементов в списке.
    private int size;

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    // Переопределение метода toString() для представления списка в виде строки.
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("["); // Начало строки.
        String temp = ""; // Разделитель между элементами.
        for (int i = 0; i < size; i++) { // Проходим по всем элементам списка.
            str.append(temp).append(mas[i]); // Добавляем элемент и разделитель.
            temp = ", "; // Разделитель устанавливается после первого элемента.
        }
        str.append("]"); // Закрываем строку.
        return str.toString(); // Возвращаем итоговую строку.
    }


    @Override
    public boolean add(E e) {
        if (size == mas.length) { // Если массив заполнен, увеличиваем его размер.
            E[] newmas = (E[]) new Object[(mas.length * 3) / 2 + 1]; // Новый массив большего размера.
            System.arraycopy(mas, 0, newmas, 0, size); // Копируем элементы в новый массив.
            mas = newmas; // Подменяем старый массив на новый.
        }
        mas[size++] = e; // Добавляем элемент в массив и увеличиваем размер.
        return true; // Успешное добавление.
    }


    @Override
    public E remove(int index) {
        E deleteMem = mas[index]; // Сохраняем удаляемый элемент.
        // Сдвигаем оставшиеся элементы массива влево.
        System.arraycopy(mas, index + 1, mas, index, size - 1 - index);
        size--; // Уменьшаем размер списка.
        mas[size] = null; // Очищаем последний элемент массива.
        return deleteMem; // Возвращаем удалённый элемент.
    }


    @Override
    public int size() {
        return size;
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Опциональные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    @Override
    public void add(int index, E element) {
        // Метод пока не реализован.
    }

    @Override
    public boolean remove(Object o) {
        // Метод пока не реализован.
        return false;
    }

    @Override
    public E set(int index, E element) {
        // Метод пока не реализован.
        return null;
    }

    @Override
    public boolean isEmpty() {

        return size == 0;
    }

    @Override
    public void clear() {
        // Удаляет все элементы из списка.
        for (int i = 0; i < size; i++) {
            mas[i] = null; // Обнуляем каждый элемент массива.
        }
        size = 0; // Устанавливаем размер в 0.
    }

    @Override
    public int indexOf(Object o) {
        // Возвращает индекс первого вхождения объекта o или -1, если его нет.
        for (int i = 0; i < size; i++) {
            if (mas[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public E get(int index) {
        // Возвращает элемент по указанному индексу.
        return mas[index];
    }

    @Override
    public boolean contains(Object o) {
        // Проверяет, есть ли объект o в списке.
        return indexOf(o) >= 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        // Возвращает индекс последнего вхождения объекта o или -1, если его нет.
        for (int i = size - 1; i >= 0; i--) {
            if (mas[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        // Метод пока не реализован.
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        // Метод пока не реализован.
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        // Метод пока не реализован.
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        // Метод пока не реализован.
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        // Метод пока не реализован.
        return false;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        // Метод пока не реализован.
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        // Метод пока не реализован.
        return null;
    }

    @Override
    public ListIterator<E> listIterator() {
        // Метод пока не реализован.
        return null;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        // Метод пока не реализован.
        return null;
    }

    @Override
    public Object[] toArray() {
        // Метод пока не реализован.
        return new Object[0];
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    ////////        Эти методы имплементировать необязательно    ////////////
    ////////        но они будут нужны для корректной отладки    ////////////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    @Override
    public Iterator<E> iterator() {
        // Метод пока не реализован.
        return null;
    }
}
