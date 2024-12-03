package by.it.group310901.kanunnikava.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

    /*Создайте class MyTreeSet<E>, который реализует интерфейс Set<E>
    и работает на основе отсортированного массива (любым способом)
            БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ */

public class MyTreeSet<E extends Comparable> implements Set<E> {
    static class Node<E> { // Вложенный класс Node для представления узлов в дереве
        E item;
        Node<E> left, right; // Ссылки на левого и правого потомков

        public Node(E item) { // Конструктор класса Node
            this.item = item;
        }
    }

    int size = 0;
    Node<E> root = null; // Корневой узел дерева

    Node<E> search(E e) { // Метод для поиска узла в дереве
        Node<E> temp = root, prev = null; // Инициализация текущего и предыдущего узлов

        while (temp != null) {
            prev = temp; // Сохранение текущего узла как предыдущего
            if (temp.item.compareTo(e) == 0) // Если элемент найден
                return temp;
            if (e.compareTo(temp.item) > 0) { // Если искомый элемент больше текущего
                temp = temp.right; // Переход к правому потомку
            } else {
                temp = temp.left; // Переход к левому потомку
            }
        }
        return prev;
    }

    void reverse(Node<E> node, StringBuilder s){ // Метод для обратного обхода дерева
        if (node==null) // Если узел пуст
            return; // Завершение метода
        if (node.left!= null) // Если левый потомок не пуст
            reverse(node.left, s); // Рекурсивный вызов для левого потомка
        s.append(node.item).append(", "); // Добавление элемента в строку
        if (node.right!=null) // Если правый потомок не пуст
            reverse(node.right, s); // Рекурсивный вызов для правого потомка
    }
    @Override
    public String toString() { // Переопределение метода для получения строкового представления множества
        StringBuilder s = new StringBuilder();
        s.append("[");
        if(root!=null){ // Если корневой узел не пуст
            reverse(root, s); // Обратный обход дерева для добавления элементов в строку
            s.delete(s.length()-2, s.length()); // Удаление последней запятой и пробела
        }
        s.append("]");
        return s.toString();
    }

    @Override
    public int size() { // Переопределение метода для получения текущего размера множества
        return size;
    }

    @Override
    public boolean isEmpty() { // Переопределение метода для проверки пустоты множества
        return size == 0;
    }

    @Override
    public boolean contains(Object o) { // Переопределение метода для проверки наличия элемента в множестве
        Node<E> node = search((E) o); // Поиск узла
        return node.item.compareTo(o) == 0; // Возвращает true, если элемент найден
    }


    @Override
    public boolean add(E e) { // Переопределение метода add для добавления элемента в множество
        Node<E> node = search(e); // Поиск узла для вставки
        if (node == null) {
            root = new Node<>(e); // Создание нового корневого узла
        } else {
            if (node.item.compareTo(e) == 0) // Если элемент уже существует
                return false;
            if (e.compareTo(node.item) > 0) { // Если элемент больше текущего
                node.right = new Node<>(e); // Вставка в правого потомка
            } else {
                node.left = new Node<>(e); // Вставка в левого потомка
            }
        }
        size++;
        return true;
    }

    Node<E> remove(Node<E> root, E e) { // Метод для удаления узла из дерева
        if (root == null) { // Если корень пуст
            return root;
        }
        if (e.compareTo(root.item) < 0) { // Если элемент меньше текущего
            root.left = remove(root.left, e); // Удаление из левого поддерева
            return root;
        } else if (e.compareTo(root.item) > 0) { // Если элемент больше текущего
            root.right = remove(root.right, e); // Удаление из правого поддерева
            return root;
        }

        if (root.left == null) { // Если левый потомок пуст
            Node<E> temp = root.right; // Сохранение правого потомка
            return temp;
        } else if (root.right == null) { // Если правый потомок пуст
            Node<E> temp = root.left; // Сохранение левого потомка
            return temp;
        } else { // Если оба потомка не пусты
            Node<E> succParent = root; // Сохранение текущего узла как родителя приемника

            Node<E> succ = root.right; // Поиск минимального узла в правом поддереве
            while (succ.left != null) {
                succParent = succ; // Обновление родителя приемника
                succ = succ.left; // Переход к левому потомку
            }
            if (succParent != root) { // Если приемник не является правым потомком текущего узла
                succParent.left = succ.right; // Установка правого потомка приемника как левого потомка родителя приемника
            } else {
                succParent.right = succ.right; // Установка правого потомка приемника как правого потомка текущего узла
            }
            root.item = succ.item; // Замена элемента текущего узла на элемент приемника
            return root;
        }
    }

    @Override
    public boolean remove(Object o) { // Переопределение метода для удаления элемента из множества
        if (contains(o)) { // Если элемент найден
            root = remove(root, (E) o); // Удаление узла
            size--;
            return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) { // Переопределение метода для проверки наличия всех элементов коллекции в множестве
        for (Object o : c) { // Итерация по всем элементам коллекции
            if (!contains(o)) // Если хотя бы один элемент не найден
                return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) { // Переопределение метода для добавления всех элементов из коллекции в множество
        for (E o : c) { // Итерация по всем элементам коллекции
            add(o); // Добавление элемента
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) { // Переопределение метода для сохранения только тех элементов, которые содержатся в коллекции
        MyTreeSet<E> set = new MyTreeSet<>(); // Создание временного множества для хранения элементов коллекции
        int count = 0;
        for (Object o : c) {  // Итерация по всем элементам коллекции
            if (contains(o)) { // Если элемент найден в текущем множестве
                set.add((E) o); // Добавляем элемент во временное множество
                count++;
            }
        }
        size = count;
        root = set.root; // Обновляем корневой узел текущего множества
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) { // Переопределение метода для удаления всех элементов коллекции из множества
        if (c.isEmpty()) // Если коллекция пустая
            return false;
        for (Object o : c) { // Итерация по всем элементам коллекции
            if (contains(o)) // Если элемент найден
                remove(o); // Удаление элемента
        }
        return true;
    }

    @Override
    public void clear() { // Переопределение метода для очистки множества
        root=null;
        size=0;
    }
///////////////////////////////////////////////////////////////

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