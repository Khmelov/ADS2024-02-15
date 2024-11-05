package by.it.group351005.egorov.lesson13;

import by.it.HomeWork;
import org.junit.Test;

@SuppressWarnings("NewClassNamingConvention")
public class Test_Part2_Lesson13 extends HomeWork {

    @Test
    public void testGraphA() {
        run("0 -> 1", true).include("0 1");
        run("0 -> 1, 1 -> 2", true).include("0 1 2");
        run("0 -> 2, 1 -> 2, 0 -> 1", true).include("0 1 2");
        run("0 -> 2, 1 -> 3, 2 -> 3, 0 -> 1", true).include("0 1 2 3");
        run("1 -> 3, 2 -> 3, 2 -> 3, 0 -> 1, 0 -> 2", true).include("0 1 2 3");
        run("0 -> 1, 0 -> 2, 0 -> 2, 1 -> 3, 1 -> 3, 2 -> 3", true).include("0 1 2 3");
        run("A -> B, A -> C, B -> D, C -> D", true).include("A B C D");
        run("A -> B, A -> C, B -> D, C -> D, A -> D", true).include("A B C D");
        run("0 -> 2, 1 -> 3, 2 -> 3, 0 -> 1", true).include("0 1 2 3");
        run("0 -> 1, 1 -> 2, 2 -> 3", true).include("0 1 2 3");
        run("0 -> 2, 0 -> 3, 2 -> 3", true).include("0 2 3");
        run("0 -> 1, 0 -> 3, 1 -> 2, 2 -> 3", true).include("0 1 2 3");
        run("2 -> 3, 1 -> 3, 0 -> 1, 0 -> 2", true).include("0 1 2 3");
        run("0 -> 1, 1 -> 2, 0 -> 3, 3 -> 2", true).include("0 1 3 2");
        run("A -> B, A -> C, B -> D, C -> D", true).include("A B C D");
        run("A -> B, B -> C, A -> D, D -> C", true).include("A B D C");
        run("X -> Y, Y -> Z, X -> Z", true).include("X Y Z");
        run("1 -> 3, 2 -> 3, 0 -> 1, 0 -> 2", true).include("0 1 2 3");
        run("A -> B, A -> C, B -> D, C -> E, D -> E", true).include("A B D C E");
        run("5 -> 2, 5 -> 0, 4 -> 0, 4 -> 1, 2 -> 3, 3 -> 1", true).include("5 4 2 3 1 0");
        run("K -> L, L -> M, K -> M", true).include("K L M");
        run("A -> C, B -> C, B -> D, A -> B", true).include("A B D C");
        run("P -> Q, P -> R, Q -> S, R -> S", true).include("P Q R S");
        run("0 -> 2, 0 -> 3, 1 -> 3, 2 -> 4, 3 -> 4", true).include("1 0 2 3 4");
    }

    @Test
    public void testGraphB() {
        run("0 -> 1", true).include("no").exclude("yes");
        run("0 -> 1, 1 -> 2", true).include("no").exclude("yes");
        run("0 -> 1, 1 -> 2, 2 -> 0", true).include("yes").exclude("no");
        run("0 -> 1, 1 -> 2, 2 -> 3", true).include("no").exclude("yes");
        run("0 -> 2, 2 -> 3, 3 -> 4", true).include("no").exclude("yes");
        run("0 -> 1, 1 -> 2, 2 -> 0", true).include("yes").exclude("no");
        run("0 -> 1, 1 -> 2, 2 -> 3, 3 -> 1", true).include("yes").exclude("no");
        run("1 -> 2, 2 -> 3, 3 -> 4, 4 -> 5", true).include("no").exclude("yes");
        run("1 -> 2, 2 -> 3, 3 -> 1", true).include("yes").exclude("no");
        run("5 -> 6, 6 -> 7", true).include("no").exclude("yes");
        run("3 -> 4, 4 -> 5, 5 -> 3", true).include("yes").exclude("no");
        run("7 -> 8, 8 -> 9, 9 -> 10", true).include("no").exclude("yes");
    }

    @Test
    public void testGraphC() {
        run("1->2, 2->3, 3->1, 3->4, 4->5, 5->6, 6->4", true)
                .include("123\n456");
        run("C->B, C->I, I->A, A->D, D->I, D->B, B->H, H->D, D->E, H->E, E->G, A->F, G->F, F->K, K->G", true)
                .include("C\nABDHI\nE\nFGK");
        //Дополните эти тесты СВОИМИ более сложными примерами и проверьте их работоспособность.
        //Параметр метода run - это ввод. Параметр метода include - это вывод.
        //Общее число примеров должно быть не менее 8 (сейчас их 2).
    }


}