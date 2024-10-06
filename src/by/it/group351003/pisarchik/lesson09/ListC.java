package by.it.group351003.pisarchik.lesson09;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListC<E> implements List<E> {

    //Создайте аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    private class Node {
        E data;
        Node next;

        Node(E data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head;

    public void ListC() {
        head = null;
    }

    @Override
    public String toString() {
        String s = "[";
        Node current = head;
        while (current != null) {
            s += String.valueOf(current.data);
            if (current.next != null) {
                s += ", ";
            }
            current = current.next;
        }
        s += "]";
        return s;
    }

    @Override
    public boolean add(E e) {
        Node newNode = new Node(e);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        return true;
    }

    @Override
    public E remove(int index) {
        if (index >= 0 || index < size()) {
            Node current = head;
            if (index == 0) {
                E data = head.data;
                head = head.next;
                return data;
            }

            Node prev = null;
            for (int i = 0; i < index; i++) {
                prev = current;
                current = current.next;
            }
            prev.next = current.next;
            return current.data;

        }
        return null;
    }

    @Override
    public int size() {
        int size = 0;
        Node current = head;
        while (current != null) {
            size++;
            current = current.next;
        }
        return size;
    }

    @Override
    public void add(int index, E element) {
        if (index < size()) {
            Node newNode = new Node(element);
            if (index == 0) {
                newNode.next = head;
                head = newNode;
            } else {
                Node current = head;
                Node prev = null;
                for (int i = 0; i < index; i++) {
                    prev = current;
                    current = current.next;
                }
                newNode.next = current;
                prev.next = newNode;
            }
        } else {
            add(element);
        }
    }

    @Override
    public boolean remove(Object o) {
        if (head != null && head.data.equals(o)) {
            head = head.next;
            return true;
        }

        Node current = head;
        Node prev = null;

        while (current != null) {
            if (current.data.equals(o)) {
                prev.next = current.next;
                return true;
            }
            prev = current;
            current = current.next;
        }

        return false;
    }

    @Override
    public E set(int index, E element) {
        if (index < size() || index > 0) {
            Node current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            E data = current.data;
            current.data = element;
            return data;
        }
        return null;
    }


    @Override
    public boolean isEmpty() {
        return head == null;
    }


    @Override
    public void clear() {
        head = null;
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        Node current = head;
        while (current != null) {
            if (current.data.equals(o)) {
                return index;
            }
            index++;
            current = current.next;
        }
        return -1;
    }

    @Override
    public E get(int index) {
        if (index < size() || index > 0) {
            Node current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current.data;
        }
        return null;
    }

    @Override
    public boolean contains(Object o) {
        Node current = head;
        while (current != null) {
            if (current.data.equals(o)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public int lastIndexOf(Object o) {
        int i = 0, index = -1;
        Node current = head;
        while (current != null) {
            if (current.data.equals(o)) {
                index = i;
            }
            i++;
            current = current.next;
        }
        return index;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        boolean isContains = true;
        for (Object elem : c) {
            isContains = isContains && contains(elem);
        }
        return isContains;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (Object elem : c) {
            add((E) elem);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        for (Object elem : c) {
            add(index,(E)elem);
            index++;
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object elem : c) {
            while (contains(elem)) {
                remove(elem);
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        Node current = head;
        Node prev = null;

        while (current != null) {
            if (!c.contains(current.data)) {
                if (prev == null) {
                    head = current.next;
                } else {
                    prev.next = current.next;
                }
                modified = true;
            } else {
                prev = current;
            }
            current = current.next;
        }

        return modified;
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
