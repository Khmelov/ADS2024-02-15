package by.it.group351001.sosnovski.lesson12;

import java.util.*;

// Класс MySplayMap реализует структуру данных "Splay Tree" для хранения пар ключ-значение,
// где ключи являются целыми числами, а значения - строками. Дерево самоподсматривающееся,
// что означает, что при каждом доступе к элементу этот элемент перемещается в корень,
// обеспечивая таким образом более быстрый доступ к часто используемым элементам.
public class MySplayMap implements NavigableMap<Integer, String> {

    // Внутренний класс Node представляет узел дерева, содержащий ключ, значение и ссылки
    // на родительский, левый и правый дочерние узлы.
    private class Node{
        Integer key; // Ключ узла
        String value; // Значение узла
        Node left; // Левый дочерний узел
        Node right; // Правый дочерний узел
        Node parent; // Родительский узел

        // Конструктор узла
        Node (Integer k, String v, Node p){
            key = k;
            value = v;
            left = null;
            right = null;
            parent = p;
        }
    }

    private int size = 0; // Размер карты
    private Node root; // Корень дерева

    // Метод для выполнения левого вращения вокруг узла
    private void rotateLeft(Node node){
        Node child = node.right; // Сохраняем правого ребенка узла
        node.right = child.left; // Перемещаем левое поддерево дочернего узла к текущему узлу
        if (child.left != null) {
            child.left.parent = node; // Обновляем родителя левого поддерева
        }
        if (node.parent == null){ // Если узел является корнем
            root = child; // Новый корень - это правый ребенок
        } else {
            if (node.parent.left == node){ // Если текущий узел - левый ребенок
                node.parent.left = child; // Обновляем ссылку на левого ребенка
            } else {
                node.parent.right = child; // Иначе обновляем ссылку на правого ребенка
            }
        }
        child.parent = node.parent; // Устанавливаем нового родителя
        node.parent = child; // Устанавливаем текущий узел в качестве левого ребенка
        child.left = node; // Устанавливаем текущий узел как левого ребенка дочернего узла
    }

    // Метод для выполнения правого вращения вокруг узла
    private void rotateRight(Node node){
        Node child = node.left; // Сохраняем левого ребенка узла
        node.left = child.right; // Перемещаем правое поддерево дочернего узла к текущему узлу
        if (child.right != null) {
            child.right.parent = node; // Обновляем родителя правого поддерева
        }
        if (node.parent == null){ // Если узел является корнем
            root = child; // Новый корень - это левый ребенок
        } else {
            if (node.parent.left == node){ // Если текущий узел - левый ребенок
                node.parent.left = child; // Обновляем ссылку на левого ребенка
            } else {
                node.parent.right = child; // Иначе обновляем ссылку на правого ребенка
            }
        }
        child.parent = node.parent; // Устанавливаем нового родителя
        node.parent = child; // Устанавливаем текущий узел в качестве правого ребенка
        child.right = node; // Устанавливаем текущий узел как правого ребенка дочернего узла
    }

    // Метод splay выполняет операцию сдвига узла в корень дерева
    private void splay(Node node){
        while (node.parent != null){ // Пока узел не является корнем
            Node parent = node.parent; // Сохраняем родителя узла
            Node grandad = node.parent.parent; // Сохраняем деда узла

            // Если узел - левый ребенок
            if (node == parent.left) {
                if (grandad == null) // Если нет деда, выполняем правое вращение
                    rotateRight(parent);
                else if (parent == grandad.left) { // Если родитель - левый ребенок деда
                    rotateRight(grandad); // Двойное вращение вправо
                    rotateRight(parent);
                } else {
                    rotateRight(parent); // Вращение вправо
                    rotateLeft(grandad); // Вращение влево
                }
            } else { // Если узел - правый ребенок
                if (grandad == null) // Если нет деда, выполняем левое вращение
                    rotateLeft(parent);
                else if (parent == grandad.right) { // Если родитель - правый ребенок деда
                    rotateLeft(grandad); // Двойное вращение влево
                    rotateLeft(parent);
                } else {
                    rotateLeft(parent); // Вращение влево
                    rotateRight(grandad); // Вращение вправо
                }
            }
        }
    }

