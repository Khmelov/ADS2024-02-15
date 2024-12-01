package by.it.group310902.yoshchyk.lesson12;

import java.util.*;

//Сплей-дерево — это самобалансирующееся бинарное дерево поиска, которое использует операции слияния и разделения для поддержания своей структуры.
// Основная идея заключается в том, что при доступе к элементу (поиске, вставке или удалении) дерево "сплеится" (перестраивается) так,
// чтобы доступ к этому элементу был более эффективным в будущем.
class MySplayMap implements NavigableMap<Integer, String> {

    private int size = 0; // Хранит количество узлов в дереве
    private MyNode root; // Корень дерева

    // Статический закрытый класс, представляющий узел в дереве
    static private class MyNode {
        Integer key; // Ключ узла
        String value; // Значение узла
        MyNode parent; // Родительский узел
        MyNode left, right; // Левый и правый дочерние узлы

        // Конструктор узла
        MyNode(Integer key, String value, MyNode parent) {
            this.key = key;
            this.value = value;
            this.right = null;
            this.left = null;
            this.parent = parent;
        }

        // Возвращает прародительский узел или null, если он не существует
        MyNode grandfather() {
            if (this != null && this.parent != null)
                return this.parent.parent;
            else
                return null;
        }
    }

    // Рекурсивный метод для добавления узлов в строку для вывода
    private void addtostring(MyNode parent, StringBuilder str) {
        if (parent.left != null)
            addtostring(parent.left, str); // Сначала обрабатываем левое поддерево
        str.append(parent.key);
        str.append("=");
        str.append(parent.value);
        str.append(", "); // Форматируем строку
        if (parent.right != null)
            addtostring(parent.right, str); // Затем обрабатываем правое поддерево
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (root != null) {
            addtostring(root, sb); // Заполняем строку данными из дерева
            sb.delete(sb.length() - 2, sb.length()); // Убираем последнюю запятую и пробел
        }
        sb.append("}");
        return sb.toString(); // Возвращаем строковое представление дерева
    }

    // Вращение вправо
    private void rotateright(MyNode node) {
        MyNode nextnode = node.left; // Сохраняем левый дочерний узел
        nextnode.parent = node.parent; // Устанавливаем родителя для нового корня
        if (node.parent != null) {
            if (node.parent.left == node)
                node.parent.left = nextnode; // Обновляем родительское указание
            else
                node.parent.right = nextnode;
        } else
            root = nextnode; // Если node был корнем, обновляем корень

        node.parent = nextnode; // Устанавливаем нового родителя для node
        node.left = nextnode.right; // Перемещаем правое поддерево nextnode на место node
        if (nextnode.right != null)
            nextnode.right.parent = node; // Обновляем указатель на родителя для правого поддерева
        nextnode.right = node; // Устанавливаем node как правого дочернего узла для nextnode
    }

    // Вращение влево
    private void rotateleft(MyNode node) {
        MyNode nextnode = node.right; // Сохраняем правый дочерний узел
        nextnode.parent = node.parent; // Устанавливаем родителя для нового корня
        if (node.parent != null) {
            if (node.parent.left == node)
                node.parent.left = nextnode; // Обновляем родительское указание
            else
                node.parent.right = nextnode;
        } else
            root = nextnode; // Если node был корнем, обновляем корень

        node.parent = nextnode; // Устанавливаем нового родителя для node
        node.right = nextnode.left; // Перемещаем левое поддерево nextnode на место node
        if (nextnode.left != null)
            nextnode.left.parent = node; // Обновляем указатель на родителя для левого поддерева
        nextnode.left = node; // Устанавливаем node как левый дочерний узел для nextnode
    }

    // Операция сплея узла
    private void splay(MyNode node) {
        while (node.parent != null) { // Пока есть родитель
            if (node == node.parent.left) { // Если узел - левый дочерний
                if (node.grandfather() == null) // Если нет деда
                    rotateright(node.parent); // Выполняем поворот вправо
                else if (node.parent == node.grandfather().left) {
                    rotateright(node.grandfather()); // Двойной поворот вправо
                    rotateright(node.parent);
                } else {
                    rotateright(node.parent); // Поворот вправо, затем влево
                    rotateleft(node.parent);
                }
            } else { // Если узел - правый дочерний
                if (node.grandfather() == null) // Если нет деда
                    rotateleft(node.parent); // Выполняем поворот влево
                else if (node.parent == node.grandfather().right) {
                    rotateleft(node.grandfather()); // Двойной поворот влево
                    rotateleft(node.parent);
                } else {
                    rotateleft(node.parent); // Поворот влево, затем вправо
                    rotateright(node.parent);
                }
            }
        }
    }

