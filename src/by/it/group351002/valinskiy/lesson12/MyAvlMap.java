package by.it.group351002.valinskiy.lesson12;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Реализация карты (Map) на основе AVL-дерева.
 * AVL-дерево — это самобалансирующееся двоичное дерево поиска,
 * которое поддерживает операции вставки, удаления и поиска,
 * обеспечивая при этом сбалансированность.
 *
 * В данной реализации используются целочисленные ключи и строковые значения.
 * Каждый узел дерева содержит ключ, значение, ссылки на левое и правое поддеревья,
 * а также информацию о высоте узла.
 *
 * Основные операции:
 * 1. Вставка элемента (put)
 * 2. Удаление элемента (remove)
 * 3. Получение значения по ключу (get)
 * 4. Проверка наличия ключа (containsKey)
 * 5. Определение размера карты (size)
 */

public class MyAvlMap implements Map<Integer, String> {

    // Внутренний класс для представления узла дерева
    private class Node {
        Integer key; // Ключ узла
        String value; // Значение узла
        Node left; // Ссылка на левое поддерево
        Node right; // Ссылка на правое поддерево
        int height; // Высота узла

        // Конструктор узла
        Node(Integer k, String v) {
            key = k;
            value = v;
            left = null;
            right = null;
            height = 0; // Изначально высота узла равна 0
        }
    }

    // Метод для получения высоты узла
    private int getHeight(Node node) {
        if (node == null) {
            return 0; // Высота пустого узла равна 0
        }
        return 1 + Math.max(getHeight(node.left), getHeight(node.right)); // Высота узла = 1 + максимальная высота его дочерних узлов
    }

    // Метод для вычисления разницы высоты между левым и правым поддеревьями узла
    private int solveDif(Node node) {
        return getHeight(node.left) - getHeight(node.right); // Возвращает разницу
    }

    // Метод для безопасного получения высоты узла
    private int takeHeight(Node node) {
        return (node == null) ? 0 : node.height; // Возвращает 0, если узел пуст, иначе - высоту узла
    }

    // Правый поворот для балансировки узла
    private Node rotateRight(Node node) {
        Node left = node.left; // Сохраняем левое поддерево
        Node swapNode = node.left.right; // Сохраняем правое поддерево левого узла
        left.right = node; // Выполняем поворот
        node.left = swapNode; // Переносим правое поддерево левого узла
        // Обновляем высоты узлов
        node.height = Math.max(takeHeight(node.left), takeHeight(node.right)) + 1;
        left.height = Math.max(takeHeight(left.left), takeHeight(left.right)) + 1;
        return left; // Возвращаем новый корень
    }

    // Левый поворот для балансировки узла
    private Node rotateLeft(Node node) {
        Node right = node.right; // Сохраняем правое поддерево
        Node NodeSwap = node.right.left; // Сохраняем левое поддерево правого узла
        right.left = node; // Выполняем поворот
        node.right = NodeSwap; // Переносим левое поддерево правого узла
        // Обновляем высоты узлов
        node.height = Math.max(takeHeight(node.left), takeHeight(node.right)) + 1;
        right.height = Math.max(takeHeight(right.left), takeHeight(right.right)) + 1;
        return right; // Возвращаем новый корень
    }

    // Правый-левый поворот для балансировки узла
    private Node rightNLeft(Node node) {
        node.right = rotateRight(node.right); // Сначала выполняем правый поворот
        return rotateLeft(node); // Затем левый поворот
    }

    // Левый-правый поворот для балансировки узла
    private Node leftRight(Node node) {
        node.left = rotateLeft(node.left); // Сначала выполняем левый поворот
        return rotateRight(node); // Затем правый поворот
    }
    // Метод добавления нового элемента в дерево
    private Node add(Node node, Integer key, String value) {
        if (node == null) {
            return new Node(key, value); // Если узел пуст, создаем новый
        }
        if (key > node.key) {
            node.right = add(node.right, key, value); // Вставляем в правое поддерево
        } else if (key < node.key) {
            node.left = add(node.left, key, value); // Вставляем в левое поддерево
        } else {
            node.value = value; // Если ключ уже существует, обновляем значение
            return node; // Возвращаем текущий узел
        }

        // Проверка и исправление баланса дерева
        int diff = solveDif(node);
        if (diff > 1) {
            if (key < node.left.key) {
                return rotateRight(node); // Правый поворот
            } else {
                return leftRight(node); // Левый-правый поворот
            }
        }
        if (diff < -1) {
            if (key < node.right.key) {
                return rightNLeft(node); // Правый-левый поворот
            } else {
                return rotateLeft(node); // Левый поворот
            }
        }
        return node; // Возвращаем сбалансированный узел
    }