    // Метод для получения строкового представления дерева
    private String elemToString(Node node){
        if (node == null){
            return ""; // Если узел пустой, возвращаем пустую строку
        }
        // Рекурсивно обходим левое поддерево, затем добавляем текущий узел и затем правое поддерево
        return elemToString(node.left) + node.key + "=" + node.value + ", " + elemToString(node.right);
    }

    // Переопределяем метод toString для представления карты в виде строки
    @Override
    public String toString(){
        String elems = elemToString(root); // Получаем строковое представление элементов
        int l = elems.length(); // Длина строки
        String result = "";
        if (elems.length() != 0){
            result = elems.substring(0, l - 2); // Убираем запятую в конце
        }
        return "{" + result + "}"; // Форматируем строку для вывода
    }

    // Методы для работы с элементами карты
    @Override
    public Entry<Integer, String> lowerEntry(Integer key) {
        return null; // Не реализовано
    }

    @Override
    public Integer lowerKey(Integer key) {
        Integer res = null; // Переменная для хранения результата
        Node temp = root; // Начинаем с корня
        while (temp != null){
            if (key > temp.key){ // Если ключ больше текущего узла
                res = temp.key; // Сохраняем ключ текущего узла
                temp = temp.right; // Переходим к правому поддереву
            } else {
                temp = temp.left; // Иначе переходим к левому поддереву
            }
        }
        return res; // Возвращаем найденный ключ
    }

    @Override
    public Entry<Integer, String> floorEntry(Integer key) {
        return null; // Не реализовано
    }

    @Override
    public Integer floorKey(Integer key) {
        Node temp = find(key); // Пытаемся найти узел с данным ключом
        if (temp == null){
            return lowerKey(key); // Если узел не найден, возвращаем ближайший меньший ключ
        } else {
            return temp.key; // Если узел найден, возвращаем его ключ
        }
    }

    @Override
    public Entry<Integer, String> ceilingEntry(Integer key) {
        return null; // Не реализовано
    }

    @Override
    public Integer ceilingKey(Integer key) {
        Node temp = find(key); // Пытаемся найти узел с данным ключом
        if (temp == null){
            return higherKey(key); // Если узел не найден, возвращаем ближайший больший ключ
        } else {
            return temp.key; // Если узел найден, возвращаем его ключ
        }
    }

    @Override
    public Entry<Integer, String> higherEntry(Integer key) {
        return null; // Не реализовано
    }

    @Override
    public Integer higherKey(Integer key) {
        Integer answer = null; // Переменная для хранения результата
        Node Iterator = root; // Начинаем с корня
        while (Iterator != null){
            if (key >= Iterator.key){ // Если ключ больше или равен текущему узлу
                Iterator = Iterator.right; // Переходим к правому поддереву
            } else {
                answer = Iterator.key; // Сохраняем ключ текущего узла
                Iterator = Iterator.left; // Переходим к левому поддереву
            }
        }
        return answer; // Возвращаем найденный ключ
    }

    @Override
    public Entry<Integer, String> firstEntry() {
        return null; // Не реализовано
    }

    @Override
    public Entry<Integer, String> lastEntry() {
        return null; // Не реализовано
    }

    @Override
    public Entry<Integer, String> pollFirstEntry() {
        return null; // Не реализовано
    }

    @Override
    public Entry<Integer, String> pollLastEntry() {
        return null; // Не реализовано
    }

    @Override
    public NavigableMap<Integer, String> descendingMap() {
        return null; // Не реализовано
    }

    @Override
    public NavigableSet<Integer> navigableKeySet() {
        return null; // Не реализовано
    }

    @Override
    public NavigableSet<Integer> descendingKeySet() {
        return null; // Не реализовано
    }

    @Override
    public NavigableMap<Integer, String> subMap(Integer fromKey, boolean fromInclusive, Integer toKey, boolean toInclusive) {
        return null; // Не реализовано
    }

    @Override
    public NavigableMap<Integer, String> headMap(Integer toKey, boolean inclusive) {
        return null; // Не реализовано
    }

