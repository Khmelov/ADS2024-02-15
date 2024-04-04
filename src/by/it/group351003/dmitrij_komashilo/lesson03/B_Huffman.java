package by.it.group351003.dmitrij_komashilo.lesson03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class B_Huffman {

    String decode(File file) throws FileNotFoundException {
        StringBuilder result = new StringBuilder();
        Scanner scanner = new Scanner(file);
        int count = scanner.nextInt();
        int length = scanner.nextInt();
        scanner.nextLine(); // skip the newline character

        Map<String, Character> huffmanCodes = new HashMap<>();
        for (int i = 0; i < count; i++) {
            String line = scanner.nextLine();
            String[] parts = line.split(": ");
            huffmanCodes.put(parts[1], parts[0].charAt(0));
        }

        String encodedString = scanner.nextLine();
        String currentCode = "";

        for (char bit : encodedString.toCharArray()) {
            currentCode += bit;
            if (huffmanCodes.containsKey(currentCode)) {
                result.append(huffmanCodes.get(currentCode));
                currentCode = "";
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
