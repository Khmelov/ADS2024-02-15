package by.it.group310902.karpechenko.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyTreeSet<E extends Comparable<E>> implements Set<E> {

    node root;
    private int size;
    MyTreeSet(){};
    private class node{
        E value;
        node left;
        node right;
        node(E value){this.value = value;}
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        inOrderTraversal(root, sb);
        return sb.append("]").toString();
    }

    void inOrderTraversal(node node, StringBuilder sb) {
        if (node == null) return;
        inOrderTraversal(node.left, sb);
        if (sb.length() > 1)
            sb.append(", ");
        sb.append(node.value);
        inOrderTraversal(node.right, sb);
    }

    @Override
    public int size() {
        return size;
    }
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private boolean isContained(node v, E element) {
        if (v == null)
            return false;
        int compare = element.compareTo(v.value);
        if (compare < 0)
            return isContained(v.left, element);
        else if (compare > 0)
            return isContained(v.right, element);
        else
            return true;
    }

    @Override
    public boolean contains(Object o) {
        return isContained(root, (E)o);
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
        if(contains(e)) return false;
        root = add(root,e);
        size++;
        return true;
    }
    private node add(node v, E element) {
        if (v == null)
            return new node(element);
        int compare = element.compareTo(v.value);
        if (compare < 0)
            v.left = add(v.left, element);
        else if (compare > 0)
            v.right = add(v.right, element);
        return v;
    }
    private node findMin(node v) {
        while (v.left != null)
            v = v.left;
        return v;
    }

    private node delete(node v, E element) {
        if (v == null)
            return null;

        int compare = element.compareTo(v.value);

        if (compare < 0)
            v.left = delete(v.left, element);
        if (compare > 0)
            v.right = delete(v.right, element);
        if (compare == 0){
            if (v.left == null)
                return v.right;
            else if (v.right == null)
                return v.left;
            v.value = findMin(v.right).value;
            v.right = delete(v.right, v.value);
        }
        return v;
    }
    @Override
    public boolean remove(Object o) {
        if(!contains((E)o)) return false;
        root = delete(root, (E)o);
        size--;
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (var i :c){
            if(!contains(i))
                return false;
        }

        return (!c.isEmpty());
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean b = false;
        for (E element: c) {
            b |= add(element);
        }
        return b;
    }
    @Override
    public boolean retainAll(Collection<?> c) {
        if (c.isEmpty()) {
            clear();
            return true;
        }
        boolean b = false;
        MyTreeSet<E> temp = new MyTreeSet<>();
        for (Object obj : c) {
            if (contains(obj)) {
                temp.add((E) obj);
                b = true;
            }
        }
        root = temp.root;
        size = temp.size;
        return b;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean b = false;
        for (Object obj: c) {
            b |= remove(obj);
        }
        return b;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }
}
