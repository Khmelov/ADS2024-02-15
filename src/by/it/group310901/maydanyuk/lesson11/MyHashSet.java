package by.it.group310901.maydanyuk.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
// имитирует поведение хэш набора
//класс реализует интерфейс Set<E> и и использует внутренние узлы для хранения элементов
public class MyHashSet<E> implements Set<E> {

    private int size = 0;
    private MyNode<E>[] table = (MyNode<E>[])new MyNode[8];//массив для хранения узлов набора
    private int takenplacecount = 0;//количество занятых позиций в массиве
//представляющий узел в хэш-наборе
    static private class MyNode<E>{
        E data;
        MyNode<E> next;
        MyNode(E e, MyNode next){
            this.data = e;
            this.next = next;//следующий узел
        }

    }
//метод для вычисления хэш-кода элемента
    private int calchash(E e){
        return e.hashCode();
    }
    //метод для представления набора в виде строки
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String divider = "";
        sb.append("[");
        for(int i = 0; i < table.length; i++){
            //Получаем текущий узел в массиве table по индексу i
            MyNode e = table[i];
            //пока текущий узел не равен null
            while (e!=null) {
                sb.append(divider);
                //Добавляем данные текущего узла в строку
                sb.append(e.data);
                divider = ", ";
                e = e.next;
            }
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
                MyNode temp = e;
                e = e.next;
                temp.next = null;
            }
            table[i] = null;
        }//Создается новый пустой массив для хранения узлов
        table = (MyNode<E>[]) new MyNode[0];
        size = 0;
        takenplacecount = 0;
    }
//пустой ли набор
    @Override
    public boolean isEmpty() {return size()==0;}
//метод для увеличения размера массива при достижении максимальной емкости
    private void resize(){
        MyNode<E>[] newtable = (MyNode<E>[])new MyNode[table.length==0?8:table.length*2];
        int newtakenplacecount = 0;
        for(int i = 0; i<table.length;i++){
            // Получаем текущий узел в таблице по индексу i
            MyNode<E> e = table[i];
            while(e!=null){
                // Вычисляем новую позицию узла в новой таблице
                int pos = calchash(e.data) % newtable.length;
                if (newtable[pos]==null){
                    // Создаем новый узел и добавляем его в новую таблицу
                    newtable[pos] = new MyNode(e.data,null);
                    newtakenplacecount++;
                }else {
                    MyNode last = newtable[pos];
                    while (last.next != null)
                        last = last.next;
                    // Добавляем новый узел в конец цепочки.
                    last.next = new MyNode(e.data,null);
                }
                e = e.next;
            }
        }// Сохраняем текущий размер набора перед очисткой
        int newsize = size;
        clear();
        table = newtable;
        size = newsize;
        takenplacecount = newtakenplacecount;
    }
//метод для добавления элемента в набор
    @Override
    public boolean add(Object o) {
        //Проверяем, равен ли счетчик занятых позиций длине таблицы
        if (takenplacecount == table.length)
            resize();
        //Метод calchash((E) o) вычисляет хэш-код элемента o
        int pos = calchash((E)o) % table.length;
        //Добавление нового узла, если по позиции нет узлов
        if (table[pos]==null){
            table[pos] = new MyNode(o, null);
            takenplacecount++;
        }else{
            //Если по позиции pos уже есть узлы, проходим по цепочке узлов до конца.
            MyNode last = table[pos];
            while (last.next!=null) {
                if (last.data.equals(o))
                    return false;
                last = last.next;
            }
            //Проверка последнего узла и добавление нового узла в конец цепочки
            if (last.data.equals(o))
                return false;
            last.next = new MyNode(o, null);
        }
        size++;
        return true;
    }
//метод для удаления элемента из набора
    @Override
    public boolean remove(Object o) {
        // Вычисляем позицию элемента в таблице, используя хэш-код и размер таблицы
        int pos = calchash((E)o) % table.length;
        MyNode prev = null;
        // Получаем первый узел в цепочке по вычисленной позиции
        MyNode e = table[pos];
        while (e!=null){
            // Проверяем, соответствует ли текущий узел удаляемому элементу
            if (e.data.equals(o)) {
                //Если текущий узел первый в цепочке (prev == null),
                // очищаем данные текущего узла и перемещаем указатель на следующий узел
                if (prev == null) {
                    table[pos].data = null;
                    table[pos] = table[pos].next;
                    //удаляем его из середины цепочки, разрывая ссылки
                } else {
                    e.data = null;
                    prev.next = e.next;
                    e.next = null;
                }
                size--;
                return true;
            }
            prev = e;
            e = e.next;
        }
        return false;
    }//для проверки наличия элемента в наборе
    @Override
    public boolean contains(Object o) {
        int pos = calchash((E)o) % table.length;
        MyNode e = table[pos];
        while (e!=null){
            if (e.data.equals(o)) {
                return true;
            }
            e = e.next;
        }
        return false;
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
    public boolean addAll(Collection c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection c) {
        return false;
    }

    @Override
    public boolean containsAll(Collection c) {
        return false;
    }

    @Override
    public Object[] toArray(Object[] a) {
        return new Object[0];
    }
}
