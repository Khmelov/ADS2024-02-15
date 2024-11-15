package by.it.group351005.bitno.lesson12;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class MyAvlMap<Integer, String> implements Map<Integer, String> {
//
    private class Node {
        String value;
        Integer key;
        Node left;
        Node right;
        private Node (String value, Integer key, Node left, Node right){
            this.value = value;
            this.key = key;
            this.left = left;
            this.right = right;
        }
    }

    @Override
    public int size() {return 0;}

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public String get(Object key) {
        return null;
    }

    @Override
    public String put(Integer key, String value) {
        return null;
    }

    @Override
    public String remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<Integer> keySet() {
        return null;
    }

    @Override
    public Collection<String> values() {
        return null;
    }

    @Override
    public Set<Entry<Integer, String>> entrySet() {
        return null;
    }
}
