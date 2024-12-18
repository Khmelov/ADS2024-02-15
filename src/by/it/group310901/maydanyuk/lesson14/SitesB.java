package by.it.group310901.maydanyuk.lesson14;

import java.util.*;
/*Пусть у нас есть набор связанных через взаимные гиперссылки сайтов,
и мы хотим разбить их на кластеры в которых можно по ссылкам дойти
до любого сайта этого кластера.

Цель: объединение связанных сайтов в кластеры.*/
public class SitesB {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Создаем объект множества непересекающихся подмножеств
        DisJointSet<String> disjointSet = new DisJointSet<>();

        while (true) {
            // Считываем ввод от пользователя
            String input = scanner.nextLine();

            // Завершаем цикл, если получено слово "end"
            if (input.equals("end")) {
                break;
            }

            // Разделяем введенные строки по символу '+'
            String[] sites = input.split("\\+");

            // Создаем множества для каждого сайта, если они еще не существуют
            for (String site : sites) {
                if (!disjointSet.contains(site)) {
                    disjointSet.makeSet(site);
                }
            }

            // Объединяем первые два сайта в одно множество
            disjointSet.union(sites[0], sites[1]);
        }

        scanner.close();

        // Создаем карту для хранения размеров кластеров
        Map<String, Integer> clusterSizes = new HashMap<>();
        HashSet<String> set = new HashSet<>();
        for (String site : disjointSet) {
            if (set.contains(site))
                continue;
            set.add(site);
            // Находим корневой элемент множества
            String root = disjointSet.findSet(site);
            // Записываем размер множества в карту
            clusterSizes.put(root, disjointSet.getClusterSize(site));
        }

        /*
        // Альтернативный способ подсчета размеров кластеров
        Map<String, Integer> clusterSizes = new HashMap<>();
        for (String site : disjointSet) {
            String root = disjointSet.findSet(site);
            clusterSizes.put(root, clusterSizes.getOrDefault(root, 0) + 1);
        }
        */

        // Создаем список для хранения размеров кластеров
        ArrayList<Integer> temp = new ArrayList<>();

        // Добавляем размеры кластеров в список
        temp.addAll(clusterSizes.values());

        // Сортируем список по убыванию
        Collections.sort(temp);
        Collections.reverse(temp);

        // Выводим размеры кластеров
        for (int item : temp)
            System.out.print(item + " ");

    }
}
