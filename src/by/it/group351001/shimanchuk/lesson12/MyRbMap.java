package by.it.group351001.shimanchuk.lesson12;

// Класс MyRbMap реализует структуру данных "Красно-черное дерево" и интерфейс SortedMap
// Он хранит пары ключ-значение, где ключи имеют тип Integer, а значения - String.
// Дерево поддерживает автоматическую балансировку, что обеспечивает логарифмическое время для операций поиска, вставки и удаления.

import java.util.*;

public class MyRbMap implements SortedMap<Integer, String> {

    // Перечисление цветов для узлов дерева
    private enum color {
        BLACK, RED; // Два цвета: черный и красный
    }

    // Внутренний класс Node представляет собой узел дерева
    private class Node {
        Integer key; // Ключ узла
        String value; // Значение узла
        color color; // Цвет узла
        Node left; // Левый дочерний узел
        Node right; // Правый дочерний узел
        Node parent; // Родительский узел
        boolean deleted; // Флаг, показывающий, был ли узел удален

        // Конструктор для создания нового узла
        Node(Integer k, String s, Node p) {
            key = k;
            value = s;
            left = null;
            right = null;
            parent = p;
            color = MyRbMap.color.RED; // Новый узел всегда красный
            deleted = false; // Новый узел не удален
        }
    }

    private Node root; // Корень дерева
    private int size; // Размер (количество узлов) дерева

    // Метод для правого вращения узла
    private Node rotateRight(Node node) {
        Node left = node.left; // Сохраняем ссылку на левое поддерево
        left.parent = node.parent; // Устанавливаем родителя для левого узла
        Node swapNode = node.left.right; // Сохраняем правое поддерево левого узла
        left.right = node; // Поворачиваем узел вправо
        left.right.parent = left; // Устанавливаем родителя для повернутого узла
        node.left = swapNode; // Присоединяем правое поддерево левого узла к узлу
        node.left.parent = node; // Устанавливаем родителя для правого поддерева

        return left; // Возвращаем новый корень (левый узел)
    }

    // Метод для левого вращения узла
    private Node rotateLeft(Node node) {
        Node right = node.right; // Сохраняем ссылку на правое поддерево
        right.parent = node.parent; // Устанавливаем родителя для правого узла
        Node swapNode = node.right.left; // Сохраняем левое поддерево правого узла
        right.left = node; // Поворачиваем узел влево
        right.left.parent = right; // Устанавливаем родителя для повернутого узла
        node.right = swapNode; // Присоединяем левое поддерево правого узла к узлу
        node.right.parent = node; // Устанавливаем родителя для левого поддерева

        return right; // Возвращаем новый корень (правый узел)
    }

    // Метод для коррекции дерева после вставки или удаления узла
    private void correction(Node node) {
        if (node.parent == null) { // Если узел является корнем
            node.color = color.BLACK; // Корень всегда черный
        } else if ((node.parent.color == color.BLACK)) {
            // Если родитель черный, ничего делать не нужно
        } else if ((node.parent.parent.left.color == color.RED) && (node.parent.parent.right.color == color.RED)) {
            // Если оба ребенка родителя красные, то выполняем поворот
            node.parent.parent.left.color = color.BLACK; // Делаем левое поддерево черным
            node.parent.parent.right.color = color.BLACK; // Делаем правое поддерево черным
            node.parent.parent.color = color.RED; // Делаем родителя красным
            correction(node.parent.parent); // Рекурсивно корректируем родителя
        } else {
            // Здесь обрабатываем случаи, когда один из детей красный
            if (node.parent.left == node) { // Если узел - левый сын
                if (node.parent.parent.left == node.parent) { // Если родитель тоже левый
                    node = node.parent; // Обновляем узел
                    rotateRight(node); // Поворачиваем вправо
                    node.color = color.BLACK; // Делаем узел черным
                    node.right.color = color.RED; // Делаем правое поддерево красным
                } else {
                    rotateRight(node); // Поворачиваем вправо
                    rotateLeft(node); // Затем поворачиваем влево
                    node.color = color.BLACK; // Делаем узел черным
                    node.right.color = color.RED; // Делаем правое поддерево красным
                }
            } else { // Если узел - правый сын
                if (node.parent.parent.right == node.parent) { // Если родитель тоже правый
                    node = node.parent; // Обновляем узел
                    rotateLeft(node); // Поворачиваем влево
                    node.color = color.BLACK; // Делаем узел черным
                    node.left.color = color.RED; // Делаем левое поддерево красным
                } else {
                    rotateLeft(node); // Поворачиваем влево
                    rotateRight(node); // Затем поворачиваем вправо
                    node.color = color.BLACK; // Делаем узел черным
                    node.right.color = color.RED; // Делаем правое поддерево красным
                }
            }
        }
    }

