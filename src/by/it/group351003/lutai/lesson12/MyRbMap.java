package by.it.group351003.lutai.lesson12;

import by.it.group351003.lutai.lesson12.RbNode;


import java.util.*;

public class MyRbMap implements SortedMap<Integer,String> {
    private RbNode root = null;
    private int size = 0;

    private static final boolean BLACK = false;
    private static final boolean RED = true;

    @Override
    public Comparator<? super Integer> comparator() {
        return null;
    }

    @Override
    public SortedMap<Integer, String> subMap(Integer fromKey, Integer toKey) {
        return null;
    }

    @Override
    public SortedMap<Integer, String> headMap(Integer toKey) {
        SortedMap<Integer, String> map = new TreeMap<>();
        headMapHelper(root,toKey,map);
        return map;
    }

    private void headMapHelper(RbNode node, int toKey, SortedMap<Integer, String> map) {
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
    public SortedMap<Integer, String> tailMap(Integer fromKey) {
        SortedMap<Integer, String> map = new TreeMap<>();
        tailMapHelper(root,fromKey,map);
        return map;
    }

    private void tailMapHelper(RbNode node, int fromKey, SortedMap<Integer, String> map) {
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
    public Integer firstKey() {
        Integer result = null;
        RbNode current = root;
        if(current != null) {
            if(current.left != null){
                while(current.left != null){
                    current = current.left;
                }
            }
            result = current.key;
        }
        return result;
    }

    @Override
    public Integer lastKey() {
        Integer result = null;
        RbNode current = root;
        if(current != null){
            if(current.right != null){
                while(current.right != null){
                    current = current.right;
                }
            }
            result = current.key;
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
        RbNode node = findNode((int) key);
        return node != null;
    }

    private boolean findValue(RbNode node, Object value){
        if(node != null){
            if(Objects.equals(value,node.value)){
                return true;
            }
            if(node.left != null){
                return findValue(node.left,value);
            }
            if(node.right != null){
                return findValue(node.right,value);
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return findValue(root,value);
    }

    @Override
    public String get(Object key) {
        String result = "";
        RbNode node = root;
        int index = (int) key;
        boolean found = false;
        while(!found){
            if(index < node.key){
                node = node.left;
            }else if(index > node.key){
                node = node.right;
            }else{
                found = true;
                result = node.value;
            }
            if(node == null){
                found = true;
                result = null;
            }
        }
        return result;
    }

    private void rotateLeft(RbNode x){
        RbNode y = x.right;
        x.right = y.left;
        if(y.left != null) y.left.parent = x;
        y.parent = x.parent;
        if(x.parent == null) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        }else{
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    private void rotateRight(RbNode y){
        RbNode x = y.left;
        y.left = x.right;
        if(x.right != null) x.right.parent = y;
        x.parent = y.parent;
        if(y.parent == null) {
            root = x;
        }else if(y == y.parent.right){
            y.parent.right = x;
        }else{
            y.parent.left = x;
        }
        x.right = y;
        y.parent = x;
    }

    private void fixAfterInsertion(RbNode node) {
        RbNode parent;
        RbNode grandParent;
        RbNode uncle;
        while(node != root && node.parent.color == RED){
            parent = node.parent;
            grandParent = parent.parent;
            if(parent == grandParent.left) {
                uncle = grandParent.right;
                if (uncle != null && uncle.color == RED) {
                    parent.color = BLACK;
                    grandParent.color = RED;
                    uncle.color = BLACK;
                    node = grandParent;
                }else{
                    if(node == parent.right){
                        node = parent;
                        rotateLeft(node);
                    }
                    parent.color = BLACK;
                    grandParent.color = RED;
                    rotateRight(grandParent);
                }
            }else{
                uncle = grandParent.left;
                if (uncle != null && uncle.color == RED) {
                    parent.color = BLACK;
                    grandParent.color = RED;
                    uncle.color = BLACK;
                    node = grandParent;
                }else{
                    if(node == parent.left){
                        node = parent;
                        rotateRight(node);
                    }
                    parent.color = BLACK;
                    grandParent.color = RED;
                    rotateLeft(grandParent);
                }
            }
            root.color = BLACK;
        }
    }

    @Override
    public String put(Integer key, String value) {
        String res = null;
        boolean finished = false;
        if (root == null) {
            root = new RbNode(BLACK,key,value,null);
            root.color = BLACK;
            size++;
        } else {
            String result;
            boolean notAdded = true;
            RbNode addNode = new RbNode(RED,key,value,null);
            RbNode current = root;
            RbNode parent = null;
            while(notAdded) {
                parent = current;
                if(key < current.key) {
                    current = current.left;
                    if(current == null) {
                        notAdded = false;
                        parent.left = addNode;
                    }
                }else if(key > current.key) {
                    current = current.right;
                    if(current == null) {
                        notAdded = false;
                        parent.right = addNode;
                    }
                }else{
                    notAdded = false;
                    result = current.value;
                    current.value = value;
                    res = result;
                    finished = true;
                }
            }
            if (!finished) {
                addNode.parent = parent;
                size++;
                fixAfterInsertion(addNode);
            }
        }

        return res;
    }

    @Override
    public String remove(Object key) {
        RbNode node = findNode((Integer) key);
        if (node == null) {
            return null;
        }

        String oldValue = node.value;
        deleteNode(node);
        size--;
        return oldValue;
    }

    private RbNode findNode(int key) {
        RbNode current = root;
        while (current != null) {
            if (key < current.key) {
                current = current.left;
            } else if (key > current.key) {
                current = current.right;
            } else {
                return current;
            }
        }
        return null;
    }

    private void deleteNode(RbNode node) {
        RbNode replacement;
        if (node.left == null || node.right == null) {
            replacement = node;
        } else {
            replacement = getMinNode(node.right);
        }

        RbNode child = (replacement.left != null) ? replacement.left : replacement.right;

        if (child != null) {
            child.parent = replacement.parent;
        }

        if (replacement.parent == null) {
            root = child;
        } else if (replacement == replacement.parent.left) {
            replacement.parent.left = child;
        } else {
            replacement.parent.right = child;
        }

        if (replacement != node) {
            node.key = replacement.key;
            node.value = replacement.value;
        }

        if (replacement.color == BLACK) {
            fixAfterDeletion(child, replacement.parent);
        }
    }

    private RbNode getMinNode(RbNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    private void fixAfterDeletion(RbNode node, RbNode parent) {
        while (node != root && (node == null || node.color == BLACK)) {
            if (node == parent.left) {
                RbNode sibling = parent.right;

                if (sibling != null) {
                    if (sibling.color == RED) {
                        sibling.color = BLACK;
                        parent.color = RED;
                        rotateLeft(parent);
                        sibling = parent.right;
                    }
                    if (sibling.left == null || sibling.left.color == BLACK) {
                        if (sibling.right == null || sibling.right.color == BLACK) {
                            sibling.color = RED;
                            node = parent;
                            parent = node.parent;
                        } else {
                            if (sibling.right == null || sibling.right.color == BLACK) {
                                sibling.left.color = BLACK;
                                sibling.color = RED;
                                rotateRight(sibling);
                                sibling = parent.right;
                            }
                            sibling.color = parent.color;
                            parent.color = BLACK;
                            if (sibling.right != null) {
                                sibling.right.color = BLACK;
                            }
                            rotateLeft(parent);
                            node = root;
                        }
                    } else {
                        if (sibling.right == null || sibling.right.color == BLACK) {
                            sibling.left.color = BLACK;
                            sibling.color = RED;
                            rotateRight(sibling);
                            sibling = parent.right;
                        }
                        sibling.color = parent.color;
                        parent.color = BLACK;
                        if (sibling.right != null) {
                            sibling.right.color = BLACK;
                        }
                        rotateLeft(parent);
                        node = root;
                    }
                } else {
                    node = root;
                }
            } else {
                RbNode sibling = parent.left;

                if (sibling != null) {
                    if (sibling.color == RED) {
                        sibling.color = BLACK;
                        parent.color = RED;
                        rotateRight(parent);
                        sibling = parent.left;
                    }
                    if ((sibling.left == null || sibling.left.color == BLACK) &&
                            (sibling.right == null || sibling.right.color == BLACK)) {
                        sibling.color = RED;
                        node = parent;
                        parent = node.parent;
                    } else {
                        if (sibling.left == null || sibling.left.color == BLACK) {
                            sibling.right.color = BLACK;
                            sibling.color = RED;
                            rotateLeft(sibling);
                            sibling = parent.left;
                        }
                        sibling.color = parent.color;
                        parent.color = BLACK;
                        if (sibling.left != null) {
                            sibling.left.color = BLACK;
                        }
                        rotateRight(parent);
                        node = root;
                    }
                } else {
                    node = root;
                }
            }
        }
        if (node != null) {
            node.color = BLACK;
        }
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

    public void leftTreeTravel(RbNode node, StringBuilder sb){
        if(node != null){
            if (node.left != null) {
                leftTreeTravel(node.left, sb);
            }
            sb.append(node.key).append("=").append(node.value).append(", ");
            if (node.right != null) {
                leftTreeTravel(node.right, sb);
            }
        }
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        leftTreeTravel(root, sb);
        if(sb.length() > 2) {
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append("}");
        return sb.toString();
    }


}

