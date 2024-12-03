package by.it.group310901.kanunnikava.lesson09;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.lang.StringBuilder;

public class ListA<E> implements List<E> { // Объявляется класс, который реализует интерфейс с параметризованным типом E
    private E[] data = (E[])new Object[0];
    private int size = 0; // Создается массив для хранения элементов списка и переменная size, хранящая текущее количество элементов в списке
    //Создайте аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    @Override
    public String toString() { // Вывод списка в виде строки (строится строка, содержащая все элементы списка, разделенные запятыми)
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
        // если текущий массив data заполнен, создается новый массив большего размера, и все элементы копируются в него
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
    public int size() { // Возвращение текущее количество элементов в списке.
        return size;
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Опциональные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    @Override
    public void add(int index, E element) {

    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public E set(int index, E element) {
        return null;
    }


    @Override
    public boolean isEmpty() {
        return false;
    }


    @Override
    public void clear() {

    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

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
