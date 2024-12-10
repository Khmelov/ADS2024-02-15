package by.it.group310901.baradzin.lesson12;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.Stack;

public class MySplayMap implements NavigableMap<Integer, String> {

    private int length = 0;
    private Node root;

    static class Node {
        Integer key;
        String value;
        Node parent, left, right;

        public Node(int key, String value) {
            this.key = key;
            this.value = value;
        }

        public MySplayMap headMap(Integer key, MySplayMap map) {
            if (left != null)
                left.headMap(key, map);
            if (key.compareTo(this.key) <= 0)
                return map;
            map.put(this.key, value);
            return right == null ? map : right.headMap(key, map);
        }

        public MySplayMap tailMap(Integer key, MySplayMap map) {
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

        public Node rotateLeft() {
            var head = right;
            right = head.left;
            head.left = this;
            return head;
        }

        public Node rotateRight() {
            var head = left;
            left = head.right;
            head.right = this;
            return head;
        }

        public Node getMax() {
            return right == null ? this : right.getMax();
        }

        public Node getMin() {
            return left == null ? this : left.getMin();
        }

        private Node getLower(Integer key) {
            if (key.compareTo(this.key) > 0) {
                var node = right == null ? null : right.getLower(key);
                return node == null ? this : node;
            }
            return left == null ? null : left.getLower(key);
        }

        private Node getFloor(Integer key) {
            if (key.compareTo(this.key) >= 0) {
                var node = right == null ? null : right.getFloor(key);
                return node == null ? this : node;
            }
            return left == null ? null : left.getFloor(key);
        }

        private Node getCeiling(Integer key) {
            if (key.compareTo(this.key) <= 0) {
                var node = left == null ? null : left.getCeiling(key);
                return node == null ? this : node;
            }
            return right == null ? null : right.getCeiling(key);
        }

        private Node getHigher(Integer key) {
            if (key.compareTo(this.key) < 0) {
                var node = left == null ? null : left.getHigher(key);
                return node == null ? this : node;
            }
            return right == null ? null : right.getHigher(key);
        }
    }

    private Node splay(Node node, Integer key) {
        if (node == null)
            return null;
        var cmp = key.compareTo(node.key);
        if (cmp == 0)
            return node;
        var target = cmp < 0 ? node.left : node.right;
        if (target == null)
            return node;
        if (cmp < 0) {
            if ((cmp = key.compareTo(target.key)) > 0) {
                target.right = splay(target.right, key);
                if (target.right != null)
                    node.left = target.rotateLeft();
            } else if (cmp < 0) {
                target.left = splay(target.left, key);
                node = node.rotateRight();
            }
            return node.left == null ? node : node.rotateRight();
        }
        if ((cmp = key.compareTo(target.key)) < 0) {
            target.left = splay(target.left, key);
            if (target.left != null)
                node.right = target.rotateRight();
        } else if (cmp > 0) {
            target.right = splay(target.right, key);
            node = node.rotateLeft();
        }
        return node.right == null ? node : node.rotateLeft();
    }

    private Node insert(Node node, Integer key, String value) {
        if (node == null)
            return new Node(key, value);
        switch (key.compareTo(node.key)) {
            case -1:
                node.left = insert(node.left, key, value);
                break;
            case 1:
                node.right = insert(node.right, key, value);
                break;
            default:
                node.value = value;
                return node;
        }
        return splay(node, key);
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
        var prev = get(key);
        root = insert(root, key, value);
        if (prev == null)
            length++;
        return prev;
    }

    @Override
    public String remove(Object key) {
        if (root == null || key == null || !(key instanceof Integer))
            return null;
        root = splay(root, (Integer) key);
        if (root == null)
            return null;
        if (((Integer) key).compareTo(root.key) != 0)
            return null;
        var removed = root.value;
        if (root.left == null) {
            root = root.right;
            if (root != null)
                root.parent = null;
        } else {
            var newRoot = root.right;
            if (newRoot != null) {
                newRoot = splay(newRoot, (Integer) key);
                if (newRoot != null) {
                    newRoot.left = root.left;
                    if (newRoot.left != null)
                        newRoot.left.parent = newRoot;
                }
                root = newRoot;
            } else {
                root = root.left;
                if (root != null)
                    root.parent = null;
            }
        }
        length--;
        return removed;
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
    public NavigableMap<Integer, String> headMap(Integer toKey) {
        return root.headMap(toKey, new MySplayMap());
    }

    @Override
    public NavigableMap<Integer, String> tailMap(Integer fromKey) {
        return root.tailMap(fromKey, new MySplayMap());
    }

    @Override
    public Integer firstKey() {
        return root == null ? null : root.getMin().key;
    }

    @Override
    public Integer lastKey() {
        return root == null ? null : root.getMax().key;
    }

    @Override
    public Integer lowerKey(Integer key) {
        return root == null ? null : root.getLower(key).key;
    }

    @Override
    public Integer floorKey(Integer key) {
        if (root == null)
            return null;
        var node = root.getFloor(key);
        return node == null ? null : node.key;
    }

    @Override
    public Integer ceilingKey(Integer key) {
        return root == null ? null : root.getCeiling(key).key;
    }

    @Override
    public Integer higherKey(Integer key) {
        return root == null ? null : root.getHigher(key).key;
    }

    // ===== optional ==========================================================

    @Override
    public Comparator<? super Integer> comparator() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'comparator'");
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

    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'putAll'");
    }

    @Override
    public Entry<Integer, String> lowerEntry(Integer key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'lowerEntry'");
    }

    @Override
    public Entry<Integer, String> floorEntry(Integer key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'floorEntry'");
    }

    @Override
    public Entry<Integer, String> ceilingEntry(Integer key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'ceilingEntry'");
    }

    @Override
    public Entry<Integer, String> higherEntry(Integer key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'higherEntry'");
    }

    @Override
    public Entry<Integer, String> firstEntry() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'firstEntry'");
    }

    @Override
    public Entry<Integer, String> lastEntry() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'lastEntry'");
    }

    @Override
    public Entry<Integer, String> pollFirstEntry() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'pollFirstEntry'");
    }

    @Override
    public Entry<Integer, String> pollLastEntry() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'pollLastEntry'");
    }

    @Override
    public NavigableMap<Integer, String> descendingMap() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'descendingMap'");
    }

    @Override
    public NavigableSet<Integer> navigableKeySet() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'navigableKeySet'");
    }

    @Override
    public NavigableSet<Integer> descendingKeySet() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'descendingKeySet'");
    }

    @Override
    public NavigableMap<Integer, String> subMap(Integer fromKey, boolean fromInclusive, Integer toKey,
            boolean toInclusive) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'subMap'");
    }

    @Override
    public SortedMap<Integer, String> subMap(Integer fromKey, Integer toKey) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'subMap'");
    }

    @Override
    public NavigableMap<Integer, String> headMap(Integer toKey, boolean inclusive) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'headMap'");
    }

    @Override
    public NavigableMap<Integer, String> tailMap(Integer fromKey, boolean inclusive) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'tailMap'");
    }
}