    // Реализация методов интерфейса SortedMap

    @Override
    public Comparator<? super Integer> comparator() {
        return null; // По умолчанию, у нас нет компаратора
    }

    @Override
    public SortedMap<Integer, String> subMap(Integer fromKey, Integer toKey) {
        return null; // Метод не реализован
    }

    @Override
    public SortedMap<Integer, String> headMap(Integer toKey) {
        MyRbMap bufMap = new MyRbMap(); // Создаем новый экземпляр для хранения результата
        recHeadMap(bufMap, root, toKey); // Рекурсивно заполняем результат
        return bufMap; // Возвращаем полученное множество
    }

    // Вспомогательный метод для заполнения headMap
    private void recHeadMap(MyRbMap res, Node node, Integer key) {
        if (node == null) { // Если узел пустой, выходим
            return;
        }
        if (node.key >= key) { // Если ключ узла больше или равен искомому
            recHeadMap(res, node.left, key); // Рекурсивно проверяем левое поддерево
            return;
        }
        recHeadMap(res, node.left, key); // Проверяем левое поддерево
        recHeadMap(res, node.right, key); // Проверяем правое поддерево
        if (node.deleted == false) // Если узел не удален, добавляем в результат
            res.put(node.key, node.value);
    }

    @Override
    public SortedMap<Integer, String> tailMap(Integer fromKey) {
        MyRbMap bufMap = new MyRbMap(); // Создаем новый экземпляр для хранения результата
        recTailMap(bufMap, root, fromKey); // Рекурсивно заполняем результат
        return bufMap; // Возвращаем полученное множество
    }

    // Вспомогательный метод для заполнения tailMap
    private void recTailMap(MyRbMap res, Node node, Integer key) {
        if (node == null) { // Если узел пустой, выходим
            return;
        }
        if (node.key < key) { // Если ключ узла меньше искомого
            recTailMap(res, node.right, key); // Проверяем правое поддерево
            return;
        }
        recTailMap(res, node.left, key); // Проверяем левое поддерево
        recTailMap(res, node.right, key); // Проверяем правое поддерево
        if (node.deleted == false) // Если узел не удален, добавляем в результат
            res.put(node.key, node.value);
    }

    // Метод для получения первого ключа в дереве
    @Override
    public Integer firstKey() {
        Node temp = root; // Начинаем с корня
        if (temp == null) { // Если дерево пустое
            return 0; // Возвращаем 0 (или можно бросить исключение)
        }
        while (temp.left != null) { // Ищем самый левый узел
            temp = temp.left; // Переходим к левому дочернему узлу
        }
        return temp.key; // Возвращаем ключ
    }

    // Метод для получения последнего ключа в дереве
    @Override
    public Integer lastKey() {
        Node temp = root; // Начинаем с корня
        if (temp == null) { // Если дерево пустое
            return 0; // Возвращаем 0 (или можно бросить исключение)
        }
        while (temp.right != null) { // Ищем самый правый узел
            temp = temp.right; // Переходим к правому дочернему узлу
        }
        return temp.key; // Возвращаем ключ
    }

    // Метод для получения размера дерева
    @Override
    public int size() {
        return size; // Возвращаем размер
    }

    // Метод для проверки, пустое ли дерево
    @Override
    public boolean isEmpty() {
        return (size == 0); // Проверяем размер
    }

    // Метод для проверки наличия ключа в дереве
    @Override
    public boolean containsKey(Object key) {
        return get((Integer) key) != null; // Возвращаем результат поиска ключа
    }

