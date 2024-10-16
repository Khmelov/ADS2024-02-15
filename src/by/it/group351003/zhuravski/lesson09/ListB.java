package by.it.group351003.zhuravski.lesson09;

import java.util.*;

public class ListB<E> implements List<E> {
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
    private ListElem jumpToPrev(int index) {
        ListElem cur = first;
        for (int i = 1; i < index; i++) {
            cur = cur.next;
        }
        return cur;
    }
    private ListElem jumpTo(int index) {
        ListElem cur = first;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        return cur;
    }
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
                ListElem cur = jumpToPrev(index);
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

    @Override
    public void add(int index, E element) {
        if (count > 0) {
            if (index == 0) {
                ListElem n = new ListElem(element, first);
                first = n;
                count++;
            }
            else if (index == count) {
                add(element);
            }
            else if (index < count) {
                ListElem cur = jumpToPrev(index);
                ListElem n2 = new ListElem(element, cur.next);
                cur.next = n2;
                count++;
            }
        }
        else if (index == 0) {
            ListElem n = new ListElem(element, null);
            first = n;
            last = n;
            count++;
        }
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index != -1) {
            remove(index);
            return true;
        }
        return false;
    }

    @Override
    public E set(int index, E element) {
        if ((count > 0) && (index < count)) {
            ListElem cur = jumpTo(index);
            E value = cur.value;
            cur.value = element;
            return value;
        }
        return null;
    }


    @Override
    public boolean isEmpty() {
        return count == 0;
    }


    @Override
    public void clear() {
        count = 0;
        first = null;
        last = null;
    }

    @Override
    public int indexOf(Object o) {
        if (count > 0) {
            ListElem cur = first;
            int index = 0;
            while (index < count) {
                if (cur.value.equals(o)) {
                    return index;
                }
                index++;
                cur = cur.next;
            }
        }
        return -1;
    }

    @Override
    public E get(int index) {
        if ((count > 0) && (index  < count)) {
            ListElem cur = jumpTo(index);
            return cur.value;
        }
        return null;
    }

    @Override
    public boolean contains(Object o) {
        int index = indexOf(o);
        if (index != -1) {
            return true;
        }
        return false;
    }

    @Override
    public int lastIndexOf(Object o) {
        int res = -1;
        if (count > 0) {
            ListElem cur = first;
            int index = 0;
            while (index < count) {
                if (cur.value.equals(o)) {
                    res = index;
                }
                index++;
                cur = cur.next;
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
