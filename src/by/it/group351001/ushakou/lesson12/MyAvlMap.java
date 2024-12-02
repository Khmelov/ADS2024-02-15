package by.it.group351001.ushakou.lesson12;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/* *
 * В данном классе реализована структура данных AVL-дерево, которая используется как
 * коллекция, реализующая интерфейс Map для хранения пар "ключ-значение".
 * В дереве AVL поддерживается балансировка, что позволяет достигать
 * логарифмической сложности для основных операций (вставка, удаление, поиск).
 * Ключи хранятся в порядке возрастания, что обеспечивает быстрый поиск.
 */
public class MyAvlMap implements Map<Integer, String> {

    // Вложенный класс для представления узлов AVL-дерева
    class AVLNode {
        int key;           // Ключ
        String value;      // Значение
        int Height;        // Высота узла (для балансировки)
        AVLNode Left, Right; // Левый и правый дочерние узлы

        // Конструктор для создания нового узла
        AVLNode(int key, String value) {
            this.key = key;
            this.value = value;
            this.Height = 1;  // Изначально высота узла равна 1
        }
    }

    // Корень дерева
    AVLNode Root;
    // Строка для хранения результата (при необходимости, например, при удалении)
    StringBuilder result;

    /**
     * Возвращает количество элементов в дереве
     */
    @Override
    public int size() {
        return size(Root);  // Вызов вспомогательного метода для подсчета элементов
    }

    // Рекурсивный метод для подсчета количества элементов в поддереве
    private int size(AVLNode node) {
        if (node == null) {
            return 0;  // Если узел пустой, количество равно 0
        }
        return 1 + size(node.Left) + size(node.Right);  // Суммируем количество элементов в левом и правом поддеревьях
    }

    /**
     * Проверка на пустоту дерева
     */
    @Override
    public boolean isEmpty() {
        return Root == null;  // Если корень дерева равен null, значит дерево пустое
    }

    /**
     * Метод для представления дерева в виде строки
     */
    @Override
    public String toString() {
        if (Root == null)  // Если дерево пустое
            return "{}";    // Возвращаем пустую строку

        StringBuilder sb = new StringBuilder().append('{'); // Начинаем строку с '{'
        InOrderTraversal(Root, sb); // Проходим дерево в порядке обхода
        sb.replace(sb.length() - 2, sb.length(), "}");  // Убираем последнюю запятую и пробел
        return sb.toString();  // Возвращаем строковое представление дерева
    }

    // Рекурсивный метод для обхода дерева в порядке "in-order"
    private void InOrderTraversal(AVLNode node, StringBuilder sb) {
        if (node != null) {
            InOrderTraversal(node.Left, sb); // Обходим левое поддерево
            sb.append(node.key + "=" + node.value + ", "); // Добавляем текущий узел в строку
            InOrderTraversal(node.Right, sb); // Обходим правое поддерево
        }
    }

    /**
     * Проверка наличия ключа в дереве
     */
    @Override
    public boolean containsKey(Object key) {
        return Search((Integer) key, Root) != null;  // Ищем ключ в дереве
    }

    // Рекурсивный метод для поиска узла по ключу
    AVLNode Search(Integer key, AVLNode node) {
        if (node == null)  // Если узел пустой, возвращаем null
            return null;

        int comparison = key.compareTo(node.key);  // Сравниваем ключи
        if (comparison == 0)  // Если ключ найден, возвращаем узел
            return node;

        // Если ключ меньше текущего, ищем в левом поддереве, если больше — в правом
        return Search(key, comparison < 0 ? node.Left : node.Right);
    }

    /**
     * Получение значения по ключу
     */
    @Override
    public String get(Object key) {
        AVLNode result = Search((Integer) key, Root);  // Ищем узел по ключу
        return result == null ? null : result.value;  // Если узел найден, возвращаем его значение
    }

    /**
     * Вставка новой пары ключ-значение в дерево
     */
    @Override
    public String put(Integer key, String value) {
        result = new StringBuilder();  // Инициализируем строку для результата
        Root = put(Root, key, value);  // Вставляем элемент в дерево
        return result.isEmpty() ? null : result.toString();  // Если результат пустой, возвращаем null
    }

    // Рекурсивный метод для вставки элемента в поддерево
    AVLNode put(AVLNode node, Integer key, String value) {
        if (node == null) {  // Если узел пустой, создаем новый
            return new AVLNode(key, value);
        }

        int comparison = key.compareTo(node.key);  // Сравниваем ключи

        if (comparison < 0)  // Если ключ меньше, вставляем в левое поддерево
            node.Left = put(node.Left, key, value);
        else if (comparison > 0)  // Если ключ больше, вставляем в правое поддерево
            node.Right = put(node.Right, key, value);
        else {  // Если ключ уже существует, обновляем значение
            if (!node.value.equalsIgnoreCase(value)) {  // Если значение отличается
                node.value = value;  // Обновляем значение
                result.append("generate" + key);  // Записываем ключ в результат
            }
        }

        // Балансировка дерева после вставки
        return Balance(node);
    }

