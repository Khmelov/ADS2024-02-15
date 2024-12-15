package by.it.group351001.orsik.lesson12;

import java.util.*;

@SuppressWarnings("unchecked")
public class MyRbMap implements SortedMap<Integer, String> {

    private Node root; // Корень дерева
    private int size; // Размер (количество элементов) в дереве

    @Override
    public String toString() {
        // Переопределение метода toString для представления дерева в виде строки
        if (isEmpty()) {
            return "{}"; // Если дерево пустое, вернуть "{}"
        }

        StringBuilder sb = new StringBuilder("{");
        inorderTraversal(sb, root); // Выполнить симметричный обход дерева
        return sb.delete(sb.length() - 2, sb.length()).append("}").toString(); // Удалить лишнюю запятую и пробел
    }

    @Override
    public String put(Integer key, String value) {
        // Метод для вставки новой пары ключ-значение
        if (isEmpty()) {
            // Если дерево пустое, создаем новый корень
            root = new Node(null, key, value, Color.BLACK);
            size++; // Увеличиваем размер
            return null; // Возврат null, если вставка прошла успешно
        }

        Node parent = root.parent; // Родитель текущего узла
        Node curr = root; // Текущий узел, начиная с корня

        // Поиск места для вставки нового узла
        while (curr != null) {
            parent = curr; // Обновляем родительский узел
            if (key < curr.key) {
                curr = curr.left; // Переход в левое поддерево
            } else if (key > curr.key) {
                curr = curr.right; // Переход в правое поддерево
            } else {
                // Если ключ уже существует, обновляем его значение
                String prevVal = curr.value; // Сохраняем предыдущее значение
                curr.value = value; // Обновляем значение
                return prevVal; // Возврат предыдущего значения
            }
        }

        // Создание нового узла
        Node newNode = new Node(parent, key, value, Color.RED);

        // Вставка нового узла как левого или правого ребенка
        if (parent.key > key) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }

