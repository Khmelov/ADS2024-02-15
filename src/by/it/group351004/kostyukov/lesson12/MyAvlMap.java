package by.it.group351004.kostyukov.lesson12;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
public class MyAvlMap implements Map<Integer, String> {

    private class TreeNode {
        Integer key;
        String value;
        TreeNode left;
        TreeNode right;
        int height;

        public TreeNode(Integer key, String value){
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
            this.height = 0;
        }
    }

    public MyAvlMap() {
        root = null;
        size = 0;
    }

    private int size;
    private TreeNode root;

    private String inOrderTravel(TreeNode node) {
        if (node == null) {
            return "";
        }
        String result = inOrderTravel(node.left);
        result += node.key + "=" + node.value + ", ";
        return result + inOrderTravel(node.right);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('{');
        stringBuilder.append(inOrderTravel(root));
        if (stringBuilder.length() > 1) {
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private String get(TreeNode node, Integer key) {
        if (node == null) {
            return null;
        }
        if (key.equals(node.key)) {
            return node.value;
        }
        return get(key > node.key ? node.right : node.left ,key);
    }

    @Override
    public boolean containsKey(Object key) {
        return get(root, (Integer)key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public String get(Object key) {
        return get(root, (Integer) key);
    }
    private int getHeight(TreeNode node) {
        return node != null ? node.height : -1;

    }
    private void fixHeight(TreeNode node) {
        int leftHeight = getHeight(node.left);
        int rightHeight = getHeight(node.right);
        node.height = Math.max(leftHeight,rightHeight) + 1;
    }
    private int getBfactor(TreeNode node) {
        return getHeight(node.right) - getHeight(node.left);
    }
    private TreeNode rotateRight(TreeNode node) {
        TreeNode temp = node.left;
        node.left = temp.right;
        temp.right = node;
        fixHeight(node);
        fixHeight(temp);
        return temp;
    }
    private TreeNode rotateLeft(TreeNode node) {
        TreeNode temp = node.right;
        node.right = temp.left;
        temp.left = node;
        fixHeight(node);
        fixHeight(temp);
        return temp;
    }
    private TreeNode balance(TreeNode node) {
        if (getBfactor(node) == 2) {
            if (getBfactor(node.right) < 0) {
                node.right = rotateRight(node.right);
            }
            return rotateLeft(node);
        }
        if (getBfactor(node) == -2) {
            if (getBfactor(node.left) > 0) {
                node.left = rotateLeft(node.left);
            }
            return rotateRight(node);
        }
        return node;
    }
    private TreeNode add(TreeNode node, Integer key, String value) {
        if (node == null) {
            return new TreeNode(key, value);
        }
        if (node.key.compareTo(key) > 0) {
            node.left = add(node.left,key,value);
        }
        else if (node.key.compareTo(key) < 0) {
            node.right = add(node.right, key, value);
        }
        else {
            node.value = value;
        }
        return balance(node);
    }
    private TreeNode findMin(TreeNode node) {
        return node.left != null ? findMin(node.left) : node;
    }
    private TreeNode remove(TreeNode node, Integer key) {
        if (node == null) {
            return null;
        }
        if (node.key > key) {
            node.left = remove(node.left, key);
        }
        else if (node.key < key) {
            node.right = remove(node.right, key);
        }
        else {
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            TreeNode minNode = findMin(node.right);
            node.key = minNode.key;
            node.value = minNode.value;
            node.right = remove(node.right, minNode.key);
        }
        return balance(node);
    }
    @Override
    public String put(Integer key, String value) {
        String lastValue = get(key);
        if (lastValue == null) {
            size++;
        }
        root = add(root, key, value);
        return lastValue;
    }

    @Override
    public String remove(Object o) {
        String removedValue = get(o);
        if (removedValue != null) {
            remove(root, (Integer)o);
            size--;
        }
        return removedValue;
    }
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {

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
