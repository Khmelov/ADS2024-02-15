package by.it.group310901.baradzin.lesson04;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.assertTrue;

public class Lesson4Test {
    @Test
    public void A() throws Exception {
        var root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group310901/baradzin/lesson04/dataA.txt");
        var instance = new A_BinaryFind();
//        var startTime = System.currentTimeMillis();
        var result = instance.findIndex(stream);
//        var finishTime = System.currentTimeMillis();
        var sb = new StringBuilder();
        for (var index : result) {
            sb.append(index).append(" ");
        }
        var ok = sb.toString().trim().equals("3 1 -1 1 -1");
        assertTrue("A failed", ok);
    }

    @Test
    public void B() throws Exception {
        var root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group310901/baradzin/lesson04/dataB.txt");
        var instance = new B_MergeSort();
//        var startTime = System.currentTimeMillis();
        var result = instance.getMergeSort(stream);
//        var finishTime = System.currentTimeMillis();
        var ok = result.length > 3;
        var test = new int[result.length];
        System.arraycopy(result, 0, test, 0, result.length);
        Arrays.sort(test);
        for (var i = 0; i < result.length; i++)
            ok = ok && (result[i] == test[i]);
        assertTrue("B failed", ok);
    }

    @Test
    public void C() throws Exception {
        var root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group310901/baradzin/lesson04/dataC.txt");
        var instance = new C_GetInversions();
//        var startTime = System.currentTimeMillis();
        var result = instance.calc(stream);
//        var finishTime = System.currentTimeMillis();
        var ok = (2 == result);
        assertTrue("C failed", ok);
    }

    @Test
    public void CBenchmark() throws Exception {
        System.out.println("Benchmark of inversions counter...\n| Size |  Time(nano-secs)  | Per-element average |");
        var time = 0L;
        var times = 20;
        var powers = 0;
        for (var i = 1; i <= times; i++) {
            time += CRunAverage(i);
            powers += (int)Math.pow(2, i);
        }
        System.out.printf("| Avrg |  %15d  |  %17d  |\n", time / times, time / times / powers);
    }

    private long CRunAverage(int power) {
        var times = 100;
        var time = 0L;
        for (var i = 0; i < times; i++) time += CRunSingle(power);
        time /= times;
        System.out.printf("| 2^%2d |  %15d  |  %17d  |\n", power, time, time / (int)Math.pow(2, power));
        return time;
    }

    private long CRunSingle(int power) {
        var random = new Random(System.currentTimeMillis());
        var arr = random.ints((long) Math.pow(2, power), 0, 1_000).toArray();
        var instance = new InverseCounter(arr, 0, arr.length);
        var time = -System.nanoTime();
        instance.invoke();
        time += System.nanoTime();
        return time;
    }
}
