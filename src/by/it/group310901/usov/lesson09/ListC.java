package by.it.group310901.usov.lesson09;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListC<E> implements List<E> {

    //Создайте аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ

    E[] arr = (E[]) new Object[0];

    ListC()
    {
        this.arr =(E[]) new Object[10000];
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();

        for(E element : this.arr) {
            if(element != null)
                res.append(element).append(", ");
        }

        if(!res.isEmpty()) res.delete(res.length() - 2, res.length());
        res.insert(0, "[");
        res.append("]");
        return res.toString();
    }

    @Override
    public boolean add(E e) {
        for (int i = 0; i < this.arr.length; i++) {
            if(this.arr[i] == null) {
                this.arr[i] = e;
                return true;
            }
        }
        return false;
    }

    @Override
    public E remove(int index) {
        E data = this.arr[index];
        for(int i = index; i < this.arr.length - 1; i++) {
            this.arr[i] = this.arr[i + 1];
        }
        return data;
    }

    @Override
    public int size() {
        int size = 0;
        for(E element : this.arr) {
            if(element != null)
                size++;
        }
        return size;
    }

    @Override
    public boolean remove(Object o) {
        var indxe = this.indexOf(o);
        if (indxe == -1 || this.arr[indxe] == null){
            return false;
        }
        for(int i = indxe; i < this.arr.length - 1; i++){
            this.arr[i] = this.arr[i + 1];
        }
        return true;
    }

    @Override
    public void add(int index, E element) {
        for(int i = this.arr.length - 1; i > index; i--){
            this.arr[i] = this.arr[i - 1];
        }
        this.arr[index] = element;
    }

    @Override
    public E set(int index, E element) {
        E data = this.arr[index];
        this.arr[index] = element;
        return data;
    }


    @Override
    public boolean isEmpty() {
        for(int i = 0 ; i < this.arr.length;i++){
            if(this.arr[i] != null){
                return false;
            }
        }
        return true;
    }


    @Override
    public void clear() {
        for(int i = 0 ; i < this.arr.length;i++){
            this.arr[i] = null;
        }
    }

    @Override
    public int indexOf(Object o) {
        for(int i = 0 ; i< this.arr.length; i++){
            if(this.arr[i] != null && this.arr[i].equals(o)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public E get(int index) {
        return this.arr[index];
    }

    @Override
    public boolean contains(Object o) {
        for(int i = 0; i < this.arr.length; i++){
            if(this.arr[i] != null && this.arr[i].equals(o)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int lastIndexOf(Object o) {
        for(int i = this.arr.length - 1; i >= 0; i--){
            if(this.arr[i] != null && this.arr[i].equals(o)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for(Object o : c){
            if(!this.contains(o)){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for(E e : c){
            this.add(e);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        for(E e : c){
            this.add(index, e);
            index++;
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean result = false;
        for(Object o : c){
            while(this.contains(o)){
                this.remove(o);
                result = true;
            }
        }
        return result;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean result = true;
        for(int i = 0; i < this.arr.length; i++){
            if(this.arr[i] != null && !c.contains(this.arr[i])){
                this.remove(this.arr[i]);
                i--;
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