    // Расширяет максимальный узел (самый правый узел) в поддереве
    private MyNode splaymax(MyNode root) {
        MyNode x = root;
        while (x.right != null) // Находим самый правый узел
            x = x.right;
        splay(x); // Сплеим его
        return x; // Возвращаем узел
    }

    // Объединяет два поддерева после удаления узла
    private MyNode merge(MyNode root1, MyNode root2) {
        if (root1 == null) // Если первое поддерево пустое
            return root2; // Возвращаем второе
        if (root2 == null) // Если второе пустое
            return root1; // Возвращаем первое
        MyNode x = splaymax(root1); // Сплеим максимальный узел первого поддерева
        x.right = root2; // Присоединяем второе поддерево
        root2.parent = x; // Устанавливаем родителя для второго поддерева
        return x; // Возвращаем новый корень
    }

    // Разделяет дерево на основе ключа
    private MyNode split(Integer key) { // root - меньше и равно, return - больше
        Integer fkey = floorKey(key); // Получаем наибольший ключ, меньший или равный key
        if (fkey != null) { // Если такой ключ существует
            MyNode temp = root.right; // Сохраняем правое поддерево
            root.right = null; // Отделяем его
            if (temp != null)
                temp.parent = null; // Обнуляем родителя для правого поддерева
            return temp; // Возвращаем правое поддерево
        } else {
            MyNode temp = root; // Если нет подходящего ключа
            root = null; // Обнуляем корень
            return temp; // Возвращаем текущее дерево
        }
    }

    @Override
    // Добавляет элемент. Если ключ существует, обновляет значение; если нет, разбивает дерево вокруг ключа и вставляет новый узел.
    public String put(Integer key, String value) {
        if (root == null) { // Если дерево пустое
            root = new MyNode(key, value, null); // Создаем новый узел
            size++; // Увеличиваем размер
            return null; // Возвращаем null, так как это новая запись
        }
        String oldvalue; // Для хранения старого значения
        MyNode greatertree = split(key); // Разделяем дерево по ключу
        if (root != null && root.key.equals(key)) { // Если ключ уже существует
            oldvalue = root.value; // Сохраняем старое значение
            root.value = value; // Обновляем значение
            root.right = greatertree; // Присоединяем правое поддерево
        } else {
            oldvalue = null; // Ключа не было, старое значение null
            MyNode x = new MyNode(key, value, null); // Создаем новый узел
            x.right = greatertree; // Присоединяем правое поддерево
            x.left = root; // Левое поддерево теперь - старое дерево
            root = x; // Обновляем корень
            size++; // Увеличиваем размер
        }
        if (root.right != null)
            root.right.parent = root; // Обновляем родителя для правого поддерева
        if (root.left != null)
            root.left.parent = root; // Обновляем родителя для левого поддерева
        return oldvalue; // Возвращаем старое значение
    }

    // Удаляет ключ и выполняет слияние левого и правого поддеревьев удаленного узла
    @Override
    public String remove(Object key) {
        String oldvalue = get(key); // Сначала получаем значение по ключу
        if (oldvalue != null) { // Если значение найдено
            if (root.right != null)
                root.right.parent = null; // Обнуляем родителя для правого поддерева
            if (root.left != null)
                root.left.parent = null; // Обнуляем родителя для левого поддерева
            root = merge(root.left, root.right); // Объединяем оставшиеся поддеревья
            size--; // Уменьшаем размер
        }
        return oldvalue; // Возвращаем старое значение
    }

    // Ищет ключ в дереве. Если ключ найден, он расширяет узел до корня для более быстрого доступа в будущем
    @Override
    public String get(Object key) {
        MyNode x = root; // Начинаем с корня
        while (x != null) {
            if (x.key.equals(key)) { // Если нашли ключ
                splay(x); // Сплеим узел
                return x.value; // Возвращаем значение
            }
            // Переходим к левому или правому поддереву
            if (x.key.compareTo((Integer) key) > 0)
                x = x.left;
            else
                x = x.right;
        }
        return null; // Если ключ не найден
    }

    @Override
    public boolean containsKey(Object key) {
        return get(key) != null; // Проверяем наличие ключа
    }

    // Рекурсивный метод для проверки наличия значения в поддереве
    private boolean nodecontains(Object value, MyNode node) {
        if (node == null)
            return false; // Если узел пустой
        return node.value.equals(value) || // Если значение найдено
                nodecontains(value, node.right) || // Проверка правого поддерева
                nodecontains(value, node.left); // Проверка левого поддерева
    }

