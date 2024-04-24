package by.it.group310901.tit.lesson02;
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
import java.util.Arrays;

/*
 В классе C_GreedyKnapsack определен вложенный класс Item,
 который представляет элементы для задачи о рюкзаке.
 У каждого элемента есть стоимость и вес. Определен
  метод calc, который принимает файл в качестве аргумента
  и выполняет решение задачи о рюкзаке.
 */
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
            // Сравниваем предметы по их стоимости за единицу веса
            double ratioThis = (double) this.cost / this.weight;
            double ratioOther = (double) o.cost / o.weight;
            return Double.compare(ratioOther, ratioThis);
        }
    }
    /*
    В методе calc:
   - Считывается количество элементов n и вместимость рюкзака W из файла.
   - Создается массив items для хранения элементов.
   - Для каждого элемента считывается его стоимость и вес, и элементы добавляются в массив.
   - Элементы сортируются по убыванию стоимости за единицу веса.
   - Вычисляется общая стоимость элементов, которые можно поместить в рюкзак, учитывая его вместимость.
   - Результат выводится на экран.
     */
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
        Arrays.sort(items);
        //тут необходимо реализовать решение задачи
        //итогом является максимально возможная стоимость вещей в рюкзаке
        //вещи можно резать на кусочки (непрерывный рюкзак)
        double result = 0;

        //тут реализуйте алгоритм сбора рюкзака
        //будет особенно хорошо, если с собственной сортировкой
        //кроме того, можете описать свой компаратор в классе Item
        int currentWeight = 0;

        for (Item item : items) {
            if (currentWeight + item.weight <= W) {
                result += item.cost;
                currentWeight += item.weight;
            } else {
                int remainingWeight = W - currentWeight;
                result += (double) item.cost * remainingWeight / item.weight;
                break; // Выходим из цикла, так как рюкзак заполнен
            }
        }

        //ваше решение.





        System.out.printf("Удалось собрать рюкзак на сумму %f\n",result);
        return result;
    }
/*
В методе main:
   - Получается путь к файлу с данными о рюкзаке.
   - Вызывается метод calc для расчета общей стоимости элементов в рюкзаке.
   - Замеряется время выполнения программы и выводится общая стоимость элементов и время выполнения.

 */
    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        String root=System.getProperty("user.dir")+"/src/";
        File f=new File(root+"by/it/a_khmelev/lesson02/greedyKnapsack.txt");
        double costFinal=new C_GreedyKnapsack().calc(f);
        long finishTime = System.currentTimeMillis();
        System.out.printf("Общая стоимость %f (время %d)",costFinal,finishTime - startTime);
    }
}