package by.it.group351003.zhuravski.lesson11;

public class SetObject<E> {
    public E val;
    public SetObject(E aVal) {
        val = aVal;
    }
    public String toString() {
        return val.toString();
    }
    public boolean compare(Object obj) {
        return val.equals(obj);
    }
};
