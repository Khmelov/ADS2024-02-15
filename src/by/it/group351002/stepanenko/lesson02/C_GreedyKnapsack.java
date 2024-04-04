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
            // Сортируем предметы по удельной стоимости (стоимость / вес) по убыванию.
            double s1 = (double) this.cost / this.weight;
            double s2 = (double) o.cost / o.weight;
            return Double.compare(s2, s1);
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

        Arrays.sort(items); // Сортировка предметов по удельной стоимости (стоимость / вес) по убыванию.

        double result = 0;
        int weightLeft = W; // Оставшийся вес рюкзака.

        for (Item item : items) {
            if (weightLeft == 0) {
                // Рюкзак полностью заполнен.
                break;
            }

            if (item.weight <= weightLeft) {
                // Полностью помещаем предмет в рюкзак.
                result += item.cost;
                weightLeft -= item.weight;
            } else {
                // Предмет не помещается полностью, поэтому добавляем только часть предмета.
                result += (double) item.cost * weightLeft / item.weight;
                weightLeft = 0;
            }
        }

        System.out.printf("Удалось собрать рюкзак на сумму %.2f\n", result);
        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        String root = System.getProperty("user.dir") + "/src/";
        File f = new File(root + "by/it/a_khmelev/lesson02/greedyKnapsack.txt");
        double costFinal = new C_GreedyKnapsack().calc(f);
        long finishTime = System.currentTimeMillis();
        System.out.printf("Общая стоимость %.2f (время %d)", costFinal, finishTime - startTime);
    }
}