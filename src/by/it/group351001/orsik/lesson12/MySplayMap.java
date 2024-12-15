package by.it.group351001.orsik.lesson12;

import java.util.*;

@SuppressWarnings("unchecked")
public class MySplayMap implements NavigableMap<Integer, String> {

    private Node root; // Корень дерева
    private int size; // Размер (количество элементов) в дереве

    @Override
    public String toString() {
        // Преобразует дерево в строку для удобного отображения
        if (isEmpty()) {
            return "{}"; // Если дерево пустое, вернуть "{}"
        }
        StringBuilder sb = new StringBuilder("{");
        inorderTraversal(sb, root); // Выполнить симметричный обход дерева
        return sb.delete(sb.length() - 2, sb.length()).append("}").toString(); // Удалить лишнюю запятую и пробел
    }

    private void inorderTraversal(StringBuilder sb, Node node) {
        // Симметричный обход дерева для добавления ключей и значений в строку
        if (node != null) {
            inorderTraversal(sb, node.left); // Обход левого поддерева
            sb.append(String.format("%s=%s, ", node.key, node.value)); // Добавление текущего узла
            inorderTraversal(sb, node.right); // Обход правого поддерева
        }
    }

    @Override
    public String put(Integer key, String value) {
        // Вставляет пару ключ-значение в дерево
        Node node = search(key); // Поиск узла по ключу
        if (node == null) {
            size++; // Увеличиваем размер, если узел не найден
            put((int) key, value); // Вставка нового узла
            return null; // Возврат null, если вставка прошла успешно
        } else {
            // Если ключ уже существует, обновляем его значение
            var oldValue = node.value; // Сохраняем предыдущее значение
            node.value = value; // Обновляем значение
            return oldValue; // Возврат предыдущего значения
        }
    }

    private void put(int key, String value) {
        // Вставляет новый узел в дерево
        if (root == null) {
            root = new Node(key, value, null); // Если дерево пустое, создаем корень
            return;
        }
        Node currentNode = root; // Начинаем с корня
        Node parent = null; // Родитель текущего узла

        // Поиск места для вставки нового узла
        while (currentNode != null) {
            parent = currentNode; // Запоминаем родителя
            if (key < currentNode.key) {
                currentNode = currentNode.left; // Переход в левое поддерево
            } else if (key > currentNode.key) {
                currentNode = currentNode.right; // Переход в правое поддерево
            }
        }

        // Создание нового узла
        Node newNode = new Node(key, value, parent);
        if (key < parent.key) {
            parent.left = newNode; // Установка нового узла как левого ребенка
        } else {
            parent.right = newNode; // Установка нового узла как правого ребенка
        }
        splay(newNode); // Сопоставляем новый узел
    }

    @Override
    public String remove(Object key) {
        // Удаляет узел по ключу и возвращает его значение
        Node node = search(key); // Поиск узла
        if (node != null) {
            size--; // Уменьшаем размер
            remove(node); // Удаление узла
        }
        return node == null ? null : node.value; // Возврат значения или null, если узел не найден
    }

    private void remove(Node node) {
        // Удаляет узел из дерева
        if (node.left == null) { // Если у узла нет левого ребенка
            root = node.right; // Устанавливаем правое поддерево как новое корневое
            if (root != null) {
                root.parent = null; // Устанавливаем родителя для нового корня
            }
        } else {
            // Если у узла есть левое поддерево
            Node rightSubtree = node.right; // Сохраняем правое поддерево
            root = node.left; // Устанавливаем левое поддерево как новое корневое
            root.parent = null; // Устанавливаем родителя для нового корня
            Node maxLeft = findMax(root); // Находим узел с максимальным ключом в левом поддереве
            splay(maxLeft); // Сопоставляем узел с максимальным ключом
            root.right = rightSubtree; // Устанавливаем правое поддерево
            if (rightSubtree != null) {
                rightSubtree.parent = root; // Устанавливаем родителя для правого поддерева
            }
        }
    }

    private Node findMax(Node node) {
        // Находит узел с максимальным ключом в поддереве
        while (node.right != null) {
            node = node.right; // Переход к правому ребенку
        }
        return node; // Возврат узла с максимальным ключом
    }

    @Override
    public String get(Object key) {
        // Возвращает значение по ключу
        Node result = search(key); // Поиск узла
        return result == null ? null : result.value; // Возврат значения или null
    }

    @Override
    public boolean containsKey(Object key) {
        // Проверяет наличие ключа в дереве
        return search(key) != null; // Возврат true, если узел найден
    }