    // Метод удаления узла по ключу
    private Node rmNode(Node node, Integer key) {
        if (node == null) {
            return null; // Если узел пуст, ничего не делаем
        }
        if (key < node.key) {
            node.left = rmNode(node.left, key); // Ищем и удаляем в левом поддереве
        } else if (key > node.key) {
            node.right = rmNode(node.right, key); // Ищем и удаляем в правом поддереве
        } else {
            // Если узел найден
            if (node.right == null && node.left == null) {
                return null; // Удаляем узел без потомков
            }
            if (node.left == null) {
                return node.right; // Удаляем узел с правым потомком
            } else if (node.right == null) {
                return node.left; // Удаляем узел с левым потомком
            } else {
                // Удаляем узел с двумя потомками
                Node temp = node.left;
                while (temp.right != null) {
                    temp = temp.right; // Находим наибольший узел в левом поддереве
                }
                Integer k = temp.key; // Сохраняем ключ
                String s = temp.value; // Сохраняем значение
                remove(temp.key); // Удаляем временный узел
                node.key = k; // Переносим ключ
                node.value = s; // Переносим значение
            }
        }

        // Проверка и исправление баланса дерева после удаления
        int diff = solveDif(node);
        if (diff > 1) {
            if (key < node.left.key) {
                return rotateRight(node); // Правый поворот
            } else {
                return leftRight(node); // Левый-правый поворот
            }
        }
        if (diff < -1) {
            if (key < node.right.key) {
                return rotateLeft(node); // Левый поворот
            } else {
                return rightNLeft(node); // Правый-левый поворот
            }
        }
        return node; // Возвращаем сбалансированный узел
    }

    private Node root = null; // Корень дерева
    private int size = 0; // Размер карты

    // Метод для обхода дерева в строковом представлении
    private String elemToString(Node node) {
        if (node == null) {
            return ""; // Если узел пуст, возвращаем пустую строку
        }
        return elemToString(node.left) + node.key + "=" + node.value + ", " + elemToString(node.right); // Строковое представление узлов
    }
    // Переопределение метода toString для представления карты
    @Override
    public String toString() {
        String elems = elemToString(root); // Получаем строковое представление всех узлов
        int l = elems.length(); // Получаем длину строки
        String result = "";
        if (elems.length() != 0) {
            result = elems.substring(0, l - 2); // Удаляем последнюю запятую
        }
        return "{" + result + "}"; // Возвращаем строку в виде карты
    }

    @Override
    public int size() {
        return size; // Возвращаем размер карты
    }

    @Override
    public boolean isEmpty() {
        return (size == 0); // Проверяем, пуста ли карта
    }

    @Override
    public boolean containsKey(Object key) {
        Node temp = root; // Начинаем с корня
        while (temp != null) {
            if (temp.key.equals(key)) {
                return true; // Если ключ найден, возвращаем true
            }
            if ((Integer) key > temp.key) {
                temp = temp.right; // Ищем в правом поддереве
            } else {
                temp = temp.left; // Ищем в левом поддереве
            }
        }
        return false; // Ключ не найден
    }

    @Override
    public boolean containsValue(Object value) {
        return false; // Данный метод не реализован
    }

    @Override
    public String get(Object key) {
        Node temp = root; // Начинаем с корня
        while (temp != null) {
            if (temp.key.equals(key)) {
                return temp.value; // Если ключ найден, возвращаем значение
            }
            if ((Integer) key > temp.key) {
                temp = temp.right; // Ищем в правом поддереве
            } else {
                temp = temp.left; // Ищем в левом поддереве
            }
        }
        return null; // Ключ не найден
    }

    @Override
    public String put(Integer key, String value) {
        String Old = get(key); // Сохраняем старое значение
        root = add(root, key, value); // Добавляем новый узел
        size += (Old == null) ? 1 : 0; // Увеличиваем размер, если элемент был добавлен
        return Old; // Возвращаем старое значение
    }

    @Override
    public String remove(Object key) {
        String removed = get((Integer) key); // Получаем значение по ключу
        if (removed != null) {
            root = rmNode(root, (Integer) key); // Удаляем узел
            size--; // Уменьшаем размер
        }
        return removed; // Возвращаем удаленное значение
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {
        // Данный метод не реализован
    }

    @Override
    public void clear() {
        root = null; // Очищаем дерево
        size = 0; // Сбрасываем размер
    }

    @Override
    public Set<Integer> keySet() {
        return null; // Данный метод не реализован
    }

    @Override
    public Collection<String> values() {
        return null; // Данный метод не реализован
    }

    @Override
    public Set<Entry<Integer, String>> entrySet() {
        return null; // Данный метод не реализован
    }
}