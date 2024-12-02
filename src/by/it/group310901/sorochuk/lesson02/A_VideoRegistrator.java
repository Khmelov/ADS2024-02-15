package by.it.group310901.sorochuk.lesson02;

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
        List<Double> starts=instance.calcStartTimes(events,1); //рассчитаем моменты старта, с длинной сеанса 1
        System.out.println(starts);                            //покажем моменты старта
    }
    //модификаторы доступа опущены для возможности тестирования
    //Этот метод вычисляет моменты старта видеокамеры на основе
    // массива событий и длительности работы видеокамеры после старта.
    //Метод принимает два аргумента: массив events и workDuration
    // Создается список result, который будет содержать моменты старта видеокамеры.
    // События в массиве events сортируются по возрастанию с помощью Arrays.sort.
    // Затем происходит итерация по массиву событий:
    // Для каждого события вычисляется момент старта видеокамеры (start)
    // и момент окончания работы видеокамеры (stop).
    List<Double> calcStartTimes(double[] events, double workDuration){
        //events - события которые нужно зарегистрировать
        //timeWorkDuration время работы видеокамеры после старта
        List<Double> result;
        result = new ArrayList<>();
        int i=0;                              //i - это индекс события events[i]
        //Комментарии от проверочного решения сохранены для подсказки, но вы можете их удалить.
        //Подготовка к жадному поглощению массива событий
        //hint: сортировка Arrays.sort обеспечит скорость алгоритма
        //C*(n log n) + C1*n = O(n log n)
        Arrays.sort(events);
        //пока есть незарегистрированные события
        //получим одно событие по левому краю
        //и запомним время старта видеокамеры
        //вычислим момент окончания работы видеокамеры
        //и теперь пропустим все покрываемые события
        //за время до конца работы, увеличивая индекс
        while (i < events.length) {
            double startMoment = events[i];
            result.add(startMoment);
            while ((i < events.length) && (events[i] <= startMoment + workDuration)) {
                i++;
            }
        }


        return result;                        //вернем итог
    }
}
