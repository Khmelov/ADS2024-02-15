package by.it.group310902.pashkovich.lesson12;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

// Реализация пользовательской структуры данных AVL Map
public class MyAvlMap implements Map<Integer, String> {

    // Внутренний класс для представления узла AVL-дерева
    class AVLNode {
        int key; // Ключ
        String value; // Значение
        int Height; // Высота узла
        AVLNode Left, Right; // Ссылки на левого и правого потомков

        AVLNode(int key, String value) { // Конструктор узла
            this.key = key;
            this.value = value;
            this.Height = 1; // Новый узел всегда имеет высоту 1
        }
    }

    AVLNode Root; // Корень AVL-дерева
    StringBuilder result; // Переменная для хранения результата операций

    // Возвращает количество элементов в дереве
    @Override
    public int size() {
        return size(Root);
    }

    private int size(AVLNode node) {
        if (node == null) {
            return 0; // Если узел пустой, размер равен 0
        }
        return 1 + size(node.Left) + size(node.Right); // Рекурсивно считаем размер левого и правого поддерева
    }

    // Проверяет, пусто ли дерево
    @Override
    public boolean isEmpty() {
        return Root == null;
    }

    // Возвращает представление дерева в виде строки
    @Override
    public String toString() {
        if (Root == null)
            return "{}";
        StringBuilder sb = new StringBuilder().append('{');
        InOrderTraversal(Root, sb); // Обход дерева в порядке возрастания ключей
        sb.replace(sb.length() - 2, sb.length(), "}"); // Убираем последнюю запятую
        return sb.toString();
    }

    // Обход дерева в порядке возрастания ключей
    private void InOrderTraversal(AVLNode node, StringBuilder sb) {
        if (node != null) {
            InOrderTraversal(node.Left, sb); // Рекурсивный обход левого поддерева
            sb.append(node.key + "=" + node.value + ", "); // Добавляем текущий узел
            InOrderTraversal(node.Right, sb); // Рекурсивный обход правого поддерева
        }
    }

    // Проверяет, содержится ли указанный ключ в дереве
    @Override
    public boolean containsKey(Object key) {
        return Search((Integer) key, Root) != null;
    }

    // Поиск узла по ключу
    AVLNode Search(Integer key, AVLNode node) {
        if (node == null)
            return null; // Ключ не найден
        int comparison = key.compareTo(node.key);
        if (comparison == 0)
            return node; // Ключ найден

        return Search(key, comparison < 0 ? node.Left : node.Right); // Рекурсивный поиск в левом или правом поддереве
    }

    // Возвращает значение по ключу
    @Override
    public String get(Object key) {
        AVLNode result = Search((Integer) key, Root); // Ищем узел
        return result == null ? null : result.value; // Возвращаем значение или null, если ключ не найден
    }

    // Добавляет или обновляет элемент в дереве
    @Override
    public String put(Integer key, String value) {
        result = new StringBuilder(); // Инициализация результата
        Root = put(Root, key, value); // Вставка элемента
        return result.isEmpty() ? null : result.toString(); // Если результат пустой, значит элемент добавлен
    }

    // Рекурсивная вставка элемента в дерево
    AVLNode put(AVLNode node, Integer key, String value) {
        if (node == null) {
            return new AVLNode(key, value); // Создаем новый узел, если достигли пустого места
        }
        int comparison = key.compareTo(node.key);
        if (comparison < 0)
            node.Left = put(node.Left, key, value); // Вставляем в левое поддерево
        else if (comparison > 0)
            node.Right = put(node.Right, key, value); // Вставляем в правое поддерево
        else {
            // Если ключ уже существует, обновляем значение
            if (!node.value.equalsIgnoreCase(value)) {
                node.value = value; // Обновляем значение
                result.append("generate" + key); // Добавляем информацию об обновлении
            }
        }
        return Balance(node); // Балансируем дерево
    }

    // Возвращает высоту узла
    int Height(AVLNode node) {
        return node == null ? 0 : node.Height;
    }

    // Вычисляет фактор баланса узла
    int BalanceFactor(AVLNode node) {
        return node == null ? 0 : Height(node.Left) - Height(node.Right);
    }

