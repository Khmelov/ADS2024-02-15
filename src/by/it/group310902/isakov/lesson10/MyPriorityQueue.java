package by.it.group310902.isakov.lesson10;
import java.util.*;

// Кастомная реализация приоритетной очереди с использованием структуры мин-кучи.
public class MyPriorityQueue<E> implements Queue<E> {

    // Внутренний массив для хранения элементов кучи, инициализирован размером 1.
    private Object[] arr = new Object[1];
    private int size = 0; // Текущий размер очереди.

    @Override
    public String toString() {
        // Преобразует очередь в строковое представление для удобного отображения.
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
            sb.append(arr[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public int size() {
        // Возвращает текущий размер очереди.
        return size;
    }

    @Override
    public boolean isEmpty() {
        // Проверяет, пуста ли очередь.
        return size == 0;
    }

    private void swap(int i, int j) {
        // Меняет местами два элемента в массиве.
        Object t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    private void heapify(int i) {
        // Восстанавливает свойство кучи для поддерева с корнем в индексе i.
        int leftChild;
        int rightChild;
        int largestChild;
        while (true) {
            leftChild = 2 * i + 1; // Левый потомок.
            rightChild = leftChild + 1; // Правый потомок.
            largestChild = i; // Изначально считаем текущий узел "наибольшим".

            // Проверяем, нужно ли обновить largestChild для левого потомка.
            if (leftChild < size && ((Comparable<? super E>) arr[leftChild]).compareTo((E) arr[largestChild]) < 0)
                largestChild = leftChild;

            // Проверяем, нужно ли обновить largestChild для правого потомка.
            if (rightChild < size && ((Comparable<? super E>) arr[rightChild]).compareTo((E) arr[largestChild]) < 0)
                largestChild = rightChild;

            if (largestChild == i) // Если текущий узел на своём месте, заканчиваем.
                break;

            swap(i, largestChild); // Меняем местами текущий узел и его потомка.
            i = largestChild; // Продолжаем обработку вниз по дереву.
        }
    }

    public void rebuildHeap(Object[] newArr) {
        // Перестраивает кучу с новым массивом элементов.
        if (newArr.length == 0) // Если массив пуст, инициализируем минимальным размером.
            arr = new Object[1];
        else
            arr = newArr;

        size = newArr.length;

        // Проходим по всем родительским узлам и восстанавливаем кучу.
        for (int i = size / 2 - 1; i >= 0; i--)
            heapify(i);
    }

    @Override
    public boolean contains(Object o) {
        // Проверяет, содержит ли очередь заданный элемент.
        for (int i = 0; i < size; i++)
            if (arr[i].equals(o))
                return true;
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        // Заглушка для итератора (не реализовано).
        return null;
    }

    @Override
    public Object[] toArray() {
        // Заглушка для преобразования в массив (не реализовано).
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        // Заглушка для преобразования в массив (не реализовано).
        return null;
    }

    @Override
    public boolean add(E e) {
        // Добавляет элемент в очередь.
        if (arr.length == size) // Если массив заполнен, увеличиваем его размер вдвое.
            arr = Arrays.copyOf(arr, size * 2);

        arr[size++] = e; // Добавляем новый элемент и увеличиваем размер.

        // Восстанавливаем свойство кучи, двигаясь вверх от добавленного элемента.
        int i = size - 1, par = (i - 1) / 2;
        while (i > 0 && ((Comparable<? super E>) arr[i]).compareTo((E) arr[par]) < 0) {
            swap(i, par); // Меняем местами элемент с его родителем.
            i = par;
            par = (i - 1) / 2;
        }
        return true;
    }

    @Override
    public boolean remove(Object o) {
        // Удаление элемента (не реализовано).
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        // Проверяет, содержит ли очередь все элементы заданной коллекции.
        for (Object o : c)
            if (!contains(o))
                return false;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        // Добавляет все элементы из заданной коллекции в очередь.
        for (E o : c)
            add(o);
        return !c.isEmpty();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        // Удаляет из очереди все элементы, присутствующие в заданной коллекции.
        int kol = 0; // Счётчик элементов, которые останутся.
        for (int i = 0; i < size; i++)
            if (!c.contains(arr[i]))
                kol++;

        if (kol == size) // Если ничего не удалили, возвращаем false.
            return false;

        Object[] newArr = new Object[kol];
        int ci = 0;

        // Создаём новый массив, исключая элементы из коллекции.
        for (int i = 0; i < size(); i++)
            if (!c.contains(arr[i]))
                newArr[ci++] = arr[i];

        rebuildHeap(newArr); // Перестраиваем кучу.
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        // Оставляет в очереди только элементы, присутствующие в заданной коллекции.
        int kol = 0;
        for (int i = 0; i < size; i++)
            if (c.contains(arr[i]))
                kol++;

        if (kol == size) // Если ничего не изменилось, возвращаем false.
            return false;

        Object[] newArr = new Object[kol];
        int ci = 0;

        // Создаём новый массив, оставляя только элементы из коллекции.
        for (int i = 0; i < size(); i++)
            if (c.contains(arr[i]))
                newArr[ci++] = arr[i];

        rebuildHeap(newArr); // Перестраиваем кучу.
        return true;
    }

    @Override
    public void clear() {
        // Очищает очередь.
        arr = new Object[1]; // Сбрасываем массив.
        size = 0; // Устанавливаем размер в 0.
    }

    @Override
    public boolean offer(E e) {
        // Добавляет элемент в очередь (аналог add).
        return add(e);
    }

    @Override
    public E remove() {
        // Удаляет и возвращает минимальный элемент из очереди.
        if (isEmpty())
            throw new NoSuchElementException(); // Если очередь пуста, бросаем исключение.
        return poll();
    }

    @Override
    public E poll() {
        // Удаляет и возвращает минимальный элемент или null, если очередь пуста.
        if (isEmpty())
            return null;

        E toRet = (E) arr[0]; // Минимальный элемент (корень кучи).
        swap(0, --size); // Перемещаем последний элемент на место корня.
        heapify(0); // Восстанавливаем кучу.
        return toRet;
    }

    @Override
    public E element() {
        // Возвращает минимальный элемент без удаления.
        return (E) arr[0];
    }

    @Override
    public E peek() {
        // Возвращает минимальный элемент без удаления или null, если очередь пуста.
        if (isEmpty())
            return null;
        return (E) arr[0];
    }
}
