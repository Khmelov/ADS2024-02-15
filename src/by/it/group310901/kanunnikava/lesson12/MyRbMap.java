package by.it.group310901.kanunnikava.lesson12;

import java.util.*;
    /*Создайте class MyRbMap, который реализует интерфейс SortedMap<Integer, String>
    и работает на основе красно-черного дерева
            БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ

            Метод toString() должен выводить элементы в порядке возрастания ключей
            Формат вывода: скобки (фигурные) и разделители
            (знак равенства и запятая с пробелом) должны
            быть такими же как в методе toString() обычной коллекции*/

public class MyRbMap implements SortedMap<Integer, String> {

    private enum COLOR{RED, BLACK}; // Перечисление для представления цвета узлов (красный или черный)

    private static class Node {
        Integer key; // Ключ узла
        String value; // Значение узла
        COLOR color; // Цвет узла
        Node left, right; // Ссылки на левого и правого потомков
        public Node(Integer key, String value, COLOR color) { // Конструктор класса Node
            this.key = key;
            this.value = value;
            this.color = color;
            this.left = this.right = null;
        }
    }

    int size = 0;
    Node head = null; // Корневой узел дерева

    private boolean isRed(Node n) { // Метод для проверки, является ли узел красным
        return n != null && n.color == COLOR.RED; // Возвращает true, если узел не пуст и его цвет красный
    }

    private void swapColors(Node n) { // Метод для смены цветов узлов
        COLOR tmp = n.left.color; // Временное сохранение цвета левого потомка
        n.left.color = n.color; // Установка цвета левого потомка в цвет текущего узла
        n.right.color = n.color; // Установка цвета правого потомка в цвет текущего узла
        n.color = tmp; // Установка цвета текущего узла во временный цвет
    }
    private Node leftRotate(Node n) { // Метод для выполнения левого поворота
        Node child = n.right; // Временное сохранение цвета левого потомка
        n.right = child.left; // Левый потомок нового родителя становится правым потомком текущего родителя
        child.left = n; // Текущий родитель становится левым потомком нового родителя
        child.color = n.color; // Установка цвета нового родителя в цвет текущего родителя
        n.color = COLOR.RED; // Установка цвета текущего родителя в красный
        return child; // Возвращение нового родителя
    }

    private Node rightRotate(Node n) { // Метод для выполнения правого поворота
         Node child = n.left; // Левый потомок становится новым родителем
         n.left = child.right; // Правый потомок нового родителя становится левым потомком текущего родителя
         child.right = n; // Текущий родитель становится правым потомком нового родителя
         child.color = n.color; // Установка цвета нового родителя в цвет текущего родителя
         n.color = COLOR.RED; // Установка цвета текущего родителя в красный
         return child; // Возвращение нового родителя
    }

    private Node balanceNode(Node n) { // Метод для балансировки узла
        if (isRed(n.right) && !isRed(n.left)) { // Если правый потомок красный, а левый не красный
            n = leftRotate(n); // Выполнение левого поворота
        }
        if (isRed(n.left) && isRed(n.left.left)) { // Если левый потомок и его левый потомок красные
            n = rightRotate(n); // Выполнение правого поворота
        }
        if (isRed(n.right) && isRed(n.left)) { // Если оба потомка красные
            swapColors(n); // Смена цветов узлов
        }

        return n;
    }


    private Node search(Node n, Integer key) { // Метод для поиска узла по ключу
        while(n != null) {
            if ((int)key < (int)n.key) { // Если ключ меньше ключа узла
                n = n.left; // Переход к левому потомку
            } else if ((int)key > (int)n.key) { // Если ключ больше ключа узла
                n = n.right; // Переход к правому потомку
            } else {
                return n; // Возвращение узла
            }
        }
        return null;
    }
    private Node put(Node n, Integer key, String value) {
        if (n == null) {
            return new Node(key, value, COLOR.RED);
        }

        if ((int)key < (int)n.key) {
            n.left = put(n.left, key, value);
        } else if ((int)key > (int)n.key) {
            n.right = put(n.right, key, value);
        }

        return balanceNode(n);
    }


