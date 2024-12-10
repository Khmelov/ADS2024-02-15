package by.it.group351003.zhuravski.lesson14;

import java.util.Arrays;
import java.util.Scanner;

public class StatesHanoiTowerC {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        scan.close();

        HanoiTower tower = new HanoiTower();
        Integer[] result = tower.move(n);
        Arrays.sort(result);
        for (int i : result) {
            System.out.printf("%d ", i);
        }
        System.out.print('\n');
    }
}
