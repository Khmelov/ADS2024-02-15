package by.it.group351003.lutai.lesson09;

import java.util.*;

public class ListC<E> implements List<E> {
    //Создайте аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ
    private int size = 10;
    private int currentIndex = 0;
    private Object[] elements;
    public ListC() {
        elements = new Object[size];
    }
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////              Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Iterator<E> it = ListC.this.iterator();
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
        if(currentIndex == elements.length){
            size = size + (size >> 1);
            elements = Arrays.copyOf(elements,size);
        }
        elements[currentIndex++] = e;
        return elements[currentIndex - 1] == e;
    }

    @Override
    public E remove(int index) {
        E removeVal;
        if(index < 0 || index > currentIndex - 1){
            removeVal = null;
        }else{
            removeVal = (E) elements[index];//0 1 2 3 4
            System.arraycopy(elements,index + 1,elements,index,currentIndex - index - 1);
            elements[currentIndex - 1] = null;
            currentIndex--;
        }
        return removeVal;
    }

    @Override
    public int size() {
        return currentIndex;
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Опциональные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    @Override
    public void add(int index, E element) {
        if(index < currentIndex && index > -1){
            if (currentIndex == size) {
                int newSize = elements.length * 2;
                elements = Arrays.copyOf(elements, newSize);
            }
            System.arraycopy(elements,index,elements,index + 1,currentIndex - index);
            elements[index] = element;
            currentIndex++;
        }
    }

    @Override
    public boolean remove(Object o) {
        Iterator<E> it = ListC.this.iterator();
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
        if(index > -1 && index < currentIndex){
            val = (E)elements[index];
            elements[index] = element;
        }
        return val;
    }


    @Override
    public boolean isEmpty() {
        return currentIndex == 0;
    }


    @Override
    public void clear() {
        if(currentIndex > 0){
            Arrays.fill(elements, 0, currentIndex, null);
            currentIndex = 0;
        }
    }

    @Override
    public int indexOf(Object o) {
        for(int i = 0;i < currentIndex;i++) {
            E val = (E)elements[i];
            if (val.equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public E get(int index) {
        E val = null;
        if(index > -1 && index < currentIndex){
            val = (E)elements[index];
        }
        return val;
    }

    @Override
    public boolean contains(Object o) {
        for(int i = 0;i < currentIndex;i++){
            if(elements[i].equals(o)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int lastIndexOf(Object o) {
        for(int i = currentIndex - 1;i > -1;i--){
            if(elements[i].equals(o)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        Set<Object> seen = new HashSet<>();
        for(int i = 0;i < currentIndex;i++){
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
            if(add((E)item)){
                isAdded = true;
            }
        }
        return isAdded;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        int prevSize = currentIndex;
        for(Object item : c){
            add(index++,(E)item);
        }
        return currentIndex - prevSize == c.size();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean isRemoved = false;
        for (int i = 0; i < currentIndex; i++) {
            if (c.contains(elements[i])) {
                remove(i);
                i--;
                isRemoved = true;
            }
        }
        return isRemoved;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean isRemoved = false;
        for (int i = 0; i < currentIndex; i++) {
            if (!c.contains(elements[i])) {
                remove(i);
                i--;
                isRemoved = true;
            }
        }
        return isRemoved;
    }


    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if(fromIndex < 0 || fromIndex > currentIndex - 1 || toIndex < 0 || toIndex > currentIndex - 1){
            return  null;
        }

        return new AbstractList<>() {
            @Override
            public E get(int index) {
                return (E) elements[fromIndex + index];
            }

            @Override
            public int size() {
                return toIndex - fromIndex;
            }
        };
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
                    ListC.this.remove(currentIndex--);
                }
            }

            @Override
            public void set(E e) {
                if(currentIndex > -1){
                    ListC.this.set(currentIndex,e);
                }
            }

            @Override
            public void add(E e) {
                ListC.this.add(e);
            }
        };
    }

    @Override
    public ListIterator<E> listIterator() {
        return ListC.this.listIterator(0);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if(a.length < currentIndex){
            a = (T[])Arrays.copyOf(elements,currentIndex);
        }
        System.arraycopy(elements,0,a,0,currentIndex);
        if(a.length > currentIndex){
            Arrays.fill(a,currentIndex,a.length,null);
        }
        return a;
    }

    @Override
    public Object[] toArray() {
        Object[] objects = new Object[currentIndex];
        System.arraycopy(elements, 0, objects, 0, currentIndex);
        return objects;
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
