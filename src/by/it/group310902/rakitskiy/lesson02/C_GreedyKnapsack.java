package by.it.group310902.rakitskiy.lesson02;
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
    private static class Item implements Comparable<Item> {
        int cost;
        private int weight;

        Item(int cost, int weight) {
            this.cost = cost;
            this.setWeight(weight);
        }

        @Override
        public String toString() {
            return "Item{" +
                    "cost=" + cost +
                    ", weight=" + getWeight() +
                    '}';
        }

        @Override
        public int compareTo(Item o) {
            //тут может быть ваш компаратор


            return 0;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
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
        for (Item item : items) {
            System.out.println(item);
        }
        System.out.printf("Всего предметов: %d. Рюкзак вмещает %d кг.\n", n, W);

        //тут необходимо реализовать решение задачи
        //итогом является максимально воможная стоимость вещей в рюкзаке
        //вещи можно резать на кусочки (непрерывный рюкзак)
        double result = 0;
        //тут реализуйте алгоритм сбора рюкзака
        //будет особенно хорошо, если с собственной сортировкой
        //кроме того, можете описать свой компаратор в классе Item
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n - i - 1; ++j) {
                if (items[j].weight < items[j + 1].weight) {
                    Item temp = items[0];
                    items[j] = items[j + 1];
                    items[j + 1] = temp;
                }
            }
            result=items[0].cost;
            W-=items[0].weight;
            for (int k = 1; k < n; ++k) {
                if(items[k].weight<=W){
                    result+=items[k].cost;
                    W-=items[k].weight;
                } else if (W!=0) {
                    result+=(items[k].cost/items[k].weight)*W;

                }


            }

            //ваше решение.


            System.out.printf("Удалось собрать рюкзак на сумму %f\n", result);
            return result;
        }

        public static void main (String[]args) throws FileNotFoundException {
            long startTime = System.currentTimeMillis();
            String root = System.getProperty("user.dir") + "/src/";
            File f = new File(root + "by/it/a_khmelev/lesson02/greedyKnapsack.txt");
            double costFinal = new C_GreedyKnapsack().calc(f);
            long finishTime = System.currentTimeMillis();
            System.out.printf("Общая стоимость %f (время %d)", costFinal, finishTime - startTime);
        }


    }
}