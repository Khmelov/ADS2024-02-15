package by.it.group310902.strizhevskiy.lesson02;
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
            return this.cost*o.weight-o.cost*this.weight;
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
        
        sort(items);
        
        //Greedy algorithm
        int space = W;
        for(int i = items.length-1; i >= 0; i--){
            if(items[i].weight > space){
                result += items[i].cost*space/items[i].weight;
                break;
            }
            result += items[i].cost;
            space -= items[i].weight;
        }


        System.out.printf("Удалось собрать рюкзак на сумму %f\n",result);
        return result;
    }

    void siftDown(Item[] arr, int size, int index){
        int C = index, L = 2*C, R = L+1, M;
        while(L < size){
            if(L == size-1){
                M = L;
            }
            else {
                M = arr[L].compareTo(arr[R]) >= 0 ? L : R;
            }
            if(arr[C].compareTo(arr[M]) >= 0) break;
            Item temp = arr[C];
            arr[C] = arr[M];
            arr[M] = temp;
            C = M; L = 2*C; R = L+1;
        }
    }

    void sort(Item[] arr){
        //To max-heap
        for(int i = arr.length/2; i >= 0; i--){
            siftDown(arr, arr.length, i);
        }

        //Swap max and last element and re-add last element to the smaller heap
        for(int i = arr.length-1; i >= 0; i--){
            Item temp = arr[i];
            arr[i] = arr[0];
            arr[0] = temp;
            siftDown(arr, i, 0);
        }
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
