package by.it.group351004.kostyukov.lesson02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class B_Sheduler {
    static class Event implements Comparable<Event>{
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
        @Override
        public int compareTo(Event o) {
            return this.stop - o.stop;
        }
    }

    public static void main(String[] args) {
        B_Sheduler instance = new B_Sheduler();
        B_Sheduler.Event[] events = {  new B_Sheduler.Event(0, 3),  new B_Sheduler.Event(0, 1), new B_Sheduler.Event(1, 2), new B_Sheduler.Event(3, 5),
                new B_Sheduler.Event(1, 3),  new B_Sheduler.Event(1, 3), new B_Sheduler.Event(1, 3), new B_Sheduler.Event(3, 6),
                new B_Sheduler.Event(2, 7),  new B_Sheduler.Event(2, 3), new B_Sheduler.Event(2, 7), new B_Sheduler.Event(7, 9),
                new B_Sheduler.Event(3, 5),  new B_Sheduler.Event(2, 4), new B_Sheduler.Event(2, 3), new B_Sheduler.Event(3, 7),
                new B_Sheduler.Event(4, 5),  new B_Sheduler.Event(6, 7), new B_Sheduler.Event(6, 9), new B_Sheduler.Event(7, 9),
                new B_Sheduler.Event(8, 9),  new B_Sheduler.Event(4, 6), new B_Sheduler.Event(8, 10), new B_Sheduler.Event(7, 10)
        };

        List<B_Sheduler.Event> starts = instance.calcStartTimes(events,0,10);
        System.out.println(starts);
    }
    List<B_Sheduler.Event> calcStartTimes(B_Sheduler.Event[] events, int from, int to) {
        List<B_Sheduler.Event> result;
        result = new ArrayList<>();
        double stopTime;

        Arrays.sort(events);

        result.add(events[0]);
        stopTime = events[0].stop;

        int firstEventInRange = 0;
        while (events[0].start < from) {
            firstEventInRange++;
        }

        for (int i = firstEventInRange; (i < events.length) && (stopTime <= to); i++){
            if (events[i].start >= stopTime){
                result.add(events[i]);
                stopTime = events[i].stop;
            }
        }
        return result;
    }
}