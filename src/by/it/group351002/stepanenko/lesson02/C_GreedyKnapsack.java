package by.it.group351002.stepanenko.lesson02;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class C_GreedyKnapsack {
    private static class Item implements Comparable<Item> {
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
        public int compareTo(Item o) {
            // Сортировка в порядке убывания отношения стоимости к весу
            double ratio1 = (double) cost / weight;
            double ratio2 = (double) o.cost / o.weight;

            if (ratio1 > ratio2) {
                return -1;
            } else if (ratio1 < ratio2) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    double calc(File source) throws FileNotFoundException {
        Scanner input = new Scanner(source);
        int n = input.nextInt();
        int W = input.nextInt();
        Item[] items = new Item[n];
        for (int i = 0; i < n; i++) {
            items[i] = new Item(input.nextInt(), input.nextInt());
        }

        // Сортировка предметов по убыванию отношения стоимости к весу
        Arrays.sort(items);

        double result = 0;
        int currentWeight = 0;
        int currentItem = 0;

        while (currentWeight < W && currentItem < n) {
            if (items[currentItem].weight <= W - currentWeight) {
                // Если предмет полностью помещается в рюкзак, добавляем его
                result += items[currentItem].cost;
                currentWeight += items[currentItem].weight;
            } else {
                // Если предмет не помещается полностью, добавляем только часть
                double remainingWeight = W - currentWeight;
                result += (remainingWeight / items[currentItem].weight) * items[currentItem].cost;
                currentWeight = W;
            }

            currentItem++;
        }

        System.out.printf("Удалось собрать рюкзак на сумму %f\n", result);
        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        String root = System.getProperty("user.dir") + "/src/";
        File f = new File(root + "by/it/a_khmelev/lesson02/greedyKnapsack.txt");
        double costFinal = new C_GreedyKnapsack().calc(f);
        long finishTime = System.currentTimeMillis();
        System.out.printf("Общая стоимость %f (время %d)", costFinal, finishTime - startTime);
    }
}