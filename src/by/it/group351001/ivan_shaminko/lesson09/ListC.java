package by.it.group351001.ivan_shaminko.lesson09;

import java.util.*;

public class ListC<E> implements List<E> {

    //Создайте аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ

    private int size = 0;
    private E[] arr = (E[]) new Object[size]; // возможно надо поменять

    //------------------------------------------------------------------------------
    //ВО ВСЕХ МЕТОДАХ КУДА ПЕРЕДАЁТСЯ ОБЪЕКТ ИЛИ ИНДЕКС ПРИНЯТО, ЧТО ОНИ СУЩЕСТВУЮТ
    //------------------------------------------------------------------------------
    @Override
    public String toString() {
        String myString = "[";

        for (int i = 0; i < size - 1; i++){
            myString += arr[i] + ", ";
        }
        if (size > 0) {
            myString += arr[size - 1] + "]";
        }
        else{
            return myString + "]";
        }

        return myString;
    }

    @Override
    public boolean add(E e) {
        if(size == arr.length){
            E[] temp = (E[])new Object[(size >> 1) + size + 1];
            System.arraycopy(arr, 0, temp, 0, size);
            arr = temp;
        }
        arr[size++] = e;
        return true;
    }

    @Override
    public E remove(int index) {
        E tempElement = arr[index];
        if (index > size - 1) {
            return  null;
        }
        else {
            System.arraycopy(arr, index + 1, arr, index, size - index - 1);
            arr[--size]= null;
        }
        return tempElement;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void add(int index, E element) {
        E[] temp = (E[]) new Object[(size >> 1) + size + 1];
        System.arraycopy(arr, 0, temp, 0, index);
        System.arraycopy(arr, index, temp, index + 1, (size++) - index);
        arr = temp;
        arr[index] = element;
    }


    @Override
    public boolean remove(Object o) { //сделано через циклы, может НЕ РАБОТАТЬ
        boolean res = false;
        for (int index = 0; index < size; index++){
            if (o.equals(arr[index])){
                System.arraycopy(arr, index + 1, arr, index, size - index - 1);
                arr[--size]= null;
                return true;
            }
        }
        return res;
    }

    @Override
    public E set(int index, E element) { //случаи выхода за пределы игнорируются (index > size)
        E temp = arr[index]; //возврат старого
        arr[index] = element;
        return temp;
    }


    @Override
    public boolean isEmpty() {
        return size == 0;
    }


    @Override
    public void clear() {
        for (int i = 0; i < size; i++){
            arr[i] = null;
        }
        size = 0;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (o.equals(arr[i])){
                return i;
            }
        }
        return -1;
    }

    @Override
    public E get(int index) {
        return arr[index];
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            if (o.equals(arr[i])){
                return true;
            }
        }
        return false;
    }

    //1024
    @Override
    public int lastIndexOf(Object o) {
        if (o == null)
            for (int i = size - 1; i >= 0; i--) {
                if (arr[i] == null)
                    return i;
            }

        for (int i = size - 1; i >= 0; i--){
            if (o.equals(arr[i])){
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        boolean res = true;
        for (Object obj : c) //toArray?
            res = res && this.contains(obj); //логическое И для проверки всех
        return res;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) { // сделано только под валидные условия
        for (Object obj : c.toArray()){
            add((E) obj);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {

        Objects.checkIndex(index, size + 1);
        Object[] newArr = c.toArray();
        if(newArr.length == 0){
            return false;
        }
        E[] temp = (E[]) new Object[size + newArr.length];
        System.arraycopy(arr, 0, temp, 0, size);
        arr = temp;

        System.arraycopy(arr, index, arr, index + newArr.length, size - index);
        System.arraycopy(newArr, 0, arr, index, newArr.length);
        size += newArr.length;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Object[] newArr = c.toArray();
        if(newArr.length == 0){
            return false;
        }
        boolean res = false;

        for(int i = 0; i < newArr.length; i++){
            int index = indexOf(newArr[i]);
            if (index >= 0){
                remove(index);
                i--;
                res = true;
            }
        }
        return res;
    }
    @Override
    public boolean retainAll(Collection<?> c) {
        boolean res = false;
        for (int i = 0; i < size; i++) {
            if (!c.contains(arr[i])) {
                remove(arr[i]);
                i--;
                res = true;
            }
        }
        return res;
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
        Object[] res = new Object[size];
        System.arraycopy(arr, 0, res, 0, size);
        return res;
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
