package by.it.group310902.kasperets.lesson08;

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

        // Initialize DP table
        int[] maxSum = new int[n + 1];
        maxSum[0] = 0; // Base case: no stairs, sum is 0
        maxSum[1] = stairs[0]; // Base case: one stair, sum is the value of the first stair

        // Fill the DP table
        for (int i = 2; i <= n; i++) {
            // Choose the maximum between taking one step or two steps
            maxSum[i] = Math.max(maxSum[i - 1] + stairs[i - 1], maxSum[i - 2] + stairs[i - 1]);
        }

        return maxSum[n];
    }


    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = C_Stairs.class.getResourceAsStream("dataC.txt");
        C_Stairs instance = new C_Stairs();
        int res=instance.getMaxSum(stream);
        System.out.println(res);
    }

}
