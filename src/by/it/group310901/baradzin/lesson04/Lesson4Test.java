package by.it.group310901.baradzin.lesson04;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class Lesson4Test {
    @Test
    public void A() throws Exception {
        var root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group310901/baradzin/lesson04/dataA.txt");
        var instance = new A_BinaryFind();
        //long startTime = System.currentTimeMillis();
        var result = instance.findIndex(stream);
        //long finishTime = System.currentTimeMillis();
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
        //long startTime = System.currentTimeMillis();
        var result = instance.getMergeSort(stream);
        //long finishTime = System.currentTimeMillis();
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
        //long startTime = System.currentTimeMillis();
        var result = instance.calc(stream);
        //long finishTime = System.currentTimeMillis();
        var ok = (2 == result);
        assertTrue("C failed", ok);
    }
}
