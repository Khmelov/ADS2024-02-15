package by.it.group310901.tit.lesson03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

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
/*декодирование строки, закодированной с использованием алгоритма Хаффмана*/
    String decode(File file) throws FileNotFoundException {
        StringBuilder result=new StringBuilder();
        //прочитаем строку для кодирования из тестового файла
        /*Метод открывает файл и считывает данные, включая количество различных
         символов (count) и длину закодированной строки (length).*/
        Scanner scanner = new Scanner(file);
        Integer count = scanner.nextInt();
        Integer length = scanner.nextInt();
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! НАЧАЛО ЗАДАЧИ !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
        //тут запишите ваше решение
        /* - Для каждого символа считывается его код и сохраняется в массив codes,
   - Каждый символ и его код связываются для последующего декодирования.*/
        String[] codes = new String[count];
        for (int i = 0; i < count; i++) {
            String s = scanner.next();
            char ch = s.charAt(0);
            String code = scanner.next();
            codes[ch - 'a'] = code;
        }
        /* акодированная строка s считывается из файла.
   - Происходит итерация по символам закодированной строки для декодирования.
   - Для каждой подстроки сравнивается с массивом кодов codes, и
    при совпадении находится соответствующий символ и декодированный символ
     добавляется к результирующей строке.*/
        String s = scanner.next();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            sb.append(s.charAt(i));
            for (int j = 0; j < count; j++) {
                if (codes[j].equals(sb.toString())) {
                    result.append((char) (j + 'a'));
                    sb = new StringBuilder();
                    break;
                }
            }
        }
        /*Декодированная строка сохраняется в result и
        возвращается в виде строки с помощью result.toString().*/
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! КОНЕЦ ЗАДАЧИ !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
        return result.toString(); //01001100100111
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        File f = new File(root + "by/it/a_khmelev/lesson03/encodeHuffman.txt");
        B_Huffman instance = new B_Huffman();
        String result = instance.decode(f);
        System.out.println(result);
    }


}
