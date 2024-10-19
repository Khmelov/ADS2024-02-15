package by.it.group351003.zhuravski.lesson12;

import java.util.*;

public class MyRbMap implements SortedMap<Integer, String> {
    class Stick {
        public Integer key = null;
        public String val = null;
        public Stick left = null;
        public Stick right = null;
        public Stick parent = null;
        public boolean black = true;
        public String toString() {
            StringBuilder builder = new StringBuilder();
            if (key != null) {
                if (left.key != null) {
                    builder.append(left.toString());
                    builder.append(", ");
                }
                builder.append(key.toString());
                builder.append('=');
                builder.append(val);
                if (right.key != null) {
                    builder.append(", ");
                    builder.append(right.toString());
                }
            }
            return builder.toString();
        }
        public void swap_values(Stick sec) {
            int temp = key;
            key = sec.key;
            sec.key = temp;
            String temp2 = val;
            val = sec.val;
            sec.val = temp2;
        }
        public void mirror_values(Stick sec) {
            key = sec.key;
            val = sec.val;
        }
    };
    Stick root;
    private int count = 0;
    public MyRbMap() {
        root = new Stick();
    }
    private Stick find_closest(Integer key) {
        Stick cur = root;
        while ((cur.key != null) && (!cur.key.equals(key))) {
            if (key > cur.key) {
                cur = cur.right;
            }
            else {
                cur = cur.left;
            }
        }
        return cur;
    }
    private Stick find_closest_by_value(Stick root, Object val) {
        if (val.equals(root.val)) {
            return root;
        }
        Stick res = null;
        if (root.key != null) {
            res = find_closest_by_value(root.left, val);
            if (res == null) {
                res = find_closest_by_value(root.right, val);
            }
        }
        return res;
    }
    private void micro_left(Stick root) {
        Stick bottom = root.right;
        root.swap_values(bottom);

        Stick first = root.left;
        Stick second = bottom.left;
        Stick third = bottom.right;

        root.left = bottom;

        bottom.left = first;
        first.parent = bottom;

        bottom.right = second;
        second.parent = bottom;

        root.right = third;
        third.parent = root;
    }
    private void micro_right(Stick root) {
        Stick bottom = root.left;
        root.swap_values(bottom);

        Stick first = root.right;
        Stick second = bottom.left;
        Stick third = bottom.right;

        root.right = bottom;

        bottom.right = first;
        first.parent = bottom;

        root.left = second;
        second.parent = root;

        bottom.left = third;
        third.parent = bottom;
    }
    private void rebalance(Stick point) {
        if (point == root) {
            point.black = true;
        }
        else if (!point.parent.black) {
            if (!point.parent.parent.left.black && !point.parent.parent.right.black) {
                point.parent.parent.left.black = true;
                point.parent.parent.right.black = true;
                point.parent.parent.black = false;
                rebalance(point.parent.parent);
            }
            else {
                Stick grandfather = point.parent.parent;
                if (point.parent == grandfather.left) {
                    if (!point.parent.right.black) {
                        micro_left(point.parent);
                    }
                    micro_right(grandfather);
                }
                else {
                    if (!point.parent.left.black) {
                        micro_right(point.parent);
                    }
                    micro_left(grandfather);
                }
            }
        }
    }
    private Stick find_biggest(Stick root) {
        Stick cur = root;
        while (cur.key != null) {
            cur = cur.right;
        }
        cur = cur.parent;
        return cur;
    }
    private Stick find_smallest(Stick root) {
        Stick cur = root;
        while (cur.key != null) {
            cur = cur.left;
        }
        cur = cur.parent;
        return cur;
    }


    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append('{');
        builder.append(root.toString());
        builder.append('}');
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
    public boolean containsKey(Object key) {
        Stick closest = find_closest((Integer)key);
        return closest.key != null;
    }

    @Override
    public boolean containsValue(Object value) {
        Stick closest = find_closest_by_value(root, value);
        return closest != null;
    }

    @Override
    public String get(Object key) {
        Stick closest = find_closest((Integer)key);
        return closest.val;
    }

    @Override
    public String put(Integer key, String value) {
        String res = null;
        Stick closest = find_closest(key);
        if (closest.key != null) {
            res = closest.val;
            closest.val = value;
        }
        else {
            count++;
            closest.val = value;
            closest.key = key;
            closest.black = false;
            closest.left = new Stick();
            closest.left.parent = closest;
            closest.right = new Stick();
            closest.right.parent = closest;
            rebalance(closest);
        }
        return res;
    }

    @Override
    public String remove(Object key) {
        return "";
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Comparator<? super Integer> comparator() {
        return null;
    }

    @Override
    public SortedMap<Integer, String> subMap(Integer fromKey, Integer toKey) {
        return null;
    }

    @Override
    public SortedMap<Integer, String> headMap(Integer toKey) {
        return null;
    }

    @Override
    public SortedMap<Integer, String> tailMap(Integer fromKey) {
        return null;
    }

    @Override
    public Integer firstKey() {
        Stick biggest = find_smallest(root);
        return biggest.key;
    }

    @Override
    public Integer lastKey() {
        Stick biggest = find_biggest(root);
        return biggest.key;
    }

    @Override
    public Set<Integer> keySet() {
        return Set.of();
    }

    @Override
    public Collection<String> values() {
        return List.of();
    }

    @Override
    public Set<Entry<Integer, String>> entrySet() {
        return Set.of();
    }
}
