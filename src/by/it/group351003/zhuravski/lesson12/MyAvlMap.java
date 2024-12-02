package by.it.group351003.zhuravski.lesson12;

import by.it.group351003.zhuravski.lesson11.MyTreeSet;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MyAvlMap implements Map<Integer, String> {
    class Stick {
        public Integer key = null;
        public String val = null;
        public Stick left = null;
        public Stick right = null;
        public Stick parent = null;
        public int height = 0;
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
        public int calcRL() {
            if (key == null) {
                return 0;
            }
            else {
                return right.height - left.height;
            }
        }
        public void swap_values(Stick sec) {
            int temp = key;
            key = sec.key;
            sec.key = temp;
            String temp2 = val;
            val = sec.val;
            sec.val = temp2;
        }
        public void update_height() {
            if (key == null) {
                height = 0;
            }
            else {
                height = 1 + Math.max(left.height, right.height);
            }
        }
        public void mirror_values(Stick sec) {
            key = sec.key;
            val = sec.val;
        }
    };
    Stick root;
    private int count = 0;
    public MyAvlMap() {
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

        bottom.update_height();
        root.update_height();
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

        bottom.update_height();
        root.update_height();
    }
    private void make_amendments(Stick root) {
        int shift = root.calcRL();
        if (shift == 2) {
            int nested_shift = root.right.calcRL();
            if (nested_shift < 0) {
                micro_right(root.right);
            }
            micro_left(root);
        }
        else if (shift == -2) {
            int nested_shift = root.left.calcRL();
            if (nested_shift > 0) {
                micro_left(root.left);
            }
            micro_right(root);
        }
    }
    private void rebalance(Stick root) {
        Stick cur = root;
        boolean needContinue = true;
        while ((cur != null) && needContinue)  {
            int old_height = cur.height;
            cur.update_height();
            make_amendments(cur);
            needContinue = cur.height != old_height;
            cur = cur.parent;
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
    private void remove_stick(Stick stick) {
        if (stick.left.key != null) {
            Stick rightest = find_biggest(stick.left);
            if (rightest.parent == stick) {
                stick.mirror_values(rightest);
                stick.left = rightest.left;
                stick.left.parent = stick;
                rebalance(stick);
            }
            else {
                stick.mirror_values(rightest);
                rightest.parent.right = rightest.left;
                rightest.left.parent = rightest.parent;
                rebalance(rightest.parent);
            }
        } else if (stick.right.key != null) {
            Stick npd = stick.right;
            stick.mirror_values(npd);
            stick.left = npd.left;
            npd.left.parent = stick;
            stick.right = npd.right;
            npd.right.parent = stick;
            rebalance(stick);
        }
        else {
            stick.key = null;
            stick.val = null;
            stick.left = null;
            stick.right = null;
            rebalance(stick.parent);
        }
        count--;
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
        return false;
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
            closest.left = new Stick();
            closest.left.parent = closest;
            closest.right = new Stick();
            closest.right.parent = closest;
            closest.height = 1;
            rebalance(closest.parent);
        }
        return res;
    }

    @Override
    public String remove(Object key) {
        String res = null;
        Stick closest = find_closest((Integer)key);
        if (closest.key != null) {
            res = closest.val;
            remove_stick(closest);
        }
        return res;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {

    }

    @Override
    public void clear() {
        while (!isEmpty()) {
            remove_stick(root);
        }
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
