package by.it.group310901.kanunnikava.lesson12;

import java.util.*;
/*    Создайте class MySplayMap, который реализует интерфейс NavigableMap<Integer, String>
    и работает на основе splay-дерева
    БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ

    Метод toString() должен выводить элементы в порядке возрастания ключей
    Формат вывода: скобки (фигурные) и разделители
    (знак равенства и запятая с пробелом) должны
    быть такими же как в методе toString() обычной коллекции*/

public class MySplayMap implements NavigableMap<Integer, String> {

    private static class Node {
        Integer key; // Ключ узла
        String value; // Значение узла
        Node parent, left, right; // Ссылки на родительского узла, левого и правого потомков
        public Node(Integer key, String value) { // Конструктор класса Node
            this.key = key;
            this.value = value;
            this.left = this.right = this.parent = null;
        }
    }

    int size = 0;
    Node head = null;



    private Node leftRotate(Node n) { // Метод для выполнения левого поворота узла
        Node child = n.right; // Получение правого потомка
        if (child != null) {
            n.right = child.left; // Левый потомок правого потомка становится правым потомком текущего узла
            if (child.left != null) {
                child.left.parent = n; // Установка ссылки на родительский узел для левого потомка
            }
            child.parent = n.parent; // Установка ссылки на родительский узел для правого потомка
            child.left = n; // Текущий узел становится левым потомком правого потомка
        }
        if (n.parent != null) {
            if (n.equals(n.parent.left)) n.parent.left = child; // Если текущий узел левый потомок, установка правого потомка как левого потомка родительского узла
            else n.parent.right = child; // Иначе установка правого потомка как правого потомка родительского узла
        }
        n.parent = child; // Установка ссылки на родительский узел для текущего узла
        return child; // Возвращение правого потомка
    }

    private Node rightRotate(Node n) { // Метод для выполнения правого поворота узла
         Node child = n.left; // Получение левого потомка
         if (child != null) {
         n.left = child.right; // Правый потомок левого потомка становится левым потомком текущего узла
            if (child.right != null) {
                child.right.parent = n; // Установка ссылки на родительский узел для правого потомка
            }
            child.parent = n.parent; // Установка ссылки на родительский узел для левого потомка
            child.right = n; // Текущий узел становится правым потомком левого потомка
         }
        if (n.parent != null) {
            if (n.equals(n.parent.left)) n.parent.left = child; // Если текущий узел левый потомок, установка левого потомка как левого потомка родительского узла
            else n.parent.right = child; // Иначе установка левого потомка как правого потомка родительского узла
            }
        n.parent = child; // Установка ссылки на родительский узел для текущего узла
        return child; // Возвращение левого потомка
    }

    private Node search(Node n, Integer key) { // Метод для поиска узла по ключу
        while(n != null) {
            if (key < n.key) { // Если ключ меньше ключа узла
                n = n.left; // Переход к левому потомку
            } else if (key > n.key) { // Если ключ больше ключа узла
                n = n.right; // Переход к правому потомку
            } else {
                return n; // Возвращение узла
            }
        }
        return null;
    }
    private Node splay(Node n) { // Метод для выполнения сплэй (splay) операции
        if (n == null) {
            return null;
        }
        while (n.parent != null) { // Пока родительский узел не null
            if (n.parent.parent == null) { // Если родительский узел корневой
                if (n.equals(n.parent.left)) n = rightRotate(n.parent); // Если текущий узел левый потомок, правый поворот родительского узла
                else n = leftRotate(n.parent); // Иначе левый поворот родительского узла
            } else if (n.equals(n.parent.left) && n.parent.parent.left == n.parent) { // Если текущий узел и его родитель оба левые потомки
                n = rightRotate(n.parent); // Правый поворот родительского узла
                n = rightRotate(n.parent); // Правый поворот нового родительского узла
            } else if (n.equals(n.parent.left) && n.parent.parent.right == n.parent) { // Если текущий узел левый потомок, а его родитель правый потомок
                n = rightRotate(n.parent); // Правый поворот родительского узла
                n = leftRotate(n.parent); // Левый поворот нового родительского узла
            } else if (n.equals(n.parent.right) && n.parent.parent.left == n.parent) { // Если текущий узел правый потомок, а его родитель левый потомок
                n = leftRotate(n.parent); // Левый поворот родительского узла
                n = rightRotate(n.parent); // Правый поворот нового родительского узла
            } else { // Иначе текущий узел и его родитель оба правые потомки
                n = leftRotate(n.parent); // Левый поворот родительского узла
                n = leftRotate(n.parent); // Левый поворот нового родительского узла
            }
        }
        return n;
    }

