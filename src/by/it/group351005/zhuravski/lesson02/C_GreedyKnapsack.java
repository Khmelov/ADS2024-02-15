package by.it.group351005.zhuravski.lesson02;
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
import java.util.*;

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

            return this.cost / this.weight - o.cost / o.weight;
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

        //ваше решение.
        //Arrays.sort(items);




        //Святая сортировка естественным слиянием.
        LinkedList<LinkedList<Item>> parts = new LinkedList<LinkedList<Item>>();
        LinkedList<Item> curPart = new LinkedList<Item>();
        curPart.add(items[0]);
        parts.add(curPart);
        for (int i = 1; i < n; i++) {
            curPart = parts.getLast();
            if (curPart.getLast().compareTo(items[i]) >= 0) {
                curPart.add(items[i]);
            }
            else {
                curPart = new LinkedList<Item>();
                curPart.add(items[i]);
                parts.add(curPart);
            }
        }
        LinkedList<LinkedList<Item>> newParts;
        LinkedList<Item> merge1;
        LinkedList<Item> merge2;
        Item it1;
        Item it2;
        while (parts.size() > 1) {
            newParts = new LinkedList<LinkedList<Item>>();
            if (parts.size() % 2 == 1) {
                newParts.add(parts.getLast());
                parts.removeLast();
            }
            while (!parts.isEmpty()) {
                merge1 = parts.getFirst();
                parts.removeFirst();
                merge2 = parts.getFirst();
                parts.removeFirst();
                curPart = new LinkedList<Item>();
                while (!merge1.isEmpty() && !merge2.isEmpty()) {
                    it1 = merge1.getFirst();
                    it2 = merge2.getFirst();
                    if (it1.compareTo(it2) >= 0) {
                        curPart.add(it1);
                        merge1.removeFirst();
                    }
                    else {
                        curPart.add(it2);
                        merge2.removeFirst();
                    }
                }
                if (!merge1.isEmpty()) {
                    curPart.addAll(merge1);
                }
                if (!merge2.isEmpty()) {
                    curPart.addAll(merge2);
                }
                newParts.add(curPart);
            }
            parts = newParts;
        }
        curPart = parts.getFirst();
        curPart.toArray(items);
        /*for (Item item:items) {
            System.out.println(item);
        }*/

        short curItem = 0;
        int freeSpace = W;
        while ((curItem < n) && (freeSpace > 0)) {
            if (items[curItem].weight >= freeSpace) {
                result += items[curItem].cost * freeSpace / items[curItem].weight;
                freeSpace = 0;
            }
            else {
                freeSpace -= items[curItem].weight;
                result += items[curItem].cost;
            }
            curItem++;
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