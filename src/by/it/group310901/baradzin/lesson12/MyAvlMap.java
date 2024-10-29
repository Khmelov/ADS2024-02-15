package by.it.group310901.baradzin.lesson12;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class MyAvlMap implements Map<Integer, String> {

    int length;
    Node root;

    static class Node {
        String value;
        int key, height = 1;
        Node left, right;

        public Node(int key, String value) {
            this.key = key;
            this.value = value;
        }

        public Node get(int key) {
            return key == this.key ? this
                    : key < this.key
                            ? left == null
                                    ? null
                                    : left.get(key)
                            : right == null
                                    ? null
                                    : right.get(key);
        }

        public boolean containsKey(int key) {
            return this.key == key ? true
                    : key < this.key
                            ? left == null
                                    ? false
                                    : left.containsKey(key)
                            : right == null
                                    ? false
                                    : right.containsKey(key);
        }

        public Node balance() {
            var leftHeight = left == null ? 0 : left.height;
            var rightHeight = right == null ? 0 : right.height;
            var balance = leftHeight - rightHeight;
            var isRight = balance == -2;

            if (!(isRight && balance == 2))
                return this;

            var target = isRight ? right : left;

            if (key == target.key)
                return this;
            if (key < target.key)
                return rotate(isRight);
            target = target.rotate(isRight);
            return rotate(isRight);
        }

        public Node rotate(boolean isRight) {
            if (isRight) {
                left.right = this;
                left = right;
                height = Math.max(left.height, right.height + 1);
                left.height = Math.max(left.left.height, left.right.height + 1);
            } else {
                right.left = this;
                right = left;
                height = Math.max(left.height, right.height + 1);
                right.height = Math.max(right.left.height, right.right.height + 1);
            }
            return left;
        }

        public Node getMin() {
            return left == null ? this : left.getMin();
        }
    }

    public Node put(Node node, int key, String value) {
        if (node == null) {
            length++;
            return new Node(key, value);
        }
        if (key < node.key) {
            node.left = put(node.left, key, value);
            return node.balance();
        }
        if (key > node.key) {
            node.right = put(node.right, key, value);
            return node.balance();
        }
        node.value = value;
        return node;
    }

    public Node remove(Node node, int key) {
        if (node == null) {
            return null;
        }
        if (key < node.key) {
            node.left = remove(node.left, key);
        } else if (key > node.key) {
            node.right = remove(node.right, key);
        } else if (node.left == null || node.right == null) {
            node = (node.left != null) ? node.left : node.right;
        } else {
            Node successor = node.right.getMin();
            node.key = successor.key;
            node.value = successor.value;
            node.right = remove(node.right, successor.key);
        }
        if (node != null) {
            return node.balance();
        }
        return null;
    }

    // ----- required ----------------------------------------------------------

    @Override
    public String toString() {
        var sb = new StringBuilder("{");
        var iter = iterator();
        Node node;
        if (iter.hasNext())
            sb.append((node = iter.next()).key).append("=").append(node.value);
        while (iter.hasNext())
            sb.append(", ").append((node = iter.next()).key).append("=").append(node.value);
        return sb.append("}").toString();
    }

    @Override
    public String put(Integer key, String value) {
        if (key == null)
            throw new NullPointerException();
        var old = get(key);
        root = put(root, key, value);
        return old;
    }

    @Override
    public String remove(Object key) {
        if (!(key instanceof Integer))
            return null;
        var removedValue = get(key);
        root = remove(root, (int) key);
        if (removedValue != null)
            length--;
        return removedValue;
    }

    @Override
    public String get(Object key) {
        if (!(key instanceof Integer) || root == null)
            return null;
        var node = root.get((int) key);
        return node == null ? null : node.value;
    }

    @Override
    public boolean containsKey(Object key) {
        return key instanceof Integer ? root.containsKey((int) key) : false;
    }

    @Override
    public int size() {
        return length;
    }

    @Override
    public void clear() {
        length = 0;
        root = null;
    }

    @Override
    public boolean isEmpty() {
        return length == 0;
    }

    // ===== optional ==========================================================

    private Iterator<Node> iterator() {
        return new Iterator<Node>() {
            Node current = root;
            Stack<Node> stack = new Stack<>();

            @Override
            public boolean hasNext() {
                return !stack.isEmpty() || current != null;
            }

            @Override
            public Node next() {
                for (; current != null; current = current.left)
                    stack.push(current);
                current = stack.pop();
                var node = current;
                current = current.right;
                return node;
            }
        };
    }

    @Override
    public boolean containsValue(Object value) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'containsValue'");
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'putAll'");
    }

    @Override
    public Set<Integer> keySet() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keySet'");
    }

    @Override
    public Collection<String> values() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'values'");
    }

    @Override
    public Set<Entry<Integer, String>> entrySet() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'entrySet'");
    }

}
