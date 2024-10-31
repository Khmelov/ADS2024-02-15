package by.it.group310902.belskiy.lesson12;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class MyAvlMap implements Map<Integer, String> {

    class Node {
        int key;
        String value;
        int Height;
        Node Left, Right;

        Node(int key, String value) {
            this.key = key;
            this.value = value;
            this.Height = 1;
        }
    }

    Node Root;
    StringBuilder result;

    @Override
    public int size() {
        return size(Root);
    }

    private int size(Node node) {
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
        InOrderTraversal(Root, sb);
        sb.replace(sb.length() - 2, sb.length(), "}");
        return sb.toString();
    }

    private void InOrderTraversal(Node node, StringBuilder sb) {
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

    Node Search(Integer key, Node node) {
        if (node == null)
            return null;
        int comparison = key.compareTo(node.key);
        if (comparison == 0)
            return node;

        return Search(key, comparison < 0 ? node.Left : node.Right);
    }

    @Override
    public String get(Object key) {

        Node result = Search((Integer) key, Root);

        return result == null ? null : result.value;
    }

    @Override
    public String put(Integer key, String value) {
        result = new StringBuilder();
        Root = put(Root, key, value);
        return result.isEmpty() ? null : result.toString();
    }

    Node put(Node node, Integer key, String value) {
        if (node == null) {
            return new Node(key, value);
        }
        int comparison = key.compareTo(node.key);
        if (comparison < 0)
            node.Left = put(node.Left, key, value);
        else if (comparison > 0)
            node.Right = put(node.Right, key, value);
        else {
            if (!node.value.equalsIgnoreCase(value)) {
                node.value = value;
                result.append("generate" + key);
            }
        }
        return Balance(node);
    }

    int Height(Node node) {
        return node == null ? 0 : node.Height;
    }

    int BalanceFactor(Node node) {
        return node == null ? 0 : Height(node.Left) - Height(node.Right);
    }

    Node RotateRight(Node node)
    {
        Node newRoot = node.Left;
        node.Left = newRoot.Right;
        newRoot.Right = node;
        node.Height = 1 + Math.max(Height(node.Left), Height(node.Right));
        newRoot.Height = 1 + Math.max(Height(newRoot.Left), Height(newRoot.Right));
        return newRoot;
    }

    Node RotateLeft(Node node)
    {
        Node newRoot = node.Right;
        node.Right = newRoot.Left;
        newRoot.Left = node;
        node.Height = 1 + Math.max(Height(node.Left), Height(node.Right));
        newRoot.Height = 1 + Math.max(Height(newRoot.Left), Height(newRoot.Right));
        return newRoot;
    }

    Node Balance(Node node)
    {
        if (node == null)
            return node;

        node.Height = 1 + Math.max(Height(node.Left), Height(node.Right));
        int balanceFactor = BalanceFactor(node);

        if (balanceFactor > 1)
        {
            if (BalanceFactor(node.Left) < 0)
                node.Left = RotateLeft(node.Left);
            return RotateRight(node);
        }

        if (balanceFactor < -1)
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

    Node remove(Node node, Integer key)
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

            Node minNode = minValueNode(node.Right);
            node.value = minNode.value;
            node.Right = RemoveMinNode(node.Right);
        }

        return Balance(node);
    }

    Node RemoveMinNode(Node node)
    {
        if (node.Left == null)
            return node.Right;

        node.Left = RemoveMinNode(node.Left);
        return Balance(node);
    }

    Node minValueNode(Node node) {
        return node.Left == null ? node : minValueNode(node.Left);
    }
    @Override
    public void clear() {
        Root = clear(Root);
    }

    Node clear(Node node) {
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