    @Override
    public NavigableMap<Integer, String> tailMap(Integer fromKey, boolean inclusive) {
        return null; // Не реализовано
    }

    @Override
    public Comparator<? super Integer> comparator() {
        return null; // Не реализовано
    }

    @Override
    public SortedMap<Integer, String> subMap(Integer fromKey, Integer toKey) {
        return null; // Не реализовано
    }

    @Override
    public SortedMap<Integer, String> headMap(Integer toKey) {
        MySplayMap bufMap = new MySplayMap(); // Создаем временную карту
        recHeadMap(bufMap, root, toKey); // Заполняем ее данными из текущего дерева
        return bufMap; // Возвращаем временную карту
    }

    // Рекурсивный метод для заполнения карты элементами, меньшими чем заданный ключ
    private void recHeadMap(MySplayMap res, Node node, Integer key){
        if (node == null) {
            return; // Если узел пустой, ничего не делаем
        }
        if (node.key >= key) {
            recHeadMap(res, node.left, key); // Переходим в левое поддерево
            return;
        }
        // Рекурсивно добавляем элементы из обоих поддеревьев
        recHeadMap(res, node.left, key);
        recHeadMap(res, node.right, key);
        res.put(node.key, node.value); // Добавляем текущий узел в результирующую карту
    }

    @Override
    public SortedMap<Integer, String> tailMap(Integer fromKey) {
        MySplayMap bufMap = new MySplayMap(); // Создаем временную карту
        recTailMap(bufMap, root, fromKey); // Заполняем ее данными из текущего дерева
        return bufMap; // Возвращаем временную карту
    }

    // Рекурсивный метод для заполнения карты элементами, большими или равными заданному ключу
    private void recTailMap(MySplayMap res, Node node, Integer key){
        if (node == null) {
            return; // Если узел пустой, ничего не делаем
        }
        if (node.key < key) {
            recTailMap(res, node.right, key); // Переходим в правое поддерево
            return;
        }
        // Рекурсивно добавляем элементы из обоих поддеревьев
        recTailMap(res, node.left, key);
        recTailMap(res, node.right, key);
        res.put(node.key, node.value); // Добавляем текущий узел в результирующую карту
    }

    @Override
    public Integer firstKey() {
        Node temp = root; // Начинаем с корня
        if (temp == null){
            return 0; // Если дерево пустое, возвращаем 0
        }
        while (temp.left != null){ // Переходим к самому левому узлу
            temp = temp.left;
        }
        return temp.key; // Возвращаем ключ самого левого узла
    }

    @Override
    public Integer lastKey() {
        Node temp = root; // Начинаем с корня
        if (temp == null){
            return 0; // Если дерево пустое, возвращаем 0
        }
        while (temp.right != null){ // Переходим к самому правому узлу
            temp = temp.right;
        }
        return temp.key; // Возвращаем ключ самого правого узла
    }

    @Override
    public int size() {
        return size; // Возвращаем размер карты
    }

    @Override
    public boolean isEmpty() {
        return (size == 0); // Проверяем, пустая ли карта
    }

    @Override
    public boolean containsKey(Object key) {
        return get(key) != null; // Проверяем наличие ключа в карте
    }

    @Override
    public boolean containsValue(Object value) {
        return checkValues(root, value); // Проверяем наличие значения в карте
    }

    // Рекурсивный метод для проверки наличия значения в дереве
    private boolean checkValues(Node node, Object value){
        if (node == null){
            return false; // Если узел пустой, возвращаем false
        }
        if (node.value.equals(value)){
            return true; // Если значение найдено, возвращаем true
        }
        // Проверяем наличие значения в обоих поддеревьях
        return checkValues(node.left, value) || checkValues(node.right, value);
    }

    @Override
    public String get(Object key) {
        Node temp = root; // Начинаем с корня
        while (temp != null){
            if (temp.key.equals(key)){ // Если ключ совпадает с текущим узлом
                return temp.value; // Возвращаем значение
            } else if ((Integer)key > temp.key){ // Если ключ больше текущего узла
                temp = temp.right; // Переходим к правому поддереву
            } else {
                temp = temp.left; // Иначе переходим к левому поддереву
            }
        }
        return null; // Если узел не найден, возвращаем null
    }

