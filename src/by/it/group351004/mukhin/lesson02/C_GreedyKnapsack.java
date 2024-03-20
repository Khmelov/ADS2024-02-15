package by.it.group351004.mukhin.lesson02;
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
            //тут может быть ваш компаратор


            return 0;
        }
    }

    double calc(File source) throws FileNotFoundException {
        Scanner input = new Scanner(source);
        int n = input.nextInt();
        int W = input.nextInt();
        by.it.group351004.mukhin.lesson02.C_GreedyKnapsack.Item[] items = new by.it.group351004.mukhin.lesson02.C_GreedyKnapsack.Item[n];   //получим список предметов
        for (int i = 0; i < n; i++) {
            items[i] = new by.it.group351004.mukhin.lesson02.C_GreedyKnapsack.Item(input.nextInt(), input.nextInt());

        }

        for (by.it.group351004.mukhin.lesson02.C_GreedyKnapsack.Item item:items) {
            System.out.println(item);
        }
        System.out.printf("Всего предметов: %d. Рюкзак вмещает %d кг.\n",n,W);

        double result = 0;

        Arrays.sort(items, (Item1, Item2)->{
            return Double.compare((double)Item1.weight/Item1.cost,(double)Item2.weight/Item2.cost);
        });
        for (int i = 0; i < items.length && W != 0; ++i){
            while (items[i].weight > W) {
                items[i].cost -= items[i].cost / items[i].weight;
                items[i].weight--;
            }

            W -= items[i].weight;
            result += items[i].cost;

        }

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