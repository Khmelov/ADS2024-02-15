package by.it.group310901.tit.lesson11;





import java.util.Collection;
import java.util.Iterator;
import java.util.Set;


// Работает на основе массива с адресацией по хеш-коду и односвязным списком для элементов с коллизиями.
// Обрабатывает коллизии в хэш-таблице с помощью списка и изменяет размер хэш-таблицы, когда она становится слишком полной.
// Связанный список позволяет предсказать порядок итерации.
public class MyLinkedHashSet<E> implements Set<E> {
    private int size = 0; // Текущее количество элементов в наборе.
    final private int minsize = 8; // Минимальный размер хэш-таблицы.
    private MyNode<E>[] table = (MyNode<E>[]) new MyNode[minsize]; // Массив для хранения узлов.
    private MyNode<E> first, last; // Ссылки на первый и последний узлы для поддержания порядка вставки.


    // Узел, который используется как в связанных списках хэш-таблицы, так и в общем связанном списке порядка вставки.
    static private class MyNode<E> {
        E data; // Данные, хранящиеся в узле.
        MyNode<E> next; // Ссылка на следующий узел в хэш-таблице.
        MyNode<E> before, after; // Ссылки на предыдущий и следующий узлы в порядке вставки.


        MyNode(E e, MyNode next, MyNode before, MyNode after) {
            this.data = e; // Инициализация данных узла.
            this.next = next; // Инициализация ссылки на следующий узел.
            this.before = before; // Инициализация ссылки на предыдущий узел.
            this.after = after; // Инициализация ссылки на следующий узел в порядке вставки.
        }
    }


    // Вычисляет хэш-код элемента.
    private int calchash(E e) {
        return e.hashCode(); // Возвращает хэш-код элемента.
    }


    @Override
    // Итерация происходит в порядке вставки с использованием ссылок «после».
    public String toString() {
        StringBuilder sb = new StringBuilder(); // Строковый строитель для представления набора.
        String divider = ""; // Разделитель для элементов.
        sb.append("[");
        for (MyNode<E> x = first; x != null; x = x.after) { // Проход по связанному списку.
            sb.append(divider); // Добавление разделителя.
            sb.append(x.data); // Добавление данных узла.
            divider = ", "; // Установка разделителя на запятую.
        }
        sb.append("]");
        return sb.toString(); // Возвращает строковое представление набора.
    }


    @Override
    public int size() {
        return size; // Возвращает текущее количество элементов в наборе.
    }


    @Override
    public void clear() {
        // Очистка всех элементов в хэш-таблице и сброс состояния.
        for (int i = 0; i < table.length; i++) {
            MyNode e = table[i]; // Получение узла из таблицы.
            while (e != null) { // Проход по связанному списку.
                e.data = null; // Освобождение данных узла.
                e.before = null; // Освобождение ссылки на предыдущий узел.
                e.after = null; // Освобождение ссылки на следующий узел.
                MyNode temp = e; // Временная переменная для хранения узла.
                e = e.next; // Переход к следующему узлу.
                temp.next = null; // Освобождение ссылки на следующий узел.
            }
            table[i] = null; // Очистка текущей позиции в таблице.
        }
        table = (MyNode<E>[]) new MyNode[minsize]; // Сброс хэш-таблицы на минимальный размер.
        first = null; // Сброс ссылки на первый узел.
        last = null; // Сброс ссылки на последний узел.
        size = 0; // Обнуление счетчика элементов.
    }


    @Override
    public boolean isEmpty() {
        return size() == 0; // Проверка, пустой ли набор.
    }


    // Когда количество элементов становится равным емкости хэш-таблицы, вызывается для удвоения емкости.
    private void resize() {
        MyNode<E>[] newtable = (MyNode<E>[]) new MyNode[table.length * 2]; // Новый массив в два раза больше.
        for (MyNode<E> x = first; x != null; x = x.after) { // Проход по связанному списку.
            int pos = calchash(x.data) % newtable.length; // Вычисление нового индекса.
            if (newtable[pos] == null) { // Если ячейка пуста,
                newtable[pos] = x; // просто добавляем элемент.
            } else {
                MyNode end = newtable[pos]; // Получаем последний узел на данной позиции.
                while (end.next != null) // Находим конец списка.
                    end = end.next;
                end.next = x; // Добавляем узел в конец списка.
            }
            x.next = null; // Освобождаем ссылку на следующий узел.
        }
        table = newtable; // Обновляем ссылку на таблицу.
    }


    @Override
    public boolean add(Object o) {
        if (size == table.length) // Проверка, нужно ли изменять размер.
            resize(); // Увеличение размера.
        MyNode<E> newnode; // Новый узел для добавления.
        int pos = calchash((E) o) % table.length; // Вычисление позиции для нового элемента.
        if (table[pos] == null) { // Если ячейка пуста,
            newnode = new MyNode(o, null, last, null); // Создаем новый узел.
            table[pos] = newnode; // Добавляем узел в таблицу.
        } else {
            MyNode end = table[pos]; // Получаем первый узел на данной позиции.
            while (end.next != null) { // Проверяем наличие дубликатов.
                if (end.data.equals(o)) // Если элемент уже существует,
                    return false; // возвращаем false.
                end = end.next; // Переход к следующему узлу.
            }
            if (end.data.equals(o)) // Проверка последнего узла.
                return false; // Если элемент уже существует, возвращаем false.
            newnode = new MyNode(o, null, last, null); // Создаем новый узел.
            end.next = newnode; // Добавляем новый узел в конец списка.
        }
        if (first != null) // Если это не первый элемент,
            last.after = newnode; // обновляем ссылку на следующий элемент.
        else
            first = newnode; // Если первый элемент, устанавливаем его как первый.
        last = newnode; // Обновляем ссылку на последний узел.
        size++; // Увеличиваем счетчик элементов.
        return true; // Элемент успешно добавлен.
    }