    @Override
    public boolean containsValue(Object value) {
        // Проверяет наличие значения в дереве
        return containsValue(root, value); // Рекурсивный поиск значения
    }

    private boolean containsValue(Node node, Object value) {
        // Рекурсивный метод для поиска значения в поддереве
        if (node == null) {
            return false; // Возврат false, если узел не найден
        }
        if (value.equals(node.value)) {
            splay(node); // Сопоставляем узел, если значение найдено
            return true; // Возврат true, если значение найдено
        }
        // Рекурсивный поиск в поддеревьях
        return containsValue(node.left, value) || containsValue(node.right, value);
    }

    @Override
    public int size() {
        // Возвращает размер дерева
        return size; // Возврат количества элементов
    }

    @Override
    public void clear() {
        // Очищает дерево
        root = null; // Устанавливаем корень в null
        size = 0; // Сбрасываем размер
    }

    @Override
    public boolean isEmpty() {
        // Проверяет, пусто ли дерево
        return (size == 0); // Возврат true, если размер равен 0
    }

    @Override
    public NavigableMap<Integer, String> headMap(Integer toKey, boolean inclusive) {
        // Возвращает поддерево с ключами, меньшими чем toKey
        return null; // Метод не реализован
    }

    @Override
    public NavigableMap<Integer, String> tailMap(Integer fromKey, boolean inclusive) {
        // Возвращает поддерево с ключами, большими или равными fromKey
        return null; // Метод не реализован
    }

    @Override
    public Integer firstKey() {
        // Возвращает первый ключ в дереве
        if (root == null) {
            return null; // Если дерево пустое
        }
        Node node = root;
        while (node.left != null) {
            node = node.left; // Переход к самому левому узлу
        }
        splay(node); // Сопоставляем узел
        return node.key; // Возврат первого ключа
    }

    @Override
    public Integer lastKey() {
        // Возвращает последний ключ в дереве
        if (root == null) {
            return null; // Если дерево пустое
        }
        Node node = root;
        while (node.right != null) {
            node = node.right; // Переход к самому правому узлу
        }
        splay(node); // Сопоставляем узел
        return node.key; // Возврат последнего ключа
    }

    @Override
    public Integer lowerKey(Integer key) {
        // Возвращает наименьший ключ, меньший чем указанный
        Node lower = findLower(root, key); // Поиск узла с меньшим ключом
        if (lower == null) {
            return null; // Если узел не найден
        }
        splay(lower); // Сопоставляем узел
        return lower.key; // Возврат ключа
    }

    private Node findLower(Node node, Integer key) {
        // Находит узел с наименьшим ключом, меньшим чем указанный
        Node lower = null;
        while (node != null) {
            if (key > node.key) {
                lower = node; // Запоминаем узел как наименьший
                node = node.right; // Переход к правому поддереву
            } else {
                node = node.left; // Переход к левому поддереву
            }
        }
        return lower; // Возврат узла с наименьшим ключом
    }

    @Override
    public Integer floorKey(Integer key) {
        // Возвращает наибольший ключ, меньший или равный указанному
        if (search(key) != null) {
            return key; // Если ключ существует, возвращаем его
        }
        return lowerKey(key); // Иначе возвращаем наименьший ключ
    }

    @Override
    public Integer ceilingKey(Integer key) {
        // Возвращает наименьший ключ, больший или равный указанному
        if (search(key) != null) {
            return key; // Если ключ существует, возвращаем его
        }
        return higherKey(key); // Иначе возвращаем наименьший ключ
    }

    @Override
    public Integer higherKey(Integer key) {
        // Возвращает наименьший ключ, больший чем указанный
        Node higher = findHigher(root, key); // Поиск узла с большим ключом
        if (higher == null) {
            return null; // Если узел не найден
        }
        splay(higher); // Сопоставляем узел
        return higher.key; // Возврат ключа
    }

    private Node findHigher(Node node, int key) {
        // Находит узел с наименьшим ключом, большим чем указанный
        Node higher = null;
        while (node != null) {
            if (key < node.key) {
                higher = node; // Запоминаем узел как наименьший
                node = node.left; // Переход к левому поддереву
            } else {
                node = node.right; // Переход к правому поддереву
            }
        }
        return higher; // Возврат узла с наименьшим ключом
    }

