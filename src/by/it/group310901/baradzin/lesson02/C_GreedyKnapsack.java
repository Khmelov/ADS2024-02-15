package by.it.group310901.baradzin.lesson02;

/*
    Даны
    1) объем рюкзака 4
    2) число возможных предметов 60
    3) сам набор предметов
        100 50
        120 30
        100 50
    Все это указано в файле (by/it/a_khmelev/lesson02/greedyKnapsack.txt)

    Необходимо собрать наиболее дорогой вариант рюкзака для этого объема
    Предметы можно резать на кусочки (т.е. алгоритм будет жадным)
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class C_GreedyKnapsack {
    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        var root = System.getProperty("user.dir") + "/src/";
        var f = new File(root + "by/it/a_khmelev/lesson02/greedyKnapsack.txt");
        var costFinal = new C_GreedyKnapsack().calc(f);
        var finishTime = System.currentTimeMillis();
        System.out.printf("Общая стоимость %f (время %d)", costFinal, finishTime - startTime);
    }

    double calc(File source) throws FileNotFoundException {
        var input = new Scanner(source);
        // сколько предметов в файле
        var n = input.nextInt();
        // какой вес у рюкзака
        var W = input.nextInt();
        // получим список предметов
        var items = new Item[n];
        // создавая каждый конструктором
        for (int i = 0; i < n; i++)
            items[i] = new Item(input.nextInt(), input.nextInt());
        //покажем предметы
        for (var item : items)
            System.out.println(item);
        System.out.printf("Всего предметов: %d. Рюкзак вмещает %d кг.\n", n, W);

        Arrays.sort(items, Collections.reverseOrder());
        double result = 0;
        for (var item: items)
            if (item.weight <= W) {
                W -= item.weight;
                result += item.cost;
            } else {
                result += W * item.profit;
                break;
            }

        System.out.printf("Удалось собрать рюкзак на сумму %f\n", result);
        return result;
    }

    private static class Item implements Comparable<Item> {
        int cost, weight;
        double profit;

        Item(int cost, int weight) {
            this.cost = cost;
            this.weight = weight;
            this.profit = (double) cost / weight;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "cost=" + cost +
                    ", weight=" + weight +
                    ", profit=" + profit +
                    '}';
        }

        @Override
        public int compareTo(Item o) {
            return Double.compare(this.profit, o.profit);
        }
    }
}
