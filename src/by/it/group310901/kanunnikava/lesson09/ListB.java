package by.it.group310901.kanunnikava.lesson09;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListB<E> implements List<E> { // Объявление класса, который реализует интерфейс с параметризованным типом E.
    private E[] data = (E[]) new Object[0];
    private int size = 0; // Создается массив для хранения элементов списка и переменная size, хранящая текущее количество элементов в списке.

    //Создайте аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    @Override
    public String toString() { //  Вывод списка в виде строки (строится строка, содержащая все элементы списка, разделенные запятыми)
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(data[i]);
            if (i == size - 1) {
                sb.append("]");
                return sb.toString();
            }
            sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean add(E e) { // Добавление элементов в список.
        // Если текущий массив data заполнен, создается новый массив большего размера, и все элементы копируются в него.
        try {
            if (size >= data.length) {
                E[] ndata = (E[]) new Object[data.length != 0 ? data.length << 1 : 10];
                System.arraycopy(data, 0, ndata, 0, size);
                data = ndata;
            }
            data[size++] = e;
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public E remove(int index) { // Удаление элементов по индексу. После удаления элементы смещаются, чтобы заполнить пробел.
        if (index >= size) {
            return null;
        }
        E rem = data[index];
        System.arraycopy(data, index + 1, data, index, size - 1 - index);
        size--;
        return rem;
    }

    @Override
    public int size() { // Возврат текущего количества элементов в списке.
        return size;
    }

    @Override
    public void add(int index, E element) { // Добавление элемент в определенное место в списке (по индексу).
        // Если массив заполнен, создается новый массив большего размера.
        if (size >= data.length) {
            E[] ndata = (E[]) new Object[data.length != 0 ? data.length << 1 : 10];
            System.arraycopy(data, 0, ndata, 0, size);
            data = ndata;
        }
        System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = element;
        size++;
    }

    @Override
    public boolean remove(Object o) { // Удаление элементов по объекту. Если объект найден, он удаляется.
        for (int i = 0; i < size; i++) {
            if (o.equals(data[i])) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public E set(int index, E element) { // Метод устанавливает элемент по индексу и возвращает старый элемент, который был на этом месте.
        if (index >= size) {
            return null;
        }
        E tmp = data[index];
        data[index] = element;
        return tmp;
    }


    @Override
    public boolean isEmpty() { // Проверка, пуст ли список, возвращая true, если размер списка равен нулю.
        return size == 0;
    }


    @Override
    public void clear() { // Метод очищает список, устанавливая массив данных в новый пустой массив и обнуляя размер.
        data = (E[]) new Object[0];
        size = 0;
    }

    @Override
    public int indexOf(Object o) { // Метод  возвращает индекс первого вхождения элемента в списке. Если элемент не найден, возвращает -1.
        for (int i = 0; i < size; i++) {
            if (o.equals(data[i]))
                return i;
        }
        return -1;
    }

    @Override
    public E get(int index) { // Метод возвращает элемент по индексу, если индекс не выходит за пределы списка.
        if (index >= size)
            return null;
        return data[index];
    }

    @Override
    public boolean contains(Object o) { // Метод проверяет, содержит ли список указанный элемент.
        for (int i = 0; i < size; i++)
            if (o.equals(data[i]))
                return true;
        return false;
    }

    @Override
    public int lastIndexOf(Object o) { // Метод возвращает индекс последнего вхождения элемента в списке. Если элемент не найден, возвращает -1.
        for (int i = size - 1; i >= 0; i--)
            if (o.equals(data[i]))
                return i;
        return -1;
    }


    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Опциональные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////


    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
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
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public Object[] toArray() {
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
        return null;
    }

}
