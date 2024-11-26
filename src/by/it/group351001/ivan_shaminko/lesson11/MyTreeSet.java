package by.it.group351001.ivan_shaminko.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

//toString() +
//size() +
//clear() +
//isEmpty() +
//add(Object) +
//remove(Object) +
//contains(Object) +
//
//containsAll(Collection) +
//addAll(Collection) +
//removeAll(Collection) +
//retainAll(Collection) +

public class MyTreeSet<E> implements Set<E> {

    private int size = 0;
    private MyNode<E> root;

    static private class MyNode<E>{
        E data;
        int hash;
        MyNode<E> left, right;
        MyNode(E e, int hash, MyNode left, MyNode right){
            this.data = e;
            this.hash = hash;
            this.right = right;
            this.left = left;
        }

    }
//////////большинство методов реализовано с помощью рекурсии, также отсутствует полноценная перебалансировка дерева/////////

    private void addtostring(MyNode parent, StringBuilder str){
        if (parent.left != null) {
            addtostring(parent.left, str);
        }
            str.append(parent.data);
            str.append(", ");
            if (parent.right != null){
                addtostring(parent.right, str); // рекурсия, иного решения не найдено
            }

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        if (root!=null) {
            addtostring(root, sb);
            sb.delete(sb.length() - 2,sb.length());
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        if (root == null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean contains(Object o) {
        if (root == null) {
            return false;
        }
        MyNode node = root;
        int hash = o.hashCode();
        while (node != null) {
            if (node.hash == hash){
                return true;
            } else if (node.hash < hash) {
                node = node.right;
            } else if (node.hash > hash) {
                node = node.left;
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
        if (root == null){
            root = new MyNode<>(e, e.hashCode(),null,null);
            size++;
            return true;
        }
        MyNode x = root;
        MyNode parent = null;
        int hash = e.hashCode();
        while(x != null){
            if (x.hash == hash) {
                return false;
            }
            parent = x;
            if (x.hash > hash) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        if (parent.hash > hash)
            parent.left = new MyNode<>(e, e.hashCode(),null,null);
        else
            parent.right = new MyNode<>(e, e.hashCode(),null,null);
        size++;
        return true;
    }

    private MyNode findmin(MyNode node){
        if (node.left==null)
            return node;
        else
            return findmin(node.left);
    }

    private MyNode delete(MyNode node, int hash){
        if (node.hash == hash){
            size--; //чтобы не писать три раза
            if (node.left == null && node.right == null)
                return null;
            if (node.left == null)
                return node.right;
            if (node.right == null)
                return node.left;
            size++; //чтобы не писать три раза
            MyNode minnode = findmin(node.right);
            node.data = minnode.data;
            node.hash = minnode.hash;
            node.right = delete(node.right, minnode.hash);
            return node;
        }
        if (node.hash > hash) {
            if(node.left == null)
                return node;
            node.left = delete(node.left, hash);
        }else{
            if(node.right == null)
                return node;
            node.right = delete(node.right, hash);
        }
        return node;
    }
    @Override
    public boolean remove(Object o) {
        MyNode x = root;
        int oldsize = size;
        if(x==null)
            return false;
        root = delete(root, o.hashCode());
        return oldsize!=size;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        Object[] cArray = c.toArray();
        for(int i = 0; i < cArray.length; i++){
            if(!contains(cArray[i]))
                return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        Object [] cArray = c.toArray();
        boolean res = false;
        if(cArray.length == 0) {
            return false;
        }
        for (int i = 0; i < cArray.length; i++) {
            res |= add((E)cArray[i]);
        }
        return res;
    }

    public boolean retain(MyNode node, Collection<?> c){
        boolean result = false;
        if(node.left!=null)
            result |= retain(node.left, c);
        if(node.right!=null)
            result |= retain(node.right, c);
        if(!c.contains(node.data)) {
            result = remove(node.data); //сойдёт
        }
        return result;
    }
    @Override
    public boolean retainAll(Collection<?> c) {
        if(root==null)
            return false;
        return retain(root, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Object [] cArray = c.toArray();
        boolean res = false;
        if(cArray.length == 0) {
            return false;
        }
        for (int i = 0; i < cArray.length; i++) {
            res |= remove(cArray[i]);
        }
        return res;
    }

    private void erase(MyNode node){
        if (node.left != null){
            erase(node.left);
        }
        if ((node.right != null)) {
            erase(node.right);
        }
        node.left = null;
        node.right = null;
        node.hash = 0;
        node.data = null;
    }
    @Override
    public void clear() {
        erase(root);
        root = null;
        size = 0;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
