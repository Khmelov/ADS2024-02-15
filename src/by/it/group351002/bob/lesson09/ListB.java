package by.it.group351002.bob.lesson09;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListB<E> implements List<E> {

    // Создайте аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ
    // This is another custom implementation of a List without using the standard library.

    // Default size for the internal array
    static final int defSize = 8;

    // Internal array to store the elements
    E[] my_list;

    // Number of elements in the list
    int size = 0;

    // Constructor (default size)
    public ListB() {
        this(defSize);
    }

    // Constructor with custom initial size
    public ListB(int size) {
        my_list = (E[]) new Object[size];
    }

    @Override
    public String toString() {
        // Returns the string representation of the list in the format: [element1, element2, ...]
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; ++i) {
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
        // Adds a new element to the list, and resizes the internal array if it's full
        if (size >= my_list.length) {
            // Double the internal array size if it's full
            E[] new_list = (E[]) new Object[my_list.length * 2];
            for (int i = 0; i < my_list.length; ++i) {
                new_list[i] = my_list[i];
            }
            my_list = new_list;
        }
        // Add the new element at the end of the list
        my_list[size] = e;
        size++;
        return true;
    }

    @Override
    public E remove(int index) {
        // Removes the element at the specified index and shifts the remaining elements to the left
        if (index < 0 || index >= size) {
            return null;
        }

        // Store the element to be removed
        E _rItem = my_list[index];

        // Shift elements to the left
        for (int i = index; i < size - 1; ++i) {
            my_list[i] = my_list[i + 1];
        }

        size--;
        return _rItem;
    }

    @Override
    public int size() {
        // Returns the number of elements in the list
        return size;
    }

    @Override
    public void add(int index, E element) {
        // Adds an element at the specified index
        if (index < 0 || index > size) {
            return;
        }

        if (size >= my_list.length) {
            // Resize array if needed
            E[] _cList = (E[]) new Object[my_list.length * 2];
            for (int i = 0; i < index; ++i) {
                _cList[i] = my_list[i];
            }

            for (int i = index + 1; i < size; ++i) {
                _cList[i] = my_list[i - 1];
            }

            my_list = _cList;
        } else {
            // Shift elements to the right
            for (int i = size; i > index; i--) {
                my_list[i] = my_list[i - 1];
            }
        }

        // Insert the new element
        my_list[index] = element;
        size++;
    }

    @Override
    public boolean remove(Object o) {
        // Removes the first occurrence of the specified object
        for (int i = 0; i < size; ++i) {
            if (o.equals(my_list[i])) {
                // Shift elements to the left after removal
                for (int j = i; j < size; ++j) {
                    my_list[j] = my_list[j + 1];
                }
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public E set(int index, E element) {
        // Replaces the element at the specified index and returns the old element
        if (index < 0 || index > size) {
            return null;
        }
        E _setItem = my_list[index];
        my_list[index] = element;
        return _setItem;
    }

    @Override
    public boolean isEmpty() {
        // Returns true if the list is empty
        return size == 0;
    }

    @Override
    public void clear() {
        // Clears the list
        my_list = (E[]) new Object[defSize];
        size = 0;
    }

    @Override
    public int indexOf(Object o) {
        // Returns the index of the first occurrence of the specified element, or -1 if it's not found
        for (int i = 0; i < size; ++i) {
            if (o.equals(my_list[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public E get(int index) {
        // Returns the element at the specified index
        if (index < 0 || index >= size) {
            return null;
        }
        return my_list[index];
    }

    @Override
    public boolean contains(Object o) {
        // Checks if the list contains the specified element
        for (int i = 0; i < size; ++i) {
            if (o.equals(my_list[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int lastIndexOf(Object o) {
        // Returns the index of the last occurrence of the specified element, or -1 if it's not found
        for (int i = size - 1; i >= 0; --i) {
            if (o.equals(my_list[i])) {
                return i;
            }
        }
        return -1;
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Optional methods (not fully implemented)    ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    @Override
    public boolean containsAll(Collection<?> c) {
        // Checks if the list contains all elements in the specified collection (not implemented)
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        // Adds all elements from the specified collection to the list (not implemented)
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        // Adds all elements from the specified collection at the specified index (not implemented)
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        // Removes all elements in the specified collection from the list (not implemented)
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        // Retains only the elements that are in the specified collection (not implemented)
        return false;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        // Returns a sublist from the specified range (not implemented)
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        // Returns a list iterator starting at the specified index (not implemented)
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

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    ////////        These methods are optional but useful for debugging  ////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    @Override
    public Iterator<E> iterator() {
        // Returns an iterator over the elements in this list (not implemented)
        return null;
    }

}
