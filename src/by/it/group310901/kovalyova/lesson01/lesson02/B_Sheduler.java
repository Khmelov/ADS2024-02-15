package by.it.group310901.kovalyova.lesson01.lesson02;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Arrays;
<<<<<<< HEAD:src/by/it/group310901/kovalyova/lesson01/lesson02/B_Sheduler.java
=======
import java.util.Comparator;
>>>>>>> f7dc13e (Lesson02 A,B,C):src/by/it/group310902/perova/lesson02/B_Sheduler.java
/*
Даны интервальные события events
реализуйте метод calcStartTimes, так, чтобы число принятых к выполнению
непересекающихся событий было максимально.
Алгоритм жадный. Для реализации обдумайте надежный шаг.
*/

public class B_Sheduler {
    //событие у аудитории(два поля: начало и конец)
    static class Event {
        int start;
        int stop;

        Event(int start, int stop) {
            this.start = start;
            this.stop = stop;
        }

        @Override
        public String toString() {
            return "("+ start +":" + stop + ")";
        }
    }

    public static void main(String[] args) {
        B_Sheduler instance = new B_Sheduler();
        Event[] events = {  new Event(0, 3),  new Event(0, 1), new Event(1, 2), new Event(3, 5),
                new Event(1, 3),  new Event(1, 3), new Event(1, 3), new Event(3, 6),
                new Event(2, 7),  new Event(2, 3), new Event(2, 7), new Event(7, 9),
                new Event(3, 5),  new Event(2, 4), new Event(2, 3), new Event(3, 7),
                new Event(4, 5),  new Event(6, 7), new Event(6, 9), new Event(7, 9),
                new Event(8, 9),  new Event(4, 6), new Event(8, 10), new Event(7, 10)
        };

        List<Event> starts = instance.calcStartTimes(events,0,10);  //рассчитаем оптимальное заполнение аудитории
        System.out.println();                                 //покажем рассчитанный график занятий
    }

    List<Event> calcStartTimes(Event[] events, int from, int to) {
        //Events - события которые нужно распределить в аудитории
        //в период [from, int] (включительно).

        //Начало и конец событий могут совпадать.
        List<Event> result;
        result = new ArrayList<>();
        //ваше решение.
<<<<<<< HEAD:src/by/it/group310901/kovalyova/lesson01/lesson02/B_Sheduler.java
        Arrays.sort(events, Comparator.comparingInt(e -> e.stop));
        for (Event e : events)
        {
            if (e.start >=from && e.stop <= to)
            {
                result.add(e);
                from = e.stop;
            }
        }
=======
        Arrays.sort(events,Comparator.comparingInt(event -> event.stop));
        int start = 0;
        int i = 1;
        do {
            result.add(events[start]);
            while ((i <= (events.length-1)) && ((events[start].stop) > (events[i].start))) {
                i++;
            }
            start = i;
        } while (i <= (events.length-1));


>>>>>>> f7dc13e (Lesson02 A,B,C):src/by/it/group310902/perova/lesson02/B_Sheduler.java
        return result;          //вернем итог
    }

}