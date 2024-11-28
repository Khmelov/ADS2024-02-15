package by.it.group351003.zhuravski.lesson14;

import java.util.*;

public class SitesB {
    static DomDSU dsu;
    static HashMap<String, Integer> map;

    static int get_id(String name) {
        if (!map.containsKey(name)) {
            map.put(name, dsu.elems.size());
            dsu.add(name);
        }
        return map.get(name);
    }

    public static void main(String[] args) {
        dsu = new DomDSU();
        map = new HashMap<>();

        Scanner scan = new Scanner(System.in);
        String line = scan.nextLine();
        while (!Objects.equals(line, "end")) {
            String[] words = line.split("\\+");
            int ind1 = get_id(words[0]);
            int ind2 = get_id(words[1]);
            dsu.union(ind1, ind2);
            line = scan.nextLine();
        }
        scan.close();

        Integer[] sizes = dsu.getSizes();
        Arrays.sort(sizes);
        for (int i = sizes.length - 1; i >= 0; i--) {
            System.out.printf("%d ", sizes[i]);
        }
        System.out.println();
    }
}
