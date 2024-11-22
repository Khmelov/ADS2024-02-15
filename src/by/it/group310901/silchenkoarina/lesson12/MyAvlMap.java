package by.it.group310901.silchenkoarina.lesson12;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

//    Создайте class MyAvlMap, который реализует интерфейс Map<Integer, String>
//    и работает на основе АВЛ-дерева
//    БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ
//
//    Метод toString() должен выводить элементы в порядке возрастания ключей
//    Формат вывода: скобки (фигурные) и разделители
//    (знак равенства и запятая с пробелом) должны
//    быть такими же как в методе toString() обычной коллекции

public class MyAvlMap implements Map<Integer, String> {

    class AVLNode {
        int key;
        String value;
        int Height;
        AVLNode Left, Right;

        AVLNode(int key, String value) {
            this.key = key;
            this.value = value;
            this.Height = 1; // начальная высота узла
        }
    }

    AVLNode Root;
    StringBuilder result;

    @Override
    public int size() {
        return size(Root);
    }

    private int size(AVLNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + size(node.Left) + size(node.Right);
    }

    @Override
    public boolean isEmpty() {
        return Root == null;
    }

    @Override
    public String toString() {
        if (Root == null)
            return "{}";
        StringBuilder sb = new StringBuilder().append('{');
        InOrderTraversal(Root, sb); //проход в порядке возрастания
        sb.replace(sb.length() - 2, sb.length(), "}"); // Заменяем последний разделитель на закрывающую скобку
        return sb.toString();
    }

    private void InOrderTraversal(AVLNode node, StringBuilder sb) {
        if (node != null) {
            InOrderTraversal(node.Left, sb);
            sb.append(node.key + "=" + node.value + ", ");
            InOrderTraversal(node.Right, sb);
        }
    }


    @Override
    public boolean containsKey(Object key) {
        return Search((Integer) key, Root) != null;
    }

    AVLNode Search(Integer key, AVLNode node) {
        if (node == null)
            return null;
        int comparison = key.compareTo(node.key);
        if (comparison == 0)
            return node;
        return Search(key, comparison < 0 ? node.Left : node.Right);
    }

    @Override
    public String get(Object key) {
        AVLNode result = Search((Integer) key, Root); // Ищем узел по ключу
        return result == null ? null : result.value; // Возвращаем значение или null
    }

    @Override
    public String put(Integer key, String value) {
        result = new StringBuilder();
        Root = put(Root, key, value);  // Вставляем новый узел
        return result.isEmpty() ? null : result.toString();
    }

    AVLNode put(AVLNode node, Integer key, String value) {
        if (node == null) {
            return new AVLNode(key, value); // Создаем новый узел
        }
        int comparison = key.compareTo(node.key);
        if (comparison < 0)
            node.Left = put(node.Left, key, value);
        else if (comparison > 0)
            node.Right = put(node.Right, key, value);
        else {
            if (!node.value.equalsIgnoreCase(value)) { // Если значение изменилось
                node.value = value;
                result.append("generate" + key);
            }
        }
        return Balance(node);
    }

    int Height(AVLNode node) {
        return node == null ? 0 : node.Height;
    }

    int BalanceFactor(AVLNode node) {
        return node == null ? 0 : Height(node.Left) - Height(node.Right);
    }

    AVLNode RotateRight(AVLNode node)
    {
        AVLNode newRoot = node.Left; // Новый корень будет левым дочерним узлом
        node.Left = newRoot.Right; // Перемещаем правое поддерево
        newRoot.Right = node;
        node.Height = 1 + Math.max(Height(node.Left), Height(node.Right));
        newRoot.Height = 1 + Math.max(Height(newRoot.Left), Height(newRoot.Right));
        return newRoot;
    }

    AVLNode RotateLeft(AVLNode node)
    {
        AVLNode newRoot = node.Right;
        node.Right = newRoot.Left;
        newRoot.Left = node;
        node.Height = 1 + Math.max(Height(node.Left), Height(node.Right));
        newRoot.Height = 1 + Math.max(Height(newRoot.Left), Height(newRoot.Right));
        return newRoot;
    }

    AVLNode Balance(AVLNode node)
    {
        if (node == null)
            return node;

        node.Height = 1 + Math.max(Height(node.Left), Height(node.Right));
        int balanceFactor = BalanceFactor(node); // Вычисляем баланс

        if (balanceFactor > 1) // Левый дисбаланс
        {
            if (BalanceFactor(node.Left) < 0)
                node.Left = RotateLeft(node.Left); // Двойное вращение
            return RotateRight(node);
        }

        if (balanceFactor < -1) // Правый дисбаланс
        {
            if (BalanceFactor(node.Right) > 0)
                node.Right = RotateRight(node.Right);
            return RotateLeft(node);
        }

        return node;
    }

    @Override
    public String remove(Object key) {
        result = new StringBuilder();
        Root = remove(Root, (Integer) key);
        return result.isEmpty() ? null : result.toString();
    }

    AVLNode remove(AVLNode node, Integer key)
    {
        if (node == null)
            return node;
        int comparison = key.compareTo(node.key);
        if (comparison < 0)
            node.Left = remove(node.Left, key);
        else if (comparison > 0)
            node.Right = remove(node.Right, key);
        else
        {
            result.append("generate" + key);
            if (node.Left == null)
                return node.Right;
            if (node.Right == null)
                return node.Left;

            AVLNode minNode = minValueNode(node.Right);
            node.value = minNode.value;
            node.Right = RemoveMinNode(node.Right);
        }

        return Balance(node);
    }

    AVLNode RemoveMinNode(AVLNode node)
    {
        if (node.Left == null)
            return node.Right;

        node.Left = RemoveMinNode(node.Left);
        return Balance(node);
    }

    AVLNode minValueNode(AVLNode node) {
        return node.Left == null ? node : minValueNode(node.Left);
    }
    @Override
    public void clear() {
        Root = clear(Root);
    }

    AVLNode clear(AVLNode node) {
        if (node == null)
            return null;
        node.Left = clear(node.Left);
        node.Right = clear(node.Right);
        return null;
    }

    //////////////////////////////////////////////////////

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