        // Исправление возможных нарушений свойств красно-черного дерева
        fixInsertion(newNode);
        size++; // Увеличиваем размер
        return null; // Возврат null, если вставка прошла успешно
    }

    private void fixInsertion(Node node) {
        // Метод для исправления нарушений свойств красно-черного дерева после вставки
        Node parent;
        Node grandparent;

        // Пока текущий узел не корень и родитель красный
        while (node != root && node.color == Color.RED && node.parent.color == Color.RED) {
            parent = node.parent; // Устанавливаем родителя
            grandparent = parent.parent; // Устанавливаем дедушку

            // Если родитель - левый ребенок дедушки
            if (parent == grandparent.left) {
                Node uncle = grandparent.right; // Дядя
                if (getColor(uncle) == Color.RED) {
                    // Случай 1: дядя красный
                    setColor(uncle, Color.BLACK); // Дядя становится черным
                    setColor(parent, Color.BLACK); // Родитель становится черным
                    setColor(grandparent, Color.RED); // Дедушка становится красным
                    node = grandparent; // Переход к дедушке
                } else {
                    // Случай 2: дядя черный
                    if (node == parent.right) {
                        // Если текущий узел - правый ребенок
                        rotateLeft(parent); // Выполняем левый поворот
                        node = parent; // Обновляем текущий узел
                        parent = node.parent; // Обновляем родителя
                    }
                    rotateRight(grandparent); // Выполняем правый поворот
                    swapColors(parent, grandparent); // Меняем цвета родителя и дедушки
                    node = parent; // Переход к родителю
                }
            } else { // Если родитель - правый ребенок дедушки
                Node uncle = grandparent.left; // Дядя
                if (getColor(uncle) == Color.RED) {
                    // Случай 1: дядя красный
                    setColor(uncle, Color.BLACK); // Дядя становится черным
                    setColor(parent, Color.BLACK); // Родитель становится черным
                    setColor(grandparent, Color.RED); // Дедушка становится красным
                    node = grandparent; // Переход к дедушке
                } else {
                    // Случай 2: дядя черный
                    if (node == parent.left) {
                        // Если текущий узел - левый ребенок
                        rotateRight(parent); // Выполняем правый поворот
                        node = parent; // Обновляем текущий узел
                        parent = node.parent; // Обновляем родителя
                    }
                    rotateLeft(grandparent); // Выполняем левый поворот
                    swapColors(parent, grandparent); // Меняем цвета родителя и дедушки
                    node = parent; // Переход к родителю
                }
            }
        }

        setColor(root, Color.BLACK); // Корень всегда черный
    }

    private void swapColors(Node node1, Node node2) {
        // Метод для обмена цветов двух узлов
        Color tmpColor = node1.color; // Временное хранение цвета первого узла
        node1.color = node2.color; // Обмен цветов
        node2.color = tmpColor; // Завершение обмена
    }

    // Левый поворот
    private void rotateLeft(Node node) {
        Node rChild = node.right; // Правый ребенок текущего узла
        node.right = rChild.left; // Перемещение левое поддерево правого ребенка

        if (node.right != null) {
            node.right.parent = node; // Установка родителя для нового узла
        }

        rChild.parent = node.parent; // Установка родителя для правого ребенка

        if (node.parent == null) {
            root = rChild; // Если текущий узел корень, обновляем корень
        } else if (node == node.parent.left) {
            node.parent.left = rChild; // Если текущий узел - левый ребенок
        } else {
            node.parent.right = rChild; // Если текущий узел - правый ребенок
        }

        rChild.left = node; // Текущий узел становится левым ребенком правого
        node.parent = rChild; // Установка родителя для текущего узла
    }

    // Правый поворот
    private void rotateRight(Node node) {
        Node lChild = node.left; // Левый ребенок текущего узла
        node.left = lChild.right; // Перемещение правого поддерева левого ребенка

        if (node.left != null) {
            node.left.parent = node; // Установка родителя для нового узла
        }

        lChild.parent = node.parent; // Установка родителя для левого ребенка

        if (node.parent == null) {
            root = lChild; // Если текущий узел корень, обновляем корень
        } else if (node == node.parent.left) {
            node.parent.left = lChild; // Если текущий узел - левый ребенок
        } else {
            node.parent.right = lChild; // Если текущий узел - правый ребенок
        }

        lChild.right = node; // Текущий узел становится правым ребенком левого
        node.parent = lChild; // Установка родителя для текущего узла
    }

    private void inorderTraversal(StringBuilder sb, Node node) {
        // Метод для симметричного обхода дерева
        if (node != null) {
            inorderTraversal(sb, node.left); // Обход левого поддерева
            sb.append(String.format("%s=%s, ", node.key, node.value)); // Добавление текущего узла в строку
            inorderTraversal(sb, node.right); // Обход правого поддерева
        }
    }

    private Node findByKey(Node node, Object key) {
        // Метод для поиска узла по ключу
        if (node == null) {
            return null; // Возврат null, если узел не найден
        }

        if (key.equals(node.key)) {
            return node; // Возврат узла, если ключ совпадает
        }

        // Сравнение ключа с ключом узла и рекурсивный вызов для поддеревьев
        Comparable<? super Integer> comparable = (Comparable<? super Integer>) key;
        if (comparable.compareTo(node.key) < 0) {
            return findByKey(node.left, key); // Поиск в левом поддереве
        }

        return findByKey(node.right, key); // Поиск в правом поддереве
    }

    private Node findByValue(Node node, Object value) {
        // Метод для поиска узла по значению
        if (node == null) {
            return null; // Возврат null, если узел не найден
        }

        if (node.value.equals(value)) {
            return node; // Возврат узла, если значение совпадает
        }

        // Рекурсивный поиск в поддеревьях
        Node target = findByValue(node.left, value);
        if (target != null) {
            return target; // Если найдено в левом поддереве
        }

        return findByValue(node.right, value); // Поиск в правом поддереве
    }

    @Override
    public String remove(Object key) {
        // Метод для удаления узла по ключу
        Node tryNode = findByKey(root, key); // Поиск узла
        if (tryNode == null) {
            return null; // Если узел не найден, возврат null
        }

        String val = tryNode.value; // Сохранение удаляемого значения
        Node node = delete(root, key); // Удаление узла из дерева
        fixDeletion(node); // Исправление нарушений свойств после удаления
        size--; // Уменьшение размера
        return val; // Возврат удалённого значения
    }

    private Node delete(Node root, Object key) {
        // Метод для удаления узла по ключу
        if (root == null) {
            return null; // Возврат null, если узел не найден
        }

        Comparable<? super Integer> comparable = (Comparable<? super Integer>) key;

        // Рекурсивный поиск узла для удаления
        if (comparable.compareTo(root.key) < 0) {
            return delete(root.left, key); // Удаление в левом поддереве
        }

        if (comparable.compareTo(root.key) > 0) {
            return delete(root.right, key); // Удаление в правом поддереве
        }

        // Если узел имеет одного ребенка
        if (root.left == null || root.right == null) {
            return root; // Возврат узла для дальнейшего удаления
        }

        // Находим узел с минимальным ключом в правом поддереве
        Node temp = minKeyNode(root.right);
        root.key = temp.key; // Замена ключа
        root.value = temp.value; // Замена значения
        return delete(root.right, temp.key); // Удаляем узел с минимальным ключом
    }

    private void fixDeletion(Node node) {
        // Метод для исправления нарушений свойств после удаления узла
        if (node == null) {
            return; // Если узел null, ничего не делаем
        }

        if (node == root) {
            root = null; // Если узел - корень, очищаем дерево
            return;
        }

        // Если удаляемый узел красный или один из его детей красный
        if (getColor(node) == Color.RED || getColor(node.left) == Color.RED || getColor(node.right) == Color.RED) {
            Node child = node.left != null ? node.left : node.right; // Устанавливаем ребенка

            // Устанавливаем связь между родителем и ребенком
            if (node == node.parent.left) {
                node.parent.left = child; // Если удаляемый узел - левый ребенок
            } else {
                node.parent.right = child; // Если удаляемый узел - правый ребенок
            }

            if (child != null) {
                child.parent = node.parent; // Устанавливаем родителя для ребенка
            }
            setColor(child, Color.BLACK); // Устанавливаем цвет ребенка в черный
        } else {
            // Сложная ситуация, когда оба ребенка черные
            Node sibling; // Брат
            Node parent; // Родитель
            Node curr = node; // Текущий узел
            setColor(curr, Color.DOUBLE_BLACK); // Установка цвета как двойной черный

            // Применяем алгоритм для исправления двойного черного
            while (curr != root && getColor(curr) == Color.DOUBLE_BLACK) {
                parent = curr.parent; // Устанавливаем родителя
                if (curr == parent.left) {
                    sibling = parent.right; // Устанавливаем брата
                    if (getColor(sibling) == Color.RED) {
                        // Если брат красный
                        setColor(sibling, Color.BLACK); // Брат становится черным
                        setColor(parent, Color.RED); // Родитель становится красным
                        rotateLeft(parent); // Левый поворот
                    } else {
                        // Если брат черный
                        if (getColor(sibling.left) == Color.BLACK && getColor(sibling.right) == Color.BLACK) {
                            // Если оба ребенка брата черные
                            setColor(sibling, Color.RED); // Брат становится красным
                            if (getColor(parent) == Color.RED) {
                                setColor(parent, Color.BLACK); // Родитель становится черным
                            } else {
                                setColor(parent, Color.DOUBLE_BLACK); // Родитель становится двойным черным
                            }
                            curr = parent; // Переход к родителю
                        } else {
                            // Если хотя бы один ребенок брата красный
                            if (getColor(sibling.right) == Color.BLACK) {
                                setColor(sibling.left, Color.BLACK); // Левый ребенок брата становится черным
                                setColor(sibling, Color.RED); // Брат становится красным
                                rotateRight(sibling); // Правый поворот
                                sibling = parent.right; // Обновление брата
                            }
                            // Устанавливаем цвета
                            setColor(sibling, parent.color); // Устанавливаем цвет брата
                            setColor(parent, Color.BLACK); // Родитель становится черным
                            setColor(sibling.right, Color.BLACK); // Правый ребенок брата становится черным
                            rotateLeft(parent); // Левый поворот
                            break;
                        }
                    }
                } else { // Если текущий узел - правый ребенок
                    sibling = parent.left; // Устанавливаем брата
                    if (getColor(sibling) == Color.RED) {
                        // Если брат красный
                        setColor(sibling, Color.BLACK); // Брат становится черным
                        setColor(parent, Color.RED); // Родитель становится красным
                        rotateRight(parent); // Правый поворот
                    } else {
                        // Если брат черный
                        if (getColor(sibling.left) == Color.BLACK && getColor(sibling.right) == Color.BLACK) {
                            // Если оба ребенка брата черные
                            setColor(sibling, Color.RED); // Брат становится красным
                            if (getColor(parent) == Color.RED) {
                                setColor(parent, Color.BLACK); // Родитель становится черным
                            } else {
                                setColor(parent, Color.DOUBLE_BLACK); // Родитель становится двойным черным
                            }
                            curr = parent; // Переход к родителю
                        } else {
                            // Если хотя бы один ребенок брата красный
                            if (getColor(sibling.left) == Color.BLACK) {
                                setColor(sibling.right, Color.BLACK); // Правый ребенок брата становится черным
                                setColor(sibling, Color.RED); // Брат становится красным
                                rotateLeft(sibling); // Левый поворот
                                sibling = parent.left; // Обновление брата
                            }
                            // Устанавливаем цвета
                            setColor(sibling, parent.color); // Устанавливаем цвет брата
                            setColor(parent, Color.BLACK); // Родитель становится черным
                            setColor(sibling.left, Color.BLACK); // Левый ребенок брата становится черным
                            rotateRight(parent); // Правый поворот
                            break;
                        }
                    }
                }
            }

            // Удаление узла
            if (node == node.parent.left) {
                node.parent.left = null; // Удаляем левый ребенок
            } else {
                node.parent.right = null; // Удаляем правый ребенок
            }

            setColor(root, Color.BLACK); // Корень всегда черный
        }
    }


    @Override
    public String get(Object key) {
        Node target = findByKey(root, key);
        return (target == null) ? null : target.value;
    }

    @Override
    public boolean containsKey(Object key) {
        return findByKey(root, key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        return findByValue(root, value) != null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public SortedMap<Integer, String> headMap(Integer toKey) {

        if (toKey == null)
            throw new NullPointerException("The key cannot be null");

        SortedMap<Integer, String> sortedMap = new MyRbMap();
        setToKey(root, toKey, sortedMap);

        return sortedMap;
    }

    private void setToKey(Node node, int toKey, SortedMap<Integer, String> sortedMap) {

        if (node == null)
            return;

        setToKey(node.left, toKey, sortedMap);

        if (node.key < toKey) {
            sortedMap.put(node.key, node.value);
            setToKey(node.right, toKey, sortedMap);
        }
    }

    @Override
    public SortedMap<Integer, String> tailMap(Integer fromKey) {

        if (fromKey == null)
            throw new NullPointerException("The key cannot be null");

        SortedMap<Integer, String> sortedMap = new MyRbMap();
        setFromKey(root, fromKey, sortedMap);

        return sortedMap;
    }

    private void setFromKey(Node node, int fromKey, SortedMap<Integer, String> sortedMap) {
        if (node == null)
            return;

        setFromKey(node.right, fromKey, sortedMap);

        if (node.key >= fromKey) {
            sortedMap.put(node.key, node.value);
            setFromKey(node.left, fromKey, sortedMap);
        }
    }

    @Override
    public Integer firstKey() {

        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        return minKeyNode(root).key;
    }

    @Override
    public Integer lastKey() {

        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        return maxKeyNode(root).key;
    }

    private Node minKeyNode(Node root) {
        Node node = root;
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    private Node maxKeyNode(Node root) {
        Node node = root;
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    private Color getColor(Node node) {

        if (node == null) {
            return Color.BLACK;
        }

        return node.color;
    }

    private void setColor(Node node, Color color) {

        if (node == null) {
            return;
        }

        node.color = color;
    }

    //–––––

    @Override
    public Comparator<? super Integer> comparator() {
        return null;
    }

    @Override
    public SortedMap<Integer, String> subMap(Integer fromKey, Integer toKey) {
        return null;
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

    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {

    }

    private static class Node {

        Color color;
        Node left, right, parent;
        Integer key;
        String value;

        public Node(Node parent, Integer key, String value, Color color) {
            this.parent = parent;
            this.key = key;
            this.value = value;
            this.color = color;
            left = right = null;
        }
    }

    private enum Color {
        BLACK,
        RED,
        DOUBLE_BLACK
    }
}