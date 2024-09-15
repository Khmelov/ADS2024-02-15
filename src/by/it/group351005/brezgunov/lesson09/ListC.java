package by.it.group351005.brezgunov.lesson09;

import java.util.*;

public class ListC<E> implements List<E> {

    //Создайте аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    final int DEFAULT_CAPACITY = 10;
    private int size;
    private Object[] arr;

    ListC() {
        this.arr = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        StringBuilder stringBuilder = new StringBuilder("[");
        for (int i = 0; i < size - 1; i++) {
            stringBuilder.append(arr[i].toString()).append(", ");
        }
        stringBuilder.append(arr[size - 1].toString()).append("]");
        return stringBuilder.toString();
    }

    @Override
    public boolean add(E e) {
        if (size == arr.length) {
            arr = Arrays.copyOf(arr, arr.length + (arr.length >> 1));
        }
        arr[size++] = e;
        return true;
    }

    @Override
    public E remove(int index) {
        E temp = (E) arr[index];
        for (int i = index + 1; i < size; i++) {
            arr[i - 1] = arr[i];
        }
        arr[--size] = null;
        return temp;
    }

    @Override
    public int size() {
        return size;
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Опциональные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    @Override
    public void add(int index, E element) {
        if (size == arr.length) {
            arr = Arrays.copyOf(arr, arr.length + (arr.length >> 1));
        }
        System.arraycopy(arr, index, arr, index + 1, size++ - index);
        arr[index] = element;
    }

    @Override
    public boolean remove(Object o) {
        boolean hasObject = false;
        for (int i = 0; i < size && !hasObject; i++) {
            if (arr[i].equals(o)) {
                System.arraycopy(arr, i + 1, arr, i, size-- - i);
                hasObject = true;
            }
        }
        return hasObject;
    }

    @Override
    public E set(int index, E element) {
        E temp = (E) arr[index];
        arr[index] = element;
        return temp;
    }


    @Override
    public boolean isEmpty() {
        return size == 0;
    }


    @Override
    public void clear() {
        size = 0;
        arr = new Object[DEFAULT_CAPACITY];
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (arr[i].equals(o)) {
                return i;
            }
        }
        return - 1;
    }

    @Override
    public E get(int index) {
        return (E) arr[index];
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            if (arr[i].equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) {
            if (arr[i].equals(o)) {
                return i;
            }
        }
        return - 1;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object object : c) {
            if (!this.contains(object)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (Object object : c) {
            this.add((E) object);
        }
        return !c.isEmpty();
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        for (Object object : c) {
            this.add(index++, (E) object);
        }
        return !c.isEmpty();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean hasChanged = false;
        for (Object object : c) {
            for (int i = 0; i < size; i++) {
                if (arr[i].equals(object)) {
                    this.remove(i--);
                    hasChanged = true;
                }
            }
        }
        return hasChanged;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean hasChanged = false;
        for (int i = 0; i < size; i++) {
            if (!c.contains(arr[i])) {
                remove(i--);
                hasChanged = true;
            }
        }
        return hasChanged;
    }


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
        Math.min(1, 2);
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
