package by.it.group351003.zhuravski.lesson11;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyTreeSet<E extends Comparable<E>> implements Set<E> {
    class Stick {
        public E value = null;
        public Stick left = null;
        public Stick right = null;
        public Stick parent = null;
        public String toString() {
            StringBuilder builder = new StringBuilder();
            if (value != null) {
                if (left.value != null) {
                    builder.append(left.toString());
                    builder.append(", ");
                }
                builder.append(value.toString());
                if (right.value != null) {
                    builder.append(", ");
                    builder.append(right.toString());
                }
            }
            return builder.toString();
        }
    };
    Stick root;
    private int count = 0;


    private Stick find_closest(Object val) {
        Stick cur = root;
        while ((cur.value != null) && (!cur.value.equals(val))) {
            if (val.hashCode() > cur.value.hashCode()) {
                cur = cur.right;
            }
            else {
                cur = cur.left;
            }
        }
        return cur;
    }
    private Stick find_biggest(Stick root) {
        Stick cur = root;
        while (cur.value != null) {
            cur = cur.right;
        }
        cur = cur.parent;
        return cur;
    }
    private void remove_stick(Stick stick) {
        if (stick.left.value != null) {
            Stick rightest = find_biggest(stick.left);
            if (rightest.parent == stick) {
                stick.value = rightest.value;
                stick.left = rightest.left;
                stick.left.parent = stick;
            }
            else {
                stick.value = rightest.value;
                rightest.parent.right = rightest.left;
                rightest.left.parent = rightest.parent;
            }
        } else if (stick.right.value != null) {
            Stick npd = stick.right;
            stick.value = npd.value;
            stick.left = npd.left;
            npd.left.parent = stick;
            stick.right = npd.right;
            npd.right.parent = stick;
        }
        else {
            stick.value = null;
            stick.left = null;
            stick.right = null;
        }
        count--;
    }

    public MyTreeSet()  {
        root = new Stick();
    }
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        builder.append(root.toString());
        builder.append(']');
        return builder.toString();
    }
    @Override
    public int size() {
        return count;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public boolean contains(Object o) {
        Stick closest = find_closest(o);
        return closest.value != null;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(E e) {
        boolean res = false;
        Stick closest = find_closest(e);
        if (closest.value == null) {
            count++;
            closest.value = e;
            closest.left = new Stick();
            closest.left.parent = closest;
            closest.right = new Stick();
            closest.right.parent = closest;
            res = true;
        }
        return res;
    }

    @Override
    public boolean remove(Object o) {
        boolean res = false;
        Stick closest = find_closest(o);
        if (closest.value != null) {
            remove_stick(closest);
            res = true;
        }
        return res;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        int oldLen = count;
        for (E e : c) {
            add(e);
        }
        return oldLen != count;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        int oldLen = count;
        Object[] retained = new Object[count];
        int retained_am = 0;
        for (Object o : c) {
            if (contains(o)) {
                retained[retained_am] = o;
                retained_am++;
            }
        }
        clear();
        for (int i = 0; i < retained_am; i++) {
            add((E)retained[i]);
        }
        return oldLen != count;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        int oldLen = count;
        for (Object o : c) {
            remove(o);
        }
        return oldLen != count;
    }

    @Override
    public void clear() {
        while (!isEmpty()) {
            remove_stick(root);
        }
    }
}