    @Override
    public boolean containsValue(Object value) {
        return nodecontains(value, root); // Проверяем наличие значения в дереве
    }

    @Override
    public int size() {
        return size; // Возвращаем размер дерева
    }

    // Рекурсивный метод для удаления узлов в дереве
    private MyNode eraseNode(MyNode node) {
        if (node != null) {
            node.right = eraseNode(node.right); // Удаляем правое поддерево
            node.left = eraseNode(node.left); // Удаляем левое поддерево
            node.key = null; // Обнуляем ключ
            node.value = null; // Обнуляем значение
            node.parent = null; // Обнуляем родителя
        }
        return null; // Возвращаем null
    }

    @Override
    public void clear() {
        size = 0; // Обнуляем размер
        root = eraseNode(root); // Удаляем все узлы
    }

    @Override
    public boolean isEmpty() {
        return size == 0; // Проверяем, пусто ли дерево
    }

    // Рекурсивный метод для добавления узлов в headMap
    private void addtoHeadMap(SortedMap<Integer, String> result, MyNode node, Integer toKey) {
        if (node != null) {
            addtoHeadMap(result, node.left, toKey); // Обрабатываем левое поддерево
            if (node.key.compareTo(toKey) < 0) { // Если ключ меньше toKey
                result.put(node.key, node.value); // Добавляем узел в результат
                addtoHeadMap(result, node.right, toKey); // Обрабатываем правое поддерево
            }
        }
    }

    @Override
    public SortedMap<Integer, String> headMap(Integer toKey) {
        if (toKey == null)
            throw new NullPointerException(); // Проверка на null
        if (root == null)
            return null; // Если дерево пустое
        TreeMap<Integer, String> result = new TreeMap<>(); // Создаем результирующую карту
        addtoHeadMap(result, root, toKey); // Заполняем карту
        return result; // Возвращаем результирующую карту
    }

    // Рекурсивный метод для добавления узлов в tailMap
    private void addtoTailMap(SortedMap<Integer, String> result, MyNode node, Integer fromKey) {
        if (node != null) {
            addtoTailMap(result, node.right, fromKey); // Обрабатываем правое поддерево
            if (node.key.compareTo(fromKey) >= 0) { // Если ключ больше или равен fromKey
                result.put(node.key, node.value); // Добавляем узел в результат
                addtoTailMap(result, node.left, fromKey); // Обрабатываем левое поддерево
            }
        }
    }

    @Override
    public SortedMap<Integer, String> tailMap(Integer fromKey) {
        if (fromKey == null)
            throw new NullPointerException(); // Проверка на null
        if (root == null)
            return null; // Если дерево пустое
        MyRbMap result = new MyRbMap(); // Создаем результирующую карту
        addtoTailMap(result, root, fromKey); // Заполняем карту
        return result; // Возвращаем результирующую карту
    }

    @Override
    public Integer firstKey() {
        if (root == null)
            throw new NoSuchElementException(); // Если дерево пустое, выбрасываем исключение
        MyNode x = root;
        while (x.left != null) // Ищем самый левый узел
            x = x.left;
        splay(x); // Сплеим его для быстрого доступа в будущем
        return x.key; // Возвращаем ключ
    }

    @Override
    public Integer lastKey() {
        if (root == null)
            throw new NoSuchElementException(); // Если дерево пустое, выбрасываем исключение
        MyNode x = root;
        while (x.right != null) // Ищем самый правый узел
            x = x.right;
        splay(x); // Сплеим его для быстрого доступа в будущем
        return x.key; // Возвращаем ключ
    }

    @Override
    public Integer lowerKey(Integer key) {
        MyNode x = root; // Начинаем с корня дерева
        while (x != null) { // Проходим по дереву, пока не достигнем конца
            // Если текущий ключ меньше заданного ключа
            if (x.key.compareTo(key) < 0) {
                if (x.right != null) // Если есть правое поддерево
                    x = x.right; // Переходим к правому узлу
                else { // Если правого узла нет, значит x - наибольший узел, меньший ключа
                    splay(x); // Сплеим узел, чтобы сделать его корнем
                    return x.key; // Возвращаем ключ
                }
            } else { // Если текущий ключ больше или равен заданному
                if (x.left != null) // Если есть левое поддерево
                    x = x.left; // Переходим к левому узлу
                else { // Если левого узла нет
                    MyNode parent = x.parent; // Запоминаем родителя
                    MyNode child = x; // Запоминаем текущий узел
                    // Ищем ближайшего родителя, который находится справа от своего родителя
                    while (parent != null && child == parent.left) {
                        child = parent; // Переходим к родителю
                        parent = parent.parent; // Переходим к родителю родителя
                    }
                    if (parent != null) { // Если нашли подходящего родителя
                        splay(parent); // Сплеим его, чтобы сделать корнем
                        return parent.key; // Возвращаем ключ найденного родителя
                    }
                    return null; // Если не нашли подходящего, возвращаем null
                }
            }
        }
        return null; // Если не нашли ничего, возвращаем null
    }

