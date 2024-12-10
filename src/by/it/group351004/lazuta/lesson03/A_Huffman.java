package by.it.group351004.lazuta.lesson03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
//Lesson 3. A_Huffman.
//Разработайте метод encode(File file) для кодирования строки (код Хаффмана)

// По данным файла (непустой строке ss длины не более 104104),
// состоящей из строчных букв латинского алфавита,
// постройте оптимальный по суммарной длине беспрефиксный код.

// Используйте Алгоритм Хаффмана — жадный алгоритм оптимального
// безпрефиксного кодирования алфавита с минимальной избыточностью.

// В первой строке выведите количество различных букв kk,
// встречающихся в строке, и размер получившейся закодированной строки.
// В следующих kk строках запишите коды букв в формате "letter: code".
// В последней строке выведите закодированную строку. Примеры ниже

//        Sample Input 1:
//        a
//
//        Sample Output 1:
//        1 1
//        a: 0
//        0

//        Sample Input 2:
//        abacabad
//
//        Sample Output 2:
//        4 14
//        a: 0
//        b: 10
//        c: 110
//        d: 111
//        01001100100111
//////////////////////////////////////////////////////////////












public class A_Huffman {

    //Изучите классы Node InternalNode LeafNode
    abstract class Node implements Comparable<Node> {
        private final int frequence;
        abstract void fillCodes(String code);
        private Node(int frequence) {
            this.frequence = frequence;
        }
        @Override
        public int compareTo(Node o) {
            return Integer.compare(frequence, o.frequence);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////
    //расширение базового класса до внутреннего узла дерева
    private class InternalNode extends Node {
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
        char symbol;
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
    ////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    String encode(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        String s = scanner.next();
        //все комментарии от тестового решения были оставлены т.к. это задание A.
        //если они вам мешают их можно удалить
        // окей, удаляю ибо мозолит глаза
        Map<Character, Integer> count = new HashMap<>();

        for (char c : s.toCharArray()) {
            count.put(c, count.getOrDefault(c, 0) + 1);
        }

        //2. перенесем все символы в приоритетную очередь в виде листьев
        PriorityQueue<Node> queue = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : count.entrySet()) {
            queue.add(new LeafNode(entry.getValue(), entry.getKey()));
        }

        while (queue.size() > 1) {
            Node left = queue.poll();
            Node right = queue.poll();
            queue.add(new InternalNode(left, right));
        }

        Node root = queue.poll();
        root.fillCodes("");
        //3. вынимая по два узла из очереди (для сборки родителя)
        //и возвращая этого родителя обратно в очередь
        //построим дерево кодирования Хаффмана.
        //У родителя частоты детей складываются.

        //4. последний из родителей будет корнем этого дерева
        //это будет последний и единственный элемент оставшийся в очереди queue.
        StringBuilder code = new StringBuilder();
        for (char c : s.toCharArray()) {
            code.append(codes.get(c));
        }
        return code.toString();
    }



    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        File f = new File(root + "by/it/group351004/lazuta/lesson03/dataHuffman.txt");
        A_Huffman instance = new A_Huffman();
        long startTime = System.currentTimeMillis();
        String result = instance.encode(f);
        long finishTime = System.currentTimeMillis();
        System.out.printf("%d %d\n", codes.size(), result.length());
        for (Map.Entry<Character, String> entry : codes.entrySet())
            System.out.printf("%s: %s\n", entry.getKey(), entry.getValue());
        System.out.println(result);
    }

}