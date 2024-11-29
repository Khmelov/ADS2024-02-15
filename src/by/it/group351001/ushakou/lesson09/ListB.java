package by.it.group351001.ushakou.lesson09;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/*
 * Класс `ListB` является реализацией интерфейса `List<E>` с использованием массива для хранения элементов.
 * В данной реализации нельзя использовать стандартные классы коллекций Java.
 * Необходимо реализовать основные методы списка (add, remove, get, set и др.), чтобы можно было добавлять,
 * удалять, изменять и искать элементы. Помимо этого, стоит реализовать несколько дополнительных методов
 * интерфейса `List`, но не все из них являются обязательными для реализации.
 *
 * Основная логика заключается в следующем:
 * - Для хранения элементов используется массив `_list`.
 * - Если массив заполняется, его размер увеличивается в два раза.
 * - Важные методы, которые нужно реализовать: `add()`, `remove()`, `get()`, `set()`, `size()` и др.
 * - Методы, помеченные как "необязательные", могут быть реализованы в будущем для полной поддержки всех возможностей интерфейса `List`.
 *
 */

// Определение класса ListB с параметром типа E, который реализует интерфейс List<E>
public class ListB<E> implements List<E> {

    // Константа для хранения значения размера массива по умолчанию
    static final int defSize = 8;

    // Массив для хранения элементов списка
    E[] _list;

    // Счетчик текущего количества элементов в списке
    int _curItem = 0;

    // Конструктор без параметров, создающий массив дефолтного размера
    public ListB() {
        this(defSize);
    }

    // Конструктор с параметром для задания начального размера массива
    public ListB(int size)
    {
        _list = (E[]) new Object[size]; // Приведение массива Object к типу E
    }

    // Метод для преобразования списка в строку, например: "[element1, element2]"
    @Override
    public String toString() {
        StringBuilder sb= new StringBuilder();
        sb.append("[");
        for (int i = 0; i < _curItem; ++i) { // Цикл по всем элементам списка
            E curSym = _list[i];
            sb.append(curSym);
            if (i < _curItem - 1) {
                sb.append(", "); // Добавление запятых между элементами
            }
        }
        sb.append("]");
        return sb.toString();
    }

    // Метод для добавления элемента в конец списка
    @Override
    public boolean add(E e) {
        // Если массив заполнен, удваиваем его размер
        if (_curItem >= _list.length) {
            E[] _cList = (E[]) new Object[_list.length * 2];
            for (int i = 0; i < _list.length; ++i) {
                _cList[i] = _list[i];
            }
            _list = _cList; // Замена старого массива на новый
        }
        _list[_curItem] = e; // Добавление элемента в конец
        _curItem++;
        return true;
    }

    // Метод для удаления элемента по индексу
    @Override
    public E remove(int index) {
        if (index < 0 || index >= _curItem) {
            return null; // Если индекс вне границ массива, вернуть null
        }

        E _rItem = _list[index]; // Запоминаем элемент для возврата

        // Смещаем элементы, чтобы удалить элемент из массива
        for (int i = index; i < _curItem - 1; ++i) {
            _list[i] = _list[i + 1];
        }

        _curItem--; // Уменьшаем счетчик элементов
        return _rItem;
    }

    // Метод для возврата количества элементов в списке
    @Override
    public int size() {
        return _curItem;
    }

    // Метод для добавления элемента на конкретный индекс
    @Override
    public void add(int index, E element) {
        if (index < 0 || index > _curItem) {
            return; // Проверка на корректность индекса
        }

        // Если массив заполнен, удваиваем его размер
        if (_curItem >= _list.length) {
            E[] _cList = (E[]) new Object[_list.length * 2];
            for (int i = 0; i < _list.length; ++i) {
                _cList[i] = _list[i];
            }
            _list = _cList;
        }

        // Сдвигаем элементы, чтобы освободить место для нового элемента
        for (int i = _curItem; i > index; i--) {
            _list[i] = _list[i - 1];
        }

        _list[index] = element; // Вставляем элемент на нужное место
        _curItem++;
    }

    // Метод для удаления первого вхождения элемента
    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < _curItem; ++i) {
            if (o.equals(_list[i])) { // Сравниваем элементы с искомым
                for (int j = i; j < _curItem; ++j) {
                    _list[j] = _list[j + 1]; // Смещаем элементы
                }
                _curItem--; // Уменьшаем счетчик
                return true;
            }
        }
        return false;
    }

    // Метод для замены элемента по индексу
    @Override
    public E set(int index, E element) {
        if (index < 0 || index > _curItem) {
            return null; // Если индекс неверный, возвращаем null
        }

        E _setItem = _list[index]; // Запоминаем старый элемент
        _list[index] = element; // Заменяем на новый
        return _setItem; // Возвращаем старый элемент
    }

    // Метод для проверки, пуст ли список
    @Override
    public boolean isEmpty() {
        return _curItem == 0;
    }

    // Метод для очистки списка
    @Override
    public void clear() {
        _list = (E[]) new Object[defSize]; // Создаем новый массив
        _curItem = 0; // Обнуляем счетчик элементов
    }

    // Метод для поиска индекса первого вхождения элемента
    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < _curItem; ++i) {
            if (o.equals(_list[i])) {
                return i; // Возвращаем индекс, если элемент найден
            }
        }
        return -1; // Если не найдено, возвращаем -1
    }

    // Метод для получения элемента по индексу
    @Override
    public E get(int index) {
        if (index < 0 || index >= _curItem) {
            return null; // Если индекс неверный, возвращаем null
        }
        return _list[index]; // Возвращаем элемент
    }

    // Метод для проверки, содержит ли список элемент
    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < _curItem; ++i) {
            if (o.equals(_list[i])) {
                return true; // Если найдено, возвращаем true
            }
        }
        return false; // Если не найдено, возвращаем false
    }

    // Метод для поиска индекса последнего вхождения элемента
    @Override
    public int lastIndexOf(Object o) {
        for (int i = _curItem - 1; i >= 0; --i) {
            if (o.equals(_list[i])) {
                return i; // Возвращаем индекс последнего вхождения
            }
        }
        return -1; // Если не найдено, возвращаем -1
    }

    // Реализация опциональных методов для интерфейса List (они могут быть реализованы позже)

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

}

