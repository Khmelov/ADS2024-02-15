package by.it.group351003.zhuravski.lesson09;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListA<E> implements List<E> {
    class ListElem {
        E value;
        ListElem next;
        ListElem(E value, ListElem next) {
            this.value = value;
            this.next = next;
        }
    };
    private int count = 0;
    private ListElem first = null;
    private ListElem last = null;
    //Создайте аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    @Override
    public String toString() {
        String res = "[";
        if (count > 0) {
            ListElem cur = first;
            for (int i = 1; i < count; i++) {
                res = res.concat(cur.value.toString());
                res = res.concat(", ");
                cur = cur.next;
            }
            res = res.concat(cur.value.toString());
        }
        res += "]";
        return res;
    }

    @Override
    public boolean add(E e) {
        if (count > 0) {
            ListElem n = new ListElem(e, null);
            last.next = n;
            last = n;
        }
        else {
            ListElem n = new ListElem(e, null);
            first = n;
            last = n;
        }
        count++;
        return true;
    }

    @Override
    public E remove(int index) {
        if ((index < count) && (count > 0)) {
            if (index > 0) {
                ListElem cur = first;
                for (int i = 1; i < index; i++) {
                    cur = cur.next;
                }
                E result = cur.next.value;
                cur.next = cur.next.next;
                count--;
                return result;
            }
            else {
                E result = first.value;
                first = first.next;
                count--;
                return result;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return count;
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Опциональные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    @Override
    public void add(int index, E element) {
        /*if (count > 0) {
            if (index == 0) {
                ListElem n = new ListElem(element, first);
                first = n;
                count++;
            }
            else if (index == count) {
                add(element);
            }
            else if (index < count) {
                ListElem cur = first;
                for (int i = 1; i <= index)
            }
        }*/
        //else {
        //    ListElem n = new ListElem(element, null);
        //    first = n;
        //    last = n;
        //    count++;
        //}
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public E set(int index, E element) {
        return null;
    }


    @Override
    public boolean isEmpty() {
        return false;
    }


    @Override
    public void clear() {

    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
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
