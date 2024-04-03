package by.it.group351002.komar.lesson02;

import java.util.ArrayList;
import java.util.List;
/*
Даны события events
реализуйте метод calcStartTimes, так, чтобы число включений регистратора на
заданный период времени (1) было минимальным, а все события events
были зарегистрированы.
Алгоритм жадный. Для реализации обдумайте надежный шаг.
*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class A_VideoRegistrator {

    public static void main(String[] args) {
        A_VideoRegistrator instance = new A_VideoRegistrator();
        double[] events = new double[]{1, 1.1, 1.6, 2.2, 2.4, 2.7, 3.9, 8.1, 9.1, 5.5, 3.7};
        List<Double> starts = instance.calcStartTimes(events, 1);
        System.out.println(starts);
    }

    List<Double> calcStartTimes(double[] events, double workDuration) {
        List<Double> result = new ArrayList<>();
        Arrays.sort(events); // Сортируем события по возрастанию времени

        int i = 0;
        while (i < events.length) {
            double startTime = events[i]; // Момент старта видеокамеры
            double endTime = startTime + workDuration; // Момент окончания работы видеокамеры
            result.add(startTime); // Добавляем момент старта в результат

            // Пропускаем все покрываемые события
            while (i < events.length && events[i] <= endTime)
                i++;
        }

        return result;
    }
}