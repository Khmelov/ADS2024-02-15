package by.it.group351003.lutai.lesson03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//
public class B_Huffman {

    String decode(File file) throws FileNotFoundException {
        StringBuilder result=new StringBuilder();
        Scanner scanner = new Scanner(file);
        Integer count = scanner.nextInt();
        Integer length = scanner.nextInt();
        char[] charArr = new char[count];
        String[] codeArr = new String[count];
        for (int i = 0; i < count; i++) {
            String temp = scanner.next() + scanner.next();
            charArr[i] = temp.charAt(0);
            codeArr[i] = temp.substring(2);
        }
        String encodedStr = scanner.next();

        int i = 0;
        int charIndex = 0;
        while (i < encodedStr.length()) {
            if (encodedStr.charAt(i) == '0') {
                result.append(charArr[charIndex]);
                charIndex = 0;
            }
            else
                charIndex++;
            i++;
        }
        if (charIndex != 0)
            result.append(charArr[charIndex]);
        return result.toString();
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        File f = new File(root + "by/it/a_khmelev/lesson03/encodeHuffman.txt");
        B_Huffman instance = new B_Huffman();
        String result = instance.decode(f);
        System.out.println(result);
    }

}