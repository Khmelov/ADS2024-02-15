package by.it.a_khmelev.lesson02;

import java.util.ArrayList;
import java.util.Comparator;
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
        Event[] events = {
                new Event(0, 3), new Event(0, 1), new Event(1, 2), new Event(3, 5),
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

        // Sort events by their stop time in ascending order
        // This allows us to greedily select events with earliest stop time
        // while ensuring that they don't overlap with previously selected events
        Comparator<Event> stopTimeComparator = Comparator.comparingInt(e -> e.stop);
        java.util.Arrays.sort(events, stopTimeComparator);

        // Keep track of the last selected event's stop time
        int lastStopTime = from;

        for (Event event : events) {
            if (event.start >= lastStopTime && event.stop <= to) {
                // The current event doesn't overlap with the previous events
                result.add(event);
                lastStopTime = event.stop;
            }
        }

        return result;
    }
}