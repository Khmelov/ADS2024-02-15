package by.it.group351003.suchok.lesson02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/*
Даны события events
реализуйте метод calcStartTimes, так, чтобы число включений регистратора на
заданный период времени (1) было минимальным, а все события events
были зарегистрированы.
Алгоритм жадный. Для реализации обдумайте надежный шаг.
*/

public class A_VideoRegistrator {

    public static void main(String[] args) {
        A_VideoRegistrator instance=new A_VideoRegistrator();
        double[] events=new double[]{1, 1.1, 1.6, 2.2, 2.4, 2.7, 3.9, 8.1, 9.1, 5.5, 3.7};
        List<Double> starts=instance.calcStartTimes(events,1);
        System.out.println(starts);
    }
    List<Double> calcStartTimes(double[] events, double workDuration){
        List<Double> result;
        result = new ArrayList<>();
        double startTime;

        Arrays.sort(events);

        startTime = events[0];
        result.add(startTime);

        for (int i = 1; i < events.length; i++) {
            if (events[i] > startTime + workDuration) {
                startTime = events[i];
                result.add(startTime);
            }
        }
        return result;
    }
}
