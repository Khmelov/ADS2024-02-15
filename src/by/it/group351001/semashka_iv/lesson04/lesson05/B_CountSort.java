package by.it.group351001.semashka_iv.lesson04.lesson05;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Первая строка содержит число 1<=n<=10000, вторая - n натуральных чисел, не превышающих 10.
Выведите упорядоченную по неубыванию последовательность этих чисел.

При сортировке реализуйте метод со сложностью O(n)

Пример: https://karussell.wordpress.com/2010/03/01/fast-integer-sorting-algorithm-on/
Вольный перевод: http://programador.ru/sorting-positive-int-linear-time/
*/

public class B_CountSort {


    int[] countSort(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
/*Чтение входных данных:
Программа считывает число n - количество чисел в последовательности, а затем сами числа.

Подсчёт чисел:
Создаётся массив arr длиной 11 элементов,
представляющий собой счётчики для каждого из чисел от 0 до 10 (включительно).
Перебираются все числа из последовательности,
и для каждого числа увеличивается соответствующий элемент массива arr.

Формирование отсортированной последовательности:
Далее происходит проход по массиву arr.
Для каждого числа k из диапазона от 0 до 10 проверяется,
сколько раз это число встречается в исходной последовательности.
Затем это число записывается в итоговый массив points столько раз, сколько оно встречается.

Вывод результата: После завершения сортировки последовательности,
отсортированные числа выводятся в консоль.*/

        //размер массива
        int n = scanner.nextInt();
        int[] points=new int[n];

        int[] arr = new int [11];
        //читаем точки
        for (int i = 0; i < n; i++) {
            points[i]=scanner.nextInt();
            arr[points[i]]++;
        }
        //тут реализуйте логику задачи с применением сортировки подсчетом
        int num = 0;
        for (int k = 0; k<10; k++) {
            for (int i = 0; i<arr[k]; i++) {
                points[num++] = k;
            }
        }







        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return points;
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson05/dataB.txt");
        B_CountSort instance = new B_CountSort();
        int[] result=instance.countSort(stream);
        for (int index:result){
            System.out.print(index+" ");
        }
    }

}

