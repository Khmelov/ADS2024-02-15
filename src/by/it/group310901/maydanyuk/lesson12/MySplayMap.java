package by.it.group310901.maydanyuk.lesson12;

import java.util.*;
// Класс MySplayMap реализует интерфейс NavigableMap, используя дерево сплей для хранения данных
public class MySplayMap implements NavigableMap<Integer, String> {

    SpTNode Root;
    // Внутренний класс для представления узла в дереве сплей
    class SpTNode {
        Integer key;
        String value;
        SpTNode left, right, parent;

        SpTNode(Integer key, String value) {
            this.key = key;
            this.value = value;
        }
    }
    // Метод для преобразования дерева в строку
    @Override
    public String toString() {
        if (Root == null) return "{}";; // Если дерево пустое, возвращаем пустые скобки
        StringBuilder sb = new StringBuilder().append("{");
        inOrderTraversal(Root, sb); // Обход дерева в порядке возрастания
        sb.replace(sb.length() - 2, sb.length(), "");
        sb.append("}");
        return sb.toString();
    }
    // Вспомогательный метод для обхода дерева в порядке возрастания
    void inOrderTraversal(SpTNode node, StringBuilder sb) {
        if (node != null) {
            inOrderTraversal(node.left, sb);// Рекурсивно обходим левое поддерево
            sb.append(node.key + "=" + node.value + ", ");// Добавляем ключ и значение узла
            inOrderTraversal(node.right, sb);// Рекурсивно обходим правое поддерево
        }
    }
    // Метод для получения размера дерева
    @Override
    public int size() {return size(Root);}
    // Вспомогательный метод для вычисления размера дерева
    int size(SpTNode node) {
        if (node == null) {return 0;}
        return 1 + size(node.left) + size(node.right);
    }

    @Override
    public boolean isEmpty() {return Root == null;}

    @Override
    public boolean containsKey(Object key) {return get(key) != null;}
    // Метод для проверки наличия ключа в дереве
    @Override
    public boolean containsValue(Object value) {return containsValue(Root, value.toString());}
    // Вспомогательный метод для проверки наличия значения в дереве
    boolean containsValue(SpTNode node, String value) {
        if (node == null) {return false;}
        if (node.value.equals(value)) {return true;}
        // Проверяем значение в левом и правом поддеревьях
        return containsValue(node.left, value) || containsValue(node.right, value);
    }
    // Метод для получения значения по ключу
    @Override
    public String get(Object key) {
        SpTNode found = SearchKey((Integer) key, Root);// Ищем узел по ключу
        if (found != null) {
            Root = splay(Root, found.key);// Применяем сплей для перемещения узла к корню
            return found.value;
        }
        return null;
    }
    // Вспомогательный метод для поиска узла по ключу
    SpTNode SearchKey(Integer key, SpTNode node) {
        if (node == null) return null;
        int comparison = key.compareTo(node.key);
        if (comparison == 0) return node;// Ключ найден
        return SearchKey(key, comparison < 0 ? node.left : node.right);
    }

    // Метод для получения первого (минимального) ключа в дереве
    @Override
    public Integer firstKey() {
        if (Root == null) return null;
        SpTNode node = Root;
        while (node.left != null) {node = node.left;}
        return node.key;
    }

    @Override
    public Integer lastKey() {
        if (Root == null) return null;
        SpTNode node = Root;
        while (node.right != null) {node = node.right;}
        return node.key;
    }
    // Метод для получения подкарты, начиная с указанного ключа
    @Override
    public SortedMap<Integer, String> tailMap(Integer fromKey) {
        MySplayMap subMap = new MySplayMap();
        tailMap(Root, fromKey, subMap);// Рекурсивно заполняем поддерево
        return subMap;
    }
    // Вспомогательный метод для заполнения подкарты
    void tailMap(SpTNode node, Integer fromKey, MySplayMap subMap) {
        if (node == null) {return;}
        if (node.key.compareTo(fromKey) >= 0) {
            subMap.put(node.key, node.value);// Добавляем узел в поддерево
            tailMap(node.left, fromKey, subMap);// Рекурсивно обходим левое поддерево
        }
        tailMap(node.right, fromKey, subMap);//правое
    }// Метод для правого вращения узла
    SpTNode rotateRight(SpTNode node) {
        SpTNode leftChild = node.left;
        node.left = leftChild.right;
        if (leftChild.right != null) {leftChild.right.parent = node;}
        leftChild.right = node;
        leftChild.parent = node.parent;
        node.parent = leftChild;
        return leftChild;
    }
    // Метод для левого вращения узла
    SpTNode rotateLeft(SpTNode node) {
        // Правый узел становится новым корнем
        // Правый поддерево нового корня становится правым поддеревом текущего узла
        SpTNode rightChild = node.right;
        node.right = rightChild.left;
        if (rightChild.left != null) {rightChild.left.parent = node;}
        rightChild.left = node;
        rightChild.parent = node.parent;// Обновляем родителей
        node.parent = rightChild;
        return rightChild;
    }
    // Метод для очистки дерева
    @Override
    public void clear() {Root = clear(Root);}

