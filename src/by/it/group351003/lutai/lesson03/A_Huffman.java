package by.it.group351003.lutai.lesson03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class A_Huffman {

    abstract class Node implements Comparable<Node> {

        private final int frequence;

        abstract void fillCodes(String code);

        //конструктор по умолчанию
        private Node(int frequence) {
            this.frequence = frequence;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(frequence, o.frequence);
        }
    }

    private class InternalNode extends Node {
        //внутренный узел дерева
        Node left;
        Node right;

        InternalNode(Node left, Node right) {
            super(left.frequence + right.frequence);
            this.left = left;
            this.right = right;
        }

        @Override
        void fillCodes(String code) {
            left.fillCodes(code + "0");
            right.fillCodes(code + "1");
        }

    }

    private class LeafNode extends Node {
        //лист
        char symbol; //символы хранятся только в листах

        LeafNode(int frequence, char symbol) {
            super(frequence);
            this.symbol = symbol;
        }

        @Override
        void fillCodes(String code) {
            codes.put(this.symbol, code);
        }
    }

    static private Map<Character, String> codes = new TreeMap<>();


    String encode(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        String s = scanner.next();


        Map<Character, Integer> count = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            if (!count.containsKey(s.charAt(i)))
                count.put(s.charAt(i), 1);
            else
                count.put(s.charAt(i), count.get(s.charAt(i)) + 1);
        }

        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        for (Character ch: count.keySet()) {
            Node node = new LeafNode(count.get(ch), ch);
            priorityQueue.add(node);
        }
        Node root = null;
        while (priorityQueue.size() > 1) {
            Node node1 = priorityQueue.remove();
            Node node2 = priorityQueue.remove();
            root = new InternalNode(node1, node2);
            priorityQueue.add(root);
        }
        StringBuilder sb = new StringBuilder();
        root.fillCodes("");
        //.....
        for (int i = 0; i < s.length(); i++)
            sb.append(codes.get(s.charAt(i)));
        return sb.toString();
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        File f = new File(root + "by/it/a_khmelev/lesson03/dataHuffman.txt");
        A_Huffman instance = new A_Huffman();
        long startTime = System.currentTimeMillis();
        String result = instance.encode(f);
        long finishTime = System.currentTimeMillis();
        System.out.printf("%d %d\n", codes.size(), result.length());
        for (Map.Entry<Character, String> entry : codes.entrySet()) {
            System.out.printf("%s: %s\n", entry.getKey(), entry.getValue());
        }
        System.out.println(result);
    }

}