package by.it.group310902.shirukovaa.lesson12;

import java.util.*;
import java.util.List;

public class MyRbMap<Integer extends Comparable<Integer>, String> implements SortedMap<Integer, String> {
    enum Color {
        RED, // Красный цвет узла
        BLACK // Черный цвет узла
    }

    private class Node {
        Color color; // Цвет узла
        Integer key; // Ключ узла
        String value; // Значение узла
        Node left; // Левый дочерний узел
        Node right; // Правый дочерний узел
        Node parent; // Родительский узел

        Node(Integer key, String value, Node parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
            left = null;
            right = null;
            color = Color.RED; // Новые узлы по умолчанию красные
        }
    }

    private Node treeRoot; // Корень дерева
    private int size; // Размер дерева

    MyRbMap() {
        this.treeRoot = null; // Инициализация корня
        this.size = 0; // Инициализация размера
    }

    void rightRotate(Node parent) {
        // Поворот вправо
        Node nLeft = parent.left;
        Node bufferParent = parent;
        Node bufferChild = nLeft.right;
        parent = nLeft;
        parent.right = bufferParent;
        parent.right.left = bufferChild;
    }

    void leftRotate(Node parent) {
        // Поворот влево
        Node nRight = parent.right;
        Node bufferParent = parent;
        Node bufferChild = nRight.left;
        parent = nRight;
        parent.left = bufferParent;
        parent.left.right = bufferChild;
    }

    void rebalance(Node node) {
        // Ребалансировка дерева
        while (node != treeRoot && node.parent != null && node.parent.color == Color.RED) {
            if (node.parent.parent != null && node.parent == node.parent.parent.left) {
                Node uncle = node.parent.parent.right;
                if (uncle != null && uncle.color == Color.RED) {
                    // Случай 1: Дядя красный
                    node.parent.color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    node.parent.parent.color = Color.RED;
                    node = node.parent.parent;
                } else if (uncle != null) {
                    // Случай 2: Дядя черный
                    if (node == node.parent.right) {
                        node = node.parent;
                        leftRotate(node);
                    }
                    node.parent.color = Color.BLACK;
                    node.parent.parent.color = Color.RED;
                    rightRotate(node.parent.parent);
                }
            } else if (node.parent.parent != null) {
                Node uncle = node.parent.parent.left;
                if (uncle != null && uncle.color == Color.RED) {
                    // Случай 1: Дядя красный
                    node.parent.color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    node.parent.parent.color = Color.RED;
                    node = node.parent.parent;
                } else if (uncle != null) {
                    // Случай 2: Дядя черный
                    if (node == node.parent.left) {
                        node = node.parent;
                        rightRotate(node);
                    }
                    node.parent.color = Color.BLACK;
                    node.parent.parent.color = Color.RED;
                    leftRotate(node.parent.parent);
                }
            }
        }
        treeRoot.color = Color.BLACK; // Корень всегда черный
    }

    Node insert(Node node, Node parent, Integer key, String value) {
        // Вставка нового узла
        if (node == null) {
            return new Node(key, value, parent); // Создание нового узла
        }

        if (key.compareTo(node.key) > 0)
            node.right = insert(node.right, node, key, value); // Вставка в правое поддерево
        else if (key.compareTo(node.key) < 0)
            node.left = insert(node.left, node, key, value); // Вставка в левое поддерево
        else {
            node.value = value; // Обновление значения по ключу
            return node;
        }

        return node;
    }

    Node getMin(Node node) {
        // Получение узла с минимальным ключом
        if (node == null)
            return null;
        if (node.left == null)
            return node;
        return getMin(node.left);
    }

    Node getMax(Node node) {
        // Получение узла с максимальным ключом
        if (node == null)
            return null;
        if (node.right == null)
            return node;
        return getMax(node.right);
    }

    void getToKeyNodes(MyRbMap tree, Node node, Integer keyTo) {
        // Получение узлов до указанного ключа
        if (node == null)
            return;

        if (node.key.compareTo(keyTo) >= 0) {
            getToKeyNodes(tree, node.left, keyTo);
            return;
        }

        getToKeyNodes(tree, node.left, keyTo);
        getToKeyNodes(tree, node.right, keyTo);
        tree.put(node.key, node.value); // Вставка узла в новый поддерево
    }

    void getFromKeyNodes(MyRbMap tree, Node node, Integer keyFrom) {
        // Получение узлов от указанного ключа
        if (node == null)
            return;
        if (node.key.compareTo(keyFrom) < 0) {
            getFromKeyNodes(tree, node.right, keyFrom);
            return;
        }

        getFromKeyNodes(tree, node.left, keyFrom);
        getFromKeyNodes(tree, node.right, keyFrom);
        tree.put(node.key, node.value); // Вставка узла в новый поддерево
    }

