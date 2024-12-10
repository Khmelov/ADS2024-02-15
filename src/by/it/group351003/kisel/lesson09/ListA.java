package by.it.group351003.kisel.lesson09;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListA<E> implements List<E> {

    //Создайте аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ

    int size = 0; // Хранит текущее количество элементов в списке
    E[] arr; // Массив для хранения элементов списка


    ListA()
    {
        // Инициализируем массив с нулевым размером
        this.arr =(E[]) new Object[this.size];
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////


    @Override
    public String toString() {
        StringBuilder res = new StringBuilder(); // StringBuilder для создания строки

        // Проход по элементам массива
        for(E element : this.arr) {
            if(element != null)
                res.append(element).append(", "); // Добавляем элемент к результату
        }

        // Если результат не пустой, удаляем последнюю запятую и пробел
        if(!res.isEmpty()) res.delete(res.length() - 2, res.length());
        res.insert(0, "["); // открывающую скобку
        res.append("]"); // закрывающую скобку
        return res.toString(); // Возвращаем строку
    }

    // Метод для добавления элемента в конец списка
    @Override
    public boolean add(E e) {
        // Создаем временный массив, который будет на 1 элемент больше
        E[] Temp = (E[]) new Object[this.size + 1];
        // Копируем старый массив в новый
        System.arraycopy(this.arr, 0, Temp, 0, this.size);
        Temp[this.size] = e; // Добавляем новый  в конец
        this.arr = Temp; // Перенаправляем ссылку на новый массив
        this.size++; // Увеличиваем размер списка
        return true; // Возвращаем true для обозначения успешного добавления
    }

    @Override
    public E remove(int index) {
        // Создаем временный массив, который будет на 1 элемент меньше
        E[] Temp = (E[]) new Object[this.size - 1];

        E data = this.arr[index]; // Сохраняем удаляемый элемент
        // Копируем элементы до удаляемого
        System.arraycopy(this.arr, 0, Temp, 0, index);
        // Копируем элементы после удаляемого
        System.arraycopy(this.arr, index + 1, Temp, index, this.size - index - 1);

        this.arr = Temp; // Перенаправляем ссылку на новый массив
        this.size--; // Уменьшаем размер списка
        return data; // Возвращаем удалённый элемент
    }

    @Override
    public int size() {
        return this.size; // Возвращаем текущее количество элементов в списке
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