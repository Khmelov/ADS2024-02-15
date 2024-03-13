package by.it.group351002.stepanenko.lesson02;
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
            return Integer.compare(o.cost / o.weight, this.cost / this.weight);
        }
    }

    double calc(File source) throws FileNotFoundException {
        Scanner input = new Scanner(source);
        int n = input.nextInt();      // сколько предметов в файле
        int W = input.nextInt();      // какой вес у рюкзака
        Item[] items = new Item[n];   // получим список предметов
        for (int i = 0; i < n; i++) { // создавая каждый конструктором
            items[i] = new Item(input.nextInt(), input.nextInt());
        }
        // покажем предметы
        for (Item item : items) {
            System.out.println(item);
        }
        System.out.printf("Всего предметов: %d. Рюкзак вмещает %d кг.\n", n, W);

        // сортируем предметы по убыванию стоимости за единицу веса
        Arrays.sort(items);

        double result = 0; // общая стоимость предметов в рюкзаке
        int currentWeight = 0; // текущий вес рюкзака

        // выбираем предметы с максимальной стоимостью до заполнения рюкзака
        for (Item item : items) {
            if (currentWeight + item.weight <= W) {
                // предмет целиком помещается в рюкзак
                result += item.cost;
                currentWeight += item.weight;
            } else {
                // предмет нужно разделить на кусочки
                double fraction = (double) (W - currentWeight) / item.weight;
                result += fraction * item.cost;
                break;
            }
        }

        System.out.printf("Удалось собрать рюкзак на сумму %f\n", result);
        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        String root = System.getProperty("user.dir") + "/src/";
        File f = new File(root + "by/it/group351002/stepanenko/lesson02/greedyKnapsack.txt");
        double costFinal = new C_GreedyKnapsack().calc(f);
        long finishTime = System.currentTimeMillis();
        System.out.printf("Общая стоимость %f (время %d)", costFinal, finishTime - startTime);
    }
}
