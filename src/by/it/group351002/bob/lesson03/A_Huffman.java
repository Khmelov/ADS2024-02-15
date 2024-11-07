package by.it.group351002.bob.lesson03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

//Lesson 3. A_Huffman.
//Разработайте метод encode(File file) для кодирования строки (код Хаффмана)

// По данным файла (непустой строке ss длины не более 104104),
// состоящей из строчных букв латинского алфавита,
// постройте оптимальный по суммарной длине беспрефиксный код.

// Используйте Алгоритм Хаффмана — жадный алгоритм оптимального
// безпрефиксного кодирования алфавита с минимальной избыточностью.

// В первой строке выведите количество различных букв kk,
// встречающихся в строке, и размер получившейся закодированной строки.
// В следующих kk строках запишите коды букв в формате "letter: code".
// В последней строке выведите закодированную строку. Примеры ниже

//        Sample Input 1:
//        a
//
//        Sample Output 1:
//        1 1
//        a: 0
//        0

//        Sample Input 2:
//        abacabad
//
//        Sample Output 2:
//        4 14
//        a: 0
//        b: 10
//        c: 110
//        d: 111
//        01001100100111

public class A_Huffman {

    //Изучите классы Node InternalNode LeafNode
    abstract class Node implements Comparable<Node> {
        //абстрактный класс элемент дерева
        //(сделан abstract, чтобы нельзя было использовать его напрямую)
        //а только через его версии InternalNode и LeafNode
        private final int frequence; //частота символов

        //генерация кодов (вызывается на корневом узле
        //один раз в конце, т.е. после построения дерева)
        abstract void fillCodes(String code);

        //конструктор по умолчанию
        private Node(int frequence) {
            this.frequence = frequence;
        }

        //метод нужен для корректной работы узла в приоритетной очереди
        //или для сортировок
        @Override
        public int compareTo(Node o) {
            return Integer.compare(frequence, o.frequence);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////
    //расширение базового класса до внутреннего узла дерева
    private class InternalNode extends Node {
        //внутренный узел дерева
        Node left;  //левый ребенок бинарного дерева
        Node right; //правый ребенок бинарного дерева

        //для этого дерева не существует внутренних узлов без обоих детей
        //поэтому вот такого конструктора будет достаточно
        InternalNode(Node left, Node right) {
            super(left.frequence + right.frequence);
            this.left = left;
            this.right = right;
        }

        @Override
        void fillCodes(String code) {
            left.fillCodes(code + "0");
            right.fillCodes(code + "1");
        }

    }

    ////////////////////////////////////////////////////////////////////////////////////
    //расширение базового класса до листа дерева
    private class LeafNode extends Node {
        //лист
        char symbol; //символы хранятся только в листах

        LeafNode(int frequence, char symbol) {
            super(frequence);
            this.symbol = symbol;
        }

        @Override
        void fillCodes(String code) {
            //добрались до листа, значит рекурсия закончена, код уже готов
            //и можно запомнить его в индексе для поиска кода по символу.
            codes.put(this.symbol, code);
        }
    }

    //индекс данных из листьев
    static private Map<Character, String> codes = new TreeMap<>();


    //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!

/* Чтение строки из файла: Программа считывает строку для кодирования из файла, предполагая,
что строка содержит только строчные буквы латинского алфавита.

Подсчет частоты встречаемости символов:
Создается словарь count,
в котором для каждого символа вычисляется частота его встречаемости в строке.

Построение дерева Хаффмана:

Создается приоритетная очередь priorityQueue,
в которую добавляются листья дерева (LeafNode), соответствующие каждому символу из словаря count.

Затем из очереди извлекаются два узла с наименьшей частотой,
они объединяются внутренним узлом (InternalNode), который добавляется обратно в очередь.
Процесс продолжается, пока в очереди не останется один узел,
который и станет корнем дерева Хаффмана.

Заполнение кодов символов: После построения дерева вызывается метод fillCodes для корневого узла.

Этот метод рекурсивно обходит дерево, назначая коды символам.
Каждый символ получает код, который соответствует пути от корня до этого символа:
0 для левого потомка и 1 для правого.

Формирование закодированной строки:
Для каждого символа в исходной строке программа добавляет его код из индекса codes
к закодированной строке.

Вывод результата: Выводится количество различных букв в строке,
размер закодированной строки и таблица кодов символов.
Затем выводится закодированная строка. */


    String encode(File file) throws FileNotFoundException {
        //прочитаем строку для кодирования из тестового файла
        Scanner scanner = new Scanner(file);
        String s = scanner.next();
        int quantity = 0;
        Map<Character, Integer> count = new HashMap<>();
        for (char j = 'a'; j < 'e'; j++) {
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == j) {
                    quantity++;
                }
            }
            count.put(j, quantity);
            quantity = 0;
        }
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        for(char elem : count.keySet()) {
            priorityQueue.add(new LeafNode(count.get(elem), elem));
        }
        while (priorityQueue.size() != 1) {
            Node left = priorityQueue.poll();
            Node right = priorityQueue.poll();
            assert left != null;
            assert right != null;
            priorityQueue.add(new InternalNode(left, right));
        }
        priorityQueue.element().fillCodes("");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++)
            sb.append(codes.get(s.charAt(i)));

        return sb.toString();
    }
    //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        File f = new File(root + "by/it/a_khmelev/lesson03/dataHuffman.txt");
        A_Huffman instance = new A_Huffman();
        long startTime = System.currentTimeMillis();
        String result = instance.encode(f);
        long finishTime = System.currentTimeMillis();
        System.out.printf("%d %d\n", codes.size(), result.length());
        for (Map.Entry<Character, String> entry : codes.entrySet()) {
            System.out.printf("%s: %s\n", entry.getKey(), entry.getValue());
        }
        System.out.println(result);
    }

}