    @Override
    public Integer floorKey(Integer key) {
        MyNode x = root; // Начинаем с корня дерева
        while (x != null) { // Проходим по дереву, пока не достигнем конца
            // Если текущий ключ меньше или равен заданному ключу
            if (x.key.compareTo(key) <= 0) {
                if (x.right != null) // Если есть правое поддерево
                    x = x.right; // Переходим к правому узлу
                else { // Если правого узла нет
                    splay(x); // Сплеим узел, чтобы сделать его корнем
                    return x.key; // Возвращаем ключ
                }
            } else { // Если текущий ключ больше заданного
                if (x.left != null) // Если есть левое поддерево
                    x = x.left; // Переходим к левому узлу
                else { // Если левого узла нет
                    MyNode parent = x.parent; // Запоминаем родителя
                    MyNode child = x; // Запоминаем текущий узел
                    // Ищем ближайшего родителя, который находится справа от своего родителя
                    while (parent != null && child == parent.left) {
                        child = parent; // Переходим к родителю
                        parent = parent.parent; // Переходим к родителю родителя
                    }
                    if (parent != null) { // Если нашли подходящего родителя
                        splay(parent); // Сплеим его, чтобы сделать корнем
                        return parent.key; // Возвращаем ключ найденного родителя
                    }
                    return null; // Если не нашли подходящего, возвращаем null
                }
            }
        }
        return null; // Если не нашли ничего, возвращаем null
    }

    @Override
    public Integer ceilingKey(Integer key) {
        MyNode x = root; // Начинаем с корня дерева
        while (x != null) { // Проходим по дереву, пока не достигнем конца
            // Если текущий ключ больше или равен заданному ключу
            if (x.key.compareTo(key) >= 0) {
                if (x.left != null) // Если есть левое поддерево
                    x = x.left; // Переходим к левому узлу
                else { // Если левого узла нет
                    splay(x); // Сплеим узел, чтобы сделать его корнем
                    return x.key; // Возвращаем ключ
                }
            } else { // Если текущий ключ меньше заданного
                if (x.right != null) // Если есть правое поддерево
                    x = x.right; // Переходим к правому узлу
                else { // Если правого узла нет
                    MyNode parent = x.parent; // Запоминаем родителя
                    MyNode child = x; // Запоминаем текущий узел
                    // Ищем ближайшего родителя, который находится слева от своего родителя
                    while (parent != null && child == parent.right) {
                        child = parent; // Переходим к родителю
                        parent = parent.parent; // Переходим к родителю родителя
                    }
                    if (parent != null) { // Если нашли подходящего родителя
                        splay(parent); // Сплеим его, чтобы сделать корнем
                        return parent.key; // Возвращаем ключ найденного родителя
                    }
                    return null; // Если не нашли подходящего, возвращаем null
                }
            }
        }
        return null; // Если не нашли ничего, возвращаем null
    }

    @Override
    public Integer higherKey(Integer key) {
        MyNode x = root; // Начинаем с корня дерева
        while (x != null) { // Проходим по дереву, пока не достигнем конца
            // Если текущий ключ больше заданного ключа
            if (x.key.compareTo(key) > 0) {
                if (x.left != null) // Если есть левое поддерево
                    x = x.left; // Переходим к левому узлу
                else { // Если левого узла нет
                    splay(x); // Сплеим узел, чтобы сделать его корнем
                    return x.key; // Возвращаем ключ
                }
            } else { // Если текущий ключ меньше или равен заданному
                if (x.right != null) // Если есть правое поддерево
                    x = x.right; // Переходим к правому узлу
                else { // Если правого узла нет
                    MyNode parent = x.parent; // Запоминаем родителя
                    MyNode child = x; // Запоминаем текущий узел
                    // Ищем ближайшего родителя, который находится справа от своего родителя
                    while (parent != null && child == parent.right) {
                        child = parent; // Переходим к родителю
                        parent = parent.parent; // Переходим к родителю родителя
                    }
                    if (parent != null) { // Если нашли подходящего родителя
                        splay(parent); // Сплеим его, чтобы сделать корнем
                        return parent.key; // Возвращаем ключ найденного родителя
                    }
                    return null; // Если не нашли подходящего, возвращаем null
                }
            }
        }
        return null; // Если не нашли ничего, возвращаем null
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
