package by.it.group310901.navrosuk.lesson11;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyTreeSet<E> implements Set<E> {

    // Массив для хранения элементов множества
    private Object[] mas = new Object[0];
    // Размер множества (количество элементов)
    private int actSize = 0;

    @Override
    public String toString() {
        // Если множество пустое, возвращаем "[]"
        if (isEmpty()) {
            return "[]";
        }
        // Строим строковое представление множества
        StringBuilder res = new StringBuilder("[");
        // Проходим по всем элементам и добавляем их в строку
        for (int i = 0; i < actSize - 1; i++) {
            res.append(mas[i].toString()).append(", ");
        }
        // Добавляем последний элемент без запятой
        return res + mas[actSize - 1].toString() + "]";
    }

    @Override
    public int size() {
        // Возвращаем количество элементов в множестве
        return actSize;
    }

    @Override
    public void clear() {
        // Очищаем множество: сбрасываем размер и создаём новый пустой массив
        actSize = 0;
        mas = new Object[0];
    }

    @Override
    public boolean isEmpty() {
        // Проверяем, пусто ли множество
        return actSize == 0;
    }

    @Override
    public boolean add(E e) {
        // Находим индекс, куда нужно вставить новый элемент
        int index = 0;
        while (index < actSize && ((Comparable<? super E>) mas[index]).compareTo(e) < 0) {
            index++;
        }

        // Если элемент уже существует в множестве, не добавляем его
        if (!isEmpty() && index < actSize && mas[index].equals(e)) {
            return false;
        }

        // Если массив полностью заполнен, увеличиваем его размер
        if (mas.length == actSize) {
            mas = Arrays.copyOf(mas, actSize * 2 + 1);
        }

        // Увеличиваем количество элементов
        actSize++;

        // Сдвигаем элементы вправо, чтобы освободить место для нового
        for (int i = actSize - 1; i > index; i--) {
            mas[i] = mas[i - 1];
        }

        // Вставляем элемент в нужное место
        mas[index] = e;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        // Находим индекс элемента, который нужно удалить
        int index = 0;
        while (index < actSize && !mas[index].equals(o))
            index++;

        // Если элемент не найден, возвращаем false
        if (index == actSize)
            return false;

        // Сдвигаем элементы влево, чтобы удалить элемент
        for (int i = index; i < size() - 1; i++)
            mas[i] = mas[i + 1];

        // Уменьшаем количество элементов
        actSize--;
        return true;
    }

    @Override
    public boolean contains(Object o) {
        // Проверяем, содержится ли элемент в множестве
        for (int i = 0; i < actSize; i++)
            if (mas[i].equals(o)) {
                return true;
            }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        // Проверяем, содержатся ли все элементы из коллекции c в множестве
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean retBull = false;
        // Добавляем все элементы из коллекции c в множество
        for (E o : c) {
            if (add(o)) {
                retBull = true;
            }
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean[] u = new boolean[actSize]; // удалить или не удалить элемент
        int kol = 0, cnt = 0;

        // Помечаем элементы, которые нужно удалить
        for (int i = 0; i < actSize; i++)
            if (c.contains(mas[i])) {
                u[i] = true;
                kol++;
            }

        // Если элементов для удаления нет, возвращаем false
        if (kol == 0)
            return false;

        // Создаём новый массив для хранения оставшихся элементов
        Object[] newArr = new Object[actSize - kol];
        for (int i = 0; i < actSize; i++)
            if (!u[i])
                newArr[cnt++] = mas[i];

        // Обновляем массив и количество элементов
        mas = newArr;
        actSize = actSize - kol;
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean[] u = new boolean[actSize]; // удалить или не удалить элемент
        int kol = 0, cnt = 0;

        // Помечаем элементы, которые нужно оставить
        for (int i = 0; i < actSize; i++)
            if (c.contains(mas[i])) {
                u[i] = true;
                kol++;
            }

        // Если все элементы уже в коллекции c, возвращаем false
        if (kol == actSize)
            return false;

        // Создаём новый массив для хранения оставшихся элементов
        Object[] newArr = new Object[kol];
        for (int i = 0; i < actSize; i++)
            if (u[i])
                newArr[cnt++] = mas[i];

        // Обновляем массив и количество элементов
        mas = newArr;
        actSize = kol;
        return true;
    }



    //опциональные методы
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
