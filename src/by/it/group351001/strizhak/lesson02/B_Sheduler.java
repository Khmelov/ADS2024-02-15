package by.it.group351001.strizhak.lesson02;

import java.util.ArrayList;
import java.util.Arrays;
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
        System.out.println(starts);                                 //покажем рассчитанный график занятий
    }

    List<Event> calcStartTimes(Event[] events, int from, int to) {
        //Events - события которые нужно распределить в аудитории
        //в период [from, int] (включительно).
        //оптимизация проводится по наибольшему числу непересекающихся событий.
        //Начало и конец событий могут совпадать.
        List<Event> result;
        result = new ArrayList<>();
        //ваше решение.
        int i, j, num, curr;
        Event temp;
        for (i = 0; i < events.length - 1; i++){
            num = i;
            for (j = i + 1; j < events.length; j++){
                if ((events[j].stop < events[num].stop) | (events[j].stop == events[num].stop && events[j].start < events[num].start)){
                    num = j;
                }
            }
            temp = events[i];
            events[i] = events[num];
            events[num] = temp;
        }
        //for (i = 0; i < events.length; i++) System.out.println(events[i] + " ");
        i = 0;
        curr = 0;
        while (i < events.length) {
            if (events[i].start >= from){
                result.add(events[i]);
                curr = i;
                i++;
                break;
            }
            i++;
        }


        while (i < events.length){
            if ((events[i].stop <= to) && (events[i].start >= events[curr].stop)){
                curr = i;
                result.add(events[i]);
            }
            i++;
        }
        return result;          //вернем итог
    }
}
