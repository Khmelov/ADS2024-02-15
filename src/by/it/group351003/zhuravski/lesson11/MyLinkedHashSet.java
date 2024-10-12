package by.it.group351003.zhuravski.lesson11;

import java.util.*;
//Сколхозил на основе 10 lesson'а
public class MyLinkedHashSet<E> implements Set<E> {
    private final int indexCapacity = 35;
    private LinkedList<SetObject>[] lists;
    private int elemAm = 0;
    MyPseudoList<SetObject> added;
    public MyLinkedHashSet() {
        lists = new LinkedList[indexCapacity];
        for (int i = 0; i < lists.length; i++) {
            lists[i] = new LinkedList<SetObject>();
        }
        added = new MyPseudoList<>();
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
            added.add(obj);
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
                added.remove(object);
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
        int oldLen = elemAm;
        for (Object obj : c) {
            add((E)obj);
        }
        return oldLen != elemAm;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        int oldLen = elemAm;
        MyPseudoList<SetObject> old = added;
        added = new MyPseudoList<>();
        clear();
        SetObject checked;
        while (null != (checked = old.poll()))  {
            if (c.contains(checked.val)) {
                add((E) checked.val);
            }
        }
        return elemAm != oldLen;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean res = false;
        for (Object o : c) {
            res |= remove(o);
        }
        return res;
    }

    @Override
    public void clear() {
        elemAm = 0;
        for (int i = 0; i < indexCapacity; i++) {
            lists[i].clear();
        }
        added.clear();
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        String divSym = ", ";
        String preSym = "";
        Elem<SetObject> cur = added.first;
        while (cur != null) {
            builder.append(preSym);
            preSym = divSym;
            builder.append(cur.val.toString());
            cur = cur.next;
        }
        builder.append(']');
        return builder.toString();
    }
}
