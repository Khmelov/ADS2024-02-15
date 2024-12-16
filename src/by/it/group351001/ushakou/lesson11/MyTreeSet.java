package by.it.group351001.ushakou.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

// Класс, реализующий структуру данных "Множество" с использованием бинарного дерева поиска
public class MyTreeSet<E extends Comparable<E>> implements Set<E> {

    // Вложенный класс для узлов бинарного дерева
    class Node {
        E data;  // Данные, хранящиеся в узле
        Node left;  // Левый дочерний узел
        Node right;  // Правый дочерний узел

        Node(E data) {
            this.data = data;  // Инициализация узла данными
        }
    }

    // Метод для представления множества в виде строки
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");  // Создаём строковый строитель для результата
        inOrderTraversal(_root, sb);  // Выполняем обход дерева в порядке "слева, корень, справа"
        return sb.append("]").toString();  // Возвращаем строку, представляющую множество
    }

    // Метод для обхода дерева в порядке "слева, корень, справа"
    void inOrderTraversal(Node node, StringBuilder sb) {
        if (node == null) return;  // Если узел пустой, выходим
        inOrderTraversal(node.left, sb);  // Рекурсивно обходим левое поддерево
        if (sb.length() > 1)  // Если не первый элемент, добавляем разделитель
            sb.append(", ");
        sb.append(node.data);  // Добавляем данные текущего узла
        inOrderTraversal(node.right, sb);  // Рекурсивно обходим правое поддерево
    }

    Node _root;  // Корень дерева
    int _count;  // Количество элементов в дереве

    // Метод для получения количества элементов в множестве
    @Override
    public int size() {
        return _count;  // Возвращаем количество элементов
    }

    // Метод для проверки, пустое ли множество
    @Override
    public boolean isEmpty() {
        return _count == 0;  // Если количество элементов равно 0, возвращаем true
    }

    // Метод для проверки, содержит ли дерево элемент
    boolean contains(Node node, E element) {
        if (node == null) return false;  // Если узел пустой, элемента нет
        int compare = element.compareTo(node.data);  // Сравниваем элементы
        if (compare < 0)  // Если элемент меньше текущего, ищем в левом поддереве
            return contains(node.left, element);
        else if (compare > 0)  // Если элемент больше текущего, ищем в правом поддереве
            return contains(node.right, element);
        else  // Если элементы равны, элемент найден
            return true;
    }

    // Публичный метод для проверки наличия элемента в дереве
    @Override
    public boolean contains(Object o) {
        return contains(_root, (E) o);  // Ищем элемент в дереве, начиная с корня
    }

    // Метод для вставки элемента в дерево
    Node insert(Node node, E element) {
        if (node == null)  // Если узел пустой, создаём новый
            return new Node(element);
        int compare = element.compareTo(node.data);  // Сравниваем элементы
        if (compare < 0)  // Если элемент меньше текущего, вставляем в левое поддерево
            node.left = insert(node.left, element);
        else if (compare > 0)  // Если элемент больше текущего, вставляем в правое поддерево
            node.right = insert(node.right, element);
        return node;  // Возвращаем изменённый узел
    }

    // Публичный метод для добавления элемента в множество
    @Override
    public boolean add(E e) {
        if (!contains(e)) {  // Если элемент не найден
            _root = insert(_root, e);  // Вставляем элемент в дерево
            _count++;  // Увеличиваем количество элементов
            return true;  // Возвращаем true, так как элемент был добавлен
        }
        return false;  // Возвращаем false, если элемент уже есть в дереве
    }

    // Метод для нахождения минимального элемента в поддереве
    Node findMin(Node node) {
        while (node.left != null) {  // Пока есть левый дочерний узел
            node = node.left;  // Переходим к левому узлу
        }
        return node;  // Возвращаем минимальный элемент
    }

    // Метод для удаления элемента из дерева
    Node delete(Node node, E element) {
        if (node == null) return null;  // Если узел пустой, возвращаем null
        int compare = element.compareTo(node.data);  // Сравниваем элементы
        if (compare < 0) {  // Если элемент меньше текущего, ищем в левом поддереве
            node.left = delete(node.left, element);
        } else if (compare > 0) {  // Если элемент больше текущего, ищем в правом поддереве
            node.right = delete(node.right, element);
        } else {  // Если элементы равны, удаляем текущий узел
            if (node.left == null) {  // Если нет левого поддерева, заменяем узел правым
                return node.right;
            } else if (node.right == null) {  // Если нет правого поддерева, заменяем узел левым
                return node.left;
            }
            node.data = findMin(node.right).data;  // Находим минимальный элемент в правом поддереве
            node.right = delete(node.right, node.data);  // Удаляем минимальный элемент из правого поддерева
        }
        return node;  // Возвращаем изменённый узел
    }

    // Публичный метод для удаления элемента из множества
    @Override
    public boolean remove(Object o) {
        if (contains(o)) {  // Если элемент найден
            _root = delete(_root, (E) o);  // Удаляем элемент из дерева
            _count--;  // Уменьшаем количество элементов
            return true;  // Возвращаем true, так как элемент был удалён
        }
        return false;  // Возвращаем false, если элемента нет в дереве
    }

    // Метод для проверки, содержатся ли все элементы из коллекции в дереве
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object obj: c) {  // Проходим по всем элементам коллекции
            if (!contains(obj))  // Если хотя бы один элемент не найден в дереве
                return false;  // Возвращаем false
        }
        return true;  // Возвращаем true, если все элементы найдены
    }

    // Метод для добавления всех элементов из коллекции в дерево
    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean isModified = false;  // Флаг для отслеживания изменений
        for (E element: c) {  // Проходим по всем элементам коллекции
            if (add(element))  // Если элемент был добавлен
                isModified = true;  // Отмечаем изменения
        }
        return isModified;  // Возвращаем true, если хотя бы один элемент был добавлен
    }

    // Метод для сохранения в дереве только тех элементов, которые присутствуют в коллекции
    @Override
    public boolean retainAll(Collection<?> c) {
        if (c.isEmpty()) {  // Если коллекция пуста
            this.clear();  // Очищаем дерево
            return true;  // Возвращаем true
        }
        boolean isModified = false;  // Флаг для отслеживания изменений
        MyTreeSet<E> retainSet = new MyTreeSet<>();  // Создаём временный набор для оставшихся элементов
        for (Object obj : c) {  // Проходим по всем элементам коллекции
            if (contains(obj)) {  // Если элемент найден в дереве
                retainSet.add((E) obj);  // Добавляем его во временный набор
                isModified = true;  // Отмечаем изменения
            }
        }
        _root = retainSet._root;  // Обновляем корень дерева
        _count = retainSet._count;  // Обновляем количество элементов

        return isModified;  // Возвращаем true, если изменения были
    }

    // Метод для удаления всех элементов из дерева
    @Override
    public boolean removeAll(Collection<?> c) {
        boolean isModified = false;  // Флаг для отслеживания изменений
        for (Object obj: c) {  // Проходим по всем элементам коллекции
            if (remove(obj))  // Если элемент был удалён
                isModified = true;  // Отмечаем изменения
        }
        return isModified;  // Возвращаем true, если хотя бы один элемент был удалён
    }

    // Метод для очистки дерева (удаления всех элементов)
    @Override
    public void clear() {
        _root = null;  // Удаляем корень дерева
        _count = 0;  // Устанавливаем количество элементов в 0
    }

    //////////////////////////////////////////////////////////////////////////////////////
    // Методы для работы с итераторами и преобразования в массивы
    @Override
    public Iterator<E> iterator() {
        return null;  // Пока не реализовано
    }

    @Override
    public Object[] toArray() {
        return new Object[0];  // Пока не реализовано
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;  // Пока не реализовано
    }
}
