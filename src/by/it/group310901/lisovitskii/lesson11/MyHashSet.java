package by.it.group310901.lisovitskii.lesson11;
//реализацию пользовательского класса MyHashSet, который работает как множество (Set) с использованием хеширования
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
//реализуем интерфейс
public class MyHashSet<E> implements Set<E> {//наподобие hash test
    //внутренний класс для узлов списка
    class Node<E> {
        E data;
        Node<E> next;//ссылка на след эл
        // конструктор узла
        Node(E data) {
            this.data = data;
        }
    }
    static final int defaultSize = 32;//размер
//массив, в котором хранятся элементы множества каждый элемент массива является связным списком
    Node<E>[] _items;//массив эл множества
    int _count;// колво  элементов
//коструктор по умолчанию с начальным размером
    public MyHashSet() {
        this(defaultSize);
    }
//конструктор с заданым размером
    public MyHashSet(int capacity) {
        _items = new Node[capacity];
    }
    //вычисляем хэш
    int GetHash(Object value) {
        return (value.hashCode() & 0x7FFFFFFF) % _items.length;
    }

    @Override
    //представляем множество в виде строки
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        boolean first = true;
        for (int i = 0; i < _items.length; i++) {
            Node<E> current = _items[i];
            while (current != null) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append(current.data);//добавляем данные в сторку
                first = false;
                current = current.next;//переходим к след эл
            }
        }

        sb.append("]");
        return sb.toString();
    }

    @Override
    public int size() {
        return _count;
    }//размер


    @Override
    public boolean isEmpty() {
        return _count == 0;
    }// проверяем пустое ли мн

    @Override
    public boolean contains(Object o) {//    // проверяет, содержит ли множество заданный элемент

        Node<E> current = _items[GetHash(o)];
        while (current != null) {
            if (current.data.equals(o)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public boolean add(E e) {//добавляем эл в мноэжетсво
        int index = GetHash(e);
        Node<E> current = _items[index];
        //проверка если эл есть
        while (current != null) {
            if (current.data.equals(e)) {
                return false;
            }
            current = current.next;
        }
        //добаваляем новый узел в начало списка
        Node<E> newNode = new Node<>(e);
        newNode.next = _items[index];
        _items[index] = newNode;
        _count++;
        if (_count > _items.length * 0.75) {//нужно ли увеличить размер массива ? если заполнено больше 75 проц
            resize();
        }
        return true;
    }
//увеличивает размер маассива и перераспределет эл
    void resize() {
        Node<E>[] newItems = new Node[_items.length * 2];
        for (int i = 0; i < _items.length; i++) {
            Node<E> current = _items[i];
            while (current != null) {
                Node<E> next = current.next;
                int newIndex = current.data.hashCode() & 0x7FFFFFFF % newItems.length;
                current.next = newItems[newIndex];
                newItems[newIndex] = current;
                current = next;
            }
        }
        _items = newItems;
    }
//удаляем эл из мн
    @Override
    public boolean remove(Object o) {
        int index = GetHash(o);
        Node<E> current = _items[index];
        Node<E> previous = null;
        while (current != null) {
            if (current.data.equals(o)) {
                if (previous == null) {
                    _items[index] = current.next;
                } else {
                    previous.next = current.next;
                }
                _count--;
                return true;
            }
            previous = current;
            current = current.next;
        }
        return false;
    }
//чистим мн
    @Override
    public void clear() {
        for (int i = 0; i < _items.length; i++)
            _items[i] = null;
        _count = 0;
    }


    ////////////////////////////////////////////////////////////////////////

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

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }
}