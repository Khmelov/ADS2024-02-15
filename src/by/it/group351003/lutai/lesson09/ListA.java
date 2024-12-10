package by.it.group351003.lutai.lesson09;

import java.util.*;

public class ListA<E> implements List<E> {

    //Создайте аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ
    private int size = 10;
    private int sizeIndex = 0;
    private Object[] elements;
    public ListA() {
        elements = new Object[size];
    }
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Iterator<E> it = ListA.this.iterator();
        if(it.hasNext()){
            sb.append(it.next());
        }
        while(it.hasNext()){
            sb.append(",").append(" ").append(it.next());
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean add(E e) {
        if(sizeIndex == size){
            size += (size >> 1);
            elements = Arrays.copyOf(elements,size);
        }
        elements[sizeIndex++] = e;
        return elements[sizeIndex - 1] == e;
    }

    @Override
    public E remove(int index) {
        E removeVal;
        if(index < 0 || index > sizeIndex - 1){
            removeVal = null;
        }else{
            removeVal = (E) elements[index];
            System.arraycopy(elements,index + 1, elements,index, sizeIndex - index - 1);
            elements[sizeIndex - 1] = null;
            sizeIndex--;
        }
        return removeVal;
    }

    @Override
    public int size() {
        return sizeIndex;
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Опциональные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    @Override
    public void add(int index, E element) {
        if(index < sizeIndex && index > -1){
            if (sizeIndex == size) {
                int newSize = elements.length * 2;
                elements = Arrays.copyOf(elements, newSize);
            }
            System.arraycopy(elements,index, elements,index + 1, sizeIndex - index);
            elements[index] = element;
            sizeIndex++;
        }
    }

    @Override
    public boolean remove(Object o) {
        Iterator<E> it = ListA.this.iterator();
        int i = 0;
        while (it.hasNext()) {
            E removeVal = it.next();
            if ((o == null && removeVal == null) || (o != null && o.equals(removeVal))) {
                remove(i);
                return true;
            }
            i++;
        }
        return false;
    }

    @Override
    public E set(int index, E element) {
        E val = null;
        if(index > -1 && index < sizeIndex){
            elements[index] = element;
            val = element;
        }
        return val;
    }


    @Override
    public boolean isEmpty() {
        return sizeIndex == 0;
    }


    @Override
    public void clear() {
        if(sizeIndex > 0){
            Arrays.fill(elements, 0, sizeIndex, null);
            sizeIndex = 0;
        }
    }

    @Override
    public int indexOf(Object o) {
        Iterator<E> it = ListA.this.iterator();
        int index = 0;
        while(it.hasNext()){
            E val = (E) it.next();
            if(val.equals(o)){
                return index;
            }
            index++;
        }
        return -1;
    }

    @Override
    public E get(int index) {
        E val = null;
        if(index > -1 && index < sizeIndex){
            val = (E) elements[index];
        }
        return val;
    }

    @Override
    public boolean contains(Object o) {
        Iterator it = ListA.this.iterator();
        while(it.hasNext()){
            if(it.next().equals(o)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int lastIndexOf(Object o) {
        Iterator it = ListA.this.iterator();
        int index = -1;
        int i = 0;
        while(it.hasNext()){
            E val = (E) it.next();
            if(val.equals(o)){
                index = i;
            }
            i++;
        }
        return index;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        Set<Object> seen = new HashSet<>();
        for(int i = 0; i < sizeIndex; i++){
            for(Object item : c){
                if(item.equals(elements[i])){
                    seen.add(elements[i]);
                }
            }
        }
        return seen.size() == c.size();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean isAdded = false;
        for(Object item : c){
            isAdded = add((E)item);
        }
        return isAdded;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        int prevSize = sizeIndex;
        for(Object item : c){
            add(index,(E)item);
        }
        return sizeIndex - prevSize == c.size();
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
        return new ListIterator<>() {
            private int currentIndex = index;
            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            public E next() {
                return get(currentIndex++);
            }

            @Override
            public boolean hasPrevious() {
                return currentIndex > 0;
            }

            @Override
            public E previous() {
                E value = null;
                if(hasPrevious()){
                    value = get(currentIndex--);
                }
                return value;
            }

            @Override
            public int nextIndex() {
                return currentIndex + 1;
            }

            @Override
            public int previousIndex() {
                return currentIndex - 1;
            }

            @Override
            public void remove() {
                if(currentIndex > -1) {
                    ListA.this.remove(currentIndex--);
                }
            }

            @Override
            public void set(E e) {
                if(currentIndex > -1){
                    ListA.this.set(currentIndex,e);
                }
            }

            @Override
            public void add(E e) {
                ListA.this.add(e);
            }
        };
    }

    @Override
    public ListIterator<E> listIterator() {
        return ListA.this.listIterator(0);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    ////////        Эти методы имплементировать необязательно    ////////////
    ////////        но они будут нужны для корректной отладки    ////////////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private int currentIndex = 0;
            @Override
            public boolean hasNext() {
                return elements[currentIndex] != null;
            }

            @Override
            public E next() {
                E value = null;
                if(hasNext()) {
                    value = get(currentIndex++);
                }
                return value;
            }
        };
    }

}
