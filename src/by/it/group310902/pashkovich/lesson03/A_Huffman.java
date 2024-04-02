package by.it.group310902.pashkovich.lesson03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class A_Huffman {

    // Абстрактный класс узла дерева
    abstract static class Node implements Comparable<Node> {
        final int frequency; // Частота символов

        // Метод для генерации кодов
        abstract void fillCodes(String code);

        // Конструктор узла
        private Node(int frequency) {
            this.frequency = frequency;
        }

        // Метод для сравнения узлов по частоте
        @Override
        public int compareTo(Node o) {
            return Integer.compare(frequency, o.frequency);
        }
    }

    // Класс внутреннего узла дерева
    private static class InternalNode extends Node {
        Node left;  // Левый потомок
        Node right; // Правый потомок

        // Конструктор внутреннего узла
        InternalNode(Node left, Node right) {
            super(left.frequency + right.frequency);
            this.left = left;
            this.right = right;
        }

        @Override
        void fillCodes(String code) {
            left.fillCodes(code + "0");
            right.fillCodes(code + "1");
        }

    }

    // Класс листового узла дерева
    private static class LeafNode extends Node {
        final char symbol; // Символ

        // Конструктор листового узла
        LeafNode(int frequency, char symbol) {
            super(frequency);
            this.symbol = symbol;
        }

        // Заполняем коды для листов
        @Override
        void fillCodes(String code) {
            codes.put(this.symbol, code);
        }
    }

    // Индекс данных из листьев
    private static Map<Character, String> codes = new TreeMap<>();

    // Метод для кодирования строки с использованием алгоритма Хаффмана
    public String encode(File file) throws FileNotFoundException {
        // Считываем строку для кодирования из файла
        Scanner scanner = new Scanner(file);
        String s = scanner.next();

        // Считаем частоту символов
        Map<Character, Integer> count = new HashMap<>();
        for (char c : s.toCharArray()) {
            count.put(c, count.getOrDefault(c, 0) + 1);
        }

        // Создаем приоритетную очередь для узлов дерева
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : count.entrySet()) {
            priorityQueue.offer(new LeafNode(entry.getValue(), entry.getKey()));
        }

        // Строим дерево Хаффмана
        while (priorityQueue.size() > 1) {
            Node left = priorityQueue.poll();
            Node right = priorityQueue.poll();
            priorityQueue.offer(new InternalNode(left, right));
        }

        // Генерируем коды символов
        Node root = priorityQueue.poll();
        root.fillCodes("");

        // Формируем закодированную строку
        StringBuilder encodedString = new StringBuilder();
        for (char c : s.toCharArray()) {
            encodedString.append(codes.get(c));
        }

        // Формируем результат
        StringBuilder result = new StringBuilder();
        result.append(count.size()).append(" ").append(encodedString.length()).append("\n");
        for (Map.Entry<Character, String> entry : codes.entrySet()) {
            result.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        result.append(encodedString);
        return result.toString();
    }

    // Метод main для тестирования
    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        File f = new File(root + "by/it/a_khmelev/lesson03/dataHuffman.txt");
        A_Huffman instance = new A_Huffman();
        String result = instance.encode(f);
        System.out.println(result);
    }
}
