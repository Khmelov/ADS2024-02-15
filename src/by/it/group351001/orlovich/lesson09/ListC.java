package by.it.group351001.orlovich.lesson09;

import java.util.*;

public class ListC<E> implements List<E> {

    private static int incrementSize = 10;
    private int currSize = 0;
    private E[] mas = (E[])new Object[0];
    //Создайте аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < currSize; i++) {
            str.append(mas[i] + ", ");
        }

        String ans = str.toString();

        if (ans.length() > 0)
        {
            ans = ans.substring(0,ans.length() - 2);
        }

        return "[" + ans + "]";
    }

    @Override
    public boolean add(E e) {
        int index = currSize;
        if (currSize == mas.length)
        {
            var tempMas = mas;
            mas = (E[]) new  Object[currSize + incrementSize];

            for (int i = 0; i < currSize; i++) {
                mas[i] = tempMas[i];
            }

        }

        mas[index] = e;
        currSize++;

        return 1 == 1;
    }

    @Override
    public E remove(int index) {
        if (index >= currSize || index < 0) return null;

        var tempMas = mas;

        E result = mas[index];

        for (int i = index + 1; i < currSize; i++) {
            mas[i - 1] = tempMas[i];
        }

        currSize--;
        return  result;
    }

    @Override
    public int size() {
        return currSize;
    }

    @Override
    public void add(int index, E element) {
        if (currSize == mas.length)
        {
            mas = (E[]) new Object[currSize + incrementSize];
        }

        for (int i = currSize; i > index; i--) {
            mas[i] = mas[i - 1];
        }

        mas[index] = element;
        currSize++;
    }

    @Override
    public boolean remove(Object o) {
        int index = this.indexOf(o);

        if (index == -1)
        {
            return false;
        }
        else
        {
            this.remove(index);
            return  1 == 1;
        }
    }

    @Override
    public E set(int index, E element) {
        E result = mas[index];
        mas[index] = element;
        return result;
    }


    @Override
    public boolean isEmpty() {
        return currSize == 0;
    }


    @Override
    public void clear() {
        currSize = 0;
    }

    @Override
    public int indexOf(Object o) {
        int result = -1;

        for (int i = 0; i < currSize; i++) {
            if (o.equals(mas[i])){
                result =  i;
                break;
            }
        }

        return result;
    }

    @Override
    public E get(int index) {
        return mas[index];
    }

    @Override
    public boolean contains(Object o) {
        return this.indexOf(o) != -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int result = -1;
        for (int i = currSize - 1; i >= 0;i--)
        {
            if (o.equals(mas[i]))
            {
                result = i;
                break;
            }
        }
        return result;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
    boolean result = true;
    for(Object item :c) {
        result &= this.contains(item);
    }
        return result;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {

        for(Object item :c) {
            this.add((E)item);
        }

        return 1 == 1;
    }
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (c.size() == 0) return false;
        if (mas.length < currSize + c.size())
        {
            int prev = mas.length;
            if (prev > 0) {
                mas = Arrays.copyOf(mas, prev + incrementSize + c.size());
            }
            else {
                 mas = (E[])new Object[prev + incrementSize + c.size()];
            }
        }
        System.arraycopy(mas, index, mas, index + c.size(), currSize - index);
        int insIndex = index;
        for (E e : c) {
            mas[insIndex] = e;
            insIndex++;
        }
        currSize += c.size();
        return 1 == 1;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Object[] newArr = c.toArray();

        for(int i = 0; i < newArr.length; i++){
            int index = indexOf(newArr[i]);
            if (index >= 0){
                remove(index);
                i--;
            }
        }
        return 1 == 1;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean result = false;
        for (int i = 0; i < currSize; i++) {
            if (!c.contains(mas[i])) {
                remove(mas[i]);
                i--;
                result = true;
            }
        }
        return result;
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
