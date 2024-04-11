package by.it.group351003.pulish.lesson03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class B_Huffman {

    String decode(File file) throws FileNotFoundException {
        StringBuilder result = new StringBuilder();

        // Считываем данные из файла
        Scanner scanner = new Scanner(file);
        int count = scanner.nextInt();
        int length = scanner.nextInt();
        scanner.nextLine();

        // Создаем словарь символов и их кодов
        Map<String, Character> codes = new HashMap<>();
        for (int i = 0; i < count; i++) {
            String[] line = scanner.nextLine().split(": ");
            char symbol = line[0].charAt(0);
            String code = line[1];
            codes.put(code, symbol);
        }

        // Считываем закодированную строку
        String encodedString = scanner.nextLine();

        // Декодируем закодированную строку
        StringBuilder currentCode = new StringBuilder();
        for (int i = 0; i < length; i++) {
            currentCode.append(encodedString.charAt(i));
            if (codes.containsKey(currentCode.toString())) {
                char symbol = codes.get(currentCode.toString());
                result.append(symbol);
                currentCode.setLength(0);
            }
        }

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
