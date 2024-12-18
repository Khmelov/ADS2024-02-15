package by.it.group310901.maydanyuk.lesson12;

import java.util.*;
// Класс MyRbMap реализует интерфейс SortedMap, используя красно-черное дерево для хранения данных
public class MyRbMap implements SortedMap<Integer, String> {

    private enum color{
        BLACK, RED;
    }
    private class Node{
        Integer key;
        String value;
        color color;
        Node left;
        Node right;
        Node parent;
        boolean deleted;// Флаг, указывающий, что узел удален
        // Конструктор узла
        Node(Integer k, String s, Node p){
            key = k;
            value = s;
            left = null;
            right = null;
            parent = p;
            color = color.RED;
            deleted = false;
        }
    }

    private Node root;
    private int size;
    // Метод для правого вращения узла
    private Node rotateRight(Node node)
    {
        Node left = node.left;
        left.parent = node.parent;
        Node swapNode = node.left.right;
        left.right = node;
        left.right.parent = left;// Обновляем родителя старого корня
        node.left = swapNode; // Левое поддерево старого корня
        node.left.parent = node;// Обновляем родителя поддерева

        return left;
    }
    // Метод для левого вращения узла
    private Node rotateLeft(Node node){
        Node right = node.right;
        right.parent = node.parent;
        Node swapNode = node.right.left;
        right.left = node;
        right.left.parent = right;
        node.right = swapNode;
        node.right.parent = node;
// Возвращаем новый корень
        return right;
    }
    // Метод для коррекции дерева после вставки
    private void correction(Node node){
        if(node.parent == null)
        {
            node.color = color.BLACK;// Корневой узел всегда черный
        }// Если родитель черный, ничего не нужно делать
        else if((node.parent.color == color.BLACK) )
        {// Случай 1: дядя красный
        } else if ((node.parent.parent.left.color == color.RED) && (node.parent.parent.right.color == color.RED)) {
            node.parent.parent.left.color = color.BLACK;
            node.parent.parent.right.color = color.BLACK;
            node.parent.parent.color = color.RED;
            correction(node.parent.parent);// Рекурсивная коррекция
        }
        else
        {// Случай 2 и 3: дядя черный
            if (node.parent.left == node)
            {// Случай 2: поворот вправо
                if (node.parent.parent.left == node.parent)
                {
                    node=node.parent;
                    rotateRight(node);
                    node.color = color.BLACK;
                    node.right.color = color.RED;
                }
                else{// Случай 3: повороты влево и вправо
                    rotateRight(node);
                    rotateLeft(node);
                    node.color = color.BLACK;
                    node.right.color = color.RED;
                }
            }
            else {
                if (node.parent.parent.right == node.parent)
                {// Случай 2: поворот влево
                    node = node.parent;
                    rotateLeft(node);
                    node.color = color.BLACK;
                    node.left.color = color.RED;
                }
                else {// Случай 3: повороты вправо и влево
                    rotateLeft(node);
                    rotateRight(node);
                    node.color = color.BLACK;
                    node.right.color = color.RED;
                }
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
    // Создаем новую карту для результата
    @Override
    public SortedMap<Integer, String> headMap(Integer toKey) {
        MyRbMap bufMap= new MyRbMap();
        recHeadMap(bufMap, root, toKey);
        return bufMap;
    }
    // Вспомогательный метод для headMap
    private void recHeadMap(MyRbMap res, Node node, Integer key){
        if (node == null)
        {
            return;
        }
        if(node.key >= key)
        {
            recHeadMap(res, node.left, key);
            return;
        }
        recHeadMap(res, node.left, key);
        recHeadMap(res, node.right, key);
        if(node.deleted == false)
            res.put(node.key, node.value);// Добавляем элемент в результат
    }
    // Создаем новую карту для результата
    @Override
    public SortedMap<Integer, String> tailMap(Integer fromKey) {
        MyRbMap bufMap= new MyRbMap();
        recTailMap(bufMap, root, fromKey);
        return bufMap;
    }

    private void recTailMap(MyRbMap res, Node node, Integer key){
        if (node == null)
        {
            return;
        }
        if(node.key < key)
        {
            recTailMap(res, node.right, key);
            return;
        }
        recTailMap(res, node.left, key);
        recTailMap(res, node.right, key);
        if(node.deleted == false)
            res.put(node.key, node.value);
    }

    @Override
    public Integer firstKey() {
        Node temp = root;
        if (temp == null){
            return 0;
        }
        while (temp.left != null){
            temp = temp.left;
        }
        return temp.key;// Возвращаем ключ самого левого узла
    }

    @Override
    public Integer lastKey() {
        Node temp = root;
        if (temp == null){
            return 0;
        }
        while (temp.right != null){
            temp = temp.right;
        }
        return temp.key;// Возвращаем ключ самого правого узла
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public boolean containsKey(Object key) {
        if (get((Integer) key) != null){
            return true;
        }
        return false;
    }
    // Проверяем наличие значения
    @Override
    public boolean containsValue(Object value) {
        return checkValues(root, value);
    }
    // Вспомогательный метод для containsValue
    private boolean checkValues(Node node, Object value){
        if (node == null){
            return false;
        }
        if ((node.value.equals(value)) && (node.deleted == false)){
            return true;
        }
        if ((checkValues(node.left, value) == true) || (checkValues(node.right, value) == true)){
            return true;
        }
        return false;
    }
//метод реализует поиск значения по ключу
    @Override
    public String get(Object key) {
        Node temp = root;
        while (temp != null){
            // Если ключ текущего узла равен искомому ключу и узел не помечен как удаленный
            if ((temp.key.equals(key)) && (temp.deleted == false)){
                return temp.value;
                // Если искомый ключ больше ключа текущего узла
            } else if ((Integer)key > temp.key){
                temp = temp.right;// Переходим к правому поддереву
            } else {
                temp = temp.left;
            }
        }
        return null;
    }
    // Метод для добавления нового узла
    @Override
    public String put(Integer key, String value) {
        String oldVal = get(key);// Получаем старое значение по ключу
        root = add(root, key, value, null);// Добавляем новый узел в дерево
        if (root.color == color.RED){
            root.color = color.BLACK;
        }// Увеличиваем размер карты, если ключ был новым
        size += oldVal == null ? 1 : 0;
        return oldVal;
    }
    // Вспомогательный метод для преобразования узла в строку
    private String elemToString(Node node){
        if (node == null){
            return "";
        }
        if (node.deleted){
            return elemToString(node.left) + elemToString(node.right);
        }
        return elemToString(node.left) + node.key + "=" + node.value + ", " + elemToString(node.right);
    }
    // Преобразуем дерево в строку
    @Override
    public String toString(){

        String elems = elemToString(root);
        int l = elems.length();
        String res = "";
        if (elems.length() != 0){
            res = elems.substring(0, l - 2);
        }
        return "{" + res + "}";
    }
    // Вспомогательный метод для добавления нового узла
    private Node add(Node node, Integer key, String value, Node parent){
        if (node == null){
            return new Node(key, value, parent);
        }
        if (key > node.key){
            node.right = add(node.right, key, value, parent);
        } else if (key < node.key){
            node.left = add(node.left, key, value, parent);
        } else {
            node.value = value;
            return node;
        }
        correction(node);

        return node;
    }

    @Override
    public String remove(Object key) {
        String RemovedValue = get((Integer) key);// Получаем значение удаляемого узла
        Node temp = root;
        if (RemovedValue != null){
            while (temp != null){
                if ((temp.key.equals(key)) && (temp.deleted == false)){
                    temp.deleted = true;// Помечаем узел как удаленный
                    temp = null;
                } else if ((Integer)key > temp.key){
                    temp = temp.right;// Идем вправо, если ключ больше
                } else if ((Integer)key < temp.key){
                    temp = temp.left;
                }
            }
            size--;
        }
        return RemovedValue;
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