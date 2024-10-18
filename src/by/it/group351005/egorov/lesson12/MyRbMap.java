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

    private void clear(TreeNode node) {
        if (node != null) {
            clear(node.left);
            clear(node.right);
            node.left = node.right = node.parent = null;
        }
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

    private TreeNode getMin(TreeNode node) {
        TreeNode min = node;
        while (min.left != null) {
            min = min.left;
        }
        return min;
    }

    private TreeNode getMax(TreeNode node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    private void transplantNode(TreeNode toNode, TreeNode fromNode) {
        if (toNode.parent == null) {
            root = fromNode;
        }
        else if (toNode == toNode.parent.left) {
            toNode.parent.left = fromNode;
        }
        else {
            toNode.parent.right = fromNode;
        }
        if (fromNode != null) {
            fromNode.parent = toNode.parent;
        }
    }

    private void fixAfterInsert(TreeNode newNode) {
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

    private void fixAfterRemoving(TreeNode node) {
        while (node != root && getColor(node) == COLOR.BLACK) {
            TreeNode brother = null;
            // if node is left child of parent
            if (node == node.parent.left) {
                brother = node.parent.right;
                // if brother RED => make it BLACK, his parent BLACK and then leftRotate for parent
                if (getColor(brother) == COLOR.RED) {
                    brother.color = COLOR.BLACK;
                    brother.parent.color = COLOR.RED;
                    rotateLeft(brother.parent);
                    brother = node.parent.right;
                }
                // if brother was black or became BLACK
                // if two child of brother black, change brother color to RED:
                // and parent color to BLACK, then fix parent balance
                if (getColor(brother.left) == COLOR.BLACK && getColor(brother.right) == COLOR.BLACK) {
                    brother.color = COLOR.RED;
                    brother.parent.color = COLOR.BLACK;
                    node = node.parent;
                }
                // if one of brother's child RED
                else {
                    // if left brother's child is RED:
                    // make right brother's child RED, left brother's child BLACK
                    // and then rightRotate for brother
                    if (getColor(brother.left) == COLOR.RED) {
                        brother.right.color = COLOR.RED;
                        brother.left.color = COLOR.BLACK;
                        rotateRight(brother);
                        brother = node.parent.right;
                    }
                    // if right brother's child right is RED or became RED:
                    // make right brother's child BLACK, then make brother color his parent's color,
                    // parent color make BLACK and then right rotate for parent
                    brother.right.color = COLOR.BLACK;
                    brother.color = brother.parent.color;
                    brother.parent.color = COLOR.BLACK;
                    rotateLeft(brother.parent);
                    node = root;
                }
            }
            // if node is right child of parent
            // make all symmetric
            else {
                brother = node.parent.right;
                if (getColor(brother) == COLOR.RED) {
                    brother.color = COLOR.BLACK;
                    brother.parent.color = COLOR.RED;
                    rotateRight(brother.parent);
                    brother = node.parent.left;
                }
                if (getColor(brother.left) == COLOR.BLACK && getColor(brother.right) == COLOR.BLACK) {
                    brother.color = COLOR.RED;
                    brother.parent.color = COLOR.BLACK;
                    node = node.parent;
                }
                else {
                    if (getColor(brother.right) == COLOR.RED) {
                        brother.left.color = COLOR.RED;
                        brother.right.color = COLOR.BLACK;
                        rotateLeft(brother);
                        brother = node.parent.left;
                    }
                    brother.left.color = COLOR.BLACK;
                    brother.color = brother.parent.color;
                    brother.parent.color = COLOR.BLACK;
                    rotateRight(brother.parent);
                    node = root;
                }
            }
        }
    }

    private void headMap(TreeNode node, Integer key, MyRbMap map) {
        if (node == null) {
            return;
        }
        if (node.key.compareTo(key) < 0) {
            map.put(node.key, node.value);
            headMap(node.right, key, map);
        }
        headMap(node.left, key, map);
    }

    private void tailMap(TreeNode node, Integer key, MyRbMap map) {
        if (node == null) {
            return;
        }
        if (node.key.compareTo(key) >= 0) {
            map.put(node.key, node.value);
            tailMap(node.left, key, map);
        }
        tailMap(node.right, key, map);
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

    @Override
    public void clear() {
        clear(root);
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
        fixAfterInsert(curNode);
        _size++;
        return null;
    }

    @Override
    public String remove(Object o) {
        TreeNode removedNode = findByKey(root, o);
        if (removedNode == null) {
            return null;
        }
        String removedValue = removedNode.value;
        if (removedNode.left != null && removedNode.right != null) {
            TreeNode minNode = getMin(removedNode.right);
            removedNode.value = minNode.value;
            removedNode.key = minNode.key;
            removedNode = minNode;
        }
        TreeNode replacement = (removedNode.left != null) ? removedNode.left : removedNode.right;
        if (replacement != null) {
            transplantNode(removedNode, replacement);
            if (removedNode.color == COLOR.BLACK) {
                fixAfterRemoving(replacement);
            }
        } else {
            transplantNode(removedNode,null);
            if (getColor(removedNode) == COLOR.BLACK) {
                fixAfterRemoving(removedNode.parent);
            }
        }
        removedNode.left = removedNode.right = removedNode.parent = null;
        _size--;
        return removedValue;
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
    public SortedMap<Integer, String> headMap(Integer key) {
        MyRbMap map = new MyRbMap();
        headMap(root, key,map);
        return map;
    }

    @Override
    public SortedMap<Integer, String> tailMap(Integer key) {
        MyRbMap map = new MyRbMap();
        tailMap(root, key, map);
        return map;
    }

    @Override
    public Integer firstKey() {
        TreeNode node = getMin(root);
        if (node == null) {
            return null;
        }
        return node.key;
    }

    @Override
    public Integer lastKey() {
        TreeNode node = getMax(root);
        if (node == null) {
            return null;
        }
        return node.key;
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