    SpTNode clear(SpTNode node) {
        if (node == null) return null;
        node.left = clear(node.left);
        node.right = clear(node.right);
        return null;// Возвращаем null, чтобы удалить все узлы
    }

    // Метод для вставки нового узла
      @Override
    public String put(Integer key, String value) {
        if (Root == null) {
            Root = new SpTNode(key, value);
            return null;
        }
        Root = splay(Root, key); // Применяем сплей для перемещения узла к корню
        int cmp = key.compareTo(Root.key);
        if (cmp == 0) {
            String oldValue = Root.value;
            Root.value = value;
            return oldValue; // Возвращаем старое значение, если ключ уже существует
        } else if (cmp < 0) {
            SpTNode newNode = new SpTNode(key, value);
            newNode.left = Root.left; // Левое поддерево нового узла
            newNode.right = Root; // Текущий корень становится правым поддеревом нового узла
            newNode.right.parent = newNode;
            Root.left = null; // Удаляем ссылку на левое поддерево из старого корня
            Root = newNode; // Новый узел становится корнем
        } else {
            SpTNode newNode = new SpTNode(key, value);
            newNode.right = Root.right; // Правое поддерево нового узла
            newNode.left = Root; // Текущий корень становится левым поддеревом нового узла
            newNode.left.parent = newNode;
            Root.right = null; // Удаляем ссылку на правое поддерево из старого корня
            Root = newNode; // Новый узел становится корнем
        }
        return null;
    }

    @Override
    public String remove(Object key) {
        if (Root == null) { return null; }
        Root = splay(Root, (Integer) key); // Применяем сплей для перемещения узла с ключом к корню
        int cmp = ((Integer) key).compareTo(Root.key);
        if (cmp != 0) { return null; }
        String removedValue = Root.value;
        if (Root.left == null) {
            Root = Root.right; // Если нет левого поддерева, новое корнем становится правое поддерево
            if (Root != null) { Root.parent = null; }
        } else {
            SpTNode newRoot = Root.right;
            newRoot = splay(newRoot, (Integer) key); // Применяем сплей к правому поддереву
            newRoot.left = Root.left; // Левое поддерево старого корня становится левым поддеревом нового корня
            newRoot.left.parent = newRoot;
            Root = newRoot; // Устанавливаем новый корень
        }
        return removedValue; // Возвращаем значение удаленного узла
    }

