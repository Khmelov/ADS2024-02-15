package by.it.group310902.karpechenko.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyHashSet<E>  implements Set <E> {


    public class node<E>{
        public E value;
        public node next;

        node(E e){
            value = e;
        }
    }

    MyHashSet() {
        elem = new node[minSize];
    }

    private final int minSize = 8;
    private node[] elem;
    private int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        int h = o.hashCode() % elem.length;
        node i = elem[h];
        while(i != null){
            if (i.value.equals(o)){
                return true;
            }
            i = i.next;
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
    private void resize(){
        size = 0;
        node[] temp = elem;
        elem = new node[elem.length * 2];
        for (var item : temp){
            node tee = item;
            while(tee != null){
                add((E)tee.value);
                tee = tee.next;
            }
        }
    }
    @Override
    public boolean add(E e) {
        int h = e.hashCode() % elem.length;
        node i = elem[h];
        node prev = null;
        while (i != null){
            if(i.value.equals(e)){
                return false;
            }
            prev = i;
            i = i.next;
        }
        if(prev == null){
            elem[h] = new node(e);
        }
        else{
            prev.next = new node(e);
        }
        size++;
        if(size >= elem.length * 0.75){
            resize();
        }
        return true;
    }

    public String toString(){
        StringBuilder st = new StringBuilder();
        st.append('[');
        for(var item : elem){
            while(item != null){
                st.append(item.value + ", ");
                item = item.next;
            }
        }
        if(st.length() > 1)
            st.delete(st.length() - 2, st.length());
        st.append(']');
        return st.toString();
    }

    @Override
    public boolean remove(Object o) {
        int h = o.hashCode() % elem.length;
        if (elem[h] == null) return false;
        node i = elem[h];
        node prev = null;
        while (i != null) {
            if (i.value.equals(o)) {
                if (prev == null){
                    elem[h] = i.next;
                }
                else {
                    prev.next = i.next;
                }
                size--;
                return true;
            }
            prev = i;
            i = i.next;
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
    public void clear(){
        size = 0;
        elem = new node[minSize];
    }
}
