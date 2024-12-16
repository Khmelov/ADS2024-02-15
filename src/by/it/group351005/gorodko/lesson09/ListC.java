package by.it.group351005.gorodko.lesson09;

import java.util.*;

public class ListC<E> implements List<E> {

    //Создайте аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    private int size = 0;
    private E[] arr = (E[]) new Object[size];
    @Override
    public String toString() {
        String res = "[";
        for (int i = 0; i < size - 1; i++) {
            res = res + arr[i].toString() + ", ";
        }
        if (size != 0) {
            res = res + arr[size - 1].toString();
        }
        return res + "]";
    }
    @Override
    public boolean add(E e) {
        E[] newarr = (E[]) new Object[size + 1];
        for (int i = 0; i < size; i++) {
            newarr[i] = arr[i];
        }
        newarr[size] = e;
        arr = newarr;
        size++;
        return true;
    }

    @Override
    public E remove(int index) {
        E res = arr[index];
        E[] newarr = (E[]) new Object[size - 1];
        for (int i = 0; i < index; i++) {
            newarr[i] = arr[i];
        }
        for (int i = index; i < size - 1; i++) {
            newarr[i] = arr[i + 1];
        }
        arr = newarr;
        size--;
        return res;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void add(int index, E element) {
        E[] newarr = (E[]) new Object[size + 1];
        for (int i = 0; i < index; i++) {
            newarr[i] = arr[i];
        }
        newarr[index] = element;
        for (int i = index; i < size; i++) {
            newarr[i + 1] = arr[i];
        }
        arr = newarr;
        size++;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index != -1) {
            E[] newarr = (E[]) new Object[size - 1];
            for (int i = 0; i < index; i++) {
                newarr[i] = arr[i];
            }
            for (int i = index; i < size - 1; i++) {
                newarr[i] = arr[i + 1];
            }
            arr = newarr;
            size--;
            return true;
        }
        return false;
    }

    @Override
    public E set(int index, E element) {
        E temp = arr[index];
        arr[index] = element;
        return temp;
    }


    @Override
    public boolean isEmpty() {
        return size == 0;
    }


    @Override
    public void clear() {
        arr = null;
        size = 0;
    }

    @Override
    public int indexOf(Object o) {
        int res = -1;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(arr[i], o)) {
                res = i;
                break;
            }
        }
        return res;
    }

    @Override
    public E get(int index) {
        return arr[index];
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(arr[i], o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int lastIndexOf(Object o) {
        int res = -1;
        for (int i = size - 1; i >= 0; i--) {
            if (Objects.equals(arr[i], o)) {
                res = i;
                break;
            }
        }
        return res;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        boolean doesContain;
        for (Object o: c) {
            doesContain = false;
            for (int i = 0; i < size; i++) {
                if (Objects.equals(arr[i], o)) {
                    doesContain = true;
                    break;
                }
            }
            if (!doesContain) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean isAdded = false;
        for (Object o: c) {
            add((E)o);
            isAdded = true;
        }
        return isAdded;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        boolean isAdded = false;
        for (Object o: c) {
            add(index, (E)o);
            index++;
            isAdded = true;
        }
        return isAdded;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean isRemoved = false;
        for (int i = 0; i < size; i++) {
            if (c.contains(arr[i])) {
                remove(i);
                i--;
                isRemoved = true;
            }
        }
        return isRemoved;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean isRetained = false;
        for (int i = 0; i < size; i++) {
            if (!c.contains(arr[i])) {
                remove(i);
                i--;
                isRetained = true;
            }
        }
        return isRetained;
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
