package by.it.group310901.baradzin.lesson03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 * Восстановите строку по её коду и беспрефиксному коду символов
 *
 * В первой строке входного файла заданы два целых числа kk и ll через пробел — количество различных букв,
 * встречающихся в строке, и размер получившейся закодированной строки, соответственно.
 *
 * В следующих kk строках записаны коды букв в формате "letter: code". Ни один код не является префиксом другого.
 * Буквы могут быть перечислены в любом порядке. В качестве букв могут встречаться лишь строчные буквы латинского
 * алфавита; каждая из этих букв встречается в строке хотя бы один раз. Наконец, в последней строке записана
 * закодированная строка. Исходная строка и коды всех букв непустые. Заданный код таков, что закодированная строка
 * имеет минимальный возможный размер.
 *
 * Sample Input 1:
 * 1 1
 * a: 0
 * 0
 * Sample Output 1:
 * a
 *
 * Sample Input 2:
 * 4 14
 * a: 0
 * b: 10
 * c: 110
 * d: 111
 * 01001100100111
 * Sample Output 2:
 * abacabad
 */

public class B_Huffman {

    public static void main(String[] args) throws FileNotFoundException {
        var root = System.getProperty("user.dir") + "/src/";
        var f = new File(root + "by/it/group310901/baradzin/lesson03/encodeHuffman.txt");
        var instance = new B_Huffman();
        var result = instance.decode(f);
        System.out.println(result);
    }

    String decode(File file) throws FileNotFoundException {
        var result = new StringBuilder();
        // прочитаем строку для кодирования из тестового файла
        var scanner = new Scanner(file);
        Integer count = scanner.nextInt();
        Integer length = scanner.nextInt();
        ////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////////////////
        return result.toString();
    }
}
