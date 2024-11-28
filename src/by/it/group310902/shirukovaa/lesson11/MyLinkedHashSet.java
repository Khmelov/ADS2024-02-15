package by.it.group310902.shirukovaa.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyLinkedHashSet<E> implements Set<E> {

    private static class Node<Object> {
        private Object o; // Хранимое значение
        private Node next; // Ссылка на следующий узел
        private Node prev; // Ссылка на предыдущий узел

        Node(Node prev, Object o, Node next) {
            this.o = o; // Инициализация узла
            this.prev = prev;
            this.next = next;
        }
    }

    // Получение хэш-кода объекта
    private int getHashCode(Object o) {
        int hashcode = o == null ? 0 : o.hashCode();
        hashcode = hashcode < 0 ? -hashcode : hashcode;
        return hashcode % buckets.length; // Индекс в массиве buckets
    }

    // Изменение размера массива buckets
    private void resize() {
        Node[] tempBuckets = buckets;
        buckets = new Node[capacity *= 2]; // Увеличение емкости
        for (Node bucket : tempBuckets) {
            Node tempNode = bucket;
            while (tempNode != null) {
                add((E) tempNode.o); // Перемещение элементов
                tempNode = tempNode.next;
            }
        }
        needResize = false; // Сбрасываем флаг
    }

    private Node first = null, last = null;
    private Node[] buckets; // Массив для узлов
    private static int capacity = 16; // Начальная емкость
    private int length = 0; // Количество элементов
    private Boolean needResize = false; // Флаг изменения размера

    private final float DEFAULT_LOAD_FACTOR = 0.75f; // Фактор загрузки

    // Конструктор
    MyLinkedHashSet() {
        buckets = new Node[capacity];
    }

    // Переопределение toString
    public String toString() {
        StringBuilder strbldr = new StringBuilder();
        strbldr.append("[");
        Node temp = first;
        while (temp != null) {
            strbldr.append(temp.o + ", "); // Добавление элементов
            temp = temp.next;
        }

        int sbLength = strbldr.length();
        if (sbLength > 1)
            strbldr.delete(sbLength - 2, sbLength); // Удаление запятой

        strbldr.append("]");
        return strbldr.toString(); // Возвращение строки
    }

    @Override
    public int size() {
        return length; // Количество элементов
    }

    @Override
    public void clear() {
        capacity = 16; // Сброс емкости
        length = 0; // Обнуление длины
        buckets = new Node[capacity]; // Новый массив
        last = first = null; // Сброс указателей
    }

    @Override
    public boolean isEmpty() {
        return size() == 0; // Проверка на пустоту
    }

    @Override
    public boolean add(E e) {
        int h = getHashCode(e); // Индекс для нового элемента
        Node prevNode = null, tempNode = buckets[h];
        while (tempNode != null) {
            if (e.equals(tempNode.o))
                return false; // Элемент уже существует
            prevNode = tempNode;
            tempNode = tempNode.next;
        }

        // Добавление элемента в соответствующий бакет
        if (prevNode != null)
            prevNode.next = new Node(prevNode, e, null);
        else
            buckets[h] = new Node(null, e, null);

        // Добавление в связный список
        if (!needResize) {
            if (last == null) {
                last = new Node(null, e, null); // Первый элемент
                first = last;
            } else {
                last.next = new Node(last, e, null); // Добавление в конец
                last = last.next;
            }
            length++; // Увеличение длины

            // Проверка необходимости изменения размера
            if (length > capacity * DEFAULT_LOAD_FACTOR) {
                needResize = true;
                resize(); // Изменение размера
            }
        }

        return true; // Успешное добавление
    }

    @Override
    public boolean remove(Object o) {
        int h = getHashCode(o); // Индекс для удаления
        Node tempNode = buckets[h];

        // Поиск элемента для удаления
        while (tempNode != null) {
            if (tempNode.o.equals(o)) {
                // Обновление ссылок для удаления
                if (tempNode.prev == null) {
                    buckets[h] = tempNode.next; // Удаление первого элемента
                    if (buckets[h] != null)
                        buckets[h].prev = null;
                } else {
                    tempNode.prev.next = tempNode.next; // Удаление из середины
                    if (tempNode.next != null)
                        tempNode.next.prev = tempNode.prev; // Обновление ссылки
                }

                length--; // Уменьшение длины

                // Удаление из связного списка
                Node n = first;
                while (n != null) {
                    if (n.o.equals(o)) {
                        if (first == last)
                            first = last = null; // Единственный элемент
                        else if (n.prev != null) {
                            n.prev.next = n.next; // Удаление из середины
                            if (n.next != null)
                                n.next.prev = n.prev; // Обновление ссылки
                            else
                                last = n.prev; // Удаление последнего
                        } else {
                            first = first.next; // Удаление первого
                            if (first != null)
                                first.prev = null;
                        }

                        return true; // Успешное удаление
                    }
                    n = n.next; // Переход к следующему
                }
            }
            tempNode = tempNode.next; // Переход к следующему узлу в бакете
        }
        return false; // Элемент не найден
    }

    @Override
    public boolean contains(Object o) {
        int h = getHashCode(o); // Индекс для поиска
        Node tempNode = buckets[h];

        // Поиск элемента
        while (tempNode != null) {
            if (o.equals(tempNode.o))
                return true; // Элемент найден
            tempNode = tempNode.next; // Переход к следующему
        }
        return false; // Элемент не найден
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c)
            if (!contains(o))
                return false; // Не все элементы найдены
        return true; // Все элементы найдены
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (Object o : c) {
            if (o == null)
                return false; // Null не допускается
            add((E) o); // Добавление каждого элемента
        }
        return true; // Все элементы добавлены
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Boolean changed = false; // Флаг изменения
        Node n = first;
        while (n != null) {
            if (!c.contains(n.o)) { // Если элемент не в коллекции
                remove(n.o); // Удаление элемента
                changed = true; // Установка флага
            }
            n = n.next; // Переход к следующему
        }
        return changed; // Возвращение изменения
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        for (Object o : c) {
            if (o == null)
                return false; // Null не допускается
            remove(o); // Удаление элемента
        }
        return true; // Все элементы удалены
    }

    ///////////////////
    //// необязательные
    ///////////////////

    @Override
    public Iterator<E> iterator() {
        return null; // Метод не реализован
    }

    @Override
    public Object[] toArray() {
        return new Object[0]; // Метод не реализован
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null; // Метод не реализован
    }
}