package by.it.group310901.baradzin.lesson05;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class Lesson5Test {
    @Test
    public void A() throws Exception {
        var root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group310901/baradzin/lesson05/dataA.txt");
        var instance = new A_QSort();
        var result=instance.getAccessory(stream);
        var ok=Arrays.equals(result,new int[]{1,0,0});
        assertTrue("A failed", ok);
    }

    @Test
    public void B() throws Exception {
        var root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group310901/baradzin/lesson05/dataB.txt");
        var instance = new B_CountSort();
        var result=instance.countSort(stream);
        var ok=Arrays.equals(result,new int[]{2,2,3,9,9});
        assertTrue("B failed", ok);
    }

    @Test
    public void C() throws Exception {
        var root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group310901/baradzin/lesson05/dataC.txt");
        var instance = new C_QSortOptimized();
        var result=instance.getAccessory2(stream);
        var ok=Arrays.equals(result,new int[]{1,0,0});
        assertTrue("C failed", ok);
    }
}
