package by.it.group310901.maydanyuk.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
//который представляет собой набор с сохранением порядка добавления элементов
public class MyLinkedHashSet<E> implements Set<E> {
    private int size = 0;
    final private int minsize = 8;//минимальный размер массива
    //массив для хранения узлов набора
    private MyNode<E>[] table = (MyNode<E>[])new MyNode[minsize];
    private MyNode<E> first, last;
//внутренний класс, представляющий узел в хэш-наборе
    static private class MyNode<E>{
        E data;
        MyNode<E> next;
        MyNode<E> before, after;
        MyNode(E e, MyNode next, MyNode before, MyNode after){
            this.data = e;
            this.next = next;//ссылка на следующий узел в цепочке
            this.before = before;//ссылки на предыдущий и следующий узлы в связном списке
            this.after = after;
        }

    }
//метод для вычисления хэш-кода элемента
    private int calchash(E e){
        return e.hashCode();
    }
    @Override
    //метод для представления набора в виде строки
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String divider = "";
        sb.append("[");
        for(MyNode<E> x = first; x!=null; x = x.after){
            sb.append(divider);
            sb.append(x.data);
            divider = ", ";
        }
        sb.append("]");
        return sb.toString();
    }
    @Override
    public int size() {return size;}
    //метод для очистки набора
    @Override
    public void clear() {
        for(int i = 0; i < table.length; i++){
            // Получаем первый узел в связном списке по индексу i
            MyNode e = table[i];
            while(e!=null){
                // Очищаем данные текущего узла
                e.data = null;
                e.before = null;
                e.after = null;
                MyNode temp = e;
                e = e.next;
                temp.next = null;
            }
            table[i] = null;
        }//Создается новый пустой массив для хранения узлов
        table = (MyNode<E>[]) new MyNode[minsize];
        first = null;
        last = null;
        size = 0;
    }

    @Override
    public boolean isEmpty() {return size()==0;}
    //метод для увеличения размера массива при достижении максимальной емкости
    private void resize(){
        //Вычисляем позицию для текущего узла в новой таблице
        MyNode<E>[] newtable = (MyNode<E>[])new MyNode[table.length*2];
        for(MyNode<E> x = first; x!=null; x = x.after){
            int pos = calchash(x.data) % newtable.length;
            //Добавление текущего узла в новую таблицу
            if (newtable[pos]==null){
                newtable[pos] = x;
            }else{
                MyNode end = newtable[pos];
                while (end.next!=null)
                    end = end.next;
                end.next = x;
            }
            x.next = null;
        }//Устанавливаем новую таблицу (newtable) в качестве текущей таблицы
        table = newtable;
    }

    @Override
    public boolean add(Object o) {
        // Проверяем, достиг ли размер таблицы текущей емкости
        if (size == table.length)
            resize();// Увеличиваем размер таблицы, если это необходимо
        MyNode<E> newnode;
        // Вычисляем позицию для нового элемента
        int pos = calchash((E)o) % table.length;
        if (table[pos]==null){
            //добавляем новый узел и увеличиваем счетчик занятых позиций
            newnode = new MyNode(o, null, last, null);
            table[pos] = newnode;
        }else {
            // Иначе проходим по цепочке узлов до конца
            MyNode end = table[pos];
            while (end.next != null) {
                if (end.data.equals(o))
                    // Если элемент уже существует, возвращаем false
                    return false;
                end = end.next;
            }//равен ли последний элемент новому элементу
            if (end.data.equals(o))
                return false;
            // Добавляем новый узел в конец цепочки
            newnode = new MyNode(o, null, last, null);
            end.next = newnode;
        }// Устанавливаем связи в связном списке для сохранения порядка добавления
        if(first!=null)
            last.after = newnode;
        else
            first = newnode;
        last = newnode;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int pos = calchash((E)o) % table.length;
        MyNode prev = null;
        MyNode e = table[pos];
        // Пока текущий узел не равен null
        while (e!=null){
            // Проверяем, соответствует ли текущий узел удаляемому элементу
            if (e.data.equals(o)) {

                if (prev == null) {//удаление из таблицы
                    table[pos] = table[pos].next;
                } else {
                    prev.next = e.next;
                    e.next = null;
                }
// Устанавливаем связи в связном списке для сохранения порядка
                MyNode<E> after = e.after;///удаление из списка
                MyNode<E> before = e.before;
                if(after != null){
                    after.before = before;
                } else {
                    last = before;
                }
                if(before != null){
                    before.after = after;
                } else {
                    first = after;
                }// Очищаем ссылки и данные текущего узла
                e.after = null;
                e.before = null;
                e.data = null;
                size--;
                return true;
            }
            prev = e;
            e = e.next;
        }
        return false;
    }
    @Override
    public boolean contains(Object o) {
        int pos = calchash((E)o) % table.length;
        MyNode e = table[pos];
        while (e!=null){// Проверяем, соответствует ли текущий узел искомому элементу
            if (e.data.equals(o)) {
                return true;
            }// Переходим к следующему узлу в цепочке
            e = e.next;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection c) {
        // Преобразуем коллекцию в массив объектов
        Object []cArray = c.toArray();
        for(int i = 0;i < cArray.length;i++){
            if(!contains(cArray[i]))
                return false;
        }
        return true;
    }
    @Override
    public boolean addAll(Collection c) {
        // Преобразуем коллекцию в массив объектов
        Object []cArray = c.toArray();
        if(cArray.length == 0) {
            return false;
        }
        boolean result = false;
        for(int i = 0;i < cArray.length;i++){
            // Добавляем элемент в набор и обновляем результат
            result |= add(cArray[i]);
        }
        return result;
    }

    @Override
    public boolean removeAll(Collection c) {
        Object []cArray = c.toArray();
        if(cArray.length == 0) {
            return false;
        }
        boolean result = false;
        for(int i = 0;i < cArray.length;i++){
            result |= remove(cArray[i]);
        }
        return result;
    }

    @Override
    public boolean retainAll(Collection c) {
        MyNode<E> x = first;
        boolean result = false;
        while(x!=null){
            if(!c.contains(x.data)){
                E tempE = x.data;
                x = x.after;
                remove(tempE);
                result = true;
            }else {
                x = x.after;
            }
        }
        return result;
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

