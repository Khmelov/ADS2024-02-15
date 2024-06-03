package by.it.group351001.semashka_iv.lesson04.lesson08;

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

    int getMaxSum(InputStream stream) {
        Scanner scanner = new Scanner(stream);
        int n = scanner.nextInt();
        int stairs[] = new int[n];
        for (int i = 0; i < n; i++) {
            stairs[i] = scanner.nextInt();
        }
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!

/*В методе getMaxSum происходит чтение входных данных из файла и их подготовка для обработки.
Создается массив sum, в котором будет храниться максимальная сумма,
которую можно получить, достигнув каждой ступеньки.
Для первых трех ступенек выполняется инициализация значений:
sum[0] устанавливается равным значению первой ступеньки stairs[0].
sum[1] устанавливается равным сумме первых двух ступенек stairs[0] + stairs[1].
sum[2] вычисляется как максимальная сумма из двух вариантов:
либо подняться на третью ступеньку с первой, либо с второй.
Это определяется суммой значений ступенек.
Далее, начиная с четвертой ступеньки,
для каждой ступеньки i вычисляется максимальная сумма как максимум из двух вариантов:
Подняться на текущую ступеньку stairs[i] и при этом добавить сумму,
достигнутую на ступени i-2 (sum[i-2]).
Подняться на текущую ступеньку stairs[i] и предыдущую stairs[i-1],
при этом добавив сумму, достигнутую на ступени i-3 (sum[i-3]).
Результат хранится в последней ячейке массива sum и возвращается.
*/

        int result = 0;
        int[] sum = new int[n];
        if (n == 1) {
            return stairs[0];
        }
        if (n == 2) {
            return stairs[0] + stairs[1];
        }
        sum[0] = stairs[0];
        sum[1] = stairs[0] + stairs[1];
        sum[2] = Math.max(stairs[0] + stairs[2], stairs[1] + stairs[2]);
        for (int i = 3; i < n; i++) {
            sum[i] = Math.max(sum[i - 2] + stairs[i], sum[i - 3] + stairs[i - 1] + stairs[i]);
        }
        result = sum[n - 1];
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }


    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = C_Stairs.class.getResourceAsStream("dataC.txt");
        C_Stairs instance = new C_Stairs();
        int res = instance.getMaxSum(stream);
        System.out.println(res);
    }

}