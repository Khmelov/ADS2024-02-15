package by.it.group351003.kisel.lesson09;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListC<E> implements List<E> {

    //Создайте аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ
    int size = 0;
    E[] arr;

    ListC()
    {
        this.arr =(E[]) new Object[this.size];
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder(); // Используем StringBuilder для создания строки

        // Проходим по элементам массива
        for (E element : this.arr) {
            if (element != null) // Проверяем, что элемент не равен null
                res.append(element).append(", "); // Добавляем элемент к результату
        }

        // Если результат не пустой, удаляем последнюю запятую и пробел
        if (!res.isEmpty())
            res.delete(res.length() - 2, res.length()); // Удаляем последние два символа (", ")

        res.insert(0, "["); // Добавляем открывающую скобку
        res.append("]"); // Добавляем закрывающую скобку
        return res.toString(); // Возвращаем строку
    }

    // Метод для добавления элемента в конец списка
    @Override
    public boolean add(E e) {
        // Создаем временный массив, который будет на 1 элемент больше
        E[] Temp = (E[]) new Object[this.size + 1];

        // Копируем старый массив в новый
        System.arraycopy(this.arr, 0, Temp, 0, this.size);

        Temp[this.size] = e; // Добавляем новый элемент в конец

        this.arr = Temp; // Перенаправляем ссылку на новый массив
        this.size++;
        return true; // Возвращаем true для обозначения успешного добавления
    }

    // Метод для удаления элемента по индексу
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
        this.size--; // Уменьшаем размер
        return data; // Возвращаем удалённый элемент
    }

    // Метод для получения текущего размера списка
    @Override
    public int size() {
        return this.size; // Возвращаем текущее количество элементов в списке
    }

    // Метод для удаления элемента по объекту
    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < this.arr.length; i++) {
            if (this.arr[i] != null && this.arr[i].equals(o)) { // Проверяем, равен ли элемент удаляемому
                this.remove(i); // Удаляем элемент по индексу
                return true; // Возвращаем true, если удаление произошло
            }
        }
        return false; // Возвращаем false, если элемент не найден
    }

    // Метод для добавления элемента по индексу
    @Override
    public void add(int index, E element) {
        // Увеличиваем размер массива, если он заполнен
        if (this.size == this.arr.length) {
            E[] temp = (E[]) new Object[this.arr.length * 2]; // Создаем новый массив в 2 раза больше
            System.arraycopy(this.arr, 0, temp, 0, this.arr.length); // Копируем старый массив в новый
            this.arr = temp; // Перенаправляем ссылку на новый массив
        }
        // Сдвигаем элементы вправо для создания места под новый элемент
        for (int i = this.size; i > index; i--) {
            this.arr[i] = this.arr[i - 1]; // Сдвигаем элементы на одну позицию вправо
        }
        this.arr[index] = element; // Вставляем новый элемент по указанному индексу
        this.size++; // Увеличиваем размер списка
    }

    // Метод для замены элемента по индексу
    @Override
    public E set(int index, E element) {
        E data = this.arr[index]; // Сохраняем старый элемент
        this.arr[index] = element; // Заменяем на новый элемент
        return data; // Возвращаем старый элемент
    }

    // Метод для проверки, пустой ли список
    @Override
    public boolean isEmpty() {
        return this.size == 0; // true, если размер списка равен 0
    }

    // Метод для очистки списка
    @Override
    public void clear() {
        this.size = 0; // Устанавливаем размер в 0
        this.arr = (E[]) new Object[this.size]; // Сброс массив
    }

    // Метод для поиска индекса элемента
    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < this.arr.length; i++) {
            if (this.arr[i] != null && this.arr[i].equals(o)) { // Проверяем на равенство
                return i; // Возвращаем индекс, если элемент найден
            }
        }
        return -1; // -1, если элемент не найден
    }

    // Метод для получения элемента по индексу
    @Override
    public E get(int index) {
        return this.arr[index]; // Возврат элемент по индексу
    }

    // Метод для проверки, содержит ли список элемент
    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < this.arr.length; i++) {
            if (this.arr[i] != null && this.arr[i].equals(o)) { // Проверяем на равенство
                return true; // true, если элемент найден
            }
        }
        return false; //false, если элемент не найден
    }

    // Метод для получения последнего индекса элемента
    @Override
    public int lastIndexOf(Object o) {
        for (int i = this.arr.length - 1; i >= 0; i--) { // Проход массив в обратном порядке
            if (this.arr[i] != null && this.arr[i].equals(o)) {
                return i; // Возвращаем индекс, если элемент найден
            }
        }
        return -1; // -1, если элемент не найден
    }

    // Метод для проверки, содержит ли список все элементы коллекции
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!this.contains(o)) { // Проверяем, содержит ли список каждый элемент коллекции
                return false; // false, если хотя бы один элемент не найден
            }
        }
        return true;
    }

    // Метод для добавления всех элементов коллекции в конец списка
    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E e : c) {
            this.add(e); // Добавляем каждый элемент коллекции в список
        }
        return true; // Возвращаем true, если добавление произошло
    }

    // Метод для добавления всех элементов коллекции по индексу
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        for (E e : c) {
            this.add(index, e); // Добавляем каждый элемент коллекции по указанному индексу
            index++; // Увеличиваем индекс для следующего добавления
        }
        return true; // Возвращаем true, если добавление произошло
    }

    // Метод для удаления всех элементов коллекции из списка
    @Override
    public boolean removeAll(Collection<?> c) {
        boolean result = true; // Флаг для обозначения успешного удаления
        for (int i = 0; i < this.arr.length; i++) {
            if (this.arr[i] != null && c.contains(this.arr[i])) { // Проверяем, есть ли элемент в коллекции
                this.remove(this.arr[i]); // Удаляем элемент
                i--; // Уменьшаем индекс для корректного обхода
            }
        }
        return result; // Возвращаем результат удаления
    }

    // Метод для сохранения только тех элементов, которые находятся в коллекции
    @Override
    public boolean retainAll(Collection<?> c) {
        boolean result = true; // Флаг для обозначения успешного выполнения
        for (int i = 0; i < this.arr.length; i++) {
            if (this.arr[i] != null && !c.contains(this.arr[i])) { // Проверяем, не содержится ли элемент в коллекции
                this.remove(this.arr[i]); // Удаляем элемент
                i--; // Уменьшаем индекс для корректного обхода
            }
        }
        return result; // Возвращаем результат выполнения
    }
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Опциональные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

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