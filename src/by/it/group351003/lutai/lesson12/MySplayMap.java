package by.it.group351003.lutai.lesson12;

import java.util.*;


public class MySplayMap implements NavigableMap<Integer,String> {

    private MySplayMap.Node root = null;
    private int size =  0;
    private static class Node{
        int key;
        String value;
        Node left;
        Node right;

        public Node(int key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    @Override
    public Entry<Integer, String> lowerEntry(Integer key) {
        return null;
    }

    @Override
    public Integer lowerKey(Integer key) {
        Integer result = null;
        Node temp = root;
        while(temp!=null){
            if(temp.key < key){
                result = temp.key;
                temp = temp.right;
            }
            else{
                temp = temp.left;
            }
        }
        return result;
    }

    @Override
    public Entry<Integer, String> floorEntry(Integer key) {
        return null;
    }

    @Override
    public Integer floorKey(Integer key) {
        Integer res = null;
        Integer result = null;
        Node temp = root;
        boolean found = false;
        while (!found && temp != null) {
            if (temp.key == key) {
                res = temp.key;
                found = true;
            } else if (temp.key < key) {
                result = temp.key;
                temp = temp.right;
            } else {
                temp = temp.left;
            }
        }
        if (res == null) {
            res = result;
        }
        return res;
    }

    @Override
    public Entry<Integer, String> ceilingEntry(Integer key) {
        return null;
    }

    @Override
    public Integer ceilingKey(Integer key) {
        Integer res = null;
        Integer result = null;
        Node temp = root;
        boolean found = false;
        while (!found && temp != null) {
            if (temp.key == key) {
                res = temp.key;
                found = true;
            } else if (temp.key > key) { // ищем наименьший ключ, >= key
                result = temp.key;
                temp = temp.left;
            } else {
                temp = temp.right;
            }
        }
        if (res == null) {
            res = result;
        }
        return res;
    }

    @Override
    public Entry<Integer, String> higherEntry(Integer key) {
        return null;
    }

    @Override
    public Integer higherKey(Integer key) {
        Integer result = null;
        Node temp = root;
        while (temp != null) {
            if (temp.key > key) { // ищем наименьший ключ, больший key
                result = temp.key;
                temp = temp.left;
            } else {
                temp = temp.right;
            }
        }
        return result;
    }

    @Override
    public Entry<Integer, String> firstEntry() {
        return null;
    }

    @Override
    public Entry<Integer, String> lastEntry() {
        return null;
    }

    @Override
    public Entry<Integer, String> pollFirstEntry() {
        return null;
    }

    @Override
    public Entry<Integer, String> pollLastEntry() {
        return null;
    }

    @Override
    public NavigableMap<Integer, String> descendingMap() {
        return null;
    }

    @Override
    public NavigableSet<Integer> navigableKeySet() {
        return null;
    }

    @Override
    public NavigableSet<Integer> descendingKeySet() {
        return null;
    }

    @Override
    public NavigableMap<Integer, String> subMap(Integer fromKey, boolean fromInclusive, Integer toKey, boolean toInclusive) {
        return null;
    }

    @Override
    public NavigableMap<Integer, String> headMap(Integer toKey, boolean inclusive) {
        return null;
    }

    private void headMapHelper(Node node, int toKey, NavigableMap<Integer, String> map) {
        if(node != null){
            if(node.key < toKey){
                headMapHelper(node.left, toKey, map);
                map.put(node.key, node.value);
                headMapHelper(node.right, toKey, map);
            }else{
                headMapHelper(node.left, toKey, map);
            }
        }
    }

    @Override
    public NavigableMap<Integer, String> tailMap(Integer fromKey, boolean inclusive) {
        return null;
    }

    private void tailMapHelper(Node node, int fromKey, SortedMap<Integer, String> map) {
        if(node != null){
            if(node.key >= fromKey){
                map.put(node.key, node.value);
                tailMapHelper(node.left, fromKey, map);
                tailMapHelper(node.right, fromKey, map);
            }else{
                tailMapHelper(node.right, fromKey, map);
            }
        }
    }

    @Override
    public Comparator<? super Integer> comparator() {
        return null;
    }

    @Override
    public SortedMap<Integer, String> subMap(Integer fromKey, Integer toKey) {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        toStringHelper(root, sb);
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2);
        }
        sb.append("}");
        return sb.toString();
    }

