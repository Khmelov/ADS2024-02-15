package by.it.group310902.strizhevskiy.lesson02;

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
        A_VideoRegistrator instance=new A_VideoRegistrator();
        double[] events=new double[]{1, 1.1, 1.6, 2.2, 2.4, 2.7, 3.9, 8.1, 9.1, 5.5, 3.7};
        List<Double> starts=instance.calcStartTimes(events,1); //рассчитаем моменты старта, с длинной сеанса 1
        System.out.println(starts);                            //покажем моменты старта
    }

    List<Double> calcStartTimes(double[] events, double workDuration){
        //events - события которые нужно зарегистрировать
        //workDuration время работы видеокамеры после старта
        List<Double> result = new ArrayList<>();
        for(double e : events){
            result.add(e);
        }

        result.sort((a,b) -> { return a <= b ? -1 : 1; });
        
        for(int i = 1; i < result.size(); i++){
            if(result.get(i) - result.get(i-1) <= workDuration){
                result.remove(i--);
            }
        }

        return result;
    }
}
