package by.it.group310901.kanunnikava.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/*Создайте class MyLinkedHashSet<E>, который реализует интерфейс Set<E>
    и работает на основе массива с адресацией по хеш-коду
            и односвязным списком для элементов с коллизиями
            БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ*/

public class MyLinkedHashSet<E> implements Set<E> {
    static class Node<E> { // Вложенный класс Node для представления узлов в хэш-таблице
        E item; // Элемент, хранимый в узле
        Node<E> next, before, after; // Ссылки на следующий узел, предыдущий узел и узел после

        public Node(E item) { // Конструктор класса Node
            this.item = item; // Инициализация элемента
        }
    }

    int INITIAL_CAPACITY = 160; // Начальная емкость хэш-таблицы
    float LOAD_FACTOR = 0.75f; // Коэффициент загрузки для увеличения емкости хэш-таблицы
    Node<E>[] array = new Node[INITIAL_CAPACITY]; // Массив узлов, представляющий хэш-таблицу
    int size = 0;
    private Node<E> head, tail; // Ссылки на первый и последний узлы

    int hash(E e, int length) { // Метод для вычисления хэш-кода элемента
        return e.hashCode() % length; // Возвращает индекс элемента, вычисленный по хэш-коду
    }

    void setHeadTail(Node<E> node) { // Метод для установки первого и последнего узлов
        if (head == null) {
            head = node; // Установка первого узла
        } else {
            tail.after = node; // Установка ссылки на следующий узел для последнего узла
            node.before = tail; // Установка ссылки на предыдущий узел для нового узла
        }
        tail = node; // Установка нового узла в качестве последнего
    }

    void resize() { // Метод для увеличения емкости хэш-таблицы
        Node<E>[] newArray = new Node[size * 2]; // Создание нового массива с увеличенной емкостью
        for (int i = 0; i < array.length; i++) { // Цикл по всем элементам старого массива
            while (array[i] != null) {
                int index = hash(array[i].item, newArray.length); // Вычисление нового индекса
                Node<E> newNode = new Node<>(array[i].item); // Создание нового узла
                if (newArray[index] == null)
                    newArray[index] = newNode; // Добавление нового узла
                else {
                    Node<E> temp = newArray[index]; // Временный узел для итерации
                    while (temp.next != null)
                        temp = temp.next; // Переход к следующему узлу
                    temp.next = newNode; // Установка нового узла
                }
                array[i] = array[i].next; // Переход к следующему узлу в старом массиве
            }
        }
        array = newArray;
    }

    @Override
    public String toString() { // Переопределение метода для получения строкового представления множества
        StringBuilder s = new StringBuilder();
        if (isEmpty())
            return "[]";
        s.append("[");
        var temp = head; // Временный узел, начиная с первого узла
        while (temp != null) {
            s.append(temp.item).append(", ");
            temp = temp.after; // Переход к следующему узлу
        }
        if (size != 0) {
            s.delete(s.length() - 2, s.length()); // Удаление последней запятой и пробела
        }
        s.append("]");
        return s.toString(); // Возвращение строки
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
        int index = hash((E) o, array.length); // Вычисление индекса на основе хэш-кода элемента

        if (array[index] == null)
            return false;
        else {
            Node<E> temp = array[index]; // Временный узел для итерации
            while (temp != null) {
                if (temp.item.equals(o)) // Если элемент найден
                    return true;
                temp = temp.next; // Переход к следующему узлу
            }
        }
        return false;
    }

    @Override
    public boolean add(E e) {  // Переопределение метода для добавления элемента в множество
        int index = hash(e, array.length);  // Вычисление индекса на основе хэш-кода элемента

        var node = new Node<>(e); // Создание нового узла
        if (array[index] == null) {
            array[index] = node; // Добавление нового узла
        } else {
            var temp = array[index]; // Временный узел для итерации
            while (temp.next != null) {
                if (temp.item.equals(e)) { // Если элемент уже существует
                    return false;
                }
                temp = temp.next; // Добавление нового узла
            }
            if (temp.item.equals(e)) // Если элемент уже существует
                return false;
            temp.next = node; // Добавление нового узла
        }
        setHeadTail(node); // Установка первого и последнего узлов
        if (++size > array.length * LOAD_FACTOR) { // Если размер превышает коэффициент загрузки
            resize(); // Увеличение емкости хэш-таблицы
        }
        return true;
    }
    void deleteFromList(Node<E> node){ // Метод для удаления узла из связного списка
        if (node.before==null && node.after==null){ // Если узел единственный
            head=null;
            tail=null;
            return;
        }
        if (node.before!=null && node.after!=null){ // Если узел в середине списка
            node.before.after=node.after; // Ссылка на следующий узел для предыдущего узла
            node.after.before=node.before; // Ссылка на предыдущий узел для следующего узла
        } else if (node.after == null) { // Если узел последний
            node.before.after=null;
            tail=node.before;
        } else if (node.before == null) { // Если узел первый
            node.after.before=null;
            head=node.after;
        }
    }
    @Override
    public boolean remove(Object o) { // Переопределение метода remove для удаления элемента из множества
        int index = hash((E) o, array.length); // Вычисление индекса на основе хэш-кода элемента

        if (array[index] == null)
            return false;
        else {
            Node<E> temp = array[index]; // Временный узел для итерации
            Node<E> prev = null; // Переменная для хранения предыдущего узла
            while (temp != null) {
                if (temp.item.equals(o)) { // Если элемент найден
                    if (prev != null) { // Если предыдущий узел не пуст
                        prev.next = temp.next; // Устанавливаем ссылку на следующий узел
                    } else {
                        array[index] = temp.next; // Устанавливаем следующий узел в начало массива
                    }
                    deleteFromList(temp); // Удаляем узел из связного списка
                    size--;
                    return true;
                }
                prev = temp; // Устанавливаем текущий узел как предыдущий
                temp = temp.next;
            }
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
        for (E o: c) { // Итерация по всем элементам коллекции
            add(o);
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) { // Переопределение метода для сохранения только тех элементов, которые содержатся в коллекции
        for (int i = 0; i < array.length; i++) { // Итерация по всем элементам массива
            if (array[i]!=null){
                var temp = array[i]; // Итерация по всем элементам массива
                while (temp!=null){
                    if(!c.contains(temp.item)) // Если элемент не содержится в коллекции
                        remove(temp.item); // Удаляем элемент
                    temp=temp.next;
                }
            }
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) { // Переопределение метода для удаления всех элементов коллекции из множества
        for (Object o : c) { // Итерация по всем элементам коллекции
            if (contains(o)) // Если элемент найден в множестве
                remove(o); // Удаляем элемент
        }
        return true;
    }

    @Override
    public void clear() { // Переопределение метода для очистки множества
        for (int i = 0; i < array.length; i++) {
            array[i]=null; // Устанавливаем элемент в null
        }
        head=null;
        tail=null;
        size=0;
    }

    /////////////////////////////////////////
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