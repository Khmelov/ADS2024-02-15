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
                .include("123\n")
                .include("456\n");
        run("C->B, C->I, I->A, A->D, D->I, D->B, B->H, H->D, D->E, H->E, E->G, A->F, G->F, F->K, K->G", true)
                .include("C\n")
                .include("E\n")
                .include("ABDHI\n")
                .include("FGK\n");
        run("X->Y, Y->Z, Z->X, Z->W, W->V, V->U, U->W", true)
                .include("XYZ\n")
                .include("UVW\n");

        run("M->N, N->O, O->M, P->Q, Q->P, R->S", true)
                .include("MNO\n")
                .include("PQ\n")
                .include("R\n")
                .include("S\n");

        run("L->M, M->N, N->O, O->L, P->Q, Q->R, R->P, S->T", true)
                .include("LMNO\n")
                .include("PQR\n")
                .include("S\n")
                .include("T\n");

        run("A->B, B->A, C->D, D->C, E->F, F->E, G->H", true)
                .include("AB\n")
                .include("CD\n")
                .include("EF\n")
                .include("G\n")
                .include("H\n");
        run("A->B, B->C, C->D, D->A, E->F, F->G, G->E, H->I", true)
                .include("ABCD\n")
                .include("EFG\n")
                .include("H\n")
                .include("I\n");

        run("1->2, 2->3, 3->1, 4->5, 5->4, 6->7, 8->9, 9->8", true)
                .include("123\n")
                .include("45\n")
                .include("6\n")
                .include("7\n")
                .include("89\n");

    }


}