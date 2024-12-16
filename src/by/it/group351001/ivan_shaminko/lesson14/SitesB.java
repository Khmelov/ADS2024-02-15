package by.it.group351001.ivan_shaminko.lesson14;

import java.util.*;


public class SitesB {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        DisJointSet<String> currentSet = new DisJointSet<>();

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("end")) {
                break;
            }

            String[] sites = input.split("\\+");

            for (String site : sites) {
                if (!currentSet.contains(site)) {
                    currentSet.makeSet(site);
                }
            }

            currentSet.union(sites[0], sites[1]);
        }

        scanner.close();

        Map<String, Integer> clusterSizes = new HashMap<>();
        HashSet<String> set = new HashSet<>();
        for (String site : currentSet) {
            if (set.contains(site))
                continue;
            set.add(site);
            String root = currentSet.findSet(site);
            clusterSizes.put(root, currentSet.getClusterSize(site));
        }

        ArrayList<Integer> temp = new ArrayList<>();

        temp.addAll(clusterSizes.values());

        Collections.sort(temp);

        Collections.reverse(temp);

        for (int item : temp)
            System.out.print(item + " ");

    }
}