package by.it.group310902.yoshchyk.lesson12;

import java.util.*;
//Работает на основе красно-черного дерева-самобалансирующегося бинарного дерева поиска.
//Позволяет хранить пары ключ-значение, где ключи сортируются в порядке возрастания

//Красно-чёрное дерево — самобалансирующихся двоичных деревьев поиска,  позвол быстро выполнять операции дерева поиска:
// добавление, удаление и поиск узла. Сбалансированность достигается за счёт введения дополнительного атрибута узла дерева — «цвета»..

public class MyRbMap implements SortedMap<Integer, String> {

    private int size = 0; // Хранит количество элементов в дереве
    private MyNode root;   // Корень дерева

    // Внутренний класс, представляющий узел дерева
    static private class MyNode {
        Integer key; // Ключ узла
        String value; // Значение узла
        Boolean color; // Цвет узла (красный = false, черный = true)
        MyNode left, right, parent; // Указатели на левого и правого ребенка и родителя

        // Конструктор узла
        MyNode(Integer key, String value, MyNode parent) {
            this.key = key;
            this.value = value;
            this.color = false; // Новый узел всегда красный
            this.right = null;
            this.left = null;
            this.parent = parent;
        }

        // Метод для получения деда узла
        MyNode grandfather() {
            if (this != null && this.parent != null)
                return this.parent.parent;
            else
                return null;
        }

        // Метод для получения дяди узла
        MyNode uncle() {
            MyNode g = this.grandfather();
            if (g == null)
                return null;
            return (this.parent == g.left) ? g.right : g.left;
        }

        // Метод для получения брата узла
        MyNode sibling() {
            return (this == this.parent.right) ? this.parent.left : this.parent.right;
        }
    }

    // Рекурсивный метод для добавления узлов в строку для метода toString
    private void addtostring(MyNode parent, StringBuilder str) {
        if (parent.left != null)
            addtostring(parent.left, str);
        str.append(parent.key).append("=").append(parent.value).append(", ");
        if (parent.right != null)
            addtostring(parent.right, str);
    }

