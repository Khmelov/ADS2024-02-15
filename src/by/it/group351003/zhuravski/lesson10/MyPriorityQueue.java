package by.it.group351003.zhuravski.lesson10;
import java.util.*;

/*
                toString()
                size()
                clear()
                add(E element)
                remove()
                contains(E element)

                offer(E element)
                poll()
                peek()
                element()
                isEmpty()

                containsAll(Collection<E> c)
                addAll(Collection<E> c)
                removeAll(Collection<E> c)
                retainAll(Collection<E> c)
 */
public class MyPriorityQueue<E> implements Queue<E> {
    Comparator<Object> comp;
    private Object[] array;
    private int length = 0;
    private int capacity = 42;
    public MyPriorityQueue() {
        array = new Object[capacity];
        comp = new DomesticComparator<Object>();
    }
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append('[');
        if (length > 0) {
            str.append(array[0].toString());
            for (int i = 1; i < length; i++) {
                str.append(", ");
                str.append(array[i].toString());
            }
        }
        str.append(']');
        return str.toString();
    }
    void shrink_width() {
        if (length == capacity) {
            Object[] old_arr = array;
            capacity += 42;
            array = new Object[capacity];
            System.arraycopy(old_arr, 0, array, 0, length);
        }
    }
    void swim_up(int index) {
        boolean state = true;
        int cur = index;                                               //   0     //4 /2 + 4%2 = 2 + 0 = 2
        while ((cur > 0) && state) {                                   // 1   2   //3 /2 + 3%2 = 2 + 0 = 2
            int comp_index = cur / 2 + cur % 2 - 1;                    //3 4 5 6  //6 /2 + 6%2 = 3
            int comp_val = comp.compare(array[comp_index], array[cur]);
            if (comp_val <= 0) {
                state = false;
            }
            else {
                Object c = array[cur];
                array[cur] = array[comp_index];
                array[comp_index] = c;
                cur = comp_index;
            }
        }
    }
    void swim_down(int index) {
        boolean state = true;
        int cur = index;
        while (state) {
            int change_index = -1;
            int comp_index = cur * 2 + 1;
            if (comp_index < length) {
                if (comp.compare(array[cur], array[comp_index]) > 0) {
                    change_index = comp_index;
                }
            }
            comp_index++;
            if (comp_index < length) {
                if (comp.compare(array[cur], array[comp_index]) > 0) {
                    if (change_index == -1) {
                        change_index = comp_index;
                    }
                    else if (comp.compare(array[comp_index], array[change_index]) < 0) {
                        change_index = comp_index;
                    }
                }
            }
            if (change_index != -1) {
                Object c = array[cur];
                array[cur] = array[change_index];
                array[change_index] = c;
                cur = change_index;
            }
            else {
                state = false;
            }
        }
    }
    @Override
    public int size() {
        return length;
    }

    @Override
    public boolean isEmpty() {
        return length == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < length; i++) {
            if (array[i].equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(E e) {
        shrink_width();
        array[length] = e;
        swim_up(length);
        length++;
        return true;
    }
    @Override
    public boolean remove(Object o) {
        if (length > 0) {
            int dest = 0;
            boolean not_found = true;
            while ((dest < length) && not_found) {
                if (array[dest].equals(o)) {
                    not_found = false;
                }
                dest++;
            }
            if (!not_found) {
                dest--;
                length--;
                array[dest] = array[length];
                swim_down(dest);
                swim_up(dest);
                /*if (dest < length) {
                    System.arraycopy(array, dest, array, dest - 1, length - dest);
                    swim_down(dest - 1);
                    swim_down(dest);
                }
                length--;*/
                return true;
            }
        }
        return false;
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
        if (!c.isEmpty()) {
            for (Object obj : c) {
                add((E)obj);
            }
            return true;
        }
        return false;
    }

    public boolean remove_sp(Object o) {
        if (length > 0) {
            int dest = 0;
            boolean not_found = true;
            while ((dest < length) && not_found) {
                if (array[dest].equals(o)) {
                    not_found = false;
                }
                dest++;
            }
            if (!not_found) {
                dest--;
                length--;
                array[dest] = array[length];
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        int old_length = length;
        for (Object obj : c) {
            while (remove_sp(obj));
        }
        for (int i = length - 1; i > 0; i--) {
            swim_down(i);
        }
        return length != old_length;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        int old_length = length;
        for (Object obj : c) {
            for (int i = length - 1; i >= 0; i--) {
                if (!c.contains(array[i])) {
                    remove_sp(array[i]);
                }
            }
        }
        for (int i = length - 1; i >= 0; i--) {
            swim_down(i);
        }
        return length != old_length;
    }

    @Override
    public void clear() {
        length = 0;
    }

    @Override
    public boolean offer(E e) {
        return add(e);
    }

    @Override
    public E remove() {
        if (length > 0) {
            E res = (E)array[0];
            length--;
            array[0] = array[length];
            swim_down(0);
            return res;
        }
        return null;
    }

    @Override
    public E poll() {
        return remove();
    }

    @Override
    public E element() {
        if (length == 0) {
            throw new NoSuchElementException();
        }
        return (E)array[0];
    }

    @Override
    public E peek() {
        if (length > 0) {
            return (E)array[0];
        }
        return null;
    }
}
