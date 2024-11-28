package by.it.group310902.shirukovaa.lesson12;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MyAvlMap<Integer extends Comparable<Integer>, String> implements Map<Integer, String> {

    private class Node {
        int height; // Высота узла
        Integer key; // Ключ узла
        String value; // Значение узла
        Node left; // Левая ветвь
        Node right; // Правая ветвь

        Node(Integer key, String value) {
            this.key = key;
            this.value = value;
            left = null;
            right = null;
            height = 0;
        }
    }

    void updateHeight(Node node) {
        if (node != null)
            node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1; // Обновление высоты
    }

    int getHeight(Node node) {
        return node == null ? -1 : node.height; // Получение высоты узла
    }

    int getBalance(Node node) {
        return node == null ? 0 : getHeight(node.left) - getHeight(node.right); // Получение баланса узла
    }

    void rebalance(Node node) {
        // Ребалансировка дерева в зависимости от баланса узла
        if (getBalance(node) == 2) {
            if (getBalance(node.left) == -1)
                leftRotate(node.left);
            rightRotate(node);
        } else if (getBalance(node) == -2) {
            if (getBalance(node.right) == 1)
                rightRotate(node.right);
            leftRotate(node);
        }
    }

    void rightRotate(Node parent) {
        Node nLeft = parent.left;
        Node bufferParent = parent; // Сохранение старого родителя для обновления
        Node bufferChild = nLeft.right;
        parent = nLeft; // Новый родитель
        parent.right = bufferParent; // Установка старого родителя как правого дочернего
        parent.right.left = bufferChild; // Установка правого дочернего узла
        updateHeight(parent); // Обновление высоты
        updateHeight(parent.right); // Обновление высоты
    }

    void leftRotate(Node parent) {
        Node nRight = parent.right;
        Node bufferParent = parent; // Сохранение старого родителя
        Node bufferChild = nRight.left;
        parent = nRight; // Новый родитель
        parent.left = bufferParent; // Установка старого родителя как левого дочернего
        parent.left.right = bufferChild; // Установка левого дочернего узла
        updateHeight(parent); // Обновление высоты
        updateHeight(parent.left); // Обновление высоты
    }

    Node getMax(Node node) {
        if (node == null) return null;
        if (node.right == null) return node; // Находим максимальный узел
        return getMax(node.right);
    }

    java.lang.String getLeftRight(Node node) {
        return node == null ? "" : getLeftRight(node.left) + node.key + "=" + node.value + ", " + getLeftRight(node.right);
    }

    Node insert(Node node, Integer key, String value) {
        if (node == null) {
            return new Node(key, value); // Создаем новый узел
        } else if (node.key.compareTo(key) > 0) {
            node.left = insert(node.left, key, value); // Вставка в левое поддерево
        } else if (node.key.compareTo(key) < 0) {
            node.right = insert(node.right, key, value); // Вставка в правое поддерево
        } else {
            node.value = value; // Обновление значения, если ключ уже существует
        }
        updateHeight(node); // Обновление высоты узла
        return node;
    }

    Node delete(Node node, Integer key) {
        if (node == null) return null; // Узел не найден
        else if (key.compareTo(node.key) < 0) {
            node.left = delete(node.left, key); // Удаление из левого поддерева
        } else if (key.compareTo(node.key) > 0) {
            node.right = delete(node.right, key); // Удаление из правого поддерева
        } else {
            // Удаление узла
            if (node.left == null || node.right == null) // Удаляемый узел имеет одного или ноль детей
                node = (node.left == null) ? node.right : node.left;
            else {
                Node max = getMax(node.left); // Находим максимальный узел в левом поддереве
                node.key = max.key; // Замена ключа
                node.value = max.value; // Замена значения
                node.right = delete(node.right, max.key); // Удаление максимального узла
            }
        }
        updateHeight(node); // Обновление высоты
        rebalance(node); // Ребалансировка дерева
        return node;
    }

    private int size; // Размер карты
    private Node treeRoot; // Корень дерева

    MyAvlMap() {
        this.size = 0; // Инициализация размера
        this.treeRoot = null; // Инициализация корня
    }

    public java.lang.String toString() {
        StringBuilder strbldr = new StringBuilder();
        strbldr.append("{");
        strbldr.append(getLeftRight(treeRoot)); // Получение строкового представления дерева

        int sbLength = strbldr.length();
        if (sbLength > 1)
            strbldr.delete(sbLength - 2, sbLength); // Удаление последней запятой

        strbldr.append("}");
        return strbldr.toString(); // Возврат строкового представления карты
    }

    @Override
    public int size() {
        return size; // Возвращение размера карты
    }

    @Override
    public boolean isEmpty() {
        return size == 0; // Проверка на пустоту
    }

    @Override
    public boolean containsKey(Object key) {
        Node tempNode = treeRoot; // Временный узел для обхода дерева
        while (tempNode != null) {
            if (tempNode.key.equals(key)) return true; // Ключ найден
            tempNode = tempNode.key.compareTo((Integer)key) < 0 ? tempNode.right : tempNode.left; // Переход к дочернему узлу
        }
        return false; // Ключ не найден
    }

    @Override
    public String get(Object key) {
        Node tempNode = treeRoot; // Временный узел для обхода дерева
        while (tempNode != null) {
            if (tempNode.key.equals(key)) return tempNode.value; // Возвращение значения по ключу
            tempNode = tempNode.key.compareTo((Integer)key) < 0 ? tempNode.right : tempNode.left; // Переход к дочернему узлу
        }
        return null; // Ключ не найден
    }

    @Override
    public String put(Integer key, String value) {
        if (treeRoot == null) {
            treeRoot = new Node(key, value); // Добавление корня, если дерево пустое
            size++; // Увеличение размера
            return null; // Возврат null, так как это новый элемент
        }

        String tempValue = get(key); // Получение старого значения
        size += tempValue == null ? 1 : 0; // Увеличение размера, если ключ новый
        insert(treeRoot, key, value); // Вставка узла

        return tempValue; // Возврат старого значения
    }

    @Override
    public String remove(Object key) {
        String tempValue = get(key); // Получение значения по ключу
        if (tempValue != null) {
            size--; // Уменьшение размера
            delete(treeRoot, (Integer)key); // Удаление узла
        }
        return tempValue; // Возврат удаленного значения
    }

    @Override
    public void clear() {
        this.size = 0; // Обнуление размера
        this.treeRoot = null; // Очистка дерева
    }

    @Override
    public boolean containsValue(Object value) {
        return false; // Не реализовано
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {
        // Не реализовано
    }

    @Override
    public Set<Integer> keySet() {
        return Set.of(); // Не реализовано
    }

    @Override
    public Collection<String> values() {
        return List.of(); // Не реализовано
    }

    @Override
    public Set<Entry<Integer, String>> entrySet() {
        return Set.of(); // Не реализовано
    }
}