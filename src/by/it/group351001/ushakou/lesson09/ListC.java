package by.it.group351001.ushakou.lesson09;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

// Реализуем собственный список без использования стандартных коллекций Java.
// Это позволит лучше понять, как работают списки "под капотом" и продемонстрировать
// базовые операции со списками, такие как добавление, удаление, поиск и т.д.

public class ListC<E> implements List<E> {

    // Определяем константу для начального размера массива
    static final int defSize = 8;

    // Массив для хранения элементов списка
    E[] _list;

    // Текущая позиция (количество элементов в списке)
    int _curItem = 0;

    // Конструктор по умолчанию, создающий массив начального размера
    public ListC() {
        this(defSize);
    }

    // Конструктор с параметром размера массива
    public ListC(int size) {
        // Создаем массив объектов указанного размера
        _list = (E[]) new Object[size];
    }

    // Метод для преобразования списка в строку, аналог toString
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");  // Начинаем со скобки
        // Проходим по всем элементам
        for (int i = 0; i < _curItem; ++i) {
            E curSym = _list[i];  // Получаем текущий элемент
            sb.append(curSym);    // Добавляем его в строку
            if (i < _curItem - 1) {
                sb.append(", ");  // Если элемент не последний, добавляем запятую
            }
        }
        sb.append("]");  // Закрываем скобку
        return sb.toString();
    }

    // Добавление элемента в конец списка
    @Override
    public boolean add(E e) {
        // Если список заполнен, удваиваем его размер
        if (_curItem >= _list.length) {
            E[] _cList = (E[]) new Object[_list.length * 2];
            // Копируем старый массив в новый
            for (int i = 0; i < _list.length; ++i) {
                _cList[i] = _list[i];
            }
            _list = _cList;  // Заменяем старый массив новым
        }
        _list[_curItem] = e;  // Добавляем новый элемент в конец
        _curItem++;  // Увеличиваем счётчик элементов
        return true;
    }

    // Удаление элемента по индексу
    @Override
    public E remove(int index) {
        // Проверяем валидность индекса
        if (index < 0 || index >= _curItem) {
            return null;  // Если индекс некорректен, возвращаем null
        }

        E _rItem = _list[index];  // Сохраняем удаляемый элемент

        // Сдвигаем все элементы после удаляемого на одну позицию влево
        for (int i = index; i < _curItem - 1; ++i) {
            _list[i] = _list[i + 1];
        }

        _curItem--;  // Уменьшаем счётчик элементов
        return _rItem;  // Возвращаем удаленный элемент
    }

    // Возвращает размер списка
    @Override
    public int size() {
        return _curItem;
    }

    // Вставка элемента в указанную позицию
    @Override
    public void add(int index, E element) {
        // Проверяем валидность индекса
        if (index < 0 || index > _curItem) {
            return;
        }

        // Если список заполнен, увеличиваем его размер
        if (_curItem >= _list.length) {
            E[] _cList = (E[]) new Object[_list.length * 2];
            for (int i = 0; i < _list.length; ++i) {
                _cList[i] = _list[i];
            }
            _list = _cList;
        }

        // Сдвигаем все элементы вправо от позиции вставки
        for (int i = _curItem; i > index; i--) {
            _list[i] = _list[i - 1];
        }

        _list[index] = element;  // Вставляем элемент
        _curItem++;  // Увеличиваем размер списка
    }

    // Удаление элемента по значению
    @Override
    public boolean remove(Object o) {
        // Проходим по списку и ищем элемент
        for (int i = 0; i < _curItem; ++i) {
            if (o.equals(_list[i])) {
                E rItem = _list[i];
                // Сдвигаем элементы после найденного элемента на одну позицию влево
                for (int j = i; j < _curItem; ++j) {
                    _list[j] = _list[j + 1];
                }
                _curItem--;  // Уменьшаем счётчик
                return true;
            }
        }
        return false;
    }

    // Замена элемента в указанной позиции
    @Override
    public E set(int index, E element) {
        // Проверяем валидность индекса
        if (index < 0 || index > _curItem) {
            return null;
        }

        E _setItem = _list[index];  // Сохраняем заменяемый элемент
        _list[index] = element;  // Заменяем его на новый
        return _setItem;  // Возвращаем старый элемент
    }

    // Проверка на пустоту
    @Override
    public boolean isEmpty() {
        return _curItem == 0;
    }

    // Очистка списка
    @Override
    public void clear() {
        _list = (E[]) new Object[defSize];  // Создаём новый массив начального размера
        _curItem = 0;  // Обнуляем счётчик
    }

    // Возвращает индекс первого вхождения элемента
    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < _curItem; ++i) {
            if (o.equals(_list[i])) {
                return i;
            }
        }
        return -1;
    }

    // Возвращает элемент по индексу
    @Override
    public E get(int index) {
        // Проверяем валидность индекса
        if (index < 0 || index >= _curItem) {
            return null;
        }
        return _list[index];  // Возвращаем элемент
    }

    // Проверка наличия элемента в списке
    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < _curItem; ++i) {
            if (o.equals(_list[i])) {
                return true;
            }
        }
        return false;
    }

    // Возвращает индекс последнего вхождения элемента
    @Override
    public int lastIndexOf(Object o) {
        for (int i = _curItem - 1; i >= 0; --i) {
            if (o.equals(_list[i])) {
                return i;
            }
        }
        return -1;
    }

    // Проверка на наличие всех элементов другой коллекции
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object item : c) {
            if (!contains(c)) {
                return false;
            }
        }
        return true;
    }

    // Добавление всех элементов другой коллекции в список
    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E item : c) {
            if (!add(item)) {
                return false;
            }
        }
        return true;
    }

    // Добавление всех элементов другой коллекции в указанную позицию
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        for (Object item : c) {
            add(index, (E) item);
            index++;
        }
        return true;
    }

    // Удаление всех элементов, присутствующих в другой коллекции
    @Override
    public boolean removeAll(Collection<?> c) {
        boolean removed = false;
        for (int i = 0; i < _curItem; i++) {
            if (c.contains(_list[i])) {
                remove(i);
                i--;
                removed = true;
            }
        }
        return removed;
    }

    // Удаление всех элементов, которых нет в другой коллекции
    @Override
    public boolean retainAll(Collection<?> c) {
        boolean retained = false;
        for (int i = 0; i < _curItem; ++i) {
            if (!c.contains(_list[i])) {
                remove(i);
                i--;
                retained = true;
            }
        }
        return retained;
    }

    // Необязательные методы (не реализованы):
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
