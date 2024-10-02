package by.it.group351005.bitno.lesson09;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListA<E> implements List<E> {

    //Создайте аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////


   private class ListElem{
        private E value;
        private ListElem nextElem = null;
    }

    private ListElem listHead = null;
    @Override
    public String toString() {
        StringBuilder answer = new StringBuilder("");
        ListElem curElem = listHead;
        answer.append('[');
        while (curElem != null){
            answer.append(curElem.value.toString());
            curElem = curElem.nextElem;
            if (curElem != null){
                answer.append(", ");
            }
        }
        answer.append(']');
        return answer.toString();
    }

    @Override
    public boolean add(E e) {
        ListElem newElem = new ListElem();
        newElem.value = e;
        ListElem curElem = listHead;
        if (listHead == null){
            listHead = newElem;
        }
        else {
            while (curElem.nextElem != null){
                curElem = curElem.nextElem;
            }
            curElem.nextElem = newElem;
        }
        return true;
    }

    @Override
    public E remove(int index) {
        E result;
        ListElem curElem = listHead;
        if (index == 0){
            result = listHead.value;
            listHead = listHead.nextElem;
        }
        else{
            for (int i = 0; i < index - 1; i++) {
                curElem = curElem.nextElem;
                if (curElem == null){
                    result = null;
                }
            }
            result = (E) curElem.nextElem.value;
            curElem.nextElem = curElem.nextElem.nextElem;
        }
        return (E) result;
    }

    @Override
    public int size() {
        int counter = 0;
        ListElem curElem = listHead;
        while (curElem != null){
            counter++;
            curElem = curElem.nextElem;
        }
        return counter;
    }

    @Override
    public void add(int index, E element) {
        ListElem newElem = new ListElem();
        newElem.value = element;
        ListElem curElem = listHead;
        if (index == 0){
            newElem.nextElem = listHead;
            listHead = newElem;
        }
        else {
            for (int i = 0; i < index - 1; i++) {
                curElem = curElem.nextElem;
            }
            newElem.nextElem = curElem.nextElem;
            curElem.nextElem = newElem;
        }
    }

    @Override
    public boolean remove(Object o) {
        ListElem curElem = listHead;
        if (o.equals(curElem.value)){
            listHead = listHead.nextElem;
            return true;
        }
        while (curElem.nextElem != null){
            if (o.equals(curElem.nextElem.value)){
                curElem.nextElem = curElem.nextElem.nextElem;
                return true;
            }
            curElem = curElem.nextElem;
        }
        return false;
    }

    @Override
    public E set(int index, E element) {
        ListElem curElem = listHead;
        for (int i = 0; i < index; i++) {
            curElem = curElem.nextElem;
        }
        E answer = curElem.value;
        curElem.value = element;
        return answer;
    }


    @Override
    public boolean isEmpty() {
        return listHead == null;
    }


    @Override
    public void clear() {
        listHead = null;
    }

    @Override
    public int indexOf(Object o) {
        ListElem curElem = listHead;
        int index = -1;
        while (curElem != null){
            index++;
            if (o.equals(curElem.value)){
                return index;
            }
            curElem = curElem.nextElem;
        }
        return -1;
    }

    @Override
    public E get(int index) {
        ListElem curElem = listHead;
        for (int i = 0; i < index; i++) {
            curElem = curElem.nextElem;
        }
        return curElem.value;
    }

    @Override
    public boolean contains(Object o) {
        ListElem curElem = listHead;
        while (curElem != null){
            if (o.equals(curElem.value)){
                return true;
            }
            curElem = curElem.nextElem;
        }
        return false;
    }

    @Override
    public int lastIndexOf(Object o) {
        int answer = -1;
        int counter = 0;
        ListElem curElem = listHead;
        while (curElem != null){
            if (o.equals(curElem.value)){
                answer = counter;
            }
            curElem = curElem.nextElem;
            counter++;
        }
        return answer;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        ListElem curElem = listHead;
        E[] collectionAsArray = (E[]) c.toArray();
        for (E e : collectionAsArray) {
            if (!contains(e)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        E[] collectionAsArray = (E[]) c.toArray();
        for (int i = 0; i < collectionAsArray.length; i++) {
            add(collectionAsArray[i]);
        }
        return collectionAsArray.length > 0;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        E[] collectionAsArray = (E[]) c.toArray();
        for (int i = 0; i < collectionAsArray.length; i++) {
            add(index, collectionAsArray[i]);
            index++;
        }
        return collectionAsArray.length > 0;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        ListElem curElem = listHead;
        boolean answer = false;
        while (curElem != null){
            if (c.contains(curElem.value)){
                answer = true;
                remove(curElem.value);
                curElem = listHead;
                continue;
            }
            curElem = curElem.nextElem;
        }
        return answer;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean answer = false;
        ListElem curElem = listHead;
        while (curElem != null){
            if (!c.contains(curElem.value)){
                answer = true;
                remove(curElem.value);
                curElem = listHead;
                continue;
            }
            curElem = curElem.nextElem;
        }
        return answer;
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
