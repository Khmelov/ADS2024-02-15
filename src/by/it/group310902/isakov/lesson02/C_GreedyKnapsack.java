package by.it.group310902.isakov.lesson02;
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
import java.util.Scanner;

public class C_GreedyKnapsack {
    private static class Item implements Comparable<by.it.group310902.isakov.lesson02.C_GreedyKnapsack.Item> {
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
        public int compareTo(by.it.group310902.isakov.lesson02.C_GreedyKnapsack.Item o) {
            //тут может быть ваш компаратор


            return 0;
        }
    }
    by.it.group310902.isakov.lesson02.C_GreedyKnapsack.Item[] itemsSort(by.it.group310902.isakov.lesson02.C_GreedyKnapsack.Item[] items) {
        int gap = items.length/2;
        while (gap>0){
            for (int i = 0; i< items.length-gap; i++){
                int j=i;
                by.it.group310902.isakov.lesson02.C_GreedyKnapsack.Item temp = items[j+gap];
                while (j>= 0 && temp.cost/temp.weight> items[j].cost/items[j].weight){
                    items[j+gap]= items[j];
                    items[j]=temp;
                    j-= gap;
                }
            }
            gap/=2;
        }
        return items;
    }

    double getMaximumPackage(by.it.group310902.isakov.lesson02.C_GreedyKnapsack.Item[] items, int W){
        double result = 0;
        int i= 0;
        double tempWeight=0;
        while (i<items.length && tempWeight < W){
            if (tempWeight + items[i].weight<W){
                result += items[i].cost;
                tempWeight += items[i].weight;
            }
            else{
                result+=items[i].cost*((W-tempWeight)/items[i].weight);
                tempWeight=W;
            }
            i++;
        }
        return result;
    }
    double calc(File source) throws FileNotFoundException {
        Scanner input = new Scanner(source);
        int n = input.nextInt();      //сколько предметов в файле
        int W = input.nextInt();      //какой вес у рюкзака
        by.it.group310902.isakov.lesson02.C_GreedyKnapsack.Item[] items = new by.it.group310902.isakov.lesson02.C_GreedyKnapsack.Item[n];   //получим список предметов
        for (int i = 0; i < n; i++) { //создавая каждый конструктором
            items[i] = new by.it.group310902.isakov.lesson02.C_GreedyKnapsack.Item(input.nextInt(), input.nextInt());
        }
        //покажем предметы
        for (by.it.group310902.isakov.lesson02.C_GreedyKnapsack.Item item:items) {
            System.out.println(item);
        }
        System.out.printf("Всего предметов: %d. Рюкзак вмещает %d кг.\n",n,W);
        items= itemsSort(items);
        double result = getMaximumPackage(items, W);


        System.out.printf("Удалось собрать рюкзак на сумму %f\n",result);
        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        String root=System.getProperty("user.dir")+"/src/";
        File f=new File(root+"by/it/a_khmelev/lesson02/greedyKnapsack.txt");
        double costFinal=new by.it.group310902.isakov.lesson02.C_GreedyKnapsack().calc(f);
        long finishTime = System.currentTimeMillis();
        System.out.printf("Общая стоимость %f (время %d)",costFinal,finishTime - startTime);
    }
}