package by.it.group351001.shimanchuk.lesson10;

import java.util.*;

/**
 * Класс MyPriorityQueue реализует интерфейс Queue с использованием кучи.
 * Он поддерживает операции добавления, удаления и доступа к элементам с учетом приоритета.
 *
 * @param <E> тип элементов, хранимых в очереди
 */
public class MyPriorityQueue<E> implements Queue<E> {

    // Массив для хранения элементов очереди
    Object[] arr = new Object[1];
    // Текущий размер очереди
    int size = 0;

    // Переопределение метода toString для удобного отображения содержимого очереди
    public String toString() {
        if (isEmpty()) {
            return "[]"; // Если очередь пуста, возвращаем "[]"
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size - 1; i++) {
            sb.append(arr[i].toString()).append(", "); // Добавляем элементы в строку
        }
        return sb.append(arr[size - 1].toString()).append("]").toString(); // Возвращаем строковое представление очереди
    }

    // Метод возвращает текущий размер очереди
    @Override
    public int size() {
        return size; // Возвращаем размер
    }

    // Метод проверяет, пуста ли очередь
    @Override
    public boolean isEmpty() {
        return size == 0; // Возвращаем true, если размер равен 0
    }

    // Метод проверяет, содержится ли элемент в очереди
    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            if (arr[i].equals(o)) { // Если элемент найден, возвращаем true
                return true;
            }
        }
        return false; // Если элемент не найден, возвращаем false
    }

    // Метод возвращает итератор по элементам очереди (не реализован)
    @Override
    public Iterator<E> iterator() {
        return null;
    }

    // Метод возвращает массив, содержащий элементы очереди
    @Override
    public Object[] toArray() {
        return new Object[0]; // Возвращаем новый пустой массив
    }

    // Метод возвращает массив, содержащий элементы очереди в указанном типе
    @Override
    public <T> T[] toArray(T[] a) {
        return null; // Возвращаем null, метод не реализован
    }

    // Метод для обмена элементов по индексу
    private void swap(int i, int j) {
        Object t = arr[i]; // Временная переменная для обмена
        arr[i] = arr[j]; // Обмен элементов
        arr[j] = t;
    }

    // Метод добавляет элемент в очередь с учетом приоритета
    @Override
    public boolean add(E e) {
        if (size == arr.length) {
            arr = Arrays.copyOf(arr, size * 2); // Увеличиваем размер массива, если он заполнен
        }
        arr[size++] = e; // Добавляем элемент в конец массива
        int i = size - 1, par = (i - 1) / 2; // Индекс текущего элемента и его родителя
        // Поднимаем элемент вверх, если он меньше своего родителя
        while (i > 0 && ((Comparable<? super E>) arr[i]).compareTo((E) arr[par]) < 0) {
            swap(i, par); // Обмен элементов
            i = par; // Переходим к родителю
            par = (i - 1) / 2; // Обновляем индекс родителя
        }
        return true; // Возвращаем true, элемент успешно добавлен
    }

    // Метод удаляет указанный элемент из очереди (не реализован)
    @Override
    public boolean remove(Object o) {
        return false; // Возвращаем false, метод не реализован
    }

    // Метод проверяет, содержатся ли все элементы коллекции в очереди
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) { // Если хотя бы один элемент не найден, возвращаем false
                return false;
            }
        }
        return true; // Все элементы найдены
    }

    // Метод добавляет все элементы из коллекции в очередь
    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E o : c)
            add(o); // Добавляем каждый элемент
        return !c.isEmpty(); // Возвращаем true, если коллекция не пуста
    }

    // Метод восстанавливает структуру кучи с заданного индекса
    private void heapify(int i) {
        int leftChild;
        int rightChild;
        int largestChild;
        while (true) {
            leftChild = 2 * i + 1; // Индекс левого дочернего узла
            rightChild = leftChild + 1; // Индекс правого дочернего узла
            largestChild = i; // Предполагаем, что текущий узел - наибольший
            // Сравниваем с левым дочерним узлом
            if (leftChild < size && ((Comparable<? super E>) arr[leftChild]).compareTo((E) arr[largestChild]) < 0)
                largestChild = leftChild; // Если левый дочерний меньше, обновляем наибольший узел
            // Сравниваем с правым дочерним узлом
            if (rightChild < size && ((Comparable<? super E>) arr[rightChild]).compareTo((E) arr[largestChild]) < 0)
                largestChild = rightChild; // Если правый дочерний меньше, обновляем наибольший узел
            if (largestChild == i)
                break; // Если текущий узел наибольший, завершаем цикл
            swap(i, largestChild); // Обмен узлов
            i = largestChild; // Переходим к наибольшему узлу
        }
    }

    // Метод восстанавливает кучу из нового массива
    public void rebuildHeap(Object[] newArr) {
        if (newArr.length == 0)
            arr = new Object[1]; // Если новый массив пуст, создаем новый массив размером 1
        else
            arr = newArr; // Иначе присваиваем новый массив
        size = newArr.length; // Обновляем размер
        // Восстанавливаем кучу, начиная с последнего родительского узла
        for (int i = size / 2 - 1; i >= 0; i--)
            heapify(i); // Восстанавливаем кучу
    }

    // Метод удаляет все элементы из очереди, которые содержатся в коллекции
    @Override
    public boolean removeAll(Collection<?> c) {
        int amount = 0; // Счетчик оставшихся элементов
        for (int i = 0; i < size; i++) {
            if (!c.contains(arr[i])) {
                amount++; // Увеличиваем счетчик, если элемент не в коллекции
            }
        }
        if (amount == size) {return false;} // Если остались все элементы, ничего не удаляем
        Object[] newArr = new Object[amount]; // Новый массив для оставшихся элементов
        int ci = 0; // Индекс для нового массива
        for (int i = 0; i < size(); i++)
            if (!c.contains(arr[i]))
                newArr[ci++] = arr[i]; // Заполняем новый массив
        rebuildHeap(newArr); // Восстанавливаем кучу
        return true; // Возвращаем true, если были удалены элементы
    }

    // Метод оставляет в очереди только те элементы, которые содержатся в коллекции
    @Override
    public boolean retainAll(Collection<?> c) {
        int amount = 0; // Счетчик оставшихся элементов
        for (int i = 0; i < size; i++)
            if (c.contains(arr[i]))
                amount++; // Увеличиваем счетчик, если элемент в коллекции
        if (amount == size)
            return false; // Если остались все элементы, ничего не удаляем
        Object[] newArr = new Object[amount]; // Новый массив для оставшихся элементов
        int ci = 0; // Индекс для нового массива
        for (int i = 0; i < size; i++)
            if (c.contains(arr[i]))
                newArr[ci++] = arr[i]; // Заполняем новый массив
        rebuildHeap(newArr); // Восстанавливаем кучу
        return true; // Возвращаем true, если были удалены элементы
    }

    // Метод очищает очередь
    @Override
    public void clear() {
        arr = new Object[1]; // Сбрасываем массив на новый
        size = 0; // Устанавливаем размер в 0
    }

    // Метод добавляет элемент в очередь (аналогично add)
    @Override
    public boolean offer(E e) {
        return add(e); // Используем метод add
    }

    // Метод удаляет и возвращает элемент с наивысшим приоритетом
    @Override
    public E remove() {
        if (isEmpty())
            throw new NoSuchElementException(); // Исключение, если очередь пуста
        return poll(); // Используем метод poll для удаления
    }

    // Метод удаляет и возвращает элемент с наивысшим приоритетом, или null, если очередь пуста
    @Override
    public E poll() {
        if (isEmpty())
            return null; // Если очередь пуста, возвращаем null

        E toRet = (E) arr[0]; // Сохраняем элемент для возвращения
        swap(0, --size); // Перемещаем последний элемент на место корня и уменьшаем размер
        heapify(0); // Восстанавливаем кучу
        return toRet; // Возвращаем элемент
    }

    // Метод возвращает элемент с наивысшим приоритетом
    @Override
    public E element() {
        if (isEmpty())
            throw new NoSuchElementException(); // Исключение, если очередь пуста
        return (E) arr[0]; // Возвращаем корневой элемент
    }

    // Метод возвращает элемент с наивысшим приоритетом, или null, если очередь пуста
    @Override
    public E peek() {
        if (isEmpty())
            return null; // Если очередь пуста, возвращаем null
        return (E) arr[0]; // Возвращаем корневой элемент
    }
}
