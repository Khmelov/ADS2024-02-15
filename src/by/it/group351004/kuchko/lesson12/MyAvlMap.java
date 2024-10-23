package by.it.group351004.kuchko.lesson12;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
@SuppressWarnings("all")
public class MyAvlMap implements Map<Integer, String> {
    private class TreeNode {
        TreeNode left, right;
        Integer key;
        String value;
        int height;

        TreeNode(Integer key, String value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
            this.height = 0;
        }
        TreeNode() {
            this.right = null;
            this.left = null;
            this.height = -1;
        }
    }

    private int size;
    private TreeNode root;

    public MyAvlMap() {
        root = null;
        size = 0;
    }

    private int getHeight(TreeNode node) { return node != null ? node.height : -1; }
    private int getBFactor(TreeNode node) { return getHeight(node.right) - getHeight(node.left); }
    private void fixHeight(TreeNode node) {
        int leftHeight = getHeight(node.left);
        int rightHeight = getHeight(node.right);
        node.height = Math.max(leftHeight,rightHeight) + 1;
    }

    private TreeNode rotateRight(TreeNode a) {
        TreeNode b;
        b = a.left;
        a.left = b.right;
        b.right = a;
        fixHeight(a);
        fixHeight(b);
        return b;
    }
    private TreeNode rotateLeft(TreeNode a) {
        TreeNode b = a.right;
        a.right = b.left;
        b.left = a;
        fixHeight(a);
        fixHeight(b);
        return b;
    }
    private TreeNode balance(TreeNode a) {
        if (getBFactor(a) > 1) {
            if (getBFactor(a.right) < 0)
                a.right = rotateRight(a.right);
            return rotateLeft(a);
        }
        if (getBFactor(a) < -1) {
            if (getBFactor(a.left) > 0)
                a.left = rotateLeft(a.left);
            return rotateRight(a);
        }
        return a;
    }

    private TreeNode add(TreeNode node, Integer key, String value) {
        if (node == null)
            return new TreeNode(key, value);
        if (node.key.compareTo(key) > 0)
            node.left = add(node.left, key, value);
        else if (node.key.compareTo(key) < 0)
            node.right = add(node.right, key, value);
        else node.value = value;
        return balance(node);
    }
    private String get(TreeNode node, Integer key) {
        if (node == null) return null;
        if (node.key.compareTo(key) == 0) return node.value;
        return get(key > node.key ? node.right : node.left, key);
    }
    private TreeNode findMin(TreeNode node) { return node.left != null ? findMin(node.left) : node; }
    private TreeNode remove(TreeNode node, Integer key) {
        if (node == null) return null;
        if (node.key.compareTo(key) > 0)
            node.left = remove(node.left, key);
        else if (node.key.compareTo(key) < 0)
            node.right = remove(node.right, key);
        else {
            if (node.left == null)
                return node.right;
            if (node.right == null)
                return node.left;
            TreeNode min = findMin(node.right);
            node.key = min.key;
            node.value = min.value;
            node.right = remove(node.right, min.key);
        }
        return balance(node);
    }
    private String inOrder(TreeNode node) {
        if (node == null) return "";
        String result = inOrder(node.left);
        result += node.key + "=" + node.value + ", ";
        return result + inOrder(node.right);
    }

    @Override
    public String toString() {
        StringBuilder line = new StringBuilder();
        line.append('{');
        line.append(inOrder(root));
        if (line.length() > 1)
            line.delete(line.length() - 2, line.length());
        line.append('}');
        return line.toString();
    }
    @Override
    public String put(Integer K, String V) {
        String lastV = get(K);
        if (lastV == null) size++;
        root = add(root, K, V);
        return lastV;
    }
    @Override
    public String remove(Object o) {
        String removedV = get(o);
        if (removedV != null) {
            remove(root, (Integer) o);
            size--;
        }
        return removedV;
    }
    @Override
    public String get(Object o) { return get(root, (Integer) o); }
    @Override
    public boolean containsKey(Object o) { return get(root, (Integer) o) != null; }
    //--------------------------------------------
    @Override
    public int size() { return size; }
    @Override
    public boolean isEmpty() { return size == 0; }
    @Override
    public void clear() {
        root = null;
        size = 0;
    }
    //--------------------------------------------
    @Override
    public boolean containsValue(Object o) { return false; }
    @Override
    public void putAll(Map<? extends Integer, ? extends String> map) { }
    @Override
    public Set<Integer> keySet() { return null; }
    @Override
    public Collection<String> values() { return null; }
    @Override
    public Set<Entry<Integer, String>> entrySet() { return null; }
}
