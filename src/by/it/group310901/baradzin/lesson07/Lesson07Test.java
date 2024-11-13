package by.it.group310901.baradzin.lesson07;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Lesson07Test {
    @Test
    public void A() throws Exception {
        var instance = new A_EditDist();
        assertEquals("A1 failed", instance.getDistanceEditing("ab", "ab"), 0);
        assertEquals("A2 failed", instance.getDistanceEditing("short", "ports"), 3);
        assertEquals("A3 failed", instance.getDistanceEditing("distance", "editing"), 5);
    }

    @Test
    public void B() throws Exception {
        var instance = new B_EditDist();
        assertEquals("B1 failed", instance.getDistanceEditing("ab", "ab"), 0);
        assertEquals("B2 failed", instance.getDistanceEditing("short", "ports"), 3);
        assertEquals("B3 failed", instance.getDistanceEditing("distance", "editing"), 5);
    }

    @Test
    public void C() throws Exception {
        // путей может быть много, поэтому тут жестко проверить все сложно надо найти и проверить их все, что делает
        // тест сложнее реализации возможно, что хватит только подсчета повторов.
        var instance = new C_EditDist();
        // ожидается: #,#,
        assertEquals("C1 failed", instance.getDistanceEditing("ab","ab"),"#,#,");
        // ожидается: -s,~p,#,#,#,+s,
        assertEquals(
                "C2 failed",
                instance.getDistanceEditing("short","ports").split("#").length,
                4
        );
        // ожидается: +e,#,#,-s,#,~i,#,-c,~g,
        assertEquals(
                "C3 failed",
                instance.getDistanceEditing("distance","editing").split("#").length,
                5
        );
    }
}
