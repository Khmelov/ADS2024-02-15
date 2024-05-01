package by.it.group310901.baradzin.lesson03;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertTrue;

public class Lesson03Test {
    @Test
    public void A() throws Exception {
        var root = System.getProperty("user.dir") + "/src/";
        var f = new File(root + "by/it/group310901/baradzin/lesson03/dataHuffman.txt");
        var instance = new A_Huffman();
        var result = instance.encode(f);
        var ok = result.equals("01001100100111");
        assertTrue("A failed", ok);
    }

    @Test
    public void B() throws Exception {
        var root = System.getProperty("user.dir") + "/src/";
        var f = new File(root + "by/it/group310901/baradzin/lesson03/encodeHuffman.txt");
        var instance = new B_Huffman();
        var result = instance.decode(f);
        var ok = result.equals("abacabad");
        assertTrue("B failed", ok);
    }

    @Test
    public void C() throws Exception {
        var root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group310901/baradzin/lesson03/heapData.txt");
        var instance = new C_HeapMax();
        var res = instance.findMaxValue(stream);
        var ok = (res == 500);
        assertTrue("C failed", ok);
    }
}