    // Вспомогательный метод для применения сплея
    SpTNode splay(SpTNode node, Integer key) {
        if (node == null) { return null; }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            if (node.left == null) { return node; }
            int cmp2 = key.compareTo(node.left.key);
            if (cmp2 < 0) {
                node.left.left = splay(node.left.left, key); // Правое вращение и сплей в левом поддереве
                node = rotateRight(node);
            } else if (cmp2 > 0) {
                node.left.right = splay(node.left.right, key); // Левое вращение и сплей в правом поддереве левого поддерева
                if (node.left.right != null) { node.left = rotateLeft(node.left); }
            }
            if (node.left == null) { return node; }
            else { return rotateRight(node); }
        } else if (cmp > 0) {
            if (node.right == null) { return node; }
            int cmp2 = key.compareTo(node.right.key);
            if (cmp2 < 0) {
                node.right.left = splay(node.right.left, key); // Правое вращение и сплей в левом поддереве правого поддерева
                if (node.right.left != null) { node.right = rotateRight(node.right); }
            } else if (cmp2 > 0) {
                node.right.right = splay(node.right.right, key); // Левое вращение и сплей в правом поддереве правого поддерева
                node = rotateLeft(node);
            }
            if (node.right == null) { return node; }
            else { return rotateLeft(node); }
        } else { return node; }
    }

    @Override
    public Integer lowerKey(Integer key) {
        if (Root == null) return null;
        SpTNode node = lowerKeyNode(key, Root);
        if (node != null) { return node.key; }
        return null;
    }

    // Вспомогательный метод для поиска ключа, меньшего заданного
    SpTNode lowerKeyNode(Integer key, SpTNode node) {
        if (node == null) return null;
        int comparison = key.compareTo(node.key);
        if (comparison <= 0) return lowerKeyNode(key, node.left);
        SpTNode rightResult = lowerKeyNode(key, node.right);
        if (rightResult != null) return rightResult;
        return node;
    }

    @Override
    public Integer floorKey(Integer key) {
        if (Root == null) return null;
        SpTNode node = floorKeyNode(key, Root);
        if (node != null) { return node.key; }
        return null;
    }

    // Вспомогательный метод для поиска ключа, равного или меньшего заданного
    SpTNode floorKeyNode(Integer key, SpTNode node) {
        if (node == null) return null;
        int comparison = key.compareTo(node.key);
        if (comparison == 0) return node;
        if (comparison < 0) return floorKeyNode(key, node.left);
        SpTNode rightResult = floorKeyNode(key, node.right);
        if (rightResult != null) return rightResult;
        return node;
    }

    @Override
    public Integer ceilingKey(Integer key) {
        if (Root == null) return null;
        SpTNode node = ceilingKeyNode(key, Root);
        if (node != null) { return node.key; }
        return null;
    }

    // Вспомогательный метод для поиска ключа, равного или большего заданного
    SpTNode ceilingKeyNode(Integer key, SpTNode node) {
        if (node == null) return null;
        int comparison = key.compareTo(node.key);
        if (comparison == 0) return node;
        if (comparison > 0) return ceilingKeyNode(key, node.right);
        SpTNode leftResult = ceilingKeyNode(key, node.left);
        if (leftResult != null) return leftResult;
        return node;
    }

    @Override
    public Integer higherKey(Integer key) {
        if (Root == null) return null;
        SpTNode node = higherKeyNode(key, Root);
        if (node != null) { return node.key; }
        return null;
    }

    // Вспомогательный метод для поиска ключа, большего заданного
    SpTNode higherKeyNode(Integer key, SpTNode node) {
        if (node == null) return null;
        int comparison = key.compareTo(node.key);
        if (comparison >= 0) return higherKeyNode(key, node.right);
        SpTNode leftResult = higherKeyNode(key, node.left);
        if (leftResult != null) return leftResult;
        return node;
    }

    @Override
    public SortedMap<Integer, String> headMap(Integer toKey) {
        MySplayMap subMap = new MySplayMap();
        headMap(Root, toKey, subMap); // Рекурсивно заполняем поддерево
        return subMap;
    }

    // Вспомогательный метод для заполнения подкарты
    void headMap(SpTNode node, Integer toKey, MySplayMap subMap) {
        if (node == null) { return; }
        if (node.key.compareTo(toKey) < 0) {
            subMap.put(node.key, node.value); // Добавляем узел в поддерево
            headMap(node.right, toKey, subMap); // Рекурсивно обходим правое поддерево
        }
        headMap(node.left, toKey, subMap); // Рекурсивно обходим левое поддерево
    }

        @Override
    public Entry<Integer, String> ceilingEntry(Integer key) {return null;}

    @Override
    public Entry<Integer, String> higherEntry(Integer key) {return null;}

    @Override
    public Entry<Integer, String> firstEntry() {return null;}

    @Override
    public Entry<Integer, String> lastEntry() {return null;}

    @Override
    public Entry<Integer, String> pollFirstEntry() {return null;}

    @Override
    public Entry<Integer, String> pollLastEntry() {return null;}

    @Override
    public SortedMap<Integer, String> subMap(Integer fromKey, Integer toKey) {return null;}

    @Override
    public NavigableMap<Integer, String> descendingMap() {return null;}

    @Override
    public NavigableSet<Integer> navigableKeySet() {return null;}

    @Override
    public NavigableSet<Integer> descendingKeySet() {return null;}

    @Override
    public NavigableMap<Integer, String> subMap(Integer fromKey, boolean fromInclusive, Integer toKey, boolean toInclusive) {return null;}

    @Override
    public NavigableMap<Integer, String> headMap(Integer toKey, boolean inclusive) {return null;}

    @Override
    public NavigableMap<Integer, String> tailMap(Integer fromKey, boolean inclusive) {return null;}

    @Override
    public Comparator<? super Integer> comparator() {return null;}

    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {}

    @Override
    public Set<Integer> keySet() {return null;}

    @Override
    public Collection<String> values() {return null;}

    @Override
    public Set<Entry<Integer, String>> entrySet() {return null;}

    @Override
    public Entry<Integer, String> lowerEntry(Integer key) {return null;}

    @Override
    public Entry<Integer, String> floorEntry(Integer key) {return null;}
}
