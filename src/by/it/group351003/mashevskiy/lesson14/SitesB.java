package by.it.group351003.mashevskiy.lesson14;

import java.util.*;

public class SitesB {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Создаем экземпляр структуры данных DSU для управления множествами сайтов
        DisJointSet<String> disjointSet = new DisJointSet<>();

        // Цикл для считывания пар связанных сайтов
        while (true) {
            String input = scanner.nextLine();

            // Если введено "end", завершаем ввод
            if (input.equals("end")) {
                break;
            }

            // Разбиваем строку на сайты, используя символ "+" в качестве разделителя
            String[] sites = input.split("\\+");

            // Для каждого сайта в паре создаем множество, если его еще нет
            for (String site : sites) {
                if (!disjointSet.contains(site)) {
                    disjointSet.makeSet(site);
                }
            }

            // Объединяем сайты в одно множество
            disjointSet.union(sites[0], sites[1]);
        }

        scanner.close(); // Закрываем сканер

        // Карта для хранения размеров кластеров
        Map<String, Integer> clusterSizes = new HashMap<>();
        // Множество для отслеживания уникальных корней кластеров
        HashSet<String> set = new HashSet<>();

        // Проходим по всем сайтам в DSU для подсчета размеров кластеров
        for (String site : disjointSet) {
            // Находим корень кластера для данного сайта
            if (set.contains(site)) {
                continue; // Если корень уже был обработан, пропускаем его
            }
            set.add(site); // Добавляем корень в множество
            String root = disjointSet.findSet(site); // Находим корень
            clusterSizes.put(root, disjointSet.getClusterSize(site)); // Получаем размер кластера
        }

        // Создаем временный список для хранения размеров кластеров
        ArrayList<Integer> temp = new ArrayList<>();
        temp.addAll(clusterSizes.values()); // Добавляем размеры кластеров в список

        // Сортируем размеры кластеров в порядке убывания
        Collections.sort(temp);
        Collections.reverse(temp);

        // Выводим размеры кластеров на консоль
        for (int item : temp) {
            System.out.print(item + " ");
        }
    }
}
