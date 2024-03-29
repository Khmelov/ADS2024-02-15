package by.it.group351004.mukhin.lesson03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Lesson 3. B_Huffman.
// Восстановите строку по её коду и беспрефиксному коду символов.

// В первой строке входного файла заданы два целых числа
// kk и ll через пробел — количество различных букв, встречающихся в строке,
// и размер получившейся закодированной строки, соответственно.
//
// В следующих kk строках записаны коды букв в формате "letter: code".
// Ни один код не является префиксом другого.
// Буквы могут быть перечислены в любом порядке.
// В качестве букв могут встречаться лишь строчные буквы латинского алфавита;
// каждая из этих букв встречается в строке хотя бы один раз.
// Наконец, в последней строке записана закодированная строка.
// Исходная строка и коды всех букв непусты.
// Заданный код таков, что закодированная строка имеет минимальный возможный размер.
//
//        Sample Input 1:
//        1 1
//        a: 0
//        0

//        Sample Output 1:
//        a


//        Sample Input 2:
//        4 14
//        a: 0
//        b: 10
//        c: 110
//        d: 111
//        01001100100111

//        Sample Output 2:
//        abacabad

public class B_Huffman {

    String decode(File file) throws FileNotFoundException {
        StringBuilder stringBuilder = new StringBuilder();
        Scanner scan = new Scanner(file);
        Integer count = scan.nextInt();
        Integer length = scan.nextInt();

        scan.nextLine();

        Node node = getNode(count, scan);
        String input = scan.nextLine();

        int i = 0;
        while (i < input.length())
            i = node.getEncode(stringBuilder, node, "", input, i);

        return stringBuilder.toString();
    }

    private static class Node {
        String string;
        char letter;
        Node left;
        Node right;
        Node (String string, char letter) {
            this.string = string;
            this.letter = letter;
        }
        Node (Node left, Node right) {
            this.left = left;
            this.right = right;
        }
        int getEncode (StringBuilder stringBuilder, Node node, String temp, String input, int i) {
            if (node.string != null && node.string.equals(temp)) {
                stringBuilder.append(node.letter);
                return i;
            } else
                i = (input.charAt(i) == '1') ? node.right.getEncode(stringBuilder, node.right, temp + input.charAt(i), input, ++i) : node.left.getEncode(stringBuilder, node.left,temp + input.charAt(i), input, ++i);
            return i;
        }
    }

    public Node getNode(Integer count, Scanner scan) {

        ArrayList<Node> nodeList = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            String[] stringArr = scan.nextLine().split(": ");
            nodeList.add(new Node(stringArr[1], stringArr[0].charAt(0)));
        }

        while (nodeList.size() > 1) {
            Node parentNode = new Node(nodeList.remove(nodeList.size() - 2), nodeList.remove(nodeList.size() - 1));
            nodeList.add(parentNode);
        }

        return nodeList.get(0);
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        File f = new File(root + "by/it/a_khmelev/lesson03/encodeHuffman.txt");
        B_Huffman instance = new B_Huffman();
        String result = instance.decode(f);
        System.out.println(result);
    }


}
