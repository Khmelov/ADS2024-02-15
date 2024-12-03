package by.it.group351002.vorobei.lesson14;

import java.util.*;

public class SitesB {

    public static void main(String[] args) {
        /*В бесконечном цикле считываются строки ввода. Если ввод равен “end”, цикл прерывается.
        Строки разбиваются на пары сайтов, которые объединяются в кластеры с помощью метода union*/
        Scanner scanner = new Scanner(System.in);

        DisJointSet<String> disjointSet = new DisJointSet<>();

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("end")) {
                break;
            }

            String[] sites = input.split("\\+");

            for (String site : sites) {
                if (!disjointSet.contains(site)) {
                    disjointSet.makeSet(site);
                }
            }

            disjointSet.union(sites[0], sites[1]);
        }

        scanner.close();

        /*Создается карта для хранения размеров кластеров и множество для отслеживания уникальных корневых сайтов.
        Для каждого сайта определяется корневой сайт кластера, и если он еще не был добавлен в множество,
        добавляется размер кластера в карту.*/
        Map<String, Integer> clusterSizes = new HashMap<>();
        HashSet<String> set = new HashSet<>();
        for (String site : disjointSet) {
            if (set.contains(site))
                continue;
            set.add(site);
            String root = disjointSet.findSet(site);
            clusterSizes.put(root, disjointSet.getClusterSize(site));
        }

        //Список размеров кластеров сортируется в порядке убывания и выводится на экран.
        ArrayList<Integer> temp = new ArrayList<>();

        temp.addAll(clusterSizes.values());

        Collections.sort(temp);
        Collections.reverse(temp);

        for (int item : temp)
            System.out.print(item + " ");

    }
}
