package by.it.group351003.zhuravski.lesson14;

import java.util.Arrays;

public class HanoiTower {
    private DomDSU dsu;
    private Integer[] indexes;
    private Integer indexesShift;
    private Integer[] towers;
    HanoiTower() {
        towers = new Integer[3];
        dsu = new DomDSU();
    }
    private void initResult(int n) {
        indexesShift = (n - 1) / 3;
        indexes = new Integer[n - indexesShift];
        Arrays.fill(indexes, -1);
        indexesShift++;
    }
    public Integer[] move(int n) {
        initResult(n);
        towers[0] = n;
        towers[1] = 0;
        towers[2] = 0;
        shift(0, 1, 2, n);
        return dsu.getSizes();
    }
    private void shift(int from, int to, int temp, int height) {
        if (height == 1) {
            towers[from]--;
            towers[to]++;
            int res = Math.max(towers[0], towers[1]);
            res = Math.max(res, towers[2]);
            int index = dsu.add(res);
            dsu.union(index, indexes[res - indexesShift]);
            indexes[res - indexesShift] = index;
        }
        else {
            shift(from, temp, to, height - 1);
            shift(from, to, temp, 1);
            shift(temp, to, from, height - 1);
        }
    }
}
