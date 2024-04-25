package by.it.group351001.sheipa.lesson02;
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

import static java.lang.Integer.min;

public class C_GreedyKnapsack {
    public static class Item implements Comparable<Item> {
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
            return ((this.cost * o.weight) - (o.cost * this.weight));
        }
    }

    public static class ItemValueComparator implements Comparator<Item> {
        @Override
        public int compare(Item it1, Item it2) {
            return Integer.compare((it2.cost * it1.weight), (it1.cost * it2.weight));
        }
    }


    void exchangeSort(Item[] items) {
        int index;
        Item buff, max;
        for (int i = 0; i < items.length; i++) {
            max = items[i];
            index = i;
            for (int j = i; j < items.length; j++) {
                if (items[j].compareTo(max) > 0) {
                    index = j;
                    max = items[j];
                }
            }

            buff = items[i];
            items[i] = max;
            items[index] = buff;
        }
    }

    double calc(File source) throws FileNotFoundException {
        Scanner input = new Scanner(source);
        int n = input.nextInt();      //сколько предметов в файле
        int W = input.nextInt();      //какой вес у рюкзака
        Item[] items = new Item[n];   //получим список предметов
        for (int i = 0; i < n; i++) { //создавая каждый конструктором
            items[i] = new Item(input.nextInt(), input.nextInt());
        }
        //покажем предметы
        for (Item item:items) {
            System.out.println(item);
        }
        System.out.printf("Всего предметов: %d. Рюкзак вмещает %d кг.\n",n,W);


        double result = 0;
        //тут реализуйте алгоритм сбора рюкзака
        //будет особенно хорошо, если с собственной сортировкой
        //кроме того, можете описать свой компаратор в классе Item

        ItemValueComparator comp = new ItemValueComparator();

        //exchangeSort(items);
        Arrays.sort(items, comp);

        for (Item item: items){
            if (W != 0) {
                result += min(W, item.weight) * (double) (item.cost / item. weight);
                W -= min(W, item.weight);
            }
        }

//        for (Item item: items){
//            if (W != 0) {
//                if (W >= item.weight) {
//                    result += item.cost;
//                    W -= item.weight;
//                }
//                else {
//                    result += W * (double) (item.cost / item. weight);
//                    W = 0;
//                }
//            }
//        }

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