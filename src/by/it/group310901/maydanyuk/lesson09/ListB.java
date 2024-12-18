package by.it.group310901.maydanyuk.lesson09;

import java.util.*;
//обобщенный класс, который реализует интерфейс List<E>
public class ListB<E> implements List<E> {
    //Создайте аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //массив, в котором хранятся элементы списка
    private E[] data = (E[]) new Object[0];
    private int size = 0;
    //возвращает строковое представление списка
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        //проверяет на последний элемент
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
    //Добавляет элемент в конец списка.
    @Override
    public boolean add(E e) {
        try {
            if (size >= data.length) {
                //Создается новый массив типа E с длиной, равной удвоенной длине старого массива
                // либо длиной 10, если старый массив пустой
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
    //Удаляет элемент по заданному индексу
    @Override
    public E remove(int index) {
        if (index >= size) {//если индекс выходит за пределы текущего размера списка
            return null;
        }
        E rem = data[index];//удаляемый объект
        System.arraycopy(data, index + 1, data, index, size - 1 - index);
        size--;
        return rem;
    }

    @Override
    public int size() {
        return size;
    }
//Добавляет элемент по указанному индексу
    @Override
    public void add(int index, E element) {
        //Если массив переполнен, создается новый массив большего размера
        if (size >= data.length) {
            E[] ndata = (E[]) new Object[data.length != 0 ? data.length << 1 : 10];
            System.arraycopy(data, 0, ndata, 0, size);
            data = ndata;
        }//Элементы сдвигаются вправо, начиная с указанного индекса
        System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = element;//Новый элемент вставляется в массив
        size++;
    }
//Удаляет первый найденный элемент, равный заданному объекту
    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (o.equals(data[i])) {
                remove(i);
                return true;
            }
        }
        return false;
    }
//Заменяет элемент по заданному индексу на новый элемент
    @Override
    public E set(int index, E element) {
        if (index >= size) {
            return null;
        }
        E tmp = data[index];
        data[index] = element;
        return tmp;
    }
//проверяет пуст ли список
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {//очищает список
        data = (E[]) new Object[0];
        size = 0;
    }
//Возвращает индекс первого найденного элемента, равного заданному объекту
    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (o.equals(data[i]))
                return i;
        }
        return -1;
    }
//Возвращает элемент по указанному индексу
    @Override
    public E get(int index) {
        if (index >= size)
            return null;
        return data[index];
    }
//Проверяет, содержит ли список заданный объект
    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++)
            if (o.equals(data[i]))
                return true;
        return false;
    }
//Возвращает индекс последнего найденного элемента, равного заданному объекту
    @Override
    public int lastIndexOf(Object o) {
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