    @Override
    public String put(Integer key, String value) {
        Node result = find(key); // Пытаемся найти узел с данным ключом
        if (result == null){ // Если узел не найден
            size++; // Увеличиваем размер карты
            insert(key, value); // Вставляем новый узел
            return null; // Возвращаем null, так как старое значение отсутствует
        }
        String old = result.value; // Сохраняем старое значение
        result.value = value; // Обновляем значение
        return old; // Возвращаем старое значение
    }

    // Метод для вставки нового узла в дерево
    private void insert(Integer key, String value){
        if (root == null){ // Если дерево пустое
            root = new Node(key,value, null); // Создаем новый корень
            return;
        }
        Node Iter = root; // Начинаем с корня
        Node parent = null; // Родитель узла
        while (Iter != null) {
            parent = Iter; // Сохраняем текущий узел как родителя
            if (key < Iter.key){ // Если ключ меньше текущего узла
                Iter = Iter.left; // Переходим в левое поддерево
            } else {
                Iter = Iter.right; // Иначе переходим в правое поддерево
            }
        }

        Node ToInsert = new Node (key, value, null); // Создаем новый узел
        ToInsert.parent = parent; // Устанавливаем родителя
        if (key < parent.key){ // Если ключ меньше ключа родителя
            parent.left = ToInsert; // Устанавливаем узел как левого ребенка
        } else {
            parent.right = ToInsert; // Иначе устанавливаем узел как правого ребенка
        }
        splay(ToInsert); // Перемещаем новый узел в корень
    }

    // Метод для поиска узла с данным ключом
    private Node find(Integer key){
        Node Iter = root; // Начинаем с корня
        while (Iter != null) {
            if (key < Iter.key)
                Iter = Iter.left; // Переходим в левое поддерево
            else if (key > Iter.key)
                Iter = Iter.right; // Переходим в правое поддерево
            else {
                splay(Iter); // Перемещаем найденный узел в корень
                return Iter; // Возвращаем найденный узел
            }
        }
        return null; // Если узел не найден, возвращаем null
    }

    @Override
    public String remove(Object key) {
        Node res = find((Integer) key); // Пытаемся найти узел с данным ключом
        if (res != null){ // Если узел найден
            size--; // Уменьшаем размер карты
            rmNode(res); // Удаляем узел
            return res.value; // Возвращаем значение удаленного узла
        }
        return null; // Если узел не найден, возвращаем null
    }

    // Метод для удаления узла из дерева
    private void rmNode (Node node){
        if (node.left == null) { // Если у узла нет левого поддерева
            if (node.right != null){
                root = node.right; // Если есть правое поддерево, поднимаем его
            } else {
                if (node == root){
                    root = null; // Если узел корень, устанавливаем корень равным null
                } else if (node.parent.left == node){
                    node.parent.left = null; // Если узел - левый ребенок, удаляем его
                } else if (node.parent.right == node){
                    node.parent.right = null; // Если узел - правый ребенок, удаляем его
                }
            }
            if (root != null)
                root.parent = null; // Если дерево не пустое, сбрасываем родителя корня
        } else {
            Node right = node.right; // Сохраняем правое поддерево
            root = node.left; // Устанавливаем левое поддерево как новое корневое
            root.parent = null; // Сбрасываем родителя корня
            Node temp = node.left; // Начинаем с левого поддерева
            while (temp.right != null){ // Находим максимальный узел в левом поддереве
                temp = temp.right;
            }
            splay(temp); // Перемещаем его в корень
            root.right = right; // Устанавливаем правое поддерево
            if (right != null)
                right.parent = root; // Устанавливаем родителя правого поддерева
        }
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {
        // Не реализовано
    }

    @Override
    public void clear() {
        root = null; // Устанавливаем корень равным null
        size = 0; // Уменьшаем размер карты
    }

    @Override
    public Set<Integer> keySet() {
        return null; // Не реализовано
    }

    @Override
    public Collection<String> values() {
        return null; // Не реализовано
    }

    @Override
    public Set<Entry<Integer, String>> entrySet() {
        return null; // Не реализовано
    }
}
