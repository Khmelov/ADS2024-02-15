package by.it.group351001.ushakou.lesson12;

import java.util.*;

public class MySplayMap implements NavigableMap<Integer, String> {

    Node Root;  // Корень дерева

    // Внутренний класс для представления узла дерева
    class Node {
        Integer key;   // Ключ
        String value;  // Значение
        Node left, right, parent;  // Левый, правый потомки и родитель

        Node(Integer key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    // Переопределение метода toString для представления карты как строки
    @Override
    public String toString() {
        if (Root == null)
            return "{}";  // Если дерево пустое, возвращаем пустое множество
        StringBuilder sb = new StringBuilder().append("{");
        inOrderTraversal(Root, sb);  // Запуск обхода дерева
        sb.replace(sb.length() - 2, sb.length(), "");  // Удаление последней запятой
        sb.append("}");
        return sb.toString();
    }

    // Метод для обхода дерева в порядке "ин-ордера"
    void inOrderTraversal(Node node, StringBuilder sb) {
        if (node != null) {
            inOrderTraversal(node.left, sb);  // Обход левого поддерева
            sb.append(node.key + "=" + node.value + ", ");  // Добавление текущего узла в строку
            inOrderTraversal(node.right, sb);  // Обход правого поддерева
        }
    }

    // Переопределение метода size для подсчета количества элементов в дереве
    @Override
    public int size() {
        return size(Root);  // Запуск метода для подсчета с корня
    }

    // Рекурсивный метод для подсчета количества элементов в дереве
    int size(Node node) {
        if (node == null) {
            return 0;  // Если узел пустой, размер 0
        }
        return 1 + size(node.left) + size(node.right);  // Рекурсивный подсчет размера
    }

    // Переопределение метода isEmpty для проверки на пустоту дерева
    @Override
    public boolean isEmpty() {
        return Root == null;  // Если корень пустой, дерево пустое
    }

    // Переопределение метода containsKey для проверки наличия ключа
    @Override
    public boolean containsKey(Object key) {
        return get(key) != null;  // Если get возвращает значение, значит ключ существует
    }

    // Переопределение метода containsValue для проверки наличия значения
    @Override
    public boolean containsValue(Object value) {
        return containsValue(Root, value.toString());  // Рекурсивная проверка по дереву
    }

    // Рекурсивный метод для проверки наличия значения в дереве
    boolean containsValue(Node node, String value) {
        if (node == null) {
            return false;  // Если узел пустой, значения нет
        }
        if (node.value.equals(value)) {
            return true;  // Если найдено соответствующее значение
        }
        return containsValue(node.left, value) || containsValue(node.right, value);  // Рекурсивный поиск в поддеревьях
    }

    // Переопределение метода get для получения значения по ключу
    @Override
    public String get(Object key) {
        Node found = SearchKey((Integer) key, Root);  // Поиск узла с ключом
        if (found != null) {
            Root = splay(Root, found.key);  // После нахождения выполняем операцию сплайя
            return found.value;  // Возвращаем найденное значение
        }
        return null;  // Если ключ не найден, возвращаем null
    }

    // Рекурсивный метод для поиска узла по ключу
    Node SearchKey(Integer key, Node node) {
        if (node == null)
            return null;  // Если узел пустой, возвращаем null
        int comparison = key.compareTo(node.key);  // Сравнение ключа
        if (comparison == 0)
            return node;  // Если ключ совпал, возвращаем текущий узел

        // Рекурсивный поиск в левой или правой части дерева
        return SearchKey(key, comparison < 0 ? node.left : node.right);
    }

    @Override
    public String put(Integer key, String value) {
        if (Root == null) {  // Если дерево пустое, создаем новый корень
            Root = new Node(key, value);
            return null;  // Возвращаем null, так как старого значения не было
        }

        Root = splay(Root, key);  // Выполняем операцию сплайя, чтобы переместить ключ в корень
        int cmp = key.compareTo(Root.key);  // Сравниваем ключ с корнем
        if (cmp == 0) {  // Если ключ уже существует, обновляем значение
            String oldValue = Root.value;
            Root.value = value;
            return oldValue;  // Возвращаем старое значение
        } else if (cmp < 0) {  // Если ключ меньше текущего (будет вставлен слева)
            Node newNode = new Node(key, value);  // Создаем новый узел
            newNode.left = Root.left;  // Левый потомок нового узла - левый потомок корня
            newNode.right = Root;  // Правый потомок нового узла - корень
            newNode.right.parent = newNode;  // Устанавливаем родителя для старого корня
            Root.left = null;  // Обнуляем левый потомок у корня
            Root = newNode;  // Новый узел становится корнем
        } else {  // Если ключ больше текущего (будет вставлен справа)
            Node newNode = new Node(key, value);  // Создаем новый узел
            newNode.right = Root.right;  // Правый потомок нового узла - правый потомок корня
            newNode.left = Root;  // Левый потомок нового узла - корень
            newNode.left.parent = newNode;  // Устанавливаем родителя для старого корня
            Root.right = null;  // Обнуляем правый потомок у корня
            Root = newNode;  // Новый узел становится корнем
        }
        return null;  // Возвращаем null, так как не было старого значения
    }

    @Override
    public String remove(Object key) {
        if (Root == null) {  // Если дерево пустое, нечего удалять
            return null;
        }

        Root = splay(Root, (Integer) key);  // Выполняем операцию сплайя для перемещения ключа в корень
        int cmp = ((Integer) key).compareTo(Root.key);  // Сравниваем ключ с корнем
        if (cmp != 0) {  // Если ключ не найден, возвращаем null
            return null;
        }

        String removedValue = Root.value;  // Сохраняем значение для возврата

        if (Root.left == null) {  // Если у корня нет левого поддерева
            Root = Root.right;  // Просто переназначаем корень на правое поддерево
            if (Root != null) {
                Root.parent = null;  // Убираем ссылку на родителя
            }
        } else {  // Если у корня есть левое поддерево
            Node newRoot = Root.right;  // Начинаем с правого поддерева корня
            newRoot = splay(newRoot, (Integer) key);  // Выполняем сплай на правое поддерево
            newRoot.left = Root.left;  // Левый потомок нового корня - левое поддерево старого корня
            newRoot.left.parent = newRoot;  // Устанавливаем родителя для левого поддерева
            Root = newRoot;  // Новый корень
        }

        return removedValue;  // Возвращаем удаленное значение
    }

    // Метод splay для перемещения узла с заданным ключом в корень
    Node splay(Node node, Integer key) {
        if (node == null) {  // Если дерево пустое, возвращаем null
            return null;
        }

        int cmp = key.compareTo(node.key);  // Сравниваем ключ с текущим узлом
        if (cmp < 0) {  // Если ключ меньше текущего
            if (node.left == null) {  // Если нет левого поддерева, возвращаем текущий узел
                return node;
            }
            int cmp2 = key.compareTo(node.left.key);  // Сравниваем с левым потомком
            if (cmp2 < 0) {  // Если ключ меньше левого потомка (двойной поворот вправо)
                node.left.left = splay(node.left.left, key);
                node = rotateRight(node);  // Поворот вправо
            } else if (cmp2 > 0) {  // Если ключ больше левого потомка (поворот влево, затем вправо)
                node.left.right = splay(node.left.right, key);
                if (node.left.right != null) {
                    node.left = rotateLeft(node.left);  // Поворот влево
                }
            }
            if (node.left == null) {  // Если нет левого потомка, возвращаем текущий узел
                return node;
            } else {
                return rotateRight(node);  // Поворот вправо
            }
        } else if (cmp > 0) {  // Если ключ больше текущего
            if (node.right == null) {  // Если нет правого поддерева, возвращаем текущий узел
                return node;
            }
            int cmp2 = key.compareTo(node.right.key);  // Сравниваем с правым потомком
            if (cmp2 < 0) {  // Если ключ меньше правого потомка (поворот вправо, затем влево)
                node.right.left = splay(node.right.left, key);
                if (node.right.left != null) {
                    node.right = rotateRight(node.right);  // Поворот вправо
                }
            } else if (cmp2 > 0) {  // Если ключ больше правого потомка (двойной поворот влево)
                node.right.right = splay(node.right.right, key);
                node = rotateLeft(node);  // Поворот влево
            }
            if (node.right == null) {  // Если нет правого потомка, возвращаем текущий узел
                return node;
            } else {
                return rotateLeft(node);  // Поворот влево
            }
        } else {
            return node;  // Если ключ равен текущему, возвращаем узел
        }
    }


    Node rotateRight(Node node) {
        Node leftChild = node.left;  // Сохраняем левый дочерний узел
        node.left = leftChild.right;  // Переносим правого потомка левого узла на место левого потомка текущего узла
        if (leftChild.right != null) {  // Если правый потомок левого узла существует, обновляем его родителя
            leftChild.right.parent = node;
        }
        leftChild.right = node;  // Переносим текущий узел на правую сторону левого узла
        leftChild.parent = node.parent;  // Обновляем родителя для левого узла
        node.parent = leftChild;  // Обновляем родителя для текущего узла
        return leftChild;  // Новый корень после поворота
    }

    Node rotateLeft(Node node) {
        Node rightChild = node.right;  // Сохраняем правый дочерний узел
        node.right = rightChild.left;  // Переносим левого потомка правого узла на место правого потомка текущего узла
        if (rightChild.left != null) {  // Если левый потомок правого узла существует, обновляем его родителя
            rightChild.left.parent = node;
        }
        rightChild.left = node;  // Переносим текущий узел на левую сторону правого узла
        rightChild.parent = node.parent;  // Обновляем родителя для правого узла
        node.parent = rightChild;  // Обновляем родителя для текущего узла
        return rightChild;  // Новый корень после поворота
    }

    @Override
    public void clear() {
        Root = clear(Root);  // Очищаем дерево, начиная с корня
    }

    // Рекурсивный метод для очистки дерева
    Node clear(Node node) {
        if (node == null)  // Если узел пустой, возвращаем null
            return null;
        node.left = clear(node.left);  // Рекурсивно очищаем левое поддерево
        node.right = clear(node.right);  // Рекурсивно очищаем правое поддерево
        return null;  // Возвращаем null, удаляя текущий узел
    }

    @Override
    public Integer lowerKey(Integer key) {
        if (Root == null)  // Если дерево пустое, возвращаем null
            return null;
        Node node = lowerKeyNode(key, Root);  // Ищем узел с ключом, меньшим переданному
        if (node != null) {
            return node.key;  // Возвращаем ключ найденного узла
        }
        return null;  // Если не нашли подходящий узел, возвращаем null
    }

    // Рекурсивный метод для поиска узла с меньшим ключом
    Node lowerKeyNode(Integer key, Node node) {
        if (node == null)  // Если узел пустой, возвращаем null
            return null;
        int comparison = key.compareTo(node.key);  // Сравниваем ключ с текущим узлом
        if (comparison <= 0)  // Если ключ меньше или равен текущему, ищем в левом поддереве
            return lowerKeyNode(key, node.left);
        Node rightResult = lowerKeyNode(key, node.right);  // Ищем в правом поддереве
        if (rightResult != null)  // Если нашли подходящий узел в правом поддереве, возвращаем его
            return rightResult;
        return node;  // Если не нашли, возвращаем текущий узел
    }

    @Override
    public Integer floorKey(Integer key) {
        if (Root == null)  // Если дерево пустое, возвращаем null
            return null;
        Node node = floorKeyNode(key, Root);  // Ищем узел с наибольшим ключом, меньшим или равным переданному
        if (node != null) {
            return node.key;  // Возвращаем ключ найденного узла
        }
        return null;  // Если не нашли подходящий узел, возвращаем null
    }

    // Рекурсивный метод для поиска узла с наибольшим ключом, меньшим или равным переданному
    Node floorKeyNode(Integer key, Node node) {
        if (node == null)  // Если узел пустой, возвращаем null
            return null;
        int comparison = key.compareTo(node.key);  // Сравниваем ключ с текущим узлом
        if (comparison == 0)  // Если ключ равен текущему, возвращаем текущий узел
            return node;
        if (comparison < 0)  // Если ключ меньше текущего, ищем в левом поддереве
            return floorKeyNode(key, node.left);
        Node rightResult = floorKeyNode(key, node.right);  // Ищем в правом поддереве
        if (rightResult != null)  // Если нашли подходящий узел в правом поддереве, возвращаем его
            return rightResult;
        return node;  // Возвращаем текущий узел, если не нашли лучшего
    }

    @Override
    public Integer ceilingKey(Integer key) {
        if (Root == null)  // Если дерево пустое, возвращаем null
            return null;
        Node node = ceilingKeyNode(key, Root);  // Ищем узел с наименьшим ключом, большим или равным переданному
        if (node != null) {
            return node.key;  // Возвращаем ключ найденного узла
        }
        return null;  // Если не нашли подходящий узел, возвращаем null
    }

    // Рекурсивный метод для поиска узла с наименьшим ключом, большим или равным переданному
    Node ceilingKeyNode(Integer key, Node node) {
        if (node == null)  // Если узел пустой, возвращаем null
            return null;
        int comparison = key.compareTo(node.key);  // Сравниваем ключ с текущим узлом
        if (comparison == 0)  // Если ключ равен текущему, возвращаем текущий узел
            return node;
        if (comparison > 0)  // Если ключ больше текущего, ищем в правом поддереве
            return ceilingKeyNode(key, node.right);
        Node leftResult = ceilingKeyNode(key, node.left);  // Ищем в левом поддереве
        if (leftResult != null)  // Если нашли подходящий узел в левом поддереве, возвращаем его
            return leftResult;
        return node;  // Возвращаем текущий узел
    }

    @Override
    public Integer higherKey(Integer key) {
        if (Root == null)  // Если дерево пустое, возвращаем null
            return null;
        Node node = higherKeyNode(key, Root);  // Ищем узел с ключом, большим переданному
        if (node != null) {
            return node.key;  // Возвращаем ключ найденного узла
        }
        return null;  // Если не нашли подходящий узел, возвращаем null
    }


    Node higherKeyNode(Integer key, Node node) {
        if (node == null)  // Если узел пустой, возвращаем null
            return null;
        int comparison = key.compareTo(node.key);  // Сравниваем ключ с текущим узлом
        if (comparison >= 0)  // Если ключ больше или равен текущему, ищем в правом поддереве
            return higherKeyNode(key, node.right);
        Node leftResult = higherKeyNode(key, node.left);  // Ищем в левом поддереве
        if (leftResult != null)  // Если нашли подходящий узел в левом поддереве, возвращаем его
            return leftResult;
        return node;  // Возвращаем текущий узел, если не нашли подходящего
    }

    @Override
    public SortedMap<Integer, String> headMap(Integer toKey) {
        MySplayMap subMap = new MySplayMap();  // Создаем новый подмап
        headMap(Root, toKey, subMap);  // Заполняем подмап
        return subMap;  // Возвращаем подмап
    }

    // Рекурсивный метод для создания подмапа с элементами, меньшими чем toKey
    void headMap(Node node, Integer toKey, MySplayMap subMap) {
        if (node == null)  // Если узел пустой, выходим
            return;

        if (node.key.compareTo(toKey) < 0) {  // Если ключ меньше toKey
            subMap.put(node.key, node.value);  // Добавляем в подмап
            headMap(node.right, toKey, subMap);  // Рекурсивно обрабатываем правое поддерево
        }

        headMap(node.left, toKey, subMap);  // Рекурсивно обрабатываем левое поддерево
    }

    @Override
    public SortedMap<Integer, String> tailMap(Integer fromKey) {
        MySplayMap subMap = new MySplayMap();  // Создаем новый подмап
        tailMap(Root, fromKey, subMap);  // Заполняем подмап
        return subMap;  // Возвращаем подмап
    }

    // Рекурсивный метод для создания подмапа с элементами, большими или равными fromKey
    void tailMap(Node node, Integer fromKey, MySplayMap subMap) {
        if (node == null)  // Если узел пустой, выходим
            return;

        if (node.key.compareTo(fromKey) >= 0) {  // Если ключ больше или равен fromKey
            subMap.put(node.key, node.value);  // Добавляем в подмап
            tailMap(node.left, fromKey, subMap);  // Рекурсивно обрабатываем левое поддерево
        }

        tailMap(node.right, fromKey, subMap);  // Рекурсивно обрабатываем правое поддерево
    }

    @Override
    public Integer firstKey() {
        if (Root == null)  // Если дерево пустое, возвращаем null
            return null;
        Node node = Root;
        while (node.left != null) {  // Ищем самый левый узел
            node = node.left;
        }
        return node.key;  // Возвращаем ключ самого левого узла
    }

    @Override
    public Integer lastKey() {
        if (Root == null)  // Если дерево пустое, возвращаем null
            return null;
        Node node = Root;
        while (node.right != null) {  // Ищем самый правый узел
            node = node.right;
        }
        return node.key;  // Возвращаем ключ самого правого узла
    }


    ///////////////////////////////////////////////////////////////

    @Override
    public Entry<Integer, String> ceilingEntry(Integer key) {
        return null;
    }

    @Override
    public Entry<Integer, String> higherEntry(Integer key) {
        return null;
    }

    @Override
    public Entry<Integer, String> firstEntry() {
        return null;
    }

    @Override
    public Entry<Integer, String> lastEntry() {
        return null;
    }

    @Override
    public Entry<Integer, String> pollFirstEntry() {
        return null;
    }

    @Override
    public Entry<Integer, String> pollLastEntry() {
        return null;
    }

    @Override
    public SortedMap<Integer, String> subMap(Integer fromKey, Integer toKey) {
        return null;
    }

    @Override
    public NavigableMap<Integer, String> descendingMap() {
        return null;
    }

    @Override
    public NavigableSet<Integer> navigableKeySet() {
        return null;
    }

    @Override
    public NavigableSet<Integer> descendingKeySet() {
        return null;
    }

    @Override
    public NavigableMap<Integer, String> subMap(Integer fromKey, boolean fromInclusive, Integer toKey, boolean toInclusive) {
        return null;
    }

    @Override
    public NavigableMap<Integer, String> headMap(Integer toKey, boolean inclusive) {
        return null;
    }

    @Override
    public NavigableMap<Integer, String> tailMap(Integer fromKey, boolean inclusive) {
        return null;
    }

    @Override
    public Comparator<? super Integer> comparator() {
        return null;
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

    @Override
    public Entry<Integer, String> lowerEntry(Integer key) {
        return null;
    }

    @Override
    public Entry<Integer, String> floorEntry(Integer key) {
        return null;
    }
}
