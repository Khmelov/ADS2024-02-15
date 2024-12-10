package by.it.group351002.bob.lesson03;

import java.io.File;
import java.io.FileNotFoundException;
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


/*
Чтение входных данных: Программа считывает данные из файла, который содержит:

Количество различных букв kk, встречающихся в строке.
Размер закодированной строки ll.
Для каждой буквы буквы kk, записаны ее коды в формате "letter: code".
В последней строке содержится закодированная строка.
Формирование таблицы кодов: Программа создает карту (map),
где код каждой буквы сопоставляется с соответствующим символом.
Коды и символы считываются из файла и добавляются в карту.

Декодирование строки: Программа начинает разбирать закодированную строку, идя от начала до конца.
Она формирует временную строку str, путем добавления каждого бита из закодированной строки.
Затем программа проверяет, содержится ли данная временная строка в карте кодов.
Если она содержится, то соответствующий символ добавляется в результирующую строку result,
а временная строка очищается.
Этот процесс повторяется до тех пор, пока закодированная строка не будет полностью разобрана.

Вывод результата: Декодированная строка выводится на экран.
*/

public class B_Huffman {


    String decode(File file) throws FileNotFoundException {
        StringBuilder result=new StringBuilder();
        //прочитаем строку для кодирования из тестового файла
        Scanner scanner = new Scanner(file);
        int count = scanner.nextInt();
        int length = scanner.nextInt();
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < count; i++) {
            String elem = scanner.next();
            String numKey = scanner.next();
            map.put(numKey, String.valueOf(elem.charAt(0)));
        }

        String res = scanner.next();
        String str = "";
        for (int i = 0; i < length; i++){
            int ch = Integer.parseInt(String.valueOf(res.charAt(i)));
            str += ch;
            if(map.containsKey(str)) {
                result.append(map.get(str));
                str = "";
            }
        }
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