    public String toString() { // Переопределение метода для получения строкового представления карты
        StringBuilder sb = new StringBuilder("{");
        String delimiter = "";
        Stack<Node> s = new Stack<Node>(); // Создание стека узлов
        Node curNode = head; // Инициализация текущего узла корневым узлом
        while (!s.isEmpty() || curNode != null) {
            if (curNode != null) { // Если текущий узел не пуст
                s.push(curNode); // Добавление текущего узла в стек
                curNode = curNode.left; // Переход к левому потомку
            } else {
                curNode = s.pop(); // Извлечение узла из стека
                sb.append(delimiter).append(curNode.key).append("=").append(curNode.value); // Добавление ключа и значения узла в строку
                delimiter = ", ";
                curNode = curNode.right;
            }
        }

        sb.append("}");
        return sb.toString();
    }

    @Override
    public Entry<Integer, String> lowerEntry(Integer key) { // Метод для получения пары "ключ-значение" с ключом ниже указанного
        return null;
    }

    private Node findLower(Node n, Integer key) { // Метод для поиска узла с ключом ниже указанного
        Node lower = null; // Инициализация переменной для хранения узла
        while (n != null) {
            if (key <= n.key) { // Если указанный ключ меньше или равен ключу текущего узла
                n = n.left; // Переход к левому потомку
            } else {
                lower = n; // Обновление переменной
                n = n.right; // Переход к правому потомку
            }
        }

        return lower;
    }
    @Override
    public Integer lowerKey(Integer key) { // Метод для получения ключа ниже указанного
        Node lower = findLower(head, key); // Поиск узла с ключом ниже указанного
        if (lower == null) { // Если узел не найден
            return null;
        }
        head = splay(lower); // Выполнение splay для найденного узла

        return lower.key;
    }

    @Override
    public Entry<Integer, String> floorEntry(Integer key) { // Метод для получения пары "ключ-значение" с ключом не выше указанного
        return null;
    }

    @Override
    public Integer floorKey(Integer key) { // Метод для получения ключа не выше указанного
        if (search(head, key) != null) { // Если ключ найден в дереве
            return key;
        }

        return lowerKey(key); // Возвращение ключа ниже указанного
    }

    @Override
    public Entry<Integer, String> ceilingEntry(Integer key) { // Метод для получения пары "ключ-значение" с ключом не ниже указанного
        return null;
    }

    @Override
    public Integer ceilingKey(Integer key) { // Метод для получения ключа не ниже указанного
        if (search(head, key) != null) { // Возвращение ключа ниже указанного
            return key;
        }
        return higherKey(key); // Возвращение ключа выше указанного
    }

    @Override
    public Entry<Integer, String> higherEntry(Integer key) { // Метод для получения пары "ключ-значение" с ключом выше указанного
        return null;
    }

    private Node findHigher(Node n, Integer key) { // Метод для поиска узла с ключом выше указанного
        Node higher = null; // Инициализация переменной для хранения узла
        while (n != null) {
            if (key < n.key) { // Если указанный ключ меньше ключа текущего узла
                higher = n; // Обновление переменной
                n = n.left; // Переход к левому потомку
            } else {
                n = n.right; // Переход к правому потомку
            }
        }

        return higher;
    }
    @Override
    public Integer higherKey(Integer key) { // Метод для получения ключа выше указанного
        Node higher = findHigher(head, key); // Поиск узла с ключом выше указанного
        if (higher == null) {
            return null;
        }
        head = splay(higher); // Выполнение splay для найденного узла

        return higher.key;
    }


    @Override
    public Entry<Integer, String> firstEntry() { // Метод для получения первой (минимальной) пары "ключ-значение"
        return null;
    }

    @Override
    public Entry<Integer, String> lastEntry() {  // Метод для получения последней (максимальной) пары "ключ-значение"
        return null;
    }

    @Override
    public Entry<Integer, String> pollFirstEntry() { // Метод для получения и удаления первой (минимальной) пары "ключ-значение"
        return null;
    }

    @Override
    public Entry<Integer, String> pollLastEntry() { // Метод для получения и удаления последней (максимальной) пары "ключ-значение"
        return null;
    }

    @Override
    public NavigableMap<Integer, String> descendingMap() { // Метод для получения карты в обратном порядке
        return null;
    }

    @Override
    public NavigableSet<Integer> navigableKeySet() { // Метод для получения множества ключей в навигационной карте
        return null;
    }

