package by.it.group351003.kalinckovich.lesson11;

import java.util.Comparator;
import java.util.Objects;

public class MyLinkedList<E>{
    private Node<E> head = null;
    private Node<E> tail = null;
    private int size = 0;
    public void add(E e){
        Node<E> newNode = new Node<>(e,null);
        if(head == null){
            head = newNode;
            tail = head;
        }else{
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }
    public boolean remove(E e){
        if (head != null) {
            Node<E> temp = head;
            if (Objects.equals(temp.value,e)) {
                head = head.next;
                if(head == null){
                    tail = null;
                }
                size--;
                return true;
            }
            while (temp.next != null) {
                if (Objects.equals(temp.next.value,e)) {
                    if(temp.next == tail){
                        tail = temp;
                        tail.next = null;
                    }else{
                        temp.next = temp.next.next;
                    }
                    size--;
                    return true;
                }
                temp = temp.next;
            }
        }
        return false;
    }
    public E takeValue(E value){
        Node<E> temp = head;
        while (temp != null) {
            if (Objects.equals(value, temp.value)) {
                return temp.value;
            }
            temp = temp.next;
        }
        return null;
    }

    public void sort(Comparator<E> comparator) {
        head = listSort(head, comparator);

        // Обновление tail после сортировки
        Node<E> temp = head;
        while (temp != null && temp.next != null) {
            temp = temp.next;
        }
        tail = temp;
    }

    private Node<E> getMiddle(Node<E> node) {
        if (node == null) return null;
        Node<E> slow = node;
        Node<E> fast = node.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    private Node<E> sortedMerge(Node<E> left, Node<E> right, Comparator<E> comparator) {
        if (left == null) return right;
        if (right == null) return left;

        Node<E> result;
        if (comparator.compare(left.value, right.value) <= 0) {
            result = left;
            result.next = sortedMerge(left.next, right, comparator);
        } else {
            result = right;
            result.next = sortedMerge(left, right.next, comparator);
        }
        return result;
    }

    private Node<E> listSort(Node<E> head, Comparator<E> comparator) {
        if (head == null || head.next == null) return head;

        Node<E> middle = getMiddle(head);
        Node<E> nextToMiddle = middle.next;
        middle.next = null;

        Node<E> left = listSort(head, comparator);
        Node<E> right = listSort(nextToMiddle, comparator);

        return sortedMerge(left, right, comparator);
    }

    public int getSize(){
        return size;
    }

    public Node<E> getHead(){
        return head;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        Node<E> temp = head;
        while (temp != null) {
            sb.append(temp.value + ", ");
            temp = temp.next;
        }
        sb.delete(sb.length() - 2, sb.length());
        return sb.toString();
    }
}