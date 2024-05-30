package by.it.group310901.baradzin.lesson08;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * <dl>
 * <dt>Задача на программирование:
 * <dt>Дано:
 * <dd><dl>
 * <dt>Первая строка ввода содержит целое число:
 * <dd>1<=n<=100 - ступенек лестницы
 * <dt>Вторая строка содержит n целых чисел:
 * <dd>−10000<=a[1],…,a[n]<=10000 - числа, которыми помечены ступеньки
 * </dd></dl>
 * <dt>
 * Найдите максимальную сумму, которую можно получить, идя по лестнице снизу вверх (от нулевой до n-й ступеньки), каждый
 * раз поднимаясь на одну или на две ступеньки
 * </dt>
 * <dt>Sample Input 1:
 * <dd>2<br/>1 2
 * <dt>Sample Output 1:
 * <dd>3
 * <dt>Sample Input 2:
 * <dd>2<br/>2 -1
 * <dt>Sample Output 2:</dt>
 * <dd>1
 * <dt>Sample Input 3:
 * <dd>3<br/>-1 2 1
 * <dt>Sample Output 3:
 * <dd>3
 */

public class C_Stairs {
    int getMaxSum(InputStream stream) {
        var scanner = new Scanner(stream);
        var n = scanner.nextInt();
        var stairs = new int[n];
        for (var i = 0; i < n; i++)
            stairs[i] = scanner.nextInt();
        return getMaxSum(stairs);
    }

    public static int getMaxSum(int[] a) {
        return getMaxSum(a, a.length - 1);
    }

    public static int getMaxSum(int[] a, int i) {
        return (i == 0)
                ? a[0]
                : (i == 1)
                ? Math.max(a[0] + a[1], a[1])
                : Math.max(getMaxSum(a, i - 1), getMaxSum(a, i - 2)) + a[i];
    }

    public static void main(String[] args) throws FileNotFoundException {
        var stream = C_Stairs.class.getResourceAsStream("dataC.txt");
        var instance = new C_Stairs();
        var res = instance.getMaxSum(stream);
        System.out.println(res);
    }
}
