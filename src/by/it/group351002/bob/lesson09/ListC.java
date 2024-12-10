package by.it.group351002.bob.lesson09;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListC<E> implements List<E> {

    // Default list size
    static final int defSize = 8;

    // The array that holds the elements of the list
    E[] my_list;

    // Tracks the number of elements in the list
    int size = 0;

    // Constructor without arguments, initializes the list with the default size
    public ListC() {
        this(defSize);
    }

    // Constructor that allows specifying an initial size for the list
    public ListC(int size) {
        // Create an array with the specified size
        my_list = (E[]) new Object[size];
    }

    // Method to return a string representation of the list
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        // Loop through the elements of the list and append them to the StringBuilder
        for (int i = 0; i < size; i++) {
            sb.append(my_list[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    // Adds an element to the end of the list
    @Override
    public boolean add(E e) {
        // If the array is full, create a new array with double the size
        if (size >= my_list.length) {
            E[] new_list = (E[]) new Object[my_list.length * 2];
            for (int i = 0; i < my_list.length; i++) {
                new_list[i] = my_list[i];
            }
            my_list = new_list;
        }
        // Add the element and increment the size
        my_list[size] = e;
        size++;
        return true;
    }

    // Removes the element at the specified index
    @Override
    public E remove(int index) {
        // Check if the index is valid
        if (index < 0 || index >= size) {
            return null;
        }
        E removedItem = my_list[index];

        // Shift the elements to the left after removing the element
        for (int i = index; i < size - 1; i++) {
            my_list[i] = my_list[i + 1];
        }
        size--;
        return removedItem;
    }

    // Returns the number of elements in the list
    @Override
    public int size() {
        return size;
    }

    // Adds an element at the specified index, shifting elements if necessary
    @Override
    public void add(int index, E element) {
        // Check if the index is valid
        if (index < 0 || index > size) {
            return;
        }
        // If the array is full, create a new array with double the size
        if (size >= my_list.length) {
            E[] new_list = (E[]) new Object[my_list.length * 2];
            // Copy elements up to the index
            for (int i = 0; i < index; i++) {
                new_list[i] = my_list[i];
            }
            // Shift elements to the right
            for (int i = index + 1; i <= size; i++) {
                new_list[i] = my_list[i - 1];
            }
            my_list = new_list;
        } else {
            // Shift elements to the right if no resizing is needed
            for (int i = size; i > index; i--) {
                my_list[i] = my_list[i - 1];
            }
        }
        // Insert the element at the specified index and increment the size
        my_list[index] = element;
        size++;
    }

    // Removes the first occurrence of the specified element
    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (o.equals(my_list[i])) {
                // Shift elements to the left
                for (int j = i; j < size - 1; j++) {
                    my_list[j] = my_list[j + 1];
                }
                size--;
                return true;
            }
        }
        return false;
    }

    // Replaces the element at the specified index
    @Override
    public E set(int index, E element) {
        if (index < 0 || index >= size) {
            return null;
        }
        E oldItem = my_list[index];
        my_list[index] = element;
        return oldItem;
    }

    // Checks if the list is empty
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // Clears the list by resetting the array and size
    @Override
    public void clear() {
        my_list = (E[]) new Object[defSize];
        size = 0;
    }

    // Returns the index of the first occurrence of the specified element
    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (o.equals(my_list[i])) {
                return i;
            }
        }
        return -1;
    }

    // Returns the element at the specified index
    @Override
    public E get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return my_list[index];
    }

    // Checks if the list contains the specified element
    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            if (o.equals(my_list[i])) {
                return true;
            }
        }
        return false;
    }

    // Returns the index of the last occurrence of the specified element
    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) {
            if (o.equals(my_list[i])) {
                return i;
            }
        }
        return -1;
    }

    // Checks if the list contains all elements from the specified collection
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object item : c) {
            if (!contains(item)) {
                return false;
            }
        }
        return true;
    }

    // Adds all elements from the specified collection to the list
    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E item : c) {
            if (!add(item)) {
                return false;
            }
        }
        return true;
    }

    // Adds all elements from the specified collection to the list starting at the specified index
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        for (Object item : c) {
            add(index, (E) item);
            index++;
        }
        return true;
    }

    // Removes all elements from the list that are in the specified collection
    @Override
    public boolean removeAll(Collection<?> c) {
        boolean removed = false;
        for (int i = 0; i < size; i++) {
            if (c.contains(my_list[i])) {
                remove(i);
                i--; // adjust the index after removal
                removed = true;
            }
        }
        return removed;
    }

    // Retains only the elements in the list that are in the specified collection
    @Override
    public boolean retainAll(Collection<?> c) {
        boolean retained = false;
        for (int i = 0; i < size; i++) {
            if (!c.contains(my_list[i])) {
                remove(i);
                i--; // adjust the index after removal
                retained = true;
            }
        }
        return retained;
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Опциональные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    // Returns a sublist between the specified indices (not implemented)
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }

    // Returns a list iterator starting from the specified index (not implemented)
    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    // Returns a list iterator for the list (not implemented)
    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    // Converts the list to an array (not implemented)
    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    // Converts the list to an object array (not implemented)
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

    // Returns an iterator for the list (not implemented)
    @Override
    public Iterator<E> iterator() {
        return null;
    }

}
