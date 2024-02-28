package by.it.group351005.melnikov.lesson02;
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
import java.io.FileReader;
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

        //ваше решение.

        String root = System.getProperty("user.dir") + "/src/";
        FileReader file = new FileReader(root+"by/it/a_khmelev/lesson02/greedyKnapsack.txt");
        Scanner MyFile = new Scanner(file);
double[]buff=new double[2];
        double[][] twoDimArray = new double[4][2];
        double buffhelp=MyFile.nextInt();
        buffhelp=MyFile.nextInt();
        twoDimArray[0][0] = MyFile.nextInt();
        twoDimArray[0][1] = MyFile.nextInt();
        twoDimArray[1][0] = MyFile.nextInt();
        twoDimArray[1][1] = MyFile.nextInt();
        twoDimArray[2][0] = MyFile.nextInt();
        twoDimArray[2][1] = MyFile.nextInt();
        twoDimArray[3][0] = MyFile.nextInt();
        twoDimArray[3][1] = MyFile.nextInt();
        for (int i=0;i<4;i++) {
            twoDimArray[i][0] = twoDimArray[i][0] / twoDimArray[i][1];
        }

for (int i=0;i<4;i++){
    for (int j=0;j<3;j++){
        if (twoDimArray[j+1][0]>twoDimArray[j][0]){
            buff=twoDimArray[j];
            twoDimArray[j]=twoDimArray[j+1];
            twoDimArray[j+1]=buff;
        }
    }
}
int k=0;
int CurrectW=0;
        int CurrectC=0;
        while (CurrectW<60){
if (twoDimArray[k][1]<W-CurrectW){
    CurrectC+=twoDimArray[k][1]*twoDimArray[k][0];
    CurrectW+=twoDimArray[k][1];
}else{
    CurrectC+=twoDimArray[k][0]*(60-CurrectW);
    CurrectW+=twoDimArray[k][1];
}
k++;
        }

        result = CurrectC;
        System.out.printf("Удалось собрать рюкзак на сумму %f\n", result);
        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        String root = System.getProperty("user.dir") + "/src/";
        File f = new File(root + "by/it/a_khmelev/lesson02/greedyKnapsack.txt");
        double costFinal = new C_GreedyKnapsack().calc(f);
        long finishTime = System.currentTimeMillis();
        System.out.printf("Общая стоимость %f (время %d)", costFinal, finishTime - startTime);
    }
}