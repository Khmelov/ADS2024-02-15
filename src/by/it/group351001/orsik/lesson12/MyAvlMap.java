package by.it.group351001.orsik.lesson12;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unchecked") // Отключаем предупреждения компилятора о неявных привидениях типов
public class MyAvlMap implements Map<Integer, String> {

    private Node root; // Корень дерева
    private int size; // Размер (количество элементов) в дереве

    @Override
    public String toString() {
        // Переопределение метода toString для представления дерева в виде строки
        if (isEmpty()) {
            return "{}"; // Вернуть пустую фигурную скобку, если дерево пустое
        }

        StringBuilder sb = new StringBuilder("{");
        inorderTraversal(sb, root); // Выполнить симметричный обход дерева
        return sb.delete(sb.length() - 2, sb.length()).append("}").toString(); // Удалить лишнюю запятую и пробел
    }

    // Метод для симметричного обхода дерева
    private void inorderTraversal(StringBuilder sb, Node node) {
        if (node != null) {
            inorderTraversal(sb, node.left); // Обход левого поддерева
            sb.append(String.format("%s=%s, ", node.key, node.value)); // Добавление текущего узла в строку
            inorderTraversal(sb, node.right); // Обход правого поддерева
        }
    }

    @Override
    public String put(Integer key, String value) {
        // Метод для вставки новой пары ключ-значение
        Node tryNode = findByKey(root, key); // Поиск узла с данным ключом
        if (tryNode != null) {
            String prevVal = tryNode.value; // Сохранение предыдущего значения
            tryNode.value = value; // Обновление значения
            return prevVal; // Возврат предыдущего значения
        }

        size++; // Увеличение размера дерева
        root = insert(root, key, value); // Вставка нового узла в дерево
        return null; // Возврат null, если вставка прошла успешно
    }

    @Override
    public String remove(Object key) {
        // Метод для удаления узла по ключу
        Node tryNode = findByKey(root, key); // Поиск узла
        if (tryNode == null) {
            return null; // Если узел не найден, возврат null
        }

        String val = tryNode.value; // Сохранение удаляемого значения
        root = remove(root, (Integer) key); // Удаление узла из дерева
        size--; // Уменьшение размера
        return val; // Возврат удалённого значения
    }

    @Override
    public String get(Object key) {
        // Метод для получения значения по ключу
        Node target = findByKey(root, key); // Поиск узла
        return (target == null) ? null : target.value; // Возврат значения или null, если узел не найден
    }

    @Override
    public boolean containsKey(Object key) {
        // Метод для проверки наличия ключа в дереве
        return findByKey(root, key) != null; // Возврат true, если узел найден
    }

    @Override
    public int size() {
        // Метод для получения размера дерева
        return size; // Возврат количества элементов
    }

    @Override
    public void clear() {
        // Метод для очистки дерева
        root = null; // Установка корня в null
        size = 0; // Сброс размера
    }

    @Override
    public boolean isEmpty() {
        // Метод для проверки, пустое ли дерево
        return (size == 0); // Возврат true, если размер равен 0
    }

    private byte height(Node node) {
        // Метод для получения высоты узла
        return node == null ? 0 : node.height; // Возврат 0 для null или высоты узла
    }

    private int calcBalanceFactor(Node node) {
        // Метод для вычисления балансировочного фактора узла
        return height(node.right) - height(node.left); // Разность высот правого и левого поддеревьев
    }

    private void fixHeight(Node node) {
        // Метод для обновления высоты узла
        byte lHeight = height(node.left); // Высота левого поддерева
        byte rHeight = height(node.right); // Высота правого поддерева
        node.height = (byte) (Math.max(lHeight, rHeight) + 1); // Установка высоты узла
    }

    private Node rotateRight(Node node) {
        // Метод для правого поворота
        Node q = node.left; // Левый узел
        node.left = q.right; // Перемещение правого поддерева
        q.right = node; // Установка текущего узла как правого ребенка
        fixHeight(node); // Обновление высоты узла
        fixHeight(q); // Обновление высоты нового корня
        return q; // Возврат нового корня
    }