    private void toStringHelper(Node node, StringBuilder sb) {
        if (node != null) {
            toStringHelper(node.left, sb);
            sb.append(node.key).append("=").append(node.value).append(", ");
            toStringHelper(node.right, sb);
        }
    }

    @Override
    public SortedMap<Integer, String> headMap(Integer toKey) {
        NavigableMap<Integer, String> result = new TreeMap<>();
        headMapHelper(root,toKey,result);
        return result;
    }

    @Override
    public SortedMap<Integer, String> tailMap(Integer fromKey) {
        NavigableMap<Integer, String> result = new TreeMap<>();
        tailMapHelper(root,fromKey,result);
        return result;
    }

    @Override
    public Integer firstKey() {
        Integer result = null;
        if (!isEmpty()) {
            Node node = root;
            while (node.left != null) node = node.left;
            result = node.key;
        }
        return result;
    }

    @Override
    public Integer lastKey() {
        Integer result = null;
        if (!isEmpty()) {
            Node node = root;
            while (node.right != null) node = node.right;
            result = node.key;
        }
        return result;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        root = splay(root, (int) key);
        return root != null && root.key == (int)key;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    private Node rotateRight(Node x){
        Node y = x.left;
        x.left = y.right;
        y.right = x;
        return y;
    }

    private Node rotateLeft(Node x){
        Node y = x.right;
        x.right = y.left;
        y.left = x;
        return y;
    }

    private Node splay(Node root, int key) {
        Node result;
        if (root == null || root.key == key) {
            result = root;
        } else if (key < root.key) {
            if (root.left == null) {
                result = root;
            } else {// Zig-Zig (левый левый)
                if (key < root.left.key) {
                    root.left.left = splay(root.left.left, key);
                    root = rotateRight(root);
                }
                // Zig-Zag (левый правый)
                else if (key > root.left.key) {
                    root.left.right = splay(root.left.right, key);
                    if (root.left.right != null) {
                        root.left = rotateLeft(root.left);
                    }
                }
                result = root.left == null ? root : rotateRight(root);
            }

        } else if (root.right == null) {
            result = root;
        } else {// Zig-Zig (правый правый)
            if (key > root.right.key) {
                root.right.right = splay(root.right.right, key);
                root = rotateLeft(root);
            }
            // Zig-Zag (правый левый)
            else if (key < root.right.key) {
                root.right.left = splay(root.right.left, key);
                if (root.right.left != null) {
                    root.right = rotateRight(root.right);
                }
            }
            result = root.right == null ? root : rotateLeft(root);
        }

        return result;
    }

    @Override
    public String get(Object key) {
        root = splay(root, (Integer) key);
        return root != null && root.key == (int)key ? root.value : null;
    }

    @Override
    public String put(Integer key, String value) {
        String result = null;
        if (root == null) {
            root = new Node(key, value);
            size++;
        } else {
            root = splay(root, key);
            if (root.key == key) {
                String oldValue = root.value;
                root.value = value;
                result = oldValue;
            } else {
                Node newNode = new Node(key, value);
                if (key < root.key) {
                    newNode.right = root;
                    newNode.left = root.left;
                    root.left = null;
                } else {
                    newNode.left = root;
                    newNode.right = root.right;
                    root.right = null;
                }
                root = newNode;
                size++;
            }
        }
        return result;
    }

    @Override
    public String remove(Object key) {
        String result = null;
        if (root != null) {
            root = splay(root, (Integer) key);
            if (root.key == (int) key) {
                String removedValue = root.value;
                if (root.left == null) {
                    root = root.right;
                } else {
                    Node temp = root.right;
                    root = root.left;
                    root = splay(root, (int) key);
                    root.right = temp;
                }
                size--;
                result = removedValue;
            }
        }

        return result;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {

    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public Set<Integer> keySet() {
        return Set.of();
    }

    @Override
    public Collection<String> values() {
        return List.of();
    }

    @Override
    public Set<Entry<Integer, String>> entrySet() {
        return Set.of();
    }
}

