package by.it.group351003.lisunova.lesson02;

import java.util.ArrayList;
import java.util.List;
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
            return "(" + start + ":" + stop + ")";
        }
    }

    public static void main(String[] args) {
        B_Sheduler instance = new B_Sheduler();
        Event[] events = {new Event(0, 3), new Event(0, 1), new Event(1, 2), new Event(3, 5),
                new Event(1, 3), new Event(1, 3), new Event(1, 3), new Event(3, 6),
                new Event(2, 7), new Event(2, 3), new Event(2, 7), new Event(7, 9),
                new Event(3, 5), new Event(2, 4), new Event(2, 3), new Event(3, 7),
                new Event(4, 5), new Event(6, 7), new Event(6, 9), new Event(7, 9),
                new Event(8, 9), new Event(4, 6), new Event(8, 10), new Event(7, 10)
        };

        //рассчитаем оптимальное заполнение аудитории
        List<Event> starts = instance.calcStartTimes(events, 0, 10);
        //покажем рассчитанный график занятий
        System.out.println(starts);
    }

    public static void Sort(Event[] events) {
        int j;
        Event temp;
        for (int i = 1; i < events.length; i++) {
            j = i - 1;
            temp = events[i];
            while (j >= 0 && events[j].start > temp.start) {
                events[j + 1] = events[j];
                j--;
            }
            events[j + 1] = temp;
        }
    }

    List<Event> calcStartTimes(Event[] events, int from, int to) {
        //Events - события которые нужно распределить в аудитории
        //в период [from, int] (включительно).
        //оптимизация проводится по наибольшему числу непересекающихся событий.
        //Начало и конец событий могут совпадать.
        List<Event> result;
        Sort(events);
        result = new ArrayList<>();
        int i;
        int startValue;
        int min;
        Event addValue;
        i = 0;
        while (i < events.length) {
            min = events[i].stop;
            startValue = events[i].start;
            addValue = events[i];
            while (i < events.length && events[i].start == startValue) {
                if (events[i].stop < min) {
                    addValue = events[i];
                    min = events[i].stop;
                }
                i++;
            }
            while (i < events.length && addValue.stop > events[i].start) {
                i++;
            }
            result.add(addValue);
        }

        return result;
    }
}