    private Node rotateLeft(Node node) {
        // Метод для левого поворота
        Node q = node.right; // Правый узел
        node.right = q.left; // Перемещение левого поддерева
        q.left = node; // Установка текущего узла как левого ребенка
        fixHeight(node); // Обновление высоты узла
        fixHeight(q); // Обновление высоты нового корня
        return q; // Возврат нового корня
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

    private Node findMin(Node node) {
        // Метод для поиска узла с минимальным ключом
        return node.left == null ? node : findMin(node.left); // Возврат узла, если нет левого поддерева
    }

    private Node removeMin(Node node) {
        // Метод для удаления узла с минимальным ключом
        if (node.left == null) {
            return node.right; // Возврат правого поддерева
        }

        node.left = removeMin(node.left); // Рекурсивное удаление в левом поддереве
        return balance(node); // Балансировка узла после удаления
    }

    private Node remove(Node node, Integer key) {
        // Метод для удаления узла по ключу
        if (node == null) {
            return null; // Возврат null, если узел не найден
        }

        // Рекурсивный поиск узла для удаления
        if (key < node.key) {
            node.left = remove(node.left, key); // Удаление в левом поддереве
        }
        else if (key > node.key) {
            node.right = remove(node.right, key); // Удаление в правом поддереве
        }
        else {
            // Узел найден, производим удаление
            Node q = node.left; // Левое поддерево
            Node r = node.right; // Правое поддерево
            if (r == null) {
                return q; // Если правого поддерева нет, возвращаем левое
            }
            Node min = findMin(r); // Находим узел с минимальным ключом в правом поддереве
            min.right = removeMin(r); // Удаляем узел с минимальным ключом
            min.left = q; // Устанавливаем левое поддерево
            return balance(min); // Балансировка узла
        }

        return balance(node); // Возврат сбалансированного узла
    }

    private Node balance(Node node) {
        // Метод для балансировки узла
        fixHeight(node); // Обновление высоты узла
        if (calcBalanceFactor(node) == 2) { // Если балансировочный фактор равен 2
            if (calcBalanceFactor(node.right) < 0) {
                node.right = rotateRight(node.right); // Правый поворот
            }
            return rotateLeft(node); // Левый поворот
        }

        if (calcBalanceFactor(node) == -2) { // Если балансировочный фактор равен -2
            if (calcBalanceFactor(node.left) > 0) {
                node.left = rotateLeft(node.left); // Левый поворот
            }
            return rotateRight(node); // Правый поворот
        }

        return node; // Возврат сбалансированного узла
    }

    private Node insert(Node node, Integer key, String value) {
        // Метод для вставки нового узла в дерево
        if (node == null) {
            return new Node(key, value); // Создание нового узла
        }

        // Рекурсивная вставка в левое или правое поддерево
        if (key > node.key) {
            node.right = insert(node.right, key, value);
        }
        else {
            node.left = insert(node.left, key, value);
        }

        return balance(node); // Возврат сбалансированного узла
    }

    // Методы, которые не реализованы в данном классе
    @Override
    public boolean containsValue(Object value) {
        return false; // Возврат false, так как метод не реализован
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {
        // Метод не реализован
    }

    @Override
    public Set<Integer> keySet() {
        return null; // Метод не реализован
    }

    @Override
    public Collection<String> values() {
        return null; // Метод не реализован
    }

    @Override
    public Set<Entry<Integer, String>> entrySet() {
        return null; // Метод не реализован
    }

    // Внутренний класс, представляющий узел дерева
    private static class Node {
        Integer key; // Ключ узла
        String value; // Значение узла
        byte height; // Высота узла
        Node left, right; // Левый и правый дочерние узлы

        public Node(Integer key, String value) {
            this.key = key; // Инициализация ключа
            this.value = value; // Инициализация значения
            height = 1; // Установка начальной высоты узла
        }

        @Override
        public String toString() {
            return String.format("{%s=%s}", key, value); // Форматирование узла для вывода
        }
    }
}