package by.it.group351003.lutai.lesson10;


import java.util.*;

public class MyPriorityQueue<E> implements Queue<E> {
    private int currentSize = 6;
    private E[] elements = (E[]) new Object[currentSize];
    private int itemCount = 0;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("[");
        if(itemCount > 0) {
            for (int i = 0; i < itemCount - 1; i++) {
                sb.append(elements[i]).append(", ");
            }
            sb.append(elements[itemCount - 1]);
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public int size() {
        return itemCount;
    }

    @Override
    public void clear() {
        itemCount = 0;
    }

    @Override
    public boolean add(E e) {
        int prevCount = itemCount;
        if (itemCount == currentSize) {
            resize();
        }

        elements[itemCount] = e;
        int current = itemCount;
        itemCount++;

        while (current > 0 && compare(elements[current], elements[getParent(current)]) < 0) {
            swap(current, getParent(current));
            current = getParent(current);
        }
        return itemCount > prevCount;
    }

    @Override
    public E remove() {
        E temp = null;
        if (itemCount > 0) {
            temp = elements[0];
            elements[0] = elements[itemCount - 1];
            elements[itemCount - 1] = null;
            itemCount--;
            minHeapify(0);
        }
        return temp;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < itemCount; i++) {
            if (elements[i].equals(o)) {
                return true;
            }
        }
        return false;
    }





    @Override
    public boolean offer(E e) {
        return add(e);
    }

    @Override
    public E poll() {
        return remove();
    }

    @Override
    public E peek() {
        E temp = null;
        if(itemCount > 0){
            temp = elements[0];
        }
        return temp;
    }

    @Override
    public E element() {
        return peek();
    }

    @Override
    public boolean isEmpty() {
        return itemCount == 0;
    }






    @Override
    public boolean containsAll(Collection<?> c) {
        for(Object o : c) {
            if(!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for (E element : c) {
            if (add(element)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean removed = false;
        E[] newHeap = (E[]) new Object[currentSize];
        int newSize = 0;
        for (int i = 0; i < itemCount; i++) {
            if (!c.contains(elements[i])) {
                newHeap[newSize++] = elements[i];  // Копируем только те элементы, которые не должны быть удалены
            } else {
                removed = true;
            }
        }
        elements = newHeap;  // Заменяем старый массив новым
        itemCount = newSize;
        for (int i = itemCount / 2 - 1; i >= 0; i--) {
            minHeapify(i);  // Восстанавливаем структуру Min-Heap
        }
        return removed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean retained = false;
        E[] newHeap = (E[]) new Object[currentSize];
        int newSize = 0;
        for (int i = 0; i < itemCount; i++) {
            if (c.contains(elements[i])) {
                newHeap[newSize++] = elements[i];  // Копируем только элементы, которые нужно сохранить
                retained = true;
            }
        }
        elements = newHeap;  // Заменяем старую кучу новой
        itemCount = newSize;
        for (int i = itemCount / 2 - 1; i >= 0; i--) {
            minHeapify(i);  // Восстанавливаем структуру Min-Heap
        }
        return true;
    }








    private void swap(int i, int j) {
        E temp = elements[i];
        elements[i] = elements[j];
        elements[j] = temp;
    }


    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = 0;
            @Override
            public boolean hasNext() {
                return index < itemCount;
            }

            @Override
            public E next() {
                return elements[index++];
            }
        };
    }
    @Override
    public boolean remove(Object o) {
        return false;
    }

    private int compare(E a, E b) {
        return ((Comparable<E>) a).compareTo(b);
    }

    private int getParent(int index) {
        return (index - 1) >> 1;
    }
    private int getLeftChild(int index) {
        return (index << 1) + 1;
    }
    private int getRightChild(int index) {
        return (index << 1) + 2;
    }
    private boolean isLeaf(int index) {
        return (index > (currentSize >> 1)) && (index < currentSize);
    }

    private void minHeapify(int index) {
        int leftChild = getLeftChild(index);
        int rightChild = getRightChild(index);
        int smallest = index;

        if (leftChild < itemCount && compare(elements[leftChild], elements[smallest]) < 0) {
            smallest = leftChild;
        }

        if (rightChild < itemCount && compare(elements[rightChild], elements[smallest]) < 0) {
            smallest = rightChild;
        }

        if (smallest != index) {
            swap(index, smallest);
            minHeapify(smallest);
        }
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }


    private void resize() {
        int updateSize = currentSize + (currentSize >> 1);
        E[] newElements = (E[]) new Object[updateSize];
        System.arraycopy(elements, 0, newElements, 0, itemCount);
        currentSize = updateSize;
        elements = newElements;
    }

}
