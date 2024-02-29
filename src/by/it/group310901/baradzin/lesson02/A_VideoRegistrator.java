package by.it.group310901.baradzin.lesson02;

import java.util.ArrayList;
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
        List<Double> result = new ArrayList<>();
        // индекс события events[i]
        var i = 0;

        // Комментарии от проверочного решения сохранены для подсказки, но вы можете их удалить.
        // Подготовка к жадному поглощению массива событий
        // hint: сортировка Arrays.sort обеспечит скорость алгоритма
        // C*(n log n) + C1*n = O(n log n)
        // пока есть незарегистрированные события
        // получим одно событие по левому краю
        // и запомним время старта видеокамеры
        // вычислим момент окончания работы видеокамеры
        // и теперь пропустим все покрываемые события
        // за время до конца работы, увеличивая индекс

        return result;
    }
}
