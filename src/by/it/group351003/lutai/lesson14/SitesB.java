package by.it.group351003.lutai.lesson14;

import java.util.*;

public class SitesB {

    static class DSU {
        private final Map<String, String> parent;
        private final Map<String, Integer> size;

        public DSU() {
            parent = new HashMap<>();
            size = new HashMap<>();
        }

        public String find(String site) {
            if (!parent.get(site).equals(site)) {
                parent.put(site, find(parent.get(site)));
            }
            return parent.get(site);
        }

        public void union(String site1, String site2) {
            String root1 = find(site1);
            String root2 = find(site2);

            if (!root1.equals(root2)) {
                if (size.get(root1) < size.get(root2)) {
                    parent.put(root1, root2);
                    size.put(root2, size.get(root2) + size.get(root1));
                } else {
                    parent.put(root2, root1);
                    size.put(root1, size.get(root1) + size.get(root2));
                }
            }
        }

        // Добавляем новый сайт
        public void addSite(String site) {
            if (!parent.containsKey(site)) {
                parent.put(site, site);
                size.put(site, 1);
            }
        }


        public List<Integer> getClusterSizes() {
            Map<String, Integer> clusterSizeMap = new HashMap<>();
            for (String site : parent.keySet()) {
                String root = find(site);
                clusterSizeMap.put(root, clusterSizeMap.getOrDefault(root, 0) + 1);
            }
            List<Integer> clusterSizes = new ArrayList<>(clusterSizeMap.values());
            Collections.sort(clusterSizes);
            return clusterSizes;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DSU dsu = new DSU();

        while (true) {
            String input = scanner.nextLine();
            if (input.equals("end")) break;

            String[] sites = input.split("\\+");
            String site1 = sites[0];
            String site2 = sites[1];

            dsu.addSite(site1);
            dsu.addSite(site2);


            dsu.union(site1, site2);
        }


        List<Integer> clusterSizes = dsu.getClusterSizes();
        for (int i = clusterSizes.size() - 1; i >= 0; i--) {
            int size = clusterSizes.get(i);
            System.out.print(size + " ");
        }
    }
}

