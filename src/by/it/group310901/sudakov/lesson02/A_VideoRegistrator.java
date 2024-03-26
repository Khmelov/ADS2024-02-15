package by.it.group310901.sudakov.lesson02;

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
        A_VideoRegistrator instance = new A_VideoRegistrator();
        double[] events = new double[]{1, 1.1, 1.6, 2.2, 2.4, 2.7, 3.9, 8.1, 9.1, 5.5, 3.7};
        List<Double> starts = instance.calcStartTimes(events, 1);
        System.out.println(starts);
    }

    List<Double> calcStartTimes(double[] events, double workDuration) {
        Arrays.sort(events); // Сортируем события по возрастанию времени

        List<Double> result = new ArrayList<>();
        int i = 0;

        while (i < events.length) {
            double start = events[i]; // Запоминаем время старта видеокамеры
            double end = start + workDuration; // Вычисляем время окончания работы видеокамеры
            result.add(start); // Добавляем время старта в результат

            // Находим индекс первого события, которое происходит после окончания работы видеокамеры
            while (i < events.length && events[i] <= end) {
                i++;
            }
        }

        return result;
    }
}
