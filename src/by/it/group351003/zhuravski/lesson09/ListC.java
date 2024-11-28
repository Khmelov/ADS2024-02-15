package by.it.group351003.zhuravski.lesson09;

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

    E[] elements;
    int curInd = 0;
    static int size = 8;

    public ListC() {
        this(size);
    }

    public ListC(int size) {
        elements = (E[]) new Object[size];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < curInd; i++) {
            sb.append(elements[i]);

            if (i < curInd - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
        return sb.toString();
    }

    @Override
    public boolean add(E e) {
        if (curInd == elements.length) {
            E[] tempElements = (E[]) new Object[elements.length * 2];

            for (int i = 0; i < elements.length; i++) {
                tempElements[i] = elements[i];
            }

            elements = tempElements;
        }

        elements[curInd] = e;
        curInd++;
        return true;
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= curInd) {
            return null;
        }

        E deletedElem = elements[index];
        for (int i = index; i < curInd - 1; i++) {
            elements[i] = elements[i + 1];
        }

        curInd--;
        return deletedElem;
    }

    @Override
    public int size() {
        return curInd;
    }

    @Override
    public void add(int index, E element) {
        if (index < 0 || index > curInd) {
            return;
        }

        if (curInd == elements.length) {
            E[] tempElements = (E[]) new Object[elements.length * 2];

            for (int i = 0; i < elements.length; i++) {
                tempElements[i] = elements[i];
            }

            elements = tempElements;
        }

        for (int i = curInd; i > index; i--) {
            elements[i] = elements[i - 1];
        }

        elements[index] = element;
        curInd++;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < curInd; i++) {
            if (o.equals(elements[i])) {
                E deletedItem = elements[i];

                for (int j = i; j < curInd; j++) {
                    elements[j] = elements[j + 1];
                }

                curInd--;
                return true;
            }
        }
        return false;
    }

    @Override
    public E set(int index, E element) {
        if (index < 0 || index >= curInd) {
            return null;
        }

        E oldElem = elements[index];
        elements[index] = element;
        return oldElem;
    }


    @Override
    public boolean isEmpty() {
        return curInd == 0;
    }


    @Override
    public void clear() {
        elements = (E[]) new Object[size];
        curInd = 0;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < curInd; i++) {
            if (o.equals(elements[i])) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= curInd) {
            return null;
        }

        return elements[index];
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < curInd; i++) {
            if (o.equals(elements[i])) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = curInd - 1; i >= 0; i--) {
            if (o.equals(elements[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object elem: c) {
            if (!contains(elem)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (Object elem: c) {
            add((E) elem);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        for (Object elem: c) {
            add(index, (E) elem);
            index++;
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {

        boolean deletedElem = false;

        for (int i = 0; i < curInd; i++) {
            if (c.contains(elements[i])) {
                remove(i);
                i--;
                deletedElem = true;
            }
        }
        return deletedElem;
    }

    @Override
    public boolean retainAll(Collection<?> c) {

        boolean deletedElem = false;

        for (int i = 0; i < curInd; i++) {
            if (!c.contains(elements[i])) {
                remove(i);
                i--;
                deletedElem = true;
            }
        }
        return deletedElem;
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


/*import java.util.Collection;
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
        StringBuilder res = new StringBuilder("[");
        ListElem curElem = first;
        while (curElem != null){
            res.append(curElem.value.toString());
            if (curElem.next != null){
                res.append(", ");
            }
            curElem = curElem.next;
        }
        res.append("]");
        return res.toString();
    }

    @Override
    public boolean add(E e) {
        if (count > 0) {
            ListElem n = new ListElem(e, null);
            last.next = n;
            this.last = n;
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

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object obj : c) {
            if (!contains(obj)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (Object obj : c) {
            add((E)obj);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (count > 0) {
            if (index == count) {
                addAll(c);
            }
            else {
                int curIndex = index;
                for (Object obj : c) {
                    add(curIndex, (E)obj);
                    curIndex++;
                }
            }
            return true;
        }
        else if (index == 0) {
            addAll(c);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (count > 0) {
            int oldCount = count;
            for (Object obj : c) {
                while (contains(obj)) {
                    remove(obj);
                }
            }
            return count != oldCount;
        }
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean res = false;
        if (count > 0) {
            int oldCount = count;
            ListElem cur = first;
            for (int i = 0; i < oldCount; i++) {
                if (c.contains(cur.value)) {
                    add(cur.value);
                }
                cur = cur.next;
            }
            for (int i = 0; i < oldCount; i++) {
                remove(0);
            }
            if (count != oldCount) {
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

}*/
