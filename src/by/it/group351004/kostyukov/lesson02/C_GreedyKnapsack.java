package by.it.group351004.kostyukov.lesson02;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class C_GreedyKnapsack {
    private static class Item implements Comparable<C_GreedyKnapsack.Item> {
        int cost;
        int weight;

        Item(int cost, int weight) {
            this.cost = cost;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "cost=" + cost +
                    ", weight=" + weight +
                    '}';
        }

        @Override
        public int compareTo(C_GreedyKnapsack.Item o) {
            return this.cost/this.weight - o.cost/o.weight;
        }
    }

    double calc(File source) throws FileNotFoundException {
        Scanner input = new Scanner(source);
        int n = input.nextInt();
        int W = input.nextInt();
        C_GreedyKnapsack.Item[] items = new C_GreedyKnapsack.Item[n];
        for (int i = 0; i < n; i++) {
            items[i] = new C_GreedyKnapsack.Item(input.nextInt(), input.nextInt());
        }
        for (C_GreedyKnapsack.Item item:items) {
            System.out.println(item);
        }
        System.out.printf("Всего предметов: %d. Рюкзак вмещает %d кг.\n",n,W);

        Arrays.sort(items);

        double result = 0;
        int nowCommonWeight = 0;
        int i;
        for (i = items.length - 1; (i > 0) && (nowCommonWeight <= W); i--) {
            result += items[i].cost;
            nowCommonWeight += items[i].weight;
        }
        result -= items[i].cost;
        result += (double)(W - (nowCommonWeight - items[i].weight)) * items[i].cost/items[i].weight;

        System.out.printf("Удалось собрать рюкзак на сумму %f\n",result);
        return result;
    }
    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        String root=System.getProperty("user.dir")+"/src/";
        File f=new File(root+"by/it/a_khmelev/lesson02/greedyKnapsack.txt");
        double costFinal=new C_GreedyKnapsack().calc(f);
        long finishTime = System.currentTimeMillis();
        System.out.printf("Общая стоимость %f (время %d)",costFinal,finishTime - startTime);
    }
}