    // Метод для получения высоты узла
    int Height(AVLNode node) {
        return node == null ? 0 : node.Height;
    }

    // Метод для вычисления баланс-фактора узла
    int BalanceFactor(AVLNode node) {
        return node == null ? 0 : Height(node.Left) - Height(node.Right);
    }

    // Поворот вправо для балансировки дерева
    AVLNode RotateRight(AVLNode node) {
        AVLNode newRoot = node.Left;
        node.Left = newRoot.Right;
        newRoot.Right = node;
        node.Height = 1 + Math.max(Height(node.Left), Height(node.Right));  // Обновляем высоту узла
        newRoot.Height = 1 + Math.max(Height(newRoot.Left), Height(newRoot.Right));  // Обновляем высоту нового корня
        return newRoot;
    }

    // Поворот влево для балансировки дерева
    AVLNode RotateLeft(AVLNode node) {
        AVLNode newRoot = node.Right;
        node.Right = newRoot.Left;
        newRoot.Left = node;
        node.Height = 1 + Math.max(Height(node.Left), Height(node.Right));  // Обновляем высоту узла
        newRoot.Height = 1 + Math.max(Height(newRoot.Left), Height(newRoot.Right));  // Обновляем высоту нового корня
        return newRoot;
    }

    // Балансировка узла
    AVLNode Balance(AVLNode node) {
        if (node == null)
            return node;

        node.Height = 1 + Math.max(Height(node.Left), Height(node.Right));  // Обновляем высоту узла
        int balanceFactor = BalanceFactor(node);  // Вычисляем баланс-фактор

        // Если баланс-фактор > 1, нужно выполнить поворот вправо
        if (balanceFactor > 1) {
            if (BalanceFactor(node.Left) < 0)  // Если левое поддерево "перевешивает" вправо, делаем поворот влево
                node.Left = RotateLeft(node.Left);
            return RotateRight(node);  // Поворот вправо
        }

        // Если баланс-фактор < -1, нужно выполнить поворот влево
        if (balanceFactor < -1) {
            if (BalanceFactor(node.Right) > 0)  // Если правое поддерево "перевешивает" влево, делаем поворот вправо
                node.Right = RotateRight(node.Right);
            return RotateLeft(node);  // Поворот влево
        }

        return node;  // Если дерево сбалансировано, возвращаем узел
    }

    /**
     * Удаление ключа из дерева
     */
    @Override
    public String remove(Object key) {
        result = new StringBuilder();  // Инициализируем строку для результата
        Root = remove(Root, (Integer) key);  // Удаляем элемент из дерева
        return result.isEmpty() ? null : result.toString();  // Если результат пустой, возвращаем null
    }

    // Рекурсивный метод для удаления элемента из поддерева
    AVLNode remove(AVLNode node, Integer key) {
        if (node == null)  // Если узел пустой, ничего не делаем
            return node;

        int comparison = key.compareTo(node.key);  // Сравниваем ключи

        if (comparison < 0)  // Если ключ меньше, удаляем из левого поддерева
            node.Left = remove(node.Left, key);
        else if (comparison > 0)  // Если ключ больше, удаляем из правого поддерева
            node.Right = remove(node.Right, key);
        else {  // Если нашли ключ
            result.append("generate" + key);  // Добавляем ключ в результат
            if (node.Left == null)  // Если у узла нет левого дочернего узла, возвращаем правый
                return node.Right;
            if (node.Right == null)  // Если у узла нет правого дочернего узла, возвращаем левый
                return node.Left;

            // Ищем минимальный узел в правом поддереве
            AVLNode minNode = minValueNode(node.Right);
            node.value = minNode.value;  // Заменяем значение на минимальное
            node.Right = RemoveMinNode(node.Right);  // Удаляем минимальный узел
        }

        return Balance(node);  // Балансируем дерево после удаления
    }

    // Метод для удаления минимального узла
    AVLNode RemoveMinNode(AVLNode node) {
        if (node.Left == null)  // Если у узла нет левого дочернего узла
            return node.Right;

        node.Left = RemoveMinNode(node.Left);  // Рекурсивно удаляем минимальный узел
        return Balance(node);  // Балансируем дерево после удаления
    }

    // Метод для нахождения минимального узла в поддереве
    AVLNode minValueNode(AVLNode node) {
        return node.Left == null ? node : minValueNode(node.Left);  // Ищем минимальный узел
    }

    /**
     * Очистка дерева
     */
    @Override
    public void clear() {
        Root = clear(Root);  // Очищаем дерево
    }

    // Рекурсивный метод для очистки поддерева
    AVLNode clear(AVLNode node) {
        if (node == null)
            return null;
        node.Left = clear(node.Left);  // Очищаем левое поддерево
        node.Right = clear(node.Right);  // Очищаем правое поддерево
        return null;  // Возвращаем null для очищенного поддерева
    }

    //////////////////////////////////////////////////////

    // Методы, не реализующие функциональность, возвращают по умолчанию "false" или "null"
    @Override
    public boolean containsValue(Object value) {
        return false;
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
}
