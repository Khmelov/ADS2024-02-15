package by.it.group310901.silchenkoarina.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

//   Создайте class MyHashSet<E>, который реализует интерфейс Set<E>
//    и работает на основе массива с адресацией по хеш-коду
//    и односвязным списком для элементов с коллизиями
//    БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ
//
//    Метод toString() может выводить элементы в произвольном порядке
//    Формат вывода: скобки (квадратные) и разделитель (запятая с пробелом) должны
//    быть такими же как в методе toString() обычной коллекции

public class MyHashSet<E> implements Set<E> {
    private int size = 0;
    private MyNode<E>[] table = (MyNode<E>[])new MyNode[8]; //массив типа MyNode<E>[] (каждый элемент либо пустой,
    // либо содержит ссылку на связный список, если в эту ячейку помещен элемент)
    private int takenplacecount = 0;

    static private class MyNode<E>{ //структура для представления уза в связном списке
        E data;
        MyNode<E> next;
        MyNode(E e, MyNode next) {
            this.data = e;
            this.next = next;
        }
    }

    private int calchash(E e){
        return e.hashCode();
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String divider = "";
        sb.append("[");
        for(int i = 0; i < table.length; i++){
            MyNode e = table[i];
            while (e!=null) {
                sb.append(divider);
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

    @Override
    public void clear() {
        for(int i = 0; i < table.length; i++){
            MyNode e = table[i];
            while(e!=null){
                e.data = null;
                MyNode temp = e;
                e = e.next;
                temp.next = null;
            }
            table[i] = null;
        }
        table = (MyNode<E>[]) new MyNode[0];
        size = 0;
        takenplacecount = 0;
    }

    @Override
    public boolean isEmpty() {return size()==0;}

    private void resize(){
        MyNode<E>[] newtable = (MyNode<E>[])new MyNode[table.length==0?8:table.length*2];
        int newtakenplacecount = 0;
        for(int i = 0; i<table.length;i++){
            MyNode<E> e = table[i];
            while(e!=null){
                int pos = calchash(e.data) % newtable.length; //пересчет позиции элемента в новом массиве
                if (newtable[pos]==null){
                    newtable[pos] = new MyNode(e.data,null);
                    newtakenplacecount++;
                }else {
                    MyNode last = newtable[pos];
                    while (last.next != null)
                        last = last.next;
                    last.next = new MyNode(e.data,null);
                }
                e = e.next;
            }
        }
        int newsize = size;
        clear();
        table = newtable;
        size = newsize;
        takenplacecount = newtakenplacecount;
    }

    @Override
    public boolean add(Object o) {
        if (takenplacecount == table.length)
            resize();
        int pos = calchash((E)o) % table.length;
        if (table[pos]==null){
            table[pos] = new MyNode(o, null);
            takenplacecount++;
        }else{
            MyNode last = table[pos];
            while (last.next!=null) {
                if (last.data.equals(o))
                    return false;
                last = last.next;
            }
            if (last.data.equals(o))
                return false;
            last.next = new MyNode(o, null);
        }
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int pos = calchash((E)o) % table.length;
        MyNode prev = null;
        MyNode e = table[pos];
        while (e!=null){
            if (e.data.equals(o)) {
                if (prev == null) {
                    table[pos].data = null;
                    table[pos] = table[pos].next; //table[pos] переназначается на следующий узел e.next, и первый узел удаляется
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
    }
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