    @Override
    public NavigableSet<Integer> descendingKeySet() { // Метод для получения набора ключей в порядке убывания
        return null;
    }

    @Override
    public NavigableMap<Integer, String> subMap(Integer fromKey, boolean fromInclusive, Integer toKey, boolean toInclusive) { // Метод для получения части карты
        return null;
    }

    @Override
    public NavigableMap<Integer, String> headMap(Integer toKey, boolean inclusive) { // Метод для получения части карты с ключами меньше заданного
        return null;
    }

    @Override
    public NavigableMap<Integer, String> tailMap(Integer fromKey, boolean inclusive) { // Метод для получения части карты с ключами больше заданного
        return null;
    }

    @Override
    public Comparator<? super Integer> comparator() { /// Метод для получения компаратора, используемого в карте
        return null;
    }

    @Override
    public SortedMap<Integer, String> subMap(Integer fromKey, Integer toKey) { // Метод для получения части карты
        return null;
    }

    private void headToKey(SortedMap<Integer, String> newMap, Integer toKey, Node n) { // Приватный метод для добавления всех узлов с ключами < toKey в новый SortedMap
        if (n == null) {
            return; // Завершение метода
        }

        headToKey(newMap, toKey, n.left); // Рекурсивный вызов для левого потомка
        if (toKey > n.key) {
            newMap.put(n.key, n.value); // Добавление узла в новый SortedMap
            headToKey(newMap, toKey, n.right); // Рекурсивный вызов для правого потомка
        }
    }

    private void tailFromKey(SortedMap<Integer, String> newMap, Integer fromKey, Node n) { // Приватный метод для добавления всех узлов с ключами >= fromKey в новый SortedMap
        if (n == null) {
            return;
        }

        tailFromKey(newMap, fromKey, n.right); // Рекурсивный вызов для правого потомка
        if (fromKey <= n.key) { // Если ключ узла >= fromKey
            newMap.put(n.key, n.value); // Добавление узла в новый SortedMap
            tailFromKey(newMap, fromKey, n.left); // Рекурсивный вызов для левого потомка
        }
    }

    @Override
    public SortedMap<Integer, String> headMap(Integer toKey) { // Метод для создания нового SortedMap с ключами < toKey
        SortedMap<Integer, String> newMap = new MyRbMap();
        headToKey(newMap, toKey, head); // Заполнение нового SortedMap узлами с ключами < toKey
        return newMap;
    }

    @Override
    public SortedMap<Integer, String> tailMap(Integer fromKey) { // Метод для создания нового SortedMap с ключами >= fromKey
        SortedMap<Integer, String> newMap = new MyRbMap();
        tailFromKey(newMap, fromKey, head); // Заполнение нового SortedMap узлами с ключами >= fromKey
        return newMap;
    }

    @Override
    public Integer firstKey() { // Метод для получения первого (минимального) ключа в дереве
        if (head == null) {
            return null;
        }

        Node n = head; // Инициализация текущего узла корневым узлом
        while (n.left != null) {
            n = n.left; // Переход к левому потомку
        }

        head = splay(n); // Вызов функции splay для перемещения найденного узла к корню дерева
        return head.key;
    }

    @Override
    public Integer lastKey() { // Метод для получения последнего (максимального) ключа в дереве
        if (head == null) {
            return null;
        }

        Node n = head; // Инициализация текущего узла корневым узлом
        while (n.right != null) {
            n = n.right; // Переход к правому потомку
        }

        head = splay(n); // Вызов функции splay для перемещения найденного узла к корню дерева
        return head.key;
    }

    @Override
    public int size() { // Метод для получения текущего размера карты
        return size;
    }

    @Override
    public boolean isEmpty() { // Метод для проверки, пустая ли карта
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) { // Метод для проверки наличия ключа в карте
        Node n = search(head, (int)key); // Поиск узла по ключу
        if (n == null) {
            return false;
        }

        head = splay(n); // Вызов функции splay для перемещения найденного узла к корню дерева
        return true;
    }

    @Override
    public boolean containsValue(Object value) { // Метод для проверки наличия значения в карте
        Stack<Node> s = new Stack<Node>(); // Создание стека узлов
        Node curNode = head; // Инициализация текущего узла корневым узлом
        while (!s.isEmpty() || curNode != null) { // Пока стек не пуст или текущий узел не пуст
            if (curNode != null) {
                s.push(curNode); // Добавление узла в стек
                curNode = curNode.left; // Переход к левому потомку
            } else {
                curNode = s.pop(); // Извлечение узла из стека
                if (value.equals(curNode.value)) {
                    head = splay(curNode); // Вызов функции splay для перемещения найденного узла к корню дерева
                    return true;
                }
                curNode = curNode.right; // Переход к правому потомку
            }
        }
        return false;
    }

