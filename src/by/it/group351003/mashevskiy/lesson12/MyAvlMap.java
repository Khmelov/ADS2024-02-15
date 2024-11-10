package by.it.group351003.mashevskiy.lesson12;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MyAvlMap implements Map<Integer,String> {

    private Node root = null;
    private int size = 0;

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
        int index =(int) key;
        Node node = getNode(root,index);
        return node != null;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    private Node getNode(Node node, int key){
        if(node != null){
            if(node.key < key){
                return getNode(node.right, key);
            }else if(node.key > key){
                return getNode(node.left, key);
            }else{
                return node;
            }
        }else{
            return null;
        }
    }

    @Override
    public String get(Object key) {
        Node val = getNode(root,(int)key);
        String result;
        if(val != null){
            result = val.value;
        }else{
            result = null;
        }
        return result;
    }

    // Подсчет высоты узла
    private int height(Node node) {
        return node == null ? 0 : node.height;
    }

    // Вычисление баланс-фактора
    private int getBalance(Node node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    // Левый поворот (RR)
    private Node rotateLeft(Node x) {
        Node y = x.right;
        x.right = y.left;
        y.left = x;
        // Обновляем высоты
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        return y;
    }

    // Правый поворот (LL)
    private Node rotateRight(Node y) {
        Node x = y.left;
        y.left = x.right;
        x.right = y;
        // Обновляем высоты
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        return x;
    }

    // Лево-правый поворот (LR)
    private Node rotateLeftRight(Node node) {
        node.left = rotateLeft(node.left);
        return rotateRight(node);
    }

    // Право-левый поворот (RL)
    private Node rotateRightLeft(Node node) {
        node.right = rotateRight(node.right);
        return rotateLeft(node);
    }

    // Рекурсивная вставка узла с балансировкой
    private Node insert(Node node, int key, String value) {
        if (node == null) {
            size++;
            return new Node(key, value);
        }

        if (key < node.key) {
            node.left = insert(node.left, key, value);
        } else if (key > node.key) {
            node.right = insert(node.right, key, value);
        } else {
            node.value = value;
            return node;
        }

        // Обновляем высоту текущего узла
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // Вычисляем баланс-фактор
        int balance = getBalance(node);

        // Проверка и выполнение необходимых поворотов
        if (balance > 1) {
            if (getBalance(node.left) >= 0) {
                return rotateRight(node); // LL
            } else {
                return rotateLeftRight(node); // LR
            }
        } else if (balance < -1) {
            if (getBalance(node.right) <= 0) {
                return rotateLeft(node); // RR
            } else {
                return rotateRightLeft(node); // RL
            }
        }

        return node;
    }

    @Override
    public String put(Integer key, String value) {
        Node prev = getNode(root,key);
        String result = prev == null ? null : prev.value;
        root = insert(root, key, value);
        return result;
    }

    private Node getMinNode(Node node) {
        Node temp = node;
        while(temp.left != null) {
            temp = temp.left;
        }
        return temp;
    }

    private Node deleteNode(Node node, int key){
        if(node == null){
            return null;
        }
        if(node.key > key){
            node.left = deleteNode(node.left, key);
        }else if(node.key < key){
            node.right = deleteNode(node.right, key);
        }else{
            if(node.left == null || node.right == null){
                node = node.left != null ? node.left : node.right;
                size--;
            }else{
                Node temp = getMinNode(node.right);
                node.key = temp.key;
                node.value = temp.value;
                node.right = deleteNode(node.right, temp.key);
            }
        }
        if(node == null){
            return null;
        }
        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = getBalance(node);
        if (balance > 1) {
            if (getBalance(node.left) >= 0) {
                return rotateRight(node);
            }else{
                return rotateLeftRight(node);
            }
        }else if (balance < -1) {
            if (getBalance(node.right) <= 0) {
                return rotateLeft(node);
            }else{
                return rotateRightLeft(node);
            }
        }
        return node;
    }

    @Override
    public String remove(Object key) {
        int index = (int) key;
        Node delNode = getNode(root,index);
        String result;
        if(delNode != null) {
            result = delNode.value;
        }else{
            result = null;
        }
        deleteNode(root, index);
        return result;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {

    }

    @Override
    public void clear() {
        size = 0;
        root = null;
    }

    public void leftTreeTravel(Node node, StringBuilder sb){
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

