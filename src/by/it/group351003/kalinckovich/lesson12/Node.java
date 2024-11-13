package by.it.group351003.kalinckovich.lesson12;

public class Node {
    int key;
    String value;
    Node left, right;
    int height;

    Node(int key, String value) {
        this.key = key;
        this.value = value;
        this.height = 1;
    }
}
