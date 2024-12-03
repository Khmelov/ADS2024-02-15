package by.it.group310901.kanunnikava.lesson12;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

 /*    Создайте class MyAvlMap, который реализует интерфейс Map<Integer, String>
    и работает на основе АВЛ-дерева
    БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ

    Метод toString() должен выводить элементы в порядке возрастания ключей
    Формат вывода: скобки (фигурные) и разделители
    (знак равенства и запятая с пробелом) должны
    быть такими же как в методе toString() обычной коллекции*/

public class MyAvlMap implements Map<Integer, String> {

    private static class Node { // Вложенный класс Node для представления узлов в AVL-дереве
        Integer key; // Ключ узла
        String value; // Значение узла
        int height; // Высота узла
        Node left, right; // Ссылки на левого и правого потомков
        public Node(Integer key, String value) { // Конструктор класса Node
            this.key = key;
            this.value = value;
            this.left = this.right = null; // Инициализация ссылок на потомков как null
            height = 1;
        }

    }
    int size = 0;
    Node head = null; // Корневой узел дерева

    private int height(Node n) { // Метод для получения высоты узла
        return n == null ? 0 : n.height; // Возвращает 0, если узел null, иначе возвращает высоту узла
    }

    private int balanceHeight(Node n) { // Метод для вычисления разницы высот правого и левого поддеревьев
        return height(n.right) - height(n.left); // Возвращает разницу высот
    }

    private void fixHeight(Node n) { // Метод для обновления высоты узла
        if (n == null) {
            return; // Завершение метода
        }
        int hl = height(n.left); // Высота левого поддерева
        int hr = height(n.right); // Высота правого поддерева
        n.height = (hl > hr ? hl : hr) + 1; // Высота узла равна наибольшей высоте поддеревьев плюс 1
    }

    private Node rotateRight(Node parent) { // Метод для правого поворота
        Node child = parent.left; // Левый потомок становится новым родителем
        parent.left = child.right; // Правый потомок нового родителя становится левым потомком старого родителя
        child.right = parent; // Старый родитель становится правым потомком нового родителя
        fixHeight(parent);
        fixHeight(child);
        return child;
    }

    private Node rotateLeft(Node parent) { // Метод для левого поворота
        Node child = parent.right; // Правый потомок становится новым родителем
        parent.right = child.left; // Левый потомок нового родителя становится правым потомком старого родителя
        child.left = parent; // Старый родитель становится левым потомком нового родителя
        fixHeight(parent); // Обновление высоты старого родителя
        fixHeight(child); // Обновление высоты нового родителя
        return child;
    }

