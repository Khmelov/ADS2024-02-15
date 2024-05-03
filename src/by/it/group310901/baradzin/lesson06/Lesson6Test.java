package by.it.group310901.baradzin.lesson06;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertTrue;

public class Lesson6Test {
    @Test
    public void A() throws Exception {
        var root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group310901/baradzin/lesson06/dataA.txt");
        var instance = new A_LIS();
        var result = instance.getSeqSize(stream);
        var ok = (result == 3);
        assertTrue("A failed", ok);
    }

    @Test
    public void B() throws Exception {
        var root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group310901/baradzin/lesson06/dataB.txt");
        var instance = new B_LongDivComSubSeq();
        var result = instance.getDivSeqSize(stream);
        var ok = (result == 3);
        assertTrue("B failed", ok);
    }

    @Test(timeout = 1000)
    public void C() throws Exception {
        var root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group310901/baradzin/lesson06/dataC.txt");
        var instance = new C_LongNotUpSubSeq();
        var result = instance.getNotUpSeqSize(stream);
        var ok = (result == 4);
        assertTrue("C failed", ok);
    }
}
