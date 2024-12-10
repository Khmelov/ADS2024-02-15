package by.it.group351001.shimanchuk.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

/**
 * Класс MyLinkedList реализует интерфейс Deque с использованием двусвязного списка.
 * Он поддерживает операции добавления, удаления и доступа к элементам с обоих концов дека.
 *
 * @param <E> тип элементов, хранимых в этом двусвязном списке
 */
public class MyLinkedList<E> implements Deque<E> {
    // Внутренний класс для представления узла двусвязного списка
    class LinkedNode<E> {
        E element; // Элемент, хранящийся в узле
        LinkedNode<E> prev; // Указатель на предыдущий узел
        LinkedNode<E> next; // Указатель на следующий узел

        public LinkedNode(E element) {
            this.element = element; // Инициализация элемента узла
        }
    }

    // Указатели на первый и последний узлы списка
    LinkedNode<E> head;
    LinkedNode<E> tail;
    // Текущее количество элементов в списке
    int size;

    // Конструктор по умолчанию, инициализирует пустой список
    MyLinkedList() {
        head = null; // Начальный узел
        tail = null; // Конечный узел
        size = 0; // Размер списка
    }

    // Переопределение метода toString для удобного отображения содержимого списка
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        LinkedNode<E> temp = head; // Временный указатель для итерации по списку
        for (int i = 0; i < size; i++) {
            sb.append(temp.element); // Добавляем элемент в строку
            if (i < size - 1) {
                sb.append(", "); // Добавляем запятую, если это не последний элемент
            }
            temp = temp.next; // Переходим к следующему узлу
        }
        sb.append(']');
        return sb.toString(); // Возвращаем строковое представление списка
    }

    // Метод добавляет элемент в конец списка
    @Override
    public boolean add(E e) {
        addLast(e); // Используем метод addLast для добавления в конец
        return true;
    }

    // Метод удаляет элемент по индексу и возвращает его
    public E remove(int index) {
        // Проверяем корректность индекса
        if (index < 0 || index >= size) {
            return null; // Если индекс вне диапазона, возвращаем null
        }

        LinkedNode<E> temp = head; // Временный указатель на узел
        // Находим узел по индексу
        for (int i = 0; i < index; i++) {
            temp = temp.next; // Переходим к следующему узлу
        }
        E e = temp.element; // Запоминаем элемент для возврата

        // Обновляем ссылки на соседние узлы
        if (temp.prev != null) {
            temp.prev.next = temp.next; // Удаляем узел из списка
        } else {
            head = temp.next; // Если удаляется голова, обновляем указатель на голову
        }

        if (temp.next != null) {
            temp.next.prev = temp.prev; // Удаляем узел из списка
        } else {
            tail = temp.prev; // Если удаляется хвост, обновляем указатель на хвост
        }

        size--; // Уменьшаем размер списка

        return e; // Возвращаем удаленный элемент
    }

    // Метод удаляет первый найденный элемент из списка
    @Override
    public boolean remove(Object o) {
        LinkedNode<E> temp = head; // Временный указатель на узел
        int index = 0; // Индекс текущего узла
        while (temp != null) {
            if (temp.element.equals(o)) { // Если нашли элемент
                remove(index); // Удаляем узел по индексу
                return true; // Возвращаем true, если элемент был удален
            }
            index++;
            temp = temp.next; // Переходим к следующему узлу
        }
        return false; // Возвращаем false, если элемент не найден
    }

    // Метод возвращает текущий размер списка
    @Override
    public int size() {
        return size; // Возвращаем размер
    }

    // Метод добавляет элемент в начало списка
    @Override
    public void addFirst(E e) {
        LinkedNode<E> node = new LinkedNode<>(e); // Создаем новый узел
        if (head != null) {
            node.next = head; // Устанавливаем следующий узел для нового узла
            head.prev = node; // Устанавливаем предыдущий узел для головы
        }
        head = node; // Обновляем указатель на голову

        if (tail == null) {
            tail = node; // Если список пуст, устанавливаем новый узел как хвост
        }

        size++; // Увеличиваем размер списка
    }

    // Метод добавляет элемент в конец списка
    @Override
    public void addLast(E e) {
        LinkedNode<E> node = new LinkedNode<>(e); // Создаем новый узел
        if (tail != null) {
            tail.next = node; // Устанавливаем следующий узел для хвоста
            node.prev = tail; // Устанавливаем предыдущий узел для нового узла
        }
        tail = node; // Обновляем указатель на хвост

        if (head == null) {
            head = node; // Если список пуст, устанавливаем новый узел как голову
        }

        size++; // Увеличиваем размер списка
    }

    // Метод возвращает первый элемент списка (без удаления)
    @Override
    public E element() {
        return getFirst(); // Возвращаем первый элемент
    }

    // Метод возвращает первый элемент списка (без удаления)
    @Override
    public E getFirst() {
        if (size == 0) {
            return null; // Если список пуст, возвращаем null
        }
        return head.element; // Возвращаем элемент головы
    }

    // Метод возвращает последний элемент списка (без удаления)
    @Override
    public E getLast() {
        if (size == 0) {
            return null; // Если список пуст, возвращаем null
        }
        return tail.element; // Возвращаем элемент хвоста
    }

    // Метод удаляет и возвращает первый элемент списка
    @Override
    public E poll() {
        return pollFirst(); // Используем метод pollFirst для удаления первого элемента
    }

    // Метод удаляет и возвращает первый элемент списка
    @Override
    public E pollFirst() {
        if (size == 0) {
            return null; // Если список пуст, возвращаем null
        }
        E e = head.element; // Запоминаем элемент для возврата
        head = head.next; // Сдвигаем голову вправо

        if (head != null) {
            head.prev = null; // Обнуляем указатель на предыдущий узел
        } else {
            tail = null; // Если голова стала null, обнуляем хвост
        }

        size--; // Уменьшаем размер списка
        return e; // Возвращаем удаленный элемент
    }

    // Метод удаляет и возвращает последний элемент списка
    @Override
    public E pollLast() {
        if (size == 0) {
            return null; // Если список пуст, возвращаем null
        }
        E e = tail.element; // Запоминаем элемент для возврата
        tail = tail.prev; // Сдвигаем хвост влево

        if (tail != null) {
            tail.next = null; // Обнуляем указатель на следующий узел
        } else {
            head = null; // Если хвост стал null, обнуляем голову
        }

        size--; // Уменьшаем размер списка
        return e; // Возвращаем удаленный элемент
    }

    // Далее идут методы-заглушки, которые можно дополнительно реализовать, но они не обязательны для базовой реализации

    @Override
    public boolean offerFirst(E e) {
        return false;
    }

    @Override
    public boolean offerLast(E e) {
        return false;
    }

    @Override
    public E removeFirst() {
        return null;
    }

    @Override
    public E removeLast() {
        return null;
    }

    @Override
    public E peekFirst() {
        return null;
    }

    @Override
    public E peekLast() {
        return null;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean offer(E e) {
        return false;
    }

    @Override
    public E remove() {
        return null;
    }

    @Override
    public E peek() {
        return null;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
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
    public void clear() {
        // Этот метод можно реализовать для очистки списка
    }

    @Override
    public void push(E e) {
        // Этот метод можно реализовать для добавления элемента в стек
    }

    @Override
    public E pop() {
        return null; // Этот метод можно реализовать для удаления элемента из стека
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false; // Можно реализовать для проверки наличия всех элементов
    }

    @Override
    public boolean contains(Object o) {
        return false; // Можно реализовать для проверки наличия элемента
    }

    @Override
    public boolean isEmpty() {
        return size == 0; // Проверяем, пуст ли список
    }

    @Override
    public Iterator<E> iterator() {
        return null; // Можно реализовать для создания итератора
    }

    @Override
    public Object[] toArray() {
        return new Object[0]; // Можно реализовать для преобразования списка в массив
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null; // Можно реализовать для преобразования списка в массив указанного типа
    }

    @Override
    public Iterator<E> descendingIterator() {
        return null; // Можно реализовать для создания итератора в обратном порядке
    }
}