    // Правый поворот узла
    AVLNode RotateRight(AVLNode node) {
        AVLNode newRoot = node.Left; // Новый корень станет левым потомком
        node.Left = newRoot.Right; // Перестраиваем ссылки
        newRoot.Right = node;
        // Обновляем высоты
        node.Height = 1 + Math.max(Height(node.Left), Height(node.Right));
        newRoot.Height = 1 + Math.max(Height(newRoot.Left), Height(newRoot.Right));
        return newRoot; // Возвращаем новый корень
    }

    // Левый поворот узла
    AVLNode RotateLeft(AVLNode node) {
        AVLNode newRoot = node.Right; // Новый корень станет правым потомком
        node.Right = newRoot.Left; // Перестраиваем ссылки
        newRoot.Left = node;
        // Обновляем высоты
        node.Height = 1 + Math.max(Height(node.Left), Height(node.Right));
        newRoot.Height = 1 + Math.max(Height(newRoot.Left), Height(newRoot.Right));
        return newRoot; // Возвращаем новый корень
    }

    // Балансировка узла
    AVLNode Balance(AVLNode node) {
        if (node == null)
            return node;

        node.Height = 1 + Math.max(Height(node.Left), Height(node.Right));
        int balanceFactor = BalanceFactor(node);

        if (balanceFactor > 1) { // Левый перекос
            if (BalanceFactor(node.Left) < 0) // Левый-правый случай
                node.Left = RotateLeft(node.Left);
            return RotateRight(node); // Левый-левый случай
        }

        if (balanceFactor < -1) { // Правый перекос
            if (BalanceFactor(node.Right) > 0) // Правый-левый случай
                node.Right = RotateRight(node.Right);
            return RotateLeft(node); // Правый-правый случай
        }

        return node; // Узел уже сбалансирован
    }

    // Удаляет элемент по ключу
    @Override
    public String remove(Object key) {
        result = new StringBuilder();
        Root = remove(Root, (Integer) key);
        return result.isEmpty() ? null : result.toString();
    }

    // Рекурсивное удаление узла
    AVLNode remove(AVLNode node, Integer key) {
        if (node == null)
            return node;
        int comparison = key.compareTo(node.key);
        if (comparison < 0)
            node.Left = remove(node.Left, key); // Ищем в левом поддереве
        else if (comparison > 0)
            node.Right = remove(node.Right, key); // Ищем в правом поддереве
        else {
            // Узел найден
            result.append("generate" + key);
            if (node.Left == null)
                return node.Right; // Если нет левого потомка, возвращаем правый
            if (node.Right == null)
                return node.Left; // Если нет правого потомка, возвращаем левый

            AVLNode minNode = minValueNode(node.Right); // Находим минимальный узел в правом поддереве
            node.value = minNode.value;
            node.Right = RemoveMinNode(node.Right); // Удаляем минимальный узел
        }

        return Balance(node); // Балансируем дерево
    }

    // Удаляет минимальный узел
    AVLNode RemoveMinNode(AVLNode node) {
        if (node.Left == null)
            return node.Right; // Возвращаем правый потомок
        node.Left = RemoveMinNode(node.Left); // Рекурсивно удаляем минимальный узел
        return Balance(node); // Балансируем дерево
    }

    // Находит минимальный узел
    AVLNode minValueNode(AVLNode node) {
        return node.Left == null ? node : minValueNode(node.Left);
    }

    // Очищает дерево
    @Override
    public void clear() {
        Root = clear(Root);
    }

    AVLNode clear(AVLNode node) {
        if (node == null)
            return null; // Если узел пустой, возвращаем null
        node.Left = clear(node.Left); // Рекурсивно очищаем левое поддерево
        node.Right = clear(node.Right); // Рекурсивно очищаем правое поддерево
        return null; // Удаляем текущий узел
    }

    // Проверяет, содержится ли значение в дереве
    @Override
    public boolean containsValue(Object value) {
        return false; // Метод не реализован
    }

    // Добавляет все элементы из другой карты
    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {
        // Метод не реализован
    }

    // Возвращает множество ключей
    @Override
    public Set<Integer> keySet() {
        return null; // Метод не реализован
    }

    // Возвращает коллекцию значений
    @Override
    public Collection<String> values() {
        return null; // Метод не реализован
    }

    // Возвращает множество пар ключ-значение
    @Override
    public Set<Entry<Integer, String>> entrySet() {
        return null; // Метод не реализован
    }
}