    boolean findValue(Node node, String value) {
        // Поиск значения в дереве
        if (node != null && (value.equals(node.value) || findValue(node.left, value) || findValue(node.right, value)))
            return true;
        return false;
    }

    void transplantNode(Node parentNodeTo, Node nodeTo, Node nodeFrom) {
        // Перемещение узла
        if (nodeTo == treeRoot)
            treeRoot = nodeFrom; // Обновление корня
        else if (nodeTo != null && nodeTo == parentNodeTo.left)
            parentNodeTo.left = nodeFrom;
        else if (nodeTo != null) {
            parentNodeTo.right = nodeFrom;
        }
    }

    void deleteFix(Node node) {
        // Исправление дерева после удаления узла
        while (node != treeRoot && node.color == Color.BLACK) {
            Node brother;
            if (node == node.parent.left) {
                brother = node.parent.right; // Брат узла
                if (brother.color == Color.RED) {
                    // Случай 1: Брат красный
                    brother.color = Color.BLACK;
                    node.parent.color = Color.RED;
                    leftRotate(node.parent);
                    brother = node.parent.right;
                }

                if (brother.left.color == Color.BLACK && brother.right.color == Color.BLACK) {
                    // Случай 2: Оба ребенка брата черные
                    brother.color = Color.RED;
                    node = node.parent;
                } else {
                    if (brother.right.color == Color.BLACK) {
                        // Случай 3: Правый ребенок брата черный
                        brother.left.color = Color.BLACK;
                        brother.color = Color.RED;
                        rightRotate(brother);
                        brother = node.parent.right;
                    }
                    brother.color = node.parent.color;
                    node.parent.color = Color.BLACK;
                    brother.right.color = Color.BLACK;
                    leftRotate(node.parent);
                    node = treeRoot;
                }
            } else {
                brother = node.parent.left; // Брат узла
                if (brother.color == Color.RED) {
                    // Случай 1: Брат красный
                    brother.color = Color.BLACK;
                    node.parent.color = Color.RED;
                    rightRotate(node.parent);
                    brother = node.parent.left;
                }

                if (brother.left.color == Color.BLACK && brother.right.color == Color.BLACK) {
                    // Случай 2: Оба ребенка брата черные
                    brother.color = Color.RED;
                    node = node.parent;
                } else {
                    if (brother.left.color == Color.BLACK) {
                        // Случай 3: Левый ребенок брата черный
                        brother.right.color = Color.BLACK;
                        brother.color = Color.RED;
                        leftRotate(brother);
                        brother = node.parent.left;
                    }
                    brother.color = node.parent.color;
                    node.parent.color = Color.BLACK;
                    brother.left.color = Color.BLACK;
                    rightRotate(node.parent);
                    node = treeRoot;
                }
            }
        }
        node.color = Color.BLACK; // Убедитесь, что узел черный
    }

    void delete(Integer key) {
        // Удаление узла по ключу
        Node delNode = null, parent = null, tempNode = treeRoot;
        while (tempNode != null) {
            if (tempNode.key.compareTo(key) == 0) {
                delNode = tempNode; // Найден узел для удаления
                break;
            }
            parent = tempNode; // Запоминаем родителя
            tempNode = tempNode.key.compareTo(key) < 0 ? tempNode.right : tempNode.left; // Поиск узла
        }

        if (delNode != null) {
            Node child = null;
            Color bufferColor = delNode.color; // Сохранение цвета узла
            if (delNode.left == null ^ delNode.right == null) {
                // Удаление узла с одним ребенком
                child = delNode.left == null ? delNode.right : delNode.left;
                transplantNode(parent, delNode, child);
            } else {
                // Удаление узла с двумя детьми
                Node minNode = getMin(delNode.right);
                if (minNode != null) {
                    delNode.key = minNode.key; // Замена ключа
                    delNode.value = minNode.value; // Замена значения
                    bufferColor = minNode.color; // Сохранение цвета
                    child = minNode.left == null ? minNode.right : minNode.left;
                    if (child == null) {
                        child = delNode.right;
                        Node tempMin = delNode.right, tempPar = null;
                        while (tempMin.left != null) {
                            tempPar = tempMin;
                            tempMin = tempMin.left; // Поиск минимального узла
                        }
                        tempPar.left = null; // Удаление минимального узла
                    }
                    transplantNode(delNode, minNode, child); // Перемещение узла
                } else {
                    // Удаление узла, если он корень
                    if (parent.key.compareTo(key) > 0)
                        parent.left = null;
                    else parent.right = null;
                }
            }

            if (bufferColor == Color.BLACK)
                deleteFix(child); // Исправление дерева
        }
    }

