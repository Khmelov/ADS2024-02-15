package by.it.group351003.zhuravski.lesson09;

import java.util.*;

public class ListB<E> implements List<E> {
    public class ListElem {
        E value;
        ListElem next;
        public ListElem(E value, ListElem next) {
            this.next = next;
            this.value = value;
        }
    };
    private ListElem first = null;
    private ListElem last = null;
    private int count = 0;

    //Создайте аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    @Override
    public String toString() {
        String result = "[";
        if (count > 0) {
            ListElem cur = first;
            for (int i = 1; i < count; i++) {
                result = result.concat(cur.value.toString());
                result = result.concat(", ");
                cur = cur.next;
            }
            result = result.concat(cur.value.toString());
        }
        result += "]";
        return result;
    }

    @Override
    public boolean add(E e) {
        if (last == null) {
            first = new ListElem(e, null);
            last = first;
        }
        else {
            ListElem v = new ListElem(e, null);
            last.next = v;
            last = v;
        }
        count += 1;
        return true;
    }

    @Override
    public E remove(int index) {
        if (index < count) {
            ListElem cur = first;
            for (int i = 2; i <= index; i++) {
                cur = cur.next;
            }
            E res = cur.next.value;
            cur.next = cur.next.next;
            count -= 1;
            return res;
        }
        return null;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public void add(int index, E element) {
        if (index == 0) {
            ListElem n = new ListElem(element, first);
            first = n;
            count++;
        }
        else if (index == count - 1) {
            add(element);
        }
        else {
            ListElem cur = first;
            for (int i = 2; i <= index; i++) {
                cur = cur.next;
            }
            ListElem n = new ListElem(element, cur.next);
            cur.next = n;
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
        if (index < count) {
            ListElem cur = first;
            for (int i = 1; i <= index; i++) {
                cur = cur.next;
            }
            E old = cur.value;
            cur.value = element;
            return old;
        }
        return null;
    }


    @Override
    public boolean isEmpty() {
        return false;
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
            int index = 0;
            ListElem cur = first;
            while (index < count) {
                if (cur.value.equals(o)) {
                    return index;
                }
                index++;
                cur = cur.next;
            }
            return -1;
        }
        return -1;
    }

    @Override
    public E get(int index) {
        if (index < count) {
            ListElem cur = first;
            for (int i = 1; i <= index; i++) {
                cur = cur.next;
            }
            return cur.value;
        }
        return null;
    }

    @Override
    public boolean contains(Object o) {
        int index = indexOf(o);
        if (index > -1) {
            return true;
        }
        return false;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (count > 0) {
            int index = 0;
            int res = -1;
            ListElem cur = first;
            while (index < count) {
                if (cur.value.equals(o)) {
                    res = index;;
                }
                index++;
                cur = cur.next;
            }
            return res;
        }
        return -1;
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
