/**
 * Класс MyTreeSet<E> — реализация множества с использованием бинарного дерева поиска (Binary Search Tree).
 * Данный класс поддерживает интерфейс Set и предоставляет основные операции для множества:
 * добавление, удаление, проверка наличия элемента и другие.
 *
 * Основные особенности реализации:
 * 1. Внутренний класс Node представляет узлы дерева, которые содержат данные и ссылки на левого и правого потомков.
 * 2. Корневой узел (_root) — это начальная точка дерева.
 * 3. Методы add и remove поддерживают свойства бинарного дерева поиска, где левое поддерево содержит
 *    меньшие элементы, а правое — большие.
 * 4. Метод contains реализует поиск элементов, используя рекурсивный подход, а также предусмотрена поддержка
 *    итераторов и работы с коллекциями (добавление всех элементов, удаление всех, пересечение).
 * 5. Метод toString() возвращает строковое представление множества в отсортированном порядке.
 *
 * Задание: Реализовать класс множества, обеспечивающий упорядоченное хранение элементов и поддерживающий
 * основные операции множества с использованием бинарного дерева поиска. Требуется также реализовать методы
 * для работы с коллекциями (retainAll, addAll, removeAll), а также перегрузить методы для проверки содержимого
 * и преобразования множества в строку.
 */

package by.it.group351001.shimanchuk.lesson11;
/**
 * Класс MyTreeSet<E> — реализация множества с использованием бинарного дерева поиска (Binary Search Tree).
 * Поддерживает интерфейс Set и предоставляет основные операции: добавление, удаление, проверка наличия элемента.
 *
 * Основные особенности реализации:
 * 1. Внутренний класс Node представляет узлы дерева с данными и ссылками на левого и правого потомков.
 * 2. Корневой узел (_root) — начальная точка дерева.
 * 3. Методы add и remove соблюдают свойства бинарного дерева, где левое поддерево содержит меньшие элементы,
 *    а правое — большие.
 * 4. Метод contains реализует рекурсивный поиск, поддерживаются итераторы и операции с коллекциями (addAll, removeAll, retainAll).
 * 5. Метод toString() возвращает строковое представление множества в отсортированном порядке.
 *
 * Задача: реализовать упорядоченное множество с бинарным деревом поиска и поддержкой основных операций.
 */
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyTreeSet<E extends Comparable<E>> implements Set<E> {
    // Внутренний класс Node представляет узел дерева
    class Node {
        E data;       // Данные элемента
        Node left;    // Левый потомок
        Node right;   // Правый потомок

        // Конструктор, инициализирующий узел с заданными данными
        Node(E data) {
            this.data = data;
        }
    }

    // Метод для представления множества в строковом виде в отсортированном порядке
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        inOrderTraversal(_root, sb); // Рекурсивный обход для сбора элементов
        return sb.append("]").toString();
    }

    // Рекурсивный метод для симметричного обхода дерева (in-order traversal) и добавления элементов в строку
    void inOrderTraversal(Node node, StringBuilder sb) {
        if (node == null) return;
        inOrderTraversal(node.left, sb); // Обход левого поддерева
        if (sb.length() > 1)
            sb.append(", ");
        sb.append(node.data);
        inOrderTraversal(node.right, sb); // Обход правого поддерева
    }

    Node _root;   // Корневой узел дерева
    int _count;   // Количество элементов в дереве

    // Возвращает количество элементов в множестве
    @Override
    public int size() {
        return _count;
    }

    // Проверяет, является ли множество пустым
    @Override
    public boolean isEmpty() {
        return _count == 0;
    }

    // Рекурсивный метод для проверки наличия элемента в дереве
    boolean contains(Node node, E element) {
        if (node == null) return false;
        int compare = element.compareTo(node.data);
        if (compare < 0)
            return contains(node.left, element); // Поиск в левом поддереве
        else if (compare > 0)
            return contains(node.right, element); // Поиск в правом поддереве
        else
            return true; // Элемент найден
    }

    // Проверка, содержит ли множество указанный элемент
    @Override
    public boolean contains(Object o) {
        return contains(_root, (E) o);
    }

    // Вставка нового элемента в дерево, с соблюдением порядка
    Node insert(Node node, E element) {
        if (node == null)
            return new Node(element); // Новый узел для вставки
        int compare = element.compareTo(node.data);
        if (compare < 0)
            node.left = insert(node.left, element); // Вставка в левое поддерево
        else if (compare > 0)
            node.right = insert(node.right, element); // Вставка в правое поддерево
        return node;
    }

    // Добавление элемента в множество
    @Override
    public boolean add(E e) {
        if (!contains(e)) {
            _root = insert(_root, e);
            _count++;
            return true;
        }
        return false;
    }

    // Поиск минимального значения в поддереве
    Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    // Удаление элемента из дерева
    Node delete(Node node, E element) {
        if (node == null) return null;
        int compare = element.compareTo(node.data);
        if (compare < 0) {
            node.left = delete(node.left, element);
        } else if (compare > 0) {
            node.right = delete(node.right, element);
        } else {
            // Если узел имеет только одного потомка или не имеет потомков
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }
            // Замена значения узла минимальным элементом правого поддерева
            node.data = findMin(node.right).data;
            node.right = delete(node.right, node.data);
        }
        return node;
    }

    // Удаление элемента из множества
    @Override
    public boolean remove(Object o) {
        if (contains(o)) {
            _root = delete(_root, (E) o);
            _count--;
            return true;
        }
        return false;
    }

    // Проверка, содержит ли множество все элементы коллекции
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object obj: c) {
            if (!contains(obj))
                return false;
        }
        return true;
    }

    // Добавление всех элементов из коллекции в множество
    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean isModified = false;
        for (E element: c) {
            if (add(element))
                isModified = true;
        }
        return isModified;
    }

    // Удаление всех элементов, не содержащихся в заданной коллекции
    @Override
    public boolean retainAll(Collection<?> c) {
        if (c.isEmpty()) {
            this.clear();
            return true;
        }
        boolean isModified = false;
        MyTreeSet<E> retainSet = new MyTreeSet<>();
        for (Object obj : c) {
            if (contains(obj)) {
                retainSet.add((E) obj);
                isModified = true;
            }
        }
        _root = retainSet._root;
        _count = retainSet._count;

        return isModified;
    }

    // Удаление всех элементов коллекции из множества
    @Override
    public boolean removeAll(Collection<?> c) {
        boolean isModified = false;
        for (Object obj: c) {
            if (remove(obj))
                isModified = true;
        }
        return isModified;
    }

    // Очищает множество
    @Override
    public void clear() {
        _root = null;
        _count = 0;
    }

    // Методы, которые могут быть доработаны или добавлены, такие как итераторы, преобразование в массивы
    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }
}
