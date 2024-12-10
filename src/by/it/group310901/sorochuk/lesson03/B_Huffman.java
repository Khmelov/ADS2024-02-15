package by.it.group310901.sorochuk.lesson03;

import org.w3c.dom.Node;

import java.io.File;
import java.io.FileNotFoundException;
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
    //Считывается строка s из входного потока, которая, предположительно, закодирована с использованием Хаффмановского кода.
//Создается объект StringBuilder для накопления раскодированной строки.
//затем происходит итерация по всем символам в строке s.
// В каждой итерации добавляется символ к накопленной подстроке.
// Затем проверяется, совпадает ли накопленная подстрока с одним из кодов в массиве codes.
    String decode(File file) throws FileNotFoundException {
        StringBuilder result=new StringBuilder();
        //прочитаем строку для кодирования из тестового файла
        Scanner scanner = new Scanner(file);
        Integer count = scanner.nextInt();
        Integer length = scanner.nextInt();
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! НАЧАЛО ЗАДАЧИ !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
        //тут запишите ваше решение
//Создается массив строк codes длиной count, где count - это количество уникальных символов,
        // которые были закодированы.
        // Для каждого символа (предположительно алфавита a-z) считывается его код
        // из входного потока scanner и добавляется в массив codes.
        Node huffmanTree = NewNode('$');

        for (int i = 0; i < count; i++) {

            String z = scanner.next();
            String code = scanner.next();
            Node temp = huffmanTree;

            for (int j = 0; j < code.length() - 1; j++) {
                if (code.charAt(j) == '0') {
                    if (temp.left == null)
                        temp.left = NewNode('$');
                    temp = temp.left;

                } else {
                    if (temp.right == null)
                        temp.right = NewNode('$');
                    temp = temp.right;
                }
            }

            if (code.charAt(code.length() - 1) == '0')
                temp.left = NewNode(z.charAt(0));
            else
                temp.right = NewNode(z.charAt(0));
        }//Считывается закодированная строка s.
        //Создается пустой StringBuilder sb, в который будут добавляться символы в процессе декодирования.
        // Каждый символ добавляется в sb.
        //Проход по массиву codes для проверки, соответствует ли текущая подстрока sb
        // какому-либо коду из массива codes.
        //Если находится совпадение, символ, соответствующий найденному коду,
        // добавляется в результирующую строку result.
        String z = scanner.next();
        Node temp = huffmanTree;
        result = new StringBuilder();

        for (int i = 0; i < z.length(); i++) {
            if (z.charAt(i) == '0')
                temp = temp.left;
            else
                temp = temp.right;

            if ((temp.left == null) && (temp.right == null)) {
                result.append(temp.chr);
                temp = huffmanTree;

            }
        }

        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! КОНЕЦ ЗАДАЧИ !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
        return result.toString(); //01001100100111
    }

    public class Node {
        Node left;
        Node right;
        Character chr;
    }

    Node NewNode(Character chr) {
        Node a = new Node();
        a.chr = chr;
        a.left = null;
        a.right = null;
        return a;
    }

    void writeTree(Node a) {
        if (a.left != null) {
            System.out.print('0');
            writeTree(a.left);
        }
        System.out.print(a.chr);
        if (a.right != null) {
            System.out.print('1');
            writeTree(a.right);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        File f = new File(root + "by/it/a_khmelev/lesson03/encodeHuffman.txt");
        B_Huffman instance = new B_Huffman();
        String result = instance.decode(f);
        System.out.println(result);
    }


}
