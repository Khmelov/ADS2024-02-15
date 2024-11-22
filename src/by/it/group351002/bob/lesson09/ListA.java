package by.it.group351002.bob.lesson09;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListA<E> implements List<E> {

    // Создайте аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ
    // This is a custom implementation of a List without using Java's standard library List implementations.

    // Default size for the list's internal array
    static final int defSize = 8;

    // Internal array to store the elements
    E[] my_list;

    // Number of elements currently in the list
    int size = 0;

    // Constructor (default size)
    public ListA() {
        this(defSize);
    }

    // Constructor with specified initial size
    public ListA(int size) {
        // The internal array is created with the specified size
        my_list = (E[]) new Object[size];
    }

    @Override
    public String toString() {
        // Returns the string representation of the list in the format: [element1, element2, ...]
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
            sb.append(my_list[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean add(E e) {
        // Adds a new element to the list and resizes the internal array if it's full

        if (size >= my_list.length) {
            // Double the size of the internal array if it's full
            E[] new_list = (E[]) new Object[my_list.length * 2];
            for (int i = 0; i < my_list.length; i++) {
                new_list[i] = my_list[i];
            }
            my_list = new_list;
        }
        // Add the new element to the end of the list
        my_list[size] = e;
        size++;
        return true;
    }

    @Override
    public E remove(int index) {
        // Removes the element at the specified index and shifts the remaining elements to the left

        if (index < 0 || index >= size) {
            // Return null if the index is invalid
            return null;
        }

        // Store the element to be removed
        E _rItem = my_list[index];

        // Shift elements to the left
        for (int i = index; i < size - 1; ++i) {
            my_list[i] = my_list[i + 1];
        }

        // Decrease the size of the list
        size--;

        // Return the removed element
        return _rItem;
    }

    @Override
    public int size() {
        // Returns the number of elements in the list
        return size;
    }

    // Below are optional methods that are not fully implemented, but are part of the List interface.

    @Override
    public void add(int index, E element) {
        // Adds an element at the specified index (not implemented)
    }

    @Override
    public boolean remove(Object o) {
        // Removes the first occurrence of the specified element (not implemented)
        return false;
    }

    @Override
    public E set(int index, E element) {
        // Replaces the element at the specified index (not implemented)
        return null;
    }

    @Override
    public boolean isEmpty() {
        // Checks if the list is empty (not implemented)
        return false;
    }

    @Override
    public void clear() {
        // Clears the list (not implemented)
    }

    @Override
    public int indexOf(Object o) {
        // Returns the index of the first occurrence of the specified element (not implemented)
        return 0;
    }

    @Override
    public E get(int index) {
        // Returns the element at the specified index (not implemented)
        return null;
    }

    @Override
    public boolean contains(Object o) {
        // Checks if the list contains the specified element (not implemented)
        return false;
    }

    @Override
    public int lastIndexOf(Object o) {
        // Returns the index of the last occurrence of the specified element (not implemented)
        return 0;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        // Checks if the list contains all the elements in the specified collection (not implemented)
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        // Adds all the elements in the specified collection to the list (not implemented)
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        // Adds all the elements in the specified collection at the specified index (not implemented)
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        // Removes all the elements in the specified collection from the list (not implemented)
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        // Retains only the elements that are also in the specified collection (not implemented)
        return false;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        // Returns a view of the portion of this list between fromIndex, inclusive, and toIndex, exclusive (not implemented)
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        // Returns a list iterator over the elements in this list, starting at the specified position (not implemented)
        return null;
    }

    @Override
    public ListIterator<E> listIterator() {
        // Returns a list iterator over the elements in this list (not implemented)
        return null;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        // Returns an array containing all the elements in this list (not implemented)
        return null;
    }

    @Override
    public Object[] toArray() {
        // Returns an array containing all the elements in this list (not implemented)
        return new Object[0];
    }

    @Override
    public Iterator<E> iterator() {
        // Returns an iterator over the elements in this list (not implemented)
        return null;
    }

}
