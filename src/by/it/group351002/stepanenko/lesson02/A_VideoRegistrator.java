package by.it.group351002.stepanenko.lesson02;

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

        Arrays.sort(events); // Сортируем события по возрастанию

        double currentStart = events[0]; // Момент старта текущего события
        double currentEnd = currentStart + workDuration; // Момент окончания работы видеокамеры

        result.add(currentStart); // Добавляем первый момент старта

        for (int i = 1; i < events.length; i++) {
            double event = events[i];

            // Если текущее событие происходит после окончания работы видеокамеры,
            // обновляем момент старта и окончания работы видеокамеры
            if (event > currentEnd) {
                currentStart = event;
                currentEnd = currentStart + workDuration;
                result.add(currentStart);
            }
        }

        return result;
    }
}