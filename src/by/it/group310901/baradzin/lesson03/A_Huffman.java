package by.it.group310901.baradzin.lesson03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/*
 * Разработайте метод encode(File file) для кодирования строки (код Хаффмана)
 *
 * По данным файла (непустой строке ss длины не более 104104), состоящей из строчных букв латинского алфавита,
 * постройте оптимальный по суммарной длине беспрефиксный код.
 *
 * Используйте Алгоритм Хаффмана — жадный алгоритм оптимального безпрефиксного кодирования алфавита с минимальной
 * избыточностью.
 *
 * В первой строке выведите количество различных букв kk, встречающихся в строке, и размер получившейся
 * закодированной строки. В следующих kk строках запишите коды букв в формате "letter: code". В последней строке
 * выведите закодированную строку. Примеры ниже
 *
 * Sample Input 1:
 * a
 * Sample Output 1:
 * 1 1
 * a: 0
 * 0
 *
 * Sample Input 2:
 * abacabad
 * Sample Output 2:
 * 4 14
 * a: 0
 * b: 10
 * c: 110
 * d: 111
 * 01001100100111
 */

public class A_Huffman {

    /**
     * Индекс данных из листьев
     */
    static private final Map<Character, String> codes = new TreeMap<>();

    public static void main(String[] args) throws FileNotFoundException {
        var root = STR."\{System.getProperty("user.dir")}/src/";
        var f = new File(STR."\{root}by/it/group310901/baradzin/lesson03/dataHuffman.txt");
        var instance = new A_Huffman();
        var result = instance.encode(f);
        System.out.printf("%d %d\n", codes.size(), result.length());
        for (var entry : codes.entrySet())
            System.out.printf("%s: %s\n", entry.getKey(), entry.getValue());
        System.out.println(result);
    }

    String encode(File file) throws FileNotFoundException {
        var scanner = new Scanner(file);
        var input = scanner.next();

        var symbols = new HashMap<Character, Integer>();
        input.chars().forEach(symbol -> symbols.compute((char) symbol, (_, frequency) -> (frequency == null ? 0 : frequency) + 1));

        var priorityQueue = new PriorityQueue<Node>(symbols.size());
        symbols.forEach((symbol, frequency) -> priorityQueue.add(new LeafNode(symbol, frequency)));

        while (priorityQueue.size() > 1)
            priorityQueue.add(new InternalNode(priorityQueue.poll(), priorityQueue.poll()));

        priorityQueue.poll().fillCodes("");
        var sb = new StringBuilder();
        input.chars().forEach(c -> sb.append(codes.get((char) c)));
        return sb.toString();
    }

    /**
     * Абстрактный класс элемента дерева
     */
    abstract static class Node implements Comparable<Node> {
        private final int frequency;

        /**
         * Конструктор по умолчанию
         */
        private Node(int frequency) {
            this.frequency = frequency;
        }

        /**
         * Генерация кодов (вызывается на корневом узле после построения дерева)
         */
        abstract void fillCodes(String code);

        /**
         * Метод для корректной работы узла в приоритетной очереди или сортировки
         */
        @Override
        public int compareTo(Node o) {
            return Integer.compare(frequency, o.frequency);
        }
    }

    /**
     * Внутренний узел дерева
     */
    private class InternalNode extends Node {
        /**
         * Левый ребенок бинарного дерева
         */
        Node left;
        /**
         * Правый ребенок бинарного дерева
         */
        Node right;

        // для этого дерева не существует внутренних узлов без обоих детей
        InternalNode(Node left, Node right) {
            super(left.frequency + right.frequency);
            this.left = left;
            this.right = right;
        }

        @Override
        void fillCodes(String code) {
            left.fillCodes(STR."\{code}0");
            right.fillCodes(STR."\{code}1");
        }

    }

    /**
     * Лист для хранения символов
     */
    private class LeafNode extends Node {
        char symbol;

        LeafNode(char symbol, int frequency) {
            super(frequency);
            this.symbol = symbol;
        }

        @Override
        void fillCodes(String code) {
            // Добрались до листа, значит рекурсия закончена, код уже готов и можно запомнить его в индексе для
            // поиска кода по символу.
            codes.put(this.symbol, code);
        }
    }
}
