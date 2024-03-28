package by.it.group310901.yakovtsov.lesson02;

import by.it.group310901.yakovtsov.lesson02.A_VideoRegistrator;
import by.it.group310901.yakovtsov.lesson02.B_Sheduler;
import by.it.group310901.yakovtsov.lesson02.C_GreedyKnapsack;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class Lesson02Test {
    /*
    для прохождения тестов создайте JUnit-конфигурацию на свой пакет:
    Поля:
    Name:               Test a_khmelev (тут ваша фамилия)
    Test kind:          All in package
    Package:            by.it.a_khmelev (тут ваша фамилия)
    Search for test:    In whole project
    */


    @Test(timeout = 2000)
    public void A_VideoRegistrator() {
        by.it.group310901.yakovtsov.lesson02.A_VideoRegistrator instance=new A_VideoRegistrator();
        double[] events=new double[]{1, 1.1, 1.6, 2.2, 2.4, 2.7, 3.9, 8.1, 9.1, 5.5, 3.7};
        List<Double> starts=instance.calcStartTimes(events,1); //рассчитаем моменты старта, с длинной сеанса 1
        boolean ok=starts.toString().equals("[1.0, 2.2, 3.7, 5.5, 8.1]");
        assertTrue("slowA failed", ok);
    }

    @Test(timeout = 2000)
    public void B_Sheduler() {
        by.it.group310901.yakovtsov.lesson02.B_Sheduler instance = new by.it.group310901.yakovtsov.lesson02.B_Sheduler();
        by.it.group310901.yakovtsov.lesson02.B_Sheduler.Event[] events = {new by.it.group310901.yakovtsov.lesson02.B_Sheduler.Event(0, 3), new by.it.group310901.yakovtsov.lesson02.B_Sheduler.Event(0, 1), new by.it.group310901.yakovtsov.lesson02.B_Sheduler.Event(1, 2), new by.it.group310901.yakovtsov.lesson02.B_Sheduler.Event(3, 5),
                new by.it.group310901.yakovtsov.lesson02.B_Sheduler.Event(1, 3), new by.it.group310901.yakovtsov.lesson02.B_Sheduler.Event(1, 3), new by.it.group310901.yakovtsov.lesson02.B_Sheduler.Event(1, 3), new by.it.group310901.yakovtsov.lesson02.B_Sheduler.Event(3, 6),
                new by.it.group310901.yakovtsov.lesson02.B_Sheduler.Event(2, 7), new by.it.group310901.yakovtsov.lesson02.B_Sheduler.Event(2, 3), new by.it.group310901.yakovtsov.lesson02.B_Sheduler.Event(2, 7), new by.it.group310901.yakovtsov.lesson02.B_Sheduler.Event(7, 9),
                new by.it.group310901.yakovtsov.lesson02.B_Sheduler.Event(3, 5), new by.it.group310901.yakovtsov.lesson02.B_Sheduler.Event(2, 4), new by.it.group310901.yakovtsov.lesson02.B_Sheduler.Event(2, 3), new by.it.group310901.yakovtsov.lesson02.B_Sheduler.Event(3, 7),
                new by.it.group310901.yakovtsov.lesson02.B_Sheduler.Event(4, 5), new by.it.group310901.yakovtsov.lesson02.B_Sheduler.Event(6, 7), new by.it.group310901.yakovtsov.lesson02.B_Sheduler.Event(6, 9), new by.it.group310901.yakovtsov.lesson02.B_Sheduler.Event(7, 9),
                new by.it.group310901.yakovtsov.lesson02.B_Sheduler.Event(8, 9), new by.it.group310901.yakovtsov.lesson02.B_Sheduler.Event(4, 6), new by.it.group310901.yakovtsov.lesson02.B_Sheduler.Event(8, 10), new by.it.group310901.yakovtsov.lesson02.B_Sheduler.Event(7, 10)
        };

        List<B_Sheduler.Event> starts = instance.calcStartTimes(events, 0, 10);  //рассчитаем оптимальное заполнение аудитории
        boolean ok=starts.toString().equals("[(0:1), (1:2), (2:3), (3:5), (6:7), (7:9)]");
        assertTrue("B_Sheduler failed", ok);
    }
    @Test(timeout = 2000)
    public void C_GreedyKnapsack() throws Exception {
        String root=System.getProperty("user.dir")+"/src/";
        File file=new File(root+"by/it/a_khmelev/lesson02/greedyKnapsack.txt");
        double costFinal=new C_GreedyKnapsack().calc(file);
        boolean ok=costFinal==200;
        assertTrue("B_Sheduler failed", ok);
    }

}
