package by.it.group310901.navrosuk.lesson09;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListB<E> implements List<E> {


    private E[] mas = (E[]) new Object[0]; // Изначально пустой массив.

    // Переменная для хранения текущего количества элементов в списке.
    private int size;

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

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

    // Добавление элемента в конец списка.
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

    // Удаление элемента по индексу.
    @Override
    public E remove(int index) {
        E deleteMem = mas[index]; // Сохраняем удаляемый элемент.
        // Сдвигаем оставшиеся элементы массива влево.
        System.arraycopy(mas, index + 1, mas, index, size - 1 - index);
        size--; // Уменьшаем размер списка.
        mas[size] = null; // Очищаем последний элемент массива.
        return deleteMem; // Возвращаем удалённый элемент.
    }

    // Возвращает текущее количество элементов в списке.
    @Override
    public int size() {
        return size;
    }



    //новые методы

    @Override
    public void add(int index, E element) {
        if (size == mas.length){
            E[] newmas = (E[]) new Object[size*3/2 + 1];
            System.arraycopy(mas, 0, newmas, 0, size);
            mas = newmas;
        }
        for (int i = size; i > index; i--){
            mas[i] = mas[i - 1];
        }
        mas[index] = element;
        size++;
    }

    @Override
    public boolean remove(Object o) {
        int delMem = indexOf(o);
        if (delMem > -1){
            remove(delMem);
        }
        return (delMem > -1);
    }

    @Override
    public E set(int index, E element) {
        E prev = mas[index];
        mas[index]=element;
        return prev;
    }


    @Override
    public boolean isEmpty() {

        return size==0;
    }


    @Override
    public void clear() {
        while(size != 0){
            remove(0);
        }
    }

    @Override
    public int indexOf(Object o) {
        for (int k = 0; k < size; k++){
            if (mas[k].equals(o)) return k;
        }

        return -1;
    }

    @Override
    public E get(int index) {

        return mas[index];
    }

    @Override
    public boolean contains(Object o) {
        return (indexOf(o) > -1);
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int k = size - 1; k >= 0; k--){
            if (mas[k].equals(o)) return k;
        }

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