    public String toString() { // Переопределение метода для получения строкового представления карты
         StringBuilder sb = new StringBuilder("{");
         String delimiter = ""; // Инициализация разделителя
         Stack<Node> s = new Stack<Node>(); // Создание стека узлов
         Node curNode = head; // Инициализация текущего узла корневым узлом
         while (!s.isEmpty() || curNode != null) {
            if (curNode != null) { // Если текущий узел не пуст
                s.push(curNode); // Добавление узла в стек
                curNode = curNode.left; // Переход к левому потомку
            } else { // Иначе
                curNode = s.pop(); // Извлечение узла из стека
                sb.append(delimiter).append(curNode.key).append("=").append(curNode.value); // Добавление ключа и значения узла в строку
                delimiter = ", "; // Установка разделителя
                curNode = curNode.right; // Переход к правому потомку
            }
        }
         sb.append("}"); // Добавление закрывающей фигурной скобки
        return sb.toString(); // Возвращение строки
    }

    @Override
    public Comparator<? super Integer> comparator() {  // Переопределение метода для получения компаратора ключей
        return null;
    }

    @Override
    public SortedMap<Integer, String> subMap(Integer fromKey, Integer toKey) { // Переопределение метода для получения подмножества карты
        return null;
    }

    private void headToKey(SortedMap<Integer, String> newMap, Integer toKey, Node n) { // Метод для добавления всех элементов до заданного ключа в новую карту
        if (n == null) {
            return; // Завершение метода
        }

        headToKey(newMap, toKey, n.left); // Рекурсивный вызов для левого поддерева
        if (toKey > n.key) { // Если ключ узла меньше заданного ключа
            newMap.put(n.key, n.value); // Добавление узла в новую карту
            headToKey(newMap, toKey, n.right); // Рекурсивный вызов для правого поддерева
        }
    }

    private void tailFromKey(SortedMap<Integer, String> newMap, Integer fromKey, Node n) { // Метод для добавления всех узлов с ключом >= fromKey в новый SortedMap
        if (n == null) {
            return;  // Завершение метода
        }

        tailFromKey(newMap, fromKey, n.right); // Рекурсивный вызов для правого потомка
        if (fromKey <= n.key) {
            newMap.put(n.key, n.value); // Добавление узла в новый SortedMap
            tailFromKey(newMap, fromKey, n.left); // Рекурсивный вызов для левого потомка
        }
    }

