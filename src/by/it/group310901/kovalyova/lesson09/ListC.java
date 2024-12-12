package by.it.group310901.kovalyova.lesson09;


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
    private final int SIZE = 8;
    private final int CUT = 4;
    private E[] arr = (E[]) new Object[SIZE];
    private int ptr = 0;

    private void resize(int newSize) {
        E[] newArr = (E[]) new Object[newSize];
        System.arraycopy(arr, 0, newArr, 0, ptr);
        arr = newArr;
    }

    @Override
    public String toString() {
        String s = "[";
        for (int i = 0; i < ptr; i++) {
            if (i < ptr - 1)
                s += arr[i].toString() + ", ";
            else
                s += arr[i].toString();
        }
        s += "]";
        return s;
    }

    @Override
    public boolean add(E e) {
        try {
            if (ptr == arr.length - 1) {
                resize(arr.length*2);
            }
            arr[ptr++] = e;
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    @Override
    public E remove(int index) {
        E tmp = arr[index];
        for (int i = index; i < ptr; i++) {
            arr[i] = arr[i + 1];
        }
        arr[ptr] = null;
        ptr--;
        if (arr.length > SIZE && ptr < arr.length / CUT) {
            resize(arr.length / 2);
        }
        return tmp;
    }

    @Override
    public int size() {
        return ptr;
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Опциональные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    @Override
    public void add(int index, E element) {
        if (ptr == arr.length - 1) {
            resize(arr.length * 2);
        }
        for (int i = ptr; i > index; i--) {
            arr[i] = arr[i - 1];
        }
        arr[index] = element;
        ptr++;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < ptr; i++) {
            if (arr[i].equals(o)) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public E set(int index, E element) {
        E tmp = arr[index];
        arr[index] = element;
        return tmp;
    }


    @Override
    public boolean isEmpty() {
        return ptr == 0;
    }


    @Override
    public void clear() {
        arr = (E[]) new Object[SIZE];
        ptr = 0;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < ptr; i++) {
            if (arr[i].equals(o))
                return i;
        }
        return -1;
    }

    @Override
    public E get(int index) {
        return arr[index];
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < ptr; i++) {
            if (arr[i].equals(o))
                return true;
        }
        return false;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = ptr - 1; i >= 0; i--) {
            if (arr[i].equals(o))
                return i;
        }
        return -1;
    }

    @Override
    public boolean containsAll(Collection<?> c)
    {
for (var o : c) {
        if (!contains(o))
            return false;
    }
        return true;
}

@Override
public boolean addAll(Collection<? extends E> c) {
    boolean modified = false;
    for (var o : c) {
        if (add(o))
            modified = true;
    }
    return modified;
}

@Override
public boolean addAll(int index, Collection<? extends E> c) {
    boolean modified = false;
    int tmp = ptr;
    for (var o : c) {
        add(index++, o);
        if (ptr > tmp)
            modified = true;
    }
    return modified;
}

@Override
public boolean removeAll(Collection<?> c) {
    boolean modified = false;
    for (var o : c) {
        while (contains(o)) {
            if (remove(o))
                modified = true;
        }
    }
    return modified;
}

@Override
public boolean retainAll(Collection<?> c) {
    boolean modified = false;
    for (int i = 0; i < ptr; i++) {
        if (!c.contains(arr[i])) {
            remove(i--);
            modified = true;
        }
    }
    return modified;
}


@Override
public List<E> subList(int fromIndex, int toIndex) {
    int len = toIndex - fromIndex;
    ListC<E> subLst = new ListC<E>();
    E[] subArr = (E[]) new Object[ptr];
    System.arraycopy(arr, fromIndex, subArr, 0, len);
    subLst.arr = subArr;
    return subLst;
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
