package by.it.group351005.gorodko.lesson10;

import java.util.PriorityQueue;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        final int RND_SEED = 123;
        Random rnd = new Random(RND_SEED);
        MyPriorityQueue<Integer> a = new MyPriorityQueue<>();
        PriorityQueue<Integer> b = new PriorityQueue<>();
        for (int i = 1; i <= 20; i++) {
            int x = rnd.nextInt() % 100;
            a.add(x);
            b.add(x);
        }
        System.out.println(a.toString());
        System.out.println(b.toString());
        for (int i = 1; i <= 1; i++) {
            a.remove();
            b.remove();
        }
        System.out.println(a.toString());
        System.out.println(b.toString());
    }
}
