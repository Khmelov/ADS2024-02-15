package by.it.group310901.tit.lesson12;



import java.util.Collection;
import java.util.Map;
import java.util.Set;


// AVL-дерево — это самоподдерживающееся двоичное дерево поиска, в котором для каждого узла разница высот его левого и правого поддеревьев называется балансом.
// Баланс должен быть равен -1, 0 или +1. Если баланс узла становится больше 1 или меньше -1 после добавления или удаления узлов, дерево перестраивается
// с помощью вращений, чтобы поддерживать свои свойства.
public class MyAvlMap implements Map<Integer, String> {


    private int size = 0; // Текущий размер карты (количество узлов)
    private MyNode root; // Корень дерева


    // Внутренний класс, представляющий узел дерева
    static private class MyNode {
        Integer key; // Ключ узла
        String value; // Значение узла
        int height; // Высота узла
        MyNode left, right; // Левый и правый дочерние узлы


        // Конструктор узла, инициализирующий ключ, значение и устанавливающий высоту узла в 1
        MyNode(Integer key, String value) {
            this.key = key;
            this.value = value;
            this.height = 1; // Высота нового узла равна 1
            this.right = null;
            this.left = null;
        }
    }


    // Получение высоты узла. Если узел null, возвращаем 0.
    private int height(MyNode node) {
        return (node != null) ? node.height : 0;
    }


    // Вычисление фактора баланса узла: разница высот правого и левого поддеревьев
    private int bfactor(MyNode node) {
        return height(node.right) - height(node.left);
    }


    // Обновление высоты узла на основе высот его дочерних узлов
    private void fixheight(MyNode node) {
        int heightright = height(node.right);
        int heightleft = height(node.left);
        node.height = Math.max(heightright, heightleft) + 1; // Высота равна максимальной высоте дочерних узлов + 1
    }


    // Преобразование дерева в строку
    private void addtostring(MyNode parent, StringBuilder str) {
        if (parent.left != null)
            addtostring(parent.left, str); // Сначала добавляем левое поддерево
        str.append(parent.key).append("=").append(parent.value).append(", "); // Добавляем текущий узел
        if (parent.right != null)
            addtostring(parent.right, str); // Затем добавляем правое поддерево
    }


