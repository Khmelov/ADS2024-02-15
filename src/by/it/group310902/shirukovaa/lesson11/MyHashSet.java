package by.it.group310902.shirukovaa.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;


public class MyHashSet<E> implements Set<E> {

    private static class Node<Object> {
        public Object o; // Хранимое значение
        public Node next; // Ссылка на следующий узел

        Node(Object o) {
            this.o = o; // Конструктор для инициализации узла
        }
    }

    // Метод для получения хэш-кода объекта с обработкой null
    private int getHashCode(Object o) {
        int hashcode = o == null ? 0 : o.hashCode();
        hashcode = hashcode < 0 ? -hashcode : hashcode; // Приведение хэш-кода к положительному
        return hashcode % buckets.length; // Возвращаем индекс в массиве buckets
    }

    // Метод для изменения размера массива buckets при превышении загрузки
    private void resize() {
        length = 0; // Сбрасываем длину
        Node[] tempBuckets = buckets; // Сохраняем старые бакеты
        buckets = new Node[capacity *= 2]; // Увеличиваем емкость массива
        for (Node bucket : tempBuckets) {
            Node tempNode = bucket;
            while (tempNode != null) {
                add((E)tempNode.o); // Перемещаем все элементы в новый массив
                tempNode = tempNode.next;
            }
        }
    }

    private Node[] buckets; // Массив для хранения узлов
    private static int capacity = 16; // Начальная емкость
    private int length = 0; // Текущая длина (количество элементов)
    private final float DEFAULT_LOAD_FACTOR = 0.75f; // Фактор загрузки по умолчанию

    // Конструктор, инициализирующий массив buckets
    MyHashSet() {
        buckets = new Node[capacity];
    }

    // Переопределенный метод toString для удобного отображения содержимого
    public String toString() {
        StringBuilder strbldr = new StringBuilder();
        strbldr.append("[");
        for (Node bucket : buckets) {
            while (bucket != null) {
                strbldr.append(bucket.o + ", "); // Добавляем элементы в строку
                bucket = bucket.next;
            }
        }

        int sbLength = strbldr.length();
        if (sbLength > 1)
            strbldr.delete(sbLength - 2, sbLength); // Удаляем запятую и пробел в конце

        strbldr.append("]");

        return strbldr.toString(); // Возвращаем строковое представление
    }

    @Override
    public int size() {
        return length; // Возвращаем количество элементов
    }

    @Override
    public void clear() {
        capacity = 16; // Сбрасываем емкость
        length = 0; // Обнуляем длину
        buckets = new Node[capacity]; // Создаем новый массив для бакетов
    }

    @Override
    public boolean isEmpty() {
        return size() == 0; // Проверяем, пуст ли набор
    }

    @Override
    public boolean add(E e) {
        int h = getHashCode(e); // Получаем индекс для нового элемента
        Node prevNode = null, tempNode = buckets[h];
        while (tempNode != null) {
            if (tempNode.o.equals(e))
                return false; // Если элемент уже существует, возвращаем false
            prevNode = tempNode;
            tempNode = tempNode.next;
        }

        // Добавляем новый элемент в конец списка
        if (prevNode != null) {
            tempNode = new Node(e);
            prevNode.next = tempNode;
        } else {
            buckets[h] = new Node(e); // Добавляем элемент в пустую корзину
        }

        length++; // Увеличиваем длину
        if (length > buckets.length * DEFAULT_LOAD_FACTOR)
            resize(); // Если превышена нагрузка, изменяем размер

        return true; // Элемент успешно добавлен
    }

    @Override
    public boolean remove(Object o) {
        int h = getHashCode(o); // Получаем индекс для элемента
        Node prevNode = null, tempNode = buckets[h];

        // Ищем элемент для удаления
        while (tempNode != null) {
            if (o.equals(tempNode.o)) {
                // Если нашли, обновляем ссылки для удаления
                if (prevNode == null)
                    buckets[h] = tempNode.next; // Удаляем первый элемент
                else
                    prevNode.next = tempNode.next; // Удаляем элемент из середины или конца
                length--; // Уменьшаем длину
                return true; // Элемент успешно удален
            }
            prevNode = tempNode;
            tempNode = tempNode.next;
        }

        return false; // Элемент не найден
    }

    @Override
    public boolean contains(Object o) {
        int h = getHashCode(o); // Получаем индекс для поиска
        Node tempNode = buckets[h];

        // Ищем элемент
        while (tempNode != null) {
            if (o.equals(tempNode.o))
                return true; // Элемент найден
            tempNode = tempNode.next;
        }
        return false; // Элемент не найден
    }

    ///////////////////
    //// необязательные
    ///////////////////

    @Override
    public boolean containsAll(Collection<?> c) {
        return false; // Метод не реализован
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false; // Метод не реализован
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false; // Метод не реализован
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false; // Метод не реализован
    }

    @Override
    public Iterator<E> iterator() {
        return null; // Метод не реализован
    }

    @Override
    public Object[] toArray() {
        return new Object[0]; // Метод не реализован
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null; // Метод не реализован
    }
}