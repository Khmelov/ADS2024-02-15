package by.it.group351003.mashevskiy.lesson05;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*убыванию последо
Первая строка содержит число 1<=n<=10000, вторая - n натуральных чисел, не превышающих 10.
Выведите упорядоченную по невательность этих чисел.

При сортировке реализуйте метод со сложностью O(n)

Пример: https://karussell.wordpress.com/2010/03/01/fast-integer-sorting-algorithm-on/
Вольный перевод: http://programador.ru/sorting-positive-int-linear-time/
*/

public class B_CountSort {


    int[] countSort(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        //размер массива
        int n = scanner.nextInt();
        int[] points=new int[n];

        //читаем точки
        for (int i = 0; i < n; i++) {
            points[i]=scanner.nextInt();
        }
        //тут реализуйте логику задачи с применением сортировки подсчетом
        int max,min;
        max = points[0];
        min = points[0];
        for(int i = 0; i < n; i++){
            if(max < points[i]){
                max = points[i];
            }
            if(min > points[i]){
                min = points[i];
            }
        }
        int[] count;
        count = new int[max - min + 1];
        for(int i = 0;i < n;i++){
            count[points[i] - min]++;
        }
        int k = 0;
        for (int i = 0; i < count.length; i++) {
            // count[0]=1, значит array[0]=0;
            // count[1]=2, значит вставляем два раза array[1]=array[2]=1;
            // count[2]=1, опять только один раз. array[3]=2;
            // count[3]=0, значит ничего не вставляем и т.д.
            for (int j = 0; j < count[i]; j++) {
                points[k++] = i + min;
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