    // Переопределение метода toString для вывода карты в удобном формате
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (root != null) {
            addtostring(root, sb); // Заполняем строку данными из дерева
            sb.delete(sb.length() - 2, sb.length()); // Удаляем последнюю запятую
        }
        sb.append("}");
        return sb.toString();
    }


    // Поворот узла вправо
    private MyNode rotateright(MyNode node) {
        MyNode nextnode = node.left; // Сохраняем левый узел
        node.left = nextnode.right; // Переключаем правого ребенка левого узла
        nextnode.right = node; // Устанавливаем текущий узел правым ребенком нового узла
        fixheight(node); // Обновляем высоту текущего узла
        fixheight(nextnode); // Обновляем высоту нового узла
        return nextnode; // Возвращаем новый корень поддерева
    }


    // Поворот узла влево
    private MyNode rotateleft(MyNode node) {
        MyNode nextnode = node.right; // Сохраняем правый узел
        node.right = nextnode.left; // Переключаем левого ребенка правого узла
        nextnode.left = node; // Устанавливаем текущий узел левым ребенком нового узла
        fixheight(node); // Обновляем высоту текущего узла
        fixheight(nextnode); // Обновляем высоту нового узла
        return nextnode; // Возвращаем новый корень поддерева
    }


    // Балансировка узла
    private MyNode balance(MyNode node) {
        fixheight(node); // Обновляем высоту узла
        int h = bfactor(node); // Получаем фактор баланса
        // Правый подъем (поворот влево)
        if (h == 2) {
            if (bfactor(node.right) < 0) // Если правое поддерево "левое"
                node.right = rotateright(node.right); // Сначала делаем правый поворот
            return rotateleft(node); // Затем поворот влево
        }
        // Левый подъем (поворот вправо)
        if (h == -2) {
            if (bfactor(node.left) > 0) // Если левое поддерево "правое"
                node.left = rotateleft(node.left); // Сначала делаем левый поворот
            return rotateright(node); // Затем поворот вправо
        }
        return node; // Узел сбалансирован
    }


    // Вставка узла
    private MyNode insert(MyNode node, Integer key, String value, StringBuilder oldvalue) {
        if (node == null) {
            size++; // Увеличиваем размер при создании нового узла
            return new MyNode(key, value); // Возвращаем новый узел
        }
        // Рекурсивная вставка: сравниваем ключи
        if (node.key > key)
            node.left = insert(node.left, key, value, oldvalue); // Вставляем в левое поддерево
        else if (node.key < key)
            node.right = insert(node.right, key, value, oldvalue); // Вставляем в правое поддерево
        else {
            oldvalue.append(node.value); // Сохраняем старое значение, если ключ уже существует
            node.value = value; // Обновляем значение узла
            return node; // Возвращаем узел
        }
        return balance(node); // Балансировка дерева после вставки
    }


    // Метод для добавления пары ключ-значение в карту
    @Override
    public String put(Integer key, String value) {
        StringBuilder oldvalue = new StringBuilder(); // Хранит старое значение
        root = insert(root, key, value, oldvalue); // Вставляем узел
        return oldvalue.isEmpty() ? null : oldvalue.toString(); // Возвращаем старое значение или null
    }


    // Поиск минимального узла в поддереве
    private MyNode findmin(MyNode node) {
        // Ищем самый левый узел
        if (node.left == null)
            return node; // Если нет левого дочернего узла, возвращаем текущий узел
        else
            return findmin(node.left); // Рекурсивно ищем в левом поддереве
    }


    // Удаление узла
    private MyNode delete(MyNode node, Integer key, StringBuilder oldvalue) {
        if (node.key.equals(key)) {
            size--; // Уменьшаем размер при удалении узла
            if (oldvalue != null)
                oldvalue.append(node.value); // Сохраняем значение удаляемого узла
            // Узел без детей (лист)
            if (node.left == null && node.right == null)
                return null; // Возвращаем null, если узел удален
            // Узел с одним ребенком
            if (node.left == null)
                return node.right; // Возвращаем правого ребенка
            if (node.right == null)
                return node.left; // Возвращаем левого ребенка
            // Узел с двумя детьми
            size++; // Увеличиваем размер, так как мы будем заменять узел
            MyNode minnode = findmin(node.right); // Находим минимальный узел в правом поддереве
            node.value = minnode.value; // Замена значения
            node.key = minnode.key; // Замена ключа
            node.right = delete(node.right, minnode.key, null); // Удаляем минимальный узел из правого поддерева
            return node; // Возвращаем узел
        }
        // Рекурсивное удаление
        if (node.key > key) {
            if(node.left == null)
                return node; // Если нет левого ребенка, возвращаем текущий узел
            node.left = delete(node.left, key, oldvalue); // Удаляем из левого поддерева
        } else {
            if(node.right == null)
                return node; // Если нет правого ребенка, возвращаем текущий узел
            node.right = delete(node.right, key, oldvalue); // Удаляем из правого поддерева
        }
        return balance(node); // Балансировка дерева после удаления
    }


    // Метод для удаления узла по ключу
    @Override
    public String remove(Object key) {
        int oldsize = size; // Сохраняем старый размер
        StringBuilder oldvalue = new StringBuilder(); // Хранит старое значение
        root = delete(root, (Integer)key, oldvalue); // Удаляем узел
        return oldsize == size ? null : oldvalue.toString(); // Возвращаем старое значение или null
    }


    // Получение значения по ключу
    @Override
    public String get(Object key) {
        MyNode x = root; // Начинаем с корня
        while(x != null) {
            if (x.key.equals(key))
                return x.value; // Если нашли узел, возвращаем значение
            if (x.key > (Integer)key)
                x = x.left; // Ищем в левом поддереве
            else
                x = x.right; // Ищем в правом поддереве
        }
        return null; // Возвращаем null, если ключ не найден
    }


    // Проверка наличия ключа в карте
    @Override
    public boolean containsKey(Object key) {
        MyNode x = root; // Начинаем с корня
        while(x != null) {
            if (x.key.equals(key))
                return true; // Если нашли узел, возвращаем true
            if (x.key > (Integer)key)
                x = x.left; // Ищем в левом поддереве
            else
                x = x.right; // Ищем в правом поддереве
        }
        return false; // Ключ не найден
    }


    // Возвращает текущий размер карты
    @Override
    public int size() {
        return size; // Возврат размера карты
    }


    // Удаление всех узлов
    private MyNode eraseNode(MyNode node) {
        if (node != null) {
            node.right = eraseNode(node.right); // Рекурсивно удаляем правое поддерево
            node.left = eraseNode(node.left); // Рекурсивно удаляем левое поддерево
            node.key = null; // Обнуляем ключ
            node.value = null; // Обнуляем значение
        }
        return null; // Возвращаем null после удаления
    }


    // Очистка карты
    @Override
    public void clear() {
        size = 0; // Сброс размера карты
        root = eraseNode(root); // Удаление всех узлов
    }


    // Проверка на пустоту карты
    @Override
    public boolean isEmpty() {
        return size == 0; // Возвращает true, если карта пуста
    }


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

