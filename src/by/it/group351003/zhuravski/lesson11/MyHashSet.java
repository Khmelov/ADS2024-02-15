package by.it.group351003.zhuravski.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
public class MyHashSet<E> implements Set<E> {
    private final int indexCapacity = 35;
    private LinkedList<SetObject>[] lists;
    private int elemAm = 0;
    public MyHashSet() {
        lists = new LinkedList[indexCapacity];
        for (int i = 0; i < lists.length; i++) {
            lists[i] = new LinkedList<SetObject>();
        }
    }

    private int calcIndex(E elem) {
        return elem.hashCode() % indexCapacity;
    }

    private void pushObj(SetObject<E> obj, int index) {
        lists[index].push(obj);
        elemAm++;
    }

    @Override
    public int size() {
        return elemAm;
    }

    @Override
    public boolean isEmpty() {
        return elemAm == 0;
    }

    @Override
    public boolean contains(Object o) {
        int index = calcIndex((E)o);
        for (SetObject object : lists[index]) {
            if (object.compare(o)) {
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
        if (!contains(e)) {
            int index = calcIndex(e);
            SetObject<E> obj = new SetObject<E>(e);
            pushObj(obj, index);
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        int index = calcIndex((E)o);
        Iterator iter = lists[index].iterator();
        for (SetObject object : lists[index]) {
            if (object.compare(o)) {
                lists[index].remove(object);
                elemAm--;
                return true;
            }
        }
        return false;
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
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        elemAm = 0;
        for (int i = 0; i < indexCapacity; i++) {
            lists[i].clear();
        }
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        String divSym = ", ";
        String preSym = "";
        for (int i = 0; i < lists.length; i++) {
            for (SetObject setObject : lists[i]) {
                builder.append(preSym);
                preSym = divSym;
                builder.append(setObject.toString());
            }
        }
        builder.append(']');
        return builder.toString();
    }
}
