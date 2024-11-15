package by.it.group351003.lutai.lesson12;


public class RbNode {
    boolean color;
    RbNode left;
    RbNode right;
    RbNode parent;
    int key;
    String value;

    public RbNode(boolean color, int key, String value, RbNode parent) {
        this.color = color;
        this.key = key;
        this.value = value;
        this.parent = parent;
    }
}

