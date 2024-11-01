package by.it.group351003.lutai.lesson10;


import java.util.*;

    public class MyArrayDeque<E> implements Deque<E> {

        private int capacity = 16;
        private E[] elements = (E[]) new Object[capacity];
        private int itemCount = 0;
        private int headIndex = 0;
        private int tailIndex = 0;

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append("[");
            for (int i = headIndex; i < tailIndex - 1; i++) {
                sb.append(elements[i]).append(", ");
            }
            sb.append(elements[tailIndex - 1]).append("]");
            return sb.toString();
        }

        @Override
        public int size() {
            return itemCount;
        }




        @Override
        public boolean add(E e) {
            boolean result;
                int temp = itemCount;
                addLast(e);
                result = itemCount > temp;
            return result;
        }

        @Override
        public void addFirst(E e) {
                if (itemCount + 1 == capacity) {
                    resize();
                }
                System.arraycopy(elements, headIndex, elements, headIndex + 1, itemCount);
                elements[headIndex] = e;
                itemCount++;
                tailIndex++;
        }

        @Override
        public void addLast(E e) {
                if (itemCount + 1 == capacity) {
                    resize();
                }
                elements[tailIndex] = e;
                itemCount++;
                tailIndex = (tailIndex + 1) % capacity;
        }





        @Override
        public E element() {
            return getFirst();
        }

        @Override
        public E getFirst() {
            E item = null;
            if(itemCount > 0){
                item = elements[headIndex];
            }
            return item;
        }

        @Override
        public E getLast() {
            E item = null;
            if(itemCount > 0){
                item = elements[tailIndex - 1];
            }
            return item;
        }





        @Override
        public E pollFirst() {
            E item = null;
                if(itemCount > 0){
                    item = elements[headIndex];
                    headIndex = (headIndex + 1) % capacity;
                    itemCount--;
                }
            return item;
        }

        @Override
        public E pollLast() {
            E item = null;
                if(itemCount > 0){
                    tailIndex = (tailIndex - 1 + capacity) % capacity;
                    item = elements[tailIndex];
                    itemCount--;
                }
            return item;
        }


        private void resize(){
            int newCapacity = capacity * 2;
            capacity = newCapacity;
            E[] newElements = (E[]) new Object[capacity];
            System.arraycopy(elements, 0, newElements, 0, itemCount);
            elements = newElements;
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
        public boolean offer(E e) {
            return false;
        }

        @Override
        public E remove() {
            return null;
        }

        @Override
        public E poll() {
            return pollFirst();
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
        public boolean isEmpty() {
            return false;
        }

        @Override
        public Iterator<E> iterator() {
            return new Iterator<E>() {
                private int currentIndex = 0;
                @Override
                public boolean hasNext() {
                    return currentIndex < itemCount;
                }

                @Override
                public E next() {
                    return elements[currentIndex++];
                }
            };
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