    private Node balanceNode(Node parent) { // Метод для балансировки узла
        if (parent == null) {
            return null;
        }
        fixHeight(parent); // Обновление высоты узла
        if (balanceHeight(parent) == 2) { // Если высота правого поддерева больше на 2
            if (balanceHeight(parent.right) < 0) { // Если высота левого поддерева правого потомка отрицательна
                parent.right = rotateRight(parent.right); // Правый поворот правого потомка
            }
            return rotateLeft(parent); // Левый поворот узла
        }

        if (balanceHeight(parent) == -2) { // Если высота левого поддерева больше на 2
            if (balanceHeight(parent.left) > 0) { // Если высота правого поддерева левого потомка положительна
                parent.left = rotateLeft(parent.left); // Левый поворот левого потомка
            }
            return rotateRight(parent); // Правый поворот узла
        }
        return parent;
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

    private Node insert(Node n, Integer key, String value) { // Метод для вставки нового узла
        if (n == null) return new Node(key, value); // Если узел null, создание и возвращение нового узла
        if ((int)key < (int)n.key) { // Если ключ меньше ключа узла
            n.left = insert(n.left, key, value); // Вставка в левое поддерево
        } else {
            n.right = insert(n.right, key, value); // Вставка в правое поддерево
        }
        return balanceNode(n);
    }

    private Node findMin(Node n) { // Метод для поиска узла с минимальным ключом
        return n.left == null ? n : findMin(n.left); // Возвращение узла, если у него нет левого потомка, иначе рекурсивный вызов для левого потомка
    }

    private Node removeMin(Node n) { // Метод для удаления узла с минимальным ключом
        if (n.left == null) {
            return n.right; // Возвращение правого потомка
        }
        n.left = removeMin(n.left); // Удаление узла в левом поддереве
        return balanceNode(n); // Балансировка узла и возврат узла
    }

    private Node remove(Node n, Integer key) { // Метод для удаления узла по ключу
        if (n == null) return null;
        if ((int)key < (int)n.key) { // Если ключ меньше ключа узла
            n.left = remove(n.left, key); // Удаление узла в левом поддереве
        } else if ((int)key > (int)n.key) {
            n.right = remove(n.right, key);  // Удаление узла в правого поддереве
        } else { // Если ключ равен ключу узла
            Node q = n.left; // Сохранение ссылки на левое поддерево
            Node r = n.right; // Сохранение ссылки на правое поддерево
            if (r == null) return q; // Если правое поддерево пусто, возвращение левого поддерева
            Node min = findMin(r); // Поиск узла с минимальным ключом в правом поддереве
            min.right = removeMin(r); // Удаление минимального узла из правого поддерева
            min.left = q; // Установка левого поддерева для минимального узла
            return balanceNode(min);
        }

        return balanceNode(n);
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
    public int size() { // Переопределение метода для получения текущего размера карты
        return size;
    }

    @Override
    public boolean isEmpty() { // Переопределение метода для проверки пустоты карты
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) { // Переопределение метода для проверки наличия ключа в карте
        Node n = search(head, (Integer)key); // Поиск узла с данным ключом
        if (n == null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean containsValue(Object value) { // Переопределение метода для проверки наличия значения в карте
        return false;
    }

    @Override
    public String get(Object key) { // Переопределение метода для получения значения по ключу
        Node n = search(head, (Integer)key);
        if (n == null) {
            return null;
        }
        return n.value; // Возвращает значение, если узел найден, иначе null
    }

    @Override
    public String put(Integer key, String value) { // Переопределение метода для вставки ключа и значения в карту
        Node n = search(head, key); // Переопределение метода для получения значения по ключу
        if (n == null) {
            head = insert(head, key, value); // Вставка нового узла
            size++;
            return null;
        }
        String prevValue = n.value; // Сохранение предыдущего значения
        n.value = value; // Обновление значения узла
        return prevValue;
    }

    @Override
    public String remove(Object key) { // Переопределение метода для удаления ключа и значения из карты
        Node n = search(head, (Integer) key); // Поиск узла с данным ключом
        if (n == null) {
            return null;
        }

        head = remove(head, (Integer) key); // Удаление узла
        size--;
        return n.value;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {  // Переопределение метода putAll для вставки всех элементов из другой карты

    }

    @Override
    public void clear() { // Переопределение метода для очистки карты
        Stack<Node> s = new Stack<Node>(); // Создание стека узлов
        Node lastVisited = null, curNode = head; // Инициализация переменных
        while (!s.isEmpty() || curNode != null) { // Пока стек не пуст или текущий узел не пуст
            if (curNode != null) {
                s.push(curNode); // Добавление узла в стек
                lastVisited = curNode; // Обновление последнего посещенного узла
                curNode = curNode.left;
            } else {
                curNode = s.pop(); // Извлечение узла из стека
                if (lastVisited != null && lastVisited != curNode) {
                    lastVisited.right = null; // Удаление ссылки на правого потомка
                }
                lastVisited = curNode; // Обновление последнего посещенного узла
                curNode.left = null; // Удаление ссылки на левого потомка
                curNode = curNode.right; // Переход к правому потомку
            }
        }
        head.left = null;
        head.right = null;
        head = null;
        size = 0;
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