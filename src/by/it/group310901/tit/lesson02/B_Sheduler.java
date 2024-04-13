package by.it.group310901.tit.lesson02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/*
Даны интервальные события events
реализуйте метод calcStartTimes, так, чтобы число принятых к выполнению
непересекающихся событий было максимально.
Алгоритм жадный. Для реализации обдумайте надежный шаг.
*/
/*
В классе B_Sheduler определен вложенный класс Event, который представляет события с определенным временем начала и окончания.
определен метод calcStartTimes, который принимает массив событий, начальное и конечное время и возвращает список событий,
которые можно выполнить в указанный промежуток времени.
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
/*
В методе main: Создается экземпляр класса B_Sheduler. Создается массив событий events.
Вызывается метод calcStartTimes для расчета времени начала выполнения событий в указанном промежутке.
Результат выводится на экран.
 */
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
    /*
    В методе calcStartTimes:События сортируются по времени окончания,
    а при равенстве времени окончания - по времени начала. Проходится по отсортированным событиям
    и добавляются в результат те, которые могут быть выполнены после окончания предыдущего события
    и до указанного конечного времени.
     */

    List<Event> calcStartTimes(Event[] events, int from, int to) {
        List<Event> result;
        result = new ArrayList<>();
        // Сортируем события по времени окончания
        Arrays.sort(events, (e1, e2) -> {
            if (e1.stop == e2.stop) {
                return e1.start - e2.start;
            } else {
                return e1.stop - e2.stop;
            }
        });

        int lastEventStop = from;
        for (Event event : events) {
            if (event.start >= lastEventStop && event.stop <= to) {
                result.add(event);
                lastEventStop = event.stop;
            }
        }

        return result;
    }
}