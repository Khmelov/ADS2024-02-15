package by.it.group351001.ivan_shaminko.lesson02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class A_VideoRegistrator {

    public static void main(String[] args) {
        A_VideoRegistrator instance=new A_VideoRegistrator();
        double[] events=new double[]{1, 1.1, 1.6, 2.2, 2.4, 2.7, 3.9, 8.1, 9.1, 5.5, 3.7};
        List<Double> starts=instance.calcStartTimes(events,1); //рассчитаем моменты старта, с длинной сеанса 1
        System.out.println(starts);

        //покажем моменты старта
    }

    //модификаторы доступа опущены для возможности тестирования
    List<Double> calcStartTimes(double[] events, double workDuration){

        Arrays.sort(events);
        List<Double> result;
        result = new ArrayList<>();
        double border = events[0] + workDuration;
        result.add(events[0]);
        int i;
        for (i = 0; i < events.length - 1; i++)
        {
            if (events[i] > border)
            {
                border = events[i] + workDuration;
                result.add(events[i]);
            }
        }



        return result;                        //вернем итог
    }
}
