package by.it.group351002.stepanenko.lesson02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class B_Sheduler {
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

        List<Event> starts = instance.calcStartTimes(events, 0, 10);
        System.out.println(starts);
    }

    List<Event> calcStartTimes(Event[] events, int from, int to) {
        List<Event> result = new ArrayList<>();

        Arrays.sort(events, (e1, e2) -> {
            // Сортируем события по возрастанию начального времени.
            if (e1.start != e2.start) {
                return Integer.compare(e1.start, e2.start);
            } else {
                // Если начальные времена совпадают, сортируем по возрастанию конечного времени.
                return Integer.compare(e1.stop, e2.stop);
            }
        });

        int lastStop = from; // Последнее окончание события
        for (Event event : events) {
            if (event.start >= lastStop && event.stop <= to) {
                // Если текущее событие начинается после или в момент последнего окончания
                // и заканчивается до или в момент требуемого времени "to",
                // добавляем его в результат и обновляем последнее окончание.
                result.add(event);
                lastStop = event.stop;
            }
        }

        return result;
    }
}