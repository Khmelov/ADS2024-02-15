package by.it.group351003.pulish.lesson11;

public class Node<E>{
    public Node<E> next;
    public E value;
    public Node(E value, Node<E> next) {
        this.value = value;
        this.next = next;
    }
}
