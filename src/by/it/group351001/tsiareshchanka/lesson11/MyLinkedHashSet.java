package by.it.group351001.tsiareshchanka.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyLinkedHashSet<E> implements Set<E> {
    private Node<E>[] table;
    private E[] orderedElements; // Массив для хранения элементов в порядке добавления
    private int size;
    private int capacity;

    private static class Node<E> {
        E data;
        Node<E> next;

        public Node(E data) {
            this.data = data;
        }
    }

    public MyLinkedHashSet() {
        table = new Node[16]; // начальный размер массива
        capacity = 16; // емкость для orderedElements
        orderedElements = (E[]) new Object[capacity]; // инициализация массива
        size = 0;
    }

    public int size() {
        return size;
    }

    public void clear() {
        table = new Node[table.length];
        orderedElements = (E[]) new Object[capacity];
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean add(E element) {
        int index = getIndex(element);
        Node<E> node = table[index];

        while (node != null) {
            if (node.data.equals(element)) {
                return false;
            }
            node = node.next;
        }

        Node<E> newNode = new Node<>(element);
        newNode.next = table[index];
        table[index] = newNode;

        if (size == orderedElements.length) {
            increaseCapacity();
        }
        orderedElements[size] = element;
        size++;
        return true;
    }

    private void increaseCapacity() {
        int newCapacity = capacity * 2;
        E[] newOrderedElements = (E[]) new Object[newCapacity];
        System.arraycopy(orderedElements, 0, newOrderedElements, 0, capacity);
        orderedElements = newOrderedElements;
        capacity = newCapacity;
    }

    public boolean remove(Object o) {
        int index = getIndex(o);
        Node<E> node = table[index];
        Node<E> prev = null;

        while (node != null) {
            if (node.data.equals(o)) {
                if (prev == null) {
                    table[index] = node.next;
                } else {
                    prev.next = node.next;
                }

                removeFromOrderedElements(o);
                size--;
                return true;
            }
            prev = node;
            node = node.next;
        }
        return false;
    }

    private void removeFromOrderedElements(Object o) {
        for (int i = 0; i < size; i++) {
            if (orderedElements[i].equals(o)) {
                System.arraycopy(orderedElements, i + 1, orderedElements, i, size - i - 1);
                orderedElements[size - 1] = null; // Убираем ссылку на последний элемент
                break;
            }
        }
    }

    public boolean contains(Object o) {
        int index = getIndex(o);
        Node<E> node = table[index];

        while (node != null) {
            if (node.data.equals(o)) {
                return true;
            }
            node = node.next;
        }
        return false;
    }

    private int getIndex(Object o) {
        int hashCode = o.hashCode();
        return Math.abs(hashCode) % table.length;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(orderedElements[i]);
        }
        sb.append("]");
        return sb.toString();
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
    public Object[] toArray(Object[] a) {
        return new Object[0];
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object element : c) {
            if (!contains(element)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for (E element : c) {
            if (add(element)) {
                modified = true; // хотя бы один элемент был добавлен
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        for (int i = 0; i < size; i++) {
            if (!c.contains(orderedElements[i])) {
                remove(orderedElements[i]); // Удаляем элемент, если его нет в `c`
                modified = true;
                i--; // Уменьшаем индекс, так как элемент удален
            }
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object element : c) {
            if (remove(element)) {
                modified = true; // хотя бы один элемент был удален
            }
        }
        return modified;
    }
}
