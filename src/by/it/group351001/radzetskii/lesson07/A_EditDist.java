package by.it.group351001.radzetskii.lesson07;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Задача на программирование: расстояние Левенштейна
    https://ru.wikipedia.org/wiki/Расстояние_Левенштейна
    http://planetcalc.ru/1721/

Дано:
    Две данных непустые строки длины не более 100, содержащие строчные буквы латинского алфавита.

Необходимо:
    Решить задачу МЕТОДАМИ ДИНАМИЧЕСКОГО ПРОГРАММИРОВАНИЯ
    Рекурсивно вычислить расстояние редактирования двух данных непустых строк

    Sample Input 1:
    ab
    ab
    Sample Output 1:
    0

    Sample Input 2:
    short
    ports
    Sample Output 2:
    3

    Sample Input 3:
    distance
    editing
    Sample Output 3:
    5

*/

public class A_EditDist {
String first;
String second;
    int [][]d;
    int getDistanceEdinting(String one, String two) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
       d=new int[one.length()+1][two.length()+1];
       for(int i=0;i<=one.length();i++){
            for(int j=0;j<=two.length();j++){
                d[i][j]=-1;
            }
        };
        first=one;
       second=two;


        int result = editDistTD(one.length(),two.length());
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }

    int min(int a, int b,int c){
        int min=a;
        if (b<min)
            min=b;
        if(c<min)
            min=c;
        return min;
    }
    int diff (char a,char b){
     if(a==b)
         return 0;
     else
         return 1;
    };
    int editDistTD(int i,int j){
    if (d[i][j]==-1){
        if(i==0){
            d[i][j]=j;
        }
        else if (j==0){
         d[i][j]=i;
        }
        else {
            int ins=editDistTD(i,j-1)+1;
            int del=editDistTD(i-1,j)+1;
            int sub=editDistTD(i-1,j-1)+(first.charAt(i - 1) == second.charAt(j - 1) ? 0 : 1);
            d[i][j]=min(ins,del,sub);
        }
    }
    return d[i][j];
    }

    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = A_EditDist.class.getResourceAsStream("dataABC.txt");
        A_EditDist instance = new A_EditDist();
        Scanner scanner = new Scanner(stream);
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
    }
}
