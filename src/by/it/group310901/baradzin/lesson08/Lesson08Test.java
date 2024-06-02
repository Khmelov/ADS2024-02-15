package by.it.group310901.baradzin.lesson08;

import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class Lesson08Test {
    @Test
    public void A() throws Exception {
        var stream = A_Knapsack.class.getResourceAsStream("dataA.txt");
        var instance = new A_Knapsack();
        var res = instance.getMaxWeight(stream);
        assertEquals("A failed", res, 14);
    }

    @Test
    public void B() throws Exception {
        var stream = B_Knapsack.class.getResourceAsStream("dataB.txt");
        var instance = new B_Knapsack();
        var res = instance.getMaxWeight(stream);
        assertEquals("B failed", res, 9);
    }

    @Test
    public void C() throws Exception {
        var stream = C_Stairs.class.getResourceAsStream("dataC.txt");
        var instance = new C_Stairs();
        var res = instance.getMaxSum(stream);
        assertEquals("C failed", res, 3);
    }
}