    // Переопределение метода toString для отображения карты в виде строки
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (root != null) {
            addtostring(root, sb);
            sb.delete(sb.length() - 2, sb.length()); // Удаление последней запятой
        }
        sb.append("}");
        return sb.toString();
    }

    // Вставляет пару ключ-значение в карту и восстанавливает свойства дерева
    @Override
    public String put(Integer key, String value) {
        if (root == null) {
            root = new MyNode(key, value, null); // Создание корня
            insertcase1(root); // Обработка случая 1 для вставки
            size++;
            return null;
        }
        MyNode x = root;
        MyNode parent = null;

        // Поиск места для вставки
        while (x != null) {
            if (x.key.equals(key)) {
                String oldvalue = x.value; // Сохранение старого значения
                x.value = value; // Обновление значения
                return oldvalue; // Возвращение старого значения
            }
            parent = x;
            if (x.key.compareTo(key) > 0)
                x = x.left;
            else
                x = x.right;
        }
        // Создание нового узла и вставка
        x = new MyNode(key, value, parent);
        if (parent.key.compareTo(key) > 0)
            parent.left = x;
        else
            parent.right = x;

        // Восстановление свойств дерева после вставки
        insertcase1(x);
        size++;
        return null; // Возвращение null, если ранее значение не существовало
    }

    // Поворот вправо для восстановления баланса после вставки или удаления
    private void rotateright(MyNode node) {
        MyNode nextnode = node.left;
        nextnode.parent = node.parent;
        if (node.parent != null) {
            if (node.parent.left == node)
                node.parent.left = nextnode;
            else
                node.parent.right = nextnode;
        } else
            root = nextnode; // Обновление корня

        node.parent = nextnode;
        node.left = nextnode.right;
        if (nextnode.right != null)
            nextnode.right.parent = node;
        nextnode.right = node; // Вращение
    }

    // Поворот влево для восстановления баланса после вставки или удаления
    private void rotateleft(MyNode node) {
        MyNode nextnode = node.right;
        nextnode.parent = node.parent;
        if (node.parent != null) {
            if (node.parent.left == node)
                node.parent.left = nextnode;
            else
                node.parent.right = nextnode;
        } else
            root = nextnode; // Обновление корня

        node.parent = nextnode;
        node.right = nextnode.left;
        if (nextnode.left != null)
            nextnode.left.parent = node;
        nextnode.left = node; // Вращение
    }

    // Обработка случая 1 после вставки для восстановления свойств дерева
    private void insertcase1(MyNode node) {
        if (node.parent == null)
            node.color = true; // Корень всегда черный
        else
            insertcase2(node); // Переход к следующему случаю
    }

    // Обработка случая 2
    private void insertcase2(MyNode node) {
        if (!node.parent.color) // Если родитель красный
            insertcase3(node); // Переход к следующему случаю
    }

    // Обработка случая 3
    private void insertcase3(MyNode node) {
        MyNode u = node.uncle(); // Получаем дядю
        if (u != null && !u.color) { // Если дядя красный
            node.parent.color = true; // Родитель черный
            u.color = true; // Дядя черный
            MyNode g = node.grandfather(); // Дед
            g.color = false; // Дед красный
            insertcase1(g); // Продолжаем с дедом
        } else {
            insertcase4(node); // Переход к следующему случаю
        }
    }

    // Обработка случая 4
    private void insertcase4(MyNode node) {
        MyNode g = node.grandfather(); // Получаем деда
        if (node.parent.right == node && node.parent == g.left) {
            rotateleft(node.parent); // Поворот влево
            node = node.left; // Обновление узла
        } else if (node.parent.left == node && node.parent == g.right) {
            rotateright(node.parent); // Поворот вправо
            node = node.right; // Обновление узла
        }
        insertcase5(node); // Переход к следующему случаю
    }

    // Обработка случая 5
    private void insertcase5(MyNode node) {
        MyNode g = node.grandfather(); // Получаем деда
        node.parent.color = true; // Родитель черный
        g.color = false; // Дед красный
        if (node == node.parent.left && node.parent == g.left)
            rotateright(g); // Поворот вправо
        else
            rotateleft(g); // Поворот влево
    }

    // Получение значения по ключу
    @Override
    public String get(Object key) {
        MyNode x = root;
        while (x != null) {
            if (x.key.equals(key))
                return x.value; // Возвращаем значение
            x = (x.key.compareTo((Integer) key) > 0) ? x.left : x.right; // Поиск
        }
        return null; // Если ключ не найден
    }

    // Обработка случая 1 при удалении узла
    private void deletecase1(MyNode node) {
        if (node.parent != null)
            deletecase2(node); // Переход к следующему случаю
    }

    // Обработка случая 2
    private void deletecase2(MyNode node) {
        MyNode s = node.sibling(); // Получаем брата
        if (!s.color) { // Если брат красный
            node.parent.color = false; // Родитель черный
            s.color = true; // Брат черный
            if (node == node.parent.left)
                rotateleft(node); // Поворот влево
            else
                rotateright(node); // Поворот вправо
        }
        deletecase3(node); // Переход к следующему случаю
    }

    // Обработка случая 3
    private void deletecase3(MyNode node) {
        MyNode s = node.sibling(); // Получаем брата
        if (node.parent.color && s.color && s.left.color && s.right.color) {
            s.color = false; // Брат красный
            deletecase1(node.parent); // Продолжаем с родителем
        } else {
            deletecase4(node); // Переход к следующему случаю
        }
    }

    // Обработка случая 4
    private void deletecase4(MyNode node) {
        MyNode s = node.sibling(); // Получаем брата
        if (!node.parent.color && s.color && s.left.color && s.right.color) {
            s.color = false; // Брат красный
            node.parent.color = true; // Родитель черный
        } else {
            deletecase5(node); // Переход к следующему случаю
        }
    }

    // Обработка случая 5
    private void deletecase5(MyNode node) {
        MyNode s = node.sibling(); // Получаем брата
        if (s.color) { // Если брат красный
            if (node == node.parent.left && s.right.color && !s.left.color) {
                s.color = false; // Брат красный
                s.left.color = true; // Левый сын черный
                rotateright(s); // Поворот вправо
            } else if (node == node.parent.right && !s.right.color && s.left.color) {
                s.color = false; // Брат красный
                s.right.color = true; // Правый сын черный
                rotateleft(s); // Поворот влево
            }
        }
        deletecase6(node); // Переход к следующему случаю
    }

    // Обработка случая 6
    private void deletecase6(MyNode node) {
        MyNode s = node.sibling(); // Получаем брата
        s.color = node.parent.color; // Брат принимает цвет родителя
        node.parent.color = true; // Родитель черный
        if (node == node.parent.left) {
            s.right.color = true; // Правый сын черный
            rotateleft(node.parent); // Поворот влево
        } else {
            s.left.color = true; // Левый сын черный
            rotateright(node.parent); // Поворот вправо
        }
    }

    // Находит минимальный узел в поддереве
    private MyNode findmin(MyNode node) {
        if (node.left == null)
            return node; // Минимальный узел найден
        else
            return findmin(node.left); // Продолжаем искать
    }

    // Заменяет дочерний узел
    private void replacechild(MyNode node, MyNode child) {
        child.parent = node.parent; // Устанавливаем родителя
        if (node.parent != null) {
            if (node == node.parent.right)
                node.parent.right = child;
            else
                node.parent.left = child;
        } else
            root = child; // Обновление корня
    }

    // Удаляет узел с одним дочерним узлом
    private void deleteonechild(MyNode node) {
        MyNode child = (node.right != null) ? node.right : node.left; // Выбираем дочерний узел
        replacechild(node, child); // Замена узла
        if (node.color) {
            if (!child.color)
                child.color = true; // Если удаляемый узел черный, делаем дочерний черным
            else
                deletecase1(child); // Восстановление свойств дерева
        }
        node.key = null; // Очистка узла
        node.value = null;
    }

    // Удаляет узел
    private void deletenode(MyNode node) {
        if (node.right != null && node.left != null) { // Если у узла есть оба ребенка
            MyNode min = findmin(node.right); // Находим минимальный узел правого поддерева
            node.value = min.value; // Копируем значение
            node.key = min.key; // Копируем ключ
            deletenode(min); // Удаляем минимальный узел
            } else if (node.right != null ^ node.left != null) { // Если есть только один ребенок
            deleteonechild(node); // Удаляем узел с одним дочерним узлом
        } else { // Если узел является листом
            if (node.parent != null) {
                if (node == node.parent.left)
                    node.parent.left = null; // Удаляем узел
                else
                    node.parent.right = null; // Удаляем узел
                if (node.color)
                    insertcase1(node.parent); // Восстанавливаем свойства дерева
            } else {
                root = null; // Дерево стало пустым
            }
        }
    }

    // Удаляет значение по ключу
    @Override
    public String remove(Object key) {
        MyNode x = root;
        while (x != null) {
            if (x.key.equals(key)) {
                String result = x.value; // Сохраняем значение для возврата
                size--; // Уменьшаем размер
                deletenode(x); // Удаляем узел
                return result; // Возвращаем удаленное значение
            }
            x = (x.key.compareTo((Integer) key) > 0) ? x.left : x.right; // Поиск
        }
        return null; // Если ключ не найден
    }

    // Проверяет наличие ключа в дереве
    @Override
    public boolean containsKey(Object key) {
        return get(key) != null; // Возвращает true, если ключ найден
    }

    // Проверяет наличие значения в дереве
    private boolean nodecontains(Object value, MyNode node) {
        if (node == null)
            return false; // Если узел пустой
        return node.value.equals(value) || nodecontains(value, node.right) || nodecontains(value, node.left); // Поиск
    }

    // Проверяет наличие значения в дереве
    @Override
    public boolean containsValue(Object value) {
        return nodecontains(value, root); // Возвращает true, если значение найдено
    }

    // Возвращает количество элементов в дереве
    @Override
    public int size() {
        return size; // Возвращает размер
    }

    // Рекурсивно очищает дерево
    private MyNode eraseNode(MyNode node) {
        if (node != null) {
            node.right = eraseNode(node.right); // Очищаем правое поддерево
            node.left = eraseNode(node.left); // Очищаем левое поддерево
            node.key = null; // Очистка ключа
            node.value = null; // Очистка значения
            node.parent = null; // Очистка родителя
        }
        return null; // Удаление узлов
    }

    // Очищает карту
    @Override
    public void clear() {
        size = 0; // Сбрасываем размер
        root = eraseNode(root); // Очищаем дерево
    }

    // Проверяет, является ли карта пустой
    @Override
    public boolean isEmpty() {
        return size == 0; // Возвращает true, если карта пустая
    }

    // Метод для получения подмножества элементов, меньших заданного ключа
    private void addtoHeadMap(SortedMap<Integer, String> result, MyNode node, Integer toKey) {
        if (node != null) {
            addtoHeadMap(result, node.left, toKey); // Рекурсивный вызов для левого поддерева
            if (node.key.compareTo(toKey) < 0) { // Если ключ меньше toKey
                result.put(node.key, node.value); // Добавляем в результат
                addtoHeadMap(result, node.right, toKey); // Рекурсивный вызов для правого поддерева
            }
        }
    }

    // Получает подмножество элементов, меньших заданного ключа
    @Override
    public SortedMap<Integer, String> headMap(Integer toKey) {
        if (toKey == null)
            throw new NullPointerException(); // Исключение, если toKey null
        if (root == null)
            return null; // Если дерево пустое, возвращаем null
        TreeMap<Integer, String> result = new TreeMap<>(); // Используем TreeMap для результата
        addtoHeadMap(result, root, toKey); // Заполняем результат
        return result; // Возвращаем подмножество
    }

    // Метод для получения подмножества элементов, больших или равных заданному ключу
    private void addtoTailMap(SortedMap<Integer, String> result, MyNode node, Integer fromKey) {
        if (node != null) {
            addtoTailMap(result, node.right, fromKey); // Рекурсивный вызов для правого поддерева
            if (node.key.compareTo(fromKey) >= 0) { // Если ключ больше или равен fromKey
                result.put(node.key, node.value); // Добавляем в результат
                addtoTailMap(result, node.left, fromKey); // Рекурсивный вызов для левого поддерева
            }
        }
    }

    public SortedMap<Integer, String> tailMap(Integer fromKey) {
        // Проверка на null, если fromKey равен null, выбрасываем исключение
        if (fromKey == null)
            throw new NullPointerException();

        // Если дерево пустое, возвращаем null
        if (root == null)
            return null;

        // Создаем новый экземпляр MyRbMap для хранения результата
        MyRbMap result = new MyRbMap();
        // Заполняем результат, добавляя элементы, начиная с fromKey
        addtoTailMap(result, root, fromKey);
        return result; // Возвращаем полученное подмножество
    }

    @Override
    public Integer firstKey() {
        // Проверка на пустое дерево, если корень null, выбрасываем исключение
        if (root == null)
            throw new NoSuchElementException();

        MyNode x = root;
        // Переход к самому левому узлу для получения минимального ключа
        while (x.left != null)
            x = x.left;
        return x.key; // Возвращаем минимальный ключ
    }

    @Override
    public Integer lastKey() {
        // Проверка на пустое дерево, если корень null, выбрасываем исключение
        if (root == null)
            throw new NoSuchElementException();

        MyNode x = root;
        // Переход к самому правому узлу для получения максимального ключа
        while (x.right != null)
            x = x.right;
        return x.key; // Возвращаем максимальный ключ
    }

    @Override
    public Comparator<? super Integer> comparator() {
        // Возвращает null, так как ключи уже сортируются по возрастанию
        return null;
    }

    @Override
    public SortedMap<Integer, String> subMap(Integer fromKey, Integer toKey) {
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
}