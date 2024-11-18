package by.it.group310902.karpechenko.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyLinkedHashSet<E>  implements Set <E> {


    public class node<E>{
        public E value;
        public node next;
        public node left, right;
        node(E e){
            value = e;
        }
    }
    node head, tail;
    MyLinkedHashSet() {
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
        node[] newtable = new node[elem.length * 2];
        for(var x = head; x!=null; x = x.right){
            int pos = x.value.hashCode() % newtable.length;
            if (newtable[pos]==null){
                newtable[pos] = x;
            }else{
                node end = newtable[pos];
                while (end.next!=null)
                    end = end.next;
                end.next = x;
            }
            x.next = null;
        }
        elem = newtable;
    }
    private void addnew(node v){
        if(head == null){
            head = v;
            tail = v;
            return;
        }
        v.left = tail;
        tail.right = v;
        tail = v;
    }
    private void removenew(node v){
        if(v == head){
            head = v.right;
            v = null;
        }
        else
        if(v == tail){
            tail = tail.left;
            tail.right = null;
            v = null;
        }
        else{
            v.left.right = v.right;
            v.right.left = v.left;
            v = null;
        }
    }
    @Override
    public boolean add(E e) {
        node newNode = new node(e);
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
            elem[h] = newNode;
        }
        else{
            prev.next = newNode;
        }
        addnew(newNode);
        size++;
        if(size >= elem.length * 0.75){
            resize();
        }
        return true;
    }

    public String toString(){
        StringBuilder st = new StringBuilder();
        st.append('[');
        node x = head;
        while(x != null){
            st.append(x.value);
            if(x.right != null) st.append(", ");
            x = x.right;
        }
        st.append("]");
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
                removenew(i);
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
        for(var item : c){
            if(!contains(item)){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if(c.isEmpty() || c == null )return false;
        for (var item : c){
            add(item);
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection c) {
        node<E> x = head;
        boolean result = false;
        while(x!=null){

            if(!c.contains(x.value)){
                E tempE = x.value;
                x = x.right;
                remove(tempE);
                result = true;
            }else {
                x = x.right;
            }
        }
        return result;
    }
    @Override
    public boolean removeAll(Collection<?> c) {
        boolean b = false;
        for (var item: c){
            if(contains(item)){
                b = remove(item);
            }
        }
        return b;
    }

    @Override
    public void clear(){
        size = 0;
        elem = new node[minSize];
        head = null;
        tail = null;
    }
}
