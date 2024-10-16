package by.it.group351005.egorov.lesson12;

import java.util.*;

public class MyRbMap implements SortedMap<Integer, String> {

    private enum COLOR {
        BLACK,
        RED
    }

    private class TreeNode {
        TreeNode left;
        TreeNode right;
        TreeNode parent;
        Integer key;
        String value;
        COLOR color;

        public TreeNode(TreeNode left, TreeNode right, TreeNode parent, Integer key, String value, COLOR color) {
            this.left = left;
            this.right = right;
            this.parent = parent;
            this.key = key;
            this.value = value;
            this.color = color;
        }
    }

    private TreeNode root = null;
    private int _size = 0;

    private int getSize(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return Math.max(getSize(node.left), getSize(node.right)) + 1;
    }

    private void rotateRight(TreeNode node) {
        TreeNode leftChild = node.left;
        node.left = leftChild.right;
        if (leftChild.right != null) {
            leftChild.right.parent = node;
        }
        leftChild.right = node;
        leftChild.parent = node.parent;
        node.parent = leftChild;
        if (leftChild.parent == null) {
            root = leftChild;
        }
        else if (leftChild.parent.left == node) {
            leftChild.parent.left = leftChild;
        }
        else {
            leftChild.parent.right = leftChild;
        }
    }

    private void rotateLeft(TreeNode node) {
        TreeNode rightChild = node.right;
        node.right = rightChild.left;
        if (rightChild.left != null) {
            rightChild.left.parent = node;
        }
        rightChild.left = node;
        rightChild.parent = node.parent;
        node.parent = rightChild;
        if (rightChild.parent == null) {
            root = rightChild;
        }
        else if (rightChild.parent.left == node) {
            rightChild.parent.left = rightChild;
        }
        else {
            rightChild.parent.right = rightChild;
        }
    }

    private TreeNode findByValue(TreeNode node, Object value) {
        if (node == null)
            return null;
        if (node.value.equals(value))
            return node;
        TreeNode leftNode = findByValue(node.left, value);
        TreeNode rightNode = findByValue(node.right, value);
        return leftNode != null ? leftNode : rightNode;
    }

    private TreeNode findByKey(TreeNode node, Object key) {
        if (node == null) {
            return null;
        }
        if (node.key.equals(key)) {
            return node;
        }
        TreeNode leftNode = findByKey(node.left, key);
        TreeNode rightNode = findByKey(node.right, key);
        return leftNode != null ? leftNode : rightNode;
    }

    private COLOR getColor(TreeNode node) {
        return node != null ? node.color : COLOR.BLACK;
    }

    private TreeNode get(TreeNode node, Integer o) {
        if (node == null) {
            return null;
        }
        if (node.key.equals(o)) {
            return node;
        }
        return get(node.key.compareTo(o) > 0 ? node.left : node.right, o);
    }

    private void filterInsert(TreeNode newNode) {
        if (newNode == root) {
            newNode.color = COLOR.BLACK;
            return;
        }
        TreeNode parent = newNode.parent;
        while (newNode != root && getColor(parent) == COLOR.RED) {
            if (parent == parent.parent.left) {
                TreeNode uncle = parent.parent.right;
                if (getColor(uncle) == COLOR.RED) {
                    uncle.color = parent.color = COLOR.BLACK;
                    uncle.parent.color = COLOR.BLACK;
                    newNode = parent.parent;
                    parent = newNode.parent;
                }
                else {
                    if (newNode == parent.right) {
                        newNode = parent;
                        rotateLeft(newNode);
                    }
                    newNode.parent.color = COLOR.BLACK;
                    newNode.parent.parent.color = COLOR.RED;
                    rotateRight(newNode.parent.parent);
                }
            }
            else {
                TreeNode uncle = parent.parent.left;
                if (getColor(uncle) == COLOR.RED) {
                    uncle.color = parent.color = COLOR.BLACK;
                    uncle.parent.color = COLOR.BLACK;
                    newNode = parent.parent;
                    parent = newNode.parent;
                }
                else {
                    if (newNode == parent.left) {
                        newNode = parent;
                        rotateRight(newNode);
                    }
                    newNode.parent.color = COLOR.BLACK;
                    newNode.parent.parent.color = COLOR.RED;
                    rotateLeft(newNode.parent.parent);
                }
            }
        }
    }

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
        return _size;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    // TODO check if correct
    @Override
    public void clear() {
        root = null;
        _size = 0;
    }

    @Override
    public String put(Integer key, String value) {
        if (root == null) {
            root = new TreeNode(null, null, null, key, value, COLOR.BLACK);
            _size++;
            return null;
        }
        TreeNode curNode = root;
        TreeNode parent = null;
        while (curNode != null) {
            if (key.compareTo(curNode.key) > 0) {
                parent = curNode;
                curNode = curNode.right;
            }
            else if (key.compareTo(curNode.key) < 0) {
                parent = curNode;
                curNode = curNode.left;
            }
            else {
                String oldValue = curNode.value;
                curNode.value = value;
                return oldValue;
            }
        }
        curNode = new TreeNode(null, null, parent, key, value, COLOR.RED);
        if (key.compareTo(parent.key) > 0) {
            parent.right = curNode;
        }
        else {
            parent.left = curNode;
        }
        filterInsert(curNode);
        _size++;
        return null;
    }

    @Override
    public String remove(Object o) {
        return null;
    }

    @Override
    public String get(Object o) {
        TreeNode foundRoot = get(root, (Integer)o);
        return foundRoot != null ? foundRoot.value : null;
    }

    @Override
    public boolean containsKey(Object key) {
        return findByKey(root, key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        return findByValue(root, value) != null;
    }


    @Override
    public Comparator<? super Integer> comparator() {
        return null;
    }

    @Override
    public SortedMap<Integer, String> subMap(Integer integer, Integer k1) {
        return null;
    }

    @Override
    public SortedMap<Integer, String> headMap(Integer integer) {
        return null;
    }

    @Override
    public SortedMap<Integer, String> tailMap(Integer integer) {
        return null;
    }

    @Override
    public Integer firstKey() {
        return null;
    }

    @Override
    public Integer lastKey() {
        return null;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends String> map) {

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
