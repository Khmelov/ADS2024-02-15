package by.it.group310902.isakov.lesson12;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unchecked")
public class MyAvlMap implements Map<Integer, String> {

    // Основное дерево AVL и размер карты
    private Node root; // Корень дерева
    private int size;  // Количество элементов в карте

    // Возвращает строковое представление карты в формате {key1=value1, key2=value2, ...}
    @Override
    public String toString() {
        if (isEmpty()) { // Проверка на пустоту карты
            return "{}";
        }
        StringBuilder sb = new StringBuilder("{");
        inorderTraversal(sb, root); // Обход дерева в порядке возрастания ключей
        return sb.delete(sb.length() - 2, sb.length()).append("}").toString(); // Удаление последней запятой и пробела
    }

    // Рекурсивный обход дерева (in-order) для формирования строки
    private void inorderTraversal(StringBuilder sb, Node node) {
        if (node != null) {
            inorderTraversal(sb, node.left); // Обход левого поддерева
            sb.append(String.format("%s=%s, ", node.key, node.value)); // Добавление текущего узла
            inorderTraversal(sb, node.right); // Обход правого поддерева
        }
    }

    // Добавляет пару ключ-значение в карту
    @Override
    public String put(Integer key, String value) {
        Node tryNode = findByKey(root, key); // Проверка существования узла с таким ключом
        if (tryNode != null) { // Если ключ уже существует
            String prevVal = tryNode.value; // Сохранение старого значения
            tryNode.value = value; // Обновление значения
            return prevVal; // Возврат старого значения
        }

        size++; // Увеличение размера карты
        root = insert(root, key, value); // Вставка нового узла в дерево
        return null; // Возвращает null, если ключа не было
    }

    // Удаляет узел по ключу и возвращает его значение
    @Override
    public String remove(Object key) {
        Node tryNode = findByKey(root, key); // Поиск узла
        if (tryNode == null) { // Если узел не найден
            return null;
        }

        String val = tryNode.value; // Сохранение значения
        root = remove(root, (Integer) key); // Удаление узла из дерева
        size--; // Уменьшение размера карты
        return val; // Возврат удалённого значения
    }

    // Возвращает значение, связанное с ключом, или null, если ключ не найден
    @Override
    public String get(Object key) {
        Node target = findByKey(root, key); // Поиск узла
        return (target == null) ? null : target.value; // Возврат значения или null
    }

    // Проверяет, содержится ли ключ в карте
    @Override
    public boolean containsKey(Object key) {
        return findByKey(root, key) != null;
    }

    // Возвращает размер карты
    @Override
    public int size() {
        return size;
    }

    // Очищает карту
    @Override
    public void clear() {
        root = null; // Удаление дерева
        size = 0; // Обнуление размера
    }

    // Проверяет, пустая ли карта
    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    // Возвращает высоту узла
    private byte height(Node node) {
        return node == null ? 0 : node.height;
    }

    // Вычисляет баланс-фактор узла (разницу высот правого и левого поддерева)
    private int calcBalanceFactor(Node node) {
        return height(node.right) - height(node.left);
    }

    // Обновляет высоту узла
    private void fixHeight(Node node) {
        byte lHeight = height(node.left); // Высота левого поддерева
        byte rHeight = height(node.right); // Высота правого поддерева
        node.height = (byte) (Math.max(lHeight, rHeight) + 1); // Новая высота узла
    }

    // Правый поворот
    private Node rotateRight(Node node) {
        Node q = node.left; // Новый корень
        node.left = q.right; // Перенос правого поддерева q
        q.right = node; // Установка текущего узла справа от нового корня
        fixHeight(node); // Обновление высот
        fixHeight(q);
        return q; // Новый корень
    }

    // Левый поворот
    private Node rotateLeft(Node node) {
        Node q = node.right; // Новый корень
        node.right = q.left; // Перенос левого поддерева q
        q.left = node; // Установка текущего узла слева от нового корня
        fixHeight(q); // Обновление высот
        fixHeight(node);
        return q; // Новый корень
    }

    // Поиск узла по ключу
    private Node findByKey(Node node, Object key) {
        if (node == null) { // Если узел не найден
            return null;
        }

        if (key.equals(node.key)) { // Если ключи совпадают
            return node;
        }

        Comparable<? super Integer> comparable = (Comparable<? super Integer>) key;
        if (comparable.compareTo(node.key) < 0) { // Если ключ меньше текущего
            return findByKey(node.left, key); // Идём в левое поддерево
        }

        return findByKey(node.right, key); // Идём в правое поддерево
    }

    // Находит узел с минимальным ключом
    private Node findMin(Node node) {
        return node.left == null ? node : findMin(node.left);
    }

    // Удаляет минимальный узел и возвращает его
    private Node removeMin(Node node) {
        if (node.left == null) { // Если нет левого поддерева
            return node.right;
        }

        node.left = removeMin(node.left); // Удаление минимального узла из левого поддерева
        return balance(node); // Балансировка дерева
    }

    // Удаляет узел по ключу и балансирует дерево
    private Node remove(Node node, Integer key) {
        if (node == null) { // Узел не найден
            return null;
        }

        if (key < node.key) { // Идём в левое поддерево
            node.left = remove(node.left, key);
        } else if (key > node.key) { // Идём в правое поддерево
            node.right = remove(node.right, key);
        } else { // Найден узел для удаления
            Node q = node.left;
            Node r = node.right;
            if (r == null) { // Если нет правого поддерева
                return q;
            }
            Node min = findMin(r); // Поиск минимального узла в правом поддереве
            min.right = removeMin(r); // Удаление минимального узла
            min.left = q; // Перенос левого поддерева
            return balance(min); // Балансировка
        }

        return balance(node); // Балансировка
    }

    // Балансировка узла
    private Node balance(Node node) {
        fixHeight(node); // Обновление высоты узла
        if (calcBalanceFactor(node) == 2) { // Если правое поддерево слишком высокое
            if (calcBalanceFactor(node.right) < 0) { // Право-левый случай
                node.right = rotateRight(node.right);
            }
            return rotateLeft(node); // Право-правый случай
        }
        if (calcBalanceFactor(node) == -2) { // Если левое поддерево слишком высокое
            if (calcBalanceFactor(node.left) > 0) { // Лево-правый случай
                node.left = rotateLeft(node.left);
            }
            return rotateRight(node); // Лево-левый случай
        }
        return node; // Балансировка не требуется
    }

    // Вставляет узел с заданным ключом и значением
    private Node insert(Node node, Integer key, String value) {
        if (node == null) { // Если узел пустой
            return new Node(key, value);
        }

        if (key > node.key) { // Если ключ больше текущего
            node.right = insert(node.right, key, value); // Идём в правое поддерево
        } else {
            node.left = insert(node.left, key, value); // Идём в левое поддерево
        }

        return balance(node); // Балансировка после вставки
    }

    // Остальные методы Map пока не реализованы

    @Override
    public boolean containsValue(Object value) {
        return false; // Проверка на значение не реализована
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {

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

    private static class Node {

        Integer key;
        String value;
        byte height;
        Node left, right;

        public Node(Integer key, String value) {
            this.key = key;
            this.value = value;
            height = 1;
        }

        @Override
        public String toString() {
            return String.format("{%s=%s}", key, value);
        }
    }
}