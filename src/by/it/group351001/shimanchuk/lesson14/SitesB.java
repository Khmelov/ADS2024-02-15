/*
 * Программа предназначена для анализа связи между веб-сайтами на основе их объединений.
 * Два веб-сайта считаются связанными, если указано, что они принадлежат одной группе.
 * Используется структура данных Disjoint Set Union (Union-Find) для моделирования объединений.
 *
 * Алгоритм:
 * 1. Считываем входные данные и создаем экземпляр структуры данных Disjoint Set.
 * 2. Пока не введено слово "end":
 *    - Считываем строку ввода, разделяем её по символу '+'.
 *    - Проверяем каждый веб-сайт на присутствие в структуре DisjointSet, если отсутствует - создаем новый.
 *    - Выполняем объединение двух сайтов через метод union.
 * 3. После завершения ввода обрабатываем данные:
 *    - Проходим по всем сайтам, чтобы найти корневые узлы каждого кластера.
 *    - Определяем размеры всех уникальных кластеров.
 * 4. Сортируем размеры кластеров по убыванию.
 * 5. Выводим размеры кластеров в порядке убывания.
 *
 * Пример:
 * Входные данные могут включать такие строки, как:
 *
 * "site1+site2"
 * "site2+site3"
 * "site4+site5"
 *
 * Эти команды объединяют сайты в группы. Затем программа подсчитывает количество сайтов в каждой группе
 * и выводит их размеры по убыванию.
 */
package by.it.group351001.shimanchuk.lesson14;

import java.util.*;

public class SitesB {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        DisJointSet<String> disjointSet = new DisJointSet<>();

        // Основной цикл для обработки ввода данных до ввода слова "end"
        while (true) {
            String input = scanner.nextLine();

            if (input.equals("end")) {
                break;
            }

            // Разделяем строку по символу '+' для обработки двух сайтов
            String[] sites = input.split("\\+");

            // Проверяем наличие сайтов в структуре, если отсутствуют, создаем их
            for (String site : sites) {
                if (!disjointSet.contains(site)) {
                    disjointSet.makeSet(site);
                }
            }

            // Объединяем два сайта в одну группу
            disjointSet.union(sites[0], sites[1]);
        }

        scanner.close();

        // Подсчет размеров кластеров
        Map<String, Integer> clusterSizes = new HashMap<>();
        HashSet<String> set = new HashSet<>();

        // Проходим по всем сайтам и определяем их корневые узлы
        for (String site : disjointSet) {
            if (set.contains(site))
                continue;
            set.add(site);
            String root = disjointSet.findSet(site);
            clusterSizes.put(root, disjointSet.getClusterSize(site));
        }

        ArrayList<Integer> temp = new ArrayList<>();

        // Добавляем размеры кластеров в список
        temp.addAll(clusterSizes.values());

        // Сортируем размеры кластеров по убыванию
        Collections.sort(temp);
        Collections.reverse(temp);

        // Выводим результаты
        for (int item : temp)
            System.out.print(item + " ");
    }
}
