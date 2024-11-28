package by.it.group351003.zhuravski.lesson14;

import java.util.ArrayList;
import java.util.Iterator;
class DSU {
    Object val;
    int parent;
    int count;
}
public class DomDSU {
    public ArrayList<DSU> elems;
    DomDSU() {
        elems = new ArrayList<>();
    }
    public int add(Object val) {
        DSU n = new DSU();
        elems.add(n);
        int index = elems.size() - 1;
        n.parent = index;
        n.val = val;
        n.count = 1;
        return index;
    }
    private int getParent(int index) {
        DSU elem = elems.get(index);
        if (elem.parent == index) {
            return index;
        }
        else {
            int result = getParent(elem.parent);
            elem.parent = result;
            return result;
        }
    }
    private int getDepth(int index) {
        int result = 1;
        int cur = index;
        DSU elem = elems.get(cur);
        while (elem.parent != cur) {
            result++;
            cur = elem.parent;
            elem = elems.get(cur);
        }
        return result;
    }
    public Integer[] getSizes() {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < elems.size(); i++) {
            DSU elem = elems.get(i);
            if (i == elem.parent) {
                result.add(elem.count);
            }
        }
        return result.toArray(new Integer[0]);
    }
    public void union(int first, int second) {
        if ((first < 0) || (first >= elems.size()) || (second < 0) || (second >= elems.size())) {
            return;
        }
        int firstParent = getParent(first);
        int secondParent = getParent(second);
        if (firstParent != secondParent) {
            int firstDepth = getDepth(first);
            int secondDepth = getDepth(second);
            if (firstDepth > secondDepth) {
                DSU elem = elems.get(secondParent);
                elem.parent = firstParent;
                DSU elem2 = elems.get(firstParent);
                elem2.count += elem.count;
            } else {
                DSU elem = elems.get(firstParent);
                elem.parent = secondParent;
                DSU elem2 = elems.get(secondParent);
                elem2.count += elem.count;
            }
        }
    }
}
