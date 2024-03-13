package by.it.group351002.stepanenko.lesson03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class B_Huffman {

    String decode(File file) throws FileNotFoundException {
        StringBuilder result = new StringBuilder();
        // Прочитаем строки из файла
        Scanner scanner = new Scanner(file);
        int count = scanner.nextInt();
        int length = scanner.nextInt();
        scanner.nextLine(); // Пропускаем пустую строку

        // Создаем словарь для хранения кодов символов
        Map<String, Character> codes = new HashMap<>();
        for (int i = 0; i < count; i++) {
            String line = scanner.nextLine();
            String[] parts = line.split(": ");
            char letter = parts[0].charAt(0);
            String code = parts[1];
            codes.put(code, letter);
        }

        // Читаем закодированную строку
        String encodedString = scanner.nextLine();

        // Декодируем строку
        StringBuilder currentCode = new StringBuilder();
        for (int i = 0; i < length; i++) {
            currentCode.append(encodedString.charAt(i));
            if (codes.containsKey(currentCode.toString())) {
                char decodedChar = codes.get(currentCode.toString());
                result.append(decodedChar);
                currentCode.setLength(0); // Очищаем текущий код
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