    @Override
    public boolean remove(Object o) {
        int pos = calchash((E) o) % table.length; // Вычисление позиции для удаления.
        MyNode prev = null; // Предыдущий узел для удаления.
        MyNode e = table[pos]; // Получаем узел по хешу.
        while (e != null) { // Проход по связанному списку.
            if (e.data.equals(o)) { // Если элемент найден,
                if (prev == null) { // Если это первый элемент в списке,
                    table[pos] = table[pos].next; // обновляем таблицу.
                } else {
                    prev.next = e.next; // Удаляем узел из списка.
                    e.next = null; // Освобождаем ссылку на следующий узел.
                }


                MyNode<E> after = e.after; // Сохранение ссылки на следующий узел в порядке вставки.
                MyNode<E> before = e.before; // Сохранение ссылки на предыдущий узел в порядке вставки.
                if (after != null) { // Если есть следующий узел,
                    after.before = before; // обновляем ссылку на предыдущий узел.
                } else {
                    last = before; // Если это последний элемент, обновляем ссылку на последний узел.
                }
                if (before != null) { // Если есть предыдущий узел,
                    before.after = after; // обновляем ссылку на следующий узел.
                } else {
                    first = after; // Если это первый элемент, обновляем ссылку на первый узел.
                }
                e.after = null; // Освобождаем ссылки на предыдущий и следующий узлы.
                e.before = null;
                e.data = null; // Освобождаем данные узла.
                size--; // Уменьшаем счетчик элементов.
                return true; // Элемент успешно удалён.
            }
            prev = e; // Обновление ссылки на предыдущий узел.
            e = e.next; // Переход к следующему узлу.
        }
        return false; // Элемент не найден для удаления.
    }


    @Override
    public boolean contains(Object o) {
        int pos = calchash((E) o) % table.length; // Вычисление позиции для поиска.
        MyNode e = table[pos]; // Получаем узел по хешу.
        while (e != null) { // Проход по связанному списку.
            if (e.data.equals(o)) { // Если элемент найден,
                return true; // возвращаем true.
            }
            e = e.next; // Переход к следующему узлу.
        }
        return false; // Элемент не найден.
    }


    // Проверяет, содержит ли набор все элементы заданной коллекции.
    @Override
    public boolean containsAll(Collection c) {
        Object[] cArray = c.toArray(); // Преобразуем коллекцию в массив.
        for (int i = 0; i < cArray.length; i++) { // Проход по элементам массива.
            if (!contains(cArray[i])) // Если элемент не найден,
                return false; // возвращаем false.
        }
        return true; // Все элементы найдены.
    }


    @Override
    public boolean addAll(Collection c) {
        Object[] cArray = c.toArray(); // Преобразуем коллекцию в массив.
        if (cArray.length == 0) { // Если коллекция пуста,
            return false; // возвращаем false.
        }
        boolean result = false; // Переменная для отслеживания результата добавления.
        for (int i = 0; i < cArray.length; i++) { // Проход по элементам массива.
            result |= add(cArray[i]); // Добавляем элемент и обновляем результат.
        }
        return result; // Возвращаем результат добавления.
    }


    @Override
    public boolean removeAll(Collection c) {
        Object[] cArray = c.toArray(); // Преобразуем коллекцию в массив.
        if (cArray.length == 0) { // Если коллекция пуста,
            return false; // возвращаем false.
        }
        boolean result = false; // Переменная для отслеживания результата удаления.
        for (int i = 0; i < cArray.length; i++) { // Проход по элементам массива.
            result |= remove(cArray[i]); // Удаляем элемент и обновляем результат.
        }
        return result; // Возвращаем результат удаления.
    }


    // Сохраняет только те элементы набора, которые содержатся в указанной коллекции.
    @Override
    public boolean retainAll(Collection c) {
        MyNode<E> x = first; // Начинаем с первого узла.
        boolean result = false; // Переменная для отслеживания результата.
        while (x != null) { // Проход по списку.
            if (!c.contains(x.data)) { // Если элемент не содержится в коллекции,
                E tempE = x.data; // Сохраняем элемент для удаления.
                x = x.after; // Переход к следующему узлу.
                remove(tempE); // Удаляем элемент.
                result = true; // Обновляем результат.
            } else {
                x = x.after; // Переход к следующему узлу.
            }
        }
        return result; // Возвращаем результат.
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public Iterator iterator() {
        return null;
    }


    @Override
    public Object[] toArray() {
        return new Object[0];
    }


    @Override
    public Object[] toArray(Object[] a) {
        return new Object[0];
    }
}
