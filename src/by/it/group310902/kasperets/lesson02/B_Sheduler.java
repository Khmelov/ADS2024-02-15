package by.it.group310902.kasperets.lesson02;
/*
Даны интервальные события events
реализуйте метод calcStartTimes, так, чтобы число принятых к выполнению
непересекающихся событий было максимально.
Алгоритм жадный. Для реализации обдумайте надежный шаг.
*/


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class B_Sheduler {
    //событие у аудитории(два поля: начало и конец)
    static class Event implements Comparable<Event> {
        int start;
        int stop;

        Event(int start, int stop) {
            this.start = start;
            this.stop = stop;
        }

        @Override
        public String toString() {
            return "(" + start + ":" + stop + ")";
        }

        @Override
        public int compareTo(Event other) {
            return this.stop - other.stop;
        }
    }

    public static void main(String[] args) {
        B_Sheduler instance = new B_Sheduler();
        Event[] events = {
        };

        List<Event> starts = instance.calcStartTimes(events, 0, 10);
        System.out.println(starts);
    }

    List<Event> calcStartTimes(Event[] events, int from, int to) {
        //Events - события которые нужно распределить в аудитории
        //в период [from, int] (включительно).
        //оптимизация проводится по наибольшему числу непересекающихся событий.
        //Начало и конец событий могут совпадать.
        List<Event> result = new ArrayList<>();
        Arrays.sort(events);

        int currentTime = from;
        for (Event event : events) {
            if (event.start >= currentTime && event.stop <= to) {
                result.add(event);
                currentTime = event.stop;
            }
        }
        return result; //вернём итог
    }
}