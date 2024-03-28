package by.it.group351002.zakrevskaya.lesson04;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class GenerateArray {
    public static void main(String[] args) throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter("src/by/it/group351001/anton_matsiushenka/lesson04/dataB.txt");
        int n = 9999999;
        Random random = new Random();
        printWriter.println(n);
        for (int i = 0; i < n; i++) {
            printWriter.print(random.nextInt() + " ");
        }
        printWriter.close();
    }
}
