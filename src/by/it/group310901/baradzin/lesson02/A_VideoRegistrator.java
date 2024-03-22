package by.it.group310901.baradzin.lesson02;

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
        var instance = new A_VideoRegistrator();
        var events = new double[]{1, 1.1, 1.6, 2.2, 2.4, 2.7, 3.9, 8.1, 9.1, 5.5, 3.7};
        // рассчитаем моменты старта, с длиной сеанса 1
        var starts = instance.calcStartTimes(events, 1);
        // покажем моменты старта
        System.out.println(starts);
    }

    /**
     * Модификаторы доступа опущены для возможности тестирования
     *
     * @param events       события которые нужно зарегистрировать
     * @param workDuration время работы видеокамеры после старта
     * @return итог
     */
    List<Double> calcStartTimes(double[] events, double workDuration) {
        Arrays.sort(events);
        var result = new ArrayList<Double>();
        double currentTime = 0;
        for (var event : events)
            if (event > currentTime) {
                result.add(event);
                currentTime = event + workDuration;
            }
        return result;
    }
}
