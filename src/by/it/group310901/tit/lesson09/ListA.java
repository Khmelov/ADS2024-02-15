package by.it.group310901.tit.lesson09;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class ListA<E> implements List<E> {


    //Создайте аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ


    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////


    private E[] elements; // Массив для хранения элементов списка
    private int curInd = 0; // Текущий индекс, указывающий количество элементов
    private static final int INITIAL_SIZE = 8; // Начальный размер массива


    // Конструктор по умолчанию, инициализирует массив с начальным размером
    @SuppressWarnings("unchecked")
    public ListA() {
        elements = (E[]) new Object[INITIAL_SIZE];
    }


    // Конструктор, позволяющий указать пользовательский размер массива
    @SuppressWarnings("unchecked")
    public ListA(int size) {
        elements = (E[]) new Object[size];
    }

// Метод для строкового представления списка
@Override
public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append('[');
    for (int i = 0; i < curInd; i++) {
        sb.append(elements[i]);
        if (i < curInd - 1) {
            sb.append(", ");
        }
    }
    sb.append(']');
    return sb.toString();
}


    // Метод для добавления элемента в конец списка
    @Override
    public boolean add(E e) {
        ensureCapacity();
        elements[curInd++] = e;
        return true;
    }


    // Метод для проверки и увеличения размера массива, если необходимо
    private void ensureCapacity() {
        if (curInd == elements.length) {
            E[] tempElements = (E[]) new Object[elements.length * 2];
            System.arraycopy(elements, 0, tempElements, 0, elements.length);
            elements = tempElements;
        }
    }


    // Метод для удаления элемента по индексу
    @Override
    public E remove(int index) {
        if (index < 0 || index >= curInd) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + curInd);
        }
        E deletedElem = elements[index];
        System.arraycopy(elements, index + 1, elements, index, curInd - index - 1);
        curInd--;
        return deletedElem;
    }


    // Метод для получения текущего количества элементов в списке
    @Override
    public int size() {
        return curInd;
    }




    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Опциональные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////


    // Метод для добавления элемента по заданному индексу
    @Override
    public void add(int index, E element) {
        if (index < 0 || index > curInd) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + curInd);
        }
        ensureCapacity();
        System.arraycopy(elements, index, elements, index + 1, curInd - index);
        elements[index] = element;
        curInd++;
    }


    // Метод для удаления первого вхождения элемента
    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < curInd; i++) {
            if (o.equals(elements[i])) {
                remove(i);
                return true;
            }
        }
        return false;
    }


    // Метод для замены элемента по индексу
    @Override
    public E set(int index, E element) {
        if (index < 0 || index >= curInd) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + curInd);
        }
        E oldElement = elements[index];
        elements[index] = element;
        return oldElement;
    }


    // Метод для проверки, пуст ли список
    @Override
    public boolean isEmpty() {
        return curInd == 0;
    }


    // Метод для очистки списка
    @Override
    public void clear() {
        for (int i = 0; i < curInd; i++) {
            elements[i] = null;
        }
        curInd = 0;
    }


    // Метод для поиска индекса первого вхождения элемента
    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < curInd; i++) {
            if (o.equals(elements[i])) {
                return i;
            }
        }
        return -1;
    }


    // Метод для получения элемента по индексу
    @Override
    public E get(int index) {
        if (index < 0 || index >= curInd) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + curInd);
        }
        return elements[index];
    }


    // Метод для проверки, содержится ли элемент в списке
    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }


    // Метод для поиска индекса последнего вхождения элемента
    @Override
    public int lastIndexOf(Object o) {
        for (int i = curInd - 1; i >= 0; i--) {
            if (o.equals(elements[i])) {
                return i;
            }
        }
        return -1;
    }


    // Метод для проверки, содержатся ли все элементы из коллекции в списке
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object item : c) {
            if (!contains(item)) {
                return false;
            }
        }
        return true;
    }


    // Метод для добавления всех элементов из коллекции в список
    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for (E item : c) {
            if (add(item)) {
                modified = true;
            }
        }
        return modified;
    }




    // Метод для добавления всех элементов из коллекции по заданному индексу
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (index < 0 || index > curInd) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + curInd);
        }
        boolean modified = false;
        for (E item : c) {
            add(index++, item);
            modified = true;
        }
        return modified;
    }


    // Метод для удаления всех элементов из коллекции из списка
    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object item : c) {
            while (remove(item)) {
                modified = true;
            }
        }
        return modified;
    }


    // Метод для удаления всех элементов, которые не содержатся в коллекции
    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        for (int i = 0; i < curInd; i++) {// Если элемент не содержится в коллекции
            if (!c.contains(elements[i])) {
                remove(i--);
                modified = true;
            }
        }
        return modified;
    }


    // Метод для получения подсписка
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > curInd || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException("fromIndex: " + fromIndex + ", toIndex: " + toIndex + ", Size: " + curInd);
        }
        ListA<E> subList = new ListA<>(toIndex - fromIndex);
        for (int i = fromIndex; i < toIndex; i++) {
            subList.add(elements[i]);
        }
        return subList;
    }


    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }


    // Метод для получения итератора (по умолчанию с нулевого индекса)
    @Override
    public ListIterator<E> listIterator() {
        return listIterator(0);
    }


    // Метод для преобразования списка в массив
    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < curInd) {
            return (T[]) java.util.Arrays.copyOf(elements, curInd, a.getClass());
        }
        System.arraycopy(elements, 0, a, 0, curInd);
        if (a.length > curInd) {
            a[curInd] = null;
        }
        return a;
    }


    // Метод для преобразования списка в массив
    @Override
    public Object[] toArray() {
        return java.util.Arrays.copyOf(elements, curInd);
    }




    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    ////////        Эти методы имплементировать необязательно    ////////////
    ////////        но они будут нужны для корректной отладки    ////////////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////


    // Метод для получения итератора по элементам списка
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int currentIndex = 0;


            @Override
            public boolean hasNext() {
                return currentIndex < curInd;
            }


            @Override
            public E next() {
                return elements[currentIndex++];
            }
        };
    }


}