    // Метод для проверки наличия значения в дереве
    @Override
    public boolean containsValue(Object value) {
        return checkValues(root, value); // Проверяем значения рекурсивно
    }

    // Вспомогательный метод для проверки наличия значения
    private boolean checkValues(Node node, Object value) {
        if (node == null) { // Если узел пустой, выходим
            return false; // Значение не найдено
        }
        if ((node.value.equals(value)) && (node.deleted == false)) { // Если значение совпадает и узел не удален
            return true; // Значение найдено
        }
        // Проверяем левое и правое поддеревья
        return checkValues(node.left, value) || checkValues(node.right, value);
    }

    // Метод для получения значения по ключу
    @Override
    public String get(Object key) {
        Node temp = root; // Начинаем с корня
        while (temp != null) { // Пока не достигнем конца
            if ((temp.key.equals(key)) && (temp.deleted == false)) { // Если ключ найден и узел не удален
                return temp.value; // Возвращаем значение
            } else if ((Integer) key > temp.key) { // Если ключ больше, идем вправо
                temp = temp.right;
            } else { // Идем влево
                temp = temp.left;
            }
        }
        return null; // Ключ не найден
    }

    // Метод для вставки новой пары ключ-значение в дерево
    @Override
    public String put(Integer key, String value) {
        String oldVal = get(key); // Сохраняем предыдущее значение
        root = add(root, key, value, null); // Добавляем новый узел
        if (root.color == color.RED) { // Если корень красный, меняем его на черный
            root.color = color.BLACK;
        }
        size += oldVal == null ? 1 : 0; // Увеличиваем размер, если значение новое
        return oldVal; // Возвращаем старое значение
    }

    // Вспомогательный метод для добавления узла
    private String elemToString(Node node) {
        if (node == null) { // Если узел пустой
            return ""; // Возвращаем пустую строку
        }
        if (node.deleted) { // Если узел удален, игнорируем его
            return elemToString(node.left) + elemToString(node.right);
        }
        // Формируем строку из левого поддерева, текущего узла и правого поддерева
        return elemToString(node.left) + node.key + "=" + node.value + ", " + elemToString(node.right);
    }

    // Переопределяем метод toString для представления дерева в виде строки
    @Override
    public String toString() {
        String elems = elemToString(root); // Получаем строку с элементами
        int l = elems.length(); // Длина строки
        String res = ""; // Результирующая строка
        if (elems.length() != 0) {
            res = elems.substring(0, l - 2); // Убираем последнюю запятую и пробел
        }
        return "{" + res + "}"; // Возвращаем результат в фигурных скобках
    }

    // Метод для добавления узла в дерево
    private Node add(Node node, Integer key, String value, Node parent) {
        if (node == null) { // Если узел пустой, создаем новый
            return new Node(key, value, parent);
        }
        if (key > node.key) { // Если ключ больше, идем вправо
            node.right = add(node.right, key, value, parent);
        } else if (key < node.key) { // Если ключ меньше, идем влево
            node.left = add(node.left, key, value, parent);
        } else { // Если ключ совпадает, обновляем значение
            node.value = value;
            return node;
        }
        correction(node); // Корректируем дерево после добавления

        return node; // Возвращаем узел
    }

    // Метод для удаления узла по ключу
    @Override
    public String remove(Object key) {
        String RemovedValue = get((Integer) key); // Получаем значение для удаления
        Node temp = root; // Начинаем с корня
        if (RemovedValue != null) { // Если значение существует
            while (temp != null) { // Пока не достигнем конца
                if ((temp.key.equals(key)) && (temp.deleted == false)) { // Если узел найден и не удален
                    temp.deleted = true; // Удаляем узел
                    temp = null; // Прерываем цикл
                } else if ((Integer) key > temp.key) { // Если ключ больше, идем вправо
                    temp = temp.right;
                } else if ((Integer) key < temp.key) { // Если ключ меньше, идем влево
                    temp = temp.left;
                }
            }
            size--; // Уменьшаем размер
        }
        return RemovedValue; // Возвращаем удаленное значение
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {
        // Метод не реализован
    }

    @Override
    public void clear() {
        root = null; // Очищаем корень
        size = 0; // Устанавливаем размер в 0
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
}
