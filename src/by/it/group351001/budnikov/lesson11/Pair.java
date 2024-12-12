package by.it.group351001.budnikov.lesson11;

public class Pair<E> {
    private E first;
    private Integer second;

    public Pair(E first, Integer second) {
        this.first = first;
        this.second = second;
    }

    public E getFirst() {
        return first;
    }

    public Integer getSecond() {
        return second;
    }
}