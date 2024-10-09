package by.it.group351003.zhuravski.lesson10;

import java.util.Comparator;

public class DomesticComparator<Object> implements Comparator<Object> {
    @Override
    public int compare(Object o1, Object o2) {
        return Integer.compare(o1.hashCode(), o2.hashCode());
    }
}
