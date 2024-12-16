package by.it.group351002.alexeichik.lesson09;

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
    E[]list;
    static final int initSize = 3;
    int size = 0;
    public ListC(){this(initSize);}
    public ListC(int size){list = (E[]) new Object[size];
    };
    @Override
    public String toString(){
        StringBuilder sb=new StringBuilder();
        sb.append("[");
        for (int i =0;i<size;i++){
            sb.append(list[i]);
            if (i<size - 1)
                sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean add(E e) {
        if (list.length == size){
            E[] newList;
            newList = (E[])new Object[size*2];
            for (int i = 0;i < size;i++)
                newList[i] = list[i];
            newList[size] = e;
            list = newList;

        }
        else
            list[size] = e;
        size++;
        return true;
    }

    @Override
    public E remove(int index){
        if (index < 0 || index>size-1)
            return null;
        E el = list[index] ;
        for (int i = index;i<size-1;i++)
            list[i] = list[i+1];
        size--;
        return el;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void add(int index, E element) {
        if (list.length == size){
            E[] newList;
            newList = (E[])new Object[size*2];
            for (int i = 0;i < size;i++)
                newList[i] = list[i];
            list = newList;

        }
        size++;
        for (int i = size-1;i>index;i--)
            list[i]=list[i-1];
        list[index]=element;
    }

    @Override
    public boolean remove(Object o) {
        int i = 0;
        while (i<size) {
            if (o.equals(list[i])) {
                for (int j = i; j < size - 1; j++)
                    list[j] = list[j + 1];
                size--;
                return true;

            }
            else
                i++;
        }
        return false;
    }
    @Override
    public E set(int index, E element) {
        if (index < 0 || index>size-1)
            return null;
        E prevEl = list[index];
        list[index] = element;
        return prevEl;
    }


    @Override
    public boolean isEmpty() {
        if (size == 0)
            return true;
        return false;
    }


    @Override
    public void clear() {
        int i = 0;
        while (i<size) {
            for (int j = i; j < size - 1; j++)
                list[j] = list[j + 1];
            size--;
        }
    }

    @Override
    public int indexOf(Object o) {
        for (int i =0;i<size;i++)
            if (o.equals(list[i]))
                return i;
        return -1;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index>size-1)
            return null;
        return list[index];
    }

    @Override
    public boolean contains(Object o) {
        for (int i =0;i<size;i++)
            if (o.equals(list[i]))
                return true;
        return false;
    }

    @Override
    public int lastIndexOf(Object o) {
        int highInd = -1;
        for (int i =0;i<size;i++) {
            if (o.equals(list[i]) && highInd<i)
                highInd = i;

        }
        return highInd;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object obj:c)
            if (!contains(obj))
                return false;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E obj:c) {
            if (!add(obj))
                return false;
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        int start = index;
        for (E obj:c){
            add(index,obj);
            index++;
        }
        if (index==start)
            return false;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean flag = false;
        int i =0;
        while (i<size) {
            if (c.contains(list[i])) {
                remove(i);
                if (!flag)
                    flag = true;
            } else
                i++;
        }
        return flag;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean flag = false;
        int i =0;
        while (i<size) {
            if (!c.contains(list[i])) {
                remove(i);
                if (!flag)
                    flag = true;
            } else
                i++;
        }
        return flag;
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
