package by.it.group310901.maydanyuk.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
//аналог двусторонней очереди (Deque) с использованием массива
// и без использования других классов стандартной библиотеки для управления очередями
public class MyArrayDeque<E> implements Deque<E> {

    private E[] elems= (E[]) new Object[]{};//элементы очереди
    private int size=0;//количество элементов
    private int head=0;//начало
    private int tail=0;//конец
//уменьшает значение индекса
    private int dec(int value){
        if (value==0){
            value=elems.length-1;//конец массива
        }
        else{
            value-=1;//уменьшается
        }
        return value;
    }//увеличивает значение индекса
    private int inc(int value){
        return (value+1)% elems.length;
    }
    //увеличивает размер массива, если он переполнен
    private void rebuildDeque(){
        int newSize=size*3/2+2;
        E[] newArr= (E[]) new Object[newSize];
        int i=head;
        int j=0;
        if (size!=0) {//Если очередь не пуста
            do {//Элементы копируются до тех пор, пока индекс не достигнет значения tail
                i = inc(i);
                newArr[j] = elems[i];
                j++;
            } while (i != tail);
            head=newSize-1;//Обновление индекса head до последней позиции нового массива
            tail=size-1;
        }
        else{
            tail = 0;
            head=0;
        }//Поле elems обновляется до ссылки на новый массив newArr, тем самым увеличивая емкость очереди
        elems=newArr;
    }
//возвращает строковое представление очереди
    @Override
    public String toString(){
        StringBuilder myStr = new StringBuilder("[");
        int i=head;
        String inBetweenSign="";
        do{
            i=inc(i);
            myStr.append(inBetweenSign).append(elems[i].toString());
            inBetweenSign=", ";
        } while(i!=tail);
        myStr.append("]");
        return myStr.toString();
    }
    @Override
    public void addFirst(E e) {
        if(size==elems.length) {
            rebuildDeque();//Если массив переполнен, вызывается метод rebuildDeque
        }//Элемент добавляется на позицию head, затем индекс head уменьшается
        elems[head]=e;
        head=dec(head);
        size++;
    }

    @Override
    public void addLast(E e) {
        if(size==elems.length){
            rebuildDeque();
        }//Индекс tail увеличивается, затем элемент добавляется на новую позицию tail
        tail=inc(tail);
        elems[tail]=e;
        size++;
    }

    @Override
    public boolean offerFirst(E e) {
        return false;
    }

    @Override
    public boolean offerLast(E e) {
        return false;
    }

    @Override
    public E removeFirst() {
        return null;
    }

    @Override
    public E removeLast() {
        return null;
    }
//Удаляет и возвращает первый элемент из очереди
    @Override
    public E pollFirst() {
        if(size==0) {
            return null;
        }
        size--;
        head=inc(head);
        E elem = elems[head];
        return elem;
    }

    @Override
    public E pollLast() {
        if(size==0){
            return null;
        }
        size--;
        E elem=elems[tail];
        tail=dec(tail);
        return elem;
    }

    @Override
    public E getFirst() {

        return elems[inc(head)];
    }

    @Override
    public E getLast() {
        return elems[tail];
    }

    @Override
    public E peekFirst() {
        return null;
    }

    @Override
    public E peekLast() {
        return null;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean add(E e) {
        if(size==elems.length){
            rebuildDeque();
        }
        tail=inc(tail);
        elems[tail]=e;
        size++;
        return true;
    }

    @Override
    public boolean offer(E e) {
        return false;
    }

    @Override
    public E remove() {
        return null;
    }

    @Override
    public E poll() {
        if(size==0){
            return null;
        }
        size--;
        head=inc(head);
        E elem=elems[head];
        return elem;
    }

    @Override
    public E element() {
        return elems[inc(head)];
    }

    @Override
    public E peek() {
        return null;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
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
    public void clear() {

    }

    @Override
    public void push(E e) {

    }

    @Override
    public E pop() {
        return null;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

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
    public Iterator<E> descendingIterator() {
        return null;
    }
}