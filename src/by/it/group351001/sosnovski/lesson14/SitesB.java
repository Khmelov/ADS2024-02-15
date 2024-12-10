package by.it.group351001.sosnovski.lesson14;

import java.util.*;

public class SitesB {

    /**
     объединяем веб-сайты в группы (кластеры), используя систему непересекающихся
     множеств (Union-Find). Каждая строка ввода представляет пару связанных сайтов,
     и программа:

     Объединяет сайты в одном кластер.
     Находит размеры всех кластеров.
     Выводит размеры кластеров в порядке убывания.
     */

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Создаём систему непересекающихся множеств
        DisJointSet<String> disjointSet = new DisJointSet<>();

        // Ввод данных до команды "end"
        while (true) {
            String input = scanner.nextLine();

            // Если ввод "end", прекращаем ввод
            if (input.equals("end")) {
                break;
            }

            // Разделяем ввод на два сайта, используя "+"
            String[] sites = input.split("\\+");

            // Добавляем сайты в систему множеств, если их ещё нет
            for (String site : sites) {
                if (!disjointSet.contains(site)) {
                    disjointSet.makeSet(site);
                }
            }

            // Объединяем сайты в один кластер
            disjointSet.union(sites[0], sites[1]);
        }

        scanner.close();

        // Словарь для хранения размеров кластеров
        Map<String, Integer> clusterSizes = new HashMap<>();
        HashSet<String> visitedRoots = new HashSet<>();

        // Перебираем все сайты
        for (String site : disjointSet) {
            // Проверяем, если корень уже учтён
            String root = disjointSet.findSet(site);
            if (visitedRoots.contains(root)) {
                continue;
            }

            // Добавляем корень в множество учтённых
            visitedRoots.add(root);

            // Записываем размер кластера
            clusterSizes.put(root, disjointSet.getClusterSize(site));
        }

        // Собираем размеры кластеров в список
        ArrayList<Integer> clusterSizesList = new ArrayList<>(clusterSizes.values());

        // Сортируем размеры кластеров по убыванию
        Collections.sort(clusterSizesList, Collections.reverseOrder());

        // Выводим размеры кластеров
        for (int size : clusterSizesList) {
            System.out.print(size + " ");
        }
    }
}
