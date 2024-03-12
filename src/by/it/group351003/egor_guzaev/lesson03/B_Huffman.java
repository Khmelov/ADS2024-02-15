package by.it.group351003.egor_guzaev.lesson03;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class B_Huffman {

    String decode(File file) throws FileNotFoundException {
        StringBuilder result = new StringBuilder();
        //прочитаем строку для кодирования из тестового файла
        Scanner scanner = new Scanner(file);
        int count = scanner.nextInt();
        int length = scanner.nextInt();

        //создадим индекс кодов символов
        Map<String, Character> codes = new HashMap<>();

        //прочитаем коды символов из файла и заполним индекс
        for (int i = 0; i < count; i++) {
            String line = scanner.nextLine();
            String[] parts = line.split(": ");
            String code = parts[1];
            char symbol = parts[0].charAt(0);
            codes.put(code, symbol);
        }

        //прочитаем закодированную строку из файла
        String encoded = scanner.nextLine();

        //декодируем строку с использованием индекса кодов символов
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int start = i;
            while (i < length && !codes.containsKey(encoded.substring(start, i + 1))) {
                i++;
            }
            char symbol = codes.get(encoded.substring(start, i + 1));
            sb.append(symbol);
            i = start + codes.get(encoded.substring(start, i + 1)).toString().length() - 1;
        }

        result.append(sb.toString());

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
