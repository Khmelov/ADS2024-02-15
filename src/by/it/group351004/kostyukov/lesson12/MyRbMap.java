package by.it.group351004.kostyukov.lesson12;

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
    private TreeNode _root = null;
    private int _size = 0;

    private String inOrderTravel(TreeNode node) { //рекурсивный симметричный обход
        if (node == null) {
            return "";
        }
        String result = inOrderTravel(node.left);     //A
        result += node.key + "=" + node.value + ", ";    //R
        return result + inOrderTravel(node.right);    //B
    }
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('{');
        stringBuilder.append(inOrderTravel(_root));
        if (stringBuilder.length() > 1) {
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
    private void clear(TreeNode node) {
        if (node != null) {
            clear(node.left);
            clear(node.right);
            node.left = node.right = node.parent = null;
        }
    }
    private void rotateRight(TreeNode node) {
        TreeNode leftChild = node.left;                 //запоминаем -ЛЕВОГО- сына (будет новым корнем поддерева)
        node.left = leftChild.right;                    //-ЛЕВЫЙ- указатель на правого внука
        if (leftChild.right != null) {
            leftChild.right.parent = node;
        }
        leftChild.right = node;                         //-ПРАВЫЙ- указатель нового корня на старый корень
        leftChild.parent = node.parent;
        node.parent = leftChild;                        //родителем старого корня стал новый корень
        if (leftChild.parent == null) {
            _root = leftChild;                          //если у нового корня поддерева нет родителей то он становится корнем дерева
        }
        else if (leftChild.parent.left == node) {
            leftChild.parent.left = leftChild;
        }
        else {
            leftChild.parent.right = leftChild;
        }
    }
    private void rotateLeft(TreeNode node) {
        TreeNode rightChild = node.right;       //запоминаем -ПРАВОГО- сына (будет новым корнем поддерева)
        node.right = rightChild.left;           //-ПРАВЫЙ- указатель на левого внука
        if (rightChild.left != null) {
            rightChild.left.parent = node;
        }
        rightChild.left = node;                 //-ЛЕВЫЙ- указатель нового корня на старый корень
        rightChild.parent = node.parent;

        node.parent = rightChild;              //родителем старого корня стал новый корень
        if (rightChild.parent == null) {
            _root = rightChild;                 //если у нового корня поддерева нет родителей то он становится корнем дерева
        }
        else if (rightChild.parent.left == node) {
            rightChild.parent.left = rightChild;
        }
        else {
            rightChild.parent.right = rightChild;
        }
    }

    private TreeNode findByValue(TreeNode root, Object value) {
        if (root == null)
            return null;
        if (root.value.equals(value))
            return root;
        TreeNode leftNode = findByValue(root.left, value);
        TreeNode rightNode = findByValue(root.right, value);
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
            _root = fromNode;
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
        if (newNode == _root) {                 //если вставленная вершина корень - просто перекрасим в чёрный
            newNode.color = COLOR.BLACK;
            return;
        }

        TreeNode parent = newNode.parent;
        while (newNode != _root && getColor(parent) == COLOR.RED) {    //свойства дерева не нарушаются если родитель черный
            if (parent == parent.parent.left) {           //если узел - левый потомок
                TreeNode uncle = parent.parent.right;
                if (getColor(uncle) == COLOR.RED) {           //если дядя красный
                    uncle.color = parent.color = COLOR.BLACK; //родителя и дядю в чёрный цвет
                    uncle.parent.color = COLOR.BLACK;
                    newNode = parent.parent;                  //поднимаемся по дереву и исправляем дальше
                    parent = newNode.parent;
                }
                else {                                    //если дядя чёрный
                    if (newNode == parent.right) {        //если это правый потомок - понадобится 2 поворота и перекраска
                        newNode = parent;
                        rotateLeft(newNode);
                    }
                    newNode.parent.color = COLOR.BLACK;     //если левый - только 1 поворот
                    newNode.parent.parent.color = COLOR.RED;
                    rotateRight(newNode.parent.parent);
                }
            }
            else {                                         //если узел - правый потомок
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
        while (node != _root && getColor(node) == COLOR.BLACK) {
            TreeNode brother = null;

            if (node == node.parent.left) {    // если это левый потомок
                brother = node.parent.right;

                if (getColor(brother) == COLOR.RED) { // если брат красный, делаем его чёрным, его родителя чёрным и затем leftRotate для родителя
                    brother.color = COLOR.BLACK;
                    brother.parent.color = COLOR.RED;
                    rotateLeft(brother.parent);
                    brother = node.parent.right;
                }
                // если брат был чёрным или стал чёрным
                // если два ребенка брата чёрные, перекрасим брата на красный
                // и родительский цвет на чёрный, затем балансируем выше
                if (getColor(brother.left) == COLOR.BLACK && getColor(brother.right) == COLOR.BLACK) {
                    brother.color = COLOR.RED;
                    brother.parent.color = COLOR.BLACK;
                    node = node.parent;
                }
                // если один из детей брата красный
                else {
                    // если ребенок левого брата красный:
                    // сделать правого брата красным, левого брата чёрным
                    // и затем rightRotate для брата
                    if (getColor(brother.left) == COLOR.RED) {
                        brother.right.color = COLOR.RED;
                        brother.left.color = COLOR.BLACK;
                        rotateRight(brother);
                        brother = node.parent.right;
                    }
                    // если правый ребенок правого брата красный или стал красным:
                    // сделать правого брата чёрным, затем сделать брата цвета его родительского цвета,
                    // родительский цвет делает чёрный, а затем вращает справа для родителя
                    brother.right.color = COLOR.BLACK;
                    brother.color = brother.parent.color;
                    brother.parent.color = COLOR.BLACK;
                    rotateLeft(brother.parent);
                    node = _root;
                }
            }
            // // если это правый потомок
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
                    node = _root;
                }
            }
        }
    }
    private void headMap(TreeNode node, Integer key, MyRbMap map) { //собирает в отдельный map все узлы с ключами меньшими чем у ноды
        if (node == null) {
            return;
        }
        if (node.key.compareTo(key) < 0) {
            map.put(node.key, node.value);
            headMap(node.right, key, map);
        }
        headMap(node.left, key, map);
    }
    private void tailMap(TreeNode node, Integer key, MyRbMap map) { //собирает в отдельный map все узлы с ключами большими или равными чем у ноды
        if (node == null) {
            return;
        }
        if (node.key.compareTo(key) >= 0) {
            map.put(node.key, node.value);
            tailMap(node.left, key, map);
        }
        tailMap(node.right, key, map);
    }
    @Override
    public String put(Integer key, String value) {
        if (_root == null) {
            _root = new TreeNode(null, null, null, key, value, COLOR.BLACK);
            _size++;
            return null;
        }
        TreeNode curNode = _root;
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
        TreeNode removedNode = findByKey(_root, o);
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
        TreeNode foundRoot = get(_root, (Integer)o);
        return foundRoot != null ? foundRoot.value : null;
    }
    @Override
    public boolean containsKey(Object key) {
        return findByKey(_root, key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        return findByValue(_root, value) != null;
    }

    @Override
    public SortedMap<Integer, String> headMap(Integer key) {
        MyRbMap map = new MyRbMap();
        headMap(_root, key,map);
        return map;
    }

    @Override
    public SortedMap<Integer, String> tailMap(Integer key) {
        MyRbMap map = new MyRbMap();
        tailMap(_root, key, map);
        return map;
    }

    @Override
    public Integer firstKey() {
        TreeNode node = getMin(_root);
        if (node == null) {
            return null;
        }
        return node.key;
    }

    @Override
    public Integer lastKey() {
        TreeNode node = getMax(_root);
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
    public SortedMap<Integer, String> subMap(Integer fromKey, Integer toKey) {
        return null;
    }

    @Override
    public int size() {
        return _size;
    }

    @Override
    public boolean isEmpty() {
        return _root == null;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {

    }

    @Override
    public void clear() {
        clear(_root);
        _root = null;
        _size = 0;
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
