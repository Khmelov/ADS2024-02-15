package by.it.group351001.tsiareshchanka.lesson2;
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
            return Integer.compare(this.cost, o.cost);
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

        //тут необходимо реализовать решение задачи
        //итогом является максимально воможная стоимость вещей в рюкзаке
        //вещи можно резать на кусочки (непрерывный рюкзак)
        double result = 0;
        //тут реализуйте алгоритм сбора рюкзака
        //будет особенно хорошо, если с собственной сортировкой
        //кроме того, можете описать свой компаратор в классе Item
        int gap;
        int j;
        Item temp;
        if (items.length % 2 == 0){
            gap = items.length / 2;
        }
        else{
            gap = (items.length - 1) / 2;
        }
        while (gap > 0){
            for (int i = gap + 1; i < items.length; i++){
                temp = items[i];
                j = i;
                while ((j > gap) && (items[j-gap].compareTo(temp) > 0)){
                    items[j] = items[j - gap];
                    j = j - gap;
                }
                items[j] = temp;
            }
            if (gap % 2 == 0){
                gap = gap / 2;
            }
            else{
                gap = (gap - 1) / 2;
            }
        }
        int middle = items.length / 2;
        for (int i = 0; i < middle; i++){
                temp = items[i];
                items[i] = items[items.length - i - 1];
                items[items.length - i - 1] = temp;
        }

        System.out.println(Arrays.toString(items));

        //System.out.println(Arrays.toString(items));
        //ваше решение.
        int total_cost = 0;
        int total_weight = 0;
        int increment = 0;
        int prev_increment = -1;
        int high_value = 0;
        int index = 0;
        while (increment < items.length){
            high_value = items[0].cost / items[0].weight;
            index = 0;
            for (int i = 1; i < items.length; i++){
                if (high_value < items[i].cost / items[i].weight) {
                high_value = items[i].cost / items[i].weight;
                index = i;
                }
            }
            total_weight += items[index].weight;
            total_cost += items[index].cost;
            items[index].cost = 1;
            items[index].weight = 1;
            if (total_weight > W) {
                prev_increment = increment;
                increment = items.length;
            }
            else{
                increment++;
            }
        }
        int extra_w = 0;
        int extra_m = 0;
        if (total_weight > W){
            extra_w = total_weight - W;
            extra_m = items[prev_increment].cost * extra_w / items[prev_increment].weight;
            total_weight = W;
            total_cost = total_cost - extra_m;
        }
        result = total_weight;
        System.out.printf("Удалось собрать рюкзак на сумму %f\n",result);
        return total_cost;
        //return total_cost;
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