    Node findNode(Integer key) {
        // Поиск узла по ключу
        Node needNode = treeRoot;
        while (!needNode.key.equals(key)) {
            needNode = needNode.key.compareTo(key) > 0 ? needNode.left : needNode.right; // Поиск узла
        }

        return needNode; // Возвращение найденного узла
    }

    java.lang.String getLeftRight(Node node) {
        // Получение строкового представления узлов
        return node == null ? "" : getLeftRight(node.left) + node.key + "=" + node.value + ", " + getLeftRight(node.right);
    }

    public java.lang.String toString() {
        // Получение строкового представления дерева
        StringBuilder strbldr = new StringBuilder();
        strbldr.append("{");
        strbldr.append(getLeftRight(treeRoot));

        int sbLength = strbldr.length();
        if (sbLength > 1)
            strbldr.delete(sbLength - 2, sbLength); // Удаление последней запятой

        strbldr.append("}");
        return strbldr.toString(); // Возврат строкового представления
    }

    @Override
    public Integer firstKey() {
        // Возврат минимального ключа
        return getMin(treeRoot).key;
    }

    @Override
    public Integer lastKey() {
        // Возврат максимального ключа
        return getMax(treeRoot).key;
    }

    @Override
    public int size() {
        // Возврат размера дерева
        return size;
    }

    @Override
    public boolean isEmpty() {
        // Проверка на пустоту
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        // Проверка наличия ключа в дереве
        Node tempNode = treeRoot;
        while (tempNode != null) {
            if (tempNode.key.equals(key))
                return true;
            tempNode = tempNode.key.compareTo((Integer)key) < 0 ? tempNode.right : tempNode.left; // Поиск узла
        }

        return false; // Ключ не найден
    }

    @Override
    public boolean containsValue(Object value) {
        // Проверка наличия значения в дереве
        return findValue(treeRoot, (String)value);
    }

    @Override
    public String get(Object key) {
        // Получение значения по ключу
        Node tempNode = treeRoot;
        while (tempNode != null) {
            if (tempNode.key.equals(key))
                return tempNode.value; // Возврат значения
            tempNode = tempNode.key.compareTo((Integer)key) < 0 ? tempNode.right : tempNode.left; // Поиск узла
        }

        return null; // Ключ не найден
    }

    @Override
    public String put(Integer key, String value) {
        // Вставка нового ключа и значения
        String tempValue = get(key);
        size += tempValue == null ? 1 : 0; // Увеличение размера при добавлении
        treeRoot = insert(treeRoot, null, key, value); // Вставка узла

        return tempValue; // Возврат старого значения
    }

    @Override
    public String remove(Object key) {
        // Удаление узла по ключу
        String tempValue = get(key);

        if (tempValue != null) {
            size--; // Уменьшение размера
            delete((Integer)key); // Удаление узла
        }

        return tempValue; // Возврат удаленного значения
    }

    @Override
    public void clear() {
        // Очистка дерева
        treeRoot = null;
        size = 0; // Обнуление размера
    }

    @Override
    public SortedMap<Integer, String> headMap(Integer toKey) {
        // Получение поддерева с ключами меньше указанного
        MyRbMap subTree = new MyRbMap();
        getToKeyNodes(subTree, treeRoot, toKey);
        return subTree;
    }

    @Override
    public SortedMap<Integer, String> tailMap(Integer fromKey) {
        // Получение поддерева с ключами больше указанного
        MyRbMap subTree = new MyRbMap();
        getFromKeyNodes(subTree, treeRoot, fromKey);
        return subTree;
    }

    /////////////////////////////

    @Override
    public Set<Integer> keySet() {
        // Получение набора ключей (не реализовано)
        return Set.of();
    }

    @Override
    public Collection<String> values() {
        // Получение коллекции значений (не реализовано)
        return List.of();
    }

    @Override
    public Set<Entry<Integer, String>> entrySet() {
        // Получение набора пар (не реализовано)
        return Set.of();
    }

    @Override
    public Comparator<? super Integer> comparator() {
        // Возврат компаратора (не реализовано)
        return null;
    }

    @Override
    public SortedMap<Integer, String> subMap(Integer fromKey, Integer toKey) {
        // Получение поддерева между двумя ключами (не реализовано)
        return null;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {
        // Вставка всех пар из другой карты (не реализовано)
    }
}