    @Override
    public SortedMap<Integer, String> headMap(Integer toKey) { // Метод для создания нового SortedMap с ключами < toKey
        SortedMap<Integer, String> newMap = new MyRbMap(); // Создание нового экземпляра MyRbMap
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
    public Integer firstKey() { // Метод для получения первого (минимального) ключа
        if (head == null) {
            return null;
        }

        Node n = head; // Инициализация текущего узла корневым узлом
        while (n.left != null) {
            n = n.left; // Переход к левому потомку
        }

        return n.key; // Возвращение ключа минимального узла
    }

    @Override
    public Integer lastKey() { // Метод для получения последнего (максимального) ключа
        if (head == null) {
            return null;
        }

        Node n = head; // Инициализация текущего узла корневым узлом
        while (n.right != null) { // Пока правый потомок не пуст
            n = n.right; // Переход к правому потомку
        }

        return n.key; // Возвращение ключа минимального узла
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
        return search(head, (int)key) != null; // Возвращает true, если ключ найден, иначе false
    }

    @Override
    public boolean containsValue(Object value) { // Метод для проверки наличия значения в карте
        Stack<Node> s = new Stack<Node>(); // Создание стека узлов
        Node curNode = head; // Инициализация текущего узла корневым узлом
        while (!s.isEmpty() || curNode != null) { // Пока стек не пуст или текущий узел не пуст
             if (curNode != null) { // Если текущий узел не пуст
                s.push(curNode); // Добавление узла в стек
            curNode = curNode.left; // Переход к левому потомку
            } else {
                curNode = s.pop(); // Извлечение узла из стека
                if (value.equals(curNode.value)) { // Если значение найдено
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
        return n == null ? null : n.value; // Возвращение значения, если узел найден, иначе null
    }

    @Override
    public String put(Integer key, String value) { // Метод для вставки ключа и значения в карту
        Node n = search(head, (int)key); // Поиск узла по ключу
        if (n == null) {
            size++;
            head = put(head, key, value); // Вставка нового узла
            if (head.color == COLOR.RED) {
                head.color = COLOR.BLACK; // Установка цвета корневого узла в черный
            }
            return null;
        }

        String oldValue = n.value; // Сохранение предыдущего значения
        n.value = value; // Обновление значения узла
        return oldValue;
    }


    private Node findMin(Node n) { // Метод для поиска узла с минимальным ключом
        while (n.left != null) {
            n = n.left; // Переход к левому потомку
        }

        return n;
    }

    private Node removeMin(Node n) { // Метод для удаления узла с минимальным ключом
        if (n.left == null) {
            return n.right; // Возвращение правого потомка
        }

        n.left = removeMin(n.left); // Удаление минимального узла в левом поддереве
        return balanceNode(n); // Балансировка узла и возврат
    }


    private Node remove(Node n, Integer key) { // Метод для удаления узла по ключу
        if (key < n.key) { // Если ключ меньше ключа узла
            n.left = remove(n.left, key); // Удаление узла в левом поддереве
        } else {
            if (isRed(n.left)) { // Если левый потомок красный
                n = rightRotate(n); // Правый поворот узла
            }
            if (n.key.equals(key) && n.right == null) { // Если ключ равен ключу узла и правый потомок пуст
                return null;
            }

            if (n.key.equals(key)) { // Если ключ равен ключу узла
                Node minNode = findMin(n.right); // Поиск узла с минимальным ключом в правом поддереве
                n.key = minNode.key;
                n.value = minNode.value;
                n.right = removeMin(n.right);
            } else {
                n.right = remove(n.right, key); // Удаление узла в правом поддереве
            }
        }
        return balanceNode(n);
    }
    @Override
    public String remove(Object key) { // Метод для удаления ключа и значения из карты
        String oldValue = get(key); get(key); // Сохранение старого значения
        if (oldValue != null) {
            size--;
            head = remove(head, (int)key); // Удаление узла
            if (head.color == COLOR.RED) {
                head.color = COLOR.BLACK; // Установка цвета корневого узла в черный
            }
        }

        return oldValue;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) { // Метод для вставки всех элементов из другой карты

    }

    @Override
    public void clear() { // Метод для очистки карты
        Stack<Node> s = new Stack<Node>(); // Создание стека узлов
        Node lastVisited = null, curNode = head; // Инициализация переменных
        while (!s.isEmpty() || curNode != null) {
            if (curNode != null) {
                s.push(curNode); // Добавление узла в стек
                lastVisited = curNode; // Обновление последнего посещенного узла
                curNode = curNode.left; // Переход к левому потомку
            } else {
                curNode = s.pop(); // Извлечение узла из стека
                if (lastVisited != null && lastVisited != curNode) { // Если последний посещенный узел не равен текущему
                    lastVisited.right = null; // Удаление ссылки на правого потомка у последнего посещенного узла
                }
                lastVisited = curNode; // Обновление последнего посещенного узла
                curNode.left = null;  // Удаление ссылки на левого потомка у текущего узла
                curNode = curNode.right; // Переход к правому потомку
            }
        }
        head.left = null;
        head.right = null;
        head = null;
        size = 0;
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