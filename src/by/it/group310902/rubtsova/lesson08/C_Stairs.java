package by.it.group310902.rubtsova.lesson08;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Даны число 1<=n<=100 ступенек лестницы и
целые числа −10000<=a[1],…,a[n]<=10000, которыми помечены ступеньки.
Найдите максимальную сумму, которую можно получить, идя по лестнице
снизу вверх (от нулевой до n-й ступеньки), каждый раз поднимаясь на
одну или на две ступеньки.

Sample Input 1:
2
1 2
Sample Output 1:
3

Sample Input 2:
2
2 -1
Sample Output 2:
1

Sample Input 3:
3
-1 2 1
Sample Output 3:
3

*/

public class C_Stairs {

    int getMaxSum(InputStream stream ) {
        Scanner scanner = new Scanner(stream);
        int n=scanner.nextInt();
        int stairs[]=new int[n];
        for (int i = 0; i < n; i++) {
            stairs[i]=scanner.nextInt();
        }
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        // result - максимальная сумма, которую можно получить
        int result=0; // максимальная сумма, которую можно получить
        int i =0; // текущая ступенька
        while(i<n-2){ // пока не дошли до предпоследней ступеньки
            if( stairs[i+1]<stairs[i+2]){ // если сумма на ступеньке через одну больше, чем на следующей ступеньке
                result+=stairs[i+2]; // переходим на ступеньку через одну и прибавляем ее значение к сумме
                i+=2; // переходим на следующую ступеньку
            }else{
                result+=stairs[i+1]; // переходим на следующую ступеньку и прибавляем ее значение к сумме
                i+=1; // переходим на следующую ступеньку
            }
        }

        if (i == n - 2) { // если остались еще две ступеньки, прибавляем значение одной из них к сумме
            result += stairs[i + 1];
        } else if (i == n - 3) { // если осталась еще одна ступенька, прибавляем ее значение к сумме
            result += Math.max(stairs[i + 1], stairs[i + 2]); // прибавляем значение большей из двух ступенек
        }

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }


    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = C_Stairs.class.getResourceAsStream("dataC.txt");
        C_Stairs instance = new C_Stairs();
        int res=instance.getMaxSum(stream);
        System.out.println(res);
    }

}