    @Override
    public String get(Object key) { // Метод для получения значения по ключу
        Node n = search(head, (int)key); // Поиск узла по ключу
        if (n == null) {
            return null;
        }

        head = splay(n); // Вызов функции splay для перемещения найденного узла к корню дерева
        return head.value;
    }

    @Override
    public String put(Integer key, String value) { // Метод для вставки нового узла или обновления значения существующего узла
        Node n = search(head, key); // Поиск узла по ключу
        if (n != null) {
            String oldValue = n.value; // Сохранение старого значения
            n.value = value; // Обновление значения узла
            head = splay(n); // Вызов функции splay для перемещения узла к корню дерева
            return oldValue;
        }

        size++;
        Node preInsert = null; // Переменная для хранения узла, к которому будет присоединен новый узел
        n = head; // Инициализация текущего узла корневым узлом
        while(n != null) {
            preInsert = n; // Сохранение текущего узла
            if (key < n.key) {
                n = n.left; // Переход к левому потомку
            } else if (key > n.key) {
                n = n.right; // Переход к правому потомку
            }
        }

        n = new Node(key, value); // Создание нового узла
        n.parent = preInsert; // Установка родителя нового узла

        if (preInsert == null) {  // Если карта была пустой
            head = n; // Новый узел становится корнем дерева
        } else if (preInsert.key < n.key) {
            preInsert.right = n; // Новый узел становится правым потомком
        } else if (preInsert.key > n.key) {
            preInsert.left = n; // Новый узел становится левым потомком
        }
        head = splay(n);
        return null;
    }

    @Override
    public void clear() { // Метод для очистки карты
        Stack<Node> s = new Stack<Node>(); // Создание стека узлов
        Node lastVisited = null, curNode = head; // Инициализация переменных
        while (!s.isEmpty() || curNode != null) {
            if (curNode != null) {
                s.push(curNode); // Добавление узла в стек
                lastVisited = curNode;  // Обновление последнего посещенного узла
                curNode = curNode.left; // Переход к левому потомку
            } else {
                curNode = s.pop(); // Извлечение узла из стека
                if (lastVisited != null && lastVisited != curNode) {
                    lastVisited.right = null; // Удаление ссылки на правого потомка у последнего посещенного узла
                }
                lastVisited = curNode; // Удаление ссылки на левого потомка у корневого узла
                curNode.left = null; // Удаление ссылки на правого потомка у корневого узла
                curNode = curNode.right; // Переход к правому потомку
            }
        }
        head.left = null;
        head.right = null;
        head = null;
        size = 0;
    }

    @Override
    public String remove(Object key) { // Метод для удаления ключа и значения из карты
        Node n = search(head, (int)key); // Поиск узла по ключу
        if (n != null) {
            size--;
            remove(n); // Удаление узла
            return n.value;
        }

        return null;
    }

    private void remove(Node node) { // Приватный метод для удаления узла из дерева
        if (node == null) {
            return; // Завершение метода
        }

        head = splay(node); // Вызов функции splay для перемещения узла к корню дерева

        if (node.left == null) {
            head = head.right; // Правый потомок становится новым корнем
            if (head != null) {
                head.parent = null; // Удаление ссылки на родителя у нового корня
            }
        } else {
            Node tmp = head; // Сохранение текущего корня
            head = splay(findMax(head.left)); // Вызов функции splay для максимального узла в левом поддереве
            head.right = tmp.right; // Установка правого потомка нового корня
            tmp.right.parent = head; // Установка ссылки на родителя у правого потомка
        }
    }

    private Node findMax(Node n) { // Приватный метод для поиска узла с максимальным ключом
        if (n == null) {
            return null;
        }

        while(n.right != null) {
            n = n.right; // Переход к правому потомку
        }

        return n;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) { // Метод для вставки всех элементов из другой карты

    }

    @Override
    public Set<Integer> keySet() { // Метод для получения множества ключей
        return null;
    }

    @Override
    public Collection<String> values() { // Метод для получения коллекции значений
        return null;
    }

    @Override
    public Set<Entry<Integer, String>> entrySet() { // Метод для получения множества пар "ключ-значение"
        return null;
    }
}