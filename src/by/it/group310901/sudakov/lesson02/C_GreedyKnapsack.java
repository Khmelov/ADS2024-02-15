package by.it.group310901.sudakov.lesson02;
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
            // Сравниваем элементы по соотношению стоимость/вес в порядке убывания
            double ratioThis = (double) this.cost / this.weight;
            double ratioOther = (double) o.cost / o.weight;
            if (ratioThis < ratioOther) return 1; // Сортировка по убыванию
            else if (ratioThis > ratioOther) return -1;
            else return 0;
        }
    }

    double calc(File source) throws FileNotFoundException {
        Scanner input = new Scanner(source);
        int n = input.nextInt();      // Сколько предметов в файле
        int W = input.nextInt();      // Какой вес у рюкзака
        Item[] items = new Item[n];   // Получим список предметов
        for (int i = 0; i < n; i++) { // Создаем каждый элемент
            items[i] = new Item(input.nextInt(), input.nextInt());
        }

        // Сортируем предметы по соотношению стоимость/вес
        Arrays.sort(items);

        double result = 0;
        int currentWeight = 0; // Текущий вес рюкзака

        for (Item item : items) {
            // Если предмет помещается полностью в рюкзак
            if (currentWeight + item.weight <= W) {
                result += item.cost;
                currentWeight += item.weight;
            } else { // Если предмет можно разделить
                int remainingWeight = W - currentWeight; // Сколько осталось места в рюкзаке
                result += (double) item.cost * remainingWeight / item.weight;
                break; // Расчет закончен
            }
        }

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
