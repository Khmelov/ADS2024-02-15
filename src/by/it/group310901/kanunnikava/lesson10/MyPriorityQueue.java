package by.it.group310901.kanunnikava.lesson10;

import java.util.Iterator;
import java.util.Queue;
import java.util.Collection;

    /*Создайте class MyPriorityQueue<E>, который реализует интерфейс Queue<E>
    и работает на основе кучи, построенной на приватном массиве типа E[]
            БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ*/

public class MyPriorityQueue<E extends Comparable<E>> implements Queue<E>{
    private E[] elements = (E[]) new Comparable[10]; // Массив элементов, начальная длина которого равна 10 private
    private int size = 0; // Переменная для отслеживания текущего размера очереди

    private void posincsize(){ // Приватный метод для увеличения размера массива при необходимости
        if (size == elements.length){
            E[] newarr =  (E[]) new Comparable[size * 3 / 2 + 1]; // Создание нового массива большего размера
            System.arraycopy(elements, 0, newarr, 0, size); // Копирование элементов в новый массив
            elements = newarr;
        }
    }

    private void SiftUp(int index){ // Приватный метод для подъема элемента по куче (Heap) вверх
        if (index > 0){
            int mother = (index - 1) / 2; // Вычисление индекса родительского элемента
            if (elements[index].compareTo(elements[mother])<0){ // Сравнение текущего элемента с родительским
                swap(index, mother); // Обмен элементов местами
                SiftUp(mother);
            }
        }
    }

    private void SiftDown(int index){ // Приватный метод для спуска элемента по куче (Heap) вниз
        int child1 = index * 2 + 1; // Вычисление индекса первого дочернего элемента
        int child2 = index * 2 + 2; // Вычисление индекса второго дочернего элемента
        int toswap = index; // Инициализация индекса для обмена
        if (child1 < size && elements[child1].compareTo(elements[toswap]) < 0) {
            toswap = child1; // Установка индекса для обмена на первый дочерний элемент
        }
        if (child2 < size && elements[child2].compareTo(elements[toswap]) < 0) {
            toswap = child2; // Установка индекса для обмена на второй дочерний элемент
        }

        if (toswap != index) { // Если индекс для обмена изменился
            swap(index, toswap); // Обмен элементов местами
            SiftDown(toswap);
        }
    }

    private void swap(int i, int j) { // Приватный метод для обмена элементов местами
        E temp = elements[i];
        elements[i] = elements[j];
        elements[j] = temp;
    }


    public String toString(){  // Метод для получения строкового представления содержимого очереди
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size-1;  i++){
            sb.append(elements[i]).append(", ");
        }
        if (size > 0){
            sb.append(elements[size-1]);
        }
        sb.append("]");
        return sb.toString();
    }

    public boolean offer(E element){  // Метод для добавления элемента в очередь
        add(element);
        return true;
    }
    public int size(){ // Метод для получения текущего размера очереди
        return size;
    }

    public void clear(){ // Метод для очистки очереди
        size = 0;
        elements = (E[]) new Comparable[1]; // Создание нового пустого массива
    }
    public boolean add(E element){ // Метод для добавления элемента в очередь
        posincsize(); // Проверка и увеличение размера массива
        elements[size] = element; // Добавление элемента в массив
        SiftUp(size); // Подъем элемента вверх по куче
        size++; // Увеличение размера очереди
        return true;
    }
    @Override
    public boolean remove(Object o) {
        return false;
    }
    public E remove(){ // Метод для удаления и возвращения первого элемента очереди
        E temp = elements[0];
        elements[0] = elements[--size]; // Замена первого элемента последним элементом и уменьшение размера
        SiftDown(0); // Спуск нового первого элемента вниз по куче
        return temp;
    }
    public boolean contains(Object o){ // Метод для проверки наличия элемента в очереди
        for (int i = 0; i < size; i++){
            if (elements[i].equals(o)){ // Если элемент найден
                return true;
            }
        }
        return false;
    }
    @Override
    public Iterator<E> iterator() {
        return null;
    }
    @Override
    public Object[] toArray() {
        return new Object[0];
    }
    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    public E peek(){ // Метод для получения первого элемента без удаления
        return elements[0];
    }

    public boolean isEmpty(){ // Метод для проверки пустоты очереди
        return size == 0;
    }

    public E poll(){ // Метод для удаления и возвращения первого элемента
        E temp = elements[0]; // Сохранение первого элемента
        remove(0); // Удаление первого элемента
        return temp; // Возвращение удаленного элемента
    }

    public E element(){ // Метод для получения первого элемента без удаления
        return elements[0];
    }
    @Override
    public boolean containsAll(Collection<?> c){
        boolean isFound; // Переменная для отслеживания нахождения элементов
        for (Object ci:c){  // Цикл по элементам коллекции
            isFound = false;
            for (int i = 0; i < size; i++){ // Цикл по элементам очереди
                if (ci.equals(elements[i])) {
                    isFound = true; // Установка переменной
                }
            }
            if (!isFound){
                return false;
            }
        }
        return true;
    }

    public void remove(int index){ // Метод для удаления элемента по индексу
        swap(index, size - 1);
        size--;
        SiftDown(index);
    }

    public boolean addAll(Collection<? extends E> c){ // Метод для добавления всех элементов из коллекции c в очередь
        for (E ci: c){ // Цикл по элементам коллекции
          add(ci); // Добавление каждого элемента в очередь
        }
        return (c.size() != 0);
    }

    public boolean removeAll(Collection<?> c){ // Метод для удаления всех элементов из коллекции c
        E[] newData = (E[]) new Comparable[size]; // Создание нового массива
        int newSize = 0;
        for (int i = 0; i < size; i++) { // Цикл по элементам очереди
            if (!c.contains(elements[i])) { // Если элемент не содержится в коллекции c
                newData[newSize] = elements[i]; // Добавление элемента в новый массив
                newSize++;
            }
        }
        boolean modified = newSize != size; // Проверка, изменился ли размер
        elements = newData;
        size = newSize;
        for (int i = size / 2; i >= 0; i--) // Восстановление свойств кучи (Heap) снизу вверх
            SiftDown(i); // Вызов метода SiftDown для элемента i
        return modified;
    }

    public boolean retainAll(Collection<?> c){ // Метод для сохранения только тех элементов, которые содержатся в коллекции c
        E[] newData = (E[]) new Comparable[size]; // Создание нового массива
        int newSize = 0;
        for (int i = 0; i < size; i++) { // Цикл по элементам очереди
            if (c.contains(elements[i])) { // Если элемент содержится в коллекции c
                newData[newSize] = elements[i]; // Добавление элемента в новый массив
                newSize++;
            }
        }
        boolean modified = newSize != size; // Проверка, изменился ли размер
        elements = newData;
        size = newSize;
        for (int i = size / 2; i >= 0; i--) // Восстановление свойств кучи (Heap) снизу вверх
            SiftDown(i); // Вызов метода SiftDown для элемента i
        return modified;
    }
}