    private Node search(Object key) {
        // Ищет узел по ключу
        Node node = root;
        Comparable<? super Integer> comparable = (Comparable<? super Integer>) key;

        while (node != null) {
            if (comparable.compareTo(node.key) < 0) {
                node = node.left; // Переход в левое поддерево
            } else if (comparable.compareTo(node.key) > 0) {
                node = node.right; // Переход в правое поддерево
            } else {
                splay(node); // Сопоставляем узел
                return node; // Возврат найденного узла
            }
        }
        return null; // Если узел не найден, возвращаем null
    }

    private void splay(Node node) {
        // Сопоставляет узел, перемещая его к корню
        while (node.parent != null) {
            Node parent = node.parent; // Устанавливаем родителя
            Node grandparent = parent.parent; // Устанавливаем дедушку

            // Выполняем повороты в зависимости от положения узла
            if (node == parent.left) {
                if (grandparent == null) {
                    rightRotate(parent); // Поворот вправо, если родитель корень
                } else {
                    if (parent == grandparent.left) {
                        rightRotate(grandparent); // Двойной поворот вправо
                        rightRotate(parent);
                    } else {
                        rightRotate(parent); // Поворот вправо
                        leftRotate(grandparent); // Поворот влево
                    }
                }
            } else {
                if (grandparent == null) {
                    leftRotate(parent); // Поворот влево, если родитель корень
                } else {
                    if (parent == grandparent.right) {
                        leftRotate(grandparent); // Двойной поворот влево
                        leftRotate(parent);
                    } else {
                        leftRotate(parent); // Поворот влево
                        rightRotate(grandparent); // Поворот вправо
                    }
                }
            }
        }
    }

    private void rightRotate(Node node) {
        // Выполняет поворот вправо
        Node left = node.left; // Левый ребенок
        node.left = left.right; // Перемещение правого поддерева

        if (left.right != null) {
            left.right.parent = node; // Установка родителя для нового узла
        }
        left.parent = node.parent; // Установка родителя для левого ребенка

        if (node.parent == null) {
            root = left; // Если текущий узел корень, обновляем корень
        } else if (node == node.parent.right) {
            node.parent.right = left; // Если текущий узел - правый ребенок
        } else {
            node.parent.left = left; // Если текущий узел - левый ребенок
        }

        left.right = node; // Текущий узел становится правым ребенком левого
        node.parent = left; // Установка родителя для текущего узла
    }

    private void leftRotate(Node node) {
        // Выполняет поворот влево
        Node right = node.right; // Правый ребенок
        node.right = right.left; // Перемещение левого поддерева

        if (right.left != null) {
            right.left.parent = node; // Установка родителя для нового узла
        }
        right.parent = node.parent; // Установка родителя для правого ребенка

        if (node.parent == null) {
            root = right; // Если текущий узел корень, обновляем корень
        } else if (node == node.parent.left) {
            node.parent.left = right; // Если текущий узел - левый ребенок
        } else {
            node.parent.right = right; // Если текущий узел - правый ребенок
        }

        right.left = node; // Текущий узел становится левым ребенком правого
        node.parent = right; // Установка родителя для текущего узла
    }

    // Методы ниже не реализованы

    //–––––

    @Override
    public Entry<Integer, String> lowerEntry(Integer key) {
        return null;
    }

    @Override
    public Entry<Integer, String> floorEntry(Integer key) {
        return null;
    }

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
    public Comparator<? super Integer> comparator() {
        return null;
    }

    @Override
    public SortedMap<Integer, String> subMap(Integer fromKey, Integer toKey) {
        return null;
    }

    @Override
    public SortedMap<Integer, String> headMap(Integer toKey) {

        if (toKey == null)
            throw new NullPointerException("The key cannot be null");

        SortedMap<Integer, String> sortedMap = new MySplayMap();
        setToKey(root, toKey, sortedMap);

        return sortedMap;
    }

    private void setToKey(Node node, int toKey, SortedMap<Integer, String> sortedMap) {
        // Заполнение поддерева узлами, ключи которых меньше toKey
        if (node == null)
            return; // Если узел null, ничего не делаем

        setToKey(node.left, toKey, sortedMap); // Рекурсивный обход влево

        if (node.key < toKey) {
            sortedMap.put(node.key, node.value); // Добавление узла в поддерево
            setToKey(node.right, toKey, sortedMap); // Рекурсивный обход вправо
        }
    }

    @Override
    public SortedMap<Integer, String> tailMap(Integer fromKey) {

        if (fromKey == null)
            throw new NullPointerException("The key cannot be null");

        SortedMap<Integer, String> sortedMap = new MySplayMap();
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

        int key;
        String value;
        Node left;
        Node right;
        Node parent;

        public Node(int key, String value, Node parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }
    }
}