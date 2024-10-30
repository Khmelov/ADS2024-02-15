package by.it.group351003.kalinckovich.lesson13;

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
        run("1 -> 2, 1 -> 3, 1 -> 4, 2 -> 5, 3 -> 5, 4 -> 5", true).include("1 2 3 4 5");
        run("0 -> 1, 0 -> 2, 0 -> 2, 1 -> 3, 1 -> 3, 2 -> 3", true).include("0 1 2 3");
        run("A -> B, A -> C, B -> D, C -> D", true).include("A B C D");
        run("A -> B, A -> C, B -> D, C -> D, A -> D", true).include("A B C D");
        run("0 -> 1, 1 -> 2, 2 -> 3, 3 -> 4", true).include("0 1 2 3 4");
        run("A -> B, B -> C, C -> D, D -> E", true).include("A B C D E");
        run("0 -> 1, 0 -> 2, 0 -> 3, 1 -> 3", true).include("0 1 2 3");
        run("0 -> 1, 0 -> 2, 2 -> 3, 1 -> 4, 3 -> 4, 4 -> 5, 5 -> 6, 5 -> 7", true).include("0 1 2 3 4 5 6 7");
        run("0 -> 1, 1 -> 2, 1 -> 3, 1 -> 4, 2 -> 5, 3 -> 5, 4 -> 5, 5 -> 6", true).include("0 1 2 3 4 5 6");
        run("A -> B, A -> C, C -> D, B -> E, D -> E, E -> F, F -> G, F -> H", true).include("A B C D E F G H");
        run("A -> B, B -> C, B -> D, B -> E, B -> F, C -> G, E -> G, G -> H", true).include("A B C D E F G H");
        run("0 -> 1, 0 -> 2, 1 -> 3, 1 -> 4, 2 -> 5, 2 -> 6, 4 -> 7, 6 -> 8", true).include("0 1 2 3 4 5 6 7 8");
        run("0 -> 1, 0 -> 2, 0 -> 3, 1 -> 4, 1 -> 5, 1 -> 6, 2 -> 7, 2 -> 8, 2 -> 9, 3 -> 10, 3 -> 11, 3 -> 12", true).include("0 1 2 3 4 5 6 7 8 9 10 11 12");
        run("A -> B, A -> C, B -> D, B -> E, C -> F, C -> G, E -> H, G -> I", true).include("A B C D E F G H I");
        run("A -> B, A -> C, A -> D, B -> E, B -> F, B -> G, C -> H, C -> I, C -> J, D -> K, D -> L, D -> M", true).include("A B C D E F G H I J K L M");
        //Дополните эти тесты СВОИМИ более сложными примерами и проверьте их работоспособность.
        //Параметр метода run - это ввод. Параметр метода include - это вывод.
        //Общее число примеров должно быть не менее 20 (сейчас их 8).
    }

    @Test
    public void testGraphB() {
        run("0 -> 1", true).include("no").exclude("yes");
        run("0 -> 1, 1 -> 2", true).include("no").exclude("yes");
        run("0 -> 1, 1 -> 2, 2 -> 0", true).include("yes").exclude("no");
        run("0 -> 1, 0 -> 2, 1 -> 3, 3 -> 4, 4 -> 9, 9 -> 11, 4 -> 10, 1 -> 8, 11 -> 1", true).include("yes").exclude("no");
        run("0 -> 1, 1 -> 2, 1 -> 3, 3 -> 1, 2 -> 1", true).include("yes").exclude("no");
        run("0 -> 1, 1 -> 2, 1 -> 3, 3 -> 1, 2 -> 3", true).include("yes").exclude("no");
        run("0 -> 1, 1 -> 2, 1 -> 3, 2 -> 3", true).include("no").exclude("yes");
        run("0 -> 1, 1 -> 2, 2 -> 3, 3 -> 4, 4 -> 2", true).include("yes").exclude("no");
        run("A -> B, B -> C, C -> D, D -> A", true).include("yes").exclude("no");
        run("0 -> 1, 1 -> 2, 2 -> 3, 3 -> 0, 4 -> 5", true).include("yes").exclude("no");
        run("1 -> 2, 2 -> 3, 3 -> 4, 4 -> 5, 6 -> 7", true).include("no").exclude("yes");
        run("0 -> 1, 1 -> 2, 2 -> 0, 3 -> 4, 4 -> 3", true).include("yes").exclude("no");
        run("A -> B, B -> C, C -> D, D -> A, E -> F, F -> G, G -> E", true).include("yes").exclude("no");
        //Дополните эти тесты СВОИМИ более сложными примерами и проверьте их работоспособность.
        //Параметр метода run - это ввод. Параметр метода include - это вывод.
        //Общее число примеров должно быть не менее 12 (сейчас их 3).
    }

    @Test
    public void testGraphC() {
        run("1->2, 2->3, 3->1, 3->4, 4->5, 5->6, 6->4", true)
                .include("123\n456");
        run("C->B, C->I, I->A, A->D, D->I, D->B, B->H, H->D, D->E, H->E, E->G, A->F, G->F, F->K, K->G", true)
                .include("C\nABDHI\nE\nFGK");
        run("A->B, B->C, C->A, D->E, E->F, F->D, G->H, H->I, I->J, J->G", true)
                .include("GHIJ\nDEF\nABC");
        run("X->Y, Y->Z, Z->X, A->B, B->C, C->A, D->E, E->D", true)
                .include("XYZ\nDE\nABC");

        run("A->B, B->C, C->A, D->E, E->F, F->D, G->H, H->I, I->G, J->K, K->L, L->J", true)
                .include("JKL\nGHI\nDEF\nABC");

        run("A->B, B->C, C->D, D->A, D->E, E->F, F->G, H->I, I->G, J->K, K->L, L->J", true)
                .include("JKL\nH\nI\nABCD\nE\nF\nG");

        run("1->2, 2->3, 3->4, 4->1, 4->5, 5->6, 6->7, 7->5", true)
                .include("1234\n567");

        run("1->2, 2->3, 3->1, 3->4, 4->5, 5->6, 6->4, 6->7, 7->8, 8->9, 9->7", true)
                .include("123\n456\n789");
        //Дополните эти тесты СВОИМИ более сложными примерами и проверьте их работоспособность.
        //Параметр метода run - это ввод. Параметр метода include - это вывод.
        //Общее число примеров должно быть не менее 8 (сейчас их 2).
    }


}