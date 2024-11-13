package by.it.group310901.baradzin.lesson12;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.Stack;

public class MyRbMap implements SortedMap<Integer, String> {

    private enum Color {
        red, black
    }

    private Node root;
    private int length;

    private static class Node {
        Integer key;
        String value;
        Node left, right;
        Color color;

        public Node(int key, String value) {
            this(key, value, Color.red);
        }

        public Node(int key, String value, Color color) {
            this.key = key;
            this.value = value;
            this.color = color;
        }

        public Node first() {
            return left == null ? this : left.first();
        }

        public Node last() {
            return right == null ? this : right.last();
        }

        public MyRbMap headMap(Integer key, MyRbMap map) {
            if (left != null)
                left.headMap(key, map);
            if (key.compareTo(this.key) <= 0)
                return map;
            map.put(this.key, value);
            return right == null ? map : right.headMap(key, map);
        }

        public MyRbMap tailMap(Integer key, MyRbMap map) {
            if (right != null)
                right.tailMap(key, map);
            if (key.compareTo(this.key) > 0)
                return map;
            map.put(this.key, value);
            return left == null ? map : left.tailMap(key, map);
        }

        public Node get(Integer key) {
            return key.equals(this.key) ? this
                    : key.compareTo(this.key) < 0
                            ? left == null ? null : left.get(key)
                            : right == null ? null : right.get(key);
        }

        public boolean containsValue(Object value) {
            return value.equals(this.value)
                    || left != null && left.containsValue(value)
                    || right != null && right.containsValue(value);
        }

        private void flipColors() {
            color = color == Color.red ? Color.black : Color.red;
            left.color = color == Color.red ? Color.black : Color.red;
            right.color = color == Color.red ? Color.black : Color.red;
        }

        private Node put(Integer key, String value) {
            if (key.compareTo(this.key) < 0)
                left = left == null ? new Node(key, value) : left.put(key, value);
            else if (key.compareTo(this.key) > 0)
                right = right == null ? new Node(key, value) : right.put(key, value);
            else
                this.value = value;

            if (isRed(right) && !isRed(left))
                return rotateLeft();
            if (isRed(left) && isRed(left.left))
                return rotateRight();
            if (isRed(left) && isRed(right))
                flipColors();

            return this;
        }

        private Node rotateLeft() {
            var x = right;
            right = x.left;
            x.left = this;
            x.color = color;
            color = Color.red;
            return x;
        }

        private Node rotateRight() {
            var x = left;
            left = x.right;
            x.right = this;
            x.color = color;
            color = Color.red;
            return x;
        }

        private boolean isRed(Node node) {
            return node != null && node.color == Color.red;
        }

        private Node remove(Integer key) {
            var cmp = key.compareTo(this.key);
            if (cmp < 0)
                left = left == null ? null : left.remove(key);
            else if (cmp > 0)
                right = right == null ? null : right.remove(key);
            else if (left == null || right == null) {
                var temp = left == null ? right : left;
                return temp == null ? null : temp;
            } else {
                var successor = right;
                while (successor.left != null)
                    successor = successor.left;
                this.key = successor.key;
                this.value = successor.value;
                right = right.remove(successor.key);
            }
            return balance();
        }

        private Node balance() {
            if (isRed(right))
                return rotateLeft();
            if (isRed(left) && isRed(left.left))
                return rotateRight();
            if (isRed(left) && isRed(right))
                flipColors();
            return this;
        }
    }

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
        var node = root == null ? null : root.get(key);
        String old = null;
        if (node == null)
            length++;
        else
            old = node.value;
        root = root == null ? new Node(key, value) : root.put(key, value);
        root.color = Color.black;
        return old;
    }

    @Override
    public String remove(Object key) {
        if (!(key instanceof Integer))
            return null;
        var target = root == null ? null : root.get((Integer) key);
        if (target == null)
            return null;
        length--;
        var removedValue = target.value;
        root = root == null ? null : root.remove((Integer) key);
        if (root != null)
            root.color = Color.black;
        return removedValue;
    }

    @Override
    public String get(Object key) {
        var node = root == null || !(key instanceof Integer)
                ? null
                : root.get((Integer) key);
        return node == null ? null : node.value;
    }

    @Override
    public boolean containsKey(Object key) {
        return key instanceof Integer && root != null && root.get((Integer) key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        return value instanceof String && root != null && root.containsValue(value);
    }

    @Override
    public int size() {
        return length;
    }

    @Override
    public void clear() {
        root = null;
        length = 0;
    }

    @Override
    public boolean isEmpty() {
        return length == 0;
    }

    @Override
    public SortedMap<Integer, String> headMap(Integer toKey) {
        var map = new MyRbMap();
        return root.headMap(toKey, map);
    }

    @Override
    public SortedMap<Integer, String> tailMap(Integer fromKey) {
        var map = new MyRbMap();
        return root.tailMap(fromKey, map);
    }

    @Override
    public Integer firstKey() {
        if (root == null)
            throw new NullPointerException();
        return root.first().key;
    }

    @Override
    public Integer lastKey() {
        if (root == null)
            throw new NullPointerException();
        return root.last().key;
    }

    // ===== optional ==========================================================

    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'putAll'");
    }

    @Override
    public Comparator<? super Integer> comparator() {
        return Integer::compareTo;
    }

    @Override
    public SortedMap<Integer, String> subMap(Integer fromKey, Integer toKey) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'subMap'");
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
